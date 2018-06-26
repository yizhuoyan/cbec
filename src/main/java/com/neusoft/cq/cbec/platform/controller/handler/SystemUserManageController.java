/**
 * shidao
 * UserManageFunction.java
 * 2015年10月31日
 */
package com.neusoft.cq.cbec.platform.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.cq.cbec.common.dto.JsonResponse;
import com.neusoft.cq.cbec.common.dto.PaginationQueryResult;
import com.neusoft.cq.cbec.common.util.PlatformUtil;
import com.neusoft.cq.cbec.common.web.springmvc.AbstractControllerSupport;
import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;
import com.neusoft.cq.cbec.platform.function.UserManagerFunction;
import com.neusoft.cq.cbec.platform.po.SysUserPo;

import java.util.List;


/**
 * @author root@yizhuoyan.com
 */
@Controller
@RequestMapping("/platform/user")
public class SystemUserManageController extends AbstractControllerSupport{

@Autowired
private UserManagerFunction function;

public JsonResponse list(String key, String pageNo, String pageSize)
    throws Exception{
  int pageNoInt = PlatformUtil.parseInt(pageNo, 0);
  int pageSizeInt = PlatformUtil.parseInt(pageSize, 0);
  PaginationQueryResult<SystemUserEntity> result = function.queryUser(key, pageNoInt, pageSizeInt);
  return JsonResponse.ok(result.toJSON(u->u.toJSON()));
}

public JsonResponse add(SysUserPo dto) throws Exception{
  SystemUserEntity model = function.addUser(dto);
  return JsonResponse.ok();
}

public JsonResponse del(String id) throws Exception{
  function.deleteUser(id);
  return JsonResponse.ok();
}

  public JsonResponse mod(String id, SysUserPo dto) throws Exception{
    SystemUserEntity model = function.modUser(id, dto);
    return JsonResponse.ok();
  }

public JsonResponse get(String id) throws Exception{
  SystemUserEntity model = function.checkUserDetail(id);
  return JsonResponse.ok(model.toJSON());
}

public JsonResponse grantRoles(String id, String roleIds) throws Exception{
  String[] roleIdArr=null;
  if((roleIds=PlatformUtil.trim(roleIds))==null){
    roleIdArr=new String[0];
  }else{
    roleIdArr=roleIds.split(",");
  }
  function.grantRoles(id,roleIdArr);
  return JsonResponse.ok();
}

public JsonResponse revokeRole(String id, String roleId) throws Exception{
  function.revokeRole(id, roleId);
  return JsonResponse.ok();
}

public JsonResponse ofRoles(String id) throws Exception{
  List<SystemRoleEntity> result = function.listRoleOfUser(id);
  return JsonResponse.ok(result,r->r.toJSON());
}


public JsonResponse glanceOwnFunctionalitys(String id) throws Exception{
  List<SystemFunctionalityEntity> result = function.glanceOwnFunctionalitys(id);
  return JsonResponse.ok(result,f->f.toJSON());
}


/**
 * 重置密码
 */
public JsonResponse resetPassword(String id) throws Exception{
  function.resetUserPassword(id);
  return JsonResponse.ok();
}

public JsonResponse lock(String id) throws Exception{
  function.lockUser(id);
  return JsonResponse.ok();
}

public JsonResponse unlock(String id, boolean enable) throws Exception{
  function.unlockUser(id);
  return JsonResponse.ok();
}



}
