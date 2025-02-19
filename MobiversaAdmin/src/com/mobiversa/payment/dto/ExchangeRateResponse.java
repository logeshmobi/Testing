package com.mobiversa.payment.dto;

import java.util.List;

public class ExchangeRateResponse {
	private int status;
	private String message;
	private List<ExchangeRateData> data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ExchangeRateData> getData() {
		return data;
	}

	public void setData(List<ExchangeRateData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExchangeRateResponse [status=" + status + ", message=" + message + ", data=" + data + "]";
	}

	public static class ExchangeRateData {
		private Long id;
		private String createdAt;
		private String date;
		private String baseCurrency;
		private String targetCurrency;
		private Double exchangeRate;
		private int conversionRequests;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getBaseCurrency() {
			return baseCurrency;
		}

		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}

		public String getTargetCurrency() {
			return targetCurrency;
		}

		public void setTargetCurrency(String targetCurrency) {
			this.targetCurrency = targetCurrency;
		}

		public Double getExchangeRate() {
			return exchangeRate;
		}

		public void setExchangeRate(Double exchangeRate) {
			this.exchangeRate = exchangeRate;
		}

		public int getConversionRequests() {
			return conversionRequests;
		}

		public void setConversionRequests(int conversionRequests) {
			this.conversionRequests = conversionRequests;
		}

		@Override
		public String toString() {
			return "ExchangeRateData [id=" + id + ", createdAt=" + createdAt + ", date=" + date + ", baseCurrency="
					+ baseCurrency + ", targetCurrency=" + targetCurrency + ", exchangeRate=" + exchangeRate
					+ ", conversionRequests=" + conversionRequests + "]";
		}
	}
}
