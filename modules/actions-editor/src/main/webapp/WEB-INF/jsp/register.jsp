
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<body>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
    <div class="text-center">

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                <strong>Error: </strong>
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <div class="well">
            <div class="panel panel-default" style="width: 455px; margin-left: auto; margin-right: auto;" >
                <div class="panel-heading" style='color: #FFF;background-color: #2B2B2B;'>
                    <h3 class="panel-title">User Registration</h3>
                </div>
                <div class="panel-body" id='panel-body'>
                    <div id="panel-form-body">
                        <form class="form-horizontal" role="form" name='form' action="register" method="POST" >
                            <div class="form-group">
                                <label for="inputUsername" class="col-sm-2 control-label">Username</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputUsername" placeholder="Username" name='username'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-2 control-label" >Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name='password'>
                                </div>
                            </div>
                            <div class="form-group">
                            	<label for="inputEmail" class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputEmail" placeholder="Email" name='email'>
                                </div>
                            </div>
                            <div class="form-group">
                            	<label for="inputName" class="col-sm-2 control-label">Full Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputName" placeholder="Full name" name='fullName'>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-1 col-sm-10">
                                    <input class="btn btn-default" name="submit" type="submit" value="Submit"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>                      
</body>
</html>
