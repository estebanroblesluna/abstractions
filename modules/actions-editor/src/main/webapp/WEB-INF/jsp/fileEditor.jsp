<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
  <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
  
	<div class="modal fade" id="saveChanges">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title">The current file has changes</h4>
	      </div>
	      <div class="modal-body">
	        <p>Do you want to save changes?</p>
	      </div>
	      <div class="modal-footer">
	        <button id="notSaveChangesBtn" type="button" class="btn btn-default" data-dismiss="modal">Don't save</button>
	        <button id="saveChangesBtn" type="button" class="btn btn-primary" >Save</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
  <script type="text/javascript">
  
  	//Initialize popovers
  	$('.popover-markup>.trigger').popover({
	    html: true,
	    title: function () {
	        return $(this).parent().find('.head').html();
	    },
	    content: function () {
	        return $(this).parent().find('.content').html();
	    }
	});	
  
	var applicationId = ${applicationId};
	//var templateEditor;
	var editor;
	var currentFilename = null;
	var newFile = false;
	var resType = "public";
	var openResType = resType;
	
	//File tree vars
	var fileTreeView;
  
	//File tree functions
	
	function selectionChanged(){
		$("#deleteFile").prop("disabled",(fileTreeView.model.getSelectedFiles().length == 0 && fileTreeView.model.getSelectedFolders().length == 0));
	}
	
	
  	function openFile(file){
  		filename = file.getPath();
		$.ajax({
  			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
  			type: "GET",
  			success: function(response) {
            	editor.setFile(filename, response);
        	}
  		});
		openResType = resType;
  		$("#saveButton").prop("disabled",false);
  	}
  	
	function changeOpenFile(node, e){
        if(editor.isModified()){
        	$("#notSaveChangesBtn").unbind().click(partial(openFile,node));
        	$("#saveChangesBtn").unbind().click( function(){
        		editor.save();
        		openFile(node);
        		$("#saveChanges").modal("hide");
        	});
        	$("#saveChanges").modal("show");
		}
        else
        	openFile(node);
	}
	
    function addFileToTree(filename) {
    	fileTreeView.model.addFile(filename);
    }
	
	//Editor functions
    
    function fileSaved(filename) {
    	showMessage("File saved","success");
    	if (newFile) {
    		addFileToTree(filename);
    		newFile = false;
    	}
    } 
	
    function saveFile(filename,content){
    	if(filename[0] != "/")
    		filename = "/"+filename;
	    $.ajax({
	    	url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ openResType + filename,
	    	type: "PUT",
	    	data: content,
	    	success: function(response) {
	      		fileSaved(filename, content);
	     	}
	    });
    }
    
    function addFileHooks(file){
    	file.fileSelectedHook.add(selectionChanged);
    	file.fileOpenHook.add(changeOpenFile);
    }
    
    function addFolderHooks(folder){
    	folder.newFolderHook.add(addFolderHooks);
    	folder.newFileHook.add(addFileHooks);
    	folder.folderSelectedHook.add(selectionChanged);
    }
    
    function fillTree(){
    	$("#fileTree").empty();
    	fileTreeView = new FolderView($("#fileTree"));
    	fileTreeView.makeRoot();
    	fileTreeView.model.newFolderHook.add(addFolderHooks);
    	fileTreeView.model.newFileHook.add(addFileHooks);
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
    	
    	//Initialize pop-overs
    	$('.popover-markup').popover({
    	    html: true,
    	    title: function () {
    	        return $(this).find('.head').html();
    	    },
    	    content: function () {
    	        return $(this).find('.content').html();
    	    }
    	    
    	});
    	
    	$('html').on('click', function(e) {
    		  if (!$(e.target).parents().hasClass("popover-markup") &&
    		     !$(e.target).parents().is('.popover.in')) {
    			$(".trigger").parent().popover("hide");
    		    $('.popover').remove();
    		  }
    	});
    	
    	$('#newFileForm').on("shown.bs.popover", function(){
    		$(".popover .newFileName").focus();
    	});
    	
    	$('#newFolderForm').on("shown.bs.popover", function(){
    		$(".popover .newFolderName").focus();
    	});
    	
    	$(".popover-markup").on("show.bs.popover",function(e){
    		$(".trigger").parent().popover("hide");
		    $('.popover').remove();
    	})
    	
    	$(document.body).on("submit",".newFileForm", function(e) {
    		e.preventDefault();
    		var ok = true;
            var filename = $(".popover .newFileName").val();
            if (!filename) {
            	showMessage("Error: empty filename!","danger");
            	ok=false;
            }
            if(filename[filename.length-1] == "/")
            	filename = filename.substr(0,filename.length-1);
            if(fileTreeView.model.findFolder(filename)){
            	showMessage("That's the name of a folder!","danger");
            	ok=false;
            }
            if(fileTreeView.model.findFile(filename)){
            	showMessage("That file already exists!","danger");
            	ok=false;
            }
            if(ok){
	            editor.setFile(filename, "");
	            newFile = true;
	        	openResType = resType;
	      		$("#saveButton").prop("disabled",false);
            }
      		$("#newFileForm").popover("toggle");
         }
    	)
    	
    	$(document.body).on("submit",".newFolderForm",function(e) {
        	e.preventDefault();
    		var ok = true;
            var folder =  $(".popover .newFolderName").val();
            if (!folder) {
            	showMessage("Error: empty folder name!")
            	ok=false;
            }
            if(fileTreeView.model.findFolder(folder)){
            	showMessage("Folder already exists!","danger");
            	ok=false;
            }
            if(fileTreeView.model.findFile(folder)){
            	showMessage("That's the name of a file!","danger");
            	ok=false;
            }
            if(ok){
	            folder = fileTreeView.model.createFolder(folder);
	            $.ajax({
	    	    	url: "${fileStorageServiceBaseUrl}" + applicationId + "/folders/"+ openResType + folder.getPath(),
	    	    	type: "PUT"
	    	    });
            }
            $("#newFolderForm").popover("toggle");
        });
    	
    	$(document.body).on("click",".deleteFilesBtn",function(){
    		while(fileTreeView.model.getSelectedFolders().length){
    			var folder = fileTreeView.model.getSelectedFolders()[0];
    			var folderName = folder.getPath();
    			folder.remove();
        		$.ajax({
           			url: "${fileStorageServiceBaseUrl}" + applicationId + "/folders/"+ resType + folderName,
           			type: "DELETE",
           			dataType: 'html'
        		});
    		}
    		
    		//delete remaining files
        	$(fileTreeView.model.getSelectedFiles()).each(function(){
        		var filename = this.getPath();
        		var self = this;
        		$.ajax({
           			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
           			type: "DELETE",
           			dataType: 'html',
           			success: function() {
           				self.remove();
               		}
        		});
        	});
    		$("#deleteForm").popover("toggle");
    	});
    	
    	
    	

    	$("#deleteFile").prop("disabled",true);
    	
    	//Initialize the file tree
    	fillTree();
    	
    	
        editor = new FileEditor($("#editor"), 700, 500,$("#toolbar"));
        editor.setSaveFileHook(saveFile);
        
        $("#publicResBtn").click(function(e){
        	fileTreeView.model.cleanSelected();
        	resType="public";
        	$("#zipUploadForm").attr("action","public/upload")
        	fillTree();
        });
        
        $("#privateResBtn").click(function(e){
        	fileTreeView.model.cleanSelected();
        	resType="private";
        	$("#zipUploadForm").attr("action","private/upload")
        	fillTree();
        });
        
        //File tree toolbar
        $("#uploadZip").click(function(e) {
            e.preventDefault();  
            $("#zipFile").trigger("click");
        });  
        
        $("#zipFile").change(function(e) {
        	$("#zipUploadForm").submit()
        }); 
        
    });
    
		</script>

  <div class="container">

    <div id="alerts"></div>
    <div class="row" id='toolbar'>
      <label class="light">Resource Type:</label>
	  <div class="btn-group" data-toggle="buttons">
		  <button class="btn btn-primary active" id="publicResBtn">
		    <input type="radio" name="resType" id="resPublic"> Public
		  </button>
		  <button class="btn btn-danger" id="privateResBtn">
		    <input type="radio" name="resType" id="resPrivate"> Private
		  </button>
	  </div>
      <form id="zipUploadForm" action="public/upload" method="POST" enctype="multipart/form-data">
        <input id="zipFile" type="file" name="file" style="display: none" />
        <button id="uploadZip" class="btn" title="Uppload zip file"><span class="glyphicon glyphicon-upload"></span></button>
      </form>
      
      <span id="newFileForm" class="popover-markup">
      	<button class="btn trigger" title="New file">
      		<span class="glyphicon glyphicon-file"></span>
      	</button>
      	<div class="content hidden">
      		<form class="form-inline newFileForm">
      			<div class="form-group">
      				<input  type="text" class="form-control newFileName" placeholder="filename..."></input>
      			</div>
				<div class="form-group">
   					<button type="submit" class="btn btn-primary newFile"><span class="glyphicon glyphicon-ok"></span></button>
   				</div>
      		</form>
      	</div>
      </span>
      
      <span id="newFolderForm" class="popover-markup">
      	<button class="btn trigger" title="New folder">
      		<span class="glyphicon glyphicon-folder-close"></span>
      	</button>
      	<div class="content hidden">
      		<form class="form-inline newFolderForm">
      			<div class="form-group">
      				<input  type="text" class="form-control newFolderName" placeholder="folder name..."></input>
      			</div>
				<div class="form-group">
   					<button type="submit" class="btn btn-primary newFolder"><span class="glyphicon glyphicon-ok"></span></button>
   				</div>
      		</form>
      	</div>
      </span>
      <span id="deleteForm" class="popover-markup">
     	<button id="deleteFile" enabled="false" class="btn trigger" title="Delete selected files?"><span class="glyphicon glyphicon-remove"></span></button>
     	<div class="head hidden"><span>Are you sure?</span></div>
     	<div class="content hidden">
   			<button type="submit" class="btn btn-danger btn-sm deleteFilesBtn"><span class="glyphicon glyphicon-ok"></span> Delete</button>
   			<button class="btn btn-sm btn-default"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
      	</div>
	  </span>
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