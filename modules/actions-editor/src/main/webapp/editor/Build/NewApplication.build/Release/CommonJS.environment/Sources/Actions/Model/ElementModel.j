@STATIC;1.0;t;7051;ELEMENT_FAKE_ID = "FAKE_ID";
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
}