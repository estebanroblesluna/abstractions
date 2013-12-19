<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

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
    
    function addFolderToTree(file,depth,root){
    	folder = file[depth-1]
    	path = file.slice(0,depth).join("/");
    	html = $("<li class='tree-folder open-folder' name='"+folder+"' path='"+path+"'/>");
    	bullet = $("<img class='tree-folder-bullet'/>");
    	anchor = $("<a>"+folder+"</a>");
    	html.append(bullet);
    	html.append(anchor);
    	html.append($("<ul class='folder-list'/>"));
    	html.append($("<ul class='file-list'/>"));
    	root.append(html);
    	//Expand/collapse con click
    	bullet.click(function(){
    		p=$(this).parent();
    		status = p.attr("class").split(" ")[1];
    		if(status == "open-folder") {
    			p.attr("class","tree-folder closed-folder");
    			$("> ul",p).hide(500);
    		} else {
    			p.attr("class","tree-folder open-folder");
    			$("> ul",p).show(500);
    		}
    	});
    	//Create dummy file on the new folder
    	return html;
    }
    
    function addFileToTree(file,root){
    	path = file.join("/");
    	filename = file[file.length-1];
    	html = $("<li class='tree-file' path='"+path+"'/>");
    	bullet = $("<img class='tree-file-bullet'/>");
    	anchor = $("<a path='"+path+"'>"+filename+"</a>");
    	anchor.click(function(e){
    		var self = this;
    		filename = $(this).attr("path");
            e.preventDefault();   
            $.ajax({url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + $(this).attr("path"), type: "GET", success: function(response) {
              fileSelected(filename, response)
             }});
    	});
    	delButton = $("<img class='delete-button' />")
    	delButton.click(function(e){
    		path = $(this).parent().attr("path");
    		self = this;
    		$.ajax({
    			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/" + path,
    			type: "DELETE", 
    			success: function(response) {
                	$(self).parent().hide(500, function() {$(self).parent().remove()});
               }});
    	});
    	html.append(bullet);
    	html.append(delButton)
    	html.append(anchor);
    	root.append(html);
    }
    
    function addPathToTree(file,depth,root){
    	node = file[depth];
    	depth += 1
    	if(file.length == depth){
    		addFileToTree(file,root.find("> .file-list"));
    		
    	} else {
    		//This is a folder.
    		//Check whether the folder already exists.
    		newroot = root.find("> ul > li[name='"+node+"']");
    		if(newroot.length != 0){
    			//The folder exists, add the file to it.
    			addPathToTree(file,depth,newroot);
    		} else {
    			newroot = addFolderToTree(file,depth,root.find("> .folder-list"));
    			addPathToTree(file,depth,newroot);
    		}
    	}
    }
    
    function addFileToList(filename) {
    	filename = filename[0] == '/' ? filename.substring(1) : filename;
    	addPathToTree(filename.split("/"),0,$("#fileTree"));
    	/*
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
        */
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
        <div class="tree-folder" id="fileTree">
        	<ul class="folder-list"></ul>
     	    <ul class="file-list"></ul>
        </div>
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