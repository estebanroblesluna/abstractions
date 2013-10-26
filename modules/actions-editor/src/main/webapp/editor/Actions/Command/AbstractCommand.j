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
	
	for (var i = 0; i < [selectedFigures count]; i++) { 
		var figure = [figures objectAtIndex: i];
		selectedUrns[i] = [figure elementId];
	}
	
	var name = window.prompt('Abstraction name');
	var urns = selectedUrns.join();
	
	CPLog.debug("Abstracting elements");
	
	$.ajax({
		type: "POST",
		url: "../service/context/" + contextId + "/abstract",
		data: {"name" : name, "elementUrns": urns}
	}).done(function( result ) {
		CPLog.debug("DONE Abstracting elements");
	});
}
@end