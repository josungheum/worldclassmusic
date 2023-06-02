package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.RestAPIDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.OrderMasterVo;
import qss.vo.RestAPIVo;

@Service("restAPIService")
public class RestAPIImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="restAPIDao")
	private RestAPIDao restAPIDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return restAPIDao.SelectListData((RestAPIVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return restAPIDao.SelectData((RestAPIVo)commonVo);

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
	@Transactional
	public HashMap SelectDataMap(CommonVo commonVo) throws Exception {
		return restAPIDao.SelectDataMap((RestAPIVo)commonVo);
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