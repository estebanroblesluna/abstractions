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
@implementation CreateElementConnectionTool : AbstractCreateConnectionTool
{
	id _elementName;
	id _acceptedSourceTypes;
	id _acceptedSourceMax;
	id _acceptedTargetTypes;
	id _acceptedTargetMax;
}

+ (id) drawing: (Drawing) aDrawing acceptedSourceTypes: (id) aListOfSources acceptedSourceMax: (id) sourceMax acceptedTargetTypes: (id) aListOfTargets acceptedTargetMax: (id) targetMax elementName: (id) elementName
{
	var tool = [super drawing: aDrawing figure: [ElementConnection class]];

	[tool acceptedSourceTypes: aListOfSources];
	[tool acceptedSourceMax: sourceMax];
	[tool acceptedTargetTypes: aListOfTargets];
	[tool acceptedTargetMax: targetMax];
	[tool elementName: elementName];

	return tool;
}

- (id) acceptsNewStartingConnection: (id) aFigure
{
	if (aFigure == nil) {
		return false;
	}
	
	if (![aFigure isKindOfClass: [ElementFigure class]] || (_acceptedSourceTypes != nil && (![_acceptedSourceTypes containsObject: [[aFigure model] elementName]]))) {
		CPLog.debug("[CreateElementConnectionTool] Not an accepted source element");
		return false;
	}
	
	var count = [aFigure countConnectionsOfType: _elementName];
	if (count > _acceptedSourceMax) {
		return false;
	}

	return true;
}

- (id) acceptsNewEndingConnection: (id) aFigure
{
	if (aFigure == nil) {
		return false;
	}
		
	if (![aFigure isKindOfClass: [ElementFigure class]] || (_acceptedTargetTypes != nil && (![_acceptedTargetTypes containsObject: [[aFigure model] elementName]]))) {
		CPLog.debug("[CreateElementConnectionTool] Not an accepted target element");
		return false;
	}
	
	var count = [aFigure countConnectionsOfType: _elementName];
	if (count > _acceptedTargetMax) {
		return false;
	}

	return true;
}

- (void) postConnectionCreated: (Connection) aConnectionFigure
{
	[self postConnectionCreated: aConnectionFigure figureId: ELEMENT_FAKE_ID properties: nil];
}

- (void) postConnectionCreated: (Connection) aConnectionFigure figureId: (id) anID properties: (id) aSetOfProperties
{
	var generator = [_drawing generator];
	var contextId = [_drawing contextId];
	var color = [generator color: _elementName];
	var source = [aConnectionFigure source];
	var target = [aConnectionFigure target];
	var targetId = [target elementId];
	
	var newModel = [generator 
		modelFor: _elementName
		elementId: anID 
		contextId: contextId 
		initialProperties: aSetOfProperties
		hasBreakpoint: false];

	if ([ELEMENT_FAKE_ID isEqual: anID]) {
		[[[source model] api]
			addConnectionType: _elementName 
			to: targetId 
			identificable: [newModel api]];
	}
	
	[aConnectionFigure model: newModel];
	[aConnectionFigure foregroundColor: [CPColor colorWithHexString: color]];
}

- (void) elementName: aValue
{
	_elementName = aValue;
}

- (void) acceptedSourceTypes: aValue
{
	_acceptedSourceTypes = aValue;
}

- (void) acceptedSourceMax: aValue
{
	_acceptedSourceMax = aValue;
}

- (void) acceptedTargetTypes: aValue
{
	_acceptedTargetTypes = aValue;
}

- (void) acceptedTargetMax: aValue
{
	_acceptedTargetMax = aValue;
}
@end