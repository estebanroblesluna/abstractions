<head>
	<script src="${staticResourcesUrl}js/codemirror.js"></script>
	<link rel="stylesheet" href="${staticResourcesUrl}css/codemirror.css">
	<link rel="stylesheet" href="${staticResourcesUrl}css/monokai.css">
	<script src="${staticResourcesUrl}js/codemirror/modes/groovy.js"></script>
	<style type="text/css">
		body {
			margin: 0;
		}
		.CodeMirror {
			height: 100%;
			width: 100%;
		}
	</style>
</head>
<body>


	<script>
		var myScript = parent.document.getElementById('scriptString').getAttribute("text");
		var myCodeMirror = CodeMirror(document.body, {
		  value: myScript,
		  mode:  "groovy",
		  theme: "monokai",
		  lineWrapping: true,
		  lineNumbers: true
		});
	</script>

</body>
</html>