package com.neusoft.cq.cbec.platform.entity;

import lombok.Data;

import java.util.Map;

import com.neusoft.cq.cbec.common.util.KeyValueMap;

/**
 * 系统配置项目
 *
 * @author Administrator
 */
@Data
public class SystemConfigEntity {
    /**
     * 唯一标识符
     */
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 内容
     */
    private String value;
    /**
     * 描述
     */
    private String remark;
    /**
     * 状态
     * 1=正常
     * 0=停止使用
     * 9=只读
     */
    private int status;


    public Map toJSON() {
        KeyValueMap map = new KeyValueMap(5);

        map.put("id", this.id);
        map.put("name", this.name);
        map.put("value", this.value);
        map.put("status", this.status);
        if (this.remark != null) {
            map.put("remark", this.remark);
        }
        return map;
    }

}
