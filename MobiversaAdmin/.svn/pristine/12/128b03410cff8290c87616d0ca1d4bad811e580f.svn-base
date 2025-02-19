package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.AgentStatusHistory;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface SubAgentDao extends BaseDAO 
{
	
	
	public void listSubAgentUser(PaginationBean<SubAgent> paginationBean, ArrayList<Criterion> props);
	
	public SubAgent loadAgentbyMailId(String email);
	
	public String loadMaxID(String agType);
	
	public List<SubAgent> loadCurrentSubAgent(Agent agentName);
	
	public List<SubAgent> loadSubAgent();
	
	
	// agent volume summary used method 08/08/2016
	public List<SubAgent> loadSubAgentById(Agent agent);
}