/*
 * shidao
 * SupperManagerFunctionImpl.java
 * 2015年10月31日	
 */
package com.neusoft.cq.cbec.platform.support.function;

import static com.neusoft.cq.cbec.common.util.AssertThrowUtil.assertFalse;
import static com.neusoft.cq.cbec.common.util.AssertThrowUtil.assertNotNull;
import static com.neusoft.cq.cbec.common.util.PlatformUtil.trim;
import static com.neusoft.cq.cbec.common.util.PlatformUtil.uuid12;
import static com.neusoft.cq.cbec.common.validatation.ParameterValidator.$;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.neusoft.cq.cbec.common.dao.support.SelectLikePo;
import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.common.validatation.ParameterObjectValidator;
import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;
import com.neusoft.cq.cbec.platform.function.SystemRoleManageFunction;
import com.neusoft.cq.cbec.platform.po.SysRolePo;

/**
 * @author root@yizhuoyan.com
 */
@Service
public class SystemRoleManageFunctionImpl extends AbstractFunctionSupport implements SystemRoleManageFunction {

	@Override
	public SystemRoleEntity addRole(SysRolePo po) throws Exception {
		ParameterObjectValidator.throwIfFail(po);
		// 代号不能重复
		String code = po.getCode();
		assertFalse("already-exist", roleDao.exist("code", code), code);
		SystemRoleEntity model = new SystemRoleEntity();
		model.setId(uuid12());
		model.setCode(code);
		model.setName(po.getName());
		model.setRemark(po.getRemark());

		this.roleDao.insert(model);
		return model;
	}

	@Override
	public SystemRoleEntity checkRoleDetail(String id) throws Exception {
		id = $("id", id);
		SystemRoleEntity model = this.roleDao.select("id", id);
		return model;
	}

	@Override
	public SystemRoleEntity modifyRole(String id, SysRolePo po) throws Exception {
		id = $("id", id);
		ParameterObjectValidator.throwIfFail(po);
		// 旧数据
		SystemRoleEntity old = this.roleDao.select("id", id);
		assertNotNull("not-exist.id", old, id);

		KeyValueMap needUpdateMap = new KeyValueMap(4);
		// 1.代号
		String newCode = po.getCode();
		if (!old.getCode().equals(newCode)) {
			// 不能重复
			assertFalse("already-exist.code", functionalityDao.exist("code", newCode), newCode);
			old.setCode(newCode);
			needUpdateMap.put("code", newCode);
		}
		// 2.名称
		String newName = po.getName();
		if (!po.getName().equals(newName)) {
			old.setName(newName);
			needUpdateMap.put("name", newName);
		}
		// 3.备注
		String newRemark = po.getRemark();

		if (!Objects.equals(newRemark, po.getRemark())) {

			old.setRemark(newRemark);
			needUpdateMap.put("remark", newRemark);
		}

		this.roleDao.update(id, needUpdateMap);
		return old;
	}

	@Override
	public List<SystemRoleEntity> listRole(String key) throws Exception {
		key = trim(key);
		List<SystemRoleEntity> list = this.roleDao
				.selectsByLike(SelectLikePo.of("code,name,remark", key).setOrderBy("code"));
		return list;
	}

	@Override
	public List<SystemFunctionalityEntity> listSystemFunctionalityOfRole(String roleId) throws Exception {
		roleId = $("roleId", roleId);
		List<SystemFunctionalityEntity> list = this.functionalityDao.selectByRoleId(roleId);
		return list;
	}

	@Override
	public void grantSystemFunctionalitysToRole(String roleId, String... functionalityIds) throws Exception {
		roleId = $("roleId", roleId);
		if (functionalityIds.length == 0) {
			// 说明是清空
			this.roleDao.disjoinOnFunctionality(roleId);
			return;
		}
		// 1清理功能模块id
		Set<String> functionalitySet = new HashSet<>(functionalityIds.length);
		SystemFunctionalityEntity f = null;
		for (String fid : functionalityIds) {
			fid = trim(fid);
			f = this.functionalityDao.select("id", fid);
			assertNotNull("功能不存在{1}", f, fid);
			functionalitySet.add(fid);
			// 如果添加子功能，把父功能模块加上
			String parentId = f.getParentId();
			while(parentId != null) {
				functionalitySet.add(parentId);
				parentId = this.functionalityDao.selectOneColumn("id", parentId, "parent_Id");
			};
		}
		// 先清空
		this.roleDao.disjoinOnFunctionality(roleId);
		// 添加
		for (String fid : functionalitySet) {
			this.roleDao.joinOnFunctionality(roleId, fid);
		}

	}

	@Override
	public List<SystemUserEntity> listUserOfRole(String roleId) throws Exception {
		roleId = $("roleId", roleId);
		List<SystemUserEntity> list = this.userDao.selectByRoleId(roleId);
		return list;
	}

	@Override
	public void deleteSystemRole(String id) throws Exception {
		id = $("id", id);
		SystemRoleEntity r = roleDao.select("id", id);
		assertNotNull("not-exist.id", r, id);
		// 删除角色和功能关联关系
		roleDao.disjoinOnFunctionality(id);
		// 删除角色和用户关联关系
		roleDao.disjoinOnUser(id);
		// 删除角色
		roleDao.delete("id", id);
	}
}
