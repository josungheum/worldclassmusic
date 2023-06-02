package qss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.FranchiseeVo;
import qss.vo.MenuVo;

@Repository("commonDao")
public class CommonDao extends EgovAbstractMapper {

	public List<String> SideMenu(MenuVo menuVo) {
		// TODO Auto-generated method stub
		return selectList("Common_SideMenu", menuVo);
	}

	public int UrlCheck(MenuVo menuVo) {
		// TODO Auto-generated method stub
		int cnt = 0;

		cnt = (Integer)selectOne("Common_UrlCheck", menuVo);

		return cnt;
	}

	public String CategoryName(CommonVo commonVo) {
		// TODO Auto-generated method stub
		String str = "";

		str = selectOne("Common_CategoryName", commonVo);

		return str;
	}

	public int UpdateTime(CommonVo commonVo){
	   int cnt = 0;

	   update("Common_UpdateTime", commonVo);

//	   cnt = commonVo.getROWCOUNT();

	   return cnt;
	}

	public void LogCreate(CommonVo commonVo){
		insert("Common_LogCreate", commonVo);
	}

	public List<?> SelectListData(CommonVo commonVo) {
		Map<String, Object> params = new HashMap<String, Object>();

		List<FranchiseeVo> list = null;

		params.put("domainIdx", commonVo.getDomainIdx());
		params.put("brandIdx", commonVo.getBrandIdx());
		params.put("francIdx", commonVo.getFrancIdx());
		params.put("serchKey", commonVo.getSerchKey());
		params.put("sessionAdminType", commonVo.getSessionAdminType());
		list = selectList(commonVo.getCaseString(), params);

		return list;
	}

	public int InsertData(CommonVo commonVo) {


		return 0;

	}

	public int UpdateData(CommonVo commonVo) {

		// 현재는 스케줄 버전관리만 저장하고 있다.
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("domainIdx", commonVo.getDomainIdx());
		dataMap.put("brandIdx", commonVo.getBrandIdx());
		dataMap.put("francIdx", commonVo.getFrancIdx());
		dataMap.put("modUser", commonVo.getRegUser());
		dataMap.put("scheduleType", commonVo.getScheduleType());
		dataMap.put("modReason", commonVo.getModReason());
		int cnt = update("Common_UpdateScheduleVersion", dataMap);
		return cnt;
	}
}