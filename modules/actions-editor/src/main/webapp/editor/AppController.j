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

@import <Foundation/CPObject.j>
@import <CupDraw/CupDraw.j>

@import "Actions/Actions.j"

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation AppController : CPObject
{
	Drawing drawing;
}

- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
    CPLogRegister(CPLogPopup);
	var theWindow = [[CPWindow alloc] initWithContentRect:CGRectMakeZero() styleMask:CPBorderlessBridgeWindowMask],
        contentView = [theWindow contentView];

	[self initializeDrawing: contentView window: theWindow];
    [theWindow orderFront: self];
	[theWindow makeFirstResponder: drawing];

}

- (void) initializeDrawing:(id) contentView window: (id) theWindow
{
	var contextId = [Actions contextId];
	drawing = [ActionsDrawing frame: [contentView bounds] contextId: contextId];
	[drawing setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];
	[contentView addSubview: drawing];
	
	var mode = [Actions modeParam];
	if (mode != nil && [mode isEqual: @"deployment"]) {
		[Actions mode: [DeploymentMode new]];
	}
	
	[Actions load: drawing];
}

@end
