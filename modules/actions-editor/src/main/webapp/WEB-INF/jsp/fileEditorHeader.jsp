<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Editor</title>
<script src="${staticResourcesUrl}js/codemirror.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/javascript.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/dust.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/xml.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/css.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/htmlmixed.js"></script>
<script src="${staticResourcesUrl}js/jquery.min.js"></script>
<script src="${staticResourcesUrl}js/bootstrap.min.js"></script>
<script src="${staticResourcesUrl}js/alert.js"></script>
<script src="${staticResourcesUrl}js/tree.js"></script>
<link rel="stylesheet" href="${staticResourcesUrl}css/codemirror.css">
<link rel="stylesheet" href="${staticResourcesUrl}css/bootstrap.min.css">
<link rel="stylesheet" href="${staticResourcesUrl}css/editor.css">
<style>
	#alerts {
		height: 63px;
	}
  
  #fileList {
    list-style: none;
    margin: 0px;
    padding: 0px;
    height:  535px;
    overflow-y: scroll;
  }
</style>
<script type="text/javascript">
		
		function showMessage(message) {
      $("#alerts .alert").remove();
      var alert = $('<div class="alert alert-warning">' + 
        message +
        '<a  class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a></div>');
      $("#alerts").append(alert);
      alert.hide().fadeIn(500);
      setTimeout(function() {alert.fadeOut(500)}, 3000);
    }
		
	</script>
</head>