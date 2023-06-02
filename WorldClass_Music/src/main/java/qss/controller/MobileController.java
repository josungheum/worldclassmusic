/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import qss.impl.BrandManagementImpl;
import qss.impl.FranchiseeManagementImpl;
import qss.service.Qss;
import qss.vo.BrandVo;
import qss.vo.CommonVo;
import qss.vo.FranchiseeVo;


/**
 * <pre>
 * qss.controller
 *    |_ MobileController.java
 *
 * </pre>
 * @date : 2019. 6. 18. 오후 2:03:47
 * @version :
 * @author : 이재환
 */
@Controller
public class MobileController {

	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	@Resource(name="franchiseeManagementService")
	Qss franchiseeManagementService = new FranchiseeManagementImpl();

	private static final Log logger = LogFactory.getLog(MobileController.class);


	/**
	 *
	 * <pre>
	 * 1. 개요 : 모바일 메인페이지 접속
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : Main
	 * @date : 2019. 6. 19.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 19.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="Mobile/Resource/Main")
	public String Main(CommonVo commonVo, HttpServletRequest request,  HttpSession session, ModelMap model)
	{
		if ((String)session.getAttribute("id") != null) {

			String ua 			= request.getHeader("User-Agent").toUpperCase();
			boolean bMobile 	= StringUtils.contains(ua, "MOBILE");
			boolean bPhone		= StringUtils.contains(ua, "PHONE");
			boolean bPc			= false;
			if(!bMobile && !bPhone) {
				bPc				= true;
			}
			if(bPc) {
				String adminType	= (String)session.getAttribute("adminType");
				if(StringUtils.equals(adminType, "ADM0001")) {
					return "redirect:/Resource/Main";
				} else if(StringUtils.equals(adminType, "ADM0002")) {
					return "redirect:/BrandManagement/Main";
				} else if(StringUtils.equals(adminType, "ADM0003")) {
					return "redirect:/FranchiseeManagement/Main";
				}
			}
			try {
				// QSS 타이틀 설정
				session.setAttribute("MENU_KOR_NAME", "QSS");

				// 관리자 정보 set
				String domainIdx	= (String)session.getAttribute("domainIdx");
				String brandIdx		= (String)session.getAttribute("brandIdx");
				String frncIdx		= (String)session.getAttribute("francIdx");
				String adminType	= (String)session.getAttribute("adminType");

				// 브랜드 콤보 목록 조회
				BrandVo brandVo = new BrandVo();
				brandVo.setCaseString("Brand_ListBrand");
				brandVo.setDomainIdx(domainIdx);
				brandVo.setSessionDomainIdx(domainIdx);
				if(commonVo.getBrandIdx() != null)
					brandVo.setSessionBrandIdx(brandIdx);
				if(commonVo.getFrancIdx() != null)
					brandVo.setSessionFrancIdx(frncIdx);


				brandVo.setSessionAdminType(adminType);
				List<BrandVo> list = (List<BrandVo>)brandManagementService.SelectListData(brandVo);

				model.addAttribute("brandList", list);

				List<?> franchiseList = null;
				// 가맹점정보가 있으면 가맹점콤보 같이 조회
				if(!StringUtils.isEmpty(frncIdx)) {
					FranchiseeVo franchiseeVo = new FranchiseeVo();

					franchiseeVo.setDomainIdx(domainIdx);
					if(commonVo.getBrandIdx() != null)
						franchiseeVo.setBrandIdx(commonVo.getBrandIdx());
					else
						franchiseeVo.setBrandIdx(brandIdx);
					franchiseeVo.setCaseString("Franchisee_SelectCombo");
					franchiseList = franchiseeManagementService.SelectListData(franchiseeVo);
				}

				model.addAttribute("francList", franchiseList);

				model.addAttribute("commonVo", commonVo);


			} catch (Exception e) {
				logger.error("========== Main ==========" + e.getMessage());
			}


			return "/Mobile/Resource/Main";
		}
		else {
			return "redirect:/Logout";
		}
	}

}
