<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
optionTable.dataTable tbody tr.selected {
  background-color: #B2CCFF;
}
</style>
<div class="modal-body">
	<div class="box-body" style="border-bottom-style: solid !important; border-bottom:2px; border-bottom-color: #d2d6de;" id="optionProdFind">
		<table id="optionTable" class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
<!-- 					<th>옵션 상품 명</th> -->
					<th>옵션 상품표시 명</th>
					<th>옵션 상품금액</th>
				</tr>
			</thead>
		</table>
		
	
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="addOptionProd()" id="procBtn">추가</button>
		<button type="button" class="btn btn-default optionProdmodal notDisabled" data-dismiss="modal">닫기</button>
	</div>
</div>
<script type="text/javaScript">
$(document).ready(function () {
	optionProdList();
	addTableSelected();
	
});



function addTableSelected() {
	var table = $('#optionTable').DataTable();

    $('#optionTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
        	$(this).addClass('selected');
        }
    });
    
   
}

function addOptionProd(){
	var tr = $('#optionProdFind').find('table[id="optionTable"]').find('tbody').find('tr.selected');
	var selectData = [];
	
	if (tr.length == 0) {
		$.modalCommon.alertView('옵션 상품을 선택해주세요.');
		return;
	}
	
	var overCnt = 0;
	for (var i = 0; i < tr.length; i++) {
		var overLap = false;
		var parent = $(tr[i]);
		var optionTableData = $('#optionTable').DataTable().rows().data();
		var optionProdTableData = $('#optionProdTable').DataTable().rows().data(); 
		
		for(var j = 0; j < optionProdTableData.length; j++){
			
			if($(tr[i].children[0]).find('input').val() == optionProdTableData[j].optionProdIdx){
				overLap = true;
				overCnt += 1;
			}
		}
		
		if(!overLap){
			for(var j = 0; j < optionTableData.length; j++){
				if($(tr[i].children[0]).find('input').val() == optionTableData[j].optionProdIdx){
					selectData.push({optionProdIdx:optionTableData[j].optionProdIdx, optionProdName:optionTableData[j].optionProdName, optionProdDisplayName:optionTableData[j].optionProdDisplayName, optionProdAmount:optionTableData[j].optionProdAmount});
				}
			}
		}else{
			overLap = false;
		}
		
	}
	
	
	if(overCnt > 0){
		$.modalCommon.alertView('중복되는 '+overCnt+"의 값을 제외하고 저장하였습니다.");
	}


	// 스케줄 테이블에 추가
	$('#optionProdTable').DataTable().rows.add(selectData).draw();

	// 스크린 화면 닫기
	$(" .optionProdmodal").click();
}

function optionProdList(){
	var param = {domainIdx: $('#user #domainIdx').val(), brandIdx:$('#user #brandIdx').val(), francIdx:$('#user #francIdx').val()}
	var optionJsonData = $.ajaxUtil.ajaxDefault('<c:url value="/OptionProd/OptionProdFormList"/>', param);
	
	var mTable = $('#optionTable').DataTable({
        data: optionJsonData.data,
        destroy: true,
        responsive: true,
        searching: true,
        bPaginate: false,
        language: {"search": "옵션 상품 명"},
        bInfo: false,
        ordering: false,
        scrollY: '35vh',
        columnDefs: [
            {
            	'width': '5%',
                'targets': 0,
                'data': 'optionProdIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return data+'<input type="hidden" value="' + full.optionProdIdx + '">';;
            	}
            },
            {
		    	"targets": 1
		       ,'data': 'optionProdDisplayName'
		       ,"className": "text-center tl270 optionProdDisplayName"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
            {
            	'targets': 2,
                'data': 'optionProdAmount',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return data;
                }
            }
        ]
    });
	
	
	
	$('.form-control.input-sm').on( 'keyup', function () {
		mTable.column('.optionProdName').search( this.value );
		mTable.draw();
	} );
}
</script>