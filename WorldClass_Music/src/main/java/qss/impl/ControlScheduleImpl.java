package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.ControlScheduleDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.ControlScheduleVo;

@Service("controlScheduleService")
public class ControlScheduleImpl extends EgovAbstractServiceImpl implements Qss {

	@Resource(name="controlScheduleDao")
	private ControlScheduleDao controlScheduleDao;
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return  controlScheduleDao.SelectListData((ControlScheduleVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return controlScheduleDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return controlScheduleDao.SelectData((ControlScheduleVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int InsertData(CommonVo commonVo) throws Exception {
		return controlScheduleDao.InsertData((ControlScheduleVo)commonVo);
	}

	@Override
	public int UpdateData(CommonVo commonVo) throws Exception {
		return controlScheduleDao.UpdateData((ControlScheduleVo)commonVo);
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		return controlScheduleDao.DeleteDataByObjectParam((ControlScheduleVo)commonVo);
	}

	@Override
	public String SelectByReturnString(CommonVo commonVo) throws Exception {
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
	public List<?> SelectCodeData(String codeGroup) {
		// TODO Auto-generated method stub
		return null;
	}
	
}