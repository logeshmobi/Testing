package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AgentStatusHistory;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AgentDaoImpl extends BaseDAOImpl implements AgentDao {
	protected static final Logger logger=Logger.getLogger(AgentDaoImpl.class.getName());
	
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true)
	public void listAgentUser(final PaginationBean<Agent> paginationBean, final ArrayList<Criterion> props) {
		logger.info("listAgentUser : about to list all agent");
		super.getPaginationItemsByPage(paginationBean, Agent.class, props,Order.desc("activateDate"));
		

	}

	@Transactional(readOnly = false)
	public void listAllTransaction(final PaginationBean<ForSettlement> paginationBean,
			final ArrayList<Criterion> props, final String date, final String date1,final int agentID) {			
		logger.info("Inside   listAllTransaction : " + date + "     " + date1);
		
		String dat = null;
		String dat1 = null;	
		String year1 = null;
		String year2 = null;
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;	

		if ((date == null || date1 == null)||(date.equals("") || date1.equals("")))  {

			Date dt = new Date();

			SimpleDateFormat month = new SimpleDateFormat("MM");
			dat = month.format(dt);
			dat = dat +"01";
			SimpleDateFormat yr1 = new SimpleDateFormat("yyyy");
			year1 = yr1.format(dt);
			
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMdd");
			dat1 = dateFormat1.format(dt1);
			SimpleDateFormat yr2 = new SimpleDateFormat("yyyy");
			year2 = yr2.format(dt1);

		} else {
			dat = date;
			String sub[] = dat.split("/");
			dat = sub[0] + sub[1];
			year1 = sub[2];

			dat1 = date1;
			String sub1[] = dat1.split("/");
			dat1 = sub1[0] + sub1[1];
			year2 =sub1[2];
		}
		
			
		/*sql="select f.city , f.BUSINESS_NAME AS MerchantName ,a.date ,a.txn_year , sum(a.AMOUNT)  TotalAmount "
				+"from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+"ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+" where a.status='S' and ag.id = "+agentID+" and DATE between '" + dat +"' and '" + dat1 +"'  and txn_year between '"+
				year1+"' and '"+year2+"' group by a.MID,a.DATE order by a.DATE desc";*/
		
		sql="select f.city , f.BUSINESS_NAME AS MerchantName ,a.date ,a.txn_year , sum(a.AMOUNT)  TotalAmount "
				+"from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+"ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+" where a.status='S' and ag.id = :agentID and DATE between  :dat and :dat1 and txn_year between "
				+":year1 and :year2 group by a.MID,a.DATE order by a.DATE desc";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setInteger("agentID", agentID);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		sqlQuery.setString("year1", year1);
		sqlQuery.setString("year2", year2);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setLocation(rec[0].toString());
			fs.setMerchantName(rec[1].toString().toUpperCase());
			fs.setDate(rec[2].toString());
			String pattern1 = rec[2].toString();
			String su = pattern1.substring(0, 2);
			String sub = pattern1.substring(2, 4);

			String month = null;
			if (su == "01" || su.equals("01"))
				month = "Jan";
			else if (su == "02" || su.equals("02"))
				month = "Feb";
			else if (su == "03" || su.equals("03"))
				month = "Mar";
			else if (su == "04" || su.equals("04"))
				month = "Apr";
			else if (su == "05" || su.equals("05"))
				month = "May";
			else if (su == "06" || su.equals("06"))
				month = "Jun";
			else if (su == "07" || su.equals("07"))
				month = "Jul";
			else if (su == "08" || su.equals("08"))
				month = "Aug";
			else if (su == "09" || su.equals("09"))
				month = "Sep";
			else if (su == "10" || su.equals("10"))
				month = "Oct";
			else if (su == "11" || su.equals("11"))
				month = "Nov";
			else if (su == "12" || su.equals("12"))
				month = "Dec";
			fs.setDate(sub + "-" + month+"-"+rec[3].toString());
			/* fs.setTime(rec[3].toString()); */
			//logger.info(" Amount : " + rec[4]);
			Double d = new Double(rec[4].toString());
			d = d / 100;
			String pattern = "#,###.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);
			//fs.setAgentName(rec[4].toString());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		//paginationBean.setTotalRowCount(fss.size());
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void findByUserNames(final String agentName, final PaginationBean<Agent> paginationBean) {
		// CHANGE INTERFACE
		Session session = sessionFactory.getCurrentSession();
		List users = session

		.createQuery("from Agent where ag_name LIKE :agentName")
				.setParameter("agentName", "%" + agentName + "%")
				.setMaxResults(paginationBean.getItemsPerPage()).setFirstResult(paginationBean.getStartIndex()).list();

		paginationBean.setItemList(users);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateAgentStatus(final Long id, final CommonStatus status, final AgentStatusHistory history) {

		getSessionFactory().save(history);

		//String query = "update " + Agent.class.getName() + " c set c.status =:status where id =:id";
		String query = "update Agent.class.getName() c set c.status =:status where id =:id";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("status", status)
				.setLong("id", id).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}
		// auto commit

	}

	@Override
	public AgentStatusHistory loadAgentStatusHistoryID(final Agent agent) {

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		AgentStatusHistory history = (AgentStatusHistory) session
				.createQuery("from AgentMerchantStatusHistory where Agent=:agent order by ID desc")
				.setParameter("agent", agent).setMaxResults(1).uniqueResult();
		return history;
	}

	@Override
	@Transactional(readOnly = false)
	public int changeAgentPassWord(String Username, String newPwd, String OldPwd) {
		String query = "update Agent c set c.password =:password where userName =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}


	@Override
	public Agent loadAgent(String username) {
		
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public Agent loadAgent(MID mid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String loadMaxID(String agType) {
		logger.info("Agent Type : "+ agType);
		//String query = "select max(ag_code) from AGENT where ag_type ='"+agType+"'";
		String query = "select max(ag_code) from AGENT where ag_type = :agType";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(query);// .addEntity(ForSettlement.class);
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
		
		logger.info("Agent Type : "+ code);
		
		return code;
	}
	
	@Override
	public Agent loadAgentbyMailId(String email) {
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("username", email))
				.setMaxResults(1).uniqueResult();
		
	}
	@Override
	public Agent loadAgentbyId(Long id) {
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
		
	}
	
	@Override
	public Agent loadAgentDetailsbyId(BigInteger id) {
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("id", id.longValue()))
				.setMaxResults(1).uniqueResult();
		
	}
	
	@Override
	public Agent loadAgentByIdPk(Long id) {
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
		
	}
	
	
	//merchant add
		@SuppressWarnings("unchecked")
		@Override
		public List<Agent> loadAgent() {
			
			return (List<Agent>) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("status",CommonStatus.ACTIVE
					)).list();
					//.setMaxResults(1).uniqueResult().;
		} 
	
	//new method started agent portal(add merchant)16062016
    @SuppressWarnings("unchecked")
    @Override
    public List<Agent> loadCurrentAgent(String agentName) {
        return (List<Agent>) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("username", agentName)).list();
   
    }

	
	@SuppressWarnings("unchecked")
	@Override
	public Merchant loadMerchant(String agId) {
		
		BigInteger dfg = new BigInteger(agId.toString());
	
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("agID",dfg ))
				.setMaxResults(1).uniqueResult();

	}
