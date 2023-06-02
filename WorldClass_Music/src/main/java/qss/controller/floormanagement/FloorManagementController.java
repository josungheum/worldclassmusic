
package qss.controller.floormanagement;

import java.math.BigInteger;
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

import qss.impl.CommonImpl;
import qss.impl.FloorManagementImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.FloorVo;
import qss.vo.SystemCodeVo;

//층관리 메인
@Controller
public class FloorManagementController {
    private static final Log logger = LogFactory.getLog(FloorManagementController.class);

    @Resource(name = "floorManagementService")
    Qss floorManagementService = new FloorManagementImpl();

    @Resource(name = "commonService")
    Qss commonService = new CommonImpl();

    // 층 관리 메인 view
    @RequestMapping(value = "FloorManagement/Main")
    public String main(@ModelAttribute("FloorVo") FloorVo floorVo, HttpSession session, ModelMap model) {
        if ((String) session.getAttribute("id") != null) {
            try {
            } catch (Exception e) {
                logger.error("##### FloorManagement Main Exception : " + e.getMessage());
            }
            return "/FloorManagement/Main";
        } else {
            return "redirect:/Logout";
        }
    }

    // 층 리스트
    @RequestMapping(value = "FloorManagement/FloorList")
    @ResponseBody
    public AjaxResultVO getFloorList(FloorVo floorVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        // 층 리스트 정보 조회
        try {
            floorVo.setCaseString("Floor_ListFloor");

            @SuppressWarnings("unchecked")
            List<FloorVo> resultList = (List<FloorVo>) floorManagementService.SelectListData(floorVo);
            result.setData(resultList);

            floorVo.setCaseString("Floor_ListCount");
            result.setiTotalDisplayRecords(floorManagementService.DataByCnt(floorVo));

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }
        return result;
    }

    // 층 정보 등록 View
    @RequestMapping(value = "FloorManagement/FormNullTemp")
    public String form(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        try {
            model.addAttribute("FloorVo", floorVo);

            if (floorVo.getFloorIdx() != null && !floorVo.getFloorIdx().equals(BigInteger.ZERO)) {
                floorVo.setCaseString("Floor_Form");
                floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

                model.addAttribute("FloorVo", (FloorVo) floorManagementService.SelectData(floorVo));
            }

            floorVo.setCaseString("Floor_BrandTypeCodeList");
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            @SuppressWarnings("unchecked")
            List<SystemCodeVo> list = (List<SystemCodeVo>) floorManagementService.SelectListData(floorVo);
            model.addAttribute("brandTypeCodeList", list);

        } catch (Exception e) {
            logger.error("##### FloorManagement FormNullTemp Exception : " + e.getMessage());
        }

        return "/FloorManagement/Form";
    }

    // 층 정보 등록 / 수정
    @RequestMapping(value = "FloorManagement/FloorUpdate")
    @ResponseBody
    public AjaxResultVO modifyFloor(FloorVo floorVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            // 층 단축표시명 중복 확인
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorVo.setRegUser((String) session.getAttribute("id"));
            floorVo.setCaseString("Floor_FloorOverlap");

            if (floorManagementService.DataByCnt(floorVo) == 0) {
                // floorIdx값으로 insert / update 판별
                if (floorVo.getFloorIdx() != null && !floorVo.getFloorIdx().equals(BigInteger.ZERO)) {
                    floorVo.setCaseString("Floor_FloorUpdate");
                } else {
                    floorVo.setCaseString("Floor_FloorCreate");
                }

                cnt = floorManagementService.UpdateData(floorVo);
                if (cnt > 0) {
                    result.setResultCode(0);
                } else {
                    result.setResultCode(1);
                    messages.put("title", "처리 되지 않았습니다.");
                }
            } else {
                if (floorVo.getFloorIdx() == null || floorVo.getFloorIdx().equals(BigInteger.ZERO)) {
                    result.setResultCode(1);
                    messages.put("title", "중복되는 층 단축표시명입니다.");
                }
            }

            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 상세정보 - Form
    @RequestMapping(value = "FloorManagement/DetailForm")
    public String detail(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        try {
            floorVo.setCaseString("Floor_Form");
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

            model.addAttribute("FloorVo", (FloorVo) floorManagementService.SelectData(floorVo));
        } catch (Exception e) {
            logger.error("##### FloorManagement DetailForm Exception : " + e.getMessage());
        }

        return "/FloorManagement/FloorDetailForm";
    }

    // 층 상세 정보 - Ajax
    @RequestMapping(value = "FloorManagement/FloorInfo")
    @ResponseBody
    public AjaxResultVO getFloorInfo(FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        String domainIdx = (String) session.getAttribute("domainIdx");

        try {
            floorVo.setCaseString("Floor_Form");
            floorVo.setDomainIdx(domainIdx);

            FloorVo resultData = (FloorVo) floorManagementService.SelectData(floorVo);
            model.addAttribute("FloorVo", resultData);

            result.setData(resultData);
            result.setResultCode(0);
            result.setMessages(messages);

        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 정보 활성화/비활성화
    @RequestMapping(value = "FloorManagement/FloorActive")
    @ResponseBody
    public AjaxResultVO activeFloor(FloorVo floorVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            floorVo.setCaseString("Floor_ActiveFloor");
            floorVo.setRegUser((String) session.getAttribute("id"));
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

            int cnt = floorManagementService.UpdateData(floorVo);

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

    // 층 정보 삭제
    @RequestMapping(value = "FloorManagement/FloorDelete")
    @ResponseBody
    public AjaxResultVO deleteFloor(FloorVo floorVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        try {
            floorVo.setCaseString("Floor_DeleteFloor");
            floorVo.setRegUser((String) session.getAttribute("id"));
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

            int cnt = floorManagementService.UpdateData(floorVo);

            if (cnt > 0) {
                result.setResultCode(0);
            } else {
                result.setResultCode(1);
                messages.put("title", "처리 되지 않았습니다.");
            }
            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
        }
        return result;
    }
}