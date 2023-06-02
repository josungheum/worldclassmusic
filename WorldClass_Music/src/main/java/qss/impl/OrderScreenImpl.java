package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.OrderScreenDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.OrderScreenVo;

@Service("orderScreenService")
public class OrderScreenImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="orderScreenDao")
	private OrderScreenDao orderScreenDao; 
	
	@Override
	public List<?> SelectListData(CommonVo cmsqVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.SelectListData((OrderScreenVo)cmsqVo);
	}

	@Override
	public int DataByCnt(CommonVo cmsqVo) {
		// TODO Auto-generated method stub
		return orderScreenDao.DataByCnt((OrderScreenVo)cmsqVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.SelectData((OrderScreenVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo cmsqVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.StringList((OrderScreenVo)cmsqVo);
	}

	@Override
	@Transactional
	public int InsertData(CommonVo cmsqVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.InsertData((OrderScreenVo)cmsqVo);
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo cmsqVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.UpdateData((OrderScreenVo)cmsqVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo cmsqVo) throws Exception {
		// TODO Auto-generated method stub
		return orderScreenDao.DeleteData((OrderScreenVo)cmsqVo);
	}

	@Override
	public String SelectByReturnString(CommonVo cmsqVo) throws Exception {
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
	public List<?> SelectCodeData(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}