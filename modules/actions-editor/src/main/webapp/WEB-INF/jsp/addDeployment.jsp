<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add deployment" />
</jsp:include>

<body>

  <script type="text/javascript">
  $(document).ready(function() {
    $("#saveButton").click(function(e) {
      var ids = "";
      e.preventDefault();
      $(".serverCheckbox:checked").each(function(i, checkbox) {
    	  ids = ids + (ids ? "," : "") + $(checkbox).val();
      })
      $("#selectedServers").val(ids);
      $("form")[0].submit();
    })
  })
  </script>
  
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
          <li><a href="/teams">Deployments</a></li>
          <li class="active">Add deployment</li>
        </ol>
      </div>

      <div class="row">
        <form class="form-horizontal" role="form" name="form" action="add" method="POST">
          <div class="col-lg-12">

            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="server" items='${servers}' varStatus="lp">
                  <tr>
                    <td><input type="checkbox" class="serverCheckbox" name="server" value="${server.id}" /></td>
                    <td>${server.name}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          
          <input type="hidden" name="servers" value="" id="selectedServers" />
          
          <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
              <button type="submit" class="btn btn-primary" id="saveButton">Save</button>
            </div>
          </div>

        </form>
      </div>

    </div>
  </div>

</body>
</html>
