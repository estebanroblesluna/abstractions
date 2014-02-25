@STATIC;1.0;I;21;Foundation/CPObject.jt;1206;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "ViewLoggerDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_logTextContainer"), new objj_ivar("_logText")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $ViewLoggerDelegate__awakeFromCib(self, _cmd)
{
    self._logText = objj_msgSend(objj_msgSend(LPMultiLineTextField, "alloc"), "initWithFrame:", objj_msgSend(self._logTextContainer, "frame"));
    objj_msgSend(self._logText, "setFrameOrigin:", CGPointMake(0.0, 0.0));
    objj_msgSend(self._logText, "setAutoresizingMask:", CPViewHeightSizable | CPViewWidthSizable);
    objj_msgSend(self._logText, "setBordered:", YES);
    objj_msgSend(self._logText, "setScrollable:", YES);
    objj_msgSend(self._logText, "setEditable:", YES);
    objj_msgSend(self._logText, "setDrawsBackground:", YES);
    objj_msgSend(self._logText, "setBezeled:", YES);
    objj_msgSend(self._logTextContainer, "addSubview:", self._logText);
    objj_msgSend(objj_msgSend(self._window, "windowController"), "logText:", self._logText);
}
,["void"])]);
}