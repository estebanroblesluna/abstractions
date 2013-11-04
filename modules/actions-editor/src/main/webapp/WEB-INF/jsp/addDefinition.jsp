<jsp:include page="/WEB-INF/jsp/header.jsp">
  <jsp:param name="title" value="Add Definition"/>
</jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
       <p class="lead" style="text-align: center;">Select the type of the definition you want to add:</p>
        <div class="well" style="max-width: 300px;margin-left: auto;margin-right: auto;">
            
            <c:forEach var="link" items='${addLinks}' >
               <button type="button" class="btn btn-primary btn-lg btn-block" onclick="location.href='/libraries/${libraryId}/definitions/${link}/'"> ${fn:substringAfter(link, 'add')} </button>
            </c:forEach>
        </div>
      </div>

    </div>
</body>
</html>
