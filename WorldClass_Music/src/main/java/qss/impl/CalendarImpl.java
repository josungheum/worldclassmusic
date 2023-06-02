package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.CalendarDao;
import qss.service.Qss;
import qss.vo.CalendarVo;
import qss.vo.CommonVo;

@Service("calendarService")
public class CalendarImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="calendarDao")
	private CalendarDao calendarDao;

	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		return  calendarDao.SelectListData((CalendarVo)commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		return calendarDao.DataByCnt(commonVo);
	}

	@Override
	public CommonVo SelectData(CommonVo commonVo) throws Exception {
		return calendarDao.SelectData((CalendarVo)commonVo);
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
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		return 0;
	}

	@Override
	public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception {
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
	public List<?> SelectCodeData(String codeGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}