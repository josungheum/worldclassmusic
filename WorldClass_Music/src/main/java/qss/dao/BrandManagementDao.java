package qss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandPaymentTypeVo;
import qss.vo.BrandVo;
import qss.vo.CommonVo;

@Repository("brandManagementDao")
public class BrandManagementDao extends EgovAbstractMapper {

	public List<?> SelectListData(BrandVo brandVo) {
		return (List<?>)selectList(brandVo.getCaseString(), brandVo);
	}

	public CommonVo SelectData(BrandVo brandVo) {
		return selectOne(brandVo.getCaseString(), brandVo);
	}

	public List<String> StringList(BrandVo brandVo) {
		return null;
	}

	public int InsertData(BrandVo brandVo) {
		 int cnt = update(brandVo.getCaseString(), brandVo);
		return cnt;
	}

	public int UpdateData(BrandVo brandVo) {

		//브랜드 수정/삭제
		if (brandVo.getCaseString().equals("Brand_UpdateBrand")
				|| brandVo.getCaseString().equals("Brand_DeleteBrand")
				|| brandVo.getCaseString().equals("Brand_ActiveBrand")
		)
		{
			//스케줄 저장
			brandVo.setScheduleType("SCH0004");
			insert("Brand_InsertScheduleVersion", brandVo);
			if(brandVo.getCaseString().equals("Brand_UpdateBrand"))
				insertUpdateBrandType(brandVo);
		}

		return update(brandVo.getCaseString(), brandVo);
	}

	public int DeleteData(BrandVo brandVo) {
		return update(brandVo.getCaseString(), brandVo);
	}

	public int DataByCnt(BrandVo brandVo) {
		return selectOne(brandVo.getCaseString(), brandVo);
	}

	public String InsertReturnKeyData(BrandVo brandVo) {
		return null;
	}

	private void insertUpdateBrandType(BrandVo brandVo) {
		BrandPaymentTypeVo brandPaymentTypeVo = new BrandPaymentTypeVo();

		brandPaymentTypeVo.setDomainIdx(brandVo.getDomainIdx());
		brandPaymentTypeVo.setBrandIdx(brandVo.getBrandIdx());
		brandPaymentTypeVo.setFrancIdx(brandVo.getFrancIdx());
		brandPaymentTypeVo.setRegUser(brandVo.getRegUser());
		brandPaymentTypeVo.setBrandOnlyYn("Y");

		BrandDeviceTypeVo brandDeviceTypeVo = new BrandDeviceTypeVo();
		brandDeviceTypeVo.setDomainIdx(brandVo.getDomainIdx());
		brandDeviceTypeVo.setBrandIdx(brandVo.getBrandIdx());
		brandDeviceTypeVo.setFrancIdx(brandVo.getFrancIdx());
		brandDeviceTypeVo.setRegUser(brandVo.getRegUser());

		//delete("Brand_DeleteBrandPaymentType", brandPaymentTypeVo);

		delete("Brand_DeleteBrandDeviceType", brandDeviceTypeVo);

		//결제 종류
		/*String[] brandPaymentType01 = brandVo.getBrandPaymentType01().split(",");
		for(int i = 0; i < brandPaymentType01.length; i++)
		{
			if(!brandPaymentType01[i].equals("")) {
				brandPaymentTypeVo.setPayTypeGroup("PAY0000");
				brandPaymentTypeVo.setPayType(brandPaymentType01[i]);
				insert("Brand_InsertBrandPaymentType", brandPaymentTypeVo);
			}


		}*/

		//포인트 종류
		/*String[] brandPaymentType02 = brandVo.getBrandPaymentType02().split(",");
		for(int i = 0; i < brandPaymentType02.length; i++)
		{
			if(!brandPaymentType02[i].equals("")) {
				brandPaymentTypeVo.setPayTypeGroup("PNT0000");
				brandPaymentTypeVo.setPayType(brandPaymentType02[i]);
				insert("Brand_InsertBrandPaymentType", brandPaymentTypeVo);
			}

		}*/


		//디바이스 종류
		if(brandVo.getBrandDeviceType() != null) {
			String[] brandDeviceType = brandVo.getBrandDeviceType().split(",");
			for(int i = 0; i < brandDeviceType.length; i++){
				if(!brandDeviceType[i].equals("")) {
					brandDeviceTypeVo.setDeviceType(brandDeviceType[i]);
					insert("Brand_InsertBrandDeviceType", brandDeviceTypeVo);
				}
			}
		}
	}
}