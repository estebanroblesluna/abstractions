@STATIC;1.0;t;1174;{var the_class = objj_allocateClassPair(ElementFigure, "MessageSourceFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("beforeDeleteMenu:"), function $MessageSourceFigure__beforeDeleteMenu_(self, _cmd, contextMenu)
{
    objj_msgSend(objj_msgSend(Actions, "mode"), "createMessageSourceFigureMenu:menu:", self, contextMenu);
}
,["void","CPMenu"]), new objj_method(sel_getUid("start:"), function $MessageSourceFigure__start_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "perform:arguments:", "start", nil);
    objj_msgSend(objj_msgSend(self, "model"), "messageSourceState:", "START");
}
,["void","id"]), new objj_method(sel_getUid("stop:"), function $MessageSourceFigure__stop_(self, _cmd, sender)
{
    objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "perform:arguments:", "stop", nil);
    objj_msgSend(objj_msgSend(self, "model"), "messageSourceState:", "STOP");
}
,["void","id"]), new objj_method(sel_getUid("acceptsNewEndingChain"), function $MessageSourceFigure__acceptsNewEndingChain(self, _cmd)
{
    return false;
}
,["id"])]);
}