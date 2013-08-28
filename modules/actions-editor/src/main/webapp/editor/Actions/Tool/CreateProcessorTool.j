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
 
@implementation CreateProcessorTool : AbstractCreateFigureTool
{
	id _elementName;
	id _generator;
}

+ (id) drawing: (Drawing) aDrawing elementName: (id) anElementName generator: (id) aGenerator
{
	var tool = [super drawing: aDrawing];
	[tool elementName: anElementName];
	[tool generator: aGenerator];
	return tool;
}

- (void) createFigureAt: (id) aPoint on: (id) aDrawing
{
	[[self class] 
		createFigureAt: aPoint 
		on: aDrawing 
		elementName: _elementName 
		edit: YES 
		elementId: nil 
		initialProperties: nil 
		tool: self];
}

+ (void) createFigureAt: (id) aPoint on: (id) aDrawing elementName: (id) elementName edit: (id) activateEdit elementId: (id) elementId initialProperties: (id) properties tool: (id) aTool
{
	var generator = [aTool generator];
	var iconUrl = [generator icon: elementName];
	
	var contextId = [aDrawing contextId];
    var newFigure = [ProcessorFigure newAt: aPoint iconUrl: iconUrl];
    var hasBreakpoint = [properties objectForKey: "_breakpoint"];
    
	var newModel = [generator 
		modelFor: elementName 
		elementId: elementId 
		contextId: contextId 
		initialProperties: properties
		hasBreakpoint: hasBreakpoint];
	
    var stateFigure = [ElementStateFigure newAt: aPoint elementModel: newModel];
    var magnet = [Magnet newWithSource: newFigure target: stateFigure selector: @selector(topLeft)];
    
    [newFigure model: newModel];
    [newFigure checkModelFeature: @"name"];
    [newModel changed];
	[aDrawing addProcessor: newFigure];
	[aDrawing addFigure: stateFigure];
	
	[aTool activateSelectionTool];
	if (activateEdit && aTool != nil) {
		[newFigure switchToEditMode];
	}
	
	return newFigure;
}

- (id) generator
{
	return _generator;
}

- (void) generator: (id) aGenerator
{
	_generator = aGenerator;
}

- (void) elementName: (id) anElementName
{
	_elementName = anElementName;
}
@end