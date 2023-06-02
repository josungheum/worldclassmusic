package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.ScheduleScreenVo;
import qss.vo.ScheduleVo;

@Repository("scheduleDao")
public class ScheduleDao extends EgovAbstractMapper {

	public List<?> SelectListData(ScheduleVo scheduleVo) {
		List<?> list = null;
		list = selectList(scheduleVo.getCaseString(), scheduleVo);
		return list;
	}

	public CommonVo SelectData(ScheduleVo scheduleVo) {
		ScheduleVo retScheduleVo = new ScheduleVo();
		Gson gson = new Gson();

		if ("Schedule_ScheduleGet".equals(scheduleVo.getCaseString())) {
			// 메인 스케줄 정보 조회
			retScheduleVo = selectOne(scheduleVo.getCaseString(), scheduleVo);
			scheduleVo = retScheduleVo;

			// 메인 스케줄에 연결된 스크린 리스트 조회
			scheduleVo.setCaseString("Schedule_ResultScreen");
			List<ScheduleScreenVo> schScreenList = selectList(scheduleVo.getCaseString(), scheduleVo);
			if(schScreenList != null && schScreenList.size() > 0) {
				scheduleVo.setResultScreenList(gson.toJson(schScreenList));
			}else {
				scheduleVo.setResultScreenList(gson.toJson(""));
			}
		}

		return scheduleVo;
	}

	public int InsertData(ScheduleVo scheduleVo) throws Exception {
		ScheduleVo newScheduleVo = new ScheduleVo();
		newScheduleVo = scheduleVo;
		if ("Schedule_CreateSchedule".equals(scheduleVo.getCaseString())) {
			Map<String, Object> dataMap = null;
			// 메인 스케줄 정보 insert
			insert("Schedule_InsertSchedule", scheduleVo);

			// 스케줄 적용 디바이스 단말 insert
			List<Map<String, Object>> newScheduleTargets = new ArrayList<Map<String, Object>>();

			if(scheduleVo.getDeviceList() != null) {
				for(int cnt=0; cnt<scheduleVo.getDeviceList().size(); cnt++) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("domainIdx", scheduleVo.getDomainIdx());
					dataMap.put("brandIdx", scheduleVo.getBrandIdx());
					dataMap.put("francIdx", scheduleVo.getFrancIdx());
					dataMap.put("mainScheduleIdx", scheduleVo.getMainScheduleIdx());
					dataMap.put("deviceIdx", scheduleVo.getDeviceList().get(cnt));
					dataMap.put("regUser", scheduleVo.getRegUser());
					dataMap.put("modUser", scheduleVo.getModUser());
					newScheduleTargets.add(dataMap);


				}

				// 적용 디바이스 스케줄버전 변경
				scheduleVo.setScheduleType("SCH0002");
				update("Schedule_UpdateScheduleVersion", scheduleVo);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", newScheduleTargets);

				// 적용 디바이스 등록
				insert("Schedule_InsertScheduleTarget", map);
			}



			/*스케줄 적용 스크린 리스트 insert*/
			if(scheduleVo.getScreenList() != null) {
				for(int cnt=0; cnt<scheduleVo.getScreenList().size(); cnt++) {
					// 리스트에서 해당 스크린IDX 별로 설정
					newScheduleVo.setScreenIdx(scheduleVo.getScreenList().get(cnt));
					newScheduleVo.setOrderSeq(cnt+1);
					newScheduleVo.setScreenType(scheduleVo.getScreenTypeList().get(cnt));
					newScheduleVo.setScreenPlayTime(scheduleVo.getScreenPlayTimeList().get(cnt));
					insert("Schedule_InsertScheduleScreen", newScheduleVo);
				}
			}
		}

