package qss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.IndexVo;

@Repository("indexDao")
public class IndexDao extends EgovAbstractMapper {

	@SuppressWarnings("unchecked")
	public int DataByCnt(IndexVo indexVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		int userCnt = 0;

		if (indexVo.getCaseString().equals("Login_GetIdCheck")) {
			params.put("id", indexVo.getId());

			userCnt = selectOne("Login_GetIdCheck", params);

		} else if (indexVo.getCaseString().equals("Login_GetLockCheck")) {
			params.put("id", indexVo.getId());

			selectOne("Login_GetLockCheck", params);

			userCnt = ((List<IndexVo>) (params.get("result"))).get(0).getCnt();
		} else if (indexVo.getCaseString().equals("Login_GetIdPwdCheck")) {
			params.put("id", indexVo.getId());
			params.put("password", indexVo.getPassword());

			selectOne("Login_GetIdPwdCheck", params);

			userCnt = ((List<IndexVo>) (params.get("result"))).get(0).getCnt();
		}

		return userCnt;
	}

	@SuppressWarnings("unchecked")
	public IndexVo SelectData(IndexVo indexVo) {
		Map<String, Object> params = new HashMap<String, Object>();

		IndexVo sessionVo = null;

		if (indexVo.getCaseString().equals("Login_GetMember")) {
			params.put("id", indexVo.getId());
			sessionVo = selectOne("Login_GetMember", params);
		}else if (indexVo.getCaseString().equals("Login_GetIdPwdCheck")) {
			params.put("id", indexVo.getId());
			params.put("password", indexVo.getPassword());

			selectOne("Login_GetIdPwdCheck", params);

			sessionVo = ((List<IndexVo>) (params.get("result"))).get(0);
		}else {
			params.put("domainIdx", indexVo.getDomainIdx());
			params.put("brandIdx", indexVo.getBrandIdx());
			params.put("francIdx", indexVo.getFrancIdx());
			sessionVo = selectOne(indexVo.getCaseString(), params);
		}

		return sessionVo;
	}

	public int UpdateData(IndexVo indexVo) {
		Map<String, Object> params = new HashMap<String, Object>();

		int cnt = 0;

		if (indexVo.getCaseString().equals("Login_SetPwd")) {
//			params.put("IDX", indexVo.getIDX());
			params.put("id", indexVo.getId());
			params.put("OLD_PWD", indexVo.getPassword());
//			params.put("NEW_PWD", indexVo.getNEW_PWD());

			update("Login_SetPwd", params);

//			cnt = ((List<IndexVo>) params.get("result")).get(0).getERR();
		} else if (indexVo.getCaseString().equals("Login_SetPwdFailCnt")) {
			params.put("id", indexVo.getId());
			params.put("cnt", indexVo.getCnt());

			update("Login_SetPwdFailCnt", params);
//			cnt = ((List<IndexVo>) params.get("result")).get(0).getCnt();
		}

		return cnt;
	}

	public String SelectByReturnString(IndexVo indexVo) {
		// TODO Auto-generated method stub
		String str = "";

		str = (String) selectOne("Login_GetUrl", indexVo);

		return str;
	}

	public HashMap SelectDataMap(IndexVo indexVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		HashMap sessionVo = null;

		if (indexVo.getCaseString().equals("Login_GetIdPwdCheck")) {
			params.put("id", indexVo.getId());
			params.put("password", indexVo.getPassword());
			sessionVo = selectOne("Login_GetIdPwdCheck", params);
		}

		return sessionVo;
	}

}