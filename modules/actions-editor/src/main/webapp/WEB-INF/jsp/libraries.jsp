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
  <script type="text/javascript">
      function filterEnvironment() {
          var environment = document.getElementById("select-environment");
          $('#properties-table td:nth-child(4)').each (function() {
            
           if ((this).innerHTML == environment.value || environment.value == 'DISPLAY ALL')
               ($(this).closest('tr')).css('display','table-row');
            else
                ($(this).closest('tr')).css('display','none');
          });
      }
  </script>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/libraries/" class='active'>Libraries</a></li>          
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
              <th>Library name</th>
              <th>Library display name</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="library" items='${libraries}' varStatus="lp">
            <tr>
              <td><input type="checkbox" class="selectedProperties" value="${library.id}" /></td>
              <td>${library.name}</td>
              <td>${library.displayName}</td>
              <td></td>
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
