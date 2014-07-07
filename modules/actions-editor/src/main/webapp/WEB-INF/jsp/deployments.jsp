<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Deployments" />
</jsp:include>

<script>
$(document).ready(function() {
  $(".viewDeployment").click(function(){
      var id = $(this).attr("id").split('-')[1];
      var loopIndex = $(this).attr("id").split('-')[2];
      str = id + "/flow/" + $($("tBody").find(".selectDeployment")[loopIndex]).val();
      window.location.href = str;
  });
});

</script>
<body>
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
          <li class="active">Deployments</li>
        </ol>
      </div>
      <div class="col-lg-12" style="text-align: right;">
        <a href="add" class="btn btn-primary">Add</a> <a href="button" class="btn btn-danger" id="deleteButton">Delete</a>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
<!--                   <th>State</th>     -->
              <th>Servers</th>
              <th>Flow</th>
              <th></th>
            </tr>
          </thead>
          <tbody id="tBody">
            <c:forEach var="deployment" items="${deployments}" varStatus="lp">
              <tr>
                <td>${deployment.id}</td>
    <%--            <td>
                    <c:choose>
                      <c:when test="${deployment.getState().toString() == 'PENDING'}"> 
                          <a href="#" class="btn btn-sm btn-primary">Pending</a>
                      </c:when>
                      <c:when test="${deployment.getState().toString() == 'STARTED'}"> 
                          <a href="#" class="btn btn-sm btn-info">Started</a> 
                      </c:when> 
                      <c:when test="${deployment.getState().toString() == 'FINISH_SUCCESSFULLY'}"> 
                          <a href="#" class="btn btn-sm btn-success">Finish successfully</a>
                      </c:when>
                      <c:when test="${deployment.getState().toString() == 'FINISH_WITH_ERRORS'}">
                          <a href="#" class="btn btn-sm btn-danger">Finish with errors</a> 
                      </c:when> 
                      <c:otherwise> 
                      </c:otherwise>
                  	</c:choose>
                  </td>                --%>
                <td>${deployment.serverList}</td>
                <td>
                  <select class="selectDeployment form-control" name="flowSelect" varStatus="fC">
                    <c:forEach var="flow" items="${deployment.snapshot.flows}">
                      <option value="${flow.id}">${flow.name}</option>
                    </c:forEach>
                  </select>
                </td>
                <td><a id="vd-${deployment.id}-${lp.index}" class="viewDeployment btn btn-primary">View deployment</a></td>
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
