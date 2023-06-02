
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
import qss.vo.FloorFacilitiesVo;
import qss.vo.FloorVo;
import qss.vo.SystemCodeVo;

//층관리 메인
@Controller
public class FloorFacilitiesManagementController {
    private static final Log logger = LogFactory.getLog(FloorFacilitiesManagementController.class);

    @Resource(name = "floorManagementService")
    Qss floorManagementService = new FloorManagementImpl();

    @Resource(name = "commonService")
    Qss commonService = new CommonImpl();

    // 층 상세정보 - 편의시설관리
    @RequestMapping(value = "FloorManagement/FacilitiesForm")
    public String facilities(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        try {
            model.addAttribute("FloorVo", floorVo);

            if (floorVo.getFloorIdx() != null && !floorVo.getFloorIdx().equals(BigInteger.ZERO)) {
                floorVo.setCaseString("Floor_Form");
                floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

                model.addAttribute("FloorVo", (FloorVo) floorManagementService.SelectData(floorVo));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "/FloorManagement/FloorFacilitiesForm";
    }

    // 층 편의시설 리스트
    @RequestMapping(value = "FloorManagement/FloorFacilitiesList")
    @ResponseBody
    public AjaxResultVO getFloorFacilitiesList(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        String domainIdx = (String) session.getAttribute("domainIdx");

        try {
            floorVo.setCaseString("Floor_FloorFacilitiesList");
            floorVo.setDomainIdx(domainIdx);

            // 층 매장 정보
            @SuppressWarnings("unchecked")
            List<FloorFacilitiesVo> resultList = (List<FloorFacilitiesVo>) floorManagementService.SelectListData(floorVo);

            model.addAttribute("FloorFacilitiesVo", resultList);
            result.setData(resultList);

            result.setResultCode(0);
            result.setMessages(messages);

        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 편의시설 추가 - Form
    @RequestMapping(value = "FloorManagement/FacilitiesAddForm")
    public String facilitiesAddForm(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        if ((String) session.getAttribute("id") != null) {
            try {
            } catch (Exception e) {
                logger.error("##### FloorManagement facilitiesAddForm Exception : " + e.getMessage());
            }
            return "/FloorManagement/FacilitiesAddForm";
        } else {
            return "redirect:/Logout";
        }
    }

    // 층 편의시설 리스트
    @RequestMapping(value = "FloorManagement/TotalFacilitiesList")
    @ResponseBody
    public AjaxResultVO getTotalFacilitiesList(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        floorVo.setCaseString("Floor_TotalFacilitiesList");
        floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

        try {
            @SuppressWarnings("unchecked")
            List<SystemCodeVo> resultList = (List<SystemCodeVo>) floorManagementService.SelectListData(floorVo);

            model.addAttribute("SystemCodeVo", resultList);
            result.setData(resultList);
            result.setiTotalDisplayRecords(6);

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 편의시설 정보 등록
    @RequestMapping(value = "FloorManagement/FloorFacilitiesInsert")
    @ResponseBody
    public AjaxResultVO insertFloorFacilities(FloorFacilitiesVo floorFacilitiesVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            floorFacilitiesVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorFacilitiesVo.setCaseString("Floor_FloorFacilitiesCreate");

            cnt = floorManagementService.UpdateData(floorFacilitiesVo);
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

    // 층 편의시설 정보 수정
    @RequestMapping(value = "FloorManagement/FloorFacilitiesUpdate")
    @ResponseBody
    public AjaxResultVO updateFloorFacilities(@RequestParam(required = false) String jsonData, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        Gson gson = new Gson();
        List<FloorFacilitiesVo> updatelist = gson.fromJson(jsonData, new TypeToken<List<FloorFacilitiesVo>>() {
        }.getType());

        try {
            int updateCount = floorManagementService.updateListData("Floor_FloorFacilitiesListUpdate", (List<?>) updatelist);

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

    // 층 편의시설 정보 삭제
    @RequestMapping(value = "FloorManagement/FloorFacilitiesDelete")
    @ResponseBody
    public AjaxResultVO removeFloorFacilities(FloorFacilitiesVo floorFacilitiesVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            floorFacilitiesVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorFacilitiesVo.setCaseString("Floor_FloorFacilitiesDelete");

            cnt = floorManagementService.DeleteDataByObjectParam(null, floorFacilitiesVo);
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