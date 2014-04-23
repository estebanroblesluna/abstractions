<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/header.jsp">

    <jsp:param name="title" value="Login" />
</jsp:include>
<head>
    <link href="/static/css/font-awesome.css" rel="stylesheet">


<body>
    <link href = "/static/css/social-buttons.css" rel = "stylesheet">
    <style type="text/css">
        #login{
            width: 80px;
        }
        html, body {
          line-height: 0px;
          font-size: 13px;
            background: url(static/img/background.jpg) repeat 0 0;
        }
        .text-center{
            text-align: center;
        }
        #jum{
        
            padding-top: 0;
            padding-bottom: 0;
            padding: 0;
            margin-bottom: 0;
            border-bottom: 1px solid #ddd;
            background-position: 0px -1000px;

        }
        h5 {
            text-align: center;
        }
        .hr{
            margin-top: 0px;
        }
        .alert{
            font-size: smaller;
        }
        #well{
            margin: 0;
            border: 0;
            padding-top: 100px;
        }
        .col-sm-offset-4{
            margin-left: 25%;
        }
        .col-sm-offset-2{
            margin-left: 0%;
        }
        .panel-body{
            
            padding: 15px;
        }
        #panel-body{
            background-color: #FFF;
            
        }
        .panel-default{
            border-color: #000;
        }
        #panel-title{
            font-size: 0;
        }
        .text-center{
            text-align: center;
        }   
        .col-sm-offset-1{
            padding-left: 0px;
            margin-left: 0;
            padding-left: 0px;
        }
        .form-horizontal .form-group {
            margin-left: 0;
            margin-right: -50px;
        }
        .btn {
            border-width: 0 1px 4px 1px;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .btn-xs{
            font-size: 15px;
            padding: 0;
        }
        #fb_signin{
            display: inline;
        }   
        #tw_signin{
            display: inline;
        }
        #panel{
            font-family: monospace;
        }
        .p{
            margin: 0 0 0px;
            line-height: 0px;
            font-size: 13px;
        }

    </style>
    <script>
        function loadLoadingImage() {
            $("#panel-form-body").hide();
            $("#loading").show();
            return true;
        }
    </script>
    <div class="text-center" id="mainContent" class="container">

        <div id="well">
            

            <div class="panel panel-default" id= "pan" style="width: 270px; margin-left: auto; margin-right: auto;" >
                
                <div class="panel-body" id='panel-body'>
                    <img src='static/img/loading.gif' alt='Loading...' id='loading' style='display:none;'/>
                    <div id="panel-form-body">
                       
                        <form class="form-horizontal" role="form" name='f' action="<c:url value='j_spring_security_check'/>" method="POST" >
                            <div class="form-group">
                                
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputEmail3" placeholder="Username" name='j_username'>
                                </div>
                            </div>
                            <div class="form-group">
                                
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name='j_password'>
                                </div>
                            </div>
                            
                            <div class="form-group">

                                <div class="col-sm-offset-3 col-sm-10" >
                                    
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

                        <hr>

                    </div>
                </div>
                
                <p>Or login with your social account</p>
                

                <div class="panel-body" id='panel-body'>
                        <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-3" >
                        <form id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
                            <input type="hidden" name="scope" value="publish_stream,offline_access" />

                            <button type="submit"  class="btn btn-facebook" class="fa fa-facebook" onclick="loadLoadingImage();">
                                
                                
                                <i class="fa fa-facebook"></i> | Facebook

                            </button>
                        </form>
                        </div>

                       
                        <div class="col-sm-offset-3 col-sm-3" >
                        <form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
                             <input type="hidden" name="scope" value="publish_stream,offline_access" />

                            <button type="submit"  class="btn btn-twitter" onclick="loadLoadingImage();">

                                <i class="fa fa-twitter"></i> | Twitter
                                
                            </button>
                        </form>
                        </div>
                        </div>

                        

                </div>
                <div class="panel-body" id='panel-body'>
                    
                
                    <p class="text-center">Don't have an account?</p>
                

                <div class="col-sm-offset-2 col-sm-12" style="
    margin-top: 20px;" >
                                    <button class="btn btn-danger btn-block" name="submit" id="register" type="submit"  onclick="loadLoadingImage();">
                                        <span>Sing UP</span>
                                        
                                        
                                    </button>
                </div>
                </div>
            </div>

        </div>
    </div>                     
</body>
</head>
</html>
