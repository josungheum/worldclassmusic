package qss.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface QssApi {
    public int CheckDeviceCode(String dvCode) throws Exception;

    public List<Map<String, Object>> EventList(String dvCode);

    public List<Map<String, Object>> AdditionList(String dvCode);

    public Map<String, Object> GetPlayerInfo(String dvCode);

    public List<Map<String, Object>> FloorList(String dvCode);
    
    public String GetMaxUpdateDt();

	public List<Map<String, Object>> MarqueeList(String dvCode);
}
