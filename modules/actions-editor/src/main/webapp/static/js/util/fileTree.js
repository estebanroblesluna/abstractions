var Folder = function(name){
	
	this.__proto__= new Tree()
	
	this.name = name || "";
	this._selected = false;
	
	this.__defineGetter__("selected",function(){
		return this._selected
	})
	
	this.__defineSetter__("selected",function(val){
		this._selected = val;
		this.folderSelectedHook.execute(this);
	})
	
	
	//Hooks
	this.newFolderHook = new MHook();			//function(newFolder)
	this.newFileHook =  new MHook();		 	//function(newFile)
	this.folderSelectedHook =  new MHook();  	//function(changedFolder)
	this.folderDeletedHook = new MHook();		//function(deletedFolder)
	
	this.getSubFolder = function(name){
		for(var i=0; i<this.children.length; i++){
			if(this.children[i].name == name)
				return this.children[i];
		}
		return undefined;
	}
	
	this.getFile = function(filename){
		for(i in this.leaves){
			if(this.leaves[i].name == filename)
				return this.leaves[i];
		}
		return undefined;
	}

	/*
	 * Returns the requested folder, creating it if it did not exist already.
	 */
	this.createFolder = function(name){
		if(name[0] == "/")
			name = name.substr(1);
		var path = name.split("/");
		var child = path[0];
		var other = path.splice(1).join("/");
		var childNode = this.getSubFolder(child);
		if(!childNode){
			childNode = new Folder(child);
			this.addSubTree(childNode);
			this.newFolderHook.execute(childNode)
		}
		if(other == "")
			return childNode;
		else return childNode.createFolder(other);
	}
	
	this.addFile = function(filename){
		
		if(filename[0] == "/")
			filename = filename.substr(1);
		var path = filename.split("/");
		
		if(path.length == 1){
			var file = path[0];
			if(file == ".")
				return;
			var fileNode = this.getFile(file);
			if(!fileNode){
				fileNode = new File(file,this.deleteFileHook);
				this.addLeaf(fileNode);
				this.newFileHook.execute(fileNode);
			}
		}
		
		else{
			var folder = this.createFolder(path[0]);
			path = path.slice(1);
			folder.addFile(path.join("/"));
		}
	}
	
	this.remove = function(){
		this.cleanSelected();
		this.parent.removeSubTree(this);
		this.folderDeletedHook.execute(this);
	}
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}
	
	this.cleanSelected = function(){
		if(this.selected)
			this.selected = false;
		this.cleanSubTreeSelection();
	}
	
	this.cleanSubTreeSelection = function(){
		$(this.children).each(function(){
			this.cleanSelected();
		})
		$(this.leaves).each(function(){
			this.cleanSelected();
		})
	}
	
	this.getSelectedFiles = function(){
		var ret = [];
		$(this.children).each(function(){
			ret = ret.concat(this.getSelectedFiles());
		})
		$(this.leaves).each(function(){
			if(this.selected)
				ret.push(this);
		})
		return ret;
	}
	
	this.getSelectedFolders = function(){
		var ret = [];
		if(this.selected)
			ret.push(this);
		$(this.children).each(function(){
			ret = ret.concat(this.getSelectedFolders());
		})
		return ret;
	}
	
	this.findFolder = function(path){
		if(path[0] == "/")
			path = path.substr(1);
		if(path == "")
			return this;
		var folders = path.split("/");
		var next = folders[0];
		folders.splice(0,1);
		for(i in this.children)
			if (this.children[i].name == next)
				return this.children[i].findFolder(folders.join("/"))
		return null;
	}
	
	
	this.findFile = function(path){
		if(path[0] == "/")
			path = path.substr(1);
		var folders = path.split("/");
		var file = folders[folders.length-1]
		folders.splice(folders.length-1,1);
		var folder = this.findFolder(folders.join("/"));
		if(folder != null){
			for(i in folder.leaves)
				if (folder.leaves[i].name == file)
					return folder.leaves[i]
		}
		return null;
	}
}


var File = function(name){
	
	this.__proto__ = new Leaf();
	this.name = name;
	this._selected = false;
	
	this.__defineSetter__("selected", function(val){
		this._selected = val;
		this.fileSelectedHook.execute(this);
	})
	
	this.__defineGetter__("selected", function(){
		return this._selected;
	})
	
	//Hooks
	this.fileSelectedHook = new MHook();		//function(changedFile)
	this.fileDeletedHook = new MHook();			//function(deletedFile)
	this.fileOpenHook = new MHook();			//function(openFile)
	
	//Methods
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}
	
	this.cleanSelected = function(){
		this.selected = false;
	}
	
	this.open = function(){
		this.fileOpenHook.execute(this);
	}
	
	this.remove = function(){
		this.selected = false;
		this.parent.removeLeaf(this);
		this.fileDeletedHook.execute(this);
	}
} 


