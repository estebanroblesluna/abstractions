<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add connector"/>
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
          <li><a href="/connectors/">Connectors</a></li>
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
