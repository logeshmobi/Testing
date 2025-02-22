package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AgentStatusHistory;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface AgentDao extends BaseDAO {
	
	public void findByUserNames(final String agentName, final PaginationBean<Agent> paginationBean);
	
	public void listAllTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1,int agentId);

	public void listAgentUser(PaginationBean<Agent> paginationBean, ArrayList<Criterion> props);

	public void updateAgentStatus(Long id, CommonStatus status, AgentStatusHistory history);

	public AgentStatusHistory loadAgentStatusHistoryID(Agent agent);

	public Agent loadAgent(String username);

	public Agent loadAgent(MID mid);
	
	public int changeAgentPassWord(String Username,String newPwd,String OldPwd);
	
	public String loadMaxID(String agType);

	public Agent loadAgentbyMailId(String email);
	
	public List<Agent> loadAgent(); 
	
	public List<Agent> loadCurrentAgent(String agentName);
	
	public Merchant loadMerchant(String agId);
	
	
	public AuditTrail loadAgentData(String username);
	
	
	public List<Agent> loadAgentDetails();
	
//new method end 16062016
	
	
	// new method for superagent 
	
	public Agent loadAgentType(String agType);

	//public Agent loadAgentbyId(BigInteger bigInteger);

	public List<Agent> loadOffsetAgent(String offset);

	public Agent validateAgentEmailId(String emailId);

	public Agent loadAgentByIdPk(Long l);

	public Agent loadAgentbyId(Long id);

	public Agent loadAgentDetailsbyId(BigInteger agID);
}
