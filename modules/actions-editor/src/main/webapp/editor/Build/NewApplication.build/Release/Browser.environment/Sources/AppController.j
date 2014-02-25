@STATIC;1.0;I;21;Foundation/CPObject.jI;17;CupDraw/CupDraw.ji;17;Actions/Actions.jt;1773;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("CupDraw/CupDraw.j", NO);objj_executeFile("Actions/Actions.j", YES);{var the_class = objj_allocateClassPair(CPObject, "AppController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("drawing")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("applicationDidFinishLaunching:"), function $AppController__applicationDidFinishLaunching_(self, _cmd, aNotification)
{
    CPLogRegister(CPLogPopup);
    var theWindow = objj_msgSend(objj_msgSend(CPWindow, "alloc"), "initWithContentRect:styleMask:", CGRectMakeZero(), CPBorderlessBridgeWindowMask),
        contentView = objj_msgSend(theWindow, "contentView");
    objj_msgSend(self, "initializeDrawing:window:", contentView, theWindow);
    objj_msgSend(theWindow, "orderFront:", self);
    objj_msgSend(theWindow, "makeFirstResponder:", self.drawing);
}
,["void","CPNotification"]), new objj_method(sel_getUid("initializeDrawing:window:"), function $AppController__initializeDrawing_window_(self, _cmd, contentView, theWindow)
{
    var contextId = objj_msgSend(Actions, "contextId");
    self.drawing = objj_msgSend(ActionsDrawing, "frame:contextId:", objj_msgSend(contentView, "bounds"), contextId);
    objj_msgSend(Actions, "drawing:", self.drawing);
    objj_msgSend(self.drawing, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    objj_msgSend(contentView, "addSubview:", self.drawing);
    var mode = objj_msgSend(Actions, "modeParam");
    if (mode != nil && objj_msgSend(mode, "isEqual:", "deployment"))
    {
        objj_msgSend(Actions, "mode:", objj_msgSend(DeploymentMode, "new"));
    }
    objj_msgSend(Actions, "load:", self.drawing);
}
,["void","id","id"])]);
}