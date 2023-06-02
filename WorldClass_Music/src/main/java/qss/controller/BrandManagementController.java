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
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import qss.impl.BrandManagementImpl;
import qss.impl.CommonImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandPaymentTypeVo;
import qss.vo.BrandVo;

/**
 * 매장 관리 > 브랜드 관리
 * <pre>
 * qss.controller
 *    |_ BrandManagementController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:19:16
 * @version :
 * @author : khk
 */
@Controller
public class BrandManagementController {
	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();


	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	private static final Log logger = LogFactory.getLog(BrandManagementController.class);

	/**
	 * <pre>
	 * 1. 개요 : 브랜드 메인 조회
	 * 2. 처리내용 : 브랜드 메인화면
	 * </pre>
	 * @Method Name : Brand
	 * @date : 2019. 1. 4.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 4.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="BrandManagement/Main")
    public String brandMain(@ModelAttribute("BrandVo")BrandVo brandVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/BrandManagement/Main";
		} else {
			return "redirect:/Logout";
		}
    }

	/**
	 * <pre>
	 * 1. 개요 : 브랜드 기본 정보 조회
	 * 2. 처리내용 : 브랜드 기본 정보 화면
	 * </pre>
	 * @Method Name : BasicAttr
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
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "BrandManagement/BasicAttrNullTemp")
	public String basicAttr(@ModelAttribute("BrandVo") BrandVo brandVo, ModelMap model, HttpSession session)
	{
		BrandVo newBrandVo = new BrandVo();
		List<BrandPaymentTypeVo> brandPaymentTypeList = new ArrayList<BrandPaymentTypeVo>();
		List<BrandDeviceTypeVo> brandDeviceTypeList = new ArrayList<BrandDeviceTypeVo>();

		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		if(brandVo.getBrandIdx() != null && !brandVo.getBrandIdx().equals("undefined"))
		{
			try {
				brandVo.setCaseString("Brand_ViewBrand");
				newBrandVo = (BrandVo) brandManagementService.SelectData(brandVo);

				brandVo.setCaseString("Brand_ListBrandPaymentType");
				brandPaymentTypeList = (List<BrandPaymentTypeVo>) brandManagementService.SelectListData(brandVo);

				brandVo.setCaseString("Brand_ListBrandDeviceType");
				brandDeviceTypeList = (List<BrandDeviceTypeVo>) brandManagementService.SelectListData(brandVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		//공통코드 조회
		brandVo.setCaseString("SystemCode_ListSystemCode");
		model.addAttribute("brandPaymentType01", systemCodeService.SelectCodeData("PAY0000"));
		model.addAttribute("brandPaymentType02", systemCodeService.SelectCodeData("PNT0000"));
		model.addAttribute("brandDeviceType", systemCodeService.SelectCodeData("DVC0000"));

		model.addAttribute("brandVo", newBrandVo);
		model.addAttribute("brandPaymentTypeList", XssUtil.jsonText(JSONArray.fromObject(brandPaymentTypeList)));
		model.addAttribute("brandDeviceTypeList", XssUtil.jsonText(JSONArray.fromObject(brandDeviceTypeList)));


		return "/BrandManagement/BasicAttr";
	}



	/**
	 * <pre>
	 * 1. 개요 : 브랜드 등록 팝업
	 * 2. 처리내용 : 브랜드 등록 팝업 화면
	 * </pre>
	 * @Method Name : BasicAttrForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/BasicAttrFormNullTemp")
	public String basicAttrForm(@ModelAttribute("BrandVo") BrandVo brandVo, ModelMap model, HttpSession session) throws Exception
	{
		model.addAttribute(brandVo);
		return "/BrandManagement/BasicAttrForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 브랜드 할인율 화면
	 * 2. 처리내용 : 브랜드 할인율 화면
	 * </pre>
	 * @Method Name : Discount
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param userVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/DiscountNullTemp")
	public String discount(@ModelAttribute("BrandVo") BrandVo brandVo, ModelMap model, HttpSession session) throws Exception
	{
		model.addAttribute(brandVo);

		return "/BrandManagement/Discount";
	}

	/**
	 * <pre>
	 * 1. 개요 : 할인율 등록/수정 화면
	 * 2. 처리내용 : 할인율 등록/수정 화면
	 * </pre>
	 * @Method Name : DiscountForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "BrandManagement/DiscountFormNullTemp")
	public String discountForm(@ModelAttribute("BrandVo") BrandVo brandVo, ModelMap model, HttpSession session)
	{

		if(brandVo.getDiscountIdx() != null && brandVo.getDiscountIdx().intValue() > 0){
			try {
				brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				brandVo.setCaseString("Brand_ViewDiscount");
				brandVo = (BrandVo) brandManagementService.SelectData(brandVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		//공통코드 조회
		brandVo.setCaseString("SystemCode_ListSystemCode");
		model.addAttribute("discountLevel", systemCodeService.SelectCodeData("DIS0000"));
		model.addAttribute(brandVo);
		return "/BrandManagement/DiscountForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 브랜드 목록 조회
	 * 2. 처리내용 : 브랜드 목록을 조회한다.
	 * </pre>
	 * @Method Name : BrandList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "BrandManagement/BrandList")
	@ResponseBody
	public AjaxResultVO brandList(BrandVo brandVo, HttpSession session)
	{
		//리턴 VO
		AjaxResultVO result = new AjaxResultVO();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		// 관리자 정보 set
		brandVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		brandVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		brandVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			//목록 조회
			brandVo.setCaseString("Brand_ListBrand");
			result.setData(brandManagementService.SelectListData(brandVo));

			//목록 카운터
			brandVo.setCaseString("Brand_ListCountBrand");
			result.setiTotalDisplayRecords(brandManagementService.DataByCnt(brandVo));
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}





	/**
	 * <pre>
	 * 1. 개요 : 브랜드 기본 정보 저장 및 수정
	 * 2. 처리내용 : 브랜드 기본 정보 저장 및 수정
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
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/Update")
	@ResponseBody
	public AjaxResultVO update(BrandVo brandVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));
		int cnt = 0;
		try
		{
			//중복 브랜드 확인
			brandVo.setCaseString("Brand_OverlapCountBrand");
			if(brandManagementService.DataByCnt(brandVo) == 0){
				//브랜드 수정
				brandVo.setCaseString("Brand_UpdateBrand");
				cnt = brandManagementService.UpdateData(brandVo);
				if (cnt > 0) {
					result.setResultCode(0);
					// 브랜드 대표 이미지 가져오기
					brandVo.setCaseString("Brand_ViewBrand");
					session.setAttribute("brandMainImgPath", ((BrandVo)brandManagementService.SelectData(brandVo)).getBrandMainImgPath());
				}else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 브랜드 코드입니다.");
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
	 * 1. 개요 : 브랜드 기본 정보 등록
	 * 2. 처리내용 : 브랜드 기본 정보를 등록한다.
	 * </pre>
	 * @Method Name : Insert
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/Insert")
	@ResponseBody
	public AjaxResultVO insert(BrandVo brandVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));
		try
		{
			brandVo.setCaseString("Brand_OverlapCountBrand");
			int cnt = brandManagementService.DataByCnt(brandVo);
			if(cnt == 0){
				brandVo.setCaseString("Brand_InsertBrand");
				cnt = brandManagementService.UpdateData(brandVo);
				if (cnt > 0)
					result.setResultCode(0);
				else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 브랜드 코드입니다.");
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
	 * 1. 개요 : 브랜드 삭제
	 * 2. 처리내용 : 선택된 브랜드를 삭제한다.
	 * </pre>
	 * @Method Name : Delete
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/Delete")
	@ResponseBody
	public AjaxResultVO delete(BrandVo brandVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));
		try {
			brandVo.setCaseString("Brand_DeleteBrand");
			int cnt = brandManagementService.UpdateData(brandVo);
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
	 * 1. 개요 : 브랜드 활성화
	 * 2. 처리내용 : 선택된 브랜드를 활성화 시킨다.
	 * </pre>
	 * @Method Name : BrandActive
	 * @date : 2019. 1. 15.
	 * @author : p
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		p				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/BrandActive")
	@ResponseBody
	public AjaxResultVO brandActive(BrandVo brandVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));
		try {
			brandVo.setCaseString("Brand_ActiveBrand");
			int cnt = brandManagementService.UpdateData(brandVo);

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
	 * 1. 개요 : 할인율 목록 조회
	 * 2. 처리내용 : 할인율 목록을 조회한다.
	 * </pre>
	 * @Method Name : DiscountList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "BrandManagement/DiscountList")
	@ResponseBody
	public AjaxResultVO discountList(BrandVo brandVo,HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		try {
			brandVo.setCaseString("Brand_ListDiscount");
			List<BrandVo> list = (List<BrandVo>) brandManagementService.SelectListData(brandVo);
			result.setData(list);

			brandVo.setCaseString("Brand_ListCountDiscount");
			result.setiTotalDisplayRecords(brandManagementService.DataByCnt(brandVo));

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
	 * 1. 개요 : 할인율 저장
	 * 2. 처리내용 : 할인율을 등록/수정한다.
	 * </pre>
	 * @Method Name : DiscountCreate
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "BrandManagement/DiscountCreate")
	@ResponseBody
	public AjaxResultVO discountCreate(BrandVo brandVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		int cnt = 0;
		try {
			brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			brandVo.setRegUser((String)session.getAttribute("id"));

			if(brandVo.getDiscountIdx() != null && brandVo.getDiscountIdx().intValue() > 0){
				brandVo.setCaseString("Brand_UpdateDiscount");
				cnt = brandManagementService.UpdateData(brandVo);
			}else{
				brandVo.setCaseString("Brand_InsertDiscount");
				cnt = brandManagementService.InsertData(brandVo);
			}

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
	 * 1. 개요 : 할인율 삭제
	 * 2. 처리내용 : 선택된 할인율을 삭제한다.
	 * </pre>
	 * @Method Name : DiscountDelete
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/DiscountDelete")
	@ResponseBody
	public AjaxResultVO discountDelete(BrandVo brandVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));
		try {
			brandVo.setCaseString("Brand_DeleteDiscount");
			int cnt = brandManagementService.DeleteDataByObjectParam(null, brandVo);

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
	 *
	 * <pre>
	 * 1. 개요 : 할인율 활성화
	 * 2. 처리내용 : 선택된 할인율을 활성화한다.
	 * </pre>
	 * @Method Name : DiscountActive
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param brandVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "BrandManagement/DiscountActive")
	@ResponseBody
	public AjaxResultVO discountActive(BrandVo brandVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		brandVo.setRegUser((String)session.getAttribute("id"));

		try {
			brandVo.setCaseString("Brand_ActiveDiscount");
			int cnt = brandManagementService.UpdateData(brandVo);
			if (cnt > 0)
				result.setResultCode(0);
			else
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}




}