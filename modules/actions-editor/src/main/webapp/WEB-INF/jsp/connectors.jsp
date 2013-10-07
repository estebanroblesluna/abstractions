<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Connectors" />
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
      <div class="col-lg-6">
      <ol class="breadcrumb">
        <li><a href="#">App ${applicationId}</a></li>
        <li class="active">Connectors</li>
      </ol>
    </div>
      <div class="col-lg-6" style="text-align:right;">
<a href="addConnector.htm" class="btn btn-primary">Add</a>
<a href="button" class="btn btn-danger">Delete</a>
    </div>
    </div>
    
    <div class="row">
    <div class="col-lg-12">
    <table class="table table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Connector type</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>Database</td>
            <td>jdbc://aaa:3034/aaa</td>
          </tr>
          <tr>
            <td>2</td>
            <td>Kafka</td>
            <td>zookeeper://aaa:51515</td>
          </tr>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>
