@STATIC;1.0;t;4697;{var the_class = objj_allocateClassPair(Figure, "DebugFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_interpreterAPI"), new objj_ivar("_label"), new objj_ivar("_currentFigure")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithContextId:elementId:"), function $DebugFigure__initializeWithContextId_elementId_(self, _cmd, aContextId, anElementId)
{
    self._currentFigure = nil;
    var button = objj_msgSend(CPButton, "buttonWithTitle:", "Run");
    objj_msgSend(button, "setButtonType:", CPOnOffButton);
    objj_msgSend(button, "setBordered:", YES);
    objj_msgSend(button, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(button, "setFrameSize:", CGSizeMake(50, 30));
    objj_msgSend(button, "setFrameOrigin:", CGPointMake(0, 0));
    objj_msgSend(button, "setTarget:", self);
    objj_msgSend(button, "setAction:", sel_getUid("run"));
    objj_msgSend(button, "sizeToFit");
    objj_msgSend(self, "addSubview:", button);
    var y = objj_msgSend(button, "frameSize").height;
    self._label = objj_msgSend(LabelFigure, "initializeWithText:at:", "Debug", CGPointMake(0, y));
    objj_msgSend(self, "addSubview:", self._label);
    self._interpreterAPI = objj_msgSend(InterpreterAPI, "newIn:elementId:", aContextId, anElementId);
    objj_msgSend(self._interpreterAPI, "delegate:", self);
    objj_msgSend(self._interpreterAPI, "createInterpreter");
    return self;
}
,["id","id","id"]), new objj_method(sel_getUid("run"), function $DebugFigure__run(self, _cmd)
{
    objj_msgSend(self._interpreterAPI, "run");
}
,["void"]), new objj_method(sel_getUid("drawRect:on:"), function $DebugFigure__drawRect_on_(self, _cmd, rect, context)
{
    CGContextSetFillColor(context, objj_msgSend(CPColor, "lightGrayColor"));
    CGContextFillRect(context, objj_msgSend(self, "bounds"));
}
,["void","CGRect","id"]), new objj_method(sel_getUid("createInterpreter:"), function $DebugFigure__createInterpreter_(self, _cmd, anInterpreterId)
{
    objj_msgSend(objj_msgSend(self, "drawing"), "registerDebug:for:", self, anInterpreterId);
}
,["void","id"]), new objj_method(sel_getUid("process:"), function $DebugFigure__process_(self, _cmd, aMessage)
{
    var eventType = aMessage.eventType;
    var currentMessage = aMessage.currentMessage;
    var processorId = aMessage.processorId;
    var jsonCurrentMessage = JSON.stringify(aMessage);
    CPLog.debug("Processor id " + processorId);
    objj_msgSend(self._label, "setText:", jsonCurrentMessage);
    if (objj_msgSend(eventType, "isEqualToString:", "breakpoint"))
    {
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "before-step"))
    {
        var figure = objj_msgSend(objj_msgSend(self, "drawing"), "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleLeft");
            var shiftedPoint = CGPointMake(point.x - 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "after-step"))
    {
        var figure = objj_msgSend(objj_msgSend(self, "drawing"), "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleRight");
            var shiftedPoint = CGPointMake(point.x + 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "finish-interpretation"))
    {
        if (self._currentFigure != nil)
        {
            objj_msgSend(self._currentFigure, "fadeOut");
        }
    }
}
,["void","id"]), new objj_method(sel_getUid("showMarkAt:"), function $DebugFigure__showMarkAt_(self, _cmd, aPoint)
{
    if (self._currentFigure != nil)
    {
        objj_msgSend(self._currentFigure, "fadeOut");
        objj_msgSend(self._currentFigure, "removeFromSuperview");
    }
    CPLog.debug("Showing debug mark at: " + aPoint);
    var stopFigure = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/stop.gif", aPoint.x, aPoint.y);
    objj_msgSend(objj_msgSend(self, "drawing"), "addFigure:", stopFigure);
    self._currentFigure = stopFigure;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:contextId:elementId:"), function $DebugFigure__newAt_contextId_elementId_(self, _cmd, aPoint, aContextId, anElementId)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 200, 300);
    var figure = objj_msgSend(self, "frame:", frame);
    return objj_msgSend(figure, "initializeWithContextId:elementId:", aContextId, anElementId);
}
,["DebugFigure","id","id","id"])]);
}