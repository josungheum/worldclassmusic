package qss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.BarLedImpl;
import qss.impl.BrandManagementImpl;
import qss.impl.CommonImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandVo;
import qss.vo.BarLedVo;
import qss.vo.SystemCodeVo;


/**
 * 편성관리 > Bar Led 전광판 관리
 * <pre>
 * qss.controller
 *    |_ BarLedController.java
 *
 * </pre>
 */
@Controller
public class BarLedController {
	@Resource(name="barledService")
	Qss barledService = new BarLedImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();
	
	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();
	
	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	private static final Log logger = LogFactory.getLog(BarLedController.class);

	@RequestMapping(value="BarLed/Main")
    public String Main(@ModelAttribute("BarLedVo")BarLedVo barledVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/BarLed/Main";
		} else {
			return "redirect:/Logout";
		}
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "BarLed/List")
	@ResponseBody
	public AjaxResultVO List(BarLedVo barledVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		barledVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		barledVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		barledVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		barledVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		barledVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			barledVo.setCaseString("BarLed_List");
			List<BarLedVo> list = (List<BarLedVo>) barledService.SelectListData(barledVo);
			result.setData(list);


			barledVo.setCaseString("BarLed_ListCnt");
			result.setiTotalDisplayRecords(barledService.DataByCnt(barledVo));

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
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "BarLed/Form")
	public String Form(@ModelAttribute("BarLedVo") BarLedVo barledVo, ModelMap model, HttpSession session) throws Exception
	{
		barledVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		List<BrandDeviceTypeVo> brandDeviceTypeList = new ArrayList<BrandDeviceTypeVo>();
		try {
			BrandVo brandVo = new BrandVo();
			brandVo.setDomainIdx(barledVo.getDomainIdx());
			brandVo.setBrandIdx((String)barledVo.getBrandIdx());
			brandVo.setCaseString("Brand_ListBrandDeviceType");
			brandDeviceTypeList = (List<BrandDeviceTypeVo>) brandManagementService.SelectListData(brandVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List<SystemCodeVo> deviceResTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVR0000");
		List<SystemCodeVo> deviceSiteTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVS0000");
		
		model.addAttribute("brandDeviceTypeList", brandDeviceTypeList);
		model.addAttribute("deviceResTypeList", deviceResTypeList);
		model.addAttribute("deviceSiteTypeList", deviceSiteTypeList);
		
		if (barledVo.getMqIdx() == null || barledVo.getMqIdx().intValue() == 0) {
			return "/BarLed/Form";
		}
		
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_Get");
			barledVo = (BarLedVo) barledService.SelectData(barledVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}
		
		model.addAttribute("BarLedVo", barledVo);
		
		if ((String) session.getAttribute("id") != null) {
			return "/BarLed/Form";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping("BarLed/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(BarLedVo barledVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_SearchKioskTree");

			barledVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			barledVo.setSessionAdminType((String)session.getAttribute("adminType"));
			List<?> list = barledService.SelectListData(barledVo);
			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("data", list);

			if (session.getAttribute("brandIdx").equals("")) {
				resultMap.put("target", "DOMAIN-" + (String)session.getAttribute("domainIdx"));
			}else if(session.getAttribute("francIdx").equals("")){
				resultMap.put("target", "BRAND-" + (String)session.getAttribute("brandIdx"));
			}else{
				resultMap.put("target", "FRANCHISEE-" + (String)session.getAttribute("francIdx"));
			}

			result.setData(resultMap);
			result.setResultCode(0);
		} catch (Exception e) {
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
		}
		result.setMessages(messages);

		return result;
	}
	
	@RequestMapping(value = "BarLed/Create")
	@ResponseBody
	public AjaxResultVO Create(BarLedVo barledVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_Create");
			barledVo.setRegUser((String)session.getAttribute("id"));
			barledVo.setModUser((String)session.getAttribute("id"));

			int resultCode = barledService.InsertData(barledVo);

			if (resultCode == 1) {
				result.setResultCode(1);
				messages.put("title", "Bar Led 전광판 등록 중 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
	
	@RequestMapping(value = "BarLed/Update")
	@ResponseBody
	public AjaxResultVO Update(BarLedVo barledVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_Update");
			barledVo.setRegUser((String)session.getAttribute("id"));
			barledVo.setModUser((String)session.getAttribute("id"));

			int resultCode = barledService.UpdateData(barledVo);

			if (resultCode == 1) {
				result.setResultCode(1);
				messages.put("title", "Bar Led 전광판 변경 중 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
	
	
	@RequestMapping(value = "BarLed/Delete")
	@ResponseBody
	public AjaxResultVO Delete(BarLedVo barledVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_Delete");
			barledVo.setRegUser((String)session.getAttribute("id"));
			barledVo.setModUser((String)session.getAttribute("id"));
			int cnt = barledService.DeleteDataByObjectParam(null, barledVo);

			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "정상적으로 삭제되지 못한 Bar Led 전광판이 존재합니다.");
				result.setMessages(messages);
			}else {
				result.setResultCode(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
	
	@RequestMapping(value = "BarLed/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(BarLedVo barledVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			barledVo.setCaseString("BarLed_SetActiveYn");
			barledVo.setModUser((String)session.getAttribute("id"));
			int cnt = barledService.UpdateData(barledVo);

			if (cnt > 0) {
				result.setResultCode(1);
				messages.put("title", "로직 오류가 발생하였습니다.");
				result.setMessages(messages);
			}else{
				result.setResultCode(0);
			}
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