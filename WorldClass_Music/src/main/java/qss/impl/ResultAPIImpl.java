package qss.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import qss.dao.ResultAPIDao;
import qss.service.QssApi;
import qss.vo.log.KioskLogH;
import qss.vo.log.PlayLogH;

@Service("resultAPIService")
public class ResultAPIImpl extends EgovAbstractServiceImpl implements QssApi {
    @Resource(name = "resultAPIDao")
    private ResultAPIDao resultAPIDao;

    @Override
    public int CheckDeviceCode(String dvCode) throws Exception {
        // TODO Auto-generated method stub
        return resultAPIDao.CheckDeviceCode(dvCode);
    }

    @Override
    public List<Map<String, Object>> EventList(String dvCode) {
        // TODO Auto-generated method stub
        return resultAPIDao.EventList(dvCode);
    }

    @Override
    public List<Map<String, Object>> AdditionList(String dvCode) {
        // TODO Auto-generated method stub
        return resultAPIDao.AdditionList(dvCode);
    }

    @Override
    public Map<String, Object> GetPlayerInfo(String dvCode) {
        // TODO Auto-generated method stub
        return resultAPIDao.GetPlayerInfo(dvCode);
    }

    @Override
    public List<Map<String, Object>> FloorList(String dvCode) {
        // TODO Auto-generated method stub
        return resultAPIDao.FloorList(dvCode);
    }

    @Override
    @Transactional
    public int insertDeviceUserActionLog(List<KioskLogH> actionLogList) {
        return resultAPIDao.insertDeviceUserActionLog(actionLogList);
    }

    @Override
    @Transactional
    public int insertContentPlayLog(List<PlayLogH> playLogList) {
        return resultAPIDao.insertContentPlayLog(playLogList);
    }

	@Override
	public String GetMaxUpdateDt() {
		// TODO Auto-generated method stub
		return resultAPIDao.GetMaxUpdateDt();
	}

	@Override
	public List<Map<String, Object>> MarqueeList(String dvCode) {
		// TODO Auto-generated method stub
		return resultAPIDao.MarqueeList(dvCode);
	}
}