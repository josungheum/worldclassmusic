package qss.controller;

import java.util.List;

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
 * <pre>
 * qss.controller 
 *    |_ OrderProdController.java
 * 
 * </pre>
 * @date : 2018. 12. 25. 오전 12:20:44
 * @version : 
 * @author : admin
 */
@Controller
public class OrderStockController {
	private static final Log logger = LogFactory.getLog(OrderStockController.class);
	
	@Resource(name="orderProdService")
	Qss orderProdService = new OrderProdImpl();
	
	@RequestMapping(value="OrderStock/Main")
    public String Main(@ModelAttribute("OrderProdVo")OrderProdVo orderProdVo, HttpSession session, ModelMap model) throws Exception 
	{	
		if ((String) session.getAttribute("id") != null) {
			return "/OrderStock/Main";
		} 
		else {
			return "/Logout";
		}
    }
	
	
	/**
	 * <pre>
	 * 1. 개요 : 재고 조회
	 * 2. 처리내용 : 재고 조회
	 * </pre>
	 * @Method Name : OrderStockList
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
	@RequestMapping("OrderStock/OrderStockList")
	@ResponseBody
	public AjaxResultVO OrderStockList(OrderProdVo orderProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		try {
			orderProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			orderProdVo.setCaseString("selectProd_List");
			List<?> list = orderProdService.SelectListData(orderProdVo);
			result.setData(list);
			
			orderProdVo.setCaseString("selectProd_Count");
			result.setiTotalDisplayRecords(orderProdService.DataByCnt(orderProdVo));
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	
}