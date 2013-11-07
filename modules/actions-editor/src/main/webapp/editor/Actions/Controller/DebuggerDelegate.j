/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@import <Foundation/CPObject.j>

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation DebuggerDelegate : CPObject 
{
	@outlet CPWindow _window;

	@outlet id _payloadContainerTF;
	@outlet id _exceptionContainerTF;
	@outlet id _evaluateContainerTF;
	@outlet id _evaluateResultContainerTF;
	@outlet id _tableView;
	@outlet id _nameColumn;
	@outlet id _stepButton;
	@outlet id _resumeButton;
	@outlet id _evaluateButton;
	
	id _payloadTF;
	id _exceptionTF;
	id _evaluateTF;
	id _evaluateResultTF;
	id _propertiesKeys;
	id _propertiesValues;
	
	id _drawing;
	id _currentProcessor;
	id _currentMessage;
	id _interpreterAPI;
	id _development;
}

- (void) awakeFromCib
{
	_propertiesKeys = [CPMutableArray array];
	_propertiesValues = [CPMutableArray array];
	
	_payloadTF = [ActionsDrawing createMultilineText: [_payloadContainerTF frame]];
	_exceptionTF = [ActionsDrawing createMultilineText: [_exceptionContainerTF frame]];
	_evaluateTF = [ActionsDrawing createMultilineText: [_evaluateContainerTF frame]];
	_evaluateResultTF = [ActionsDrawing createMultilineText: [_evaluateResultContainerTF frame]];
	
	[_payloadContainerTF addSubview: _payloadTF];
	[_exceptionContainerTF addSubview: _exceptionTF];
	[_evaluateContainerTF addSubview: _evaluateTF];
	[_evaluateResultContainerTF addSubview: _evaluateResultTF];
	
    [_tableView setDataSource: self];
    [_tableView setDelegate: self];
    
    _drawing = [Actions drawing];
    _development = [Actions isDevelopment];
    _interpreterAPI = [[_window windowController] interpreterAPI];
    
    [[Actions controller]
    	register: self
    	asDebugDelegateFor: [[_window windowController] id]];
}

- (id) interpreterAPI
{
	return [[_window windowController] interpreterAPI];
}

- (id) drawing
{
	return [[_window windowController] drawing];
}

- (id) currentProcessor
{
	return [[_window windowController] currentProcessor];
}

- (IBAction) resume: (id) aSender
{
	[_drawing unselectAll];
	[_interpreterAPI resume];
	[_stepButton setEnabled: NO];
	[_resumeButton setEnabled: NO];

	if (_currentProcessor != nil) {
		[_currentProcessor unhighlight];
	}
}

- (IBAction) step: (id) aSender
{
	[_drawing unselectAll];
	[_interpreterAPI step];

	if (_currentProcessor != nil) {
		[_currentProcessor unhighlight];
	}
}

- (IBAction) evaluate: (id) aSender
{
	var expression = [_evaluateTF stringValue];
	[_interpreterAPI evaluate: expression];
}

- (void) evaluationResult: (id) aResult
{
    [_evaluateResultTF setStringValue: aResult];
}

- (int) numberOfRowsInTableView: (CPTableView) aTableView 
{
    if (_currentMessage == nil) {
		return 0;
	} else {
		return _currentMessage.properties.length;
	}	
}

- (id) tableView: (CPTableView) aTableView objectValueForTableColumn: (CPTableColumn) aTableColumn row: (int) rowIndex 
{
	if (_nameColumn == aTableColumn) {
		return _currentMessage.properties[rowIndex].key;
	} else {
		return _currentMessage.properties[rowIndex].value;
	}
}

- (void) tableView: (CPTableView) aTableView setObjectValue: (id) aValue forTableColumn: (CPTableColumn) aTableColumn row: (int) rowIndex 
{
}

- (void) reloadData {
	[_tableView reloadData];	
}

