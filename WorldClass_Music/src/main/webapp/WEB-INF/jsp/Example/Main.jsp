<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/jsp/Common/metaCommon.jsp" />
<div class="box box-widget">
	<div class="box-header with-border">
		<div class="user-block">
			<i class="fa fa-fw fa-gear"></i>예제 페이지
		</div>
	</div>
	
	<div class="box-body">
		<div style="margin-top:4px;  overflow-x: hidden; overflow-y: auto;" class="proton-demo" id="targetTreeList" >
		</div>
	</div>

</div>

<div class="modal-footer">
	<a href="" onclick="Save()">단말기 트리 테스트</a>
</div>


<script type="text/javaScript">
	$(document).ready(function () {
	    var param = {
		   	serchKey : $('#treeSearch').val()
		}
		      
		treeSearchList(0,'<c:url value="/Example/SearchKioskTree"/>',param);
	});
	
	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e){}
	
	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){}
	
	//트리가 처음 그려지는 경우 콜백
	function callbackFirstSelect(jsonData){}
	
	function Save() {
		var arrTarget = [];
		
		$.each($('#targetTreeList').jstree('get_selected', true), function () 
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				arrTarget.push(this.li_attr);			
	    });
		
		alert(arrTarget.length+"개의 디바이스가 선택되었습니다.");
	}
	
	
	
</script>