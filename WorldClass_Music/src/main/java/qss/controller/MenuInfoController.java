/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.MenuInfoImpl;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.MenuInfoVo;

/**
 * <pre>
 * qss.controller
 *    |_ MenuInfoController.java
 *
 * </pre>
 * @date : 2019. 6. 11. 오후 3:07:47
 * @version :
 * @author : 이재환
 */
@Controller
public class MenuInfoController {

	private static final Log logger = LogFactory.getLog(MenuInfoController.class);

	@Resource(name="menuInfoService")
	MenuInfoImpl menuInfoService = new MenuInfoImpl();

	/**
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 관리 화면
	 * 2. 처리내용 : 메뉴 관리 화면 표시
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 6. 12.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 12.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="MenuInfo/Main")
	public String Main()
	{
		return "/MenuInfo/Main";
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 관리 화면 조회
	 * 2. 처리내용 : 메뉴 관리 목록 조회
	 * </pre>
	 * @Method Name : MainInfoList
	 * @date : 2019. 6. 12.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 12.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="MenuInfo/MenuInfoList")
	@ResponseBody
	public AjaxResultVO MenuInfoList(MenuInfoVo menuInfoVo)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			menuInfoVo.setCaseString("Menu_MenuList");
			List<MenuInfoVo> list = (List<MenuInfoVo>)menuInfoService.SelectListData2(menuInfoVo);
			result.setData(list);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 활성화/비활성화
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : MenuInfoActive
	 * @date : 2019. 6. 17.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="MenuInfo/MenuInfoActive")
	@ResponseBody
	public AjaxResultVO MenuInfoActive(MenuInfoVo menuInfoVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			menuInfoVo.setCaseString("Menu_MenuUpdate");
			menuInfoVo.setRegUser((String)session.getAttribute("id"));
			int cnt = menuInfoService.UpdateData(menuInfoVo);

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
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 삭제
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : MenuInfoDelete
	 * @date : 2019. 6. 17.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="MenuInfo/MenuInfoDelete")
	@ResponseBody
	public AjaxResultVO MenuInfoDelete(MenuInfoVo menuInfoVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			menuInfoVo.setCaseString("Menu_MenuUpdate");
			menuInfoVo.setRegUser((String)session.getAttribute("id"));
			menuInfoVo.setDelYn("Y");

			int cnt				= 0;
			String idx 		  	= menuInfoVo.getIdx();
			String[] menuIdxs	= StringUtils.split(idx, "[,]");
			for (String menuIdx : menuIdxs) {
				if(StringUtils.isNumeric(menuIdx)) {
					menuInfoVo.setMenuIdx(new BigInteger(menuIdx));
					cnt += menuInfoService.UpdateData(menuInfoVo);
				}
			}

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
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 등록/수정 폼
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : FormNullTemp
	 * @date : 2019. 6. 17.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MenuInfo/FormNullTemp")
	public String FormNullTemp(@ModelAttribute("MenuInfoVo") MenuInfoVo menuInfoVo, ModelMap model, HttpSession session)
	{
		try {
			// default menu depth
			menuInfoVo.setDepth("0");

			List<MenuInfoVo> list 	= null;
			String parentMenuNm 	= null;
			String depth			= null;
			// 부모MenuId가 있으면 정보 조회
			if(!StringUtils.isEmpty(menuInfoVo.getParentMenuId())) {
				MenuInfoVo pMenuInfoVo = new MenuInfoVo();
				pMenuInfoVo.setMenuId(menuInfoVo.getParentMenuId());
				pMenuInfoVo.setCaseString("Menu_MenuList");
				List<MenuInfoVo> list2 = (List<MenuInfoVo>)menuInfoService.SelectListData2(pMenuInfoVo);
				if(list2 != null && list2.size() > 0) {
					pMenuInfoVo = list2.get(0);
					parentMenuNm 	= pMenuInfoVo.getMenuKorName();
					depth			= new Integer(pMenuInfoVo.getDepth())+1+"";
				}
			}
			if(menuInfoVo.getMenuIdx() != null) {
				// 수정
				menuInfoVo.setCaseString("Menu_MenuList");
				list = (List<MenuInfoVo>)menuInfoService.SelectListData2(menuInfoVo);
				if(list == null) {
					throw new Exception("수정 정보가 명확하지 않습니다.");
				}
				menuInfoVo = list.get(0);
				model.addAttribute("MenuInfoVo", menuInfoVo);
			}
			if(!StringUtils.isEmpty(parentMenuNm)) {
				menuInfoVo.setParentMenuNm(parentMenuNm);
			}
			if(!StringUtils.isEmpty(depth)) {
				menuInfoVo.setDepth(depth);
			}

			model.addAttribute("MenuInfoVo", menuInfoVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/MenuInfo/Form";
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 저장
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : Save
	 * @date : 2019. 6. 17.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="MenuInfo/Save")
	@ResponseBody
	public AjaxResultVO Save(MenuInfoVo menuInfoVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			if(menuInfoVo.getMenuIdx() != null && menuInfoVo.getMenuIdx().intValue() > 0) {
				// 수정
				menuInfoVo.setRegUser((String)session.getAttribute("id"));
				menuInfoVo.setCaseString("Menu_MenuUpdate");
				int cnt = menuInfoService.UpdateData(menuInfoVo);
				if(cnt > 0) {
					result.setResultCode(0);
				} else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
					result.setMessages(messages);
				}

			} else {
				// 등록
				// 부모 메뉴 ID가 있으면 부모 메뉴 ID의 순서 조회, 부모 메뉴 ID가 없으면 Max 순서 조회
				menuInfoVo.setCaseString("Menu_MenuGetOrderMax");
				int orderSeq = menuInfoService.DataByCnt(menuInfoVo);
				if(!StringUtils.isEmpty(menuInfoVo.getParentMenuId())) {
					// 자식 메뉴는 1단위로 정렬순서 늘림
					orderSeq++;
				} else {
					// 대메뉴는 100단위로 정렬순서 늘림
					orderSeq += 100;
				}
				menuInfoVo.setOrderSeq(orderSeq);

				// 조회된 orderSeq보다 큰 orderSeq는 모두 +1 해줌 ---->> 필요없음.
				//menuInfoVo.setCaseString("Menu_MenuUpdateOrderSeq");
				//menuInfoService.UpdateData(menuInfoVo);

				// 메뉴ID의 중복 체크
				menuInfoVo.setCaseString("Menu_MenuDupCheck");
				int dupCnt = menuInfoService.DataByCnt(menuInfoVo);
				if(dupCnt > 0) {
					result.setResultCode(1);
					messages.put("title", "중복되는 메뉴ID 입니다.");
					result.setMessages(messages);
				} else {
					menuInfoVo.setRegUser((String)session.getAttribute("id"));
					menuInfoVo.setCaseString("Menu_MenuCreate");
					if(StringUtils.isEmpty(menuInfoVo.getParentMenuId())) {
						menuInfoVo.setParentMenuId(null);
					}

					menuInfoService.UpdateData(menuInfoVo);

					result.setResultCode(0);
				}
			}
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 메뉴 위치 변경
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : TreeUpDown
	 * @date : 2019. 6. 17.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 17.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param menuInfoVo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="MenuInfo/TreeUpDown")
	@ResponseBody
	public AjaxResultVO TreeUpDown(MenuInfoVo menuInfoVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			if(menuInfoVo.getMenuIdx() == null) {
				result.setResultCode(1);
				messages.put("title", "위치를 변경할 메뉴정보가 없습니다.");
				result.setMessages(messages);
			} else if(StringUtils.isEmpty(menuInfoVo.getTreeUpDown())) {
				result.setResultCode(1);
				messages.put("title", "위치 변경 정보가 없습니다.");
				result.setMessages(messages);
			} else {
				menuInfoVo.setCaseString("Menu_MenuList");
				// 순서를 변경할 메뉴 정보 조회
				List<MenuInfoVo> list = (List<MenuInfoVo>)menuInfoService.SelectListData2(menuInfoVo);
				if(list == null) {
					result.setResultCode(1);
					messages.put("title", "위치를 변경할 메뉴정보가 없습니다.");
					result.setMessages(messages);
				} else {
					MenuInfoVo rVo = list.get(0);
					rVo.setTreeUpDown(menuInfoVo.getTreeUpDown());
					rVo.setCaseString("Menu_MenuUpSearch");
					// 위/아래로 한칸 있는 메뉴 정보 조회
					MenuInfoVo rVo2 = (MenuInfoVo)menuInfoService.SelectData(rVo);
					int pOrderSeq = rVo.getOrderSeq();
					int nOrderSeq = rVo2.getOrderSeq();
					BigInteger pMenuIdx = rVo.getMenuIdx();
					BigInteger nMenuIdx = rVo2.getMenuIdx();

					// 1.자식메뉴가 있으면 자식메뉴들도 같이 순서를 변경해야 한다. (변경점: SQL로 가져올 때 자식은 부모 밑에 나열되므로 자식까지 변경하지 않는 것으로 한다.)
					// 1.1 같은 뎁스의 바로 위/아래 결과를 조회하여 서로 orderSeq를 바꾼다.
					MenuInfoVo paramVo = new MenuInfoVo();
					paramVo.setMenuIdx(nMenuIdx);
					paramVo.setOrderSeq(pOrderSeq);
					paramVo.setRegUser((String)session.getAttribute("id"));
					paramVo.setCaseString("Menu_MenuUpdate");
					menuInfoService.UpdateData(paramVo);

					paramVo.setMenuIdx(pMenuIdx);
					paramVo.setOrderSeq(nOrderSeq);
					menuInfoService.UpdateData(paramVo);

					// 1.2.부모 메뉴ID가 없고, LEAF가 있으면 자식들을 조회하여, 자식들의 정렬순서의 백단위를 맞춰준다.
					if(StringUtils.isEmpty(rVo.getParentMenuId()) && rVo.getLeaf() > 0) {
						menuInfoVo.setPathCond(rVo.getPath());
						List<MenuInfoVo> subList = (List<MenuInfoVo>)menuInfoService.SelectListData2(menuInfoVo);
						for(MenuInfoVo vo : subList) {
							vo.setOrderSeq(nOrderSeq + vo.getOderSeq());
							vo.setRegUser((String)session.getAttribute("id"));
							vo.setCaseString("Menu_MenuUpdate");
							menuInfoService.UpdateData(paramVo);
						}
					}

					result.setResultCode(0);
				}
			}
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
}
