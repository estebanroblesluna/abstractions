@STATIC;1.0;t;5718;var ElementNextInChainConnection = "NEXT_IN_CHAIN";
var ElementAllConnection = "ALL";
var ElementChoiceConnection = "CHOICE";
var ElementWireTapConnection = "WIRE_TAP";
{var the_class = objj_allocateClassPair(IconLabelFigure, "ElementFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_delegate")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:iconUrl:"), function $ElementFigure__initWithFrame_iconUrl_(self, _cmd, aFrame, iconUrl)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementFigure").super_class }, "initWithFrame:iconUrl:", aFrame, iconUrl);
    objj_msgSend(objj_msgSend(Actions, "mode"), "createElementFigureMenu:", self);
    return self;
}
,["id","CGRect","id"]), new objj_method(sel_getUid("delegate:"), function $ElementFigure__delegate_(self, _cmd, aDelegate)
{
    self._delegate = aDelegate;
    if (objj_msgSend(self, "elementId") != nil)
    {
        objj_msgSend(self._delegate, "registerElement:for:", self, objj_msgSend(self, "elementId"));
    }
}
,["void","id"]), new objj_method(sel_getUid("deleteFromServer"), function $ElementFigure__deleteFromServer(self, _cmd)
{
    objj_msgSend(objj_msgSend(self, "model"), "deleteFromServer");
}
,["void"]), new objj_method(sel_getUid("deleted"), function $ElementFigure__deleted(self, _cmd)
{
    objj_msgSend(self, "removeMyself");
}
,["void"]), new objj_method(sel_getUid("elementId"), function $ElementFigure__elementId(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self, "model"), "elementId");
}
,["id"]), new objj_method(sel_getUid("nextInChain:"), function $ElementFigure__nextInChain_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Setting next in chain");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementNextInChainConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addAllConnection:"), function $ElementFigure__addAllConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding all connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementAllConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addWireTapConnection:"), function $ElementFigure__addWireTapConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding wire tap connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementWireTapConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("addChoiceConnection:"), function $ElementFigure__addChoiceConnection_(self, _cmd, aConnection)
{
    CPLog.debug("[ElementFigure] Adding choice connection");
    return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "api"), "addConnectionType:to:identificable:", ElementChoiceConnection, objj_msgSend(objj_msgSend(aConnection, "target"), "elementId"), aConnection);
}
,["id","id"]), new objj_method(sel_getUid("nextInChainId:for:"), function $ElementFigure__nextInChainId_for_(self, _cmd, anId, anElementId)
{
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(outConnection, "isKindOfClass:", objj_msgSend(NextInChainConnection, "class")) && objj_msgSend(objj_msgSend(objj_msgSend(outConnection, "target"), "elementId"), "isEqual:", anElementId))
        {
            objj_msgSend(outConnection, "id:", anId);
        }
    }
}
,["void","id","id"]), new objj_method(sel_getUid("elementId:"), function $ElementFigure__elementId_(self, _cmd, anElementId)
{
    CPLog.debug("[ElementFigure] Element created with id " + anElementId);
    if (self._delegate != nil)
    {
        objj_msgSend(self._delegate, "registerElement:for:", self, anElementId);
    }
}
,["void","id"]), new objj_method(sel_getUid("model:"), function $ElementFigure__model_(self, _cmd, aModel)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ElementFigure").super_class }, "model:", aModel);
    objj_msgSend(aModel, "delegate:", self);
}
,["void","id"]), new objj_method(sel_getUid("acceptsNewStartingChain"), function $ElementFigure__acceptsNewStartingChain(self, _cmd)
{
    return true;
}
,["id"]), new objj_method(sel_getUid("acceptsNewEndingChain"), function $ElementFigure__acceptsNewEndingChain(self, _cmd)
{
    return true;
}
,["id"]), new objj_method(sel_getUid("hasNextInChainConnections"), function $ElementFigure__hasNextInChainConnections(self, _cmd)
{
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(outConnection, "isKindOfClass:", objj_msgSend(NextInChainConnection, "class")))
        {
            return true;
        }
    }
    return false;
}
,["id"]), new objj_method(sel_getUid("countConnectionsOfType:"), function $ElementFigure__countConnectionsOfType_(self, _cmd, anElementName)
{
    var count = 0;
    for (var i = 0; i < objj_msgSend(self._outConnections, "count"); i++)
    {
        var outConnection = objj_msgSend(self._outConnections, "objectAtIndex:", i);
        if (objj_msgSend(objj_msgSend(outConnection, "elementName"), "isEqual:", anElementName))
        {
            count++;
        }
    }
    return count;
}
,["id","id"])]);
}