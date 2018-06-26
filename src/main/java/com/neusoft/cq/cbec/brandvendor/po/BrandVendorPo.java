package com.neusoft.cq.cbec.brandvendor.po;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 */
@Data
public class BrandVendorPo {
	@NotBlank
	private String enName;
	@NotBlank
	private String cnName;
	@NotBlank
	private String gmcReportType;
	@NotBlank
	private String gmcReportUrl;
	
	private String introduction;
}
