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
@implementation DynamicModelGenerator : CPObject
{
	id _definitions;
}

- (id) init
{
	[super init];

	_definitions = [CPDictionary dictionary];

	return self;
}

- (void) addDefinitions: (id) aListOfDefinitions
{
	for (var j = 0; j < aListOfDefinitions.length; j++) {
		var element = aListOfDefinitions[j];
		[self addDefinition: element];
	}
}

- (void) addDefinition: (id) element
{
	var key = element.name;
	[_definitions setObject: element forKey: key];
}

- (ElementModel) modelFor: (id) anElementName elementId: (id) elementId contextId: (id) contextId initialProperties: (id) properties hasBreakpoint: (id) hasBreakpoint
{
	var definition = [_definitions objectForKey: anElementName];

	if (definition != nil) {
		var displayName = definition.displayName;
		var modelClass = [DynamicElementModel class];

		if (definition.type == "MESSAGE_SOURCE") {
			modelClass = [DynamicMessageSourceModel class];
		}

	    if (elementId == nil) {
	    	newModel = [modelClass contextId: contextId elementName: anElementName];
	    } else {
	    	newModel = [modelClass contextId: contextId elementId: elementId hasBreakpoint: hasBreakpoint];
	    }

	    [newModel elementName: anElementName];
	    [newModel defaultNameValue: displayName];

	    var modelClassDefinition = definition.properties;
	    for (var i = 0; i < modelClassDefinition.length; i++) {
	    	var property = modelClassDefinition[i];
			[newModel
				addProperty: property.name
				displayName: property.displayName
				value: property.defaultValue];
	    }

	    if (properties != nil) {
    		CPLog.debug(@"Setting initial properties");
    		[newModel initializeWithProperties: properties];
    		[newModel changed];
    		CPLog.debug(@"Initial properties set");
    	}

    	return newModel;
	} else {
		return nil;
	}
}

- (id) icon: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.icon;
}

- (id) displayName: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.displayName;
}

- (id) isProcessor: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.type == "PROCESSOR";
}

- (id) isMessageSource: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.type == "MESSAGE_SOURCE";
}

- (id) isRouter: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.type == "ROUTER";
}

- (id) color: (id) anElementName
{
	var definition = [_definitions objectForKey: anElementName];
	return definition.color;
}
@end
