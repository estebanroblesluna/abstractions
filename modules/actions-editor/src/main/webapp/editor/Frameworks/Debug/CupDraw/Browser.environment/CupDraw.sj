@STATIC;1.0;p;9;CupDraw.jt;3475;@STATIC;1.0;i;23;CPCancelableTextField.ji;22;LPMultiLineTextField.ji;24;CPCustomRowTableColumn.ji;15;GeometryUtils.ji;14;HandleMagnet.ji;8;Figure.ji;17;CompositeFigure.ji;7;Model.ji;10;Property.ji;6;Grid.ji;9;Drawing.ji;14;DrawingModel.ji;8;Handle.ji;17;CompositeFigure.ji;10;Polyline.ji;12;Connection.ji;13;ImageFigure.ji;15;ToolboxFigure.ji;13;LabelFigure.ji;12;LinkFigure.ji;18;PropertiesFigure.ji;17;IconLabelFigure.ji;17;RectangleFigure.ji;14;CircleFigure.ji;13;GroupFigure.ji;11;ToolState.ji;27;SelectionToolInitialState.ji;15;SelectedState.ji;18;MoveFiguresState.ji;17;MoveHandleState.ji;23;MarqueeSelectionState.ji;6;Tool.ji;18;StateMachineTool.ji;15;SelectionTool.ji;26;AbstractCreateFigureTool.ji;30;AbstractCreateConnectionTool.ji;17;CreateImageTool.ji;17;CreateLabelTool.ji;9;Command.ji;14;GroupCommand.ji;16;UngroupCommand.ji;13;LockCommand.ji;15;UnlockCommand.ji;21;BringToFrontCommand.ji;19;SendToBackCommand.ji;21;BringForwardCommand.ji;21;SendBackwardCommand.ji;18;AlignLeftCommand.ji;19;AlignRightCommand.ji;20;AlignCenterCommand.ji;17;AlignTopCommand.ji;20;AlignBottomCommand.ji;20;AlignMiddleCommand.ji;16;EditorDelegate.jt;2326;objj_executeFile("CPCancelableTextField.j", YES);
objj_executeFile("LPMultiLineTextField.j", YES);
objj_executeFile("CPCustomRowTableColumn.j", YES);
objj_executeFile("GeometryUtils.j", YES);
objj_executeFile("HandleMagnet.j", YES);
objj_executeFile("Figure.j", YES);
objj_executeFile("CompositeFigure.j", YES);
objj_executeFile("Model.j", YES);
objj_executeFile("Property.j", YES);
objj_executeFile("Grid.j", YES);
objj_executeFile("Drawing.j", YES);
objj_executeFile("DrawingModel.j", YES);
objj_executeFile("Handle.j", YES);
objj_executeFile("CompositeFigure.j", YES);
objj_executeFile("Polyline.j", YES);
objj_executeFile("Connection.j", YES);
objj_executeFile("ImageFigure.j", YES);
objj_executeFile("ToolboxFigure.j", YES);
objj_executeFile("LabelFigure.j", YES);
objj_executeFile("LinkFigure.j", YES);
objj_executeFile("PropertiesFigure.j", YES);
objj_executeFile("IconLabelFigure.j", YES);
objj_executeFile("RectangleFigure.j", YES);
objj_executeFile("CircleFigure.j", YES);
objj_executeFile("GroupFigure.j", YES);
objj_executeFile("ToolState.j", YES);
objj_executeFile("SelectionToolInitialState.j", YES);
objj_executeFile("SelectedState.j", YES);
objj_executeFile("MoveFiguresState.j", YES);
objj_executeFile("MoveHandleState.j", YES);
objj_executeFile("MarqueeSelectionState.j", YES);
objj_executeFile("Tool.j", YES);
objj_executeFile("StateMachineTool.j", YES);
objj_executeFile("SelectionTool.j", YES);
objj_executeFile("AbstractCreateFigureTool.j", YES);
objj_executeFile("AbstractCreateConnectionTool.j", YES);
objj_executeFile("CreateImageTool.j", YES);
objj_executeFile("CreateLabelTool.j", YES);
objj_executeFile("Command.j", YES);
objj_executeFile("GroupCommand.j", YES);
objj_executeFile("UngroupCommand.j", YES);
objj_executeFile("LockCommand.j", YES);
objj_executeFile("UnlockCommand.j", YES);
objj_executeFile("BringToFrontCommand.j", YES);
objj_executeFile("SendToBackCommand.j", YES);
objj_executeFile("BringForwardCommand.j", YES);
objj_executeFile("SendBackwardCommand.j", YES);
objj_executeFile("AlignLeftCommand.j", YES);
objj_executeFile("AlignRightCommand.j", YES);
objj_executeFile("AlignCenterCommand.j", YES);
objj_executeFile("AlignTopCommand.j", YES);
objj_executeFile("AlignBottomCommand.j", YES);
objj_executeFile("AlignMiddleCommand.j", YES);
objj_executeFile("EditorDelegate.j", YES);

p;20;AlignBottomCommand.jt;1153;@STATIC;1.0;t;1134;{var the_class = objj_allocateClassPair(Command, "AlignBottomCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignBottomCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignBottomCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var y = objj_msgSend(referenceFigure, "bottomMiddle").y;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
      var frameSize = objj_msgSend(figure, "frameSize");
   var newPosition = CGPointMake(oldFrameOrigin.x, y - frameSize.height);
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;20;AlignCenterCommand.jt;1155;@STATIC;1.0;t;1136;{var the_class = objj_allocateClassPair(Command, "AlignCenterCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignCenterCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignCenterCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var x = objj_msgSend(referenceFigure, "topMiddle").x;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
      var frameSize = objj_msgSend(figure, "frameSize");
   var newPosition = CGPointMake(x - (frameSize.width / 2), oldFrameOrigin.y);
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;18;AlignLeftCommand.jt;1066;@STATIC;1.0;t;1047;{var the_class = objj_allocateClassPair(Command, "AlignLeftCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignLeftCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignLeftCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var x = objj_msgSend(referenceFigure, "topLeft").x;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
   var newPosition = CGPointMake(x, oldFrameOrigin.y);
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;20;AlignMiddleCommand.jt;1153;@STATIC;1.0;t;1134;{var the_class = objj_allocateClassPair(Command, "AlignMiddleCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignMiddleCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignMiddleCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var y = objj_msgSend(referenceFigure, "center").y;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
      var frameSize = objj_msgSend(figure, "frameSize");
   var newPosition = CGPointMake(oldFrameOrigin.x, y - (frameSize.height / 2));
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;19;AlignRightCommand.jt;1145;@STATIC;1.0;t;1126;{var the_class = objj_allocateClassPair(Command, "AlignRightCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignRightCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignRightCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var x = objj_msgSend(referenceFigure, "topRight").x;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
      var frameSize = objj_msgSend(figure, "frameSize");
   var newPosition = CGPointMake(x - frameSize.width, oldFrameOrigin.y);
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;17;AlignTopCommand.jt;1122;@STATIC;1.0;t;1103;{var the_class = objj_allocateClassPair(Command, "AlignTopCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $AlignTopCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $AlignTopCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") > 1) {
     var referenceFigure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var y = objj_msgSend(referenceFigure, "topMiddle").y;
  for (var i = 1; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
      var oldFrameOrigin = objj_msgSend(figure, "frameOrigin");
      var frameSize = objj_msgSend(figure, "frameSize");
   var newPosition = CGPointMake(oldFrameOrigin.x, y);
   objj_msgSend(figure, "moveTo:",  newPosition);
  }
  objj_msgSend(tool, "updateInitialPoints");
 }
}
},["void"])]);
}

p;21;BringForwardCommand.jt;1060;@STATIC;1.0;t;1041;{var the_class = objj_allocateClassPair(Command, "BringForwardCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $BringForwardCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $BringForwardCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") == 1) {
  var subviews = objj_msgSend(_drawing, "subviews");
     var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var insertIndex = objj_msgSend(subviews, "indexOfObjectIdenticalTo:",  figure) + 1;
     var otherFigure = objj_msgSend(subviews, "objectAtIndex:",  insertIndex);
  objj_msgSend(tool, "unselect:",  figure);
  objj_msgSend(_drawing, "addSubview:positioned:relativeTo:",  figure,  CPWindowAbove,  otherFigure);
  objj_msgSend(tool, "select:",  figure);
 }
}
},["void"])]);
}

p;21;BringToFrontCommand.jt;1031;@STATIC;1.0;t;1012;{var the_class = objj_allocateClassPair(Command, "BringToFrontCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $BringToFrontCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $BringToFrontCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") == 1) {
  var subviews = objj_msgSend(_drawing, "subviews");
     var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var insertIndex = objj_msgSend(subviews, "count") - 1;
     var otherFigure = objj_msgSend(subviews, "objectAtIndex:",  insertIndex);
  objj_msgSend(tool, "unselect:",  figure);
  objj_msgSend(_drawing, "addSubview:positioned:relativeTo:",  figure,  CPWindowAbove,  otherFigure);
  objj_msgSend(tool, "select:",  figure);
 }
}
},["void"])]);
}

p;9;Command.jt;862;@STATIC;1.0;t;844;{var the_class = objj_allocateClassPair(CPObject, "Command"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_drawing")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithDrawing:"), function $Command__initWithDrawing_(self, _cmd, aDrawing)
{ with(self)
{
 _drawing = aDrawing;
 return self;
}
},["id","Drawing"]), new objj_method(sel_getUid("undo"), function $Command__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $Command__execute(self, _cmd)
{ with(self)
{
}
},["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:"), function $Command__drawing_(self, _cmd, aDrawing)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithDrawing:",  aDrawing);
}
},["id","Drawing"])]);
}

p;14;GroupCommand.jt;1164;@STATIC;1.0;t;1145;{var the_class = objj_allocateClassPair(Command, "GroupCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $GroupCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $GroupCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") >= 2) {
  var frame = objj_msgSend(GeometryUtils, "computeFrameForViews:",  selectedFigures);
  var figuresTranslation = CGPointMake(-frame.origin.x, -frame.origin.y);
  var group = objj_msgSend(GroupFigure, "frame:",  frame);
  for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
   objj_msgSend(group, "addFigure:",  figure);
   objj_msgSend(figure, "translateBy:",  figuresTranslation);
  }
  objj_msgSend(_drawing, "addFigure:",  group);
  objj_msgSend(tool, "clearSelection");
  objj_msgSend(tool, "select:",  group);
 }
}
},["void"])]);
}

p;13;LockCommand.jt;834;@STATIC;1.0;t;816;{var the_class = objj_allocateClassPair(Command, "LockCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $LockCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $LockCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") >= 1) {
  for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
   objj_msgSend(figure, "moveable:",  NO);
   objj_msgSend(figure, "editable:",  NO);
   objj_msgSend(tool, "unselect:",  figure);
  }
 }
}
},["void"])]);
}

p;21;SendBackwardCommand.jt;1060;@STATIC;1.0;t;1041;{var the_class = objj_allocateClassPair(Command, "SendBackwardCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $SendBackwardCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $SendBackwardCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") == 1) {
  var subviews = objj_msgSend(_drawing, "subviews");
     var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var insertIndex = objj_msgSend(subviews, "indexOfObjectIdenticalTo:",  figure) - 1;
     var otherFigure = objj_msgSend(subviews, "objectAtIndex:",  insertIndex);
  objj_msgSend(tool, "unselect:",  figure);
  objj_msgSend(_drawing, "addSubview:positioned:relativeTo:",  figure,  CPWindowBelow,  otherFigure);
  objj_msgSend(tool, "select:",  figure);
 }
}
},["void"])]);
}

p;19;SendToBackCommand.jt;990;@STATIC;1.0;t;972;{var the_class = objj_allocateClassPair(Command, "SendToBackCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $SendToBackCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $SendToBackCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") == 1) {
  var subviews = objj_msgSend(_drawing, "subviews");
     var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var insertIndex = 0;
     var otherFigure = objj_msgSend(subviews, "objectAtIndex:",  insertIndex);
  objj_msgSend(tool, "unselect:",  figure);
  objj_msgSend(_drawing, "addSubview:positioned:relativeTo:",  figure,  CPWindowAbove,  otherFigure);
  objj_msgSend(tool, "select:",  figure);
 }
}
},["void"])]);
}

p;16;UngroupCommand.jt;980;@STATIC;1.0;t;962;{var the_class = objj_allocateClassPair(Command, "UngroupCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $UngroupCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $UngroupCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") == 1) {
  var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  0);
  var ungroupedFigures = objj_msgSend(figure, "figures");
  objj_msgSend(tool, "unselect:",  figure);
  objj_msgSend(figure, "ungroup");
  for (var i = 0; i < objj_msgSend(ungroupedFigures, "count"); i++) {
      var ungroupedFigure = objj_msgSend(ungroupedFigures, "objectAtIndex:",  i);
   objj_msgSend(tool, "select:",  ungroupedFigure);
  }
 }
}
},["void"])]);
}

p;15;UnlockCommand.jt;842;@STATIC;1.0;t;824;{var the_class = objj_allocateClassPair(Command, "UnlockCommand"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("undo"), function $UnlockCommand__undo(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("execute"), function $UnlockCommand__execute(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(_drawing, "tool");
 var selectedFigures = objj_msgSend(tool, "selectedFigures");
 if (objj_msgSend(selectedFigures, "count") >= 1) {
  for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++) {
      var figure = objj_msgSend(selectedFigures, "objectAtIndex:",  i);
   objj_msgSend(figure, "moveable:",  YES);
   objj_msgSend(figure, "editable:",  YES);
   objj_msgSend(tool, "unselect:",  figure);
  }
 }
}
},["void"])]);
}

p;9;Drawing.jt;7885;@STATIC;1.0;t;7866;DrawingSelectionChangedNotification = "DrawingSelectionChangedNotification";
{var the_class = objj_allocateClassPair(CompositeFigure, "Drawing"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_currentTool"), new objj_ivar("_selectedFigure"), new objj_ivar("_backgroundLayer"), new objj_ivar("_toolbox"), new objj_ivar("_properties")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $Drawing__init(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Drawing").super_class }, "init");
 _currentTool = objj_msgSend(SelectionTool, "drawing:",  self);
 _selectedFigure = nil;
 _selectable = false;
 _moveable = false;
 _editable = false;
 objj_msgSend(self, "model:",  objj_msgSend(DrawingModel, "new"));
 return self;
}
},["id"]), new objj_method(sel_getUid("initWithFrame:"), function $Drawing__initWithFrame_(self, _cmd, aFrame)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Drawing").super_class }, "initWithFrame:",  aFrame);
 _backgroundLayer = objj_msgSend(CompositeFigure, "frame:",  aFrame);
 objj_msgSend(_backgroundLayer, "selectable:",  NO);
 objj_msgSend(_backgroundLayer, "moveable:",  NO);
 objj_msgSend(_backgroundLayer, "setAutoresizingMask:",  CPViewHeightSizable | CPViewWidthSizable);
 objj_msgSend(self, "addFigure:",  _backgroundLayer);
 objj_msgSend(self, "computeBackgroundLayer");
 return self;
}
},["id","CGRect"]), new objj_method(sel_getUid("toolbox:"), function $Drawing__toolbox_(self, _cmd, aToolbox)
{ with(self)
{
 _toolbox = aToolbox;
 objj_msgSend(self, "addFigure:",  _toolbox);
}
},["void","ToolboxFigure"]), new objj_method(sel_getUid("properties:"), function $Drawing__properties_(self, _cmd, aProperties)
{ with(self)
{
 _properties = aProperties;
 objj_msgSend(self, "addFigure:",  _properties);
}
},["void","PropertiesFigure"]), new objj_method(sel_getUid("computeBackgroundLayer"), function $Drawing__computeBackgroundLayer(self, _cmd)
{ with(self)
{
 objj_msgSend(_backgroundLayer, "clearFigures");
 if (objj_msgSend(self, "showGrid")) {
  var frame = CGRectMake(0, 0, 1600, 1600);
  var grid = objj_msgSend(Grid, "frame:showGrid:gridSize:",  frame,  objj_msgSend(self, "showGrid"),  objj_msgSend(self, "gridSize"));
  objj_msgSend(_backgroundLayer, "addFigure:",  grid);
 }
}
},["void"]), new objj_method(sel_getUid("select"), function $Drawing__select(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Drawing").super_class }, "select");
 objj_msgSend(objj_msgSend(self, "window"), "makeFirstResponder:",  self);
}
},["void"]), new objj_method(sel_getUid("drawing"), function $Drawing__drawing(self, _cmd)
{ with(self)
{
 return self;
}
},["Drawing"]), new objj_method(sel_getUid("showGrid"), function $Drawing__showGrid(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "propertyValue:",  "showGrid"), "boolValue");
}
},["bool"]), new objj_method(sel_getUid("snapToGrid"), function $Drawing__snapToGrid(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "propertyValue:",  "snapToGrid"), "boolValue");
}
},["bool"]), new objj_method(sel_getUid("floatingToolboxes"), function $Drawing__floatingToolboxes(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "propertyValue:",  "floatingToolboxes"), "boolValue");
}
},["bool"]), new objj_method(sel_getUid("gridSize"), function $Drawing__gridSize(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(objj_msgSend(self, "model"), "propertyValue:",  "gridSize"), "intValue");
}
},["int"]), new objj_method(sel_getUid("mouseDown:"), function $Drawing__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_currentTool, "mouseDown:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $Drawing__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_currentTool, "mouseDragged:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $Drawing__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_currentTool, "mouseUp:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("acceptsFirstResponder"), function $Drawing__acceptsFirstResponder(self, _cmd)
{ with(self)
{
    return YES;
}
},["BOOL"]), new objj_method(sel_getUid("keyUp:"), function $Drawing__keyUp_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_currentTool, "keyUp:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyDown:"), function $Drawing__keyDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_currentTool, "keyDown:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("unselectAll"), function $Drawing__unselectAll(self, _cmd)
{ with(self)
{
 var subviews = objj_msgSend(self, "subviews");
 for (var i = objj_msgSend(subviews, "count") -1; i >= 0 ; i--) {
     var figure = objj_msgSend(subviews, "objectAtIndex:", i);
  objj_msgSend(figure, "unselect");
 }
}
},["void"]), new objj_method(sel_getUid("tool"), function $Drawing__tool(self, _cmd)
{ with(self)
{
 return _currentTool;
}
},["Tool"]), new objj_method(sel_getUid("tool:"), function $Drawing__tool_(self, _cmd, aTool)
{ with(self)
{
 objj_msgSend(_currentTool, "release");
 _currentTool = aTool;
 objj_msgSend(_currentTool, "activate");
}
},["void","Tool"]), new objj_method(sel_getUid("selectedFigure"), function $Drawing__selectedFigure(self, _cmd)
{ with(self)
{
 return _selectedFigure;
}
},["id"]), new objj_method(sel_getUid("selectedFigure:"), function $Drawing__selectedFigure_(self, _cmd, aFigure)
{ with(self)
{
 _selectedFigure = aFigure;
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "postNotificationName:object:",  DrawingSelectionChangedNotification,  self);
}
},["void","id"]), new objj_method(sel_getUid("invalidate"), function $Drawing__invalidate(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "computeBackgroundLayer");
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Drawing").super_class }, "invalidate");
}
},["void"]), new objj_method(sel_getUid("modelChanged"), function $Drawing__modelChanged(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "computeBackgroundLayer");
 var floatingToolboxes = objj_msgSend(self, "floatingToolboxes");
 var drawingFrame = objj_msgSend(self, "frame");
 CPLog.debug("[DRAWING] Model changed, floatingToolboxes:" + floatingToolboxes);
 if (_toolbox != nil) {
  objj_msgSend(_toolbox, "selectable:",  floatingToolboxes);
  objj_msgSend(_toolbox, "moveable:",  floatingToolboxes);
  if (!floatingToolboxes) {
   var frame = objj_msgSend(_toolbox, "frame");
   var newFrame = CGRectMake(0, 0, frame.size.width, drawingFrame.size.height);
   objj_msgSend(_toolbox, "setFrame:",  newFrame);
   objj_msgSend(_toolbox, "setAutoresizingMask:",  CPViewHeightSizable);
  } else {
   objj_msgSend(_toolbox, "sizeToFit");
  }
 }
 if (_properties != nil) {
  var wasFloatingBefore = !objj_msgSend(_properties, "isMoveable");
  objj_msgSend(_properties, "selectable:",  floatingToolboxes);
  objj_msgSend(_properties, "moveable:",  floatingToolboxes);
  if (!floatingToolboxes) {
   var leftOffset;
   if (_toolbox != nil) {
    leftOffset = objj_msgSend(_toolbox, "frame").size.width;
   } else {
    leftOffset = 0;
   }
   var frame = objj_msgSend(_properties, "frame");
   var newFrame = CGRectMake(leftOffset, drawingFrame.size.height - frame.size.height, drawingFrame.size.width, frame.size.height);
   objj_msgSend(_properties, "setFrame:",  newFrame);
   objj_msgSend(_properties, "setAutoresizingMask:",  CPViewMinYMargin | CPViewWidthSizable);
  } else if (wasFloatingBefore) {
   objj_msgSend(_properties, "setFrame:",  objj_msgSend(PropertiesFigure, "defaultFrame"));
  }
 }
}
},["void"])]);
}