//This creates an object to render a folder. 
//Params:
//	domRoot: where to display the tree
//	model: the folder to display
function FolderView(domRoot, model){	
	//Hook handlers
	

	function folderAdded(view,model){
		//TODO: should this go in the constructor?
		var res = $("<li class='tree-folder' expanded='false' >");
		var bullet = $("<span class='glyphicon glyphicon-folder-close'></span>");
		var folderTag = $("<span class='folderTag'></span>")
		folderTag.append(bullet);
		bullet.click(partial(swapCollapseStatus,model));
		folderTag.click(partial(folderClicked,model));
		folderTag.append("<span class='folder-name'>"+model.name+"<span/>");
		res.append(folderTag);
		res.attr("path",model.getPath());
		newView = new FolderView(res,model);
		//Add the new folder in the correct position.
		//TODO: binary search for the position to insert.
		var found = false;
		$(">li",view.folderList).each(function(){
			if($(this).attr("path") > res.attr("path")){
				$(this).before(res);
				found = true;
				return false;
			}
		})
		if(!found)
			view.folderList.append(res);


	}
	
	function fileAdded(view, model){
		
		var newView = new FileView(model);
		//Add the new file in the correct position.
		//TODO: binary search for the position to insert.
		var found = false;
		$(">li",newView.domRoot).each(function(){
			if($(this).attr("path") > res.attr("path")){
				$(this).before(newView.dom);
				found = true;
				return false;
			}
		})
		if(!found)
			view.fileList.append(newView.dom);
	}
	
	function folderSelected(view, model){
  		if(model.selected)
			view.domRoot.attr("class","tree-folder selectedFolder");
		else
			view.domRoot.attr("class","tree-folder");
	}
	
	function folderDeleted(view, model){
		view.domRoot.remove();
	}
  	
  	
  	//Events handlers
  	function folderClicked(model,e){
  		e.preventDefault();
  		if(!e.ctrlKey)
		{
			model.getRoot().cleanSelected();
			model.selected = true;
		} else {
			model.selected = !model.selected;
		}

  	}
	
	function swapCollapseStatus(model,e){
		e.preventDefault();
		e.stopPropagation();
		var p = $(this).parent();
		var gp=p.parent();
		status = gp.attr("expanded");
		if(status == "true") {
			gp.attr("expanded","false");
			$("> .glyphicon", p).attr("class","glyphicon glyphicon-folder-close");
			$("> ul",gp).hide(500);
			model.cleanSubTreeSelection();
		} else {
			gp.attr("expanded","true")
			$("> .glyphicon", p).attr("class","glyphicon glyphicon-folder-open");
			$("> ul",gp).show(500);
		}
	}
	
	//methods
	this.makeRoot = function(){
		$("> ul",this.domRoot).removeAttr("hidden");
	}
	
	this.selectedFiles = function(){
		return $(".selectedFile",this.domRoot);
	}
	
	this.selectedFolders = function(){
		return $(".selectedFolder",this.domRoot);
	}
	
	//Initialization
	this.domRoot = domRoot;
	this.subViews = [];
	this.model = model || new Folder();
	
	this.folderList = $("<ul class='folder-list' hidden></ul>");
	this.fileList = $("<ul class='file-list' hidden></ul>");
	domRoot.append(this.folderList);
	domRoot.append(this.fileList);
	
	this.model.newFolderHook.add(partial(folderAdded,this));
	this.model.newFileHook.add(partial(fileAdded,this));
	this.model.folderSelectedHook.add(partial(folderSelected,this));
	this.model.folderDeletedHook.add(partial(folderDeleted,this));
}


function FileView(model){
	
	//Hooks handlers
	function fileRemoved(view,model){
		view.dom.remove();
	}
	
  	function fileSelectionChanged(view,model){
		if(model.selected)
			view.dom.attr("class","file selectedFile");
		else
			view.dom.attr("class","file");
  	}
  	
  	//Event Handlers
  	function fileClicked(model, e){
  		e.preventDefault();
		if(!e.ctrlKey)
		{
			model.getRoot().cleanSelected();
			model.selected = true;
		} else {
			model.selected = !model.selected;;
		}
  	}
	
  	function fileDblClicked(model, e){
  		model.open();
  	}
  	
  	
	this.dom = $("<li class='file'>");
	this.model = model;
	
	var bullet = $("<span class='glyphicon glyphicon-file'></span>");
	this.dom.append(bullet);
	var link = $("<a>"+this.model.name+"</a>")
	this.dom.append(link);
	this.dom.attr("path",this.model.getPath());
	this.dom.click(partial(fileClicked,model));
	this.dom.dblclick(partial(fileDblClicked,model));
	
	this.model.fileSelectedHook.add(partial(fileSelectionChanged,this));
	this.model.fileDeletedHook.add(partial(fileRemoved,this));
}
