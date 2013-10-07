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

var ElementNextInChainConnection = @"NEXT_IN_CHAIN";
var ElementAllConnection         = @"ALL";
var ElementChoiceConnection      = @"CHOICE";
var ElementWireTapConnection     = @"WIRE_TAP";

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation ElementFigure : IconLabelFigure 
{ 
	id _delegate;
} 

- (id) initWithFrame: (CGRect) aFrame iconUrl: (id) iconUrl
{
	[super initWithFrame: aFrame iconUrl: iconUrl];

	[[Actions mode]	createElementFigureMenu: self];

	return self;
}

- (void) delegate: (id) aDelegate
{
	_delegate = aDelegate;

	if ([self elementId] != nil) {
		[_delegate registerElement: self for: [self elementId]];
	}
}

- (void) deleteFromServer
{
	[[self model] deleteFromServer];
}

- (void) deleted
{
   [self removeMyself];
}

- (id) elementId
{
	return [[self model] elementId];
}

- (id) nextInChain: (id) aConnection
{
	CPLog.debug("[ElementFigure] Setting next in chain");
	return [[[self model] api]
				addConnectionType: ElementNextInChainConnection
				to: [[aConnection target] elementId]
				identificable: aConnection];
}

- (id) addAllConnection: (id) aConnection
{
	CPLog.debug("[ElementFigure] Adding all connection");
	return [[[self model] api]
				addConnectionType: ElementAllConnection
				to: [[aConnection target] elementId]
				identificable: aConnection];
}

- (id) addWireTapConnection: (id) aConnection
{
	CPLog.debug("[ElementFigure] Adding wire tap connection");
	return [[[self model] api]
				addConnectionType: ElementWireTapConnection
				to: [[aConnection target] elementId]
				identificable: aConnection];
}

- (id) addChoiceConnection: (id) aConnection
{
	CPLog.debug("[ElementFigure] Adding choice connection");
	return [[[self model] api]
				addConnectionType: ElementChoiceConnection
				to: [[aConnection target] elementId]
				identificable: aConnection];
}

- (void) nextInChainId: (id) anId for: (id) anElementId
{
   	for (var i = 0; i < [_outConnections count]; i++) { 
		var outConnection = [_outConnections objectAtIndex:i];
		if ([outConnection isKindOfClass: [NextInChainConnection class]] 
				&& [[[outConnection target] elementId] isEqual: anElementId]) {
			[outConnection id: anId];
		}
	}
}

- (void) elementId: (id) anElementId
{
   CPLog.debug("[ElementFigure] Element created with id " + anElementId);
   if (_delegate != nil) {
		[_delegate registerElement: self for: anElementId];
   }
}

- (void) model: (id) aModel
{
	[super model: aModel];
	[aModel delegate: self];
}

- (id) acceptsNewStartingChain
{
	 return true;
}

- (id) acceptsNewEndingChain
{
	return true;
}

- (id) hasNextInChainConnections
{
	for (var i = 0; i < [_outConnections count]; i++) { 
		var outConnection = [_outConnections objectAtIndex:i];
		if ([outConnection isKindOfClass: [NextInChainConnection class]]) {
			return true;
		}
	}
	
	return false;
}

- (id) countConnectionsOfType: (id) anElementName
{
	var count = 0;
	for (var i = 0; i < [_outConnections count]; i++) { 
		var outConnection = [_outConnections objectAtIndex:i];
		if ([[outConnection elementName] isEqual: anElementName]) {
			count++;
		}
	}
	
	return count;
}
@end