
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<body>
    <script>
        function loadLoadingImage() {
            $("#panel-form-body").hide();
            $("#loading").show();
            return true;
        }
    </script>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
    <div class="text-center">
        <h1>Abstractions</h1>
        <p class="lead">Login page</p>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                <strong>Error: </strong>
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <div class="well">
            <div class="panel panel-default" style="width: 450px; margin-left: auto; margin-right: auto;" >
                <div class="panel-heading" style='color: #FFF;background-color: #2B2B2B;'>
                    <h3 class="panel-title">User Login</h3>
                </div>
                <div class="panel-body" id='panel-body'>
                    <img src='static/img/loading.gif' alt='Loading...' id='loading' style='display:none;'/>
                    <div id="panel-form-body">
                        <form class="form-horizontal" role="form" name='f' action="<c:url value='j_spring_security_check'/>" method="POST" >
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-2 control-label">Username</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputEmail3" placeholder="Username" name='j_username'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-2 control-label" >Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name='j_password'>
                                </div>
                            </div>
                            <div class="form-group">
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-1 col-sm-10">
                                    <input class="btn btn-default" name="submit" type="submit" value="Submit" onclick="loadLoadingImage();"/>
                                </div>
                            </div>
                        </form>

                        <div> --- OR --- </div>

                        <form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
                            <input type="hidden" name="scope" value="publish_stream,offline_access" />
                            <button type="submit"  class="btn btn-primary" style="margin:20px;" onclick="loadLoadingImage();">
                                Login with Facebook
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>                      
</body>
</html>
