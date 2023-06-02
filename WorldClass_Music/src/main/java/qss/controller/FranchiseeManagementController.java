package qss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import qss.impl.BrandManagementImpl;
import qss.impl.CommonImpl;
import qss.impl.FranchiseeManagementImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandPaymentTypeVo;
import qss.vo.BrandVo;
import qss.vo.FranchiseeVo;
import qss.vo.SystemCodeVo;

/**
 * 매장 관리 > 사이트 관리
 * <pre>
 * qss.controller
 *    |_ FranchiseeManagementController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:19:56
 * @version :
 * @author : admin
 */
@Controller
public class FranchiseeManagementController {
	@Resource(name="franchiseeManagementService")
	Qss franchiseeManagementService = new FranchiseeManagementImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	private static final Log logger = LogFactory.getLog(FranchiseeManagementController.class);

	/**
	 * <pre>
	 * 1. 개요 : 사이트 화면
	 * 2. 처리내용 : 사이트 화면
	 * </pre>
	 * @Method Name : Brand
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="FranchiseeManagement/Main")
    public String Brand(@ModelAttribute("FranchiseeVo")FranchiseeVo franchiseeVo, HttpSession session, ModelMap model, HttpServletRequest request)
	{
		if ((String)session.getAttribute("id") != null) {

			return "/FranchiseeManagement/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }

	/**
	 * <pre>
	 * 1. 개요 : 사이트 기본 화면
	 * 2. 처리내용 : 사이트 기본 화면
	 * </pre>
	 * @Method Name : BasicAttr
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "FranchiseeManagement/BasicAttrNullTemp")
	public String BasicAttr(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session)
	{
		List<BrandPaymentTypeVo> francPaymentTypeList = new ArrayList<BrandPaymentTypeVo>();
		List<BrandPaymentTypeVo> brandPaymentTypeList = new ArrayList<BrandPaymentTypeVo>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		FranchiseeVo newFrancVo = new FranchiseeVo();
		franchiseeVo.setCaseString("Franchisee_ViewFranchisee");
		try {
			newFrancVo = (FranchiseeVo) franchiseeManagementService.SelectData(franchiseeVo);

			BrandVo brandVo = new BrandVo();
			brandVo.setCaseString("Brand_ListBrandPaymentType");
			brandVo.setDomainIdx(franchiseeVo.getDomainIdx());
			brandVo.setBrandIdx(franchiseeVo.getBrandIdx());
			brandPaymentTypeList = (List<BrandPaymentTypeVo>) brandManagementService.SelectListData(brandVo);

			//기본 정보 형태 구분 조회
			franchiseeVo.setCaseString("Franchisee_ListFrancPaymentType");
			francPaymentTypeList = (List<BrandPaymentTypeVo>) franchiseeManagementService.SelectListData(franchiseeVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		//공통코드 조회
		franchiseeVo.setCaseString("SystemCode_ListSystemCode");
		List<SystemCodeVo> francPaymentType01 = (List<SystemCodeVo>) systemCodeService.SelectCodeData("PAY0000");
		List<SystemCodeVo> francPaymentTypeNew01 = new ArrayList<SystemCodeVo>();
		for(int i = 0; i < brandPaymentTypeList.size(); i++) {
			for(int j = 0; j < francPaymentType01.size(); j++) {
				if(brandPaymentTypeList.get(i).getPayType().equals(francPaymentType01.get(j).getCodeValue())
					&& brandPaymentTypeList.get(i).getPayTypeGroup().equals("PAY0000"))
					francPaymentTypeNew01.add(francPaymentType01.get(j));
			}
		}


		List<SystemCodeVo> francPaymentType02 = (List<SystemCodeVo>) systemCodeService.SelectCodeData("PNT0000");
		List<SystemCodeVo> francPaymentTypeNew02 = new ArrayList<SystemCodeVo>();
		for(int i = 0; i < brandPaymentTypeList.size(); i++) {
			for(int j = 0; j < francPaymentType02.size(); j++) {
				if(brandPaymentTypeList.get(i).getPayType().equals(francPaymentType02.get(j).getCodeValue())
						&& brandPaymentTypeList.get(i).getPayTypeGroup().equals("PNT0000"))
					francPaymentTypeNew02.add(francPaymentType02.get(j));
			}
		}

		model.addAttribute("francPaymentTypeList", XssUtil.jsonText(JSONArray.fromObject(francPaymentTypeList)));
		model.addAttribute("franchiseeVo", newFrancVo);
		model.addAttribute("francPaymentType01", francPaymentTypeNew01);
		model.addAttribute("francPaymentType02", francPaymentTypeNew02);

		return "/FranchiseeManagement/BasicAttr";
	}

	/**
	 * <pre>
	 * 1. 개요 : 사이트 등록 팝업
	 * 2. 처리내용 : 사이트 등록 팝업
	 * </pre>
	 * @Method Name : BasicAttrForm
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/BasicAttrFormNullTemp")
	public String BasicAttrForm(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session)
	{
		//model.addAttribute("franchiseeVo", franchiseeVo);
	    model.addAttribute(franchiseeVo);
		return "/FranchiseeManagement/BasicAttrForm";
	}
	
	@RequestMapping(value = "FranchiseeManagement/BasicAttrGroupFormNullTemp")
	public String BasicAttrGroupForm(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session)
	{
	    if (franchiseeVo.getGroupIdx() == null || franchiseeVo.getGroupIdx() == "0") {
            franchiseeVo.setGroupIdx("0");
            model.addAttribute(franchiseeVo);
            return "/FranchiseeManagement/BasicAttrGroupForm";
        }
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            franchiseeVo.setCaseString("Franchisee_SiteGroupGet");
            franchiseeVo = (FranchiseeVo) franchiseeManagementService.SelectData(franchiseeVo);

        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            messages.put("title", "서버오류가 발생하였습니다.");
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
            result.setMessages(messages);
        }

        model.addAttribute(franchiseeVo);
		return "/FranchiseeManagement/BasicAttrGroupForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 상세 조회
	 * 2. 처리내용 : 상세 조회
	 * </pre>
	 * @Method Name : MoreInfo
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/DetailNullTemp")
	public String MoreInfo(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session)
	{
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		try {
			franchiseeVo.setCaseString("Franchisee_ViewFranchiseeDetail");
			franchiseeVo  = (FranchiseeVo)franchiseeManagementService.SelectData(franchiseeVo);

			//공통코드 조회
			model.addAttribute("catetoryTypeList", systemCodeService.SelectCodeData("CAT0000"));
			model.addAttribute("colCountTypeList", systemCodeService.SelectCodeData("COL0000"));
			model.addAttribute("franchiseeVo",franchiseeVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "/FranchiseeManagement/Detail";
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말기 화면
	 * 2. 처리내용 : 단말기 화면
	 * </pre>
	 * @Method Name : Koisk
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param KioskVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/KioskNullTemp")
	public String Koisk(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, @ModelAttribute("KioskVo") qss.vo.KioskVo KioskVo, ModelMap model, HttpSession session)
	{
		// 관리자 정보 set
		franchiseeVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		franchiseeVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		franchiseeVo.setSessionAdminType((String)session.getAttribute("adminType"));

		model.addAttribute(franchiseeVo);

		return "/FranchiseeManagement/Kiosk";
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말기 저장 화면
	 * 2. 처리내용 : 단말기 저장 화면
	 * </pre>
	 * @Method Name : KioskForm
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "FranchiseeManagement/KioskFormNullTemp")
	public String KioskForm(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session)
	{
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		FranchiseeVo franchiseeVoResult = franchiseeVo;
		List<BrandDeviceTypeVo> brandDeviceTypeList = new ArrayList<BrandDeviceTypeVo>();
		if(franchiseeVo.getDeviceIdx() != null && franchiseeVo.getDeviceIdx().intValue() > 0){
			franchiseeVo.setCaseString("Franchisee_ViewKiosk");
			try {
				franchiseeVoResult = (FranchiseeVo)franchiseeManagementService.SelectData(franchiseeVo);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			// 해당 매장이 속한 서비스에서 설정한 디바이스 정보 조회
			BrandVo brandVo = new BrandVo();
			brandVo.setDomainIdx(franchiseeVoResult.getDomainIdx());
			brandVo.setBrandIdx((String)franchiseeVoResult.getBrandIdx());
			brandVo.setCaseString("Brand_ListBrandDeviceType");
			brandDeviceTypeList = (List<BrandDeviceTypeVo>) brandManagementService.SelectListData(brandVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<SystemCodeVo> colCountTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("COL0000");
		List<SystemCodeVo> deviceResTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVR0000");
		List<SystemCodeVo> deviceSiteTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVS0000");
		List<SystemCodeVo> deviceTranTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVT0000");

		model.addAttribute("brandDeviceTypeList", brandDeviceTypeList);
		model.addAttribute("deviceResTypeList", deviceResTypeList);
		model.addAttribute("deviceSiteTypeList", deviceSiteTypeList);
		model.addAttribute("deviceTranTypeList", deviceTranTypeList);
		model.addAttribute("colCountTypeList", colCountTypeList);
		model.addAttribute(franchiseeVoResult);

		return "/FranchiseeManagement/KioskForm";
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 사이트 기본 저장
	 * 2. 처리내용 : 사이트 기본 정보를 저장한다.
	 * </pre>
	 * @Method Name : Update
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/Update")
	@ResponseBody
	public AjaxResultVO Update(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_OverlapCountFranchisee");
			int cnt = franchiseeManagementService.DataByCnt(franchiseeVo);
			if(cnt == 0){
				franchiseeVo.setCaseString("Franchisee_UpdateFranchisee");
				cnt = franchiseeManagementService.UpdateData(franchiseeVo);
				if (cnt > 0) {
					result.setResultCode(0);
				}
				else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 사이트 코드입니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 사이트 목록 조회
	 * 2. 처리내용 : 사이트 목록 조회
	 * </pre>
	 * @Method Name : FrancList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "FranchiseeManagement/FrancList")
	@ResponseBody
	public AjaxResultVO FrancList(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		franchiseeVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		franchiseeVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		franchiseeVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			franchiseeVo.setCaseString("Franchisee_ListFranchisee");
			List<FranchiseeVo> list = (List<FranchiseeVo>) franchiseeManagementService.SelectListData(franchiseeVo);
			result.setData(list);

			franchiseeVo.setCaseString("Franchisee_ListCountFranchisee");
			result.setiTotalDisplayRecords(franchiseeManagementService.DataByCnt(franchiseeVo));

			result.setResultCode(0);
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}



	/**
	 * <pre>
	 * 1. 개요 : 사이트 기본 정보 등록
	 * 2. 처리내용 : 사이트 기본 정보 등록
	 * </pre>
	 * @Method Name : Insert
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/Insert")
	@ResponseBody
	public AjaxResultVO Insert(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try
		{

			franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			franchiseeVo.setRegUser((String)session.getAttribute("id"));
			franchiseeVo.setCaseString("Franchisee_OverlapCountFranchisee");
			int cnt = franchiseeManagementService.DataByCnt(franchiseeVo);
			if(cnt == 0){
				franchiseeVo.setCaseString("Franchisee_InsertFranchisee");
				cnt = franchiseeManagementService.UpdateData(franchiseeVo);
				if (cnt > 0)
				{
					result.setResultCode(0);
				}
				else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 사이트 코드입니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 사이트 삭제
	 * 2. 처리내용 : 사이트 삭제
	 * </pre>
	 * @Method Name : Delete
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/Delete")
	@ResponseBody
	public AjaxResultVO Delete(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_DeleteFranchisee");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}



	/**
	 * <pre>
	 * 1. 개요 : 상세 수정
	 * 2. 처리내용 : 상세 수정
	 * </pre>
	 * @Method Name : DetailInfoUpdate
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/DetailInfoUpdate")
	@ResponseBody
	public AjaxResultVO DetailInfoUpdate(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_UpdateFranchiseeDetail");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);;

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 사이트 활성화
	 * 2. 처리내용 : 사이트 활성화
	 * </pre>
	 * @Method Name : FrancActive
	 * @date : 2019. 1. 7.
	 * @author : p
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		p				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/FrancActive")
	@ResponseBody
	public AjaxResultVO FrancActive(FranchiseeVo franchiseeVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			franchiseeVo.setRegUser((String)session.getAttribute("id"));
			franchiseeVo.setCaseString("Franchisee_ActiveFranchisee");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0) {
				result.setResultCode(0);
			} else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}



	/**
	 * <pre>
	 * 1. 개요 : 단말기 목록 화면
	 * 2. 처리내용 : 단말기 목록 화면
	 * </pre>
	 * @Method Name : KioskList
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "FranchiseeManagement/KioskList")
	@ResponseBody
	public AjaxResultVO KioskList(FranchiseeVo franchiseeVo,HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();

		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		List<FranchiseeVo> list;
		try {
			franchiseeVo.setCaseString("Franchisee_ListKiosk");
			list = (List<FranchiseeVo>) franchiseeManagementService.SelectListData(franchiseeVo);

			franchiseeVo.setCaseString("Franchisee_ListCountKiosk");
			result.setiTotalDisplayRecords(franchiseeManagementService.DataByCnt(franchiseeVo));

			result.setResultCode(0);
			result.setData(list);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}



		return result;
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 단말 생성
	 * 2. 처리내용 : 단말 생성
	 * </pre>
	 * @Method Name : KioskCreate
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/KioskCreate")
	@ResponseBody
	public AjaxResultVO KioskCreate(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_OverlapCountKiosk");
			int cnt = franchiseeManagementService.DataByCnt(franchiseeVo);
			if(cnt == 0){
				if(franchiseeVo.getDeviceIdx() != null && franchiseeVo.getDeviceIdx().intValue() > 0)
					franchiseeVo.setCaseString("Franchisee_UpdateKiosk");
				else
					franchiseeVo.setCaseString("Franchisee_InsertKiosk");

				cnt = franchiseeManagementService.UpdateData(franchiseeVo);

				if (cnt > 0) {
					result.setResultCode(0);
				}
				else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 단말 코드입니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말 삭제
	 * 2. 처리내용 : 단말 삭제
	 * </pre>
	 * @Method Name : KioskDelete
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/KioskDelete")
	@ResponseBody
	public AjaxResultVO KioskDelete(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_DeleteKiosk");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0)
				result.setResultCode(0);
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}


		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말 수정
	 * 2. 처리내용 : 단말 수정
	 * </pre>
	 * @Method Name : KioskUpdate
	 * @date : 2019. 1. 5.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 5.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/KioskUpdate")
	@ResponseBody
	public AjaxResultVO KioskUpdate(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_UpdateKiosk");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 사이트 단말 활성화
	 * 2. 처리내용 : 사이트 단말 활성화
	 * </pre>
	 * @Method Name : NoticeActive
	 * @date : 2019. 1. 7.
	 * @author : p
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		p				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FranchiseeManagement/KioskActive")
	@ResponseBody
	public AjaxResultVO KioskActive(FranchiseeVo franchiseeVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_ActiveKiosk");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0)
				result.setResultCode(0);
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 사이트 콤보 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : FranchiseCombo
	 * @date : 2019. 6. 19.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 19.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param franchiseeVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="FranchiseeManagement/FranchiseCombo")
	@ResponseBody
	public AjaxResultVO FranchiseCombo(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			String id = (String)session.getAttribute("id");
			if(!StringUtils.isEmpty(id)) {
				franchiseeVo.setCaseString("Franchisee_SelectCombo");
				List<?> franchiseList =  franchiseeManagementService.SelectListData(franchiseeVo);

				result.setData(franchiseList);
			}

		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	@RequestMapping(value = "FranchiseeManagement/InsertGroup")
	@ResponseBody
	public AjaxResultVO InsertGroup(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try
		{

			franchiseeVo.setRegUser((String)session.getAttribute("id"));
			franchiseeVo.setCaseString("Franchisee_InsertFranchiseeGroup");
			int cnt = franchiseeManagementService.InsertData(franchiseeVo);
			if (cnt > 0)
			{
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

    @RequestMapping(value = "FranchiseeManagement/UpdateGroup")
    @ResponseBody
    public AjaxResultVO UpdateGroup(FranchiseeVo franchiseeVo, HttpSession session)
    {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
        franchiseeVo.setRegUser((String)session.getAttribute("id"));
        try {
            franchiseeVo.setCaseString("Franchisee_UpdateFranchiseeGroup");
            int cnt = franchiseeManagementService.UpdateData(franchiseeVo);;

            if (cnt > 0) {
                result.setResultCode(0);
            }
            else {
                result.setResultCode(1);
                messages.put("title", "처리 되지 않았습니다.");
            }
            result.setMessages(messages);
        }
        catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }
        return result;
    }
	
	@RequestMapping(value = "FranchiseeManagement/DeleteGroup")
	@ResponseBody
	public AjaxResultVO DeleteGroup(FranchiseeVo franchiseeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		franchiseeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		franchiseeVo.setRegUser((String)session.getAttribute("id"));
		try {
			franchiseeVo.setCaseString("Franchisee_DeleteGroup");
			int cnt = franchiseeManagementService.UpdateData(franchiseeVo);

			if (cnt > 0)
				result.setResultCode(0);
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}


		return result;
	}

   @RequestMapping(value = "FranchiseeManagement/GroupFind", method = RequestMethod.GET)
    public String PLFind(@ModelAttribute("FranchiseeVo") FranchiseeVo franchiseeVo, ModelMap model, HttpSession session) throws Exception
    {
        model.addAttribute("franchiseeVo", franchiseeVo);
        return "/FranchiseeManagement/GroupFind";
    }
}