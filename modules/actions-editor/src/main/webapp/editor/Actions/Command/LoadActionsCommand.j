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
@implementation LoadActionsCommand : Command
{
}

- (void) execute
{
	var contextId = [_drawing contextId];

	CPLog.debug("Loading context " + contextId);
	$.ajax({
		type: "POST",
		url: "../service/context/" + contextId + "/load"
	}).done(function( result ) {
		CPLog.debug("Context loaded " + contextId);
		
		[self processResult: result];
	});
}

- (void) processResult: (id) aContextDefinitionRoot
{
	var contextDefinition = jQuery.parseJSON(aContextDefinitionRoot.contextdefinition);

	[self processObjectDefinitions: contextDefinition.definitions];
}

- (void) processObjectDefinitions: (id) definitions
{
	var connections = [CPMutableArray array];
	
	for (var i = 0; i < definitions.length; i++) {
		var objectDefinition = definitions[i];
		
		var elementName = objectDefinition.name;
		
		if ([elementName hasSuffix: "_CONNECTION"]) {
			[connections addObject: objectDefinition];
		} else {
			var elementId = objectDefinition.id;
			var x = objectDefinition.properties["x"];
			var y = objectDefinition.properties["y"];
			
			x = x == nil ? 0 : [x intValue];
			y = y == nil ? 0 : [y intValue];
			var properties = [CPDictionary dictionaryWithJSObject: objectDefinition.properties];
			
			CPLog.debug(properties);
			CPLog.debug([properties allKeys]);
			
			var aPoint = CGPointMake(x, y);

			if ([self isProcessor: elementName]) {
				[CreateProcessorTool 
					createFigureAt: aPoint 
					on: _drawing
					elementName: elementName 
					edit: NO
					elementId: elementId
					initialProperties: properties
					tool: nil];
			} else if ([self isMessageSource: elementName]) {
				[CreateMessageSourceTool 
					createFigureAt: aPoint 
					on: _drawing
					elementName: elementName 
					edit: NO
					elementId: elementId
					initialProperties: properties
					tool: nil];
			}
		}
		
	}

	for (var i = 0; i < connections.length; i++) {
		var connection = connections[i];
		[self processConnection: connection];
	}
}

- (id) isProcessor: (id) anElementName
{
	return [CreateProcessorTool isProcessor: anElementName];
}

- (id) isMessageSource: (id) anElementName
{
	return [CreateMessageSourceTool isMessageSource: anElementName];
}

- (void) processConnection: (id) aConnection
{
	var sourceId = aConnection.properties["source"].substring("urn:".length);
	var targetId = aConnection.properties["target"].substring("urn:".length);
	var source = [_drawing processorFor: sourceId];
	var target = [_drawing processorFor: targetId];

	var properties = nil;
	if (aConnection.properties != nil) {
		properties = [CPDictionary dictionaryWithJSObject: aConnection.properties];
	}
	

	var pointsAsString = aConnection.properties["points"];
	var points = nil;

	if (pointsAsString != nil) {
		points = [CPMutableArray array];
		var pointsArray = pointsAsString.split(";");
	
		for (var j = 0; j < pointsArray.length; j++) {
			var pointAsString = pointsArray[j];
			if (![pointAsString isEqual: @""]) {
				var pointParts = pointAsString.split(",");
				var x = pointParts[0];
				var y = pointParts[1];
				var point = CGPointMake([x intValue], [y intValue]);
				
				[points addObject: point];
			}
		}
	}

	var elementId = aConnection.id;
	var elementName = aConnection.name;
	var connectionClass = [NextInChainConnection class];
	var model;

	if ([elementName isEqual: "NEXT_IN_CHAIN_CONNECTION"]) {
		connectionClass = [NextInChainConnection class];
	} else if ([elementName isEqual: "CHOICE_CONNECTION"]) {
		connectionClass = [ChoiceConnection class];
	} else if ([elementName isEqual: "ALL_CONNECTION"]) {
		connectionClass = [AllConnection class];
	} else if ([elementName isEqual: "WIRE_TAP_CONNECTION"]) {
		connectionClass = [WireTapConnection class];
	}
	
	var figure = [connectionClass 
					source: source
					target: target
					points: points];
	[_drawing addFigure: figure];

	[figure id: elementId];
	
    if (properties != nil) {
    	CPLog.debug(@"[LoadActionsCommand] Setting initial properties of connection " + properties);
    	[[figure model] initializeWithProperties: properties];
    	CPLog.debug(@"[LoadActionsCommand] Initial properties set of connection");
    }					
}
@end