package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.UploadVo;

@Repository("instaFeedDao")
public class InstaFeedDao extends EgovAbstractMapper {

	public List<?> SelectListData(UploadVo uploadVo) {
		return selectList(uploadVo.getCaseString(), uploadVo);
	}

	public CommonVo SelectData(UploadVo uploadVo) {
		return selectOne(uploadVo.getCaseString(), uploadVo);
	}

	public int InsertData(UploadVo uploadVo) throws Exception {
		return insert(uploadVo.getCaseString(), uploadVo);
	}

	public int UpdateData(UploadVo uploadVo) throws Exception {
		if(uploadVo.getCaseString().equals("InstaFeed_SortUpdate")) {
			int cnt = 0;
			List<UploadVo> list = uploadVo.getList();
			for(int i=0; i<list.size(); i++) {
				UploadVo temp = list.get(i);
				temp.setModUser(uploadVo.getModUser());
				cnt = update("InstaFeed_SortUpdate", temp);
			}
			
			return cnt;
		}else {
			return update(uploadVo.getCaseString(), uploadVo);
		}
	}

	public int DeleteDataByObjectParam(UploadVo uploadVo) {
		return update(uploadVo.getCaseString(), uploadVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public int UrlCheck(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return 0;
	}
}