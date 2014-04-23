<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
  <script type="text/javascript">
  
	var applicationId = ${applicationId};
	//var templateEditor;
	var editor;
	var currentFilename = null;
	var newFile = false;
	var resType = "public";
	
	//File tree vars
	var fileTreeView;
  
	//File tree functions
  	function selectFile(node, e){
		if(!e.ctrlKey)
		{
			$(".selectedFile").attr("class","file");
			node.cleanTreeSelection();
		}
		node.selected = true;
		$(this).attr("class","file selectedFile");
		filename = node.getPath();
        e.preventDefault(); 
  		$.ajax({
  			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
  			type: "GET",
  			success: function(response) {
            	editor.setFile(filename, response);
        	}
  		});
  	}
	
    function addFileToTree(filename) {
    	filename = filename[0] == '/' ? filename.substring(1) : filename;
    	fileTreeView.model.addFile(filename);
    }
	
	//Editor functions
    
    function fileSaved(filename, content) {
    	showMessage("File saved");
    	debugger;
    	if (newFile) {
    		addFileToTree(filename);
    		newFile = false;
    	}
    } 
	
    function saveFile(filename,content){
    	if(filename[0] != "/")
    		filename = "/"+filename;
	    $.ajax({
	    	url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
	    	type: "PUT",
	    	data: content,
	    	success: function(response) {
	      		fileSaved(filename, content);
	     	}
	    });
    }	
    
    function fillTree(){
    	$("#fileTree").empty();
    	fileTreeView = new FolderView($("#fileTree"));
    	fileTreeView.fileSelectedHook = selectFile;
    	$.ajax({
   			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType +"/" + $(self).text(),
   			type: "GET",
   			dataType: 'json',
   			success: function(response) {
           		$.each(response.files, function(_, filename) {
           	 		addFileToTree(filename);
           		});
       		}
		});
    }

    
    $(document).ready(function() {
    	
    	//Initialize the file tree
    	fillTree();
    	
    	
        editor = new FileEditor($("#editor"), 700, 500,$("#toolbar"));
        editor.setSaveFileHook(saveFile)
        
        $("#publicResBtn").click(function(e){
        	resType="public";
        	fillTree();
        });
        
        $("#privateResBtn").click(function(e){
        	resType="private";
        	fillTree();
        });
        
        //File tree toolbar
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
            editor.setFile(filename, "");
            newFile = true;
         });
        
        $("#deleteFile").click(function(e){
        	e.preventDefault();
        	$(".selectedFile").each(function(){
        		var filename = $(this).parent().attr("path");
        		var self = this;
        		$.ajax({
           			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
           			type: "DELETE",
           			dataType: 'html',
           			success: function() {
           				fileTreeView.model.deleteFile(filename);
           				$(self).parent().remove();
               		}
        		});
        		fileTreeView.model.deleteFile(filename);
        	})
        });
        
    });
    
		</script>

  <div class="container">

    <div id="alerts"></div>
    <div class="row" id='toolbar'>
      <label>Resource Type:</label>
	  <div class="btn-group" data-toggle="buttons">
		  <button class="btn active" id="publicResBtn">
		    <input type="radio" name="resType" id="resPublic"> Public
		  </button>
		  <button class="btn" id="privateResBtn">
		    <input type="radio" name="resType" id="resPrivate"> Private
		  </button>
	  </div>
      <form id="zipUploadForm" action="upload" method="POST" enctype="multipart/form-data">
        <input id="zipFile" type="file" name="file" style="display: none" />
        <button id="uploadZip" class="btn" title="Uppload zip file"><span class="glyphicon glyphicon-upload"></span></button>
        <button id="newFile" class="btn" title="New file"><span class="glyphicon glyphicon-file"></span></button>
        <button id="deleteFile" class="btn" title="Delete file"><span class="glyphicon glyphicon-remove"></span></button>
      </form>

    </div>

    <div class="row" id="editor-container">
      <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" id="tree-container">
        <div class="tree-folder" id="fileTree">
        </div>
      </div>
      <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
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