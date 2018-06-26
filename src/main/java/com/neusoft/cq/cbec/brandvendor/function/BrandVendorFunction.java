package com.neusoft.cq.cbec.brandvendor.function;

import org.springframework.transaction.annotation.Transactional;

import com.neusoft.cq.cbec.brandvendor.entity.BrandVendorEntity;
import com.neusoft.cq.cbec.brandvendor.po.BrandVendorPo;

/**
 */
@Transactional
public interface BrandVendorFunction {

  BrandVendorEntity addBrandVendor(BrandVendorPo po)throws Exception;
  BrandVendorEntity modifyBrandVendor(String id,BrandVendorPo po)throws Exception;
  BrandVendorEntity loadBrandVendor(String id)throws Exception;

}
