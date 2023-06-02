package qss.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.BrandPaymentTypeVo;
import qss.vo.BrandVo;
import qss.vo.CommonVo;
import qss.vo.FranchiseeVo;

@Repository("franchiseeManagementDao")
public class FranchiseeManagementDao extends EgovAbstractMapper {

	public List<?> SelectListData(FranchiseeVo franchiseeVo) {
		return (List<?>)selectList(franchiseeVo.getCaseString(), franchiseeVo);
	}

	public CommonVo SelectData(FranchiseeVo franchiseeVo) {
		FranchiseeVo franchisee = new FranchiseeVo();
		franchisee = selectOne(franchiseeVo.getCaseString(), franchiseeVo);
		if (franchiseeVo.getCaseString().equals("Franchisee_ViewFranchiseeDetail")){
			if(franchisee.getSalesStartTime() != null && franchisee.getSalesEndTime() != null) {
				franchisee.setSalesStartTime(franchisee.getSalesStartTime().substring(0,2)+":"+franchisee.getSalesStartTime().substring(2,4));
				franchisee.setSalesEndTime(franchisee.getSalesEndTime().substring(0,2)+":"+franchisee.getSalesEndTime().substring(2,4));
			}
		}
		return franchisee;
	}

	public List<String> StringList(FranchiseeVo franchiseeVo) {
		return null;
	}

	public int InsertData(FranchiseeVo franchiseeVo) throws Exception{
		if (franchiseeVo.getCaseString().equals("Franchisee_InsertFranchiseeGroup")) {
			if(franchiseeVo.getGroupIdx().equals("0")) {
				franchiseeVo.setGroupIdx("");
				franchiseeVo.setGroupDepth(0);
				return insert(franchiseeVo.getCaseString(), franchiseeVo);
			}
			else {
				int depth = selectOne("Franchisee_GetGroupDepth", franchiseeVo);
				franchiseeVo.setGroupDepth(depth);
				return insert(franchiseeVo.getCaseString(), franchiseeVo);
			}
		   }
		else {
			return insert(franchiseeVo.getCaseString(), franchiseeVo);
		}
	}

