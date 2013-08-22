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
@implementation NewMessagePanel : CPView 
{
	id _tableView;
	id _nameColumn;
	id _valueColumn;

	id _addProperty;
	id _removeProperty;
	
	id _payloadValue;
	id _propertiesKeys;
	id _propertiesValues;
}

+ (NewMessagePanel) newAt: (id) aPoint
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 65 * 10, 300);
	var panel = [self new];
	[panel setFrame: frame];
	[panel initialize];
	return panel;
}

- (id) initialize
{
	_propertiesKeys = [CPMutableArray array];
	_propertiesValues = [CPMutableArray array];

	var payloadLabel = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [payloadLabel setStringValue: @"Initial payload:"];
    [payloadLabel setTextColor: [CPColor whiteColor]];
    [payloadLabel sizeToFit];
	[payloadLabel setFrameOrigin: CGPointMake(0, 0)];
	[self addSubview: payloadLabel];
	
	_payloadValue = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_payloadValue setStringValue: @"Insert payload here..."];
    [_payloadValue setBezeled: YES];
    [_payloadValue setTextColor: [CPColor blackColor]];
    [_payloadValue setEditable: YES];
    [_payloadValue sizeToFit];

	var panelFrameSize = [self frameSize];
	var payloadValueFrameSize = [_payloadValue frameSize];

    [_payloadValue setFrameSize: CGSizeMake(panelFrameSize.width, payloadValueFrameSize.height)];
	[_payloadValue setFrameOrigin: CGPointMake(0, 15)];
	[self addSubview: _payloadValue];
	
	_addProperty = [CPButton buttonWithTitle: @"+"];
	[_addProperty setButtonType: CPOnOffButton];
	[_addProperty setBordered: YES];
	[_addProperty setBezelStyle: CPRegularSquareBezelStyle];
	[_addProperty setAutoresizingMask: CPViewMinXMargin];
	[_addProperty setTarget: self];
	[_addProperty sizeToFit];

	var addPropertyFrameSize = [_addProperty frameSize];

	[_addProperty setFrameOrigin: CGPointMake(panelFrameSize.width - ((addPropertyFrameSize.width + 1) * 2), 50)];

	[_addProperty setAction: @selector(addProperty)];
	[self addSubview: _addProperty];

	_removeProperty = [CPButton buttonWithTitle: @"-"];
	[_removeProperty setButtonType: CPOnOffButton];
	[_removeProperty setBordered: YES];
	[_removeProperty setBezelStyle: CPRegularSquareBezelStyle];
	[_removeProperty setFrameOrigin: CGPointMake(panelFrameSize.width - ((30 + 1) * 1), 50)];
	[_removeProperty setAutoresizingMask: CPViewMinXMargin];
	[_removeProperty setTarget: self];
	[_removeProperty sizeToFit];

    [_removeProperty setFrameSize: CGSizeMake(addPropertyFrameSize.width, addPropertyFrameSize.height)];
	[_removeProperty setFrameOrigin: CGPointMake(panelFrameSize.width - ((addPropertyFrameSize.width + 1) * 1), 50)];

	[_removeProperty setAction: @selector(removeProperty)];
	[self addSubview: _removeProperty];
	
	var propertiesLabel = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [propertiesLabel setStringValue: @"Initial properties:"];
    [propertiesLabel setTextColor: [CPColor whiteColor]];
    [propertiesLabel sizeToFit];
	[propertiesLabel setFrameOrigin: CGPointMake(0, 50)];
	[self addSubview: propertiesLabel];

	var scrollFrame = CGRectMake(0, 80, 65 * 10, 115);
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
	[_nameColumn setEditable: YES];
	[_tableView addTableColumn: _nameColumn];

	_valueColumn = [[CPTableColumn alloc] initWithIdentifier: @"valueColumn"];
	[[_valueColumn headerView] setStringValue: @"Value"];
	[_valueColumn setMinWidth: 400];
	[_valueColumn setEditable: YES];
	[_tableView addTableColumn: _valueColumn];

    [scrollView setDocumentView: _tableView];

    [_tableView setDataSource: self];
    [_tableView setDelegate: self];

    [self addSubview: scrollView];
	[scrollView setAutoresizingMask: CPViewWidthSizable];
}

- (id) buildJsonMessage
{
	var payload = [_payloadValue stringValue];
	var message = {"payload" : payload, "properties" : []};
	
	for (var i = 0; i < [_propertiesKeys count] ; i++) { 
	    var key = [_propertiesKeys objectAtIndex: i];
	    var value = [_propertiesValues objectAtIndex: i];
	    
	    var property = {"key" : key, "value" : value};
	    message.properties.push(property);
	}
	
	return message;
}

- (void) addProperty
{
	[_propertiesKeys addObject: "new property name"];
	[_propertiesValues addObject: "new property value"];
	[self reloadData];
}

- (void) removeProperty
{
	var index = [_tableView selectedRow];
	[_propertiesKeys removeObjectAtIndex: index];
	[_propertiesValues removeObjectAtIndex: index];
	[self reloadData];
}

- (int) numberOfRowsInTableView: (CPTableView) aTableView 
{
	return [_propertiesKeys count];
}

- (id) tableView: (CPTableView) aTableView objectValueForTableColumn: (CPTableColumn) aTableColumn row: (int) rowIndex 
{
	if (_nameColumn == aTableColumn) {
		return [_propertiesKeys objectAtIndex: rowIndex];
	} else {
		return [_propertiesValues objectAtIndex: rowIndex];
	}
}

- (void) tableView: (CPTableView) aTableView setObjectValue: (id) aValue forTableColumn: (CPTableColumn) aTableColumn row: (int) rowIndex 
{
	if (_nameColumn == aTableColumn) {
		return [_propertiesKeys replaceObjectAtIndex: rowIndex withObject: aValue];
	} else {
		return [_propertiesValues replaceObjectAtIndex: rowIndex withObject: aValue];
	}
}

- (void) reloadData {
	[_tableView reloadData];	
}
@end