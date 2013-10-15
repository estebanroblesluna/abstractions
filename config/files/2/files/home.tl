<html>
<head>
	<script src="{cdn}lib/jquery-2.0.3.min.js"></script>
    <script src="{cdn}lib/dust.js"></script>
    <script src="{cdn}templates/t.ctl"></script>
</head>
<script type="text/javascript">
	var json = {json|s};
    $(document).ready(function() {
        dust.render("templates_t", json, function(err, out) {
        $("body").append(out);
        });
    });
</script>
<body>
	
</body>
</html>