@STATIC;1.0;t;1783;{var the_class = objj_allocateClassPair(Figure, "MultiStateFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_stateMapping")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $MultiStateFigure__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("MultiStateFigure").super_class }, "init");
    objj_msgSend(self, "selectable:", NO);
    objj_msgSend(self, "moveable:", YES);
    objj_msgSend(self, "editable:", NO);
    self._stateMapping = objj_msgSend(CPDictionary, "dictionary");
    return self;
}
,["id"]), new objj_method(sel_getUid("addFigure:forState:"), function $MultiStateFigure__addFigure_forState_(self, _cmd, aFigure, aState)
{
    objj_msgSend(self._stateMapping, "setObject:forKey:", aFigure, aState);
}
,["void","id","id"]), new objj_method(sel_getUid("switchToState:"), function $MultiStateFigure__switchToState_(self, _cmd, aState)
{
    CPLog.debug("Switching to state " + aState);
    var figure = objj_msgSend(self._stateMapping, "objectForKey:", aState);
    if (figure != nil)
    {
        objj_msgSend(self, "setSubviews:", objj_msgSend(CPMutableArray, "array"));
        objj_msgSend(self, "addSubview:", figure);
        var frameSize = objj_msgSend(figure, "frameSize");
        objj_msgSend(self, "setFrameSize:", frameSize);
        objj_msgSend(self, "invalidate");
        CPLog.debug("Switched to state " + aState);
    }
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $MultiStateFigure__newAt_(self, _cmd, aPoint)
{
    return objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("MultiStateFigure").super_class }, "newAt:", aPoint);
}
,["Figure","CGPoint"])]);
}