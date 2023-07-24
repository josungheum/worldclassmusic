package qss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.UploadVo;

@Repository("uploadDao")
public class UploadDao extends EgovAbstractMapper {
	
	
	public UploadVo SelectData(UploadVo uploadVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idx", uploadVo.getFileContentIdx());
		
		return selectOne(uploadVo.getCaseString(), params);
	}

	public String InsertReturnKeyData(UploadVo uploadVo) {
		insert("File_Create", uploadVo);
		
		String fileContentIdx = uploadVo.getFileContentIdx();
		
		return fileContentIdx;
	}
	
	public int InsertData(UploadVo uploadVo) throws Exception {
		return insert(uploadVo.getCaseString(), uploadVo);
	}
	
	public int UpdateData(UploadVo uploadVo) throws Exception {
		return update(uploadVo.getCaseString(), uploadVo);
	}
}