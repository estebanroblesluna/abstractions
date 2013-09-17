<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Deployments" />
</jsp:include>

<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Deployments<b class="caret"></b></a>
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
          <li><a href="#">App 1</a></li>
          <li class="active">Deployments</li>
        </ol>
      </div>
      <div class="col-lg-6" style="text-align: right;">
        <a href="add" class="btn btn-primary">Add</a> <a href="button" class="btn btn-danger" id="deleteButton">Delete</a>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>State</th>
              <th>Servers</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="deployment" items='${deployments}' varStatus="lp">
            <tr>
              <td>${deployment.id}</td>
              <td>
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
              </td>
              <td>${deployment.serverList}</td>
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
