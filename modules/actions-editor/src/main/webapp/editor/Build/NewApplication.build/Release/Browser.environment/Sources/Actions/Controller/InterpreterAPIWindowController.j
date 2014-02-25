@STATIC;1.0;I;21;Foundation/CPObject.jt;660;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "InterpreterAPIWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_interpreterAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("interpreterAPI"), function $InterpreterAPIWindowController__interpreterAPI(self, _cmd)
{
    return self._interpreterAPI;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI:"), function $InterpreterAPIWindowController__interpreterAPI_(self, _cmd, anInterpreterAPI)
{
    self._interpreterAPI = anInterpreterAPI;
}
,["void","id"])]);
}