package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.dao.AgentDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.UserDao;
import com.mobiversa.payment.dto.Request;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.RandomPassword;
import com.mobiversa.payment.util.ResponseDetails;
import com.postmark.java.Attachment;
import com.postmark.java.MerchantActivation;
import com.postmark.java.MsgDto;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import com.postmark.java.PostmarkResponse;
import com.postmark.java.TempletFields;

@Controller
@RequestMapping(value = ResetPasswordController.URL_BASE)
public class ResetPasswordController extends BaseController {

    private static Logger logger = Logger
            .getLogger(ResetPasswordController.class);
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MerchantDao merchantDAO;

    @Autowired
    private AgentDao agentDAO;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserDao userDAO;

    public static final String URL_BASE = "/resetPwd";

    @RequestMapping(value = {"/", "", "/**"})
    public String defaultPage() {
        logger.info("default url");
        return "redirect:" + URL_BASE + "/resetPwdBymerchant";
    }



    @RequestMapping(value = {"/resetPwdByUser"}, method = RequestMethod.GET)
    public String resetPwdByUser(@RequestParam("username") String username, ModelMap model) {

        logger.info("Given Merchant username is: "+ username);
        if(username != null || !username.trim().isEmpty()) {

            Merchant merchant = merchantService.validateMerchantUserName(username);;
            Agent agent = agentService.validateAgentUserName(username);
            BankUser bankUser = adminService.validateAdminUserName(username);

            if(merchant != null && (merchant.getPassword() == null || merchant.getPassword().trim().isEmpty())) {
                logger.info("Merchant password is empty");
                model.addAttribute("username",username);
                return "admin/resetPasswordNew";
            }
            else if(agent != null && (agent.getPassword() == null || agent.getPassword().trim().isEmpty())) {
                logger.info("Agent password is empty");
                model.addAttribute("username",username);
                return "admin/resetPasswordNew";
            }
            else if(bankUser != null && (bankUser.getPassword() == null || bankUser.getPassword().trim().isEmpty())) {
                logger.info("BankUser password is empty");
                model.addAttribute("username",username);
                return "admin/resetPasswordNew";
            }
            logger.info("Link Expired");
            return "admin/linkExpired";
        }
        return "admin/linkExpired";
    }


