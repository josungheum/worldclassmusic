package qss.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import qss.impl.BrandManagementImpl;
import qss.impl.ResourceImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.BrandVo;
import qss.vo.KioskVo;
import qss.vo.SystemCodeVo;

/**
 * <pre>
 * qss.controller
 *    |_ ResourceController.java
 *
 * </pre>
 * @date : 2019. 1. 15. 오전 11:00:33
 * @version :
 * @author : p
 */
@Controller
public class ResourceController {

	@Resource(name="resourceService")
	Qss resourceService = new ResourceImpl();

	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	private static final Log logger = LogFactory.getLog(ScheduleController.class);

	/**
	 * <pre>
	 * 1. 개요 : 통합 상환판 조회
	 * 2. 처리내용 : 통합 상환판 조회
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기			최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="Resource/Main")
    public String Main(@ModelAttribute("BrandVo")BrandVo brandVo, HttpSession session, ModelMap model, HttpServletRequest request)
	{
		if ((String)session.getAttribute("id") != null) {
			try {
				brandVo.setCaseString("Brand_ListBrand");
				brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));

				// 관리자 정보 set
				brandVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
				brandVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
				brandVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
				brandVo.setSessionAdminType((String)session.getAttribute("adminType"));

//				model.addAttribute("brandList", XssUtil.jsonText(JSONArray.fromObject(brandManagementService.SelectListData(brandVo))));

				SystemCodeVo systemCodeVo = new SystemCodeVo();
				systemCodeVo.setCaseString("SystemCode_ListSystemCode");
				systemCodeVo.setCodeGroup("SRD0000");
				model.addAttribute("codeList", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo))));

			} catch (Exception e) {
				logger.error("========== Main ==========" + e.getMessage());
			}
			return "/Resource/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }


	/**
	 * <pre>
	 * 1. 개요 : 접속 상태 세부 현황
	 * 2. 처리내용 : 접속 상태 세부 현황
	 * </pre>
	 * @Method Name : KioskResource
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기			최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskVo
	 * @return
	 */
	@RequestMapping(value = "Resource/KioskResource")
	@ResponseBody
	public AjaxResultVO KioskResource(@ModelAttribute("KioskVo") KioskVo kioskVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {

			// 관리자 정보 set
			kioskVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
			kioskVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
			kioskVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			kioskVo.setSessionAdminType((String)session.getAttribute("adminType"));

			//목록 조회
			kioskVo.setCaseString("Resource_ListResource");
			result.setData(resourceService.SelectListData(kioskVo));

			//목록 카운터
			kioskVo.setCaseString("Resource_ListCountResource");
			result.setiTotalDisplayRecords(resourceService.DataByCnt(kioskVo));
			result.setResultCode(0);

		}catch (Exception e){
			logger.error("========== KioskResource ==========" + e.getMessage());
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 통합 미디어 현황
	 * 2. 처리내용 : 통합 미디어 현황
	 * </pre>
	 * @Method Name : ResourceStatistics
	 * @date : 2019. 1. 15.
	 * @author : p
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		p				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskVo
	 * @return
	 */
	@RequestMapping(value = "Resource/ResourceStatistics")
	@ResponseBody
	public AjaxResultVO ResourceStatistics(@ModelAttribute("KioskVo") KioskVo kioskVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			// 관리자 정보 set
			kioskVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
			kioskVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
			kioskVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			kioskVo.setSessionAdminType((String)session.getAttribute("adminType"));

			kioskVo.setCaseString("Resource_ViewResource");
			result.setData(resourceService.SelectListData(kioskVo));

			//목록 카운터
			kioskVo.setCaseString("Resource_ViewCountResource");
			result.setiTotalDisplayRecords(resourceService.DataByCnt(kioskVo));
			result.setResultCode(0);
		}catch (Exception e){
			logger.error("========== ResourceStatistics ==========" + e.getMessage());
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}


}