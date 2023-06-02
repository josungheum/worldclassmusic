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
import qss.impl.CommonImpl;
import qss.impl.OrderProdImpl;
import qss.impl.OrderScreenImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.OrderProdVo;
import qss.vo.OrderScreenVo;

/**
 * <pre>
 * qss.controller
 *    |_ OrderScreenController.java
 *
 * </pre>
 * @date : 2019. 1. 20. 오후 10:14:22
 * @version :
 * @author : admin
 */
@Controller
public class OrderScreenController {
	private static final Log logger = LogFactory.getLog(OrderScreenController.class);

	@Resource(name="orderScreenService")
	Qss orderScreenService = new OrderScreenImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	@Resource(name="orderProdService")
	Qss orderProdService = new OrderProdImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	/**
	 * <pre>
	 * 1. 개요 : 주문 화면 조회
	 * 2. 처리내용 : 주문 화면 조회
	 * </pre>
	 * @Method Name : OrderScreen
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="OrderScreen/Main")
    public String OrderScreen(@ModelAttribute("OrderScreenVo")OrderScreenVo orderScreenVo, HttpSession session, ModelMap model)
	{
		if ((String) session.getAttribute("id") != null) {
			return "/OrderScreen/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }

	/**
	 * <pre>
	 * 1. 개요 : 주문화면 목록 조회
	 * 2. 처리내용 : 주문화면 목록 조회
	 * </pre>
	 * @Method Name : OrderProdList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OrderScreen/OrderScreenList")
	@ResponseBody
	public AjaxResultVO OrderProdList(OrderScreenVo orderScreenVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		orderScreenVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		orderScreenVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		orderScreenVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		orderScreenVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {

			orderScreenVo.setCaseString("OrderScreen_ListOrderScreen");
			List<?> list = orderScreenService.SelectListData(orderScreenVo);
			result.setData(list);

			orderScreenVo.setCaseString("OrderScreen_ListCountOrderScreen");
			result.setiTotalDisplayRecords(orderScreenService.DataByCnt(orderScreenVo));


		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}


		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 주문화면 저장/수정
	 * 2. 처리내용 : 주문화면 저장/수정
	 * </pre>
	 * @Method Name : Create
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "OrderScreen/Create")
	@ResponseBody
	public AjaxResultVO Create(OrderScreenVo orderScreenVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		orderScreenVo.setRegUser((String)session.getAttribute("id"));
		try {
			int cnt = 0;

			if(orderScreenVo.getOrderScreenIdx() != null && orderScreenVo.getOrderScreenIdx().intValue() > 0)
				orderScreenVo.setCaseString("OrderScreen_UpdateOrderScreen");
			else
				orderScreenVo.setCaseString("OrderScreen_InsertOrderScreen");

			cnt = orderScreenService.UpdateData(orderScreenVo);

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
	 * 1. 개요 : 주문화면 삭제
	 * 2. 처리내용 : 주문화면 삭제
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
	 * @param orderScreenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderScreen/OrderScreenDelete")
	@ResponseBody
	public AjaxResultVO Delete(OrderScreenVo orderScreenVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		orderScreenVo.setRegUser((String)session.getAttribute("id"));
		try {
			orderScreenVo.setCaseString("OrderScreen_DeleteOrderScreen");
			int cnt = orderScreenService.UpdateData(orderScreenVo);

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
	 * 1. 개요 : 주문화면 상세
	 * 2. 처리내용 : 주문화면 상세
	 * </pre>
	 * @Method Name : Form
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param KioskVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderScreen/Form")
	public String Form(@ModelAttribute("OrderScreenVo") OrderScreenVo orderScreenVo, @ModelAttribute("KioskVo") qss.vo.KioskVo KioskVo, ModelMap model, HttpSession session)
	{
		if(orderScreenVo.getOrderScreenIdx() != null && orderScreenVo.getOrderScreenIdx().intValue() > 0){
			orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderScreenVo.setCaseString("OrderScreen_ViewOrderScreen");
			try {
				orderScreenVo = (OrderScreenVo) orderScreenService.SelectData(orderScreenVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		model.addAttribute("prodStateCode", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectCodeData("STA0000"))));
		model.addAttribute("orderScreenVo", orderScreenVo);

		return "/OrderScreen/Form";
	}

	/**
	 * <pre>
	 * 1. 개요 : 주문화면 단말 트리
	 * 2. 처리내용 : 주문화면 단말 트리
	 * </pre>
	 * @Method Name : SearchKioskTree
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OrderScreen/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(OrderScreenVo orderScreenVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderScreenVo.setCaseString("OrderScreen_TreeOrderScreenTarget");


			List<?> list = orderScreenService.SelectListData(orderScreenVo);
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
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}


		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 메뉴 트리 저장
	 * 2. 처리내용 : 메뉴 트리 저장
	 * </pre>
	 * @Method Name : ProductMenuTree
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OrderScreen/ProductMenuTree")
	@ResponseBody
	public AjaxResultVO ProductMenuTree(OrderScreenVo orderScreenVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderScreenVo.setCaseString("OrderScreen_ListOrderScreenMenu");

			List<?> list = orderScreenService.SelectListData(orderScreenVo);

			result.setData(list);
			result.setResultCode(0);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 상품 조회
	 * 2. 처리내용 : 상품 조회
	 * </pre>
	 * @Method Name : ProductList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "OrderScreen/ProductList")
	@ResponseBody
	public AjaxResultVO ProductList(OrderScreenVo orderScreenVo,HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		orderScreenVo.setCaseString("OrderScreen_ListOrderScreenMenuItem");
		List<OrderScreenVo> list;
		try {
			list = (List<OrderScreenVo>) orderScreenService.SelectListData(orderScreenVo);
			result.setResultCode(0);
			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return result;
	}


	/**
	 * <pre>
	 * 1. 개요 : 상품 상세
	 * 2. 처리내용 : 상품 상세
	 * </pre>
	 * @Method Name : ProductForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param KioskVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderScreen/ProductForm")
	public String ProductForm(@ModelAttribute("OrderScreenVo") OrderScreenVo orderScreenVo, @ModelAttribute("KioskVo") qss.vo.KioskVo KioskVo, ModelMap model, HttpSession session)
	{
		model.addAttribute(orderScreenVo);
		model.addAttribute("prodStateCode", JSONArray.fromObject(systemCodeService.SelectCodeData("STA0000")));

		return "/OrderScreen/ProductForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 상품 목록
	 * 2. 처리내용 : 상품 목록
	 * </pre>
	 * @Method Name : ProdList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderScreen/ProdList/{brandIdx}/{francIdx}")
	@ResponseBody
	public AjaxResultVO ProdList(OrderProdVo orderProdVo,HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();

		orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		orderProdVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		orderProdVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		orderProdVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		orderProdVo.setSessionAdminType((String)session.getAttribute("adminType"));
		orderProdVo.setActiveYn("Y");

		orderProdVo.setCaseString("OrderProd_ListOrderProd");
		List<?> list;
		try {
			list = orderProdService.SelectListData(orderProdVo);
			orderProdVo.setCaseString("OrderProd_ListCountOrderProd");
			result.setiTotalDisplayRecords(orderProdService.DataByCnt(orderProdVo));

			result.setResultCode(0);
			result.setData(list);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 모바일 주문 화면 조회
	 * 2. 처리내용 : 주문 화면 조회
	 * </pre>
	 * @Method Name : OrderScreen
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="Mobile/OrderScreen/Main")
    public String mobileOrderScreen(@ModelAttribute("OrderScreenVo")OrderScreenVo orderScreenVo, HttpSession session, ModelMap model)
	{
		if ((String) session.getAttribute("id") != null) {
			return "/Mobile/OrderScreen/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }



	/**
	 * <pre>
	 * 1. 개요 : 주문화면 상세
	 * 2. 처리내용 : 주문화면 상세
	 * </pre>
	 * @Method Name : Form
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param KioskVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Mobile/OrderScreen/Form")
	public String MobileForm(@ModelAttribute("OrderScreenVo") OrderScreenVo orderScreenVo, @ModelAttribute("KioskVo") qss.vo.KioskVo KioskVo, ModelMap model, HttpSession session)
	{
		if(orderScreenVo.getOrderScreenIdx() != null && orderScreenVo.getOrderScreenIdx().intValue() > 0){
			orderScreenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderScreenVo.setCaseString("OrderScreen_ViewOrderScreen");
			try {
				orderScreenVo = (OrderScreenVo) orderScreenService.SelectData(orderScreenVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		model.addAttribute("prodStateCode", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectCodeData("STA0000"))));
		model.addAttribute("orderScreenVo", orderScreenVo);

		return "/Mobile/OrderScreen/Form";
	}


	/**
	 * <pre>
	 * 1. 개요 : 상품 상세
	 * 2. 처리내용 : 상품 상세
	 * </pre>
	 * @Method Name : ProductForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param orderScreenVo
	 * @param KioskVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Mobile/OrderScreen/ProductForm")
	public String MobileProductForm(@ModelAttribute("OrderScreenVo") OrderScreenVo orderScreenVo, @ModelAttribute("KioskVo") qss.vo.KioskVo KioskVo, ModelMap model, HttpSession session)
	{
		model.addAttribute(orderScreenVo);
		model.addAttribute("prodStateCode", JSONArray.fromObject(systemCodeService.SelectCodeData("STA0000")));

		return "/Mobile/OrderScreen/ProductForm";
	}

}