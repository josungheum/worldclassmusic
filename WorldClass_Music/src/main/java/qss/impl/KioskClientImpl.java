package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.KioskClientDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.KioskClientVo;

@Service("kioskClientService")
public class KioskClientImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="kioskClientDao")
	private KioskClientDao kioskClientDao; 
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return kioskClientDao.SelectListData((KioskClientVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return kioskClientDao.DataByCnt((KioskClientVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return (KioskClientVo)kioskClientDao.SelectData((KioskClientVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return kioskClientDao.InsertData((KioskClientVo)commonVo);
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		return kioskClientDao.UpdateData((KioskClientVo)commonVo);
		
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
//		return kioskClientDao.DeleteData((KioskClientVo)commonVo);
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