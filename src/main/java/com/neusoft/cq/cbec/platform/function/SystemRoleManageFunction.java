/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.neusoft.cq.cbec.platform.function;

import org.springframework.transaction.annotation.Transactional;

import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;
import com.neusoft.cq.cbec.platform.po.SysRolePo;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemRoleManageFunction {



SystemRoleEntity addRole(SysRolePo po) throws Exception;

SystemRoleEntity modifyRole(String id, SysRolePo po) throws Exception;

List<SystemFunctionalityEntity> listSystemFunctionalityOfRole(String roleId) throws Exception;

List<SystemUserEntity> listUserOfRole(String roleId) throws Exception;

void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds) throws Exception;

SystemRoleEntity checkRoleDetail(String id) throws Exception;

List<SystemRoleEntity> listRole(String key) throws Exception;
    void deleteSystemRole(String id)throws Exception;




}