p;14;DrawingModel.jt;960;@STATIC;1.0;t;942;{var the_class = objj_allocateClassPair(Model, "DrawingModel"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $DrawingModel__init(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("DrawingModel").super_class }, "init");
 objj_msgSend(self, "addProperty:displayName:value:",  "name",  "Name",  "");
 objj_msgSend(self, "addProperty:displayName:value:type:",  "showGrid",  "Show grid?",  NO,  PropertyTypeBoolean);
 objj_msgSend(self, "addProperty:displayName:value:type:",  "gridSize",  "Grid size",  20,  PropertyTypeInteger);
 objj_msgSend(self, "addProperty:displayName:value:type:",  "snapToGrid",  "Snap to grid?",  NO,  PropertyTypeBoolean);
 objj_msgSend(self, "addProperty:displayName:value:type:",  "floatingToolboxes",  "Floating toolboxes?",  YES,  PropertyTypeBoolean);
 return self;
}
},["id"])]);
}

p;6;Grid.jt;2117;@STATIC;1.0;t;2098;{var the_class = objj_allocateClassPair(Figure, "Grid"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_showGrid"), new objj_ivar("_gridSize"), new objj_ivar("_gridColor")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:showGrid:gridSize:"), function $Grid__initWithFrame_showGrid_gridSize_(self, _cmd, aFrame, aShowGrid, aGridSize)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Grid").super_class }, "initWithFrame:",  aFrame);
 _showGrid = aShowGrid;
 _gridSize = aGridSize;
 _gridColor = objj_msgSend(CPColor, "colorWithHexString:",  "F7F0F3");
 return self;
}
},["id","CGRect","bool","int"]), new objj_method(sel_getUid("drawRect:on:"), function $Grid__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
 CGContextSetFillColor(context, objj_msgSend(CPColor, "colorWithHexString:",  "FEFEFE"));
 CGContextFillRect(context, rect);
 if (_showGrid) {
  CGContextSetLineWidth(context, 0.25);
  for (var p = 0; p <= rect.size.width ; p = p + _gridSize) {
   objj_msgSend(self, "drawGridLineX:y:x:y:context:",  p,  0,  p,  rect.size.height,  context);
  }
  for (var p = 0; p <= rect.size.height ; p = p + _gridSize) {
   objj_msgSend(self, "drawGridLineX:y:x:y:context:",  0,  p,  rect.size.width,  p,  context);
  }
 }
}
},["void","CGRect","id"]), new objj_method(sel_getUid("drawGridLineX:y:x:y:context:"), function $Grid__drawGridLineX_y_x_y_context_(self, _cmd, x1, y1, x2, y2, context)
{ with(self)
{
 CGContextMoveToPoint(context, x1, y1);
 CGContextAddLineToPoint(context, x2, y2);
 CGContextSetStrokeColor(context, _gridColor);
    CGContextStrokePath(context);
}
},["void","int","int","int","int",null])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("frame:showGrid:gridSize:"), function $Grid__frame_showGrid_gridSize_(self, _cmd, aFrame, aShowGrid, aGridSize)
{ with(self)
{
 var grid = objj_msgSend(objj_msgSend(self, "alloc"), "initWithFrame:showGrid:gridSize:",  aFrame,  aShowGrid,  aGridSize);
 return grid;
}
},["Grid","CGRect","bool","int"])]);
}

p;23;CPCancelableTextField.jt;826;@STATIC;1.0;t;808;{var the_class = objj_allocateClassPair(CPTextField, "CPCancelableTextField"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_cancelator")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("cancelator:"), function $CPCancelableTextField__cancelator_(self, _cmd, aCancelator)
{ with(self)
{
 _cancelator = aCancelator;
}
},["void","id"]), new objj_method(sel_getUid("keyDown:"), function $CPCancelableTextField__keyDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("CPCancelableTextField").super_class }, "keyDown:",  anEvent);
 if (_cancelator != nil && objj_msgSend(anEvent, "keyCode") == CPKeyCodes.ESC) {
  objj_msgSend(_cancelator, "cancelEditing");
 }
}
},["void","CPEvent"])]);
}

p;24;CPCustomRowTableColumn.jt;1359;@STATIC;1.0;t;1340;{var the_class = objj_allocateClassPair(CPTableColumn, "CPCustomRowTableColumn"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_model")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("dataViewForRow:"), function $CPCustomRowTableColumn__dataViewForRow_(self, _cmd, aRowIndex)
{ with(self)
{
 if (aRowIndex < 0 || _model == nil) {
  return objj_msgSend(self, "dataView");
 } else {
  var propertyType = objj_msgSend(_model, "propertyTypeAt:",  aRowIndex);
  if (objj_msgSend(propertyType, "isEqual:",  PropertyTypeBoolean)) {
   var editableView = objj_msgSend(CPCheckBox, "checkBoxWithTitle:", "");
         objj_msgSend(editableView, "sizeToFit");
         return editableView;
  } else if (objj_msgSend(propertyType, "isEqual:",  PropertyTypeInteger)) {
   return objj_msgSend(self, "dataView");
  } else if (objj_msgSend(propertyType, "isEqual:",  PropertyTypeFloat)) {
   return objj_msgSend(self, "dataView");
  } else if (objj_msgSend(propertyType, "isEqual:",  PropertyTypeString)) {
   return objj_msgSend(self, "dataView");
  } else {
   return objj_msgSend(self, "dataView");
  }
 }
}
},["id","int"]), new objj_method(sel_getUid("model:"), function $CPCustomRowTableColumn__model_(self, _cmd, aModel)
{ with(self)
{
 _model = aModel;
}
},["void","id"])]);
}

p;22;LPMultiLineTextField.jt;11125;@STATIC;1.0;I;20;AppKit/CPTextField.jt;11080;objj_executeFile("AppKit/CPTextField.j", NO);
var CPTextFieldInputOwner = nil;
{var the_class = objj_allocateClassPair(CPTextField, "LPMultiLineTextField"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_DOMTextareaElement"), new objj_ivar("_stringValue"), new objj_ivar("_hideOverflow")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("_DOMTextareaElement"), function $LPMultiLineTextField___DOMTextareaElement(self, _cmd)
{ with(self)
{
    if (!_DOMTextareaElement)
    {
        _DOMTextareaElement = document.createElement("textarea");
        _DOMTextareaElement.style.position = "absolute";
        _DOMTextareaElement.style.background = "none";
        _DOMTextareaElement.style.border = "0";
        _DOMTextareaElement.style.outline = "0";
        _DOMTextareaElement.style.zIndex = "100";
        _DOMTextareaElement.style.resize = "none";
        _DOMTextareaElement.style.padding = "0";
        _DOMTextareaElement.style.margin = "0";
        _DOMTextareaElement.onkeyup = function(event) {
                objj_msgSend(CPTextFieldInputOwner, "keyUp:", nil);
         }
        _DOMTextareaElement.onblur = function(){
                objj_msgSend(objj_msgSend(CPTextFieldInputOwner, "window"), "makeFirstResponder:", nil);
                CPTextFieldInputOwner = nil;
         };
        self._DOMElement.appendChild(_DOMTextareaElement);
    }
    return _DOMTextareaElement;
}
},["DOMElement"]), new objj_method(sel_getUid("initWithFrame:"), function $LPMultiLineTextField__initWithFrame_(self, _cmd, aFrame)
{ with(self)
{
    if (self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "initWithFrame:", aFrame))
    {
    }
    return self;
}
},["id","CGRect"]), new objj_method(sel_getUid("isScrollable"), function $LPMultiLineTextField__isScrollable(self, _cmd)
{ with(self)
{
   return !_hideOverflow;
}
},["BOOL"]), new objj_method(sel_getUid("setScrollable:"), function $LPMultiLineTextField__setScrollable_(self, _cmd, shouldScroll)
{ with(self)
{
    _hideOverflow = !shouldScroll;
}
},["void","BOOL"]), new objj_method(sel_getUid("setEditable:"), function $LPMultiLineTextField__setEditable_(self, _cmd, shouldBeEditable)
{ with(self)
{
    objj_msgSend(self, "_DOMTextareaElement").style.cursor = shouldBeEditable ? "cursor" : "default";
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "setEditable:", shouldBeEditable);
}
},["void","BOOL"]), new objj_method(sel_getUid("selectText:"), function $LPMultiLineTextField__selectText_(self, _cmd, sender)
{ with(self)
{
    objj_msgSend(self, "_DOMTextareaElement").select();
}
},["void","id"]), new objj_method(sel_getUid("layoutSubviews"), function $LPMultiLineTextField__layoutSubviews(self, _cmd)
{ with(self)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "layoutSubviews");
    var contentView = objj_msgSend(self, "layoutEphemeralSubviewNamed:positioned:relativeToEphemeralSubviewNamed:", "content-view", CPWindowAbove, "bezel-view");
    objj_msgSend(contentView, "setHidden:", YES);
    var DOMElement = objj_msgSend(self, "_DOMTextareaElement"),
        contentInset = objj_msgSend(self, "currentValueForThemeAttribute:", "content-inset"),
        bounds = objj_msgSend(self, "bounds");
    DOMElement.style.top = contentInset.top + "px";
    DOMElement.style.bottom = contentInset.bottom + "px";
    DOMElement.style.left = contentInset.left + "px";
    DOMElement.style.right = contentInset.right + "px";
    DOMElement.style.width = (CGRectGetWidth(bounds) - contentInset.left - contentInset.right) + "px";
    DOMElement.style.height = (CGRectGetHeight(bounds) - contentInset.top - contentInset.bottom) + "px";
    DOMElement.style.color = objj_msgSend(objj_msgSend(self, "currentValueForThemeAttribute:", "text-color"), "cssString");
    DOMElement.style.font = objj_msgSend(objj_msgSend(self, "currentValueForThemeAttribute:", "font"), "cssString");
    switch (objj_msgSend(self, "currentValueForThemeAttribute:", "alignment"))
    {
        case CPLeftTextAlignment:
            DOMElement.style.textAlign = "left";
            break;
        case CPJustifiedTextAlignment:
            DOMElement.style.textAlign = "justify";
            break;
        case CPCenterTextAlignment:
            DOMElement.style.textAlign = "center";
            break;
        case CPRightTextAlignment:
            DOMElement.style.textAlign = "right";
            break;
        default:
            DOMElement.style.textAlign = "left";
    }
    DOMElement.value = _stringValue || "";
    if(_hideOverflow)
        DOMElement.style.overflow="hidden";
}
},["void"]), new objj_method(sel_getUid("scrollWheel:"), function $LPMultiLineTextField__scrollWheel_(self, _cmd, anEvent)
{ with(self)
{
    var DOMElement = objj_msgSend(self, "_DOMTextareaElement");
    DOMElement.scrollLeft += anEvent._deltaX;
    DOMElement.scrollTop += anEvent._deltaY;
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDown:"), function $LPMultiLineTextField__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
    if (objj_msgSend(self, "isEditable") && objj_msgSend(self, "isEnabled"))
        objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "platformWindow"), "_propagateCurrentDOMEvent:", YES);
    else
        objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "mouseDown:", anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $LPMultiLineTextField__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
    return objj_msgSend(objj_msgSend(objj_msgSend(anEvent, "window"), "platformWindow"), "_propagateCurrentDOMEvent:", YES);
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyDown:"), function $LPMultiLineTextField__keyDown_(self, _cmd, anEvent)
{ with(self)
{
    if (objj_msgSend(anEvent, "keyCode") === CPTabKeyCode)
    {
        if (objj_msgSend(anEvent, "modifierFlags") & CPShiftKeyMask)
            objj_msgSend(objj_msgSend(self, "window"), "selectPreviousKeyView:", self);
        else
            objj_msgSend(objj_msgSend(self, "window"), "selectNextKeyView:", self);
        if (objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "firstResponder"), "respondsToSelector:", sel_getUid("selectText:")))
            objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "firstResponder"), "selectText:", self);
        objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "platformWindow"), "_propagateCurrentDOMEvent:", NO);
    }
    else
        objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "platformWindow"), "_propagateCurrentDOMEvent:", YES);
    objj_msgSend(objj_msgSend(CPRunLoop, "currentRunLoop"), "limitDateForMode:", CPDefaultRunLoopMode);
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyUp:"), function $LPMultiLineTextField__keyUp_(self, _cmd, anEvent)
{ with(self)
{
    if (_stringValue !== objj_msgSend(self, "stringValue"))
    {
        _stringValue = objj_msgSend(self, "stringValue");
        if (!_isEditing)
        {
            _isEditing = YES;
            objj_msgSend(self, "textDidBeginEditing:", objj_msgSend(CPNotification, "notificationWithName:object:userInfo:", CPControlTextDidBeginEditingNotification, self, nil));
        }
        objj_msgSend(self, "textDidChange:", objj_msgSend(CPNotification, "notificationWithName:object:userInfo:", CPControlTextDidChangeNotification, self, nil));
    }
    objj_msgSend(objj_msgSend(objj_msgSend(self, "window"), "platformWindow"), "_propagateCurrentDOMEvent:", YES);
}
},["void","CPEvent"]), new objj_method(sel_getUid("becomeFirstResponder"), function $LPMultiLineTextField__becomeFirstResponder(self, _cmd)
{ with(self)
{
    _stringValue = objj_msgSend(self, "stringValue");
    objj_msgSend(self, "setThemeState:", CPThemeStateEditing);
    setTimeout(function(){
        objj_msgSend(self, "_DOMTextareaElement").focus();
        CPTextFieldInputOwner = self;
    }, 0.0);
    objj_msgSend(self, "textDidFocus:", objj_msgSend(CPNotification, "notificationWithName:object:userInfo:", CPTextFieldDidFocusNotification, self, nil));
    return YES;
}
},["BOOL"]), new objj_method(sel_getUid("resignFirstResponder"), function $LPMultiLineTextField__resignFirstResponder(self, _cmd)
{ with(self)
{
    objj_msgSend(self, "unsetThemeState:", CPThemeStateEditing);
    objj_msgSend(self, "setStringValue:", objj_msgSend(self, "stringValue"));
    objj_msgSend(self, "_DOMTextareaElement").blur();
    if (_isEditing)
    {
        _isEditing = NO;
        objj_msgSend(self, "textDidEndEditing:", objj_msgSend(CPNotification, "notificationWithName:object:userInfo:", CPControlTextDidEndEditingNotification, self, nil));
        if (objj_msgSend(self, "sendsActionOnEndEditing"))
            objj_msgSend(self, "sendAction:to:", objj_msgSend(self, "action"), objj_msgSend(self, "target"));
    }
    objj_msgSend(self, "textDidBlur:", objj_msgSend(CPNotification, "notificationWithName:object:userInfo:", CPTextFieldDidBlurNotification, self, nil));
    return YES;
}
},["BOOL"]), new objj_method(sel_getUid("stringValue"), function $LPMultiLineTextField__stringValue(self, _cmd)
{ with(self)
{
    return (!!_DOMTextareaElement) ? _DOMTextareaElement.value : "";
}
},["CPString"]), new objj_method(sel_getUid("setStringValue:"), function $LPMultiLineTextField__setStringValue_(self, _cmd, aString)
{ with(self)
{
    _stringValue = aString;
    objj_msgSend(self, "setNeedsLayout");
}
},["void","CPString"])]);
}
var LPMultiLineTextFieldStringValueKey = "LPMultiLineTextFieldStringValueKey",
    LPMultiLineTextFieldScrollableKey = "LPMultiLineTextFieldScrollableKey";
{
var the_class = objj_getClass("LPMultiLineTextField")
if(!the_class) throw new SyntaxError("*** Could not find definition for class \"LPMultiLineTextField\"");
var meta_class = the_class.isa;class_addMethods(the_class, [new objj_method(sel_getUid("initWithCoder:"), function $LPMultiLineTextField__initWithCoder_(self, _cmd, aCoder)
{ with(self)
{
    if (self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "initWithCoder:", aCoder))
    {
        objj_msgSend(self, "setStringValue:", objj_msgSend(aCoder, "decodeObjectForKey:", LPMultiLineTextFieldStringValueKey));
        objj_msgSend(self, "setScrollable:", objj_msgSend(aCoder, "decodeBoolForKey:", LPMultiLineTextFieldScrollableKey));
    }
    return self;
}
},["id","CPCoder"]), new objj_method(sel_getUid("encodeWithCoder:"), function $LPMultiLineTextField__encodeWithCoder_(self, _cmd, aCoder)
{ with(self)
{
    objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LPMultiLineTextField").super_class }, "encodeWithCoder:", aCoder);
    objj_msgSend(aCoder, "encodeObject:forKey:", _stringValue, LPMultiLineTextFieldStringValueKey);
    objj_msgSend(aCoder, "encodeBool:forKey:", (_hideOverflow?NO:YES), LPMultiLineTextFieldScrollableKey);
}
},["void","CPCoder"])]);
}

p;14;CircleFigure.jt;2367;@STATIC;1.0;t;2348;{var the_class = objj_allocateClassPair(Figure, "CircleFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:"), function $CircleFigure__initWithFrame_(self, _cmd, aFrame)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("CircleFigure").super_class }, "initWithFrame:",  aFrame);
 if (self) {
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topMiddle"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topRight"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleRight"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomMiddle"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomRight"));
  return self;
 }
}
},["id","CGRect"]), new objj_method(sel_getUid("drawRect:on:"), function $CircleFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
        CGContextSetFillColor(context, objj_msgSend(self, "backgroundColor"));
        CGContextFillEllipseInRect(context, objj_msgSend(self, "bounds"));
  CGContextSetLineWidth(context, 0.5);
        CGContextSetStrokeColor(context, objj_msgSend(self, "foregroundColor"));
        CGContextStrokeEllipseInRect(context, objj_msgSend(self, "bounds"));
}
},["void","CGRect","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $CircleFigure__newAt_(self, _cmd, aPoint)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 50, 50);
 var widget = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:",  frame);
 return widget;
}
},["CircleFigure","CGPoint"]), new objj_method(sel_getUid("newWith:"), function $CircleFigure__newWith_(self, _cmd, aFrame)
{ with(self)
{
 var widget = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:",  aFrame);
 return widget;
}
},["CircleFigure","id"])]);
}

