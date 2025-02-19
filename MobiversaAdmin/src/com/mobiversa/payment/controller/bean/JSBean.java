package com.mobiversa.payment.controller.bean;

import java.io.Serializable;
import java.util.HashMap;

import com.mobiversa.common.util.FormatUtil;

public class JSBean implements Serializable {

	private static final long serialVersionUID = 402224039834343432L;

	private String jsFile;
	private HashMap<String, String> param;

	public JSBean() {
		// TODO Auto-generated constructor stub
	}

	public JSBean(final String jsFile, final HashMap<String, String> param) {
		super();
		this.jsFile = jsFile;
		this.param = param;
	}

	public String getDataParam() {
		if ((param == null) || param.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (String key : param.keySet()) {
			sb.append(String.format(" data-%s='%s'", key, FormatUtil.formatAsHTML(param.get(key))));
		}
		return sb.toString();
	}

	public String getJsFile() {
		return this.jsFile;
	}

	public void setJsFile(final String jsFile) {
		this.jsFile = jsFile;
	}

	public HashMap<String, String> getParam() {
		return this.param;
	}

	public void setParam(final HashMap<String, String> param) {
		this.param = param;
	}

}
