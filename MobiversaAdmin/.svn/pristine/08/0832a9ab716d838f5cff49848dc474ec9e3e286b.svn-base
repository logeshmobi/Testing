package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Merchant;
//import com.mobiversa.common.bo.MerchantCustMail;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface PromotionDao extends BaseDAO {

	// new method custMail
	public void listPromotions(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props);

	//public String loadMaxID(String agType);

	public void listMerchantPromo(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props);

	// new method for custmail & promotion 22/05/2017
	public MerchantDetails loadMerchantPoints(String mid);

	public Merchant loadMerchantByMid(String mid);

	// new method for promotion 25-04-2017
	public List<TerminalDetails> loadTerminalDetails(String mid);

	// new method for tid list 03052017 push notification
	public MobileUser loadMobileUser(String tid);

	public TerminalDetails loadDeviceID(String deviceId);

	public TerminalDetails loadTid(String tid);
	
	
	//new method for promotion summary search condition 11072017
	public void listPromotionSearch(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props);

	public List<MobileUser> loadMobileUserDetails(Long id);
	
	public MobileUser loadMobileUserDetailsByUsername(String username);

	public MerchantDetails loadMerchantPointsbyMid(Merchant merchant);

	public MerchantDetails loadMerchantPointsallmid(Merchant merchant);

	public List<MobileUser> loadMobileUserDetailsbytid(String tid);

	/*public String loadMerchantByMidAuditTrail(String mid);
*/

	//public MobileUser loadMobUserDetails(int merchantId);

	//List<MobileUser> loadMobileUserDetails(BigInteger id);

	

}
