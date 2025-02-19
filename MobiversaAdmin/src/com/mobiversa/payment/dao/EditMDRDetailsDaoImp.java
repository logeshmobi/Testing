package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.PayoutMdr;
import com.mobiversa.payment.dto.EditMDRDetailsBean;

@Repository
@SuppressWarnings({ "unchecked", "nls" })
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EditMDRDetailsDaoImp extends BaseDAOImpl implements EditMDRDetailsDao {

	private static final Logger logger = Logger.getLogger(EditMDRDetailsDaoImp.class.getName());

	private static String safeToString(Object obj) {
	    return obj != null ? obj.toString() : null;
	}
	
	@Override
	public List<Merchant> loadMerchant() {
		try {
			List<CommonStatus> statuses = Arrays.asList(CommonStatus.ACTIVE, CommonStatus.SUSPENDED);
			return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class)
					.add(Restrictions.in("status", statuses)).list();
		} catch (Exception e) {
			logger.error("Exception in obtaining all merchants detail: " + e.getMessage(), e);
			return new ArrayList<Merchant>();
		}
	}

	@Override
	public List<Merchant> loadSubMerchants(Merchant merchantDetails) {
		try {
			String query = "SELECT m.ID,m.BUSINESS_NAME FROM mobiversa.MERCHANT m " +
	                   "INNER JOIN mobiversa.MID mi ON mi.MERCHANT_FK = m.ID  " +
	                   "INNER JOIN mobiversa.MOBIVERSA_MDR mm ON mm.MID = mi.SUB_MERCHANT_MID " +
	                   "WHERE mi.SUB_MERCHANT_MID IS NOT NULL AND m.MM_ID = :businessName " +
	                   "GROUP BY m.ID";
			
//			List<Merchant> merchants = getSessionFactory().createSQLQuery(query)
//                    .setParameter("businessName", merchantDetails.getBusinessName())
//                    .setResultTransformer(Transformers.aliasToBean(Merchant.class))
//                    .list();
//
//			return merchants;

			List<Object[]> resultList = (List<Object[]>) getSessionFactory().createSQLQuery(query)
					.setParameter("businessName", merchantDetails.getBusinessName()).list();

			return mapResultsToMerchants(resultList);
		} catch (Exception e) {
			logger.error("Exception in obtaining all Sub-Merchants detail for businessName: "
					+ merchantDetails.getBusinessName() + ". " + e);
			return new ArrayList<Merchant>();
		}
	}

	private static List<Merchant> mapResultsToMerchants(List<Object[]> results) {
		return results.stream().map(row -> {
			Merchant merchant = new Merchant();
		    merchant.setId(((BigInteger) row[0]).longValue()); // Convert BigInteger to Long
			merchant.setBusinessName((String) row[1]);

			return merchant;
		}).collect(Collectors.toList());
	}

	@Override
	public Merchant loadMerchantByID(long id) {
		try {
			Merchant merchantData = new Merchant();

			String query = "SELECT mi.FPX_MID,mi.BOOST_MID,mi.GRAB_MID,mi.TNG_MID,mi.SHOPPY_MID,mi.UM_EZYWAY_MID,mi.UM_MOTO_MID,m.BUSINESS_NAME,m.ENBL_PAYOUT,mi.SUB_MERCHANT_MID,mi.FIUU_MID "+					
		                "FROM mobiversa.MERCHANT m " +
		                "INNER JOIN " +
		                "mobiversa.`MID` mi ON mi.MERCHANT_FK = m.ID " +
		                "INNER JOIN " +
		                "mobiversa.MOBIVERSA_MDR mdr ON " +
		                "mdr.`MID` = mi.FPX_MID OR " +
		                "mdr.`MID` = mi.BOOST_MID OR " +
		                "mdr.`MID` = mi.GRAB_MID OR " +
		                "mdr.`MID` = mi.TNG_MID OR " +
		                "mdr.`MID` = mi.SHOPPY_MID OR " +
		                "mdr.`MID` = mi.UM_EZYWAY_MID OR " +
		                "mdr.`MID` = mi.UM_MOTO_MID OR " +
		                "mdr.`MID` = mi.FIUU_MID " +
		                "WHERE m.ID = :merchantID " +
		                "LIMIT 1";

			Object[] resultList = (Object[]) getSessionFactory().createSQLQuery(query)
					.setString("merchantID", String.valueOf(id)).uniqueResult();

			if (resultList != null) {
				MID mid = new MID();
				mid.setFpxMid(safeToString(resultList[0]));
				mid.setBoostMid(safeToString(resultList[1]));
				mid.setGrabMid(safeToString(resultList[2]));
				mid.setTngMid(safeToString(resultList[3]));
				mid.setShoppyMid(safeToString(resultList[4]));
				mid.setUmEzywayMid(safeToString(resultList[5]));
				mid.setUmMotoMid(safeToString(resultList[6]));
				mid.setSubMerchantMID(safeToString(resultList[9]));
				mid.setFiuuMid(safeToString(resultList[10]));
				merchantData.setMid(mid);
				merchantData.setBusinessName(safeToString(resultList[7]));
				merchantData.setEnblPayout(resultList[8] != null && !resultList[8].toString().trim().isEmpty()
						&& resultList[8].toString().equalsIgnoreCase("Yes") ? "Yes" : "No");
			}

			return merchantData;
		} catch (Exception e) {
			logger.error("Exception in load Merchant by ID: " + e.getMessage(), e);
			return new Merchant();
		}
	}
	
	@Override
	public List<Merchant> loadSubMerchantByID(List<Long> ids) {
		try {
			List<Merchant> subMerchantData = (List<Merchant>) getSessionFactory().createCriteria(Merchant.class)
					.add(Restrictions.in("id", ids)).list();

			logger.info("No. of subMerchant found for merchantId: " + ids + ", is - " + subMerchantData.size());
			
			return subMerchantData;
		} catch (Exception e) {
			logger.error("Exception in load Sub-Merchant By ID: " + e.getMessage(), e);
			return new ArrayList<Merchant>();
		}
	}
	
	@Override
	public List<Merchant> loadAllSubMerchantByMmid(Merchant merchantData) {
		try {
			List<Merchant> subMerchantData = getSessionFactory().createCriteria(Merchant.class)
					.add(Restrictions.eq("mmId", merchantData.getBusinessName())).list();

			logger.info("No. of subMerchant found for merchant: " + merchantData.getBusinessName() + ", is - " + subMerchantData.size());

			return subMerchantData;
		} catch (Exception e) {
			logger.error("Exception in load Sub-Merchant By ID: " + e.getMessage(), e);
			return new ArrayList<Merchant>();
		}
	}
	
	@Override
	public EditMDRDetailsBean loadFPXMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "FPX")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(mdrTable)) {

				mdrBean.setFpxMerchantMDR(getString(mdrTable.getFpxMercAmt()));
				mdrBean.setFpxHostMDR(getString(mdrTable.getFpxHostAmt()));
				mdrBean.setFpxMobiMDR(getString(mdrTable.getFpxMobiAmt()));
				mdrBean.setFpxMinimumMDR(getString(mdrTable.getMinValue()));
				mdrBean.setType(Arrays.asList("FPX"));
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load FPX Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	@Override
	public EditMDRDetailsBean loadGrabMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "GRAB")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(mdrTable)) {

				mdrBean.setGrabMerchantMDR(getString(mdrTable.getGrabEcomMerchantMDR()));
				mdrBean.setGrabHostMDR(getString(mdrTable.getGrabEcomHostMDR()));
				mdrBean.setGrabMobiMDR(getString(mdrTable.getGrabEcomMobiMDR()));
				mdrBean.setGrabMinimumMDR(getString(mdrTable.getMinValue()));
				mdrBean.setType(Arrays.asList("GRAB"));
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load Grab Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}

	}

	@Override
	public EditMDRDetailsBean loadBoostMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "BOOST")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(mdrTable)) {

				mdrBean.setBoostMerchantMDR(getString(mdrTable.getBoostEcomMerchantMDR()));
				mdrBean.setBoostHostMDR(getString(mdrTable.getBoostEcomHostMDR()));
				mdrBean.setBoostMobiMDR(getString(mdrTable.getBoostEcomMobiMDR()));
				mdrBean.setBoostMinimumMDR(getString(mdrTable.getMinValue()));
				mdrBean.setType(Arrays.asList("BOOST"));
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load Boost Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	@Override
	public EditMDRDetailsBean loadTngMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "TNG")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(mdrTable)) {

				mdrBean.setTngMerchantMDR(getString(mdrTable.getTngEcomMerchantMDR()));
				mdrBean.setTngHostMDR(getString(mdrTable.getTngEcomHostMDR()));
				mdrBean.setTngMobiMDR(getString(mdrTable.getTngEcomMobiMDR()));
				mdrBean.setTngMinimumMDR(getString(mdrTable.getMinValue()));
				mdrBean.setType(Arrays.asList("TNG"));
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load TNG Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}
	
	@Override
	public EditMDRDetailsBean loadSppMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "SHOPPY")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(mdrTable)) {

				mdrBean.setSppMerchantMDR(getString(mdrTable.getTngEcomMerchantMDR()));
				mdrBean.setSppHostMDR(getString(mdrTable.getTngEcomHostMDR()));
				mdrBean.setSppMobiMDR(getString(mdrTable.getTngEcomMobiMDR()));
				mdrBean.setSppMinimumMDR(getString(mdrTable.getMinValue()));
				mdrBean.setType(Arrays.asList("SHOPPY"));
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load SHOPPY Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	@Override
	public EditMDRDetailsBean loadM1PayMdrDetails(String mid) {
		try {
			MobiMDR mdrTable = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "TNG")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();

			if (Objects.nonNull(mdrTable)) {

				EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

				mdrBean.setM1PayMerchantMDR(getString(mdrTable.getTngEcomMerchantMDR()));
				mdrBean.setM1PayHostMDR(getString(mdrTable.getTngEcomHostMDR()));
				mdrBean.setM1PayMobiMDR(getString(mdrTable.getTngEcomMobiMDR()));
				mdrBean.setM1PayMinimumMDR(getString(mdrTable.getMinValue()));

				return mdrBean;
			} else {
				return new EditMDRDetailsBean();
			}
		} catch (Exception e) {
			logger.error("Exception in load M1pay Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	@Override
	public EditMDRDetailsBean loadUMEzywayMdrDetails(String mid) {
		try {
			MobiMDR visaMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "VISA")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
			MobiMDR masterMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "MASTERCARD")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
			MobiMDR unionPayMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "UNIONPAY")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(visaMdrDetails)) {

				if (Objects.nonNull(visaMdrDetails.getFpxMercAmt())
						&& Objects.nonNull(visaMdrDetails.getFpxHostAmt())) {

					mdrBean.setFpxMerchantMDR(getString(visaMdrDetails.getFpxMercAmt()));
					mdrBean.setFpxHostMDR(getString(visaMdrDetails.getFpxHostAmt()));
					mdrBean.setFpxMobiMDR(getString(visaMdrDetails.getFpxMobiAmt()));
					mdrBean.setFpxMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("FPX");
					} else {
						type.add("FPX");
					}
					mdrBean.setType(type);

				}

				if (Objects.nonNull(visaMdrDetails.getBoostEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getBoostEcomHostMDR())) {

					mdrBean.setBoostMerchantMDR(getString(visaMdrDetails.getBoostEcomMerchantMDR()));
					mdrBean.setBoostHostMDR(getString(visaMdrDetails.getBoostEcomHostMDR()));
					mdrBean.setBoostMobiMDR(getString(visaMdrDetails.getBoostEcomMobiMDR()));
					mdrBean.setBoostMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("BOOST");
					} else {
						type.add("BOOST");
					}
					mdrBean.setType(type);
				}

				if (Objects.nonNull(visaMdrDetails.getGrabEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getGrabEcomHostMDR())) {

					mdrBean.setGrabMerchantMDR(getString(visaMdrDetails.getGrabEcomMerchantMDR()));
					mdrBean.setGrabHostMDR(getString(visaMdrDetails.getGrabEcomHostMDR()));
					mdrBean.setGrabMobiMDR(getString(visaMdrDetails.getGrabEcomMobiMDR()));
					mdrBean.setGrabMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("GRAB");
					} else {
						type.add("GRAB");
					}
					mdrBean.setType(type);
				}

				if (Objects.nonNull(visaMdrDetails.getTngEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getTngEcomHostMDR())) {

					mdrBean.setTngMerchantMDR(getString(visaMdrDetails.getTngEcomMerchantMDR()));
					mdrBean.setTngHostMDR(getString(visaMdrDetails.getTngEcomHostMDR()));
					mdrBean.setTngMobiMDR(getString(visaMdrDetails.getTngEcomMobiMDR()));
					mdrBean.setTngMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("TNG");
					} else {
						type.add("TNG");
					}
					mdrBean.setType(type);
				}

				mdrBean.setVisaLocalDebitCardMDR(getString(visaMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setVisaLocalCreditCardMDR(getString(visaMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setVisaForeignDebitCardMDR(getString(visaMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setVisaForeignCreditCardMDR(getString(visaMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("VISA");
				} else {
					type.add("VISA");
				}
				mdrBean.setType(type);
			}

			if (Objects.nonNull(masterMdrDetails)) {
				mdrBean.setMasterLocalDebitCardMDR(getString(masterMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setMasterLocalCreditCardMDR(getString(masterMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setMasterForeignDebitCardMDR(getString(masterMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setMasterForeignCreditCardMDR(getString(masterMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("MASTER");
				} else {
					type.add("MASTER");
				}
				mdrBean.setType(type);
			}

			if (Objects.nonNull(unionPayMdrDetails)) {
				mdrBean.setUnionPayLocalDebitCardMDR(getString(unionPayMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setUnionPayLocalCreditCardMDR(getString(unionPayMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setUnionPayForeignDebitCardMDR(getString(unionPayMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setUnionPayForeignCreditCardMDR(getString(unionPayMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("UNIONPAY");
				} else {
					type.add("UNIONPAY");
				}
				mdrBean.setType(type);
			}
			return mdrBean;

//			MobiMDR visaMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
//					.add(Restrictions.eq("cardBrand", "VISA")).add(Restrictions.eq("mid", mid))
//					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
//			
//			MobiMDR masterMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
//					.add(Restrictions.eq("cardBrand", "MASTER")).add(Restrictions.eq("mid", mid))
//					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
//			
//			MobiMDR unionPayMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
//					.add(Restrictions.eq("cardBrand", "UNIONPAY")).add(Restrictions.eq("mid", mid))
//					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
//			
//			// For existing merchants, need to check all the Mid's of Fpx and E-Wallet.
////			MobiMDR fpxMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
////					.add(Restrictions.eq("cardBrand", "FPX")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
////					.uniqueResult();
////			MobiMDR boostMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
////					.add(Restrictions.eq("cardBrand", "BOOST")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
////					.uniqueResult();
////			MobiMDR grabMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
////					.add(Restrictions.eq("cardBrand", "GRAB")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
////					.uniqueResult();
////			MobiMDR tngMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
////					.add(Restrictions.eq("cardBrand", "TNG")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
////					.uniqueResult();
////			MobiMDR sppMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
////					.add(Restrictions.eq("cardBrand", "SHOPPY")).add(Restrictions.eq("mid", mid)).setMaxResults(1)
////					.uniqueResult();
//
//			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();
//
//			if (Objects.nonNull(visaMdrDetails)) {
//
//				if ((visaMdrDetails.getFpxMercAmt() > 0 || visaMdrDetails.getFpxMercAmt() < 0)
//						&& (visaMdrDetails.getFpxHostAmt() > 0 || visaMdrDetails.getFpxHostAmt() < 0)) {
//
//					mdrBean.setFpxMerchantMDR(getString(visaMdrDetails.getFpxMercAmt()));
//					mdrBean.setFpxHostMDR(getString(visaMdrDetails.getFpxHostAmt()));
//					mdrBean.setFpxMobiMDR(getString(visaMdrDetails.getFpxMobiAmt()));
//					mdrBean.setFpxMinimumMDR(visaMdrDetails.getMinValue());
//
//					List<String> type = mdrBean.getType();
//					if (type == null) {
//						type = new ArrayList<>();
//						type.add("FPX");
//					} else {
//						type.add("FPX");
//					}
//					mdrBean.setType(type);
//
//				}
//				
//				if ((visaMdrDetails.getBoostEcomMerchantMDR() > 0 || visaMdrDetails.getBoostEcomMerchantMDR() < 0)
//						&& (visaMdrDetails.getBoostEcomHostMDR() > 0 || visaMdrDetails.getBoostEcomHostMDR() < 0)) {
//					
//					mdrBean.setBoostMerchantMDR(getString(visaMdrDetails.getBoostEcomMerchantMDR()));
//					mdrBean.setBoostHostMDR(getString(visaMdrDetails.getBoostEcomHostMDR()));
//					mdrBean.setBoostMobiMDR(getString(visaMdrDetails.getBoostEcomMobiMDR()));
//					mdrBean.setBoostMinimumMDR(visaMdrDetails.getMinValue());
//	
//					List<String> type = mdrBean.getType();
//					if (type == null) {
//						type = new ArrayList<>();
//						type.add("BOOST");
//					} else {
//						type.add("BOOST");
//					}
//					mdrBean.setType(type);
//				}
//				
//				
//				if ((visaMdrDetails.getGrabEcomMerchantMDR() > 0 || visaMdrDetails.getGrabEcomMerchantMDR() < 0)
//						&& (visaMdrDetails.getGrabEcomHostMDR() > 0 || visaMdrDetails.getGrabEcomHostMDR() < 0)) {
//					
//					mdrBean.setGrabMerchantMDR(getString(visaMdrDetails.getGrabEcomMerchantMDR()));
//					mdrBean.setGrabHostMDR(getString(visaMdrDetails.getGrabEcomHostMDR()));
//					mdrBean.setGrabMobiMDR(getString(visaMdrDetails.getGrabEcomMobiMDR()));
//					mdrBean.setGrabMinimumMDR(visaMdrDetails.getMinValue());
//
//					List<String> type = mdrBean.getType();
//					if (type == null) {
//						type = new ArrayList<>();
//						type.add("GRAB");
//					} else {
//						type.add("GRAB");
//					}
//					mdrBean.setType(type);
//				}
//				
//				if ((visaMdrDetails.getTngEcomMerchantMDR() > 0 || visaMdrDetails.getTngEcomMerchantMDR() < 0)
//						&& (visaMdrDetails.getTngEcomHostMDR() > 0 || visaMdrDetails.getTngEcomHostMDR() < 0)) {
//					
//					mdrBean.setTngMerchantMDR(getString(visaMdrDetails.getTngEcomMerchantMDR()));
//					mdrBean.setTngHostMDR(getString(visaMdrDetails.getTngEcomHostMDR()));
//					mdrBean.setTngMobiMDR(getString(visaMdrDetails.getTngEcomMobiMDR()));
//					mdrBean.setTngMinimumMDR(visaMdrDetails.getMinValue());
//	
//					List<String> type = mdrBean.getType();
//					if (type == null) {
//						type = new ArrayList<>();
//						type.add("TNG");
//					} else {
//						type.add("TNG");
//					}
//					mdrBean.setType(type);
//				}
//								
//				mdrBean.setVisaLocalDebitCardMDR(getString(visaMdrDetails.getDebitLocalMerchantMDR()));
//				mdrBean.setVisaLocalCreditCardMDR(getString(visaMdrDetails.getCreditLocalMerchantMDR()));
//				mdrBean.setVisaForeignDebitCardMDR(getString(visaMdrDetails.getDebitForeignMerchantMDR()));
//				mdrBean.setVisaForeignCreditCardMDR(getString(visaMdrDetails.getCreditForeignMerchantMDR()));
//				
//				List<String> type = mdrBean.getType();
//				if (type == null) {
//					type = new ArrayList<>();
//					type.add("VISA");
//				} else {
//					type.add("VISA");
//				}
//				mdrBean.setType(type);
//			}
//
//			if (Objects.nonNull(masterMdrDetails)) {
//				mdrBean.setMasterLocalDebitCardMDR(getString(masterMdrDetails.getDebitLocalMerchantMDR()));
//				mdrBean.setMasterLocalCreditCardMDR(getString(masterMdrDetails.getCreditLocalMerchantMDR()));
//				mdrBean.setMasterForeignDebitCardMDR(getString(masterMdrDetails.getDebitForeignMerchantMDR()));
//				mdrBean.setMasterForeignCreditCardMDR(getString(masterMdrDetails.getCreditForeignMerchantMDR()));
//
//				List<String> type = mdrBean.getType();
//				if (type == null) {
//					type = new ArrayList<>();
//					type.add("MASTER");
//				} else {
//					type.add("MASTER");
//				}
//				mdrBean.setType(type);
//			}
//
//			if (Objects.nonNull(unionPayMdrDetails)) {
//				mdrBean.setVisaLocalDebitCardMDR(getString(unionPayMdrDetails.getDebitLocalMerchantMDR()));
//				mdrBean.setVisaLocalCreditCardMDR(getString(unionPayMdrDetails.getCreditLocalMerchantMDR()));
//				mdrBean.setVisaForeignDebitCardMDR(getString(unionPayMdrDetails.getDebitForeignMerchantMDR()));
//				mdrBean.setVisaForeignCreditCardMDR(getString(unionPayMdrDetails.getCreditForeignMerchantMDR()));
//
//				List<String> type = mdrBean.getType();
//				if (type == null) {
//					type = new ArrayList<>();
//					type.add("UNIONPAY");
//				} else {
//					type.add("UNIONPAY");
//				}
//				mdrBean.setType(type);
//			}
//
////			if (Objects.nonNull(fpxMdrDetails)) {
////				mdrBean.setFpxMerchantMDR(getString(fpxMdrDetails.getFpxMercAmt()));
////				mdrBean.setFpxHostMDR(getString(fpxMdrDetails.getFpxHostAmt()));
////				mdrBean.setFpxMobiMDR(getString(fpxMdrDetails.getFpxMobiAmt()));
////				mdrBean.setFpxMinimumMDR(fpxMdrDetails.getMinValue());
////
////				List<String> type = mdrBean.getType();
////				if (type == null) {
////					type = new ArrayList<>();
////					type.add("FPX");
////				} else {
////					type.add("FPX");
////				}
////				mdrBean.setType(type);
////
//////				List<String> type = mdrBean.getType();
//////				type.add("FPX");
//////				mdrBean.setType(type);
////			}
////
////			if (Objects.nonNull(boostMdrDetails)) {
////				mdrBean.setBoostMerchantMDR(getString(boostMdrDetails.getBoostEcomMerchantMDR()));
////				mdrBean.setBoostHostMDR(getString(boostMdrDetails.getBoostEcomHostMDR()));
////				mdrBean.setBoostMobiMDR(getString(boostMdrDetails.getBoostEcomMobiMDR()));
////				mdrBean.setBoostMinimumMDR(boostMdrDetails.getMinValue());
////
////				List<String> type = mdrBean.getType();
////				if (type == null) {
////					type = new ArrayList<>();
////					type.add("BOOST");
////				} else {
////					type.add("BOOST");
////				}
////				mdrBean.setType(type);
////			}
////
////			if (Objects.nonNull(grabMdrDetails)) {
////				mdrBean.setGrabMerchantMDR(getString(grabMdrDetails.getGrabEcomMerchantMDR()));
////				mdrBean.setGrabHostMDR(getString(grabMdrDetails.getGrabEcomHostMDR()));
////				mdrBean.setGrabMobiMDR(getString(grabMdrDetails.getGrabEcomMobiMDR()));
////				mdrBean.setGrabMinimumMDR(grabMdrDetails.getMinValue());
////
////				List<String> type = mdrBean.getType();
////				if (type == null) {
////					type = new ArrayList<>();
////					type.add("GRAB");
////				} else {
////					type.add("GRAB");
////				}
////				mdrBean.setType(type);
////			}
////
////			if (Objects.nonNull(tngMdrDetails)) {
////				mdrBean.setTngMerchantMDR(getString(tngMdrDetails.getTngEcomMerchantMDR()));
////				mdrBean.setTngHostMDR(getString(tngMdrDetails.getTngEcomHostMDR()));
////				mdrBean.setTngMobiMDR(getString(tngMdrDetails.getTngEcomMobiMDR()));
////				mdrBean.setTngMinimumMDR(tngMdrDetails.getMinValue());
////
////				List<String> type = mdrBean.getType();
////				if (type == null) {
////					type = new ArrayList<>();
////					type.add("TNG");
////				} else {
////					type.add("TNG");
////				}
////				mdrBean.setType(type);
////			}
////
////			if (Objects.nonNull(sppMdrDetails)) {
////				mdrBean.setSppMerchantMDR(getString(sppMdrDetails.getTngEcomMerchantMDR()));
////				mdrBean.setSppHostMDR(getString(sppMdrDetails.getTngEcomHostMDR()));
////				mdrBean.setSppMobiMDR(getString(sppMdrDetails.getTngEcomMobiMDR()));
////				mdrBean.setSppMinimumMDR(sppMdrDetails.getMinValue());
////
////				List<String> type = mdrBean.getType();
////				if (type == null) {
////					type = new ArrayList<>();
////					type.add("SHOPPY");
////				} else {
////					type.add("SHOPPY");
////				}
////				mdrBean.setType(type);
////			}
//
//			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load UMEzyway Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}
	
	@Override
	public EditMDRDetailsBean loadUMEzyMotoMdrDetails(String mid) {
		try {
			MobiMDR visaMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "VISA")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
			MobiMDR masterMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "MASTERCARD")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();
			MobiMDR unionPayMdrDetails = (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
					.add(Restrictions.eq("cardBrand", "UNIONPAY")).add(Restrictions.eq("mid", mid))
					.add(Restrictions.isNull("tier")).setMaxResults(1).uniqueResult();

			EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

			if (Objects.nonNull(visaMdrDetails)) {

				if (Objects.nonNull(visaMdrDetails.getFpxMercAmt())
						&& Objects.nonNull(visaMdrDetails.getFpxHostAmt())) {

					mdrBean.setFpxMerchantMDR(getString(visaMdrDetails.getFpxMercAmt()));
					mdrBean.setFpxHostMDR(getString(visaMdrDetails.getFpxHostAmt()));
					mdrBean.setFpxMobiMDR(getString(visaMdrDetails.getFpxMobiAmt()));
					mdrBean.setFpxMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("FPX");
					} else {
						type.add("FPX");
					}
					mdrBean.setType(type);

				}

				if (Objects.nonNull(visaMdrDetails.getBoostEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getBoostEcomHostMDR())) {

					mdrBean.setBoostMerchantMDR(getString(visaMdrDetails.getBoostEcomMerchantMDR()));
					mdrBean.setBoostHostMDR(getString(visaMdrDetails.getBoostEcomHostMDR()));
					mdrBean.setBoostMobiMDR(getString(visaMdrDetails.getBoostEcomMobiMDR()));
					mdrBean.setBoostMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("BOOST");
					} else {
						type.add("BOOST");
					}
					mdrBean.setType(type);
				}

				if (Objects.nonNull(visaMdrDetails.getGrabEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getGrabEcomHostMDR())) {

					mdrBean.setGrabMerchantMDR(getString(visaMdrDetails.getGrabEcomMerchantMDR()));
					mdrBean.setGrabHostMDR(getString(visaMdrDetails.getGrabEcomHostMDR()));
					mdrBean.setGrabMobiMDR(getString(visaMdrDetails.getGrabEcomMobiMDR()));
					mdrBean.setGrabMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("GRAB");
					} else {
						type.add("GRAB");
					}
					mdrBean.setType(type);
				}

				if (Objects.nonNull(visaMdrDetails.getTngEcomMerchantMDR())
						&& Objects.nonNull(visaMdrDetails.getTngEcomHostMDR())) {

					mdrBean.setTngMerchantMDR(getString(visaMdrDetails.getTngEcomMerchantMDR()));
					mdrBean.setTngHostMDR(getString(visaMdrDetails.getTngEcomHostMDR()));
					mdrBean.setTngMobiMDR(getString(visaMdrDetails.getTngEcomMobiMDR()));
					mdrBean.setTngMinimumMDR(getString(visaMdrDetails.getMinValue()));

					List<String> type = mdrBean.getType();
					if (type == null) {
						type = new ArrayList<>();
						type.add("TNG");
					} else {
						type.add("TNG");
					}
					mdrBean.setType(type);
				}

				mdrBean.setVisaLocalDebitCardMDR(getString(visaMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setVisaLocalCreditCardMDR(getString(visaMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setVisaForeignDebitCardMDR(getString(visaMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setVisaForeignCreditCardMDR(getString(visaMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("VISA");
				} else {
					type.add("VISA");
				}
				mdrBean.setType(type);
			}

			if (Objects.nonNull(masterMdrDetails)) {
				mdrBean.setMasterLocalDebitCardMDR(getString(masterMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setMasterLocalCreditCardMDR(getString(masterMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setMasterForeignDebitCardMDR(getString(masterMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setMasterForeignCreditCardMDR(getString(masterMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("MASTER");
				} else {
					type.add("MASTER");
				}
				mdrBean.setType(type);
			}

			if (Objects.nonNull(unionPayMdrDetails)) {
				mdrBean.setVisaLocalDebitCardMDR(getString(unionPayMdrDetails.getDebitLocalMerchantMDR()));
				mdrBean.setVisaLocalCreditCardMDR(getString(unionPayMdrDetails.getCreditLocalMerchantMDR()));
				mdrBean.setVisaForeignDebitCardMDR(getString(unionPayMdrDetails.getDebitForeignMerchantMDR()));
				mdrBean.setVisaForeignCreditCardMDR(getString(unionPayMdrDetails.getCreditForeignMerchantMDR()));

				List<String> type = mdrBean.getType();
				if (type == null) {
					type = new ArrayList<>();
					type.add("UNIONPAY");
				} else {
					type.add("UNIONPAY");
				}
				mdrBean.setType(type);
			}
			return mdrBean;
		} catch (Exception e) {
			logger.error("Exception in load UMEzyMoto Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	@Override
	public EditMDRDetailsBean loadPayoutMdrDetails(String merchantId) {
		try {
			PayoutMdr payoutMdr = (PayoutMdr) getSessionFactory().createCriteria(PayoutMdr.class)
					.add(Restrictions.eq("merchantId", merchantId.trim())).setMaxResults(1).uniqueResult();

			if (Objects.nonNull(payoutMdr)) {

				EditMDRDetailsBean mdrBean = new EditMDRDetailsBean();

				mdrBean.setPayoutMerchantMDR(getString(payoutMdr.getMdr()));
				mdrBean.setPayoutHostMDR("N/A");
				mdrBean.setPayoutMobiMDR("N/A");
				mdrBean.setPayoutMinimumMDR(getString(payoutMdr.getMinPayoutMdrAmount()));
				mdrBean.setType(Arrays.asList("PAYOUT"));

				return mdrBean;
			} else {
				return new EditMDRDetailsBean();
			}
		} catch (Exception e) {
			logger.error("Exception in load payout Mdr details: " + e.getMessage(), e);
			return new EditMDRDetailsBean();
		}
	}

	private static String getString(Float floatValue) {
		return (floatValue != null) ? String.valueOf(floatValue) : "0.0"; //$NON-NLS-1$
	}

	private static String getString(String stringValue) {
		return (stringValue != null) ? stringValue : "0.0"; //$NON-NLS-1$
	}

	@Override
	public int updateFPXMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET mdr.FPX_MERC_AMOUNT = :fpxMercAmount,"
					+ "mdr.FPX_HOST_AMOUNT = :fpxHostAmount,mdr.FPX_MOBI_AMOUNT = :fpxMobiAmount, "
					+ "mdr.MIN_VALUE = :minValue, mdr.FPX_TXN_MDR =:fpxTxnMdr WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("fpxMercAmount", updateMdrDetails.getFpxMerchantMDR())
					.setParameter("fpxHostAmount", updateMdrDetails.getFpxHostMDR())
					.setParameter("fpxMobiAmount", updateMdrDetails.getFpxMobiMDR())
					.setParameter("minValue", updateMdrDetails.getFpxMinimumMDR())
					.setParameter("fpxTxnMdr", updateMdrDetails.getFpxMerchantMDR())
					.setParameter("mid", mid.trim())
					.setParameter("cardBrand", "FPX").executeUpdate();

			logger.info("No: of rows affected while updating Fpx-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update FPX Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateGrabMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET mdr.GRAB_ECOM_MRCH_MDR = :grabMercAmount,mdr.GRAB_ECOM_HOST_MDR = :grabHostAmount, "
					+ "mdr.GRAB_ECOM_MOBI_MDR = :grabMobiAmount,mdr.MIN_VALUE = :minValue "
					+ "WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("grabMercAmount", updateMdrDetails.getGrabMerchantMDR())
					.setParameter("grabHostAmount", updateMdrDetails.getGrabHostMDR())
					.setParameter("grabMobiAmount", updateMdrDetails.getGrabMobiMDR())
					.setParameter("minValue", updateMdrDetails.getGrabMinimumMDR())
					.setParameter("mid", mid.trim())
					.setParameter("cardBrand", "GRAB").executeUpdate();

			logger.info("No: of rows affected while updating Grab-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update Grab Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}
	
	@Override
	public int updateBoostMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET mdr.BOOST_ECOM_MRCH_MDR = :boostMercAmount, "
					+ "mdr.BOOST_ECOM_HOST_MDR = :boostHostAmount, mdr.BOOST_ECOM_MOBI_MDR = :boostMobiAmount, "
					+ "mdr.MIN_VALUE = :minValue WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("boostMercAmount", updateMdrDetails.getBoostMerchantMDR())
					.setParameter("boostHostAmount", updateMdrDetails.getBoostHostMDR())
					.setParameter("boostMobiAmount", updateMdrDetails.getBoostMobiMDR())
					.setParameter("minValue", updateMdrDetails.getBoostMinimumMDR())
					.setParameter("mid", mid.trim())
					.setParameter("cardBrand", "BOOST").executeUpdate();

			logger.info("No: of rows affected while updating BOOST-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update Boost Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateTngMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET mdr.TNG_ECOM_MRCH_MDR = :tngMercAmount, "
					+ "mdr.TNG_ECOM_HOST_MDR = :tngHostAmount, mdr.TNG_ECOM_MOBI_MDR = :tngMobiAmount, "
					+ "mdr.MIN_VALUE = :minValue WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("tngMercAmount", updateMdrDetails.getTngMerchantMDR())
					.setParameter("tngHostAmount", updateMdrDetails.getTngHostMDR())
					.setParameter("tngMobiAmount", updateMdrDetails.getTngMobiMDR())
					.setParameter("minValue", updateMdrDetails.getTngMinimumMDR())
					.setParameter("mid", mid.trim())
					.setParameter("cardBrand", "TNG").executeUpdate();

			logger.info("No: of rows affected while updating TNG-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update TNG Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}
	
	@Override	
	public int updateSppMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET mdr.TNG_ECOM_MRCH_MDR = :tngMercAmount, "
					+ "mdr.TNG_ECOM_HOST_MDR = :tngHostAmount, mdr.TNG_ECOM_MOBI_MDR = :tngMobiAmount, "
					+ "mdr.MIN_VALUE = :minValue WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("tngMercAmount", updateMdrDetails.getSppMerchantMDR())
					.setParameter("tngHostAmount", updateMdrDetails.getSppHostMDR())
					.setParameter("tngMobiAmount", updateMdrDetails.getSppMobiMDR())
					.setParameter("minValue", updateMdrDetails.getSppMinimumMDR())
					.setParameter("mid", mid.trim())
					.setParameter("cardBrand", "SHOPPY").executeUpdate();

			logger.info("No: of rows affected while updating TNG-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update TNG Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateM1PayMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUMEzywayMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		int updateVisa = updateVisaCardMdrDetails(updateMdrDetails, mid);
		int updateMaster = updateMasterCardMdrDetails(updateMdrDetails, mid);
		int updateUnionPay = updateUnionPayMdrDetails(updateMdrDetails, mid);

		return ((updateVisa > 0 && updateMaster > 0 && updateUnionPay > 0) ? 1 : 0);
	}

	@Override
	public int updateUMEzyMotoMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		int updateVisa = updateVisaCardMdrDetails(updateMdrDetails, mid);
		int updateMaster = updateMasterCardMdrDetails(updateMdrDetails, mid);
		int updateUnionPay = updateUnionPayMdrDetails(updateMdrDetails, mid);

		return ((updateVisa > 0 && updateMaster > 0 && updateUnionPay > 0) ? 1 : 0);
	}
	
	
	//fiuu 
	@Override
	public int updateFiuuMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		int updateVisa = updateVisaCardMdrDetails(updateMdrDetails, mid);
		int updateMaster = updateMasterCardMdrDetails(updateMdrDetails, mid);
		int updateUnionPay = updateUnionPayMdrDetails(updateMdrDetails, mid);

		return ((updateVisa > 0 && updateMaster > 0 && updateUnionPay > 0) ? 1 : 0);
	}

	@Override
	public int updatePayoutMdrDetails(EditMDRDetailsBean updateMdrDetails, String merchantId) {
		try {
			String sql = "UPDATE mobiversa.PAYOUT_MDR pm SET " + "pm.MDR = :mdr, "
					+ "pm.MIN_PAYOUT_MDR_AMOUNT = :minPayoutMdrAmount WHERE pm.MERCHANT_ID = :merchantId";
			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("mdr", updateMdrDetails.getPayoutMerchantMDR())
					.setParameter("minPayoutMdrAmount", updateMdrDetails.getPayoutMinimumMDR())
					.setParameter("merchantId", merchantId).executeUpdate();
			
			logger.info("No: of rows affected while updating Payout-MDR details: " + rowCount);
			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update Payout-MDR details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateVisaCardMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET " 
					+ "mdr.FPX_MERC_AMOUNT = :fpxMercAmount, "
					+ "mdr.FPX_HOST_AMOUNT = :fpxHostAmount, "
					+ "mdr.FPX_MOBI_AMOUNT = :fpxMobiAmount, "
					
					+ "mdr.BOOST_ECOM_MRCH_MDR = :boostMercAmount, " 
					+ "mdr.BOOST_ECOM_HOST_MDR = :boostHostAmount, "
					+ "mdr.BOOST_ECOM_MOBI_MDR = :boostMobiAmount, "
					
					+ "mdr.GRAB_ECOM_MRCH_MDR = :grabMercAmount, "
					+ "mdr.GRAB_ECOM_HOST_MDR = :grabHostAmount, "
					+ "mdr.GRAB_ECOM_MOBI_MDR = :grabMobiAmount, "
					
					+ "mdr.TNG_ECOM_MRCH_MDR = :tngMercAmount, " 
					+ "mdr.TNG_ECOM_HOST_MDR = :tngHostAmount, " 
					+ "mdr.TNG_ECOM_MOBI_MDR = :tngMobiAmount, " 
					
					+ "mdr.DR_LO_MRCH_MDR = :debitLocalMerchantMdr, "
					+ "mdr.CR_LO_MRCH_MDR = :creditLocalMerchantMdr, "
					+ "mdr.DR_FR_MRCH_MDR = :debitForeignMerchantMdr, "
					+ "mdr.CR_FR_MRCH_MDR = :creditForeignMerchantMdr, "
					+ "mdr.MIN_VALUE = :minValue "
					+ "WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					
					.setParameter("fpxMercAmount", updateMdrDetails.getFpxMerchantMDR())
					.setParameter("fpxHostAmount", updateMdrDetails.getFpxHostMDR())
					.setParameter("fpxMobiAmount", updateMdrDetails.getFpxMobiMDR())

					.setParameter("boostMercAmount", updateMdrDetails.getBoostMerchantMDR())
					.setParameter("boostHostAmount", updateMdrDetails.getBoostHostMDR())
					.setParameter("boostMobiAmount", updateMdrDetails.getBoostMobiMDR())

					.setParameter("grabMercAmount", updateMdrDetails.getGrabMerchantMDR())
					.setParameter("grabHostAmount", updateMdrDetails.getGrabHostMDR())
					.setParameter("grabMobiAmount", updateMdrDetails.getGrabMobiMDR())
					
					.setParameter("tngMercAmount", updateMdrDetails.getTngMerchantMDR())
					.setParameter("tngHostAmount", updateMdrDetails.getTngHostMDR())
					.setParameter("tngMobiAmount", updateMdrDetails.getTngMobiMDR())
					
					.setParameter("debitLocalMerchantMdr", updateMdrDetails.getVisaLocalDebitCardMDR())
					.setParameter("creditLocalMerchantMdr", updateMdrDetails.getVisaLocalCreditCardMDR())
					.setParameter("debitForeignMerchantMdr", updateMdrDetails.getVisaForeignDebitCardMDR())
					.setParameter("creditForeignMerchantMdr", updateMdrDetails.getVisaForeignCreditCardMDR())
					
					.setParameter("minValue", updateMdrDetails.getFpxMinimumMDR())
					
					.setParameter("mid", mid)
					
					.setParameter("cardBrand", "VISA").executeUpdate();
			
			logger.info("No: of rows affected while updating VISA-Mdr is: " + rowCount);
			
			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update VisaCard Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateMasterCardMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET " 
					+ "mdr.DR_LO_MRCH_MDR = :debitLocalMerchantMdr, "
					+ "mdr.CR_LO_MRCH_MDR = :creditLocalMerchantMdr, "
					+ "mdr.DR_FR_MRCH_MDR = :debitForeignMerchantMdr, "
					+ "mdr.CR_FR_MRCH_MDR = :creditForeignMerchantMdr, "
					+ "mdr.MIN_VALUE = :minValue "
					+ "WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";
			
			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("debitLocalMerchantMdr", updateMdrDetails.getMasterLocalDebitCardMDR())
					.setParameter("creditLocalMerchantMdr", updateMdrDetails.getMasterLocalCreditCardMDR())
					.setParameter("debitForeignMerchantMdr", updateMdrDetails.getMasterForeignDebitCardMDR())
					.setParameter("creditForeignMerchantMdr", updateMdrDetails.getMasterForeignCreditCardMDR())
					.setParameter("minValue", updateMdrDetails.getMinValue())
					.setParameter("mid", mid)
					.setParameter("cardBrand", "MASTERCARD").executeUpdate();

			logger.info("No: of rows affected while updating MASTER-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update MasterCard Mdr details: " + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int updateUnionPayMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid) {
		logger.info("UpdateUnionPayMdrDetails: "+updateMdrDetails);
		try {
			String sql = "UPDATE mobiversa.MOBIVERSA_MDR mdr SET " 
					+ "mdr.DR_LO_MRCH_MDR = :debitLocalMerchantMdr, "
					+ "mdr.CR_LO_MRCH_MDR = :creditLocalMerchantMdr, "
					+ "mdr.DR_FR_MRCH_MDR = :debitForeignMerchantMdr, "
					+ "mdr.CR_FR_MRCH_MDR = :creditForeignMerchantMdr, "
					+ "mdr.MIN_VALUE = :minValue "
					+ "WHERE mdr.MID = :mid AND mdr.CARD_BRAND = :cardBrand";

			int rowCount = this.sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("debitLocalMerchantMdr", updateMdrDetails.getUnionPayLocalDebitCardMDR())
					.setParameter("creditLocalMerchantMdr", updateMdrDetails.getUnionPayLocalCreditCardMDR())
					.setParameter("debitForeignMerchantMdr", updateMdrDetails.getUnionPayForeignDebitCardMDR())
					.setParameter("creditForeignMerchantMdr", updateMdrDetails.getUnionPayForeignCreditCardMDR())
					.setParameter("minValue", updateMdrDetails.getMinValue()).setParameter("mid", mid)
					.setParameter("cardBrand", "UNIONPAY")
					.executeUpdate();

			logger.info("No: of rows affected while updating MASTER-Mdr is: " + rowCount);

			return rowCount;
		} catch (Exception e) {
			logger.error("Exception in update UnionPay mdr details: " + e.getMessage(), e);
			return 0;
		}
	}
}