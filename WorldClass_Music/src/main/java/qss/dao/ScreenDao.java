package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.util.WebUtil;
import qss.vo.CommonVo;
import qss.vo.ScreenVo;

@Repository("screenDao")
public class ScreenDao extends EgovAbstractMapper {

	public int InsertData(ScreenVo screenVo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		if (screenVo.getCaseString().equals("Screen_Create")) {

			// 스크린 정보 수정
			params.put("domainIdx", screenVo.getDomainIdx());
			params.put("brandIdx", screenVo.getBrandIdx());
			params.put("francIdx", screenVo.getFrancIdx());
			params.put("screenName", screenVo.getScreenName()/* WebUtil.EncodingStr(screenVo.getScreenName()) */);
			params.put("rowCnt", screenVo.getRowCnt());
			params.put("colCnt", screenVo.getColCnt());
			params.put("resolutionX", screenVo.getResolutionX());
			params.put("resolutionY", screenVo.getResolutionY());
			params.put("playTime", screenVo.getPlayTime());
			params.put("delYn", "N");
			params.put("regUser", screenVo.getRegUser());
			params.put("modUser", screenVo.getModUser());
			params.put("screenIdx", ""); // 리턴받을 키

			insert("Screen_InsertScreen", params);
			String screenIdx = params.get("screenIdx").toString();

			LayerAndContentInsert(screenVo, screenIdx);
		}
		num = 1;

		return num;
	}

	/**
	 * <pre>
	 * 1. 개요 : 조회
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @Method Name : SelectListData
	 * @date : 2019. 1. 7.
	 * @author : ksh
	 * @history :
	 *          -----------------------------------------------------------------------
	 *          변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2019. 1. 7. ksh 최초 작성
	 *          -----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @return
	 */
	public List<?> SelectListData(ScreenVo screenVo) {
		List<?> list = null;

		if (screenVo.getCaseString().equals("Screen_ScreenList")) {
			list = selectList(screenVo.getCaseString(), screenVo);
		} else if (screenVo.getCaseString().equals("Screen_ScreenGet")) {
			list = selectList(screenVo.getCaseString(), screenVo);
		}

		return list;
	}

	/**
	 * <pre>
	 * 1. 개요 : 수정
	 * 2. 처리내용 : 스크린 정보 수정
	 * </pre>
	 *
	 * @Method Name : UpdateData
	 * @date : 2019. 1. 7.
	 * @author : ksh
	 * @history :
	 *          -----------------------------------------------------------------------
	 *          변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2019. 1. 7. ksh 최초 작성
	 *          -----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @return
	 * @throws Exception
	 */
	public int UpdateData(ScreenVo screenVo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		if (screenVo.getCaseString().equals("Screen_Update")) {

			// 스크린 정보 수정
			String screenIdx = screenVo.getScreenIdx().toString();
			params.put("domainIdx", screenVo.getDomainIdx());
			params.put("brandIdx", screenVo.getBrandIdx());
			params.put("francIdx", screenVo.getFrancIdx());
			params.put("screenIdx", screenIdx);
			params.put("screenName", screenVo.getScreenName()/* WebUtil.EncodingStr(screenVo.getScreenName()) */);
			params.put("rowCnt", screenVo.getRowCnt());
			params.put("colCnt", screenVo.getColCnt());
			params.put("resolutionX", screenVo.getResolutionX());
			params.put("resolutionY", screenVo.getResolutionY());
			params.put("playTime", screenVo.getPlayTime());
			params.put("delYn", "N");
			params.put("modUser", screenVo.getModUser());

			update("Screen_UpdateScreen", params);

			// 공통 파일 테이블인 FILE_CONTENT에서 해당 스크린에서 이전 사용하던 파일정보 사용안함으로 변경
//			update("Screen_UpdateFile", params);

			// 해당 스크린(screenIdx)의 하위 정보인 스크린 레이어, 레이어컨텐츠는 삭제
			delete("Screen_DeleteContent", params);
			delete("Screen_DeleteLayer", params);

			// 신규로 INSERT
			LayerAndContentInsert(screenVo, screenIdx);

			// 해당 스크린 정보가 적용된 디바이스에 대해서는 스케줄버전 update
			List<String> listMap = new ArrayList<String>();
			listMap = selectList("Screen_SelectScheduleDeviceList", screenVo);
			if (listMap.size() > 0) {
				for (int cnt = 0; cnt < listMap.size(); cnt++) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("domainIdx", screenVo.getDomainIdx());
					dataMap.put("brandIdx", screenVo.getBrandIdx());
					dataMap.put("francIdx", screenVo.getFrancIdx());
					dataMap.put("deviceIdx", listMap.get(cnt).toString());
					dataMap.put("regUser", screenVo.getRegUser());
					dataMap.put("modUser", screenVo.getModUser());
//					dataMap.put("scheduleType", "SCH0001");
//					dataMap.put("modReason", "스크린번경");
//		            dataMap.put("scheduleVersionIdx", "");
//					update("Screen_UpdateScheduleVersion", dataMap);
				}
			}
		}
		num = 1;

