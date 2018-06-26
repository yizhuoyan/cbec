package com.neusoft.cq.cbec.platform.po;

import lombok.Data;

import javax.validation.constraints.*;

import com.neusoft.cq.cbec.common.validatation.MaxLength;
import com.neusoft.cq.cbec.common.validatation.MustIn;


/**
 * @author yizhuoyan@
 */
@Data
public class SysUserPo{
/**
 * 账户
 */
@NotBlank
@MaxLength(16)
private String account;
/**
 * 名字
 */
@NotBlank
@MaxLength(16)
private String name;
/**
 * 头像地址
 */
@MaxLength(256)
private String portraitPath;
/**
 * 状态
 */
@MustIn("0/1")
private int status;
/**
 * 备注
 */
@MaxLength(512)
private String remark;


}