p;17;CompositeFigure.jt;1823;@STATIC;1.0;t;1804;{var the_class = objj_allocateClassPair(Figure, "CompositeFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("addFigure:"), function $CompositeFigure__addFigure_(self, _cmd, aFigure)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("CompositeFigure").super_class }, "addSubview:",  aFigure);
}
},["void","Figure"]), new objj_method(sel_getUid("removeFigure:"), function $CompositeFigure__removeFigure_(self, _cmd, aFigure)
{ with(self)
{
 objj_msgSend(aFigure, "removeFromSuperview");
}
},["void","Figure"]), new objj_method(sel_getUid("addSubview:"), function $CompositeFigure__addSubview_(self, _cmd, aView)
{ with(self)
{
 objj_msgSend(CPException, "raise:reason:",  "invalid method", "Use addFigure instead");
}
},["void","id"]), new objj_method(sel_getUid("clearFigures"), function $CompositeFigure__clearFigures(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "setSubviews:",  objj_msgSend(CPMutableArray, "array"));
}
},["void"]), new objj_method(sel_getUid("figures"), function $CompositeFigure__figures(self, _cmd)
{ with(self)
{
 var figures = objj_msgSend(CPMutableArray, "array");
 objj_msgSend(figures, "addObjectsFromArray:",  objj_msgSend(self, "subviews"));
 return figures;
}
},["id"]), new objj_method(sel_getUid("figuresIn:"), function $CompositeFigure__figuresIn_(self, _cmd, rect)
{ with(self)
{
 var result = objj_msgSend(CPMutableArray, "array");
 var figures = objj_msgSend(self, "subviews");
 for (var i = 0; i < objj_msgSend(figures, "count"); i++) {
  var figure = objj_msgSend(figures, "objectAtIndex:", i);
  if (CGRectContainsRect(rect, objj_msgSend(figure, "frame"))) {
   objj_msgSend(result, "addObject:",  figure);
  }
 }
 return result;
}
},["CPArray","id"])]);
}

p;12;Connection.jt;6549;@STATIC;1.0;t;6530;{var the_class = objj_allocateClassPair(Polyline, "Connection"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_sourceFigure"), new objj_ivar("_targetFigure"), new objj_ivar("_magnet1"), new objj_ivar("_magnet2"), new objj_ivar("_p1Arrow"), new objj_ivar("_p2Arrow")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithSource:target:points:"), function $Connection__initWithSource_target_points_(self, _cmd, aSourceFigure, aTargetFigure, anArrayOfPoints)
{ with(self)
{
 _sourceFigure = aSourceFigure;
 _targetFigure = aTargetFigure;
 var points = objj_msgSend(CPMutableArray, "array");
 if (anArrayOfPoints == nil) {
  objj_msgSend(points, "addObject:",  objj_msgSend(_sourceFigure, "center"));
  if (_sourceFigure == _targetFigure) {
   var center = objj_msgSend(_sourceFigure, "center");
   objj_msgSend(points, "addObject:",  CGPointMake(center.x + 100, center.y));
   objj_msgSend(points, "addObject:",  CGPointMake(center.x + 100, center.y - 100));
   objj_msgSend(points, "addObject:",  CGPointMake(center.x , center.y - 100));
  }
  objj_msgSend(points, "addObject:",  objj_msgSend(_targetFigure, "center"));
 } else {
  CPLog.debug("[Connection] Points " + anArrayOfPoints);
  objj_msgSend(points, "addObjectsFromArray:",  anArrayOfPoints);
 }
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Connection").super_class }, "initWithPoints:",  points);
 if (self) {
  objj_msgSend(self, "recomputeFrame");
  _magnet1 = objj_msgSend(objj_msgSend(HandleMagnet, "alloc"), "initWithHandle:source:target:",  objj_msgSend(self, "handleAt:",  ((objj_msgSend(points, "count") - 1) * 2)),  _sourceFigure,  _targetFigure);
  _magnet2 = objj_msgSend(objj_msgSend(HandleMagnet, "alloc"), "initWithHandle:source:target:",  objj_msgSend(self, "handleAt:",  0),  _targetFigure,  _sourceFigure);
  objj_msgSend(_magnet1, "updateHandleLocation:",  nil);
  objj_msgSend(_magnet2, "updateHandleLocation:",  nil);
  objj_msgSend(_sourceFigure, "addOutConnection:",  self);
  objj_msgSend(_targetFigure, "addInConnection:",  self);
  return self;
 }
}
},["id","Figure","Figure","id"]), new objj_method(sel_getUid("drawRect:on:"), function $Connection__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Connection").super_class }, "drawRect:on:",  rect,  context);
 var point = objj_msgSend(_points, "objectAtIndex:",  (objj_msgSend(_points, "count") - 1));
 var origin = objj_msgSend(self, "frameOrigin");
 CGContextBeginPath(context);
 CGContextMoveToPoint(context, _p1Arrow.x - origin.x, _p1Arrow.y - origin.y);
 CGContextAddLineToPoint(context, _p2Arrow.x - origin.x, _p2Arrow.y - origin.y);
 CGContextAddLineToPoint(context, point.x - origin.x, point.y - origin.y);
 CGContextClosePath(context);
 CGContextSetFillColor(context, objj_msgSend(self, "foregroundColor"));
 CGContextFillPath(context);
}
},["void","CGRect","id"]), new objj_method(sel_getUid("computeArrowPoints"), function $Connection__computeArrowPoints(self, _cmd)
{ with(self)
{
 var antPoint = objj_msgSend(_points, "objectAtIndex:",  (objj_msgSend(_points, "count") - 2));
 var point = objj_msgSend(_points, "objectAtIndex:",  (objj_msgSend(_points, "count") - 1));
 var p1 = nil;
 var p2 = nil;
 var xDiff = 5;
 var yDiff = 7;
 if (antPoint.x == point.x) {
  if (antPoint.y < point.y) {
   p1 = CPPointMake(point.x - xDiff, point.y - yDiff);
   p2 = CPPointMake(point.x + xDiff, point.y - yDiff);
  } else {
   p1 = CPPointMake(point.x - xDiff, point.y + yDiff);
   p2 = CPPointMake(point.x + xDiff, point.y + yDiff);
  }
 } else {
  if (antPoint.y == point.y) {
   if (antPoint.x < point.x) {
    p1 = CPPointMake(point.x - yDiff, point.y - xDiff);
    p2 = CPPointMake(point.x - yDiff, point.y + xDiff);
   } else {
    p1 = CPPointMake(point.x + yDiff, point.y - xDiff);
    p2 = CPPointMake(point.x + yDiff, point.y + xDiff);
   }
  } else {
   var lineVector = CPPointMake(point.x - antPoint.x, point.y - antPoint.y);
   var lineLength = SQRT((lineVector.x * lineVector.x) + (lineVector.y * lineVector.y));
   var pi = 3.14159265;
   var nWidth = 10;
   var fTheta = pi / 8;
   var tPointOnLine = nWidth / (2 * (TAN(fTheta) / 2) * lineLength);
   tPointOnLine = tPointOnLine / 3;
   var pointOnLine = CPPointMake(point.x + -tPointOnLine * lineVector.x, point.y + -tPointOnLine * lineVector.y);
   var normalVector = CPPointMake(-lineVector.y, lineVector.x);
   var tNormal = nWidth / (2 * lineLength);
   var leftPoint = CPPointMake(pointOnLine.x + tNormal * normalVector.x, pointOnLine.y + tNormal * normalVector.y);
   var rightPoint = CPPointMake(pointOnLine.x + -tNormal * normalVector.x, pointOnLine.y + -tNormal * normalVector.y);
   p1 = leftPoint;
   p2 = rightPoint;
  }
 }
 _p1Arrow = p1;
 _p2Arrow = p2;
}
},["void"]), new objj_method(sel_getUid("recomputeFrame"), function $Connection__recomputeFrame(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "computeArrowPoints");
 var pointsWithArrow = objj_msgSend(CPMutableArray, "arrayWithArray:",  _points);
 objj_msgSend(pointsWithArrow, "addObject:",  _p1Arrow);
 objj_msgSend(pointsWithArrow, "addObject:",  _p2Arrow);
 var newFrame = objj_msgSend(GeometryUtils, "computeFrameFor:",  pointsWithArrow);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void"]), new objj_method(sel_getUid("source"), function $Connection__source(self, _cmd)
{ with(self)
{
    return _sourceFigure;
}
},["Figure"]), new objj_method(sel_getUid("target"), function $Connection__target(self, _cmd)
{ with(self)
{
    return _targetFigure;
}
},["Figure"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("connect:with:"), function $Connection__connect_with_(self, _cmd, aTargetFigure, aSourceFigure)
{ with(self)
{
 return objj_msgSend(self, "source:target:",  aSourceFigure,  aTargetFigure);
}
},["Connection","Figure","Figure"]), new objj_method(sel_getUid("source:target:"), function $Connection__source_target_(self, _cmd, aSourceFigure, aTargetFigure)
{ with(self)
{
 return objj_msgSend(self, "source:target:points:",  aSourceFigure,  aTargetFigure,  nil);
}
},["Connection","Figure","Figure"]), new objj_method(sel_getUid("source:target:points:"), function $Connection__source_target_points_(self, _cmd, aSourceFigure, aTargetFigure, anArrayOfPoints)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithSource:target:points:",  aSourceFigure,  aTargetFigure,  anArrayOfPoints);
}
},["Connection","Figure","Figure","id"])]);
}

p;8;Figure.jt;16478;@STATIC;1.0;t;16458;{var the_class = objj_allocateClassPair(CPView, "Figure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("handles"), new objj_ivar("_inConnections"), new objj_ivar("_outConnections"), new objj_ivar("_backgroundColor"), new objj_ivar("_foregroundColor"), new objj_ivar("_selectable"), new objj_ivar("_moveable"), new objj_ivar("_editable"), new objj_ivar("_model"), new objj_ivar("_selected")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $Figure__init(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Figure").super_class }, "init");
 handles = objj_msgSend(CPMutableArray, "array");
 _inConnections = objj_msgSend(CPMutableArray, "array");
 _outConnections = objj_msgSend(CPMutableArray, "array");
 _backgroundColor = objj_msgSend(CPColor, "blackColor");
 _foregroundColor = _backgroundColor;
 _selectable = true;
 _moveable = true;
 _editable = true;
 _selected = false;
 objj_msgSend(self, "setPostsFrameChangedNotifications:",  YES);
 return self;
}
},["id"]), new objj_method(sel_getUid("figureAt:"), function $Figure__figureAt_(self, _cmd, aPoint)
{ with(self)
{
 var figureInSubfigures = objj_msgSend(self, "primSubfiguresAt:",  aPoint);
 if (figureInSubfigures != nil) {
  return figureInSubfigures;
 }
 return objj_msgSend(self, "primFigureAt:",  aPoint);
}
},["id","CPPoint"]), new objj_method(sel_getUid("primSubfiguresAt:"), function $Figure__primSubfiguresAt_(self, _cmd, aPoint)
{ with(self)
{
 var figures = objj_msgSend(self, "subviews");
 var origin = objj_msgSend(self, "frameOrigin");
 var translatedPoint = CGPointMake(aPoint.x - origin.x, aPoint.y - origin.y);
 for (var i = objj_msgSend(figures, "count") - 1; i >= 0 ; i--) {
     var figure = objj_msgSend(figures, "objectAtIndex:",  i);
  var result;
  if (objj_msgSend(figure, "respondsToSelector:",  sel_getUid("figureAt:"))) {
   result = objj_msgSend(figure, "figureAt:",  translatedPoint);
  } else {
   if (CPRectContainsPoint(objj_msgSend(figure, "frame"), translatedPoint)) {
    result = self;
   } else {
    result = nil;
   }
  }
  if (result != nil) {
   return result;
  }
 }
}
},["id","CPPoint"]), new objj_method(sel_getUid("primFigureAt:"), function $Figure__primFigureAt_(self, _cmd, aPoint)
{ with(self)
{
 var frame = objj_msgSend(self, "frame");
 if (CPRectContainsPoint(frame, aPoint)) {
  return self;
 } else {
  return nil;
 }
}
},["id","CPPoint"]), new objj_method(sel_getUid("globalToLocal:"), function $Figure__globalToLocal_(self, _cmd, aPoint)
{ with(self)
{
 var current = self;
 var offset = CGPointMake(0, 0);
 while (current != nil && !objj_msgSend(current, "isKindOfClass:", objj_msgSend(Drawing, "class"))) {
  var frameOrigin = objj_msgSend(current, "frameOrigin");
  offset = CGPointMake(offset.x - frameOrigin.x, offset.y - frameOrigin.y);
  current = objj_msgSend(current, "superview");
 }
 var result = CGPointMake(aPoint.x + offset.x, aPoint.y + offset.y);
 return result;
}
},["void","CPPoint"]), new objj_method(sel_getUid("addInConnection:"), function $Figure__addInConnection_(self, _cmd, aConnection)
{ with(self)
{
 objj_msgSend(_inConnections, "addObject:",  aConnection);
}
},["void","id"]), new objj_method(sel_getUid("addOutConnection:"), function $Figure__addOutConnection_(self, _cmd, aConnection)
{ with(self)
{
 objj_msgSend(_outConnections, "addObject:",  aConnection);
}
},["void","id"]), new objj_method(sel_getUid("moveTo:"), function $Figure__moveTo_(self, _cmd, aPoint)
{ with(self)
{
 if (_moveable) {
  objj_msgSend(self, "setFrameOrigin:",  aPoint);
 }
}
},["void","CGPoint"]), new objj_method(sel_getUid("translateBy:"), function $Figure__translateBy_(self, _cmd, aPoint)
{ with(self)
{
 if (_moveable) {
  var frameOrigin = objj_msgSend(self, "frameOrigin");
  var newFrameOrigin = CGPointMake(frameOrigin.x + aPoint.x, frameOrigin.y + aPoint.y);
  objj_msgSend(self, "setFrameOrigin:",  newFrameOrigin);
 }
}
},["void","CGPoint"]), new objj_method(sel_getUid("handleAt:"), function $Figure__handleAt_(self, _cmd, anIndex)
{ with(self)
{
 return objj_msgSend(handles, "objectAtIndex:",  anIndex);
}
},["id","int"]), new objj_method(sel_getUid("borderColor"), function $Figure__borderColor(self, _cmd)
{ with(self)
{
 return objj_msgSend(CPColor, "blackColor");
}
},["CPColor"]), new objj_method(sel_getUid("handleColor"), function $Figure__handleColor(self, _cmd)
{ with(self)
{
 return objj_msgSend(self, "borderColor");
}
},["CPColor"]), new objj_method(sel_getUid("isSelectable"), function $Figure__isSelectable(self, _cmd)
{ with(self)
{
 return _selectable;
}
},["bool"]), new objj_method(sel_getUid("isMoveable"), function $Figure__isMoveable(self, _cmd)
{ with(self)
{
 return _moveable;
}
},["bool"]), new objj_method(sel_getUid("isEditable"), function $Figure__isEditable(self, _cmd)
{ with(self)
{
 return _editable;
}
},["bool"]), new objj_method(sel_getUid("selectable:"), function $Figure__selectable_(self, _cmd, aValue)
{ with(self)
{
 _selectable = aValue;
}
},["void","boolean"]), new objj_method(sel_getUid("moveable:"), function $Figure__moveable_(self, _cmd, aValue)
{ with(self)
{
 _moveable = aValue;
}
},["void","boolean"]), new objj_method(sel_getUid("editable:"), function $Figure__editable_(self, _cmd, aValue)
{ with(self)
{
 _editable = aValue;
}
},["void","boolean"]), new objj_method(sel_getUid("isHandle"), function $Figure__isHandle(self, _cmd)
{ with(self)
{
 return false;
}
},["bool"]), new objj_method(sel_getUid("invalidate"), function $Figure__invalidate(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "setNeedsDisplay:",  YES);
}
},["void"]), new objj_method(sel_getUid("switchToEditMode"), function $Figure__switchToEditMode(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("update"), function $Figure__update(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("select"), function $Figure__select(self, _cmd)
{ with(self)
{
 _selected = true;
 var container = objj_msgSend(self, "superview");
 for (var i = 0; i < objj_msgSend(handles, "count"); i++) {
     var handle = objj_msgSend(handles, "objectAtIndex:", i);
  objj_msgSend(container, "addFigure:",  handle);
 }
}
},["void"]), new objj_method(sel_getUid("unselect"), function $Figure__unselect(self, _cmd)
{ with(self)
{
 _selected = false;
 for (var i = 0; i < objj_msgSend(handles, "count"); i++) {
     var handle = objj_msgSend(handles, "objectAtIndex:", i);
  objj_msgSend(handle, "removeFromSuperview");
 }
}
},["void"]), new objj_method(sel_getUid("drawing"), function $Figure__drawing(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "superview"), "drawing");
}
},["Drawing"]), new objj_method(sel_getUid("handles"), function $Figure__handles(self, _cmd)
{ with(self)
{
 return handles;
}
},["CPArray"]), new objj_method(sel_getUid("drawRect:"), function $Figure__drawRect_(self, _cmd, rect)
{ with(self)
{
    var context = objj_msgSend(objj_msgSend(CPGraphicsContext, "currentContext"), "graphicsPort");
 objj_msgSend(self, "drawRect:on:",  rect,  context);
}
},["void","CGRect"]), new objj_method(sel_getUid("drawRect:on:"), function $Figure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
}
},["void","CGRect","id"]), new objj_method(sel_getUid("topLeft"), function $Figure__topLeft(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x, objj_msgSend(self, "frame").origin.y);
}
},["CPPoint"]), new objj_method(sel_getUid("topLeft:"), function $Figure__topLeft_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = oldFrame.origin.x - aPoint.x;
 var heightOffset = oldFrame.origin.y - aPoint.y;
 var newFrame = CGRectMake(aPoint.x, aPoint.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("topMiddle"), function $Figure__topMiddle(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + (objj_msgSend(self, "frame").size.width / 2), objj_msgSend(self, "frame").origin.y);
}
},["CPPoint"]), new objj_method(sel_getUid("topMiddle:"), function $Figure__topMiddle_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = 0;
 var heightOffset = aPoint.y - oldFrame.origin.y;
 var newFrame = CGRectMake(oldFrame.origin.x, oldFrame.origin.y + heightOffset, oldFrame.size.width + widthOffset, oldFrame.size.height - heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("topRight"), function $Figure__topRight(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + objj_msgSend(self, "frame").size.width, objj_msgSend(self, "frame").origin.y);
}
},["CPPoint"]), new objj_method(sel_getUid("topRight:"), function $Figure__topRight_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = aPoint.x - (oldFrame.origin.x + oldFrame.size.width);
 var heightOffset = aPoint.y - oldFrame.origin.y;
 var newFrame = CGRectMake(oldFrame.origin.x, oldFrame.origin.y + heightOffset, oldFrame.size.width + widthOffset, oldFrame.size.height - heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("middleLeft"), function $Figure__middleLeft(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x, objj_msgSend(self, "frame").origin.y + (objj_msgSend(self, "frame").size.height / 2));
}
},["CPPoint"]), new objj_method(sel_getUid("middleLeft:"), function $Figure__middleLeft_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = oldFrame.origin.x - aPoint.x;
 var heightOffset = 0;
 var newFrame = CGRectMake(aPoint.x, oldFrame.origin.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("center"), function $Figure__center(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + (objj_msgSend(self, "frame").size.width / 2), objj_msgSend(self, "frame").origin.y + (objj_msgSend(self, "frame").size.height / 2));
}
},["CPPoint"]), new objj_method(sel_getUid("middleRight"), function $Figure__middleRight(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + objj_msgSend(self, "frame").size.width, objj_msgSend(self, "frame").origin.y + (objj_msgSend(self, "frame").size.height / 2));
}
},["CPPoint"]), new objj_method(sel_getUid("middleRight:"), function $Figure__middleRight_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = aPoint.x - (oldFrame.origin.x + oldFrame.size.width);
 var heightOffset = 0;
 var newFrame = CGRectMake(oldFrame.origin.x, oldFrame.origin.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("bottomLeft"), function $Figure__bottomLeft(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x, objj_msgSend(self, "frame").origin.y + objj_msgSend(self, "frame").size.height);
}
},["CPPoint"]), new objj_method(sel_getUid("bottomLeft:"), function $Figure__bottomLeft_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = oldFrame.origin.x - aPoint.x;
 var heightOffset = aPoint.y - (oldFrame.origin.y + oldFrame.size.height);
 var newFrame = CGRectMake(aPoint.x, oldFrame.origin.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("bottomMiddle"), function $Figure__bottomMiddle(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + (objj_msgSend(self, "frame").size.width / 2), objj_msgSend(self, "frame").origin.y + objj_msgSend(self, "frame").size.height);
}
},["CPPoint"]), new objj_method(sel_getUid("bottomMiddle:"), function $Figure__bottomMiddle_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = 0;
 var heightOffset = aPoint.y - (oldFrame.origin.y + oldFrame.size.height);
 var newFrame = CGRectMake(oldFrame.origin.x, oldFrame.origin.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("bottomRight"), function $Figure__bottomRight(self, _cmd)
{ with(self)
{
 return CGPointMake(objj_msgSend(self, "frame").origin.x + objj_msgSend(self, "frame").size.width, objj_msgSend(self, "frame").origin.y + objj_msgSend(self, "frame").size.height);
}
},["CPPoint"]), new objj_method(sel_getUid("bottomRight:"), function $Figure__bottomRight_(self, _cmd, aPoint)
{ with(self)
{
 var oldFrame = objj_msgSend(self, "frame");
 var widthOffset = aPoint.x - (oldFrame.origin.x + oldFrame.size.width);
 var heightOffset = aPoint.y - (oldFrame.origin.y + oldFrame.size.height);
 var newFrame = CGRectMake(oldFrame.origin.x, oldFrame.origin.y, oldFrame.size.width + widthOffset, oldFrame.size.height + heightOffset);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void",null]), new objj_method(sel_getUid("backgroundColor"), function $Figure__backgroundColor(self, _cmd)
{ with(self)
{
 return _backgroundColor;
}
},["id"]), new objj_method(sel_getUid("backgroundColor:"), function $Figure__backgroundColor_(self, _cmd, aColor)
{ with(self)
{
 _backgroundColor = aColor;
}
},["void","CPColor"]), new objj_method(sel_getUid("foregroundColor"), function $Figure__foregroundColor(self, _cmd)
{ with(self)
{
 return _foregroundColor;
}
},["id"]), new objj_method(sel_getUid("foregroundColor:"), function $Figure__foregroundColor_(self, _cmd, aColor)
{ with(self)
{
 _foregroundColor = aColor;
}
},["void","CPColor"]), new objj_method(sel_getUid("model"), function $Figure__model(self, _cmd)
{ with(self)
{
 return _model;
}
},["id"]), new objj_method(sel_getUid("model:"), function $Figure__model_(self, _cmd, aModel)
{ with(self)
{
 if (_model != nil) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:",  self,  ModelPropertyChangedNotification,  _model);
 }
 _model = aModel;
 if (_model != nil) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("modelChanged"),  ModelPropertyChangedNotification,  _model);
 }
}
},["void",null]), new objj_method(sel_getUid("modelChanged"), function $Figure__modelChanged(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("fadeIn"), function $Figure__fadeIn(self, _cmd)
{ with(self)
{
 var frame = objj_msgSend(self, "frame");
 var animations = objj_msgSend(CPDictionary, "dictionaryWithObjects:forKeys:", [self, frame, frame, CPViewAnimationFadeInEffect],  [CPViewAnimationTargetKey, CPViewAnimationStartFrameKey, CPViewAnimationEndFrameKey, CPViewAnimationEffectKey]);
 var animation = objj_msgSend(objj_msgSend(CPViewAnimation, "alloc"), "initWithViewAnimations:",  objj_msgSend(CPArray, "arrayWithObject:",  animations));
 objj_msgSend(animation, "startAnimation");
}
},["void"]), new objj_method(sel_getUid("fadeOut"), function $Figure__fadeOut(self, _cmd)
{ with(self)
{
 var frame = objj_msgSend(self, "frame");
 var animations = objj_msgSend(CPDictionary, "dictionaryWithObjects:forKeys:", [self, frame, frame, CPViewAnimationFadeOutEffect],  [CPViewAnimationTargetKey, CPViewAnimationStartFrameKey, CPViewAnimationEndFrameKey, CPViewAnimationEffectKey]);
 var animation = objj_msgSend(objj_msgSend(CPViewAnimation, "alloc"), "initWithViewAnimations:",  objj_msgSend(CPArray, "arrayWithObject:",  animations));
 objj_msgSend(animation, "startAnimation");
}
},["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("frame:"), function $Figure__frame_(self, _cmd, aFrame)
{ with(self)
{
 var figure = objj_msgSend(self, "new");
 objj_msgSend(figure, "initWithFrame:",  aFrame);
 return figure;
}
},["Figure","CGRect"]), new objj_method(sel_getUid("newAt:"), function $Figure__newAt_(self, _cmd, aPoint)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 20, 20);
 var figure = objj_msgSend(self, "frame:",  frame);
 return figure;
}
},["Figure","CGPoint"])]);
}

