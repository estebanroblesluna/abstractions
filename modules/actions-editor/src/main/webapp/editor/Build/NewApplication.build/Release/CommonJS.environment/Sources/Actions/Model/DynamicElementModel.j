@STATIC;1.0;t;916;{var the_class = objj_allocateClassPair(ElementModel, "DynamicElementModel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_defaultName")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("elementName"), function $DynamicElementModel__elementName(self, _cmd)
{
    return self._elementName;
}
,["id"]), new objj_method(sel_getUid("elementName:"), function $DynamicElementModel__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"]), new objj_method(sel_getUid("defaultNameValue"), function $DynamicElementModel__defaultNameValue(self, _cmd)
{
    return self._defaultName;
}
,["id"]), new objj_method(sel_getUid("defaultNameValue:"), function $DynamicElementModel__defaultNameValue_(self, _cmd, aDefaultValue)
{
    self._defaultName = aDefaultValue;
}
,["void","id"])]);
}