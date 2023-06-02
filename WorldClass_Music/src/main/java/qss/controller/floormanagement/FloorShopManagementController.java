
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
import qss.vo.FloorShopVo;
import qss.vo.FloorVo;
import qss.vo.ShopVo;

//층관리 메인
@Controller
public class FloorShopManagementController {
    private static final Log logger = LogFactory.getLog(FloorShopManagementController.class);

    @Resource(name = "floorManagementService")
    Qss floorManagementService = new FloorManagementImpl();

    @Resource(name = "commonService")
    Qss commonService = new CommonImpl();

    // 층 상세정보 - 브랜드(매장)관리
    @RequestMapping(value = "FloorManagement/ShopForm")
    public String shop(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
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

        return "/FloorManagement/FloorShopForm";
    }

    // 층 브랜드(매장) 리스트
    @RequestMapping(value = "FloorManagement/FloorShopList")
    @ResponseBody
    public AjaxResultVO getFloorShopList(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        String domainIdx = (String) session.getAttribute("domainIdx");

        // 층 리스트 정보 조회
        try {
            floorVo.setCaseString("Floor_FloorShopList");
            floorVo.setDomainIdx(domainIdx);

            // 층 매장 정보
            @SuppressWarnings("unchecked")
            List<FloorShopVo> resultList = (List<FloorShopVo>) floorManagementService.SelectListData(floorVo);

            // 매장 이름 줄바꿈 처리
            for (FloorShopVo floorShop : resultList) {
                String replacedName = floorShop.getDispNmKr().replaceAll("(\r\n|\r|\n|\n\r|\\n|\\r|\\r\\n|\n/)", "<br />");
                replacedName.replaceAll("[\\t]", "");

                floorShop.setDispNmKr(replacedName);
            }

            model.addAttribute("FloorShopVo", resultList);
            result.setData(resultList);

            result.setResultCode(0);
            result.setMessages(messages);

        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 브랜드(매장) 추가 - Form
    @RequestMapping(value = "FloorManagement/ShopAddForm")
    public String shopAddForm(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        if ((String) session.getAttribute("id") != null) {
            try {
            } catch (Exception e) {
                logger.error("##### FloorManagement shopAddForm Exception : " + e.getMessage());
            }
            return "/FloorManagement/ShopAddForm";
        } else {
            return "redirect:/Logout";
        }
    }

    // 층 브랜드(매장) 리스트
    @RequestMapping(value = "FloorManagement/TotalShopList")
    @ResponseBody
    public AjaxResultVO getTotalShopListByFloor(@ModelAttribute("FloorVo") FloorVo floorVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        floorVo.setCaseString("Floor_TotalShopList");
        floorVo.setDomainIdx((String) session.getAttribute("domainIdx"));
        floorVo.setFloorIdx(null); // 전체 매장리스트

        try {
            @SuppressWarnings("unchecked")
            List<ShopVo> resultList = (List<ShopVo>) floorManagementService.SelectListData(floorVo);

            for (ShopVo shop : resultList) {
                shop.setDispNmKr(shop.getDispNmKr().replaceAll("(\r\n|\r|\n|\n\r)", "<br />"));
            }

            model.addAttribute("ShopVo", resultList);
            result.setData(resultList);

            floorVo.setCaseString("Floor_TotalShopListCount");
            int s = floorManagementService.DataByCnt(floorVo);
            result.setiTotalDisplayRecords(floorManagementService.DataByCnt(floorVo));

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 매장 리스트 중복 체크
    @RequestMapping(value = "FloorManagement/FloorShopOverlap")
    @ResponseBody
    public AjaxResultVO checkFloorShopOverlap(FloorShopVo floorShopVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            // 층 단축표시명 중복 확인
            floorShopVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorShopVo.setCaseString("Floor_FloorShopOverlap");

            cnt = floorManagementService.DataByCnt(floorShopVo);
            result.setResultCode(0);

            if (cnt > 0) {
                result.setResultCode(1);
                messages.put("title", "이미 포함되어 있는 브랜드입니다.");
            } else {
                // messages.put("title", "중복 없음.");
            }

            result.setMessages(messages);
        } catch (Exception e) {
            result = WebUtil.ExceptionMessages(result, 2, e, logger);
        }

        return result;
    }

    // 층 브랜드(매장) 정보 등록
    @RequestMapping(value = "FloorManagement/FloorShopInsert")
    @ResponseBody
    public AjaxResultVO insertFloorShop(FloorShopVo floorShopVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            floorShopVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorShopVo.setCaseString("Floor_FloorShopCreate");

            cnt = floorManagementService.UpdateData(floorShopVo);
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

    // 층 브랜드(매장) 정보 수정
    @RequestMapping(value = "FloorManagement/FloorShopUpdate")
    @ResponseBody
    public AjaxResultVO updateFloorShop(@RequestParam(required = false) String jsonData, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        Gson gson = new Gson();
        List<FloorShopVo> updatelist = gson.fromJson(jsonData, new TypeToken<List<FloorShopVo>>() {
        }.getType());

        try {
            int updateCount = floorManagementService.updateListData("Floor_FloorShopListUpdate", (List<?>) updatelist);

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

    // 층 브랜드(매장) 정보 삭제
    @RequestMapping(value = "FloorManagement/FloorShopDelete")
    @ResponseBody
    public AjaxResultVO removeFloorShop(FloorShopVo floorShopVo, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();
        int cnt = 0;

        try {
            floorShopVo.setDomainIdx((String) session.getAttribute("domainIdx"));
            floorShopVo.setCaseString("Floor_FloorShopDelete");

            cnt = floorManagementService.DeleteDataByObjectParam(null, floorShopVo);
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