package com.neusoft.cq.cbec.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.neusoft.cq.cbec.common.dao.support.SelectLikePo;

public interface CRUDDao<T>{

void insert(T entity) throws Exception;

int delete(String column, Object value) throws Exception;

void update(Serializable id, Map<String, Object> valueMap) throws Exception;

void update(Serializable id, String columnName, Object value) throws Exception;

boolean exist(String column, Object value) throws Exception;

T select(String column, Object value) throws Exception;

<T>T selectOneColumn(String uniqueColumnName,Object uniqueColumnValue,String selectColumnName) throws Exception;
Object[] selectColumns2Array(String uniqueColumnName,Object uniqueColumnValue,String... selectColumns) throws Exception;
Map<String,Object> selectColumns2Map(String uniqueColumnName,Object uniqueColumnValue,String... selectColumns) throws Exception;


List<T> selects(String column, Object value,String orderBy) throws Exception;
List<T> selects(String orderBy) throws Exception;

List<T> selectsByLike(SelectLikePo po) throws Exception;
    int selectsByLikeOnPagination(List<T> pageData,SelectLikePo po) throws Exception;

}
