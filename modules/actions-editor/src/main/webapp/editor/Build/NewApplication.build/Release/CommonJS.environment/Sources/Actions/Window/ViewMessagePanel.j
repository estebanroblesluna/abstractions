@STATIC;1.0;t;9006;{var the_class = objj_allocateClassPair(CPView, "ViewMessagePanel"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_tableView"), new objj_ivar("_nameColumn"), new objj_ivar("_valueColumn"), new objj_ivar("_currentMessage"), new objj_ivar("_payloadValue"), new objj_ivar("_expressionValue"), new objj_ivar("_evaluateButton"), new objj_ivar("_expressionResult"), new objj_ivar("_interpreterAPI")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initialize:"), function $ViewMessagePanel__initialize_(self, _cmd, anInterpreterAPI)
{
    self._interpreterAPI = anInterpreterAPI;
    var payloadLabel = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(payloadLabel, "setStringValue:", "Payload:");
    objj_msgSend(payloadLabel, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(payloadLabel, "sizeToFit");
    objj_msgSend(payloadLabel, "setFrameOrigin:", CGPointMake(0, 0));
    objj_msgSend(self, "addSubview:", payloadLabel);
    self._payloadValue = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._payloadValue, "setStringValue:", "");
    objj_msgSend(self._payloadValue, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(self._payloadValue, "sizeToFit");
    objj_msgSend(self._payloadValue, "setFrameOrigin:", CGPointMake(0, 15));
    objj_msgSend(self, "addSubview:", self._payloadValue);
    var propertiesLabel = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(propertiesLabel, "setStringValue:", "Properties:");
    objj_msgSend(propertiesLabel, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(propertiesLabel, "sizeToFit");
    objj_msgSend(propertiesLabel, "setFrameOrigin:", CGPointMake(0, 40));
    objj_msgSend(self, "addSubview:", propertiesLabel);
    var scrollFrame = CGRectMake(0, 60, 65 * 5, 134);
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
    objj_msgSend(self._nameColumn, "setEditable:", NO);
    objj_msgSend(self._tableView, "addTableColumn:", self._nameColumn);
    self._valueColumn = objj_msgSend(objj_msgSend(CPTableColumn, "alloc"), "initWithIdentifier:", "valueColumn");
    objj_msgSend(objj_msgSend(self._valueColumn, "headerView"), "setStringValue:", "Value");
    objj_msgSend(self._valueColumn, "setMinWidth:", 200);
    objj_msgSend(self._valueColumn, "setEditable:", NO);
    objj_msgSend(self._tableView, "addTableColumn:", self._valueColumn);
    objj_msgSend(scrollView, "setDocumentView:", self._tableView);
    objj_msgSend(self._tableView, "setDataSource:", self);
    objj_msgSend(self._tableView, "setDelegate:", self);
    objj_msgSend(self, "addSubview:", scrollView);
    objj_msgSend(scrollView, "setAutoresizingMask:", CPViewWidthSizable);
    var panelFrameSize = objj_msgSend(self, "frameSize");
    var expressionLabel = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(expressionLabel, "setStringValue:", "Expression:");
    objj_msgSend(expressionLabel, "setTextColor:", objj_msgSend(CPColor, "whiteColor"));
    objj_msgSend(expressionLabel, "sizeToFit");
    objj_msgSend(expressionLabel, "setFrameOrigin:", CGPointMake(65 * 5 + 15, 0));
    objj_msgSend(self, "addSubview:", expressionLabel);
    self._expressionValue = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._expressionValue, "setStringValue:", "");
    objj_msgSend(self._expressionValue, "sizeToFit");
    objj_msgSend(self._expressionValue, "setBezeled:", YES);
    objj_msgSend(self._expressionValue, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._expressionValue, "setEditable:", YES);
    objj_msgSend(self._expressionValue, "setFrameOrigin:", CGPointMake(65 * 5 + 15, 25));
    objj_msgSend(self._expressionValue, "setFrameSize:", CGSizeMake(panelFrameSize.width / 2 - 5, 30));
    objj_msgSend(self, "addSubview:", self._expressionValue);
    self._evaluateButton = objj_msgSend(CPButton, "buttonWithTitle:", "Evaluate");
    objj_msgSend(self._evaluateButton, "setButtonType:", CPOnOffButton);
    objj_msgSend(self._evaluateButton, "setBordered:", YES);
    objj_msgSend(self._evaluateButton, "setBezelStyle:", CPRegularSquareBezelStyle);
    objj_msgSend(self._evaluateButton, "setFrameOrigin:", CGPointMake(65 * 5 + 110, 0));
    objj_msgSend(self._evaluateButton, "setTarget:", self);
    objj_msgSend(self._evaluateButton, "sizeToFit");
    objj_msgSend(self._evaluateButton, "setFrameSize:", CGSizeMake(65, 24));
    objj_msgSend(self._evaluateButton, "setAction:", sel_getUid("evaluate"));
    objj_msgSend(self, "addSubview:", self._evaluateButton);
    self._expressionResult = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
    objj_msgSend(self._expressionResult, "setStringValue:", "");
    objj_msgSend(self._expressionResult, "sizeToFit");
    objj_msgSend(self._expressionResult, "setBezeled:", YES);
    objj_msgSend(self._expressionResult, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
    objj_msgSend(self._expressionResult, "setEditable:", YES);
    objj_msgSend(self._expressionResult, "setFrameOrigin:", CGPointMake(65 * 5 + 15, 55));
    objj_msgSend(self._expressionResult, "setFrameSize:", CGSizeMake(panelFrameSize.width / 2 - 5, 130));
    objj_msgSend(self, "addSubview:", self._expressionResult);
}
,["id","id"]), new objj_method(sel_getUid("setMessage:"), function $ViewMessagePanel__setMessage_(self, _cmd, aMessage)
{
    self._currentMessage = aMessage;
    var payload = aMessage.payload;
    if (payload == nil)
    {
        payload = "{null payload}";
    }
    else if (objj_msgSend(payload, "isEqual:", ""))
    {
        payload = "{empty string payload}";
    }
    objj_msgSend(self._payloadValue, "setStringValue:", payload);
    objj_msgSend(self._payloadValue, "sizeToFit");
    objj_msgSend(self, "reloadData");
}
,["void","id"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $ViewMessagePanel__numberOfRowsInTableView_(self, _cmd, aTableView)
{
    if (self._currentMessage == nil)
    {
        return 0;
    }
    else
    {
        return self._currentMessage.properties.length;
    }
}
,["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $ViewMessagePanel__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
{
    if (self._nameColumn == aTableColumn)
    {
        return self._currentMessage.properties[rowIndex].key;
    }
    else
    {
        return self._currentMessage.properties[rowIndex].value;
    }
}
,["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $ViewMessagePanel__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
{
}
,["void","CPTableView","id","CPTableColumn","int"]), new objj_method(sel_getUid("reloadData"), function $ViewMessagePanel__reloadData(self, _cmd)
{
    objj_msgSend(self._tableView, "reloadData");
}
,["void"]), new objj_method(sel_getUid("evaluationResult:"), function $ViewMessagePanel__evaluationResult_(self, _cmd, aResult)
{
    objj_msgSend(self._expressionResult, "setStringValue:", aResult);
}
,["void","id"]), new objj_method(sel_getUid("evaluate"), function $ViewMessagePanel__evaluate(self, _cmd)
{
    var expression = objj_msgSend(self._expressionValue, "stringValue");
    objj_msgSend(self._interpreterAPI, "evaluate:", expression);
}
,["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:api:"), function $ViewMessagePanel__newAt_api_(self, _cmd, aPoint, anInterpreterAPI)
{
    var frame = CGRectMake(aPoint.x, aPoint.y, 65 * 10, 300);
    var panel = objj_msgSend(self, "new");
    objj_msgSend(panel, "setFrame:", frame);
    objj_msgSend(panel, "initialize:", anInterpreterAPI);
    return panel;
}
,["ViewMessagePanel","id","id"])]);
}