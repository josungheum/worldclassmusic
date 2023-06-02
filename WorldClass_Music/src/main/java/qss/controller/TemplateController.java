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

import qss.impl.TemplateImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.TemplateVo;

@Controller
public class TemplateController {
    private static final Log logger = LogFactory.getLog(TemplateController.class);

    @Resource(name = "templateService")
    Qss templateService = new TemplateImpl();

    @RequestMapping(value = "Template/Main")
    public String template(@ModelAttribute("TemplateVo") TemplateVo templateVo, HttpSession session, ModelMap model) throws Exception {
        if ((String) session.getAttribute("id") != null) {
            return "/Template/Main";
        } else {
            return "/Logout";
        }
    }

    @RequestMapping(value = "Template/Form")
    public String template(@ModelAttribute("TemplateVo") TemplateVo templateVo, ModelMap model, HttpSession session) throws Exception {
        model.addAttribute(templateVo);

        if ((String) session.getAttribute("id") != null) {
            return "/Template/Form";
        } else {
            return "/Logout";
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("Template/List")
    @ResponseBody
    public AjaxResultVO getTemplateList(TemplateVo templateVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        templateVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        // 관리자 정보 set
        templateVo.setSessionDomainIdx((String) session.getAttribute("domainIdx"));
        templateVo.setSessionBrandIdx((String) session.getAttribute("brandIdx"));
        templateVo.setSessionFrancIdx((String) session.getAttribute("francIdx"));
        templateVo.setSessionAdminType((String) session.getAttribute("adminType"));

        try {
            templateVo.setCaseString("Template_TemplateList");
            List<TemplateVo> list = (List<TemplateVo>) templateService.SelectListData(templateVo);
            result.setData(list);

            templateVo.setCaseString("Template_TemplateListCnt");
            result.setiTotalDisplayRecords(templateService.DataByCnt(templateVo));

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            messages.put("title", "서버오류가 발생하였습니다.");
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
            result.setMessages(messages);
        }

        return result;
    }

    @RequestMapping("Template/Get")
    @ResponseBody
    public AjaxResultVO templateform(TemplateVo templateVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        templateVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        try {
            templateVo.setCaseString("Template_TemplateGet");
            List<?> list = templateService.SelectListData(templateVo);

            result.setData(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            messages.put("title", "서버오류가 발생하였습니다.");
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
            result.setMessages(messages);
        }

        return result;
    }

    @RequestMapping("Template/Create")
    @ResponseBody
    public AjaxResultVO CreateTemplate(TemplateVo templateVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            templateVo.setCaseString("Template_Create");
            templateVo.setRegUser((String) session.getAttribute("id"));
            templateVo.setModUser((String) session.getAttribute("id"));
            int cnt = templateService.InsertData(templateVo);

            if (cnt > 0) {
                result.setResultCode(0);
            } else {
                result.setResultCode(1);
                messages.put("title", "처리되지 않았습니다.");
                messages.put("detail", "");
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

    @RequestMapping("Template/Update")
    @ResponseBody
    public AjaxResultVO updateTemplate(TemplateVo templateVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            templateVo.setCaseString("Template_Update");
            templateVo.setRegUser((String) session.getAttribute("id"));
            templateVo.setModUser((String) session.getAttribute("id"));
            int cnt = templateService.UpdateData(templateVo);

            if (cnt > 0) {
                result.setResultCode(0);
            } else {
                result.setResultCode(1);
                messages.put("title", "처리되지 않았습니다.");
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

    @RequestMapping("Template/Delete")
    @ResponseBody
    public AjaxResultVO deleteTemplate(TemplateVo templateVo, HttpSession session) throws Exception {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            templateVo.setCaseString("Template_Delete");
            templateVo.setRegUser((String) session.getAttribute("id"));
            templateVo.setModUser((String) session.getAttribute("id"));
            int cnt = templateService.DeleteDataByObjectParam(null, templateVo);

            if (cnt > 0) {
                if (cnt == 1) {
                    result.setResultCode(0);
                } else {
                    result.setResultCode(1);
                    //messages.put("title", "현재 스크린을 적용한 스케줄이 존재합니다.");
                    //messages.put("detail", "Schedule에 적용된 Screen이 존재합니다.");
                    messages.put("title", "처리되지 않았습니다.");
                    result.setMessages(messages);
                }
            } else {
                result.setResultCode(1);
                messages.put("title", "처리되지 않았습니다.");
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

    @RequestMapping(value = "Template/ContentForm")
    public String templateForm(@ModelAttribute("TemplateVo") TemplateVo templateVo, ModelMap model) {
        return "/Template/ContentForm";
    }

}