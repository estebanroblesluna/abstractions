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
@implementation SendMessageDelegate : CPObject 
{
	@outlet CPWindow _window;

	@outlet id _payloadTF;
	@outlet id _isExpressionCB;
	@outlet id _tableView;

	id _propertiesKeys;
	id _propertiesValues;
}

- (void) awakeFromCib
{
	_propertiesKeys = [CPMutableArray array];
	_propertiesValues = [CPMutableArray array];
	
	[_tableView setDoubleAction: @selector(doubleClick:) ]; 
    [_tableView setDataSource: self];
    [_tableView setDelegate: self];
}

- (id) buildJsonMessage
{
	var payload = [_payloadTF stringValue];
	var message = {"payload" : payload, "properties" : []};
	
	for (var i = 0; i < [_propertiesKeys count] ; i++) { 
	    var key = [_propertiesKeys objectAtIndex: i];
	    var value = [_propertiesValues objectAtIndex: i];
	    
	    var property = {"key" : key, "value" : value};
	    message.properties.push(property);
	}
	
	return message;
}

- (id) interpreterAPI
{
	//TODO
}

- (IBAction) run: (id) aSender
{
	var initialMessage = [self buildJsonMessage];
	[[self interpreterAPI] run: initialMessage];
}

- (IBAction) debug: (id) aSender
{
	var initialMessage = [self buildJsonMessage];
	[[self interpreterAPI] debug: initialMessage];
}

- (IBAction) addProperty: (id) aSender
{
	[_propertiesKeys addObject: "new property name"];
	[_propertiesValues addObject: "new property value"];
	[self reloadData];
}

- (IBAction) removeProperty: (id) aSender
{
	var index = [_tableView selectedRow];
	[_propertiesKeys removeObjectAtIndex: index];
	[_propertiesValues removeObjectAtIndex: index];
	[self reloadData];
}

- (void) reloadData {
	[_tableView reloadData];	
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

@end