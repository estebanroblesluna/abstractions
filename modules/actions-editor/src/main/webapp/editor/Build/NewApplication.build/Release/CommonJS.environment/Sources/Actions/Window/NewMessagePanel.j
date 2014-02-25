@STATIC;1.0;t;9068;{var the_class = objj_allocateClassPair(CPView, "NewMessagePanel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_tableView"), new objj_ivar("_nameColumn"), new objj_ivar("_valueColumn"), new objj_ivar("_addProperty"), new objj_ivar("_removeProperty"), new objj_ivar("_payloadValue"), new objj_ivar("_propertiesKeys"), new objj_ivar("_propertiesValues")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initialize"), function $NewMessagePanel__initialize(self, _cmd)
{
    self._propertiesKeys = objj_msgSend(CPMutableArray, "array");
    self._propertiesValues = objj_msgSend(CPMutableArray, "array");
    var payloadLabel = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(payloadLabel, "setStringValue:", "Initial payload:");
    objj_msgSend(payloadLabel, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(payloadLabel, "sizeToFit");
    objj_msgSend(payloadLabel, "setFrameOrigin:", CGPointMake(0, 0));
    objj_msgSend(self, "addSubview:", payloadLabel);
    self._payloadValue = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._payloadValue, "setStringValue:", "Insert payload here...");
    objj_msgSend(self._payloadValue, "setBezeled:", YES);
    objj_msgSend(self._payloadValue, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._payloadValue, "setEditable:", YES);
    objj_msgSend(self._payloadValue, "sizeToFit");
    var panelFrameSize = objj_msgSend(self, "frameSize");
    var payloadValueFrameSize = objj_msgSend(self._payloadValue, "frameSize");
    objj_msgSend(self._payloadValue, "setFrameSize:", CGSizeMake(panelFrameSize.width, payloadValueFrameSize.height));
    objj_msgSend(self._payloadValue, "setFrameOrigin:", CGPointMake(0, 15));
    objj_msgSend(self, "addSubview:", self._payloadValue);
    self._addProperty = objj_msgSend(CPButton, "buttonWithTitle:", "+");
    objj_msgSend(self._addProperty, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._addProperty, "setBordered:", YES);
    objj_msgSend(self._addProperty, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._addProperty, "setAutoresizingMask:", CPViewMinXMargin);
    objj_msgSend(self._addProperty, "setTarget:", self);
    objj_msgSend(self._addProperty, "sizeToFit");
    var addPropertyFrameSize = objj_msgSend(self._addProperty, "frameSize");
    objj_msgSend(self._addProperty, "setFrameOrigin:", CGPointMake(panelFrameSize.width - (addPropertyFrameSize.width + 1) * 2, 50));
    objj_msgSend(self._addProperty, "setAction:", sel_getUid("addProperty"));
    objj_msgSend(self, "addSubview:", self._addProperty);
    self._removeProperty = objj_msgSend(CPButton, "buttonWithTitle:", "-");
    objj_msgSend(self._removeProperty, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._removeProperty, "setBordered:", YES);
    objj_msgSend(self._removeProperty, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._removeProperty, "setFrameOrigin:", CGPointMake(panelFrameSize.width - (30 + 1) * 1, 50));
    objj_msgSend(self._removeProperty, "setAutoresizingMask:", CPViewMinXMargin);
    objj_msgSend(self._removeProperty, "setTarget:", self);
    objj_msgSend(self._removeProperty, "sizeToFit");
    objj_msgSend(self._removeProperty, "setFrameSize:", CGSizeMake(addPropertyFrameSize.width, addPropertyFrameSize.height));
    objj_msgSend(self._removeProperty, "setFrameOrigin:", CGPointMake(panelFrameSize.width - (addPropertyFrameSize.width + 1) * 1, 50));
    objj_msgSend(self._removeProperty, "setAction:", sel_getUid("removeProperty"));
    objj_msgSend(self, "addSubview:", self._removeProperty);
    var propertiesLabel = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(propertiesLabel, "setStringValue:", "Initial properties:");
    objj_msgSend(propertiesLabel, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(propertiesLabel, "sizeToFit");
    objj_msgSend(propertiesLabel, "setFrameOrigin:", CGPointMake(0, 50));
    objj_msgSend(self, "addSubview:", propertiesLabel);
    var scrollFrame = CGRectMake(0, 80, 65 * 10, 115);
    var scrollView = objj_msgSend(objj_msgSend(CPScrollView, "alloc"), "initWithFrame:", scrollFrame);
    objj_msgSend(scrollView, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    objj_msgSend(scrollView, "setAutohidesScrollers:", YES);
    self._tableView = objj_msgSend(objj_msgSend(CPTableView, "alloc"), "initWithFrame:", objj_msgSend(scrollView, "bounds"));
    objj_msgSend(self._tableView, "setDoubleAction:", sel_getUid("doubleClick:"));
    objj_msgSend(self._tableView, "setUsesAlternatingRowBackgroundColors:", YES);
    objj_msgSend(self._tableView, "setAutoresizingMask:", CPViewWidthSizable | CPViewHeightSizable);
    self._nameColumn = objj_msgSend(objj_msgSend(CPTableColumn, "alloc"), "initWithIdentifier:", "nameColumn");
    objj_msgSend(objj_msgSend(self._nameColumn, "headerView"), "setStringValue:", "Name");
    objj_msgSend(self._nameColumn, "setMinWidth:", 100);
    objj_msgSend(self._nameColumn, "setEditable:", YES);
    objj_msgSend(self._tableView, "addTableColumn:", self._nameColumn);
    self._valueColumn = objj_msgSend(objj_msgSend(CPTableColumn, "alloc"), "initWithIdentifier:", "valueColumn");
    objj_msgSend(objj_msgSend(self._valueColumn, "headerView"), "setStringValue:", "Value");
    objj_msgSend(self._valueColumn, "setMinWidth:", 400);
    objj_msgSend(self._valueColumn, "setEditable:", YES);
    objj_msgSend(self._tableView, "addTableColumn:", self._valueColumn);
    objj_msgSend(scrollView, "setDocumentView:", self._tableView);
    objj_msgSend(self._tableView, "setDataSource:", self);
    objj_msgSend(self._tableView, "setDelegate:", self);
    objj_msgSend(self, "addSubview:", scrollView);
    objj_msgSend(scrollView, "setAutoresizingMask:", CPViewWidthSizable);
}
,["id"]), new objj_method(sel_getUid("buildJsonMessage"), function $NewMessagePanel__buildJsonMessage(self, _cmd)
{
    var payload = objj_msgSend(self._payloadValue, "stringValue");
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
,["id"]), new objj_method(sel_getUid("addProperty"), function $NewMessagePanel__addProperty(self, _cmd)
{
    objj_msgSend(self._propertiesKeys, "addObject:", "new property name");
    objj_msgSend(self._propertiesValues, "addObject:", "new property value");
    objj_msgSend(self, "reloadData");
}
,["void"]), new objj_method(sel_getUid("removeProperty"), function $NewMessagePanel__removeProperty(self, _cmd)
{
    var index = objj_msgSend(self._tableView, "selectedRow");
    objj_msgSend(self._propertiesKeys, "removeObjectAtIndex:", index);
    objj_msgSend(self._propertiesValues, "removeObjectAtIndex:", index);
    objj_msgSend(self, "reloadData");
}
,["void"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $NewMessagePanel__numberOfRowsInTableView_(self, _cmd, aTableView)
{
    return objj_msgSend(self._propertiesKeys, "count");
}
,["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $NewMessagePanel__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
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
,["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $NewMessagePanel__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
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
,["void","CPTableView","id","CPTableColumn","int"]), new objj_method(sel_getUid("reloadData"), function $NewMessagePanel__reloadData(self, _cmd)
{
    objj_msgSend(self._tableView, "reloadData");
}
,["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $NewMessagePanel__newAt_(self, _cmd, aPoint)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 65 * 10, 300);
    var panel = objj_msgSend(self, "new");
    objj_msgSend(panel, "setFrame:", frame);
    objj_msgSend(panel, "initialize");
    return panel;
}
,["NewMessagePanel","id"])]);
}