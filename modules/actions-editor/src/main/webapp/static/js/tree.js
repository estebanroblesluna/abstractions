var Tree = function(){
	this.children = [];
	this.leaves = []
	this.addSubTree = function(child){
		child.parent = this;
		this.children.push(child);
	}
	this.addLeaf = function(leaf){
		leaf.parent = this;
		this.leaves.push(leaf);
	}
	this.parent = undefined;
}

var Leaf = function(){
	this.parent = undefined;
}

var Folder = function(name){
	this.name = name || "";
	
	this.getSubFolder = function(name){
		for(var i=0; i<this.children.length; i++){
			if(this.children[i].name == name)
				return this.children[i];
		}
		return undefined;
	}
	
	this.getFile = function(filename){
		for(var i=0; i<this.length; i++){
			if(this.leaves[i].name == name)
				return this.leaves[i];
		}
		return undefined;
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
		}
		if(other == "")
			return childNode;
		else return childNode.createSubFolder(other);
	}
	
	this.addFile = function(filename, content){
		if(filename[0] == "/")
			filename = filename.splice(1);
		var path = filename.split("/");
		var file = path.splice(-1);
		path = path.join("/");
		if(path == "")
			folder = this;
		else
			var folder = this.createFolder(path)
		var fileNode = folder.getFile(file);
		if(!fileNode){
			fileNode = new File(file)
			folder.addLeaf(fileNode);
		}
		fileNode.content = content;
	}
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath + "/" + this.name; 
	}
}

Folder.prototype = new Tree();


var File = function(name,content){
	this.name = name || "";
	this.content = content || "";
	
	this.getPath = function(){
		if(!this.parent)
			return this.name;
		else 
			return this.parent.getPath + "/" + this.name; 
	}
}

File.prototype = new Leaf();



