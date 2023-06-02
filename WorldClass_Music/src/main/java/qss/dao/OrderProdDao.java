package qss.dao;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.OptionProdVo;
import qss.vo.OrderProdVo;

@Repository("orderProdDao")
public class OrderProdDao extends EgovAbstractMapper {

	public List<?> SelectListData(OrderProdVo orderProdVo) {
		List<?> list = null;
		list = selectList(orderProdVo.getCaseString(), orderProdVo);
		return list;
	}

	public int UpdateData(OrderProdVo orderProdVo) {
		
		OrderProdVo newOrderProdVo = new OrderProdVo();
		newOrderProdVo = orderProdVo;
		
		//상품 저장, 수정, 삭제, 활성화
		int cnt = update(orderProdVo.getCaseString(), orderProdVo);
		
		//옵션 상품 저장 추가
		if(orderProdVo.getCaseString().equals("OrderProd_InsertOrderProd")
			|| orderProdVo.getCaseString().equals("OrderProd_UpdateOrderProd")
		) 
		{
			/*옵션 리스트 처리 시작*/
			// 기존 옵션 정보 삭제
			delete("OrderProd_DeleteOrderProdOptionTarget", orderProdVo);

			// 신규 스크린 정보 처리
			/*스케줄 적용 스크린 리스트 insert*/
			if(orderProdVo.getOptionProdIdxList() != null) {
				for(int i = 0; i < orderProdVo.getOptionProdIdxList().size(); i++) {
					
					// 리스트에서 해당 상품IDX 별로 설정
					newOrderProdVo.setOptionProdIdx(orderProdVo.getOptionProdIdxList().get(i));
					if(orderProdVo.getCaseString().equals("OrderProd_InsertOrderProd"))
							newOrderProdVo.setOrderProdIdx(orderProdVo.getReturnIdx());
					
					newOrderProdVo.setOrderSeq(i+1);
					insert("OrderProd_InsertOrderProdOptionTarget", newOrderProdVo);
				}
			}
		}
		
		
		//스케줄 버전 저장
		orderProdVo.setScheduleType("SCH0008");
		if(orderProdVo.getCaseString().equals("OrderProd_InsertOrderProd"))
		{
		  
		   BigInteger[] checkboxArr = {orderProdVo.getReturnIdx()};
		   orderProdVo.setCheckboxArr(checkboxArr);
		   insert("OrderProd_InsertScheduleVersion", orderProdVo);
		}
		//그외
		else 
		{
		   BigInteger[] checkboxArr = {orderProdVo.getOrderProdIdx()};
		   if(!orderProdVo.getCaseString().equals("OrderProd_DeleteOrderProd")) {
			   orderProdVo.setCheckboxArr(checkboxArr);
		   }
		   insert("OrderProd_InsertScheduleVersion", orderProdVo);
		}
		return cnt;
	}

	public int DataByCnt(OrderProdVo orderProdVo) {
		return selectOne(orderProdVo.getCaseString(), orderProdVo);
	}

	public OrderProdVo SelectData(OrderProdVo orderProdVo) {
		Gson gson = new Gson();
		orderProdVo = selectOne(orderProdVo.getCaseString(), orderProdVo);
//		orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		// 상품 관리에  연결된 옵션 목록 조회
		orderProdVo.setCaseString("OrderProd_ResultOptionProd");
		List<OptionProdVo> optionProdList = selectList(orderProdVo.getCaseString(), orderProdVo);
		if(optionProdList != null && optionProdList.size() > 0) {
			try {
				String strOptionProd = gson.toJson(optionProdList);
				orderProdVo.setResultOptionProdList(strOptionProd);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			orderProdVo.setResultOptionProdList(gson.toJson(""));
		}
		
		return orderProdVo;
	}

	public int DeleteDataByObjectParam(OrderProdVo commonVo) {
		return 0;
	}


}