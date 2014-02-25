@STATIC;1.0;t;2941;{var the_class = objj_allocateClassPair(MultiStateFigure, "ElementStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementModel"), new objj_ivar("_lastState")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithModel:"), function $ElementStateFigure__initializeWithModel_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementStateFigure").super_class }, "init");
    var state1 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/cross.gif", 0, 0);
    var state2 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/sync.gif", 0, 0);
    var state3 = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/tick.gif", 0, 0);
    objj_msgSend(state1, "showBorder:", NO);
    objj_msgSend(state2, "showBorder:", NO);
    objj_msgSend(state3, "showBorder:", NO);
    objj_msgSend(self, "addFigure:forState:", state1, "NOT_IN_SYNC");
    objj_msgSend(self, "addFigure:forState:", state2, "SYNCING");
    objj_msgSend(self, "addFigure:forState:", state3, "SYNCED");
    self._elementModel = aModel;
    objj_msgSend(self, "setHidden:", YES);
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateStateFigure"), ModelPropertyChangedNotification, self._elementModel);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("updateStateFigure"), function $ElementStateFigure__updateStateFigure(self, _cmd)
{
    var state = objj_msgSend(self._elementModel, "state");
    if (self._lastState == nil || !objj_msgSend(self._lastState, "isEqualToString:", state))
    {
        if (objj_msgSend(state, "isEqualToString:", "NOT_IN_SYNC"))
        {
            objj_msgSend(self, "switchToState:", "NOT_IN_SYNC");
            objj_msgSend(self, "fadeIn");
        }
        if (objj_msgSend(state, "isEqualToString:", "SYNCING"))
        {
            objj_msgSend(self, "switchToState:", "SYNCING");
        }
        if (objj_msgSend(state, "isEqualToString:", "SYNCED"))
        {
            objj_msgSend(self, "switchToState:", "SYNCED");
            objj_msgSend(self, "fadeOut");
        }
        if (objj_msgSend(state, "isEqualToString:", "DELETED"))
        {
            objj_msgSend(self, "removeMyself");
        }
    }
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementStateFigure__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:elementModel:"), function $ElementStateFigure__newAt_elementModel_(self, _cmd, aPoint, aModel)
{
    return objj_msgSend(objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("ElementStateFigure").super_class }, "newAt:", aPoint), "initializeWithModel:", aModel);
}
,["Figure","CGPoint","id"])]);
}