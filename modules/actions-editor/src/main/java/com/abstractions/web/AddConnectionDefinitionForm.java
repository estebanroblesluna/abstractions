package com.abstractions.web;

/**
 *
 * @author Guido J. Celada
 */
public class AddConnectionDefinitionForm extends AddElementDefinitionForm {
        private String color;
	private String acceptedSourceTypes;
	private int acceptedSourceMax;
	private String acceptedTargetTypes;
	private int acceptedTargetMax;

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the acceptedSourceTypes
     */
    public String getAcceptedSourceTypes() {
        return acceptedSourceTypes;
    }

    /**
     * @param acceptedSourceTypes the acceptedSourceTypes to set
     */
    public void setAcceptedSourceTypes(String acceptedSourceTypes) {
        this.acceptedSourceTypes = acceptedSourceTypes;
    }

    /**
     * @return the acceptedSourceMax
     */
    public int getAcceptedSourceMax() {
        return acceptedSourceMax;
    }

    /**
     * @param acceptedSourceMax the acceptedSourceMax to set
     */
    public void setAcceptedSourceMax(int acceptedSourceMax) {
        this.acceptedSourceMax = acceptedSourceMax;
    }

    /**
     * @return the acceptedTargetTypes
     */
    public String getAcceptedTargetTypes() {
        return acceptedTargetTypes;
    }

    /**
     * @param acceptedTargetTypes the acceptedTargetTypes to set
     */
    public void setAcceptedTargetTypes(String acceptedTargetTypes) {
        this.acceptedTargetTypes = acceptedTargetTypes;
    }

    /**
     * @return the acceptedTargetMax
     */
    public int getAcceptedTargetMax() {
        return acceptedTargetMax;
    }

    /**
     * @param acceptedTargetMax the acceptedTargetMax to set
     */
    public void setAcceptedTargetMax(int acceptedTargetMax) {
        this.acceptedTargetMax = acceptedTargetMax;
    }
}
