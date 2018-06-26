/**
 * shidao
 * SupperManagerFunction.java
 * 2015年10月31日
 */
package com.neusoft.cq.cbec.platform.function;

import org.springframework.transaction.annotation.Transactional;

import com.neusoft.cq.cbec.platform.entity.SystemConfigEntity;
import com.neusoft.cq.cbec.platform.po.SystemConfigPo;

import java.util.List;

/**
 * 超级管理员功能
 *
 * @author root@yizhuoyan.com
 */
@Transactional
public interface SystemConfigFunction {


/**
 * 获取系统配置列表
 *
 * @param key 查询关键字
 * @return
 * @throws Exception
 */
List<SystemConfigEntity> listSystemConfig(String key) throws Exception;

/**
 * 查看系统配置项目详情
 *
 * @param id
 * @return
 * @throws Exception
 */
SystemConfigEntity checkSystemConfigDetail(String id) throws Exception;

/**
 * 新增系统配置项
 *
 * @param po 信息
 * @throws Exception
 */
SystemConfigEntity addSystemConfig(SystemConfigPo po) throws Exception;

/**
 * 修改某个系统配置项
 *
 * @param po 修改信息
 * @throws Exception
 */
SystemConfigEntity modifySystemConfig(String id, SystemConfigPo po) throws Exception;





}
