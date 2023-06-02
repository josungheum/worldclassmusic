package qss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.CommonImpl;
import qss.impl.ExampleImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.CommonVo;

@Controller
public class ExampleController {
	@Resource(name="exampleService")
	Qss exampleService = new ExampleImpl();
	
	@Resource(name="commonService")
	Qss commonService = new CommonImpl();
	
	@RequestMapping(value="Example/Main")
    public String Main(@ModelAttribute("CommonVo")CommonVo commonVo, HttpSession session, ModelMap model) throws Exception 
	{	
		if ((String)session.getAttribute("id") != null) {	
			return "/Example/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }
	
	@RequestMapping("Example/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(CommonVo commonVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			commonVo.setCaseString("Example_SearchKioskTree");
			commonVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			commonVo.setBrandIdx((String)session.getAttribute("brandIdx"));
			commonVo.setFrancIdx((String)session.getAttribute("francIdx"));
			
			List<?> list = commonService.SelectListData(commonVo);
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
	
}