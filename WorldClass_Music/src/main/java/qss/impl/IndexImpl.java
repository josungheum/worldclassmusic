package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.IndexDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.IndexVo;

@Service("indexService")
public class IndexImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="indexDao")
	private IndexDao indexDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return indexDao.DataByCnt((IndexVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		IndexVo resultVo = indexDao.SelectData((IndexVo)commonVo);
		
		return resultVo;
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub		
		return indexDao.UpdateData((IndexVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return indexDao.SelectByReturnString((IndexVo)commonVo);
	}
	
	@Override
	public HashMap SelectDataMap(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		HashMap resultVo = indexDao.SelectDataMap((IndexVo)commonVo);
		
		return resultVo;
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