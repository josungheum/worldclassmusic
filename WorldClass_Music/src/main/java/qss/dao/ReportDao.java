package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.ReportVo;

@Repository("reportDao")
public class ReportDao extends EgovAbstractMapper {
	public List<?> SelectListData(ReportVo reportVo) {
		List<?> list = null;
		list = selectList(reportVo.getCaseString(), reportVo);
		return list;
	}

	public CommonVo SelectData(ReportVo reportVo) throws Exception{
		List<?> list = null;

		if ("Report_Detail".equals(reportVo.getCaseString())) {
			reportVo.setCaseString("Report_List");
			reportVo = (ReportVo)selectList(reportVo.getCaseString(), reportVo).get(0);

			reportVo.setCaseString("Report_Detail");
			list = selectList(reportVo.getCaseString(), reportVo);
		}

		return reportVo;
	}

	public int DataByCnt(ReportVo reportVo) {
		return selectOne(reportVo.getCaseString(), reportVo);
	}
}