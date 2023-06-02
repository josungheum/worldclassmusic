package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;

@Repository("exampleDao")
public class ExampleDao extends EgovAbstractMapper {

	public List<?> SelectListData(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return selectList(commonVo.getCaseString(), commonVo);
	}

	
}