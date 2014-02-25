@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;2788;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ContextAPI"),
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
}