package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.PlayReportDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.PlayReportVo;

@Service("playReportService")
public class PlayReportImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="playReportDao")
	private PlayReportDao playReportDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return playReportDao.SelectListData((PlayReportVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return playReportDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<?, ?> SelectDataMap(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String InsertReturnKeyData(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> SelectCodeData(String codeGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}