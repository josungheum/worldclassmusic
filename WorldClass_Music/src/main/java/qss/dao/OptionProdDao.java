package qss.dao;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.OptionProdVo;

@Repository("optionProdDao")
public class OptionProdDao extends EgovAbstractMapper {

	public List<?> SelectListData(OptionProdVo optionProdVo) {
		List<?> list = null;
		list = selectList(optionProdVo.getCaseString(), optionProdVo);
		return list;
	}

	public int UpdateData(OptionProdVo optionProdVo) {
		int cnt = update(optionProdVo.getCaseString(), optionProdVo);
		//상품 스케줄 버전
	    if (optionProdVo.getCaseString().equals("OptionProd_UpdateOptionProd")
			   || optionProdVo.getCaseString().equals("OptionProd_DeleteOptionProd")
			   || optionProdVo.getCaseString().equals("OptionProd_InsertOptionProd")
			   || optionProdVo.getCaseString().equals("OptionProd_ActiveOptionProd")
	    ){
	    	optionProdVo.setScheduleType("SCH0011");
		   //저장
		   if(optionProdVo.getCaseString().equals("OptionProd_InsertOptionProd"))
		   {
			   BigInteger[] checkboxArr = {optionProdVo.getReturnIdx()};
			   optionProdVo.setCheckboxArr(checkboxArr);
			   insert("OptionProd_InsertScheduleVersion", optionProdVo);
		   }
		   //수정
		   else if(optionProdVo.getCaseString().equals("OptionProd_UpdateOptionProd")
				   || optionProdVo.getCaseString().equals("OptionProd_ActiveOptionProd")
				   || optionProdVo.getCaseString().equals("OptionProd_DeleteOptionProd")
		   ){
			   BigInteger[] checkboxArr = {optionProdVo.getOptionProdIdx()};
			   if(!optionProdVo.getCaseString().equals("OptionProd_DeleteOptionProd")) {
				   optionProdVo.setCheckboxArr(checkboxArr);
			   }
			   insert("OptionProd_InsertScheduleVersion", optionProdVo);
		   }
	    }

		return cnt;
	}

	public int DataByCnt(OptionProdVo optionProdVo) {
		return selectOne(optionProdVo.getCaseString(), optionProdVo);
	}

	public OptionProdVo SelectData(OptionProdVo optionProdVo) {
		return selectOne(optionProdVo.getCaseString(), optionProdVo);
	}

	public int DeleteDataByObjectParam(OptionProdVo commonVo) {
		return 0;
	}


}