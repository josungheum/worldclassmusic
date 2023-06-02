package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CalculateVo;
import qss.vo.CommonVo;

@Repository("calculateDao")
public class CalculateDao extends EgovAbstractMapper {
	public List<?> SelectListData(CalculateVo calculateVo) {
		List<?> list = null;
		if ("Calculate_List".equals(calculateVo.getCaseString())) {
			list = selectList(calculateVo.getCaseString(), calculateVo);
		}else if ("Calculate_Detail".equals(calculateVo.getCaseString())) {
			/*정산 확인 팝업(초기 정산)*/
			calculateVo.setCaseString("Calculate_Detail");
			list = selectList(calculateVo.getCaseString(), calculateVo);

		}else if ("ReCalculate_Detail".equals(calculateVo.getCaseString())) {
			/*정산 확인 팝업(재정산)*/
			calculateVo.setCaseString("ReCalculate_Detail");
			list = selectList(calculateVo.getCaseString(), calculateVo);
		}

		return list;
	}

	public CalculateVo SelectData(CalculateVo calculateVo) throws Exception{

		return calculateVo;
	}

	@SuppressWarnings("unchecked")
	public int InsertData(CalculateVo calculateVo) throws Exception{
		List<?> list = null;
		if ("Calculate_InsertCalculate".equals(calculateVo.getCaseString())) {
			// 초기 정산 처리
			// 현재 기준으로 정산처리할 정보를 다시 가져온 후 정산 처리
			calculateVo.setCaseString("Calculate_DetailUpdate");
			list = selectList(calculateVo.getCaseString(), calculateVo);
			List<CalculateVo> CalculateVoList = (List<CalculateVo>)list;

			if(list.size() > 0) {
				// 등록자, 수정자 설정
				for(int cnt=0; cnt<CalculateVoList.size(); cnt++) {
					CalculateVoList.get(cnt).setRegUser(calculateVo.getRegUser());
					CalculateVoList.get(cnt).setModUser(calculateVo.getRegUser());
				}
				calculateVo.setCaseString("Calculate_InsertCalculate");
				insert(calculateVo.getCaseString(), CalculateVoList);
			}

		} else if ("Calculate_UpdateCalculate".equals(calculateVo.getCaseString())) {
			// 초기 정산 후 제장신 처리
			// 현재 기준으로 정산처리할 정보를 다시 가져온 후 정산 처리
			calculateVo.setCaseString("ReCalculate_DetailUpdate");
			list = selectList(calculateVo.getCaseString(), calculateVo);
			List<CalculateVo> CalculateVoList = (List<CalculateVo>)list;

			if(list.size() > 0) {
				// 등록자, 수정자 설정
				for(int cnt=0; cnt<CalculateVoList.size(); cnt++) {
					CalculateVoList.get(cnt).setRegUser(calculateVo.getRegUser());
					CalculateVoList.get(cnt).setModUser(calculateVo.getRegUser());
				}
				calculateVo.setCaseString("Calculate_InsertCalculate");
				// 정산 마스터 정보 처리
				insert(calculateVo.getCaseString(), CalculateVoList);
			}
		}

		return 0;
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}