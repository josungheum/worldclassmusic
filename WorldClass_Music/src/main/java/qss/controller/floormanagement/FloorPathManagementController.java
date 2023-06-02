
package qss.controller.floormanagement;

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

import com.google.gson.Gson;

import qss.impl.CommonImpl;
import qss.impl.FloorManagementImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.FloorKioskVo;
import qss.vo.FloorMappathVo;
import qss.vo.FloorVo;

@Controller
public class FloorPathManagementController {
    private static final Log logger = LogFactory.getLog(FloorPathManagementController.class);

    @Resource(name = "floorManagementService")
    Qss floorManagementService = new FloorManagementImpl();

    @Resource(name = "commonService")
    Qss commonService = new CommonImpl();

    // 층 상세정보 Form - 경로관리
    @RequestMapping(value = "FloorManagement/PathForm")
    public String path(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        try {
            floorVo.setCaseString("Floor_Form");
            floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));

            model.addAttribute("FloorVo", (FloorVo) floorManagementService.SelectData(floorVo));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "/FloorManagement/FloorPathForm";
    }

    // 층 경로 정보
    @RequestMapping(value = "FloorManagement/FloorPath")
    @ResponseBody
    public AjaxResultVO getFloorPathInfo(FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        String domainIdx = (String) session.getAttribute("domainIdx");

        try {
            floorVo.setCaseString("Floor_FloorMappath");
            floorVo.setDomainIdx(domainIdx);

            FloorMappathVo resultData = (FloorMappathVo) floorManagementService.SelectData(floorVo);
            model.addAttribute("FloorMappathVo", resultData);

            floorVo.setCaseString("Floor_KioskTotalList");
            @SuppressWarnings("unchecked")
            List<FloorKioskVo> floorKioskList = (List<FloorKioskVo>) floorManagementService.SelectListData(floorVo);
            // model.addAttribute("FloorKioskList", floorKioskList);
            Gson gson = new Gson();
            String list = gson.toJson(floorKioskList);
            messages.put("floorKioskList", list); // TODO : message가 아닌 다른 형태로

            result.setData(resultData);
            result.setResultCode(0);
            result.setMessages(messages);

        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 경로 정보 등록 / 수정
    @RequestMapping(value = "FloorManagement/FloorMappathUpdate")
    @ResponseBody
    public AjaxResultVO modifyFloorPath(FloorMappathVo floorMappathVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            // 층 경로 정보 저장 확인
            floorMappathVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorMappathVo.setRegUser((String) session.getAttribute("id"));
            floorMappathVo.setCaseString("Floor_FloorMappathOverlap");

            if (floorManagementService.DataByCnt(floorMappathVo) == 0) {
                floorMappathVo.setCaseString("Floor_FloorMappathCreate");
            } else {
                floorMappathVo.setCaseString("Floor_FloorMappathUpdate");
            }

            cnt = floorManagementService.UpdateData(floorMappathVo);
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