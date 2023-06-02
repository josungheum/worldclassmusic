package qss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.OrderProdDao;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.OrderProdVo;

@Service("orderProdService")
public class OrderProdImpl extends EgovAbstractServiceImpl implements Qss {
	@Resource(name="orderProdDao")
	private OrderProdDao orderProdDao; 
	
	@Override
	public List<?> SelectListData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return orderProdDao.SelectListData((OrderProdVo) commonVo);
	}

	@Override
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return orderProdDao.DataByCnt((OrderProdVo) commonVo);
	}

	@Override
	public OrderProdVo SelectData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return orderProdDao.SelectData((OrderProdVo) commonVo);
	}

	@Override
	public List<String> StringList(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int InsertData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public int UpdateData(CommonVo commonVo) throws Exception {
		// TODO Auto-generated method stub
		return orderProdDao.UpdateData((OrderProdVo) commonVo);
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