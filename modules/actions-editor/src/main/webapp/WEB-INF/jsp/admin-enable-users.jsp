<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="title" value="Confirm Users" />
</jsp:include>

<body>
	<script type="text/javascript">
  $(document).ready(function() {
    $("#submit").click(function(e) {
      var usernames = "";
      e.preventDefault();
      $(".selectedObjects:checked").each(function(i, checkbox) {
    	  usernames = usernames + (usernames ? "," : "") + $(checkbox).val();
      })
      $("#usersToEnable").val(usernames);
      $("#EnableUsersForm")[0].submit();
    })
  })
  </script>
	<jsp:include page="/WEB-INF/jsp/navbar.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<ol class="breadcrumb">
					<li>Admin</li>
					<li class="active">Enable confirmed users</li>
				</ol>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">

				<table class="table table-striped">
					<thead>
					<b>Disabled users with confirmed email:</b>
						<tr>
							<th>Username</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Email</th>
							<th>Enable?</th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="u" items='${my_conf_users}' varStatus="lp">
							<tr>
								<td>${u.username}</td>
								<td>${u.firstName}</td>
								<td>${u.lastName}</td>
								<td>${u.email}</td>
								<td><input type="checkbox" class="selectedObjects"
									name="usersToEnable" value="${u.username}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<form id="EnableUsersForm" class="form-horizontal" role="form"
					name="form" action="enable-users" method="POST">
					<input type="hidden" name="usersToEnable" id="usersToEnable" />
				</form>
				<button type="submit" class="btn btn-default" id="submit">
						<b>Submit</b>
				</button>
			</div>
		</div>

	</div>

</body>
</html>
