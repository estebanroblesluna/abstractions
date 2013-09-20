<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">File Editor</a></li>
      </ul>
    </div>
  </nav>

  <div class="container">
    <div id="alerts"></div>
    <div id="templateEditor">
      <form action="upload" method="POST" enctype="multipart/form-data">
        <input type="file" name="file" />
        <button>Upload zip file</button>
      </form>
      <div id="editor"></div>
    </div>
  </div>
</body>
</html>