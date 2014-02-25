@STATIC;1.0;t;1666;{var the_class = objj_allocateClassPair(CPObject, "Magnet"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_sourceFigure"), new objj_ivar("_targetFigure"), new objj_ivar("_selector")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithSource:target:selector:"), function $Magnet__initWithSource_target_selector_(self, _cmd, aSourceFigure, aTargetFigure, aSelector)
{
    self._sourceFigure = aSourceFigure;
    self._targetFigure = aTargetFigure;
    self._selector = aSelector;
    objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:", self, sel_getUid("updateLocation:"), "CPViewFrameDidChangeNotification", self._sourceFigure);
    return self;
}
,["id","id","id","id"]), new objj_method(sel_getUid("updateLocation:"), function $Magnet__updateLocation_(self, _cmd, aNotification)
{
    var position = objj_msgSend(self._sourceFigure, "performSelector:", self._selector);
    var targetFrame = objj_msgSend(self._targetFigure, "frame");
    var xShift = 0;
    var yShift = 0;
    var newTopLeft = CGPointMake(position.x - xShift, position.y - yShift);
    CPLog.debug(newTopLeft.x, newTopLeft.y);
    objj_msgSend(self._targetFigure, "setFrameOrigin:", newTopLeft);
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newWithSource:target:selector:"), function $Magnet__newWithSource_target_selector_(self, _cmd, aSourceFigure, aTargetFigure, aSelector)
{
    return objj_msgSend(objj_msgSend(self, "new"), "initWithSource:target:selector:", aSourceFigure, aTargetFigure, aSelector);
}
,["id","id","id","id"])]);
}