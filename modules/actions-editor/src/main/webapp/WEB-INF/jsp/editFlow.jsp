<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Edit flow"/>
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/teams/">Teams</a></li>
          <li>${teamName}</li>
          <li><a href="/teams/${teamId}/applications/">Applications</a></li>
          <li>${applicationName}</li>
          <li> <a href="/teams/${teamId}/applications/${applicationId}/flows/">Flows</a></li>
          <li class="active">Edit Flow</li>
        </ol>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
      <script type="text/javascript">
    	var diagramRepresentation = ${flow.getJson()};
    	var libraries = ${libraries};
      </script>
      <iframe src="/editor/index-debug.html?saveUrl=/teams/${teamId}/applications/${applicationId}/flows/save/${flow.id}&contextId=${flow.name}" style="width:100%; height: 550px" frameBorder="0"></iframe>
      </div>
    </div>
  </div>
</body>
</html>
