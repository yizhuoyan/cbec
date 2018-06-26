package com.neusoft.cq.cbec.common.dto;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.platform.entity.SystemFunctionalityEntity;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 用户上下文
 *
 * @author Administrator
 */
@Data
public class UserContext {
	private static final ThreadLocal<UserContext> THREAD_LOCAL=new ThreadLocal<>();
	
    //用户账户信息
    private String userId;
    private String name;
    private String account;
    private boolean firstLogin;
    private String avator;
    private String lastLoginIp;


    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后一次修改密码时间
     */
    private Date lastModPassTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否锁定
     */
    private boolean locked;


    private String currentLoginIp;


    //用户拥有角色
    private LinkedHashMap<String, SystemRoleEntity> rolesMap;

    //所有功能
    private LinkedHashMap<String, SystemFunctionalityEntity> functionalitysMap;
    //菜单功能
    private List<SystemFunctionalityEntity> menuFunctionalitys;
    //登陆凭据
    private String token;


    static private UserContext getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserContext userContext = (UserContext) session.getAttribute(UserContext.class.getName());
            if (userContext != null) {
                return userContext;
            }
        }
        UserContext uc=new UserContext();
        uc.setUserId("t1");
        //throw new PlatformException("not-login-in");
        return uc;
    }
    static public UserContext getCurrentUser() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getCurrentUser(req);
    }
    static public String getCurrentUserId() {
    	return getCurrentUser().getUserId();
    }

    static public void saveCurrentUser(UserContext uc) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        req.getSession().setAttribute(UserContext.class.getName(), uc);
    }

    public Object toJson() {
        KeyValueMap map = new KeyValueMap(12);
        map.put("token", this.token);
        map.put("id", this.userId);
        map.put("account", this.account);
        map.put("name", this.name);
        map.put("avator", this.avator);
        if (this.locked) {
            map.put("locked", true);
        }
        if (this.firstLogin) {
            map.put("firstLogin", true);
        } else {
            map.put("lastLoginTime", this.lastLoginTime);
            map.put("lastLoginIp", this.lastLoginIp);
        }

        map.put("lastModPassTime", this.lastModPassTime);
        map.put("createTime", this.createTime);
        map.put("loginIp", this.currentLoginIp);
        return map;
    }
}
