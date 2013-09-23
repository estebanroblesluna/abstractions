<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dust Template Editor</title>
<script src="${staticResourcesUrl}js/codemirror.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/javascript.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/dust.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/xml.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/css.js"></script>
<script src="${staticResourcesUrl}js/codemirror/modes/htmlmixed.js"></script>
<script src="${staticResourcesUrl}js/jquery.min.js"></script>
<script src="${staticResourcesUrl}js/bootstrap.min.js"></script>
<script src="${staticResourcesUrl}js/alert.js"></script>
<link rel="stylesheet" href="${staticResourcesUrl}css/codemirror.css">
<link rel="stylesheet" href="${staticResourcesUrl}css/bootstrap.min.css">
<style>
	#alerts {
		height: 63px;
	}
</style>
<script type="text/javascript">


		var TemplateStore = function() {}
		var templateEditor;

		$(document).ready(function() {
			templateEditor = CodeMirror($("#editor")[0], {
			  value: "Hello world, {name}",
			  mode:  "dust",
			  lineNumbers: true
			});
			templateEditor.setSize(700, 500);
			
			var templatesById = {};
			var nextTemporaryTemplateId = 0;
			
			function getCurrentTemplate() {
				return templatesById[$("#templates").val()];
			}
			
			function showMessage(message) {
				$("#alerts .alert").remove();
				var alert = $('<div class="alert alert-warning">' + 
					message +
					'<a  class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a></div>');
				$("#alerts").append(alert);
				alert.hide().fadeIn(500);
				setTimeout(function() {alert.fadeOut(500)}, 3000);
			}
			
			$.ajax({
			  url: "${serviceBase}templates/",
			  type: "GET",
			  success: function(response) {
					templatesById = {};
					$.each(response.templates, function(i, template) {
						templatesById[template.id] = template;
					});
					renderTemplates(templatesById);
					$("#templates").trigger('change');
			  },
			  dataType: "json"
			});
			
			function renderTemplates(templatesById, selectedId) {
				if (Object.keys(templatesById) == 0) {
					var id = nextTemporaryTemplateId++;
					templatesById[id] = {id: id, name: 'template1', content:'Hello, {name}', isNew: true};
					selectedId = id;
				}
				$("#templates option").remove();
				$.each(templatesById, function(i, template) {
					$("#templates").append('<option value="[value]">[name]</option>'
							.replace("[value]", template.id)
							.replace("[name]", template.name + (template.isNew ? "*" : "")));		
				});
				if (selectedId) {
					$("#templates").val(selectedId);
				}
				$("#templates").trigger('change');
			}
			
			$("#templates").change(function() {
				if ($("#templates").val() != null) {
					templateEditor.setValue(getCurrentTemplate().content);
				}
			});
			
			$("#saveTemplate").click(function() {
				var originalTemplate = getCurrentTemplate();
				if (originalTemplate.isNew) {
					$.ajax({
					  url: "${serviceBase}templates/" + originalTemplate.name,
					  type: "POST",
					  data: "templateName=[name]&templateContent=[content]"
					  	.replace("[name]", originalTemplate.name)
					  	.replace("[content]", originalTemplate.content),
					  success: function(newTemplate) {
						  delete templatesById[originalTemplate.id];
						  templatesById[newTemplate.id] = newTemplate;
						  renderTemplates(templatesById, newTemplate.id);
						  showMessage("Template saved");
					  },
					  dataType: "json"
					});
				} else {
					$.ajax({
						  url: "${serviceBase}templates/" + originalTemplate.id,
						  type: "PUT",
						  data: "templateName=[name]&templateContent=[content]"
						  	.replace("[name]", originalTemplate.name)
						  	.replace("[content]", originalTemplate.content),
						  success: function(newTemplate) {
							  showMessage("Template saved");
						  },
						  dataType: "json"
						});
				}
			});
			
			$("#newTemplate").click(function() {
				var templateName = prompt("Enter template name:");
				if (!templateName) {
					return;
				}
				var newTemplate = {name: templateName, id: nextTemporaryTemplateId++, content: "", isNew: true}
				templatesById[newTemplate.id] = newTemplate;
				renderTemplates(templatesById, newTemplate.id);
			});
			
			$("#deleteTemplate").click(function() {
				var template = getCurrentTemplate();
				if (template.isNew) {
					delete templatesById[template.id];
					renderTemplates(templatesById);
				} else {
					$.ajax({
					  url: "${serviceBase}templates/" + template.id,
					  type: "DELETE",
					  success: function(newTemplate) {
						  delete templatesById[template.id];
						  renderTemplates(templatesById);
						  showMessage("Template deleted");
					  }
					});
				}
			})
			
			$("#renameTemplate").click(function() {
				template = getCurrentTemplate();
				var newTemplateName = prompt("Enter new template name:", template.name);
				if (!newTemplateName) {
					return;
				}
				template.name = newTemplateName;
				if (template.isNew) {
					renderTemplates(templatesById, template.id);
					return;
				}
				$.ajax({
					  url: "${serviceBase}templates/" + template.id,
					  type: "PUT",
					  data: "templateName=[name]"
					  	.replace("[name]", template.name),
					  success: function() {
						  showMessage("Template renamed");
						  renderTemplates(templatesById, template.id);
					  },
					  dataType: "json"
					});
			})
			
		})
		
	</script>
</head>