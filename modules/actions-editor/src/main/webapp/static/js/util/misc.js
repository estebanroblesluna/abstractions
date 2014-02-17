function getUrlParameters(){
	var params = window.location.search.substr(1).split("&");
	var ret = {}
	for(i in params){
		kvp = params[i].split("=")
		ret[kvp[0]] = kvp[1];
	}
	return ret;
}