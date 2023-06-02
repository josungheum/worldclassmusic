package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.CommonDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.MenuVo;

@Service("commonService")
public class CommonImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="commonDao")
	private CommonDao commonDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return commonDao.SelectListData(commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return commonDao.UrlCheck((MenuVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return null;
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return commonDao.SideMenu((MenuVo)commonVo);
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		
		commonDao.LogCreate(commonVo);
		
		return 0;
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		
		return commonDao.UpdateData(commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		/*if (commonVo.getCaseString().equals("CategoryName")) {
			return commonDao.CategoryName(commonVo);
		}
		else {
			return null;
		}*/
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