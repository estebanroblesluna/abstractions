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
        <li><a href="/teams/${team.id}/serverGroups/">Server Groups</a></li>
        <li>${serverGroup.name}</li>
        <li class="active">Servers</li>
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
            <th>Server name</th>
            <th>IP/DNS</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="server" items='${servers}' varStatus="lp">
            <tr>
              <td>${lp.index + 1}</td>
              <td>${server.name}</td>
              <td>${server.ipDNS}</td>
              <td><span class="label label-success">Running</span> TODO</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>