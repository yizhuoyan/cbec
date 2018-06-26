package com.neusoft.cq.cbec.brandvendor.support.function;

import static com.neusoft.cq.cbec.common.util.AssertThrowUtil.*;
import static com.neusoft.cq.cbec.common.validatation.ParameterValidator.*;

import java.util.Objects;

import static com.neusoft.cq.cbec.common.util.PlatformUtil.*;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.cq.cbec.brandvendor.dao.BrandVendorDao;
import com.neusoft.cq.cbec.brandvendor.entity.BrandVendorEntity;
import com.neusoft.cq.cbec.brandvendor.function.BrandVendorFunction;
import com.neusoft.cq.cbec.brandvendor.po.BrandVendorPo;
import com.neusoft.cq.cbec.common.dto.UserContext;
import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.common.validatation.ParameterObjectValidator;
import com.neusoft.cq.cbec.platform.entity.SystemConfigEntity;

/**
 */
@Service
public class BrandVendorFunctionImpl implements BrandVendorFunction {
	@Autowired
	BrandVendorDao bvDao;

	@Override
	public BrandVendorEntity addBrandVendor(BrandVendorPo po) throws Exception {
		ParameterObjectValidator.throwIfFail(po, Default.class);

		BrandVendorEntity e = new BrandVendorEntity();
		String currentUserId = UserContext.getCurrentUserId();
		e.setId(currentUserId);
		e.setCnName(po.getCnName());
		e.setEnName(po.getEnName());
		e.setIntroduction(po.getIntroduction());
		e.setGmcReportType(po.getGmcReportType());
		e.setGmcReportUrl(po.getGmcReportUrl());
		return e;
	}

	@Override
	public BrandVendorEntity modifyBrandVendor(String id, BrandVendorPo po) throws Exception {
		ParameterObjectValidator.throwIfFail(po, Default.class);
		id = $("id", id);
		BrandVendorEntity old = bvDao.select("id", id);
		KeyValueMap map=new KeyValueMap(5);
		String cnName=po.getCnName();
		if(!Objects.equals(cnName, old.getCnName())) {
			old.setCnName(cnName);
			map.put("name_cn", cnName);
		}
		String enName=po.getEnName();
		if(!Objects.equals(enName, old.getEnName())) {
			old.setCnName(enName);
			map.put("name_en", enName);
		}
		String introduction=po.getIntroduction();
		if(!Objects.equals(introduction, old.getIntroduction())) {
			old.setIntroduction(introduction);
			map.put("introduction", introduction);
		}
		String gmcType=po.getGmcReportType();
		if(!Objects.equals(gmcType, old.getGmcReportType())) {
			old.setGmcReportType(gmcType);
			map.put("GMC_REPORT_TYPE", gmcType);
		}
		String gmcUrl=po.getGmcReportUrl();
		if(!Objects.equals(gmcUrl, old.getGmcReportUrl())) {
			old.setGmcReportUrl(gmcUrl);
			map.put("GMC_REPORT_URL", gmcUrl);
		}
		bvDao.update(id, map);
		return old;
	}

	@Override
	public BrandVendorEntity loadBrandVendor(String id) throws Exception {
		id = $("id", id);
		BrandVendorEntity e = bvDao.select("id", id);
		assertNotNull("数据不存在{1}", e, id);
		return e;
	}

}
