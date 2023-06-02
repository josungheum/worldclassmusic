package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.log.KioskLogH;
import qss.vo.log.PlayLogH;

@Repository("resultAPIDao")
public class ResultAPIDao extends EgovAbstractMapper {

    public int CheckDeviceCode(String dvCode) {
        return selectOne("ResultApi_CheckDeviceCode", dvCode);
    }

    public List<Map<String, Object>> EventList(String dvCode) {
        List<Map<String, Object>> list = selectList("ResultApi_EventList", dvCode);

        for (Map<String, Object> map : list) {
            int idx = Integer.valueOf(map.get("eventIdx").toString());

            Map<String, Object> api = new HashMap<String, Object>();
            api.put("idx", idx);
            api.put("type", "EVT0001");
            List<Map<String, Object>> horiList = new ArrayList<Map<String, Object>>();
            horiList = selectList("ResultApi_ImgList", api);

            api.put("type", "EVT0002");
            List<Map<String, Object>> vertList = new ArrayList<Map<String, Object>>();
            vertList = selectList("ResultApi_ImgList", api);

            map.put("vertical", vertList);
            map.put("horizontal", horiList);
        }

        return list;
    }

    public List<Map<String, Object>> AdditionList(String dvCode) {
        List<Map<String, Object>> list = selectList("ResultApi_AdditionList", dvCode);

        String arr[] = {"ADD0001", "ADD0002", "ADD0003", "ADD0004", "ADD0005", "ADD0006"};

        for (int i = 0; i < arr.length; i++) {
            Map<String, Object> api = new HashMap<String, Object>();
            api.put("dvCode", dvCode);
            api.put("type", arr[i]);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("additionType", arr[i]);
            result.put("additionImageList", selectList("ResultApi_AdditionList", api));

            list.add(result);
        }

        return list;
    }

    public Map<String, Object> GetPlayerInfo(String dvCode) {
        // TODO Auto-generated method stub
        return selectOne("ResultApi_PlayerInfo", dvCode);
    }

    public List<Map<String, Object>> FloorList(String dvCode) {
        List<Map<String, Object>> list = selectList("ResultApi_FloorList", dvCode);
        Map<String, Object> vMap = selectOne("ResultApi_GetDomainBrand", dvCode);

        for (Map<String, Object> map : list) {
            HashMap<String, Object> api = new HashMap<String, Object>();
            api.put("floorIdx", map.get("floorIdx"));
            api.put("domainIdx", vMap.get("domainIdx"));
            api.put("brandIdx", vMap.get("brandIdx"));

            List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
            brandList = selectList("ResultApi_BrandList", api);
            for (Map<String, Object> map2 : brandList) {
                int shopIdx = Integer.valueOf(map2.get("brandIdx").toString());
                HashMap<String, Object> api2 = new HashMap<String, Object>();
                api2.put("shopIdx", shopIdx);
                api2.put("domainIdx", vMap.get("domainIdx"));
                api2.put("brandIdx", vMap.get("brandIdx"));

                List<Map<String, Object>> brandMenuList = new ArrayList<Map<String, Object>>();
                brandMenuList = selectList("ResultApi_BrandMenuList", api2);
                map2.put("brandMenu", brandMenuList);
            }

            List<Map<String, Object>> facilityList = new ArrayList<Map<String, Object>>();
            facilityList = selectList("ResultApi_FacilityList", api);

            map.put("brandList", brandList);
            map.put("facilityList", facilityList);
        }

        return list;
    }

    public int insertDeviceUserActionLog(List<KioskLogH> actionLogList) {
        int count = insert("ResultApi_DeviceUserActionLogCreate", actionLogList);
        return count;
    }

    public int insertContentPlayLog(List<PlayLogH> playLogList) {
        int count = insert("ResultApi_ContentPlayLogCreate", playLogList);
        return count;
    }

	public String GetMaxUpdateDt() {
		// TODO Auto-generated method stub
		return selectOne("ResultApi_GetMaxUpdateDt");
	}

	public List<Map<String, Object>> MarqueeList(String dvCode) {
		// TODO Auto-generated method stub
		return selectList("ResultApi_MarqueeList", dvCode);
	}
}