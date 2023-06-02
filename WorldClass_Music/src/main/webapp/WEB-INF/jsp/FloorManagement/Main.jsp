<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">층관리</div>
	</div>
	<div class="box-body">
		<table id="floorListTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>No.</th>
					<th>층표시명</th>
					<th>층표시명(한)</th>
					<th>층표시명(영)</th>
					<th>단말갯수</th>
					<th>시설갯수</th>
					<th>브랜드갯수</th>
					<!-- th>활성화</th-->
					<th>최종수정일</th>
					<th>Action</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="addFloor();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="deleteFloor()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(function() {
		treeSearchList(0, false, null, 'STO01');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});

	//층 리스트 정보 binding
	function makeFloorListTable() {
		var optObject = {};

		optObject.id = "#floorListTable";
		optObject.url = '<c:url value="/FloorManagement/FloorList"/>';

		//데이터 필드 정보
		optObject.arrColumns = [
	   		{data: "floorIdx"},
            {data: "rowIdx"},
            {data: "dispShortNm"},
            {data: "dispNmKr"},
            {data: "dispNmEn"},
            {data: "kioskCount"},
            {data: "facilitiesCount"},
            {data: "shopCount"},
            //{data: "activeYn"},
            {data: "modDate"},
            {data: "floorIdx"}
        ];

		//데이터 필드 속성 정보
		optObject.arrColumnDefs = [
			{
				'width' : '30px',
				'targets' : [0],
				'data' : 'idx',
				'className' : 'text-center',
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					if(full.modYn == 'N') {
						return '<input type="checkbox" value="' + data + '" id="checkboxArr" />';
					} else {
						return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
					}
				}
			}, {
				'width' : '50px',
				'targets' : [1],
				'className' : 'text-center',
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return dataTableRowIdx(meta);
				}
			}, {
				'width' : '60px',
				'targets' : [2],
				'className' : "text-center",
				'orderable' : true,
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
				}
			}, {
				'width' : '100px',
				'targets' : [3, 4],
				'className' : "text-center",
				'orderable' : true,
				'render': function (data, type, full, meta) {
					var result = data.replace("\\n", '<br />');
					return '<div style="line-height:22px;">' + result + '</div>';
					//return '<div class="ellipsis130">'+fnDataTableRenderText(data)+'</div>';
				}
			}, {
				'width' : '80px',
				'targets' : [5, 6, 7, 8],
				'className' : "text-center",
				'orderable' : true,
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
				}
			}, /*{
	        	//활성화, 비활성화
	        	'width': '135px',
	            'targets': [8],
	            'data': 'activeYn',
	            'orderable': false,
	            'type': 'date',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
					if(fnDataTableRenderText(data) == 'Y') {
						return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.floorIdx+'" onchange="activeFloor(\'' + full.floorIdx + '\');" data-reverse checked>';
					} else {
						return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.floorIdx+'" onchange="activeFloor(\'' + full.floorIdx + '\');" data-reverse>';
					}
				}
			},*/ {
				//정보팝업
				'width' : '155px',
				'targets': [9],
				'orderable': false,
				'data': 'idx',
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					return '<button type="button" class="btn btn-warning" onclick="editFloorBasic(\'' + full.floorIdx + '\', \'' + full.dispShortNm + '\');" id="basicForm">기본정보</button>&nbsp;'
						+ '<button type="button" class="btn btn-warning" onclick="editFloorDetail(\'' + full.floorIdx + '\', \'' + full.dispShortNm + '\');" id="detailForm">상세정보</button>';
					}
				}
			];

		optObject.language = {"search": "층 표시명"};
		optObject.pageLen = 30;

		//활성화, 비활성화 라벨
		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {offLabel: '비활성화', onLabel: '활성화'});
		}

		//서버 전달 파라미터
		optObject.serverParams = function (aoData) {
			aoData.push({"name" : "domainIdx" , "value": $("#domainIdx").val()});
			aoData.push({"name" : "brandIdx" , "value": $("#brandIdx").val()});
			aoData.push({"name" : "defaultOrderName" , "value" : "orderSeq"});
            aoData.push({"name" : "defaultOrderType" , "value" : "ASC"});
        }

		var mTable = commonPagingDateTable(optObject);
	}

	//활성화/비활성화
	function activeFloor(floorIdx) {
		var brandIdx = $('#brandIdx').val();

		commonActive(floorIdx, {brandIdx : brandIdx, floorIdx : floorIdx}, '<c:url value="/FloorManagement/FloorActive"/>');
	}

	//기본정보
	function editFloorBasic(floorIdx) {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};

		$.modalCommon.loadFullDataView('층 정보 수정', '<c:url value="/FloorManagement/FormNullTemp"/>', param);
	}

	//상세정보
	function editFloorDetail(floorIdx, floorShortName) {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $('#brandIdx').val();
		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};

		$.modalCommon.loadFullDataView(floorShortName + ' - 층 상세 정보', '<c:url value="/FloorManagement/DetailForm"/>', param);
	}

	//층 등록
	function addFloor() {
		$.sessionCheck();

		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : 0};
		$.modalCommon.loadFullDataView('층 정보 등록', '<c:url value="/FloorManagement/FormNullTemp"/>', param);
	}

	//층 삭제
	function deleteFloor() {
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');
		var callBack = function (result) {
			if (result) {
				var domainIdx = $("#domainIdx").val();
				var brandIdx = $("#brandIdx").val();

				var param = {domainIdx : domainIdx, brandIdx : brandIdx, checkboxArr : checkboxArr};

				var dResult = $.ajaxUtil.ajaxArray('<c:url value="/FloorManagement/FloorDelete"/>', param);
				if (dResult.resultCode == 0){
					makeFloorListTable();
				}
			}
		}
		commonDelete(checkboxArr, callBack);
	}

	// 트리 노드가 선택될 경우 - 테이블 데이터 세팅
	function callbackSelectNodeJstree(data) {
		$('#domainIdx').val(data.node.li_attr.domainidx);
		$('#brandIdx').val(data.node.li_attr.brandidx);

		makeFloorListTable();
	}

	//트리 생성 후 callback
	function callbackFirstSelect(jsonData) {
		//TODO : 도메인이 선택되어 있으면 첫번째 브랜드 선택드로 이동
		if(jsonData != null && jsonData.DEPTH == 1) {
			makeFloorListTable();

			$('#addBtn').attr('disabled', 'disabled');
     		$('#delBtn').attr('disabled', 'disabled');
		}
	}
</script>
