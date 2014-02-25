@STATIC;1.0;t;5364;{var the_class = objj_allocateClassPair(AbstractCreateConnectionTool, "CreateElementConnectionTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_elementName"), new objj_ivar("_acceptedSourceTypes"), new objj_ivar("_acceptedSourceMax"), new objj_ivar("_acceptedTargetTypes"), new objj_ivar("_acceptedTargetMax")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("acceptsNewStartingConnection:"), function $CreateElementConnectionTool__acceptsNewStartingConnection_(self, _cmd, aFigure)
{
    if (aFigure == nil)
    {
        return false;
    }
    if (!objj_msgSend(aFigure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")) || self._acceptedSourceTypes != nil && !objj_msgSend(self._acceptedSourceTypes, "containsObject:", objj_msgSend(objj_msgSend(aFigure, "model"), "elementName")))
    {
        CPLog.debug("[CreateElementConnectionTool] Not an accepted source element");
        return false;
    }
    var count = objj_msgSend(aFigure, "countConnectionsOfType:", self._elementName);
    if (count > self._acceptedSourceMax)
    {
        return false;
    }
    return true;
}
,["id","id"]), new objj_method(sel_getUid("acceptsNewEndingConnection:"), function $CreateElementConnectionTool__acceptsNewEndingConnection_(self, _cmd, aFigure)
{
    if (aFigure == nil)
    {
        return false;
    }
    if (!objj_msgSend(aFigure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")) || self._acceptedTargetTypes != nil && !objj_msgSend(self._acceptedTargetTypes, "containsObject:", objj_msgSend(objj_msgSend(aFigure, "model"), "elementName")))
    {
        CPLog.debug("[CreateElementConnectionTool] Not an accepted target element");
        return false;
    }
    var count = objj_msgSend(aFigure, "countConnectionsOfType:", self._elementName);
    if (count > self._acceptedTargetMax)
    {
        return false;
    }
    return true;
}
,["id","id"]), new objj_method(sel_getUid("postConnectionCreated:"), function $CreateElementConnectionTool__postConnectionCreated_(self, _cmd, aConnectionFigure)
{
    objj_msgSend(self, "postConnectionCreated:figureId:properties:", aConnectionFigure, ELEMENT_FAKE_ID, nil);
}
,["void","Connection"]), new objj_method(sel_getUid("postConnectionCreated:figureId:properties:"), function $CreateElementConnectionTool__postConnectionCreated_figureId_properties_(self, _cmd, aConnectionFigure, anID, aSetOfProperties)
{
    var generator = objj_msgSend(self._drawing, "generator");
    var contextId = objj_msgSend(self._drawing, "contextId");
    var color = objj_msgSend(generator, "color:", self._elementName);
    var source = objj_msgSend(aConnectionFigure, "source");
    var target = objj_msgSend(aConnectionFigure, "target");
    var targetId = objj_msgSend(target, "elementId");
    var newModel = objj_msgSend(generator, "modelFor:elementId:contextId:initialProperties:hasBreakpoint:", self._elementName, anID, contextId, aSetOfProperties, false);
    if (objj_msgSend(ELEMENT_FAKE_ID, "isEqual:", anID))
    {
        objj_msgSend(objj_msgSend(objj_msgSend(source, "model"), "api"), "addConnectionType:to:identificable:", self._elementName, targetId, objj_msgSend(newModel, "api"));
    }
    objj_msgSend(aConnectionFigure, "model:", newModel);
    objj_msgSend(newModel, "delegate:", aConnectionFigure);
    objj_msgSend(aConnectionFigure, "foregroundColor:", objj_msgSend(CPColor, "colorWithHexString:", color));
}
,["void","Connection","id","id"]), new objj_method(sel_getUid("elementName:"), function $CreateElementConnectionTool__elementName_(self, _cmd, aValue)
{
    self._elementName = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedSourceTypes:"), function $CreateElementConnectionTool__acceptedSourceTypes_(self, _cmd, aValue)
{
    self._acceptedSourceTypes = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedSourceMax:"), function $CreateElementConnectionTool__acceptedSourceMax_(self, _cmd, aValue)
{
    self._acceptedSourceMax = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedTargetTypes:"), function $CreateElementConnectionTool__acceptedTargetTypes_(self, _cmd, aValue)
{
    self._acceptedTargetTypes = aValue;
}
,["void","id"]), new objj_method(sel_getUid("acceptedTargetMax:"), function $CreateElementConnectionTool__acceptedTargetMax_(self, _cmd, aValue)
{
    self._acceptedTargetMax = aValue;
}
,["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:acceptedSourceTypes:acceptedSourceMax:acceptedTargetTypes:acceptedTargetMax:elementName:"), function $CreateElementConnectionTool__drawing_acceptedSourceTypes_acceptedSourceMax_acceptedTargetTypes_acceptedTargetMax_elementName_(self, _cmd, aDrawing, aListOfSources, sourceMax, aListOfTargets, targetMax, elementName)
{
    var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("CreateElementConnectionTool").super_class }, "drawing:figure:", aDrawing, objj_msgSend(ElementConnection, "class"));
    objj_msgSend(tool, "acceptedSourceTypes:", aListOfSources);
    objj_msgSend(tool, "acceptedSourceMax:", sourceMax);
    objj_msgSend(tool, "acceptedTargetTypes:", aListOfTargets);
    objj_msgSend(tool, "acceptedTargetMax:", targetMax);
    objj_msgSend(tool, "elementName:", elementName);
    return tool;
}
,["id","Drawing","id","id","id","id","id"])]);
}