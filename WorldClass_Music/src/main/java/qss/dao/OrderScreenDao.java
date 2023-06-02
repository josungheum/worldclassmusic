package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.OrderScreenVo;

@Repository("orderScreenDao")
public class OrderScreenDao extends EgovAbstractMapper {

	public List<OrderScreenVo> SelectListData(OrderScreenVo orderScreenVo) {
		List<OrderScreenVo> list = null;
		list = selectList(orderScreenVo.getCaseString(), orderScreenVo);

		return list;
	}

	public CommonVo SelectData(OrderScreenVo orderScreenVo) {
		return selectOne(orderScreenVo.getCaseString(), orderScreenVo);
	}

	public List<String> StringList(OrderScreenVo orderScreenVo) {
		return null;
	}

	@Transactional
	public int InsertData(OrderScreenVo orderScreenVo) {
		return 0;
	}

	@Transactional
	public int UpdateData(OrderScreenVo orderScreenVo) throws Exception{
		int cnt = 0;
		if(orderScreenVo.getCaseString().equals("OrderScreen_UpdateOrderScreen") 
				|| orderScreenVo.getCaseString().equals("OrderScreen_InsertOrderScreen")
		) 
		{
			//주문화면 저장
			cnt = update(orderScreenVo.getCaseString(), orderScreenVo);
			
			//스케줄 버전 저장
			orderScreenVo.setScheduleType("SCH0007");
			insert("OrderScreen_InsertScheduleVersion", orderScreenVo);
			
//			orderScreenVo.setScheduleType("SCH0008");
//			insert("OrderScreen_InsertScheduleVersion", orderScreenVo);
			
			//주문화면 타겟 삭제
			cnt += update("OrderScree_DeleteOrderScreenTarget", orderScreenVo);
			//주문화면 타겟 하나씩 저장
			if(orderScreenVo.getCheckboxArr() != null) {
				for(int i = 0; i < orderScreenVo.getCheckboxArr().length; i++) {
					orderScreenVo.setDeviceIdx(orderScreenVo.getCheckboxArr()[i]);
					cnt += update("OrderScree_InsertOrderScreenTarget", orderScreenVo);
				}
			}
			
			//주문화면 메뉴 삭제
			cnt += update("OrderScree_DeleteOrderScreenMenuItem", orderScreenVo);
			cnt += update("OrderScree_DeleteOrderScreenMenu", orderScreenVo);
			//주문화면 메뉴 하나씩 저장
			JsonParser jsonParser = new JsonParser();
			JsonArray  jsonMenuArray = jsonParser.parse(orderScreenVo.getMenuJson()).getAsJsonArray();
			JsonArray  jsonProdArray = jsonParser.parse(orderScreenVo.getProdList()).getAsJsonArray();
			Gson gson = new Gson();
			//상품 스케줄 추가

			for (JsonElement menuObj : jsonMenuArray) {
	        	OrderScreenVo menuVo = gson.fromJson(menuObj, OrderScreenVo.class);
	        	orderScreenVo.setOrderScreenMenuIdx(menuVo.getOrderScreenMenuIdx());
				orderScreenVo.setMenuName(menuVo.getMenuName());
				//주문화면 메뉴 상품 삭제
				cnt += update("OrderScree_InsertOrderScreenMenu", orderScreenVo);
				for(JsonElement prodObj : jsonProdArray) {
					OrderScreenVo prodVo = gson.fromJson(prodObj, OrderScreenVo.class);
					if(menuVo.getOrderScreenMenuIdx().compareTo(prodVo.getOrderScreenMenuIdx()) == 0) {
						orderScreenVo.setOrderProdIdx(prodVo.getOrderProdIdx());
						orderScreenVo.setStateCode(prodVo.getStateCode());
						//주문화면 메뉴 상품 하나씩 저장
						cnt += update("OrderScree_InsertOrderScreenMenuItem", orderScreenVo);

					}
				}
	        }

			
		}
		else if(orderScreenVo.getCaseString().equals("OrderScreen_DeleteOrderScreen"))
		{
			//스케줄 버전 저장
			orderScreenVo.setScheduleType("SCH0007");
			insert("OrderScreen_InsertDelScheduleVersion", orderScreenVo);
			cnt = update(orderScreenVo.getCaseString(), orderScreenVo);
		}
		else
		{
			cnt = update(orderScreenVo.getCaseString(), orderScreenVo);
		}
		return cnt;
	}

	public int DeleteData(OrderScreenVo orderScreenVo) {
		return 0;
	}

	public int DataByCnt(OrderScreenVo orderScreenVo) {
		return selectOne(orderScreenVo.getCaseString(), orderScreenVo);
	}
}