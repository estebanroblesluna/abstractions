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
@implementation EditionMode : CPObject
{
}

- (void) postAddProcessor: (id) aProcessorFigure
{
	var topRight = [aProcessorFigure topRight];
	var _breakpointFigure = [ImageFigure initializeWithImage: @"Resources/stop.gif" x: topRight.x y: topRight.y];
	[aProcessorFigure setBreakpointFigure: _breakpointFigure];
	var hasBreakpoint = [[aProcessorFigure model] hasBreakpoint];
	[_breakpointFigure setHidden: !hasBreakpoint];
    var magnet = [Magnet newWithSource: aProcessorFigure target: _breakpointFigure selector: @selector(topRight)];

	var drawing = [aProcessorFigure drawing];

	[drawing addFigure:	_breakpointFigure];
}

- (void) createMessageSourceStateFigure: (id) aMessageSourceFigure drawing: (id) aDrawing point: (id) aPoint
{
	var messageSourceStateFigure = [MessageSourceStateFigure newAt: aPoint model: [aMessageSourceFigure model]];
    var messageSourceMagnet = [Magnet newWithSource: aMessageSourceFigure target: messageSourceStateFigure selector: @selector(topRight)];
	[aDrawing addFigure: messageSourceStateFigure];
	[messageSourceStateFigure updateStateFigure];
	[messageSourceStateFigure setFrameSize: CGSizeMake(16, 16)];
	[messageSourceMagnet updateLocation: nil];
}

- (void) createMessageSourceFigureMenu: (id) aMessageSourceFigure menu: (CPMenu) contextMenu
{
    var startMenu = [[CPMenuItem alloc] initWithTitle:@"Start" action: @selector(start:) keyEquivalent:@""];
    [startMenu setTarget: aMessageSourceFigure];
    [startMenu setEnabled: YES];
    [contextMenu addItem: startMenu];

    var stopMenu = [[CPMenuItem alloc] initWithTitle:@"Stop" action: @selector(stop:) keyEquivalent:@""];
    [stopMenu setTarget: aMessageSourceFigure];
    [stopMenu setEnabled: YES];
    [contextMenu addItem: stopMenu];
}

- (void) createProcessorFigureMenu: (id) aProcessorFigure menu: (CPMenu) contextMenu
{
    var sendMessageMenu = [[CPMenuItem alloc] initWithTitle:@"Send message" action: @selector(sendMessage:) keyEquivalent:@""];
    [sendMessageMenu setTarget: aProcessorFigure];
    [sendMessageMenu setEnabled: YES];
    [contextMenu addItem: sendMessageMenu];

    var setBreakpointMenu = [[CPMenuItem alloc] initWithTitle:@"Set breakpoint" action: @selector(setBreakpoint:) keyEquivalent:@""];
    [setBreakpointMenu setTarget: aProcessorFigure];
    [setBreakpointMenu setEnabled: YES];
    [contextMenu addItem: setBreakpointMenu];
}

- (void) createElementFigureMenu: (id) anElementFigure
{
    var contextMenu = [[CPMenu alloc] init];
    [contextMenu setDelegate: anElementFigure];

	[anElementFigure beforeDeleteMenu: contextMenu];

    var deleteMenu = [[CPMenuItem alloc] initWithTitle:@"Delete" action: @selector(deleteFromServer) keyEquivalent:@""];
    [deleteMenu setTarget: anElementFigure];
    [deleteMenu setEnabled: YES];
    [contextMenu addItem: deleteMenu];

	[anElementFigure setMenu: contextMenu];
}

- (void) createElementConnectionMenu: (id) anElementConnection
{
	var contextMenu = [[CPMenu alloc] init];
    [contextMenu setDelegate: anElementConnection];

    var deleteMenu = [[CPMenuItem alloc] initWithTitle: @"Delete" action: @selector(deleteFromServer) keyEquivalent:@""];
    [deleteMenu setTarget: anElementConnection];
    [deleteMenu setEnabled: YES];
    [contextMenu addItem: deleteMenu];

	[anElementConnection setMenu: contextMenu];
}

