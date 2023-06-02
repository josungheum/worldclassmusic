<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#shop, #path {
	float : left;
	height : 100%;
}

#shop {
	width : 28%;
	margin: 10px;
}

#path {
	width : 67%;
	margin: 10px;
}

#mapBackgroundImg {
	position : relative;
	text-align : center;
}

#shopDiv {
	border : 1px solid rgba(0, 0, 0, .5);
	position : absolute;
	width : 920px;
	height : 736px;
	top : 79px;
	left : 40px;
	cursor : pointer;
	z-index : 10000;
}

.modal-footer {
	position : relative;
	clear : both;
}

#shopListTable tr {
	cursor : pointer;
}

</style>
<div class="box box-widget" id="shop">
	<div class="box-header">
		<div class="user-block under-line">포함된 브랜드</div>
    </div>
    <div class="box-body" style="overflow : auto; height : 746px;">
    	<div class="pull-right">
    		<button type="button" class="btn btn-primary" onclick="addFloorShop();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>추가</button>
    	</div>
    	<div style="margin : 40px 0 0 0;">
			<table id="shopListTable" class="table table-striped">
				<thead>
					<tr>
						<th>브랜드명</th>
						<th>좌표(x)</th>
						<th>좌표(y)</th>
						<th>좌표적용</th>
						<th>삭제</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- div>현재 마우스 좌표 : <span style="color:red;" id="mouseX"></span> , <span style="color:blue;" id="mouseY"></span> </div>
		<div>Text 포지션 좌표 : <span style="color:red;" id="divMouseX"></span> , <span style="color:blue;" id="divMouseY"></span> </div-->
	</div>
</div>

<div class="box box-widget" id="path">
	<div class="box-header">
		<div class="user-block under-line">위치관리</div>
	</div>
	<div class="box-body" id="mapImg" style="overflow:auto; width:100%; height:100%;">
		<img id="mapBackgroundImg" style=""></img>
		<div id="shopDiv"></div>
	</div>
</div>
<div class="modal-footer">
	<!-- button type="button" class="btn btn-warning" style="float : left;" onclick="toggleShopDivBorder()" id="toggleBtn">Toggle</button-->
	<button type="button" class="btn btn-primary" onclick="save()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>

