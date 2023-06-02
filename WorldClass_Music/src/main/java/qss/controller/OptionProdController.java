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

import qss.impl.OptionProdImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.OptionProdVo;

/**
 * 상품 관리
 * <pre>
 * qss.controller 
 *    |_ OptionProdController.java
 * 
 * </pre>
 * @date : 2019. 1. 15. 오후 5:25:18
 * @version : 
 * @author : 김한기
 */
@Controller
public class OptionProdController {
	private static final Log logger = LogFactory.getLog(OptionProdController.class);
	
	@Resource(name="optionProdService")
	Qss optionProdService = new OptionProdImpl();
	
	
	
	/**
	 * <pre>
	 * 1. 개요 : 옵션 상품 관리 화면
	 * 2. 처리내용 : 옵션 상품 관리 화면
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
	 * @param optionProdVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="OptionProd/Main")
    public String Main(@ModelAttribute("OptionProdVo")OptionProdVo optionProdVo, HttpSession session, ModelMap model) 
	{	
		if ((String) session.getAttribute("id") != null) 
			return "/OptionProd/Main";
		else 
			return "/Logout";
    }
	
	/**
	 * <pre>
	 * 1. 개요 : 상품 조회
	 * 2. 처리내용 : 상품 조회
	 * </pre>
	 * @Method Name : OptionProdList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param OptionProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OptionProd/OptionProdList")
	@ResponseBody
	public AjaxResultVO OptionProdList(OptionProdVo optionProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		
		// 관리자 정보 set
		optionProdVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		optionProdVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		optionProdVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		optionProdVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			optionProdVo.setCaseString("OptionProd_ListOptionProd");
			List<?> list = optionProdService.SelectListData(optionProdVo);
			result.setData(list);
			
			optionProdVo.setCaseString("OptionProd_ListCountOptionProd");
			result.setiTotalDisplayRecords(optionProdService.DataByCnt(optionProdVo));
			
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
	 * @param OptionProdVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OptionProd/Form")
	public String Form(@ModelAttribute("OptionProdVo") OptionProdVo optionProdVo, ModelMap model, HttpSession session) 
	{
		try {
				optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				if(optionProdVo.getOptionProdIdx() != null && optionProdVo.getOptionProdIdx().intValue() > 0) {
					optionProdVo.setCaseString("OptionProd_ViewOptionProd");
					optionProdVo = (OptionProdVo) optionProdService.SelectData(optionProdVo);
				}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		model.addAttribute("optionProdVo", optionProdVo);
		
		return "/OptionProd/Form";
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
	 * @param optionProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OptionProd/Insert")
	@ResponseBody
	public AjaxResultVO Insert(OptionProdVo optionProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try 
		{
			optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			optionProdVo.setRegUser((String)session.getAttribute("id"));
			
			optionProdVo.setCaseString("OptionProd_OverlapCountOptionProd");
			if(optionProdService.DataByCnt(optionProdVo) == 0){
				if(optionProdVo.getOptionProdIdx() != null && optionProdVo.getOptionProdIdx().intValue() > 0) 
					optionProdVo.setCaseString("OptionProd_UpdateOptionProd");
				else 
					optionProdVo.setCaseString("OptionProd_InsertOptionProd");
				
				int cnt = optionProdService.UpdateData(optionProdVo);
				if (cnt > 0) 
					result.setResultCode(0);
				else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else {
				result.setResultCode(1);
				messages.put("title", "중복된 옵션 상품코드입니다.");
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
	 * @Method Name : OptionProdDelete
	 * @date : 2019. 1. 8.
	 * @author : p
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 8.		p				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param optionProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "OptionProd/OptionProdDelete")
	@ResponseBody
	public AjaxResultVO OptionProdDelete(OptionProdVo optionProdVo, HttpSession session) 
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		optionProdVo.setRegUser((String)session.getAttribute("id"));
		try {
			optionProdVo.setCaseString("OptionProd_DeleteOptionProd");
			
			int cnt = optionProdService.UpdateData(optionProdVo);
		
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
	 * @Method Name : OptionProdActive
	 * @date : 2019. 1. 20.
	 * @author : admin
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 20.		admin				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param optionProdVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "OptionProd/OptionProdActive")
	@ResponseBody
	public AjaxResultVO OptionProdActive(OptionProdVo optionProdVo, HttpSession session){
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		optionProdVo.setRegUser((String)session.getAttribute("id"));
		try {
			optionProdVo.setCaseString("OptionProd_ActiveOptionProd");
			
			int cnt = optionProdService.UpdateData(optionProdVo);

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
	 * 1. 개요 : 상품 조회
	 * 2. 처리내용 : 상품 조회
	 * </pre>
	 * @Method Name : OptionProdList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param OptionProdVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("OptionProd/OptionProdFormList")
	@ResponseBody
	public AjaxResultVO OptionProdFormList(OptionProdVo optionProdVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		optionProdVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		
		// 관리자 정보 set
		optionProdVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		optionProdVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		optionProdVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		optionProdVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			optionProdVo.setCaseString("OptionProd_FormListOptionProd");
			List<?> list = optionProdService.SelectListData(optionProdVo);
			result.setData(list);
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}
	
	
	
}