<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add flow"/>
</jsp:include>

<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/teams/">Home</a></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Applications<b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">App 1</a></li>
          </ul></li>
      </ul>
    </div>
  </nav>

  <div class="container">
    <div class="row">
      <div class="col-lg-6">
        <ol class="breadcrumb">
          <li> <a href="#">Flows</a></li>
          <li class="active">Edit Flow</li>
        </ol>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
      <script type="text/javascript">
        var libraries = ${libraries};
      </script>
      <iframe src="/editor/index-debug.html?saveUrl=/teams/${teamId}/applications/${applicationId}/flows/save" style="width:100%; height: 550px" frameBorder="0"></iframe>
      </div>
    </div>
  </div>
</body>
</html>
