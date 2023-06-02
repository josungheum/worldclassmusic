package qss.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.KioskClientVo;

@Repository("kioskClientDao")
public class KioskClientDao extends EgovAbstractMapper {

	public List<KioskClientVo> SelectListData(KioskClientVo kioskClientVo) {
		List<KioskClientVo> list = null;
		list = selectList(kioskClientVo.getCaseString(), kioskClientVo);
		return list;
	}
	
	public KioskClientVo SelectData(KioskClientVo kioskClientVo) {
		return selectOne(kioskClientVo.getCaseString(), kioskClientVo);
		
	}

	public int InsertData(KioskClientVo kioskClientVo) {
		return insert(kioskClientVo.getCaseString(), kioskClientVo);
	}

	public int UpdateData(KioskClientVo kioskClientVo) {
		int cnt = 0;
		
		if(kioskClientVo.getCaseString().equals("KioskClient_InsertDistribute")) 
		{
			//삭제
			cnt = update("KioskClient_DeleteDistribute", kioskClientVo);
			//단건씩 저장
			if(kioskClientVo.getCheckboxArr() != null) {
				for(int i = 0; i < kioskClientVo.getCheckboxArr().length; i++) {
					String [] checkboxArr = kioskClientVo.getCheckboxArr()[i].split("_");
					if(checkboxArr.length > 3) {
						kioskClientVo.setDomainIdx(checkboxArr[0]);
						kioskClientVo.setBrandIdx(checkboxArr[1]);
						kioskClientVo.setFrancIdx(checkboxArr[3]);
						kioskClientVo.setDeviceIdx(new BigInteger(checkboxArr[4].trim()));
						cnt = update(kioskClientVo.getCaseString(), kioskClientVo);
					}
					
				}
			}
			
			//배포 건수 정보 업데이트
			kioskClientVo.setDistCount(kioskClientVo.getCheckboxArr().length);
			cnt = update("KioskClient_CountUpdateKioskClient", kioskClientVo);
		}
		else 
		{
			cnt = update(kioskClientVo.getCaseString(), kioskClientVo);
		}
		
		
		return cnt;
	}

	public int DeleteData(KioskClientVo kioskClientVo) {
		return update(kioskClientVo.getCaseString(), kioskClientVo);
	}

	public int DataByCnt(KioskClientVo kioskClientVo) {
		return selectOne(kioskClientVo.getCaseString(), kioskClientVo);
	}
}