p;13;GroupFigure.jt;2124;@STATIC;1.0;t;2105;{var the_class = objj_allocateClassPair(CompositeFigure, "GroupFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:"), function $GroupFigure__initWithFrame_(self, _cmd, aFrame)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("GroupFigure").super_class }, "initWithFrame:", aFrame);
 if (self) {
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topMiddle"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topRight"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleRight"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomMiddle"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomRight"));
  return self;
 }
}
},["id","CGRect"]), new objj_method(sel_getUid("figureAt:"), function $GroupFigure__figureAt_(self, _cmd, aPoint)
{ with(self)
{
 var figure = objj_msgSend(self, "primSubfiguresAt:",  aPoint);
 if (figure != nil) {
  return self;
 } else {
  return nil;
 }
}
},["id","CPPoint"]), new objj_method(sel_getUid("ungroup"), function $GroupFigure__ungroup(self, _cmd)
{ with(self)
{
 var parent = objj_msgSend(self, "superview");
 var figures = objj_msgSend(self, "figures");
 var translation = objj_msgSend(self, "frameOrigin");
 objj_msgSend(parent, "removeFigure:",  self);
 for (var i = 0; i < objj_msgSend(figures, "count"); i++) {
     var figure = objj_msgSend(figures, "objectAtIndex:",  i);
  objj_msgSend(figure, "translateBy:",  translation);
  objj_msgSend(parent, "addFigure:",  figure);
 }
 return figures;
}
},["id"])]);
}

p;8;Handle.jt;5555;@STATIC;1.0;t;5536;{var the_class = objj_allocateClassPair(Figure, "Handle"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_targetFigure"), new objj_ivar("_getSelector"), new objj_ivar("_setSelector"), new objj_ivar("_extraArgument"), new objj_ivar("_displayColor")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithTarget:getSelector:setSelector:extraArgument:"), function $Handle__initWithTarget_getSelector_setSelector_extraArgument_(self, _cmd, aTargetFigure, getSelector, setSelector, extraArgument)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Handle").super_class }, "init");
 _targetFigure = aTargetFigure;
 _getSelector = getSelector;
 _setSelector = setSelector;
 _extraArgument = extraArgument;
 _displayColor = objj_msgSend(_targetFigure, "handleColor");
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("updateLocation:"),  "CPViewFrameDidChangeNotification",  _targetFigure);
 var point = objj_msgSend(self, "getPoint");
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Handle").super_class }, "initWithFrame:",  CGRectMake(point.x - 4, point.y - 4, 8, 8));
 return self;
}
},["id","id","SEL","SEL","id"]), new objj_method(sel_getUid("extraArgument"), function $Handle__extraArgument(self, _cmd)
{ with(self)
{
 return _extraArgument;
}
},["id"]), new objj_method(sel_getUid("extraArgument:"), function $Handle__extraArgument_(self, _cmd, anExtraArgument)
{ with(self)
{
 _extraArgument = anExtraArgument;
}
},["void","id"]), new objj_method(sel_getUid("getSelector:setSelector:"), function $Handle__getSelector_setSelector_(self, _cmd, aGetSelector, aSetSelector)
{ with(self)
{
 _getSelector = aGetSelector;
 _setSelector = aSetSelector;
}
},["void","id","id"]), new objj_method(sel_getUid("getPoint"), function $Handle__getPoint(self, _cmd)
{ with(self)
{
 var point = nil;
 if (_extraArgument == nil) {
  point = objj_msgSend(_targetFigure, "performSelector:",  _getSelector);
 } else {
  point = objj_msgSend(_targetFigure, "performSelector:withObject:",  _getSelector,  _extraArgument);
 }
 return point;
}
},["CPPoint"]), new objj_method(sel_getUid("updateLocation:"), function $Handle__updateLocation_(self, _cmd, aNotification)
{ with(self)
{
 var point = objj_msgSend(self, "getPoint");
 var x = point.x - 4;
 var y = point.y - 4;
 var newOrigin = CGPointMake(x, y);
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Handle").super_class }, "setFrameOrigin:",  newOrigin);
}
},["void","id"]), new objj_method(sel_getUid("isHandle"), function $Handle__isHandle(self, _cmd)
{ with(self)
{
 return true;
}
},["bool"]), new objj_method(sel_getUid("isMoveable"), function $Handle__isMoveable(self, _cmd)
{ with(self)
{
 return true;
}
},["bool"]), new objj_method(sel_getUid("targetFigure"), function $Handle__targetFigure(self, _cmd)
{ with(self)
{
 return _targetFigure;
}
},["Figure"]), new objj_method(sel_getUid("setFrameOrigin:"), function $Handle__setFrameOrigin_(self, _cmd, aPoint)
{ with(self)
{
 if (_extraArgument == nil) {
  objj_msgSend(_targetFigure, "performSelector:withObject:",  _setSelector,  aPoint);
 } else {
  objj_msgSend(_targetFigure, "performSelector:withObject:withObject:",  _setSelector,  _extraArgument,  aPoint);
 }
}
},["void","CGPoint"]), new objj_method(sel_getUid("moveTo:"), function $Handle__moveTo_(self, _cmd, aPoint)
{ with(self)
{
 if (objj_msgSend(_targetFigure, "isEditable")) {
  objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Handle").super_class }, "moveTo:",  aPoint);
 }
}
},["void","CGPoint"]), new objj_method(sel_getUid("displayColor:"), function $Handle__displayColor_(self, _cmd, aDisplayColor)
{ with(self)
{
 _displayColor = aDisplayColor;
 objj_msgSend(self, "setNeedsDisplay:",  YES);
}
},["void","CPColor"]), new objj_method(sel_getUid("drawRect:on:"), function $Handle__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
 CGContextSetFillColor(context, _displayColor);
 CGContextFillRect(context, objj_msgSend(self, "bounds"));
 CGContextSetFillColor(context, objj_msgSend(CPColor, "whiteColor"));
 CGContextFillRect(context, CGRectMake(2, 2, 4, 4));
}
},["void","CGRect","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("target:selector:"), function $Handle__target_selector_(self, _cmd, aTargetFigure, aStringSelector)
{ with(self)
{
  return objj_msgSend(self, "target:selector:extraArgument:",  aTargetFigure,  aStringSelector,  nil);
}
},["id","id","CPString"]), new objj_method(sel_getUid("target:selector:extraArgument:"), function $Handle__target_selector_extraArgument_(self, _cmd, aTargetFigure, aStringSelector, extraArgument)
{ with(self)
{
 var getSelector = CPSelectorFromString(aStringSelector);
 var setSelector = CPSelectorFromString(objj_msgSend(aStringSelector, "stringByAppendingString:",  ":"));
 return objj_msgSend(self, "target:getSelector:setSelector:extraArgument:",  aTargetFigure,  getSelector,  setSelector,  extraArgument);
}
},["id","id","CPString","id"]), new objj_method(sel_getUid("target:getSelector:setSelector:extraArgument:"), function $Handle__target_getSelector_setSelector_extraArgument_(self, _cmd, aTargetFigure, getSelector, setSelector, extraArgument)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "alloc"), "initWithTarget:getSelector:setSelector:extraArgument:",  aTargetFigure,  getSelector,  setSelector,  extraArgument);
}
},["id","id","SEL","SEL","id"])]);
}

p;17;IconLabelFigure.jt;5078;@STATIC;1.0;t;5059;{var the_class = objj_allocateClassPair(Figure, "IconLabelFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_label"), new objj_ivar("_iconUrl"), new objj_ivar("_modelFeature"), new objj_ivar("_iconView")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:iconUrl:"), function $IconLabelFigure__initWithFrame_iconUrl_(self, _cmd, aFrame, iconUrl)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("IconLabelFigure").super_class }, "initWithFrame:", aFrame);
 if (self) {
  _iconUrl = iconUrl;
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleLeft"));
  objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleRight"));
  var label = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
  objj_msgSend(label, "setStringValue:",  "");
  objj_msgSend(label, "setTextColor:", objj_msgSend(CPColor, "blackColor"));
  objj_msgSend(label, "sizeToFit");
  objj_msgSend(label, "setFrameOrigin:",  CGPointMake(22, 4));
  objj_msgSend(self, "addSubview:",  label);
  _label = label;
  var icon = objj_msgSend(objj_msgSend(CPImage, "alloc"), "initWithContentsOfFile:size:",  _iconUrl, CGSizeMake(16, 16));
  var iconView = objj_msgSend(objj_msgSend(CPImageView, "alloc"), "initWithFrame:", CGRectMake(4, 4, 16, 160));
  objj_msgSend(iconView, "setHasShadow:", NO);
  objj_msgSend(iconView, "setImageScaling:", CPScaleNone);
  _iconView = iconView;
  var iconSize = objj_msgSend(icon, "size");
  objj_msgSend(iconView, "setFrameSize:",  iconSize);
  objj_msgSend(iconView, "setImage:",  icon);
  objj_msgSend(self, "addSubview:",  iconView);
  return self;
 }
}
},["id","CGRect","id"]), new objj_method(sel_getUid("switchToEditMode"), function $IconLabelFigure__switchToEditMode(self, _cmd)
{ with(self)
{
 if (objj_msgSend(self, "isEditable")) {
  var editorDelegate = objj_msgSend(objj_msgSend(EditorDelegate, "alloc"), "initWithWidget:label:window:figureContainer:drawing:",  _label,  _label,  objj_msgSend(self, "window"),  self,  objj_msgSend(self, "drawing"));
 }
}
},["void"]), new objj_method(sel_getUid("value"), function $IconLabelFigure__value(self, _cmd)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "model"), "propertyValue:",  _modelFeature);
}
},["id"]), new objj_method(sel_getUid("value:"), function $IconLabelFigure__value_(self, _cmd, aValue)
{ with(self)
{
 objj_msgSend(objj_msgSend(self, "model"), "propertyValue:be:",  _modelFeature,  aValue);
}
},["void","id"]), new objj_method(sel_getUid("setEditionResult:"), function $IconLabelFigure__setEditionResult_(self, _cmd, aValue)
{ with(self)
{
 if (_modelFeature != nil && (objj_msgSend(self, "model") != nil)) {
  objj_msgSend(self, "value:",  aValue);
 } else {
  objj_msgSend(self, "setLabelValue:",  aValue);
 }
}
},["void","String"]), new objj_method(sel_getUid("setLabelValue:"), function $IconLabelFigure__setLabelValue_(self, _cmd, aValue)
{ with(self)
{
 if (aValue == nil) {
  aValue = "";
 }
 objj_msgSend(_label, "setObjectValue:",  aValue);
 objj_msgSend(_label, "sizeToFit");
 var currentFrameSize = objj_msgSend(self, "frameSize");
 currentFrameSize.width = objj_msgSend(_label, "frameOrigin").x + objj_msgSend(_label, "frameSize").width;
 currentFrameSize.height = objj_msgSend(_label, "frameOrigin").y + objj_msgSend(_label, "frameSize").height;
 objj_msgSend(self, "setFrameSize:",  currentFrameSize);
}
},["void","String"]), new objj_method(sel_getUid("propertyChanged"), function $IconLabelFigure__propertyChanged(self, _cmd)
{ with(self)
{
 var value = objj_msgSend(self, "value");
 objj_msgSend(self, "setLabelValue:",  value);
}
},["void"]), new objj_method(sel_getUid("update"), function $IconLabelFigure__update(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "propertyChanged");
}
},["void"]), new objj_method(sel_getUid("checkModelFeature:"), function $IconLabelFigure__checkModelFeature_(self, _cmd, aModelFeature)
{ with(self)
{
 if (_modelFeature != nil && (objj_msgSend(self, "model") != nil)) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:",  self,  ModelPropertyChangedNotification,  objj_msgSend(self, "model"));
 }
 _modelFeature = aModelFeature
 if (_modelFeature != nil && (objj_msgSend(self, "model") != nil)) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("propertyChanged"),  ModelPropertyChangedNotification,  objj_msgSend(self, "model"));
  objj_msgSend(self, "propertyChanged");
 }
}
},["void","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:iconUrl:"), function $IconLabelFigure__newAt_iconUrl_(self, _cmd, aPoint, iconUrl)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 100, 25);
 var widget = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:iconUrl:",  frame,  iconUrl);
 return widget;
}
},["IconLabelFigure","CGPoint","id"])]);
}

p;13;ImageFigure.jt;3189;@STATIC;1.0;t;3170;{var the_class = objj_allocateClassPair(Figure, "ImageFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_textField"), new objj_ivar("_offset"), new objj_ivar("_showBorder")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWithImage:x:y:offset:"), function $ImageFigure__initializeWithImage_x_y_offset_(self, _cmd, stringResource, anX, anY, anOffset)
{ with(self)
{
 var frame = CGRectMake(anX, anY, 0, 0);
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ImageFigure").super_class }, "initWithFrame:",  frame);
 if (self) {
  var icon = objj_msgSend(objj_msgSend(CPImage, "alloc"), "initWithContentsOfFile:",  stringResource);
  _offset = anOffset;
  _showBorder = true;
  objj_msgSend(icon, "setDelegate:",  self);
  return self;
 }
}
},["id","id","id","id","id"]), new objj_method(sel_getUid("showBorder:"), function $ImageFigure__showBorder_(self, _cmd, aValue)
{ with(self)
{
 _showBorder = aValue;
}
},["void","boolean"]), new objj_method(sel_getUid("imageDidLoad:"), function $ImageFigure__imageDidLoad_(self, _cmd, image)
{ with(self)
{
 var size = objj_msgSend(image, "size");
 var iconView = objj_msgSend(objj_msgSend(CPImageView, "alloc"), "initWithFrame:",  CGRectMake(_offset, _offset, size.width, size.height));
 objj_msgSend(iconView, "setHasShadow:",  NO);
 objj_msgSend(iconView, "setImageScaling:",  CPScaleNone);
 var frameSize = CGSizeMake(size.width + (_offset * 2), size.height + (_offset * 2));
 objj_msgSend(iconView, "setImage:",  image);
 objj_msgSend(self, "addSubview:",  iconView);
 objj_msgSend(self, "setFrameSize:",  frameSize);
 objj_msgSend(self, "invalidate");
}
},["void","CPImage"]), new objj_method(sel_getUid("borderColor"), function $ImageFigure__borderColor(self, _cmd)
{ with(self)
{
 return objj_msgSend(CPColor, "colorWithHexString:",  "#EAEAEA");
}
},["CPColor"]), new objj_method(sel_getUid("drawRect:on:"), function $ImageFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
 CGContextSetFillColor(context, objj_msgSend(CPColor, "whiteColor"));
    CGContextFillRect(context, objj_msgSend(self, "bounds"));
 if (_showBorder) {
  CGContextSetLineWidth(context, 0.25);
     CGContextSetFillColor(context, objj_msgSend(self, "borderColor"));
     CGContextSetStrokeColor(context, objj_msgSend(self, "borderColor"));
     CGContextStrokeRect(context, objj_msgSend(self, "bounds"));
 }
}
},["void","CGRect","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("initializeWithImage:x:y:"), function $ImageFigure__initializeWithImage_x_y_(self, _cmd, stringResource, anX, anY)
{ with(self)
{
 return objj_msgSend(self, "initializeWithImage:x:y:offset:",  stringResource,  anX,  anY,  0);
}
},["ImageFigure","id","id","id"]), new objj_method(sel_getUid("initializeWithImage:x:y:offset:"), function $ImageFigure__initializeWithImage_x_y_offset_(self, _cmd, stringResource, anX, anY, anOffset)
{ with(self)
{
 var figure = objj_msgSend(objj_msgSend(self, "alloc"), "initializeWithImage:x:y:offset:",  stringResource,  anX,  anY,  anOffset);
 return figure;
}
},["ImageFigure","id","id","id","id"])]);
}

