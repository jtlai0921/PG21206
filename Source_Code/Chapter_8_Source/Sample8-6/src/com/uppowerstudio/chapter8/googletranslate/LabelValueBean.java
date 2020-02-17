package com.uppowerstudio.chapter8.googletranslate;

import java.io.Serializable;

public class LabelValueBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1445489849818133922L;

	public LabelValueBean() {
		super();
	}

	public LabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	private String label;
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return label;
	}
}
