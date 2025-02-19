package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NonMerchantDaoImpl extends BaseDAOImpl implements NonMerchantDao {

	@Override
	@Transactional(readOnly = false)
	public void listNonMerchantUser(final PaginationBean<Merchant> paginationBean, final ArrayList<Criterion> props) {
		logger.info("NonMerchantDaoImpl:listNonMerchantUser");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("activateDate"));

	}

	@Override
	@SuppressWarnings("unchecked")
	public void findByUserNames(final String businessName, final PaginationBean<Merchant> paginationBean) {
		// CHANGE INTERFACE
		logger.info("NonMerchantDaoImpl:findByUserNames");
		Session session = sessionFactory.getCurrentSession();
		List users = session

		.createQuery("from Merchant where business_name LIKE :business_name")
				.setParameter("business_name", "%" + businessName + "%")
				.setMaxResults(paginationBean.getItemsPerPage()).setFirstResult(paginationBean.getStartIndex()).list();

		paginationBean.setItemList(users);

	}
	@Override
	public Merchant loadNonMerchant(String username)
	{
		logger.info("NonMerchantDaoImpl:loadnonMerchant");
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
		
	}

	
	@Override
	@Transactional(readOnly = false)
	public int changeNonMerchantPassWord(String Username, String newPwd, String OldPwd)
	{
		logger.info("NonMerchantDaoImpl:changeNonMerchantPassWord");
		logger.info(Username+" "+newPwd+" "+OldPwd);
		String query = "update Merchant c set c.password =:password where userName =:userName";
		logger.info("query "+ query);
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}
/*
	@Override
	@Transactional(readOnly = false)
	public void updateNonMerchantStatus(final Long id, final CommonStatus status, final NonMerchantStatusHistory history) {
		logger.info("NonMerchantDaoImpl:updateMerchantStatus");
		getSessionFactory().save(history);

		//String query = "update " + Merchant.class.getName() + " c set c.status =:status where id =:id";
		String query = "update Non_Merchant c set c.status =:status where id =:id";
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
	public NonMerchantStatusHistory loadNonMerchantStatusHistoryID(final NonMerchant nonmerchant) {
		logger.info("NonMerchantDaoImpl:loadMerchantStatusHistoryID");
		Session session = sessionFactory.getCurrentSession();
		NonMerchantStatusHistory history = (NonMerchantStatusHistory) session
				.createQuery("from MerchantStatusHistory where merchant=:merchant order by ID desc")
				.setParameter("nonmerchant", nonmerchant).setMaxResults(1).uniqueResult();
		return history;
	}

	@Override
	public NonMerchant loadNonMerchant(final String username) {
		logger.info("NonMerchantDaoImpl:loadNonMerchant");
		return (NonMerchant) getSessionFactory().createCriteria(NonMerchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public NonMerchant loadNonMerchant(MID mid) {
		logger.info("MerchantDaoImpl:loadMerchant MID");
		// TODO Auto-generated method stub
		return (NonMerchant) getSessionFactory().createCriteria(NonMerchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	
	
	// new changes //
	@Override
	public NonMerchant loadNonMerchantbyEmail(String email) {
		logger.info("NonMerchantDaoImpl:loadNonMerchantbyEmail");
		return (NonMerchant) getSessionFactory().createCriteria(NonMerchant.class).add(Restrictions.eq("username", email))
				.setMaxResults(1).uniqueResult();
	}
 
	//change later----
	@Override
	@Transactional(readOnly = false)
	public int updateMIDData(Long m_id,Long nonmerchant_id) {
		logger.info("NonMerchantDaoImpl:updateMIDData");
		//MID mID = new MID();
		//BigInteger dfg = new BigInteger(id.toString());
		Session session = sessionFactory.getCurrentSession();
		String sql="insert into MID(MID,MERCHANT_FK) values ('?','?')";
		
		//String sql= " INSERT INTO MID ( MID , MERCHANT_FK ) VALUES ( '"+ mid +"',"+ dfg +") ";
		
		//String sql= "update MID set  merchant_fk ="+merchant_id+"  where id="+m_id;
		String sql= "update MID set  merchant_fk = :merchant_id where id= :m_id";
	
				
		logger.info(" Query :"+sql);
		
		Query insertQuery = session.createQuery(sql);
		insertQuery.setLong("nonmerchant_id", nonmerchant_id);
		insertQuery.setLong("m_id", m_id);
		//Query insertQuery = session.createSQLQuery("insert into MID(MID,MERCHANT_FK) values ('"+mid+"',"+dfg+")");
		insertQuery.setParameter(0, mid);
		insertQuery.setBigInteger(1, dfg);
		int a= insertQuery.executeUpdate();
		
		//int result = insertQuery.executeUpdate();
		return a;
		
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NonMerchant> loadNonMerchant() {
		logger.info("NonMerchantDaoImpl:loadNonMerchant");
		return (List<NonMerchant>) getSessionFactory().createCriteria(NonMerchant.class).add(Restrictions.eq("status",CommonStatus.PENDING)).list();
		
	}



	@Override
	public void listAgentNonMerchant(PaginationBean<NonMerchant> paginationBean, ArrayList<Criterion> props) {
		logger.info("NonMerchantDaoImpl:listAgentNonMerchant");
		super.getPaginationItemsByPage(paginationBean, NonMerchant.class, props,Order.desc("createdDate"));
		
	}

	
	// new method for mid already exist 24062016
		@Override
		public MID loadMid(String mid) {
			logger.info("NonMerchantDaoImpl:loadMid");
			
			return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.like("mid", mid , MatchMode.ANYWHERE))
					.setMaxResults(1).uniqueResult();
		}

	
	//new method in pending Merchant 24062016
	
		@Override
		public void listNonMerchantUser1(PaginationBean<NonMerchant> paginationBean, ArrayList<Criterion> props) {
			logger.info("NonMerchantDaoImpl:listNonMerchantUser1");
			super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("id"));
		}
		
	@Override
	public FileUpload loadFileById(String id) {
		logger.info("MerchantDaoImpl:loadFileById FileId:"+id);
		Long lo = new Long(id);
		return (FileUpload) getSessionFactory().createCriteria(FileUpload.class).add(Restrictions.eq("id", lo))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public FileUpload updateFileById(FileUpload fileUpload) {
		logger.info("MerchantDaoImpl:updateFileById ");
		Session session = sessionFactory.getCurrentSession();

		//String sql= "update FileUpload set  merchant_id ="+fileUpload.getMerchantId()+"  where id="+fileUpload.getId();
		String sql= "update FileUpload set  merchant_id = :merchantId where id= :Id";
		logger.info(" Query :"+sql);
		
		Query insertQuery = session.createQuery(sql);
		insertQuery.setString("merchantId", fileUpload.getMerchantId());
		insertQuery.setLong("Id", fileUpload.getId());
		int a= insertQuery.executeUpdate();
		
		return fileUpload;
	}*/

	
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<FileUpload> loadFileByNonMerchantId(String nonmerchId) {
		logger.info("NonMerchantDaoImpl:loadFileByNonMerchantId");
		return (List<FileUpload>) getSessionFactory().createCriteria(FileUpload.class).add(Restrictions.eq("merchantId", merchId)).list();
		
	}

	@Override
	public Merchant loadMerchantDetails(String username) {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public void listMerchantSearch(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props) {
		// TODO Auto-generated method stub
		logger.info("check merchant data:");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("activateDate"));
	}
