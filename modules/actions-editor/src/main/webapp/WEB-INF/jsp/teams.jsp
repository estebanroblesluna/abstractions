<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Teams" />
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-6">
      <ol class="breadcrumb">
        <li><a href="#">Teams</a></li>
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
            <th>Name</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="team" items='${teams}' varStatus="lp">
            <tr>
              <td>${lp.index + 1}</td>
              <td>${team.name}</td>
              <td><a href="/teams/${team.id}/applications/">Apps</a></td>
              <td><a href="/teams/${team.id}/serverGroups/">Server Groups</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>