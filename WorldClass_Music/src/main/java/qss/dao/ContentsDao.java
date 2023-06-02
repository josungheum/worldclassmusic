package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.UploadVo;

@Repository("contentsDao")
public class ContentsDao extends EgovAbstractMapper {

	public List<?> SelectListData(UploadVo uploadVo) {
		List<?> list = null;
		if ("Contents_GroupList".equals(uploadVo.getCaseString()) || "Contents_SearchTree".equals(uploadVo.getCaseString())) {
			String userIdx = selectOne("Contents_GetUserIdx", uploadVo);
			uploadVo.setUSERS_IDX(userIdx);
			list = selectList(uploadVo.getCaseString(), uploadVo);
		}
		else {
			list = selectList(uploadVo.getCaseString(), uploadVo);
		}
		return list;
	}

	public CommonVo SelectData(UploadVo uploadVo) {
		UploadVo retUploadVo = new UploadVo();

		retUploadVo = selectOne(uploadVo.getCaseString(), uploadVo);
		uploadVo = retUploadVo;

		return uploadVo;
	}

	public int InsertData(UploadVo uploadVo) throws Exception {
		if ("Contents_CreateGroup".equals(uploadVo.getCaseString())) {
			String userIdx = selectOne("Contents_GetUserIdx", uploadVo);
			uploadVo.setUSERS_IDX(userIdx);
			if(uploadVo.getPARENT_GROUP_IDX().equals("0")) {
				uploadVo.setPARENT_GROUP_IDX("");
			}
			return insert(uploadVo.getCaseString(), uploadVo);
		}
		else {
			return insert(uploadVo.getCaseString(), uploadVo);
		}
	}

	public int UpdateData(UploadVo uploadVo) throws Exception {
		return update(uploadVo.getCaseString(), uploadVo);
	}

	public int DeleteDataByObjectParam(UploadVo uploadVo) {
		int num = 0;

		num = update(uploadVo.getCaseString(), uploadVo);

		return num;
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public int UrlCheck(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return 0;
	}
}