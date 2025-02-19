package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface SubAgentMenuDao extends BaseDAO  {
	
	
		
		
		//public void listSubAgentUser(PaginationBean<SubAgent> paginationBean, ArrayList<Criterion> props);
		
		public SubAgent loadAgentbyMailId(String email);
		
		public String loadMaxID(String agType);
	
		public void listSubAgent(PaginationBean<SubAgent> paginationBean, ArrayList<Criterion> props);

}
