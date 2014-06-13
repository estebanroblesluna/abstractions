var Tree = function(){
	this.children = [];
	this.leaves = []
	
	this.addSubTree = function(child){
		child.parent = this;
		this.children.push(child);
	}
	
	this.removeSubTree = function(child){
		for(i in this.children){
			if(child == this.children[i])
				this.children.splice(i,1);
		}
	}
	
	this.removeLeaf = function(leaf){
		for(i in this.leaves){
			if(leaf == this.leaves[i])
				this.leaves.splice(i,1);
		}
	}
	
	this.addLeaf = function(leaf){
		leaf.parent = this;
		this.leaves.push(leaf);
	}
	this.getRoot = function(){
		if(this.parent)
			return this.parent.getRoot();
		return this;
	}
	this.parent = null;
}

var Leaf = function(){
	this.parent = null;
	
	this.getRoot = function(){
		if(this.parent)
			return this.parent.getRoot();
		return this;
	}
}






