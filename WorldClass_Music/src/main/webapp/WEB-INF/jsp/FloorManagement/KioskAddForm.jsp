<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	#kioskTotalListTable tr {
		cursor : pointer;
	}
</style>
<div class="box box-widget" id="kioskForm">
	<div class="box-header">
		<div class="user-block under-line">단말 추가</div>
    </div>
    <div class="box-body" style="overflow : auto; height : 600px;">
    	<div style="margin : 40px 0 0 0;">
			<table id="kioskTotalListTable" class="table table-striped">
				<thead>
					<tr>
						<th>NO</th>
						<th>단말명</th>
						<th>사이트명</th>
						<th>포함된 층</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="addkiosk()" id="addKioskButton">추가</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>
<script type="text/javascript">
	$(function() {
		makeKioskTotalListTable();

		//단말 선택 multi 처리
		$('#kioskTotalListTable tbody').on('click', 'tr', function () {
			$(this).toggleClass('selected');
		});
	});

	//단말 리스트
	function makeKioskTotalListTable() {
		var optObject = {};

		optObject.id = "#kioskTotalListTable";
		optObject.url = '<c:url value="/FloorManagement/KioskTotalList"/>';

		optObject.arrColumns = [
			{data: "rowIdx"},
			{data: "deviceName"},
			{data: "francName"},
			{data: "dispShortNm"}
		];

		optObject.arrColumnDefs = [
			{
				'width' : '10%',
				'targets' : [0],
				'data' : 'idx',
				'className' : 'text-center',
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return dataTableRowIdx(meta);
				}
			}, {
				'width' : '35%',
				'targets' : [1, 2],
				'className' : 'text-center',
				'orderable' : true,
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
				}
			}, {
				'width' : '20%',
				'targets' : [3],
				'className' : 'text-center',
				'orderable' : true,
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
				}
			}
		];

		optObject.serverParams = function (aoData) {
			aoData.push({"name" : "domainIdx", "value": $("#domainIdx").val()});
			aoData.push({"name" : "brandIdx", "value": $("#brandIdx").val()});
			aoData.push({"name" : "floorIdx", "value": null});
		}

		//optObject.language = {"search": "검색"};
		optObject.searching = false;
		optObject.type = 4;
		optObject.pageLen = 100;

		var mTable = commonPagingDateTable(optObject);
	}

	//단말 정보 추가
	function addkiosk() {
		var rowData = $('#kioskTotalListTable').DataTable().rows('.selected').data();

		if(rowData == null || rowData == '') {
			$.modalCommon.alertView('단말을 선택해 주세요');
			return false;
		} else {
			var params = [];

			$.each(rowData, function() {
				var domainIdx = $("#domainIdx").val();
				var brandIdx = $("#brandIdx").val();
				var floorIdx = $('#floorIdx').val();
				var francIdx = this.francIdx;
				var deviceIdx = this.deviceIdx;

				var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, francIdx : francIdx, deviceIdx : deviceIdx};
				params.push(param);
			});

			var jsonData = JSON.stringify(params);

			BootstrapDialog.confirm({
				title : '단말 추가',
				message : '추가하시겠습니까?',
				type : BootstrapDialog.TYPE_WARNING,
				closable : false,
				draggable : true,
				btnCancelLabel : '취소',
				btnOKLabel : '추가',
				btnOKClass : 'btn-warning',
				action : function(dialogRef) {
					dialogRef.close();
				},
				callback : function (result) {
					var kioskInsertUrl = '/FloorManagement/FloorKioskInsert';

					if (result) {
						var result = $.ajaxUtil.ajaxArray(kioskInsertUrl, {"jsonData" : jsonData});

						if (result.resultCode == 0){
							$.modalCommon.alertView('단말이 추가되었습니다');
							makeKioskTotalListTable(); //단말 추가 팝업 data refresh
							makeFloorKioskListTable(false); //단말 관리 추가 팝업 table data refresh
						}
					}
				}
			});
		}
	}
</script>
