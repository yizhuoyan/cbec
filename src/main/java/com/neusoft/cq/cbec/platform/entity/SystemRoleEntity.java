/**
 * shidao
 * SysRoleModel.java
 * 2015年10月30日
 */
package com.neusoft.cq.cbec.platform.entity;

import java.util.List;
import java.util.Map;

import com.neusoft.cq.cbec.common.util.KeyValueMap;

import lombok.Data;

/**
 * @author root@yizhuoyan.com
 */
@Data
public class SystemRoleEntity {

private static final long serialVersionUID = 6361545273496684832L;

/**
 * 唯一标识符
 */
private String id;
/**
 * 名字
 */
private String name;
/**
 * 代号
 */
private String code;
/**
 * 描述
 */
private String remark;
/**
 * 角色下功能模块
 */
private List<SystemFunctionalityEntity> functionalitys;

  public Map toJSON() {
    KeyValueMap map = KeyValueMap.of(4);
    map.put("id", this.getId());
    map.put("code", this.getCode());
    map.put("name", this.getName());
    if(this.remark!=null) {
        map.put("remark", this.getRemark());
    }
    return map;
  }

 


}
