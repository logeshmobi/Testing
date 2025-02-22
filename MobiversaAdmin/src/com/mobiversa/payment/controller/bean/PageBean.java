package com.mobiversa.payment.controller.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PageBean implements Serializable {

	private static final long serialVersionUID = 402224039834343432L;

	public enum Module {
		HOME,
		MERCHANT,
		MERCHANTADD,
		NON_MERCHANT,
		MOBILITE_MERCHANT,
		SETTLEMENT_USER,
		MOBILE_USER,
		MOBILEUSERADD,
		READER,
		TRANSACTION,
		SETTLEMENT,
		ADMIN,
		BANKUSER_ADD,
		HOME_WEB,
		MOBILEUSER_WEB,
		READER_WEB,
		TRANSACTION_WEB,
		SETTLEMENT_WEB,
		SETTINGS_WEB,
		MOBILEUSERLIST_WEB,
		MOBILEUSERADD_WEB,
		MOBILEUSEREDIT_WEB,
		AGENT,
		MASTER_MERCHANT,
		AGENT_WEB,
		SETTLEMENT_WEB12,
		AGENTADD,
		PROMOTION,
		PAYOUT_TEST
		

	}

	/* Page title */
	private String title = "";

	/* Content of the page */
	private String contentFile = null;

	/* Current processing module to get left panel */
	private Module currentModule = null;
	private final ArrayList<JSBean> jsFiles = new ArrayList<JSBean>();

	/**
	 * Side menu that exists on most of the pages
	 */
	private String sideMenuFile = null;

	public PageBean(final String pageTitle, final String contentFile, final Module currentModule) {
		this(pageTitle, contentFile, currentModule, null);
	}

	/**
	 * Convenient constructor for creating page bean with side menu (applicable
	 * to most of the pages)
	 * 
	 * @param pageTitle
	 *            page title to be displayed
	 * @param contentFile
	 *            main content JSP file
	 * @param currentModule
	 *            module to highlight at the top menu
	 * @param sideMenuFile
	 *            side menu JSP file
	 */
	public PageBean(final String pageTitle, final String contentFile, final Module currentModule,
			final String sideMenuFile) {
		this.title = pageTitle;
		this.contentFile = contentFile;
		this.currentModule = currentModule;
		this.sideMenuFile = sideMenuFile;
	}

	public void addJS(final String js, final HashMap<String, String> map) {
		JSBean jsBean = new JSBean(js, map);
		jsFiles.add(jsBean);
	}

	public void addJS(final String js) {
		JSBean jsBean = new JSBean();
		jsBean.setJsFile(js);
		jsFiles.add(jsBean);
	}

	public String getContentFile() {
		return contentFile;
	}

	public void setContentFile(final String contentFile) {
		this.contentFile = contentFile;
	}

	public Module getCurrentModule() {
		return currentModule;
	}

	public void setCurrentModule(final Module currentModule) {
		this.currentModule = currentModule;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public ArrayList<JSBean> getJsFiles() {
		return this.jsFiles;
	}

	public String getSideMenuFile() {
		return this.sideMenuFile;
	}

	public void setSideMenuFile(final String sideMenuFile) {
		this.sideMenuFile = sideMenuFile;
	}

}
