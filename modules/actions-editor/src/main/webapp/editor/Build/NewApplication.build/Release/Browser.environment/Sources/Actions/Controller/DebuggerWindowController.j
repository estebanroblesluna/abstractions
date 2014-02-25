@STATIC;1.0;I;21;Foundation/CPObject.jt;919;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "DebuggerWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_id"), new objj_ivar("_interpreterAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("interpreterAPI"), function $DebuggerWindowController__interpreterAPI(self, _cmd)
{
    return self._interpreterAPI;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI:"), function $DebuggerWindowController__interpreterAPI_(self, _cmd, anInterpreterAPI)
{
    self._interpreterAPI = anInterpreterAPI;
}
,["void","id"]), new objj_method(sel_getUid("id:"), function $DebuggerWindowController__id_(self, _cmd, anID)
{
    self._id = anID;
}
,["void","id"]), new objj_method(sel_getUid("id"), function $DebuggerWindowController__id(self, _cmd)
{
    return self._id;
}
,["id"])]);
}