<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Applications" />
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">

      <div class="col-lg-3">
        <div class="thumbnail">
          <img src="../static/pages/image.png" alt="...">
          <div class="caption container" style="text-align: center;">
            <a href="connectors.htm"><h3>Connectors</h3></a>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="thumbnail">
          <img src="../static/pages/image.png" alt="...">
          <div class="caption container" style="text-align: center;">
            <a href="flows.htm"><h3>Flows</h3></a>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="thumbnail">
          <img src="../static/pages/image.png" alt="...">
          <div class="caption container" style="text-align: center;">
            <a href="templates.htm"><h3>Templates</h3></a>
          </div>
        </div>
      </div>

    </div>
    <div class="row">
      <p>&nbsp;</p>
    </div>
    
    <div class="row">

      <div class="col-lg-3">
        <div class="thumbnail">
          <img src="../static/pages/image.png" alt="...">
          <div class="caption container" style="text-align: center;">
            <a href="properties.htm"><h3>Properties</h3></a>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="thumbnail">
          <img src="../static/pages/image.png" alt="...">
          <div class="caption container" style="text-align: center;">
            <a href="servers.htm"><h3>Servers</h3></a>
          </div>
        </div>
      </div>
      
    </div>
  </div>
</body>
</html>
