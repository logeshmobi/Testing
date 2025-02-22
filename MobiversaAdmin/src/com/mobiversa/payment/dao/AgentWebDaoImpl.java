package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Repository
@Transactional(readOnly = false)
public class AgentWebDaoImpl extends BaseDAOImpl implements AgentWebDao {
	@Autowired
	protected SessionFactory sessionFactory;
	protected final Logger logger = Logger.getLogger(AgentWebDaoImpl.class.getName());

	@Override
	public Agent findByAgentName(final String username) {
		//logger.info("Loading Agent based on userName :" + username);
		
		logger.info("Username : "+username);
		
		Session session = sessionFactory.getCurrentSession();
		List users = session.createQuery("from Agent where username=:username and status ='ACTIVE'").setParameter("username", username)
				.setMaxResults(1).list();
		if (users.size() > 0) {
			return (Agent) users.get(0);
		} else {
		//logger.info("return null");
			return null;
		}
	}


	
	
	@Override
	public void listAgentUser(PaginationBean<Agent> paginationBean, ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Agent.class, props, Order.asc("id"));

	}

	@Override
	public Agent loadAgentByID(long Ag_id) {
		// TODO Auto-generated method stub
		return null;
	}


}