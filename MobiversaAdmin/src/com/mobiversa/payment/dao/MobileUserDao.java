package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.KManager;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteUser;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MobileUserStatusHistory;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.UMKManager;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.common.dto.MobileUserDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.RegMobileUser;

public interface MobileUserDao extends BaseDAO {

	public void listMobileUser(PaginationBean<MobileUser> paginationBean, ArrayList<Criterion> props);

	public TID getTID(MobileUser mobileUser);

	public void updateMobileUserStatus(Long id, CommonStatus status, MobileUserStatusHistory history);

	public void findByUserNames(final String username, final PaginationBean<MobileUserDTO> paginationBean);

	public MobileUserStatusHistory loadMobileStatusHistoryID(MobileUser mobileUser);

	public List<MobileUser> getMobileUser(final Merchant merchant);

	public void merchantBasedMobileUsers(final String username, final PaginationBean<MobileUser> paginationBean);

	public List<Long> listMobileUsers(Merchant merchant);

	public List<Long> listTIDUsers(Merchant currentMerchant);

	// mobile user updated 31052016 (2)
	public int addDeviceIdData(String did);

	// new method 26/05/2016
	public String loadMidData(String merchant);

	public void changePwdMobileUser(MobileUser mobileUser);

	public void editMobileUserDetails(MobileUser mobileUser);

	public RegMobileUser loadMobileUserDeviceId(String deviceId);

	// new demo method mobileuser 20062016

	public TerminalDetails loadDeviceId(String deviceId);

	public int updateKManager(String refNo, String tid);

	public List<KManager> loadRefNoToTid();

	public void listMobileUserDetails(PaginationBean<MobileUser> paginationBean, ArrayList<Criterion> props,
			String date, String date1);

	// new method for wifi 21062017
	public int loadTerminalDetailsByMid(String mid, String connectType);

	public List<TerminalDetails> loadTerminalDetailsByMidAndType(String mid, String connectType);

	public MobileUser loadMobileUserbyTidAndType(String tid, String connectType);

	public TerminalDetails loadTerminalDetailsByTid(String tid);

	public List<MobileUser> listMobileUserexpt(ArrayList<Criterion> props, final String date, final String date1);

	public MobileUserData loadMobileUserByTid(String tid);

	public MobileUser loadMobileUserbyTid(String tid);

	// public void updateMobileUserByTid(String tid,String preAuth,long id);

	public void updateMobileUserByTid(String tid, String preAuth, long id, String enableBoost, String enableMoto);

	public void updateTerminalDetailsByTid(TerminalDetails terminalDetails);

	public MobileUser loadMobileUserByIdAndName(long id, String username);

	public MobileUser loadMobileUserDeviceMapping();

	public MobileUser loadMobileUserBoostandMoto(Long id);

	public List<MobileUser> loadMobileUserByFK(Long id);

	public String loadMobileUserByFKBoost(Long id);

	public String loadMobileUserByFKMoto(Long id);

	public List<MobileUser> loadMobileUserByIdAndEmail(long id, String username);

	public List<MobileUser> loadMobileUserByIdAndContact(long id, String contact);

	public Merchant loadIserMidDetails(Long id);

	public List<TerminalDetails> loadTerminalDetailsByMid(String mid);

	public List<MobileUser> loadMobileUserDetails(Long id);

	public Merchant loadMidDetails(Long id);

	public MobileUser loadMobileUsertidDetails(String tid);

	// public int updateMobileuser(String tid, String motoTid);

	public int updateMobileuserEzypass(String tid, String ezypassTid, String updateType);

	public MobileUser loadMobileUserMototidDetails(String motoTid);

	public MobileUser loadMobileUserEzypasstidDetails(String ezypassTid);

	public int updateEzywireMobileuser(String tid, String tid2, String updateType, String preAuth);

	public int updateMotoMobileuser(String motoTid, String tid, String updateType);

	public TerminalDetails loadTerminalDetailsByTidAndMotoTid(String tid, String motoTid);

	public MobileUser loadMobileUserEzyRectidDetails(String ezyrecTid);

	public int updateEzyRecMobileuser(String ezyrecTid, String searchTid, String updateType);

	public TerminalDetails loadTerminalDetailsByAnyTid(RegMobileUser tid);

	public void updateMobileUserWithTid(MobileUser regmob);
	// void updateMobileUserWithTid(long id,String username,String tid,String
	// preAuth,String motoTid);

	public TerminalDetails loadTerminalDetailsByActivationcode(String activationCode);

	public String loadBusinessName(final String mid);

	public List<MobileUser> loadMobileUser();

	public int updateUM_KManager(String um_refNo, String um_tid);

	public TerminalDetails loadTerminalDetailsByAnyTids(RegMobileUser regMob);

	public List<MobileUser> loadMobileUsersbyMerchantFK(String string);

	public MobileUser loadGrabPayTid(String gPayTid);

	public MobileUser loadMobileUserbyMerchantFK(String mercantid);

	public List<UMKManager> loadUmRefNoToTid();

	UMMidTxnLimit loadDetByMid(String mid);

	int updateUMTxnLimit(String hashkey, String dtl, String mid, String redirecturl);

	public List<MobileUser> loadUmMobileUserDetails(Long id);

	UMMidTxnLimit loadUmMidTxnLimitDetails(String motoMid);

	MobileUser loadMobileUserEzywaytidDetails(String ezywayTid);

	MobiLiteUser loadMobiliteUserByFk(Long merchantId);

	UMMidTxnLimit loadDtlMidDetails(Long id);

	MobileUser getMobileUserData(Merchant merchant);

}
