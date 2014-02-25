@STATIC;1.0;I;21;Foundation/CPObject.jt;12016;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "DebuggerDelegate"),
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
}