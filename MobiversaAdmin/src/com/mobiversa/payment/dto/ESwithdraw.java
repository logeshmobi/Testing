package com.mobiversa.payment.dto;

public class ESwithdraw {

	public String FinalPayableAmt;
	public String FinalWithdrawfee;
	public String FinalNetamt;
	
	public String FinalPayoutdate;

	public String Finalipdateone;
	public String Finalipdatetwo;
	public String Finalipdatethree;
	public String Finalipdatefour;
	public String Finalipdatefive;
	
	private String sucessdate;

	private String ezysettleVaild;

	public String getFinalPayableAmt() {
		return FinalPayableAmt;
	}

	public void setFinalPayableAmt(String finalPayableAmt) {
		FinalPayableAmt = finalPayableAmt;
	}

	public String getFinalWithdrawfee() {
		return FinalWithdrawfee;
	}

	public String getSucessdate() {
		return sucessdate;
	}

	public void setSucessdate(String sucessdate) {
		this.sucessdate = sucessdate;
	}

	public String getFinalPayoutdate() {
		return FinalPayoutdate;
	}

	public void setFinalPayoutdate(String finalPayoutdate) {
		FinalPayoutdate = finalPayoutdate;
	}

	public void setFinalWithdrawfee(String finalWithdrawfee) {
		FinalWithdrawfee = finalWithdrawfee;
	}

	public String getFinalNetamt() {
		return FinalNetamt;
	}

	public void setFinalNetamt(String finalNetamt) {
		FinalNetamt = finalNetamt;
	}

	public String getFinalipdateone() {
		return Finalipdateone;
	}

	public void setFinalipdateone(String finalipdateone) {
		Finalipdateone = finalipdateone;
	}

	public String getFinalipdatetwo() {
		return Finalipdatetwo;
	}

	public void setFinalipdatetwo(String finalipdatetwo) {
		Finalipdatetwo = finalipdatetwo;
	}

	public String getFinalipdatethree() {
		return Finalipdatethree;
	}

	public void setFinalipdatethree(String finalipdatethree) {
		Finalipdatethree = finalipdatethree;
	}

	public String getFinalipdatefour() {
		return Finalipdatefour;
	}

	public void setFinalipdatefour(String finalipdatefour) {
		Finalipdatefour = finalipdatefour;
	}

	public String getFinalipdatefive() {
		return Finalipdatefive;
	}

	public void setFinalipdatefive(String finalipdatefive) {
		Finalipdatefive = finalipdatefive;
	}

	public String getEzysettleVaild() {
		return ezysettleVaild;
	}

	public void setEzysettleVaild(String ezysettleVaild) {
		this.ezysettleVaild = ezysettleVaild;
	}

}
