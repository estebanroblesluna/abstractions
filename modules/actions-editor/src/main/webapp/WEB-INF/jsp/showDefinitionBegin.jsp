

<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Definitions" />
</jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <ol class="breadcrumb">
          <li><a href="/libraries/">Libraries</a></li>
          <li>${libraryName}</li>
          <li><a href="/libraries/${libraryId}/definitions/" >Definitions</a></li> 
          <li>Show</li>
          <li><a class='active' href="/libraries/${libraryId}/definitions/show/${definition.id}" class='active'>${definition.name}</a></li>
        </ol>   
      </div>
    </div>

    <div class="row">
                <div class="col-lg-9">
                    <form class="form-horizontal">  
                        <div class="form-group">
                            <label for="inputName" class="col-lg-2 control-label">Name</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputName" name="name" placeholder="" readonly='readonly' value='${definition.name}'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputDisplayName" class="col-lg-2 control-label" >Display Name</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputDisplayName" name="displayName" placeholder="" readonly='readonly' value='${definition.displayName}'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputImplementation" class="col-lg-2 control-label">Implementation</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="inputImplementation" name="implementation" placeholder="" readonly='readonly' value='${definition.implementation}'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputIsScript" class="col-lg-2 control-label">Script?</label>
                            <div class="col-lg-10">
                                <div>
                                    <input type="text" class="form-control" id="inputIsScript" name="isScript" placeholder="" readonly='readonly' value='${definition.isScript() == true ? 'Yes' : 'No'}'>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputIcon" class="col-lg-2 control-label">Icon</label>
                            <div class="col-lg-10">
                                <img src="/editor/${definition.icon}" alt="...">
                            </div>
                        </div>
