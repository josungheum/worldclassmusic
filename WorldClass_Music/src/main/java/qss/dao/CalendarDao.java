package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CalendarVo;
import qss.vo.CommonVo;

@Repository("calendarDao")
public class CalendarDao extends EgovAbstractMapper {

	public List<?> SelectListData(CalendarVo vo) {
		List<?> list = null;
		list = selectList(vo.getCaseString(), vo);
		return list;
	}

	public CommonVo SelectData(CalendarVo vo) {
//		Map<String, Object> param = new HashMap<String, Object>();
		return vo;
	}


	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}