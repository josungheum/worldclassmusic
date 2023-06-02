package qss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;

@Repository("userActionLogDao")
public class UserActionLogDao extends EgovAbstractMapper {

	public List<?> SelectListData(CommonVo commonVo) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		/*param.put("STARTNUM", cmsqVo.getSTARTNUM());
		param.put("PAGELENGTH", cmsqVo.getPAGELENGTH());
		param.put("SEARCHKEY", cmsqVo.getSEARCHKEY());*/
		
		selectList("UserActionLog_PagingList", param);
		
		return (List<?>) param.get("result");
	}

	@SuppressWarnings("unchecked")
	public CommonVo SelectData(CommonVo commonVo) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (commonVo.getCaseString().equals("UserActionLog_TotalCnt")) {
			
			selectOne(commonVo.getCaseString(), params);
		}
		else if (commonVo.getCaseString().equals("UserActionLog_FilterCnt")) {
			params.put("SEARCHKEY", commonVo.getSerchKey());
			
			selectOne(commonVo.getCaseString(), params);
		}
		else if (commonVo.getCaseString().equals("UserActionLog_Detail")) {
//			params.put("IDX", commonVo.getLOG_IDX());
			
			selectOne(commonVo.getCaseString(), params);
		}
		
		return ((List<CommonVo>) (params.get("result"))).get(0);
	}
	
}