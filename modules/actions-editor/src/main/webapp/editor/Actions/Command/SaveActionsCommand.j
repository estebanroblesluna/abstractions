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
@implementation SaveActionsCommand : Command
{
}

- (void) execute
{
	var json = { "positions" : [], "connections" : [] };
	var contextId = [_drawing contextId];

	var figures = [_drawing figures];
	for (var i = 0; i < [figures count]; i++) { 
		var figure = [figures objectAtIndex:i];
		if ([figure isKindOfClass: [ElementFigure class]]) {
			var frameOrigin = [figure frameOrigin];
			var id = [[figure model] elementId];
			var point = { "x" : frameOrigin.x, "y" : frameOrigin.y, "id" : id };
			json.positions.push(point);
		} else if ([figure isKindOfClass: [Connection class]]) {
			var id = [[figure model] elementId];
			var sourceId = [[[figure source] model] elementId];
			var connection = { "id" : id, "sourceId" : sourceId, points: []};
			var points = [figure points];					
			for (var j = 0; j < [points count]; j++) { 
				var cupPoint = [points objectAtIndex: j];
				var point = { "x" : cupPoint.x, "y" : cupPoint.y};
				connection.points.push(point);
			}
			json.connections.push(connection);
		}
	}
	
	var jsonAsString = JSON.stringify(json);
	var saveUrl = [Actions saveUrl];
	
	CPLog.debug("Saving context " + contextId);
	
	$.ajax({
		type: "POST",
		url: saveUrl,
		data: { "name": contextId, "json" : jsonAsString },
		cache: false,
        success: function(response) {
	        var newAlert = [[CPAlert alloc] init];
	        [newAlert setMessageText: @"Saved"];
	        [newAlert setAlertStyle: CPInformationalAlertStyle];
	        [newAlert setTitle: @"Message"];
	        [newAlert runModal];
	        var flowId = response.flowId;
	        [Actions adjustSaveUrl: flowId]
        },
        error: function() {
	        var newAlert = [CPAlert alertWithError: @"Error saving"];
	        [newAlert setTitle: @"Message"];
	        [newAlert runModal];
        }
	});
}
@end