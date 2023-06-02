package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.CommonDao;
import qss.dao.FloorManagementDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.FloorFacilitiesVo;
import qss.vo.FloorKioskVo;
import qss.vo.FloorMappathVo;
import qss.vo.FloorShopVo;
import qss.vo.FloorVo;

@Service("floorManagementService")
public class FloorManagementImpl extends EgovAbstractServiceImpl implements Qss {
    @Resource(name = "floorManagementDao")
    private FloorManagementDao floorManagementDao;

    @Resource(name = "commonDao")
    private CommonDao commonDao;

    @Override
    public List<?> SelectListData(CommonVo commonVo) throws Exception {
        // TODO Auto-generated method stub
        return (List<?>) floorManagementDao.SelectListData((FloorVo) commonVo);
    }

    @Override
    public int DataByCnt(CommonVo commonVo) {
        if (StringUtils.equals("Floor_FloorShopOverlap", commonVo.getCaseString())) {
            return floorManagementDao.DataByCnt((FloorShopVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorMappathOverlap", commonVo.getCaseString())) {
            return floorManagementDao.DataByCnt((FloorMappathVo) commonVo);
        }

        return floorManagementDao.DataByCnt((FloorVo) commonVo);
    }

    @Override
    public CommonVo SelectData(CommonVo commonVo) {
        return floorManagementDao.SelectData((FloorVo) commonVo);
    }

    @Override
    public List<String> StringList(CommonVo commonVo) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public int InsertData(CommonVo commonVo) throws Exception {
        return floorManagementDao.InsertData((FloorVo) commonVo);
    }

    @Transactional
    public int insertListData(String queryList, List<CommonVo> commonVoList) {
        int insertCount = 0;
        return insertCount;
    }

    @Override
    @Transactional
    public int UpdateData(CommonVo commonVo) throws Exception {
        if (StringUtils.equals("Floor_FloorShopCreate", commonVo.getCaseString())) {
            return floorManagementDao.UpdateData((FloorShopVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorFacilitiesCreate", commonVo.getCaseString())) {
            return floorManagementDao.UpdateData((FloorFacilitiesVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorKioskCreate", commonVo.getCaseString())) {
            return floorManagementDao.UpdateData((FloorKioskVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorMappathCreate", commonVo.getCaseString())
                || StringUtils.equals("Floor_FloorMappathUpdate", commonVo.getCaseString())) {
            return floorManagementDao.UpdateData((FloorMappathVo) commonVo);
        }

        return floorManagementDao.UpdateData((FloorVo) commonVo);
    }

    @Transactional
    public int updateListData(String queryString, List<?> commonVoList) {
        if (StringUtils.equals("Floor_FloorShopListUpdate", queryString)) {
            return floorManagementDao.updateListData(queryString, commonVoList);
        } else if (StringUtils.equals("Floor_FloorFacilitiesListUpdate", queryString)) {
            return floorManagementDao.updateListData(queryString, commonVoList);
        } else if (StringUtils.equals("Floor_FloorKioskListCreate", queryString) || (StringUtils.equals("Floor_FloorKioskListUpdate", queryString))) {
            return floorManagementDao.updateListData(queryString, commonVoList);
        }

        return -1;
    }

    @Override
    public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
        if (StringUtils.equals("Floor_FloorShopDelete", commonVo.getCaseString())) {
            return floorManagementDao.DeleteData((FloorShopVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorFacilitiesDelete", commonVo.getCaseString())) {
            return floorManagementDao.DeleteData((FloorFacilitiesVo) commonVo);
        } else if (StringUtils.equals("Floor_FloorKioskDelete", commonVo.getCaseString())) {
            return floorManagementDao.DeleteData((FloorKioskVo) commonVo);
        }

        return floorManagementDao.DeleteData((FloorVo) commonVo);
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
        return floorManagementDao.InsertReturnKeyData((FloorVo) commonVo);
    }

    @Override
    public List<?> SelectCodeData(String string) {
        return null;
    }

}