<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Flows" />
</jsp:include>

<body>
  <script type="text/javascript">
  $(document).ready(function() {
	  
	linkToSelection($("#deleteButton"),".selectedObjects");
    $("#deleteButton").click(function(e) {
      var propertiesIds = "";
      e.preventDefault();
      $(".selectedObjects:checked").each(function(i, checkbox) {
    	  propertiesIds = propertiesIds + (propertiesIds ? "," : "") + $(checkbox).val();
      })
      $("#objectsToRemove").val(propertiesIds);
      $("#removeObjectsForm")[0].submit();
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
          <li class="active">Flows</li>
        </ol>
      </div>
      <div class="col-lg-12" style="text-align: right;">
        <a href="add" class="btn btn-primary">Add</a> 
        <button href="button" class="btn btn-danger" id="deleteButton">Delete</button>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Flow name</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="flow" items='${flows}' varStatus="lp">
            <tr>
              <td><input type="checkbox" class="selectedObjects" value="${flow.id}" /></td>
              <td>${flow.name}</td>
              <td><a href="edit/${flow.id}" class="btn btn-primary">Edit</a> </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

  </div>
  
  <form id="removeObjectsForm" class="form-horizontal" role="form" name="form" action="remove" method="POST" style="display:hidden">
    <input type="hidden" name="objectsToRemove" id="objectsToRemove" />
  </form>
  
</body>
</html>
