package qss.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import qss.impl.EventImpl;
import qss.service.Qss;
import qss.vo.EventVo;

@Component
public class BatchScheduler {
	
	@Resource(name="eventService")
	Qss eventService = new EventImpl();
	
	private static Logger logger = LoggerFactory.getLogger(BatchScheduler.class);
	
	/*
	 * 배치 스케줄러
	 * 매일 0시 0분 0초에 실행
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void EventBatch() throws Exception {
		EventVo eventVo = new EventVo();
		
		try {
			logger.info("Evnet Batch Start");
			eventVo.setCaseString("Event_BatchDelete");
			eventService.UpdateData(eventVo);
			
			logger.info("Evnet Batch Success");
		} catch (Exception e) {
			logger.error("Batch Exception : {}", e.getMessage());
		}
	}
}
