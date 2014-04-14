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
	id _dataFigure;
	id _loggerWindow;
} 

- (void) beforeDeleteMenu: (CPMenu) contextMenu
{
	[[Actions mode]	createProcessorFigureMenu: self menu: contextMenu];
}

- (void) setBreakpointFigure: (id) aBreakpointFigure 
{
	_breakpointFigure = aBreakpointFigure;
}

- (id) dataFigure
{
	return _dataFigure;
}

- (void) setDataFigure: (id) aLabel 
{
	_dataFigure = aLabel;
}

- (void) setBreakpoint:(id) sender 
{
	var hasBreakpoint = [[self model] hasBreakpoint];
    [[self model] setBreakpoint: !hasBreakpoint];
    
	[_breakpointFigure setHidden: hasBreakpoint];
}

- (void) sendMessage:(id) sender 
{ 
	[[Actions controller]
		initiateMessageSendFrom: self];
}

- (void) addProfiler: (id) sender
{
	[[[self model] api] addProfiler];
}

- (void) removeProfiler: (id) sender
{
	[[[self model] api] removeProfiler];
}

- (void) addLogger: (id) sender
{
	var myController = [[ElementAPIWindowController alloc] initWithWindowCibName: "AddLoggerWindow"];
	[myController showWindow: nil];
	[myController elementAPI: [[self model] api]];
}

- (void) removeLogger: (id) sender
{
	[[[self model] api] removeLogger];
}

- (void) viewLog: (id) sender
{
	[[[self model] api] getLogger];
}

- (void) loggerLines: (id) loggerInfo
{
	//if (_loggerWindow == nil) {
	//	_loggerWindow = [ViewLoggerWindow
	//					newAt: [self frameOrigin]];
	//}

	var elementId = [self elementId];
	var lines = loggerInfo.logger.lines;
	if (lines === undefined) {
		lines = "No log yet";
	}
						
	var myController = [[ViewLoggerWindowController alloc] initWithWindowCibName: "ViewLoggerWindow"];
	[myController lines: lines];
	[myController showWindow: nil];
						
	//TODO do something with all servers
	//[_loggerWindow orderFront: self];
	//[_loggerWindow loggerInfo: lines];
}

- (id) acceptsNewStartingChain
{
	 return ([_outConnections count] == 0) || (![self hasNextInChainConnections]);
}

- (void) highlight
{
}

- (void) unhighlight
{
}
@end