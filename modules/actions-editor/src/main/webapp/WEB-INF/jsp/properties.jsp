<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Properties" />
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
          <li class="active">Properties</li>
        </ol>
      </div>
      <div class="col-lg-6" style="text-align: right;">
        <div class="col-lg-2" style="text-align: right;">
          <form class="form-horizontal" role="form">
            <select class="form-control">
              <option>DEV</option>
              <option>QA</option>
              <option>PROD</option>
            </select>
          </form>
        </div>
        <a href="addProperty.htm" class="btn btn-primary">Add</a> <a href="button" class="btn btn-danger">Delete</a>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Property name</th>
              <th>Property value</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>db.username</td>
              <td>root</td>
            </tr>
            <tr>
              <td>2</td>
              <td>db.password</td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

  </div>
</body>
</html>
