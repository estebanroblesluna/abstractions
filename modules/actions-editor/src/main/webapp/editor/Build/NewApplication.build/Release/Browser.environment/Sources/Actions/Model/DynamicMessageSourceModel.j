@STATIC;1.0;t;1604;{var the_class = objj_allocateClassPair(ElementModel, "DynamicMessageSourceModel"),
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
}