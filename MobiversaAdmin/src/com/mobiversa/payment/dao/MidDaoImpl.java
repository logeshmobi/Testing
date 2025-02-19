package com.mobiversa.payment.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MidDaoImpl extends BaseDAOImpl implements MidDao {

}
