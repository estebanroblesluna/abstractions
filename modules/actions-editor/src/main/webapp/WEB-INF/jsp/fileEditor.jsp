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
	        <button id="saveChangesBtn" type="button" class="btn btn-primary"  data-dismiss="modal">Save</button>
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
	var editor = null;
	var newFile = false;
	var currentResType = "public";
	var openResType;
	var openFileId
	var currentFile;
	var openFiles = {}
	
	//File tree vars
	var fileTreeView;
  
	//Tabs
    function autoCollapse() {
  	  var tabs = $('#tabs');
	  var tabsHeight = tabs.innerHeight();
	  $("#lastTab").show();
	  if (tabsHeight >= 50) {
	    while(tabsHeight > 50) {
	      //console.log("new"+tabsHeight);
	      
	      var children = tabs.children('li:not(:last-child)');
	      var count = children.size();
	      $(children[count-1]).prependTo('#collapsed');
	      
	      tabsHeight = tabs.innerHeight();
	    }
	  }
	  else {
	  	while(tabsHeight < 50 && ($('#collapsed').children('li').size()>0)) {
	  	  var collapsed = $('#collapsed').children('li');
	      var count = collapsed.size();
	      $(collapsed[0]).insertBefore(tabs.children('li:last-child'));
	      tabsHeight = tabs.innerHeight();
	    }
	  	if(tabsHeight > 50)
	  		tabs.children('li:not(:last-child)').last().prependTo('#collapsed');
	    if ($('#collapsed').children('li').size() == 0)
	    	$("#lastTab").hide();
	    else
	    	$("#lastTab").show();
	  }  
	}
    
	
	//File tree functions
	
	function selectionChanged(){
		$("#deleteFile").prop("disabled",(fileTreeView.model.getSelectedFiles().length == 0 && fileTreeView.model.getSelectedFolders().length == 0));
	}
	
	var nextId = 0;
	function genTabId(){
		var ret = "filetab_"+nextId
		nextId += 1
		return ret;
	}
	
	function genFileId(filename,resType){
		var tabId = resType+filename;
		return tabId;
	}
	
	function closeTab(fileId){
		if(openFileId == fileId){
			editor = null;
			$("#saveButton").prop("disabled",true);
		}
		openFiles[fileId].tab.remove()
		openFiles[fileId].tabPane.remove();
		delete openFiles[fileId];
	}
	
	function tryToCloseTab(filename,resType){
		var fileId = genFileId(filename,resType)
		if(openFiles[fileId].editor.isModified()){
			$("#notSaveChangesBtn").unbind().click(function(){
				closeTab(fileId);
			})
			$("#saveChangesBtn").unbind().click(function(){
				saveFile(filename,resType,openFiles[fileId].editor.getContent())
				closeTab(fileId);
			})
			$("#saveChanges").modal("show")
		}
		else {
			closeTab(fileId)
		}
		
		
	}
	
	function createTab(filename,resType){
		var file = $(filename.split("/"));
		file = file[file.length-1];
		var icon
		if(resType == "public")
			icon = '<span class="glyphicon glyphicon-plus-sign"></span>'
		else
			icon = '<span class="glyphicon glyphicon-minus-sign"></span>'	
		var closeButton = $('<button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>')
		closeButton.click(function(e){
			e.stopPropagation();
			tryToCloseTab(filename,resType);
		});
		var tabId = genTabId();
		tab = $("<li><a class='fileTab' file='"+filename+"' restype='"+resType+"' role='tab' data-toggle='tab' href='#"+tabId+"'>"+icon+file+"</a></li>")
		$("a",tab).append(closeButton)
		tabPane = $("<div class='tab-pane' id='"+tabId+"'></div>")
		$("#tabs").prepend(tab);
		autoCollapse();
		$("#tabContents").append(tabPane);
		$("a",tab).tab("show")
		var editorl = new FileEditor(tabPane,700,500)
		var fileId = genFileId(filename,resType);
		openFiles[fileId] = {}
		openFiles[fileId].editor = editorl
		openFiles[fileId].resType = resType
		openFiles[fileId].tab = $("a",tab)
		openFiles[fileId].tabPane = tabPane
		openResType = resType
		editor = editorl
		autoCollapse();
	}
	
	
	function openFile(filename,resType){
  		var fileId = genFileId(filename,resType);
  		if(fileId in openFiles){
  			editor = openFiles[fileId].editor;
  			openResType = openFiles[fileId].resType;
  			openFiles[fileId].tab.tab("show");
  			openFileId = fileId
  		}
  		else{
  			createTab(filename,resType);
  			openResType = openFiles[fileId].resType;
  			openFileId = fileId;
			$.ajax({
	  			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ resType + filename,
	  			type: "GET",
	  			dataType: "text",
	  			success: function(response) {
	            	editor.setFile(filename, response);
	        	}
	  		});
  		}
		
  		$("#saveButton").prop("disabled",false);
  	}
  	
	function changeOpenFile(file){
		openFile(file.getPath(),currentResType);
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
    	editor.markAsSaved()
    } 
	
    function saveFile(filename,resType,content){
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
   			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ currentResType +"/" + $(self).text(),
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
    	
    	$('html').on("click",".fileTab",function(e){
    		var filename = $(e.target).attr("file");
    		var resType = $(e.target).attr("resType");
    		openFile(filename,resType);
    		
    	})
    	
    	$("#lastTab").on("shown.bs.tab",function(e){
    		li=$(e.target).parent()
    		$("#tabs").prepend(li);
    		$("#saveButton").prop("disabled",false)
    		autoCollapse();
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
	            createTab(filename,currentResType);
	            editor.setFile(filename,"");
	            newFile = true;
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
	    	    	url: "${fileStorageServiceBaseUrl}" + applicationId + "/folders/"+ currentResType + folder.getPath(),
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
           			url: "${fileStorageServiceBaseUrl}" + applicationId + "/folders/"+ currentResType + folderName,
           			type: "DELETE",
           			dataType: 'html'
        		});
    		}
    		
    		//delete remaining files
        	$(fileTreeView.model.getSelectedFiles()).each(function(){
        		var filename = this.getPath();
        		var self = this;
        		$.ajax({
           			url: "${fileStorageServiceBaseUrl}" + applicationId + "/files/"+ currentResType + filename,
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
    	autoCollapse()
        
        $("#publicResBtn").click(function(e){
        	fileTreeView.model.cleanSelected();
        	currentResType="public";
        	$("#zipUploadForm").attr("action","public/upload")
        	fillTree();
        });
        
        $("#privateResBtn").click(function(e){
        	fileTreeView.model.cleanSelected();
        	currentResType="private";
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
        
        $("#saveButton").click(function (){
        	saveFile(editor.filename,openResType,editor.getContent());
        })
        
    });
    
		</script>

  <div class="container">

    <div id="alerts"></div>
    <div class="row" id='toolbar'>
      <label class="light">Resource Type:</label>
	  <div class="btn-group" data-toggle="buttons">
		  <button class="btn btn-primary active" id="publicResBtn">
		    <input type="radio" name="resType" id="resPublic"><span class="glyphicon glyphicon-plus-sign"></span> Public
		  </button>
		  <button class="btn btn-danger" id="privateResBtn">
		    <input type="radio" name="resType" id="resPrivate"><span class="glyphicon glyphicon-minus-sign"></span> Private
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
	  <button id="saveButton" enabled="false" class="btn" title="Save files."><span class="glyphicon glyphicon-floppy-disk"></span></button>

    </div>

    <div class="row" id="editor-container">
      <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3" id="tree-container">
        <div class="tree-folder" id="fileTree">
        </div>
      </div>
      <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
        <div class="row">
          <ul class="nav nav-tabs" id="tabs" role="tablist">
            <li id="lastTab">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              More <span class="caret"></span>
            </a>
            <ul class="dropdown-menu" id="collapsed">
              
            </ul>
          </li>
     	 </ul>
         <div class="tab-content" id="tabContents">
         
         </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>