p;13;LabelFigure.jt;3648;@STATIC;1.0;t;3629;{var the_class = objj_allocateClassPair(Figure, "LabelFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_textField")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:textField:"), function $LabelFigure__initWithFrame_textField_(self, _cmd, aFrame, aTextField)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LabelFigure").super_class }, "initWithFrame:",  aFrame);
 _textField = aTextField;
 _backgroundColor = objj_msgSend(CPColor, "whiteColor");
 _foregroundColor = objj_msgSend(CPColor, "blackColor");
 objj_msgSend(self, "addSubview:",  _textField);
 return self;
}
},["id","CGRect","id"]), new objj_method(sel_getUid("figureAt:"), function $LabelFigure__figureAt_(self, _cmd, aPoint)
{ with(self)
{
 var frame = objj_msgSend(self, "frame");
 if (CPRectContainsPoint(frame, aPoint)) {
  return self;
 } else {
  return nil;
 }
}
},["void","CPPoint"]), new objj_method(sel_getUid("setText:"), function $LabelFigure__setText_(self, _cmd, aText)
{ with(self)
{
 objj_msgSend(_textField, "setStringValue:",  aText);
 objj_msgSend(_textField, "sizeToFit");
 objj_msgSend(self, "setFrameSize:",  objj_msgSend(_textField, "frameSize"));
}
},["void","id"]), new objj_method(sel_getUid("backgroundColor:"), function $LabelFigure__backgroundColor_(self, _cmd, aColor)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LabelFigure").super_class }, "backgroundColor:",  aColor);
 objj_msgSend(self, "invalidate");
}
},["void","CPColor"]), new objj_method(sel_getUid("foregroundColor:"), function $LabelFigure__foregroundColor_(self, _cmd, aColor)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LabelFigure").super_class }, "foregroundColor:",  aColor);
 objj_msgSend(_textField, "setTextColor:",  aColor);
}
},["void","CPColor"]), new objj_method(sel_getUid("drawRect:on:"), function $LabelFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
        CGContextSetFillColor(context, objj_msgSend(self, "backgroundColor"));
        CGContextFillRect(context, objj_msgSend(self, "bounds"));
}
},["void","CGRect","id"]), new objj_method(sel_getUid("isSelectable"), function $LabelFigure__isSelectable(self, _cmd)
{ with(self)
{
 return true;
}
},["bool"]), new objj_method(sel_getUid("isMoveable"), function $LabelFigure__isMoveable(self, _cmd)
{ with(self)
{
 return true;
}
},["bool"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("initializeWithText:at:"), function $LabelFigure__initializeWithText_at_(self, _cmd, text, aPoint)
{ with(self)
{
 var label = objj_msgSend(objj_msgSend(CPTextField, "alloc"), "initWithFrame:", CGRectMakeZero());
 objj_msgSend(label, "setStringValue:",  text);
 objj_msgSend(label, "setTextColor:",  objj_msgSend(CPColor, "blackColor"));
 objj_msgSend(label, "setFrameOrigin:",  CGPointMake(0, 0));
 objj_msgSend(label, "sizeToFit");
 objj_msgSend(label, "setBordered:",  NO);
 objj_msgSend(label, "setBezeled:",  NO);
 return objj_msgSend(self, "initializeWithTextField:at:",  label,  aPoint);
}
},["LabelFigure","id","CPPoint"]), new objj_method(sel_getUid("initializeWithTextField:at:"), function $LabelFigure__initializeWithTextField_at_(self, _cmd, textField, aPoint)
{ with(self)
{
 var textFrameSize = objj_msgSend(textField, "frameSize");
 var frame = CGRectMake(aPoint.x, aPoint.y, textFrameSize.width, textFrameSize.height);
 var label = objj_msgSend(objj_msgSend(self, "alloc"), "initWithFrame:textField:",  frame,  textField);
 return label;
}
},["LabelFigure","CPTextField","CPPoint"])]);
}

p;12;LinkFigure.jt;853;@STATIC;1.0;t;835;{var the_class = objj_allocateClassPair(LabelFigure, "LinkFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:textField:"), function $LinkFigure__initWithFrame_textField_(self, _cmd, aFrame, aTextField)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("LinkFigure").super_class }, "initWithFrame:textField:",  aFrame,  aTextField);
 objj_msgSend(_textField, "setTextColor:",  objj_msgSend(CPColor, "blueColor"));
 self._DOMElement.style.cursor = "pointer";
 return self;
}
},["id","CGRect","id"]), new objj_method(sel_getUid("mouseUp:"), function $LinkFigure__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 var url = objj_msgSend(_textField, "objectValue");
 window.open(url, '_blank');
}
},["void","CPEvent"])]);
}

p;10;Polyline.jt;6864;@STATIC;1.0;t;6845;var CachedNotificationCenter = nil;
{var the_class = objj_allocateClassPair(Figure, "Polyline"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_points"), new objj_ivar("_lineWidth")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithPoints:"), function $Polyline__initWithPoints_(self, _cmd, anArrayOfPoints)
{ with(self)
{
 CachedNotificationCenter = objj_msgSend(CPNotificationCenter, "defaultCenter");
 var frame = objj_msgSend(GeometryUtils, "computeFrameFor:",  anArrayOfPoints);
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Polyline").super_class }, "initWithFrame:",  frame);
 _points = objj_msgSend(CPMutableArray, "arrayWithArray:",  anArrayOfPoints);
 _lineWidth = 0.5;
 for (var i = 0; i < objj_msgSend(_points, "count"); i++) {
     var point = objj_msgSend(_points, "objectAtIndex:", i);
  objj_msgSend(handles, "addObject:",  objj_msgSend(self, "addNewHandle:",  i));
  if (i != objj_msgSend(_points, "count") - 1) {
   objj_msgSend(handles, "addObject:",  objj_msgSend(self, "addCreateHandle:",  i));
  }
 }
 if (self) {
  return self;
 }
}
},["id","CPArray"]), new objj_method(sel_getUid("points"), function $Polyline__points(self, _cmd)
{ with(self)
{
 return _points;
}
},["id"]), new objj_method(sel_getUid("addNewHandle:"), function $Polyline__addNewHandle_(self, _cmd, anIndex)
{ with(self)
{
 var newHandle = objj_msgSend(Handle, "target:getSelector:setSelector:extraArgument:",  self,  sel_getUid("pointAt:"),  sel_getUid("pointAt:put:"),  anIndex);
 return newHandle;
}
},["Handle","int"]), new objj_method(sel_getUid("addCreateHandle:"), function $Polyline__addCreateHandle_(self, _cmd, anIndex)
{ with(self)
{
 var createHandle = objj_msgSend(Handle, "target:getSelector:setSelector:extraArgument:",  self,  sel_getUid("insertionPointAt:"),  sel_getUid("createPointAt:put:"),  anIndex);
 objj_msgSend(createHandle, "displayColor:",  objj_msgSend(CPColor, "redColor"));
 return createHandle;
}
},["Handle","int"]), new objj_method(sel_getUid("isSelectable"), function $Polyline__isSelectable(self, _cmd)
{ with(self)
{
 return true;
}
},["bool"]), new objj_method(sel_getUid("pointAt:"), function $Polyline__pointAt_(self, _cmd, anIndex)
{ with(self)
{
 var point = objj_msgSend(_points, "objectAtIndex:",  anIndex);
 return point;
}
},["CPPoint","int"]), new objj_method(sel_getUid("pointAt:put:"), function $Polyline__pointAt_put_(self, _cmd, anIndex, aPoint)
{ with(self)
{
 objj_msgSend(_points, "replaceObjectAtIndex:withObject:",  anIndex,  aPoint);
 objj_msgSend(self, "recomputeFrame");
}
},["void","int","CPPoint"]), new objj_method(sel_getUid("insertionPointAt:"), function $Polyline__insertionPointAt_(self, _cmd, anIndex)
{ with(self)
{
 var point1 = objj_msgSend(_points, "objectAtIndex:",  anIndex);
 var point2 = objj_msgSend(_points, "objectAtIndex:",  (anIndex + 1));
 var x = (point1.x + point2.x) / 2;
 var y = (point1.y + point2.y) / 2;
 return CGPointMake(x,y);
}
},["CPPoint","int"]), new objj_method(sel_getUid("createPointAt:put:"), function $Polyline__createPointAt_put_(self, _cmd, anIndex, aPoint)
{ with(self)
{
 var insertIndex = anIndex + 1;
 objj_msgSend(_points, "insertObject:atIndex:",  aPoint,  insertIndex);
 var handleIndex = (anIndex * 2) + 1;
 var handleToConvert = objj_msgSend(handles, "objectAtIndex:",  handleIndex);
 objj_msgSend(handleToConvert, "displayColor:",  objj_msgSend(CPColor, "blackColor"));
 objj_msgSend(handleToConvert, "getSelector:setSelector:",  sel_getUid("pointAt:"),  sel_getUid("pointAt:put:"));
 objj_msgSend(handleToConvert, "extraArgument:",  insertIndex);
 var newCreateHandleBefore = objj_msgSend(self, "addCreateHandle:",  insertIndex - 1);
 var newCreateHandleAfter = objj_msgSend(self, "addCreateHandle:",  insertIndex);
 objj_msgSend(handles, "insertObject:atIndex:",  newCreateHandleAfter,  handleIndex + 1);
 objj_msgSend(handles, "insertObject:atIndex:",  newCreateHandleBefore,  handleIndex);
 for (var i = handleIndex; i < objj_msgSend(handles, "count"); i++) {
     var handle = objj_msgSend(handles, "objectAtIndex:",  i);
  var extraArg = FLOOR(i / 2);
  objj_msgSend(handle, "extraArgument:",  extraArg);
 }
 var diagram = objj_msgSend(self, "superview");
 objj_msgSend(diagram, "addFigure:",  newCreateHandleBefore);
 objj_msgSend(diagram, "addFigure:",  newCreateHandleAfter);
 objj_msgSend(self, "recomputeFrame");
}
},["void","int","CPPoint"]), new objj_method(sel_getUid("figureAt:"), function $Polyline__figureAt_(self, _cmd, aPoint)
{ with(self)
{
 for (var i = 0; i < objj_msgSend(_points, "count") - 1; i++) {
     var a = objj_msgSend(_points, "objectAtIndex:",  i);
     var b = objj_msgSend(_points, "objectAtIndex:",  (i + 1));
  if (objj_msgSend(GeometryUtils, "distanceFrom:and:to:",  a,  b,  aPoint) < 5) {
   return self;
  }
 }
 return nil;
}
},["void","CPPoint"]), new objj_method(sel_getUid("recomputeFrame"), function $Polyline__recomputeFrame(self, _cmd)
{ with(self)
{
 var newFrame = objj_msgSend(GeometryUtils, "computeFrameFor:",  _points);
 objj_msgSend(self, "setNeedsDisplay:",  YES);
 objj_msgSend(self, "setFrame:",  newFrame);
}
},["void"]), new objj_method(sel_getUid("drawRect:on:"), function $Polyline__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
 var origin = objj_msgSend(self, "frameOrigin");
    var intialPoint = objj_msgSend(_points, "objectAtIndex:", 0);
 CGContextBeginPath(context);
 CGContextMoveToPoint(context, intialPoint.x - origin.x, intialPoint.y - origin.y);
 for (var i = 1; i < objj_msgSend(_points, "count"); i++) {
     var point = objj_msgSend(_points, "objectAtIndex:", i);
  CGContextAddLineToPoint(context, point.x - origin.x, point.y - origin.y);
 }
 CGContextSetStrokeColor(context, objj_msgSend(self, "foregroundColor"));
 CGContextSetLineWidth(context, _lineWidth);
    CGContextStrokePath(context);
}
},["void","CGRect","id"]), new objj_method(sel_getUid("translatedBy:"), function $Polyline__translatedBy_(self, _cmd, aPoint)
{ with(self)
{
 var frame = objj_msgSend(self, "frame");
 var xOffset = aPoint.x - frame.origin.x;
 var yOffset = aPoint.y - frame.origin.y;
 for (var i = 0; i < objj_msgSend(_points, "count"); i++) {
     var point = objj_msgSend(_points, "objectAtIndex:", i);
  point = CGPointMake(point.x + xOffset, point.y + yOffset);
  objj_msgSend(_points, "replaceObjectAtIndex:withObject:",  i,  point);
 }
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("Polyline").super_class }, "translatedBy:",  aPoint);
}
},["void","CGPoint"]), new objj_method(sel_getUid("lineWidth"), function $Polyline__lineWidth(self, _cmd)
{ with(self)
{
 return _lineWidth;
}
},["id"]), new objj_method(sel_getUid("lineWidth:"), function $Polyline__lineWidth_(self, _cmd, aLineWidth)
{ with(self)
{
 _lineWidth = aLineWidth;
}
},["void",null])]);
}

p;18;PropertiesFigure.jt;6060;@STATIC;1.0;t;6041;{var the_class = objj_allocateClassPair(Figure, "PropertiesFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_drawing"), new objj_ivar("_selectedFigure"), new objj_ivar("_nameColumn"), new objj_ivar("_valueColumn"), new objj_ivar("_tableView")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:drawing:"), function $PropertiesFigure__initWithFrame_drawing_(self, _cmd, aFrame, aDrawing)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("PropertiesFigure").super_class }, "initWithFrame:", aFrame);
 if (self) {
  _drawing = aDrawing;
  _selectedFigure = nil;
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("selectionChanged"),  DrawingSelectionChangedNotification,  _drawing);
  var scrollFrame = CGRectMake(5, 0, 695, 100);
  var scrollView = objj_msgSend(objj_msgSend(CPScrollView, "alloc"), "initWithFrame:",  scrollFrame);
  objj_msgSend(scrollView, "setAutohidesScrollers:",  YES);
  _tableView = objj_msgSend(objj_msgSend(CPTableView, "alloc"), "initWithFrame:",  CGRectMakeZero());
  objj_msgSend(_tableView, "setDoubleAction:",  sel_getUid("doubleClick:") );
  objj_msgSend(_tableView, "setUsesAlternatingRowBackgroundColors:",  YES);
  objj_msgSend(_tableView, "setAutoresizingMask:",  CPViewWidthSizable | CPViewHeightSizable);
  _nameColumn = objj_msgSend(objj_msgSend(CPTableColumn, "alloc"), "initWithIdentifier:", "nameColumn");
  objj_msgSend(objj_msgSend(_nameColumn, "headerView"), "setStringValue:", "Name");
  objj_msgSend(_nameColumn, "setMinWidth:",  200);
  objj_msgSend(_nameColumn, "setEditable:",  NO);
  objj_msgSend(_tableView, "addTableColumn:",  _nameColumn);
  _valueColumn = objj_msgSend(objj_msgSend(CPCustomRowTableColumn, "alloc"), "initWithIdentifier:", "valueColumn");
  objj_msgSend(objj_msgSend(_valueColumn, "headerView"), "setStringValue:", "Value");
  objj_msgSend(_valueColumn, "setMinWidth:",  400);
  objj_msgSend(_valueColumn, "setEditable:",  YES);
  objj_msgSend(_tableView, "addTableColumn:",  _valueColumn);
     objj_msgSend(scrollView, "setDocumentView:",  _tableView);
     objj_msgSend(_tableView, "setDataSource:",  self);
     objj_msgSend(_tableView, "setDelegate:",  self);
     objj_msgSend(self, "addSubview:",  scrollView);
  objj_msgSend(scrollView, "setAutoresizingMask:",  CPViewWidthSizable);
  _selectable = true;
  _moveable = true;
  return self;
 }
}
},["id","CGRect","Drawing"]), new objj_method(sel_getUid("doubleClick:"), function $PropertiesFigure__doubleClick_(self, _cmd, anObject)
{ with(self)
{
 var column = 1;
 var row = objj_msgSend(_tableView, "selectedRow");
 objj_msgSend(_tableView, "editColumn:row:withEvent:select:",  column,  row,  nil, YES);
}
},["void","id"]), new objj_method(sel_getUid("numberOfRowsInTableView:"), function $PropertiesFigure__numberOfRowsInTableView_(self, _cmd, aTableView)
{ with(self)
{
    if (_selectedFigure == nil) {
  return 0;
 } else {
  var model = objj_msgSend(_selectedFigure, "model");
  if (model != nil) {
   var size = objj_msgSend(model, "propertiesSize");
   return size;
  } else {
   return 0;
  }
 }
}
},["int","CPTableView"]), new objj_method(sel_getUid("tableView:objectValueForTableColumn:row:"), function $PropertiesFigure__tableView_objectValueForTableColumn_row_(self, _cmd, aTableView, aTableColumn, rowIndex)
{ with(self)
{
 var model = objj_msgSend(_selectedFigure, "model");
 if (_nameColumn == aTableColumn) {
  return objj_msgSend(model, "propertyDisplayNameAt:",  rowIndex);
 } else {
  return objj_msgSend(model, "propertyValueAt:",  rowIndex);
 }
}
},["id","CPTableView","CPTableColumn","int"]), new objj_method(sel_getUid("tableView:setObjectValue:forTableColumn:row:"), function $PropertiesFigure__tableView_setObjectValue_forTableColumn_row_(self, _cmd, aTableView, aValue, aTableColumn, rowIndex)
{ with(self)
{
 var model = objj_msgSend(_selectedFigure, "model");
 if (aTableColumn == _valueColumn) {
  return objj_msgSend(model, "propertyValueAt:be:",  rowIndex,  aValue);
 }
}
},["void","CPTableView","id","CPTableColumn","int"]), new objj_method(sel_getUid("selectionChanged"), function $PropertiesFigure__selectionChanged(self, _cmd)
{ with(self)
{
 if (_selectedFigure != nil) {
  var model = objj_msgSend(_selectedFigure, "model");
  if (model != nil) {
   objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:",  self,  ModelPropertyChangedNotification,  model);
  }
 }
 _selectedFigure = objj_msgSend(_drawing, "selectedFigure");
 if (_selectedFigure != nil) {
  var model = objj_msgSend(_selectedFigure, "model");
  if (model != nil) {
   objj_msgSend(_valueColumn, "model:",  model);
   objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("reloadData"),  ModelPropertyChangedNotification,  model);
  }
 }
 objj_msgSend(self, "reloadData");
}
},["void"]), new objj_method(sel_getUid("reloadData"), function $PropertiesFigure__reloadData(self, _cmd)
{ with(self)
{
 objj_msgSend(_tableView, "reloadData");
}
},["void"]), new objj_method(sel_getUid("drawRect:on:"), function $PropertiesFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
    CGContextSetFillColor(context, objj_msgSend(CPColor, "lightGrayColor"));
    CGContextFillRect(context, objj_msgSend(self, "bounds"));
}
},["void","CGRect","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("defaultFrame"), function $PropertiesFigure__defaultFrame(self, _cmd)
{ with(self)
{
 return CGRectMake(10, 450, 700, 100);
}
},["CPRect"]), new objj_method(sel_getUid("newAt:drawing:"), function $PropertiesFigure__newAt_drawing_(self, _cmd, aPoint, aDrawing)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 700, 100);
 var figure = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:drawing:",  frame,  aDrawing);
 return figure;
}
},["PropertiesFigure","CGPoint","Drawing"])]);
}

