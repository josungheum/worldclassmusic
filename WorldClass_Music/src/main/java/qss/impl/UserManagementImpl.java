package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.UserManagementDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.UserVo;

@Service("userManagementService")
public class UserManagementImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="userManagementDao")
	private UserManagementDao UserManagementDao; 
	
	@Override
	public List<UserVo> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return (List<UserVo>)UserManagementDao.SelectListData((UserVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return UserManagementDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
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
		return UserManagementDao.InsertData((UserVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return UserManagementDao.UpdateData((UserVo)commonVo);
	}

	@Override
	@Transactional
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return UserManagementDao.DeleteData((UserVo)commonVo);
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