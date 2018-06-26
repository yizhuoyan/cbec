package com.neusoft.cq.cbec.platform.support.function;

import org.springframework.stereotype.Service;

import com.neusoft.cq.cbec.common.dao.support.SelectLikePo;
import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.common.validatation.ParameterObjectValidator;
import com.neusoft.cq.cbec.platform.entity.SystemConfigEntity;
import com.neusoft.cq.cbec.platform.function.SystemConfigFunction;
import com.neusoft.cq.cbec.platform.po.SystemConfigPo;

import javax.validation.groups.Default;

import static com.neusoft.cq.cbec.common.util.AssertThrowUtil.*;
import static com.neusoft.cq.cbec.common.util.PlatformUtil.*;
import static com.neusoft.cq.cbec.common.validatation.ParameterValidator.$;

import java.util.List;
import java.util.Objects;

/**
 */
@Service
public class SystemConfigFunctionImpl extends AbstractFunctionSupport implements SystemConfigFunction {

    @Override
    public List<SystemConfigEntity> listSystemConfig(String key)
            throws Exception {
        key = trim(key);
        List<SystemConfigEntity> result = this.configDao.selectsByLike(
                SelectLikePo.of("name,value,remark",key)
                        .setOrderBy("name"));
        return result;
    }

    @Override
    public SystemConfigEntity checkSystemConfigDetail(String id) throws Exception {
        id = $("id", id);
        SystemConfigEntity item = configDao.select("id", id);
        assertNotNull("not-exist.id", item, id);
        return item;
    }

    @Override
    public SystemConfigEntity addSystemConfig(SystemConfigPo po)
            throws Exception {
        ParameterObjectValidator.throwIfFail(po, Default.class);
        // 配置命名不能重复
        String name = po.getName();
        assertFalse("already-exist.name", configDao.exist("name", name), name);

        SystemConfigEntity item = new SystemConfigEntity();
        item.setId(uuid12());
        item.setName(po.getName());
        item.setValue(po.getValue());
        item.setRemark(trim(po.getRemark()));
        int status = po.getStatus();
        item.setStatus(status);
        this.configDao.insert(item);
        return item;
    }


    @Override
    public SystemConfigEntity modifySystemConfig(String id, SystemConfigPo po) throws Exception {
        id = $("id", id);
        SystemConfigEntity old = configDao.select("id", id);
        assertNotNull("not-exist.id", old, id);
        ParameterObjectValidator.throwIfFail(po);
        KeyValueMap needUpdate = new KeyValueMap(4);

        String newName = po.getName();
        if (!Objects.equals(newName, old.getName())) {
            assertFalse("dataExist", configDao.exist("name", newName));
            needUpdate.put("name", newName);
            old.setName(newName);
        }

        String newValue = po.getValue();
        if (!Objects.equals(newValue, old.getValue())) {
            needUpdate.put("value", newValue);
            old.setValue(newValue);
        }

        String newRemark = po.getRemark();
        if (!Objects.equals(newRemark, old.getRemark())) {
            needUpdate.put("remark", newRemark);
            old.setRemark(newRemark);
        }
        int newStatus = po.getStatus();
        if (newStatus == old.getStatus()) {
            needUpdate.put("status", newStatus);
            old.setStatus(newStatus);
        }
        configDao.update(id, needUpdate);
        return old;
    }


}
