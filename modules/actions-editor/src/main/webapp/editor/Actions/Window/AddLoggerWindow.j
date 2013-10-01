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
@implementation AddLoggerWindow : CPPanel 
{
	id _elementAPI;
	id _beforeExpression;
	id _afterExpression;
}

+ (AddLoggerWindow) newAt: (id) aPoint elementAPI: (id) anElementAPI
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 663, 250);
	var window = [self alloc];
	[window initWithContentRect: frame styleMask: CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask]; //CPResizableWindowMask
	return [window initializeWithElementAPI: anElementAPI];
}

- (id) initializeWithElementAPI: anElementAPI
{
	_elementAPI = anElementAPI;

	var contentView = [self contentView];

	var _addButton = [CPButton buttonWithTitle: @"Add"];
	[_addButton setButtonType: CPOnOffButton];
	[_addButton setBordered: YES];
	[_addButton setBezelStyle: CPRegularSquareBezelStyle];
	[_addButton setFrameOrigin: CGPointMake(0, 0)];
	[_addButton setTarget: self];
	[_addButton sizeToFit];
	[_addButton setFrameSize: CGSizeMake(65, 24)];
	[_addButton setAction: @selector(add)];
	[contentView addSubview: _addButton];



	_beforeExpression = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_beforeExpression setStringValue: @"Insert expression here..."];
    [_beforeExpression setBezeled: YES];
    [_beforeExpression setTextColor: [CPColor blackColor]];
    [_beforeExpression setEditable: YES];
    [_beforeExpression sizeToFit];

	var panelFrameSize = [contentView frameSize];
	var expressionFrameSize = [_beforeExpression frameSize];

    [_beforeExpression setFrameSize: CGSizeMake(panelFrameSize.width, 70)];
	[_beforeExpression setFrameOrigin: CGPointMake(0, 30)];
	[contentView addSubview: _beforeExpression];
	


	_afterExpression = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_afterExpression setStringValue: @"Insert expression here..."];
    [_afterExpression setBezeled: YES];
    [_afterExpression setTextColor: [CPColor blackColor]];
    [_afterExpression setEditable: YES];
    [_afterExpression sizeToFit];

	var panelFrameSize = [contentView frameSize];
	var expressionFrameSize = [_afterExpression frameSize];

    [_afterExpression setFrameSize: CGSizeMake(panelFrameSize.width, 70)];
	[_afterExpression setFrameOrigin: CGPointMake(0, 110)];
	[contentView addSubview: _afterExpression];

	return self;
}

- (void) add
{
	[_elementAPI 
		addLogger: [_beforeExpression stringValue]
		afterExpression: [_afterExpression stringValue]];
	
	[self close];
}

@end