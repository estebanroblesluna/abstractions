@STATIC;1.0;t;3940;{var the_class = objj_allocateClassPair(ElementFigure, "ProcessorFigure"),
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
}