//method end 16062016

	@Override
	public AuditTrail loadAgentData(String username) {
		//BigInteger dfg = new BigInteger(username.toString());
		
		return (AuditTrail) getSessionFactory().createCriteria(AuditTrail.class).add(Restrictions.eq("username",username ))
				.setMaxResults(1).uniqueResult();

	}

	@Override
	
	@SuppressWarnings("unchecked")
	public List<Agent> loadAgentDetails() {
		return (List<Agent>) getSessionFactory().createCriteria(Agent.class).list();
		
		
				//.setMaxResults(1).uniqueResult();
	}

	@Override
	public Agent loadAgentType(String agType) {
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("agType",agType ))
				.setMaxResults(1).uniqueResult();
		
	}

	@Override
	public List<Agent> loadOffsetAgent(String offset) {


		String sql = null;
		List<Agent> listAgents = new ArrayList<Agent>();

		logger.info("Inside load offset :"+offset);
			
		sql="select f.ID ,f.FIRST_NAME  from AGENT f where f.`STATUS` = 'ACTIVE' order by f.ID asc limit 10 "
					+ " OFFSET "+offset;


		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		//sqlQuery.setString("agname", agname);
		//sqlQuery.setString("agid", agid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size ---: " + resultSet.size());

		for (Object[] rec : resultSet) {

			if(rec[0]!=null){
				
				Agent  agent = new Agent();
				
				agent.setId(Long.parseLong(rec[0].toString()));
				agent.setFirstName(rec[1].toString());
				
				listAgents.add(agent);
			}
			/*if(rec[2]!=null){
				listMid.add(rec[2].toString());
			}*/
			//logger.info("display list MID: " + rec[0].toString());

		}
		return listAgents;
	
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public Agent validateAgentEmailId(String emailId) {
		logger.info("validateAgentEmailId ");
		
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("email", emailId))
				.setMaxResults(1).uniqueResult();
		
	}

	


}
