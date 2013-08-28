package com.core.meta;

public class Meta {

	public static Library getCommonLibrary() {
		Library library = new Library("common");
		library.setDisplayName("Common");
		
		library.addDefinition(createGroovyProcessor());
		library.addDefinition(createNextInChainConnection());
		library.addDefinition(createChoiceRouter());
		library.addDefinition(createChoiceConnection());
		library.addDefinition(createAllRouter());
		library.addDefinition(createAllConnection());
		library.addDefinition(createWireTapRouter());
		library.addDefinition(createWireTapConnection());

		return library;
	}
	
	public static Library getModulesLibrary() {
		Library library = new Library("modules");
		library.setDisplayName("Modules");
		
		library.addDefinition(createHttpFetcherProcessor());
		library.addDefinition(createHttpMessageSource());
		library.addDefinition(createFileReaderProcessor());
		library.addDefinition(createDustRendererProcessor());
		library.addDefinition(createLogProcessor());

		return library;
	}

	private static ElementDefinition createChoiceRouter() {
		RouterDefinition definition = new RouterDefinition("CHOICE");
		definition.setDisplayName("Choice router");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.core.routing.ChoiceRouter");
		
		definition.addProperty(createNameProperty("New choice router"));
		definition.setRouterEvaluatorImplementation("com.core.interpreter.ChoiceRouterEvaluator");
		definition.setRouterEvaluatorScript(false);
		
		return definition;
	}

	private static ElementDefinition createAllRouter() {
		RouterDefinition definition = new RouterDefinition("ALL");
		definition.setDisplayName("All router");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.core.routing.AllRouter");
		
		definition.addProperty(createNameProperty("New all router"));
		definition.setRouterEvaluatorImplementation("com.core.interpreter.AllRouterEvaluator");
		definition.setRouterEvaluatorScript(false);

		return definition;
	}

	private static ElementDefinition createWireTapRouter() {
		RouterDefinition definition = new RouterDefinition("WIRE_TAP");
		definition.setDisplayName("Wire tap router");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.core.routing.WireTapRouter");
		
		definition.addProperty(createNameProperty("New wire tap router"));
		definition.setRouterEvaluatorImplementation("com.core.interpreter.WireTapRouterEvaluator");
		definition.setRouterEvaluatorScript(false);

		return definition;
	}

	private static ElementDefinition createNextInChainConnection() {
		ConnectionDefinition definition = new ConnectionDefinition("NEXT_IN_CHAIN_CONNECTION");
		definition.setDisplayName("Next in chain connection");
		definition.setIcon("Resources/nextInChainConnection.png");
		definition.setClassName("com.core.impl.NextInChainConnection");

		definition.setColor("777777");
		
		definition.setAcceptedSourceMax(1);
		definition.setAcceptedTargetMax(Integer.MAX_VALUE);
		definition.setAcceptedSourceTypes("*");
		definition.setAcceptedTargetTypes("*");

		definition.addProperty(createNameProperty("New next in chain connection"));

		return definition;
	}

	private static ElementDefinition createChoiceConnection() {
		ConnectionDefinition definition = new ConnectionDefinition("CHOICE_CONNECTION");
		definition.setDisplayName("Choice connection");
		definition.setIcon("Resources/choiceConnection.png");
		definition.setClassName("com.core.impl.ChoiceConnection");

		definition.setColor("0000BB");
		
		definition.setAcceptedSourceMax(Integer.MAX_VALUE);
		definition.setAcceptedTargetMax(Integer.MAX_VALUE);
		definition.setAcceptedSourceTypes("CHOICE");
		definition.setAcceptedTargetTypes("*");

		definition.addProperty(createNameProperty("New choice connection"));

		PropertyDefinition expressionProperty = new PropertyDefinition("expression");
		expressionProperty.setDisplayName("Expression");
		expressionProperty.setType(PropertyType.EXPRESSION);
		expressionProperty.setDefaultValue("expression:groovy:message.properties['name'] == 'A'");
		definition.addProperty(expressionProperty);

		return definition;
	}

	private static ElementDefinition createAllConnection() {
		ConnectionDefinition definition = new ConnectionDefinition("ALL_CONNECTION");
		definition.setDisplayName("All connection");
		definition.setIcon("Resources/allConnection.png");
		definition.setClassName("com.core.impl.AllConnection");

		definition.setColor("00BB00");
		
		definition.setAcceptedSourceMax(Integer.MAX_VALUE);
		definition.setAcceptedTargetMax(Integer.MAX_VALUE);
		definition.setAcceptedSourceTypes("ALL");
		definition.setAcceptedTargetTypes("*");

		definition.addProperty(createNameProperty("New all connection"));

		PropertyDefinition targetExpressionProperty = new PropertyDefinition("targetExpression");
		targetExpressionProperty.setDisplayName("Target expression");
		targetExpressionProperty.setType(PropertyType.EXPRESSION);
		targetExpressionProperty.setDefaultValue("expression:groovy:message.properties['r1'] = result.payload");
		definition.addProperty(targetExpressionProperty);

		return definition;
	}

