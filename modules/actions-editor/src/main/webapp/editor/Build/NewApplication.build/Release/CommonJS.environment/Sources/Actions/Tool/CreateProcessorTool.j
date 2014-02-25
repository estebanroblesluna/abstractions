@STATIC;1.0;t;3084;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateProcessorTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_generator")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateProcessorTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{
    objj_msgSend(objj_msgSend(self, "class"), "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, aDrawing, self._elementName, YES, nil, nil, self);
}
,["void","id","id"]), new objj_method(sel_getUid("generator"), function $CreateProcessorTool__generator(self, _cmd)
{
    return self._generator;
}
,["id"]), new objj_method(sel_getUid("generator:"), function $CreateProcessorTool__generator_(self, _cmd, aGenerator)
{
    self._generator = aGenerator;
}
,["void","id"]), new objj_method(sel_getUid("elementName:"), function $CreateProcessorTool__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:elementName:generator:"), function $CreateProcessorTool__drawing_elementName_generator_(self, _cmd, aDrawing, anElementName, aGenerator)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateProcessorTool").super_class }, "drawing:", aDrawing);
    objj_msgSend(tool, "elementName:", anElementName);
    objj_msgSend(tool, "generator:", aGenerator);
    return tool;
}
,["id","Drawing","id","id"]), new objj_method(sel_getUid("createFigureAt:on:elementName:edit:elementId:initialProperties:tool:"), function $CreateProcessorTool__createFigureAt_on_elementName_edit_elementId_initialProperties_tool_(self, _cmd, aPoint, aDrawing, elementName, activateEdit, elementId, properties, aTool)
{
    var generator = objj_msgSend(aTool, "generator");
    var iconUrl = objj_msgSend(generator, "icon:", elementName);
    var contextId = objj_msgSend(aDrawing, "contextId");
    var newFigure = objj_msgSend(ProcessorFigure, "newAt:iconUrl:", aPoint, iconUrl);
    var hasBreakpoint = objj_msgSend(properties, "objectForKey:", "_breakpoint");
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", elementName, elementId, contextId, properties, hasBreakpoint);
    var stateFigure = objj_msgSend(ElementStateFigure, "newAt:elementModel:", aPoint, newModel);
    var magnet = objj_msgSend(Magnet, "newWithSource:target:selector:", newFigure, stateFigure, sel_getUid("topLeft"));
    objj_msgSend(newFigure, "model:", newModel);
    objj_msgSend(newFigure, "checkModelFeature:", "name");
    objj_msgSend(newModel, "changed");
    objj_msgSend(aDrawing, "addProcessor:", newFigure);
    objj_msgSend(aDrawing, "addFigure:", stateFigure);
    objj_msgSend(aTool, "activateSelectionTool");
    if (activateEdit && aTool != nil)
    {
        objj_msgSend(newFigure, "switchToEditMode");
    }
    return newFigure;
}
,["void","id","id","id","id","id","id","id"])]);
}