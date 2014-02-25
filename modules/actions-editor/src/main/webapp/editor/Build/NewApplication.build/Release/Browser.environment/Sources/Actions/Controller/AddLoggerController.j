@STATIC;1.0;I;21;Foundation/CPObject.jt;2680;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLoggerController"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_beforeConditionalContainerTF"), new objj_ivar("_afterConditionalContainerTF"), new objj_ivar("_beforeContainerTF"), new objj_ivar("_afterContainerTF"), new objj_ivar("_isBeforeConditionalCB"), new objj_ivar("_isAfterConditionalCB"), new objj_ivar("_beforeConditionalTF"), new objj_ivar("_afterConditionalTF"), new objj_ivar("_beforeTF"), new objj_ivar("_afterTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("awakeFromCib"), function $AddLoggerController__awakeFromCib(self, _cmd)
{
    self._beforeConditionalTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._beforeConditionalContainerTF, "frame"));
    self._afterConditionalTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._afterConditionalContainerTF, "frame"));
    self._beforeTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._beforeContainerTF, "frame"));
    self._afterTF = objj_msgSend(ActionsDrawing, "createMultilineText:", objj_msgSend(self._afterContainerTF, "frame"));
    objj_msgSend(self._beforeConditionalContainerTF, "addSubview:", self._beforeConditionalTF);
    objj_msgSend(self._afterConditionalContainerTF, "addSubview:", self._afterConditionalTF);
    objj_msgSend(self._beforeContainerTF, "addSubview:", self._beforeTF);
    objj_msgSend(self._afterContainerTF, "addSubview:", self._afterTF);
}
,["void"]), new objj_method(sel_getUid("add:"), function $AddLoggerController__add_(self, _cmd, aSender)
{
    var beforeExpressionValue = objj_msgSend(self._beforeTF, "stringValue");
    var afterExpressionValue = objj_msgSend(self._afterTF, "stringValue");
    var isBeforeConditional = objj_msgSend(self._isBeforeConditionalCB, "stringValue") == "1";
    var isAfterConditional = objj_msgSend(self._isAfterConditionalCB, "stringValue") == "1";
    var beforeConditionalExpressionValue = isBeforeConditional ? objj_msgSend(self._beforeConditionalTF, "stringValue") : nil;
    var afterConditionalExpressionValue = isAfterConditional ? objj_msgSend(self._afterConditionalTF, "stringValue") : nil;
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLogger:afterExpression:beforeConditionalExpressionValue:afterConditionalExpressionValue:", beforeExpressionValue, afterExpressionValue, beforeConditionalExpressionValue, afterConditionalExpressionValue);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}