<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Connectors" />
</jsp:include>

<body>  
  <script type="text/javascript">
    $(document).ready(function() {
      linkToSelection($("#deleteButton"),".selectedConnectors");
      $("#deleteButton").click(function(e) {
        var connectorsIds = "";
        e.preventDefault();
        $(".selectedConnectors:checked").each(function(i, checkbox) {
          connectorsIds = connectorsIds + (connectorsIds ? "," : "") + $(checkbox).val();
        })
        $("#objectsToRemove").val(connectorsIds);
        $("#removeConnectorsForm")[0].submit();
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
          <li class="active">Connectors</li>
        </ol>
      </div>
      <div class="col-lg-12" style="text-align:right;">
        <a href="add" class="btn btn-primary">Add</a>
        <button class="btn btn-danger" id="deleteButton">Delete</button>
      </div>
    </div>  

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Connector name</th>
              <th>Connector type</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="connector" items='${connectors}' varStatus="lp">
              <tr>
                <td><input type="checkbox" class="selectedConnectors" value="${connector.id}" /> </td>
                <td>${connector.name}</td>
                <td>${connector.type}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <form id="removeConnectorsForm" class="form-horizontal" role="form" name="form" action="remove" method="POST" style="display:hidden">
    <input type="hidden" name="objectsToRemove" id="objectsToRemove" />
  </form>

</body>
</html>
