<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
</style>
<div class="box box-widget" id="facilitiesForm">
	<div class="box-header">
		<div class="user-block under-line">편의시설 추가</div>
    </div>
    <div class="box-body">
    	<div style="margin : 40px 0 0 0;">
			<table id="facilitiesTotalListTable" class="table table-striped">
				<thead>
					<tr>
						<th>NO</th>
						<th>편의시설명</th>
						<th>편의시설코드</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="addFacilities()" id="addFacilitiesButton">추가</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>
<style>
	#facilitiesTotalListTable tr {
		cursor : pointer;
	}
</style>
<script type="text/javascript">
	$(function() {
		makeFacilitiesTotalListTable();

		//편의시설 선택 single 처리
		$('#facilitiesTotalListTable tbody').on('click', 'tr', function () {
			var table = $('#facilitiesTotalListTable').DataTable();
	        var data = table.row(this).data();

	        if ($(this).hasClass('selected')) {
	            $(this).removeClass('selected');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    });
	});

	//편의시설 리스트
	function makeFacilitiesTotalListTable() {
		var optObject = {};

		optObject.id = "#facilitiesTotalListTable";
		optObject.url = '<c:url value="/FloorManagement/TotalFacilitiesList"/>';

		optObject.arrColumns = [
	   		{data: "rowIdx"},
            {data: "codeName"},
            {data: "codeValue"}
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
		    	'width' : '50%',
		    	'targets' : [1],
				'className' : 'text-center',
				'orderable' : true,
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
               }
		    }, {
		    	'width' : '40%',
		    	'targets' : [2],
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

		optObject.searching = false;
		optObject.type = 4;

		var mTable = commonPagingDateTable(optObject);
	}

	//편의시설 정보 추가
	function addFacilities() {
		var rowData = $('#facilitiesTotalListTable').DataTable().row('.selected').data();

		if(rowData == null || rowData == '') {
			$.modalCommon.alertView('편의시설을 선택해 주세요');
			return false;
		} else {
			var domainIdx = $("#domainIdx").val();
			var brandIdx = $("#brandIdx").val();
			var floorIdx = $('#floorIdx').val();
			var codeValue = rowData.codeValue;
			var codeName = rowData.codeName;

			var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, facilityType : codeValue};

			BootstrapDialog.confirm({
							title : '',
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
	            callback: function (result) {
	            	var facilitiesInsertUrl = '/FloorManagement/FloorFacilitiesInsert';

	                if (result) {
	                    var result = $.ajaxUtil.ajaxArray(facilitiesInsertUrl, param);

	                    if (result.resultCode == 0){
	                    	//$.modalCommon.alertView('편의시설(' + codeName + ')이 추가되었습니다');
	                    	//makeFacilitiesTotalListTable(); //편의시설 추가 팝업 data refresh
	                    	makeFloorFacilitiesListTable(false); //편의시설 관리 추가 팝업 table data refresh
	                    }
	                }
	            }
	        });
       	}
	}
</script>
