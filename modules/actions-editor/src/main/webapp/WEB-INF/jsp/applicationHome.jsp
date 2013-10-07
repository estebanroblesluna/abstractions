<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Applications" />
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
