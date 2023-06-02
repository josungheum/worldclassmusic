package qss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import qss.vo.CommonVo;

@Service
public interface Qss {
    public List<?> SelectListData(CommonVo commonVo) throws Exception;

    public int DataByCnt(CommonVo commonVo);

    public CommonVo SelectData(CommonVo commonVo) throws Exception;

    public List<String> StringList(CommonVo commonVo) throws Exception;

    public int InsertData(CommonVo commonVo) throws Exception;

    default int insertListData(String queryString, List<CommonVo> commonVoList) {
        int insertCount = 0;
        return insertCount;
    }

    public int UpdateData(CommonVo commonVo) throws Exception;

    default int updateListData(String queryString, List<?> commonVoList) {
        int updateCount = 0;
        return updateCount;
    }

    public int DeleteDataByObjectParam(Map<String, Object> ID, CommonVo commonVo) throws Exception;

    public String SelectByReturnString(CommonVo commonVo) throws Exception;

    public HashMap<?, ?> SelectDataMap(CommonVo commonVo) throws Exception;

    public String InsertReturnKeyData(CommonVo commonVo);

    public List<?> SelectCodeData(String codeGroup);

}
