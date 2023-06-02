package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.FloorFacilitiesVo;
import qss.vo.FloorKioskVo;
import qss.vo.FloorMappathVo;
import qss.vo.FloorShopVo;
import qss.vo.FloorVo;

@Repository("floorManagementDao")
public class FloorManagementDao extends EgovAbstractMapper {

    public List<?> SelectListData(FloorVo floorVo) {
        return (List<?>) selectList(floorVo.getCaseString(), floorVo);
    }

    public CommonVo SelectData(FloorVo floorVo) {
        return selectOne(floorVo.getCaseString(), floorVo);
    }

    public List<String> StringList(FloorVo floorVo) {
        return null;
    }

    public int InsertData(FloorVo floorVo) {
        int cnt = update(floorVo.getCaseString(), floorVo);
        return cnt;
    }

    public int UpdateData(FloorVo floorVo) {
        return update(floorVo.getCaseString(), floorVo);
    }

    public int UpdateData(FloorShopVo floorShopVo) {
        return update(floorShopVo.getCaseString(), floorShopVo);
    }

    public int UpdateData(FloorFacilitiesVo floorFacilitiesVo) {
        return update(floorFacilitiesVo.getCaseString(), floorFacilitiesVo);
    }

    public int UpdateData(FloorKioskVo floorKioskVo) {
        return update(floorKioskVo.getCaseString(), floorKioskVo);
    }

    public int UpdateData(FloorMappathVo floorMappathVo) {
        return update(floorMappathVo.getCaseString(), floorMappathVo);
    }

    public int updateListData(String queryString, List<?> updateList) {
        return update(queryString, updateList);
    }

    public int DeleteData(FloorVo floorVo) {
        return update(floorVo.getCaseString(), floorVo);
    }

    public int DeleteData(FloorShopVo floorShopVo) {
        return update(floorShopVo.getCaseString(), floorShopVo);
    }

    public int DeleteData(FloorFacilitiesVo floorFacilitiesVo) {
        return update(floorFacilitiesVo.getCaseString(), floorFacilitiesVo);
    }

    public int DeleteData(FloorKioskVo floorKioskVo) {
        return update(floorKioskVo.getCaseString(), floorKioskVo);
    }

    public int DataByCnt(FloorVo floorVo) {
        return selectOne(floorVo.getCaseString(), floorVo);
    }

    public int DataByCnt(FloorShopVo floorShopVo) {
        return selectOne(floorShopVo.getCaseString(), floorShopVo);
    }

    public int DataByCnt(FloorMappathVo floorMappathVo) {
        return selectOne(floorMappathVo.getCaseString(), floorMappathVo);
    }

    public String InsertReturnKeyData(FloorVo floorVo) {
        return null;
    }
}