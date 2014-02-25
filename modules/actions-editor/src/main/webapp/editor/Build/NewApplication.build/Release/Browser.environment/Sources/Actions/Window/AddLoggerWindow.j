@STATIC;1.0;t;3725;{var the_class = objj_allocateClassPair(CPPanel, "AddLoggerWindow"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementAPI"), new objj_ivar("_beforeExpression"), new objj_ivar("_afterExpression")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithElementAPI:"), function $AddLoggerWindow__initializeWithElementAPI_(self, _cmd, anElementAPI)
{
    self._elementAPI = anElementAPI;
    var contentView = objj_msgSend(self, "contentView");
    var _addButton = objj_msgSend(CPButton, "buttonWithTitle:", "Add");
    objj_msgSend(_addButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(_addButton, "setBordered:", YES);
    objj_msgSend(_addButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(_addButton, "setFrameOrigin:", CGPointMake(0, 0));
    objj_msgSend(_addButton, "setTarget:", self);
    objj_msgSend(_addButton, "sizeToFit");
    objj_msgSend(_addButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(_addButton, "setAction:", sel_getUid("add"));
    objj_msgSend(contentView, "addSubview:", _addButton);
    self._beforeExpression = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._beforeExpression, "setStringValue:", "Insert expression here...");
    objj_msgSend(self._beforeExpression, "setBezeled:", YES);
    objj_msgSend(self._beforeExpression, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._beforeExpression, "setEditable:", YES);
    objj_msgSend(self._beforeExpression, "sizeToFit");
    var panelFrameSize = objj_msgSend(contentView, "frameSize");
    var expressionFrameSize = objj_msgSend(self._beforeExpression, "frameSize");
    objj_msgSend(self._beforeExpression, "setFrameSize:", CGSizeMake(panelFrameSize.width, 70));
    objj_msgSend(self._beforeExpression, "setFrameOrigin:", CGPointMake(0, 30));
    objj_msgSend(contentView, "addSubview:", self._beforeExpression);
    self._afterExpression = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._afterExpression, "setStringValue:", "Insert expression here...");
    objj_msgSend(self._afterExpression, "setBezeled:", YES);
    objj_msgSend(self._afterExpression, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._afterExpression, "setEditable:", YES);
    objj_msgSend(self._afterExpression, "sizeToFit");
    var panelFrameSize = objj_msgSend(contentView, "frameSize");
    var expressionFrameSize = objj_msgSend(self._afterExpression, "frameSize");
    objj_msgSend(self._afterExpression, "setFrameSize:", CGSizeMake(panelFrameSize.width, 70));
    objj_msgSend(self._afterExpression, "setFrameOrigin:", CGPointMake(0, 110));
    objj_msgSend(contentView, "addSubview:", self._afterExpression);
    return self;
}
,["id","id"]), new objj_method(sel_getUid("add"), function $AddLoggerWindow__add(self, _cmd)
{
    objj_msgSend(self._elementAPI, "addLogger:afterExpression:", objj_msgSend(self._beforeExpression, "stringValue"), objj_msgSend(self._afterExpression, "stringValue"));
    objj_msgSend(self, "close");
}
,["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:elementAPI:"), function $AddLoggerWindow__newAt_elementAPI_(self, _cmd, aPoint, anElementAPI)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 663, 250);
    var window = objj_msgSend(self, "alloc");
    objj_msgSend(window, "initWithContentRect:styleMask:", frame, CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask);
    return objj_msgSend(window, "initializeWithElementAPI:", anElementAPI);
}
,["AddLoggerWindow","id","id"])]);
}