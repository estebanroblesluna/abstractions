<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add deployment" />
</jsp:include>

<body>

  <script type="text/javascript">
  $(document).ready(function() {
    $("#saveButton").click(function(e) {
      var ids = "";
      e.preventDefault();
      $(".serverCheckbox:checked").each(function(i, checkbox) {
    	  ids = ids + (ids ? "," : "") + $(checkbox).val();
      })
      if(ids != ""){
        $("#selectedServers").val(ids);
        $("form")[0].submit();
      }
    })
  })
  </script>
  
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/teams/">Teams</a></li>
          <li>${teamName}</li>
          <li><a href="/teams/${teamId}/applications/">Applications</a></li>
          <li>${applicationName}</li>
          <li> <a href="/teams/${teamId}/applications/${applicationId}/snapshots/">Snapshots</a></li>
          <li><a href="/teams/${teamId}/applications/${applicationId}/snapshots/${snapshotId}/deployments/">Deployments</a></li>
          <li class="active">Add deployment</li>
        </ol>
      </div>

      <div class="row">
        <form class="form-horizontal" role="form" name="form" action="add" method="POST">
          <div class="col-lg-12">

            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="server" items='${servers}' varStatus="lp">
                  <tr>
                    <td><input type="checkbox" class="serverCheckbox" name="server" value="${server.id}" /></td>
                    <td>${server.name}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          
          <input type="hidden" name="servers" value="" id="selectedServers" />
          
          <div class="form-group">
            <div class="col-lg-offset-11 col-lg-10">
              <button type="submit" class="btn btn-primary" id="saveButton">Save</button>
            </div>
          </div>

        </form>
      </div>

    </div>
  </div>

</body>
</html>
