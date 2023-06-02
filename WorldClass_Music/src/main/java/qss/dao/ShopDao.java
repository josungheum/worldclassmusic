package qss.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.ShopVo;

@Repository("shopDao")
public class ShopDao extends EgovAbstractMapper {

	public List<?> SelectListData(ShopVo shopVo) {
		// TODO Auto-generated method stub
		return selectList(shopVo.getCaseString(), shopVo);
	}

	public CommonVo SelectData(ShopVo shopVo) {
		// TODO Auto-generated method stub
		return selectOne(shopVo.getCaseString(), shopVo);
	}

	@Transactional
	public int InsertData(ShopVo shopVo) {
		int cnt = insert(shopVo.getCaseString(), shopVo);
		BigInteger idx = shopVo.getNewIdx();
		if (cnt > 0 && shopVo.getDetailMenuXML() != null) {
			shopVo.setShopIdx(idx);
			insert("Shop_ImageListCreate", shopVo);
		}
		return cnt;
	}

	@Transactional
	public int UpdateData(ShopVo shopVo) {
		int cnt = 0;
		if (shopVo.getCaseString().equals("Shop_Update")) {
			cnt = update(shopVo.getCaseString(), shopVo);
			delete("Shop_ImageListDelete", shopVo);
			if (cnt > 0 && shopVo.getDetailMenuXML() != null) {
				insert("Shop_ImageListCreate", shopVo);
			}
		}
		else if(shopVo.getCaseString().equals("Shop_Delete")){
			cnt = update(shopVo.getCaseString(), shopVo);
			if (cnt > 0) {
				delete("Shop_ImageListDelete", shopVo);
			}
		}
		else {
			cnt = update(shopVo.getCaseString(), shopVo);
		}
		
		return cnt;
	}

	public int DataByCnt(CommonVo commonVo) {
		// TODO Auto-generated method stub
		return selectOne(commonVo.getCaseString(), commonVo);
	}
}