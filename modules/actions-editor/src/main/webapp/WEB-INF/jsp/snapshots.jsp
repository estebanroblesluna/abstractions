<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Snapshots" />
</jsp:include>

<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/teams/">Home</a></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Snapshots<b class="caret"></b></a>
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
          <li class="active">Snapshots</li>
        </ol>
      </div>
      <div class="col-lg-6" style="text-align: right;">
        <a href="generate" class="btn btn-primary">Generate</a>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Snapshot date</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="snapshot" items='${snapshots}' varStatus="lp">
            <tr>
              <td>${snapshot.id}</td>
              <td>${snapshot.date}</td>
              <td><a href="/teams/${teamId}/applications/${applicationId}/snapshots/${snapshot.id}/deployments/">Deployments</a>
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