	public int UpdateData(FranchiseeVo franchiseeVo) throws Exception{
	   int cnt = 0;
	   //사이트 상세 수정시 :제거
	   if (franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchiseeDetail")) {
		   franchiseeVo.setSalesStartTime(franchiseeVo.getSalesStartTime().replace(":",""));
		   franchiseeVo.setSalesEndTime(franchiseeVo.getSalesEndTime().replace(":",""));
	   }

	   if(franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchiseeGroup") && franchiseeVo.getParentGroupIdx().equals("0")) {
           franchiseeVo.setParentGroupIdx("");
       }

	   cnt = update(franchiseeVo.getCaseString(), franchiseeVo);

	   //사이트 스케줄 버전
	   if (franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchisee")
			   || franchiseeVo.getCaseString().equals("Franchisee_DeleteFranchisee")
			   || franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchiseeDetail")
			   || franchiseeVo.getCaseString().equals("Franchisee_ActiveFranchisee")

	   ) {
		   franchiseeVo.setScheduleType("SCH0005");
		   insert("Franchisee_InsertFrancScheduleVersion", franchiseeVo);

		   //사이트 유형 수정
		   if (franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchisee"))
				   insertUpdateFranc(franchiseeVo);
	   }

	   //서비스 기준 사이트 유형 저장
	   if(franchiseeVo.getCaseString().equals("Franchisee_InsertFranchisee")) {
		   insertBrandUpdateFranc(franchiseeVo);
	   }

	   //단말 스케줄 버전
	   if (franchiseeVo.getCaseString().equals("Franchisee_UpdateKiosk")
			   || franchiseeVo.getCaseString().equals("Franchisee_DeleteKiosk")
			   || franchiseeVo.getCaseString().equals("Franchisee_InsertKiosk")
			   || franchiseeVo.getCaseString().equals("Franchisee_ActiveKiosk")
	   ) {

		   //저장
		   if(franchiseeVo.getCaseString().equals("Franchisee_InsertKiosk"))
		   {
			   BigInteger[] checkboxArr = {franchiseeVo.getReturnIdx()};
			   franchiseeVo.setCheckboxArr(checkboxArr);
		   }
		   //수정
		   else if(franchiseeVo.getCaseString().equals("Franchisee_UpdateKiosk")
				   || franchiseeVo.getCaseString().equals("Franchisee_ActiveKiosk"))
		   {
			   BigInteger[] checkboxArr = {franchiseeVo.getDeviceIdx()};
			   franchiseeVo.setCheckboxArr(checkboxArr);
		   }
		   //서비스
		   BrandVo brandVo = new BrandVo();
		   brandVo.setRegUser(franchiseeVo.getRegUser());
		   brandVo.setDomainIdx(franchiseeVo.getDomainIdx());
		   brandVo.setBrandIdx(franchiseeVo.getBrandIdx());
		   brandVo.setScheduleType("SCH0004");
		   insert("Brand_InsertScheduleVersion", brandVo);
		   //사이트
		   franchiseeVo.setScheduleType("SCH0005");
		   insert("Franchisee_InsertFrancScheduleVersion", franchiseeVo);
		   //단말
		   franchiseeVo.setScheduleType("SCH0006");
		   insert("Franchisee_InsertDeviceScheduleVersion", franchiseeVo);
		   
		   //주문화면 변경
		   franchiseeVo.setScheduleType("SCH0007");
		   insert("Franchisee_InsertDeviceScheduleVersion", franchiseeVo);
		   
		   //상품 변경
		   franchiseeVo.setScheduleType("SCH0008");
		   insert("Franchisee_InsertDeviceScheduleVersion", franchiseeVo);
		   
		   //스케줄 변경
		   franchiseeVo.setScheduleType("SCH0002");
		   insert("Franchisee_InsertDeviceScheduleVersion", franchiseeVo);
		   
		   
		   //공지사항 변경
		   franchiseeVo.setScheduleType("SCH0009");
		   insert("Franchisee_InsertDeviceScheduleVersion", franchiseeVo);
		   
		   

	   }

		return cnt;
	}

	public int DeleteData(FranchiseeVo franchiseeVo){
		return update(franchiseeVo.getCaseString(), franchiseeVo);
	}

	public int DataByCnt(CommonVo commonVo) {
		return selectOne(commonVo.getCaseString(), commonVo);
	}

	public String InsertReturnKeyData(FranchiseeVo franchiseeVo) {
		if (franchiseeVo.getCaseString().equals("Franchisee_UpdateFranchisee")) {
			insertUpdateFranc(franchiseeVo);
		}
		//기본 정보 저장
		insert(franchiseeVo.getCaseString(), franchiseeVo);
		String francIdx = franchiseeVo.getReturnFrancIdx();

		return francIdx;
	}

	private void insertUpdateFranc(FranchiseeVo franchiseeVo) {
		BrandPaymentTypeVo brandPaymentTypeVo = new BrandPaymentTypeVo();
		brandPaymentTypeVo.setDomainIdx(franchiseeVo.getDomainIdx());
		brandPaymentTypeVo.setBrandIdx(franchiseeVo.getBrandIdx());
		brandPaymentTypeVo.setFrancIdx(franchiseeVo.getFrancIdx());
		brandPaymentTypeVo.setRegUser(franchiseeVo.getRegUser());
		brandPaymentTypeVo.setBrandOnlyYn("N");
		delete("Franchisee_DeleteFrancPaymentType", brandPaymentTypeVo);

		//결제 종류
		String[] francPaymentType01 = franchiseeVo.getFrancPaymentType01().split(",");
		for(int i = 0; i < francPaymentType01.length; i++)
		{
			if(!francPaymentType01[i].equals(""))
			{
				brandPaymentTypeVo.setPayType(francPaymentType01[i]);
				brandPaymentTypeVo.setPayTypeGroup("PAY0000");
				insert("Franchisee_InsertFrancPaymentType", brandPaymentTypeVo);
			}
		}

		//포인트 종류
		String[] francPaymentType02 = franchiseeVo.getFrancPaymentType02().split(",");
		for(int i = 0; i < francPaymentType02.length; i++)
		{
			if(!francPaymentType02[i].equals(""))
			{
				brandPaymentTypeVo.setPayType(francPaymentType02[i]);
				brandPaymentTypeVo.setPayTypeGroup("PNT0000");
				insert("Franchisee_InsertFrancPaymentType", brandPaymentTypeVo);
			}
		}
	}

	private void insertBrandUpdateFranc(FranchiseeVo franchiseeVo) {
		BrandPaymentTypeVo brandPaymentTypeVo = new BrandPaymentTypeVo();
		brandPaymentTypeVo.setDomainIdx(franchiseeVo.getDomainIdx());
		brandPaymentTypeVo.setBrandIdx(franchiseeVo.getBrandIdx());

		List<BrandPaymentTypeVo> brandPaymentType = selectList("Brand_ListBrandPaymentType", brandPaymentTypeVo);
		for(int i = 0; i < brandPaymentType.size(); i++)
		{
			brandPaymentType.get(i).setFrancIdx(franchiseeVo.getReturnFrancIdx());
			brandPaymentType.get(i).setBrandOnlyYn("N");
			brandPaymentType.get(i).setRegUser(franchiseeVo.getRegUser());
			insert("Franchisee_InsertFrancPaymentType", brandPaymentType.get(i));
		}
	}

}