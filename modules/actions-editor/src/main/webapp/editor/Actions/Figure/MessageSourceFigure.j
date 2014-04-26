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
 * status
 *  - running
 *  - stop
 *  
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation MessageSourceFigure : ElementFigure 
{ 
} 

- (void) beforeDeleteMenu: (CPMenu) contextMenu
{
	[[Actions mode]	createMessageSourceFigureMenu: self menu: contextMenu];
}

- (void) start: (id) sender 
{ 
    [[[self model] api]
    	perform: @"start" 
    	arguments: nil];
    	
    [[self model] messageSourceState: "START"];
}

- (void) stop: (id) sender 
{ 
    [[[self model] api]
    	perform: @"stop" 
    	arguments: nil];
    	
    [[self model] messageSourceState: "STOP"];
} 

- (void) openTestUrl: (id) sender 
{
	var w = window.open('', '_blank');
	
	var callback = (function(theWindow) {
		return function(result) {
			theWindow.location = result;
		};
	})(w);
	
	[[[self model] api]
		perform: @"getTestUrl"
		arguments: nil
		onResponse: callback]
}


- (id) acceptsNewEndingChain
{
	return false;
}
@end