
package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface NonMerchantWebDao extends BaseDAO {

	public Merchant findByUserName(final String username);

	

	Merchant loadMerchantByID(long id);

	void listNonMerchantUser(PaginationBean<Merchant> paginationBean,
			ArrayList<Criterion> props);

}
