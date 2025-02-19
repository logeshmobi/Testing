package com.mobiversa.payment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.PayoutMdr;
import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.controller.bean.RestOfMdrRatesBean;
import com.mobiversa.payment.dto.SubmerchantDto;
import com.mobiversa.payment.util.DBConnection;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.SubmerchantMdrUtils;
import com.mobiversa.payment.util.Utils;

@Component
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class SubMerchantRegistrationDaoImpl extends BaseDAOImpl implements SubMerchantRegistrationDao {

	@Transactional
	public String addPayoutGrandDetailInitial() {

		Utils util = new Utils();
		String createdDate = util.createdDate();

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			String midTablesql = "INSERT INTO mobiversa.PAYOUT_GRAND_DETAIL (TIME_STAMP) VALUES (?)";

			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setString(1, createdDate);

			int merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("merchantTableRowsAffected :" + merchantTableRowsAffected);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = statement.executeQuery("SELECT * FROM mobiversa.PAYOUT_GRAND_DETAIL");
			logger.info("resultset " + resultSet);

			String merchantId = null;
			if (resultSet.last()) {
				logger.info("merchantId : " + resultSet.getLong(1));

				merchantId = String.valueOf(resultSet.getLong(1));
			}

			logger.info("merchantTableRowsAffected :");

			return merchantId;

		} catch (Exception e) {
			logger.error("Exception while Merchant table updation : " + e.getMessage(), e);

			return null;
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.error("Failed to close ResultSet: " + e.getMessage());
					return null;
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return null;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return null;
				}
			}
		}

	}

	@Override
	@Transactional
	public Merchant addsubMerchantDetails(Merchant merchant, String merchantId, Merchant mainMerchant) {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		Utils util = new Utils();
		String createdDate = util.createdDate();

		try {
			String merchantTablesql = "INSERT INTO mobiversa.MERCHANT (CREATED_DATE,MODIFIED_DATE,ACTIVATE_DATE,STATUS,BUSINESS_NAME,EMAIL,WEBSITE,NATURE_OF_BUSINESS,COUNTRY,ENABLE_CARD,ENABLE_EWALLET,ENABLE_FPX,ENABLE_FOREIGN_CARD,ENABLE_BNPL,VERSION,FAILED_LOGIN_ATTEMPT,PASSWORD,FIRST_NAME,LAST_NAME,BUSINESS_CONTACT_NUMBER,BUSINESS_ADDRESS1,BUSINESS_SHORTNAME,MAX_AMOUNT_PER_MONTH,MAX_AMOUNT_PER_TRANSACTION,CONTACT_PERSON_NAME,JS_CHECKBOX_COUNT,JS_WITHDRAW_COUNT,PAYOUTGRANDDETAIL_FK,MM_ID,USERNAME,ENBL_PAYOUT,ROLE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			preparedStatement = connection.prepareStatement(merchantTablesql);
			preparedStatement.setString(1, createdDate);
			preparedStatement.setString(2, createdDate);
			preparedStatement.setString(3, createdDate);
			preparedStatement.setString(4, "PENDING");
			preparedStatement.setString(5, merchant.getBusinessName());
			preparedStatement.setString(6, merchant.getEmail());
			preparedStatement.setString(7, merchant.getWebsite());
			preparedStatement.setString(8, merchant.getNatureOfBusiness());
			preparedStatement.setString(9, merchant.getCountry());
			preparedStatement.setString(10, merchant.getEnableCard());
			preparedStatement.setString(11, merchant.getEnableEwallet());
			preparedStatement.setString(12, merchant.getEnableFpx());
			preparedStatement.setString(13, merchant.getForeignCard());
			preparedStatement.setString(14, merchant.getEnableBnpl());
			preparedStatement.setInt(15, 1);
			preparedStatement.setInt(16, 0);
			preparedStatement.setString(17, merchant.getPassword());
			preparedStatement.setString(18, "null");
			preparedStatement.setString(19, "null");
			preparedStatement.setString(20, "null");
			preparedStatement.setString(21, merchant.getCountry());
			preparedStatement.setString(22, "null");
			preparedStatement.setString(23, "2147483647");
			preparedStatement.setString(24, "2147483647");
			preparedStatement.setString(25, "null");
			preparedStatement.setString(26, "null");
			preparedStatement.setString(27, "null");
			preparedStatement.setLong(28, Long.parseLong(merchantId));

			preparedStatement.setString(29, mainMerchant.getBusinessName());
			preparedStatement.setString(30, merchant.getUsername());
			preparedStatement.setString(31, merchant.getEnblPayout());
			preparedStatement.setString(32, "BANK_MERCHANT");

			// 5. Execute the insert query
			int merchantTableRowsAffected = preparedStatement.executeUpdate();

			logger.info("Affected rows in merchant : " + merchantTablesql);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
				}
			}
		}

		return merchant;
	}

	@Override
	@Transactional
	public int updateMerchantInfo(Merchant merchant) {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		int affectedRows = 0;
		int merchantInfoTableRowsAffected = 0;
		try {
			String midTablesql = "INSERT INTO mobiversa.MERCHANT_INFO (MERCHANT_FK,SUBMERCHANT_APPROVAL_INDICATOR) VALUES (?,?)";

			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setLong(1, merchant.getId());
			preparedStatement.setString(2, "0");

			merchantInfoTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("For merchantInfoTableRowsAffected :");
		} catch (Exception e) {
			logger.error("Exception while Merchant Info table updation : " + e.getMessage(), e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
				}
			}
		}
		return merchantInfoTableRowsAffected;
	}

	@Override
	@Transactional
	public int addMerchantDetails(Merchant subMerchant, Merchant mainMerchant) {

		Utils util = new Utils();
		String createdDate = util.createdDate();

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			String merchantDetailsTablesql = "INSERT INTO mobiversa.MERCHANT_DETAILS (MERCHANT_ID,MERCHANT_NAME,M_CATEGORY,POINTS,VERSION,CREATED_DATE) VALUES (?,?,?,?,?,?) ";

			preparedStatement = connection.prepareStatement(merchantDetailsTablesql);
			preparedStatement.setString(1, String.valueOf(mainMerchant.getId()));
			preparedStatement.setString(2, subMerchant.getBusinessName());
			preparedStatement.setString(3, subMerchant.getNatureOfBusiness());
			preparedStatement.setString(4, "100");
			preparedStatement.setInt(5, 0);
			preparedStatement.setString(6, createdDate);
			int merchantDetailsTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("merchantDetailsTableRowsAffected : " + merchantDetailsTableRowsAffected);

			return merchantDetailsTableRowsAffected;
		} catch (Exception e) {
			logger.error("Exception while Merchant details table updation : " + e.getMessage(), e);

			return 0;
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return 0;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return 0;
				}
			}
		}

	}

	public MobileOTP getReference(Merchant currentMerchant) {

		return (MobileOTP) getSessionFactory().createCriteria(MobileOTP.class).add(Restrictions.eq("mobileNo", "2011"))
				.add(Restrictions.eq("deviceToken", "2011")).add(Restrictions.eq("deviceType", "REG")).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	@Transactional
	public String addMidtable(Merchant merchant, MobileOTP mobileOTP) {

		Utils util = new Utils();
		String createdDate = util.createdDate();

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;

		try {
			String midTablesql = "INSERT INTO mobiversa.MID (SUB_MERCHANT_MID,MERCHANT_FK,CREATED_DATE,VERSION) VALUES (?,?,?,?)";

			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setString(1, mobileOTP.getOptData());
			preparedStatement.setLong(2, merchant.getId());
			preparedStatement.setString(3, createdDate);
			preparedStatement.setInt(4, 0);
			int merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("Mid TableRowsAffected :" + merchantTableRowsAffected);

			logger.info("Mid TableRowsAffected :" + merchantTableRowsAffected);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = statement.executeQuery("SELECT * FROM mobiversa.MID");
			logger.info("resultset " + resultSet);

			String merchantId = null;
			if (resultSet.last()) {
				logger.info("merchantId : " + resultSet.getLong(1));

				merchantId = String.valueOf(resultSet.getLong(1));
			}

			return merchantId;
		} catch (Exception e) {
			logger.error("Exception while Mid table updation : " + e.getMessage(), e);
			return null;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.error("Failed to close ResultSet: " + e.getMessage());
					return null;
				}
			}
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return null;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return null;
				}
			}
		}
	}

	@Override
	@Transactional
	public int addmidFk(String id, String merchantName) {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String midTablesql = "UPDATE mobiversa.MERCHANT SET MID_FK = ? WHERE BUSINESS_NAME = ? ";

			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setLong(1, Long.parseLong(id));
			preparedStatement.setString(2, merchantName);

			int merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("merchantTableRowsAffected :" + merchantTableRowsAffected);

			return merchantTableRowsAffected;
		} catch (Exception e) {
			logger.error("Exception while Payout grand Details table updation : " + e.getMessage(), e);
			return 0;
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return 0;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return 0;
				}
			}
		}

	}

	@Override
	@Transactional
	public int addPayoutGrandDetail(Merchant subMerchant, String payoutgrandDetailsId) {

		logger.info("Sub Merchant Id :" + subMerchant.getId());
		logger.info("payoutgrandDetailsId :" + payoutgrandDetailsId);
		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			String midTablesql = "UPDATE mobiversa.PAYOUT_GRAND_DETAIL SET MERCHANT_FK = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setLong(1, subMerchant.getId());
			preparedStatement.setLong(2, Long.parseLong(payoutgrandDetailsId));

			int merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("merchantTableRowsAffected :" + merchantTableRowsAffected);

			return merchantTableRowsAffected;
		} catch (Exception e) {
			logger.error("Exception while Payout grand Details table updation : " + e.getMessage(), e);
			return 0;
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return 0;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return 0;
				}
			}
		}

	}

	@Override
	@Transactional
	public int updateMobileOTPDetails(MobileOTP mobileOTP) {

		Long midReferenceNo = Long.parseLong(mobileOTP.getOptData());
		logger.info("Mid Reference No :" + midReferenceNo + 1);
		Long ref = midReferenceNo + 1;
		String midReferenceNoUpdate = String.valueOf(ref);
		logger.info("Mid ReferenceNoUpdate :" + midReferenceNoUpdate);

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		try {

			String updateSql = "UPDATE mobiversa.MOBILE_OPT SET DEVICE_TYPE = ?, DEVICE_TOKEN = ?, OTP_DATA = ? WHERE MOBILE_NO = ?";

			preparedStatement = connection.prepareStatement(updateSql);
			preparedStatement.setString(1, "REG");
			preparedStatement.setString(2, "2011");
			preparedStatement.setString(3, midReferenceNoUpdate);
			preparedStatement.setString(4, "2011");

			int merchantTableRowsAffected = preparedStatement.executeUpdate();

			return merchantTableRowsAffected;
		} catch (Exception e) {
			logger.error("Exception while Mobile opt table updation : " + e.getMessage(), e);
			return 0;
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
					return 0;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
					return 0;
				}
			}
		}
	}

	@Override
	@Transactional
	public int addMobiversaMdr(MDRDetailsBean mdrDetails) {

		int merchantTableRowsAffected = 0;
		Utils util = new Utils();

		String[] a = { "VISA", "MASTERCARD", "UNIONPAY", "FPX", "BOOST", "GRAB", "TNG", "SHOPPY" };

		String cardBrand = "";
		logger.info("mdrDetails : " + mdrDetails.toString());
		for (int start = 0; start <= a.length - 1; start++) {
			cardBrand = a[start];

			logger.info("cardBrand : " + cardBrand);
			logger.info("start : " + start);

			SubmerchantMdrUtils submerchantUtils = new SubmerchantMdrUtils();
			RestOfMdrRatesBean restofMdr = new RestOfMdrRatesBean();
			restofMdr = submerchantUtils.submerchantRestOfMdrRates(cardBrand, mdrDetails);

			String createdDate = util.createdDate();

			Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Statement statement = null;
			try {
				String midTablesql = "INSERT INTO mobiversa.MOBIVERSA_MDR (CARD_BRAND,CREATED_DATE,CR_FR_HOST_MDR,CR_FR_MRCH_MDR,CR_FR_MOBI_MDR,CR_LO_HOST_MDR,CR_LO_MRCH_MDR,CR_LO_MOBI_MDR,DR_FR_HOST_MDR,DR_FR_MRCH_MDR,"
						+ "DR_FR_MOBI_MDR,DR_LO_HOST_MDR,DR_LO_MRCH_MDR,DR_LO_MOBI_MDR,`MID`,TIME_STAMP,BOOST_ECOM_HOST_MDR,BOOST_ECOM_MRCH_MDR,BOOST_ECOM_MOBI_MDR,"
						+ "BOOST_QR_HOST_MDR,BOOST_QR_MRCH_MDR,BOOST_QR_MOBI_MDR,GRAB_ECOM_HOST_MDR,GRAB_ECOM_MRCH_MDR,GRAB_ECOM_MOBI_MDR,GRAB_QR_MRCH_MDR,GRAB_QR_MOBI_MDR,GRAB_QR_HOST_MDR,FPX_HOST_AMOUNT,"
						+ "FPX_MERC_AMOUNT,FPX_MOBI_AMOUNT,FPX_TXN_MDR,TNG_ECOM_HOST_MDR,TNG_ECOM_MRCH_MDR,TNG_ECOM_MOBI_MDR,TNG_QR_HOST_MDR,TNG_QR_MRCH_MDR,TNG_QR_MOBI_MDR,BNPL_ECOM_HOST_MDR,"
						+ "BNPL_ECOM_MRCH_MDR,BNPL_ECOM_MOBI_MDR,BNPL_QR_HOST_MDR,BNPL_QR_MRCH_MDR,BNPL_QR_MOBI_MDR,MIN_VALUE,CL_FPX_HOST_AMOUNT,CL_FPX_MOBI_AMOUNT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

				preparedStatement = connection.prepareStatement(midTablesql);
				preparedStatement.setString(1, cardBrand);
				preparedStatement.setString(2, createdDate);
				preparedStatement.setFloat(3, restofMdr.getForiegncreditHostmdr());
				preparedStatement.setFloat(4, restofMdr.getForiegncreditMerchmdr());
				preparedStatement.setFloat(5, restofMdr.getForiegncreditmobimdr());
				preparedStatement.setFloat(6, restofMdr.getLocalcreditHostmdr());
				preparedStatement.setFloat(7, restofMdr.getLocalcreditMerchmdr());
				preparedStatement.setFloat(8, restofMdr.getLocalcreditmobimdr());
				preparedStatement.setFloat(9, restofMdr.getForiegndebitHostmdr());
				preparedStatement.setFloat(10, restofMdr.getForiegndebitMerchmdr());

				preparedStatement.setFloat(11, restofMdr.getForiegndebitmobimdr());
				preparedStatement.setFloat(12, restofMdr.getLocaldebitHostmdr());
				preparedStatement.setFloat(13, restofMdr.getLocaldebitMerchmdr());
				preparedStatement.setFloat(14, restofMdr.getLocaldebitmobimdr());
				preparedStatement.setString(15, mdrDetails.getMerchantDetail().getMid());
				preparedStatement.setString(16, createdDate);
				preparedStatement.setFloat(17, restofMdr.getBoostHostMdr());
				preparedStatement.setFloat(18, restofMdr.getBoostMercMdr());
				preparedStatement.setFloat(19, restofMdr.getBoostMobiMdr());

				preparedStatement.setFloat(20, 0.0f);
				preparedStatement.setFloat(21, 0.0f);
				preparedStatement.setFloat(22, 0.0f);
				preparedStatement.setFloat(23, restofMdr.getGrabHostMdr());
				preparedStatement.setFloat(24, restofMdr.getGrabMercMdr());
				preparedStatement.setFloat(25, restofMdr.getGrabMobiMdr());
				preparedStatement.setFloat(26, 0.0f);
				preparedStatement.setFloat(27, 0.0f);
				preparedStatement.setFloat(28, 0.0f);
				preparedStatement.setFloat(29, restofMdr.getFpxHostMdr());

				preparedStatement.setFloat(30, restofMdr.getFpxMercMdr());
				preparedStatement.setFloat(31, restofMdr.getFpxMobiMdr());
				preparedStatement.setFloat(32, restofMdr.getFpxMercMdr());

				if (cardBrand.equalsIgnoreCase("tng")) {
					preparedStatement.setFloat(33, restofMdr.getTngHostMdr());
					preparedStatement.setFloat(34, restofMdr.getTngMercMdr());
					preparedStatement.setFloat(35, restofMdr.getTngMobiMdr());
				} else {
					preparedStatement.setFloat(33, restofMdr.getSppHostMdr());
					preparedStatement.setFloat(34, restofMdr.getSppMercMdr());
					preparedStatement.setFloat(35, restofMdr.getSppMobiMdr());
				}

				preparedStatement.setFloat(36, 0.0f);
				preparedStatement.setFloat(37, 0.0f);
				preparedStatement.setFloat(38, 0.0f);
				preparedStatement.setFloat(39, 0.0f);

				preparedStatement.setFloat(40, 0.0f);
				preparedStatement.setFloat(41, 0.0f);
				preparedStatement.setFloat(42, 0.0f);
				preparedStatement.setFloat(43, 0.0f);
				preparedStatement.setFloat(44, 0.0f);
				preparedStatement.setString(45, restofMdr.getMinimumMdr());
				preparedStatement.setFloat(46, 0.0f);
				preparedStatement.setFloat(47, 0.0f);

				merchantTableRowsAffected = preparedStatement.executeUpdate();
				logger.info("merchantTableRowsAffected :" + merchantTableRowsAffected);

			} catch (Exception e) {
				logger.error("Exception while Merchant table updation : " + e.getMessage(), e);

			} finally {

				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						logger.error("Failed to close statement: " + e.getMessage());
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error("Failed to close connection: " + e.getMessage());
					}
				}

			}

		}

		return merchantTableRowsAffected;

	}

	@Override
	@Transactional
	public int addPayoutMdr(MDRDetailsBean mdrDetails, Merchant merchantData) {

		// payoutmdr

		Utils util = new Utils();
		String createdDate = util.createdDate();

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		int affectedRows = 0;
		try {
			String midTablesql = "INSERT INTO mobiversa.PAYOUT_MDR (MDR,MIN_PAYOUT_MDR_AMOUNT,MERCHANT_ID,HOST_MDR,MOBI_MDR) VALUES (?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(midTablesql);
			preparedStatement.setString(1, mdrDetails.getPayout().getMerchantmdr());
			preparedStatement.setString(2, mdrDetails.getPayout().getMinimummdr());
			preparedStatement.setString(3, String.valueOf(merchantData.getId()));
			preparedStatement.setString(4, mdrDetails.getPayout().getHostmdr());
			preparedStatement.setString(5, mdrDetails.getPayout().getMobimdr());

			int merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("For payout Mdr merchantTableRowsAffected :");
		} catch (Exception e) {
			logger.error("Exception while Merchant table updation : " + e.getMessage(), e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());

				}
			}
		}

		return affectedRows;

	}

	@Override
	public List<MerchantInfo> loadApproveSubmerchant() {
		/*
		 * I faced the "could not initialize proxy - no Session" error because I tried
		 * to access a lazily loaded object outside of the Hibernate session (here I use
		 * JSP). To resolve this, I use Hibernate.initialize.
		 */

//		Criteria criteria = getSessionFactory().createCriteria(MerchantInfo.class)
//				.add(Restrictions.eq("submerchantApprovalIndicator", "0"));
		Criteria criteria = getSessionFactory().createCriteria(MerchantInfo.class)
			    // Join the Merchant table with MerchantInfo using merchantFK to Merchant ID mapping
				 .createAlias("merchant", "m")   // "merchant" refers to the mapped relationship to the Merchant entity
			    .add(Restrictions.eq("submerchantApprovalIndicator", "0"))
			    // Add a restriction for the Merchant table's status using the merchantFK from MerchantInfo
			    .add(Restrictions.eq("m.status", CommonStatus.PENDING));

		List<MerchantInfo> merchantInfoList = criteria.list();
		merchantInfoList.forEach(info -> Hibernate.initialize(info.getMerchant()));
		return merchantInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterMerchant> loadMasterMerchant() {
		return ((List<MasterMerchant>) getSessionFactory().createCriteria(MasterMerchant.class).list());
	}

	public List<MerchantInfo> loadsubmerchantInRiskandCompilence(int currPage) {
		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;
		logger.info("currPage : " + currPage);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantInfo.class, "merchantInfo")
				.createAlias("merchantInfo.merchant", "merchant")
				.add(Restrictions.eq("merchantInfo.submerchantApprovalIndicator", "0"));

		criteria.addOrder(Order.desc("merchant.activateDate"));
		criteria.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	public List<MerchantInfo> loadsubmerchantInRiskandCompilenceSize() {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantInfo.class, "merchantInfo")
				.createAlias("merchantInfo.merchant", "merchant")
				.add(Restrictions.eq("merchantInfo.submerchantApprovalIndicator", "0"));
		criteria.addOrder(Order.desc("merchant.activateDate"));
		return criteria.list();
	}

	public List<MerchantInfo> loadsubmerchantInRiskandCompilence(String mmid, int currPage) {
		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;
		logger.info("currPage : " + currPage);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantInfo.class, "merchantInfo")
				.createAlias("merchantInfo.merchant", "merchant").add(Restrictions.eq("merchant.mmId", mmid))
				.add(Restrictions.eq("merchantInfo.submerchantApprovalIndicator", "0"));

		criteria.addOrder(Order.desc("merchant.activateDate"));
		criteria.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	public List<MerchantInfo> loadsubmerchantInRiskandCompilenceSize(String mmid) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantInfo.class, "merchantInfo")
				.createAlias("merchantInfo.merchant", "merchant").add(Restrictions.eq("merchant.mmId", mmid))
				.add(Restrictions.eq("merchantInfo.submerchantApprovalIndicator", "0"));

		criteria.addOrder(Order.desc("merchant.activateDate"));

		return criteria.list();
	}

	public int updateMerchantStatusAndReason(String status, String reason, String submerchantname,
			String descriptionUpdatedBy) {
		logger.info("status :" + status);
		logger.info("reason :" + reason);
		logger.info("submerchantname :" + submerchantname);
		logger.info("descriptionUpdatedBy :" + descriptionUpdatedBy);

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		int merchantTableRowsAffected = 0;
		try {
			String updateSql = "UPDATE mobiversa.MERCHANT_INFO AS s INNER JOIN mobiversa.MERCHANT AS m ON s.MERCHANT_FK = m.ID SET s.SUBMERCHANT_APPROVAL_INDICATOR = ?,m.STATUS = ?, s.SUBMERCHANT_DESCRIPTION = ?, s.DESCRIPTION_UPDATED_BY = ? WHERE m.BUSINESS_NAME = ?";

			preparedStatement = connection.prepareStatement(updateSql);
			preparedStatement.setString(1, "1");
			preparedStatement.setString(2, status);
			preparedStatement.setString(3, reason);
			preparedStatement.setString(4, descriptionUpdatedBy);
			preparedStatement.setString(5, submerchantname);
			merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info(
					"Updated Merchant Info table: Status, Submerchant-Approval-Indicator, Submerchant-Description, and Description-Updated. Rows affected: "
							+ merchantTableRowsAffected);

		} catch (Exception e) {
			logger.error("Exception while Mobile opt table updation : " + e.getMessage(), e);
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());

				}
			}
		}
		return merchantTableRowsAffected;
	}

