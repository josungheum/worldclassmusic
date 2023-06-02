package qss.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.EventVo;

@Repository("eventDao")
public class EventDao extends EgovAbstractMapper {

	public List<?> SelectListData(EventVo eventVo) {
		// TODO Auto-generated method stub
		List<?> list = null;
		if (eventVo.getCaseString().equals("Event_ImageList")) {
			List<EventVo> imgList = new ArrayList<EventVo>();
			EventVo ev = new EventVo();
			eventVo.setEventType("EVT0001");
			ev.setImageList(selectList(eventVo.getCaseString(), eventVo));
			imgList.add(ev);
			
			ev = new EventVo();
			eventVo.setEventType("EVT0002");
			ev.setImageList(selectList(eventVo.getCaseString(), eventVo));
			imgList.add(ev);
			
			list = imgList;
		}
		else {
			list = selectList(eventVo.getCaseString(), eventVo);
		}
		return list;
	}

	public CommonVo SelectData(EventVo eventVo) {
		// TODO Auto-generated method stub
		return selectOne(eventVo.getCaseString(), eventVo);
	}

	@Transactional
	public int InsertData(EventVo eventVo) {
		// TODO Auto-generated method stub
		int cnt = insert(eventVo.getCaseString(), eventVo);
		BigInteger idx = eventVo.getNewIdx();
		update("Event_UpdateOrderSeq");
		if (cnt > 0 && eventVo.getVertXML() != null) {
			eventVo.setEventIdx(idx);
			eventVo.setEventType("EVT0002");
			eventVo.setImageList(eventVo.getVertList());
			insert("Event_ImageListCreate", eventVo);
		}
		if (cnt > 0 && eventVo.getHoriXML() != null) {
			eventVo.setEventIdx(idx);
			eventVo.setEventType("EVT0001");
			eventVo.setImageList(eventVo.getHoriList());
			insert("Event_ImageListCreate", eventVo);
		}
		return cnt;
	}

	@Transactional
	public int UpdateData(EventVo eventVo) {
		int cnt = 0;
		if (eventVo.getCaseString().equals("Event_Update")) {
			int orderSeq = selectOne("Event_GetOrderSeq", eventVo);
			
			cnt = update(eventVo.getCaseString(), eventVo);
			
			if (orderSeq < eventVo.getOrderSeq()) {
				update("Event_UpdateOrderSeqAsc");
			}
			else {
				update("Event_UpdateOrderSeq");
			}

			delete("Event_ImageListDelete", eventVo);
			
			if (cnt > 0 && eventVo.getVertXML() != null) {
				eventVo.setEventType("EVT0002");
				eventVo.setImageList(eventVo.getVertList());
				insert("Event_ImageListCreate", eventVo);
			}
			if (cnt > 0 && eventVo.getHoriXML() != null) {
				eventVo.setEventType("EVT0001");
				eventVo.setImageList(eventVo.getHoriList());
				insert("Event_ImageListCreate", eventVo);
			}
		}
		else if(eventVo.getCaseString().equals("Event_Delete")){
			cnt = update(eventVo.getCaseString(), eventVo);
			if (cnt > 0) {
				delete("Event_ImageListDelete", eventVo);
			}
			update("Event_UpdateOrderSeq");
		}
		else if(eventVo.getCaseString().equals("Event_BatchDelete")){
			cnt = update("Event_BatchDelete");
			if (cnt > 0) {
				update("Event_UpdateOrderSeq");
			}
		}
		else {
			cnt = update(eventVo.getCaseString(), eventVo);
		}
		
		return cnt;
	}

	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}