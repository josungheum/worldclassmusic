package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.ReportDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.ReportVo;

@Service("reportService")
public class ReportImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="reportDao")
	private ReportDao reportDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return  reportDao.SelectListData((ReportVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return reportDao.DataByCnt((ReportVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return reportDao.SelectData((ReportVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int InsertData(CommonVo commonVo) throws Exception {
		return 0;
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		return 0;
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		return 0;
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap SelectDataMap(CommonVo commonVo) throws Exception {
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