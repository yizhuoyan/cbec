/**
 * shidao
 * SysUserDao.java
 * 2016年1月15日
 */
package com.neusoft.cq.cbec.platform.dao;

import java.sql.Connection;
import java.util.List;

import com.neusoft.cq.cbec.common.dao.CRUDDao;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;

/**
 * @author root@yizhuoyan.com
 */
public interface SystemUserDao extends CRUDDao<SystemUserEntity> {

List<SystemUserEntity> selectByRoleId(String roleId) throws Exception;

void joinOnRole(String userId, String roleId) throws Exception;

void disjoinOnRole(String userId, String roleId) throws Exception;

void disjoinOnRole(String userId) throws Exception;

Connection getConnection()throws  Exception;
}