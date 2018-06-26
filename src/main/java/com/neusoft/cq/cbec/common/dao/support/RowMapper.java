package com.neusoft.cq.cbec.common.dao.support;

import java.sql.ResultSet;

/**
 */
@FunctionalInterface
public interface RowMapper<T>{

T map(ResultSet rs) throws Exception;
}
