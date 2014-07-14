function FileEditor(container, width, height){
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
	this.filename = null;
	this.originalContent = null;
	
	//DOM structure
	var title = $("<h2 class='filename'></h2>");
	container.append(title);

	
	var innerDiv = $("<div></div>");
	container.append(innerDiv);
	var editor = CodeMirror(innerDiv[0], {
        value: "",
        mode:  "dust",
        lineNumbers: true
    });  
	editor.setSize(width, height);
	
	//Methods
    this.setFile = function (filename, content) {
        editor.setValue(content);
        this.originalContent = editor.getValue();
        this.updateMode(filename);
        title.text(filename);
        this.filename = filename;
        $("#editorContainer",innerDiv).fadeIn(500);
    }
    
    this.updateMode = function(filename) {
    	var extension = extractExtension(filename);
    	if (extensionModeMappings[extension]) {
    		editor.setOption("mode", extensionModeMappings[extension]);
    	}
    }
    
    this.isModified = function(){
    	return (this.originalContent != null) && (this.originalContent != editor.getValue());
    }
    
    this.markAsSaved = function(){
    	this.originalContent = editor.getValue();
    }
    
}