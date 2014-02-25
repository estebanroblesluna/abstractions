@STATIC;1.0;I;21;Foundation/CPObject.jt;612;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "ElementAPIWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("elementAPI"), function $ElementAPIWindowController__elementAPI(self, _cmd)
{
    return self._elementAPI;
}
,["id"]), new objj_method(sel_getUid("elementAPI:"), function $ElementAPIWindowController__elementAPI_(self, _cmd, anElementAPI)
{
    self._elementAPI = anElementAPI;
}
,["void","id"])]);
}