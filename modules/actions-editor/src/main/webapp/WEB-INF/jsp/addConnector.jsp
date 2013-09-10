<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add connector"/>
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
          <li><a href="connectors.htm">Connectors</a></li>
          <li class="active">Add connector</li>
        </ol>
      </div>

      <div class="row">
        <div class="col-lg-9">
          <form class="form-horizontal" role="form">

            <div class="form-group">
              <label for="inputServerName" class="col-lg-2 control-label">Connector type</label>
              <div class="col-lg-10">
<select class="form-control">
  <option>Database</option>
  <option>Kafka</option>
  <option>Memcached</option>
</select>
              </div>
            </div>

            <div class="form-group">
              <label for="inputJDBC" class="col-lg-2 control-label">JDBC url</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputJDBC" name="jdbc" placeholder="jdbc...">
              </div>
            </div>

            <div class="form-group">
              <label for="inputUsername" class="col-lg-2 control-label">Username</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputUsername" name="username" placeholder="username...">
              </div>
            </div>

            <div class="form-group">
              <label for="inputPassword" class="col-lg-2 control-label">Password</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputPassword" name="password" placeholder="password...">
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
