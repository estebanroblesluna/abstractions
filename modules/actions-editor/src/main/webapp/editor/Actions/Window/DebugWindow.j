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

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation DebugWindow : CPPanel 
{
	id _interpreterAPI;
	id _label;
	id _currentFigure;
	id _currentProcessor;
	id _drawing;
	
	id _debugButton;
	id _runButton;
	id _stepButton;
	id _resumeButton;
	
	id _newMessagePanel;
	id _viewMessagePanel;
	
	id _development;
	id _state;
}

+ (DebugWindow) newAt: (id) aPoint contextId: (id) aContextId elementId: (id) anElementId drawing: (id) aDrawing
{
	return [self newAt: aPoint contextId: aContextId elementId: anElementId drawing: aDrawing interpreterId: nil threadId: nil];
}

+ (DebugWindow) newAt: (id) aPoint contextId: (id) aContextId elementId: (id) anElementId drawing: (id) aDrawing interpreterId: (id) anInterpreterId threadId: (id) aThreadId
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 663, 222);
	var window = [self alloc];
	[window initWithContentRect: frame styleMask: CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask]; //CPResizableWindowMask
	return [window initializeWithContextId: aContextId elementId: anElementId drawing: aDrawing interpreterId: anInterpreterId threadId: aThreadId];
}

- (id) initializeWithContextId: (id) aContextId elementId: (id) anElementId drawing: (id) aDrawing interpreterId: (id) anInterpreterId threadId: (id) aThreadId
{
	_currentFigure = nil;
	_development = [Actions isDevelopment];
	_drawing = aDrawing;

	var processorFigure = [_drawing processorFor: anElementId]
	[self setFloatingPanel: YES];

	var contentView = [self contentView];

	_runButton = [CPButton buttonWithTitle: @"Run"];
	[_runButton setButtonType: CPOnOffButton];
	[_runButton setBordered: YES];
	[_runButton setBezelStyle: CPRegularSquareBezelStyle];
	[_runButton setFrameOrigin: CGPointMake(0, 0)];
	[_runButton setTarget: self];
	[_runButton sizeToFit];
	[_runButton setFrameSize: CGSizeMake(65, 24)];
	[_runButton setAction: @selector(run)];
	[contentView addSubview: _runButton];

	_debugButton = [CPButton buttonWithTitle: @"Debug"];
	[_debugButton setButtonType: CPOnOffButton];
	[_debugButton setBordered: YES];
	[_debugButton setBezelStyle: CPRegularSquareBezelStyle];
	[_debugButton setFrameOrigin: CGPointMake(65 * 1 + 1, 0)];
	[_debugButton setTarget: self];
	[_debugButton sizeToFit];
	[_debugButton setFrameSize: CGSizeMake(65, 24)];
	[_debugButton setAction: @selector(debug)];
	[contentView addSubview: _debugButton];
	
	_stepButton = [CPButton buttonWithTitle: @"Step"];
	[_stepButton setButtonType: CPOnOffButton];
	[_stepButton setBordered: YES];
	[_stepButton setBezelStyle: CPRegularSquareBezelStyle];
	[_stepButton setFrameOrigin: CGPointMake(663 - ((65 + 1) * 1), 0)];
	[_stepButton setAutoresizingMask: CPViewMinXMargin];
	[_stepButton setTarget: self];
	[_stepButton sizeToFit];
	[_stepButton setFrameSize: CGSizeMake(65, 24)];
	[_stepButton setAction: @selector(step)];
	[_stepButton setEnabled: NO];
	[contentView addSubview: _stepButton];

	_resumeButton = [CPButton buttonWithTitle: @"Resume"];
	[_resumeButton setButtonType: CPOnOffButton];
	[_resumeButton setBordered: YES];
	[_resumeButton setBezelStyle: CPRegularSquareBezelStyle];
	[_resumeButton setFrameOrigin: CGPointMake(663 - ((65 + 1) * 2), 0)];
	[_resumeButton setAutoresizingMask: CPViewMinXMargin];
	[_resumeButton setTarget: self];
	[_resumeButton sizeToFit];
	[_resumeButton setFrameSize: CGSizeMake(65, 24)];
	[_resumeButton setAction: @selector(resume)];
	[_resumeButton setEnabled: NO];
	[contentView addSubview: _resumeButton];	
	
	var y = 30;
	_label = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_label setStringValue: @"Debug console"];
    [_label setTextColor: [CPColor whiteColor]];
    [_label sizeToFit];
	[_label setFrameOrigin: CGPointMake(0, y)];
    [_label setHidden: YES];
	[contentView addSubview: _label];

	_interpreterAPI = [InterpreterAPI newIn: aContextId elementId: anElementId];
	[_interpreterAPI delegate: self];
    
    _newMessagePanel = [NewMessagePanel newAt: CGPointMake(0, y)];
    [_newMessagePanel setFrameSize: CGSizeMake(663, 222 - y)];
	[_newMessagePanel setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];
	[contentView addSubview: _newMessagePanel];

    _viewMessagePanel = [ViewMessagePanel newAt: CGPointMake(0, y) api: _interpreterAPI];
    [_viewMessagePanel setHidden: YES];
    [_viewMessagePanel setFrameSize: CGSizeMake(663, 222 - y)];
	[_viewMessagePanel setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];
	[contentView addSubview: _viewMessagePanel];
                            				
	if (anInterpreterId == nil) {
		CPLog.debug("[DEBUG WINDOW] Creating new interpreter");
		
		[self setTitle: @"Send message to " + [[processorFigure model] propertyValue: @"Name"]];
		[_interpreterAPI createInterpreter];
	} else {
		CPLog.debug("[DEBUG WINDOW] Creating new interpreter for thread " + aThreadId);

		_state = @"Debugging";
		[_runButton setEnabled: NO];
		[_debugButton setEnabled: NO];
		[_newMessagePanel setHidden: YES];
		[_viewMessagePanel setHidden: NO];
		[_interpreterAPI interpreterId: anInterpreterId threadId: aThreadId];
	}
	
	return self;
}

