@STATIC;1.0;t;6633;{var the_class = objj_allocateClassPair(Command, "LoadActionsCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $LoadActionsCommand__execute(self, _cmd)
{
    var diagramRepresentation = objj_msgSend(DataUtil, "var:", "diagramRepresentation");
    if (diagramRepresentation != nil)
    {
        CPLog.debug("[LoadActionsCommand] Diagram representation found!");
        var contextDefinition = diagramRepresentation;
        objj_msgSend(self, "processObjectDefinitions:", contextDefinition.definitions);
    }
    return;
}
,["void"]), new objj_method(sel_getUid("processResult:"), function $LoadActionsCommand__processResult_(self, _cmd, aContextDefinitionRoot)
{
    var contextDefinition = jQuery.parseJSON(aContextDefinitionRoot.contextdefinition);
    objj_msgSend(self, "processObjectDefinitions:", contextDefinition.definitions);
}
,["void","id"]), new objj_method(sel_getUid("processObjectDefinitions:"), function $LoadActionsCommand__processObjectDefinitions_(self, _cmd, definitions)
{
    CPLog.debug("[LoadActionsCommand] Processing " + definitions.length + " definitions");
    var connections = objj_msgSend(CPMutableArray, "array");
    for (var i = 0; i < definitions.length; i++)
    {
        var objectDefinition = definitions[i];
        var elementName = objectDefinition.name;
        CPLog.debug("[LoadActionsCommand] Processing " + elementName);
        if (objj_msgSend(elementName, "hasSuffix:", "_CONNECTION"))
        {
            CPLog.debug("[LoadActionsCommand] Saving connection for later process");
            objj_msgSend(connections, "addObject:", objectDefinition);
        }
        else
        {
            var elementId = objectDefinition.id;
            var x = objectDefinition.properties["x"];
            var y = objectDefinition.properties["y"];
            x = x == nil ? 0 : objj_msgSend(x, "intValue");
            y = y == nil ? 0 : objj_msgSend(y, "intValue");
            var properties = objj_msgSend(CPDictionary, "dictionaryWithJSObject:", objectDefinition.properties);
            var aPoint = CGPointMake(x, y);
            var figure = nil;
            if (objj_msgSend(self, "isProcessor:", elementName) || objj_msgSend(self, "isRouter:", elementName))
            {
                CPLog.debug("[LoadActionsCommand] Adding processor or router");
                var tool = objj_msgSend(CreateProcessorTool, "drawing:elementName:generator:", self._drawing, elementName, objj_msgSend(self._drawing, "generator"));
                figure = objj_msgSend(CreateProcessorTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, self._drawing, elementName, NO, elementId, properties, tool);
            }
            else if (objj_msgSend(self, "isMessageSource:", elementName))
            {
                CPLog.debug("[LoadActionsCommand] Adding message source");
                var tool = objj_msgSend(CreateMessageSourceTool, "drawing:elementName:generator:", self._drawing, elementName, objj_msgSend(self._drawing, "generator"));
                figure = objj_msgSend(CreateMessageSourceTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", aPoint, self._drawing, elementName, NO, elementId, properties, tool);
            }
            if (figure != nil)
            {
                objj_msgSend(self._drawing, "registerElement:for:", figure, elementId);
            }
        }
    }
    for (var i = 0; i < connections.length; i++)
    {
        CPLog.debug("[LoadActionsCommand] Post processing connection");
        var connection = connections[i];
        objj_msgSend(self, "processConnection:", connection);
    }
}
,["void","id"]), new objj_method(sel_getUid("isRouter:"), function $LoadActionsCommand__isRouter_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isRouter:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("isProcessor:"), function $LoadActionsCommand__isProcessor_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isProcessor:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("isMessageSource:"), function $LoadActionsCommand__isMessageSource_(self, _cmd, anElementName)
{
    var generator = objj_msgSend(self._drawing, "generator");
    return objj_msgSend(generator, "isMessageSource:", anElementName);
}
,["id","id"]), new objj_method(sel_getUid("processConnection:"), function $LoadActionsCommand__processConnection_(self, _cmd, aConnection)
{
    var sourceId = aConnection.properties["source"].substring("urn:".length);
    var targetId = aConnection.properties["target"].substring("urn:".length);
    var source = objj_msgSend(self._drawing, "processorFor:", sourceId);
    var target = objj_msgSend(self._drawing, "processorFor:", targetId);
    var properties = nil;
    if (aConnection.properties != nil)
    {
        properties = objj_msgSend(CPDictionary, "dictionaryWithJSObject:", aConnection.properties);
    }
    var pointsAsString = aConnection.properties["points"];
    var points = nil;
    if (pointsAsString != nil)
    {
        points = objj_msgSend(CPMutableArray, "array");
        var pointsArray = pointsAsString.split(";");
        for (var j = 0; j < pointsArray.length; j++)
        {
            var pointAsString = pointsArray[j];
            if (!objj_msgSend(pointAsString, "isEqual:", ""))
            {
                var pointParts = pointAsString.split(",");
                var x = pointParts[0];
                var y = pointParts[1];
                var point = CGPointMake(objj_msgSend(x, "intValue"), objj_msgSend(y, "intValue"));
                objj_msgSend(points, "addObject:", point);
            }
        }
    }
    var elementId = aConnection.id;
    var elementName = aConnection.name;
    var connectionTool = objj_msgSend(CreateElementConnectionTool, "drawing:acceptedSourceTypes:acceptedSourceMax:acceptedTargetTypes:acceptedTargetMax:elementName:", self._drawing, nil, 0, nil, 0, elementName);
    var connectionFigure = objj_msgSend(ElementConnection, "source:target:points:", source, target, points);
    objj_msgSend(self._drawing, "addFigure:", connectionFigure);
    objj_msgSend(connectionTool, "postConnectionCreated:figureId:properties:", connectionFigure, elementId, properties);
    if (connectionFigure != nil)
    {
        objj_msgSend(self._drawing, "registerElement:for:", connectionFigure, elementId);
    }
}
,["void","id"])]);
}