<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="box box-widget" id="detail">
	<div class="box-header">
    		<div class="user-block under-line">서비스 속성</div>
    </div>
    <div class="box-body">
	    	<div class="row nav-tabs" id="tabLab" style="margin: 5px 0 30px 4px;">
			<ul class="nav nav-pills" id="tabList"></ul>
		</div>
		<div id="contentView"></div>
     </div>
</div>

<div class="box box-widget" id="brandList">
	<div class="box-header">
		<div class="user-block under-line">서비스 속성</div>
	</div>
	<!-- Tap Control -->
	<div class="box-body">
		<table id="brandListTable" class="table table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>서비스 명</th>
					<th>서비스 코드</th>
					<th>사이트 수</th>
					<th>최종 수정일</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
 		if('${sessionScope.adminType}' == 'ADM0002'){
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

		treeSearchList(0, false, null, 'STO01');
		treeBtnHeight("580");

		$(window).resize(function(){
			treeBtnHeight("580");
	    });
	});

	function tabView(val) {
		var domainId = 1;
		var nameArr = new Array();
		var urlArr = new Array();

		if($('#brandIdx').val() != "0"){
			$('#brandList').hide();
			$('#tabLab').show();
			$('#detail').show();

			var tabDataArr = [
					{tabName: "기본속성", tabUrl: '<c:url value="/BrandManagement/BasicAttrNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val()}},
					{tabName: "사용자", tabUrl: '<c:url value="/UserManagement/UserNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val()}}
					/* {tabName: "할인율", tabUrl: '<c:url value="/BrandManagement/DiscountNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val()}},
					{tabName: "공지사항", tabUrl: '<c:url value="/NoticeManagement/NoticeNullTemp"/>', tabParam: {brandIdx: $("#brandIdx").val()}} */
			];

			$.newTabControl(tabDataArr, val);
		}
		else {
			$('#detail').hide();
			fncBrandList();
			$('#brandList').show();
		}
	}

	var mTable = "";
	function fncBrandList() {

		if(mTable != "")
			mTable.destroy();

		var optObject = {};

		optObject.id = "#brandListTable";
		optObject.url = '<c:url value="/BrandManagement/BrandList"/>';

	   	optObject.arrColumns = [
            {data: "rowIdx"},
            {data: "name"},
            {data: "brandCode"},
            {data: "orderSheetType"},
            {data: "modDate"},
            {data: "activeYn"}


        ];

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
		    	"targets": [2, 3, 4]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		    },
		    {
		    	"targets": [1]
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
		    {
	        	   'width': '15%',
	               'targets': 5,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.brandIdx+'" onchange="mainActive(\'' + full.brandIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.brandIdx+'" onchange="mainActive(\'' + full.brandIdx + '\');" data-reverse>';
	               }
	         }

	    ];

	   	optObject.language = {"search": "서비스 명"};

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
			aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		mTable = commonPagingDateTable(optObject);
	}

	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	function callbackSelectNodeJstree(data)
	{
		tabView(0);
	}

	function callbackFirstSelect(jsonData)
	{
		tabView(0);
	}

	//활성화 공통으로 변경
	function mainActive(val)
	{
		commonActive(val, { brandIdx: val}, '<c:url value="/BrandManagement/BrandActive"/>');
	}

</script>