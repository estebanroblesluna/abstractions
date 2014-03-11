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
@implementation AbstractCommand : Command
{
}

- (void) execute
{
	var contextId = [_drawing contextId]
	var selectedFigures = [[_drawing tool] selectedFigures];
	var selectedUrns = new Array();

	var j = 0;
	for (var i = 0; i < [selectedFigures count]; i++) {
		var figure = [selectedFigures objectAtIndex: i];
		if ([figure respondsToSelector: @selector(elementId)]) {
			selectedUrns[j] = [figure elementId];
			j++;
		}
	}

	var name = window.prompt('Abstraction name');
	var urns = selectedUrns.join();

	CPLog.debug("Abstracting elements");

	$.ajax({
		type: "POST",
		url: "../service/context/" + contextId + "/abstract",
		data: {"name" : name, "displayName" : name, "elementUrns": urns}
	}).done(function( result ) {
		[self processResult: result.definition selectedUrns: selectedUrns];
		CPLog.debug("DONE Abstracting elements");
	});
}

- (void) processResult: (id) aNewDefinition selectedUrns: (id) selectedUrns
{
	//TODO change this number to the internal user toolbox
	var toolbox = [_drawing toolboxWithId: "user-defined"];

	[toolbox setHidden: false];
	[toolbox setNeedsDisplay: true];


	//add the definition to the generator
	var generator = [_drawing generator];
	[generator addDefinition: aNewDefinition];


	//add the element into the toolbar
	var tool = [CreateProcessorTool
					drawing: _drawing
					elementName: aNewDefinition.name
					generator: generator];
	[toolbox
		addTool: tool
		withTitle: aNewDefinition.displayName
		image: aNewDefinition.icon];


	//get the (x,y) of the starting id
	var startingId = aNewDefinition.startingDefinitionId;
	var startingFigure = [_drawing processorFor: startingId];
	var topLeft = [startingFigure topLeft];


	//remove all the figures
	[_drawing removeFigures: selectedUrns];


	//add the new element using the (x,y)
	var newFigure = [CreateProcessorTool
						createFigureAt: topLeft
						on: _drawing
						elementName: aNewDefinition.name
						edit: NO
						elementId: nil
						initialProperties: nil
						tool: tool];
}
@end
