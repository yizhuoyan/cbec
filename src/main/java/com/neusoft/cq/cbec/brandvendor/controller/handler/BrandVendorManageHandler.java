package com.neusoft.cq.cbec.brandvendor.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.cq.cbec.brandvendor.entity.BrandVendorEntity;
import com.neusoft.cq.cbec.brandvendor.function.BrandVendorFunction;
import com.neusoft.cq.cbec.brandvendor.po.BrandVendorPo;
import com.neusoft.cq.cbec.common.dto.JsonResponse;

import java.util.List;

/**
 */
@Controller
@RequestMapping("/brandvendor")
public class BrandVendorManageHandler {
    @Autowired
    private BrandVendorFunction fun;

    public JsonResponse add(BrandVendorPo po)throws  Exception{
        BrandVendorEntity m=fun.addBrandVendor(po);
        return JsonResponse.ok(m.toJSON());
    }
    
    public JsonResponse get(String id)throws  Exception{
        BrandVendorEntity m=fun.loadBrandVendor(id);
        return JsonResponse.ok(m.toJSON());
    }

    public JsonResponse mod(String id,BrandVendorPo po)throws  Exception{
        BrandVendorEntity m=fun.modifyBrandVendor(id, po);
        return JsonResponse.ok(m.toJSON());
    }
   

}
