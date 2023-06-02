<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#findTable.dataTable tbody tr.selected {
  background-color: #B2CCFF;
}
</style>
<form:form commandName="orderScreenVo" name="productform" id="productform" method="post" onsubmit="return false;">
	 <div class="modal-body">
		<div style="margin-top:4px; height:510px; overflow-y:scroll; overflow-x: hidden;" class="proton-demo" id="treeFind">
			<table id="findTable" style="width:100%" class="table table-bordered table-striped">
			    <thead>
			        <tr>
			        	<th>No</th>
			            <th>상품코드</th>
			            <th>상품표시명</th>
			            <th>영문표시명</th>
			            <th>판매금액</th>
			        </tr>
			    </thead>
			</table>
	    </div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="prodAdd()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="prodCloseBtn">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog')[1].style.width = '1300px';
		treeFind();
		addTableSelected();
	});

    function addTableSelected() {
		var table = $('#findTable').DataTable();

	    $('#findTable tbody').on('click', 'tr', function () {
	        if ($(this).hasClass('selected')) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$(this).addClass('selected');
	        }
	    });
	}

    function treeFind() {
    	var brand = $('#brandIdx').val();
    	var franc = $('#francIdx').val();

		var optObject = {};

		optObject.id = "#findTable";
		optObject.url = '<c:url value="/OrderScreen/ProdList/'+brand+'/'+franc+'"/>';
		optObject.arrColumnDefs = [
			{
            	'width': '15%',
                'targets': 0,
                "orderable": false,
                'data': 'rowIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                   	return 	dataTableRowIdx(meta)+'<input type="hidden" value="' + full.orderProdIdx + '">';
                }
             },
             {
             	'width': '15%',
                 'targets': 1,
                 "className": "text-center",
                 'render': function (data, type, full, meta) {
                     return fnDataTableRenderText(data)+'<input type="hidden" value="' + full.thumbnailPath + '">';
                 }
              },
              {
               	'width': '15%',
                   'targets': 4,
                   "className": "text-center",
                   'render': function (data, type, full, meta) {
                	   return autoComma(uncomma(full.prodAmount) - uncomma(full.prodDiscountAmount));
                   }
                },
                {
    		    	"targets": [2,3]
    		       ,"className": "text-center tl270"
    	    	   ,'render': function (data, type, full, meta) {
               	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
                   }
    		    }
	    ];
	   	optObject.arrColumns = [
            {data: "rowIdx"},
            {data: "prodCode"},
            {data: "prodDisplayName"},
            {data: "prodEnName"},
            {data: "prodAmount"}

        ];

	   	optObject.language = {"search": "상품표시 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
        }

		var mTable = commonPagingDateTable(optObject);



    }

    function prodAdd() {
    	//기존 메뉴에 선택된 상품들
    	var innerHtml = "";

		var arrTarget = [];
		var over = 0;
		var arrprod = $.formOtherUtil.isArrIdText('prodIdx');
		//선택된 상품
		var tr = $('#treeFind').find('table[id="findTable"]').find('tbody').find('tr.selected');




		var idCnt = 0;
		for (var i = 0; i < tr.length; i++) {
			if($(tr[i].children[0]).find('input').val() == null){
				idCnt = 1;
			}
		}

		if (tr.length == 0 || idCnt == 1) {
			$.modalCommon.alertView('상품을 선택해주세요.');
			return;
		}


		var overCnt = 0;
		for (var i = 0; i < tr.length; i++) {
			var overLap = false;


			$("#sortable"+$('#parentIdx').val()).find("li").each(function(index) {
				if($(tr[i].children[0]).find('input').val() == $(this).attr("name")){
					overLap = true;
					overCnt += 1;
				}
			});

			if(!overLap){

				innerHtml += '	<li name=\"'+$(tr[i].children[0]).find('input').val()+'\" class="ui-sortable-handle">';


				innerHtml += '		<div class="card" style="">';
				innerHtml += '			<div class="card-header">';
				innerHtml += '				<select class="form-control" style="ime-mode:active; width: 180px; display: inline; background-color: white;margin-bottom: 10px;" id="prodStateCode\"'+$(tr[i].children[0]).find('input').val()+'\"">';
				//임시
				if('${prodStateCode}' != "")
				{
					var prodStateCode = JSON.parse('${prodStateCode}');
					for(var j = 0; j < prodStateCode.length; j++)
						innerHtml += '<option value="'+fnDataTableRenderText(prodStateCode[j].codeValue)+'">'+fnDataTableRenderText(prodStateCode[j].codeName)+'</option>';
				}
				innerHtml += '				</select>';
				innerHtml += '				<button onclick="prodDelete('+$('#parentIdx').val()+','+$(tr[i].children[0]).find('input').val()+')" style="width: 17px; height: 17px;" class="lEditorLayerlistItemDel" ot="lEditorLayerlistItemDel"></button>';
				innerHtml += '			</div>';
				innerHtml += '			<div class="card-body" style="background-color: #dcdbdb;border-radius: 20px;"> ';
				innerHtml += '				<img width="240px" height="240px" src="'+$(tr[i].children[1]).find('input').val()+'" class="card-img-top" alt="..." style="border-radius: 20px 20px 0px 0px;">';
				innerHtml += '				<ul class="list-group list-group-flush" style="padding: 10px;">';
				innerHtml += '					<li class="list-group-item2" style="font-size: 5px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><strong style="font-size: 20px;">'+fnDataTableRenderText($(tr[i].children[2]).text());
				innerHtml += '						</strong><br>';
				innerHtml += '						<font style="color:grey;font-size: 12px;word-break:break-all;">'+fnDataTableRenderText($(tr[i].children[3]).text());
				innerHtml += '						</font><br>';
				innerHtml += '						<div style="font-size: 20px;width: 100%;font-weight: bold;text-align: right;">￦ '+fnDataTableRenderText($(tr[i].children[4]).text())+'</div>';

				innerHtml += '					</li>';
				innerHtml += '				</ul>';
				innerHtml += '			</div>';
				innerHtml += '		</div>';

				innerHtml += '	</li>';
			}else{
				overLap = false;
			}
		}
		if(overCnt > 0){
			$.modalCommon.alertView('중복되는 '+overCnt+"의 값을 제외하고 저장하였습니다.");
		}
		$(innerHtml).appendTo($('#sortable'+$('#parentIdx').val()))

	    $('#prodCloseBtn').click();
	}
</script>