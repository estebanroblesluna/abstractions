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
@implementation ProcessorFigure : ElementFigure 
{ 
	id _breakpointFigure;
} 

- (void) beforeDeleteMenu: (CPMenu) contextMenu
{
    var sendMessageMenu = [[CPMenuItem alloc] initWithTitle:@"Send message" action: @selector(sendMessage:) keyEquivalent:@""]; 
    [sendMessageMenu setTarget: self]; 
    [sendMessageMenu setEnabled: YES]; 
    [contextMenu addItem: sendMessageMenu]; 

    var setBreakpointMenu = [[CPMenuItem alloc] initWithTitle:@"Set breakpoint" action: @selector(setBreakpoint:) keyEquivalent:@""]; 
    [setBreakpointMenu setTarget: self]; 
    [setBreakpointMenu setEnabled: YES]; 
    [contextMenu addItem: setBreakpointMenu]; 
}

- (void) initializeBreakpoint
{
	var topRight = [self topRight];
	_breakpointFigure = [ImageFigure initializeWithImage: @"Resources/stop.gif" x: topRight.x y: topRight.y];
	var hasBreakpoint = [[self model] hasBreakpoint];
	[_breakpointFigure setHidden: !hasBreakpoint];
    var magnet = [Magnet newWithSource: self target: _breakpointFigure selector: @selector(topRight)];

	var drawing = [self drawing];
	
	[drawing addFigure:	_breakpointFigure];
}

- (void) setBreakpoint:(id) sender 
{
	var hasBreakpoint = [[self model] hasBreakpoint];
    [[self model] setBreakpoint: !hasBreakpoint];
    
	[_breakpointFigure setHidden: hasBreakpoint];
}

- (void) sendMessage:(id) sender 
{ 
    [[self drawing] initiateMessageSendFrom: self];
} 

- (id) acceptsNewStartingChain
{
	 return ([_outConnections count] == 0) || (![self hasNextInChainConnections]);
}
@end