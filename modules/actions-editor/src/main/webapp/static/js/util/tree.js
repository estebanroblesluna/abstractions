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






