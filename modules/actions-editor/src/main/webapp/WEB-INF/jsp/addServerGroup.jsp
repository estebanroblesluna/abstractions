<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add team"/>
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-6">
        <ol class="breadcrumb">
          <li><a href="/teams/">Teams</a></li>
          <li>${team.name}</li>
          <li><a href="/teams/${team.id}/serverGroups/">Server Groups</a></li>
          <li class="active">Add server group</li>
        </ol>
      </div>

      <div class="row">
        <div class="col-lg-9">
          <form class="form-horizontal" role="form" name="form" action="add" method="POST">
            <div class="form-group">
              <label for="inputServerGroupName" class="col-lg-2 control-label">Server group name</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputServerGroupName" name="name" placeholder="">
              </div>
              <label for="inputServerGroupEnvironment" class="col-lg-2 control-label">Server group Environment</label>
             
              <div class="col-lg-10">
                <select class="form-control" id="inputServerGroupEnvironment" name="environment">
                    <option>DEV</option>
                    <option>QA</option>
                    <option>PROD</option>
                    <option>STG_ALPHA</option>
                    <option>STG_BETA</option>
                </select>
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
    </div>
</body>
</html>
