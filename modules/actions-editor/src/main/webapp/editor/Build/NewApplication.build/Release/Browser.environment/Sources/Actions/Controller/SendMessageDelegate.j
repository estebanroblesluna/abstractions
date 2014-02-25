@STATIC;1.0;I;21;Foundation/CPObject.jt;4436;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "SendMessageDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_payloadTF"), new objj_ivar("_isExpressionCB"), new objj_ivar("_tableView"), new objj_ivar("_nameColumn"), new objj_ivar("_propertiesKeys"), new objj_ivar("_propertiesValues")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $SendMessageDelegate__awakeFromCib(self, _cmd)
{
    self._propertiesKeys = objj_msgSend(CPMutableArray, "array");
    self._propertiesValues = objj_msgSend(CPMutableArray, "array");
    objj_msgSend(self._tableView, "setDoubleAction:", sel_getUid("doubleClick:"));
    objj_msgSend(self._tableView, "setDataSource:", self);
    objj_msgSend(self._tableView, "setDelegate:", self);
}
,["void"]), new objj_method(sel_getUid("buildJsonMessage"), function $SendMessageDelegate__buildJsonMessage(self, _cmd)
{
    var payload = objj_msgSend(self._payloadTF, "stringValue");
    var message = {"payload": payload, "properties": []};
    for (var i = 0; i < objj_msgSend(self._propertiesKeys, "count"); i++)
    {
        var key = objj_msgSend(self._propertiesKeys, "objectAtIndex:", i);
        var value = objj_msgSend(self._propertiesValues, "objectAtIndex:", i);
        var property = {"key": key, "value": value};
        message.properties.push(property);
    }
    return message;
}
,["id"]), new objj_method(sel_getUid("interpreterAPI"), function $SendMessageDelegate__interpreterAPI(self, _cmd)
{
    return objj_msgSend(objj_msgSend(self._window, "windowController"), "interpreterAPI");
}
,["id"]), new objj_method(sel_getUid("run:"), function $SendMessageDelegate__run_(self, _cmd, aSender)
{
    var initialMessage = objj_msgSend(self, "buildJsonMessage");
    objj_msgSend(objj_msgSend(self, "interpreterAPI"), "run:", initialMessage);
    objj_msgSend(self._window, "close");
}
,["void","id"]), new objj_method(sel_getUid("debug:"), function $SendMessageDelegate__debug_(self, _cmd, aSender)
{
    var initialMessage = objj_msgSend(self, "buildJsonMessage");
    objj_msgSend(objj_msgSend(self, "interpreterAPI"), "debug:", initialMessage);
    objj_msgSend(self._window, "close");
}
,["void","id"]), new objj_method(sel_getUid("addProperty:"), function $SendMessageDelegate__addProperty_(self, _cmd, aSender)
{
    objj_msgSend(self._propertiesKeys, "addObject:", "new property name");
    objj_msgSend(self._propertiesValues, "addObject:", "new property value");
    objj_msgSend(self, "reloadData");
}
,["void","id"]), new objj_method(sel_getUid("removeProperty:"), function $SendMessageDelegate__removeProperty_(self, _cmd, aSender)
{
    var index = objj_msgSend(self._tableView, "selectedRow");
    objj_msgSend(self._propertiesKeys, "removeObjectAtIndex:", index);
    objj_msgSend(self._propertiesValues, "removeObjectAtIndex:", index);
    objj_msgSend(self, "reloadData");
}
,["void","id"]), new objj_method(sel_getUid("reloadData"), function $SendMessageDelegate__reloadData(self, _cmd)
{
    objj_msgSend(self._tableView, "reloadData");
}
,["void"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $SendMessageDelegate__numberOfRowsInTableView_(self, _cmd, aTableView)
{
    return objj_msgSend(self._propertiesKeys, "count");
}
,["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $SendMessageDelegate__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return objj_msgSend(self._propertiesKeys, "objectAtIndex:", rowIndex);
    }
    else
    {
        return objj_msgSend(self._propertiesValues, "objectAtIndex:", rowIndex);
    }
}
,["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $SendMessageDelegate__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return objj_msgSend(self._propertiesKeys, "replaceObjectAtIndex:withObject:", rowIndex, aValue);
    }
    else
    {
        return objj_msgSend(self._propertiesValues, "replaceObjectAtIndex:withObject:", rowIndex, aValue);
    }
}
,["void","CPTableView","id","CPTableColumn","int"])]);
}