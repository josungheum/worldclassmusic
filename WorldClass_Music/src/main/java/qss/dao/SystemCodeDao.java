package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.SystemCodeVo;

@Repository("systemCodeDao")
public class SystemCodeDao extends EgovAbstractMapper {

	public List<?> SelectListData(SystemCodeVo systemCodeVo) {
		return selectList(systemCodeVo.getCaseString(), systemCodeVo);
	}

	public int UpdateData(SystemCodeVo systemCodeVo) {
		return update(systemCodeVo.getCaseString(), systemCodeVo);
	}

	public CommonVo SelectData(SystemCodeVo systemCodeVo) {
		return selectOne(systemCodeVo.getCaseString(), systemCodeVo);
	}

	public int DataByCnt(SystemCodeVo systemCodeVo) {
		return selectOne(systemCodeVo.getCaseString(), systemCodeVo);
	}

	public List<?> selectCodeData(String codeGroup) {
		SystemCodeVo systemCodeVo = new SystemCodeVo();
		systemCodeVo.setCaseString("SystemCode_ListSystemCode");
		systemCodeVo.setCodeGroup(codeGroup);
		return selectList(systemCodeVo.getCaseString(), systemCodeVo);
	}
	
}