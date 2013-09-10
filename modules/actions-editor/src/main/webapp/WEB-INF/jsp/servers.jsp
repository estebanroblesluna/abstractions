<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Servers" />
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
        <li class="active">Servers</li>
      </ol>
    </div>
      <div class="col-lg-6" style="text-align:right;">
<a href="addServer.htm" class="btn btn-primary">Add</a>
<a href="button" class="btn btn-danger">Delete</a>
    </div>
    </div>
    
    <div class="row">
    <div class="col-lg-12">
    <table class="table table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Server name</th>
            <th>Server group</th>
            <th>IP/DNS</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>QA server</td>
            <td>QA</td>
            <td>eca2.asdas.amazon.com</td>
            <td><span class="label label-success">Running</span></td>
          </tr>
          <tr>
            <td>2</td>
            <td>PROD 1</td>
            <td>PROD</td>
            <td>eca33.asdas.amazon.com</td>
            <td><span class="label label-success">Running</span></td>
          </tr>
          <tr>
            <td>3</td>
            <td>PROD 2</td>
            <td>PROD</td>
            <td>eca3443.asdas.amazon.com</td>
            <td><span class="label label-danger">Stopped</span></td>
          </tr>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>