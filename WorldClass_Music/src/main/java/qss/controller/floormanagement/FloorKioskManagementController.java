
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import qss.impl.CommonImpl;
import qss.impl.FloorManagementImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.FloorKioskVo;
import qss.vo.FloorVo;

@Controller
public class FloorKioskManagementController {
    private static final Log logger = LogFactory.getLog(FloorKioskManagementController.class);

    @Resource(name = "floorManagementService")
    Qss floorManagementService = new FloorManagementImpl();

    @Resource(name = "commonService")
    Qss commonService = new CommonImpl();

    // 층 상세정보 Form - 단말관리
    @RequestMapping(value = "FloorManagement/KioskForm")
    public String kioskForm(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        try {

            if (floorVo.getFloorIdx() != null && !floorVo.getFloorIdx().equals(BigInteger.ZERO)) {
                floorVo.setCaseString("Floor_Form");
                floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

                model.addAttribute("FloorVo", (FloorVo) floorManagementService.SelectData(floorVo));
            }
        } catch (Exception e) {
            logger.error("##### FloorManagement KisokForm Exception : " + e.getMessage());
        }

        return "/FloorManagement/FloorKioskForm";
    }

    // 층 단말 리스트
    @RequestMapping(value = "FloorManagement/FloorKioskList")
    @ResponseBody
    public AjaxResultVO getFloorKioskList(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        String domainIdx = (String) session.getAttribute("domainIdx");

        try {
            floorVo.setCaseString("Floor_FloorKioskList");
            floorVo.setDomainIdx(domainIdx);

            // 층 단말 정보
            @SuppressWarnings("unchecked")
            List<FloorKioskVo> resultList = (List<FloorKioskVo>) floorManagementService.SelectListData(floorVo);

            model.addAttribute("FloorKioskVo", resultList);
            result.setData(resultList);
            result.setResultCode(0);
            result.setMessages(messages);

        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 단말 추가 - Form
    @RequestMapping(value = "FloorManagement/KioskAddForm")
    public String kioskAddForm(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        if ((String) session.getAttribute("id") != null) {
            try {
            } catch (Exception e) {
                logger.error("##### FloorManagement KioskAddForm Exception : " + e.getMessage());
            }
            return "/FloorManagement/KioskAddForm";
        } else {
            return "redirect:/Logout";
        }
    }

    // 층 단말 리스트
    @RequestMapping(value = "FloorManagement/KioskTotalList")
    @ResponseBody
    public AjaxResultVO getKioskTotalList(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        floorVo.setCaseString("Floor_KioskTotalList");
        floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        try {
            @SuppressWarnings("unchecked")
            List<FloorKioskVo> resultList = (List<FloorKioskVo>) floorManagementService.SelectListData(floorVo);

            model.addAttribute("FloorKioskVo", resultList);
            result.setData(resultList);
            result.setiTotalDisplayRecords(57);

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 단말 정보 등록
    @RequestMapping(value = "FloorManagement/FloorKioskInsert")
    @ResponseBody
    public AjaxResultVO addFloorKiosk(@RequestParam(required = false) String jsonData, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        Gson gson = new Gson();
        List<FloorKioskVo> insertlist = gson.fromJson(jsonData, new TypeToken<List<FloorKioskVo>>() {
        }.getType());

        try {
            int insertCount = floorManagementService.updateListData("Floor_FloorKioskListCreate", (List<?>) insertlist);

            if (insertCount > 0) {
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

    // 층 단말 정보 수정
    @RequestMapping(value = "FloorManagement/FloorKioskUpdate")
    @ResponseBody
    public AjaxResultVO modifyFloorKiosk(@RequestParam(required = false) String jsonData, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        Gson gson = new Gson();
        List<FloorKioskVo> updatelist = gson.fromJson(jsonData, new TypeToken<List<FloorKioskVo>>() {
        }.getType());

        try {
            int updateCount = floorManagementService.updateListData("Floor_FloorKioskListUpdate", (List<?>) updatelist);

            if (updateCount > 0) {
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

    // 층 단말 정보 삭제
    @RequestMapping(value = "FloorManagement/FloorKioskDelete")
    @ResponseBody
    public AjaxResultVO removeFloorKiosk(FloorKioskVo floorKioskVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            floorKioskVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorKioskVo.setCaseString("Floor_FloorKioskDelete");

            cnt = floorManagementService.DeleteDataByObjectParam(null, floorKioskVo);
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
}