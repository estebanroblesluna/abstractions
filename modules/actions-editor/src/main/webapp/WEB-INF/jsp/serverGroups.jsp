<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Servers" />
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-6">
      <ol class="breadcrumb">
        <li><a href="/teams/">Teams</a></li>
        <li>${team.name}</li>
        <li class="active">Server Groups</li>
      </ol>
    </div>
      <div class="col-lg-6" style="text-align:right;">
<a href="add" class="btn btn-primary">Add</a>
<a href="button" class="btn btn-danger">Delete</a>
    </div>
    </div>
    
    <div class="row">
    <div class="col-lg-12">
    <table class="table table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Server group name</th>
            <th>Environment</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="serverGroup" items='${serverGroups}' varStatus="lp">
            <tr>
              <td>${lp.index + 1}</td>
              <td>${serverGroup.name}</td>
              <td>${serverGroup.environment}</td>
              <td><a href="/teams/${teamId}/serverGroups/${serverGroup.id}/servers/">Servers</a>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>