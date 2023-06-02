package qss.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import qss.impl.CalculateImpl;
import qss.impl.RestAPIImpl;
import qss.impl.ResultAPIImpl;
import qss.service.Qss;
import qss.service.QssApi;
import qss.vo.RestAPIVo;
import qss.vo.ResultAPIVo;

/**
 * api 관리
 * <pre>
 * qss.controller
 *    |_ RestAPIController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:21:48
 * @version :
 * @author : admin
 */
@RestController
@RequestMapping(value = "/RestAPIController")
public class RestAPIController {

	@Resource(name = "restAPIService")
	Qss restAPIservice = new RestAPIImpl();

	@Resource(name = "calculateService")
	Qss calculateService = new CalculateImpl();
	
	@Resource(name = "resultAPIService")
	QssApi resultAPIservice = new ResultAPIImpl();
		
	private static final Log logger = LogFactory.getLog(RestAPIController.class);
	
	
	
	@RequestMapping(value = "/getMarqueeList", method=RequestMethod.GET)
	@ResponseBody
	public ResultAPIVo GetMarqueeList(@RequestParam("DeviceCode") String dvCode, HttpServletRequest request) throws Exception {
		ResultAPIVo result = new ResultAPIVo();
		Map<String, Object> message = new HashMap<String, Object>();
		
		try {
			if (CheckParam(dvCode)) {
				result.setResultCode(1);
				message.put("title", "필수 요청 파라미터가 없습니다.");
				result.setMessages(message);
			}
			else if(CheckDevice(dvCode)) {
				result.setResultCode(1);
				message.put("title", "존재하지 않는 Device Code입니다.");
				result.setMessages(message);
			}
			else {
				List<Map<String, Object>> list = resultAPIservice.MarqueeList(dvCode);
				if (list.size() > 0) {
					result.setResultCode(0);
					message.put("title", "success");
					result.setMessages(message);
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("marqueeList", list);
					result.setData(resultMap);
				}
				else {
					result.setResultCode(0);
					message.put("title", "존재하는 층 정보가 없습니다.");
					result.setMessages(message);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			message.put("title", "Exception");
			message.put("detail", e.getMessage());
			result.setMessages(message);
		}
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/getEventList", method=RequestMethod.GET)
	@ResponseBody
	public ResultAPIVo GetEventList(@RequestParam("DeviceCode") String dvCode, HttpServletRequest request) throws Exception {
		ResultAPIVo result = new ResultAPIVo();
		Map<String, Object> message = new HashMap<String, Object>();
		
		try {
			if (CheckParam(dvCode)) {
				result.setResultCode(1);
				message.put("title", "필수 요청 파라미터가 없습니다.");
				result.setMessages(message);
			}
			else if(CheckDevice(dvCode)) {
				result.setResultCode(1);
				message.put("title", "존재하지 않는 Device Code입니다.");
				result.setMessages(message);
			}
			else {
				List<Map<String, Object>> list = resultAPIservice.EventList(dvCode);
				if (list.size() > 0) {
					result.setResultCode(0);
					message.put("title", "success");
					result.setMessages(message);
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("eventList", list);
					result.setData(resultMap);
				}
				else {
					result.setResultCode(0);
					message.put("title", "존재하는 이벤트가 없습니다.");
					result.setMessages(message);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			message.put("title", "Exception");
			message.put("detail", e.getMessage());
			result.setMessages(message);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/getAdditionalList", method=RequestMethod.GET)
	@ResponseBody
	public ResultAPIVo GetAdditionalList(@RequestParam("DeviceCode") String dvCode, HttpServletRequest request) throws Exception {
		ResultAPIVo result = new ResultAPIVo();
		Map<String, Object> message = new HashMap<String, Object>();
		
		try {
			if (CheckParam(dvCode)) {
				result.setResultCode(1);
				message.put("title", "필수 요청 파라미터가 없습니다.");
				result.setMessages(message);
			}
			else if(CheckDevice(dvCode)) {
				result.setResultCode(1);
				message.put("title", "존재하지 않는 Device Code입니다.");
				result.setMessages(message);
			}
			else {
				List<Map<String, Object>> list = resultAPIservice.AdditionList(dvCode);
				if (list.size() > 0) {
					result.setResultCode(0);
					message.put("title", "success");
					result.setMessages(message);
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("additionalList", list);
					result.setData(resultMap);
				}
				else {
					result.setResultCode(0);
					message.put("title", "존재하는 부가이미지가 없습니다.");
					result.setMessages(message);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			message.put("title", "Exception");
			message.put("detail", e.getMessage());
			result.setMessages(message);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/getPlayerInfo", method=RequestMethod.GET)
	@ResponseBody
	public ResultAPIVo GetPlayerInfo(@RequestParam("DeviceCode") String dvCode, HttpServletRequest request) throws Exception {
		ResultAPIVo result = new ResultAPIVo();
		Map<String, Object> message = new HashMap<String, Object>();
		
		try {
			if (CheckParam(dvCode)) {
				result.setResultCode(1);
				message.put("title", "필수 요청 파라미터가 없습니다.");
				result.setMessages(message);
			}
			else if(CheckDevice(dvCode)) {
				result.setResultCode(1);
				message.put("title", "존재하지 않는 Device Code입니다.");
				result.setMessages(message);
			}
			else {
				SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String now = format.format(date);
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap = resultAPIservice.GetPlayerInfo(dvCode);
				resultMap.put("DateTime", now);
				result.setResultCode(0);
				message.put("title", "success");
				result.setMessages(message);
				result.setData(resultMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			message.put("title", "Exception");
			message.put("detail", e.getMessage());
			result.setMessages(message);
		}
		return result;
	}
	
	
	
	/*
	 * Parameter Check
	 */
	public boolean CheckParam(String param) {
		return (param == null) || ("".equals(param));
	}
	
	/*
	 * Device Check
	 */
	public boolean CheckDevice(String dvid) throws Exception {
		boolean result = false;
		int cnt = resultAPIservice.CheckDeviceCode(dvid);
		if (cnt > 0) {
			result = false;
		}
		else {
			result = true;
		}
		return result;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 : 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : 조회
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 조회 정보 모델
	 */
	@RequestMapping(value = {
								 "/GetBrandDetail"    		/* 브랜드 조회 */
								,"/GetFranchiseeDetail"		/* 가맹점 조회 */
								,"/GetDeviceDetail"			/* 단말 조회 */
								,"/GetProductList"			/* 상품 조회 */
								,"/GetMenuList"				/* 주문 메뉴 조회 */
								,"/GetNoticeList"			/* 공지사항 조회 */
								,"/GetScreenScheduleList"	/* 재생 스케줄 조회 */
								,"/GetSoftwareUpdateList"	/* 플레이어 업데이트 */
								,"/GetDocNo"				/* 전문 번호 조회*/
								,"/GetOrderList"			/* 결제정보 조회*/
							})
	public @ResponseBody RestAPIVo GetDetail(HttpServletRequest request) {
		String path = request.getServletPath().replace("/RestAPIController/", "");
		RestAPIVo result = new RestAPIVo();
		String body = returnBody(request);
		ObjectMapper mapper = new ObjectMapper();

		RestAPIVo restAPIVo = new RestAPIVo();
		try {
			restAPIVo = mapper.readValue(body, RestAPIVo.class);

			// 실 다운로드 경로 설정
			restAPIVo.setRealPath(request.getRequestURL().toString().replace(request.getRequestURI(),""));
			
			try {
				restAPIVo.setCaseString(path);
				result = (RestAPIVo) restAPIservice.SelectData(restAPIVo);
			} catch (Exception e) {
				result.setErrorCode("1");
				result.setErrorMessage("서버오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			result.setErrorCode("1");
			result.setErrorMessage("넘어온 데이터의 형식이  맞지 않습니다.");
		}


		return result;
	}



	/**
	 * <pre>
	 * 1. 개요 : 주문 등록
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : InsertOrderDetail
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 주문 번호(매일 단말마다 유니크한 1부터 시작하는 증분 숫자 리턴)
	 */
	@RequestMapping(value = "/InsertOrderDetail")
	public @ResponseBody RestAPIVo InsertOrderDetail(HttpServletRequest request) {
		RestAPIVo result = new RestAPIVo();
		try {
			String body = returnBody(request);
			ObjectMapper mapper = new ObjectMapper();

			RestAPIVo restAPIVo = mapper.readValue(body, RestAPIVo.class);
			//로직 구현
			restAPIVo.setCaseString("InsertOrderDetail");
			result = (RestAPIVo) restAPIservice.SelectData(restAPIVo);

		} catch (Exception e) {
			result.setErrorCode("1");
	        result.setErrorMessage(e.getMessage());
		}
		return result;
	}



	/**
	 * <pre>
	 * 1. 개요 : 주문 수정
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateOrderDetail
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 성공 여부(기존 주문을 수정하고 주문 번호를 리턴)
	 */
	@RequestMapping(value = "/UpdateOrderDetail")
	public @ResponseBody RestAPIVo UpdateOrderDetail(HttpServletRequest request) {
		RestAPIVo result = new RestAPIVo();
		try {
			String body = returnBody(request);
			ObjectMapper mapper = new ObjectMapper();

			RestAPIVo restAPIVo = mapper.readValue(body, RestAPIVo.class);
			//로직 구현
			restAPIVo.setCaseString("UpdateOrderDetail");
			result = (RestAPIVo) restAPIservice.SelectData(restAPIVo);
		} catch (Exception e) {
			result.setErrorCode("1");
	        result.setErrorMessage(e.getMessage());
		}
		return result;
	}




	/**
	 * <pre>
	 * 1. 개요 : 상품 정보 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateProductList
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 성공 여부
	 */
	@RequestMapping(value = "/UpdateProductList")
	public @ResponseBody RestAPIVo UpdateProductList(HttpServletRequest request) {
		RestAPIVo result = new RestAPIVo();
		String body = returnBody(request);
		ObjectMapper mapper = new ObjectMapper();
		RestAPIVo restAPIVo = new RestAPIVo();
		try {
			restAPIVo = mapper.readValue(body, RestAPIVo.class);
			try {
				restAPIVo.setCaseString("UpdateProductList");
				result = (RestAPIVo) restAPIservice.SelectData(restAPIVo);
			} catch (Exception e) {
				result.setErrorCode("1");
				result.setErrorMessage("서버오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			result.setErrorCode("1");
			result.setErrorMessage("넘어온 데이터의 형식이  맞지 않습니다.");
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 리소스 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateResourceDetail
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 주문 정보 모델 목록
	 */
	@RequestMapping(value = "/UpdateResourceDetail")
	public @ResponseBody RestAPIVo UpdateResourceDetail(HttpServletRequest request) {
		RestAPIVo result = new RestAPIVo();
		String body = returnBody(request);
		ObjectMapper mapper = new ObjectMapper();
		RestAPIVo restAPIVo = new RestAPIVo();
		try {
			restAPIVo = mapper.readValue(body, RestAPIVo.class);
			try {
				restAPIVo.setCaseString("UpdateResourceDetail");
				result = (RestAPIVo) restAPIservice.SelectData(restAPIVo);
			} catch (Exception e) {
				result.setErrorCode("1");
				result.setErrorMessage("서버오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			result.setErrorCode("1");
			result.setErrorMessage("넘어온 데이터의 형식이  맞지 않습니다.");
		}


		return result;
	}


	/**
	 * <pre>
	 * 1. 개요 : 전문 등록
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : InsertDocumentDetail
	 * @date : 2019. 1. 22.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param requestJson
	 * @param String DeviceID(단말코드), String Version
	 * @return 성공 여부
	 */
	@RequestMapping(value = "/InsertDocumentDetail")
	public @ResponseBody RestAPIVo InsertDocumentDetail(HttpServletRequest request) {
		RestAPIVo result = new RestAPIVo();
		try {
			String body = returnBody(request);
			ObjectMapper mapper = new ObjectMapper();

			RestAPIVo value = mapper.readValue(body, RestAPIVo.class);
			//로직 구현
			System.out.println(value);

		} catch (Exception e) {
			e.getStackTrace();
		}
		return result;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : returnBody
	 * @date : 2019. 1. 22.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 22.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param request
	 * @return
	 */
	private String returnBody(HttpServletRequest request) {
		InputStream is;
		StringWriter writer = null;
		try {
			is = request.getInputStream();
			writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer.toString();

	}

}
