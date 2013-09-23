<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">File Editor</a></li>
      </ul>
    </div>
  </nav>

  <script type="text/javascript">
    var currentFilePath = null;
    
    var extensionModeMappings = {
    	js: "javascript",
    	xml: "xml",
    	html: "htmlmixed",
    	css: "css"    	
    }
    
    function updateEditorMode(filename) {
    	var parts = filename.split(".");
    	var extension = parts[parts.length - 1];
    	if (extensionModeMappings[extension]) {
    		templateEditor.setOption("mode", extensionModeMappings[extension]);
    	}
    }
    
    $(document).ready(function() {
        $("a.fileLink").click(function(e) {
        	  var self = this;
        	  e.preventDefault();   
            $.ajax({url: "${fileStorageServiceBaseUrl}files/" + $(self).text(), type: "GET", success: function(response) {
                 templateEditor.setValue(response);
                 updateEditorMode($(self).text())
             }});
         });  
    });

    
    
  </script>

  <div class="container">
    <div id="alerts"></div>
    <div class="col-xs-3">
      <c:forEach var="file" items='${files}'>
        <c:choose>
          <c:when test="${file.editable}">
            <a href="file/${file.name}" class="fileLink">${file.name}</a>
          </c:when>
          <c:otherwise>
          ${file.name}
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>
    <div class="col-xs-9">
      <div id="templateEditor">
        <form action="upload" method="POST" enctype="multipart/form-data">
          <input type="file" name="file" />
          <button>Upload zip file</button>
        </form>
        <div id="editor"></div>
      </div>
    </div>

  </div>
</body>
</html>