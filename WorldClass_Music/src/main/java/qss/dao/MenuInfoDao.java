package qss.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.IndexVo;
import qss.vo.MenuInfoVo;

@Repository("menuInfoDao")
public class MenuInfoDao extends EgovAbstractMapper {


	public List<?> SelectListData(IndexVo indexVo) {
		return (List<?>) selectList(indexVo.getCaseString(), indexVo);
	}

	public List<?> SelectListData2(MenuInfoVo menuInfoVo) {
		return (List<?>) selectList(menuInfoVo.getCaseString(), menuInfoVo);
	}

	public int InsertData(MenuInfoVo menuInfoVo) {
		return insert(menuInfoVo.getCaseString(), menuInfoVo);
	}

	public int UpdateData(MenuInfoVo menuInfoVo) {
		return update(menuInfoVo.getCaseString(), menuInfoVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return (Integer)selectOne(commonVo.getCaseString(), commonVo);
	}

	public CommonVo SelectData(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}