package qss.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;

@Repository("resourceDao")
public class ResourceDao extends EgovAbstractMapper{
	
	public List<?> SelectListData(CommonVo commonVo) {
		return selectList(commonVo.getCaseString(), commonVo);
	}

	public HashMap SelectDataMap(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

}
