package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.OrderItemVo;
import qss.vo.SalesHistoryVo;

@Repository("salesHistoryDao")
public class SalesHistoryDao extends EgovAbstractMapper {
	public List<?> SelectListData(SalesHistoryVo salesHistoryVo) {
		List<?> list = null;
		list = selectList(salesHistoryVo.getCaseString(), salesHistoryVo);
		return list;
	}

	@SuppressWarnings("unchecked")
	public CommonVo SelectData(SalesHistoryVo salesHistoryVo) throws Exception{
		List<?> list = null;

		if ("SalesHistory_Detail".equals(salesHistoryVo.getCaseString())) {
			salesHistoryVo.setCaseString("SalesHistory_List");
			salesHistoryVo = (SalesHistoryVo)selectList(salesHistoryVo.getCaseString(), salesHistoryVo).get(0);

			salesHistoryVo.setCaseString("SalesHistory_Detail");
			list = selectList(salesHistoryVo.getCaseString(), salesHistoryVo);

			// 주문 상품 목록
			salesHistoryVo.setOrderItemVo((List<OrderItemVo>) list);
		}

		return salesHistoryVo;
	}

	public int DataByCnt(SalesHistoryVo salesHistoryVo) {
		return selectOne(salesHistoryVo.getCaseString(), salesHistoryVo);
	}
}