@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jI;15;AppKit/CPView.jI;20;AppKit/CPTextField.ji;15;Util/DataUtil.ji;19;ActionsController.ji;37;Controller/DebuggerWindowController.ji;39;Controller/ElementAPIWindowController.ji;43;Controller/InterpreterAPIWindowController.ji;32;Controller/AddLoggerController.ji;31;Controller/ViewLoggerDelegate.ji;39;Controller/ViewLoggerWindowController.ji;32;Controller/SendMessageDelegate.ji;29;Controller/DebuggerDelegate.ji;48;Controller/AddLazyAutorefreshableCacheDelegate.ji;41;Controller/AddLazyComputedCacheDelegate.ji;20;Model/ElementModel.ji;27;Model/DynamicElementModel.ji;33;Model/DynamicMessageSourceModel.ji;29;Model/DynamicModelGenerator.ji;15;Figure/Magnet.ji;25;Figure/MultiStateFigure.ji;22;Figure/ElementFigure.ji;27;Figure/ElementStateFigure.ji;24;Figure/ProcessorFigure.ji;28;Figure/MessageSourceFigure.ji;33;Figure/MessageSourceStateFigure.ji;20;Figure/DebugFigure.ji;26;Figure/ElementConnection.ji;24;Window/NewMessagePanel.ji;25;Window/ViewMessagePanel.ji;20;Window/DebugWindow.ji;24;Window/AddLoggerWindow.ji;25;Window/ViewLoggerWindow.ji;26;Tool/CreateProcessorTool.ji;34;Tool/CreateElementConnectionTool.ji;30;Tool/CreateMessageSourceTool.ji;23;Command/DeployCommand.ji;28;Command/LoadActionsCommand.ji;28;Command/SaveActionsCommand.ji;25;Command/AbstractCommand.ji;19;Server/ContextAPI.ji;19;Server/ElementAPI.ji;23;Server/InterpreterAPI.ji;19;Server/LibraryAPI.ji;16;ActionsDrawing.ji;13;EditionMode.ji;16;DeploymentMode.jt;4667;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);objj_executeFile("AppKit/CPView.j", NO);objj_executeFile("AppKit/CPTextField.j", NO);objj_executeFile("Util/DataUtil.j", YES);objj_executeFile("ActionsController.j", YES);objj_executeFile("Controller/DebuggerWindowController.j", YES);objj_executeFile("Controller/ElementAPIWindowController.j", YES);objj_executeFile("Controller/InterpreterAPIWindowController.j", YES);objj_executeFile("Controller/AddLoggerController.j", YES);objj_executeFile("Controller/ViewLoggerDelegate.j", YES);objj_executeFile("Controller/ViewLoggerWindowController.j", YES);objj_executeFile("Controller/SendMessageDelegate.j", YES);objj_executeFile("Controller/DebuggerDelegate.j", YES);objj_executeFile("Controller/AddLazyAutorefreshableCacheDelegate.j", YES);objj_executeFile("Controller/AddLazyComputedCacheDelegate.j", YES);objj_executeFile("Model/ElementModel.j", YES);objj_executeFile("Model/DynamicElementModel.j", YES);objj_executeFile("Model/DynamicMessageSourceModel.j", YES);objj_executeFile("Model/DynamicModelGenerator.j", YES);objj_executeFile("Figure/Magnet.j", YES);objj_executeFile("Figure/MultiStateFigure.j", YES);objj_executeFile("Figure/ElementFigure.j", YES);objj_executeFile("Figure/ElementStateFigure.j", YES);objj_executeFile("Figure/ProcessorFigure.j", YES);objj_executeFile("Figure/MessageSourceFigure.j", YES);objj_executeFile("Figure/MessageSourceStateFigure.j", YES);objj_executeFile("Figure/DebugFigure.j", YES);objj_executeFile("Figure/ElementConnection.j", YES);objj_executeFile("Window/NewMessagePanel.j", YES);objj_executeFile("Window/ViewMessagePanel.j", YES);objj_executeFile("Window/DebugWindow.j", YES);objj_executeFile("Window/AddLoggerWindow.j", YES);objj_executeFile("Window/ViewLoggerWindow.j", YES);objj_executeFile("Tool/CreateProcessorTool.j", YES);objj_executeFile("Tool/CreateElementConnectionTool.j", YES);objj_executeFile("Tool/CreateMessageSourceTool.j", YES);objj_executeFile("Command/DeployCommand.j", YES);objj_executeFile("Command/LoadActionsCommand.j", YES);objj_executeFile("Command/SaveActionsCommand.j", YES);objj_executeFile("Command/AbstractCommand.j", YES);objj_executeFile("Server/ContextAPI.j", YES);objj_executeFile("Server/ElementAPI.j", YES);objj_executeFile("Server/InterpreterAPI.j", YES);objj_executeFile("Server/LibraryAPI.j", YES);objj_executeFile("ActionsDrawing.j", YES);objj_executeFile("EditionMode.j", YES);objj_executeFile("DeploymentMode.j", YES);var _actionsDrawingMode = objj_msgSend(EditionMode, "new");
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
}