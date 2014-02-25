@STATIC;1.0;I;21;Foundation/CPObject.jI;23;Foundation/Foundation.jt;587;objj_executeFile("Foundation/CPObject.j", NO);objj_executeFile("Foundation/Foundation.j", NO);{var the_class = objj_allocateClassPair(CPObject, "LibraryAPI"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("libraries:"), function $LibraryAPI__libraries_(self, _cmd, aFunction)
{
    CPLog.debug("Getting libraries");
    ($.ajax({type: "GET", url: "../service/library/"})).done(function(result)
    {
        CPLog.debug("Libraries received");
        aFunction(result);
    });
    return self;
}
,["id","id"])]);
}