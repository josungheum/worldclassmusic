<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
</style>
<div class="box box-widget" id="shopForm">
	<div class="box-header">
		<div class="user-block under-line">브랜드 추가</div>
    </div>
    <div class="box-body" style="overflow : auto; height : 600px;">
    	<div style="margin : 40px 0 0 0;">
			<table id="shopTotalListTable" class="table table-striped">
				<thead>
					<tr>
						<th>NO</th>
						<th>브랜드명</th>
						<th>포함된 층</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="addShop()" id="addShopButton">추가</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>
<style>
	#shopTotalListTable tr {
		cursor : pointer;
	}
</style>
<script type="text/javascript">
	$(function() {
		makeShopTotalListTable();

		//shop 선택 single 처리
		$('#shopTotalListTable tbody').on('click', 'tr', function () {
			var table = $('#shopTotalListTable').DataTable();
	        var data = table.row(this).data();
	        
	        if ($(this).hasClass('selected')) {
	            $(this).removeClass('selected');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    });
	});
	
	//층 별 브랜드(매장) 리스트
	function makeShopTotalListTable() {
		var optObject = {};

		optObject.id = "#shopTotalListTable";
		optObject.url = '<c:url value="/FloorManagement/TotalShopList"/>';

		optObject.arrColumns = [
	   		{data: "rowIdx"},
            {data: "dispNmKr"},
            {data: "floorNm"}
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
		    	'width' : '70%',
		    	'targets' : [1],
				'className' : 'text-center',
				'orderable' : true,
				'render': function (data, type, full, meta) {
					//var result = full.dispNmKr.replace(/(?:\r\n|\r|\n)/g, '<br />');
					var result = data.replace("\\n", '<br />');
					return '<div style="line-height:22px;">' + result + '</div>';
					//return fnDataTableRenderText(data);  		   
               }
		    }, {
		    	'width' : '20%',
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

		optObject.language = {"search": "브랜드명 : "};
		optObject.pageLen = 30;

		var mTable = commonPagingDateTable(optObject);
	}

	//브랜드(매장) 정보 추가
	function addShop() {
		var rowData = $('#shopTotalListTable').DataTable().row('.selected').data();
		
		if(rowData == null || rowData == '') {
			$.modalCommon.alertView('브랜드를 선택해 주세요');
			return false;
		} else {
			//브랜드 중복체크
			var domainIdx = rowData.domainIdx;
			var brandIdx = rowData.brandIdx;
			//var floorIdx = rowData.floorIdx;
			var floorIdx = $('#floorIdx').val();
			var shopIdx = rowData.shopIdx;
			var dispNmKr = rowData.dispNmKr;
		
			var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, shopIdx : shopIdx};
			
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorShopOverlap"/>', param);

			//추가
			if (result.resultCode == 0) {
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
		            	var shopInsertUrl = '/FloorManagement/FloorShopInsert';
		            	
		                if (result) {
		                    var result = $.ajaxUtil.ajaxArray(shopInsertUrl, param);

		                    if (result.resultCode == 0){
		                    	$.modalCommon.alertView('브랜드(' + dispNmKr + ')가 추가되었습니다');
		                    	makeShopTotalListTable(); //브랜드 추가 팝업 data refresh
		                    	makeFloorShopListTable(false); //브랜드 관리 추가 팝업 table data refresh
		                    }
		                }
		            }
		        });
        	} else {
        	}
        }
	}
</script>
