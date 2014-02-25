@STATIC;1.0;t;2324;{var the_class = objj_allocateClassPair(Connection, "ElementConnection"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithSource:target:points:"), function $ElementConnection__initWithSource_target_points_(self, _cmd, aSourceFigure, aTargetFigure, anArrayOfPoints)
{
    CPLog.debug("[ElementConnection] " + JSON.stringify(anArrayOfPoints));
    self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementConnection").super_class }, "initWithSource:target:points:", aSourceFigure, aTargetFigure, anArrayOfPoints);
    objj_msgSend(objj_msgSend(Actions, "mode"), "createElementConnectionMenu:", self);
    return self;
}
,["id","Figure","Figure","id"]), new objj_method(sel_getUid("elementName"), function $ElementConnection__elementName(self, _cmd)
{
    return self._elementName;
}
,["id"]), new objj_method(sel_getUid("deleteFromServer"), function $ElementConnection__deleteFromServer(self, _cmd)
{
    objj_msgSend(objj_msgSend(self, "model"), "deleteFromServer");
}
,["void"]), new objj_method(sel_getUid("deleted"), function $ElementConnection__deleted(self, _cmd)
{
    objj_msgSend(self, "removeMyself");
}
,["void"]), new objj_method(sel_getUid("addLazyAutorefreshableCache"), function $ElementConnection__addLazyAutorefreshableCache(self, _cmd)
{
    var myController = objj_msgSend(objj_msgSend(ElementAPIWindowController, "alloc"), "initWithWindowCibName:", "AddLazyAutorefreshableCacheWindow");
    objj_msgSend(myController, "showWindow:", nil);
    objj_msgSend(myController, "elementAPI:", objj_msgSend(objj_msgSend(self, "model"), "api"));
}
,["void"]), new objj_method(sel_getUid("addLazyComputedCache"), function $ElementConnection__addLazyComputedCache(self, _cmd)
{
    var myController = objj_msgSend(objj_msgSend(ElementAPIWindowController, "alloc"), "initWithWindowCibName:", "AddLazyComputedCacheWindow");
    objj_msgSend(myController, "showWindow:", nil);
    objj_msgSend(myController, "elementAPI:", objj_msgSend(objj_msgSend(self, "model"), "api"));
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementConnection__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"])]);
}