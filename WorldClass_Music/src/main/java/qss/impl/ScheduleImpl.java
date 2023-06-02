package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.ScheduleDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.ScheduleVo;

@Service("scheduleService")
public class ScheduleImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="scheduleDao")
	private ScheduleDao scheduleDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return  scheduleDao.SelectListData((ScheduleVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return scheduleDao.DataByCnt((ScheduleVo)commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return scheduleDao.SelectData((ScheduleVo)commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int InsertData(CommonVo commonVo) throws Exception {
		return scheduleDao.InsertData((ScheduleVo)commonVo);
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		return scheduleDao.UpdateData((ScheduleVo)commonVo);
	}

	@Override
	@Transactional
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
		return scheduleDao.DeleteDataByObjectParam((ScheduleVo)commonVo);
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