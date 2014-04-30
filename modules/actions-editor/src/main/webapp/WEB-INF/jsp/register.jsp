
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<body>
    <style type="text/css">
        html, body {
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
        
        .registerTitle {
          margin-top: 10px;
          margin-bottom: 20px;
        }
    </style>
        <div class="container">
          
          <div class="row" style="margin-top: 50px">
            <div class="alert alert-info" id="info" style="text-align: center">
                <span class="glyphicon glyphicon-info-sign"></span>
                Please remember that your account will be <strong>enabled</strong> after our admins check your information 
            </div>
            <div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4 col-xs-4 col-xs-offset-4 whiteBackground well" id="pan" style="min-width: 324px;">
              <div class="container text-center">
                <div class="row">
                  <c:set var="usernameExistsError" value="${usernameExistsError}"/>
                  <c:set var="emailExistsError" value="${emailExistsError}"/>

                  <c:if test="${not empty usernameExistsError}">
                    <div class="alert alert-danger">
                      <strong>Error: </strong> ${usernameExistsError}
                    </div>
                  </c:if>

                  <c:if test="${not empty emailExistsError}">
                    <div class="alert alert-danger">
                      <strong>Error: </strong> ${emailExistsError}
                    </div>
                  </c:if>
                  
                  <form:form class="form-horizontal" role="form" name='form' action="register" method="POST" modelAttribute="registerForm">
                    <div class="form-group">
                        <div class="col-md-12 col-sm-12">
                          <img src="static/img/logo.png" />
                        </div>
                    </div>
                    <div class="form-group">
                      <label for="inputUsername" class="col-sm-3 control-label">Username</label>
                      <div class="col-sm-9 controls">
                        <form:input path="username" type="text" class="form-control" id="inputUsername" name='username' />
                        <form:errors path="username" cssClass="alert-danger" element="div"></form:errors>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="inputPassword3" class="col-sm-3 control-label">Password</label>
                      <div class="col-sm-9">
                        <form:input path="password" type="password" class="form-control" id="inputPassword3" name='password' />
                        <form:errors path="password" cssClass="alert-danger" element="div"></form:errors>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="inputEmail" class="col-sm-3 control-label">Email</label>
                      <div class="col-sm-9">
                        <form:input path="email" type="text" class="form-control" id="inputEmail" name='email' />
                        <form:errors path="email" cssClass="alert-danger" element="div"></form:errors>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="inputName" class="col-sm-3 control-label">Full Name</label>
                      <div class="col-sm-9">
                        <form:input path="fullName" type="text" class="form-control" id="inputName" name='fullName' />
                        <form:errors path="fullName" cssClass="alert-danger" element="div"></form:errors>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <button class="btn btn-success btn-block" name="submit" type="submit">
                          <span>Submit</span> <span class="glyphicon glyphicon-ok-sign"></span>
                        </button>
                      </div>
                    </div>
                  </form:form>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>                      
</body>
</html>
