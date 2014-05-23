<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add connector"/>
</jsp:include>

<body>
  <script type="text/javascript">
    $(document).ready(function() {
      $('#addButton').click(function() {
        $('#configurations').append('<div class="form-group" id="inputConnectorConfiguration"> <label for="inputConnectorConfigurationName" class="col-lg-2 control-label">Name</label> <div class="col-lg-4"> <input type="text" class="form-control" name="configurationNames" placeholder="Name..."> </div> <label for="inputConnectorConfigurationValue" class="col-lg-2 control-label">Value</label> <div class="col-lg-4"> <input type="text" class="form-control" name="configurationValues" placeholder="Value..."> </div> </div>'); })
    })
  </script>

  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/teams/">Teams</a></li>
          <li>${teamName}</li>
          <li><a href="/teams/${teamId}/connectors/">Connectors</a></li>
          <li class="active">Add connector</li>
        </ol>
      </div>

      <div class="row">
        <div class="col-lg-9">
          <form class="form-horizontal" role="form" name="form" action="add" method="POST">

            <div class="form-group">
              <label for="inputConnectorName" class="col-lg-2 control-label">Connector name</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputConnectorName" name="name" placeholder="Connector name...">
              </div>
            </div>

            <div class="form-group">
              <label for="inputConnectorType" class="col-lg-2 control-label">Connector type</label>
              <div class="col-lg-10">
                <input type="text" class="form-control" id="inputConnectorType" name="type" placeholder="Connector type...">
              </div>
            </div>

            <div class="form-group">
              <label class="col-lg-2 control-label">Configurations</label>
              <div class="col-lg-10">
                <button type="button" id="addButton" class="btn btn-success">Add</button>
              </div>
            </div>

            <div id="configurations">
              <div class="form-group" id="inputConnectorConfiguration">
                <label for="inputConnectorConfigurationName" class="col-lg-2 control-label">Name</label>
                <div class="col-lg-4">
                  <input type="text" class="form-control" name="configurationNames" placeholder="Name...">
                </div>
                <label for="inputConnectorConfigurationValue" class="col-lg-2 control-label">Value</label>
                <div class="col-lg-4">
                  <input type="text" class="form-control" name="configurationValues" placeholder="Value...">
                </div>
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
