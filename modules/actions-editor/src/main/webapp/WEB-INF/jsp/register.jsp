
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<body>
    <style type="text/css">

        body {
          background: url(static/img/background.jpg) repeat 0 0;
        }        
        .col-sm-offset-1{
            margin-left: 0%;
        }
        
        .whiteBackground {
          background-color: #FFF;
        }

        p {
          margin-bottom: 15px;
        }
    </style>
    <div class="text-center">
    	<c:set var="usernameExistsError" value="${usernameExistsError}"/>
    	<c:set var="emailExistsError" value="${emailExistsError}"/>
    	<c:if test="${not empty usernameExistsError}">
            <div class="alert alert-danger">
                <strong>Error: </strong>
                ${usernameExistsError}
            </div>
        </c:if>
        
        <c:if test="${not empty emailExistsError}">
            <div class="alert alert-danger">
                <strong>Error: </strong>
                ${emailExistsError}
            </div>
        </c:if>
    	
    	

            <div class="panel panel-default" style="width: 400px; margin-left: auto; margin-right: auto; margin-top: 100px;" >

                <div class="panel-body" id='panel-body'>
                    <div id="panel-form-body">
                        <form:form class="form-horizontal" role="form" name='form' action="register" method="POST" modelAttribute="registerForm"  >
                            <div class="form-group">
                                <label for="inputUsername" class="col-sm-2 control-label" style="font-size: smaller">Username</label>
                                <div class="col-sm-10">
                                	<form:errors path="username" cssClass="alert-danger" element="div"></form:errors>
                                    <form:input path="username" type="text" class="form-control" id="inputUsername"  name='username' />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-2 control-label"style="font-size: smaller">Password</label>
                                <div class="col-sm-10">
                                	<form:errors path="password"  cssClass="alert-danger" element="div"></form:errors>
                                    <form:input path="password" type="password" class="form-control" id="inputPassword3"  name='password' />
                                </div>
                            </div>
                            <div class="form-group">
                            	<label for="inputEmail" class="col-sm-2 control-label" style="font-size: smaller">Email</label>
                                <div class="col-sm-10">
                                	<form:errors path="email"  cssClass="alert-danger" element="div"></form:errors>
                                    <form:input path="email" type="text" class="form-control" id="inputEmail"  name='email' />
                                </div>
                            </div>
                            <div class="form-group">
                            	<label for="inputName" class="col-sm-2 control-label"style="font-size: smaller">Full Name</label style="font-size: small">
                                <div class="col-sm-10">
                                	<form:errors path="fullName"  cssClass="alert-danger" element="div"></form:errors>
                                    <form:input path="fullName" type="text" class="form-control" id="inputName"  name='fullName' />
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="col-sm-offset-1 col-sm-12">
                                    <button class="btn btn-success btn-block" name="submit" type="submit" onclick="alto()">
                                        <span>Submit</span>
                                        <span class="glyphicon glyphicon-ok-sign"></span>
                                        
                                </button>
                                </div>
                                
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>                      
</body>
</html>
