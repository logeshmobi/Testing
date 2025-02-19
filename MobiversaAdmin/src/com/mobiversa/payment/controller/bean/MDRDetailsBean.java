package com.mobiversa.payment.controller.bean;

public class MDRDetailsBean {

	private Fpx fpx;
	private Cards cards;
	private Ewallet ewallet;
	private Payout payout;
	private MerchantDetail merchantDetail;

	public Fpx getFpx() {
		return fpx;
	}

	public void setFpx(Fpx fpx) {
		this.fpx = fpx;
	}

	public Cards getCards() {
		return cards;
	}

	public void setCards(Cards cards) {
		this.cards = cards;
	}

	public Ewallet getEwallet() {
		return ewallet;
	}

	public void setEwallet(Ewallet ewallet) {
		this.ewallet = ewallet;
	}

	public Payout getPayout() {
		return payout;
	}

	public void setPayout(Payout payout) {
		this.payout = payout;
	}

	public MerchantDetail getMerchantDetail() {
		return merchantDetail;
	}

	public void setMerchantDetail(MerchantDetail merchantDetail) {
		this.merchantDetail = merchantDetail;
	}

	public static class Fpx {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Fpx [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr + ", minimummdr="
					+ minimummdr + "]";
		}

	
		

	}

	public static class Cards {
		private Visa visa;
		private Master master;
		private Union union;

		public Visa getVisa() {
			return visa;
		}

		public void setVisa(Visa visa) {
			this.visa = visa;
		}

		public Master getMaster() {
			return master;
		}

		public void setMaster(Master master) {
			this.master = master;
		}

		public Union getUnion() {
			return union;
		}

		public void setUnion(Union union) {
			this.union = union;
		}

		@Override
		public String toString() {
			return "Cards [visa=" + visa + ", master=" + master + ", union=" + union + "]";
		}

	}

	public static class Visa {
		private String localdebitmdr;
		private String localcreditmdr;
		private String foriegndebitmdr;
		private String foriegncreditmdr;
		public String getLocaldebitmdr() {
			return localdebitmdr;
		}
		public void setLocaldebitmdr(String localdebitmdr) {
			this.localdebitmdr = localdebitmdr;
		}
		public String getLocalcreditmdr() {
			return localcreditmdr;
		}
		public void setLocalcreditmdr(String localcreditmdr) {
			this.localcreditmdr = localcreditmdr;
		}
		public String getForiegndebitmdr() {
			return foriegndebitmdr;
		}
		public void setForiegndebitmdr(String foriegndebitmdr) {
			this.foriegndebitmdr = foriegndebitmdr;
		}
		public String getForiegncreditmdr() {
			return foriegncreditmdr;
		}
		public void setForiegncreditmdr(String foriegncreditmdr) {
			this.foriegncreditmdr = foriegncreditmdr;
		}
		@Override
		public String toString() {
			return "Visa [localdebitmdr=" + localdebitmdr + ", localcreditmdr=" + localcreditmdr + ", foriegndebitmdr="
					+ foriegndebitmdr + ", foriegncreditmdr=" + foriegncreditmdr + "]";
		}

		

	}

	public static class Master {
		private String localdebitmdr;
		private String localcreditmdr;
		private String foriegndebitmdr;
		private String foriegncreditmdr;
		public String getLocaldebitmdr() {
			return localdebitmdr;
		}
		public void setLocaldebitmdr(String localdebitmdr) {
			this.localdebitmdr = localdebitmdr;
		}
		public String getLocalcreditmdr() {
			return localcreditmdr;
		}
		public void setLocalcreditmdr(String localcreditmdr) {
			this.localcreditmdr = localcreditmdr;
		}
		public String getForiegndebitmdr() {
			return foriegndebitmdr;
		}
		public void setForiegndebitmdr(String foriegndebitmdr) {
			this.foriegndebitmdr = foriegndebitmdr;
		}
		public String getForiegncreditmdr() {
			return foriegncreditmdr;
		}
		public void setForiegncreditmdr(String foriegncreditmdr) {
			this.foriegncreditmdr = foriegncreditmdr;
		}
		@Override
		public String toString() {
			return "Master [localdebitmdr=" + localdebitmdr + ", localcreditmdr=" + localcreditmdr
					+ ", foriegndebitmdr=" + foriegndebitmdr + ", foriegncreditmdr=" + foriegncreditmdr + "]";
		}

		

	}

