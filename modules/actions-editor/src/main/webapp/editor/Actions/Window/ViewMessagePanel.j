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
@implementation ViewMessagePanel : CPView 
{
	id _tableView;
	id _nameColumn;
	id _valueColumn;
	
	id _currentMessage;
	id _payloadValue;
	id _expressionValue;
	id _evaluateButton;
	id _expressionResult;
	id _interpreterAPI;
}

+ (ViewMessagePanel) newAt: (id) aPoint api: (id) anInterpreterAPI
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 65 * 10, 300);
	var panel = [self new];
	[panel setFrame: frame];
	[panel initialize: anInterpreterAPI];
	return panel;
}

- (id) initialize: (id) anInterpreterAPI
{
	_interpreterAPI = anInterpreterAPI;

	var payloadLabel = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [payloadLabel setStringValue: @"Payload:"];
    [payloadLabel setTextColor: [CPColor whiteColor]];
    [payloadLabel sizeToFit];
	[payloadLabel setFrameOrigin: CGPointMake(0, 0)];
	[self addSubview: payloadLabel];
	
	_payloadValue = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_payloadValue setStringValue: @""];
    [_payloadValue setTextColor: [CPColor whiteColor]];
    [_payloadValue sizeToFit];
	[_payloadValue setFrameOrigin: CGPointMake(0, 15)];
	[self addSubview: _payloadValue];
	
	var propertiesLabel = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [propertiesLabel setStringValue: @"Properties:"];
    [propertiesLabel setTextColor: [CPColor whiteColor]];
    [propertiesLabel sizeToFit];
	[propertiesLabel setFrameOrigin: CGPointMake(0, 40)];
	[self addSubview: propertiesLabel];

	var scrollFrame = CGRectMake(0, 60, 65 * 5, 134);
	var scrollView = [[CPScrollView alloc] initWithFrame: scrollFrame];
	[scrollView setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];
	[scrollView setAutohidesScrollers: YES];
	
	_tableView = [[CPTableView alloc] initWithFrame: [scrollView bounds]];
	[_tableView setDoubleAction: @selector(doubleClick:) ]; 
	[_tableView setUsesAlternatingRowBackgroundColors: YES];
	[_tableView setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];

	_nameColumn = [[CPTableColumn alloc] initWithIdentifier: @"nameColumn"];
	[[_nameColumn headerView] setStringValue: @"Name"];
	[_nameColumn setMinWidth: 100];
	[_nameColumn setEditable: NO];
	[_tableView addTableColumn: _nameColumn];

	_valueColumn = [[CPTableColumn alloc] initWithIdentifier: @"valueColumn"];
	[[_valueColumn headerView] setStringValue: @"Value"];
	[_valueColumn setMinWidth: 200];
	[_valueColumn setEditable: NO];
	[_tableView addTableColumn: _valueColumn];

    [scrollView setDocumentView: _tableView];

    [_tableView setDataSource: self];
    [_tableView setDelegate: self];

    [self addSubview: scrollView];
	[scrollView setAutoresizingMask: CPViewWidthSizable];
	
	var panelFrameSize = [self frameSize];
	
	
	var expressionLabel = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [expressionLabel setStringValue: @"Expression:"];
    [expressionLabel setTextColor: [CPColor whiteColor]];
    [expressionLabel sizeToFit];
	[expressionLabel setFrameOrigin: CGPointMake(65 * 5 + 15, 0)];
	[self addSubview: expressionLabel];
	
	_expressionValue = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_expressionValue setStringValue: @""];
    [_expressionValue sizeToFit];
    [_expressionValue setBezeled: YES];
    [_expressionValue setTextColor: [CPColor blackColor]];
    [_expressionValue setEditable: YES];
	[_expressionValue setFrameOrigin: CGPointMake(65 * 5 + 15, 25)];
    [_expressionValue setFrameSize: CGSizeMake(panelFrameSize.width / 2 - 5, 30)];
	[self addSubview: _expressionValue];

	_evaluateButton = [CPButton buttonWithTitle: @"Evaluate"];
	[_evaluateButton setButtonType: CPOnOffButton];
	[_evaluateButton setBordered: YES];
	[_evaluateButton setBezelStyle: CPRegularSquareBezelStyle];
	[_evaluateButton setFrameOrigin: CGPointMake(65 * 5 + 110, 0)];
	[_evaluateButton setTarget: self];
	[_evaluateButton sizeToFit];
	[_evaluateButton setFrameSize: CGSizeMake(65, 24)];
	[_evaluateButton setAction: @selector(evaluate)];
	[self addSubview: _evaluateButton];
	
	_expressionResult = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_expressionResult setStringValue: @""];
    [_expressionResult sizeToFit];
    [_expressionResult setBezeled: YES];
    [_expressionResult setTextColor: [CPColor blackColor]];
    [_expressionResult setEditable: YES];
	[_expressionResult setFrameOrigin: CGPointMake(65 * 5 + 15, 55)];
    [_expressionResult setFrameSize: CGSizeMake(panelFrameSize.width / 2 - 5, 130)];
	[self addSubview: _expressionResult];
}

- (void) setMessage: (id) aMessage
{
	_currentMessage = aMessage;
	
	var payload = aMessage.payload;
	if (payload == nil) {
		payload = "{null payload}";
	} else if ([payload isEqual: @""]) {
		payload = "{empty string payload}";
	}
	
	[_payloadValue setStringValue: payload];
    [_payloadValue sizeToFit];
	[self reloadData];
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

- (void) evaluationResult: (id) aResult
{
    [_expressionResult setStringValue: aResult];
}

- (void) evaluate
{
	var expression = [_expressionValue stringValue];
	[_interpreterAPI evaluate: expression];
}
@end