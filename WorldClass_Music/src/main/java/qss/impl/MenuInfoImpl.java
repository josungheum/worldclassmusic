package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.MenuInfoDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.IndexVo;
import qss.vo.MenuInfoVo;

@Service("menuInfoService")
public class MenuInfoImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="menuInfoDao")
	private MenuInfoDao menuInfoDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return  menuInfoDao.SelectListData((IndexVo)commonVo);
	}


	public List<?> SelectListData2(CommonVo commonVo) throws Exception {
		return  menuInfoDao.SelectListData2((MenuInfoVo)commonVo);
	}



	@Override
	public int DataByCnt(CommonVo commonVo) {
		return menuInfoDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return menuInfoDao.SelectData(commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		return menuInfoDao.InsertData((MenuInfoVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		return menuInfoDao.UpdateData((MenuInfoVo)commonVo);
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
