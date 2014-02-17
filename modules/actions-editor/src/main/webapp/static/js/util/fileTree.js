var Folder = function(name,clickOnFile,clickOnFolder){
	
	this.__proto__= new Tree()
	this.name = name || "";
	this.newFolderHook = nop;
	this.newFileHook = nop;
	this.renderOn = null
	
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
	
	this.html = function(){
		var fileList = $("<ul>");
		$(this.leaves).each(function(index,elem){
			fileList.append(elem.html());
		});
		var folderList = $("<ul>");
		$(this.children).each(function(index,elem){
			folderList.append(elem.html())
		});
		var res;
		if(this.name == ""){
			res = $("<div>");
		} else {
			res = $("<li class='tree-folder open-folder'>");
			var bullet = $("<span class='glyphicon glyphicon-folder-open'></span>");
			res.append(bullet);
			bullet.click(swapCollapseStatus)
			res.append("<span>"+this.name+"<span/>")
			res.attr("path",this.getPath())
		}
		res.append(folderList);
		res.append(fileList);
		return res;
	}
	/*
	 * Returns the requested folder, creating it if it did not exist already.
	 */
	this.createFolder = function(name){
		if(name[0] == "/")
			name = name.splice(1);
		var path = name.split("/");
		var child = path[0];
		var other = path.splice(1).join("/");
		var childNode = this.getSubFolder(child);
		if(!childNode){
			childNode = new Folder(child);
			this.addSubTree(childNode);
			this.newFolderHook(childNode)
		}
		if(other == "")
			return childNode;
		else return childNode.createFolder(other);
	}
	
	this.addFile = function(filename){
		
		if(filename[0] == "/")
			filename = filename.splice(1);
		var path = filename.split("/");
		
		if(path.length == 1){
			var file = path[0];
			var fileNode = this.getFile(file);
			if(!fileNode){
				fileNode = new File(file,this.clickOnFile);
				this.addLeaf(fileNode);
				this.newFileHook(fileNode);
			}
		}
		
		else{
			var folder = this.createFolder(path[0]);
			path = path.slice(1);
			folder.addFile(path.join("/"));
		}
	}
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}
}


var File = function(name,clickOnFile){
	
	this.__proto__ = new Leaf()
	this.name = name || "";
	this.clickOnFile = clickOnFile || nop
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}
	
	this.html = function(){
		var res;
		res = $("<li>");
		var bullet = $("<span class='glyphicon glyphicon-file'></span>");
		res.append(bullet);
		var link = $("<a>"+this.name+"</a>")
		res.append(link);
		link.click(partial(this.clickOnFile,this));
		res.attr("path",this.getPath());
		return res;
	}
}

function FolderView(domRoot, model){
	
	function swapCollapseStatus(){
		p=$(this).parent();
		status = p.attr("class");
		if(status == "tree-folder open-folder") {
			p.attr("class","tree-folder closed-folder");
			$("> .glyphicon", p).attr("class","glyphicon glyphicon-folder-close");
			$("> ul",p).hide(500);
		} else {
			p.attr("class","tree-folder open-folder");
			$("> .glyphicon", p).attr("class","glyphicon glyphicon-folder-open");
			$("> ul",p).show(500);
		}
	}
	
	//Event handlers
	function folderAdded(domNode,parentView,model){
		var res = $("<li class='tree-folder open-folder'>")
		var bullet = $("<span class='glyphicon glyphicon-folder-open'></span>");
		res.append(bullet);
		bullet.click(swapCollapseStatus)
		res.append("<span>"+model.name+"<span/>")
		res.attr("path",model.getPath())
		domNode.append(res);
		newView = new FolderView(res,model);
		newView.fileSelectedHook = parentView.fileSelectedHook;
		parentView.subViews.push(newView);
	}
	
	function fileAdded(domNode,parentView,model){
		var res;
		res = $("<li>");
		var bullet = $("<span class='glyphicon glyphicon-file'></span>");
		res.append(bullet);
		var link = $("<a>"+model.name+"</a>")
		res.append(link);
		link.click(partial(parentView.fileSelectedHook,model));
		res.attr("path",model.getPath());
		domNode.append(res);
	}
	
	
	this.domRoot = domRoot;
	this.subViews = [];
	this.model = model || new Folder();
	this.fileSelectedHook = nop;
	
	var folderList = $("<ul class='folder-list'></ul>");
	var fileList = $("<ul class='file-list'></ul>");
	domRoot.append(folderList);
	domRoot.append(fileList);
	
	this.model.newFolderHook = partial(folderAdded,folderList,this);
	this.model.newFileHook = partial(fileAdded,fileList,this);
}