- (void) process: (id) aMessage
{
	var eventType = aMessage.eventType;
	var currentMessage = aMessage.currentMessage;
	var processorId = aMessage.processorId;
	var jsonCurrentMessage = JSON.stringify(aMessage);
	var exception = aMessage.exception;

	CPLog.debug("[DebuggerDelegate] Processor id " + processorId);
	CPLog.debug("[DebuggerDelegate] " + jsonCurrentMessage);

	if ([eventType isEqualToString: "breakpoint"]) {
		[self processBreakpoint: aMessage processorId: processorId];
	} else if ([eventType isEqualToString: "before-step"]) {
		[self processBeforeStep: aMessage processorId: processorId];
	} else if ([eventType isEqualToString: "after-step"]) {
		[self processAfterStep: aMessage processorId: processorId];
	} else if ([eventType isEqualToString: "uncaught-exception"]) {
		[self processUncaughtException: aMessage processorId: processorId exception: exception];
	} else if ([eventType isEqualToString: "finish-interpretation"]) {
		[self processFinish: aMessage];
	}
}

- (void) setTitle: (id) aTitle
{
	[_window setTitle: aTitle];
}

- (void) close
{
	[_window close];
}

- (void) processUncaughtException: (id) aMessage processorId: (id) processorId exception: (id) anException
{
	[_stepButton setEnabled: NO];
	[_resumeButton setEnabled: NO];
	[_evaluateButton setEnabled: NO];
	[_exceptionTF setStringValue: anException];
	[self showMessage: aMessage];
		
	var figure = [_drawing processorFor: processorId];
	_currentProcessor = figure;
		
	CPLog.debug("[DEBUG WINDOW] >> processUncaughtException " + _currentProcessor);
		
	if (figure != nil) {
		[self setTitle: @"Uncaught exception at " + [[figure model] propertyValue: @"name"]];
		[_drawing unselectAll];
		[figure select];
		[_drawing selectedFigure: figure];
		[figure highlight];
	}
}

- (void) processFinish: (id) aMessage
{
	if ([[_interpreterAPI threadId] isEqual: @"1"]) {
		[self setTitle: @"Finished"];
		[self showMessage: aMessage];
		[_stepButton setEnabled: NO];
		[_resumeButton setEnabled: NO];
		[_evaluateButton setEnabled: NO];
	} else {
		[self close];
	}
}

- (void) processAfterStep: (id) aMessage processorId: (id) processorId
{
	if (_development) {
		var figure = [_drawing processorFor: processorId];
		if (figure != nil) {
			var point = [figure middleRight];
			var shiftedPoint = CGPointMake(point.x + 8, point.y - 4);
			[self showMarkAt: shiftedPoint];
		}
		[self showMessage: aMessage];
	}
}

- (void) processBeforeStep: (id) aMessage processorId: (id) processorId
{
	if (_development) {
		var figure = [_drawing processorFor: processorId];
		if (figure != nil) {
			var point = [figure middleLeft];
			var shiftedPoint = CGPointMake(point.x - 8, point.y - 4);
			[self showMarkAt: shiftedPoint];
		}
		[self showMessage: aMessage];
	}
}

- (void) processBreakpoint: (id) aMessage processorId: (id) processorId
{
	[_stepButton setEnabled: YES];
	[_resumeButton setEnabled: YES];
	[self showMessage: aMessage];
	
	var figure = [_drawing processorFor: processorId];
	_currentProcessor = figure;
	
	CPLog.debug("[DEBUG WINDOW] >> processBreakpoint" + _currentProcessor);
	
	if (figure != nil) {
		[self setTitle: @"Stopped at " + [[figure model] propertyValue: @"name"]];
		[_drawing unselectAll];
		[figure select];
		[_drawing selectedFigure: figure];
		[figure highlight];
	}
}

- (void) showMarkAt: (id) aPoint
{
	if (_currentProcessor != nil) {
		[_currentProcessor fadeOut];
		[_currentProcessor removeFromSuperview];
	}
	
	CPLog.debug("Showing debug mark at: " + aPoint);
	
	var stopFigure = [ImageFigure initializeWithImage: "Resources/stop.gif" x: aPoint.x y: aPoint.y];
	
	[_drawing addFigure: stopFigure];
	_currentProcessor = stopFigure;
}

- (void) showMessage: (id) aMessage
{
	_currentMessage = aMessage.currentMessage;
	
	var payload = _currentMessage.payload;
	if (payload == nil) {
		payload = "{null payload}";
	} else if ([payload isEqual: @""]) {
		payload = "{empty string payload}";
	}
	
	[_payloadTF setStringValue: payload];
	[self reloadData];
}
@end