//	public List<SubmerchantDto> loadSubmerchantListForOperationChildvalidationWithpagination(int currPage) {
//		List<SubmerchantDto> submerchantDtoList = new ArrayList<>();
//
//		try {
//
//			Session session = sessionFactory.getCurrentSession();
//			
//			String sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, md.SUB_MERCHANT_MID, m.MM_ID, "
//					+ "m.`STATUS`, m.COUNTRY, m.EMAIL, m.NATURE_OF_BUSINESS, n.SUBMERCHANT_DESCRIPTION ,m.WEBSITE "
//					+ "FROM mobiversa.MERCHANT m LEFT JOIN mobiversa.merchant_info n ON n.MERCHANT_FK = m.id "
//					+ "INNER JOIN mobiversa.MID md ON md.ID = m.MID_FK WHERE m.MM_ID IS NOT NULL "
//					+ "AND m.MM_ID NOT IN ('', '--Select the Master Merchant--', '0') ORDER BY "
//					+ "m.ACTIVATE_DATE DESC";
//
//			logger.info("Query : " + sql);
//			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = currPage;
//			logger.info("Page Number:" + pageNumFromJsp);
//			logger.info("Max Count for Records:" + pageSize);
//
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//			sqlQuery.setMaxResults(pageSize);
//			
//			
//			List<Object[]> resultSet = sqlQuery.list();
//			logger.info("resultset size:" + resultSet.size());
//			for (Object[] rec : resultSet) {
//
//				SubmerchantDto submerchantDto = new SubmerchantDto();
//
//				submerchantDto.setCreatedDate(rec[0] != null ? rec[0].toString():"");
//				submerchantDto.setBusinessName(rec[1] != null ? rec[1].toString():"");
//				submerchantDto.setSubMerchantMID(rec[2] !=null ? rec[2].toString():"");
//				submerchantDto.setMmId(rec[3] != null ? rec[3].toString() : "");
//				submerchantDto.setStatus(rec[4] != null ? rec[4].toString() : "");
//				submerchantDto.setCountry(rec[5] != null ? rec[5].toString():"");
//				submerchantDto.setEmail(rec[6] != null ? rec[6].toString() : "");
//				submerchantDto.setNatureOfBusiness(rec[7] != null ? rec[7].toString():"");
//				submerchantDto.setSubMerchantDescription(rec[8] != null ? rec[8].toString() : "");
//				submerchantDto.setWebsite(rec[9] != null ? rec[9].toString() : "");
//
//				submerchantDtoList.add(submerchantDto);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return submerchantDtoList;
//	}

	public List<SubmerchantDto> loadSubmerchantListForOperationChildvalidationWithpagination(int currPage) {
		List<SubmerchantDto> submerchantDtoList = new ArrayList<>();
		int totalCount = 0;

		try {
			Session session = sessionFactory.getCurrentSession();

			String sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, md.SUB_MERCHANT_MID, m.MM_ID, "
					+ "m.`STATUS`, m.COUNTRY, m.EMAIL, m.NATURE_OF_BUSINESS, n.SUBMERCHANT_DESCRIPTION ,m.WEBSITE, "
					+ "(SELECT COUNT(*) FROM mobiversa.MERCHANT m2 WHERE m2.MM_ID IS NOT NULL "
					+ "AND m2.MM_ID NOT IN ('', '--Select the Master Merchant--', '0')) as totalCount "
					+ "FROM mobiversa.MERCHANT m " + "LEFT JOIN mobiversa.MERCHANT_INFO n ON n.MERCHANT_FK = m.id "
					+ "INNER JOIN mobiversa.MID md ON md.ID = m.MID_FK " + "WHERE m.MM_ID IS NOT NULL "
					+ "AND m.MM_ID NOT IN ('', '--Select the Master Merchant--', '0') "
					+ "ORDER BY m.ACTIVATE_DATE DESC";

			logger.info("Query : " + sql);
			Query sqlQuery = session.createSQLQuery(sql);

			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);

			int pageNumFromJsp = currPage;
			logger.info("Page Number:" + pageNumFromJsp);
			logger.info("Max Count for Records:" + pageSize);

			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);

			List<Object[]> resultSet = sqlQuery.list();
			logger.info("resultset size:" + resultSet.size());

			if (!resultSet.isEmpty()) {
				totalCount = ((Number) resultSet.get(0)[10]).intValue();
			}

			for (Object[] rec : resultSet) {
				SubmerchantDto submerchantDto = new SubmerchantDto();
				submerchantDto.setCreatedDate(rec[0] != null ? rec[0].toString() : "");
				submerchantDto.setBusinessName(rec[1] != null ? rec[1].toString() : "");
				submerchantDto.setSubMerchantMID(rec[2] != null ? rec[2].toString() : "");
				submerchantDto.setMmId(rec[3] != null ? rec[3].toString() : "");
				submerchantDto.setStatus(rec[4] != null ? rec[4].toString() : "");
				submerchantDto.setCountry(rec[5] != null ? rec[5].toString() : "");
				submerchantDto.setEmail(rec[6] != null ? rec[6].toString() : "");
				submerchantDto.setNatureOfBusiness(rec[7] != null ? rec[7].toString() : "");
				submerchantDto.setSubMerchantDescription(rec[8] != null ? rec[8].toString() : "");
				submerchantDto.setWebsite(rec[9] != null ? rec[9].toString() : "");
				submerchantDto.setSubmerchantListSizeForOperationChild(totalCount);
				submerchantDtoList.add(submerchantDto);
			}

			logger.info("Total Count: " + totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return submerchantDtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantInfo> loadApprovedMerchant() {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantInfo.class, "merchantInfo");
		criteria.createAlias("merchantInfo.merchant", "merchant");
		criteria.add(Restrictions.eq("merchant.status", CommonStatus.APPROVED));
		return (List<MerchantInfo>) criteria.list();

	}

	public Merchant findMerchantBusinessName(String businessName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Merchant.class)
				.add(Restrictions.eq("businessName", businessName));
		return (Merchant) criteria.setMaxResults(1).uniqueResult();
	}

	public List<SubmerchantDto> loadSubmerchantListForOperationChildvalidationWithpagination(int currPage,
			String mmid) {

		List<SubmerchantDto> submerchantDtoList = new ArrayList<>();

		try {
			Session session = sessionFactory.getCurrentSession();

			// Query for counting total records
			String countSql = "SELECT COUNT(*) " + "FROM mobiversa.MERCHANT m "
					+ "LEFT JOIN mobiversa.MERCHANT_INFO n ON n.MERCHANT_FK = m.id "
					+ "INNER JOIN mobiversa.MID md ON md.ID = m.MID_FK " + "WHERE m.MM_ID = :mmid";

			Query countQuery = session.createSQLQuery(countSql);
			countQuery.setString("mmid", mmid);
			int count = ((Number) countQuery.uniqueResult()).intValue();

			// Query for retrieving paginated records
			String sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, md.SUB_MERCHANT_MID, m.MM_ID, "
					+ "m.`STATUS`, m.COUNTRY, m.EMAIL, m.NATURE_OF_BUSINESS, n.SUBMERCHANT_DESCRIPTION, m.WEBSITE "
					+ "FROM mobiversa.MERCHANT m " + "LEFT JOIN mobiversa.MERCHANT_INFO n ON n.MERCHANT_FK = m.id "
					+ "INNER JOIN mobiversa.MID md ON md.ID = m.MID_FK "
					+ "WHERE m.MM_ID = :mmid ORDER BY m.ACTIVATE_DATE DESC";

			Query sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setString("mmid", mmid);

			String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(dynamicPage);

			int firstResult = (currPage * pageSize) - pageSize;
			sqlQuery.setFirstResult(firstResult);
			sqlQuery.setMaxResults(pageSize);

			List<Object[]> resultSet = sqlQuery.list();
			for (Object[] rec : resultSet) {
				SubmerchantDto submerchantDto = new SubmerchantDto();
				submerchantDto.setCreatedDate(rec[0] != null ? rec[0].toString() : "");
				submerchantDto.setBusinessName(rec[1] != null ? rec[1].toString() : "");
				submerchantDto.setSubMerchantMID(rec[2] != null ? rec[2].toString() : "");
				submerchantDto.setMmId(rec[3] != null ? rec[3].toString() : "");
				submerchantDto.setStatus(rec[4] != null ? rec[4].toString() : "");
				submerchantDto.setCountry(rec[5] != null ? rec[5].toString() : "");
				submerchantDto.setEmail(rec[6] != null ? rec[6].toString() : "");
				submerchantDto.setNatureOfBusiness(rec[7] != null ? rec[7].toString() : "");
				submerchantDto.setSubMerchantDescription(rec[8] != null ? rec[8].toString() : "");
				submerchantDto.setWebsite(rec[9] != null ? rec[9].toString() : "");
				submerchantDto.setSubmerchantListSizeForOperationChild(count);
				submerchantDtoList.add(submerchantDto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return submerchantDtoList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadSubMerchantApprove(int currPage) {

		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;

		Session session = sessionFactory.getCurrentSession();

		// Count query
		Criteria countCriteria = session.createCriteria(Merchant.class)
				.add(Restrictions.eq("status", CommonStatus.APPROVED)).setProjection(Projections.rowCount());
		long totalCount = (long) countCriteria.uniqueResult();

		// Paginated list query
		Criteria listCriteria = session.createCriteria(Merchant.class)
				.add(Restrictions.eq("status", CommonStatus.APPROVED))
				.setFirstResult((pageNumFromJsp - pageSize) * pageSize).setMaxResults(pageSize);
		List<Merchant> merchants = (List<Merchant>) listCriteria.list();

		// Create result map
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		result.put("merchants", merchants);

		return result;
	}

	@Override
	public Map<String, Object> loadMainMerchantUsingMMIDoperationTeam(String mmid, int currPage) {
		logger.info("Load Main merchant using MMID");

		// Load pagination count from properties
		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;

		// Get the current session
		Session session = sessionFactory.getCurrentSession();

		// Criteria for fetching paginated list
		Criteria listCriteria = session.createCriteria(Merchant.class);
		listCriteria.add(Restrictions.eq("mmId", mmid));
		listCriteria.add(Restrictions.eq("status", CommonStatus.APPROVED));
		listCriteria.setFirstResult((pageNumFromJsp - 1) * pageSize);
		listCriteria.setMaxResults(pageSize);

		// Fetch paginated list of merchants
		List<Merchant> merchants = (List<Merchant>) listCriteria.list();

		// Criteria for counting total number of records
		Criteria countCriteria = session.createCriteria(Merchant.class);
		countCriteria.add(Restrictions.eq("mmId", mmid));
		countCriteria.add(Restrictions.eq("status", CommonStatus.APPROVED));
		countCriteria.setProjection(Projections.rowCount());

		// Fetch total count
		Long totalCount = (Long) countCriteria.uniqueResult();

		// Prepare and return the result map
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		result.put("merchants", merchants);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Merchant loadSubMerchant(String merchantName) {
		return (Merchant) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("status", CommonStatus.APPROVED))
				.add(Restrictions.eq("businessName", merchantName)).setMaxResults(1).uniqueResult();

	}

	@Override
	public Map<String, Object> loadSubMerchantAfterApproveOperationteam(int currPage) {
		logger.info("Loading SubMerchant after approval by Operation team");

		// Load pagination count from properties
		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;

		// Get the current session
		Session session = sessionFactory.getCurrentSession();

		// Criteria for fetching paginated list
		Criteria listCriteria = session.createCriteria(Merchant.class);
		listCriteria.add(Restrictions.eq("status", CommonStatus.SUBMITTED));
		listCriteria.setFirstResult((pageNumFromJsp - pageSize) * pageSize);
		listCriteria.setMaxResults(pageSize);

		// Fetch paginated list of merchants
		List<Merchant> merchants = (List<Merchant>) listCriteria.list();

		// Criteria for counting total number of records
		Criteria countCriteria = session.createCriteria(Merchant.class);
		countCriteria.add(Restrictions.eq("status", CommonStatus.SUBMITTED));
		countCriteria.setProjection(Projections.rowCount());

		// Fetch total count
		Long totalCount = (Long) countCriteria.uniqueResult();

		// Prepare and return the result map
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		result.put("merchants", merchants);

		return result;
	}

	@Override
	public Map<String, Object> loadMerchantsUsingMMIDAndName(String merchantName, int currPage) {
		logger.info("Loading Merchants using MMID and Merchant Name");

		// Load pagination count from properties
		String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(dynamicPage);
		int pageNumFromJsp = currPage;

		// Get the current session
		Session session = sessionFactory.getCurrentSession();

		// Criteria for fetching paginated list
		Criteria listCriteria = session.createCriteria(Merchant.class);
		listCriteria.add(Restrictions.eq("mmId", merchantName));
		listCriteria.add(Restrictions.eq("status", CommonStatus.SUBMITTED));
		listCriteria.setFirstResult((pageNumFromJsp - pageSize) * pageSize);
		listCriteria.setMaxResults(pageSize);

		// Fetch paginated list of merchants
		List<Merchant> merchants = (List<Merchant>) listCriteria.list();

		// Criteria for counting total number of records
		Criteria countCriteria = session.createCriteria(Merchant.class);
		countCriteria.add(Restrictions.eq("mmId", merchantName));
		countCriteria.add(Restrictions.eq("status", CommonStatus.SUBMITTED));
		countCriteria.setProjection(Projections.rowCount());

		// Fetch total count
		Long totalCount = (Long) countCriteria.uniqueResult();

		// Prepare and return the result map
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		result.put("merchants", merchants);

		return result;
	}

	public int updateMerchantStatusOperationTeam(String status, String businessName) {

		Connection connection = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;

		int merchantTableRowsAffected = 0;
		try {
			String updateSql = "UPDATE mobiversa.MERCHANT AS m set m.STATUS = ? WHERE m.BUSINESS_NAME = ?";

			preparedStatement = connection.prepareStatement(updateSql);
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, businessName);
			merchantTableRowsAffected = preparedStatement.executeUpdate();
			logger.info("Successfully Updated Status in merchant table ");
		} catch (Exception e) {
			logger.error("Exception while Mobile opt table updation : " + e.getMessage(), e);
		} finally {
			// Close resources in a finally block to ensure proper cleanup
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.error("Failed to close statement: " + e.getMessage());

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Failed to close connection: " + e.getMessage());
				}
			}
		}
		return merchantTableRowsAffected;
	}

	public MobiMDR loadMdrList(String mid, String cardBrand) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MobiMDR.class)
				.add(Restrictions.eq("mid", mid)).add(Restrictions.eq("cardBrand", cardBrand));
		return (MobiMDR) criteria.setMaxResults(1).uniqueResult();
	}

	public PayoutMdr loadPayoutMdr(Merchant merchantData) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PayoutMdr.class)
				.add(Restrictions.eq("merchantId", String.valueOf(merchantData.getId())));
		return (PayoutMdr) criteria.setMaxResults(1).uniqueResult();

	}

}
