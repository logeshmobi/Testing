package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface AgentWebDao {
	
	/*public void findByAgentName(final String username, final PaginationBean<Agent> paginationBean);*/
	
	public Agent findByAgentName(final String username);

	public void listAgentUser(PaginationBean<Agent> paginationBean, ArrayList<Criterion> props);

	public Agent loadAgentByID(long Ag_id);

	//public boolean validatePassword(String password);

}
