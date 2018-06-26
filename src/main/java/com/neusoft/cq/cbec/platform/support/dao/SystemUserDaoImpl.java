package com.neusoft.cq.cbec.platform.support.dao;

import org.springframework.stereotype.Repository;

import com.neusoft.cq.cbec.common.dao.support.JDBCUtil;
import com.neusoft.cq.cbec.common.dao.support.SingleTableDaoSupport;
import com.neusoft.cq.cbec.common.dao.support.Sql;
import com.neusoft.cq.cbec.platform.dao.SystemUserDao;
import com.neusoft.cq.cbec.platform.entity.SystemUserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 用户dao
 *
 * @author Administrator
 */
@Repository
public class SystemUserDaoImpl extends SingleTableDaoSupport<SystemUserEntity> implements SystemUserDao{
public SystemUserDaoImpl(){
  super("sys_user", "id,account,name,password,avator,createTime,lastModPassTime,lastLoginTime,remark,status,loginIp", true);
}

@Override
public void obj2row(PreparedStatement ps, SystemUserEntity m) throws Exception{
  int i = 1;
  ps.setString(i++, m.getAccount());
  ps.setString(i++, m.getAvator());
  ps.setTimestamp(i++, JDBCUtil.toSqlTimestamp(m.getCreateTime()));
  ps.setString(i++, m.getId());
  ps.setTimestamp(i++, JDBCUtil.toSqlTimestamp(m.getLastLoginTime()));
  ps.setTimestamp(i++, JDBCUtil.toSqlTimestamp(m.getLastModPassTime()));
  ps.setString(i++, m.getLoginIp());
  ps.setString(i++, m.getName());
  ps.setString(i++, m.getPassword());
  ps.setString(i++, m.getRemark());
  ps.setInt(i++, m.getStatus());
}

public void joinOnRole(String userId, String roleId) throws Exception{
  this.executeInsert("rel_user_role(user_id,role_id)", userId, roleId);
}

public void disjoinOnRole(String userId, String roleId) throws Exception{
  this.executeDelete("rel_user_role(user_id,role_id)", userId, roleId);
}

public void disjoinOnRole(String userId) throws Exception{
  this.executeDelete("rel_user_role(user_id)", userId);
}

public List<SystemUserEntity> selectByRoleId(String roleId) throws Exception{
  Sql sql = Sql.select(generateSelectColumns("u"))
                .from(tableName, " u")
                .join("rel_user_role rel on rel.user_id=u.id")
                .join("sys_role r on r.id= rel.role_id")
                .where("u.status!=? and r.id=?");
  return this.executesQuerySql(sql, this::row2obj, SystemUserEntity.STATUS_DELETED,roleId);
}

@Override
protected SystemUserEntity row2obj(ResultSet rs) throws Exception{
  SystemUserEntity m = new SystemUserEntity();
  int i = 1;
  m.setAccount(rs.getString(i++));
  m.setAvator(rs.getString(i++));
  m.setCreateTime(rs.getTimestamp(i++));
  m.setId(rs.getString(i++));
  m.setLastLoginTime(rs.getTimestamp(i++));
  m.setLastModPassTime(rs.getTimestamp(i++));
  m.setLoginIp(rs.getString(i++));
  m.setName(rs.getString(i++));
  m.setPassword(rs.getString(i++));
  m.setRemark(rs.getString(i++));
  m.setStatus(rs.getInt(i++));

  return m;
}
}