		return 0;
	}

	public int UpdateData(ScheduleVo scheduleVo) throws Exception {
		ScheduleVo newScheduleVo = new ScheduleVo();
		newScheduleVo = scheduleVo;

		if ("Schedule_SetActiveYn".equals(scheduleVo.getCaseString())) {
			String[] checkboxArr = new String[1];
			checkboxArr[0] = scheduleVo.getMainScheduleIdx().toString();
			scheduleVo.setCheckboxArr(checkboxArr);
			scheduleVo.setScheduleType("SCH0002");
			update("Schedule_InsertDelScheduleVersion", scheduleVo);

			update("Schedule_SetActiveYn", scheduleVo);
		} else if ("Schedule_UpdateSchedule".equals(scheduleVo.getCaseString())) {
			if ("Schedule_UpdateSchedule".equals(scheduleVo.getCaseString())) {
				 Map<String, Object> dataMap = null;
				// 메인스케줄 정보 수정
				update("Schedule_UpdateSchedule", scheduleVo);

				List<Map<String, Object>> newScheduleTargets = new ArrayList<Map<String, Object>>();

				if(scheduleVo.getDeviceList() != null) {
					for(int cnt=0; cnt<scheduleVo.getDeviceList().size(); cnt++) {
						dataMap = new HashMap<String, Object>();
						dataMap.put("domainIdx", scheduleVo.getDomainIdx());
						dataMap.put("brandIdx", scheduleVo.getBrandIdx());
						dataMap.put("francIdx", scheduleVo.getFrancIdx());
						dataMap.put("mainScheduleIdx", scheduleVo.getMainScheduleIdx());
						dataMap.put("deviceIdx", scheduleVo.getDeviceList().get(cnt));
						dataMap.put("checkboxArr", scheduleVo.getDeviceList());
						dataMap.put("regUser", scheduleVo.getRegUser());
						dataMap.put("modUser", scheduleVo.getModUser());
						newScheduleTargets.add(dataMap);
					}

					// 적용 디바이스 스케줄버전 변경
					scheduleVo.setScheduleType("SCH0002");
					update("Schedule_UpdateScheduleVersion", scheduleVo);

					// 기존 타겟 정보 삭제
					delete("Schedule_DeleteScheduleTarget", scheduleVo);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", newScheduleTargets);

					insert("Schedule_InsertScheduleTarget", map);
				}


				/*스크린 리스트 처리 시작*/
				// 기존 스크린 정보 삭제
				delete("Schedule_DeleteScheduleScreen", scheduleVo);

				// 신규 스크린 정보 처리
				/*스케줄 적용 스크린 리스트 insert*/
				if(scheduleVo.getScreenList() != null) {
					for(int cnt=0; cnt<scheduleVo.getScreenList().size(); cnt++) {
						// 리스트에서 해당 스크린IDX 별로 설정
						newScheduleVo.setScreenIdx(scheduleVo.getScreenList().get(cnt));
						newScheduleVo.setOrderSeq(cnt+1);
						newScheduleVo.setScreenType(scheduleVo.getScreenTypeList().get(cnt));
						newScheduleVo.setScreenPlayTime(scheduleVo.getScreenPlayTimeList().get(cnt));
						insert("Schedule_InsertScheduleScreen", newScheduleVo);
					}
				}
				
			}

			return 0;
		}

		return 0;
	}

	public int DeleteDataByObjectParam(ScheduleVo scheduleVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		params.put("domainIdx", scheduleVo.getDomainIdx());
		params.put("brandIdx", scheduleVo.getBrandIdx());
		params.put("francIdx", scheduleVo.getFrancIdx());
		params.put("arrIndex", scheduleVo.getCheckboxArr());
		params.put("modUser", scheduleVo.getModUser());// 적용 디바이스 스케줄버전 변경

		scheduleVo.setScheduleType("SCH0002");
		update("Schedule_InsertDelScheduleVersion", scheduleVo);

		// 메인 스케줄 정보 삭제처리
		update("Schedule_DeleteScheduleScreenMap", params);

		// 메인 스케줄 스크린 정보 삭제처리
		num = update("Schedule_DeleteScheduleMain", params);



		return num;
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}