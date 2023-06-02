package qss.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import qss.vo.CommonVo;
import qss.vo.NoticeVo;

@Repository("noticeManagementDao")
public class NoticeManagementDao extends EgovAbstractMapper {

	public List<?> SelectListData(NoticeVo noticeVo) {
		List<?> list = null;
		list = selectList(noticeVo.getCaseString(), noticeVo);
		return list;
	}

	public int UpdateData(NoticeVo noticeVo) {
		int cnt = update(noticeVo.getCaseString(), noticeVo);
		//공지사항 스케줄 버전
	    if (noticeVo.getCaseString().equals("Notice_UpdateNotice")
			   || noticeVo.getCaseString().equals("Notice_DeleteNotice")
			   || noticeVo.getCaseString().equals("Notice_InsertNotice")
			   || noticeVo.getCaseString().equals("Notice_ActiveNotice")
	    ) {
	    	noticeVo.setScheduleType("SCH0009");
		   //저장
		   if(noticeVo.getCaseString().equals("Notice_InsertNotice")) 
		   {
			   BigInteger[] checkboxArr = {noticeVo.getReturnIdx()};
			   noticeVo.setCheckboxArr(checkboxArr);
		   }
		   //수정
		   else if(noticeVo.getCaseString().equals("Notice_UpdateNotice")
				   || noticeVo.getCaseString().equals("Notice_ActiveNotice")) 
		   {
			   BigInteger[] checkboxArr = {noticeVo.getNoticeIdx()};
			   noticeVo.setCheckboxArr(checkboxArr);
		   }
		   insert("Notice_InsertScheduleVersion", noticeVo);
		   
	    }
		return cnt;
	}

	public int DataByCnt(NoticeVo noticeVo) {
		return selectOne(noticeVo.getCaseString(), noticeVo);
	}

	public CommonVo SelectData(NoticeVo noticeVo) {
		return selectOne(noticeVo.getCaseString(), noticeVo);
	}

	public int DeleteData(NoticeVo noticeVo) {
		return update(noticeVo.getCaseString(), noticeVo);
	}

	
}