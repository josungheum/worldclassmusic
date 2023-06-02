package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.ScheduleReportVo;

@Repository("scheduleReportDao")
public class ScheduleReportDao extends EgovAbstractMapper {

	public List<?> SelectListData(ScheduleReportVo scheduleReportVo) {
		// TODO Auto-generated method stub
		return selectList(scheduleReportVo.getCaseString(), scheduleReportVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public int InsertData(ScheduleReportVo scheduleReportVo) {
		// TODO Auto-generated method stub
		return insert(scheduleReportVo.getCaseString(), scheduleReportVo);
	}
	
}