package com.neusoft.cq.cbec.platform.support.function;

import org.springframework.stereotype.Service;

import com.neusoft.cq.cbec.common.dao.support.SelectLikePo;
import com.neusoft.cq.cbec.common.dto.PaginationQueryResult;
import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.common.validatation.ParameterObjectValidator;
import com.neusoft.cq.cbec.common.validatation.validategroup.Mod;
import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;
import com.neusoft.cq.cbec.platform.function.UserManagerFunction;
import com.neusoft.cq.cbec.platform.po.SysUserPo;

import static com.neusoft.cq.cbec.common.util.AssertThrowUtil.*;
import static com.neusoft.cq.cbec.common.util.PlatformUtil.*;
import static com.neusoft.cq.cbec.common.validatation.ParameterValidator.$;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 管理员功能实现类
 *
 * @author Administrator
 */
@Service
public class UserManagerFunctionImpl extends AbstractFunctionSupport implements
        UserManagerFunction {


    public SystemUserEntity addUser(SysUserPo dto) throws Exception {
        // 验证dto
        ParameterObjectValidator.throwIfFail(dto);
        // 代号不能重复
        String account = dto.getAccount();
        assertFalse("already-exist.account", userDao.exist("account", account), account);
        // 获取默认密码
        String password = configService.getSystemConfig("USER-INIT-PASSWORD", "123456");
        SystemUserEntity model = new SystemUserEntity();
        model.setId(uuid12());
        model.setAccount(account);
        model.setCreateTime(new Date());
        model.setLastLoginTime(null);
        model.setLastModPassTime(null);
        model.setName(dto.getName());
        model.setPassword(password);
        model.setAvator(dto.getPortraitPath());
        model.setRemark(dto.getRemark());
        model.setStatus(dto.getStatus());
        this.userDao.insert(model);
        return model;
    }

    public SystemUserEntity modUser(String id, SysUserPo dto) throws Exception {
        // 验证dto
        ParameterObjectValidator.throwIfFail(dto, Mod.class);
        // id必须
        id = $("id", id);
        SystemUserEntity oldUser = userDao.select("id", id);
        assertNotNull("not-exist.id", oldUser, id);
        KeyValueMap needUpdate = new KeyValueMap(2);
        // 获取状态
        int newStatus = dto.getStatus();
        if (newStatus != oldUser.getStatus()) {
            needUpdate.put("status", newStatus);
            oldUser.setStatus(newStatus);
        }
        //获取备注
        String newRemark = dto.getRemark();
        if (!Objects.equals(newRemark, oldUser.getRemark())) {
            needUpdate.put("remark", newRemark);
            oldUser.setRemark(newRemark);
        }

        this.userDao.update(id, needUpdate);

        return oldUser;
    }

    public PaginationQueryResult<SystemUserEntity> queryUser(String key,
                                                         int pageNo, int pageSize) throws Exception {
        key = trim(key);
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            // 查询系统设置
            pageSize = configService.getSystemConfig("PAGINATION-PAGESIZE", 10);
        }

        List<SystemUserEntity> pageData = new ArrayList<>(pageSize);
        int total = userDao.selectsByLikeOnPagination(pageData,SelectLikePo.of("account,name,remark", key)
                        .setOrderBy("account,createTime")
                        .setPageNo(pageNo)
                        .setPageSize(pageSize)
        );
        PaginationQueryResult<SystemUserEntity> paginationResult = new PaginationQueryResult<SystemUserEntity>(total, pageData);
        paginationResult.setPageNo(pageNo);
        paginationResult.setPageSize(pageSize);
        return paginationResult;
    }


    @Override
    public void resetUserPassword(String id) throws Exception {
        id = $("id", id);
        // 1找到教师
        SystemUserEntity m = this.userDao.select("id", id);
        assertNotNull("not-exist", m);
        // 2找到初始化密码
        String initPassword = configService.getSystemConfig("STUDENT-INIT-PASSWORD", "123456");
        userDao.update(id, "password", initPassword);
    }


    @Override
    public void lockUser(String id) throws Exception {
        id = $("id", id);
        SystemUserEntity m = userDao.select("id", id);
        m.setStatus(0);
        userDao.update(id, "status", SystemUserEntity.STATUS_LOCKED);
    }

    @Override
    public void unlockUser(String id) throws Exception {
        id = $("id", id);
        SystemUserEntity m = userDao.select("id", id);
        m.setStatus(1);
        userDao.update(id, "status", SystemUserEntity.STATUS_NORMAL);
    }

    @Override
    public void deleteUser(String id) throws Exception {
        id = $("id", id);
        SystemUserEntity m = userDao.select("id", id);
        assertNotNull("not-exist.id", m, id);
        //删除用户，做逻辑删除
        userDao.update(id, "status", SystemUserEntity.STATUS_DELETED);


    }

    public SystemUserEntity checkUserDetail(String id) throws Exception {
        id = $("id", id);
        SystemUserEntity model = this.userDao.select("id", id);
        assertNotNull("not-exist.id", model, id);
        return model;
    }

    public List<SystemRoleEntity> listRoleOfUser(String userId)
            throws Exception {
        userId = $("userId", userId);
        List<SystemRoleEntity> roles = this.roleDao.selectByUserId(userId);
        return roles;
    }


    public List<SystemFunctionalityEntity> glanceOwnFunctionalitys(String userId)
            throws Exception {
        userId = $("userId", userId);
        List<SystemFunctionalityEntity> functionalitys = functionalityDao.selectByUserId(userId);
        List<SystemRoleEntity> belongRoles;
        for (SystemFunctionalityEntity f : functionalitys) {
            //加载角色
            belongRoles = roleDao.selectByUserIdAndFunctionalityId(userId, f.getId());
            f.setRoles(belongRoles);
        }
        return functionalitys;
    }

    public void grantRoles(String userId, String... roleIds)
            throws Exception {
        userId = $("userId", userId);
        //判断userId是否存在
        assertTrue("not-exist.userId", userDao.exist("id", userId), userId);
        //先撤销全部，
        userDao.disjoinOnRole(userId);
        //再重新授权
        for (String roleId : roleIds) {
            //判断roleid是否存在
            roleId = $("userId", roleId);
            assertTrue("not-exist.roleId", roleDao.exist("id", roleId));
            //撤销全部
            userDao.joinOnRole(userId, roleId);
        }
    }

    @Override
    public void revokeRole(String userId, String roleId)
            throws Exception {
        userId = $("userId", userId);
        roleId = $("roleId", roleId);
        this.userDao.disjoinOnRole(userId, roleId);
    }


}
