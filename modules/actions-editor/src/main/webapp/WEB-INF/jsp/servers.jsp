<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Servers" />
</jsp:include>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <script>
    $(document).ready(function() {
      $('.showScript').click(function() {
   		var data = $(this).children().first().html();
   		$('#AMIScript').html(data);
   		$('#AMIScriptModal').modal('show');
      });
    });
  </script>
    <!-- Modal -->
  <div id="AMIScriptModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 800px; margin-left: -400px; display: none;">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
      <h3 id="myModalLabel" style="margin-top: 0px;">AMI script</h3>
    </div>
    <div class="modal-body" id="AMIScript" style="font-size: 10px;">
    </div>
    <div class="modal-footer">
      <button class="btn btn-info" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-lg-12">
      <ol class="breadcrumb">
        <li><a href="/teams/">Teams</a></li>
        <li>${team.name}</li>
        <li><a href="/teams/${team.id}/serverGroups/">Server Groups</a></li>
        <li>${serverGroup.name}</li>
        <li class="active">Servers</li>
      </ol>
    </div>
      <div class="col-lg-12" style="text-align:right;">
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
            <th>Last update</th>
            <th>External id</th>
            <th>Key</th>
            <th>AMI script</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="server" items='${servers}' varStatus="lp">
            <tr>
              <td>${lp.index + 1}</td>
              <td>${server.name}</td>
              <td>${server.ipDNS}</td>
              <c:set var="serverTime" scope="request" value="${server.getLastUpdate().getTime()}" />
              <c:set var="now" scope="request" value="<%=new Date().getTime() %>" />
              <c:set var="secondsSinceLastUpdate" scope="request" value="${(now-serverTime)/1000}" />
              <c:choose>
                  <c:when test="${secondsSinceLastUpdate < 70}">
                    <td><span class="label label-success">Running</span></td>
                  </c:when>
                  <c:when test="${secondsSinceLastUpdate < 200}">
                    <td><span class="label label-warning">Not sure</span></td>
                  </c:when>
                  <c:otherwise>
                    <td><span class="label label-danger">Unreachable</span></td>
                  </c:otherwise>
                </c:choose>
              <td><fmt:formatDate type="date" value="${server.getLastUpdate()}" pattern="yyyy-MM-dd hh:mm:ss"/>  </td>
              <td>${server.externalId}</td>
              <td>${server.key}</td>
              <td>
              <div class="btn btn-info showScript"><div style="display:none;">${server.getAmiScript()}</div>Show script</div>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>