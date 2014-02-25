@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;4557;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "InterpreterAPI"),
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
}