<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/header.jsp">

    <jsp:param name="title" value="Login" />
</jsp:include>
<head>
    <link href="/static/css/font-awesome.css" rel="stylesheet">


<body>
    <link href = "/static/css/social-buttons.css" rel = "stylesheet">
    <style type="text/css">
        html, body {
          line-height: 0px;
          font-size: 13px;
        }

        body {
          background: url(static/img/background.jpg) repeat 0 0;
        }        
        
        .whiteBackground {
          background-color: #FFF;
        }
        
        p {
          margin-bottom: 15px;
        }
    </style>
    <script>
        function loadLoadingImage() {
            $("#panel-form-body").hide();
            $("#loading").show();
            return true;
        }
    </script>
    
    <div class="text-center" class="container">
        <div class="row" style="margin-top: 50px">
			<c:if test='${not empty param.confirmed}'>
				<div class="alert alert-success" id="info" style="min-width:620px; max-width: 1170px; margin-right: auto; margin-left: auto">
					<span class="glyphicon glyphicon-ok"></span>
					Your e-mail has been confirmed
				</div>
				<div class="alert alert-info" id="info" style="min-width:620px; max-width: 1170px; margin-right: auto; margin-left: auto">
					<span class="glyphicon glyphicon-info-sign" ></span>
					Please remember that your account will be <strong>enabled</strong> after our admins check your information
				</div>
			</c:if>
			<div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4 col-xs-4 col-xs-offset-4 whiteBackground well" id="pan" style="min-width: 340px;">
              <div class="container">
                <div class="row">
                  <div class="col-sm-12 col-md-12">
                    <img src='static/img/loading.gif' alt='Loading...' id='loading' style='display:none;'/>
                    <div id="panel-form-body">
                       
                        <form class="form-horizontal" role="form" name='f' action="<c:url value='j_spring_security_check'/>" method="POST" >
                            <div class="form-group">
                                <div class="col-md-12 col-sm-12">
                                  <img src="static/img/logo.png" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 col-sm-12">
                                    <input type="text" class="form-control" id="inputEmail3" placeholder="Username" name='j_username'>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 col-sm-12">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name='j_password'>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 col-sm-12" >
                                    <button class="btn btn-success btn-block" name="submit" id="login" type="submit"  onclick="loadLoadingImage();" onclick="alto()">
                                        <span>Login</span>
                                        <span class="glyphicon glyphicon-ok-sign"></span>
                                    </button>
                                </div>
                            </div>
                            
                            <c:if test='${not empty param.error}'>
                                <div class="alert alert-danger" id="alert">
                                    <span class="glyphicon glyphicon-remove-circle"></span>
                                    <strong>Error: </strong>
                                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                                </div>
                            </c:if>
                        </form>
                    </div>
                  </div>
                </div>
                
                <hr>
                <c:if test="${socialLoginEnabled}">
                  <div class="row">
                    <p>Or login with:</p>
                    <div class="col-sm-12 col-md-12" >
                      <form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
                          <input type="hidden" name="scope" value="publish_stream,offline_access" />
                          <button type="submit"  class="btn btn-facebook pull-left col-sm-5 col-md-5" class="fa fa-facebook" onclick="loadLoadingImage();">
                            <i class="fa fa-facebook"></i> | Facebook
                          </button>
                      </form>
                      <form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
                         <input type="hidden" name="scope" value="publish_stream,offline_access" />
                         <button type="submit"  class="btn btn-twitter pull-right col-sm-5 col-md-5" onclick="loadLoadingImage();">
                            <i class="fa fa-twitter"></i> | Twitter
                        </button>
                      </form>
                    </div>
                  </div>
                </c:if>

                <div class="row" style="margin-top:30px">
                  <p>Don't have an account?</p>
                  <div class="col-md-12 col-sm-12">
                    <a class="btn btn-danger btn-block" name="submit" id="register" type="submit" href="register">
                      <span>Sign up</span>
                    </a>
                  </div>
                </div>
            </div>

        </div>
    </div>                     
    </div>
</body>
</head>
</html>
