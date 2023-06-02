package qss.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import qss.impl.ResultAPIImpl;
import qss.service.QssApi;
import qss.vo.ResultAPIVo;
import qss.vo.log.KioskLogH;
import qss.vo.log.PlayLogH;

@RestController
@RequestMapping(value = "/RestAPIController")
public class LogApi {

    @Resource(name = "resultAPIService")
    QssApi resultAPIservice = new ResultAPIImpl();

    private static final Log logger = LogFactory.getLog(LogApi.class);

    /**
     * 단말 별 사용자 action 로그 저장
     */
    @RequestMapping(value = "/api/users/action/log", method = RequestMethod.POST)
    @ResponseBody
    public ResultAPIVo userActionLog(HttpServletRequest request, @RequestParam("action_log_file") MultipartFile multipartFile) throws Exception {
        ResultAPIVo result = new ResultAPIVo();
        Map<String, Object> message = new HashMap<String, Object>();

        List<KioskLogH> actionLogList = new ArrayList<KioskLogH>();

        try {
            if (!multipartFile.isEmpty()) {
                byte[] fileData = multipartFile.getBytes();
                String convertedStr = new String(fileData, "UTF-8");

                // 개행을 기준으로 문자열 parsing
                String[] lines = convertedStr.split("\\r\\n|\\n\\r|\\r|\\n");

                for (String log : lines) {
                    // ',' 를 기준으로 문자열 parsing
                    String[] logData = log.split(",");
                    KioskLogH actionLog = new KioskLogH();

                    for (int i = 0; i < logData.length; i++) {
                        if (i == 0) {
                            //actionLog.setDeviceIdx(new BigInteger(logData[0]));
                            actionLog.setDeviceCode(logData[0]);
                        } else if (i == 1) {
                            actionLog.setFloorIdx(new BigInteger(logData[1]));
                        } else if (i == 2) {
                            actionLog.setActionType(logData[2]);
                        } else if (i == 3) {
                            actionLog.setActionName(logData[3]);
                        } else if (i == 4) {
                            actionLog.setPlaydate(logData[4]);
                        } else if (i == 5) {
                            actionLog.setStarttime(logData[5]);
                        } else if (i == 6) {
                            actionLog.setDayofweek(logData[6]);
                        }
                    }

                    actionLogList.add(actionLog);
                }

                // 로그 저장
                int resultCount = resultAPIservice.insertDeviceUserActionLog(actionLogList);
                result.setResultCode(0);
                message.put("title", "success");
                message.put("detail", String.valueOf(resultCount));
            } else {
                result.setResultCode(1);
                message.put("title", "no logs");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            message.put("title", "exception");
            message.put("detail", e.getMessage());
        }

        result.setMessages(message);

        return result;
    }

    /**
     * 컨텐트 재생시간 로그 저장
     */
    @RequestMapping(value = "/api/content/play/log", method = RequestMethod.POST)
    @ResponseBody
    public ResultAPIVo contentPlayLog(HttpServletRequest request, @RequestParam("play_log_file") MultipartFile multipartFile) throws Exception {
        ResultAPIVo result = new ResultAPIVo();
        Map<String, Object> message = new HashMap<String, Object>();

        List<PlayLogH> playLogList = new ArrayList<PlayLogH>();

        try {
            if (!multipartFile.isEmpty()) {
                byte[] fileData = multipartFile.getBytes();
                String convertedStr = new String(fileData);

                // 개행을 기준으로 문자열 parsing
                String[] lines = convertedStr.split("\\r\\n|\\n\\r|\\r|\\n");

                for (String log : lines) {
                    // ',' 를 기준으로 문자열 parsing
                    String[] logData = log.split(",");
                    PlayLogH playLog = new PlayLogH();

                    for (int i = 0; i < logData.length; i++) {
                        if (i == 0) {
                            //playLog.setDeviceIdx(new BigInteger(logData[0])); // 단말일련번호
                            playLog.setDeviceCode(logData[0]); // 단말코드
                        } else if (i == 1) {
                            playLog.setMainScheduleIdx(new BigInteger(logData[1])); // 메인스케쥴일련번호
                        } else if (i == 2) {
                            playLog.setScreenIdx(new BigInteger(logData[2])); // 스크린일련번호
                        } else if (i == 3) {
                            playLog.setFileContentIdx(new BigInteger(logData[3])); // 파일컨텐트일련번호
                        } else if (i == 4) {
                            playLog.setPlPlaydate(logData[4]); // 재생 일자
                        } else if (i == 5) {
                            playLog.setPlStarttime(logData[5]); // 재생 시작 시간
                        } else if (i == 6) {
                            playLog.setPlEndtime(logData[6]); // 재생 종료 시간
                        } else if (i == 7) {
                            playLog.setPlPlaytime(0);
                            playLog.setPlPlaymillitime(0);

                            Integer playtime = Integer.parseInt(logData[7]);
                            if (playtime != null && playtime != 0) {
                                int second = playtime / 1000;
                                //2020417 밀리세컨드만 아니라 전체시간으로 저장
                                //int millisecond = Integer.parseInt(StringUtils.substring(logData[7], -3));

                                playLog.setPlPlaytime(second); // 재생 시간 (초)
                                playLog.setPlPlaymillitime(playtime); // 재생 시간 (밀리세컨드까지)
                            }
                        }
                    }

                    playLogList.add(playLog);
                }

                // 로그 저장
                int resultCount = resultAPIservice.insertContentPlayLog(playLogList);
                result.setResultCode(0);
                message.put("title", "success");
                message.put("detail", String.valueOf(resultCount));
            } else {
                result.setResultCode(1);
                message.put("title", "no logs");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            message.put("title", "exception");
            message.put("detail", e.getMessage());
        }

        result.setMessages(message);
        return result;
    }
}
