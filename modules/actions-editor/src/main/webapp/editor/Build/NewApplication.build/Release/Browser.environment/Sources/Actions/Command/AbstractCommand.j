@STATIC;1.0;t;2350;{var the_class = objj_allocateClassPair(Command, "AbstractCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $AbstractCommand__execute(self, _cmd)
{
    var contextId = objj_msgSend(self._drawing, "contextId");
    var selectedFigures = objj_msgSend(objj_msgSend(self._drawing, "tool"), "selectedFigures");
    var selectedUrns = new Array();
    var j = 0;
    for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++)
    {
        var figure = objj_msgSend(selectedFigures, "objectAtIndex:", i);
        if (objj_msgSend(figure, "respondsToSelector:", sel_getUid("elementId")))
        {
            selectedUrns[j] = objj_msgSend(figure, "elementId");
            j++;
        }
    }
    var name = window.prompt('Abstraction name');
    var urns = selectedUrns.join();
    CPLog.debug("Abstracting elements");
    ($.ajax({type: "POST", url: "../service/context/" + contextId + "/abstract", data: {"name": name, "displayName": name, "elementUrns": urns}})).done(function(result)
    {
        objj_msgSend(self, "processResult:selectedUrns:", result.definition, selectedUrns);
        CPLog.debug("DONE Abstracting elements");
    });
}
,["void"]), new objj_method(sel_getUid("processResult:selectedUrns:"), function $AbstractCommand__processResult_selectedUrns_(self, _cmd, aNewDefinition, selectedUrns)
{
    var toolbox = objj_msgSend(self._drawing, "toolboxWithId:", "user-defined");
    var generator = objj_msgSend(self._drawing, "generator");
    objj_msgSend(generator, "addDefinition:", aNewDefinition);
    var tool = objj_msgSend(CreateProcessorTool, "drawing:elementName:generator:", self._drawing, aNewDefinition.name, generator);
    objj_msgSend(toolbox, "addTool:withTitle:image:", tool, aNewDefinition.displayName, aNewDefinition.icon);
    var startingId = aNewDefinition.startingDefinitionId;
    var startingFigure = objj_msgSend(self._drawing, "processorFor:", startingId);
    var topLeft = objj_msgSend(startingFigure, "topLeft");
    objj_msgSend(self._drawing, "removeFigures:", selectedUrns);
    var newFigure = objj_msgSend(CreateProcessorTool, "createFigureAt:on:elementName:edit:elementId:initialProperties:tool:", topLeft, self._drawing, aNewDefinition.name, NO, nil, nil, tool);
}
,["void","id","id"])]);
}