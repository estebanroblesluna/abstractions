@STATIC;1.0;t;2850;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateMessageSourceTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_generator")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateMessageSourceTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{
    objj_msgSend(objj_msgSend(self, "class"), "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, aDrawing, self._elementName, YES, nil, nil, self);
}
,["void","id","id"]), new objj_method(sel_getUid("generator"), function $CreateMessageSourceTool__generator(self, _cmd)
{
    return self._generator;
}
,["id"]), new objj_method(sel_getUid("generator:"), function $CreateMessageSourceTool__generator_(self, _cmd, aGenerator)
{
    self._generator = aGenerator;
}
,["void","id"]), new objj_method(sel_getUid("elementName:"), function $CreateMessageSourceTool__elementName_(self, _cmd, anElementName)
{
    self._elementName = anElementName;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:elementName:generator:"), function $CreateMessageSourceTool__drawing_elementName_generator_(self, _cmd, aDrawing, anElementName, aGenerator)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateMessageSourceTool").super_class }, "drawing:", aDrawing);
    objj_msgSend(tool, "elementName:", anElementName);
    objj_msgSend(tool, "generator:", aGenerator);
    return tool;
}
,["id","Drawing","id","id"]), new objj_method(sel_getUid("createFigureAt:on:elementName:edit:elementId:initialProperties:tool:"), function $CreateMessageSourceTool__createFigureAt_on_elementName_edit_elementId_initialProperties_tool_(self, _cmd, aPoint, aDrawing, elementName, activateEdit, elementId, properties, aTool)
{
    var generator = objj_msgSend(aTool, "generator");
    var iconUrl = objj_msgSend(generator, "icon:", elementName);
    var contextId = objj_msgSend(aDrawing, "contextId");
    var newFigure = objj_msgSend(MessageSourceFigure, "newAt:iconUrl:", aPoint, iconUrl);
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", elementName, elementId, contextId, properties, false);
    objj_msgSend(newFigure, "model:", newModel);
    objj_msgSend(newFigure, "checkModelFeature:", "name");
    objj_msgSend(aDrawing, "addMessageSource:", newFigure);
    objj_msgSend(aTool, "activateSelectionTool");
    if (activateEdit && aTool != nil)
    {
        objj_msgSend(newFigure, "switchToEditMode");
    }
    objj_msgSend(objj_msgSend(Actions, "mode"), "createMessageSourceStateFigure:drawing:point:", newFigure, aDrawing, aPoint);
    return newFigure;
}
,["void","id","id","id","id","id","id","id"])]);
}