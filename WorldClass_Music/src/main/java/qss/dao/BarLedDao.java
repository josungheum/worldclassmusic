package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.BarLedVo;
import qss.vo.CommonVo;

@Repository("barledDao")
public class BarLedDao extends EgovAbstractMapper {

	public List<?> SelectListData(BarLedVo barledVo) {
		List<?> list = null;
		list = selectList(barledVo.getCaseString(), barledVo);
		return list;
	}

	@Transactional
	public int InsertData(BarLedVo barledVo) throws Exception {
		if ("BarLed_Create".equals(barledVo.getCaseString())) {
			Map<String, Object> dataMap = null;
			// 메인 스케줄 정보 insert
			insert("BarLed_Insert", barledVo);

			// 스케줄 적용 디바이스 단말 insert
			List<Map<String, Object>> newMarqueeTargets = new ArrayList<Map<String, Object>>();

			if(barledVo.getDeviceList() != null) {
				for(int cnt=0; cnt<barledVo.getDeviceList().size(); cnt++) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("domainIdx", barledVo.getDomainIdx());
					dataMap.put("brandIdx", barledVo.getBrandIdx());
					dataMap.put("francIdx", barledVo.getFrancIdx());
					dataMap.put("mqIdx", barledVo.getMqIdx());
					dataMap.put("deviceIdx", barledVo.getDeviceList().get(cnt));
					dataMap.put("regUser", barledVo.getRegUser());
					dataMap.put("modUser", barledVo.getModUser());
					newMarqueeTargets.add(dataMap);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", newMarqueeTargets);

				// 적용 디바이스 등록
				insert("BarLed_InsertMarqueeTarget", map);
			}
			
			//마퀴 텍스트 배포
			if (barledVo.getActiveYn().equals("Y")) {
				update("BarLed_SetDistribute", barledVo);
			}
		}

		return 0;
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public CommonVo SelectData(BarLedVo barledVo) {
		// TODO Auto-generated method stub
		return selectOne(barledVo.getCaseString(), barledVo);
	}

	@Transactional
	public int UpdateData(BarLedVo barledVo) {
		if ("BarLed_Update".equals(barledVo.getCaseString())) {
			Map<String, Object> dataMap = null;
			// 메인 스케줄 정보 update
			update("BarLed_Update", barledVo);
			
			// 기존 타겟 정보 삭제
			delete("BarLed_DeleteMarqueeTarget", barledVo);

			// 스케줄 적용 디바이스 단말 insert
			List<Map<String, Object>> newMarqueeTargets = new ArrayList<Map<String, Object>>();

			if(barledVo.getDeviceList() != null) {
				for(int cnt=0; cnt<barledVo.getDeviceList().size(); cnt++) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("domainIdx", barledVo.getDomainIdx());
					dataMap.put("brandIdx", barledVo.getBrandIdx());
					dataMap.put("francIdx", barledVo.getFrancIdx());
					dataMap.put("mqIdx", barledVo.getMqIdx());
					dataMap.put("deviceIdx", barledVo.getDeviceList().get(cnt));
					dataMap.put("regUser", barledVo.getRegUser());
					dataMap.put("modUser", barledVo.getModUser());
					newMarqueeTargets.add(dataMap);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", newMarqueeTargets);

				// 적용 디바이스 등록
				insert("BarLed_InsertMarqueeTarget", map);
			}
			
			//마퀴 텍스트 배포
			if (barledVo.getActiveYn().equals("Y")) {
				update("BarLed_SetDistribute", barledVo);
			}
		}
		else if("BarLed_SetActiveYn".equals(barledVo.getCaseString())) {
			update(barledVo.getCaseString(), barledVo);
			
			update("BarLed_SetDistribute", barledVo);
		}
		
		return 0;
	}

	public int DeleteDataByObjectParam(BarLedVo barledVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		params.put("domainIdx", barledVo.getDomainIdx());
		params.put("brandIdx", barledVo.getBrandIdx());
		params.put("francIdx", barledVo.getFrancIdx());
		params.put("arrIndex", barledVo.getCheckboxArr());
		params.put("modUser", barledVo.getModUser());

		//마퀴 삭제
		num = update("BarLed_Delete", params);

		//타겟 정보 삭제
		delete("BarLed_DeleteMultiMarqueeTarget", params);

		return num;
	}

}