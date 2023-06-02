package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.OptionProdDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.OptionProdVo;

@Service("optionProdService")
public class OptionProdImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="optionProdDao")
	private OptionProdDao optionProdDao; 
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return optionProdDao.SelectListData((OptionProdVo) commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return optionProdDao.DataByCnt((OptionProdVo) commonVo);
	}

	@Override
	public OptionProdVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return optionProdDao.SelectData((OptionProdVo) commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return optionProdDao.UpdateData((OptionProdVo) commonVo);
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
}