p;17;RectangleFigure.jt;2499;@STATIC;1.0;t;2480;{var the_class = objj_allocateClassPair(Figure, "RectangleFigure"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithFrame:"), function $RectangleFigure__initWithFrame_(self, _cmd, aFrame)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("RectangleFigure").super_class }, "initWithFrame:",  aFrame);
 objj_msgSend(self, "backgroundColor:",  objj_msgSend(CPColor, "whiteColor"));
 objj_msgSend(self, "foregroundColor:",  objj_msgSend(CPColor, "blackColor"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topLeft"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topMiddle"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "topRight"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleLeft"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "middleRight"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomLeft"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomMiddle"));
 objj_msgSend(handles, "addObject:",  objj_msgSend(Handle, "target:selector:",  self,  "bottomRight"));
 return self;
}
},["id","CGRect"]), new objj_method(sel_getUid("drawRect:on:"), function $RectangleFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
        CGContextSetFillColor(context, objj_msgSend(self, "backgroundColor"));
        CGContextFillRect(context, objj_msgSend(self, "bounds"));
  CGContextSetLineWidth(context, 0.5);
        CGContextSetStrokeColor(context, objj_msgSend(self, "foregroundColor"));
        CGContextStrokeRect(context, objj_msgSend(self, "bounds"));
}
},["void","CGRect","id"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("newAt:"), function $RectangleFigure__newAt_(self, _cmd, aPoint)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 50, 50);
 var widget = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:",  frame);
 return widget;
}
},["RectangleFigure","CGPoint"]), new objj_method(sel_getUid("newWith:"), function $RectangleFigure__newWith_(self, _cmd, aFrame)
{ with(self)
{
 var widget = objj_msgSend(objj_msgSend(self, "new"), "initWithFrame:",  aFrame);
 return widget;
}
},["RectangleFigure","id"])]);
}

p;15;ToolboxFigure.jt;4975;@STATIC;1.0;t;4956;{var the_class = objj_allocateClassPair(Figure, "ToolboxFigure"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_drawing"), new objj_ivar("_buttonsMapping"), new objj_ivar("_currentColumn"), new objj_ivar("_maxColumn"), new objj_ivar("_currentY")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initializeWith:at:"), function $ToolboxFigure__initializeWith_at_(self, _cmd, aDrawing, aPoint)
{ with(self)
{
 var frame = CGRectMake(aPoint.x, aPoint.y, 50, 1);
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ToolboxFigure").super_class }, "initWithFrame:",  frame);
 if (self) {
  _drawing = aDrawing;
  _currentColumn = 1;
  _maxColumn = 2;
  _currentY = 15;
  _selectable = true;
  _moveable = true;
  _buttonsMapping = objj_msgSend(CPDictionary, "dictionary");
  return self;
 }
}
},["id","Drawing","CPPoint"]), new objj_method(sel_getUid("columns:"), function $ToolboxFigure__columns_(self, _cmd, columns)
{ with(self)
{
 _maxColumn = columns;
}
},["void","int"]), new objj_method(sel_getUid("addSeparator"), function $ToolboxFigure__addSeparator(self, _cmd)
{ with(self)
{
 _currentY = _currentY + 25;
 _currentColumn = 1;
}
},["void"]), new objj_method(sel_getUid("addTool:withTitle:image:"), function $ToolboxFigure__addTool_withTitle_image_(self, _cmd, aTool, aTitle, url)
{ with(self)
{
 var button = objj_msgSend(self, "addButtonWithTitle:image:action:",  aTitle,  url,  sel_getUid("selectTool:"));
 objj_msgSend(_buttonsMapping, "setObject:forKey:",  aTool,  button);
}
},["void","Tool","id","id"]), new objj_method(sel_getUid("selectTool:"), function $ToolboxFigure__selectTool_(self, _cmd, aButton)
{ with(self)
{
 var tool = objj_msgSend(_buttonsMapping, "objectForKey:",  aButton);
 objj_msgSend(_drawing, "tool:",  tool);
}
},["void","CPButton"]), new objj_method(sel_getUid("addCommand:withTitle:image:"), function $ToolboxFigure__addCommand_withTitle_image_(self, _cmd, aCommand, aTitle, url)
{ with(self)
{
 var button = objj_msgSend(self, "addButtonWithTitle:image:action:",  aTitle,  url,  sel_getUid("selectCommand:"));
 objj_msgSend(_buttonsMapping, "setObject:forKey:",  aCommand,  button);
}
},["void","Command","id","id"]), new objj_method(sel_getUid("selectCommand:"), function $ToolboxFigure__selectCommand_(self, _cmd, aButton)
{ with(self)
{
 var commandClass = objj_msgSend(_buttonsMapping, "objectForKey:",  aButton);
 var command = objj_msgSend(commandClass, "drawing:",  _drawing);
 objj_msgSend(command, "execute");
}
},["void","CPButton"]), new objj_method(sel_getUid("addButtonWithTitle:image:action:"), function $ToolboxFigure__addButtonWithTitle_image_action_(self, _cmd, aTitle, url, aSelector)
{ with(self)
{
 var buttonWidth = 30;
 var buttonHeight = 25;
 var button = objj_msgSend(CPButton, "buttonWithTitle:",  "");
 var y = _currentY;
 var x = (_currentColumn - 1) * buttonWidth;
 var origin = CGPointMake(x, y);
 objj_msgSend(button, "setFrameOrigin:",  origin);
 var icon = objj_msgSend(objj_msgSend(CPImage, "alloc"), "initWithContentsOfFile:",  url);
 objj_msgSend(button, "setImage:",  icon);
 objj_msgSend(button, "setButtonType:",  CPOnOffButton);
 objj_msgSend(button, "setBordered:",  YES);
 objj_msgSend(button, "setBezelStyle:",  CPRegularSquareBezelStyle);
 objj_msgSend(button, "setFrameSize:",  CGSizeMake(buttonWidth, buttonHeight));
 objj_msgSend(button, "setTarget:",  self);
 objj_msgSend(button, "setAction:",  aSelector);
 objj_msgSend(self, "addSubview:",  button);
 var newSize = CGSizeMake(buttonWidth * _maxColumn, _currentY + buttonHeight);
 objj_msgSend(self, "setFrameSize:",  newSize);
 if (_currentColumn == _maxColumn) {
  _currentY = _currentY + buttonHeight;
 }
 _currentColumn = _currentColumn + 1;
 if (_currentColumn > _maxColumn) {
  _currentColumn = 1;
 }
 return button;
}
},["CPButton","id","id","SEL"]), new objj_method(sel_getUid("drawRect:on:"), function $ToolboxFigure__drawRect_on_(self, _cmd, rect, context)
{ with(self)
{
    CGContextSetFillColor(context, objj_msgSend(CPColor, "lightGrayColor"));
    CGContextFillRect(context, objj_msgSend(self, "bounds"));
}
},["void","CGRect","id"]), new objj_method(sel_getUid("sizeToFit"), function $ToolboxFigure__sizeToFit(self, _cmd)
{ with(self)
{
 var currentTopLeft = objj_msgSend(self, "topLeft");
 var frame = objj_msgSend(GeometryUtils, "computeFrameForViews:",  objj_msgSend(self, "subviews"));
 frame.origin.y = currentTopLeft.y + frame.origin.y - 15;
 frame.origin.x = currentTopLeft.x;
 frame.size.height = frame.size.height + 15;
 objj_msgSend(self, "setFrame:",  frame);
}
},["void"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("initializeWith:at:"), function $ToolboxFigure__initializeWith_at_(self, _cmd, aDrawing, aPoint)
{ with(self)
{
 var figure = objj_msgSend(objj_msgSend(self, "new"), "initializeWith:at:",  aDrawing,  aPoint);
 return figure;
}
},["ToolboxFigure","Drawing","CPPoint"])]);
}

p;7;Model.jt;6246;@STATIC;1.0;t;6227;ModelPropertyChangedNotification = "ModelPropertyChangedNotification";
{var the_class = objj_allocateClassPair(CPObject, "Model"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_properties"), new objj_ivar("_propertiesByName"), new objj_ivar("_fireNotifications")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $Model__init(self, _cmd)
{ with(self)
{
 _properties = objj_msgSend(CPMutableArray, "array");
 _propertiesByName = objj_msgSend(CPDictionary, "dictionary");
 _fireNotifications = YES;
 return self;
}
},["id"]), new objj_method(sel_getUid("addProperty:"), function $Model__addProperty_(self, _cmd, aPropertyName)
{ with(self)
{
 CPLog.info(aPropertyName);
 objj_msgSend(self, "addProperty:value:",  aPropertyName,  nil);
}
},["void","id"]), new objj_method(sel_getUid("addProperty:value:"), function $Model__addProperty_value_(self, _cmd, aPropertyName, aValue)
{ with(self)
{
 objj_msgSend(self, "addProperty:displayName:value:",  aPropertyName,  aPropertyName,  aValue);
}
},["void","id","id"]), new objj_method(sel_getUid("addProperty:displayName:value:"), function $Model__addProperty_displayName_value_(self, _cmd, aPropertyName, aDisplayName, aValue)
{ with(self)
{
 objj_msgSend(self, "addProperty:displayName:value:editable:type:",  aPropertyName,  aDisplayName,  aValue,  YES,  PropertyTypeString);
}
},["void","id","id","id"]), new objj_method(sel_getUid("addProperty:displayName:value:type:"), function $Model__addProperty_displayName_value_type_(self, _cmd, aPropertyName, aDisplayName, aValue, aType)
{ with(self)
{
 objj_msgSend(self, "addProperty:displayName:value:editable:type:",  aPropertyName,  aDisplayName,  aValue,  YES,  aType);
}
},["void","id","id","id","id"]), new objj_method(sel_getUid("addProperty:value:editable:"), function $Model__addProperty_value_editable_(self, _cmd, aPropertyName, aValue, anEditableValue)
{ with(self)
{
 objj_msgSend(self, "addProperty:displayName:value:editable:type:",  aPropertyName,  aPropertyName,  aValue,  anEditableValue,  PropertyTypeString);
}
},["void","id","id","boolean"]), new objj_method(sel_getUid("addProperty:displayName:value:editable:type:"), function $Model__addProperty_displayName_value_editable_type_(self, _cmd, aPropertyName, aDisplayName, aValue, anEditableValue, aType)
{ with(self)
{
 var property = objj_msgSend(Property, "name:displayName:value:type:",  aPropertyName,  aDisplayName,  aValue,  aType);
 objj_msgSend(property, "editable:",  anEditableValue);
 objj_msgSend(_properties, "addObject:",  property);
 objj_msgSend(_propertiesByName, "setObject:forKey:",  property,  aPropertyName);
}
},["void","id","id","id","boolean","id"]), new objj_method(sel_getUid("propertiesSize"), function $Model__propertiesSize(self, _cmd)
{ with(self)
{
 return objj_msgSend(_properties, "count");
}
},["id"]), new objj_method(sel_getUid("propertyNameAt:"), function $Model__propertyNameAt_(self, _cmd, anIndex)
{ with(self)
{
 var property = objj_msgSend(_properties, "objectAtIndex:",  anIndex);
 return objj_msgSend(property, "name");
}
},["id","id"]), new objj_method(sel_getUid("propertyDisplayNameAt:"), function $Model__propertyDisplayNameAt_(self, _cmd, anIndex)
{ with(self)
{
 var property = objj_msgSend(_properties, "objectAtIndex:",  anIndex);
 return objj_msgSend(property, "displayName");
}
},["id","id"]), new objj_method(sel_getUid("propertyValueAt:"), function $Model__propertyValueAt_(self, _cmd, anIndex)
{ with(self)
{
 var property = objj_msgSend(_properties, "objectAtIndex:",  anIndex);
 return objj_msgSend(property, "value");
}
},["id","id"]), new objj_method(sel_getUid("propertyTypeAt:"), function $Model__propertyTypeAt_(self, _cmd, anIndex)
{ with(self)
{
 var property = objj_msgSend(_properties, "objectAtIndex:",  anIndex);
 return objj_msgSend(property, "type");
}
},["id","id"]), new objj_method(sel_getUid("propertyValue:"), function $Model__propertyValue_(self, _cmd, aName)
{ with(self)
{
 var property = objj_msgSend(_propertiesByName, "objectForKey:",  aName);
 return objj_msgSend(property, "value");
}
},["id","id"]), new objj_method(sel_getUid("propertyValue:be:"), function $Model__propertyValue_be_(self, _cmd, aName, aValue)
{ with(self)
{
 objj_msgSend(self, "basicPropertyValue:be:",  aName,  aValue);
}
},["void","id","id"]), new objj_method(sel_getUid("basicPropertyValue:be:"), function $Model__basicPropertyValue_be_(self, _cmd, aName, aValue)
{ with(self)
{
 var property = objj_msgSend(_propertiesByName, "objectForKey:",  aName);
 if (property != nil) {
  objj_msgSend(property, "value:",  aValue);
  CPLog.info("Setting property " + objj_msgSend(property, "name"));
  CPLog.info("Value set " + aValue);
  if (_fireNotifications) {
   objj_msgSend(self, "changed");
  }
 } else {
  CPLog.info("Property not found " + aName);
 }
}
},["void","id","id"]), new objj_method(sel_getUid("propertyValueAt:be:"), function $Model__propertyValueAt_be_(self, _cmd, anIndex, aValue)
{ with(self)
{
 var property = objj_msgSend(_properties, "objectAtIndex:",  anIndex);
 objj_msgSend(property, "value:",  aValue);
 if (_fireNotifications) {
  objj_msgSend(self, "changed");
 }
}
},["void","id","id"]), new objj_method(sel_getUid("changed"), function $Model__changed(self, _cmd)
{ with(self)
{
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "postNotificationName:object:",  ModelPropertyChangedNotification,  self);
}
},["void"]), new objj_method(sel_getUid("fireNotifications:"), function $Model__fireNotifications_(self, _cmd, aValue)
{ with(self)
{
 _fireNotifications = aValue;
}
},["void","bool"]), new objj_method(sel_getUid("initializeWithProperties:"), function $Model__initializeWithProperties_(self, _cmd, properties)
{ with(self)
{
 objj_msgSend(self, "fireNotifications:",  NO);
 var keys = objj_msgSend(properties, "allKeys");
 for (var i = 0; i < objj_msgSend(keys, "count"); i++) {
  var propertyName = objj_msgSend(keys, "objectAtIndex:", i);
  var propertyValue = objj_msgSend(properties, "valueForKey:",  propertyName);
  objj_msgSend(self, "basicPropertyValue:be:",  propertyName,  propertyValue);
 }
 objj_msgSend(self, "fireNotifications:",  YES);
}
},["void","id"])]);
}

p;10;Property.jt;2722;@STATIC;1.0;t;2703;PropertyTypeBoolean = "TYPE_BOOLEAN";
PropertyTypeInteger = "TYPE_INTEGER";
PropertyTypeFloat = "TYPE_FLOAT";
PropertyTypeString = "TYPE_STRING";
{var the_class = objj_allocateClassPair(CPObject, "Property"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_name"), new objj_ivar("_displayName"), new objj_ivar("_value"), new objj_ivar("_type"), new objj_ivar("_editable")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithName:displayName:value:type:"), function $Property__initWithName_displayName_value_type_(self, _cmd, aPropertyName, aDisplayName, aValue, aType)
{ with(self)
{
 _name = aPropertyName;
 _value = aValue;
 _displayName = aDisplayName;
 _hidden = NO;
 _type = aType;
 return self;
}
},["id","id","id","id","id"]), new objj_method(sel_getUid("name"), function $Property__name(self, _cmd)
{ with(self)
{
 return _name;
}
},["id"]), new objj_method(sel_getUid("type"), function $Property__type(self, _cmd)
{ with(self)
{
 return _type;
}
},["id"]), new objj_method(sel_getUid("displayName"), function $Property__displayName(self, _cmd)
{ with(self)
{
 return _displayName;
}
},["id"]), new objj_method(sel_getUid("value"), function $Property__value(self, _cmd)
{ with(self)
{
 return _value;
}
},["id"]), new objj_method(sel_getUid("value:"), function $Property__value_(self, _cmd, aValue)
{ with(self)
{
 _value = aValue;
}
},["void",null]), new objj_method(sel_getUid("editable"), function $Property__editable(self, _cmd)
{ with(self)
{
 return _editable;
}
},["boolean"]), new objj_method(sel_getUid("editable:"), function $Property__editable_(self, _cmd, aValue)
{ with(self)
{
 _editable = aValue;
}
},["void",null])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("name:value:"), function $Property__name_value_(self, _cmd, aPropertyName, aValue)
{ with(self)
{
 return objj_msgSend(self, "name:displayName:value:",  aPropertyName,  aPropertyName,  aValue);
}
},["Property","id","id"]), new objj_method(sel_getUid("name:displayName:value:"), function $Property__name_displayName_value_(self, _cmd, aPropertyName, aDisplayName, aValue)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithName:displayName:value:type:",  aPropertyName,  aDisplayName,  aValue,  PropertyTypeString);
}
},["Property","id","id","id"]), new objj_method(sel_getUid("name:displayName:value:type:"), function $Property__name_displayName_value_type_(self, _cmd, aPropertyName, aDisplayName, aValue, aType)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithName:displayName:value:type:",  aPropertyName,  aDisplayName,  aValue,  aType);
}
},["Property","id","id","id","id"])]);
}

p;30;AbstractCreateConnectionTool.jt;4404;@STATIC;1.0;t;4385;{var the_class = objj_allocateClassPair(Tool, "AbstractCreateConnectionTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_connection"), new objj_ivar("_initialFigure"), new objj_ivar("_figureClass"), new objj_ivar("_validStartingConnection")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("figureClass:"), function $AbstractCreateConnectionTool__figureClass_(self, _cmd, aFigureClass)
{ with(self)
{
 _figureClass = aFigureClass;
}
},["void","id"]), new objj_method(sel_getUid("mouseDown:"), function $AbstractCreateConnectionTool__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 var figure = objj_msgSend(_drawing, "figureAt:",  point);
 var figureAcceptsNewConnection = objj_msgSend(self, "acceptsNewStartingConnection:",  figure);
 _validStartingConnection = figureAcceptsNewConnection;
 var points = objj_msgSend(CPMutableArray, "array");
 objj_msgSend(points, "addObject:",  objj_msgSend(figure, "center"));
 objj_msgSend(points, "addObject:",  objj_msgSend(figure, "center"));
 var connection = objj_msgSend(objj_msgSend(Connection, "alloc"), "initWithPoints:",  points);
 objj_msgSend(connection, "recomputeFrame");
 objj_msgSend(_drawing, "addFigure:",  connection);
 _connection = connection;
 _initialFigure = figure;
 if (!_validStartingConnection) {
  objj_msgSend(_connection, "foregroundColor:",  objj_msgSend(CPColor, "colorWithHexString:",  "CC0000"));
  objj_msgSend(_connection, "lineWidth:",  2);
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $AbstractCreateConnectionTool__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 if (_connection != nil) {
  var point = objj_msgSend(anEvent, "locationInWindow");
  point = CGPointMake(point.x - 6, point.y - 6);
  objj_msgSend(_connection, "pointAt:put:",  1,  point);
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $AbstractCreateConnectionTool__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 CPLog.debug("[CreateConnectionTool] Mouse up");
 if (_validStartingConnection) {
  var point = objj_msgSend(anEvent, "locationInWindow");
  var figure = objj_msgSend(_drawing, "figureAt:",  point);
  CPLog.debug("[CreateConnectionTool] Mouse up figure: " + figure);
  var acceptsNewEndingConnection = objj_msgSend(self, "acceptsNewEndingConnection:",  figure);
  if (acceptsNewEndingConnection) {
   objj_msgSend(self, "createFigureFrom:target:points:",  _initialFigure,  figure,  nil);
  } else {
   objj_msgSend(_connection, "foregroundColor:",  objj_msgSend(CPColor, "colorWithHexString:",  "CC0000"));
   objj_msgSend(_connection, "lineWidth:",  2);
   objj_msgSend(_connection, "invalidate");
  }
 } else {
  CPLog.debug("[CreateConnectionTool] Connection is nil");
 }
 if (_connection != nil && (objj_msgSend(_connection, "superview") != nil)) {
  objj_msgSend(_connection, "removeFromSuperview");
 }
 _connection = nil;
 _initialFigure = nil;
 objj_msgSend(self, "activateSelectionTool");
}
},["void","CPEvent"]), new objj_method(sel_getUid("createFigureFrom:target:points:"), function $AbstractCreateConnectionTool__createFigureFrom_target_points_(self, _cmd, source, target, points)
{ with(self)
{
 var connectionFigure = objj_msgSend(_figureClass, "source:target:points:",  source,  target,  points);
 objj_msgSend(_drawing, "addFigure:",  connectionFigure);
 objj_msgSend(self, "postConnectionCreated:",  connectionFigure);
}
},["void","id","id","id"]), new objj_method(sel_getUid("postConnectionCreated:"), function $AbstractCreateConnectionTool__postConnectionCreated_(self, _cmd, aConnectionFigure)
{ with(self)
{
}
},["void","Connection"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:"), function $AbstractCreateConnectionTool__drawing_(self, _cmd, aDrawing)
{ with(self)
{
 return objj_msgSend(self, "drawing:figure:",  aDrawing,  objj_msgSend(Connection, "class"));
}
},["id","Drawing"]), new objj_method(sel_getUid("drawing:figure:"), function $AbstractCreateConnectionTool__drawing_figure_(self, _cmd, aDrawing, aFigureClass)
{ with(self)
{
 var tool = objj_msgSendSuper({ receiver:self, super_class:objj_getMetaClass("AbstractCreateConnectionTool").super_class }, "drawing:",  aDrawing);
 objj_msgSend(tool, "figureClass:",  aFigureClass);
 return tool;
}
},["id","Drawing","id"])]);
}