		return num;
	}

	/**
	 * <pre>
	 * 1. 개요 : 삭제
	 * 2. 처리내용 : 스크린 정보 삭제
	 * </pre>
	 *
	 * @Method Name : DeleteDataByObjectParam
	 * @date : 2019. 1. 7.
	 * @author : ksh
	 * @history :
	 *          -----------------------------------------------------------------------
	 *          변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2019. 1. 7. ksh 최초 작성
	 *          -----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @return
	 */
	public int DeleteDataByObjectParam(ScreenVo screenVo) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		int num = 0;

		// 삭제전 스케줄 정보가 존재한다면 에러
		params.put("domainIdx", screenVo.getDomainIdx());
		params.put("brandIdx", screenVo.getBrandIdx());
		params.put("francIdx", screenVo.getFrancIdx());
		params.put("arrIndex", screenVo.getCheckboxArr());
		params.put("modUser", screenVo.getModUser());

		int cnt = selectOne("Screen_SelectScheduleCnt", params);

		// 스케줄정보에 포함된 스크린 정보가 존재할경우에는 삭제 불가
		if (cnt <= 0) {
			num = update("Screen_Delete", params);

			// 스케줄버전 처리
			screenVo.setScheduleType("SCH0002");
			screenVo.setModReason("스크린번경");
			insert("Screen_InsertScheduleVersion", screenVo);
			num = 1;
		} else {
			num = 2;
		}

		return num;
	}

	/**
	 * <pre>
	 * 1. 개요 : 등록
	 * 2. 처리내용 : 스크린 상세 정보 등록
	 * </pre>
	 *
	 * @Method Name : LayerAndContentInsert
	 * @date : 2019. 1. 7.
	 * @author : ksh
	 * @history :
	 *          -----------------------------------------------------------------------
	 *          변경일 작성자 변경내용 ----------- -------------------
	 *          --------------------------------------- 2019. 1. 7. ksh 최초 작성
	 *          -----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param setScreenIdx
	 * @throws Exception
	 */
	private void LayerAndContentInsert(ScreenVo screenVo, String setScreenIdx) throws Exception {
		ArrayList<String> setName = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "screenlayerName");
		ArrayList<String> setRow = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "startRow");
		ArrayList<String> setCol = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "startCol");
		ArrayList<String> setRspan = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "rowSpan");
		ArrayList<String> setCspan = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "colSpan");
		ArrayList<String> setPlayTime = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "playTime");
		ArrayList<String> setOrder = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "orderSeq");
		ArrayList<String> setContentXml = WebUtil.ParsingXML(screenVo.getLayerXml(), "Layer", "ContentXml");

		for (int j = 0; j < setName.size(); j++) {
			HashMap<String, Object> setLayerMap = new HashMap<String, Object>();
			setLayerMap.put("domainIdx", screenVo.getDomainIdx());
			setLayerMap.put("brandIdx", screenVo.getBrandIdx());
			setLayerMap.put("francIdx", screenVo.getFrancIdx());
			setLayerMap.put("screenIdx", setScreenIdx);
			setLayerMap.put("screenlayerName", setName.get(j));
			setLayerMap.put("startRow", setRow.get(j));
			setLayerMap.put("startCol", setCol.get(j));
			setLayerMap.put("rowSpan", setRspan.get(j));
			setLayerMap.put("colSpan", setCspan.get(j));
			setLayerMap.put("playTime", setPlayTime.get(j));
			setLayerMap.put("orderSeq", setOrder.get(j));
			setLayerMap.put("regUser", screenVo.getRegUser());
			setLayerMap.put("modUser", screenVo.getModUser());
			setLayerMap.put("screenLayerIdx", ""); // 리턴받을 키

			insert("Screen_InsertScreenLayer", setLayerMap);
			ArrayList<String> setFileIdx = WebUtil.ParsingXML(setContentXml.get(j), "Content", "fileContentIdx");
			ArrayList<String> setFilePlayTime = WebUtil.ParsingXML(setContentXml.get(j), "Content", "playTime");
			ArrayList<String> setFileOrder = WebUtil.ParsingXML(setContentXml.get(j), "Content", "orderSeq");

			String screenLayerIdx = setLayerMap.get("screenLayerIdx").toString();

			for (int i = 0; i < setFileIdx.size(); i++) {
				HashMap<String, Object> setContentMap = new HashMap<String, Object>();
				setContentMap.put("domainIdx", screenVo.getDomainIdx());
				setContentMap.put("brandIdx", screenVo.getBrandIdx());
				setContentMap.put("francIdx", screenVo.getFrancIdx());
				setContentMap.put("screenIdx", setScreenIdx);
				setContentMap.put("screenLayerIdx", screenLayerIdx);
				setContentMap.put("fileContentIdx", setFileIdx.get(i));
				setContentMap.put("playTime", setFilePlayTime.get(i));
				setContentMap.put("orderSeq", setFileOrder.get(i));
				setContentMap.put("regUser", screenVo.getRegUser());
				setContentMap.put("modUser", screenVo.getModUser());

				insert("Screen_InsertLayerContent", setContentMap);
			}
		}

		// 스케줄버전 처리
		screenVo.setScheduleType("SCH0002");
		screenVo.setModReason("스크린번경");
		insert("Screen_InsertScheduleVersion", screenVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}