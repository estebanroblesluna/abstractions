function FileEditor(container, width, height, toolbar){
	//Helper functions and vars
	var extensionModeMappings = {
    	js: "javascript",
    	xml: "xml",
    	html: "htmlmixed",
    	htm: "htmlmixed",
    	css: "css",
    	tl: "dust"
	};
	
	//FIXME: files without extension
	function extractExtension(filename) {
    	var parts = filename.split(".");
      return parts[parts.length - 1];
    }
	
	//Local data
	var editingFile = null;
	var saveFileHook = nop;
	var originalContent = null;
	
	//DOM structure
	var title = $("<h2 class='filename'></h2>");
	container.append(title);
	if(!toolbar){
		toolbar = $("<div></div>");
		container.append(toolbar);
	}else{
		toolbar = $(toolbar);
	}
	var toolbarSection = $("<span class='editor-buttons'></span>");
	toolbar.append(toolbarSection);
	
	var innerDiv = $("<div></div>");
	container.append(innerDiv);
	var editor = CodeMirror(innerDiv[0], {
        value: "",
        mode:  "dust",
        lineNumbers: true
    });  
	editor.setSize(width, height);
	
	//Toolbar buttons
	var saveButton = $('<button class="btn" id="saveButton" title="Save file"><span class="glyphicon glyphicon-floppy-disk"></span> </button>');
    toolbarSection.append(saveButton);
    saveButton.prop("disabled",true);
    $(saveButton).click(partial(function(editor,ev){
    	ev.preventDefault();
    	editor.save();
    },this));
   
    
	
	//Methods
    this.setFile = function (filename, content) {
        editor.setValue(content);
        originalContent = editor.getValue();
        this.updateMode(filename);
        title.text(filename);
        editingFile = filename;
        $("#editorContainer",innerDiv).fadeIn(500);
    }
    
    this.updateMode = function(filename) {
    	var extension = extractExtension(filename);
    	if (extensionModeMappings[extension]) {
    		editor.setOption("mode", extensionModeMappings[extension]);
    	}
    }
    
    this.setSaveFileHook = function(func){
    	saveFileHook = func;
    }
    
    this.isModified = function(){
    	return (originalContent != null) && (originalContent != editor.getValue());
    }
    
    this.save = function(){
    	originalContent = editor.getValue();
    	saveFileHook(editingFile,editor.getValue());
    }
    
}