	private static ElementDefinition createWireTapConnection() {
		ConnectionDefinition definition = new ConnectionDefinition("WIRE_TAP_CONNECTION");
		definition.setDisplayName("Wire tap connection");
		definition.setIcon("Resources/allConnection.png");
		definition.setClassName("com.core.impl.WireTapConnection");

		definition.setColor("AA00BB");
		
		definition.setAcceptedSourceMax(1);
		definition.setAcceptedTargetMax(Integer.MAX_VALUE);
		definition.setAcceptedSourceTypes("WIRE_TAP");
		definition.setAcceptedTargetTypes("*");

		definition.addProperty(createNameProperty("New wire tap connection"));

		return definition;
	}

	private static ElementDefinition createGroovyProcessor() {
		ElementDefinition definition = new ProcessorDefinition("GROOVY");
		definition.setDisplayName("Groovy");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.common.expression.ScriptingProcessor");
		
		definition.addProperty(createNameProperty("New script"));
		definition.addProperty(createStringProperty("script", "Script", ""));

		return definition;
	}



	private static ElementDefinition createLogProcessor() {
		ElementDefinition definition = new ProcessorDefinition("LOG");
		definition.setDisplayName("Logger");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.core.common.LogProcessor");
		
		definition.addProperty(createNameProperty("New log"));
		definition.addProperty(createStringProperty("logName", "Log name", "Log name"));
		definition.addProperty(createExpressionProperty("toLogExpression", "String to be logged expression", "exp"));

		return definition;
	}

	private static ElementDefinition createHttpFetcherProcessor() {
		ElementDefinition definition = new ProcessorDefinition("HTTP_FETCHER");
		definition.setDisplayName("Http fetcher");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.modules.http.HttpFetcherProcessor");
		
		definition.addProperty(createNameProperty("New http fetcher"));
		definition.addProperty(createExpressionProperty("urlExpression", "URL expression", "'http://'"));
		definition.addProperty(createEnumProperty("fetchMode", "Fetch mode", "GET"));
		definition.addProperty(createBooleanProperty("streaming", "Use streaming mode?", "false"));

		return definition;
	}

	private static ElementDefinition createFileReaderProcessor() {
		ElementDefinition definition = new ProcessorDefinition("FILE_READER");
		definition.setDisplayName("File reader");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.modules.file.FileReaderProcessor");
		
		definition.addProperty(createNameProperty("New file reader"));
		definition.addProperty(createExpressionProperty("directory", "Directory expression", "exp"));
		definition.addProperty(createExpressionProperty("filePath", "File path expression", "exp"));

		return definition;
	}

	private static ElementDefinition createDustRendererProcessor() {
		ElementDefinition definition = new ProcessorDefinition("DUST_RENDERER");
		definition.setDisplayName("Dust renderer");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.modules.dust.DustRendererProcessor");
		
		definition.addProperty(createNameProperty("New dust renderer"));
		definition.addProperty(createExpressionProperty("template", "Template expression", "exp"));
		definition.addProperty(createExpressionProperty("jsonData", "JSON data expression", "exp"));
		
		return definition;
	}

	private static ElementDefinition createHttpMessageSource() {
		ElementDefinition definition = new MessageSourceDefinition("HTTP_MESSAGE_SOURCE");
		definition.setDisplayName("Http message source");
		definition.setIcon("Resources/groovy.gif");
		definition.setClassName("com.modules.http.HttpMessageSource");

		definition.addProperty(createNameProperty("New http message source"));
		definition.addProperty(createNumberProperty("port", "Port", "8811"));
		definition.addProperty(createExpressionProperty("timeoutExpression", "Timeout expression", "return -1"));
		
		return definition;
	}
	
	private static PropertyDefinition createNameProperty(String defaultValue) {
		return createStringProperty("name", "Name", defaultValue);
	}

	private static PropertyDefinition createEnumProperty(String name, String displayName, String defaultValue) {
		return createProperty(name, displayName, defaultValue, PropertyType.ENUM);
	}

	private static PropertyDefinition createBooleanProperty(String name, String displayName, String defaultValue) {
		return createProperty(name, displayName, defaultValue, PropertyType.BOOLEAN);
	}

	private static PropertyDefinition createNumberProperty(String name, String displayName, String defaultValue) {
		return createProperty(name, displayName, defaultValue, PropertyType.NUMBER);
	}

	private static PropertyDefinition createExpressionProperty(String name, String displayName, String defaultValue) {
		return createProperty(name, displayName, defaultValue, PropertyType.EXPRESSION);
	}
	
	private static PropertyDefinition createStringProperty(String name, String displayName, String defaultValue) {
		return createProperty(name, displayName, defaultValue, PropertyType.STRING);
	}
	
	private static PropertyDefinition createProperty(String name, String displayName, String defaultValue, PropertyType type) {
		PropertyDefinition scriptProperty = new PropertyDefinition(name);
		scriptProperty.setDisplayName(displayName);
		scriptProperty.setType(type);
		scriptProperty.setDefaultValue(defaultValue);
		return scriptProperty;
	}
}