- (void) load: (id) aDrawing
{
	[self loadCommonToolbars: aDrawing];
	[self loadLibraries: aDrawing];
	[self loadDiagramElements: aDrawing]

	var timer = [CPTimer
				scheduledTimerWithTimeInterval: 5
				target: aDrawing
				selector: @selector(update:)
				userInfo: nil
				repeats: YES];

	[aDrawing timer: timer];
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

- (void) loadCommonToolbars: (id) drawing
{
	var commonToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(800, 25)];
	[commonToolbox columns: 2];

	[commonToolbox addTool: [SelectionTool drawing: drawing] withTitle: @"Selection" image: @"Resources/Selection.png"];
	[commonToolbox addCommand: [SaveActionsCommand class] withTitle: @"Save" image: @"Resources/Save.gif"];
	[commonToolbox addCommand: [AbstractCommand class] withTitle: @"Abstract" image: @"Resources/abstract.png"];

    [commonToolbox addCommand: [GroupCommand class] withTitle: @"Group" image: @"Resources/Group.gif"];
    [commonToolbox addCommand: [UngroupCommand class] withTitle: @"Ungroup" image: @"Resources/Ungroup.gif"];

    [commonToolbox addCommand: [LockCommand class] withTitle: @"Lock" image: @"Resources/Lock.gif"];
    [commonToolbox addCommand: [UnlockCommand class] withTitle: @"Unlock" image: @"Resources/Unlock.gif"];

    [commonToolbox addCommand: [BringToFrontCommand class] withTitle: @"Bring to front" image: @"Resources/BringToFront.gif"];
    [commonToolbox addCommand: [SendToBackCommand class] withTitle: @"Send to back" image: @"Resources/SendToBack.gif"];
    [commonToolbox addCommand: [BringForwardCommand class] withTitle: @"Bring forward" image: @"Resources/BringForward.gif"];
    [commonToolbox addCommand: [SendBackwardCommand class] withTitle: @"Send backward" image: @"Resources/SendBackward.gif"];

	[commonToolbox addSeparator];

	var alignToolbox = [ToolboxFigure initializeWith: drawing at: CGPointMake(880, 25)];
	[alignToolbox columns: 3];

	[alignToolbox addCommand: [AlignLeftCommand class] withTitle: @"Align left" image: @"Resources/AlignLeft.gif"];
	[alignToolbox addCommand: [AlignCenterCommand class] withTitle: @"Align center" image: @"Resources/AlignCenter.gif"];
	[alignToolbox addCommand: [AlignRightCommand class] withTitle: @"Align right" image: @"Resources/AlignRight.gif"];
	[alignToolbox addCommand: [AlignTopCommand class] withTitle: @"Align top" image: @"Resources/AlignTop.gif"];
	[alignToolbox addCommand: [AlignMiddleCommand class] withTitle: @"Align middle" image: @"Resources/AlignMiddle.gif"];
	[alignToolbox addCommand: [AlignBottomCommand class] withTitle: @"Align bottom" image: @"Resources/AlignBottom.gif"];

	var properties = [PropertiesFigure newAt: CGPointMake(20, 420) drawing: drawing];

	[drawing toolbox: commonToolbox];
	[drawing addFigure: alignToolbox];
	[drawing properties: properties];
}

- (void) loadLibraries: (id) aDrawing
{
	var libraries = [DataUtil var: @"libraries"];
	[self libraries: libraries drawing: aDrawing];
}

- (void) libraries: (id) aJSON drawing: (id) aDrawing
{
	CPLog.debug("Libraries received");
	CPLog.debug(aJSON);

	//var libraries = aJSON.libraries.libraries;
	var libraries = aJSON.libraries;
	var generator = [DynamicModelGenerator new];
	[aDrawing generator: generator];

	//INITIALIZE MODEL GENERATOR
	for (var i = 0; i < libraries.length; i++) {
		var library = libraries[i];
		var elements = library.elements;

		CPLog.debug("Processing library " + library.name);

		[generator addDefinitions: elements];
	}

	//CREATE THE TOOLBOXES
	var initialX = 20;
	for (var i = 0; i < libraries.length; i++) {
		var library = libraries[i];
		var elements = library.elements;
		var toolbox = [ToolboxFigure initializeWith: aDrawing at: CGPointMake(initialX, 25)];


		if (elements.length == 0)
			[toolbox setHidden: true];

		for (var j = 0; j < elements.length; j++) {
			var element = elements[j];
			if (element.type == "PROCESSOR" || element.type == "ROUTER" || element.type == "ABSTRACTION") {
				[toolbox
					addTool: [CreateProcessorTool drawing: aDrawing elementName: element.name generator: generator]
					withTitle: element.displayName
					image: element.icon];
			} else if (element.type == "MESSAGE_SOURCE") {
				[toolbox
					addTool: [CreateMessageSourceTool drawing: aDrawing elementName: element.name generator: generator]
					withTitle: element.displayName
					image: element.icon];
			} else if (element.type == "CONNECTION") {

				var acceptedSourceTypes = nil;
				var acceptedSourceMax = element.acceptedSourceMax;
				var acceptedTargetTypes = nil;
				var acceptedTargetMax = element.acceptedSourceMax;

				if (element.acceptedSourceTypes != "*") {
					var types = element.acceptedSourceTypes.split(',');
					acceptedSourceTypes = [CPSet setWithArray: types];
				}
				if (element.acceptedTargetTypes != "*") {
					var types = element.acceptedTargetTypes.split(',');
					acceptedTargetTypes = [CPSet setWithArray: types];
				}

				[toolbox
					addTool: [CreateElementConnectionTool
						drawing: aDrawing
						acceptedSourceTypes: acceptedSourceTypes
						acceptedSourceMax: acceptedSourceMax
						acceptedTargetTypes: acceptedTargetTypes
						acceptedTargetMax: acceptedTargetMax
						elementName: element.name]
					withTitle: element.displayName
					image: element.icon];
			}
		}


		[aDrawing addToolbox: toolbox withId: library.name];
		initialX = initialX + 80;

	}
}

@end
