@STATIC;1.0;p;6;main.jt;292;@STATIC;1.0;I;23;Foundation/Foundation.jI;15;AppKit/AppKit.ji;15;AppController.jt;206;objj_executeFile("Foundation/Foundation.j", NO);objj_executeFile("AppKit/AppKit.j", NO);objj_executeFile("AppController.j", YES);main = function(args, namedArgs)
{
    CPApplicationMain(args, namedArgs);
}
p;15;AppController.jt;1862;@STATIC;1.0;I;21;Foundation/CPObject.jI;17;CupDraw/CupDraw.ji;17;Actions/Actions.jt;1773;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("CupDraw/CupDraw.j", NO);objj_executeFile("Actions/Actions.j", YES);{var the_class = objj_allocateClassPair(CPObject, "AppController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("drawing")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("applicationDidFinishLaunching:"), function $AppController__applicationDidFinishLaunching_(self, _cmd, aNotification)
{
    CPLogRegister(CPLogPopup);
    var theWindow = objj_msgSend(objj_msgSend(CPWindow, "alloc"), "initWithContentRect:styleMask:", CGRectMakeZero(), CPBorderlessBridgeWindowMask),
        contentView = objj_msgSend(theWindow, "contentView");
    objj_msgSend(self, "initializeDrawing:window:", contentView, theWindow);
    objj_msgSend(theWindow, "orderFront:", self);
    objj_msgSend(theWindow, "makeFirstResponder:", self.drawing);
}
,["void","CPNotification"]), new objj_method(sel_getUid("initializeDrawing:window:"), function $AppController__initializeDrawing_window_(self, _cmd, contentView, theWindow)
{
    var contextId = objj_msgSend(Actions, "contextId");
    self.drawing = objj_msgSend(ActionsDrawing, "frame:contextId:", objj_msgSend(contentView, "bounds"), contextId);
    objj_msgSend(Actions, "drawing:", self.drawing);
    objj_msgSend(self.drawing, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    objj_msgSend(contentView, "addSubview:", self.drawing);
    var mode = objj_msgSend(Actions, "modeParam");
    if (mode != nil && objj_msgSend(mode, "isEqual:", "deployment"))
    {
        objj_msgSend(Actions, "mode:", objj_msgSend(DeploymentMode, "new"));
    }
    objj_msgSend(Actions, "load:", self.drawing);
}
,["void","id","id"])]);
}p;17;Actions/Actions.jt;6100;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jI;15;AppKit/CPView.jI;20;AppKit/CPTextField.ji;15;Util/DataUtil.ji;19;ActionsController.ji;37;Controller/DebuggerWindowController.ji;39;Controller/ElementAPIWindowController.ji;43;Controller/InterpreterAPIWindowController.ji;32;Controller/AddLoggerController.ji;31;Controller/ViewLoggerDelegate.ji;39;Controller/ViewLoggerWindowController.ji;32;Controller/SendMessageDelegate.ji;29;Controller/DebuggerDelegate.ji;48;Controller/AddLazyAutorefreshableCacheDelegate.ji;41;Controller/AddLazyComputedCacheDelegate.ji;20;Model/ElementModel.ji;27;Model/DynamicElementModel.ji;33;Model/DynamicMessageSourceModel.ji;29;Model/DynamicModelGenerator.ji;15;Figure/Magnet.ji;25;Figure/MultiStateFigure.ji;22;Figure/ElementFigure.ji;27;Figure/ElementStateFigure.ji;24;Figure/ProcessorFigure.ji;28;Figure/MessageSourceFigure.ji;33;Figure/MessageSourceStateFigure.ji;26;Figure/ElementConnection.ji;26;Tool/CreateProcessorTool.ji;34;Tool/CreateElementConnectionTool.ji;30;Tool/CreateMessageSourceTool.ji;28;Command/LoadActionsCommand.ji;28;Command/SaveActionsCommand.ji;25;Command/AbstractCommand.ji;19;Server/ContextAPI.ji;19;Server/ElementAPI.ji;23;Server/InterpreterAPI.ji;16;ActionsDrawing.ji;13;EditionMode.ji;16;DeploymentMode.jt;4811;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);objj_executeFile("AppKit/CPView.j", NO);objj_executeFile("AppKit/CPTextField.j", NO);objj_executeFile("Util/DataUtil.j", YES);objj_executeFile("ActionsController.j", YES);objj_executeFile("Controller/DebuggerWindowController.j", YES);objj_executeFile("Controller/ElementAPIWindowController.j", YES);objj_executeFile("Controller/InterpreterAPIWindowController.j", YES);objj_executeFile("Controller/AddLoggerController.j", YES);objj_executeFile("Controller/ViewLoggerDelegate.j", YES);objj_executeFile("Controller/ViewLoggerWindowController.j", YES);objj_executeFile("Controller/SendMessageDelegate.j", YES);objj_executeFile("Controller/DebuggerDelegate.j", YES);objj_executeFile("Controller/AddLazyAutorefreshableCacheDelegate.j", YES);objj_executeFile("Controller/AddLazyComputedCacheDelegate.j", YES);objj_executeFile("Model/ElementModel.j", YES);objj_executeFile("Model/DynamicElementModel.j", YES);objj_executeFile("Model/DynamicMessageSourceModel.j", YES);objj_executeFile("Model/DynamicModelGenerator.j", YES);objj_executeFile("Figure/Magnet.j", YES);objj_executeFile("Figure/MultiStateFigure.j", YES);objj_executeFile("Figure/ElementFigure.j", YES);objj_executeFile("Figure/ElementStateFigure.j", YES);objj_executeFile("Figure/ProcessorFigure.j", YES);objj_executeFile("Figure/MessageSourceFigure.j", YES);objj_executeFile("Figure/MessageSourceStateFigure.j", YES);/* objj_executeFile("Figure/DebugFigure.j", YES) */ (undefined);objj_executeFile("Figure/ElementConnection.j", YES);/* objj_executeFile("Window/NewMessagePanel.j", YES) */ (undefined);/* objj_executeFile("Window/ViewMessagePanel.j", YES) */ (undefined);/* objj_executeFile("Window/DebugWindow.j", YES) */ (undefined);/* objj_executeFile("Window/AddLoggerWindow.j", YES) */ (undefined);/* objj_executeFile("Window/ViewLoggerWindow.j", YES) */ (undefined);objj_executeFile("Tool/CreateProcessorTool.j", YES);objj_executeFile("Tool/CreateElementConnectionTool.j", YES);objj_executeFile("Tool/CreateMessageSourceTool.j", YES);/* objj_executeFile("Command/DeployCommand.j", YES) */ (undefined);objj_executeFile("Command/LoadActionsCommand.j", YES);objj_executeFile("Command/SaveActionsCommand.j", YES);objj_executeFile("Command/AbstractCommand.j", YES);objj_executeFile("Server/ContextAPI.j", YES);objj_executeFile("Server/ElementAPI.j", YES);objj_executeFile("Server/InterpreterAPI.j", YES);/* objj_executeFile("Server/LibraryAPI.j", YES) */ (undefined);objj_executeFile("ActionsDrawing.j", YES);objj_executeFile("EditionMode.j", YES);objj_executeFile("DeploymentMode.j", YES);var _actionsDrawingMode = objj_msgSend(EditionMode, "new");
var _drawing;
var _controller;
{var the_class = objj_allocateClassPair(CPObject, "Actions"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(meta_class, [new objj_method(sel_getUid("isDevelopment"), function $Actions__isDevelopment(self, _cmd)
{
    return false;
}
,["id"]), new objj_method(sel_getUid("mode:"), function $Actions__mode_(self, _cmd, aMode)
{
    _actionsDrawingMode = aMode;
}
,["void","id"]), new objj_method(sel_getUid("drawing"), function $Actions__drawing(self, _cmd)
{
    return _drawing;
}
,["id"]), new objj_method(sel_getUid("controller"), function $Actions__controller(self, _cmd)
{
    return _controller;
}
,["id"]), new objj_method(sel_getUid("drawing:"), function $Actions__drawing_(self, _cmd, aDrawing)
{
    _drawing = aDrawing;
    _controller = objj_msgSend(ActionsController, "drawing:", _drawing);
}
,["void","id"]), new objj_method(sel_getUid("mode"), function $Actions__mode(self, _cmd)
{
    return _actionsDrawingMode;
}
,["id"]), new objj_method(sel_getUid("load:"), function $Actions__load_(self, _cmd, aDrawing)
{
    objj_msgSend(_actionsDrawingMode, "load:", aDrawing);
}
,["void","id"]), new objj_method(sel_getUid("contextId"), function $Actions__contextId(self, _cmd)
{
    var sharedApplication = objj_msgSend(CPApplication, "sharedApplication");
    var namedArguments = objj_msgSend(sharedApplication, "namedArguments");
    var contextId = objj_msgSend(namedArguments, "objectForKey:", "contextId");
    return contextId;
}
,["id"]), new objj_method(sel_getUid("modeParam"), function $Actions__modeParam(self, _cmd)
{
    var sharedApplication = objj_msgSend(CPApplication, "sharedApplication");
    var namedArguments = objj_msgSend(sharedApplication, "namedArguments");
    var mode = objj_msgSend(namedArguments, "objectForKey:", "mode");
    return mode;
}
,["id"]), new objj_method(sel_getUid("deploymentId"), function $Actions__deploymentId(self, _cmd)
{
    var deploymentId = window.top.location.href;
    deploymentId = deploymentId.substring(deploymentId.lastIndexOf('/') + 1);
    return deploymentId;
}
,["id"])]);
}p;21;Actions/EditionMode.jt;12418;@STATIC;1.0;t;12398;{var the_class = objj_allocateClassPair(CPObject, "EditionMode"),
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
}p;23;Actions/Figure/Magnet.jt;1685;@STATIC;1.0;t;1666;{var the_class = objj_allocateClassPair(CPObject, "Magnet"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_sourceFigure"), new objj_ivar("_targetFigure"), new objj_ivar("_selector")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithSource:target:selector:"), function $Magnet__initWithSource_target_selector_(self, _cmd, aSourceFigure, aTargetFigure, aSelector)
{
    self._sourceFigure = aSourceFigure;
    self._targetFigure = aTargetFigure;
    self._selector = aSelector;
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateLocation:"), "CPViewFrameDidChangeNotification", self._sourceFigure);
    return self;
}
,["id","id","id","id"]), new objj_method(sel_getUid("updateLocation:"), function $Magnet__updateLocation_(self, _cmd, aNotification)
{
    var position = objj_msgSend(self._sourceFigure, "performSelector:", self._selector);
    var targetFrame = objj_msgSend(self._targetFigure, "frame");
    var xShift = 0;
    var yShift = 0;
    var newTopLeft = CGPointMake(position.x - xShift, position.y - yShift);
    CPLog.debug(newTopLeft.x, newTopLeft.y);
    objj_msgSend(self._targetFigure, "setFrameOrigin:", newTopLeft);
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newWithSource:target:selector:"), function $Magnet__newWithSource_target_selector_(self, _cmd, aSourceFigure, aTargetFigure, aSelector)
{
    return objj_msgSend(objj_msgSend(self, "new"), "initWithSource:target:selector:", aSourceFigure, aTargetFigure, aSelector);
}
,["id","id","id","id"])]);
}p;41;Actions/Figure/MessageSourceStateFigure.jt;2229;@STATIC;1.0;t;2210;{var the_class = objj_allocateClassPair(MultiStateFigure, "MessageSourceStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_lookModel"), new objj_ivar("_lastState")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithModel:"), function $MessageSourceStateFigure__initializeWithModel_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("MessageSourceStateFigure").super_class }, "init");
    var state1 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/service_stop.png", 0, 0);
    var state2 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/service_running.png", 0, 0);
    objj_msgSend(state1, "showBorder:", NO);
    objj_msgSend(state2, "showBorder:", NO);
    objj_msgSend(self, "addFigure:forState:", state1, "STOP");
    objj_msgSend(self, "addFigure:forState:", state2, "START");
    self._lookModel = aModel;
    objj_msgSend(self, "setHidden:", NO);
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateStateFigure"), ModelPropertyChangedNotification, self._lookModel);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("updateStateFigure"), function $MessageSourceStateFigure__updateStateFigure(self, _cmd)
{
    var state = objj_msgSend(self._lookModel, "messageSourceState");
    if (self._lastState == nil || !objj_msgSend(self._lastState, "isEqualToString:", state))
    {
        if (objj_msgSend(state, "isEqualToString:", "STOP"))
        {
            objj_msgSend(self, "switchToState:", "STOP");
        }
        if (objj_msgSend(state, "isEqualToString:", "START"))
        {
            objj_msgSend(self, "switchToState:", "START");
        }
    }
}
,["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:model:"), function $MessageSourceStateFigure__newAt_model_(self, _cmd, aPoint, aModel)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("MessageSourceStateFigure").super_class }, "newAt:", aPoint), "initializeWithModel:", aModel);
}
,["Figure","CGPoint","id"])]);
}p;33;Actions/Figure/MultiStateFigure.jt;1802;@STATIC;1.0;t;1783;{var the_class = objj_allocateClassPair(Figure, "MultiStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_stateMapping")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $MultiStateFigure__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("MultiStateFigure").super_class }, "init");
    objj_msgSend(self, "selectable:", NO);
    objj_msgSend(self, "moveable:", YES);
    objj_msgSend(self, "editable:", NO);
    self._stateMapping = objj_msgSend(CPDictionary, "dictionary");
    return self;
}
,["id"]), new objj_method(sel_getUid("addFigure:forState:"), function $MultiStateFigure__addFigure_forState_(self, _cmd, aFigure, aState)
{
    objj_msgSend(self._stateMapping, "setObject:forKey:", aFigure, aState);
}
,["void","id","id"]), new objj_method(sel_getUid("switchToState:"), function $MultiStateFigure__switchToState_(self, _cmd, aState)
{
    CPLog.debug("Switching to state " + aState);
    var figure = objj_msgSend(self._stateMapping, "objectForKey:", aState);
    if (figure != nil)
    {
        objj_msgSend(self, "setSubviews:", objj_msgSend(CPMutableArray, "array"));
        objj_msgSend(self, "addSubview:", figure);
        var frameSize = objj_msgSend(figure, "frameSize");
        objj_msgSend(self, "setFrameSize:", frameSize);
        objj_msgSend(self, "invalidate");
        CPLog.debug("Switched to state " + aState);
    }
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $MultiStateFigure__newAt_(self, _cmd, aPoint)
{
    return objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("MultiStateFigure").super_class }, "newAt:", aPoint);
}
,["Figure","CGPoint"])]);
}p;36;Actions/Command/LoadActionsCommand.jt;6652;@STATIC;1.0;t;6633;{var the_class = objj_allocateClassPair(Command, "LoadActionsCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $LoadActionsCommand__execute(self, _cmd)
{
    var diagramRepresentation = objj_msgSend(DataUtil, "var:", "diagramRepresentation");
    if (diagramRepresentation != nil)
    {
        CPLog.debug("[LoadActionsCommand] Diagram representation found!");
        var contextDefinition = diagramRepresentation;
        objj_msgSend(self, "processObjectDefinitions:", contextDefinition.definitions);
    }
    return;
}
,["void"]), new objj_method(sel_getUid("processResult:"), function $LoadActionsCommand__processResult_(self, _cmd, aContextDefinitionRoot)
{
    var contextDefinition = jQuery.parseJSON(aContextDefinitionRoot.contextdefinition);
    objj_msgSend(self, "processObjectDefinitions:", contextDefinition.definitions);
}
,["void","id"]), new objj_method(sel_getUid("processObjectDefinitions:"), function $LoadActionsCommand__processObjectDefinitions_(self, _cmd, definitions)
{
    CPLog.debug("[LoadActionsCommand] Processing " + definitions.length + " definitions");
    var connections = objj_msgSend(CPMutableArray, "array");
    for (var i = 0; i < definitions.length; i++)
    {
        var objectDefinition = definitions[i];
        var elementName = objectDefinition.name;
        CPLog.debug("[LoadActionsCommand] Processing " + elementName);
        if (objj_msgSend(elementName, "hasSuffix:", "_CONNECTION"))
        {
            CPLog.debug("[LoadActionsCommand] Saving connection for later process");
            objj_msgSend(connections, "addObject:", objectDefinition);
        }
        else
        {
            var elementId = objectDefinition.id;
            var x = objectDefinition.properties["x"];
            var y = objectDefinition.properties["y"];
            x = x == nil ? 0 : objj_msgSend(x, "intValue");
            y = y == nil ? 0 : objj_msgSend(y, "intValue");
            var properties = objj_msgSend(CPDictionary, "dictionaryWithJSObject:", objectDefinition.properties);
            var aPoint = CGPointMake(x, y);
            var figure = nil;
            if (objj_msgSend(self, "isProcessor:", elementName) || objj_msgSend(self, "isRouter:", elementName))
            {
                CPLog.debug("[LoadActionsCommand] Adding processor or router");
                var tool = objj_msgSend(CreateProcessorTool, "drawing:elementName:generator:", self._drawing, elementName, objj_msgSend(self._drawing, "generator"));
                figure = objj_msgSend(CreateProcessorTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, self._drawing, elementName, NO, elementId, properties, tool);
            }
            else if (objj_msgSend(self, "isMessageSource:", elementName))
            {
                CPLog.debug("[LoadActionsCommand] Adding message source");
                var tool = objj_msgSend(CreateMessageSourceTool, "drawing:elementName:generator:", self._drawing, elementName, objj_msgSend(self._drawing, "generator"));
                figure = objj_msgSend(CreateMessageSourceTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, self._drawing, elementName, NO, elementId, properties, tool);
            }
            if (figure != nil)
            {
                objj_msgSend(self._drawing, "registerElement:for:", figure, elementId);
            }
        }
    }
    for (var i = 0; i < connections.length; i++)
    {
        CPLog.debug("[LoadActionsCommand] Post processing connection");
        var connection = connections[i];
        objj_msgSend(self, "processConnection:", connection);
    }
}
,["void","id"]), new objj_method(sel_getUid("isRouter:"), function $LoadActionsCommand__isRouter_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isRouter:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("isProcessor:"), function $LoadActionsCommand__isProcessor_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isProcessor:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("isMessageSource:"), function $LoadActionsCommand__isMessageSource_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isMessageSource:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("processConnection:"), function $LoadActionsCommand__processConnection_(self, _cmd, aConnection)
{
    var sourceId = aConnection.properties["source"].substring("urn:".length);
    var targetId = aConnection.properties["target"].substring("urn:".length);
    var source = objj_msgSend(self._drawing, "processorFor:", sourceId);
    var target = objj_msgSend(self._drawing, "processorFor:", targetId);
    var properties = nil;
    if (aConnection.properties != nil)
    {
        properties = objj_msgSend(CPDictionary, "dictionaryWithJSObject:", aConnection.properties);
    }
    var pointsAsString = aConnection.properties["points"];
    var points = nil;
    if (pointsAsString != nil)
    {
        points = objj_msgSend(CPMutableArray, "array");
        var pointsArray = pointsAsString.split(";");
        for (var j = 0; j < pointsArray.length; j++)
        {
            var pointAsString = pointsArray[j];
            if (!objj_msgSend(pointAsString, "isEqual:", ""))
            {
                var pointParts = pointAsString.split(",");
                var x = pointParts[0];
                var y = pointParts[1];
                var point = CGPointMake(objj_msgSend(x, "intValue"), objj_msgSend(y, "intValue"));
                objj_msgSend(points, "addObject:", point);
            }
        }
    }
    var elementId = aConnection.id;
    var elementName = aConnection.name;
    var connectionTool = objj_msgSend(CreateElementConnectionTool, "drawing:acceptedSourceTypes:acceptedSourceMax:acceptedTargetTypes:acceptedTargetMax:elementName:", self._drawing, nil, 0, nil, 0, elementName);
    var connectionFigure = objj_msgSend(ElementConnection, "source:target:points:", source, target, points);
    objj_msgSend(self._drawing, "addFigure:", connectionFigure);
    objj_msgSend(connectionTool, "postConnectionCreated:figureId:properties:", connectionFigure, elementId, properties);
    if (connectionFigure != nil)
    {
        objj_msgSend(self._drawing, "registerElement:for:", connectionFigure, elementId);
    }
}
,["void","id"])]);
}p;23;Actions/Util/DataUtil.jt;727;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;655;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "DataUtil"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(meta_class, [new objj_method(sel_getUid("var:"), function $DataUtil__var_(self, _cmd, aVarName)
{
    var current = window;
    var value = current[aVarName];
    while (value == undefined && window.top != current)
    {
        current = current.parent;
        value = current[aVarName];
    }
    if (value != undefined)
    {
        return value;
    }
    else
    {
        return nil;
    }
}
,["id","id"])]);
}p;34;Actions/Tool/CreateProcessorTool.jt;3103;@STATIC;1.0;t;3084;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateProcessorTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_generator")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateProcessorTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{
    objj_msgSend(objj_msgSend(self, "class"), "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, aDrawing, self._elementName, YES, nil, nil, self);
}
,["void","id","id"]), new objj_method(sel_getUid("generator"), function $CreateProcessorTool__generator(self, _cmd)
{
    return self._generator;
}
,["id"]), new objj_method(sel_getUid("generator:"), function $CreateProcessorTool__generator_(self, _cmd, aGenerator)
{
    self._generator = aGenerator;
}
,["void","id"]), new objj_method(sel_getUid("elementName:"), function $CreateProcessorTool__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:elementName:generator:"), function $CreateProcessorTool__drawing_elementName_generator_(self, _cmd, aDrawing, anElementName, aGenerator)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateProcessorTool").super_class }, "drawing:", aDrawing);
    objj_msgSend(tool, "elementName:", anElementName);
    objj_msgSend(tool, "generator:", aGenerator);
    return tool;
}
,["id","Drawing","id","id"]), new objj_method(sel_getUid("createFigureAt:on:elementName:edit:elementId:initialProperties:tool:"), function $CreateProcessorTool__createFigureAt_on_elementName_edit_elementId_initialProperties_tool_(self, _cmd, aPoint, aDrawing, elementName, activateEdit, elementId, properties, aTool)
{
    var generator = objj_msgSend(aTool, "generator");
    var iconUrl = objj_msgSend(generator, "icon:", elementName);
    var contextId = objj_msgSend(aDrawing, "contextId");
    var newFigure = objj_msgSend(ProcessorFigure, "newAt:iconUrl:", aPoint, iconUrl);
    var hasBreakpoint = objj_msgSend(properties, "objectForKey:", "_breakpoint");
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", elementName, elementId, contextId, properties, hasBreakpoint);
    var stateFigure = objj_msgSend(ElementStateFigure, "newAt:elementModel:", aPoint, newModel);
    var magnet = objj_msgSend(Magnet, "newWithSource:target:selector:", newFigure, stateFigure, sel_getUid("topLeft"));
    objj_msgSend(newFigure, "model:", newModel);
    objj_msgSend(newFigure, "checkModelFeature:", "name");
    objj_msgSend(newModel, "changed");
    objj_msgSend(aDrawing, "addProcessor:", newFigure);
    objj_msgSend(aDrawing, "addFigure:", stateFigure);
    objj_msgSend(aTool, "activateSelectionTool");
    if (activateEdit && aTool != nil)
    {
        objj_msgSend(newFigure, "switchToEditMode");
    }
    return newFigure;
}
,["void","id","id","id","id","id","id","id"])]);
}p;32;Actions/Figure/ProcessorFigure.jt;3959;@STATIC;1.0;t;3940;{var the_class = objj_allocateClassPair(ElementFigure, "ProcessorFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_breakpointFigure"), new objj_ivar("_dataFigure"), new objj_ivar("_loggerWindow")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("beforeDeleteMenu:"), function $ProcessorFigure__beforeDeleteMenu_(self, _cmd, contextMenu)
{
    objj_msgSend(objj_msgSend(Actions, "mode"), "createProcessorFigureMenu:menu:", self, contextMenu);
}
,["void","CPMenu"]), new objj_method(sel_getUid("setBreakpointFigure:"), function $ProcessorFigure__setBreakpointFigure_(self, _cmd, aBreakpointFigure)
{
    self._breakpointFigure = aBreakpointFigure;
}
,["void","id"]), new objj_method(sel_getUid("dataFigure"), function $ProcessorFigure__dataFigure(self, _cmd)
{
    return self._dataFigure;
}
,["id"]), new objj_method(sel_getUid("setDataFigure:"), function $ProcessorFigure__setDataFigure_(self, _cmd, aLabel)
{
    self._dataFigure = aLabel;
}
,["void","id"]), new objj_method(sel_getUid("setBreakpoint:"), function $ProcessorFigure__setBreakpoint_(self, _cmd, sender)
{
    var hasBreakpoint = objj_msgSend(objj_msgSend(self, "model"), "hasBreakpoint");
    objj_msgSend(objj_msgSend(self, "model"), "setBreakpoint:", !hasBreakpoint);
    objj_msgSend(self._breakpointFigure, "setHidden:", hasBreakpoint);
}
,["void","id"]), new objj_method(sel_getUid("sendMessage:"), function $ProcessorFigure__sendMessage_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(Actions, "controller"), "initiateMessageSendFrom:", self);
}
,["void","id"]), new objj_method(sel_getUid("addProfiler:"), function $ProcessorFigure__addProfiler_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addProfiler");
}
,["void","id"]), new objj_method(sel_getUid("removeProfiler:"), function $ProcessorFigure__removeProfiler_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "removeProfiler");
}
,["void","id"]), new objj_method(sel_getUid("addLogger:"), function $ProcessorFigure__addLogger_(self, _cmd, sender)
{
    var myController = objj_msgSend(objj_msgSend(ElementAPIWindowController, "alloc"), "initWithWindowCibName:", "AddLoggerWindow");
    objj_msgSend(myController, "showWindow:", nil);
    objj_msgSend(myController, "elementAPI:", objj_msgSend(objj_msgSend(self, "model"), "api"));
}
,["void","id"]), new objj_method(sel_getUid("removeLogger:"), function $ProcessorFigure__removeLogger_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "removeLogger");
}
,["void","id"]), new objj_method(sel_getUid("viewLog:"), function $ProcessorFigure__viewLog_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "getLogger");
}
,["void","id"]), new objj_method(sel_getUid("loggerLines:"), function $ProcessorFigure__loggerLines_(self, _cmd, loggerInfo)
{
    var elementId = objj_msgSend(self, "elementId");
    var serverInfo = loggerInfo.logger.servers[0];
    var lines;
    if (serverInfo === undefined)
    {
        lines = "No log yet";
    }
    else
    {
        lines = serverInfo.logger[elementId];
    }
    var myController = objj_msgSend(objj_msgSend(ViewLoggerWindowController, "alloc"), "initWithWindowCibName:", "ViewLoggerWindow");
    objj_msgSend(myController, "lines:", lines);
    objj_msgSend(myController, "showWindow:", nil);
}
,["void","id"]), new objj_method(sel_getUid("acceptsNewStartingChain"), function $ProcessorFigure__acceptsNewStartingChain(self, _cmd)
{
    return objj_msgSend(self._outConnections, "count") == 0 || !objj_msgSend(self, "hasNextInChainConnections");
}
,["id"]), new objj_method(sel_getUid("highlight"), function $ProcessorFigure__highlight(self, _cmd)
{
}
,["void"]), new objj_method(sel_getUid("unhighlight"), function $ProcessorFigure__unhighlight(self, _cmd)
{
}
,["void"])]);
}p;30;Actions/Figure/ElementFigure.jt;5737;@STATIC;1.0;t;5718;var ElementNextInChainConnection = "NEXT_IN_CHAIN";
var ElementAllConnection = "ALL";
var ElementChoiceConnection = "CHOICE";
var ElementWireTapConnection = "WIRE_TAP";
{var the_class = objj_allocateClassPair(IconLabelFigure, "ElementFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_delegate")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:iconUrl:"), function $ElementFigure__initWithFrame_iconUrl_(self, _cmd, aFrame, iconUrl)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementFigure").super_class }, "initWithFrame:iconUrl:", aFrame, iconUrl);
    objj_msgSend(objj_msgSend(Actions, "mode"), "createElementFigureMenu:", self);
    return self;
}
,["id","CGRect","id"]), new objj_method(sel_getUid("delegate:"), function $ElementFigure__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
    if (objj_msgSend(self, "elementId") != nil)
    {
        objj_msgSend(self._delegate, "registerElement:for:", self, objj_msgSend(self, "elementId"));
    }
}
,["void","id"]), new objj_method(sel_getUid("deleteFromServer"), function $ElementFigure__deleteFromServer(self, _cmd)
{
    objj_msgSend(objj_msgSend(self, "model"), "deleteFromServer");
}
,["void"]), new objj_method(sel_getUid("deleted"), function $ElementFigure__deleted(self, _cmd)
{
    objj_msgSend(self, "removeMyself");
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementFigure__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"]), new objj_method(sel_getUid("nextInChain:"), function $ElementFigure__nextInChain_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Setting next in chain");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementNextInChainConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addAllConnection:"), function $ElementFigure__addAllConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding all connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementAllConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addWireTapConnection:"), function $ElementFigure__addWireTapConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding wire tap connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementWireTapConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addChoiceConnection:"), function $ElementFigure__addChoiceConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding choice connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementChoiceConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("nextInChainId:for:"), function $ElementFigure__nextInChainId_for_(self, _cmd, anId, anElementId)
{
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(outConnection, "isKindOfClass:", objj_msgSend(NextInChainConnection, "class")) && objj_msgSend(objj_msgSend(objj_msgSend(outConnection, "target"), "elementId"), "isEqual:", anElementId))
        {
            objj_msgSend(outConnection, "id:", anId);
        }
    }
}
,["void","id","id"]), new objj_method(sel_getUid("elementId:"), function $ElementFigure__elementId_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementFigure] Element created with id " + anElementId);
    if (self._delegate != nil)
    {
        objj_msgSend(self._delegate, "registerElement:for:", self, anElementId);
    }
}
,["void","id"]), new objj_method(sel_getUid("model:"), function $ElementFigure__model_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementFigure").super_class }, "model:", aModel);
    objj_msgSend(aModel, "delegate:", self);
}
,["void","id"]), new objj_method(sel_getUid("acceptsNewStartingChain"), function $ElementFigure__acceptsNewStartingChain(self, _cmd)
{
    return true;
}
,["id"]), new objj_method(sel_getUid("acceptsNewEndingChain"), function $ElementFigure__acceptsNewEndingChain(self, _cmd)
{
    return true;
}
,["id"]), new objj_method(sel_getUid("hasNextInChainConnections"), function $ElementFigure__hasNextInChainConnections(self, _cmd)
{
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(outConnection, "isKindOfClass:", objj_msgSend(NextInChainConnection, "class")))
        {
            return true;
        }
    }
    return false;
}
,["id"]), new objj_method(sel_getUid("countConnectionsOfType:"), function $ElementFigure__countConnectionsOfType_(self, _cmd, anElementName)
{
    var count = 0;
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(objj_msgSend(outConnection, "elementName"), "isEqual:", anElementName))
        {
            count++;
        }
    }
    return count;
}
,["id","id"])]);
}p;47;Actions/Controller/ElementAPIWindowController.jt;656;@STATIC;1.0;I;21;Foundation/CPObject.jt;612;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "ElementAPIWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("elementAPI"), function $ElementAPIWindowController__elementAPI(self, _cmd)
{
    return self._elementAPI;
}
,["id"]), new objj_method(sel_getUid("elementAPI:"), function $ElementAPIWindowController__elementAPI_(self, _cmd, anElementAPI)
{
    self._elementAPI = anElementAPI;
}
,["void","id"])]);
}p;47;Actions/Controller/ViewLoggerWindowController.jt;1056;@STATIC;1.0;I;21;Foundation/CPObject.jt;1011;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "ViewLoggerWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_lines"), new objj_ivar("_logText")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("lines"), function $ViewLoggerWindowController__lines(self, _cmd)
{
    return self._lines;
}
,["id"]), new objj_method(sel_getUid("lines:"), function $ViewLoggerWindowController__lines_(self, _cmd, aLines)
{
    self._lines = aLines;
}
,["void","id"]), new objj_method(sel_getUid("logText:"), function $ViewLoggerWindowController__logText_(self, _cmd, aLogText)
{
    self._logText = aLogText;
}
,["void","id"]), new objj_method(sel_getUid("windowDidLoad"), function $ViewLoggerWindowController__windowDidLoad(self, _cmd)
{
    var lines = objj_msgSend(self, "lines");
    var log = lines.join('\n');
    objj_msgSend(self._logText, "setStringValue:", log);
}
,["void"])]);
}p;35;Actions/Figure/ElementStateFigure.jt;2960;@STATIC;1.0;t;2941;{var the_class = objj_allocateClassPair(MultiStateFigure, "ElementStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementModel"), new objj_ivar("_lastState")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithModel:"), function $ElementStateFigure__initializeWithModel_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementStateFigure").super_class }, "init");
    var state1 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/cross.gif", 0, 0);
    var state2 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/sync.gif", 0, 0);
    var state3 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/tick.gif", 0, 0);
    objj_msgSend(state1, "showBorder:", NO);
    objj_msgSend(state2, "showBorder:", NO);
    objj_msgSend(state3, "showBorder:", NO);
    objj_msgSend(self, "addFigure:forState:", state1, "NOT_IN_SYNC");
    objj_msgSend(self, "addFigure:forState:", state2, "SYNCING");
    objj_msgSend(self, "addFigure:forState:", state3, "SYNCED");
    self._elementModel = aModel;
    objj_msgSend(self, "setHidden:", YES);
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateStateFigure"), ModelPropertyChangedNotification, self._elementModel);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("updateStateFigure"), function $ElementStateFigure__updateStateFigure(self, _cmd)
{
    var state = objj_msgSend(self._elementModel, "state");
    if (self._lastState == nil || !objj_msgSend(self._lastState, "isEqualToString:", state))
    {
        if (objj_msgSend(state, "isEqualToString:", "NOT_IN_SYNC"))
        {
            objj_msgSend(self, "switchToState:", "NOT_IN_SYNC");
            objj_msgSend(self, "fadeIn");
        }
        if (objj_msgSend(state, "isEqualToString:", "SYNCING"))
        {
            objj_msgSend(self, "switchToState:", "SYNCING");
        }
        if (objj_msgSend(state, "isEqualToString:", "SYNCED"))
        {
            objj_msgSend(self, "switchToState:", "SYNCED");
            objj_msgSend(self, "fadeOut");
        }
        if (objj_msgSend(state, "isEqualToString:", "DELETED"))
        {
            objj_msgSend(self, "removeMyself");
        }
    }
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementStateFigure__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:elementModel:"), function $ElementStateFigure__newAt_elementModel_(self, _cmd, aPoint, aModel)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ElementStateFigure").super_class }, "newAt:", aPoint), "initializeWithModel:", aModel);
}
,["Figure","CGPoint","id"])]);
}p;38;Actions/Tool/CreateMessageSourceTool.jt;2869;@STATIC;1.0;t;2850;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateMessageSourceTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_generator")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateMessageSourceTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{
    objj_msgSend(objj_msgSend(self, "class"), "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, aDrawing, self._elementName, YES, nil, nil, self);
}
,["void","id","id"]), new objj_method(sel_getUid("generator"), function $CreateMessageSourceTool__generator(self, _cmd)
{
    return self._generator;
}
,["id"]), new objj_method(sel_getUid("generator:"), function $CreateMessageSourceTool__generator_(self, _cmd, aGenerator)
{
    self._generator = aGenerator;
}
,["void","id"]), new objj_method(sel_getUid("elementName:"), function $CreateMessageSourceTool__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:elementName:generator:"), function $CreateMessageSourceTool__drawing_elementName_generator_(self, _cmd, aDrawing, anElementName, aGenerator)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateMessageSourceTool").super_class }, "drawing:", aDrawing);
    objj_msgSend(tool, "elementName:", anElementName);
    objj_msgSend(tool, "generator:", aGenerator);
    return tool;
}
,["id","Drawing","id","id"]), new objj_method(sel_getUid("createFigureAt:on:elementName:edit:elementId:initialProperties:tool:"), function $CreateMessageSourceTool__createFigureAt_on_elementName_edit_elementId_initialProperties_tool_(self, _cmd, aPoint, aDrawing, elementName, activateEdit, elementId, properties, aTool)
{
    var generator = objj_msgSend(aTool, "generator");
    var iconUrl = objj_msgSend(generator, "icon:", elementName);
    var contextId = objj_msgSend(aDrawing, "contextId");
    var newFigure = objj_msgSend(MessageSourceFigure, "newAt:iconUrl:", aPoint, iconUrl);
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", elementName, elementId, contextId, properties, false);
    objj_msgSend(newFigure, "model:", newModel);
    objj_msgSend(newFigure, "checkModelFeature:", "name");
    objj_msgSend(aDrawing, "addMessageSource:", newFigure);
    objj_msgSend(aTool, "activateSelectionTool");
    if (activateEdit && aTool != nil)
    {
        objj_msgSend(newFigure, "switchToEditMode");
    }
    objj_msgSend(objj_msgSend(Actions, "mode"), "createMessageSourceStateFigure:drawing:point:", newFigure, aDrawing, aPoint);
    return newFigure;
}
,["void","id","id","id","id","id","id","id"])]);
}p;36;Actions/Figure/MessageSourceFigure.jt;1193;@STATIC;1.0;t;1174;{var the_class = objj_allocateClassPair(ElementFigure, "MessageSourceFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("beforeDeleteMenu:"), function $MessageSourceFigure__beforeDeleteMenu_(self, _cmd, contextMenu)
{
    objj_msgSend(objj_msgSend(Actions, "mode"), "createMessageSourceFigureMenu:menu:", self, contextMenu);
}
,["void","CPMenu"]), new objj_method(sel_getUid("start:"), function $MessageSourceFigure__start_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "perform:arguments:", "start", nil);
    objj_msgSend(objj_msgSend(self, "model"), "messageSourceState:", "START");
}
,["void","id"]), new objj_method(sel_getUid("stop:"), function $MessageSourceFigure__stop_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "perform:arguments:", "stop", nil);
    objj_msgSend(objj_msgSend(self, "model"), "messageSourceState:", "STOP");
}
,["void","id"]), new objj_method(sel_getUid("acceptsNewEndingChain"), function $MessageSourceFigure__acceptsNewEndingChain(self, _cmd)
{
    return false;
}
,["id"])]);
}p;42;Actions/Tool/CreateElementConnectionTool.jt;5383;@STATIC;1.0;t;5364;{var the_class = objj_allocateClassPair(AbstractCreateConnectionTool, "CreateElementConnectionTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_acceptedSourceTypes"), new objj_ivar("_acceptedSourceMax"), new objj_ivar("_acceptedTargetTypes"), new objj_ivar("_acceptedTargetMax")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("acceptsNewStartingConnection:"), function $CreateElementConnectionTool__acceptsNewStartingConnection_(self, _cmd, aFigure)
{
    if (aFigure == nil)
    {
        return false;
    }
    if (!objj_msgSend(aFigure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")) || self._acceptedSourceTypes != nil && !objj_msgSend(self._acceptedSourceTypes, "containsObject:", objj_msgSend(objj_msgSend(aFigure, "model"), "elementName")))
    {
        CPLog.debug("[CreateElementConnectionTool] Not an accepted source element");
        return false;
    }
    var count = objj_msgSend(aFigure, "countConnectionsOfType:", self._elementName);
    if (count > self._acceptedSourceMax)
    {
        return false;
    }
    return true;
}
,["id","id"]), new objj_method(sel_getUid("acceptsNewEndingConnection:"), function $CreateElementConnectionTool__acceptsNewEndingConnection_(self, _cmd, aFigure)
{
    if (aFigure == nil)
    {
        return false;
    }
    if (!objj_msgSend(aFigure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")) || self._acceptedTargetTypes != nil && !objj_msgSend(self._acceptedTargetTypes, "containsObject:", objj_msgSend(objj_msgSend(aFigure, "model"), "elementName")))
    {
        CPLog.debug("[CreateElementConnectionTool] Not an accepted target element");
        return false;
    }
    var count = objj_msgSend(aFigure, "countConnectionsOfType:", self._elementName);
    if (count > self._acceptedTargetMax)
    {
        return false;
    }
    return true;
}
,["id","id"]), new objj_method(sel_getUid("postConnectionCreated:"), function $CreateElementConnectionTool__postConnectionCreated_(self, _cmd, aConnectionFigure)
{
    objj_msgSend(self, "postConnectionCreated:figureId:properties:", aConnectionFigure, ELEMENT_FAKE_ID, nil);
}
,["void","Connection"]), new objj_method(sel_getUid("postConnectionCreated:figureId:properties:"), function $CreateElementConnectionTool__postConnectionCreated_figureId_properties_(self, _cmd, aConnectionFigure, anID, aSetOfProperties)
{
    var generator = objj_msgSend(self._drawing, "generator");
    var contextId = objj_msgSend(self._drawing, "contextId");
    var color = objj_msgSend(generator, "color:", self._elementName);
    var source = objj_msgSend(aConnectionFigure, "source");
    var target = objj_msgSend(aConnectionFigure, "target");
    var targetId = objj_msgSend(target, "elementId");
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", self._elementName, anID, contextId, aSetOfProperties, false);
    if (objj_msgSend(ELEMENT_FAKE_ID, "isEqual:", anID))
    {
        objj_msgSend(objj_msgSend(objj_msgSend(source, "model"), "api"), "addConnectionType:to:identificable:", self._elementName, targetId, objj_msgSend(newModel, "api"));
    }
    objj_msgSend(aConnectionFigure, "model:", newModel);
    objj_msgSend(newModel, "delegate:", aConnectionFigure);
    objj_msgSend(aConnectionFigure, "foregroundColor:", objj_msgSend(CPColor, "colorWithHexString:", color));
}
,["void","Connection","id","id"]), new objj_method(sel_getUid("elementName:"), function $CreateElementConnectionTool__elementName_(self, _cmd, aValue)
{
    self._elementName = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedSourceTypes:"), function $CreateElementConnectionTool__acceptedSourceTypes_(self, _cmd, aValue)
{
    self._acceptedSourceTypes = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedSourceMax:"), function $CreateElementConnectionTool__acceptedSourceMax_(self, _cmd, aValue)
{
    self._acceptedSourceMax = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedTargetTypes:"), function $CreateElementConnectionTool__acceptedTargetTypes_(self, _cmd, aValue)
{
    self._acceptedTargetTypes = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedTargetMax:"), function $CreateElementConnectionTool__acceptedTargetMax_(self, _cmd, aValue)
{
    self._acceptedTargetMax = aValue;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:acceptedSourceTypes:acceptedSourceMax:acceptedTargetTypes:acceptedTargetMax:elementName:"), function $CreateElementConnectionTool__drawing_acceptedSourceTypes_acceptedSourceMax_acceptedTargetTypes_acceptedTargetMax_elementName_(self, _cmd, aDrawing, aListOfSources, sourceMax, aListOfTargets, targetMax, elementName)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateElementConnectionTool").super_class }, "drawing:figure:", aDrawing, objj_msgSend(ElementConnection, "class"));
    objj_msgSend(tool, "acceptedSourceTypes:", aListOfSources);
    objj_msgSend(tool, "acceptedSourceMax:", sourceMax);
    objj_msgSend(tool, "acceptedTargetTypes:", aListOfTargets);
    objj_msgSend(tool, "acceptedTargetMax:", targetMax);
    objj_msgSend(tool, "elementName:", elementName);
    return tool;
}
,["id","Drawing","id","id","id","id","id"])]);
}p;28;Actions/Model/ElementModel.jt;7070;@STATIC;1.0;t;7051;ELEMENT_FAKE_ID = "FAKE_ID";
{var the_class = objj_allocateClassPair(Model, "ElementModel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementAPI"), new objj_ivar("_delegate")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $ElementModel__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementModel").super_class }, "init");
    CPLog.debug("[ElementModel] Adding properties of ElementModel");
    return self;
}
,["id"]), new objj_method(sel_getUid("initialize:"), function $ElementModel__initialize_(self, _cmd, aContextId)
{
    self._elementAPI = objj_msgSend(ElementAPI, "newIn:elementName:", aContextId, objj_msgSend(self, "elementName"));
    CPLog.debug("[ElementModel] Setting delegate: " + self);
    objj_msgSend(self._elementAPI, "delegate:", self);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("initialize:elementName:"), function $ElementModel__initialize_elementName_(self, _cmd, aContextId, anElementName)
{
    self._elementAPI = objj_msgSend(ElementAPI, "newIn:elementName:", aContextId, anElementName);
    CPLog.debug("[ElementModel] Setting delegate: " + self);
    objj_msgSend(self._elementAPI, "delegate:", self);
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("initialize:elementId:hasBreakpoint:"), function $ElementModel__initialize_elementId_hasBreakpoint_(self, _cmd, aContextId, anElementId, hasBreakpoint)
{
    self._elementAPI = objj_msgSend(ElementAPI, "newIn:elementId:hasBreakpoint:", aContextId, anElementId, hasBreakpoint);
    CPLog.debug("[ElementModel] Setting delegate: " + self);
    objj_msgSend(self._elementAPI, "delegate:", self);
    return self;
}
,["id","id","id","id"]), new objj_method(sel_getUid("delegate:"), function $ElementModel__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
}
,["void","id"]), new objj_method(sel_getUid("defaultNameValue"), function $ElementModel__defaultNameValue(self, _cmd)
{
    objj_msgSend(CPException, "raise:reason:", "Subclass responsibility", "Subclass should implement");
}
,["id"]), new objj_method(sel_getUid("elementName"), function $ElementModel__elementName(self, _cmd)
{
    objj_msgSend(CPException, "raise:reason:", "Subclass responsibility", "Subclass should implement");
}
,["id"]), new objj_method(sel_getUid("propertyValue:be:"), function $ElementModel__propertyValue_be_(self, _cmd, aName, aValue)
{
    if (self._fireNotifications)
    {
        objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementModel").super_class }, "propertyValue:be:", aName, aValue);
        objj_msgSend(self._elementAPI, "set:value:", aName, aValue);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("propertyValueAt:be:"), function $ElementModel__propertyValueAt_be_(self, _cmd, anIndex, aValue)
{
    if (self._fireNotifications)
    {
        objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementModel").super_class }, "propertyValueAt:be:", anIndex, aValue);
        var aName = objj_msgSend(self, "propertyNameAt:", anIndex);
        objj_msgSend(self._elementAPI, "set:value:", aName, aValue);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("deleteFromServer"), function $ElementModel__deleteFromServer(self, _cmd)
{
    objj_msgSend(self._elementAPI, "delete");
}
,["void"]), new objj_method(sel_getUid("addCache"), function $ElementModel__addCache(self, _cmd)
{
    objj_msgSend(self._elementAPI, "addCache");
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementModel__elementId(self, _cmd)
{
    return objj_msgSend(self._elementAPI, "elementId");
}
,["id"]), new objj_method(sel_getUid("elementId:"), function $ElementModel__elementId_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementModel] Element created with id " + anElementId);
    if ([self._delegate != nil])
    {
        objj_msgSend(self._delegate, "elementId:", anElementId);
    }
}
,["void","id"]), new objj_method(sel_getUid("loggerLines:"), function $ElementModel__loggerLines_(self, _cmd, loggerInfo)
{
    if ([self._delegate != nil])
    {
        objj_msgSend(self._delegate, "loggerLines:", loggerInfo);
    }
}
,["void","id"]), new objj_method(sel_getUid("deleted"), function $ElementModel__deleted(self, _cmd)
{
    if ([self._delegate != nil])
    {
        objj_msgSend(self._delegate, "deleted");
    }
}
,["void"]), new objj_method(sel_getUid("api"), function $ElementModel__api(self, _cmd)
{
    return self._elementAPI;
}
,["id"]), new objj_method(sel_getUid("nextInChain:"), function $ElementModel__nextInChain_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementModel] Setting next in chain");
    return objj_msgSend(self._elementAPI, "nextInChain:", anElementId);
}
,["id","id"]), new objj_method(sel_getUid("addAllConnection:"), function $ElementModel__addAllConnection_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementModel] By default ignore");
}
,["id","id"]), new objj_method(sel_getUid("addChoiceConnection:"), function $ElementModel__addChoiceConnection_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementModel] By default ignore");
}
,["id","id"]), new objj_method(sel_getUid("nextInChainId:for:"), function $ElementModel__nextInChainId_for_(self, _cmd, anId, anElementId)
{
    if ([self._delegate != nil])
    {
        objj_msgSend(self._delegate, "nextInChainId:for:", anId, anElementId);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("state"), function $ElementModel__state(self, _cmd)
{
    return objj_msgSend(self._elementAPI, "state");
}
,["id"]), new objj_method(sel_getUid("hasBreakpoint"), function $ElementModel__hasBreakpoint(self, _cmd)
{
    return objj_msgSend(self._elementAPI, "hasBreakpoint");
}
,["id"]), new objj_method(sel_getUid("setBreakpoint:"), function $ElementModel__setBreakpoint_(self, _cmd, aBreakpoint)
{
    objj_msgSend(self._elementAPI, "setBreakpoint:", aBreakpoint);
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("contextId:"), function $ElementModel__contextId_(self, _cmd, aContextId)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ElementModel").super_class }, "new"), "initialize:", aContextId);
}
,["id","id"]), new objj_method(sel_getUid("contextId:elementName:"), function $ElementModel__contextId_elementName_(self, _cmd, aContextId, anElementName)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ElementModel").super_class }, "new"), "initialize:elementName:", aContextId, anElementName);
}
,["id","id","id"]), new objj_method(sel_getUid("contextId:elementId:hasBreakpoint:"), function $ElementModel__contextId_elementId_hasBreakpoint_(self, _cmd, aContextId, anElementId, hasBreakpoint)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ElementModel").super_class }, "new"), "initialize:elementId:hasBreakpoint:", aContextId, anElementId, hasBreakpoint);
}
,["id","id","id","id"])]);
}p;27;Actions/Server/ElementAPI.jt;12854;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;12780;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ElementAPI"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_contextId"), new objj_ivar("_elementId"), new objj_ivar("_state"), new objj_ivar("_delegate"), new objj_ivar("_hasBreakpoint")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithContextId:elementName:"), function $ElementAPI__initWithContextId_elementName_(self, _cmd, contextId, elementName)
{
    self._contextId = contextId;
    self._state = "NOT_IN_SYNC";
    self._hasBreakpoint = NO;
    objj_msgSend(self, "createElement:", elementName);
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("initWithContextId:elementId:hasBreakpoint:"), function $ElementAPI__initWithContextId_elementId_hasBreakpoint_(self, _cmd, contextId, elementId, hasBreakpoint)
{
    self._contextId = contextId;
    self._elementId = elementId;
    self._state = "SYNCED";
    self._hasBreakpoint = hasBreakpoint;
    return self;
}
,["id","id","id","id"]), new objj_method(sel_getUid("delegate:"), function $ElementAPI__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
}
,["void","id"]), new objj_method(sel_getUid("state"), function $ElementAPI__state(self, _cmd)
{
    return self._state;
}
,["id"]), new objj_method(sel_getUid("hasBreakpoint"), function $ElementAPI__hasBreakpoint(self, _cmd)
{
    return self._hasBreakpoint;
}
,["id"]), new objj_method(sel_getUid("contextId"), function $ElementAPI__contextId(self, _cmd)
{
    return self._contextId;
}
,["id"]), new objj_method(sel_getUid("elementId"), function $ElementAPI__elementId(self, _cmd)
{
    return self._elementId;
}
,["id"]), new objj_method(sel_getUid("createElement:"), function $ElementAPI__createElement_(self, _cmd, elementName)
{
    CPLog.debug("Creating element with name: " + elementName);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + elementName})).done(function(result)
    {
        self._elementId = result.id;
        self._state = "SYNCED";
        CPLog.debug("[ElementAPI] Element created with id " + self._elementId);
        objj_msgSend(self._delegate, "elementId:", self._elementId);
        objj_msgSend(self, "changed");
    });
    objj_msgSend(self, "changed");
    return self;
}
,["id","id"]), new objj_method(sel_getUid("addProfiler"), function $ElementAPI__addProfiler(self, _cmd)
{
    CPLog.debug("Adding profiler to element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "PUT", url: "../service/element/" + self._contextId + "/" + self._elementId + "/profiler/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Profiler added to " + self._elementId);
    });
}
,["id"]), new objj_method(sel_getUid("addLazyComputedCache:ttl:keyExpression:cacheExpressions:"), function $ElementAPI__addLazyComputedCache_ttl_keyExpression_cacheExpressions_(self, _cmd, memcachedURL, ttl, keyExpression, cacheExpressions)
{
    CPLog.debug("Adding cache to element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/cache/computed/" + objj_msgSend(Actions, "deploymentId"), data: {"memcachedURL": memcachedURL, "ttl": ttl, "keyExpression": keyExpression, "cacheExpressions": cacheExpressions}})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Cache added to " + self._elementId);
    });
}
,["id","id","id","id","id"]), new objj_method(sel_getUid("addLazyAutorefreshableCache:oldCacheEntryInMills:keyExpression:cacheExpressions:"), function $ElementAPI__addLazyAutorefreshableCache_oldCacheEntryInMills_keyExpression_cacheExpressions_(self, _cmd, memcachedURL, oldCacheEntryInMills, keyExpression, cacheExpressions)
{
    CPLog.debug("Adding cache to element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/cache/autorefreshable/" + objj_msgSend(Actions, "deploymentId"), data: {"memcachedURL": memcachedURL, "oldCacheEntryInMills": oldCacheEntryInMills, "keyExpression": keyExpression, "cacheExpressions": cacheExpressions}})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Cache added to " + self._elementId);
    });
}
,["id","id","id","id","id"]), new objj_method(sel_getUid("removeProfiler"), function $ElementAPI__removeProfiler(self, _cmd)
{
    CPLog.debug("Removing profiler to element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "DELETE", url: "../service/element/" + self._contextId + "/" + self._elementId + "/profiler/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Profiler deleted to " + self._elementId);
    });
}
,["id"]), new objj_method(sel_getUid("addLogger:afterExpression:beforeConditionalExpressionValue:afterConditionalExpressionValue:"), function $ElementAPI__addLogger_afterExpression_beforeConditionalExpressionValue_afterConditionalExpressionValue_(self, _cmd, beforeExpression, afterExpression, beforeConditionalExpressionValue, afterConditionalExpressionValue)
{
    CPLog.debug("Adding logger to element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    var isBeforeConditional = beforeConditionalExpressionValue != nil;
    var isAfterConditional = afterConditionalExpressionValue != nil;
    ($.ajax({type: "POST", data: {"beforeExpression": beforeExpression, "afterExpression": afterExpression, "isBeforeConditional": isBeforeConditional, "isAfterConditional": isAfterConditional, "beforeConditionalExpressionValue": beforeConditionalExpressionValue, "afterConditionalExpressionValue": afterConditionalExpressionValue}, url: "../service/element/" + self._contextId + "/" + self._elementId + "/logger/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Logger added to " + self._elementId);
    });
}
,["id","id","id","id","id"]), new objj_method(sel_getUid("removeLogger"), function $ElementAPI__removeLogger(self, _cmd)
{
    CPLog.debug("Removing logger of element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "DELETE", url: "../service/element/" + self._contextId + "/" + self._elementId + "/logger/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Logger removed to " + self._elementId);
    });
}
,["id"]), new objj_method(sel_getUid("getLogger"), function $ElementAPI__getLogger(self, _cmd)
{
    CPLog.debug("Getting logger of element " + self._elementId);
    ($.ajax({type: "GET", url: "../service/element/" + self._contextId + "/" + self._elementId + "/logger/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        objj_msgSend(self._delegate, "loggerLines:", result);
        CPLog.debug("Logger removed to " + self._elementId);
    });
}
,["id"]), new objj_method(sel_getUid("delete"), function $ElementAPI__delete(self, _cmd)
{
    CPLog.debug("Deleting element " + self._elementId);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "DELETE", url: "../service/element/" + self._contextId + "/" + self._elementId})).done(function(result)
    {
        self._state = "DELETED";
        objj_msgSend(self, "changed");
        objj_msgSend(self._delegate, "deleted");
        objj_msgSend(objj_msgSend(Actions, "drawing"), "removeFigures:", result.deleted.deleted);
        CPLog.debug("Element deleted " + self._elementId);
    });
    objj_msgSend(self, "changed");
    return self;
}
,["id"]), new objj_method(sel_getUid("set:value:"), function $ElementAPI__set_value_(self, _cmd, propertyName, propertyValue)
{
    CPLog.debug("Setting property of " + self._elementId + " property " + propertyName + " value " + propertyValue);
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/property/" + propertyName, data: {"propertyValue": propertyValue}})).done(function(result)
    {
        self._state = "NOT_IN_SYNC";
        objj_msgSend(self, "changed");
        CPLog.debug("Element property set of " + self._elementId + " property " + propertyName + " value " + propertyValue);
    });
    objj_msgSend(self, "changed");
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("addConnectionType:to:identificable:"), function $ElementAPI__addConnectionType_to_identificable_(self, _cmd, aConnectionType, anElementId, anIdentificable)
{
    CPLog.debug("[ElementAPI] Adding connection from " + self._elementId + " to " + anElementId + " of type " + aConnectionType);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/" + anElementId + "/connection/" + aConnectionType})).done(function(result)
    {
        self._state = "SYNCED";
        var id = result.id;
        objj_msgSend(anIdentificable, "id:", id);
        objj_msgSend(self, "changed");
        CPLog.debug("[ElementAPI] Added connection from " + self._elementId + " to " + anElementId + " of type " + aConnectionType);
    });
    objj_msgSend(self, "changed");
    return self;
}
,["id","id","id","id"]), new objj_method(sel_getUid("setBreakpoint:"), function $ElementAPI__setBreakpoint_(self, _cmd, aBreakpoint)
{
    CPLog.debug("Setting breakpoint of " + self._elementId + " is " + aBreakpoint);
    self._state = "NOT_IN_SYNC";
    ($.ajax({type: "PUT", url: "../service/element/" + self._contextId + "/" + self._elementId + "/breakpoint/" + aBreakpoint})).done(function(result)
    {
        self._state = "SYNCED";
        self._hasBreakpoint = aBreakpoint;
        objj_msgSend(self, "changed");
        CPLog.debug("Set breakpoint of " + self._elementId + " next is " + aBreakpoint);
    });
    objj_msgSend(self, "changed");
    return self;
}
,["id","id"]), new objj_method(sel_getUid("perform:arguments:"), function $ElementAPI__perform_arguments_(self, _cmd, actionName, anArrayOfArguments)
{
    CPLog.debug("Performing action of " + self._elementId + " action " + actionName + " arguments " + anArrayOfArguments);
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/action/" + actionName})).done(function(result)
    {
        CPLog.debug("Action performed of " + self._elementId + " action " + actionName + " arguments " + anArrayOfArguments);
    });
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("sync"), function $ElementAPI__sync(self, _cmd)
{
    CPLog.debug("Syncing element " + self._elementId);
    self._state = "SYNCING";
    objj_msgSend(self, "changed");
    ($.ajax({type: "POST", url: "../service/element/" + self._contextId + "/" + self._elementId + "/sync"})).done(function(result)
    {
        self._state = "SYNCED";
        objj_msgSend(self, "changed");
        CPLog.debug("Element synced " + self._elementId);
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("changed"), function $ElementAPI__changed(self, _cmd)
{
    if (self._elementId != nil && self._delegate != nil)
    {
        CPLog.debug("Object " + self._elementId + " changed");
        objj_msgSend(self._delegate, "changed");
        if (objj_msgSend(self._state, "isEqualToString:", "NOT_IN_SYNC"))
        {
            var _timer = objj_msgSend(CPTimer, "scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:", 2, self, sel_getUid("sync"), nil, NO);
        }
    }
}
,["id"]), new objj_method(sel_getUid("id:"), function $ElementAPI__id_(self, _cmd, anID)
{
    self._elementId = anID;
}
,["id","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newIn:elementName:"), function $ElementAPI__newIn_elementName_(self, _cmd, contextId, elementName)
{
    return objj_msgSend(objj_msgSend(self, "new"), "initWithContextId:elementName:", contextId, elementName);
}
,["ElementAPI","id","id"]), new objj_method(sel_getUid("newIn:elementId:hasBreakpoint:"), function $ElementAPI__newIn_elementId_hasBreakpoint_(self, _cmd, contextId, elementId, hasBreakpoint)
{
    return objj_msgSend(objj_msgSend(self, "new"), "initWithContextId:elementId:hasBreakpoint:", contextId, elementId, hasBreakpoint);
}
,["ElementAPI","id","id","id"])]);
}p;34;Actions/Figure/ElementConnection.jt;2343;@STATIC;1.0;t;2324;{var the_class = objj_allocateClassPair(Connection, "ElementConnection"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithSource:target:points:"), function $ElementConnection__initWithSource_target_points_(self, _cmd, aSourceFigure, aTargetFigure, anArrayOfPoints)
{
    CPLog.debug("[ElementConnection] " + JSON.stringify(anArrayOfPoints));
    self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementConnection").super_class }, "initWithSource:target:points:", aSourceFigure, aTargetFigure, anArrayOfPoints);
    objj_msgSend(objj_msgSend(Actions, "mode"), "createElementConnectionMenu:", self);
    return self;
}
,["id","Figure","Figure","id"]), new objj_method(sel_getUid("elementName"), function $ElementConnection__elementName(self, _cmd)
{
    return self._elementName;
}
,["id"]), new objj_method(sel_getUid("deleteFromServer"), function $ElementConnection__deleteFromServer(self, _cmd)
{
    objj_msgSend(objj_msgSend(self, "model"), "deleteFromServer");
}
,["void"]), new objj_method(sel_getUid("deleted"), function $ElementConnection__deleted(self, _cmd)
{
    objj_msgSend(self, "removeMyself");
}
,["void"]), new objj_method(sel_getUid("addLazyAutorefreshableCache"), function $ElementConnection__addLazyAutorefreshableCache(self, _cmd)
{
    var myController = objj_msgSend(objj_msgSend(ElementAPIWindowController, "alloc"), "initWithWindowCibName:", "AddLazyAutorefreshableCacheWindow");
    objj_msgSend(myController, "showWindow:", nil);
    objj_msgSend(myController, "elementAPI:", objj_msgSend(objj_msgSend(self, "model"), "api"));
}
,["void"]), new objj_method(sel_getUid("addLazyComputedCache"), function $ElementConnection__addLazyComputedCache(self, _cmd)
{
    var myController = objj_msgSend(objj_msgSend(ElementAPIWindowController, "alloc"), "initWithWindowCibName:", "AddLazyComputedCacheWindow");
    objj_msgSend(myController, "showWindow:", nil);
    objj_msgSend(myController, "elementAPI:", objj_msgSend(objj_msgSend(self, "model"), "api"));
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementConnection__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"])]);
}p;36;Actions/Command/SaveActionsCommand.jt;2051;@STATIC;1.0;t;2032;{var the_class = objj_allocateClassPair(Command, "SaveActionsCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $SaveActionsCommand__execute(self, _cmd)
{
    var json = {"positions": [], "connections": []};
    var contextId = objj_msgSend(self._drawing, "contextId");
    var figures = objj_msgSend(self._drawing, "figures");
    for (var i = 0; i < objj_msgSend(figures, "count"); i++)
    {
        var figure = objj_msgSend(figures, "objectAtIndex:", i);
        if (objj_msgSend(figure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")))
        {
            var frameOrigin = objj_msgSend(figure, "frameOrigin");
            var id = objj_msgSend(objj_msgSend(figure, "model"), "elementId");
            var point = {"x": frameOrigin.x, "y": frameOrigin.y, "id": id};
            json.positions.push(point);
        }
        else if (objj_msgSend(figure, "isKindOfClass:", objj_msgSend(Connection, "class")))
        {
            var id = objj_msgSend(objj_msgSend(figure, "model"), "elementId");
            var sourceId = objj_msgSend(objj_msgSend(objj_msgSend(figure, "source"), "model"), "elementId");
            var connection = {"id": id, "sourceId": sourceId, points: []};
            var points = objj_msgSend(figure, "points");
            for (var j = 0; j < objj_msgSend(points, "count"); j++)
            {
                var cupPoint = objj_msgSend(points, "objectAtIndex:", j);
                var point = {"x": cupPoint.x, "y": cupPoint.y};
                connection.points.push(point);
            }
            json.connections.push(connection);
        }
    }
    var jsonAsString = JSON.stringify(json);
    var saveUrl = ($.url()).param('saveUrl');
    CPLog.debug("Saving context " + contextId);
    ($.ajax({type: "POST", url: saveUrl, data: {"name": contextId, "json": jsonAsString}})).done(function(result)
    {
        CPLog.debug("Context saved " + contextId);
    });
}
,["void"])]);
}p;33;Actions/Command/AbstractCommand.jt;2369;@STATIC;1.0;t;2350;{var the_class = objj_allocateClassPair(Command, "AbstractCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $AbstractCommand__execute(self, _cmd)
{
    var contextId = objj_msgSend(self._drawing, "contextId");
    var selectedFigures = objj_msgSend(objj_msgSend(self._drawing, "tool"), "selectedFigures");
    var selectedUrns = new Array();
    var j = 0;
    for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++)
    {
        var figure = objj_msgSend(selectedFigures, "objectAtIndex:", i);
        if (objj_msgSend(figure, "respondsToSelector:", sel_getUid("elementId")))
        {
            selectedUrns[j] = objj_msgSend(figure, "elementId");
            j++;
        }
    }
    var name = window.prompt('Abstraction name');
    var urns = selectedUrns.join();
    CPLog.debug("Abstracting elements");
    ($.ajax({type: "POST", url: "../service/context/" + contextId + "/abstract", data: {"name": name, "displayName": name, "elementUrns": urns}})).done(function(result)
    {
        objj_msgSend(self, "processResult:selectedUrns:", result.definition, selectedUrns);
        CPLog.debug("DONE Abstracting elements");
    });
}
,["void"]), new objj_method(sel_getUid("processResult:selectedUrns:"), function $AbstractCommand__processResult_selectedUrns_(self, _cmd, aNewDefinition, selectedUrns)
{
    var toolbox = objj_msgSend(self._drawing, "toolboxWithId:", "user-defined");
    var generator = objj_msgSend(self._drawing, "generator");
    objj_msgSend(generator, "addDefinition:", aNewDefinition);
    var tool = objj_msgSend(CreateProcessorTool, "drawing:elementName:generator:", self._drawing, aNewDefinition.name, generator);
    objj_msgSend(toolbox, "addTool:withTitle:image:", tool, aNewDefinition.displayName, aNewDefinition.icon);
    var startingId = aNewDefinition.startingDefinitionId;
    var startingFigure = objj_msgSend(self._drawing, "processorFor:", startingId);
    var topLeft = objj_msgSend(startingFigure, "topLeft");
    objj_msgSend(self._drawing, "removeFigures:", selectedUrns);
    var newFigure = objj_msgSend(CreateProcessorTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", topLeft, self._drawing, aNewDefinition.name, NO, nil, nil, tool);
}
,["void","id","id"])]);
}p;37;Actions/Model/DynamicModelGenerator.jt;4436;@STATIC;1.0;t;4417;{var the_class = objj_allocateClassPair(CPObject, "DynamicModelGenerator"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_definitions")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DynamicModelGenerator__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("DynamicModelGenerator").super_class }, "init");
    self._definitions = objj_msgSend(CPDictionary, "dictionary");
    return self;
}
,["id"]), new objj_method(sel_getUid("addDefinitions:"), function $DynamicModelGenerator__addDefinitions_(self, _cmd, aListOfDefinitions)
{
    for (var j = 0; j < aListOfDefinitions.length; j++)
    {
        var element = aListOfDefinitions[j];
        objj_msgSend(self, "addDefinition:", element);
    }
}
,["void","id"]), new objj_method(sel_getUid("addDefinition:"), function $DynamicModelGenerator__addDefinition_(self, _cmd, element)
{
    var key = element.name;
    objj_msgSend(self._definitions, "setObject:forKey:", element, key);
}
,["void","id"]), new objj_method(sel_getUid("modelFor:elementId:contextId:initialProperties:hasBreakpoint:"), function $DynamicModelGenerator__modelFor_elementId_contextId_initialProperties_hasBreakpoint_(self, _cmd, anElementName, elementId, contextId, properties, hasBreakpoint)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    if (definition != nil)
    {
        var displayName = definition.displayName;
        var modelClass = objj_msgSend(DynamicElementModel, "class");
        if (definition.type == "MESSAGE_SOURCE")
        {
            modelClass = objj_msgSend(DynamicMessageSourceModel, "class");
        }
        if (elementId == nil)
        {
            newModel = objj_msgSend(modelClass, "contextId:elementName:", contextId, anElementName);
        }
        else
        {
            newModel = objj_msgSend(modelClass, "contextId:elementId:hasBreakpoint:", contextId, elementId, hasBreakpoint);
        }
        objj_msgSend(newModel, "elementName:", anElementName);
        objj_msgSend(newModel, "defaultNameValue:", displayName);
        var modelClassDefinition = definition.properties;
        for (var i = 0; i < modelClassDefinition.length; i++)
        {
            var property = modelClassDefinition[i];
            objj_msgSend(newModel, "addProperty:displayName:value:", property.name, property.displayName, property.defaultValue);
        }
        if (properties != nil)
        {
            CPLog.debug("Setting initial properties");
            objj_msgSend(newModel, "initializeWithProperties:", properties);
            objj_msgSend(newModel, "changed");
            CPLog.debug("Initial properties set");
        }
        return newModel;
    }
    else
    {
        return nil;
    }
}
,["ElementModel","id","id","id","id","id"]), new objj_method(sel_getUid("icon:"), function $DynamicModelGenerator__icon_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.icon;
}
,["id","id"]), new objj_method(sel_getUid("displayName:"), function $DynamicModelGenerator__displayName_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.displayName;
}
,["id","id"]), new objj_method(sel_getUid("isProcessor:"), function $DynamicModelGenerator__isProcessor_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "PROCESSOR";
}
,["id","id"]), new objj_method(sel_getUid("isMessageSource:"), function $DynamicModelGenerator__isMessageSource_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "MESSAGE_SOURCE";
}
,["id","id"]), new objj_method(sel_getUid("isRouter:"), function $DynamicModelGenerator__isRouter_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "ROUTER";
}
,["id","id"]), new objj_method(sel_getUid("color:"), function $DynamicModelGenerator__color_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.color;
}
,["id","id"])]);
}p;35;Actions/Model/DynamicElementModel.jt;934;@STATIC;1.0;t;916;{var the_class = objj_allocateClassPair(ElementModel, "DynamicElementModel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_defaultName")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("elementName"), function $DynamicElementModel__elementName(self, _cmd)
{
    return self._elementName;
}
,["id"]), new objj_method(sel_getUid("elementName:"), function $DynamicElementModel__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"]), new objj_method(sel_getUid("defaultNameValue"), function $DynamicElementModel__defaultNameValue(self, _cmd)
{
    return self._defaultName;
}
,["id"]), new objj_method(sel_getUid("defaultNameValue:"), function $DynamicElementModel__defaultNameValue_(self, _cmd, aDefaultValue)
{
    self._defaultName = aDefaultValue;
}
,["void","id"])]);
}p;41;Actions/Model/DynamicMessageSourceModel.jt;1623;@STATIC;1.0;t;1604;{var the_class = objj_allocateClassPair(ElementModel, "DynamicMessageSourceModel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_defaultName"), new objj_ivar("_state")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DynamicMessageSourceModel__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("DynamicMessageSourceModel").super_class }, "init");
    self._state = "STOP";
    return self;
}
,["id"]), new objj_method(sel_getUid("elementName"), function $DynamicMessageSourceModel__elementName(self, _cmd)
{
    return self._elementName;
}
,["id"]), new objj_method(sel_getUid("elementName:"), function $DynamicMessageSourceModel__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"]), new objj_method(sel_getUid("defaultNameValue"), function $DynamicMessageSourceModel__defaultNameValue(self, _cmd)
{
    return self._defaultName;
}
,["id"]), new objj_method(sel_getUid("defaultNameValue:"), function $DynamicMessageSourceModel__defaultNameValue_(self, _cmd, aDefaultValue)
{
    self._defaultName = aDefaultValue;
}
,["void","id"]), new objj_method(sel_getUid("messageSourceState"), function $DynamicMessageSourceModel__messageSourceState(self, _cmd)
{
    return self._state;
}
,["id"]), new objj_method(sel_getUid("messageSourceState:"), function $DynamicMessageSourceModel__messageSourceState_(self, _cmd, anState)
{
    self._state = anState;
    objj_msgSend(self, "changed");
}
,["void","id"])]);
}p;27;Actions/ActionsController.jt;5752;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;5679;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ActionsController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_drawing"), new objj_ivar("_interpreters"), new objj_ivar("_interpretersControllers"), new objj_ivar("_interpretersDelegates")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initialize:"), function $ActionsController__initialize_(self, _cmd, aDrawing)
{
    self._drawing = aDrawing;
    self._interpreters = objj_msgSend(CPDictionary, "dictionary");
    self._interpretersControllers = objj_msgSend(CPDictionary, "dictionary");
    self._interpretersDelegates = objj_msgSend(CPDictionary, "dictionary");
    objj_msgSend(self, "setupNotifications");
    return self;
}
,["id","id"]), new objj_method(sel_getUid("initiateMessageSendFrom:"), function $ActionsController__initiateMessageSendFrom_(self, _cmd, aProcessorFigure)
{
    var myController = objj_msgSend(objj_msgSend(InterpreterAPIWindowController, "alloc"), "initWithWindowCibName:", "SendMessageWindow");
    objj_msgSend(myController, "showWindow:", nil);
    var interpreterAPI = objj_msgSend(InterpreterAPI, "newIn:elementId:", objj_msgSend(self._drawing, "contextId"), objj_msgSend(aProcessorFigure, "elementId"));
    objj_msgSend(interpreterAPI, "delegate:", self);
    objj_msgSend(interpreterAPI, "createInterpreter");
    objj_msgSend(myController, "interpreterAPI:", interpreterAPI);
}
,["void","id"]), new objj_method(sel_getUid("createInterpreter:with:"), function $ActionsController__createInterpreter_with_(self, _cmd, anInterpreterId, anInterpreterAPI)
{
    objj_msgSend(self._interpreters, "setObject:forKey:", anInterpreterAPI, anInterpreterId);
}
,["void","id","id"]), new objj_method(sel_getUid("setStatus:"), function $ActionsController__setStatus_(self, _cmd, aMessage)
{
}
,["void","id"]), new objj_method(sel_getUid("receiveMessage:"), function $ActionsController__receiveMessage_(self, _cmd, aMessage)
{
    objj_msgSend(self, "setStatus:", aMessage);
}
,["void","id"]), new objj_method(sel_getUid("setupNotifications"), function $ActionsController__setupNotifications(self, _cmd)
{
    objj_msgSend(self, "setStatus:", "Connecting...");
    var socket = $.atmosphere;
    var request = {url: '../atmo/' + objj_msgSend(self._drawing, "contextId"), contentType: "application/json", logLevel: 'debug', transport: 'websocket', fallbackTransport: 'long-polling'};
    request.onOpen = function(response)
    {
        objj_msgSend(self, "setStatus:", "Connected");
    };
    request.onMessage = function(response)
    {
        var message = $.parseJSON(response.responseBody);
        if ([message.interpreterId != nil])
        {
            objj_msgSend(self, "processDebugMessage:", message);
        }
        else
        {
            objj_msgSend(self, "receiveMessage:", message);
        }
    };
    request.onError = function(response)
    {
        objj_msgSend(self, "receiveMessage:", "Error connecting to the server");
    };
    var subSocket = socket.subscribe(request);
}
,["void"]), new objj_method(sel_getUid("processDebugMessage:"), function $ActionsController__processDebugMessage_(self, _cmd, aMessage)
{
    var id = aMessage.interpreterId + "#" + aMessage.threadId;
    var interpreterAPI = objj_msgSend(self._interpreters, "objectForKey:", aMessage.interpreterId);
    var controller = objj_msgSend(self._interpretersControllers, "objectForKey:", id);
    if (controller == nil)
    {
        controller = objj_msgSend(objj_msgSend(DebuggerWindowController, "alloc"), "initWithWindowCibName:", "DebuggerWindow");
        objj_msgSend(controller, "id:", id);
        objj_msgSend(controller, "interpreterAPI:", interpreterAPI);
        objj_msgSend(self._interpretersControllers, "setObject:forKey:", controller, id);
    }
    objj_msgSend(controller, "showWindow:", nil);
    var delegate = objj_msgSend(self._interpretersDelegates, "objectForKey:", id);
    objj_msgSend(delegate, "process:", aMessage);
    var eventType = aMessage.eventType;
    var interpreterId = aMessage.interpreterId;
    if (objj_msgSend(eventType, "isEqualToString:", "uncaught-exception"))
    {
        objj_msgSend(self._interpreters, "removeObjectForKey:", interpreterId);
        objj_msgSend(self._interpretersControllers, "removeObjectForKey:", id);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "finish-interpretation"))
    {
        objj_msgSend(self._interpreters, "removeObjectForKey:", interpreterId);
        objj_msgSend(self._interpretersControllers, "removeObjectForKey:", id);
    }
}
,["void","id"]), new objj_method(sel_getUid("register:asDebugDelegateFor:"), function $ActionsController__register_asDebugDelegateFor_(self, _cmd, aDebuggerDelegate, anID)
{
    objj_msgSend(self._interpretersDelegates, "setObject:forKey:", aDebuggerDelegate, anID);
}
,["void","id","id"]), new objj_method(sel_getUid("evaluationResult:currentMessage:"), function $ActionsController__evaluationResult_currentMessage_(self, _cmd, result, aMessage)
{
    var id = aMessage.interpreterId + "#" + aMessage.threadId;
    var delegate = objj_msgSend(self._interpretersDelegates, "objectForKey:", id);
    objj_msgSend(delegate, "evaluationResult:", result);
}
,["void","id","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:"), function $ActionsController__drawing_(self, _cmd, aDrawing)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ActionsController").super_class }, "new"), "initialize:", aDrawing);
}
,["id","id"])]);
}p;51;Actions/Controller/InterpreterAPIWindowController.jt;704;@STATIC;1.0;I;21;Foundation/CPObject.jt;660;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "InterpreterAPIWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_interpreterAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("interpreterAPI"), function $InterpreterAPIWindowController__interpreterAPI(self, _cmd)
{
    return self._interpreterAPI;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI:"), function $InterpreterAPIWindowController__interpreterAPI_(self, _cmd, anInterpreterAPI)
{
    self._interpreterAPI = anInterpreterAPI;
}
,["void","id"])]);
}p;31;Actions/Server/InterpreterAPI.jt;4630;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;4557;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "InterpreterAPI"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_contextId"), new objj_ivar("_elementId"), new objj_ivar("_interpreterId"), new objj_ivar("_threadId"), new objj_ivar("_delegate")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithContextId:elementId:"), function $InterpreterAPI__initWithContextId_elementId_(self, _cmd, contextId, elementId)
{
    self._contextId = contextId;
    self._elementId = elementId;
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("interpreterId:threadId:"), function $InterpreterAPI__interpreterId_threadId_(self, _cmd, anInterpreterId, aThreadId)
{
    self._interpreterId = anInterpreterId;
    self._threadId = aThreadId;
}
,["void","id","id"]), new objj_method(sel_getUid("threadId"), function $InterpreterAPI__threadId(self, _cmd)
{
    return self._threadId;
}
,["id"]), new objj_method(sel_getUid("createInterpreter"), function $InterpreterAPI__createInterpreter(self, _cmd)
{
    CPLog.debug("Creating interpreter");
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._contextId + "/create", data: {"initialProcessorId": self._elementId}})).done(function(result)
    {
        self._interpreterId = result.id;
        self._threadId = "1";
        CPLog.debug("Interpreter created with id:" + self._interpreterId);
        objj_msgSend(self._delegate, "createInterpreter:with:", self._interpreterId, self);
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("run:"), function $InterpreterAPI__run_(self, _cmd, initialMessage)
{
    CPLog.debug("Running interpreter " + self._interpreterId);
    var message = JSON.stringify(initialMessage);
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._interpreterId + "/run", data: {"initialMessage": message}})).done(function(result)
    {
        CPLog.debug("Interpreter run " + self._interpreterId);
    });
    return self;
}
,["id","id"]), new objj_method(sel_getUid("debug:"), function $InterpreterAPI__debug_(self, _cmd, initialMessage)
{
    CPLog.debug("Debugging interpreter " + self._interpreterId);
    var message = JSON.stringify(initialMessage);
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._interpreterId + "/debug", data: {"initialMessage": message}})).done(function(result)
    {
        CPLog.debug("Interpreter debugged " + self._interpreterId);
    });
    return self;
}
,["id","id"]), new objj_method(sel_getUid("step"), function $InterpreterAPI__step(self, _cmd)
{
    CPLog.debug("Stepping interpreter " + self._interpreterId);
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._interpreterId + "/" + self._threadId + "/step"})).done(function(result)
    {
        CPLog.debug("Thread " + self._threadId + " of interpreter " + self._interpreterId + " has stepped");
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("resume"), function $InterpreterAPI__resume(self, _cmd)
{
    CPLog.debug("Resuming interpreter " + self._interpreterId);
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._interpreterId + "/" + self._threadId + "/resume"})).done(function(result)
    {
        CPLog.debug("Thread " + self._threadId + " of interpreter " + self._interpreterId + " has resumed");
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("evaluate:"), function $InterpreterAPI__evaluate_(self, _cmd, anExpression)
{
    CPLog.debug("Evaluating " + anExpression);
    ($.ajax({type: "POST", url: "../service/interpreter/" + self._interpreterId + "/" + self._threadId + "/evaluate", data: {"expression": anExpression}})).done(function(r)
    {
        var result = $.parseJSON(r.result);
        objj_msgSend(self._delegate, "evaluationResult:currentMessage:", result.evaluationResult, result);
        CPLog.debug("Evaluated expression in " + self._interpreterId + " thread " + self._threadId);
    });
    return self;
}
,["id","id"]), new objj_method(sel_getUid("delegate:"), function $InterpreterAPI__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newIn:elementId:"), function $InterpreterAPI__newIn_elementId_(self, _cmd, contextId, elementId)
{
    return objj_msgSend(objj_msgSend(self, "new"), "initWithContextId:elementId:", contextId, elementId);
}
,["InterpreterAPI","id","id"])]);
}p;45;Actions/Controller/DebuggerWindowController.jt;963;@STATIC;1.0;I;21;Foundation/CPObject.jt;919;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "DebuggerWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_id"), new objj_ivar("_interpreterAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("interpreterAPI"), function $DebuggerWindowController__interpreterAPI(self, _cmd)
{
    return self._interpreterAPI;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI:"), function $DebuggerWindowController__interpreterAPI_(self, _cmd, anInterpreterAPI)
{
    self._interpreterAPI = anInterpreterAPI;
}
,["void","id"]), new objj_method(sel_getUid("id:"), function $DebuggerWindowController__id_(self, _cmd, anID)
{
    self._id = anID;
}
,["void","id"]), new objj_method(sel_getUid("id"), function $DebuggerWindowController__id(self, _cmd)
{
    return self._id;
}
,["id"])]);
}p;24;Actions/ActionsDrawing.jt;9077;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;9004;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(Drawing, "ActionsDrawing"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_contextAPI"), new objj_ivar("_libraryAPI"), new objj_ivar("_timer"), new objj_ivar("_dirty"), new objj_ivar("_generator"), new objj_ivar("_statusFigure"), new objj_ivar("_openInNewWindowFigure"), new objj_ivar("_processorFigures"), new objj_ivar("_toolboxes")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWith:"), function $ActionsDrawing__initWith_(self, _cmd, aContextId)
{
    self._dirty = false;
    self._processorFigures = objj_msgSend(CPDictionary, "dictionary");
    self._toolboxes = objj_msgSend(CPDictionary, "dictionary");
    var isDevelopment = objj_msgSend(Actions, "isDevelopment");
    self._statusFigure = objj_msgSend(LabelFigure, "initializeWithText:at:", "", CGPointMake(0, 20));
    self._openInNewWindowFigure = objj_msgSend(LinkFigure, "initializeWithText:at:", "", CGPointMake(0, 0));
    objj_msgSend(self._statusFigure, "setHidden:", !isDevelopment);
    objj_msgSend(self, "addFigure:", self._statusFigure);
    objj_msgSend(self, "addFigure:", self._openInNewWindowFigure);
    if (aContextId == nil)
    {
        self._contextAPI = objj_msgSend(ContextAPI, "new");
    }
    else
    {
        self._contextAPI = objj_msgSend(ContextAPI, "newWith:", aContextId);
        objj_msgSend(self, "setupNotifications");
    }
    objj_msgSend(self._contextAPI, "delegate:", self);
    objj_msgSend(objj_msgSend(self, "model"), "propertyValue:be:", "showGrid", YES);
    objj_msgSend(objj_msgSend(self, "model"), "propertyValue:be:", "gridSize", 25);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("timer:"), function $ActionsDrawing__timer_(self, _cmd, aTimer)
{
    self._timer = aTimer;
}
,["void","id"]), new objj_method(sel_getUid("removeFigures:"), function $ActionsDrawing__removeFigures_(self, _cmd, aListOfIds)
{
    var figures = objj_msgSend(self, "figures");
    for (var i = objj_msgSend(figures, "count") - 1; i >= 0; i--)
    {
        var figure = objj_msgSend(figures, "objectAtIndex:", i);
        if (objj_msgSend(figure, "respondsToSelector:", sel_getUid("elementId")))
        {
            var elementId = objj_msgSend(figure, "elementId");
            if ($.inArray(elementId, aListOfIds) != -1)
            {
                objj_msgSend(figure, "removeMyself");
            }
        }
    }
}
,["void","id"]), new objj_method(sel_getUid("addProcessor:"), function $ActionsDrawing__addProcessor_(self, _cmd, aProcessorFigure)
{
    CPLog.debug("Adding processor");
    objj_msgSend(self, "addFigure:", aProcessorFigure);
    objj_msgSend(aProcessorFigure, "delegate:", self);
    objj_msgSend(objj_msgSend(Actions, "mode"), "postAddProcessor:", aProcessorFigure);
    self._dirty = true;
}
,["void","id"]), new objj_method(sel_getUid("addToolbox:withId:"), function $ActionsDrawing__addToolbox_withId_(self, _cmd, aToolboxFigure, anId)
{
    objj_msgSend(self._toolboxes, "setObject:forKey:", aToolboxFigure, anId);
    objj_msgSend(self, "addFigure:", aToolboxFigure);
}
,["void","id","id"]), new objj_method(sel_getUid("toolboxWithId:"), function $ActionsDrawing__toolboxWithId_(self, _cmd, anId)
{
    return objj_msgSend(self._toolboxes, "objectForKey:", anId);
}
,["id","id"]), new objj_method(sel_getUid("addMessageSource:"), function $ActionsDrawing__addMessageSource_(self, _cmd, aMessageSourceFigure)
{
    CPLog.debug("Adding message source");
    objj_msgSend(self, "addFigure:", aMessageSourceFigure);
    objj_msgSend(aMessageSourceFigure, "delegate:", self);
    self._dirty = true;
}
,["void","id"]), new objj_method(sel_getUid("willRemoveSubview:"), function $ActionsDrawing__willRemoveSubview_(self, _cmd, figure)
{
    if (objj_msgSend(figure, "isKindOfClass:", objj_msgSend(ProcessorFigure, "class")))
    {
        objj_msgSend(figure, "deleteFromServer");
    }
}
,["void","id"]), new objj_method(sel_getUid("sync"), function $ActionsDrawing__sync(self, _cmd)
{
    self._dirty = false;
    objj_msgSend(self._contextAPI, "sync");
}
,["void"]), new objj_method(sel_getUid("contextId"), function $ActionsDrawing__contextId(self, _cmd)
{
    return objj_msgSend(self._contextAPI, "contextId");
}
,["id"]), new objj_method(sel_getUid("update:"), function $ActionsDrawing__update_(self, _cmd, aTimer)
{
    objj_msgSend(self, "sync");
}
,["void","CPTimer"]), new objj_method(sel_getUid("contextCreated:"), function $ActionsDrawing__contextCreated_(self, _cmd, aContextId)
{
    var host = location.host;
    var path = location.pathname;
    var url = "http://" + host + path + "?contextId=" + aContextId;
    objj_msgSend(self._openInNewWindowFigure, "setText:", url);
    objj_msgSend(self, "setupNotifications");
}
,["void","id"]), new objj_method(sel_getUid("updateProfilingInfo"), function $ActionsDrawing__updateProfilingInfo(self, _cmd)
{
    objj_msgSend(self._contextAPI, "profilingInfo");
}
,["void"]), new objj_method(sel_getUid("profilingInfo:"), function $ActionsDrawing__profilingInfo_(self, _cmd, aProfilingInfo)
{
    var servers = aProfilingInfo.profilingInfo.servers;
    for (var i = 0; i < servers.length; i++)
    {
        var serverInfo = servers[i];
        var serverProfilingInfo = serverInfo.profilingInfo.averages;
        for (var key in serverProfilingInfo)
        {
            var value = serverProfilingInfo[key].toFixed(2);
            var processorFigure = objj_msgSend(self, "processorFor:", key);
            if (processorFigure != nil)
            {
                var dataFigure = objj_msgSend(processorFigure, "dataFigure");
                objj_msgSend(dataFigure, "setText:", value + " ms");
                objj_msgSend(dataFigure, "setHidden:", NO);
            }
        }
    }
}
,["void","id"]), new objj_method(sel_getUid("setStatus:"), function $ActionsDrawing__setStatus_(self, _cmd, aMessage)
{
    objj_msgSend(self._statusFigure, "setText:", aMessage);
}
,["void","id"]), new objj_method(sel_getUid("initiateMessageSendFrom:"), function $ActionsDrawing__initiateMessageSendFrom_(self, _cmd, aProcessorFigure)
{
    var myController = objj_msgSend(objj_msgSend(InterpreterAPIWindowController, "alloc"), "initWithWindowCibName:", "SendMessageWindow");
    objj_msgSend(myController, "showWindow:", nil);
    var interpreterAPI = objj_msgSend(InterpreterAPI, "newIn:elementId:", objj_msgSend(self, "contextId"), objj_msgSend(aProcessorFigure, "elementId"));
    objj_msgSend(interpreterAPI, "delegate:", self);
    objj_msgSend(interpreterAPI, "createInterpreter");
    objj_msgSend(myController, "interpreterAPI:", interpreterAPI);
}
,["void","id"]), new objj_method(sel_getUid("setupNotifications"), function $ActionsDrawing__setupNotifications(self, _cmd)
{
    objj_msgSend(objj_msgSend(Actions, "controller"), "setupNotifications");
}
,["void"]), new objj_method(sel_getUid("generator"), function $ActionsDrawing__generator(self, _cmd)
{
    return self._generator;
}
,["id"]), new objj_method(sel_getUid("generator:"), function $ActionsDrawing__generator_(self, _cmd, aGenerator)
{
    self._generator = aGenerator;
}
,["id","id"]), new objj_method(sel_getUid("registerElement:for:"), function $ActionsDrawing__registerElement_for_(self, _cmd, anElementFigure, anElementId)
{
    CPLog.debug("[ActionsDrawingdsadasfdfdsaf] Register element: " + anElementId);
    objj_msgSend(self._processorFigures, "setObject:forKey:", anElementFigure, anElementId);
}
,["void","id","id"]), new objj_method(sel_getUid("processorFor:"), function $ActionsDrawing__processorFor_(self, _cmd, aProcessorId)
{
    if (aProcessorId.indexOf('urn:') == 0)
    {
        aProcessorId = aProcessorId.substr(4);
    }
    var processorFigure = objj_msgSend(self._processorFigures, "objectForKey:", aProcessorId);
    return processorFigure;
}
,["id","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("frame:contextId:"), function $ActionsDrawing__frame_contextId_(self, _cmd, aFrame, aContextId)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ActionsDrawing").super_class }, "frame:", aFrame), "initWith:", aContextId);
}
,["ActionsDrawing","id","id"]), new objj_method(sel_getUid("createMultilineText:"), function $ActionsDrawing__createMultilineText_(self, _cmd, aFrame)
{
    var multiline = objj_msgSend(objj_msgSend(LPMultiLineTextField, "alloc"), "initWithFrame:", aFrame);
    objj_msgSend(multiline, "setFrameOrigin:", CGPointMake(0.0, 0.0));
    objj_msgSend(multiline, "setAutoresizingMask:", CPViewHeightSizable | CPViewWidthSizable);
    objj_msgSend(multiline, "setBordered:", YES);
    objj_msgSend(multiline, "setScrollable:", YES);
    objj_msgSend(multiline, "setEditable:", YES);
    objj_msgSend(multiline, "setDrawsBackground:", YES);
    objj_msgSend(multiline, "setBezeled:", YES);
    return multiline;
}
,["id","id"])]);
}p;27;Actions/Server/ContextAPI.jt;2861;@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;2788;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ContextAPI"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_contextId"), new objj_ivar("_delegate")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("delegate:"), function $ContextAPI__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
}
,["void","id"]), new objj_method(sel_getUid("initWithContextId:"), function $ContextAPI__initWithContextId_(self, _cmd, contextId)
{
    self._contextId = contextId;
    if (self._contextId == nil)
    {
        objj_msgSend(self, "createContext");
    }
    return self;
}
,["id","id"]), new objj_method(sel_getUid("contextId"), function $ContextAPI__contextId(self, _cmd)
{
    return self._contextId;
}
,["id"]), new objj_method(sel_getUid("createContext"), function $ContextAPI__createContext(self, _cmd)
{
    if (self._contextId != nil)
    {
        return self;
    }
    CPLog.debug("Creating context");
    ($.ajax({type: "POST", url: "../service/context/"})).done(function(result)
    {
        self._contextId = result.id;
        objj_msgSend(self._delegate, "contextCreated:", self._contextId);
        CPLog.debug("Context created with id:" + self._contextId);
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("sync"), function $ContextAPI__sync(self, _cmd)
{
    CPLog.debug("Syncing context " + self._contextId);
    ($.ajax({type: "POST", url: "../service/context/" + self._contextId + "/sync"})).done(function(result)
    {
        CPLog.debug("Context synced " + self._contextId);
    });
    return self;
}
,["id"]), new objj_method(sel_getUid("profilingInfo"), function $ContextAPI__profilingInfo(self, _cmd)
{
    CPLog.debug("Getting profiling info for context " + self._contextId);
    ($.ajax({type: "GET", url: "../service/context/" + self._contextId + "/profilingInfo/" + objj_msgSend(Actions, "deploymentId")})).done(function(result)
    {
        objj_msgSend(self._delegate, "profilingInfo:", result);
        CPLog.debug("Profiling info obtained for " + self._contextId);
    });
    return self;
}
,["id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("new"), function $ContextAPI__new(self, _cmd)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ContextAPI").super_class }, "new"), "initWithContextId:", nil);
}
,["ContextAPI"]), new objj_method(sel_getUid("newWith:"), function $ContextAPI__newWith_(self, _cmd, contextId)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ContextAPI").super_class }, "new"), "initWithContextId:", contextId);
}
,["ContextAPI","id"])]);
}p;24;Actions/DeploymentMode.jt;8049;@STATIC;1.0;t;8030;{var the_class = objj_allocateClassPair(CPObject, "DeploymentMode"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_timer"), new objj_ivar("_drawing")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DeploymentMode__init(self, _cmd)
{
    self._timer = objj_msgSend(CPTimer, "scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:", 5, self, sel_getUid("updateProfilingInfo:"), nil, YES);
    return self;
}
,["id"]), new objj_method(sel_getUid("updateProfilingInfo:"), function $DeploymentMode__updateProfilingInfo_(self, _cmd, sender)
{
    if (self._drawing != nil)
    {
        objj_msgSend(self._drawing, "updateProfilingInfo");
    }
}
,["void","id"]), new objj_method(sel_getUid("postAddProcessor:"), function $DeploymentMode__postAddProcessor_(self, _cmd, aProcessorFigure)
{
    var bottomRight = objj_msgSend(aProcessorFigure, "bottomRight");
    var performanceLabel = objj_msgSend(LabelFigure, "initializeWithText:at:", "", bottomRight);
    objj_msgSend(performanceLabel, "backgroundColor:", objj_msgSend(CPColor, "blueColor"));
    objj_msgSend(performanceLabel, "foregroundColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(performanceLabel, "setHidden:", YES);
    var magnet = objj_msgSend(Magnet, "newWithSource:target:selector:", aProcessorFigure, performanceLabel, sel_getUid("bottomRight"));
    var drawing = objj_msgSend(aProcessorFigure, "drawing");
    objj_msgSend(aProcessorFigure, "setDataFigure:", performanceLabel);
    objj_msgSend(drawing, "addFigure:", performanceLabel);
}
,["void","id"]), new objj_method(sel_getUid("createMessageSourceStateFigure:drawing:point:"), function $DeploymentMode__createMessageSourceStateFigure_drawing_point_(self, _cmd, aMessageSourceFigure, aDrawing, aPoint)
{
}
,["void","id","id","id"]), new objj_method(sel_getUid("createMessageSourceFigureMenu:menu:"), function $DeploymentMode__createMessageSourceFigureMenu_menu_(self, _cmd, aMessageSourceFigure, contextMenu)
{
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createProcessorFigureMenu:menu:"), function $DeploymentMode__createProcessorFigureMenu_menu_(self, _cmd, aProcessorFigure, contextMenu)
{
    var addProfilerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add profiler", sel_getUid("addProfiler:"), "");
    objj_msgSend(addProfilerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(addProfilerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", addProfilerMenu);
    var removeProfilerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Remove profiler", sel_getUid("removeProfiler:"), "");
    objj_msgSend(removeProfilerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(removeProfilerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", removeProfilerMenu);
    objj_msgSend(contextMenu, "addItem:", objj_msgSend(CPMenuItem, "separatorItem"));
    var addLoggerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add logger", sel_getUid("addLogger:"), "");
    objj_msgSend(addLoggerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(addLoggerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", addLoggerMenu);
    var removeLoggerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Remove logger", sel_getUid("removeLogger:"), "");
    objj_msgSend(removeLoggerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(removeLoggerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", removeLoggerMenu);
    var viewLogMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "View log", sel_getUid("viewLog:"), "");
    objj_msgSend(viewLogMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(viewLogMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", viewLogMenu);
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createElementFigureMenu:"), function $DeploymentMode__createElementFigureMenu_(self, _cmd, anElementFigure)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementFigure);
    objj_msgSend(anElementFigure, "beforeDeleteMenu:", contextMenu);
    objj_msgSend(anElementFigure, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("createElementConnectionMenu:"), function $DeploymentMode__createElementConnectionMenu_(self, _cmd, anElementConnection)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementConnection);
    var cacheMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add cache (lazy compute)", sel_getUid("addLazyComputedCache"), "");
    objj_msgSend(cacheMenu, "setTarget:", anElementConnection);
    objj_msgSend(cacheMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", cacheMenu);
    var cache2Menu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add cache (async lazy compute)", sel_getUid("addLazyAutorefreshableCache"), "");
    objj_msgSend(cache2Menu, "setTarget:", anElementConnection);
    objj_msgSend(cache2Menu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", cache2Menu);
    var asyncMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add async operation", sel_getUid("addCache"), "");
    objj_msgSend(asyncMenu, "setTarget:", anElementConnection);
    objj_msgSend(asyncMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", asyncMenu);
    var trackingMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add tracking", sel_getUid("addCache"), "");
    objj_msgSend(trackingMenu, "setTarget:", anElementConnection);
    objj_msgSend(trackingMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", trackingMenu);
    objj_msgSend(anElementConnection, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("load:"), function $DeploymentMode__load_(self, _cmd, aDrawing)
{
    self._drawing = aDrawing;
    objj_msgSend(self, "loadGenerator:", aDrawing);
    objj_msgSend(self, "loadDiagramElements:", aDrawing);
    var performanceLabelPoint = CGPointMake(10, 10);
    var performanceLabel = objj_msgSend(LabelFigure, "initializeWithText:at:", "Performance in ms", performanceLabelPoint);
    objj_msgSend(performanceLabel, "backgroundColor:", objj_msgSend(CPColor, "blueColor"));
    objj_msgSend(performanceLabel, "foregroundColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(self._drawing, "addFigure:", performanceLabel);
}
,["void","id"]), new objj_method(sel_getUid("loadDiagramElements:"), function $DeploymentMode__loadDiagramElements_(self, _cmd, drawing)
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
,["void","id"]), new objj_method(sel_getUid("loadGenerator:"), function $DeploymentMode__loadGenerator_(self, _cmd, aDrawing)
{
    var libraries = objj_msgSend(DataUtil, "var:", "libraries").libraries;
    var generator = objj_msgSend(DynamicModelGenerator, "new");
    objj_msgSend(aDrawing, "generator:", generator);
    for (var i = 0; i < libraries.length; i++)
    {
        var library = libraries[i];
        var elements = library.elements;
        CPLog.debug("Processing library " + library.name);
        objj_msgSend(generator, "addDefinitions:", elements);
    }
}
,["void","id"])]);
}p;56;Actions/Controller/AddLazyAutorefreshableCacheDelegate.jt;1218;@STATIC;1.0;I;21;Foundation/CPObject.jt;1173;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLazyAutorefreshableCacheDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_memcachedURLTF"), new objj_ivar("_oldCacheEntryInMillsTF"), new objj_ivar("_keyExpressionTF"), new objj_ivar("_cacheExpressionsTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("add:"), function $AddLazyAutorefreshableCacheDelegate__add_(self, _cmd, aSender)
{
    var memcachedURL = objj_msgSend(self._memcachedURLTF, "stringValue");
    var oldCacheEntryInMills = objj_msgSend(self._oldCacheEntryInMillsTF, "stringValue");
    var keyExpression = objj_msgSend(self._keyExpressionTF, "stringValue");
    var cacheExpressions = objj_msgSend(self._cacheExpressionsTF, "stringValue");
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLazyAutorefreshableCache:oldCacheEntryInMills:keyExpression:cacheExpressions:", memcachedURL, oldCacheEntryInMills, keyExpression, cacheExpressions);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}p;49;Actions/Controller/AddLazyComputedCacheDelegate.jt;1112;@STATIC;1.0;I;21;Foundation/CPObject.jt;1067;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLazyComputedCacheDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_memcachedURLTF"), new objj_ivar("_ttlTF"), new objj_ivar("_keyExpressionTF"), new objj_ivar("_cacheExpressionsTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("add:"), function $AddLazyComputedCacheDelegate__add_(self, _cmd, aSender)
{
    var memcachedURL = objj_msgSend(self._memcachedURLTF, "stringValue");
    var ttl = objj_msgSend(self._ttlTF, "stringValue");
    var keyExpression = objj_msgSend(self._keyExpressionTF, "stringValue");
    var cacheExpressions = objj_msgSend(self._cacheExpressionsTF, "stringValue");
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLazyComputedCache:ttl:keyExpression:cacheExpressions:", memcachedURL, ttl, keyExpression, cacheExpressions);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}p;40;Actions/Controller/AddLoggerController.jt;2725;@STATIC;1.0;I;21;Foundation/CPObject.jt;2680;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLoggerController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_beforeConditionalContainerTF"), new objj_ivar("_afterConditionalContainerTF"), new objj_ivar("_beforeContainerTF"), new objj_ivar("_afterContainerTF"), new objj_ivar("_isBeforeConditionalCB"), new objj_ivar("_isAfterConditionalCB"), new objj_ivar("_beforeConditionalTF"), new objj_ivar("_afterConditionalTF"), new objj_ivar("_beforeTF"), new objj_ivar("_afterTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $AddLoggerController__awakeFromCib(self, _cmd)
{
    self._beforeConditionalTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._beforeConditionalContainerTF, "frame"));
    self._afterConditionalTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._afterConditionalContainerTF, "frame"));
    self._beforeTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._beforeContainerTF, "frame"));
    self._afterTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._afterContainerTF, "frame"));
    objj_msgSend(self._beforeConditionalContainerTF, "addSubview:", self._beforeConditionalTF);
    objj_msgSend(self._afterConditionalContainerTF, "addSubview:", self._afterConditionalTF);
    objj_msgSend(self._beforeContainerTF, "addSubview:", self._beforeTF);
    objj_msgSend(self._afterContainerTF, "addSubview:", self._afterTF);
}
,["void"]), new objj_method(sel_getUid("add:"), function $AddLoggerController__add_(self, _cmd, aSender)
{
    var beforeExpressionValue = objj_msgSend(self._beforeTF, "stringValue");
    var afterExpressionValue = objj_msgSend(self._afterTF, "stringValue");
    var isBeforeConditional = objj_msgSend(self._isBeforeConditionalCB, "stringValue") == "1";
    var isAfterConditional = objj_msgSend(self._isAfterConditionalCB, "stringValue") == "1";
    var beforeConditionalExpressionValue = isBeforeConditional ? objj_msgSend(self._beforeConditionalTF, "stringValue") : nil;
    var afterConditionalExpressionValue = isAfterConditional ? objj_msgSend(self._afterConditionalTF, "stringValue") : nil;
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLogger:afterExpression:beforeConditionalExpressionValue:afterConditionalExpressionValue:", beforeExpressionValue, afterExpressionValue, beforeConditionalExpressionValue, afterConditionalExpressionValue);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}p;37;Actions/Controller/DebuggerDelegate.jt;12062;@STATIC;1.0;I;21;Foundation/CPObject.jt;12016;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "DebuggerDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_payloadContainerTF"), new objj_ivar("_exceptionContainerTF"), new objj_ivar("_evaluateContainerTF"), new objj_ivar("_evaluateResultContainerTF"), new objj_ivar("_tableView"), new objj_ivar("_nameColumn"), new objj_ivar("_stepButton"), new objj_ivar("_resumeButton"), new objj_ivar("_evaluateButton"), new objj_ivar("_payloadTF"), new objj_ivar("_exceptionTF"), new objj_ivar("_evaluateTF"), new objj_ivar("_evaluateResultTF"), new objj_ivar("_propertiesKeys"), new objj_ivar("_propertiesValues"), new objj_ivar("_drawing"), new objj_ivar("_currentProcessor"), new objj_ivar("_currentMessage"), new objj_ivar("_interpreterAPI"), new objj_ivar("_development")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $DebuggerDelegate__awakeFromCib(self, _cmd)
{
    self._propertiesKeys = objj_msgSend(CPMutableArray, "array");
    self._propertiesValues = objj_msgSend(CPMutableArray, "array");
    self._payloadTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._payloadContainerTF, "frame"));
    self._exceptionTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._exceptionContainerTF, "frame"));
    self._evaluateTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._evaluateContainerTF, "frame"));
    self._evaluateResultTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._evaluateResultContainerTF, "frame"));
    objj_msgSend(self._payloadContainerTF, "addSubview:", self._payloadTF);
    objj_msgSend(self._exceptionContainerTF, "addSubview:", self._exceptionTF);
    objj_msgSend(self._evaluateContainerTF, "addSubview:", self._evaluateTF);
    objj_msgSend(self._evaluateResultContainerTF, "addSubview:", self._evaluateResultTF);
    objj_msgSend(self._tableView, "setDataSource:", self);
    objj_msgSend(self._tableView, "setDelegate:", self);
    self._drawing = objj_msgSend(Actions, "drawing");
    self._development = objj_msgSend(Actions, "isDevelopment");
    self._interpreterAPI = objj_msgSend(objj_msgSend(self._window, "windowController"), "interpreterAPI");
    objj_msgSend(objj_msgSend(Actions, "controller"), "register:asDebugDelegateFor:", self, objj_msgSend(objj_msgSend(self._window, "windowController"), "id"));
}
,["void"]), new objj_method(sel_getUid("interpreterAPI"), function $DebuggerDelegate__interpreterAPI(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self._window, "windowController"), "interpreterAPI");
}
,["id"]), new objj_method(sel_getUid("drawing"), function $DebuggerDelegate__drawing(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self._window, "windowController"), "drawing");
}
,["id"]), new objj_method(sel_getUid("currentProcessor"), function $DebuggerDelegate__currentProcessor(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self._window, "windowController"), "currentProcessor");
}
,["id"]), new objj_method(sel_getUid("resume:"), function $DebuggerDelegate__resume_(self, _cmd, aSender)
{
    objj_msgSend(self._drawing, "unselectAll");
    objj_msgSend(self._interpreterAPI, "resume");
    objj_msgSend(self._stepButton, "setEnabled:", NO);
    objj_msgSend(self._resumeButton, "setEnabled:", NO);
    if (self._currentProcessor != nil)
    {
        objj_msgSend(self._currentProcessor, "unhighlight");
    }
}
,["void","id"]), new objj_method(sel_getUid("step:"), function $DebuggerDelegate__step_(self, _cmd, aSender)
{
    objj_msgSend(self._drawing, "unselectAll");
    objj_msgSend(self._interpreterAPI, "step");
    if (self._currentProcessor != nil)
    {
        objj_msgSend(self._currentProcessor, "unhighlight");
    }
}
,["void","id"]), new objj_method(sel_getUid("evaluate:"), function $DebuggerDelegate__evaluate_(self, _cmd, aSender)
{
    var expression = objj_msgSend(self._evaluateTF, "stringValue");
    objj_msgSend(self._interpreterAPI, "evaluate:", expression);
}
,["void","id"]), new objj_method(sel_getUid("evaluationResult:"), function $DebuggerDelegate__evaluationResult_(self, _cmd, aResult)
{
    objj_msgSend(self._evaluateResultTF, "setStringValue:", aResult);
}
,["void","id"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $DebuggerDelegate__numberOfRowsInTableView_(self, _cmd, aTableView)
{
    if (self._currentMessage == nil)
    {
        return 0;
    }
    else
    {
        return self._currentMessage.properties.length;
    }
}
,["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $DebuggerDelegate__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return self._currentMessage.properties[rowIndex].key;
    }
    else
    {
        return self._currentMessage.properties[rowIndex].value;
    }
}
,["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $DebuggerDelegate__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
{
}
,["void","CPTableView","id","CPTableColumn","int"]), new objj_method(sel_getUid("reloadData"), function $DebuggerDelegate__reloadData(self, _cmd)
{
    objj_msgSend(self._tableView, "reloadData");
}
,["void"]), new objj_method(sel_getUid("process:"), function $DebuggerDelegate__process_(self, _cmd, aMessage)
{
    var eventType = aMessage.eventType;
    var currentMessage = aMessage.currentMessage;
    var processorId = aMessage.processorId;
    var jsonCurrentMessage = JSON.stringify(aMessage);
    var exception = aMessage.exception;
    CPLog.debug("[DebuggerDelegate] Processor id " + processorId);
    CPLog.debug("[DebuggerDelegate] " + jsonCurrentMessage);
    if (objj_msgSend(eventType, "isEqualToString:", "breakpoint"))
    {
        objj_msgSend(self, "processBreakpoint:processorId:", aMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "before-step"))
    {
        objj_msgSend(self, "processBeforeStep:processorId:", aMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "after-step"))
    {
        objj_msgSend(self, "processAfterStep:processorId:", aMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "uncaught-exception"))
    {
        objj_msgSend(self, "processUncaughtException:processorId:exception:", aMessage, processorId, exception);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "finish-interpretation"))
    {
        objj_msgSend(self, "processFinish:", aMessage);
    }
}
,["void","id"]), new objj_method(sel_getUid("setTitle:"), function $DebuggerDelegate__setTitle_(self, _cmd, aTitle)
{
    objj_msgSend(self._window, "setTitle:", aTitle);
}
,["void","id"]), new objj_method(sel_getUid("close"), function $DebuggerDelegate__close(self, _cmd)
{
    objj_msgSend(self._window, "close");
}
,["void"]), new objj_method(sel_getUid("processUncaughtException:processorId:exception:"), function $DebuggerDelegate__processUncaughtException_processorId_exception_(self, _cmd, aMessage, processorId, anException)
{
    objj_msgSend(self._stepButton, "setEnabled:", NO);
    objj_msgSend(self._resumeButton, "setEnabled:", NO);
    objj_msgSend(self._evaluateButton, "setEnabled:", NO);
    objj_msgSend(self._exceptionTF, "setStringValue:", anException);
    objj_msgSend(self, "showMessage:", aMessage);
    var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
    self._currentProcessor = figure;
    CPLog.debug("[DEBUG WINDOW] >> processUncaughtException " + self._currentProcessor);
    if (figure != nil)
    {
        objj_msgSend(self, "setTitle:", "Uncaught exception at " + objj_msgSend(objj_msgSend(figure, "model"), "propertyValue:", "name"));
        objj_msgSend(self._drawing, "unselectAll");
        objj_msgSend(figure, "select");
        objj_msgSend(self._drawing, "selectedFigure:", figure);
        objj_msgSend(figure, "highlight");
    }
}
,["void","id","id","id"]), new objj_method(sel_getUid("processFinish:"), function $DebuggerDelegate__processFinish_(self, _cmd, aMessage)
{
    if (objj_msgSend(objj_msgSend(self._interpreterAPI, "threadId"), "isEqual:", "1"))
    {
        objj_msgSend(self, "setTitle:", "Finished");
        objj_msgSend(self, "showMessage:", aMessage);
        objj_msgSend(self._stepButton, "setEnabled:", NO);
        objj_msgSend(self._resumeButton, "setEnabled:", NO);
        objj_msgSend(self._evaluateButton, "setEnabled:", NO);
    }
    else
    {
        objj_msgSend(self, "close");
    }
}
,["void","id"]), new objj_method(sel_getUid("processAfterStep:processorId:"), function $DebuggerDelegate__processAfterStep_processorId_(self, _cmd, aMessage, processorId)
{
    if (self._development)
    {
        var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleRight");
            var shiftedPoint = CGPointMake(point.x + 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
        objj_msgSend(self, "showMessage:", aMessage);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("processBeforeStep:processorId:"), function $DebuggerDelegate__processBeforeStep_processorId_(self, _cmd, aMessage, processorId)
{
    if (self._development)
    {
        var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleLeft");
            var shiftedPoint = CGPointMake(point.x - 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
        objj_msgSend(self, "showMessage:", aMessage);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("processBreakpoint:processorId:"), function $DebuggerDelegate__processBreakpoint_processorId_(self, _cmd, aMessage, processorId)
{
    objj_msgSend(self._stepButton, "setEnabled:", YES);
    objj_msgSend(self._resumeButton, "setEnabled:", YES);
    objj_msgSend(self, "showMessage:", aMessage);
    var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
    self._currentProcessor = figure;
    CPLog.debug("[DEBUG WINDOW] >> processBreakpoint" + self._currentProcessor);
    if (figure != nil)
    {
        objj_msgSend(self, "setTitle:", "Stopped at " + objj_msgSend(objj_msgSend(figure, "model"), "propertyValue:", "name"));
        objj_msgSend(self._drawing, "unselectAll");
        objj_msgSend(figure, "select");
        objj_msgSend(self._drawing, "selectedFigure:", figure);
        objj_msgSend(figure, "highlight");
    }
}
,["void","id","id"]), new objj_method(sel_getUid("showMarkAt:"), function $DebuggerDelegate__showMarkAt_(self, _cmd, aPoint)
{
    if (self._currentProcessor != nil)
    {
        objj_msgSend(self._currentProcessor, "fadeOut");
        objj_msgSend(self._currentProcessor, "removeFromSuperview");
    }
    CPLog.debug("Showing debug mark at: " + aPoint);
    var stopFigure = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/stop.gif", aPoint.x, aPoint.y);
    objj_msgSend(self._drawing, "addFigure:", stopFigure);
    self._currentProcessor = stopFigure;
}
,["void","id"]), new objj_method(sel_getUid("showMessage:"), function $DebuggerDelegate__showMessage_(self, _cmd, aMessage)
{
    self._currentMessage = aMessage.currentMessage;
    var payload = self._currentMessage.payload;
    if (payload == nil)
    {
        payload = "{null payload}";
    }
    else if (objj_msgSend(payload, "isEqual:", ""))
    {
        payload = "{empty string payload}";
    }
    objj_msgSend(self._payloadTF, "setStringValue:", payload);
    objj_msgSend(self, "reloadData");
}
,["void","id"])]);
}p;40;Actions/Controller/SendMessageDelegate.jt;4481;@STATIC;1.0;I;21;Foundation/CPObject.jt;4436;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "SendMessageDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_payloadTF"), new objj_ivar("_isExpressionCB"), new objj_ivar("_tableView"), new objj_ivar("_nameColumn"), new objj_ivar("_propertiesKeys"), new objj_ivar("_propertiesValues")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $SendMessageDelegate__awakeFromCib(self, _cmd)
{
    self._propertiesKeys = objj_msgSend(CPMutableArray, "array");
    self._propertiesValues = objj_msgSend(CPMutableArray, "array");
    objj_msgSend(self._tableView, "setDoubleAction:", sel_getUid("doubleClick:"));
    objj_msgSend(self._tableView, "setDataSource:", self);
    objj_msgSend(self._tableView, "setDelegate:", self);
}
,["void"]), new objj_method(sel_getUid("buildJsonMessage"), function $SendMessageDelegate__buildJsonMessage(self, _cmd)
{
    var payload = objj_msgSend(self._payloadTF, "stringValue");
    var message = {"payload": payload, "properties": []};
    for (var i = 0; i < objj_msgSend(self._propertiesKeys, "count"); i++)
    {
        var key = objj_msgSend(self._propertiesKeys, "objectAtIndex:", i);
        var value = objj_msgSend(self._propertiesValues, "objectAtIndex:", i);
        var property = {"key": key, "value": value};
        message.properties.push(property);
    }
    return message;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI"), function $SendMessageDelegate__interpreterAPI(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self._window, "windowController"), "interpreterAPI");
}
,["id"]), new objj_method(sel_getUid("run:"), function $SendMessageDelegate__run_(self, _cmd, aSender)
{
    var initialMessage = objj_msgSend(self, "buildJsonMessage");
    objj_msgSend(objj_msgSend(self, "interpreterAPI"), "run:", initialMessage);
    objj_msgSend(self._window, "close");
}
,["void","id"]), new objj_method(sel_getUid("debug:"), function $SendMessageDelegate__debug_(self, _cmd, aSender)
{
    var initialMessage = objj_msgSend(self, "buildJsonMessage");
    objj_msgSend(objj_msgSend(self, "interpreterAPI"), "debug:", initialMessage);
    objj_msgSend(self._window, "close");
}
,["void","id"]), new objj_method(sel_getUid("addProperty:"), function $SendMessageDelegate__addProperty_(self, _cmd, aSender)
{
    objj_msgSend(self._propertiesKeys, "addObject:", "new property name");
    objj_msgSend(self._propertiesValues, "addObject:", "new property value");
    objj_msgSend(self, "reloadData");
}
,["void","id"]), new objj_method(sel_getUid("removeProperty:"), function $SendMessageDelegate__removeProperty_(self, _cmd, aSender)
{
    var index = objj_msgSend(self._tableView, "selectedRow");
    objj_msgSend(self._propertiesKeys, "removeObjectAtIndex:", index);
    objj_msgSend(self._propertiesValues, "removeObjectAtIndex:", index);
    objj_msgSend(self, "reloadData");
}
,["void","id"]), new objj_method(sel_getUid("reloadData"), function $SendMessageDelegate__reloadData(self, _cmd)
{
    objj_msgSend(self._tableView, "reloadData");
}
,["void"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $SendMessageDelegate__numberOfRowsInTableView_(self, _cmd, aTableView)
{
    return objj_msgSend(self._propertiesKeys, "count");
}
,["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $SendMessageDelegate__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return objj_msgSend(self._propertiesKeys, "objectAtIndex:", rowIndex);
    }
    else
    {
        return objj_msgSend(self._propertiesValues, "objectAtIndex:", rowIndex);
    }
}
,["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $SendMessageDelegate__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return objj_msgSend(self._propertiesKeys, "replaceObjectAtIndex:withObject:", rowIndex, aValue);
    }
    else
    {
        return objj_msgSend(self._propertiesValues, "replaceObjectAtIndex:withObject:", rowIndex, aValue);
    }
}
,["void","CPTableView","id","CPTableColumn","int"])]);
}p;39;Actions/Controller/ViewLoggerDelegate.jt;1251;@STATIC;1.0;I;21;Foundation/CPObject.jt;1206;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ViewLoggerDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_logTextContainer"), new objj_ivar("_logText")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $ViewLoggerDelegate__awakeFromCib(self, _cmd)
{
    self._logText = objj_msgSend(objj_msgSend(LPMultiLineTextField, "alloc"), "initWithFrame:", objj_msgSend(self._logTextContainer, "frame"));
    objj_msgSend(self._logText, "setFrameOrigin:", CGPointMake(0.0, 0.0));
    objj_msgSend(self._logText, "setAutoresizingMask:", CPViewHeightSizable | CPViewWidthSizable);
    objj_msgSend(self._logText, "setBordered:", YES);
    objj_msgSend(self._logText, "setScrollable:", YES);
    objj_msgSend(self._logText, "setEditable:", YES);
    objj_msgSend(self._logText, "setDrawsBackground:", YES);
    objj_msgSend(self._logText, "setBezeled:", YES);
    objj_msgSend(self._logTextContainer, "addSubview:", self._logText);
    objj_msgSend(objj_msgSend(self._window, "windowController"), "logText:", self._logText);
}
,["void"])]);
}