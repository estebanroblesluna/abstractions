@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;655;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "DataUtil"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(meta_class, [new objj_method(sel_getUid("var:"), function $DataUtil__var_(self, _cmd, aVarName)
{
    var current = window;
    var value = current[aVarName];
    while (value == undefined && window.top != current)
    {
        current = current.parent;
        value = current[aVarName];
    }
    if (value != undefined)
    {
        return value;
    }
    else
    {
        return nil;
    }
}
,["id","id"])]);
}