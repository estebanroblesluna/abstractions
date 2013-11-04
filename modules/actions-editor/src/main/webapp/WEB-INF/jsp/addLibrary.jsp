<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add connector"/>
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/libraries/">Libraries</a></li>
          <li class="active">Add Library</li>
        </ol>
      </div>

      <div class="row">
        <div class="col-lg-9">
          <form class="form-horizontal" role="form" name="form" action="add" method="POST">
            <div class="form-group">
              <label for="inputApplicationName" class="col-lg-2 control-label">Library name</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputLibraryName" name="name" placeholder="Enter library name...">
              </div>
            </div>
            <div class="form-group">
              <label for="inputApplicationName" class="col-lg-2 control-label">Library display name</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputLibraryDisplayName" name="displayName" placeholder="Enter library display name...">
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