- (void) run
{
	var initialMessage = [_newMessagePanel buildJsonMessage];
	[_newMessagePanel setHidden: YES];
	[_interpreterAPI run: initialMessage];
	_state = @"Running";
	
	[_runButton setEnabled: NO];
	[_debugButton setEnabled: NO];
}

- (void) debug
{
	var initialMessage = [_newMessagePanel buildJsonMessage];
	[_newMessagePanel setHidden: YES];
	[_interpreterAPI debug: initialMessage];
	_state = @"Debugging";

	[_runButton setEnabled: NO];
	[_debugButton setEnabled: NO];
}

- (void) step
{
	[_drawing unselectAll];
	[_interpreterAPI step];

	if (_currentProcessor != nil) {
		[_currentProcessor unhighlight];
	}
	
}

- (void) resume
{
	[_drawing unselectAll];
	[_interpreterAPI resume];
	[_stepButton setEnabled: NO];
	[_resumeButton setEnabled: NO];

	if (_currentProcessor != nil) {
		[_currentProcessor unhighlight];
	}
}

- (void) drawRect:(CGRect)rect on: (id)context
{
    CGContextSetFillColor(context, [CPColor lightGrayColor]); 
    CGContextFillRect(context, [self bounds]); 
}

- (void) createInterpreter: (id) anInterpreterId
{
	//be the main thread
	[_drawing registerDebug: self for: (anInterpreterId + "#1")];
}

- (void) process: (id) aMessage
{
	var eventType = aMessage.eventType;
	var currentMessage = aMessage.currentMessage;
	var processorId = aMessage.processorId;
	var jsonCurrentMessage = JSON.stringify(aMessage);

	CPLog.debug("[DEBUG WINDOW] Processor id " + processorId);
	CPLog.debug("[DEBUG WINDOW] " + jsonCurrentMessage);

	[_label setStringValue: jsonCurrentMessage];
    [_label sizeToFit];
	
	if ([eventType isEqualToString: "breakpoint"]) {
		[self processBreakpoint: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "before-step"]) {
		[self processBeforeStep: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "after-step"]) {
		[self processAfterStep: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "finish-interpretation"]) {
		[self processFinish: currentMessage];
	}
}

- (void) processFinish: (id) aMessage
{
	if ([[_interpreterAPI threadId] isEqual: @"1"]) {
		[self setTitle: @"Finished"];
		if (_currentFigure != nil) {
			[_currentFigure fadeOut];
		}
		[self showMessage: aMessage];
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
	if ([_state isEqualToString: @"Debugging"]) {
		[_stepButton setEnabled: YES];
		[_resumeButton setEnabled: YES];
		[self showMessage: aMessage];
		
		var figure = [_drawing processorFor: processorId];
		_currentProcessor = figure;
		
		CPLog.debug("[DEBUG WINDOW] >> processBreakpoint" + _currentProcessor);
		
		if (figure != nil) {
			[self setTitle: @"Stopped at " + [[figure model] propertyValue: @"Name"]];
			[_drawing unselectAll];
			[figure select];
			[_drawing selectedFigure: figure];
			[figure highlight];
		}
	}
}

- (void) evaluationResult: (id) aResult currentMessage: (id) aMessage
{
	if ([_state isEqualToString: @"Debugging"]) {
		[_viewMessagePanel evaluationResult: aResult];
		[self showMessage: aMessage];
	}
}

- (void) showMessage: (id) aMessage
{
	[_viewMessagePanel setMessage: aMessage];
	[_viewMessagePanel setHidden: NO];
}

- (void) showMarkAt: (id) aPoint
{
	if (_currentFigure != nil) {
		[_currentFigure fadeOut];
		[_currentFigure removeFromSuperview];
	}
	
	CPLog.debug("Showing debug mark at: " + aPoint);
	
	var stopFigure = [ImageFigure initializeWithImage: "Resources/stop.gif" x: aPoint.x y: aPoint.y];
	
	[_drawing addFigure: stopFigure];
	_currentFigure = stopFigure;
}
@end