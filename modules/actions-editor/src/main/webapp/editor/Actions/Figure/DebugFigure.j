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
@implementation DebugFigure : Figure 
{
	id _interpreterAPI;
	id _label;
	id _currentFigure;
}

+ (DebugFigure) newAt: (id) aPoint contextId: (id) aContextId elementId: (id) anElementId
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 200, 300);
	var figure = [self frame: frame];

	return [figure initializeWithContextId: aContextId elementId: anElementId];
}

- (id) initializeWithContextId: (id) aContextId elementId: (id) anElementId
{
	_currentFigure = nil;

	var button = [CPButton buttonWithTitle:@"Run"];
	[button setButtonType: CPOnOffButton];
	[button setBordered: YES];
	[button setBezelStyle: CPRegularSquareBezelStyle];
	[button setFrameSize: CGSizeMake(50, 30)];
	[button setFrameOrigin: CGPointMake(0, 0)];
	[button setTarget: self];
	[button setAction: @selector(run)];
	[button sizeToFit];
	[self addSubview: button];
	
	var y = [button frameSize].height;
	_label = [LabelFigure initializeWithText: @"Debug" at: CGPointMake(0, y)];
	[self addSubview: _label];
	
	_interpreterAPI = [InterpreterAPI newIn: aContextId elementId: anElementId];
	[_interpreterAPI delegate: self];
	[_interpreterAPI createInterpreter];
	
	return self;
}

- (void) run
{
	[_interpreterAPI run];
}

- (void) drawRect:(CGRect)rect on: (id)context
{
    CGContextSetFillColor(context, [CPColor lightGrayColor]); 
    CGContextFillRect(context, [self bounds]); 
}

- (void) createInterpreter: (id) anInterpreterId
{
	[[self drawing] registerDebug: self for: anInterpreterId];
}

- (void) process: (id) aMessage
{
	var eventType = aMessage.eventType;
	var currentMessage = aMessage.currentMessage;
	var processorId = aMessage.processorId;
	var jsonCurrentMessage = JSON.stringify(aMessage);

	CPLog.debug("Processor id " + processorId);

	[_label setText: jsonCurrentMessage];
	
	
	if ([eventType isEqualToString: "breakpoint"]) {
	
	} else if ([eventType isEqualToString: "before-step"]) {
		var figure = [[self drawing] processorFor: processorId];
		if (figure != nil) {
			var point = [figure middleLeft];
			var shiftedPoint = CGPointMake(point.x - 8, point.y - 4);
			[self showMarkAt: shiftedPoint];
		}
	
	} else if ([eventType isEqualToString: "after-step"]) {
		var figure = [[self drawing] processorFor: processorId];
		if (figure != nil) {
			var point = [figure middleRight];
			var shiftedPoint = CGPointMake(point.x + 8, point.y - 4);
			[self showMarkAt: shiftedPoint];
		}
	
	} else if ([eventType isEqualToString: "finish-interpretation"]) {
		if (_currentFigure != nil) {
			[_currentFigure fadeOut];
		}
	}
}

- (void) showMarkAt: (id) aPoint
{
	if (_currentFigure != nil) {
		[_currentFigure fadeOut];
		[_currentFigure removeFromSuperview];
	}
	
	CPLog.debug("Showing debug mark at: " + aPoint);
	
	var stopFigure = [ImageFigure initializeWithImage: "Resources/stop.gif" x: aPoint.x y: aPoint.y];
	
	[[self drawing] addFigure: stopFigure];
	_currentFigure = stopFigure;
}
@end