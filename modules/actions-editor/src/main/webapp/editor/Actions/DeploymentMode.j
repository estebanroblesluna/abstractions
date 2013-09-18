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
@implementation DeploymentMode : CPObject
{
	id _timer;
	id _drawing;
}

- (id) init
{
	_timer = [CPTimer 
				scheduledTimerWithTimeInterval: 5 
				target:self 
				selector: @selector(updateProfilingInfo:) 
				userInfo: nil 
				repeats: YES];
				
	return self;
}

- (void) updateProfilingInfo: (id) sender
{
	if (_drawing != nil) {
		[_drawing updateProfilingInfo];
	}
}

- (void) postAddProcessor: (id) aProcessorFigure
{
	var bottomRight = [aProcessorFigure bottomRight];
	var performanceLabel = [LabelFigure initializeWithText: @"" at: bottomRight];
    var magnet = [Magnet newWithSource: aProcessorFigure target: performanceLabel selector: @selector(bottomRight)];

	var drawing = [aProcessorFigure drawing];
	
	[aProcessorFigure setDataFigure: performanceLabel];
	
	[drawing addFigure:	performanceLabel];
}

- (void) createMessageSourceStateFigure: (id) aMessageSourceFigure drawing: (id) aDrawing point: (id) aPoint
{
}

- (void) createMessageSourceFigureMenu: (id) aMessageSourceFigure menu: (CPMenu) contextMenu
{
}

- (void) createProcessorFigureMenu: (id) aProcessorFigure menu: (CPMenu) contextMenu
{
    var addProfilerMenu = [[CPMenuItem alloc] initWithTitle:@"Add profiler" action: @selector(addProfiler:) keyEquivalent:@""]; 
    [addProfilerMenu setTarget: aProcessorFigure]; 
    [addProfilerMenu setEnabled: YES]; 
    [contextMenu addItem: addProfilerMenu]; 

    var removeProfilerMenu = [[CPMenuItem alloc] initWithTitle:@"Remove profiler" action: @selector(removeProfiler:) keyEquivalent:@""]; 
    [removeProfilerMenu setTarget: aProcessorFigure]; 
    [removeProfilerMenu setEnabled: YES]; 
    [contextMenu addItem: removeProfilerMenu]; 
}

- (void) createElementFigureMenu: (id) anElementFigure
{
    var contextMenu = [[CPMenu alloc] init]; 
    [contextMenu setDelegate: anElementFigure]; 

	[anElementFigure beforeDeleteMenu: contextMenu];
	[anElementFigure setMenu: contextMenu];
}

- (void) createElementConnectionMenu: (id) anElementConnection
{
}

- (void) load: (id) aDrawing
{
	_drawing = aDrawing;
	[self loadGenerator: aDrawing];
	[self loadDiagramElements: aDrawing]
}

- (void) loadDiagramElements: (id) drawing
{
	var contextId = [Actions contextId];
    if (contextId != nil) {
    	CPLog.debug("[AppController] Context id found!");
    	CPLog.debug("[AppController] " + contextId);
        var command = [LoadActionsCommand drawing: drawing];
        [command execute];
	} else {
	    CPLog.debug("[AppController] Context id NOT found!");
	}
}

- (void) loadGenerator: (id) aDrawing
{
	var libraries = [DataUtil var: @"libraries"].libraries;
	var generator = [DynamicModelGenerator new];
	[aDrawing generator: generator];
	
	//INITIALIZE MODEL GENERATOR
	for (var i = 0; i < libraries.length; i++) {
		var library = libraries[i];
		var elements = library.elements;

		CPLog.debug("Processing library " + library.name);

		[generator addDefinitions: elements];
	}
}
@end