var Folder = function(name){
	
	this.__proto__= new Tree()
	this.name = name || "";
	this.newFolderHook = nop;
	this.newFileHook = nop;
	this.deleteFileHook = nop;
	this.renderOn = null;
	this.selected = false;
	
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
				fileNode = new File(file,this.clickOnFile,this.deleteFileHook,this.dblClickFileHook);
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
	
	this.deleteFile = function(filename){
		var path = filename.split("/");
		if(path.length == 1){
			for(i in this.leaves)
				if(this.leaves[i].name == path[0]){
					this.leaves[i].deleteFileHook();
					this.leaves.splice(i,1);
				}
		} else {
			path.splice(0,1);
			this.deleteFile(path.join("/"));
		}
		
			
	}
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}
	
	this.cleanSelected = function(){
		$(this.children).each(function(){
			this.cleanSelected();
		})
		$(this.leaves).each(function(){
			this.cleanSelected();
		})
	}
	
	this.getSelected = function(){
		var ret = [];
		$(this.children).each(function(){
			ret.concat(this.getSelected());
			if(this.selected)
				ret.append(this);
		})
		$(this.leaves).each(function(){
			if(this.selected)
				ret.append(this);
		})
	}
}


var File = function(name,clickOnFile,deleteFileHook,dblClickOnFile){
	
	this.__proto__ = new Leaf();
	this.name = name || "";
	this.clickOnFile = clickOnFile || nop;
	this.dblClickOnFile = dblClickOnFile || nop;
	this.deleteFileHook = deleteFileHook || nop;
	this.selected = false;
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath() + "/" + this.name; 
	}

	this.cleanTreeSelection = function(){
		var root = this.getRoot();
		root.cleanSelected();
	}
	
	this.cleanSelected = function(){
		this.selected = false;
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
		var res = $("<li class='tree-folder closed-folder'>")
		var bullet = $("<span class='glyphicon glyphicon-folder-close'></span>");
		res.append(bullet);
		bullet.click(swapCollapseStatus)
		res.append("<span>"+model.name+"<span/>")
		res.attr("path",model.getPath())
		domNode.append(res);
		newView = new FolderView(res,model);
		newView.fileSelectedHook = parentView.fileSelectedHook;
		newView.fileOpenHook = parentView.fileOpenHook;
		parentView.subViews.push(newView);
	}
	
	function fileAdded(domNode,parentView,model){
		var res;
		res = $("<li class='file'>");

		var bullet = $("<span class='glyphicon glyphicon-file'></span>");
		res.append(bullet);
		var link = $("<a>"+model.name+"</a>")
		res.append(link);
		res.click(partial(parentView.fileSelectedHook,model));
		res.dblclick(partial(parentView.fileOpenHook,model));
		res.attr("path",model.getPath());
		domNode.append(res);
	}
	
	function fileRemoved(view){
		filename = this.getPath();
		$("li [path='"+filename+"']",view.domRoot).remove();
	}

	
	this.domRoot = domRoot;
	this.subViews = [];
	this.model = model || new Folder();
	this.fileSelectedHook = nop;
	this.fileOpenHook = nop;
	
	var folderList = $("<ul class='folder-list'></ul>");
	var fileList = $("<ul class='file-list'></ul>");
	domRoot.append(folderList);
	domRoot.append(fileList);
	
	this.model.newFolderHook = partial(folderAdded,folderList,this);
	this.model.newFileHook = partial(fileAdded,fileList,this);
}
