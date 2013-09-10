<jsp:include page="/WEB-INF/jsp/templateEditorHeader.jsp" />
<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Dust Editor</a></li>
      </ul>
    </div>
  </nav>

  <div class="container">
    <div id="alerts"></div>
    <div id="templateEditor">
      <div class="field control-group">
        <label for="templates" class="control-label">Template</label> <select id="templates" class="controls"></select>
      </div>
      <button id="saveTemplate" class="btn btn-xs">Save template</button>
      <button id="deleteTemplate" class="btn btn-xs">Delete Template</button>
      <button id="renameTemplate" class="btn btn-xs">Rename template</button>
      <button id="newTemplate" class="btn btn-xs">New Template</button>
      <div id="editor"></div>
    </div>
  </div>
</body>
</html>