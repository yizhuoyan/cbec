/**
 * shidao
 * SysFunctionalityDao1.java
 * 2016年1月15日
 */
package com.neusoft.cq.cbec.platform.dao;

import java.util.List;

import com.neusoft.cq.cbec.common.dao.CRUDDao;
import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;

/**
 * @author root@yizhuoyan.com
 */
public interface SystemFunctionalityDao extends CRUDDao<SystemFunctionalityEntity>{

void updateCodeCascade(String code, String newCode) throws Exception;

List<SystemFunctionalityEntity> selectByUserId(String userId) throws Exception;

    /**
     * 查找包括自己在内的所有后代
     * @param code
     * @return
     * @throws Exception
     */
    List<SystemFunctionalityEntity> selectDescendantByCode(String code) throws Exception;

List<SystemFunctionalityEntity> selectByStatusAndUserId(int status, String userId) throws Exception;

List<SystemFunctionalityEntity> selectByRoleId(String roleId) throws Exception;

    void disjoinOnRole(String functionalityId) throws Exception;

}