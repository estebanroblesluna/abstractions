
<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Add Definition"/>
</jsp:include>

<body>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <ol class="breadcrumb">
                    <li><a href="/libraries/">Libraries</a></li>
                    <li>${libraryName}</li>
                    <li><a href="/libraries/${libraryId}/definitions/" class='active'>Definitions</a></li>   
                    <li class='active'>Add Definition</li>  
                </ol> 
            </div>

            <div class="row">
                <div class="col-lg-9">
                    <form class="form-horizontal" role="form" name="form" action="add" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="inputName" class="col-lg-2 control-label">Name</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputName" name="name" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputDisplayName" class="col-lg-2 control-label">Display Name</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputDisplayName" name="displayName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputImplementation" class="col-lg-2 control-label">Implementation</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputImplementation" name="implementation" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputIsScript" class="col-lg-2 control-label">Script?</label>
                            <div class="col-lg-10">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="isScript" id='inputIsScript' > Is script?
                                         <input type="hidden" value="on" name="_isScript">
                                    </label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="inputIcon" class="col-lg-2 control-label">Icon</label>
                            <div class="col-lg-10">
                                <input type="file" class="form-control" id="inputIcon" name="icon" style="padding:0px;">
                            </div>
                        </div>
  

