<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget" id="detail">
	<div class="box-header">
		<div class="user-block under-line">사이트 속성</div>
	</div>
	<!-- Tap Control -->
	<div class="box-body">
		<div class="row nav-tabs" id="tabLab" style="margin: 5px 0 30px 4px;">
			<ul class="nav nav-pills" id="tabList"></ul>
		</div>
		<div id="contentView"></div>
	</div>
</div>

<div class="box box-widget" id="francList">
	<div class="box-header">
		<div class="user-block under-line">사이트 속성</div>
	</div>

	<!-- Tap Control -->
	<div class="box-body">
		<table id="francListTable" class="table table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>그룹 명</th>
					<th>사이트 명</th>
					<th>사이트 코드</th>
					<th>사이트 연락처</th>
					<th>단말 개수</th>
<!-- 					<th>24시 여부</th> -->
					<th>영업 시작 시간</th>
					<th>영업 종료 시간</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		if('${sessionScope.adminType}' == 'ADM0003'){
 			// 로그인 페이지로 뒤로가기 방지
 			history.pushState(null, null, location.href);
 			window.onpopstate = function(event) {

 				if(document.referrer){
 					var url = document.referrer.split('//');
 					var host = url[1].substr(0, url[1].indexOf('/'));
 					var hostUrl = url[1].split('/');

 					if(hostUrl[2] == null || hostUrl[2] == ""){
 						history.go(1);
 					}else{
 						document.location.replace(document.referrer);
 					}
 				}else{
 					history.go(1);
 				}
 			};
 		}

	    treeSearchList(0, false, null, 'STO02');
	    treeBtnHeight("580");

		$(window).resize(function(){
			treeBtnHeight('580');
	    });

	});

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO02');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data)
	{
		//상세
        if($("#francIdx").val() != "0")
        	tabView(0);
        //리스트
        else
            tabView();
	}

	//트리가 처음 그려지는 경우 콜백
	function callbackFirstSelect(jsonData)
	{

		tabView();
	}

	function tabView(val) {
		var domainId = 1;
		var nameArr = new Array();
		var urlArr = new Array();

		if(val != null){
			$('#francList').hide();
			$('#tabLab').show();
			$('#detail').show();

			var domainEmptyCheck = $("#domainIdx").val() == "" ? "0" : $("#domainIdx").val();
			var brandEmptyCheck = $("#brandIdx").val() == "" ? "0" : $("#brandIdx").val();
			var francEmptyCheck = $("#francIdx").val() == "" ? "0" : $("#francIdx").val();

			var tabDataArr = [
				{tabName: "기본속성", tabUrl: '<c:url value="/FranchiseeManagement/BasicAttrNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()}},
				/* {tabName: "상세정보", tabUrl: '<c:url value="/FranchiseeManagement/DetailNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()}}, */
				{tabName: "단말관리", tabUrl: '<c:url value="/FranchiseeManagement/KioskNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()}},
				{tabName: "사용자",  tabUrl: '<c:url value="/UserManagement/UserNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()}}
				/* {tabName: "공지사항", tabUrl: '<c:url value="/NoticeManagement/NoticeNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()}} */
			];

			$.newTabControl(tabDataArr, val);
		} else {
			$('#detail').hide();
			FrancList();
			$('#francList').show();
		}
	}

	var mTable = "";
	function FrancList() {
		if(mTable != "")
			mTable.destroy();

		var optObject = {};

		optObject.id = "#francListTable";
		optObject.url = '<c:url value="/FranchiseeManagement/FrancList"/>';
		optObject.arrColumnDefs = [

		    {
		    	"targets": [0]
		       ,"className": "text-center"
		       ,"orderable": false
		       ,'render': function (data, type, full, meta) {
	               	return dataTableRowIdx(meta);
	           }
		    },
		    {
		    	"targets": [2, 6, 7]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		    },
		    {
		    	"targets": [1, 3, 4, 5]
		       ,"className": "text-center tl130"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<div class="ellipsis130">'+(fnDataTableRenderText(data) == null ? "" : fnDataTableRenderText(data))+'</div>';
               }
		    }, 
		    {
		    	   'width': '15%',
	        	   'targets': 8,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="francActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="francActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse>';
	               }
	         }
	    ];
	   	optObject.arrColumns = [
            {data: "rowIdx"},
            {data: "groupName"},
            {data: "francName"},
            {data: "francCode"},
            {data: "telNum"},
            {data: "deviceCnt"},
//             {data: "allDaySalesYn"},
            {data: "salesStartTime"},
            {data: "salesEndTime"},
            {data: "activeYn"}
        ];

	   	optObject.language = {"search": "사이트 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "groupIdx", "value": $("#groupIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "regDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		mTable = commonPagingDateTable(optObject);


	}

	//활성화 공통으로 변경
	function francActive(rownum, brandIdx, francIdx)
	{
		commonActive(rownum, { brandIdx: brandIdx, francIdx: francIdx}, '<c:url value="/FranchiseeManagement/FrancActive"/>');
	}

</script>