@STATIC;1.0;t;613;{var the_class = objj_allocateClassPair(Command, "DeployCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $DeployCommand__execute(self, _cmd)
{
    var json = {"positions": [], "connections": []};
    var contextId = objj_msgSend(self._drawing, "contextId");
    CPLog.debug("Deploying context " + contextId);
    ($.ajax({type: "POST", url: "../service/context/" + contextId + "/deploy/localhost/9876"})).done(function(result)
    {
        CPLog.debug("Context deployed " + contextId);
    });
}
,["void"])]);
}