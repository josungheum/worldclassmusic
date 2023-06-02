package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.BrandManagementDao;
import qss.dao.CommonDao;
import qss.service.Qss;
import qss.vo.BrandVo;
import qss.vo.CommonVo;

@Service("brandManagementService")
public class BrandManagementImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="brandManagementDao")
	private BrandManagementDao brandManagementDao; 
	
	@Resource(name="commonDao")
	private CommonDao commonDao; 
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return (List<?>)brandManagementDao.SelectListData((BrandVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return brandManagementDao.DataByCnt((BrandVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo)  {
		// TODO Auto-generated method stub
		return brandManagementDao.SelectData((BrandVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
//		return brandManagementDao.StringList((BrandVo)commonVo);
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return brandManagementDao.InsertData((BrandVo)commonVo);
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		return brandManagementDao.UpdateData((BrandVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return brandManagementDao.DeleteData((BrandVo)commonVo);
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
		return brandManagementDao.InsertReturnKeyData((BrandVo)commonVo);
	}

	@Override
	public List<?> SelectCodeData(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	
}