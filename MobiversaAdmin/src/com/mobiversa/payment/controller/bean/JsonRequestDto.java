package com.mobiversa.payment.controller.bean;

import java.util.List;

public class JsonRequestDto {

	private List<Long> merchantId;
	private String hostId;
	private String updateType;

	public JsonRequestDto(List<Long> merchantId, String hostId, String updateType) {
		this.merchantId = merchantId;
		this.hostId = hostId;
		this.updateType = updateType;
	}

	public List<Long> getMerchantId() {
		return merchantId;
	}

	public String getHostId() {
		return hostId;
	}

	public String getUpdateType() {
		return updateType;
	}
}
