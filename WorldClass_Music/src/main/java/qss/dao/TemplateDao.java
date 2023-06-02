package qss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.util.WebUtil;
import qss.vo.CommonVo;
import qss.vo.TemplateVo;

@Repository("templateDao")
public class TemplateDao extends EgovAbstractMapper {

    public int InsertData(TemplateVo templateVo) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int num = 0;

        if (templateVo.getCaseString().equals("Template_Create")) {
            params.put("domainIdx", templateVo.getDomainIdx());
            params.put("brandIdx", templateVo.getBrandIdx());
            params.put("francIdx", templateVo.getFrancIdx());
            params.put("templateName", templateVo.getTemplateName());
            params.put("rowCnt", templateVo.getRowCnt());
            params.put("colCnt", templateVo.getColCnt());
            params.put("resolutionX", templateVo.getResolutionX());
            params.put("resolutionY", templateVo.getResolutionY());
            params.put("playTime", templateVo.getPlayTime());
            params.put("delYn", "N");
            params.put("regUser", templateVo.getRegUser());
            params.put("modUser", templateVo.getModUser());
            params.put("templateIdx", ""); // 리턴받을 키

            insert("Template_InsertTemplate", params);
            String templateIdx = params.get("templateIdx").toString();

            //템플릿 레이어 저장
            insertLayer(templateVo, templateIdx);
        }
        num = 1;

        return num;
    }

    public List<?> SelectListData(TemplateVo templateVo) {
        List<?> list = null;

        if (templateVo.getCaseString().equals("Template_TemplateList")) {
            list = selectList(templateVo.getCaseString(), templateVo);
        } else if (templateVo.getCaseString().equals("Template_TemplateGet")) {
            list = selectList(templateVo.getCaseString(), templateVo);
        }

        return list;
    }

    public int UpdateData(TemplateVo templateVo) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int num = 1;

        if (templateVo.getCaseString().equals("Template_Update")) {
            String templateIdx = templateVo.getTemplateIdx().toString();
            params.put("domainIdx", templateVo.getDomainIdx());
            params.put("brandIdx", templateVo.getBrandIdx());
            params.put("francIdx", templateVo.getFrancIdx());
            params.put("templateIdx", templateIdx);
            params.put("templateName", templateVo.getTemplateName());
            params.put("rowCnt", templateVo.getRowCnt());
            params.put("colCnt", templateVo.getColCnt());
            params.put("resolutionX", templateVo.getResolutionX());
            params.put("resolutionY", templateVo.getResolutionY());
            params.put("playTime", templateVo.getPlayTime());
            params.put("delYn", "N");
            params.put("modUser", templateVo.getModUser());

            try {
                update("Template_UpdateTemplate", params);

                // 해당 템플릿(templateIdx)의 하위 정보인 템플릿 레이어 삭제
                //delete("Template_DeleteContent", params);
                delete("Template_DeleteLayer", params);

                // 신규로 INSERT
                insertLayer(templateVo, templateIdx);
            } catch (Exception e) {
                num = 2;
                logger.error(e.getMessage());
            }
        }

        return num;
    }

    public int DeleteDataByObjectParam(TemplateVo templateVo) {
        // TODO Auto-generated method stub
        Map<String, Object> params = new HashMap<String, Object>();
        int num = 1;

        params.put("domainIdx", templateVo.getDomainIdx());
        params.put("brandIdx", templateVo.getBrandIdx());
        params.put("francIdx", templateVo.getFrancIdx());
        params.put("arrIndex", templateVo.getCheckboxArr());
        params.put("modUser", templateVo.getModUser());

        try {
            update("Template_Delete", params);
        } catch (Exception e) {
            num = 2;
            logger.error(e.getMessage());
        }

        return num;
    }

    private void insertLayer(TemplateVo templateVo, String templateIdx) throws Exception {
        ArrayList<String> setName = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "templatelayerName");
        ArrayList<String> setRow = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "startRow");
        ArrayList<String> setCol = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "startCol");
        ArrayList<String> setRspan = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "rowSpan");
        ArrayList<String> setCspan = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "colSpan");
        ArrayList<String> setPlayTime = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "playTime");
        ArrayList<String> setOrder = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "orderSeq");
        //ArrayList<String> setContentXml = WebUtil.ParsingXML(templateVo.getLayerXml(), "Layer", "ContentXml");

        for (int j = 0; j < setName.size(); j++) {
            HashMap<String, Object> setLayerMap = new HashMap<String, Object>();
            setLayerMap.put("domainIdx", templateVo.getDomainIdx());
            setLayerMap.put("brandIdx", templateVo.getBrandIdx());
            setLayerMap.put("francIdx", templateVo.getFrancIdx());
            setLayerMap.put("templateIdx", templateIdx);
            setLayerMap.put("templatelayerName", setName.get(j));
            setLayerMap.put("startRow", setRow.get(j));
            setLayerMap.put("startCol", setCol.get(j));
            setLayerMap.put("rowSpan", setRspan.get(j));
            setLayerMap.put("colSpan", setCspan.get(j));
            setLayerMap.put("playTime", setPlayTime.get(j));
            setLayerMap.put("orderSeq", setOrder.get(j));
            setLayerMap.put("regUser", templateVo.getRegUser());
            setLayerMap.put("modUser", templateVo.getModUser());
            setLayerMap.put("templateLayerIdx", ""); // 리턴받을 키

            insert("Template_InsertTemplateLayer", setLayerMap);
            /*
             * ArrayList<String> setFileIdx = WebUtil.ParsingXML(setContentXml.get(j), "Content", "fileContentIdx"); ArrayList<String> setFilePlayTime =
             * WebUtil.ParsingXML(setContentXml.get(j), "Content", "playTime"); ArrayList<String> setFileOrder = WebUtil.ParsingXML(setContentXml.get(j),
             * "Content", "orderSeq"); String templateLayerIdx = setLayerMap.get("templateLayerIdx").toString(); for (int i = 0; i < setFileIdx.size(); i++) {
             * HashMap<String, Object> setContentMap = new HashMap<String, Object>(); setContentMap.put("domainIdx", templateVo.getDomainIdx());
             * setContentMap.put("brandIdx", templateVo.getBrandIdx()); setContentMap.put("francIdx", templateVo.getFrancIdx());
             * setContentMap.put("templateIdx", templateIdx); setContentMap.put("templateLayerIdx", templateLayerIdx); setContentMap.put("fileContentIdx",
             * setFileIdx.get(i)); setContentMap.put("playTime", setFilePlayTime.get(i)); setContentMap.put("orderSeq", setFileOrder.get(i));
             * setContentMap.put("regUser", templateVo.getRegUser()); setContentMap.put("modUser", templateVo.getModUser()); insert("Screen_InsertLayerContent",
             * setContentMap); }
             */
        }

        // 스케줄버전 처리
        //templateVo.setScheduleType("SCH0002");
        //templateVo.setModReason("스크린번경");
        //insert("Screen_InsertScheduleVersion", templateVo);
    }

    public int DataByCnt(CommonVo commonVo) {
        return selectOne(commonVo.getCaseString(), commonVo);
    }
}