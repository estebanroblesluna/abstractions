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
 
var _iconMapping = [CPDictionary dictionary];
var _modelMapping = [CPDictionary dictionary];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"HTTP_MESSAGE_SOURCE"];
[_modelMapping setObject: [HttpMessageSourceModel class]  forKey: @"HTTP_MESSAGE_SOURCE"];

@implementation CreateMessageSourceTool : AbstractCreateFigureTool
{
	id _elementName;
	id _iconUrl;
}

+ (id) drawing: (Drawing) aDrawing elementName: (id) anElementName
{
	var tool = [super drawing: aDrawing];
	[tool elementName: anElementName];
	return tool;
}

+ (id) isMessageSource: anElementName
{
	return [_iconMapping containsKey: anElementName];
}

- (void) elementName: (id) anElementName
{
	_elementName = anElementName;
}

- (void) createFigureAt: (id) aPoint on: (id) aDrawing
{
	[[self class] createFigureAt: aPoint on: aDrawing elementName: _elementName edit: YES elementId: nil initialProperties: nil tool: self];
}

+ (void) createFigureAt: (id) aPoint on: (id) aDrawing elementName: (id) elementName edit: (id) activateEdit elementId: (id) elementId initialProperties: (id) properties tool: (id) aTool
{
	var iconUrl = [_iconMapping objectForKey: elementName];
	var modelClass = [_modelMapping objectForKey: elementName];

	var contextId = [aDrawing contextId];
    var newFigure = [MessageSourceFigure newAt: aPoint iconUrl: iconUrl];
    var newModel;
    
    if (elementId == nil) {
    	newModel = [modelClass contextId: contextId];
    } else {
    	newModel = [modelClass contextId: contextId elementId: elementId hasBreakpoint: false];
    }
    
    if (properties != nil) {
    	CPLog.debug(@"Setting initial properties");
    	[newModel initializeWithProperties: properties];
    	CPLog.debug(@"Initial properties set");
    }
    
	var messageSourceStateFigure = [MessageSourceStateFigure newAt: aPoint model: newModel];
    var messageSourceMagnet = [Magnet newWithSource: newFigure target: messageSourceStateFigure selector: @selector(topRight)];

    [newFigure model: newModel];
    [newFigure checkModelFeature: @"Name"];
    
	[aDrawing addMessageSource: newFigure];
	[aDrawing addFigure: messageSourceStateFigure];
	
	[aTool activateSelectionTool];
	if (activateEdit && aTool != nil) {
		[newFigure switchToEditMode];
	}
	
	[messageSourceStateFigure updateStateFigure];
	[messageSourceStateFigure setFrameSize: CGSizeMake(16, 16)];
}
@end