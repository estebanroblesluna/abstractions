<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add server"/>
</jsp:include>

<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
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
          <li><a href="#">App 1</a></li>
          <li><a href="servers.htm">Servers</a></li>
          <li class="active">Add server</li>
        </ol>
      </div>

      <div class="row">
        <div class="col-lg-9">
          <form class="form-horizontal" role="form" name="form" action="add" method="POST">

            <div class="form-group">
              <label for="inputServerName" class="col-lg-2 control-label">Server name</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputServerName" name="name" placeholder="Server name...">
              </div>
            </div>

            <div class="form-group">
              <label for="inputIPDNS" class="col-lg-2 control-label">IP/DNS</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputIPDNS" name="ipDNS" placeholder="IP/DNS...">
              </div>
            </div>

            <div class="form-group">
              <label for="inputPort" class="col-lg-2 control-label">Port</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputPort" name="port" placeholder="80">
              </div>
            </div>


            <div class="form-group">
              <div class="col-lg-offset-2 col-lg-10">
                <button type="submit" class="btn btn-primary">Save</button>
              </div>
            </div>
          </form>
        </div>
      </div>

    </div>
</body>
</html>
