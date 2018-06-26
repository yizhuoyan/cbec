/**
 * shidao
 * SysRoleDao.java
 * 2015年10月30日
 */
package com.neusoft.cq.cbec.platform.support.dao;

import org.springframework.stereotype.Repository;

import com.neusoft.cq.cbec.common.dao.support.SingleTableDaoSupport;
import com.neusoft.cq.cbec.common.dao.support.Sql;
import com.neusoft.cq.cbec.platform.dao.SystemRoleDao;
import com.neusoft.cq.cbec.platform.entity.SystemRoleEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 用户角色dao
 *
 * @author root@yizhuoyan.com
 */
@Repository
public class SystemRoleDaoImpl extends SingleTableDaoSupport<SystemRoleEntity> implements SystemRoleDao{

public SystemRoleDaoImpl(){
  super("sys_role", "id,code,name,remark");
}

@Override
public void obj2row(PreparedStatement ps, SystemRoleEntity m) throws Exception{
  int i = 1;
  ps.setString(i++, m.getId());
  ps.setString(i++, m.getCode());
  ps.setString(i++, m.getName());
  ps.setString(i++, m.getRemark());
}

@Override
public void joinOnFunctionality(String roleId, String functionalityId) throws Exception{
  executeInsert("rel_role_functionality(role_id,functionality_id)", roleId, functionalityId);
}

@Override
public void disjoinOnFunctionality(String roleId, String functionalityId) throws Exception{
  executeDelete("rel_role_functionality(role_id,functionality_id)", roleId, functionalityId);
}

@Override
public void disjoinOnFunctionality(String roleId) throws Exception{
  executeDelete("rel_role_functionality(role_id)", roleId);

}

  @Override
  public void disjoinOnUser(String roleId) throws Exception {
    executeDelete("rel_user_role(role_id)", roleId);
  }

  public List<SystemRoleEntity> selectByUserId(String userId) throws Exception{
  Sql sql = Sql.select(this.generateSelectColumns("r"))
                .from(this.tableName, " r")
                .join("rel_user_role rel on rel.role_id=r.id")
                .where("rel.user_id=?");
  return this.executesQuerySql(sql, this::row2obj, userId);
}

public List<SystemRoleEntity> selectByFunctionalityId(String functionalityId)
    throws Exception{
  Sql sql = Sql.select(generateSelectColumns("r"))
                .from(this.tableName, " r")
                .join("rel_role_functionality rel on rel.role_id=r.id ")
                .where("rel.functionality_id=?");
  return this.executesQuerySql(sql, this::row2obj, functionalityId);
}
public List<SystemRoleEntity> selectByUserIdAndFunctionalityId(String userId, String functionalityId)
    throws Exception{
  Sql sql = Sql.select(generateSelectColumns("r"))
                .from(this.tableName, " r")
                .join("rel_user_role uor on r.id=uor.role_id")
                .join("sys_user u on u.id=uor.user_id")
                .join("rel_role_functionality rof on rof.role_id=r.id")
                .join("sys_functionality f on f.id=rof.functionality_id")
                .where("u.id=? and f.id=?");
  return this.executesQuerySql(sql, this::row2obj, userId,functionalityId);
}

@Override
protected SystemRoleEntity row2obj(ResultSet rs) throws Exception{
  SystemRoleEntity m = new SystemRoleEntity();
  int i = 1;
  m.setId(rs.getString(i++));
  m.setCode(rs.getString(i++));
  m.setName(rs.getString(i++));
  m.setRemark(rs.getString(i++));
  return m;
}
}
