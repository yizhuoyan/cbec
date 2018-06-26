/**
 * shidao
 * SystemConfigController.java
 * 2015年10月31日
 */
package com.neusoft.cq.cbec.platform.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.cq.cbec.common.dto.JsonResponse;
import com.neusoft.cq.cbec.common.web.springmvc.AbstractControllerSupport;
import com.neusoft.cq.cbec.platform.entity.SystemConfigEntity;
import com.neusoft.cq.cbec.platform.function.SystemConfigFunction;
import com.neusoft.cq.cbec.platform.po.SystemConfigPo;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */
@Controller
@RequestMapping("/platform/config")
public class SystemConfigManageController extends AbstractControllerSupport{
@Autowired
private SystemConfigFunction function;


public JsonResponse list(String key) throws Exception{
  List<SystemConfigEntity> result = function.listSystemConfig(key);

  return JsonResponse.ok(result,c->c.toJSON());
}

public JsonResponse get(String id) throws Exception{
  SystemConfigEntity m = function.checkSystemConfigDetail(id);
  return JsonResponse.ok(m.toJSON());
}

public JsonResponse mod(String id, SystemConfigPo po) throws Exception{
  function.modifySystemConfig(id, po);
  return JsonResponse.ok();
}

public JsonResponse add(SystemConfigPo po) throws Exception{
  function.addSystemConfig(po);
  return JsonResponse.ok();
}

}
