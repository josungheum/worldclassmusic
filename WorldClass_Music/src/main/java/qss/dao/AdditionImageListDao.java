package qss.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.AdditionImageListVo;
import qss.vo.CommonVo;

@Repository("additionImageListDao")
public class AdditionImageListDao extends EgovAbstractMapper {
	
	public List<?> SelectListData(AdditionImageListVo additionImageListVo) {
		List<?> list = null;
		if (additionImageListVo.getCaseString().equals("AdditionImageList_List")) {
			List<AdditionImageListVo> addList = new ArrayList<AdditionImageListVo>();
			AdditionImageListVo addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0001");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);
			
			addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0002");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);
			
			addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0003");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);
			
			addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0004");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);

			addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0005");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);

			addition = new AdditionImageListVo();
			additionImageListVo.setAdditionType("ADD0006");
			addition.setAdditionList(selectList(additionImageListVo.getCaseString(), additionImageListVo));
			addList.add(addition);
			
			list = addList;
		}
		
		return list;
	}

	public CommonVo SelectData(AdditionImageListVo additionImageListVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public int InsertData(AdditionImageListVo additionImageListVo) {
		// TODO Auto-generated method stub
		String[] arr = additionImageListVo.getAdditionType().split(",");
		delete("AdditionImageList_Delete", arr[0]);
		delete("AdditionImageList_Delete", arr[1]);
		int cnt = insert(additionImageListVo.getCaseString(), additionImageListVo);
		
		return cnt;
	}

	@Transactional
	public int UpdateData(AdditionImageListVo additionImageListVo) {
		// TODO Auto-generated method stub
		int cnt = 1;
		String[] arr = additionImageListVo.getAdditionType().split(",");
		delete("AdditionImageList_Delete", arr[0]);
		delete("AdditionImageList_Delete", arr[1]);
		
		return cnt;
	}
}