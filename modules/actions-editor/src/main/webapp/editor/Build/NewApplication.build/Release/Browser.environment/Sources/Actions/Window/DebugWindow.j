@STATIC;1.0;t;16036;{var the_class = objj_allocateClassPair(CPPanel, "DebugWindow"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_interpreterAPI"), new objj_ivar("_label"), new objj_ivar("_currentFigure"), new objj_ivar("_currentProcessor"), new objj_ivar("_drawing"), new objj_ivar("_debugButton"), new objj_ivar("_runButton"), new objj_ivar("_stepButton"), new objj_ivar("_resumeButton"), new objj_ivar("_newMessagePanel"), new objj_ivar("_viewMessagePanel"), new objj_ivar("_development"), new objj_ivar("_state")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithContextId:elementId:drawing:interpreterId:threadId:"), function $DebugWindow__initializeWithContextId_elementId_drawing_interpreterId_threadId_(self, _cmd, aContextId, anElementId, aDrawing, anInterpreterId, aThreadId)
{
    self._currentFigure = nil;
    self._development = objj_msgSend(Actions, "isDevelopment");
    self._drawing = aDrawing;
    var processorFigure = objj_msgSend(self._drawing, "processorFor:", anElementId);
    objj_msgSend(self, "setFloatingPanel:", YES);
    var contentView = objj_msgSend(self, "contentView");
    self._runButton = objj_msgSend(CPButton, "buttonWithTitle:", "Run");
    objj_msgSend(self._runButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._runButton, "setBordered:", YES);
    objj_msgSend(self._runButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._runButton, "setFrameOrigin:", CGPointMake(0, 0));
    objj_msgSend(self._runButton, "setTarget:", self);
    objj_msgSend(self._runButton, "sizeToFit");
    objj_msgSend(self._runButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(self._runButton, "setAction:", sel_getUid("run"));
    objj_msgSend(contentView, "addSubview:", self._runButton);
    self._debugButton = objj_msgSend(CPButton, "buttonWithTitle:", "Debug");
    objj_msgSend(self._debugButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._debugButton, "setBordered:", YES);
    objj_msgSend(self._debugButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._debugButton, "setFrameOrigin:", CGPointMake(65 * 1 + 1, 0));
    objj_msgSend(self._debugButton, "setTarget:", self);
    objj_msgSend(self._debugButton, "sizeToFit");
    objj_msgSend(self._debugButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(self._debugButton, "setAction:", sel_getUid("debug"));
    objj_msgSend(contentView, "addSubview:", self._debugButton);
    self._stepButton = objj_msgSend(CPButton, "buttonWithTitle:", "Step");
    objj_msgSend(self._stepButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._stepButton, "setBordered:", YES);
    objj_msgSend(self._stepButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._stepButton, "setFrameOrigin:", CGPointMake(663 - (65 + 1) * 1, 0));
    objj_msgSend(self._stepButton, "setAutoresizingMask:", CPViewMinXMargin);
    objj_msgSend(self._stepButton, "setTarget:", self);
    objj_msgSend(self._stepButton, "sizeToFit");
    objj_msgSend(self._stepButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(self._stepButton, "setAction:", sel_getUid("step"));
    objj_msgSend(self._stepButton, "setEnabled:", NO);
    objj_msgSend(contentView, "addSubview:", self._stepButton);
    self._resumeButton = objj_msgSend(CPButton, "buttonWithTitle:", "Resume");
    objj_msgSend(self._resumeButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._resumeButton, "setBordered:", YES);
    objj_msgSend(self._resumeButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._resumeButton, "setFrameOrigin:", CGPointMake(663 - (65 + 1) * 2, 0));
    objj_msgSend(self._resumeButton, "setAutoresizingMask:", CPViewMinXMargin);
    objj_msgSend(self._resumeButton, "setTarget:", self);
    objj_msgSend(self._resumeButton, "sizeToFit");
    objj_msgSend(self._resumeButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(self._resumeButton, "setAction:", sel_getUid("resume"));
    objj_msgSend(self._resumeButton, "setEnabled:", NO);
    objj_msgSend(contentView, "addSubview:", self._resumeButton);
    var y = 30;
    self._label = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._label, "setStringValue:", "Debug console");
    objj_msgSend(self._label, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(self._label, "sizeToFit");
    objj_msgSend(self._label, "setFrameOrigin:", CGPointMake(0, y));
    objj_msgSend(self._label, "setHidden:", YES);
    objj_msgSend(contentView, "addSubview:", self._label);
    self._interpreterAPI = objj_msgSend(InterpreterAPI, "newIn:elementId:", aContextId, anElementId);
    objj_msgSend(self._interpreterAPI, "delegate:", self);
    self._newMessagePanel = objj_msgSend(NewMessagePanel, "newAt:", CGPointMake(0, y));
    objj_msgSend(self._newMessagePanel, "setFrameSize:", CGSizeMake(663, 222 - y));
    objj_msgSend(self._newMessagePanel, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    objj_msgSend(contentView, "addSubview:", self._newMessagePanel);
    self._viewMessagePanel = objj_msgSend(ViewMessagePanel, "newAt:api:", CGPointMake(0, y), self._interpreterAPI);
    objj_msgSend(self._viewMessagePanel, "setHidden:", YES);
    objj_msgSend(self._viewMessagePanel, "setFrameSize:", CGSizeMake(663, 222 - y));
    objj_msgSend(self._viewMessagePanel, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    objj_msgSend(contentView, "addSubview:", self._viewMessagePanel);
    if (anInterpreterId == nil)
    {
        CPLog.debug("[DEBUG WINDOW] Creating new interpreter");
        objj_msgSend(self, "setTitle:", "Send message to " + objj_msgSend(objj_msgSend(processorFigure, "model"), "propertyValue:", "Name"));
        objj_msgSend(self._interpreterAPI, "createInterpreter");
    }
    else
    {
        CPLog.debug("[DEBUG WINDOW] Creating new interpreter for thread " + aThreadId);
        self._state = "Debugging";
        objj_msgSend(self._runButton, "setEnabled:", NO);
        objj_msgSend(self._debugButton, "setEnabled:", NO);
        objj_msgSend(self._newMessagePanel, "setHidden:", YES);
        objj_msgSend(self._viewMessagePanel, "setHidden:", NO);
        objj_msgSend(self._interpreterAPI, "interpreterId:threadId:", anInterpreterId, aThreadId);
    }
    return self;
}
,["id","id","id","id","id","id"]), new objj_method(sel_getUid("run"), function $DebugWindow__run(self, _cmd)
{
    var initialMessage = objj_msgSend(self._newMessagePanel, "buildJsonMessage");
    objj_msgSend(self._newMessagePanel, "setHidden:", YES);
    objj_msgSend(self._interpreterAPI, "run:", initialMessage);
    self._state = "Running";
    objj_msgSend(self._runButton, "setEnabled:", NO);
    objj_msgSend(self._debugButton, "setEnabled:", NO);
}
,["void"]), new objj_method(sel_getUid("debug"), function $DebugWindow__debug(self, _cmd)
{
    var initialMessage = objj_msgSend(self._newMessagePanel, "buildJsonMessage");
    objj_msgSend(self._newMessagePanel, "setHidden:", YES);
    objj_msgSend(self._interpreterAPI, "debug:", initialMessage);
    self._state = "Debugging";
    objj_msgSend(self._runButton, "setEnabled:", NO);
    objj_msgSend(self._debugButton, "setEnabled:", NO);
}
,["void"]), new objj_method(sel_getUid("step"), function $DebugWindow__step(self, _cmd)
{
    objj_msgSend(self._drawing, "unselectAll");
    objj_msgSend(self._interpreterAPI, "step");
    if (self._currentProcessor != nil)
    {
        objj_msgSend(self._currentProcessor, "unhighlight");
    }
}
,["void"]), new objj_method(sel_getUid("resume"), function $DebugWindow__resume(self, _cmd)
{
    objj_msgSend(self._drawing, "unselectAll");
    objj_msgSend(self._interpreterAPI, "resume");
    objj_msgSend(self._stepButton, "setEnabled:", NO);
    objj_msgSend(self._resumeButton, "setEnabled:", NO);
    if (self._currentProcessor != nil)
    {
        objj_msgSend(self._currentProcessor, "unhighlight");
    }
}
,["void"]), new objj_method(sel_getUid("drawRect:on:"), function $DebugWindow__drawRect_on_(self, _cmd, rect, context)
{
    CGContextSetFillColor(context, objj_msgSend(CPColor, "lightGrayColor"));
    CGContextFillRect(context, objj_msgSend(self, "bounds"));
}
,["void","CGRect","id"]), new objj_method(sel_getUid("createInterpreter:"), function $DebugWindow__createInterpreter_(self, _cmd, anInterpreterId)
{
    objj_msgSend(self._drawing, "registerDebug:for:", self, anInterpreterId + "#1");
}
,["void","id"]), new objj_method(sel_getUid("process:"), function $DebugWindow__process_(self, _cmd, aMessage)
{
    var eventType = aMessage.eventType;
    var currentMessage = aMessage.currentMessage;
    var processorId = aMessage.processorId;
    var jsonCurrentMessage = JSON.stringify(aMessage);
    var exception = aMessage.exception;
    CPLog.debug("[DEBUG WINDOW] Processor id " + processorId);
    CPLog.debug("[DEBUG WINDOW] " + jsonCurrentMessage);
    objj_msgSend(self._label, "setStringValue:", jsonCurrentMessage);
    objj_msgSend(self._label, "sizeToFit");
    if (objj_msgSend(eventType, "isEqualToString:", "breakpoint"))
    {
        objj_msgSend(self, "processBreakpoint:processorId:", currentMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "before-step"))
    {
        objj_msgSend(self, "processBeforeStep:processorId:", currentMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "after-step"))
    {
        objj_msgSend(self, "processAfterStep:processorId:", currentMessage, processorId);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "uncaught-exception"))
    {
        objj_msgSend(self, "processUncaughtException:processorId:exception:", currentMessage, processorId, exception);
    }
    else if (objj_msgSend(eventType, "isEqualToString:", "finish-interpretation"))
    {
        objj_msgSend(self, "processFinish:", currentMessage);
    }
}
,["void","id"]), new objj_method(sel_getUid("processUncaughtException:processorId:exception:"), function $DebugWindow__processUncaughtException_processorId_exception_(self, _cmd, aMessage, processorId, anException)
{
    objj_msgSend(self._stepButton, "setEnabled:", NO);
    objj_msgSend(self._resumeButton, "setEnabled:", NO);
    objj_msgSend(self, "showMessage:", aMessage);
    var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
    self._currentProcessor = figure;
    CPLog.debug("[DEBUG WINDOW] >> processUncaughtException " + self._currentProcessor);
    if (figure != nil)
    {
        objj_msgSend(self, "setTitle:", "Uncaught exception at " + objj_msgSend(objj_msgSend(figure, "model"), "propertyValue:", "Name"));
        objj_msgSend(self._drawing, "unselectAll");
        objj_msgSend(figure, "select");
        objj_msgSend(self._drawing, "selectedFigure:", figure);
        objj_msgSend(figure, "highlight");
    }
}
,["void","id","id","id"]), new objj_method(sel_getUid("processFinish:"), function $DebugWindow__processFinish_(self, _cmd, aMessage)
{
    if (objj_msgSend(objj_msgSend(self._interpreterAPI, "threadId"), "isEqual:", "1"))
    {
        objj_msgSend(self, "setTitle:", "Finished");
        if (self._currentFigure != nil)
        {
            objj_msgSend(self._currentFigure, "fadeOut");
        }
        objj_msgSend(self, "showMessage:", aMessage);
    }
    else
    {
        objj_msgSend(self, "close");
    }
}
,["void","id"]), new objj_method(sel_getUid("processAfterStep:processorId:"), function $DebugWindow__processAfterStep_processorId_(self, _cmd, aMessage, processorId)
{
    if (self._development)
    {
        var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleRight");
            var shiftedPoint = CGPointMake(point.x + 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
        objj_msgSend(self, "showMessage:", aMessage);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("processBeforeStep:processorId:"), function $DebugWindow__processBeforeStep_processorId_(self, _cmd, aMessage, processorId)
{
    if (self._development)
    {
        var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
        if (figure != nil)
        {
            var point = objj_msgSend(figure, "middleLeft");
            var shiftedPoint = CGPointMake(point.x - 8, point.y - 4);
            objj_msgSend(self, "showMarkAt:", shiftedPoint);
        }
        objj_msgSend(self, "showMessage:", aMessage);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("processBreakpoint:processorId:"), function $DebugWindow__processBreakpoint_processorId_(self, _cmd, aMessage, processorId)
{
    if (objj_msgSend(self._state, "isEqualToString:", "Debugging"))
    {
        objj_msgSend(self._stepButton, "setEnabled:", YES);
        objj_msgSend(self._resumeButton, "setEnabled:", YES);
        objj_msgSend(self, "showMessage:", aMessage);
        var figure = objj_msgSend(self._drawing, "processorFor:", processorId);
        self._currentProcessor = figure;
        CPLog.debug("[DEBUG WINDOW] >> processBreakpoint" + self._currentProcessor);
        if (figure != nil)
        {
            objj_msgSend(self, "setTitle:", "Stopped at " + objj_msgSend(objj_msgSend(figure, "model"), "propertyValue:", "Name"));
            objj_msgSend(self._drawing, "unselectAll");
            objj_msgSend(figure, "select");
            objj_msgSend(self._drawing, "selectedFigure:", figure);
            objj_msgSend(figure, "highlight");
        }
    }
}
,["void","id","id"]), new objj_method(sel_getUid("evaluationResult:currentMessage:"), function $DebugWindow__evaluationResult_currentMessage_(self, _cmd, aResult, aMessage)
{
    if (objj_msgSend(self._state, "isEqualToString:", "Debugging"))
    {
        objj_msgSend(self._viewMessagePanel, "evaluationResult:", aResult);
        objj_msgSend(self, "showMessage:", aMessage);
    }
}
,["void","id","id"]), new objj_method(sel_getUid("showMessage:"), function $DebugWindow__showMessage_(self, _cmd, aMessage)
{
    objj_msgSend(self._viewMessagePanel, "setMessage:", aMessage);
    objj_msgSend(self._viewMessagePanel, "setHidden:", NO);
}
,["void","id"]), new objj_method(sel_getUid("showMarkAt:"), function $DebugWindow__showMarkAt_(self, _cmd, aPoint)
{
    if (self._currentFigure != nil)
    {
        objj_msgSend(self._currentFigure, "fadeOut");
        objj_msgSend(self._currentFigure, "removeFromSuperview");
    }
    CPLog.debug("Showing debug mark at: " + aPoint);
    var stopFigure = objj_msgSend(ImageFigure, "initializeWithImage:x:y:", "Resources/stop.gif", aPoint.x, aPoint.y);
    objj_msgSend(self._drawing, "addFigure:", stopFigure);
    self._currentFigure = stopFigure;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:contextId:elementId:drawing:"), function $DebugWindow__newAt_contextId_elementId_drawing_(self, _cmd, aPoint, aContextId, anElementId, aDrawing)
{
    return objj_msgSend(self, "newAt:contextId:elementId:drawing:interpreterId:threadId:", aPoint, aContextId, anElementId, aDrawing, nil, nil);
}
,["DebugWindow","id","id","id","id"]), new objj_method(sel_getUid("newAt:contextId:elementId:drawing:interpreterId:threadId:"), function $DebugWindow__newAt_contextId_elementId_drawing_interpreterId_threadId_(self, _cmd, aPoint, aContextId, anElementId, aDrawing, anInterpreterId, aThreadId)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 663, 222);
    var window = objj_msgSend(self, "alloc");
    objj_msgSend(window, "initWithContentRect:styleMask:", frame, CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask);
    return objj_msgSend(window, "initializeWithContextId:elementId:drawing:interpreterId:threadId:", aContextId, anElementId, aDrawing, anInterpreterId, aThreadId);
}
,["DebugWindow","id","id","id","id","id","id"])]);
}