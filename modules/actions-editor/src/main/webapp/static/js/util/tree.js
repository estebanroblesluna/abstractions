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






