package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.MainFeedDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.UploadVo;

@Service("mainFeedService")
public class MainFeedImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="mainFeedDao")
	private MainFeedDao mainFeedDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return mainFeedDao.SelectListData((UploadVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return mainFeedDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return mainFeedDao.SelectData((UploadVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		return mainFeedDao.InsertData((UploadVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		return mainFeedDao.UpdateData((UploadVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		return mainFeedDao.DeleteDataByObjectParam((UploadVo)commonVo);
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
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