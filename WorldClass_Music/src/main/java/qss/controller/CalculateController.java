package qss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.CalculateImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.CalculateVo;

/**
 * 매출 관리 > 정산 관리
 * <pre>
 * qss.controller
 *    |_ CalculateController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:18:43
 * @version :
 * @author : admin
 */
@Controller
public class CalculateController {

	@Resource(name = "calculateService")
	Qss calculateService = new CalculateImpl();
	private static final Log logger = LogFactory.getLog(CalculateController.class);

	/**
	 * <pre>
	 * 1. 개요 : Main
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Calculate/Main")
	public String Main(@ModelAttribute("CalculateVo") CalculateVo calculateVo, HttpSession session, ModelMap model) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/Calculate/Main";
		} else {
			return "redirect:/Logout";
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : List
	 * 2. 처리내용 : 리스트 조회
	 * </pre>
	 * @Method Name : List
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Calculate/List")
	@ResponseBody
	public AjaxResultVO List(CalculateVo calculateVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		calculateVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		// 관리자 정보 set
		calculateVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		calculateVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		calculateVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		calculateVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			calculateVo.setCaseString("Calculate_List");
			List<CalculateVo> list = (List<CalculateVo>) calculateService.SelectListData(calculateVo);
			result.setData(list);

			calculateVo.setCaseString("Calculate_ListCnt");
			result.setiTotalDisplayRecords(calculateService.DataByCnt(calculateVo));

			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : CalculateFormDetail
	 * 2. 처리내용 : 상세화면 조회
	 * </pre>
	 * @Method Name : CalculateFormDetail
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Calculate/CalculateFormDetail")
	@ResponseBody
	public AjaxResultVO CalculateFormDetail(CalculateVo calculateVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			calculateVo.setCaseString("Calculate_Detail");
			List<?> list = calculateService.SelectListData(calculateVo);

			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}


	/**
	 * <pre>
	 * 1. 개요 : ReCalculateFormDetail
	 * 2. 처리내용 : 재정산상세화면 조회
	 * </pre>
	 * @Method Name : ReCalculateFormDetail
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Calculate/ReCalculateFormDetail")
	@ResponseBody
	public AjaxResultVO ReCalculateFormDetail(CalculateVo calculateVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			calculateVo.setCaseString("ReCalculate_Detail");
			List<?> list = calculateService.SelectListData(calculateVo);

			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : calculateForm
	 * 2. 처리내용 : 정산화면 호출
	 * </pre>
	 * @Method Name : calculateForm
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Calculate/CalculateForm")
	public String calculateForm(CalculateVo calculateVo, ModelMap model, HttpSession session) throws Exception{
		model.addAttribute("calculateVo", calculateVo);

		if ((String) session.getAttribute("id") != null) {
			return "/Calculate/CalculateForm";
		} else {
			return "/Logout";
		}
	}

	@RequestMapping(value = "Calculate/ReCalculateForm")
	public String recalculateForm(CalculateVo calculateVo, ModelMap model, HttpSession session) throws Exception{
		model.addAttribute("calculateVo", calculateVo);

		if ((String) session.getAttribute("id") != null) {
			return "/Calculate/ReCalculateForm";
		} else {
			return "/Logout";
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : InsertCalculate
	 * 2. 처리내용 : 정산등록
	 * </pre>
	 * @Method Name : InsertCalculate
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "Calculate/InsertCalculate")
	@ResponseBody
	public AjaxResultVO InsertCalculate(CalculateVo calculateVo, ModelMap model, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			if (calculateVo.getDeviceIdx() == null || calculateVo.getDeviceIdx().toString() == "" || calculateVo.getDeviceIdx().toString() == "0") {
				result.setResultCode(1);
				messages.put("title", "정상적인 접근이 아닙니다.");
				result.setMessages(messages);
				return result;
			}

			calculateVo.setCaseString("Calculate_InsertCalculate");
			calculateVo.setRegUser((String)session.getAttribute("id"));
			calculateVo.setModUser((String)session.getAttribute("id"));
			int resultCode = calculateService.InsertData(calculateVo);

			result.setData(resultCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : UpdateCalculate
	 * 2. 처리내용 : 정산내용 수정
	 * </pre>
	 * @Method Name : UpdateCalculate
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param calculateVo
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "Calculate/UpdateCalculate")
	@ResponseBody
	public AjaxResultVO UpdateCalculate(CalculateVo calculateVo, ModelMap model, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			if (calculateVo.getDeviceIdx() == null || calculateVo.getDeviceIdx().toString() == "" || calculateVo.getDeviceIdx().toString() == "0") {
				result.setResultCode(1);
				messages.put("title", "정상적인 접근이 아닙니다.");
				result.setMessages(messages);
				return result;
			}

			calculateVo.setCaseString("Calculate_UpdateCalculate");
			calculateVo.setRegUser((String)session.getAttribute("id"));
			calculateVo.setModUser((String)session.getAttribute("id"));
			int resultCode = calculateService.InsertData(calculateVo);

			result.setData(resultCode);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
}
