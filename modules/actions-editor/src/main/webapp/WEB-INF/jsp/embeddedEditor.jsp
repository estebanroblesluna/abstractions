<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<jsp:include page="/WEB-INF/jsp/fileEditorHeader.jsp" />
<body>
	<script type="text/javascript">
		var editor;
		$(function(){
			editor = new FileEditor($("#editor"),500,700);
		})
	</script>
	<div id="editor"></div>
</body>
</html>