<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	#templateListTable tr {
		cursor : pointer;
	}
</style>
<div class="box box-widget" id="templateForm">
	<div class="box-header">
		<div class="user-block under-line">템플릿 불러오기</div>
    </div>
    <div class="box-body" style="overflow : auto; height : 600px;">
    	<div style="margin : 40px 0 0 0;">
			<table id="templateListTable" class="table table-striped">
				<thead>
					<tr>
						<th>NO</th>
						<th>템플릿 명</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="addTemplate()" id="addTemplateButton">추가</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>
<script type="text/javascript">
	$(function() {
		$('.modal-lg').css('width', '600px');
		
		makeTemplateListTable();

		//템플릿 선택 single 처리
		$('#templateListTable tbody').on('click', 'tr', function () {
			var table = $('#templateListTable').DataTable();
	        var data = table.row(this).data();
	        
	        if ($(this).hasClass('selected')) {
	            $(this).removeClass('selected');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    });
	});

	//템플릿 리스트
	function makeTemplateListTable() {
		var optObject = {};

		optObject.id = "#templateListTable";
		optObject.url = '<c:url value="/Screen/TemplateList"/>';

		optObject.arrColumns = [
			{data: "rowIdx"},
			{data: "templateName"}
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
				'width' : '90%',
				'targets' : [1],
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
			aoData.push({"name" : "francIdx", "value": $("#francIdx").val()});
		}

		//optObject.language = {"search": "검색"};
		optObject.searching = false;
		optObject.type = 4;
		optObject.pageLen = 100;

		var mTable = commonPagingDateTable(optObject);
	}

	//템플릿 정보 추가
	function addTemplate() {
		var rowData = $('#templateListTable').DataTable().row('.selected').data();

		if(rowData == null || rowData == '') {
			$.modalCommon.alertView('템플릿을 선택해 주세요');
			return false;
		} else {
			var domainIdx = rowData.domainIdx;
			var brandIdx = rowData.brandIdx;
			var francIdx = rowData.francIdx;
			var templateIdx = rowData.templateIdx;

			BootstrapDialog.confirm({
				title : '템플릿 추가',
				message : '추가하시겠습니까?',
				type : BootstrapDialog.TYPE_WARNING,
				closable : true,
				draggable : true,
				btnCancelLabel : '취소',
				btnOKLabel : '추가',
				btnOKClass : 'btn-warning',
				action : function(dialogRef) {
					dialogRef.close();
				},
				callback : function (result) {
					if(result) {
						applyTemplate(domainIdx, brandIdx, francIdx, templateIdx);
						$.modalCommon.alertView('템플릿이 추가되었습니다');
					}
				}
			});
		}
	}
</script>
