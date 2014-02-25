@STATIC;1.0;t;8030;{var the_class = objj_allocateClassPair(CPObject, "DeploymentMode"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_timer"), new objj_ivar("_drawing")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DeploymentMode__init(self, _cmd)
{
    self._timer = objj_msgSend(CPTimer, "scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:", 5, self, sel_getUid("updateProfilingInfo:"), nil, YES);
    return self;
}
,["id"]), new objj_method(sel_getUid("updateProfilingInfo:"), function $DeploymentMode__updateProfilingInfo_(self, _cmd, sender)
{
    if (self._drawing != nil)
    {
        objj_msgSend(self._drawing, "updateProfilingInfo");
    }
}
,["void","id"]), new objj_method(sel_getUid("postAddProcessor:"), function $DeploymentMode__postAddProcessor_(self, _cmd, aProcessorFigure)
{
    var bottomRight = objj_msgSend(aProcessorFigure, "bottomRight");
    var performanceLabel = objj_msgSend(LabelFigure, "initializeWithText:at:", "", bottomRight);
    objj_msgSend(performanceLabel, "backgroundColor:", objj_msgSend(CPColor, "blueColor"));
    objj_msgSend(performanceLabel, "foregroundColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(performanceLabel, "setHidden:", YES);
    var magnet = objj_msgSend(Magnet, "newWithSource:target:selector:", aProcessorFigure, performanceLabel, sel_getUid("bottomRight"));
    var drawing = objj_msgSend(aProcessorFigure, "drawing");
    objj_msgSend(aProcessorFigure, "setDataFigure:", performanceLabel);
    objj_msgSend(drawing, "addFigure:", performanceLabel);
}
,["void","id"]), new objj_method(sel_getUid("createMessageSourceStateFigure:drawing:point:"), function $DeploymentMode__createMessageSourceStateFigure_drawing_point_(self, _cmd, aMessageSourceFigure, aDrawing, aPoint)
{
}
,["void","id","id","id"]), new objj_method(sel_getUid("createMessageSourceFigureMenu:menu:"), function $DeploymentMode__createMessageSourceFigureMenu_menu_(self, _cmd, aMessageSourceFigure, contextMenu)
{
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createProcessorFigureMenu:menu:"), function $DeploymentMode__createProcessorFigureMenu_menu_(self, _cmd, aProcessorFigure, contextMenu)
{
    var addProfilerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add profiler", sel_getUid("addProfiler:"), "");
    objj_msgSend(addProfilerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(addProfilerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", addProfilerMenu);
    var removeProfilerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Remove profiler", sel_getUid("removeProfiler:"), "");
    objj_msgSend(removeProfilerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(removeProfilerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", removeProfilerMenu);
    objj_msgSend(contextMenu, "addItem:", objj_msgSend(CPMenuItem, "separatorItem"));
    var addLoggerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add logger", sel_getUid("addLogger:"), "");
    objj_msgSend(addLoggerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(addLoggerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", addLoggerMenu);
    var removeLoggerMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Remove logger", sel_getUid("removeLogger:"), "");
    objj_msgSend(removeLoggerMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(removeLoggerMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", removeLoggerMenu);
    var viewLogMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "View log", sel_getUid("viewLog:"), "");
    objj_msgSend(viewLogMenu, "setTarget:", aProcessorFigure);
    objj_msgSend(viewLogMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", viewLogMenu);
}
,["void","id","CPMenu"]), new objj_method(sel_getUid("createElementFigureMenu:"), function $DeploymentMode__createElementFigureMenu_(self, _cmd, anElementFigure)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementFigure);
    objj_msgSend(anElementFigure, "beforeDeleteMenu:", contextMenu);
    objj_msgSend(anElementFigure, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("createElementConnectionMenu:"), function $DeploymentMode__createElementConnectionMenu_(self, _cmd, anElementConnection)
{
    var contextMenu = objj_msgSend(objj_msgSend(CPMenu, "alloc"), "init");
    objj_msgSend(contextMenu, "setDelegate:", anElementConnection);
    var cacheMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add cache (lazy compute)", sel_getUid("addLazyComputedCache"), "");
    objj_msgSend(cacheMenu, "setTarget:", anElementConnection);
    objj_msgSend(cacheMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", cacheMenu);
    var cache2Menu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add cache (async lazy compute)", sel_getUid("addLazyAutorefreshableCache"), "");
    objj_msgSend(cache2Menu, "setTarget:", anElementConnection);
    objj_msgSend(cache2Menu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", cache2Menu);
    var asyncMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add async operation", sel_getUid("addCache"), "");
    objj_msgSend(asyncMenu, "setTarget:", anElementConnection);
    objj_msgSend(asyncMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", asyncMenu);
    var trackingMenu = objj_msgSend(objj_msgSend(CPMenuItem, "alloc"), "initWithTitle:action:keyEquivalent:", "Add tracking", sel_getUid("addCache"), "");
    objj_msgSend(trackingMenu, "setTarget:", anElementConnection);
    objj_msgSend(trackingMenu, "setEnabled:", YES);
    objj_msgSend(contextMenu, "addItem:", trackingMenu);
    objj_msgSend(anElementConnection, "setMenu:", contextMenu);
}
,["void","id"]), new objj_method(sel_getUid("load:"), function $DeploymentMode__load_(self, _cmd, aDrawing)
{
    self._drawing = aDrawing;
    objj_msgSend(self, "loadGenerator:", aDrawing);
    objj_msgSend(self, "loadDiagramElements:", aDrawing);
    var performanceLabelPoint = CGPointMake(10, 10);
    var performanceLabel = objj_msgSend(LabelFigure, "initializeWithText:at:", "Performance in ms", performanceLabelPoint);
    objj_msgSend(performanceLabel, "backgroundColor:", objj_msgSend(CPColor, "blueColor"));
    objj_msgSend(performanceLabel, "foregroundColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(self._drawing, "addFigure:", performanceLabel);
}
,["void","id"]), new objj_method(sel_getUid("loadDiagramElements:"), function $DeploymentMode__loadDiagramElements_(self, _cmd, drawing)
{
    var contextId = objj_msgSend(Actions, "contextId");
    if (contextId != nil)
    {
        CPLog.debug("[AppController] Context id found!");
        CPLog.debug("[AppController] " + contextId);
        var command = objj_msgSend(LoadActionsCommand, "drawing:", drawing);
        objj_msgSend(command, "execute");
    }
    else
    {
        CPLog.debug("[AppController] Context id NOT found!");
    }
}
,["void","id"]), new objj_method(sel_getUid("loadGenerator:"), function $DeploymentMode__loadGenerator_(self, _cmd, aDrawing)
{
    var libraries = objj_msgSend(DataUtil, "var:", "libraries").libraries;
    var generator = objj_msgSend(DynamicModelGenerator, "new");
    objj_msgSend(aDrawing, "generator:", generator);
    for (var i = 0; i < libraries.length; i++)
    {
        var library = libraries[i];
        var elements = library.elements;
        CPLog.debug("Processing library " + library.name);
        objj_msgSend(generator, "addDefinitions:", elements);
    }
}
,["void","id"])]);
}