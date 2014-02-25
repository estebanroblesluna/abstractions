@STATIC;1.0;t;2032;{var the_class = objj_allocateClassPair(Command, "SaveActionsCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("execute"), function $SaveActionsCommand__execute(self, _cmd)
{
    var json = {"positions": [], "connections": []};
    var contextId = objj_msgSend(self._drawing, "contextId");
    var figures = objj_msgSend(self._drawing, "figures");
    for (var i = 0; i < objj_msgSend(figures, "count"); i++)
    {
        var figure = objj_msgSend(figures, "objectAtIndex:", i);
        if (objj_msgSend(figure, "isKindOfClass:", objj_msgSend(ElementFigure, "class")))
        {
            var frameOrigin = objj_msgSend(figure, "frameOrigin");
            var id = objj_msgSend(objj_msgSend(figure, "model"), "elementId");
            var point = {"x": frameOrigin.x, "y": frameOrigin.y, "id": id};
            json.positions.push(point);
        }
        else if (objj_msgSend(figure, "isKindOfClass:", objj_msgSend(Connection, "class")))
        {
            var id = objj_msgSend(objj_msgSend(figure, "model"), "elementId");
            var sourceId = objj_msgSend(objj_msgSend(objj_msgSend(figure, "source"), "model"), "elementId");
            var connection = {"id": id, "sourceId": sourceId, points: []};
            var points = objj_msgSend(figure, "points");
            for (var j = 0; j < objj_msgSend(points, "count"); j++)
            {
                var cupPoint = objj_msgSend(points, "objectAtIndex:", j);
                var point = {"x": cupPoint.x, "y": cupPoint.y};
                connection.points.push(point);
            }
            json.connections.push(connection);
        }
    }
    var jsonAsString = JSON.stringify(json);
    var saveUrl = ($.url()).param('saveUrl');
    CPLog.debug("Saving context " + contextId);
    ($.ajax({type: "POST", url: saveUrl, data: {"name": contextId, "json": jsonAsString}})).done(function(result)
    {
        CPLog.debug("Context saved " + contextId);
    });
}
,["void"])]);
}