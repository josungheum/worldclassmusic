package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.UserVo;

@Repository("userManagementDao")
public class UserManagementDao extends EgovAbstractMapper {

	public List<UserVo> SelectListData(UserVo userVo) {
		List<UserVo> list = null;
		list = selectList(userVo.getCaseString(), userVo);
		return list;
	}

	public int InsertData(UserVo userVo) {
		return insert(userVo.getCaseString(), userVo);
	}

	public int UpdateData(UserVo userVo) {
		return update(userVo.getCaseString(), userVo);
	}

	public int DeleteData(UserVo userVo) {
		return update(userVo.getCaseString(), userVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}