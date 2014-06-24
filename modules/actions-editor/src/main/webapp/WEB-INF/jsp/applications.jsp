<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Applications" />
</jsp:include>

<body>
  <script type="text/javascript">
  
  $(document).ready(function() {
	  linkToSelection($("#deleteButton"),".selectedApplications");
	  $("#deleteButton").click(function(e) {
		  var applicationsIds = "";
		  e.preventDefault();
		  $(".selectedApplications:checked").each(function(i, checkbox) {
			  applicationsIds = applicationsIds + (applicationsIds ? "," : "") + $(checkbox).val();
		  })
		  $("#objectsToRemove").val(applicationsIds);
		  $("#removeApplicationsForm")[0].submit();
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
        <li class="active">Applications</li>
      </ol>
      </div>
      <div class="col-lg-12" style="text-align: right;">
        <a href="add" class="btn btn-primary">Add</a> <button disabled="true" href="button" class="btn btn-danger" id="deleteButton" >Delete</button>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th></th>
              <th>Application name</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="application" items='${applications}' varStatus="lp">
            <tr>
              <td><input type="checkbox" class="selectedApplications" value="${application.id}" /> </td>
              <td>${application.name}</td>
              <td><a href="/teams/${application.team.id}/applications/${application.id}/properties/">Properties</a></td>
              <td><a href="/teams/${application.team.id}/applications/${application.id}/snapshots/">Snapshots</a></td>
              <td><a href="/teams/${application.team.id}/applications/${application.id}/flows/">Flows</a></td>
              <td><a href="/teams/${application.team.id}/applications/${application.id}/files/">Files</a></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

  </div>
  <form id="removeApplicationsForm" class="form-horizontal" role="form" name="form" action="remove" method="POST" style="display:hidden">
    <input type="hidden" name="objectsToRemove" id="objectsToRemove" />
  </form>
</body>
</html>