p;26;AbstractCreateFigureTool.jt;653;@STATIC;1.0;t;635;{var the_class = objj_allocateClassPair(Tool, "AbstractCreateFigureTool"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("mouseDown:"), function $AbstractCreateFigureTool__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 objj_msgSend(self, "createFigureAt:on:",  point,  objj_msgSend(self, "drawing"));
}
},["void","CPEvent"]), new objj_method(sel_getUid("createFigureAt:on:"), function $AbstractCreateFigureTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{ with(self)
{
}
},["void",null,null])]);
}

p;17;CreateImageTool.jt;2182;@STATIC;1.0;t;2163;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateImageTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_editableLabel"), new objj_ivar("_point")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateImageTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{ with(self)
{
 _editableLabel = objj_msgSend(CPCancelableTextField, "textFieldWithStringValue:placeholder:width:",  "",  "Insert url",  100);
 _point = aPoint;
 objj_msgSend(_editableLabel, "setEditable:",  YES);
 objj_msgSend(_editableLabel, "setBordered:",  YES);
 objj_msgSend(_editableLabel, "setFrameOrigin:",  aPoint);
 objj_msgSend(_editableLabel, "cancelator:",  self);
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("controlTextDidEndEditing:"),  CPControlTextDidEndEditingNotification,  _editableLabel);
 objj_msgSend(aDrawing, "addFigure:",  _editableLabel);
 objj_msgSend(objj_msgSend(aDrawing, "window"), "makeFirstResponder:",  _editableLabel);
}
},["void","id","id"]), new objj_method(sel_getUid("cancelEditing"), function $CreateImageTool__cancelEditing(self, _cmd)
{ with(self)
{
 if (_editableLabel != nil) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:", self,  CPControlTextDidEndEditingNotification,  _editableLabel);
  objj_msgSend(_editableLabel, "removeFromSuperview");
  var drawing = objj_msgSend(self, "drawing");
  objj_msgSend(objj_msgSend(drawing, "window"), "makeFirstResponder:",  drawing);
 }
}
},["void"]), new objj_method(sel_getUid("controlTextDidEndEditing:"), function $CreateImageTool__controlTextDidEndEditing_(self, _cmd, notification)
{ with(self)
{
 var url = objj_msgSend(_editableLabel, "objectValue");
 var image = objj_msgSend(ImageFigure, "initializeWithImage:x:y:offset:",  url,  _point.x,  _point.y,  3);
 objj_msgSend(objj_msgSend(self, "drawing"), "addFigure:",  image);
 objj_msgSend(self, "cancelEditing");
 objj_msgSend(self, "activateSelectionTool");
}
},["void","CPNotification"])]);
}

p;17;CreateLabelTool.jt;2158;@STATIC;1.0;t;2139;{var the_class = objj_allocateClassPair(AbstractCreateFigureTool, "CreateLabelTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_editableLabel"), new objj_ivar("_point")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("createFigureAt:on:"), function $CreateLabelTool__createFigureAt_on_(self, _cmd, aPoint, aDrawing)
{ with(self)
{
 _editableLabel = objj_msgSend(CPCancelableTextField, "textFieldWithStringValue:placeholder:width:",  "",  "Insert url",  100);
 _point = aPoint;
 objj_msgSend(_editableLabel, "setEditable:",  YES);
 objj_msgSend(_editableLabel, "setBordered:",  YES);
 objj_msgSend(_editableLabel, "setFrameOrigin:",  aPoint);
 objj_msgSend(_editableLabel, "cancelator:",  self);
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("controlTextDidEndEditing:"),  CPControlTextDidEndEditingNotification,  _editableLabel);
 objj_msgSend(aDrawing, "addFigure:",  _editableLabel);
 objj_msgSend(objj_msgSend(aDrawing, "window"), "makeFirstResponder:",  _editableLabel);
}
},["void","id","id"]), new objj_method(sel_getUid("cancelEditing"), function $CreateLabelTool__cancelEditing(self, _cmd)
{ with(self)
{
 if (_editableLabel != nil) {
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:", self,  CPControlTextDidEndEditingNotification,  _editableLabel);
  objj_msgSend(_editableLabel, "removeFromSuperview");
  var drawing = objj_msgSend(self, "drawing");
  objj_msgSend(objj_msgSend(drawing, "window"), "makeFirstResponder:",  drawing);
 }
}
},["void"]), new objj_method(sel_getUid("controlTextDidEndEditing:"), function $CreateLabelTool__controlTextDidEndEditing_(self, _cmd, notification)
{ with(self)
{
 var text = objj_msgSend(_editableLabel, "objectValue");
 var image = objj_msgSend(LabelFigure, "initializeWithText:at:",  text,  _point);
 objj_msgSend(objj_msgSend(self, "drawing"), "addFigure:",  image);
 objj_msgSend(self, "cancelEditing");
 objj_msgSend(self, "activateSelectionTool");
}
},["void","CPNotification"])]);
}

p;23;MarqueeSelectionState.jt;2879;@STATIC;1.0;t;2860;{var the_class = objj_allocateClassPair(ToolState, "MarqueeSelectionState"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_initialDragPoint"), new objj_ivar("_rectangleFigure")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithInitialDragPoint:"), function $MarqueeSelectionState__initWithInitialDragPoint_(self, _cmd, anInitialDragPoint)
{ with(self)
{
 _initialDragPoint = anInitialDragPoint;
 _rectangleFigure = objj_msgSend(RectangleFigure, "newAt:",  _initialDragPoint);
 objj_msgSend(_rectangleFigure, "backgroundColor:",  objj_msgSend(CPColor, "colorWithWhite:alpha:",  0,  0));
 var frame = CGRectMake(_initialDragPoint.x, _initialDragPoint.y, 1, 1);
 objj_msgSend(_rectangleFigure, "setFrame:",  frame);
 objj_msgSend(objj_msgSend(_tool, "drawing"), "addFigure:",  _rectangleFigure);
 return self;
}
},["id","CPPoint"]), new objj_method(sel_getUid("mouseDragged:"), function $MarqueeSelectionState__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 var frame = objj_msgSend(self, "computeFrame:",  anEvent);
 objj_msgSend(_rectangleFigure, "setFrame:",  frame);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $MarqueeSelectionState__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 var frame = objj_msgSend(self, "computeFrame:",  anEvent);
 objj_msgSend(_rectangleFigure, "removeFromSuperview");
 var figures = objj_msgSend(objj_msgSend(_tool, "drawing"), "figuresIn:",  frame);
 if (objj_msgSend(figures, "count") > 0) {
  for (var i = 0; i < objj_msgSend(figures, "count"); i++) {
   var selectedFigure = objj_msgSend(figures, "objectAtIndex:",  i);
   objj_msgSend(_tool, "select:",  selectedFigure);
  }
  objj_msgSend(self, "transitionTo:",  objj_msgSend(SelectedState, "tool:initialDragPoint:",  _tool,  nil));
 } else {
  objj_msgSend(_tool, "clearSelection");
  objj_msgSend(self, "transitionToInitialState");
  objj_msgSend(_tool, "select:",  objj_msgSend(_tool, "drawing"));
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("computeFrame:"), function $MarqueeSelectionState__computeFrame_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 var x = MIN(_initialDragPoint.x, point.x);
 var y = MIN(_initialDragPoint.y, point.y);
 var width = ABS(_initialDragPoint.x - point.x);
 var height = ABS(_initialDragPoint.y - point.y);
 var frame = CGRectMake(x, y, width, height);
 return frame;
}
},["id","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:initialDragPoint:"), function $MarqueeSelectionState__tool_initialDragPoint_(self, _cmd, aTool, anInitialDragPoint)
{ with(self)
{
 var state = objj_msgSend(self, "tool:",  aTool);
 objj_msgSend(state, "initWithInitialDragPoint:",  anInitialDragPoint);
 return state;
}
},["id","StateMachineTool","CPPoint"])]);
}

p;18;MoveFiguresState.jt;2235;@STATIC;1.0;t;2216;{var the_class = objj_allocateClassPair(ToolState, "MoveFiguresState"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_initialDragPoint")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithInitialDragPoint:"), function $MoveFiguresState__initWithInitialDragPoint_(self, _cmd, anInitialDragPoint)
{ with(self)
{
 _initialDragPoint = anInitialDragPoint;
 return self;
}
},["id","CPPoint"]), new objj_method(sel_getUid("mouseDragged:"), function $MoveFiguresState__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 var newLocation = objj_msgSend(anEvent, "locationInWindow");
 var dragXOffset = newLocation.x - _initialDragPoint.x;
 var dragYOffset = newLocation.y - _initialDragPoint.y;
 var selectedFigures = objj_msgSend(_tool, "selectedFigures");
 var snapToGrid = objj_msgSend(objj_msgSend(_tool, "drawing"), "snapToGrid");
 var gridSize = objj_msgSend(objj_msgSend(_tool, "drawing"), "gridSize");
 for (var i = 0; i < objj_msgSend(selectedFigures, "count"); i++) {
  var selectedFigure = objj_msgSend(selectedFigures, "objectAtIndex:", i);
  var initialFigurePosition = objj_msgSend(_tool, "initialPositionOf:",  selectedFigure);
  var newOrigin = CGPointMake(initialFigurePosition.x + dragXOffset, initialFigurePosition.y + dragYOffset);
  if (snapToGrid) {
   newOrigin = CGPointMake(ROUND(newOrigin.x / gridSize) * gridSize, ROUND(newOrigin.y / gridSize) * gridSize);
  }
  objj_msgSend(selectedFigure, "moveTo:",  newOrigin);
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $MoveFiguresState__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 objj_msgSend(self, "transitionTo:",  objj_msgSend(SelectedState, "tool:initialDragPoint:",  _tool,  point));
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:initialDragPoint:"), function $MoveFiguresState__tool_initialDragPoint_(self, _cmd, aTool, anInitialDragPoint)
{ with(self)
{
 var state = objj_msgSend(self, "tool:",  aTool);
 objj_msgSend(state, "initWithInitialDragPoint:",  anInitialDragPoint);
 return state;
}
},["id","StateMachineTool","CPPoint"])]);
}

p;17;MoveHandleState.jt;2131;@STATIC;1.0;t;2112;{var the_class = objj_allocateClassPair(ToolState, "MoveHandleState"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_initialDragPoint"), new objj_ivar("_handle")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithInitialDragPoint:handle:"), function $MoveHandleState__initWithInitialDragPoint_handle_(self, _cmd, anInitialDragPoint, aHandle)
{ with(self)
{
 _initialDragPoint = anInitialDragPoint;
 _handle = aHandle;
 return self;
}
},["id","CPPoint","Handle"]), new objj_method(sel_getUid("mouseDragged:"), function $MoveHandleState__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 var newLocation = objj_msgSend(anEvent, "locationInWindow");
 var dragXOffset = newLocation.x - _initialDragPoint.x;
 var dragYOffset = newLocation.y - _initialDragPoint.y;
 var snapToGrid = objj_msgSend(objj_msgSend(_tool, "drawing"), "snapToGrid");
 var gridSize = objj_msgSend(objj_msgSend(_tool, "drawing"), "gridSize");
 var initialFigurePosition = objj_msgSend(_tool, "initialPositionOf:",  _handle);
 var newOrigin = CGPointMake(initialFigurePosition.x + dragXOffset, initialFigurePosition.y + dragYOffset);
 if (snapToGrid) {
  newOrigin = CGPointMake(ROUND(newOrigin.x / gridSize) * gridSize, ROUND(newOrigin.y / gridSize) * gridSize);
 }
    objj_msgSend(_handle, "moveTo:",  newOrigin);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $MoveHandleState__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 objj_msgSend(self, "transitionTo:",  objj_msgSend(SelectedState, "tool:initialDragPoint:",  _tool,  point));
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:initialDragPoint:handle:"), function $MoveHandleState__tool_initialDragPoint_handle_(self, _cmd, aTool, anInitialDragPoint, aHandle)
{ with(self)
{
 var state = objj_msgSend(self, "tool:",  aTool);
 objj_msgSend(state, "initWithInitialDragPoint:handle:",  anInitialDragPoint,  aHandle);
 return state;
}
},["id","StateMachineTool","CPPoint","Handle"])]);
}

p;15;SelectedState.jt;2716;@STATIC;1.0;t;2697;{var the_class = objj_allocateClassPair(ToolState, "SelectedState"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_initialDragPoint")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithInitialDragPoint:"), function $SelectedState__initWithInitialDragPoint_(self, _cmd, anInitialDragPoint)
{ with(self)
{
 _initialDragPoint = anInitialDragPoint;
 return self;
}
},["id","CPPoint"]), new objj_method(sel_getUid("mouseDown:"), function $SelectedState__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 var drawing = objj_msgSend(_tool, "drawing");
 var figureUnderPoint = objj_msgSend(drawing, "figureAt:",  point);
 figureUnderPoint = objj_msgSend(_tool, "selectableFigure:",  figureUnderPoint);
 _initialDragPoint = point;
 if (figureUnderPoint == nil || figureUnderPoint == drawing) {
  objj_msgSend(_tool, "clearSelection");
  objj_msgSend(self, "transitionToInitialState");
 } else {
  if (!objj_msgSend(figureUnderPoint, "isHandle")) {
   var selectedFigures = objj_msgSend(_tool, "selectedFigures");
   var wasSelected = objj_msgSend(selectedFigures, "containsObject:",  figureUnderPoint);
   var notAddOperation = (objj_msgSend(anEvent, "modifierFlags") & (CPControlKeyMask | CPCommandKeyMask)) == 0;
   if (!wasSelected && notAddOperation) {
    objj_msgSend(_tool, "clearSelection");
   }
  }
  objj_msgSend(_tool, "select:",  figureUnderPoint);
  objj_msgSend(_tool, "updateInitialPoints");
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $SelectedState__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 var figureUnderPoint = objj_msgSend(objj_msgSend(_tool, "drawing"), "figureAt:",  point);
 if (objj_msgSend(figureUnderPoint, "isHandle")) {
  objj_msgSend(self, "transitionTo:",  objj_msgSend(MoveHandleState, "tool:initialDragPoint:handle:",  _tool,  _initialDragPoint,  figureUnderPoint));
 } else {
  objj_msgSend(self, "transitionTo:",  objj_msgSend(MoveFiguresState, "tool:initialDragPoint:",  _tool,  _initialDragPoint));
 }
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $SelectedState__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:initialDragPoint:"), function $SelectedState__tool_initialDragPoint_(self, _cmd, aTool, anInitialDragPoint)
{ with(self)
{
 var state = objj_msgSend(self, "tool:",  aTool);
 objj_msgSend(state, "initWithInitialDragPoint:",  anInitialDragPoint);
 return state;
}
},["id","StateMachineTool","CPPoint"])]);
}

p;15;SelectionTool.jt;4340;@STATIC;1.0;t;4321;{var the_class = objj_allocateClassPair(StateMachineTool, "SelectionTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_selectedFigures"), new objj_ivar("_initialPositions"), new objj_ivar("_initialDragPoint")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $SelectionTool__init(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("SelectionTool").super_class }, "init");
 _selectedFigures = objj_msgSend(CPMutableArray, "array");
 _initialPositions = objj_msgSend(CPDictionary, "dictionary");
 return self;
}
},["id"]), new objj_method(sel_getUid("initialState"), function $SelectionTool__initialState(self, _cmd)
{ with(self)
{
 return objj_msgSend(SelectionToolInitialState, "tool:",  self);
}
},["id"]), new objj_method(sel_getUid("selectedFigures"), function $SelectionTool__selectedFigures(self, _cmd)
{ with(self)
{
 return _selectedFigures;
}
},["CPMutableArray"]), new objj_method(sel_getUid("select:"), function $SelectionTool__select_(self, _cmd, aFigure)
{ with(self)
{
 if (!objj_msgSend(_selectedFigures, "containsObjectIdenticalTo:",  aFigure)) {
  objj_msgSend(_selectedFigures, "addObject:",  aFigure);
  objj_msgSend(aFigure, "select");
  objj_msgSend(_drawing, "selectedFigure:",  aFigure);
  objj_msgSend(_initialPositions, "setObject:forKey:",  objj_msgSend(aFigure, "frameOrigin"),  aFigure);
 }
}
},["void","Figure"]), new objj_method(sel_getUid("unselect:"), function $SelectionTool__unselect_(self, _cmd, aFigure)
{ with(self)
{
 objj_msgSend(_selectedFigures, "removeObject:",  aFigure);
 objj_msgSend(aFigure, "unselect");
 objj_msgSend(_drawing, "selectedFigure:",  nil);
 objj_msgSend(_initialPositions, "removeObjectForKey:",  aFigure);
}
},["void","Figure"]), new objj_method(sel_getUid("initialPositionOf:"), function $SelectionTool__initialPositionOf_(self, _cmd, aFigure)
{ with(self)
{
 return objj_msgSend(_initialPositions, "objectForKey:",  aFigure);
}
},["CPPoint","Figure"]), new objj_method(sel_getUid("updateInitialPoints"), function $SelectionTool__updateInitialPoints(self, _cmd)
{ with(self)
{
 for (var i = 0; i < objj_msgSend(_selectedFigures, "count"); i++) {
     var selectedFigure = objj_msgSend(_selectedFigures, "objectAtIndex:", i);
  objj_msgSend(_initialPositions, "setObject:forKey:",  objj_msgSend(selectedFigure, "frameOrigin"),  selectedFigure);
 }
}
},["void"]), new objj_method(sel_getUid("clearSelection"), function $SelectionTool__clearSelection(self, _cmd)
{ with(self)
{
 for (var i = 0; i < objj_msgSend(_selectedFigures, "count"); i++) {
     var selectedFigure = objj_msgSend(_selectedFigures, "objectAtIndex:", i);
  objj_msgSend(selectedFigure, "unselect");
 }
 objj_msgSend(_selectedFigures, "removeAllObjects");
 objj_msgSend(_initialPositions, "removeAllObjects");
 objj_msgSend(_drawing, "selectedFigure:",  nil);
}
},["void"]), new objj_method(sel_getUid("release"), function $SelectionTool__release(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "clearSelection");
}
},["void"]), new objj_method(sel_getUid("selectableFigure:"), function $SelectionTool__selectableFigure_(self, _cmd, aFigure)
{ with(self)
{
 while (aFigure != _drawing && !objj_msgSend(aFigure, "isSelectable")) {
  aFigure = objj_msgSend(aFigure, "superview");
 }
 return aFigure;
}
},["Figure","Figure"]), new objj_method(sel_getUid("keyDown:"), function $SelectionTool__keyDown_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyUp:"), function $SelectionTool__keyUp_(self, _cmd, anEvent)
{ with(self)
{
 if ((objj_msgSend(anEvent, "keyCode") == CPKeyCodes.F2) && (objj_msgSend(_selectedFigures, "count") == 1)) {
  var currentFigure = objj_msgSend(_selectedFigures, "objectAtIndex:",  0);
  if (objj_msgSend(currentFigure, "isEditable")) {
   objj_msgSend(currentFigure, "switchToEditMode");
  }
 }
 if (objj_msgSend(anEvent, "keyCode") == CPKeyCodes.DELETE || objj_msgSend(anEvent, "keyCode") == CPKeyCodes.BACKSPACE) {
  for (var i = 0; i < objj_msgSend(_selectedFigures, "count"); i++) {
      var selectedFigure = objj_msgSend(_selectedFigures, "objectAtIndex:", i);
   objj_msgSend(selectedFigure, "removeFromSuperview");
  }
  objj_msgSend(self, "clearSelection");
 }
}
},["void","CPEvent"])]);
}

