package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.ControlScheduleVo;

@Repository("controlScheduleDao")
public class ControlScheduleDao extends EgovAbstractMapper {

	public List<?> SelectListData(ControlScheduleVo scheduleVo) {
		List<?> list = null;
		list = selectList(scheduleVo.getCaseString(), scheduleVo);
		return list;
	}

	public CommonVo SelectData(ControlScheduleVo scheduleVo) {
//		Map<String, Object> param = new HashMap<String, Object>();
		ControlScheduleVo retScheduleVo = new ControlScheduleVo();
//		Gson gson = new Gson();

		if ("ControlSchedule_ScheduleGet".equals(scheduleVo.getCaseString())) {
			// 메인 스케줄 정보 조회
			retScheduleVo = selectOne(scheduleVo.getCaseString(), scheduleVo);
			scheduleVo = retScheduleVo;

		
		}

		return scheduleVo;
	}

	public int InsertData(ControlScheduleVo scheduleVo) throws Exception {
//		ControlScheduleVo newScheduleVo = new ControlScheduleVo();
//		newScheduleVo = scheduleVo;
		if ("ControlSchedule_CreateSchedule".equals(scheduleVo.getCaseString())) {
			Map<String, Object> dataMap = null;
			// 제어 스케줄 정보 insert
			insert("ControlSchedule_InsertSchedule", scheduleVo);

			// 스케줄 적용 디바이스 단말 insert
			List<Map<String, Object>> newScheduleTargets = new ArrayList<Map<String, Object>>();

			if(scheduleVo.getDeviceList() != null) {
				for(int cnt=0; cnt<scheduleVo.getDeviceList().size(); cnt++) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("domainIdx", scheduleVo.getDomainIdx());
					dataMap.put("brandIdx", scheduleVo.getBrandIdx());
					dataMap.put("francIdx", scheduleVo.getFrancIdx());
					dataMap.put("controlScheduleIdx", scheduleVo.getControlScheduleIdx());
					dataMap.put("deviceIdx", scheduleVo.getDeviceList().get(cnt));
					dataMap.put("regUser", scheduleVo.getRegUser());
					dataMap.put("modUser", scheduleVo.getModUser());
					newScheduleTargets.add(dataMap);
				}

				// 적용 디바이스 스케줄버전 변경
				scheduleVo.setScheduleType("SCH0010");
				update("ControlSchedule_UpdateScheduleVersion", scheduleVo);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", newScheduleTargets);

				// 적용 디바이스 등록
				insert("ControlSchedule_InsertScheduleTarget", map);
			}

		}

		return 0;
	}

	public int UpdateData(ControlScheduleVo scheduleVo) throws Exception {
//		ControlScheduleVo newScheduleVo = new ControlScheduleVo();
//		newScheduleVo = scheduleVo;

		if ("ControlSchedule_SetActiveYn".equals(scheduleVo.getCaseString())) {
//			Map<String, Object> params = new HashMap<String, Object>();

			String[] checkboxArr = new String[1];
			checkboxArr[0] = scheduleVo.getControlScheduleIdx().toString();
			scheduleVo.setCheckboxArr(checkboxArr);
			scheduleVo.setScheduleType("SCH0010");
			update("ControlSchedule_InsertDelScheduleVersion", scheduleVo);

			update("ControlSchedule_SetActiveYn", scheduleVo);
		} else if ("ControlSchedule_UpdateSchedule".equals(scheduleVo.getCaseString())) {
			if ("ControlSchedule_UpdateSchedule".equals(scheduleVo.getCaseString())) {
				 Map<String, Object> dataMap = null;
				// 메인스케줄 정보 수정
				update("ControlSchedule_UpdateSchedule", scheduleVo);

				List<Map<String, Object>> newScheduleTargets = new ArrayList<Map<String, Object>>();

				if(scheduleVo.getDeviceList() != null) {
					for(int cnt=0; cnt<scheduleVo.getDeviceList().size(); cnt++) {
						dataMap = new HashMap<String, Object>();
						dataMap.put("domainIdx", scheduleVo.getDomainIdx());
						dataMap.put("brandIdx", scheduleVo.getBrandIdx());
						dataMap.put("francIdx", scheduleVo.getFrancIdx());
						dataMap.put("controlScheduleIdx", scheduleVo.getControlScheduleIdx());
						dataMap.put("deviceIdx", scheduleVo.getDeviceList().get(cnt));
						dataMap.put("checkboxArr", scheduleVo.getDeviceList());
						dataMap.put("regUser", scheduleVo.getRegUser());
						dataMap.put("modUser", scheduleVo.getModUser());
						newScheduleTargets.add(dataMap);
					}

					// 적용 디바이스 스케줄버전 변경
					scheduleVo.setScheduleType("SCH0010");
					update("ControlSchedule_UpdateScheduleVersion", scheduleVo);

					// 기존 타겟 정보 삭제
					delete("ControlSchedule_DeleteScheduleTarget", scheduleVo);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", newScheduleTargets);

					insert("ControlSchedule_InsertScheduleTarget", map);
				}
			}

			return 0;
		}

		return 0;
	}

	public int DeleteDataByObjectParam(ControlScheduleVo scheduleVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		params.put("domainIdx", scheduleVo.getDomainIdx());
		params.put("brandIdx", scheduleVo.getBrandIdx());
		params.put("francIdx", scheduleVo.getFrancIdx());
		params.put("arrIndex", scheduleVo.getCheckboxArr());
		params.put("modUser", scheduleVo.getModUser());// 적용 디바이스 스케줄버전 변경

		scheduleVo.setScheduleType("SCH0010");
		update("ControlSchedule_InsertDelScheduleVersion", scheduleVo);

		// 메인 스케줄 정보 삭제처리
		num = update("ControlSchedule_DeleteScheduleMain", params);

		



		return num;
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}