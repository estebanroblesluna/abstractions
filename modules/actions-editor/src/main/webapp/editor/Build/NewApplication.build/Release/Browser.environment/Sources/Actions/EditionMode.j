@STATIC;1.0;t;12398;{var the_class = objj_allocateClassPair(CPObject, "EditionMode"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("postAddProcessor:"), function $EditionMode__postAddProcessor_(self, _cmd, aProcessorFigure)
{
    var topRight = objj_msgSend(aProcessorFigure, "topRight");
    var _breakpointFigure = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/stop.gif", topRight.x, topRight.y);
    objj_msgSend(aProcessorFigure, "setBreakpointFigure:", _breakpointFigure);
    var hasBreakpoint = objj_msgSend(objj_msgSend(aProcessorFigure, "model"), "hasBreakpoint");
    objj_msgSend(_breakpointFigure, "setHidden:", !hasBreakpoint);
    var magnet = objj_msgSend(Magnet, "newWithSource:target:selector:", aProcessorFigure, _breakpointFigure, sel_getUid("topRight"));
    var drawing = objj_msgSend(aProcessorFigure, "drawing");
    objj_msgSend(drawing, "addFigure:", _breakpointFigure);
}
,["void","id"]), new objj_method(sel_getUid("createMessageSourceStateFigure:drawing:point:"), function $EditionMode__createMessageSourceStateFigure_drawing_point_(self, _cmd, aMessageSourceFigure, aDrawing, aPoint)
{
    var messageSourceStateFigure = objj_msgSend(MessageSourceStateFigure, "newAt:model:", aPoint, objj_msgSend(aMessageSourceFigure, "model"));
    var messageSourceMagnet = objj_msgSend(Magnet, "newWithSource:target:selector:", aMessageSourceFigure, messageSourceStateFigure, sel_getUid("topRight"));
    objj_msgSend(aDrawing, "addFigure:", messageSourceStateFigure);
    objj_msgSend(messageSourceStateFigure, "updateStateFigure");
    objj_msgSend(messageSourceStateFigure, "setFrameSize:", CGSizeMake(16, 16));
    objj_msgSend(messageSourceMagnet, "updateLocation:", nil);
}
,["void","id","id","id"]), new objj_method(sel_getUid("createMessageSourceFigureMenu:menu:"), function $EditionMode__createMessageSourceFigureMenu_menu_(self, _cmd, aMessageSourceFigure, contextMenu)
{
    var startMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Start", sel_getUid("start:"), "");
    objj_msgSend(startMenu, "setTarget:", aMessageSourceFigure);
    objj_msgSend(startMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", startMenu);
    var stopMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Stop", sel_getUid("stop:"), "");
    objj_msgSend(stopMenu, "setTarget:", aMessageSourceFigure);
    objj_msgSend(stopMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", stopMenu);
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createProcessorFigureMenu:menu:"), function $EditionMode__createProcessorFigureMenu_menu_(self, _cmd, aProcessorFigure, contextMenu)
{
    var sendMessageMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Send message", sel_getUid("sendMessage:"), "");
    objj_msgSend(sendMessageMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(sendMessageMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", sendMessageMenu);
    var setBreakpointMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Set breakpoint", sel_getUid("setBreakpoint:"), "");
    objj_msgSend(setBreakpointMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(setBreakpointMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", setBreakpointMenu);
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createElementFigureMenu:"), function $EditionMode__createElementFigureMenu_(self, _cmd, anElementFigure)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementFigure);
    objj_msgSend(anElementFigure, "beforeDeleteMenu:", contextMenu);
    var deleteMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Delete", sel_getUid("deleteFromServer"), "");
    objj_msgSend(deleteMenu, "setTarget:", anElementFigure);
    objj_msgSend(deleteMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", deleteMenu);
    objj_msgSend(anElementFigure, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("createElementConnectionMenu:"), function $EditionMode__createElementConnectionMenu_(self, _cmd, anElementConnection)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementConnection);
    var deleteMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Delete", sel_getUid("deleteFromServer"), "");
    objj_msgSend(deleteMenu, "setTarget:", anElementConnection);
    objj_msgSend(deleteMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", deleteMenu);
    objj_msgSend(anElementConnection, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("load:"), function $EditionMode__load_(self, _cmd, aDrawing)
{
    objj_msgSend(self, "loadCommonToolbars:", aDrawing);
    objj_msgSend(self, "loadLibraries:", aDrawing);
    objj_msgSend(self, "loadDiagramElements:", aDrawing);
    var timer = objj_msgSend(CPTimer, "scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:", 5, aDrawing, sel_getUid("update:"), nil, YES);
    objj_msgSend(aDrawing, "timer:", timer);
}
,["void","id"]), new objj_method(sel_getUid("loadDiagramElements:"), function $EditionMode__loadDiagramElements_(self, _cmd, drawing)
{
    var contextId = objj_msgSend(Actions, "contextId");
    if (contextId != nil)
    {
        CPLog.debug("[AppController] Context id found!");
        CPLog.debug("[AppController] " + contextId);
        var command = objj_msgSend(LoadActionsCommand, "drawing:", drawing);
        objj_msgSend(command, "execute");
    }
    else
    {
        CPLog.debug("[AppController] Context id NOT found!");
    }
}
,["void","id"]), new objj_method(sel_getUid("loadCommonToolbars:"), function $EditionMode__loadCommonToolbars_(self, _cmd, drawing)
{
    var commonToolbox = objj_msgSend(ToolboxFigure, "initializeWith:at:", drawing, CGPointMake(800, 25));
    objj_msgSend(commonToolbox, "columns:", 2);
    objj_msgSend(commonToolbox, "addTool:withTitle:image:", objj_msgSend(SelectionTool, "drawing:", drawing), "Selection", "Resources/Selection.png");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(SaveActionsCommand, "class"), "Save", "Resources/Save.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(AbstractCommand, "class"), "Abstract", "Resources/Save.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(GroupCommand, "class"), "Group", "Resources/Group.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(UngroupCommand, "class"), "Ungroup", "Resources/Ungroup.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(LockCommand, "class"), "Lock", "Resources/Lock.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(UnlockCommand, "class"), "Unlock", "Resources/Unlock.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(BringToFrontCommand, "class"), "Bring to front", "Resources/BringToFront.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(SendToBackCommand, "class"), "Send to back", "Resources/SendToBack.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(BringForwardCommand, "class"), "Bring forward", "Resources/BringForward.gif");
    objj_msgSend(commonToolbox, "addCommand:withTitle:image:", objj_msgSend(SendBackwardCommand, "class"), "Send backward", "Resources/SendBackward.gif");
    objj_msgSend(commonToolbox, "addSeparator");
    var alignToolbox = objj_msgSend(ToolboxFigure, "initializeWith:at:", drawing, CGPointMake(880, 25));
    objj_msgSend(alignToolbox, "columns:", 3);
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignLeftCommand, "class"), "Align left", "Resources/AlignLeft.gif");
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignCenterCommand, "class"), "Align center", "Resources/AlignCenter.gif");
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignRightCommand, "class"), "Align right", "Resources/AlignRight.gif");
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignTopCommand, "class"), "Align top", "Resources/AlignTop.gif");
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignMiddleCommand, "class"), "Align middle", "Resources/AlignMiddle.gif");
    objj_msgSend(alignToolbox, "addCommand:withTitle:image:", objj_msgSend(AlignBottomCommand, "class"), "Align bottom", "Resources/AlignBottom.gif");
    var properties = objj_msgSend(PropertiesFigure, "newAt:drawing:", CGPointMake(20, 420), drawing);
    objj_msgSend(drawing, "toolbox:", commonToolbox);
    objj_msgSend(drawing, "addFigure:", alignToolbox);
    objj_msgSend(drawing, "properties:", properties);
}
,["void","id"]), new objj_method(sel_getUid("loadLibraries:"), function $EditionMode__loadLibraries_(self, _cmd, aDrawing)
{
    var libraries = objj_msgSend(DataUtil, "var:", "libraries");
    objj_msgSend(self, "libraries:drawing:", libraries, aDrawing);
}
,["void","id"]), new objj_method(sel_getUid("libraries:drawing:"), function $EditionMode__libraries_drawing_(self, _cmd, aJSON, aDrawing)
{
    CPLog.debug("Libraries received");
    CPLog.debug(aJSON);
    var libraries = aJSON.libraries;
    var generator = objj_msgSend(DynamicModelGenerator, "new");
    objj_msgSend(aDrawing, "generator:", generator);
    for (var i = 0; i < libraries.length; i++)
    {
        var library = libraries[i];
        var elements = library.elements;
        CPLog.debug("Processing library " + library.name);
        objj_msgSend(generator, "addDefinitions:", elements);
    }
    var initialX = 20;
    for (var i = 0; i < libraries.length; i++)
    {
        var library = libraries[i];
        var toolbox = objj_msgSend(ToolboxFigure, "initializeWith:at:", aDrawing, CGPointMake(initialX, 25));
        objj_msgSend(toolbox, "columns:", 2);
        var elements = library.elements;
        for (var j = 0; j < elements.length; j++)
        {
            var element = elements[j];
            if (element.type == "PROCESSOR" || element.type == "ROUTER" || element.type == "ABSTRACTION")
            {
                objj_msgSend(toolbox, "addTool:withTitle:image:", objj_msgSend(CreateProcessorTool, "drawing:elementName:generator:", aDrawing, element.name, generator), element.displayName, element.icon);
            }
            else if (element.type == "MESSAGE_SOURCE")
            {
                objj_msgSend(toolbox, "addTool:withTitle:image:", objj_msgSend(CreateMessageSourceTool, "drawing:elementName:generator:", aDrawing, element.name, generator), element.displayName, element.icon);
            }
            else if (element.type == "CONNECTION")
            {
                var acceptedSourceTypes = nil;
                var acceptedSourceMax = element.acceptedSourceMax;
                var acceptedTargetTypes = nil;
                var acceptedTargetMax = element.acceptedSourceMax;
                if (element.acceptedSourceTypes != "*")
                {
                    var types = element.acceptedSourceTypes.split(',');
                    acceptedSourceTypes = objj_msgSend(CPSet, "setWithArray:", types);
                }
                if (element.acceptedTargetTypes != "*")
                {
                    var types = element.acceptedTargetTypes.split(',');
                    acceptedTargetTypes = objj_msgSend(CPSet, "setWithArray:", types);
                }
                objj_msgSend(toolbox, "addTool:withTitle:image:", objj_msgSend(CreateElementConnectionTool, "drawing:acceptedSourceTypes:acceptedSourceMax:acceptedTargetTypes:acceptedTargetMax:elementName:", aDrawing, acceptedSourceTypes, acceptedSourceMax, acceptedTargetTypes, acceptedTargetMax, element.name), element.displayName, element.icon);
            }
        }
        objj_msgSend(aDrawing, "addToolbox:withId:", toolbox, library.name);
        initialX = initialX + 80;
    }
}
,["void","id","id"])]);
}