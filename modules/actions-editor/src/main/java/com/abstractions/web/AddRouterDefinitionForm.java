package com.abstractions.web;

/**
 *
 * @author Guido J. Celada
 */
public class AddRouterDefinitionForm extends AddElementDefinitionForm {
    private String routerEvaluatorImplementation;
    private boolean isRouterEvaluatorScript;

    /**
     * @return the routerEvaluatorImplementation
     */
    public String getRouterEvaluatorImplementation() {
        return routerEvaluatorImplementation;
    }

    /**
     * @param routerEvaluatorImplementation the routerEvaluatorImplementation to set
     */
    public void setRouterEvaluatorImplementation(String routerEvaluatorImplementation) {
        this.routerEvaluatorImplementation = routerEvaluatorImplementation;
    }

    /**
     * @return the isRouterEvaluatorScript
     */
    public boolean getIsRouterEvaluatorScript() {
        return isRouterEvaluatorScript;
    }

    /**
     * @param isRouterEvaluatorScript the isRouterEvaluatorScript to set
     */
    public void setIsRouterEvaluatorScript(boolean isRouterEvaluatorScript) {
        this.isRouterEvaluatorScript = isRouterEvaluatorScript;
    }
}
