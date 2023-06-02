package qss.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

import qss.impl.BrandManagementImpl;
import qss.impl.CommonImpl;
import qss.impl.IndexImpl;
import qss.impl.MenuInfoImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.BrandVo;
import qss.vo.CommonVo;
import qss.vo.IndexVo;
import qss.vo.MenuInfoVo;

/**
 * 인덱스 관리
 * <pre>
 * qss.controller
 *    |_ IndexContorller.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:20:08
 * @version :
 * @author : admin
 */
@Controller
public class IndexContorller {
	private static final Log logger = LogFactory.getLog(IndexContorller.class);

	@Resource(name = "indexService")
	Qss indexService = new IndexImpl();
	SessionManage sManage = new qss.controller.SessionManage();

	@Resource(name = "commonService")
	Qss commonService = new CommonImpl();

	@Resource(name = "menuInfoService")
	Qss menuInfoService = new MenuInfoImpl();

	@Resource(name = "brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	@RequestMapping(value = "/Index")
	public String Index(@ModelAttribute("IndexVo") IndexVo indexVo) throws Exception {
		return "/Index";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/LoginCheck", method = RequestMethod.POST)
	@JsonIgnore
	@ResponseBody
	private AjaxResultVO LoginCheck(@ModelAttribute("IndexVo") IndexVo indexVo, HttpServletRequest request, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		IndexVo sessionVo = new IndexVo();

		try {
			if (session.getAttribute("id") != null) {
				session.removeAttribute((String) session.getAttribute("id"));
			}

//			indexVo.setLH_ACCESSIP(request.getRemoteAddr());
			indexVo.setCaseString("Login_GetIdPwdCheck");
			HashMap<?, ?> checkSessionMap = indexService.SelectDataMap(indexVo);

			if(checkSessionMap == null
					|| "N".equals(checkSessionMap.get("idCheck").toString())
					|| "N".equals(checkSessionMap.get("francCheck").toString())
					|| "N".equals(checkSessionMap.get("brandCheck").toString())
				)
			{
				result.setResultCode(1);
				messages.put("title", "ID 또는 비밀번호가 올바르지 않습니다.");
				result.setMessages(messages);
			}else if(checkSessionMap == null || "N".equals(checkSessionMap.get("activeCheck").toString())){
				result.setResultCode(1);
				messages.put("title", "비활성화 된 사용자 입니다. \n관리자에게 문의하세요.");
				result.setMessages(messages);
			}else if(checkSessionMap == null || Integer.parseInt(checkSessionMap.get("passwordCntCheck").toString()) >= 5){
				result.setResultCode(1);
				messages.put("title", " 로그인 5회이상 틀리셨습니다. \n계정이 잠겨있습니다. \n관리자에게 문의하세요.");
				result.setMessages(messages);
			}else if(checkSessionMap == null || "N".equals(checkSessionMap.get("passwordCheck").toString())){
				result.setResultCode(1);
				/* 비밀번호 실패 횟수 추가 */
				indexVo.setCaseString("Login_SetPwdFailCnt");
				indexVo.setCnt(Integer.parseInt(checkSessionMap.get("passwordCntCheck").toString())+1);
				indexService.UpdateData(indexVo);
				// 20190531 4회에서 5회째일때 메시지 분기 추가
				if(Integer.parseInt(checkSessionMap.get("passwordCntCheck").toString()) >= 4) {
					messages.put("title", " 로그인 5회이상 틀리셨습니다. \n계정이 잠겨있습니다. \n관리자에게 문의하세요.");
				}else {
					messages.put("title", "ID 또는 비밀번호가 올바르지 않습니다.");
				}

				result.setMessages(messages);
			}else {

				/* 비밀번호 실패 초기화 추가 */
				indexVo.setCaseString("Login_SetPwdFailCnt");
				indexVo.setCnt(0);
				indexService.UpdateData(indexVo);

				indexVo.setCaseString("Login_GetMember");
				sessionVo = (IndexVo) indexService.SelectData(indexVo);

				// 비활성화 사용자 제한 추가 20190314
				if(!"Y".equals(sessionVo.getActiveYn())) {
					result.setResultCode(1);
					messages.put("title", "비활성화 된 사용자 입니다.\n관리자에게 문의하세요.");
					result.setMessages(messages);
				}

				// 브랜드 및 가맹점 비활성화 여부 추가 20190314
				if("ADM0002".equals(sessionVo.getAdminType())) {
					// 브랜드 관리자
					sessionVo.setCaseString("Login_GetBrnActiveCheck");
					IndexVo checkVo = new IndexVo();
					checkVo = (IndexVo) indexService.SelectData(sessionVo);

					if(!"Y".equals(checkVo.getActiveYn())) {
						result.setResultCode(1);
						messages.put("title", "비활성화 된 사용자 입니다.\n관리자에게 문의하세요.");
						result.setMessages(messages);
					}

				}else if("ADM0003".equals(sessionVo.getAdminType())) {
					// 매장 관리자

					// 브랜드 활성화 여부 확인
					sessionVo.setCaseString("Login_GetBrnActiveCheck");
					IndexVo checkVo = new IndexVo();
					checkVo = (IndexVo) indexService.SelectData(sessionVo);

					if(!"Y".equals(checkVo.getActiveYn())) {
						result.setResultCode(1);
						messages.put("title", "비활성화 된 사용자 입니다.\n관리자에게 문의하세요.");
						result.setMessages(messages);
					}

					// 매장활성화 여부 확인
					sessionVo.setCaseString("Login_GetFrnActiveCheck");
					checkVo = new IndexVo();
					checkVo = (IndexVo) indexService.SelectData(sessionVo);

					if(!"Y".equals(checkVo.getActiveYn())) {
						result.setResultCode(1);
						messages.put("title", "비활성화 된 사용자 입니다.\n관리자에게 문의하세요.");
						result.setMessages(messages);
					}
				}

				//menu 정보 가져오기
				sessionVo.setCaseString("Menu_List");
				List<MenuInfoVo> menelist = (List<MenuInfoVo>) menuInfoService.SelectListData(sessionVo);

				// 브랜드 대표 이미지 가져오기
				BrandVo brandVo = new BrandVo();
				brandVo.setCaseString("Brand_ViewBrand");

				brandVo.setDomainIdx(sessionVo.getDomainIdx());
				brandVo.setBrandIdx(sessionVo.getBrandIdx());
				brandVo.setFrancIdx(sessionVo.getFrancIdx());
				brandVo = (BrandVo) brandManagementService.SelectData(brandVo);
				if(brandVo != null) {
					session.setAttribute("brandMainImgPath", brandVo.getBrandMainImgPath());
				}
//				result.setData(list);
				setSession(sessionVo, menelist, session);

				result.setData(sessionVo);
			}


			// 사용자 정보 확인
//			int idChk = indexService.DataByCnt(indexVo);

//			if (idChk == 1) {
//				indexVo.setCaseString("Login_GetLockCheck");
//				int lockCnt = indexService.DataByCnt(indexVo);
//
//				// 계정이 Lock으로 인한 처리
//				if (lockCnt == 0) {
//					result.setResultCode(1);
//					messages.put("title", "5회이상 비밀번호 오류로 접속이 차단됩니다.\r\n 관리자에게 문의 바랍니다.");
//					result.setMessages(messages);
//				} else {
					/*indexVo.setCaseString("Login_GetIdPwdCheck");
					int idPwdChk = indexService.DataByCnt(indexVo);

					if (idPwdChk == 1) {*/
//						indexVo.setCaseString("Login_GetMember");
//						IndexVo sessionVo = (IndexVo) indexService.SelectData(indexVo);

						/*if (sessionVo.getPASSWORDEXPIREDATE() < sessionVo.getMODIDATE()) {
							messages.put("title", "PASSWORD 만료일이 지나 변경이 필요합니다.");
							result.setMessages(messages);
							result.setData(sessionVo.getIDX());
						} else {
							if (indexVo.getMID().equals(indexVo.getPASSWORD())) {
								messages.put("title", "ID와 PASSWORD가 동일한 경우 PASSWORD 변경 후 사용이 가능합니다.");
								result.setMessages(messages);
								result.setData(sessionVo.getIDX());
							} else {
								setSession(sessionVo, session);
							}
						}*/
					/*} else {
						indexVo.setCaseString("Login_SetPwdFailCnt");
						int failCount = indexService.UpdateData(indexVo);

						result.setResultCode(1);

						if (failCount > 5) {
							messages.put("title", "5회이상 비밀번호 오류로 접속이 차단됩니다. \r\n 관리자에게 문의 바랍니다.");
						} else {
							messages.put("title", "5회이상 비밀번호 오류 시 접속이 차단 됩니다. \r\n" + failCount + "회입력 오류가 발생 하였습니다.");
						}
						result.setMessages(messages);
					}*/
//				}
//			} else {
//				result.setResultCode(1);
//				messages.put("title", "등록되지 않은 ID입니다.");
//				result.setMessages(messages);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage());

			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

/*		private int IDX;
		private String NAME;
		private String ID;
		private String MID;
		private String PASSWORD;
		private String ORL_PWD;
		private String NEW_PWD;
		private int MODIDATE;
		private int PASSWORDEXPIREDATE;
		private int DOMAINIDX;
		private int FRANCIDX;
		private int BRANDIDX;
		private String mainModYn;
		private String orderModYn;
		private int CNT;
		private int ERR;*/

		/*result.setResultCode(0);
		IndexVo sessionVo = new IndexVo();
		sessionVo.setIDX(0);
		sessionVo.setNAME("test");
		sessionVo.setID("test");
		sessionVo.setMID("test");
		sessionVo.setPASSWORD("test");
		sessionVo.setMODIDATE(99991212);
		sessionVo.setPASSWORDEXPIREDATE(99991212);
		sessionVo.setDOMAINIDX(0);
		sessionVo.setFRANCIDX(0);
		sessionVo.setBRANDIDX(0);
		sessionVo.setMainModYn("Y");
		sessionVo.setOrderModYn("Y");
		sessionVo.setCNT(1);
		sessionVo.setERR(0);
		setSession(sessionVo, session);
		*/

		return result;
	}

	@RequestMapping(value = "/PwdChange", method = RequestMethod.POST)
	@JsonIgnore
	@ResponseBody
	private AjaxResultVO PwdChange(@ModelAttribute("IndexVo") IndexVo indexVo, HttpServletRequest request) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			indexVo.setCaseString("Login_SetPwd");
			int pwCnt = indexService.UpdateData(indexVo);

			if (pwCnt == 0) {
				String reqData = "";

				reqData = reqData + "{\"ID\":\"";
				reqData = reqData + indexVo.getId();
//				reqData = reqData + "\", \"IDX\":\"";
//				reqData = reqData + indexVo.getIDX();
				reqData = reqData + "\", \"PASSWORD\":\"";
				reqData = reqData + indexVo.getPassword();
//				reqData = reqData + "\", \"NEW_PWD\":\"";
//				reqData = reqData + indexVo.getNEW_PWD();
				reqData = reqData + "\"}";

				CommonVo commonVo = new CommonVo();
				/*commonVo.setLOG_IDX(indexVo.getIDX());
				commonVo.setCTRLNAME("PwdChange");
				commonVo.setREQDATA(reqData);
				commonVo.setUSERIP(request.getRemoteAddr());*/

				commonService.InsertData(commonVo);
			} else if (pwCnt == 1) {
				result.setResultCode(1);
				messages.put("title", "이미 등록되었던 password는 다시 사용이 불가능합니다.");
				result.setMessages(messages);

			} else if (pwCnt == 2) {
				result.setResultCode(1);
				messages.put("title", "사용이 중지되었거나, 잠긴 계정입니다. \n 운영팀에 문의바랍니다.");
				result.setMessages(messages);
			}
		} catch (Exception e) {
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
			logger.error(e.getMessage());
		}

		return result;
	}

	@SuppressWarnings("static-access")
	private void setSession(@ModelAttribute("IndexVo") IndexVo indexVo, List<MenuInfoVo> menuInfoVoList, HttpSession session) throws Exception {
		session.setAttribute("id", indexVo.getId());
		session.setAttribute("name", indexVo.getName());
//		session.setAttribute("IDX", indexVo.getIDX());
		session.setAttribute("domainIdx", indexVo.getDomainIdx());
		session.setAttribute("brandIdx", indexVo.getBrandIdx());
		session.setAttribute("francIdx", indexVo.getFrancIdx());
		session.setAttribute("menuList", menuInfoVoList);
//		session.setAttribute("MAINMODYN", indexVo.getMainModYn());
//		session.setAttribute("ORDERMODYN", indexVo.getOrderModYn());
		session.setAttribute("adminType", indexVo.getAdminType());
		session.setAttribute(indexVo.getId(), sManage.getInstance());
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	private int ServerSessionChk(@ModelAttribute("IndexVo") IndexVo indexVo, HttpSession session, HttpServletResponse response)
			throws Exception, IllegalStateException {
		int isUsing = 0;

		if (sManage.loginUsers.containsValue(indexVo.getId())) {
			Enumeration e = sManage.loginUsers.keys();
			session = null;

			while (e.hasMoreElements()) {
				session = (HttpSession) e.nextElement();

				if (session != null) {
					if (sManage.loginUsers.get(session).equals(indexVo.getId())) {
						isUsing++;
					}
				}
			}

			if (isUsing > 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/SessionCheck")
	@JsonIgnore
	@ResponseBody
	private String SessionCheck(IndexVo indexVo, HttpSession session, HttpServletResponse response) throws Exception {
		String loginChk = "";
		int sChk = ServerSessionChk(indexVo, session, response);

		if (sChk > 0) {
			if (sManage.loginUsers.containsValue(indexVo.getId())) {
				@SuppressWarnings("rawtypes")
				Enumeration e = sManage.loginUsers.keys();
				session = null;

				while (e.hasMoreElements()) {
					session = (HttpSession) e.nextElement();

					if (session != null) {
						if (sManage.loginUsers.get(session).equals(indexVo.getId())) {
							session.removeAttribute(indexVo.getId());
							session.invalidate();
						}
					}
				}
			}
			loginChk = "false";
		} else {
			loginChk = "true";
		}

		return loginChk;
		//20190520 test를 위한 임시주석
		//return "true";
	}
}