p;27;SelectionToolInitialState.jt;1560;@STATIC;1.0;t;1541;{var the_class = objj_allocateClassPair(ToolState, "SelectionToolInitialState"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithTool:"), function $SelectionToolInitialState__initWithTool_(self, _cmd, aTool)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("SelectionToolInitialState").super_class }, "initWithTool:",  aTool);
 return self;
}
},["id","StateMachineTool"]), new objj_method(sel_getUid("mouseDown:"), function $SelectionToolInitialState__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 var point = objj_msgSend(anEvent, "locationInWindow");
 var figureUnderPoint = objj_msgSend(objj_msgSend(_tool, "drawing"), "figureAt:",  point);
 figureUnderPoint = objj_msgSend(_tool, "selectableFigure:",  figureUnderPoint);
 if (figureUnderPoint != nil && (figureUnderPoint != objj_msgSend(_tool, "drawing"))) {
  objj_msgSend(_tool, "select:",  figureUnderPoint);
  objj_msgSend(self, "transitionTo:",  objj_msgSend(SelectedState, "tool:initialDragPoint:",  _tool,  point));
 } else {
  objj_msgSend(_tool, "clearSelection");
  objj_msgSend(self, "transitionTo:",  objj_msgSend(MarqueeSelectionState, "tool:initialDragPoint:",  _tool,  point));
 }
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:"), function $SelectionToolInitialState__tool_(self, _cmd, aTool)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithTool:",  aTool);
}
},["id","StateMachineTool"])]);
}

p;18;StateMachineTool.jt;1769;@STATIC;1.0;t;1750;{var the_class = objj_allocateClassPair(Tool, "StateMachineTool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_state")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("init"), function $StateMachineTool__init(self, _cmd)
{ with(self)
{
 objj_msgSendSuper({ receiver:self, super_class:objj_getClass("StateMachineTool").super_class }, "init");
 objj_msgSend(self, "setState:",  objj_msgSend(self, "initialState"));
 return self;
}
},["id"]), new objj_method(sel_getUid("initialState"), function $StateMachineTool__initialState(self, _cmd)
{ with(self)
{
 return nil;
}
},["id"]), new objj_method(sel_getUid("setState:"), function $StateMachineTool__setState_(self, _cmd, aNewState)
{ with(self)
{
 _state = aNewState;
}
},["void","ToolState"]), new objj_method(sel_getUid("mouseDown:"), function $StateMachineTool__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_state, "mouseDown:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $StateMachineTool__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_state, "mouseDragged:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $StateMachineTool__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_state, "mouseUp:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyUp:"), function $StateMachineTool__keyUp_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_state, "keyUp:",  anEvent);
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyDown:"), function $StateMachineTool__keyDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(_state, "keyDown:",  anEvent);
}
},["void","CPEvent"])]);
}

p;6;Tool.jt;1860;@STATIC;1.0;t;1841;{var the_class = objj_allocateClassPair(CPObject, "Tool"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_drawing")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithDrawing:"), function $Tool__initWithDrawing_(self, _cmd, aDrawing)
{ with(self)
{
 _drawing = aDrawing;
 return self;
}
},["id","Drawing"]), new objj_method(sel_getUid("drawing"), function $Tool__drawing(self, _cmd)
{ with(self)
{
 return _drawing;
}
},["Drawing"]), new objj_method(sel_getUid("activateSelectionTool"), function $Tool__activateSelectionTool(self, _cmd)
{ with(self)
{
 var tool = objj_msgSend(SelectionTool, "drawing:",  _drawing);
 objj_msgSend(_drawing, "tool:",  tool);
}
},["void"]), new objj_method(sel_getUid("activate"), function $Tool__activate(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("release"), function $Tool__release(self, _cmd)
{ with(self)
{
}
},["void"]), new objj_method(sel_getUid("mouseDown:"), function $Tool__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $Tool__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $Tool__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyUp:"), function $Tool__keyUp_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyDown:"), function $Tool__keyDown_(self, _cmd, anEvent)
{ with(self)
{
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("drawing:"), function $Tool__drawing_(self, _cmd, aDrawing)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithDrawing:",  aDrawing);
}
},["id","Drawing"])]);
}

p;11;ToolState.jt;2321;@STATIC;1.0;t;2302;{var the_class = objj_allocateClassPair(CPObject, "ToolState"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_tool")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithTool:"), function $ToolState__initWithTool_(self, _cmd, aTool)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("ToolState").super_class }, "init");
 if (self) {
  _tool = aTool;
  return self;
 }
}
},["id","StateMachineTool"]), new objj_method(sel_getUid("transitionTo:"), function $ToolState__transitionTo_(self, _cmd, aNewState)
{ with(self)
{
 objj_msgSend(_tool, "setState:",  aNewState);
}
},["void","ToolState"]), new objj_method(sel_getUid("activateSelectionTool"), function $ToolState__activateSelectionTool(self, _cmd)
{ with(self)
{
 objj_msgSend(_tool, "activateSelectionTool");
}
},["void"]), new objj_method(sel_getUid("transitionToInitialState"), function $ToolState__transitionToInitialState(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "transitionTo:",  objj_msgSend(_tool, "initialState"));
}
},["void"]), new objj_method(sel_getUid("mouseDown:"), function $ToolState__mouseDown_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(self, "transitionToInitialState");
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseDragged:"), function $ToolState__mouseDragged_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(self, "transitionToInitialState");
}
},["void","CPEvent"]), new objj_method(sel_getUid("mouseUp:"), function $ToolState__mouseUp_(self, _cmd, anEvent)
{ with(self)
{
 objj_msgSend(self, "transitionToInitialState");
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyUp:"), function $ToolState__keyUp_(self, _cmd, anEvent)
{ with(self)
{
 CPLog.debug(anEvent);
 objj_msgSend(self, "transitionToInitialState");
}
},["void","CPEvent"]), new objj_method(sel_getUid("keyDown:"), function $ToolState__keyDown_(self, _cmd, anEvent)
{ with(self)
{
 CPLog.debug(anEvent);
 objj_msgSend(self, "transitionToInitialState");
}
},["void","CPEvent"])]);
class_addMethods(meta_class, [new objj_method(sel_getUid("tool:"), function $ToolState__tool_(self, _cmd, aTool)
{ with(self)
{
 return objj_msgSend(objj_msgSend(self, "new"), "initWithTool:",  aTool);
}
},["id","StateMachineTool"])]);
}

p;16;EditorDelegate.jt;3616;@STATIC;1.0;t;3597;{var the_class = objj_allocateClassPair(CPObject, "EditorDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_editableLabel"), new objj_ivar("_editableFigure"), new objj_ivar("_drawing"), new objj_ivar("_window")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithWidget:label:window:figureContainer:drawing:"), function $EditorDelegate__initWithWidget_label_window_figureContainer_drawing_(self, _cmd, aFigure, aLabel, aWindow, aContainer, aDrawing)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("EditorDelegate").super_class }, "init");
 if (self) {
  var width = objj_msgSend(aLabel, "frameSize").width + (6 * 2);
  var currentValue = objj_msgSend(aLabel, "objectValue");
  var position = objj_msgSend(aDrawing, "convertPoint:fromView:",  CPPointMake(-5, -5),  aFigure);
  var editableLabel = objj_msgSend(CPCancelableTextField, "textFieldWithStringValue:placeholder:width:",  currentValue,  "",  width);
  objj_msgSend(editableLabel, "setEditable:",  YES);
  objj_msgSend(editableLabel, "setBordered:",  NO);
  objj_msgSend(editableLabel, "sizeToFit");
  objj_msgSend(editableLabel, "setFrameOrigin:",  position);
  objj_msgSend(editableLabel, "cancelator:",  self);
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("controlTextDidBlur:"),  CPTextFieldDidBlurNotification,  editableLabel);
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("controlTextDidEndEditing:"),  CPControlTextDidEndEditingNotification,  editableLabel);
  objj_msgSend(objj_msgSend(aWindow, "contentView"), "addSubview:",  editableLabel);
  objj_msgSend(aWindow, "makeFirstResponder:",  editableLabel);
  _editableLabel = editableLabel;
  _editableFigure = aContainer;
  _drawing = aDrawing;
  _window = aWindow;
  return self;
 }
}
},["id","Figure","CPTextField","id","Figure","Drawing"]), new objj_method(sel_getUid("controlTextDidChange:"), function $EditorDelegate__controlTextDidChange_(self, _cmd, notification)
{ with(self)
{
 var keyValue = objj_msgSend(objj_msgSend(notification, "userInfo"), "objectForKey:",  "CPFieldEditor");
 CPLog.debug(keyValue);
}
},["void","CPNotification"]), new objj_method(sel_getUid("controlTextDidEndEditing:"), function $EditorDelegate__controlTextDidEndEditing_(self, _cmd, notification)
{ with(self)
{
 objj_msgSend(_editableFigure, "setEditionResult:",  objj_msgSend(_editableLabel, "objectValue"));
 objj_msgSend(self, "cleanUp");
}
},["void","CPNotification"]), new objj_method(sel_getUid("controlTextDidBlur:"), function $EditorDelegate__controlTextDidBlur_(self, _cmd, notification)
{ with(self)
{
 objj_msgSend(self, "controlTextDidEndEditing:",  notification);
}
},["void","CPNotification"]), new objj_method(sel_getUid("cancelEditing"), function $EditorDelegate__cancelEditing(self, _cmd)
{ with(self)
{
 objj_msgSend(self, "cleanUp");
}
},["void"]), new objj_method(sel_getUid("cleanUp"), function $EditorDelegate__cleanUp(self, _cmd)
{ with(self)
{
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:", self,  CPControlTextDidEndEditingNotification,  _editableLabel);
 objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "removeObserver:name:object:", self,  CPTextFieldDidBlurNotification,  _editableLabel);
 objj_msgSend(_editableLabel, "removeFromSuperview");
 objj_msgSend(_window, "makeFirstResponder:",  _drawing);
}
},["void"])]);
}

p;15;GeometryUtils.jt;4012;@STATIC;1.0;t;3993;{var the_class = objj_allocateClassPair(CPObject, "GeometryUtils"),
meta_class = the_class.isa;objj_registerClassPair(the_class);
class_addMethods(meta_class, [new objj_method(sel_getUid("computeFrameFor:"), function $GeometryUtils__computeFrameFor_(self, _cmd, points)
{ with(self)
{
 var firstPoint = objj_msgSend(points, "objectAtIndex:",  0);
 var minX = firstPoint.x;
 var maxX = minX;
 var minY = firstPoint.y;
 var maxY = minY;
 for (var i = 1; i < objj_msgSend(points, "count"); i++) {
     var point = objj_msgSend(points, "objectAtIndex:", i);
  minX = MIN(minX, point.x);
  maxX = MAX(maxX, point.x);
  minY = MIN(minY, point.y);
  maxY = MAX(maxY, point.y);
 }
 var width = ABS(maxX - minX);
 var height = ABS(maxY - minY);
 width = MAX(width, 1);
 height = MAX(height, 1);
 return CGRectMake(minX, minY, width, height);
}
},["CGRect","CPArray"]), new objj_method(sel_getUid("computeFrameForViews:"), function $GeometryUtils__computeFrameForViews_(self, _cmd, views)
{ with(self)
{
 var firstPoint = objj_msgSend(objj_msgSend(views, "objectAtIndex:",  0), "frame").origin;
 var minX = firstPoint.x;
 var maxX = minX;
 var minY = firstPoint.y;
 var maxY = minY;
 for (var i = 1; i < objj_msgSend(views, "count"); i++) {
     var point = objj_msgSend(objj_msgSend(views, "objectAtIndex:",  i), "frame").origin;
  minX = MIN(minX, point.x);
  maxX = MAX(maxX, point.x);
  minY = MIN(minY, point.y);
  maxY = MAX(maxY, point.y);
  var size = objj_msgSend(objj_msgSend(views, "objectAtIndex:",  i), "frame").size;
     var point2 = CGPointMake(point.x + size.width, point.y + size.height);
  minX = MIN(minX, point2.x);
  maxX = MAX(maxX, point2.x);
  minY = MIN(minY, point2.y);
  maxY = MAX(maxY, point2.y);
 }
 var width = ABS(maxX - minX);
 var height = ABS(maxY - minY);
 width = MAX(width, 1);
 height = MAX(height, 1);
 return CGRectMake(minX, minY, width, height);
}
},["CGRect","CPArray"]), new objj_method(sel_getUid("intersectionOf:with:with:with:"), function $GeometryUtils__intersectionOf_with_with_with_(self, _cmd, p1, p2, p3, p4)
{ with(self)
{
 var Ax = p1.x;
 var Ay = p1.y;
 var Bx = p2.x;
 var By = p2.y;
 var Cx = p3.x;
 var Cy = p3.y;
 var Dx = p4.x;
 var Dy = p4.y;
 var distAB, theCos, theSin, newX, ABpos;
 if (Ax==Bx && Ay==By || Cx==Dx && Cy==Dy) return nil;
 if (Ax==Cx && Ay==Cy || Bx==Cx && By==Cy
   || Ax==Dx && Ay==Dy || Bx==Dx && By==Dy) {
     return nil;
 }
 Bx-=Ax; By-=Ay;
 Cx-=Ax; Cy-=Ay;
 Dx-=Ax; Dy-=Ay;
 distAB= objj_msgSend(CPPredicateUtilities, "sqrt:",  (Bx*Bx+By*By));
 theCos=Bx/distAB;
 theSin=By/distAB;
 newX=Cx*theCos+Cy*theSin;
 Cy =Cy*theCos-Cx*theSin; Cx=newX;
 newX=Dx*theCos+Dy*theSin;
 Dy =Dy*theCos-Dx*theSin; Dx=newX;
 if (Cy<0. && Dy<0. || Cy>=0. && Dy>=0.) return nil;
 ABpos=Dx+(Cx-Dx)*Dy/(Dy-Cy);
 if (ABpos<0. || ABpos>distAB) return nil;
 var point = CGPointMake(ROUND(Ax+ABpos*theCos), ROUND(Ay+ABpos*theSin));
 return point;
}
},["CGPoint","CGPoint","CGPoint","CGPoint","CGPoint"]), new objj_method(sel_getUid("distanceFrom:to:"), function $GeometryUtils__distanceFrom_to_(self, _cmd, p1, p2)
{ with(self)
{
 var xOff = (p1.x - p2.x);
 var yOff = (p1.y - p2.y);
 return objj_msgSend(CPPredicateUtilities, "sqrt:",  (xOff * xOff + yOff * yOff))
}
},["id","CGPoint","CGPoint"]), new objj_method(sel_getUid("distanceFrom:and:to:"), function $GeometryUtils__distanceFrom_and_to_(self, _cmd, a, b, p)
{ with(self)
{
 if (a == b) {
  return objj_msgSend(self, "distanceFrom:to:",  a,  p);
 }
 var r = ( (p.x - a.x) * (b.x - a.x) + (p.y - a.y) * (b.y - a.y) ) / ( (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y) );
 if (r <= 0.0) {
  return objj_msgSend(self, "distanceFrom:to:",  a,  p);
 }
 if (r >= 1.0) {
  return objj_msgSend(self, "distanceFrom:to:",  b,  p);
 }
 var s = ((a.y - p.y) *(b.x - a.x) - (a.x - p.x)*(b.y - a.y) ) / ((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y) );
 return ABS(s) * SQRT(((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y)));
}
},["id","CGPoint","CGPoint","CGPoint"])]);
}

p;14;HandleMagnet.jt;3297;@STATIC;1.0;t;3278;{var the_class = objj_allocateClassPair(CPObject, "HandleMagnet"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_handle"), new objj_ivar("_sourceFigure"), new objj_ivar("_targetFigure")]);
objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("initWithHandle:source:target:"), function $HandleMagnet__initWithHandle_source_target_(self, _cmd, aHandle, aSourceFigure, aTargetFigure)
{ with(self)
{
 self = objj_msgSendSuper({ receiver:self, super_class:objj_getClass("HandleMagnet").super_class }, "init");
 if (self) {
  _handle = aHandle;
  _sourceFigure = aSourceFigure;
  _targetFigure = aTargetFigure;
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("updateHandleLocation:"),  "CPViewFrameDidChangeNotification",  _targetFigure);
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("updateHandleLocation:"),  "CPViewFrameDidChangeNotification",  _handle);
  objj_msgSend(objj_msgSend(CPNotificationCenter, "defaultCenter"), "addObserver:selector:name:object:",  self,  sel_getUid("updateHandleLocation:"),  "CPViewFrameDidChangeNotification",  objj_msgSend(_handle, "targetFigure"));
  return self;
 }
}
},["id","Handle",null,null]), new objj_method(sel_getUid("updateHandleLocation:"), function $HandleMagnet__updateHandleLocation_(self, _cmd, aNotification)
{ with(self)
{
 var sourceCenter = objj_msgSend(_sourceFigure, "center");
 var p1 = objj_msgSend(GeometryUtils, "intersectionOf:with:with:with:",  objj_msgSend(_targetFigure, "topLeft"),  objj_msgSend(_targetFigure, "topRight"),  objj_msgSend(_targetFigure, "center"),  sourceCenter);
 var p2 = objj_msgSend(GeometryUtils, "intersectionOf:with:with:with:",  objj_msgSend(_targetFigure, "topRight"),  objj_msgSend(_targetFigure, "bottomRight"),  objj_msgSend(_targetFigure, "center"),  sourceCenter);
 var p3 = objj_msgSend(GeometryUtils, "intersectionOf:with:with:with:",  objj_msgSend(_targetFigure, "bottomRight"),  objj_msgSend(_targetFigure, "bottomLeft"),  objj_msgSend(_targetFigure, "center"),  sourceCenter);
 var p4 = objj_msgSend(GeometryUtils, "intersectionOf:with:with:with:",  objj_msgSend(_targetFigure, "bottomLeft"),  objj_msgSend(_targetFigure, "topLeft"),  objj_msgSend(_targetFigure, "center"),  sourceCenter);
 var selected = p1;
 if (selected == nil || (p2 != nil && (objj_msgSend(GeometryUtils, "distanceFrom:to:",  p2,  objj_msgSend(_handle, "center")) < objj_msgSend(GeometryUtils, "distanceFrom:to:",  selected,  objj_msgSend(_handle, "center"))))) {
  selected = p2;
 }
 if (selected == nil || (p3 != nil && (objj_msgSend(GeometryUtils, "distanceFrom:to:",  p3,  objj_msgSend(_handle, "center")) < objj_msgSend(GeometryUtils, "distanceFrom:to:",  selected,  objj_msgSend(_handle, "center"))))) {
  selected = p3;
 }
 if (selected == nil || (p4 != nil && (objj_msgSend(GeometryUtils, "distanceFrom:to:",  p4,  objj_msgSend(_handle, "center")) < objj_msgSend(GeometryUtils, "distanceFrom:to:",  selected,  objj_msgSend(_handle, "center"))))) {
  selected = p4;
 }
 if (selected != nil) {
  objj_msgSend(_handle, "setFrameOrigin:",  selected);
 } else {
 }
}
},["void",null])]);
}

e;