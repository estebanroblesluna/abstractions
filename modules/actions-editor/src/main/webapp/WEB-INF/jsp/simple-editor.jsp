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
			height: 94.3%;
		}

		.toolbar {
			overflow: hidden;
			background: #CFCFCF; /* Fallback */
			background: -moz-linear-gradient(top, #cfcfcf 0%, #a8a8a8 100%); /* Firefox */
			background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#cfcfcf), to(#a8a8a8)); /* Safari + Chrome */
			position: relative;
			overflow: hidden;
			font: 14px/26px 'lucinda grande', Helvetica, Arial;
			text-align: center;
			padding: 1px 0 0 68px;
		}
		.toolbar, .toolbar * {
			color: #111;
			text-shadow: 0 1px 0 rgba(255,255,255,0.4);
		}

		.nav {
			position: absolute;
			top: 6px;
			left: 12px;
		}
		.nav a {
			position: relative;
			overflow: hidden;
			display: block;
			float: left;
			width: 14px;
			margin-right: 7px;
			font: bold 14px/14px 'Helvetica', 'Arial';
			text-align: center;
			color: #FFF;

			-moz-box-shadow: 0 -1px 1px 0 rgba(0, 0, 0, 0.3) inset, 0 1px 3px 0 rgba(0, 0, 0, 0.80) inset, 0 1px 0px 0 rgba(255, 255, 255, 0.40); /* Firefox */
			-webkit-box-shadow: 0 -1px 1px 0 rgba(0, 0, 0, 0.3) inset, 0 1px 3px 0 rgba(0, 0, 0, 0.80) inset, 0 1px 0px 0 rgba(255, 255, 255, 0.40); /* Safari + Chrome */
			box-shadow: 0 -1px 1px 0 rgba(0, 0, 0, 0.3) inset, 0 1px 3px 0 rgba(0, 0, 0, 0.80) inset, 0 1px 0px 0 rgba(255, 255, 255, 0.40);

			-moz-border-radius: 7px;
			-webkit-border-radius: 7px;
			border-radius: 7px;

		}
		.nav a.close {
			background: #f12519;
			background: -moz-linear-gradient(top, #f12519 0%, #ff8684 100%); /* Firefox */
			background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#f12519), to(#ff8684)); /* Safari + Chrome */

			color: #630f0a;
		}
		.nav a.minimize {
			background: #da8e28;
			background: -moz-linear-gradient(top, #e59130 0%, #ffdf4b 100%); /* Firefox */
			background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#e59130), to(#ffdf4b)); /* Safari + Chrome */
			color: #742a08;
		}
		.nav a.expand {
			background: #67982f;
			background: -moz-linear-gradient(top, #70a847 0%, #a1e268 100%);
			background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#70a847), to(#a1e268)); /* Safari + Chrome */
			color: #093b00;
		}

		.nav a:before {
			content: '';
			width: 6px;
			height: 3px;
			background: -moz-radial-gradient(50% 50% 90deg,ellipse contain, rgba(255,255,255,1), rgba(255,255,255,0) 100%); /* Firefox */
			position: absolute;
			left: 50%;
			top: 1px;
			margin-left: -3px;
		}

		.nav a:after {
			content: '';
			line-height: 2px;
			width: 12px;
			height: 12px;
			position: absolute;
			left: 50%;
			margin-left: -6px;
			bottom: -4px;
			opacity:0.55;
			background: -moz-radial-gradient(50% 50% 90deg,ellipse contain, rgba(255,255,255,1), rgba(255,255,255,0) 100%); /* Firefox */
		}

		.nav:hover a.close:after {
			content: 'x';
		}
		.nav:hover a.minimize:after {
			content: '-';
		}
		.nav:hover a.expand:after {
			content: '+';
		}

		a.close:active {
			background: #c84840;
		}
		a.minimize:active {
			background: #bb7e39;
		}
		a.expand:active {
			background: #5b9d2a;
		}
		#save {
			position: absolute;
			top: 0;
			right: 12;
			font-weight: bold;
			text-decoration: none;
		}
	</style>
</head>
<body>
	<div class="toolbar">
		<div class="nav">
			<a class="close" href="javascript: close() ">&nbsp;</a>
			<!--<a class="minimize" href="#">&nbsp;</a>
			<a class="expand" href="#">&nbsp;</a> -->
		</div>
			Groovy Script Editor</a>
		<div id='save'>
			<a href="javascript: save()"> Save </a>
		</div>
	</div>

	<script>
		var myScript = parent.document.getElementById('scriptString').getAttribute("text");
		var myCodeMirror = CodeMirror(document.body, {
		  value: myScript,
		  mode:  "groovy",
		  theme: "monokai",
		  lineWrapping: true,
		  lineNumbers: true
		});

		function close() {
			var parentIframe = parent.document.getElementById('simple-editor-iframe');
			parentIframe.parentNode.removeChild(parentIframe);
		}

		function save() {
			var oldScriptDiv = parent.document.getElementById("newScriptString");
			if ( oldScriptDiv != null)
				oldScriptDiv.parentNode.removeChild(oldScriptDiv);

			var newScriptString = myCodeMirror.getValue();
			newScriptStringDiv = document.createElement("div");
		    newScriptStringDiv.style.display = "none";
		    newScriptStringDiv.setAttribute("id", "newScriptString");
		    newScriptStringDiv.setAttribute("text", newScriptString);
		    parent.document.body.appendChild(newScriptStringDiv);

		    parent.saveScript();
		}
	</script>

</body>
</html>