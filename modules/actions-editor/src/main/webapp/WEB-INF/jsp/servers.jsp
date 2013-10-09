<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Servers" />
</jsp:include>

<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Applications<b class="caret"></b></a>
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
            <th>Last update</th>
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
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    </div>
    
  </div>
</body>
</html>