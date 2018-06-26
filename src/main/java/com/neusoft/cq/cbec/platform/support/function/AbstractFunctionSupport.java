/**
 * shidao
 * FunctionTempldate.java
 * 2016年1月20日
 */
package com.neusoft.cq.cbec.platform.support.function;

import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.cq.cbec.platform.dao.SystemConfigDao;
import com.neusoft.cq.cbec.platform.dao.SystemFunctionalityDao;
import com.neusoft.cq.cbec.platform.dao.SystemRoleDao;
import com.neusoft.cq.cbec.platform.dao.SystemUserDao;
import com.neusoft.cq.cbec.platform.support.service.SystemConfigService;

/**
 * @author root@yizhuoyan.com
 */
public abstract class AbstractFunctionSupport{
@Autowired
protected SystemConfigService configService;
@Autowired
protected SystemFunctionalityDao functionalityDao;
@Autowired
protected SystemRoleDao roleDao;
@Autowired
protected SystemUserDao userDao;
@Autowired
protected SystemConfigDao configDao;


}
