package com.mobiversa.payment.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.service.AdminService;

@SuppressWarnings("unused")
@Controller
public class UserProfileController extends BaseController {
	
	@Autowired
	private AdminService adminService;
	
	static final Logger logger = Logger.getLogger(UserProfileController.class.getName());
	
	//@RequestMapping(value = { "", "/", "/**" }, method = RequestMethod.GET)
	public String defaultPage() {
	
		return "redirect:/bank/user";
	}
	
	
	@RequestMapping(value = { "/admProf/userDetails" }, method = RequestMethod.GET)
	public String bankUserDetails(final Model model, final Principal principal) {
		PageBean pageBean = new PageBean("User Details", "home/userProfile",
				Module.ADMIN, "user/sideMenuAdminWeb");
		System.out.println("User Name :"+principal.getName());
		BankUser bankUser = adminService.loadBankUser(principal.getName());
		System.out.println("list Bank user details");
		model.addAttribute("pageBean", pageBean);
		//model.addAttribute("mobileUser", mobileUser);
		//System.out.println("Data : "+agent.getName());
		model.addAttribute("user", bankUser);
		System.out.println("testcase");
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/AdminMerchantProfile/changePassword" }, method = RequestMethod.GET)
	public String admmerchantDetails(final Model model,@PathVariable final long id, final Principal principal) {
		PageBean pageBean = new PageBean("User Details", "home/AdminMerchantchangePwd",
				Module.ADMIN, "user/sideMenuAdminWeb");
		
		logger.info("id checking: "+id);
		System.out.println("User Name :"+principal.getName());
		Merchant merchant = adminService.loadAdminMerchant(principal.getName());
		System.out.println("list merchant user details");
		model.addAttribute("pageBean", pageBean);
		//model.addAttribute("mobileUser", mobileUser);
		//System.out.println("Data : "+agent.getName());
		model.addAttribute("user", merchant);
		System.out.println("testcase10");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/admProf/changePassWordbyAdmin" }, method = RequestMethod.GET)
	public String changePassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Login Person change password");
		PageBean pageBean = new PageBean("userProfile", "home/userchangePassword",
				Module.ADMIN, "user/sideMenuAdminWeb");
		//Merchant merchant = merchantService.loadMerchant(principal.getName());
		model.addAttribute("pageBean", pageBean);
		
		HttpSession session = request.getSession();
	    session.setAttribute("MerchantUserName",  principal.getName());
	    session.setAttribute("adminUserName",  principal.getName());
		//model.addAttribute("mobileUser", mobileUser);
		//model.addAttribute("merchant", merchant);
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/admProfileconfirm/adminConfirmPassword" }, method = RequestMethod.POST)
	public String confirmPassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Old PassWord"+request.getParameter("password")+"New Password"+request.getParameter("newPassword"));
		int count=adminService.changeUserPassWord(principal.getName(), request.getParameter("newPassword"), request.getParameter("password"));
		PageBean pageBean=null;
		if(count==1){
			logger.info("Success");
		 pageBean = new PageBean("User Profile", "home/changeUPasswordSuccess",
				Module.ADMIN, "user/sideMenuAdminWeb");
		 
				AuditTrail auditTrail=adminService.updateAuditTrailByAdmin(principal.getName(),
						principal.getName(), "changePassword");
				if(auditTrail.getUsername()!=null)
				{
					logger.info("Admin :"+ auditTrail.getUsername()+" Password Successfully Changed by Admin: "+auditTrail.getModifiedBy());;
				}
			
		}else{
			logger.info("Failure");
			 pageBean = new PageBean("User Profile", "home/changeUPasswordFailure",
						Module.ADMIN, "user/sideMenuAdminWeb");
		}
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
	}

}
