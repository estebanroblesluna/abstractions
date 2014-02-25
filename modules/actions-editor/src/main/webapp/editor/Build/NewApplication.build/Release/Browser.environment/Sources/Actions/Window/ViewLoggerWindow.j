@STATIC;1.0;t;1866;{var the_class = objj_allocateClassPair(CPPanel, "ViewLoggerWindow"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_lines")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initialize"), function $ViewLoggerWindow__initialize(self, _cmd)
{
    var contentView = objj_msgSend(self, "contentView");
    self._lines = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._lines, "setStringValue:", "");
    objj_msgSend(self._lines, "setBezeled:", YES);
    objj_msgSend(self._lines, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._lines, "setEditable:", YES);
    objj_msgSend(self._lines, "sizeToFit");
    var panelFrameSize = objj_msgSend(contentView, "frameSize");
    var expressionFrameSize = objj_msgSend(self._lines, "frameSize");
    objj_msgSend(self._lines, "setFrameSize:", CGSizeMake(panelFrameSize.width, 150));
    objj_msgSend(self._lines, "setFrameOrigin:", CGPointMake(0, 30));
    objj_msgSend(contentView, "addSubview:", self._lines);
    objj_msgSend(self, "setTitle:", "Logger view");
    return self;
}
,["id"]), new objj_method(sel_getUid("loggerInfo:"), function $ViewLoggerWindow__loggerInfo_(self, _cmd, loggerInfo)
{
    var string = loggerInfo.join('\n');
    objj_msgSend(self._lines, "setStringValue:", string);
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $ViewLoggerWindow__newAt_(self, _cmd, aPoint)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 663, 250);
    var window = objj_msgSend(self, "alloc");
    objj_msgSend(window, "initWithContentRect:styleMask:", frame, CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask);
    return objj_msgSend(window, "initialize");
}
,["AddLoggerWindow","id"])]);
}