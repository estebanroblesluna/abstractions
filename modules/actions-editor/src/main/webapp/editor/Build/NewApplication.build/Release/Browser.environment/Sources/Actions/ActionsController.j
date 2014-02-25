@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;5679;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ActionsController"),
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
}