
<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Definitions" />
</jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
  <script type="text/javascript">
  $(document).ready(function() {
    $("#deleteButton").click(function(e) {
      var propertiesIds = "";
      e.preventDefault();
      $(".selectedProperties:checked").each(function(i, checkbox) {
    	  propertiesIds = propertiesIds + (propertiesIds ? "," : "") + $(checkbox).val();
      })
      $("#objectsToRemove").val(propertiesIds);
      $("#removePropertiesForm")[0].submit();
    })  
  })
  </script>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/libraries/">Libraries</a></li>
          <li>${libraryName}</li>
          <li><a href="/libraries/${libraryId}/definitions/" class='active'>Definitions</a></li>   
        </ol>   
      </div>
      <div class="col-lg-12" style="text-align: right;">       
        <a href="add" class="btn btn-primary">Add</a> <a href="button" class="btn btn-danger" id="deleteButton">Delete</a>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped" id="properties-table">
          <thead>
            <tr>
              <th>#</th>
              <th>Definition name</th>
              <th>Display name</th>
              <th>Icon</th>
              <th>Implementation</th>
              <th>Script?</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="definition" items='${definitions}' varStatus="lp">
            <tr>
              <td><input type="checkbox" class="selectedProperties" value="${definition.id}" /></td>
              <td>${definition.name}</td>
              <td>${definition.displayName}</td>
              <td><img src="/editor/${definition.icon}" alt="..." style="max-width: 20px;max-height: 20px;"/></td>
              <td><small><i>${definition.implementation}</i></small></td>
              <td>${definition.isScript() == true ? 'Yes' : 'No'} </td>
              <td><button type="button" class="btn btn-default btn-xs" onclick="location.href='/libraries/${libraryId}/definitions/show/${definition.id}/'"><span class="glyphicon glyphicon-eye-open"></span> <small>Show</small></button></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

  </div>
  
  <form id="removePropertiesForm" class="form-horizontal" role="form" name="form" action="remove" method="POST" style="display:hidden">
    <input type="hidden" name="objectsToRemove" id="objectsToRemove" />
  </form>
  
</body>
</html>
