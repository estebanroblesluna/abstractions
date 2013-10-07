<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/teams/">File Editor</a></li>
      </ul>
    </div>
  </nav>

  <script type="text/javascript">
    var templateEditor;
    var currentFilename = null;
    var newFile = false;
    var applicationId = ${applicationId};
    
    var extensionModeMappings = {
    	js: "javascript",
    	xml: "xml",
    	html: "htmlmixed",
    	htm: "htmlmixed",
    	css: "css",
    	tl: "dust"
    }
    
    function extractExtension(filename) {
    	var parts = filename.split(".");
      return parts[parts.length - 1];
    }
    
    function updateEditorMode(filename) {
    	var extension = extractExtension(filename);
    	if (extensionModeMappings[extension]) {
    		templateEditor.setOption("mode", extensionModeMappings[extension]);
    	}
    }
    
    function fileSelected(filename, content) {
        templateEditor.setValue(content);
        updateEditorMode(filename);
        $("#filename").text(filename);
        currentFilename = filename;
        $("#editorContainer").fadeIn(500);
        newFile = false;
    }
    
    function fileSaved(filename, content) {
    	showMessage("File saved");
    	debugger;
    	if (newFile) {
    		addFileToList(filename);
    		newFile = false;
    	}
    }
    
    function addFileToList(filename) {
        var html = "<li>";
        filename = filename[0] == '/' ? filename.substring(1) : filename;
        var extension = extractExtension(filename);
        html = html + '<button class="btn btn-xs deleteFile" href="[filename]">Del</button>'.replace(/\[filename\]/g, filename);
        if (extensionModeMappings[extension]) {
          html = html + '<a href="file/[filename]" class="fileLink">[filename]</a>'.replace(/\[filename\]/g, filename);
        } else {
          html = html + filename;
        }
        html = html + "</li>";
        var jHtml = $(html);
        jHtml.find('a.fileLink').click(function(e) {
            var self = this;
            e.preventDefault();   
            $.ajax({url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + filename, type: "GET", success: function(response) {
              fileSelected(filename, response)
             }});
        });
        jHtml.find('button.deleteFile').click(function(e) {
            var self = this;
            e.preventDefault();
            $.ajax({url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + $(self).attr('href'), type: "DELETE", success: function(response) {
              $(self).parent().hide(500, function() {$(self).parent().remove()});
            }});
        });
        $("#fileList").append(jHtml);
    }
    
    $(document).ready(function() {
    	
        templateEditor = CodeMirror($("#editor")[0], {
            value: "",
            mode:  "dust",
            lineNumbers: true
        });
        templateEditor.setSize(700, 500);
    	
    	  $.ajax({url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + $(self).text(), type: "GET", dataType: 'json', success: function(response) {
            $.each(response.files, function(_, filename) {
            	 addFileToList(filename);
            });
        }});
        
        $("#saveButton").click(function(e) {
            var self = this;
            e.preventDefault();   
            $.ajax({url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + currentFilename, type: "PUT", data: templateEditor.getValue(), success: function(response) {
              fileSaved(currentFilename, templateEditor.getValue());
             }});
        });
        
        $("#uploadZip").click(function(e) {
            var self = this;
            e.preventDefault();  
            $("#zipFile").trigger("click");
        });  
        
        $("#zipFile").change(function(e) {
        	$("#zipUploadForm").submit()
        }); 
        
        $("#newFile").click(function(e) {
        	  e.preventDefault();
            var filename = prompt("Enter filename");
            if (!filename) {
            	return;
            }
            if (filename[0] == '/') {
            	filename = filename.substring(1);
            }
            fileSelected(filename, "");
            newFile = true;
         });
        
    });
    
		</script>

  <div class="container">
    <div id="alerts"></div>
    <div class="row">
      <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
        <form id="zipUploadForm" action="upload" method="POST" enctype="multipart/form-data">
          <input id="zipFile" type="file" name="file" style="display: none" />
          <button id="uploadZip" class="btn">Upload zip file</button>
          <button id="newFile" class="btn">New file</button>
        </form>
      </div>
      <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
        <h2>
          <span id="filename"></span>
        </h2>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
        <ul id="fileList"></ul>
      </div>
      <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
        <div class="row">
          <button class="btn" id="saveButton">Save</button>
        </div>
        <div class="row">
          <div id="editorContainer">
            <div id="editor"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>