    public boolean sendMailToResetPwd(MsgDto md, TempletFields tempField, String emailId) {

        logger.info("sendMailToResetPwd" + emailId);
        boolean result = true;
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        headers.add(new NameValuePair("HEADER", "test"));

        String fromAddress = "info@gomobi.io";
        String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
        String toAddress = emailId;
        //String ccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_CCMAIL");
        String bccMail = PropertyLoader.getFile().getProperty("RESETPWD_BCCMAIL");
        //String bccMail = "mathew@mobiversa.com";
        String subject = PropertyLoader.getFile().getProperty("RESETPWD_SUBJECT");

        //String subject = "[Important] Your Mobi Web Portal Account Password Has Been Changed!";

        String emailBody = MerchantActivation.sentResetPwdTempletContent(tempField);

        List<Attachment> attachments = new ArrayList<Attachment>();

        Attachment mobiImg = new Attachment("mobiImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("MOBIIMG"), "cid:mobiImg");

        Attachment fbImg = new Attachment("mobi_facebook.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("FBIMG"), "cid:fbImg");

        Attachment twitImg = new Attachment("twitImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("TWITIMG"), "cid:twitImg");

        Attachment InstaImg = new Attachment("InstaImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("INSTAIMG"), "cid:InstaImg");

			/*Attachment InstaImg = new Attachment("InstaImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("INSTAIMGUPD"), "cid:InstaImg");*/

        Attachment YoutubeImg = new Attachment("YoutubeImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("YOUTUBEIMG"), "cid:YoutubeImg");

        Attachment linkedInImg = new Attachment("linkedInImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("LINKEDINIMG"), "cid:linkedInImg");

        Attachment emailImg = new Attachment("emailImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("EMAILIMG"), "cid:emailImg");

        Attachment tollfreeImg = new Attachment("tollfreeImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("TOLLFREEIMG"), "cid:tollfreeImg");

        Attachment telephoneImg = new Attachment("telephoneImg.jpg", "image/jpg",
                PropertyLoader.getFile().getProperty("TELEPHONEIMG"), "cid:telephoneImg");

        Attachment webImg = new Attachment(
                "webImg.png",
                "image/jpg", PropertyLoader.getFile().getProperty("WEBIMG"),
                "cid:webImg");
			/*Attachment activationBannerImg = new Attachment(
					"activationBannerImg.png",
					"image/jpg",PropertyLoader.getFile().getProperty("ACTIVATIONBANNERIMG"),
					"cid:activationBannerImg");*/
        Attachment pwdResetBannerImg = new Attachment(
                "pwdResetBannerImg.png",
                "image/jpg", PropertyLoader.getFile().getProperty("PWDRESETIMG"),
                "cid:pwdResetBannerImg");

        //attachments.add(activationBannerImg);
        attachments.add(pwdResetBannerImg);
        attachments.add(webImg);
        attachments.add(mobiImg);
        attachments.add(fbImg);
        attachments.add(twitImg);
        attachments.add(InstaImg);
        attachments.add(linkedInImg);
        attachments.add(emailImg);
        attachments.add(tollfreeImg);
        attachments.add(telephoneImg);
        attachments.add(YoutubeImg);
        PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, null, bccMail, subject,
                emailBody, true, "test-email", null, attachments);
        PostmarkClient client = new PostmarkClient(apiKey);

        try {
            PostmarkResponse mailSent = client.sendMessage(message);
            logger.info("mailSent status::" + mailSent.getStatus());
            if (mailSent.getStatus().equals("SUCCESS")) {
                logger.info("mailSent status SUCCESS");
                result = true;
                logger.info("Merchant password rest Email Sent Successfully to" + emailId);
                logger.info("result::" + result);

            }
        } catch (PostmarkException pe) {

            result = false;
            logger.info("Invalid Signature Base64 String" + result);

        }

        return result;
    }


    @RequestMapping(value = {"/resetByPwdByMailId"}, method = RequestMethod.POST)
    public String resetByPwdByMailId(
            @RequestParam("email") String emailId,
            @RequestParam(required = false, defaultValue = "1") final int currPage,
            final HttpServletRequest request, final Model model,
            final Principal principal) {

        logger.info("resetByPwdByMailId" + emailId);
        TempletFields tempField = new TempletFields();
        boolean result = false;
        String statusMsg = null;
        boolean isValid = true;
        Merchant merchant = merchantService.validateMerchantEmailId(emailId);
        Agent agent = agentService.validateAgentEmailId(emailId);
        BankUser bankUser = adminService.validateAdminEmailId(emailId);

        if (merchant != null) {
            if (emailId.equals(merchant.getEmail())) {
                logger.info("merchant" + merchant);
                RandomPassword rpwd = new RandomPassword();
                String genPwd = rpwd.generateRandomString();
                logger.info("genPwd" + genPwd);
                merchant.setPassword(encoder.encode(genPwd));

                tempField.setSalutation(merchant.getSalutation());
                tempField.setFirstName(merchant.getContactPersonName());
                tempField.setUserName(merchant.getUsername());
                tempField.setPassword(genPwd);
                tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                MsgDto md = new MsgDto();
                md.setFirstName(merchant.getContactPersonName());
                md.setUserName(merchant.getUsername());
                md.setPassword(genPwd);
                md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                result = sendMailToResetPwd(md, tempField, emailId);
                logger.info("result " + result);
                logger.info("About to add Merchant ");
                merchant = merchantDAO.saveOrUpdateEntity(merchant);
                statusMsg = "Email sent";
                logger.info("Merchant Added Successfully");
            }
        } else if (agent != null) {
            if (emailId.equals(agent.getEmail())) {
                RandomPassword rpwd = new RandomPassword();
                String genPwd = rpwd.generateRandomString();
                agent.setPassword(encoder.encode(genPwd));

                tempField.setSalutation(agent.getSalutation());
                tempField.setFirstName(agent.getContact());
                tempField.setUserName(agent.getUsername());
                tempField.setPassword(genPwd);
                tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                MsgDto md = new MsgDto();
                md.setFirstName(agent.getContact());
                md.setUserName(agent.getUsername());
                md.setPassword(genPwd);
                md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                result = sendMailToResetPwd(md, tempField, emailId);

                logger.info("About to add Merchant ");
                agent = agentDAO.saveOrUpdateEntity(agent);
                logger.info("Agent Added Successfully");
            }

        } else if (bankUser != null) {


            if (emailId.equals(bankUser.getEmail())) {

                RandomPassword rpwd = new RandomPassword();
                String genPwd = rpwd.generateRandomString();
                bankUser.setPassword(encoder.encode(genPwd));

                tempField.setSalutation(bankUser.getSalutation());
                tempField.setFirstName(bankUser.getContact());
                tempField.setUserName(bankUser.getUsername());
                tempField.setPassword(genPwd);
                tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                MsgDto md = new MsgDto();
                md.setFirstName(bankUser.getContact());
                md.setUserName(bankUser.getUsername());
                md.setPassword(genPwd);
                md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

                result = sendMailToResetPwd(md, tempField, emailId);

                logger.info("About to add Merchant ");
                bankUser = userDAO.saveOrUpdateEntity(bankUser);
                logger.info("Agent Added Successfully");

            }


        } else {
            statusMsg = "Please enter the registered valid Email Id";
            isValid = false;
        }

        if (!isValid) {
            return "admin/forgotPasswordDetailsInvalid";

        } else if (!result) {
            return "admin/forgotPasswordDetailsFailure";

        } else {

            return "admin/forgotPasswordDetailsSuccess";
        }

    }


    @RequestMapping(value = {"/sendOtpToUserIfExist"}, method = RequestMethod.POST)
    public String sendOtpToUserIfExist(
            @RequestParam("username") String uname,
            @RequestParam(required = false, defaultValue = "1") final int currPage,
            final HttpServletRequest request, final Model model,
            final Principal principal) {


        String loggedUser = null;

        logger.info("forgetPwdByUserName" + uname);
        TempletFields tempField = new TempletFields();
        boolean result = false;
        String statusMsg = null;
        String username = null;
        String mobileNo = null;
        String email = null;
        boolean isValid = true;

        Merchant merchant = merchantService.validateMerchantUserName(uname);
        Agent agent = agentService.validateAgentUserName(uname);
        BankUser bankUser = adminService.validateAdminUserName(uname);

        if (merchant != null) {
            loggedUser = "merchant";
            logger.info("logged user is :" + loggedUser);
            Request inData = new Request();

            if (merchant.getContactPersonPhoneNo() != null) {
                inData.setMobileNo(merchant.getContactPersonPhoneNo());
                logger.info("Mobile No" + inData.getMobileNo());
            } else {
                inData.setMobileNo("");
            }

            if (merchant.getEmail() != null) {
                inData.setEmail(merchant.getEmail());
                logger.info("Email " + inData.getEmail());
            } else {
                inData.setEmail("");
            }

            if (merchant.getSalutation() != null) {
                inData.setSalutation(merchant.getSalutation());
                logger.info("Salutation  " + inData.getEmail());
            } else {
                inData.setSalutation("Mr/Ms");
            }

            if (merchant.getContactPersonName() != null) {
                inData.setFirstName(merchant.getContactPersonName());
            } else {
                inData.setFirstName("");
            }

            inData.setService("WEB_REQ_FORGET_PASSWORD_OTP");
            inData.setUsername(merchant.getUsername());

            ResponseDetails data = MotoPaymentCommunication.ForgetPassword(inData);

            if (data.getResponseCode().equals("0000")) {
                // inserting the time into db to track the expiry time
//                setOtpExpiryTime(merchant);

                setOtpExpiryTime(merchant);

                logger.info("Success");
                logger.info("UserName :" + data.getResponseData().getUsername());
                logger.info("Email    :" + data.getResponseData().getEmail());
                logger.info("Mobile no:" + data.getResponseData().getMobileNo());

                if (data.getResponseData().getUsername() != null) {
                    username = data.getResponseData().getUsername();
                } else {
                    username = "";
                }
                if (data.getResponseData().getMobileNo() != null) {
                    mobileNo = data.getResponseData().getMobileNo();
                } else {
                    mobileNo = "";
                }
                if (data.getResponseData().getEmail() != null) {
                    email = data.getResponseData().getEmail();
                } else {
                    email = "";
                }


                model.addAttribute("username", username);
                model.addAttribute("mobileNo", mobileNo);
                model.addAttribute("email", email);
                model.addAttribute("otpTime", getExpiredTimeDifference(String.valueOf(merchant.getId()),"MERCHANT"));
                logger.info("OTP expiry time:" + getExpiredTimeDifference(String.valueOf(merchant.getId()),"MERCHANT"));


                return "admin/resetPasswordDetailsNew";

            } else {
                logger.info("Failure");
                statusMsg = data.getResponseDescription();
                return "admin/resetPasswordDetailsNewInvalid";

            }
        } else if (agent != null) {
            loggedUser = "agent";
            logger.info("logged user is :" + loggedUser);
            Request inData = new Request();

            if (agent.getPhoneNo() != null) {
                inData.setMobileNo(agent.getPhoneNo());
                logger.info("Mobile No " + inData.getMobileNo());
            } else {
                inData.setMobileNo("");
            }

            if (agent.getMailId() != null) {
                inData.setEmail(agent.getMailId());
                logger.info("Email " + inData.getEmail());
            } else {
                inData.setEmail("");
            }

            if (agent.getSalutation() != null) {
                inData.setSalutation(agent.getSalutation());
                logger.info("Salutation  " + inData.getEmail());
            } else {
                inData.setSalutation("Mr/Ms");
            }

            if (agent.getFirstName() != null) {
                inData.setFirstName(agent.getFirstName());
            } else {
                inData.setFirstName("");
            }

            inData.setService("WEB_REQ_FORGET_PASSWORD_OTP");
            inData.setUsername(agent.getUsername());

            ResponseDetails data = MotoPaymentCommunication.ForgetPassword(inData);

            if (data.getResponseCode().equals("0000")) {

                setOtpExpiryTime(agent);
                logger.info("Success");
                logger.info("UserName :" + data.getResponseData().getUsername());
                logger.info("Email    :" + data.getResponseData().getEmail());
                logger.info("Mobile no:" + data.getResponseData().getMobileNo());

                if (data.getResponseData().getUsername() != null) {
                    username = data.getResponseData().getUsername();
                } else {
                    username = "";
                }
                if (data.getResponseData().getMobileNo() != null) {
                    mobileNo = data.getResponseData().getMobileNo();
                } else {
                    mobileNo = "";
                }
                if (data.getResponseData().getEmail() != null) {
                    email = data.getResponseData().getEmail();
                } else {
                    email = "";
                }


                model.addAttribute("username", username);
                model.addAttribute("mobileNo", mobileNo);
                model.addAttribute("email", email);
                model.addAttribute("otpTime", getExpiredTimeDifference(String.valueOf(agent.getUsername()),"AGENT"));
                logger.info("OTP expiry time:" + getExpiredTimeDifference(String.valueOf(agent.getUsername()),"AGENT"));

                return "admin/resetPasswordDetailsNew";

            } else {
                logger.info("Failure");
                statusMsg = data.getResponseDescription();
                return "admin/resetPasswordDetailsNewInvalid";

            }


        } else if (bankUser != null) {
            loggedUser = "bankUser";
            logger.info("logged user is :" + loggedUser);
            Request inData = new Request();

            if (bankUser.getContact() != null) {
                inData.setMobileNo(bankUser.getContact());
                logger.info("Mobile No" + inData.getMobileNo());
            } else {
                inData.setMobileNo("");
            }

            if (bankUser.getEmail() != null) {
                inData.setEmail(bankUser.getEmail());
                logger.info("Email " + inData.getEmail());
            } else {
                inData.setEmail("");
            }

            if (bankUser.getSalutation() != null) {
                inData.setSalutation(bankUser.getSalutation());
                logger.info("Salutation  " + inData.getEmail());
            } else {
                inData.setSalutation("Mr/Ms");
            }

            if (bankUser.getFirstName() != null) {
                inData.setFirstName(bankUser.getFirstName());
            } else {
                inData.setFirstName("");
            }

            inData.setService("WEB_REQ_FORGET_PASSWORD_OTP");
            inData.setUsername(bankUser.getUsername());

            ResponseDetails data = MotoPaymentCommunication.ForgetPassword(inData);

            if (data.getResponseCode().equals("0000")) {
                setOtpExpiryTime(bankUser);
                logger.info("Success");
                logger.info("UserName :" + data.getResponseData().getUsername());
                logger.info("Email    :" + data.getResponseData().getEmail());
                logger.info("Mobile no:" + data.getResponseData().getMobileNo());

                if (data.getResponseData().getUsername() != null) {
                    username = data.getResponseData().getUsername();
                } else {
                    username = "";
                }
                if (data.getResponseData().getMobileNo() != null) {
                    mobileNo = data.getResponseData().getMobileNo();
                } else {
                    mobileNo = "";
                }
                if (data.getResponseData().getEmail() != null) {
                    email = data.getResponseData().getEmail();
                } else {
                    email = "";
                }


                model.addAttribute("username", username);
                model.addAttribute("mobileNo", mobileNo);
                model.addAttribute("email", email);
                model.addAttribute("otpTime", getExpiredTimeDifference(String.valueOf(bankUser.getUsername()),"BANKUSER"));
                logger.info("OTP expiry time:" + getExpiredTimeDifference(String.valueOf(bankUser.getUsername()),"BANKUSER"));


                return "admin/resetPasswordDetailsNew";

            } else {
                logger.info("Failure");
                statusMsg = data.getResponseDescription();
                return "admin/resetPasswordDetailsNewInvalid";

            }

        } else {
            statusMsg = "Invalid username";
            model.addAttribute("statusMsg", statusMsg);
            return "admin/resetPasswordNew";
        }

    }

    private String getExpiredTimeDifference(String idOrUsername,String userType) {

        if(userType.equals("MERCHANT")) {
            MerchantInfo merchantInfo = merchantService.loadMerchantInfoByFk(idOrUsername);
            LocalTime dbTime = LocalTime.parse(merchantInfo.getOtpExpiryTime());
            LocalTime currentTime = LocalTime.now();
            Duration duration = Duration.between(currentTime,dbTime);
            long secondsDifference = duration.getSeconds();
            return String.format("%02d", secondsDifference);
        }
        else if(userType.equals("AGENT")) {
            Agent currentUser = agentService.loadAgent(idOrUsername);
            LocalTime dbTime = LocalTime.parse(currentUser.getOtpExpiryTime());
            LocalTime currentTime = LocalTime.now();
            Duration duration = Duration.between(currentTime,dbTime);
            long secondsDifference = duration.getSeconds();
            return String.format("%02d", secondsDifference);
        }
        else if(userType.equals("BANKUSER")) {
            BankUser currentUser = adminService.loadBankUser(idOrUsername);
            LocalTime dbTime = LocalTime.parse(currentUser.getOtpExpiryTime());
            LocalTime currentTime = LocalTime.now();
            Duration duration = Duration.between(currentTime,dbTime);
            long secondsDifference = duration.getSeconds();
            return String.format("%02d", secondsDifference);
        }
        return null;
    }


    private void setOtpExpiryTime(Object user) {
        if (user==null){
            return;
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalDateTime newDateTime = currentDateTime.plusMinutes(1).plusSeconds(10);
        LocalDateTime newDateTime = currentDateTime.plusMinutes(2);
//        LocalDateTime newDateTime = currentDateTime.plusSeconds(20);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = timeFormatter.format(newDateTime);
        if (user instanceof Merchant) {
            Merchant merchant = (Merchant) user;

            //check if exist
            MerchantInfo merchantInfo = merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));
            if(merchantInfo != null) {
                merchantService.updateOtpTime(formattedTime, String.valueOf(merchant.getId()),"MERCHANT_FK","mobiversa.MERCHANT_INFO");
                logger.info(merchant.getUsername()+"'s formattedTime updated");
            } else {
            merchantService.insertMerchantInfoByFK(formattedTime, String.valueOf(merchant.getId()),"MERCHANT_FK","mobiversa.MERCHANT_INFO");
                logger.info(merchant.getUsername()+"'s formattedTime inserted");
            }
        } else if (user instanceof Agent) {
            Agent agent = (Agent) user;
            merchantService.updateOtpTime(formattedTime, String.valueOf(agent.getUsername()),"USERNAME","mobiversa.AGENT");
            logger.info(agent.getUsername()+"'s formattedTime updated ");
        } else if (user instanceof BankUser) {
            BankUser bankUser = (BankUser) user;
            merchantService.updateOtpTime(formattedTime, String.valueOf(bankUser.getUsername()),"USERNAME","mobiversa.BANK_USER");
            logger.info(bankUser.getUsername()+"'s formattedTime updated ");
        }
    }

    @RequestMapping(value = {"/resetPwdByUserName"}, method = RequestMethod.POST)
    public String resetPwdByUserName(
            @RequestParam("username") String uname,
            @RequestParam("mobileNo") String mno,
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam(required = false, defaultValue = "1") final int currPage,
            final HttpServletRequest request, final Model model,
            final Principal principal) {


        try {
            logger.info("resetByPwdByUserName" + uname);
            TempletFields tempField = new TempletFields();
            boolean result = false;
            String statusMsg = null;


            logger.info("User Name  :" + uname);
            logger.info("Mobile No  :" + mno);
            logger.info("Email      :" + email);
            logger.info("OTP        :" + otp);


            if (uname == null || uname.trim().isEmpty()) {
                logger.info("Failure");
                statusMsg = "InValid User Name";
                model.addAttribute("statusMsg", statusMsg);
                logger.info("InValid User Name::::::::::::");

            }
            if (mno == null && mno.trim().isEmpty() && email == null && email.trim().isEmpty()) {
                statusMsg = "InValid mobile number or email";
                model.addAttribute("statusMsg", statusMsg);
                logger.info("InValid mobile number or email::::::");

            }

            if (otp == null || otp.trim().isEmpty()) {
                statusMsg = "InValid OTP";
                model.addAttribute("statusMsg", statusMsg);
                logger.info("InValid OTP ::::");
            }

            Merchant merchant = merchantService.validateMerchantUserName(uname);
            Agent agent = agentService.validateAgentUserName(uname);
            BankUser bankUser = adminService.validateAdminUserName(uname);

            MobileOTP otp1 = merchantService.checkOTP(mno, email);
            if (otp1 != null) {
                logger.info("Optained OTP : " + otp1.getOptData());

                if (otp1.getOptData().equalsIgnoreCase(otp)) {
                    logger.info("Valid OTP");
//                    model.addAttribute("isOtpValid", "true");
                } else {
                    statusMsg = "InValid OTP";
                    logger.info("Invalid OTP");
                    model.addAttribute("invalidOtp", "Invalid OTP");
                    model.addAttribute("isOtpValid", "false");

                    //new chnages for otp time
                    if (merchant != null) {
                        String remainingTime = getExpiredTimeDifference(String.valueOf(merchant.getId()), "MERCHANT");
                        model.addAttribute("otpTime", Integer.parseInt(remainingTime) < 0 ? "0" : remainingTime);
                    }
                    else if (agent != null) {
                        String remainingTime = getExpiredTimeDifference(String.valueOf(agent.getUsername()), "AGENT");
                        model.addAttribute("otpTime", Integer.parseInt(remainingTime) < 0 ? "0" : remainingTime);
                    } else if (bankUser != null) {
                        String remainingTime = getExpiredTimeDifference(String.valueOf(bankUser.getUsername()), "BANKUSER");
                        model.addAttribute("otpTime", Integer.parseInt(remainingTime) < 0 ? "0" : remainingTime);
                    }
                    model.addAttribute("statusMsg", statusMsg);
                    model.addAttribute("username", uname);
                    model.addAttribute("mobileNo", mno);
                    model.addAttribute("email", email);
                    return "admin/resetPasswordDetailsNew";
                }
            } else {
                logger.info("OTP Not exist for the Mobile Number / Email");
                statusMsg = "OTP Not exist for the Mobile Number / Email";
                model.addAttribute("statusMsg", statusMsg);
                logger.info(statusMsg);
            }

            if (merchant != null) {
                MerchantInfo currentMerchantInfo = merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));
                checkOtpExpired(currentMerchantInfo.getOtpExpiryTime(),model);
            }
            else if (agent != null) {
                Agent currentAgent = agentService.loadAgent(agent.getUsername());
                checkOtpExpired(currentAgent.getOtpExpiryTime(),model);
            } else if (bankUser != null) {
                BankUser currentBankuser = merchantService.loadBankUserByUsername(bankUser.getUsername());
                checkOtpExpired(currentBankuser.getOtpExpiryTime(),model);
            }
            else {
                logger.warn("User is not exist - Null pointer Exception");
            }

            model.addAttribute("username", uname);
            model.addAttribute("mobileNo", mno);
            model.addAttribute("email", email);
            model.addAttribute("otp", otp);

        } catch (Exception e){
            logger.error("Exception occurred: " + e.getMessage(), e);
        }
        return "admin/resetPasswordDetailsNew";
    }


    @RequestMapping(value = {"/resetPwdByUserNameConfirmed"}, method = RequestMethod.POST)
    public String resetPwdByUserNameConfirmed(
            @RequestParam("username") String uname,
            @RequestParam("mobileNo") String mno,
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("npassword") String npwd,
            @RequestParam("cpassword") String cpwd,
            @RequestParam(required = false, defaultValue = "1") final int currPage,
            @RequestParam(value = "requestState") String requestState,
            final HttpServletRequest request, final Model model,
            final Principal principal) {

        logger.info("requestState " + requestState);

        logger.info("resetByPwdByUserName" + uname);
        TempletFields tempField = new TempletFields();
        boolean result = false;
        String statusMsg = null;

        logger.info("User Name  :" + uname);
        logger.info("Mobile No  :" + mno);
        logger.info("Email      :" + email);
        logger.info("OTP        :" + otp);
        logger.info("Password   :" + cpwd);

        if (uname == null || uname.trim().isEmpty()) {
            logger.info("Failure");
            statusMsg = "InValid User Name";
            model.addAttribute("statusMsg", statusMsg);
            logger.info("InValid User Name::::::::::::");
        }
        if (mno == null && mno.trim().isEmpty() && email == null && email.trim().isEmpty()) {
            statusMsg = "InValid mobile number or email";
            model.addAttribute("statusMsg", statusMsg);
            logger.info("InValid mobile number or email::::::");
        }

        if (otp == null || otp.trim().isEmpty()) {
            statusMsg = "InValid OTP";
            model.addAttribute("statusMsg", statusMsg);
            logger.info("InValid OTP ::::");
        }
        if (cpwd == null || cpwd.trim().isEmpty()) {
            statusMsg = "InValid Password";
            model.addAttribute("statusMsg", statusMsg);
            logger.info("InValid Password :::::::");
        }

        MobileOTP otp1 = merchantService.checkOTP(mno, email);

        Merchant merchant = merchantService.validateMerchantUserName(uname);
        Agent agent = agentService.validateAgentUserName(uname);
        BankUser bankUser = adminService.validateAdminUserName(uname);

        if (merchant != null) {
            String genPwd = cpwd;
            logger.info(" Provided PWD : " + genPwd);
            logger.info("coming into merchant if condition : " + uname);
            model.addAttribute("IsPassChanged", "true");
            merchant.setPassword(encoder.encode(cpwd));
            merchantDAO.saveOrUpdateEntity(merchant);
//            return "admin/forgotPasswordDetailsSuccessNew";
        } else if (agent != null) {
            String genPwd = cpwd;
            logger.info(" Provided PWD : " + genPwd);
            model.addAttribute("IsPassChanged", "true");
            agent.setPassword(encoder.encode(cpwd));
            merchantDAO.saveOrUpdateEntity(agent);
//            return "admin/forgotPasswordDetailsSuccessNew";

        } else if (bankUser != null) {
            String genPwd = cpwd;
            logger.info(" Provided PWD : " + genPwd);
            model.addAttribute("IsPassChanged", "true");
            bankUser.setPassword(encoder.encode(cpwd));
            merchantDAO.saveOrUpdateEntity(bankUser);
//            return "admin/forgotPasswordDetailsSuccessNew";

        } else {
            statusMsg = "Invalid Username";
            model.addAttribute("statusMsg", statusMsg);
            model.addAttribute("IsPassChanged", "false");
        }


        model.addAttribute("username", uname);
        model.addAttribute("mobileNo", mno);
        model.addAttribute("email", email);
        model.addAttribute("otp", otp);


        if (requestState.equalsIgnoreCase("second")) {
            model.addAttribute("IsPassChanged", "true");
            return "admin/passwordReseted";
        }
        return "admin/resetPasswordDetailsNew";

    }

    public void checkOtpExpired(String OtpExpiryTime, Model model) {
        logger.info("OtpExpiryTime " + OtpExpiryTime);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime otpExpiryTime = LocalTime.parse(OtpExpiryTime, timeFormatter);
        LocalTime currentTime = currentDateTime.toLocalTime();

        if (currentTime.isBefore(otpExpiryTime) || currentTime.equals(otpExpiryTime)) {
            model.addAttribute("isExpired", "true");
            model.addAttribute("isOtpValid", "true");
            logger.info("OTP not expired.");
        } else {
            logger.info("OTP expired.");
            model.addAttribute("isExpired", "The OTP has expired. Click 'Resend OTP' to receive a new one.");
        }
    }

}