<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Connectors" />
</jsp:include>


<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-6">
      <ol class="breadcrumb">
          <li><a href="/teams/">Teams</a></li>
          <li>${teamName}</li>
          <li><a href="/teams/${teamId}/applications/">Applications</a></li>
          <li>${applicationName}</li>
          <li class="active">Connectors</li>
      </ol>
    </div>
      <div class="col-lg-6" style="text-align:right;">
<a href="add/" class="btn btn-primary">Add</a>
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
