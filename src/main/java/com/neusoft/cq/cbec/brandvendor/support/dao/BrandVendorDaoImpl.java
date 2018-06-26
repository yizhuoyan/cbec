package com.neusoft.cq.cbec.brandvendor.support.dao;

import org.springframework.stereotype.Repository;

import com.neusoft.cq.cbec.brandvendor.dao.BrandVendorDao;
import com.neusoft.cq.cbec.brandvendor.entity.BrandVendorEntity;
import com.neusoft.cq.cbec.common.dao.support.SingleTableDaoSupport;
import com.neusoft.cq.cbec.common.dao.support.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 */
@Repository
public class BrandVendorDaoImpl extends SingleTableDaoSupport<BrandVendorEntity> implements BrandVendorDao {

    public BrandVendorDaoImpl() {
        super("qst_knowledge_point", "id,code,name,remark,children_Amount,parent_id,next_child_code",true);
    }

    @Override
    protected BrandVendorEntity row2obj(ResultSet rs) throws Exception {
        BrandVendorEntity m=new BrandVendorEntity();
        int i=1;
        
        return m;
    }

    @Override
    protected void obj2row(PreparedStatement ps, BrandVendorEntity e) throws Exception {

        int i=1;
    }

}
