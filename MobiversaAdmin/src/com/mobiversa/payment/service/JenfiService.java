package com.mobiversa.payment.service;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.payment.util.BankCode;
import com.mobiversa.payment.util.PropertyLoad;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.UUID;

@Component
public class JenfiService {


    @Autowired
    private MerchantService merchantService;

    private static final Logger logger = Logger.getLogger(JenfiService.class.getName());

    public String checkJenfiEnabled(boolean isJenfiEnabled) {
        logger.info("Received isJenfiEnabled "+isJenfiEnabled);
        return isJenfiEnabled ? "true" : "false";
    }

    public void bindJenfiModelAttributes(Merchant merchant, Model model) {
        logger.info("Binding model attributes starts ..");
        UUID uuid = UUID.randomUUID();
        String uniqueID = uuid.toString();

        MerchantInfo merchantInfo = null;
        try {
            if (StringUtils.isBlank(uniqueID)) {
                logger.error("Failed to generate a valid UUID.");
                return;
            }

            merchantInfo = this.merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));
            int rowsAffected;
            if (merchantInfo == null) {
                logger.info("MerchantInfo not found. Inserting new entry in DB.");
                rowsAffected = this.merchantService.insertMerchantInfoForJenfi(String.valueOf(merchant.getId()), uniqueID);
                logger.info("Inserted new MerchantInfo. Rows affected: " + rowsAffected);
                merchantInfo = this.merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));
            } else if (StringUtils.isBlank(merchantInfo.getJenfiRef())) {
                logger.info("JenfiRef is blank. Updating MerchantInfo in DB.");
                rowsAffected = this.merchantService.updateuniqueIDForJenfi(String.valueOf(merchant.getId()), uniqueID);
                logger.info("Updated JenfiRef in MerchantInfo. Rows affected: " + rowsAffected);
                merchantInfo = this.merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));
            } else {
                logger.info("No update required. Existing JenfiRef: " + merchantInfo.getJenfiRef());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while processing jenfi " + e);
            throw e;
        }

        if (merchantInfo != null) {
            uniqueID = merchantInfo.getJenfiRef();
        }


        logger.info("uniqueID id after db operation " + uniqueID);
        String bicCode = BankCode.getBankBicCode(merchant.getBankName());
        String patnerRef = PropertyLoad.getFile().getProperty("partner_ref");
        String baseUrl = PropertyLoad.getFile().getProperty("base_url");
        String secretApiKey = PropertyLoad.getFile().getProperty("secret_api_key");
        String currencyType = PropertyLoad.getFile().getProperty("currencyType");
        String merchant_ref = uniqueID + "-" + merchant.getId();

        logger.info(String.format("merchant_ref: %s, currencyType: %s, patnerRef: %s, baseUrl: %s, secretApiKey: %s, bicCode: %s",
                merchant_ref, currencyType, patnerRef, baseUrl, secretApiKey, bicCode));


        model.addAttribute("merchant_ref", merchant_ref);
        model.addAttribute("bicCode", bicCode);
        model.addAttribute("currencyType", currencyType);
        model.addAttribute("patnerRef", patnerRef);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("secretApiKey", secretApiKey);
    }
}

