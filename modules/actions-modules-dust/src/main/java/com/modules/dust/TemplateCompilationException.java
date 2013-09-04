package com.modules.dust;

public class TemplateCompilationException extends Exception {

	private static final long serialVersionUID = 4543047535949196994L;
	
	public TemplateCompilationException(String compilationError) {
		super(compilationError);
	}
	
}