	public static class Union {
		private String localdebitmdr;
		private String localcreditmdr;
		private String foriegndebitmdr;
		private String foriegncreditmdr;
		public String getLocaldebitmdr() {
			return localdebitmdr;
		}
		public void setLocaldebitmdr(String localdebitmdr) {
			this.localdebitmdr = localdebitmdr;
		}
		public String getLocalcreditmdr() {
			return localcreditmdr;
		}
		public void setLocalcreditmdr(String localcreditmdr) {
			this.localcreditmdr = localcreditmdr;
		}
		public String getForiegndebitmdr() {
			return foriegndebitmdr;
		}
		public void setForiegndebitmdr(String foriegndebitmdr) {
			this.foriegndebitmdr = foriegndebitmdr;
		}
		public String getForiegncreditmdr() {
			return foriegncreditmdr;
		}
		public void setForiegncreditmdr(String foriegncreditmdr) {
			this.foriegncreditmdr = foriegncreditmdr;
		}
		@Override
		public String toString() {
			return "Union [localdebitmdr=" + localdebitmdr + ", localcreditmdr=" + localcreditmdr + ", foriegndebitmdr="
					+ foriegndebitmdr + ", foriegncreditmdr=" + foriegncreditmdr + "]";
		}

	

	}

	public static class Ewallet {
		private Boost boost;
		private Grab grab;
		private Tng tng;
		private Spp spp;

		public Boost getBoost() {
			return boost;
		}

		public void setBoost(Boost boost) {
			this.boost = boost;
		}

		public Grab getGrab() {
			return grab;
		}

		public void setGrab(Grab grab) {
			this.grab = grab;
		}

		public Tng getTng() {
			return tng;
		}

		public void setTng(Tng tng) {
			this.tng = tng;
		}

		public Spp getSpp() {
			return spp;
		}

		public void setSpp(Spp spp) {
			this.spp = spp;
		}

		@Override
		public String toString() {
			return "Ewallet [boost=" + boost + ", grab=" + grab + ", tng=" + tng + ", spp=" + spp + "]";
		}

	}

	public static class Boost {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Boost [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr
					+ ", minimummdr=" + minimummdr + "]";
		}

		

	}

	public static class Grab {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Grab [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr
					+ ", minimummdr=" + minimummdr + "]";
		}

		

	}

	public static class Tng {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Tng [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr + ", minimummdr="
					+ minimummdr + "]";
		}

	

	}

	public static class Spp {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Spp [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr + ", minimummdr="
					+ minimummdr + "]";
		}

		
		

	}

	public static class Payout {
		private String merchantmdr;
		private String hostmdr;
		private String mobimdr;
		private String minimummdr;
		public String getMerchantmdr() {
			return merchantmdr;
		}
		public void setMerchantmdr(String merchantmdr) {
			this.merchantmdr = merchantmdr;
		}
		public String getHostmdr() {
			return hostmdr;
		}
		public void setHostmdr(String hostmdr) {
			this.hostmdr = hostmdr;
		}
		public String getMobimdr() {
			return mobimdr;
		}
		public void setMobimdr(String mobimdr) {
			this.mobimdr = mobimdr;
		}
		public String getMinimummdr() {
			return minimummdr;
		}
		public void setMinimummdr(String minimummdr) {
			this.minimummdr = minimummdr;
		}
		@Override
		public String toString() {
			return "Payout [merchantmdr=" + merchantmdr + ", hostmdr=" + hostmdr + ", mobimdr=" + mobimdr
					+ ", minimummdr=" + minimummdr + "]";
		}

	
		

	}

	public static class MerchantDetail {
		private String merchantName;
		private String mid;

		public String getMerchantName() {
			return merchantName;
		}

		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		@Override
		public String toString() {
			return "MerchantDetail [merchantName=" + merchantName + ", mid=" + mid + "]";
		}

	}

	@Override
	public String toString() {
		return "MDRDetailsBean [fpx=" + getString(fpx)+ ", cards=" + getString(cards) + ", ewallet=" + getString(ewallet) + ", payout=" + getpayoutString(payout)
				+ ", merchantDetail=" + getString(merchantDetail) + "]";
	}
	
	private static String getString(Object value) {
		if(value==null) {
			return "";
		}
		return value.toString();
	}
	private static String getpayoutString(Object value) {
		if(value==null) {
			return "0.0";
		}
		return value.toString();
	}
	
	
}