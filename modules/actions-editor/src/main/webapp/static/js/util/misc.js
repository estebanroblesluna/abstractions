function getUrlParameters(){
	var params = window.location.search.substr(1).split("&");
	var ret = {}
	for(i in params){
		kvp = params[i].split("=")
		ret[kvp[0]] = kvp[1];
	}
	return ret;
}


//Multiple Hook. This object allows for many functions to be attached  to it an called when 
//an event occurs. The call order is not documented and may change.

function MHook(){
	this.hooks = []
	
	//methods
	this.add = function(hook){
		this.hooks.push(hook)
	}
	
	this.rem = function(hook){
		for(i in this.hooks)
			if(this.hooks[i] == hook){
				this.hooks.splice(i,1);
				break;
			}
	}
	
	this.execute = function(/*, 0..n args */){
		for(i in this.hooks){
			this.hooks[i].apply(this,arguments);
		}
	}
}


//Link a button enable state to the selection of a group of checkboxes
function linkToSelection(button,checkboxClass){
	$(document.body).on("change",checkboxClass,function(){
		button.prop("disabled",$(checkboxClass+":checked").length ==0);
	});
	button.prop("disabled",$(checkboxClass+":checked").length ==0);
}