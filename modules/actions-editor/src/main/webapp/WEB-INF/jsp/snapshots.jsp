<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Snapshots" />
</jsp:include>

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
          <li class="active">Snapshots</li>
        </ol>
      </div>
    </div>
    
    <div class="row">
      	<form class="form-inline text-right" role="form" name="form" action="generate" method="POST">
	     <div class="form-group"><label  for="inputPropertyEnvironment">Environment:</label></div>
	     <div class="form-group">
      	 <select class="form-control span2" id="inputProperyEnvironment" name="environment">
             <option>DEV</option>
             <option>QA</option>
             <option>PROD</option>
             <option>STG_ALPHA</option>
             <option>STG_BETA</option>
         </select>
         </div>
         <div class="form-group">
	        <button type="submit" href="generate" class="btn btn-primary span2 pull-right">Generate</button>
	     </div>
        </form>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Snapshot date</th>
              <th>Environment</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="snapshot" items='${snapshots}' varStatus="lp">
            <tr>
              <td>${snapshot.id}</td>
              <td>${snapshot.date}</td>
              <td>${snapshot.environment}</td>
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
