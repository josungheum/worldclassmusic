package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.PlayReportVo;

@Repository("playReportDao")
public class PlayReportDao extends EgovAbstractMapper {

	public List<?> SelectListData(PlayReportVo playReportVo) {
		// TODO Auto-generated method stub
		return selectList(playReportVo.getCaseString(), playReportVo);
	}
	
	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}