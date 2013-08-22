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

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"ALL"];
[_modelMapping setObject: [AllModel class]  forKey: @"ALL"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"CHOICE"];
[_modelMapping setObject: [ChoiceModel class]  forKey: @"CHOICE"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"WIRE_TAP"];
[_modelMapping setObject: [WireTapModel class]  forKey: @"WIRE_TAP"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"GROOVY"];
[_modelMapping setObject: [GroovyModel class]  forKey: @"GROOVY"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"HTTP_FETCHER"];
[_modelMapping setObject: [HttpFetcherModel class]  forKey: @"HTTP_FETCHER"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"FILE_READER"];
[_modelMapping setObject: [FileReaderModel class]  forKey: @"FILE_READER"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"DUST_RENDERER"];
[_modelMapping setObject: [DustRendererModel class]  forKey: @"DUST_RENDERER"];

[_iconMapping setObject: @"Resources/groovy.gif" forKey: @"LOG"];
[_modelMapping setObject: [LogModel class]  forKey: @"LOG"];


@implementation CreateProcessorTool : AbstractCreateFigureTool
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

+ (id) isProcessor: anElementName
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
    var newFigure = [ProcessorFigure newAt: aPoint iconUrl: iconUrl];
    var newModel;
    
    var hasBreakpoint = [properties objectForKey: "_breakpoint"];
    
    
    if (elementId == nil) {
    	newModel = [modelClass contextId: contextId];
    } else {
    	newModel = [modelClass contextId: contextId elementId: elementId hasBreakpoint: hasBreakpoint];
    }
    
    if (properties != nil) {
    	CPLog.debug(@"Setting initial properties");
    	[newModel initializeWithProperties: properties];
    	[newModel changed];
    	CPLog.debug(@"Initial properties set");
    }
    
    var stateFigure = [ElementStateFigure newAt: aPoint elementModel: newModel];
    var magnet = [Magnet newWithSource: newFigure target: stateFigure selector: @selector(topLeft)];
    
    [newFigure model: newModel];
    [newFigure checkModelFeature: @"name"];
    
	[aDrawing addProcessor: newFigure];
	[aDrawing addFigure: stateFigure];
	
	[aTool activateSelectionTool];
	if (activateEdit && aTool != nil) {
		[newFigure switchToEditMode];
	}
}
@end