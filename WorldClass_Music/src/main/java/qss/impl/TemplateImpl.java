package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.TemplateDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.TemplateVo;

@Service("templateService")
public class TemplateImpl extends EgovAbstractServiceImpl implements Qss {
    @Resource(name = "templateDao")
    private TemplateDao templateDao;

    @Override
    public List<?> SelectListData(CommonVo commonVo) throws Exception {
        return templateDao.SelectListData((TemplateVo) commonVo);
    }

    @Override
    public int DataByCnt(CommonVo commonVo) {
        return templateDao.DataByCnt(commonVo);
    }

    @Override
    public CommonVo SelectData(CommonVo commonVo) throws Exception {
        return null;
    }

    @Override
    public List<String> StringList(CommonVo commonVo) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public int InsertData(CommonVo commonVo) throws Exception {
        return templateDao.InsertData((TemplateVo) commonVo);
    }

    @Override
    @Transactional
    public int UpdateData(CommonVo commonVo) throws Exception {
        return templateDao.UpdateData((TemplateVo) commonVo);
    }

    @Override
    @Transactional
    public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
        return templateDao.DeleteDataByObjectParam((TemplateVo) commonVo);
    }

    @Override
    public String SelectByReturnString(CommonVo commonVo) throws Exception {
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public HashMap SelectDataMap(CommonVo commonVo) throws Exception {
        return null;
    }

    @Override
    public String InsertReturnKeyData(CommonVo commonVo) {
        return null;
    }

    @Override
    public List<?> SelectCodeData(String string) {
        return null;
    }
}
