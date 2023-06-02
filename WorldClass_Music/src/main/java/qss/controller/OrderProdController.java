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

import qss.impl.OrderProdImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.OrderProdVo;

/**
 * 상품 관리
 * <pre>
 * qss.controller 
 *    |_ OrderProdController.java
 * 
 * </pre>
 * @date : 2019. 1. 15. 오후 5:25:18
 * @version : 
 * @author : 김한기
 */
@Controller
public class OrderProdController {
	private static final Log logger = LogFactory.getLog(OrderProdController.class);
	
	@Resource(name="orderProdService")
	Qss orderProdService = new OrderProdImpl();
	
	
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 관리 화면
	 * 2. 처리내용 : 상품 관리 화면
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 1. 14.
	 * @author : khk
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 14.		khk				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="OrderProd/Main")
    public String Main(@ModelAttribute("OrderProdVo")OrderProdVo orderProdVo, HttpSession session, ModelMap model) 
	{	
		if ((String) session.getAttribute("id") != null) 
			return "/OrderProd/Main";
		else 
			return "/Logout";
    }
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 조회
	 * 2. 처리내용 : 상품 조회
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
	 * @param orderProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OrderProd/OrderProdList")
	@ResponseBody
	public AjaxResultVO OrderProdList(OrderProdVo orderProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		
		// 관리자 정보 set
		orderProdVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		orderProdVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		orderProdVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		orderProdVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			orderProdVo.setCaseString("OrderProd_ListOrderProd");
			List<?> list = orderProdService.SelectListData(orderProdVo);
			result.setData(list);
			
			orderProdVo.setCaseString("OrderProd_ListCountOrderProd");
			result.setiTotalDisplayRecords(orderProdService.DataByCnt(orderProdVo));
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 상세 조회
	 * 2. 처리내용 : 상품 상세 조회
	 * </pre>
	 * @Method Name : Form
	 * @date : 2019. 1. 7.
	 * @author : p
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		p				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderProd/Form")
	public String Form(@ModelAttribute("OrderProdVo") OrderProdVo orderProdVo, ModelMap model, HttpSession session) 
	{
		try {
				orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				if(orderProdVo.getOrderProdIdx() != null && orderProdVo.getOrderProdIdx().intValue() > 0) {
					orderProdVo.setCaseString("OrderProd_ViewOrderProd");
					orderProdVo = (OrderProdVo) orderProdService.SelectData(orderProdVo);
				}
				
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		model.addAttribute("orderProdVo", orderProdVo);
		
		return "/OrderProd/Form";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 등록 및 수정
	 * 2. 처리내용 : 상품 등록 및 수정
	 * </pre>
	 * @Method Name : Insert
	 * @date : 2019. 1. 7.
	 * @author : p
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		p				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderProd/Insert")
	@ResponseBody
	public AjaxResultVO Insert(OrderProdVo orderProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try 
		{
			orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderProdVo.setRegUser((String)session.getAttribute("id"));
			
			orderProdVo.setCaseString("OrderProd_OverlapCountOrderProd");
			if(orderProdService.DataByCnt(orderProdVo) == 0){
				orderProdVo.setCaseString("OrderProd_OverlapCountProdBarcode");
				if(orderProdService.DataByCnt(orderProdVo) == 0) {
					if(orderProdVo.getOrderProdIdx() != null && orderProdVo.getOrderProdIdx().intValue() > 0) 
						orderProdVo.setCaseString("OrderProd_UpdateOrderProd");
					else 
						orderProdVo.setCaseString("OrderProd_InsertOrderProd");
					
					int cnt = orderProdService.UpdateData(orderProdVo);
					if (cnt > 0) 
						result.setResultCode(0);
					else {
						result.setResultCode(1);
						messages.put("title", "처리 되지 않았습니다.");
					}
					result.setMessages(messages);
				}
				else 
				{
					result.setResultCode(1);
					messages.put("title", "중복된 바코드가 존재합니다.");
				}
				
			} else 
			{
				result.setResultCode(1);
				messages.put("title", "중복된 상품코드입니다.");
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
	 * 1. 개요 : 상품 삭제
	 * 2. 처리내용 : 상품 삭제
	 * </pre>
	 * @Method Name : OrderProdDelete
	 * @date : 2019. 1. 8.
	 * @author : p
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 8.		p				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OrderProd/OrderProdDelete")
	@ResponseBody
	public AjaxResultVO OrderProdDelete(OrderProdVo orderProdVo, HttpSession session) 
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		orderProdVo.setRegUser((String)session.getAttribute("id"));
		try {
			orderProdVo.setCaseString("OrderProd_DeleteOrderProd");
			
			int cnt = orderProdService.UpdateData(orderProdVo);
		
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
	 * 1. 개요 : 상품 활성화
	 * 2. 처리내용 : 상품 활성화 
	 * </pre>
	 * @Method Name : OrderProdActive
	 * @date : 2019. 1. 20.
	 * @author : admin
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 20.		admin				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "OrderProd/OrderProdActive")
	@ResponseBody
	public AjaxResultVO OrderProdActive(OrderProdVo orderProdVo, HttpSession session){
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		orderProdVo.setRegUser((String)session.getAttribute("id"));
		try {
			orderProdVo.setCaseString("OrderProd_ActiveOrderProd");
			
			int cnt = orderProdService.UpdateData(orderProdVo);

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
	
	@RequestMapping(value = "OrderProd/OptionProdForm")
	public String OptionProdForm(@ModelAttribute("OrderProdVo") OrderProdVo orderProdVo, ModelMap model) {
		return "/OrderProd/OptionProdForm";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 모바일 상품 관리 화면
	 * 2. 처리내용 : 상품 관리 화면
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 1. 14.
	 * @author : khk
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 14.		khk				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="Mobile/OrderProd/Main")
    public String mobileMain(@ModelAttribute("OrderProdVo")OrderProdVo orderProdVo, HttpSession session, ModelMap model) 
	{	
		if ((String) session.getAttribute("id") != null) {
//			model.addAttribute("orderProdVo", orderProdVo);
			return "/Mobile/OrderProd/Main";
		}
			
		else 
			return "/Logout";
    }
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 상세 조회
	 * 2. 처리내용 : 상품 상세 조회
	 * </pre>
	 * @Method Name : Form
	 * @date : 2019. 1. 7.
	 * @author : p
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		p				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param orderProdVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Mobile/OrderProd/Form")
	public String mobileForm(@ModelAttribute("OrderProdVo") OrderProdVo orderProdVo, ModelMap model, HttpSession session) 
	{
		try {
				orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				if(orderProdVo.getOrderProdIdx() != null && orderProdVo.getOrderProdIdx().intValue() > 0) {
					orderProdVo.setCaseString("OrderProd_ViewOrderProd");
					orderProdVo = (OrderProdVo) orderProdService.SelectData(orderProdVo);
				}
				
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		model.addAttribute("orderProdVo", orderProdVo);
		
		return "/Mobile/OrderProd/Form";
	}
	
	@RequestMapping(value = "Mobile/OrderProd/OptionProdForm")
	public String mobileOptionProdForm(@ModelAttribute("OrderProdVo") OrderProdVo orderProdVo, ModelMap model) {
		return "/Mobile/OrderProd/OptionProdForm";
	}
	
}