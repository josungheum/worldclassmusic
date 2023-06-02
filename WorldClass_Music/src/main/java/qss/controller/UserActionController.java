package qss.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.UserActionLogImpl;
import qss.service.Qss;
import qss.vo.CommonVo;

/**
 * 설정 내역 > 이용 내역 관리
 * <pre>
 * qss.controller
 *    |_ UserActionController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:23:05
 * @version :
 * @author : admin
 */
@Controller
public class UserActionController {

	@Resource(name = "userActionLogService")
	Qss userActionLogService = new UserActionLogImpl();
	private static final Log logger = LogFactory.getLog(UserActionController.class);

	@RequestMapping(value = "UserActionLog/Main")
	public String Main(@ModelAttribute("CommonVo") CommonVo commonVo, HttpSession session, ModelMap model) {
		if ((String) session.getAttribute("id") != null) {
			model.put("MENU_KOR_NAME", "이용내역 목록");
			model.put("MENU_ENG_NAME", "user action log");
			model.put("SUB_MENU_NAME", "이용내역 목록");
			return "/UserActionLog/Main";
		} else {
			return "redirect:/Logout";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("UserActionLog/PagingList")
	@ResponseBody
	public JSONObject PagingList(CommonVo commonVo, HttpSession session) {
		JSONObject json = new JSONObject();

		try {
			commonVo.setCaseString("UserActionLog_TotalCnt");
//			int totalCnt = userActionLogService.SelectData(commonVo).getCnt1();

			commonVo.setCaseString("UserActionLog_FilterCnt");
//			int filterCnt = userActionLogService.SelectData(commonVo).getCnt1();

			commonVo.setCaseString("UserActionLog_PagingList");
			List<CommonVo> list = (List<CommonVo>) userActionLogService.SelectListData(commonVo);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("data", list);
//			map.put("recordsTotal", totalCnt);
//			map.put("recordsFiltered", filterCnt);
//			map.put("draw", commonVo.getDRAWNUM());

			Iterator<String> iterator = map.keySet().iterator();

			while (iterator.hasNext()) {
				String key = (String)iterator.next();
				json.put(key, map.get(key));
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return json;
	}

	
	@RequestMapping(value = "UserActionLog/Form/{LOG_IDX}")
	public String UserForm(@ModelAttribute("CommonVo") CommonVo commonVo, ModelMap model, HttpSession session) throws Exception
	{
		CommonVo cmsq = new CommonVo();
		commonVo.setCaseString("UserActionLog_Detail");
		cmsq = userActionLogService.SelectData(commonVo);
		
//		commonVo.setREQDATA(cmsq.getREQDATA());

		model.addAttribute(commonVo);

		return "/UserActionLog/Form";
	}


}
