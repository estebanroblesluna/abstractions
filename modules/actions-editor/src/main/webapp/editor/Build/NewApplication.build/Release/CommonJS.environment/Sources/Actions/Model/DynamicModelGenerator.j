@STATIC;1.0;t;4417;{var the_class = objj_allocateClassPair(CPObject, "DynamicModelGenerator"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_definitions")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DynamicModelGenerator__init(self, _cmd)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("DynamicModelGenerator").super_class }, "init");
    self._definitions = objj_msgSend(CPDictionary, "dictionary");
    return self;
}
,["id"]), new objj_method(sel_getUid("addDefinitions:"), function $DynamicModelGenerator__addDefinitions_(self, _cmd, aListOfDefinitions)
{
    for (var j = 0; j < aListOfDefinitions.length; j++)
    {
        var element = aListOfDefinitions[j];
        objj_msgSend(self, "addDefinition:", element);
    }
}
,["void","id"]), new objj_method(sel_getUid("addDefinition:"), function $DynamicModelGenerator__addDefinition_(self, _cmd, element)
{
    var key = element.name;
    objj_msgSend(self._definitions, "setObject:forKey:", element, key);
}
,["void","id"]), new objj_method(sel_getUid("modelFor:elementId:contextId:initialProperties:hasBreakpoint:"), function $DynamicModelGenerator__modelFor_elementId_contextId_initialProperties_hasBreakpoint_(self, _cmd, anElementName, elementId, contextId, properties, hasBreakpoint)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    if (definition != nil)
    {
        var displayName = definition.displayName;
        var modelClass = objj_msgSend(DynamicElementModel, "class");
        if (definition.type == "MESSAGE_SOURCE")
        {
            modelClass = objj_msgSend(DynamicMessageSourceModel, "class");
        }
        if (elementId == nil)
        {
            newModel = objj_msgSend(modelClass, "contextId:elementName:", contextId, anElementName);
        }
        else
        {
            newModel = objj_msgSend(modelClass, "contextId:elementId:hasBreakpoint:", contextId, elementId, hasBreakpoint);
        }
        objj_msgSend(newModel, "elementName:", anElementName);
        objj_msgSend(newModel, "defaultNameValue:", displayName);
        var modelClassDefinition = definition.properties;
        for (var i = 0; i < modelClassDefinition.length; i++)
        {
            var property = modelClassDefinition[i];
            objj_msgSend(newModel, "addProperty:displayName:value:", property.name, property.displayName, property.defaultValue);
        }
        if (properties != nil)
        {
            CPLog.debug("Setting initial properties");
            objj_msgSend(newModel, "initializeWithProperties:", properties);
            objj_msgSend(newModel, "changed");
            CPLog.debug("Initial properties set");
        }
        return newModel;
    }
    else
    {
        return nil;
    }
}
,["ElementModel","id","id","id","id","id"]), new objj_method(sel_getUid("icon:"), function $DynamicModelGenerator__icon_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.icon;
}
,["id","id"]), new objj_method(sel_getUid("displayName:"), function $DynamicModelGenerator__displayName_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.displayName;
}
,["id","id"]), new objj_method(sel_getUid("isProcessor:"), function $DynamicModelGenerator__isProcessor_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "PROCESSOR";
}
,["id","id"]), new objj_method(sel_getUid("isMessageSource:"), function $DynamicModelGenerator__isMessageSource_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "MESSAGE_SOURCE";
}
,["id","id"]), new objj_method(sel_getUid("isRouter:"), function $DynamicModelGenerator__isRouter_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.type == "ROUTER";
}
,["id","id"]), new objj_method(sel_getUid("color:"), function $DynamicModelGenerator__color_(self, _cmd, anElementName)
{
    var definition = objj_msgSend(self._definitions, "objectForKey:", anElementName);
    return definition.color;
}
,["id","id"])]);
}