*/
	/*@Override
	public List<Merchant> listMerchantSummary(ArrayList<Criterion> props, String date, String date1) {
		
		
		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<Merchant> merchantSummary = new ArrayList<Merchant>();
		
		//TerminalDetails reader = readerDAO.loadTerminalByDevice(id);
		String sql = null;

		if(date!= null)
		{

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat));
				logger.info("check activation date1:" + dat);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		if (date1 == null || date1.equals("")) {
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dat1 = dateFormat1.format(dt1);
			logger.info("check activation date2:" + dat1);

		} else {

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		
		sql = "select m.ACTIVATE_DATE,m.USERNAME,m.BUSINESS_NAME,m.CONTACT_PERSON_PHONE_NUMBER,t.MID,m.BUSINESS_ADDRESS1 from MERCHANT m  INNER JOIN mid t"
				+ " on t.MERCHANT_FK = m.MID_FK where m.ACTIVATE_DATE  between :dat and :dat1"
				+ " order by m.ACTIVATE_DATE desc " ;
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {
			
			Merchant merchantData = new Merchant();
			
			//logger.info("activate date from mobile user:" + merchantData.getActivateDate() );
			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[0].toString()));
				
				
				Date actDate = merchantData.getActivateDate();
				
				DateTime fromDate = new DateTime(actDate);
				merchantData.setActivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(rec[0].toString()));
				
				//merchantData.setActivateDate(new Date(rec[0].toString()));
				
				logger.info(" activation date from merchant 12233:" + merchantData.getActivateDate());
				} catch (ParseException e) {

				e.printStackTrace();
			}
			merchantData.setUsername(rec[1].toString());
			merchantData.setBusinessName(rec[2].toString());
			merchantData.setContactPersonPhoneNo(rec[3].toString());
			merchantData.setFirstName((rec[4].toString()));
			merchantData.setBusinessAddress1(rec[5].toString());
			
			
			
			merchantSummary.add(merchantData);
		}
		
		return merchantSummary;
	}

	@Override
	public void listNonMerchantUser1(
			PaginationBean<NonMerchant> paginationBean,
			ArrayList<Criterion> props) {
		// TODO Auto-generated method stub
		
	}*/

	/*@Override
	public List<FileUpload> loadFileByNonMerchantId(String nonmerchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NonMerchant loadNonMerchantDetails(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void listNonMerchantSearch(
			PaginationBean<NonMerchant> paginationBean,
			ArrayList<Criterion> props) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NonMerchant> listNonMerchantSummary(ArrayList<Criterion> props,
			String date, String date1) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	
	
	
}
