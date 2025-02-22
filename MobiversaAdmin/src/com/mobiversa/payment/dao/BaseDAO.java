package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.mobiversa.payment.controller.bean.PaginationBean;

public interface BaseDAO {

	public <E> E saveOrUpdateEntity(final E entity);

	public void saveOrUpdateEntities(final Iterable<? extends Object> entities);

	public <E> E loadEntityByKey(final Class<E> clazz, final Long key);

	public void getPaginationItemsByPage(final PaginationBean paginationBean,
			@SuppressWarnings("rawtypes") final Class clazz, final ArrayList<Criterion> props, final Order order);
	
	
	//new method for update merchant 05/07/2016
	
	public <E> E saveOrUpdateEntity1(final E entity);

//	void payoutTxnList(PaginationBean<PayoutModel> paginationBean, ArrayList<Criterion> criterionList, String date,
//			String date1);

}
