package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SubAgentDaoImpl extends BaseDAOImpl implements SubAgentDao {
	protected static final Logger logger=Logger.getLogger(SubAgentDaoImpl.class.getName());

	
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	//@Transactional(readOnly = true)
	public void listSubAgentUser(final PaginationBean<SubAgent> paginationBean, final ArrayList<Criterion> props) {
		logger.info("listAgentUser : about to list all agent" );
		super.getPaginationItemsByPage(paginationBean, SubAgent.class, props,Order.desc("createdDate"));
		
	
	}

	@Override
	public SubAgent loadAgentbyMailId(String email) {
		return (SubAgent) getSessionFactory().createCriteria(SubAgent.class).add(Restrictions.eq("mailId", email))
				.setMaxResults(1).uniqueResult();
		
	}
	
	//@Override
	/*@Transactional(readOnly = false)*/
	public String loadMaxID(String agType) {
		logger.info("Agent Type : "+ agType);
		
		//String query = "select max(code) from SUB_AGENT where type ='"+agType+"'";
		String query = "select max(code) from SUB_AGENT where type = :agType";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(query);// .addEntity(SubAgent.class);
		sqlQuery.setString("agType", agType);
		@SuppressWarnings("unchecked")
		List<Object> resultSet = sqlQuery.list();
		String code = null;
		if(resultSet != null){
			for (Object rs : resultSet){
				if(rs != null){
					code = rs.toString();
				}else{
					code ="0";
				}
				
			}
		}else{
			code ="0";
		}
		
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubAgent> loadCurrentSubAgent(Agent agent) {

		//String query = "update " + Agent.class.getName() + " c set c.status =:status where id =:id";
		return (List<SubAgent>) getSessionFactory().createCriteria(SubAgent.class).add(Restrictions.eq("agent", agent)).list();

		 
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAgent> loadSubAgent() {
		
		return (List<SubAgent>) getSessionFactory().createCriteria(SubAgent.class).list();
		
		 
	}

	
	// agent volume summary used this method 08/08/2016
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAgent> loadSubAgentById(Agent agent) {
		
		return (List<SubAgent>) getSessionFactory().createCriteria(SubAgent.class).add(Restrictions.eq("agent", agent)).list();
	}

}

	
