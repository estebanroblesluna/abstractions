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
    var startMenu = [[CPMenuItem alloc] initWithTitle:@"Start" action: @selector(start:) keyEquivalent:@""]; 
    [startMenu setTarget: self]; 
    [startMenu setEnabled: YES]; 
    [contextMenu addItem: startMenu]; 

    var stopMenu = [[CPMenuItem alloc] initWithTitle:@"Stop" action: @selector(stop:) keyEquivalent:@""]; 
    [stopMenu setTarget: self]; 
    [stopMenu setEnabled: YES]; 
    [contextMenu addItem: stopMenu]; 
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

- (id) acceptsNewEndingChain
{
	return false;
}
@end