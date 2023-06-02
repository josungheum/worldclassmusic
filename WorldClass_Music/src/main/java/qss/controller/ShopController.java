package qss.controller;

import java.math.BigInteger;
import java.util.ArrayList;
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

import qss.impl.ShopImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.ShopVo;

@Controller
@RequestMapping(value="/Shop")
public class ShopController {
	private static final Log logger = LogFactory.getLog(ShopController.class);
	
	@Resource(name="shopService")
	Qss shopService = new ShopImpl();

	@RequestMapping(value="/Main")
    public String Main(@ModelAttribute("ShopVo")ShopVo shopVo, HttpSession session, ModelMap model) throws Exception
	{	
		if ((String) session.getAttribute("id") != null) 
			return "/Shop/Main";
		else 
			return "/Logout";
    }
	
	@RequestMapping("/List")
	@ResponseBody
	public AjaxResultVO List(ShopVo shopVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		// 관리자 정보 set
		shopVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		shopVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		shopVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			shopVo.setCaseString("Shop_List");
			List<?> list = shopService.SelectListData(shopVo);
			result.setData(list);
			
			shopVo.setCaseString("Shop_ListCnt");
			result.setiTotalDisplayRecords(shopService.DataByCnt(shopVo));
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}
	
	@RequestMapping("/DetailList")
	@ResponseBody
	public AjaxResultVO DetailList(ShopVo shopVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			shopVo.setCaseString("Shop_ImageListList");
			List<?> list = shopService.SelectListData(shopVo);
			result.setData(list);
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}
	
	@RequestMapping("/FloorList")
	@ResponseBody
	public AjaxResultVO FloorList(ShopVo shopVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			shopVo.setCaseString("Shop_FloorNmList");
			List<?> list = shopService.SelectListData(shopVo);
			result.setData(list);
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}

	@RequestMapping(value = "/Form")
	public String Form(@ModelAttribute("ShopVo") ShopVo shopVo, ModelMap model, HttpSession session) throws Exception
	{
		
		if (shopVo.getShopIdx() != null && !shopVo.getShopIdx().equals(BigInteger.ZERO)) {
			shopVo.setCaseString("Shop_Get");
			shopVo = (ShopVo)shopService.SelectData(shopVo);
		}
		model.addAttribute("shopVo", shopVo);
		
		return "/Shop/Form";
	}
	

	@RequestMapping(value = "/Create")
	@ResponseBody
	public AjaxResultVO Create(ShopVo shopVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			shopVo.setRegUser((String)session.getAttribute("id"));
			shopVo.setModUser((String)session.getAttribute("id"));
			
			if (shopVo.getDetailMenuXML() != null && !shopVo.getDetailMenuXML().equals("")) {
				ArrayList<String> fileIdx = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "orderSeq");
				ArrayList<String> title = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "title");
				ArrayList<String> titleEn = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "titleEn");
				ArrayList<String> desc = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "desc");
				ArrayList<String> descEn = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "descEn");
				
				List<ShopVo> list = new ArrayList<ShopVo>();
				
				for (int i = 0; i < fileIdx.size(); i++) {
					ShopVo vo = new ShopVo();
					vo.setFileContentIdx(Integer.valueOf(fileIdx.get(i)));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					vo.setDetailMenuTitle(title.get(i));
					vo.setDetailMenuTitleEn(titleEn.get(i));
					vo.setDetailMenuDesc(desc.get(i));
					vo.setDetailMenuDescEn(descEn.get(i));
					vo.setDomainIdx(shopVo.getDomainIdx());
					vo.setBrandIdx(shopVo.getBrandIdx());
					list.add(i, vo);
				}
				shopVo.setDetailMenuList(list);
			}
			
			shopVo.setCaseString("Shop_Create");
			int idx = shopService.InsertData(shopVo);
			if (idx > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "등록도중 오류가 발생하였습니다.");
				messages.put("detail", "");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/Update")
	@ResponseBody
	public AjaxResultVO Update(ShopVo shopVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			shopVo.setModUser((String)session.getAttribute("id"));
			
			if (shopVo.getDetailMenuXML() != null && !shopVo.getDetailMenuXML().equals("")) {
				ArrayList<String> fileIdx = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "orderSeq");
				ArrayList<String> title = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "title");
				ArrayList<String> titleEn = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "titleEn");
				ArrayList<String> desc = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "desc");
				ArrayList<String> descEn = WebUtil.ParsingXML(shopVo.getDetailMenuXML(), "Detail", "descEn");
				
				List<ShopVo> list = new ArrayList<ShopVo>();
				
				for (int i = 0; i < fileIdx.size(); i++) {
					ShopVo vo = new ShopVo();
					vo.setFileContentIdx(Integer.valueOf(fileIdx.get(i)));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					vo.setDetailMenuTitle(title.get(i));
					vo.setDetailMenuTitleEn(titleEn.get(i));
					vo.setDetailMenuDescEn(descEn.get(i));
					vo.setDetailMenuDesc(desc.get(i));
					vo.setDomainIdx(shopVo.getDomainIdx());
					vo.setBrandIdx(shopVo.getBrandIdx());
					list.add(i, vo);
				}
				shopVo.setDetailMenuList(list);
			}
			
			shopVo.setCaseString("Shop_Update");
			int idx = shopService.UpdateData(shopVo);
			if (idx > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "수정도중 오류가 발생하였습니다.");
				messages.put("detail", "");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/Delete")
	@ResponseBody
	public AjaxResultVO Delete(ShopVo shopVo, HttpSession session) 
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		shopVo.setModUser((String)session.getAttribute("id"));
		try {
			shopVo.setCaseString("Shop_Delete");
			int cnt = shopService.UpdateData(shopVo);
		
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
	
	@RequestMapping(value = "/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(ShopVo shopVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			shopVo.setCaseString("Shop_ActiveYn");
			shopVo.setModUser((String)session.getAttribute("id"));
			int cnt = shopService.UpdateData(shopVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else{
				result.setResultCode(1);
				messages.put("title", "로직 오류가 발생하였습니다.");
				result.setMessages(messages);
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