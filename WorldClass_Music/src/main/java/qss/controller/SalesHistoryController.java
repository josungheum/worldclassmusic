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

import net.sf.json.JSONArray;
import qss.impl.SalesHistoryImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.SalesHistoryVo;
import qss.vo.ScreenVo;
import qss.vo.SystemCodeVo;

/**
 * 매출 관리 > 매출 관리
 * <pre>
 * qss.controller
 *    |_ SalesHistoryController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:05
 * @version :
 * @author : admin
 */
@Controller
public class SalesHistoryController {

	@Resource(name="salesHistoryService")
	Qss salesHistoryService = new SalesHistoryImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();
	private static final Log logger = LogFactory.getLog(SalesHistoryController.class);

	/**
	 * <pre>
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 1. 15.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param salesHistoryVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="SalesHistory/Main")
    public String Main(@ModelAttribute("SalesHistoryVo")SalesHistoryVo salesHistoryVo, HttpSession session, ModelMap model) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			SystemCodeVo systemCodeVo = new SystemCodeVo();
			systemCodeVo.setCaseString("SystemCode_ListSystemCode");
			systemCodeVo.setCodeGroup("PAY0000");

			/*
			String systemCodeList = JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo)).toString();
			systemCodeList = systemCodeList.replaceAll("\\\"", "\\\\\"");
			systemCodeList = systemCodeList.replaceAll("\\\'", "\\\\\'");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println(systemCodeList);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			model.addAttribute("SystemCodeList", systemCodeList);
			*/
//			model.addAttribute("SystemCodeList", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo))));
			model.addAttribute("SystemCodeList", systemCodeService.SelectListData(systemCodeVo));

			systemCodeVo.setCaseString("SystemCode_ListSystemCode");
			systemCodeVo.setCodeGroup("ORD0000");
			model.addAttribute("payProcCodeList", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo))));

			return "/SalesHistory/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }


	/**
	 * <pre>
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : List
	 * @date : 2019. 1. 15.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param salesHistoryVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "SalesHistory/List")
	@ResponseBody
	public AjaxResultVO List(SalesHistoryVo salesHistoryVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		salesHistoryVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		salesHistoryVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		salesHistoryVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		salesHistoryVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		salesHistoryVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			salesHistoryVo.setCaseString("SalesHistory_List");
			List<SalesHistoryVo> list = (List<SalesHistoryVo>) salesHistoryService.SelectListData(salesHistoryVo);
			result.setData(list);

			salesHistoryVo.setCaseString("SalesHistory_ListCnt");
			result.setiTotalDisplayRecords(salesHistoryService.DataByCnt(salesHistoryVo));

			salesHistoryVo.setCaseString("SalesHistory_Stat");
			List<ScreenVo> totlist = (List<ScreenVo>) salesHistoryService.SelectListData(salesHistoryVo);
			result.setTotalData(totlist);



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
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : scheduleForm
	 * @date : 2019. 1. 15.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param salesHistoryVo
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "SalesHistory/SalesHistoryForm/{brandIdx}/{francIdx}/{deviceIdx}/{orderMasterIdx}")
	public String salesHistoryForm(SalesHistoryVo salesHistoryVo, ModelMap model, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		salesHistoryVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		salesHistoryVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		salesHistoryVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		salesHistoryVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		salesHistoryVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			if (salesHistoryVo.getOrderMasterIdx() == null || salesHistoryVo.getOrderMasterIdx().toString() == "" || salesHistoryVo.getOrderMasterIdx().toString() == "0") {
				result.setResultCode(1);
				messages.put("title", "정상적인 접근이 아닙니다.");
				result.setMessages(messages);
				return "/SalesHistory/SalesHistoryForm";
			}

			salesHistoryVo.setCaseString("SalesHistory_Detail");
			salesHistoryVo = (SalesHistoryVo) salesHistoryService.SelectData(salesHistoryVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}
		model.addAttribute("SalesHistoryVo", salesHistoryVo);

		return "/SalesHistory/SalesHistoryForm";
	}

}
