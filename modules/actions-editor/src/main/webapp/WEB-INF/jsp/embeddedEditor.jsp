<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
	<script type="text/javascript">
		var editor;
		
		function saveFile(filename,content){
		  $.ajax({
		  	url: "/${file}",
		  	type: "PUT",
		  	data: content,
		  	success: function(response) {
		    		;
		   	}
		  });
		}	
		
		$(function(){
			editor = new FileEditor($("#editor"),500,700);
			editor.setSaveFileHook(saveFile)
			
			$.ajax({
	  			url: "/${file}",
	  			type: "GET",
	  			success: function(response) {
	            	editor.setFile("${file}", response);
	        	}
	  		});
			
		})
	</script>
	<div id="editor"></div>
</body>
</html>