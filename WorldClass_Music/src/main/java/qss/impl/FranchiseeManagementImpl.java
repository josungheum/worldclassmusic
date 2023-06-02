package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.FranchiseeManagementDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.FranchiseeVo;

@Service("franchiseeManagementService")
public class FranchiseeManagementImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="franchiseeManagementDao")
	private FranchiseeManagementDao franchiseeManagementDao; 
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return franchiseeManagementDao.SelectListData((FranchiseeVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return franchiseeManagementDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return franchiseeManagementDao.SelectData((FranchiseeVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
//		return franchiseeManagementDao.StringList((FranchiseeVo)commonVo);
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return franchiseeManagementDao.InsertData((FranchiseeVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return franchiseeManagementDao.UpdateData((FranchiseeVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return franchiseeManagementDao.DeleteData((FranchiseeVo)commonVo);
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
		return franchiseeManagementDao.InsertReturnKeyData((FranchiseeVo)commonVo);
	}

	@Override
	public List<?> SelectCodeData(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}