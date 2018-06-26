package com.neusoft.cq.cbec.brandvendor.entity;

import java.util.Map;

import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌商实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BrandVendorEntity extends SystemUserEntity {
	private String enName;
	private String cnName;
	private String gmcReportType;
	private String gmcReportUrl;
	private String introduction;

	public Map toJSON() {
		KeyValueMap m = KeyValueMap.of(6);
		m.put("id", this.getId());
		m.put("enName", this.getAccount());
		m.put("cnName", this.getName());
		m.put("gmcReportType", this.getStatus());
		m.put("gmcReportUrl", this.getAvator());
		m.put("introduction", this.getRemark());
		return m;
	}
}
