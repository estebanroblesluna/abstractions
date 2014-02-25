@STATIC;1.0;t;2210;{var the_class = objj_allocateClassPair(MultiStateFigure, "MessageSourceStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_lookModel"), new objj_ivar("_lastState")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithModel:"), function $MessageSourceStateFigure__initializeWithModel_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("MessageSourceStateFigure").super_class }, "init");
    var state1 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/service_stop.png", 0, 0);
    var state2 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/service_running.png", 0, 0);
    objj_msgSend(state1, "showBorder:", NO);
    objj_msgSend(state2, "showBorder:", NO);
    objj_msgSend(self, "addFigure:forState:", state1, "STOP");
    objj_msgSend(self, "addFigure:forState:", state2, "START");
    self._lookModel = aModel;
    objj_msgSend(self, "setHidden:", NO);
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateStateFigure"), ModelPropertyChangedNotification, self._lookModel);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("updateStateFigure"), function $MessageSourceStateFigure__updateStateFigure(self, _cmd)
{
    var state = objj_msgSend(self._lookModel, "messageSourceState");
    if (self._lastState == nil || !objj_msgSend(self._lastState, "isEqualToString:", state))
    {
        if (objj_msgSend(state, "isEqualToString:", "STOP"))
        {
            objj_msgSend(self, "switchToState:", "STOP");
        }
        if (objj_msgSend(state, "isEqualToString:", "START"))
        {
            objj_msgSend(self, "switchToState:", "START");
        }
    }
}
,["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:model:"), function $MessageSourceStateFigure__newAt_model_(self, _cmd, aPoint, aModel)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("MessageSourceStateFigure").super_class }, "newAt:", aPoint), "initializeWithModel:", aModel);
}
,["Figure","CGPoint","id"])]);
}