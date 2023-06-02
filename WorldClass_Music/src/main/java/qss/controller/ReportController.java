package qss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.BrandManagementImpl;
import qss.impl.FranchiseeManagementImpl;
import qss.impl.ReportImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.ReportVo;
import qss.vo.SystemCodeVo;

/**
 * 매출 관리 > 매출 내역 리포트
 * 
 * <pre>
 * scoservice.controller
 *    |_ ReportController.java
 *
 * </pre>
 * 
 * @date : 2019. 05. 28. 오전 12:22:05
 * @version :
 * @author : admin
 */
@Controller
public class ReportController {

    @Resource(name = "reportService")
    Qss reportService = new ReportImpl();

    @Resource(name = "systemCodeService")
    Qss systemCodeService = new SystemCodeImpl();

    @Resource(name = "brandManagementService")
    Qss brandManagementService = new BrandManagementImpl();

    @Resource(name = "franchiseeManagementService")
    Qss franchiseeManagementService = new FranchiseeManagementImpl();

    private static final Log logger = LogFactory.getLog(ReportController.class);

    /**
     * <pre>
     * 1. 개요 :
     * 2. 처리내용 :
     * </pre>
     * 
     * @Method Name : Main
     * @date : 2019. 1. 15.
     * @author : ksh
     * @history : ----------------------------------------------------------------------- 변경일 작성자 변경내용 ----------- -------------------
     *          --------------------------------------- 2019. 1. 15. ksh 최초 작성 -----------------------------------------------------------------------
     *
     * @param ReportVo
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "Report/Main")
    public String Main(@ModelAttribute("ReportVo") ReportVo reportVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        // 20200409 : 광교갤러리아 커스터마이징 보고서
        if ((String) session.getAttribute("id") != null) {
            model.addAttribute("ReportVo", reportVo);
            return "/Report/Main";
        } else {
            return "redirect:/Logout";
        }
    }

    /**
     * <pre>
     * 1. 개요 :
     * 2. 처리내용 :
     * </pre>
     * 
     * @Method Name : List
     * @date : 2019. 1. 15.
     * @author : ksh
     * @history : ----------------------------------------------------------------------- 변경일 작성자 변경내용 ----------- -------------------
     *          --------------------------------------- 2019. 1. 15. ksh 최초 작성 -----------------------------------------------------------------------
     *
     * @param ReportVo
     * @param session
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Report/List")
    @ResponseBody
    public AjaxResultVO List(ReportVo reportVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        reportVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        // 관리자 정보 set
        reportVo.setSessionDomainIdx((String) session.getAttribute("domainIdx"));
        reportVo.setSessionBrandIdx((String) session.getAttribute("brandIdx"));
        reportVo.setSessionFrancIdx((String) session.getAttribute("francIdx"));
        reportVo.setSessionAdminType((String) session.getAttribute("adminType"));

        try {

            if ("".equals(reportVo.getReportType())) {
                reportVo.setReportType("RPT0001");;
            }

            reportVo.setCaseString("Report_List");

            List<ReportVo> list = (List<ReportVo>) reportService.SelectListData(reportVo);
            result.setData(list);

            reportVo.setCaseString("Report_ListCnt");
            result.setiTotalDisplayRecords(reportService.DataByCnt(reportVo));

            reportVo.setCaseString("Report_Stat");
            List<ReportVo> totlist = (List<ReportVo>) reportService.SelectListData(reportVo);

            result.setTotalData(totlist);

        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            messages.put("title", "서버오류가 발생하였습니다.");
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
            result.setMessages(messages);
        }

        return result;
    }

    /**
     * <pre>
     * 1. 개요 :
     * 2. 처리내용 :
     * </pre>
     * 
     * @Method Name : Main
     * @date : 2019. 1. 15.
     * @author : ksh
     * @history : ----------------------------------------------------------------------- 변경일 작성자 변경내용 ----------- -------------------
     *          --------------------------------------- 2019. 1. 15. ksh 최초 작성 -----------------------------------------------------------------------
     *
     * @param ReportVo
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "Mobile/Report/Main")
    public String MobileMain(@ModelAttribute("ReportVo") ReportVo reportVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {

        if ((String) session.getAttribute("id") != null) {
            SystemCodeVo systemCodeVo = new SystemCodeVo();
            systemCodeVo.setCaseString("SystemCode_ListSystemCode");
            systemCodeVo.setCodeGroup("RPT0000");
            model.addAttribute("SystemCodeList", systemCodeService.SelectListData(systemCodeVo));

            return "/Mobile/Report/Main";
        } else {
            return "redirect:/Logout";
        }
    }

}
