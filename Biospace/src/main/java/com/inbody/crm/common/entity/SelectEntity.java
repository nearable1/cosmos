package com.inbody.crm.common.entity;

public class SelectEntity {
	private String id;
	private String text;
	private String value;
	private String label;
	private String model;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
