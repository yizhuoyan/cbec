package com.neusoft.cq.cbec.platform.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.neusoft.cq.cbec.common.validatation.MaxLength;
@Data
public class SysRolePo {
    /**
     * 代号
     */
    @NotBlank
    @MaxLength(32)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @MaxLength(32)
    private String name;
    /**
     * 描述
     */
    @MaxLength(512)
    private String remark;





}
