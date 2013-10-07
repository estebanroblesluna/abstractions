<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Properties" />
</jsp:include>

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
          <li><a href="/teams/${teamId}/applications/">Applications</a></li>
          <li>${applicationName}</li>
          <li class="active"> Properties </li>
          
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
        <a href="add" class="btn btn-primary">Add</a> <a href="button" class="btn btn-danger" id="deleteButton">Delete</a>
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
          <c:forEach var="property" items='${properties}' varStatus="lp">
            <tr>
              <td><input type="checkbox" class="selectedProperties" value="${property.id}" /></td>
              <td>${property.name}</td>
              <td>${property.value}</td>
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
