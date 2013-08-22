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

- (void)initializeDrawing:(id) contentView window: (id) theWindow
{
	var sharedApplication = [CPApplication sharedApplication];
    var namedArguments = [sharedApplication namedArguments];
	var contextId = [namedArguments objectForKey: "contextId"];

	drawing = [ActionsDrawing frame: [contentView bounds] contextId: contextId];

	var basicToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(20,70)];
	[basicToolbox columns: 2];

	[basicToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"GROOVY"] 
		withTitle: @"Groovy" 
		image: @"Resources/groovy.gif"];

	[basicToolbox 
		addTool: [CreateNextInChainTool drawing: drawing] 
		withTitle: @"Next in chain connection" 
		image: @"Resources/nextInChainConnection.png"];

	[basicToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"CHOICE"] 
		withTitle: @"Choice" 
		image: @"Resources/groovy.gif"];

	[basicToolbox 
		addTool: [CreateChoiceConnectionTool drawing: drawing] 
		withTitle: @"Choice connection" 
		image: @"Resources/choiceConnection.png"];

	[basicToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"ALL"] 
		withTitle: @"All" 
		image: @"Resources/groovy.gif"];

	[basicToolbox 
		addTool: [CreateAllConnectionTool drawing: drawing] 
		withTitle: @"All connection" 
		image: @"Resources/allConnection.png"];

	[basicToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"WIRE_TAP"] 
		withTitle: @"Wire tap" 
		image: @"Resources/groovy.gif"];

	[basicToolbox 
		addTool: [CreateWireTapConnectionTool drawing: drawing] 
		withTitle: @"Wire tap connection" 
		image: @"Resources/allConnection.png"];

	var modulesToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(20,400)];
	[modulesToolbox columns: 2];

	[modulesToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"HTTP_FETCHER"] 
		withTitle: @"Http fetcher" 
		image: @"Resources/groovy.gif"];

	[modulesToolbox 
		addTool: [CreateMessageSourceTool drawing: drawing elementName: @"HTTP_MESSAGE_SOURCE"] 
		withTitle: @"Http message source" 
		image: @"Resources/groovy.gif"];

	[modulesToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"FILE_READER"] 
		withTitle: @"File reader" 
		image: @"Resources/groovy.gif"];

	[modulesToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"DUST_RENDERER"] 
		withTitle: @"Dust renderer" 
		image: @"Resources/groovy.gif"];

	[modulesToolbox 
		addTool: [CreateProcessorTool drawing: drawing elementName: @"LOG"] 
		withTitle: @"Log" 
		image: @"Resources/groovy.gif"];


	var commonToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(800,70)];
	[commonToolbox columns: 2];

	[commonToolbox addTool: [SelectionTool drawing: drawing] withTitle: @"Selection" image: @"Resources/Selection.png"];
	[commonToolbox addCommand: [SaveActionsCommand class] withTitle: @"Save" image: @"Resources/Save.gif"];

    [commonToolbox addCommand: [GroupCommand class] withTitle: @"Group" image: @"Resources/Group.gif"];
    [commonToolbox addCommand: [UngroupCommand class] withTitle: @"Ungroup" image: @"Resources/Ungroup.gif"];

    [commonToolbox addCommand: [LockCommand class] withTitle: @"Lock" image: @"Resources/Lock.gif"];
    [commonToolbox addCommand: [UnlockCommand class] withTitle: @"Unlock" image: @"Resources/Unlock.gif"];

    [commonToolbox addCommand: [BringToFrontCommand class] withTitle: @"Bring to front" image: @"Resources/BringToFront.gif"];
    [commonToolbox addCommand: [SendToBackCommand class] withTitle: @"Send to back" image: @"Resources/SendToBack.gif"];
    [commonToolbox addCommand: [BringForwardCommand class] withTitle: @"Bring forward" image: @"Resources/BringForward.gif"];
    [commonToolbox addCommand: [SendBackwardCommand class] withTitle: @"Send backward" image: @"Resources/SendBackward.gif"];

	[commonToolbox addSeparator];

	var alignToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(800,310)];
	[alignToolbox columns: 3];

	[alignToolbox addCommand: [AlignLeftCommand class] withTitle: @"Align left" image: @"Resources/AlignLeft.gif"];
	[alignToolbox addCommand: [AlignCenterCommand class] withTitle: @"Align center" image: @"Resources/AlignCenter.gif"];
	[alignToolbox addCommand: [AlignRightCommand class] withTitle: @"Align right" image: @"Resources/AlignRight.gif"];
	[alignToolbox addCommand: [AlignTopCommand class] withTitle: @"Align top" image: @"Resources/AlignTop.gif"];
	[alignToolbox addCommand: [AlignMiddleCommand class] withTitle: @"Align middle" image: @"Resources/AlignMiddle.gif"];
	[alignToolbox addCommand: [AlignBottomCommand class] withTitle: @"Align bottom" image: @"Resources/AlignBottom.gif"];
	
	
	var properties = [PropertiesFigure newAt: CGPointMake(20,520) drawing: drawing];
	
	[drawing setAutoresizingMask: CPViewWidthSizable | CPViewHeightSizable];

	[contentView addSubview: drawing];
	
	[drawing toolbox: basicToolbox];
	[drawing addFigure: commonToolbox];
	[drawing addFigure: alignToolbox];
	[drawing addFigure: modulesToolbox];
	[drawing properties: properties];
	

    if (contextId != nil) {
        var command = [LoadActionsCommand drawing: drawing];
        [command execute];
	}
}

@end
