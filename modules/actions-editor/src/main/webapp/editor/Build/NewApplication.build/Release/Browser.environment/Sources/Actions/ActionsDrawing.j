@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;9004;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(Drawing, "ActionsDrawing"),
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
}