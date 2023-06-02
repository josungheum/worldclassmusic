package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.CalculateDao;
import qss.service.Qss;
import qss.vo.BrandVo;
import qss.vo.CalculateVo;
import qss.vo.CommonVo;

@Service("calculateService")
public class CalculateImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="calculateDao")
	private CalculateDao calculateDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return calculateDao.SelectListData((CalculateVo) commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return calculateDao.DataByCnt((CalculateVo) commonVo);
	}

	@Override
	public CalculateVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return calculateDao.SelectData((CalculateVo) commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int InsertData(CommonVo commonVo) throws Exception {
		return calculateDao.InsertData((CalculateVo)commonVo);
	}


	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo)
			throws Exception {
		// TODO Auto-generated method stub
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
	public List<?> SelectCodeData(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		return 0;
	}

}