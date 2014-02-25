@STATIC;1.0;I;21;Foundation/CPObject.jt;1011;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPWindowController, "ViewLoggerWindowController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_lines"), new objj_ivar("_logText")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("lines"), function $ViewLoggerWindowController__lines(self, _cmd)
{
    return self._lines;
}
,["id"]), new objj_method(sel_getUid("lines:"), function $ViewLoggerWindowController__lines_(self, _cmd, aLines)
{
    self._lines = aLines;
}
,["void","id"]), new objj_method(sel_getUid("logText:"), function $ViewLoggerWindowController__logText_(self, _cmd, aLogText)
{
    self._logText = aLogText;
}
,["void","id"]), new objj_method(sel_getUid("windowDidLoad"), function $ViewLoggerWindowController__windowDidLoad(self, _cmd)
{
    var lines = objj_msgSend(self, "lines");
    var log = lines.join('\n');
    objj_msgSend(self._logText, "setStringValue:", log);
}
,["void"])]);
}