<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Flow deployment"/>
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
      <script type="text/javascript">
    	var diagramRepresentation = ${flow.getJson()};
    	var libraries = ${libraries};
      </script>
      <iframe src="/editor/index-debug.html?mode=deployment&contextId=${flow.name}" style="width:100%; height: 550px" frameBorder="0"></iframe>
      </div>
    </div>
  </div>
</body>
</html>
