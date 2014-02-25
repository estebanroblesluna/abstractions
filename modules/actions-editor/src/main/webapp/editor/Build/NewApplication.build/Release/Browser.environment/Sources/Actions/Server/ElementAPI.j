@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;12780;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ElementAPI"),
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
}