<script type="text/javaScript">
	$(function() {
		$("#shopDiv").mousemove(function(event) {
			var clickX = Math.round(event.pageX - $(this).offset().left);
			var clickY = Math.round(event.pageY - $(this).offset().top);

			//$("#mouseX").text(clickX);
			//$("#mouseY").text(clickY);
		});

		makeFloorShopListTable(true);
		drawFloorMap();

		//shop 선택 single 처리
		makeOneSelectedRow();

		//맵 이미지 클릭 좌표
		$('#shopDiv').on('click', function(e) {
			var clickX = Math.round(e.pageX - $(this).offset().left);
			var clickY = Math.round(e.pageY - $(this).offset().top);

			var rowData = $('#shopListTable').DataTable().row('.selected').data();

			if(rowData == null || rowData == '') {
				$.modalCommon.alertView('브랜드를 선택해 주세요');
				return false;
			} else {
				if((rowData.posX != null && rowData.posX != "0") && (rowData.posY != null && rowData.posY != "0")) {
					$.modalCommon.alertView('이미 적용된 브랜드 입니다.');
					return false;
				} else {
					var isApplied = false;

					//적용되지 않은 브랜드(매장) 확인
					$(".draggable").each(function(index, item){
						if($(item).attr("id") == rowData.shopIdx) {
							isApplied = true;

							$.modalCommon.alertView('이미 적용된 브랜드 입니다.');
							return false;
						}
					});

					var shopIdx = rowData.shopIdx;
					var dispNmKr = rowData.dispNmKr.replace(/\\r\\n|\\n/g, "<br />");

					if(!isApplied) {
						createShopDiv(shopIdx, dispNmKr, clickX, clickY, "red");
					}
				}
			}
		});
	});

	//층 별 브랜드(매장) 리스트
	function makeFloorShopListTable(refreshDivContent) {
		var optObject = {};

		optObject.id = "#shopListTable";
		optObject.url = '<c:url value="/FloorManagement/FloorShopList"/>';

		//데이터 필드 정보
		optObject.arrColumns = [
			{data: "dispNmKr"},
			{data: "posX"},
			{data: "posY"},
			{data: "shopIdx"},
			{data: "shopIdx"}
		];

		//데이터 필드 속성 정보
		optObject.arrColumnDefs = [
			{
		    	'targets' : [0],
		    	'width' : '40%',
				'className' : "text-center",
				'orderable' : false,
				'render': function (data, type, full, meta) {
					var result = data.replace(/\\r\\n|\\n/g, "<br />");
					return '<div style="line-height:22px;">' + result + '</div>';
				}
			}, {
				'targets' : [1],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.shopIdx + '_x" class="" value="' +  full.posX + '" style="width:34px;height:34px;" maxlength="4" onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [2],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.shopIdx + '_y" class="" value="' +  full.posY + '" style="width:34px;height:34px;" maxlength="4" onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [3],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-success" style="width:34px;" onclick="applyShopPosition(this);" id="' + full.shopIdx + '_btn">O</button>';
				}
			}, {
				'targets' : [4],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-danger" onclick="deleteFloorShop(\'' + full.shopIdx + '\');" id="basicForm">X</button>'
				}
			}
		];

		//서버 전달 파라미터
		optObject.serverParams = function (aoData) {
			aoData.push({"name" : "domainIdx", "value": $("#domainIdx").val()});
			aoData.push({"name" : "brandIdx", "value": $("#brandIdx").val()});
			aoData.push({"name" : "floorIdx", "value": $("#floorIdx").val()});
        }

		optObject.type = 2;

		//draw the table
		var mTable = commonPagingDateTable(optObject);

		clearTextColor();	//매장 텍스트 색상 default
		makeOneSelectedRow();	//그리드 단일 선택만 가능하도록

		//위치관리 data create
		if(refreshDivContent) {
			drawShopInfo();
		}
	}

	//층별 맵 이미지
	function drawFloorMap() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorInfo"/>', param);

		if (result.resultCode == 0) {
			if(result.data != null) {
				$("#mapBackgroundImg").attr("src" , result.data.mapImgPath);
			}
		}
	}

	//층 이미지에 shop 정보 매핑
	function drawShopInfo() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorShopList"/>', param);

		if (result.resultCode == 0) {
			if(result.data != null) {
				$.each(result.data, function(key, item){
					var shopIdx = item.shopIdx;
					var dispNmKr = item.dispNmKr.replace(/\\r\\n|\\n/g, "<br />");
					var left = item.posX;
					var top = item.posY;

					//포지션이 0, 0 인것은 제외하고 생성
					if((left != null && left != "0") && (top != null && top != "0")) {
						createShopDiv(shopIdx, dispNmKr, left, top, "black");
					}
				});
			}
		}
	}

	//매장 Div 생성
	function createShopDiv(id, name, posX, posY, textColor) {
		var splitedShopName = name.split('<br />');
		var longestWordLength = splitedShopName.reduce(function(a, b) {
			return a.length > b.length ? a : b}, '');

		var divWidth = longestWordLength.length * 16;
		var divHeight = splitedShopName.length * 13;

		var divPosX = posX - Math.round(divWidth / 2);
		var divPosY = posY - Math.round(divHeight / 2);

		var createdDiv = $("<div />", {
			html : name,
			id : id,
			"class" : "draggable default-border",
			css : {
				position : "absolute",
				top : divPosY,
				left : divPosX,
				width : divWidth + "px",
				height : divHeight + "px",
				lineHeight : "13px",
				color : textColor,
				fontSize : "11px",
				fontWeight : "bold",
				cursor : "pointer",
				zIndex : "10000",
				display : "table-cell",
				textAlign : "center",
				verticalAlign : "middle",
				display : "table-cell"
				//border: "1px dotted black"
			},

			appendTo : '#shopDiv'
		});

		$(createdDiv).data("shopIdx", id);
		$(createdDiv).data("posX", posX);
		$(createdDiv).data("posY", posY);

		//최초 적용되는 매장 처리
		if(textColor == "red") {
			$(createdDiv).css("color", "red");

			//이미지 위치에 따라 textinput 값 적용
			var inputPosX = "#" + id + "_x";
			var inputPosY = "#" + id + "_y";

			$(inputPosX).val(posX);
			$(inputPosY).val(posY);
		}

		$(".draggable").draggable( {
			start : function(e) {
				clearTextColor();

				var rowData = $('#shopListTable').DataTable().row('.selected').data();
				var divId = $(e.target).attr("id");

				//선택된 매장 div
				if(rowData != null && rowData != undefined) {
					var currentSelectedRowId = rowData.shopIdx;

					if(divId != currentSelectedRowId) {
						changeSeletedRow(divId);
					}
				} else {
					changeSeletedRow(divId);
				}

				$("#" + divId).css("color", "red")
			},
			drag: function(e) {
				var mapOffset = $('#shopDiv').offset();

				var offset = $(this).offset();
				var xPos = offset.left - mapOffset.left;
				var yPos = offset.top - mapOffset.top;

				//$('#divMouseX').text(xPos);
				//$('#divMouseY').text(yPos);

				//매장위치 input text에 반영
				var divId = $(e.target).attr("id");
				var selectedInputPosX = "#" + divId + "_x";
				var selectedInputPosY = "#" + divId + "_y";

				var divWidth = parseInt($(e.target).css("width").replace(/[^-\d\.]/g, ''));
				var divHeight = parseInt($(e.target).css("height").replace(/[^-\d\.]/g, ''));
				var divLeft = parseInt($(e.target).css("left").replace(/[^-\d\.]/g, ''));
				var divTop = parseInt($(e.target).css("top").replace(/[^-\d\.]/g, ''));

				var currentPosX = divLeft + Math.round(divWidth / 2);
				var currentPosY = divTop + Math.round(divHeight / 2);

				$(selectedInputPosX).val(currentPosX);
				$(selectedInputPosY).val(currentPosY);
			}
		});
	}

	//층 브랜드(매장) 삭제
	function deleteFloorShop(shopIdx) {
		BootstrapDialog.confirm({
			title: '',
			message: '삭제하시겠습니까?',
			type: BootstrapDialog.TYPE_WARNING,
			closable: false,
			draggable: true,
			btnCancelLabel: '취소',
			btnOKLabel: '삭제',
			btnOKClass: 'btn-warning',
			callback: function(result) {
				if(!result) {
					return;
				}

				var domainIdx = $("#domainIdx").val();
				var brandIdx = $("#brandIdx").val();
				var floorIdx = $("#floorIdx").val();

				var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, shopIdx : shopIdx};
				var resultData = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorShopDelete"/>', param);

				if (resultData.resultCode == 0) {
					$.modalCommon.alertView('브랜드정보를 삭제하였습니다.');

					//브랜드 정보 리셋
					$(".draggable").each(function(index, item) {
						$(item).remove();
					});

					makeFloorShopListTable(true);
				}
			}
		});
	}

	//층 브랜드(매장)정보 추가 팝업
	function addFloorShop() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		$.modalCommon.loadFullDataView('브랜드 추가', '<c:url value="/FloorManagement/ShopAddForm"/>', param);
	}

	//층 브랜드(매장) 정보 저장
	function save() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var params = [];

		$(".draggable").each(function(index, item){
			var shopIdx = $(item).attr("id");

			//실제 저장이 되어야할 위치 계산 - div의 가운데
			var divWidth = parseInt($(item).css("width").replace(/[^-\d\.]/g, ''));
			var divHeight = parseInt($(item).css("height").replace(/[^-\d\.]/g, ''));
			var divLeft = parseInt($(item).css("left").replace(/[^-\d\.]/g, ''));
			var divTop = parseInt($(item).css("top").replace(/[^-\d\.]/g, ''));

			var posX = divLeft + Math.round(divWidth / 2);
			var posY = divTop + Math.round(divHeight / 2);

			var param = {domainIdx : domainIdx , brandIdx : brandIdx, floorIdx : floorIdx, shopIdx : shopIdx, posX : posX, posY : posY};
			params.push(param);
		});

		var jsonData = JSON.stringify(params);
		var result = $.ajaxUtil.ajaxArray('<c:url value="/FloorManagement/FloorShopUpdate"/>', {"jsonData" : jsonData});

		if (result.resultCode == 0) {
			$.modalCommon.alertView('브랜드정보를 저장하였습니다.');

			//매장 정보 리셋
			$(".draggable").each(function(index, item){
				$(item).remove();
			});

			makeFloorShopListTable(true);
		}
	}

	//shop 선택 single 처리
	function makeOneSelectedRow() {
		var table = $('#shopListTable').DataTable();
		$('#shopListTable tbody').off('click');
		$('#shopListTable tbody').on('click', 'tr', function (e) {
			var data = table.row(this).data();

			if ($(this).hasClass('selected')) {
				//textinput , button 선택시에는 선택 유지
				if(e.target.type != "text" && e.target.type != "button") {
					$(this).removeClass('selected');

					clearTextColor();
				}
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');

				clearTextColor();

				$("#shopDiv div").each(function(index, item) {
					if(data.shopIdx == $(item).attr("id")) {
						$(item).css("color", "red");
						return false;
					}
				});
			}
		});
	}

	//선택된 매장에 따라 그리드 row 선택 변경
	function changeSeletedRow(divId) {
		var table = $('#shopListTable').DataTable();
		table.$('tr.selected').removeClass('selected');

		$('#shopListTable tbody tr').each(function(index, item) {
			var data = table.row(item).data();
			var rowId = data.shopIdx;

			//선택된 매장에 맞게 그리드 row를 선택
			if(rowId == divId) {
				$(item).addClass('selected');
				return false;
			}
		});
	}

	//좌표적용 버튼 클릭 시 textinput 좌표 적용
	function applyShopPosition(e) {
		var uniqueIdx = $(e).attr("id").replace("_btn", "");

		var inputPosX = "#" + uniqueIdx + "_x";
		var inputPosY = "#" + uniqueIdx + "_y";

		//매장 div 포지션 변경 //TODO
		var shopDivText = "#" + uniqueIdx;

		$("#shopDiv div").each(function(index, item) {
			if(uniqueIdx == $(item).attr("id")) {

				var posX = parseInt($(inputPosX).val());
				var posY = parseInt($(inputPosY).val());
				var divWidth = parseInt($(item).css("width").replace(/[^-\d\.]/g, ''));
				var divHeight = parseInt($(item).css("height").replace(/[^-\d\.]/g, ''));

				var divPosX = posX - Math.round(divWidth / 2);
				var divPosY = posY - Math.round(divHeight / 2);

				$(shopDivText).css("left", divPosX + "px");
				$(shopDivText).css("top", divPosY + "px");

				return false;
			}
		});

		clearTextColor();

		$(shopDivText).css("color", "color");
	}

	//매장 div text 색상 검은색 처리
	function clearTextColor() {
		$("#shopDiv div").each(function(index, item) {
			$(item).css("color", "black");
		});
	}

	//shop div border 속성 변경
	function toggleShopDivBorder() {
		$(".draggable").each(function(index, item){
			if($(item).hasClass("default-border")) {
				$(item).css("border", "");
				$(item).removeClass("default-border");
			} else {
				$(item).css("border", "1px dotted black");
				$(item).addClass("default-border");
			}
		});
	}
</script>
