package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.CommonDao;
import qss.dao.ContentsDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.UploadVo;

@Service("contentsService")
public class ContentsImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="contentsDao")
	private ContentsDao contentsDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return contentsDao.SelectListData((UploadVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return contentsDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return contentsDao.SelectData((UploadVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		return contentsDao.InsertData((UploadVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		return contentsDao.UpdateData((UploadVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		return contentsDao.DeleteDataByObjectParam((UploadVo)commonVo);
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