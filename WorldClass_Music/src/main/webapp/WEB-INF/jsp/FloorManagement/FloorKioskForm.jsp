<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#kiosk, #kiosk_path {
	float : left;
	height : 100%;
}

#kiosk {
	width : 28%;
	margin: 10px;
}

#kiosk_path {
	width : 67%;
	margin: 10px;
}

#kioskListTable tr {
	cursor : pointer;
}

#mapBackgroundImg {
	position : relative;
	text-align : center;
}

#kioskDiv {
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

</style>
<div class="box box-widget" id="kiosk">
	<div class="box-header">
		<div class="user-block under-line">포함된 단말</div>
	</div>
	<div class="box-body">
		<div class="pull-right">
			<button type="button" class="btn btn-primary" onclick="addFloorKiosk();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>추가</button>
		</div>
		<div style="margin : 40px 0 0 0;">
			<table id="kioskListTable" class="table table-striped">
				<thead>
					<tr>
						<th>단말명</th>
						<th>좌표(x)</th>
						<th>좌표(y)</th>
						<th>방향</th>
						<th>적용</th>
						<th>삭제</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- div>현재 마우스 좌표 : <span style="color:red;" id="mouseX"></span> , <span style="color:blue;" id="mouseY"></span> </div>
		<div>이미지 포지션 좌표 : <span style="color:red;" id="divMouseX"></span> , <span style="color:blue;" id="divMouseY"></span> </div-->
	</div>
</div>

<div class="box box-widget" id="kiosk_path">
	<div class="box-header">
		<div class="user-block under-line">위치관리</div>
	</div>
	<div class="box-body" id="mapImg" style="overflow:auto; width:100%; height:100%;">
		<div>
			<img id="mapBackgroundImg"></img>
		</div>
		<div id="kioskDiv"></div>
	</div>
</div>
<div class="modal-footer">
	<!-- button type="button" class="btn btn-warning" style="float : left;" onclick="toggleKioskDivBorder()" id="toggleBtn">Toggle</button-->
	<button type="button" class="btn btn-primary" onclick="save()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>

<script type="text/javaScript">
	$(function() {
		$("#kioskDiv").mousemove(function(event) {
			var clickX = Math.round(event.pageX - $(this).offset().left);
			var clickY = Math.round(event.pageY - $(this).offset().top);

			//$("#mouseX").text(clickX);
			//$("#mouseY").text(clickY);
		});

		makeFloorKioskListTable(true);
		drawFloorMap();

		//kiosk 선택 single 처리
		makeOneSelectedRow();

		//맵 이미지 클릭 시 이미지 생성
		$('#kioskDiv').on('click', function(e) {
			var clickX = Math.round(e.pageX - $(this).offset().left);
			var clickY = Math.round(e.pageY - $(this).offset().top);

			var rowData = $('#kioskListTable').DataTable().row('.selected').data();

			if(rowData == null || rowData == '') {
				$.modalCommon.alertView('단말을 선택해 주세요');
				return false;
			} else {
				if((rowData.posX != null && rowData.posX != "0") && (rowData.posY != null && rowData.posY != "0")) {
					$.modalCommon.alertView('이미 적용된 단말 입니다.');
					return false;
				} else {
					var isApplied = false;

					//적용되지 않은 단말 확인
					$(".draggable").each(function(index, item){
						var rowDataId = rowData.francIdx + "_" + rowData.deviceIdx;
						if($(item).attr("id") == rowDataId) {
							isApplied = true;

							$.modalCommon.alertView('이미 적용된 단말 입니다.');
							return false;
						}
					});

					if(!isApplied) {
						createKioskImg(rowData.francIdx, rowData.deviceIdx, rowData.deviceName, clickX, clickY, 'b', "ACTIVE");
					}
				}
			}
		});
	});

	//층 별 단말 리스트
	function makeFloorKioskListTable(refreshDivContent) {
		var optObject = {};

		optObject.id = "#kioskListTable";
		optObject.url = '<c:url value="/FloorManagement/KioskTotalList"/>';

		//데이터 필드 정보
		optObject.arrColumns = [
			{data: "deviceName"},
			{data: "posX"},
			{data: "posY"},
			{data: "direction"},
			{data: "deviceIdx"},
			{data: "deviceIdx"}
		];

		//데이터 필드 속성 정보
		optObject.arrColumnDefs = [
			{
				'targets' : [0],
				'width' : '40%',
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<div class="ellipsis130">' + fnDataTableRenderText(data) + '</div>';
				}
			}, {
				'targets' : [1],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.francIdx + '_' + full.deviceIdx + '_x" class="" value="' + full.posX + '" style="width:34px;height:34px;" maxlength="4"'
						+ 'onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [2],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.francIdx + '_' + full.deviceIdx + '_y" class="" value="' + full.posY + '" style="width:34px;height:34px;" maxlength="4"'
						+ 'onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [3],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.francIdx + '_' + full.deviceIdx + '_d" class="" value="' + full.direction + '" style="width:34px;height:34px;text-transform:lowercase;" maxlength="1" '
						+ 'onKeyup="this.value=this.value.replace(/[^a-z]/g,\'\');" />';
				}
			}, {
				'targets' : [4],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-success" style="width:34px;" onclick="applyKioskPosition(this);" id="' + full.francIdx + '_' + full.deviceIdx + '_btn">O</button>';
				}
			}, {
				'targets' : [5],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-danger" style="width:34px;" onclick="deleteFloorKiosk(\'' + full.francIdx + '\',\'' + full.deviceIdx + '\');" id="">X</button>'
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

		clearImgFilter();	//단말 이미지 필터 제거
		makeOneSelectedRow();	//그리드 단일 선택만 가능하도록

		//위치관리 data create
		if(refreshDivContent) {
			drawKioskInfo();
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

	//층 이미지에 편의시설 정보 매핑
	function drawKioskInfo() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/KioskTotalList"/>', param);

		if (result.resultCode == 0) {
			if(result.data != null) {
				$.each(result.data, function(key, item){
					var francIdx = item.francIdx;
					var deviceIdx = item.deviceIdx;
					var deviceName = item.deviceName;
					var left = item.posX;
					var top = item.posY;
					var direction = item.direction;

					//포지션이 0, 0 인것은 제외하고 생성
					if((left != null && left != "0") && (top != null && top != "0")) {
						createKioskImg(francIdx, deviceIdx, deviceName, left, top, direction, "DEACTIVE");
					}
				});
			}
		}
	}

	//단말 이미지 생성
	function createKioskImg(francIdx, deviceIdx, deviceName, posX, posY, direction, imageType) {
		var imageSrc = changeKioskImg(direction);

		//20200413 단말방향에 따라 위치 보정
		var offsetX = 32;
		var offsetY = 64;
		if(direction == "t") {	//상단
			var offsetY = 0;
		} else if(direction == "r") {	//오른쪽
			var offsetX = 64;
			var offsetY = 32;
		} else if(direction == "l") {	//왼쪽
			var offsetX = 0;
			var offsetY = 32;
		}

		var createdImg = $("<img />", {
			id : francIdx + "_" + deviceIdx,
			src : imageSrc,
			"class" : "draggable",
			css : {
				position : "absolute",
				top : posY - offsetY,
				left : posX - offsetX,
				width : "64px",
				height : "64px",
				cursor : "pointer",
				zIndex : "10000",
				border : "1px solid #eaeaea"
			},
			appendTo : '#kioskDiv'
		});

		$(createdImg).data("francIdx", francIdx);
		$(createdImg).data("deviceIdx", deviceIdx);

		//최초 적용되는 단말 Active 처리
		if(imageType == "ACTIVE") {
			var filterVal = "invert(75%)";
			$(createdImg).css("filter", filterVal).css("webkitFilter", filterVal).css("mozFilter", filterVal).css("oFilter", filterVal).css("msFilter", filterVal);

			//이미지 위치에 따라 textinput 값 적용
			var inputPosX = "#" + francIdx + "_" + deviceIdx + "_x";
			var inputPosY = "#" + francIdx + "_" + deviceIdx + "_y";

			$(inputPosX).val(posX);
			$(inputPosY).val(posY);
		}

		$(".draggable").draggable( {
			start : function(e) {
				clearImgFilter();

				var rowData = $('#kioskListTable').DataTable().row('.selected').data();
				var imgId = $(e.target).attr("id");

				//선택된 단말이미지 필터 적용
				if(rowData != null && rowData != undefined) {
					var currentSelectedRowId = rowData.francIdx + "_" + rowData.deviceIdx;

					if(imgId != currentSelectedRowId) {
						changeSeletedRow(imgId);
					}
				} else {
					changeSeletedRow(imgId);
				}

				var filterVal = "invert(75%)";
				$("#" + imgId).css("filter", filterVal).css("webkitFilter", filterVal).css("mozFilter", filterVal).css("oFilter", filterVal).css("msFilter", filterVal);
			},
			drag : function(e) {
				var mapOffset = $('#kioskDiv').offset();

				var offset = $(this).offset();
				var xPos = offset.left - mapOffset.left;
				var yPos = offset.top - mapOffset.top;

				//$('#divMouseX').text(xPos);
				//$('#divMouseY').text(yPos);

				//단말위치 input text에 반영
				var imgId = $(e.target).attr("id");
				var selectedInputPosX = "#" + imgId + "_x";
				var selectedInputPosY = "#" + imgId + "_y";

				var currentPosX = parseInt($(e.target).css("left").replace(/[^-\d\.]/g, '')) + 32;
				var currentPosY = parseInt($(e.target).css("top").replace(/[^-\d\.]/g, '')) + 64;

				$(selectedInputPosX).val(currentPosX);
				$(selectedInputPosY).val(currentPosY);
			}
		});
	}

	//층 단말정보 삭제
	function deleteFloorKiosk(francIdx, deviceIdx) {
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

				var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, francIdx : francIdx, deviceIdx : deviceIdx};
				var resultData = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorKioskDelete"/>', param);

				if (resultData.resultCode == 0) {
					$.modalCommon.alertView('단말정보를 삭제하였습니다.');

					//단말 정보 리셋
					$(".draggable").each(function(index, item) {
						$(item).remove();
					});

					makeFloorKioskListTable(true);
				}
			}
		});
	}

	//층 단말 추가 팝업
	function addFloorKiosk() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		$.modalCommon.loadFullDataView('단말 추가', '<c:url value="/FloorManagement/KioskAddForm"/>', param);
	}

	//층 단말 정보 저장
	function save() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var params = [];

		$(".draggable").each(function(index, item){
			var francIdx = $(item).data("francIdx");
			var deviceIdx = $(item).data("deviceIdx");
			var posX = $(item).css("left").replace("px", "").replace(/[^-\d\.]/g, '');
			var posY = $(item).css("top").replace("px", "").replace(/[^-\d\.]/g, '');

			//20200413 단말 방향 저장
			var offsetX = 32;
			var offsetY = 64;
			var direction = $(item).attr("src").replace(".png", "").slice(-1);
			if(direction == "t") {	//상단
				var offsetY = 0;
			} else if(direction == "r") {	//오른쪽
				var offsetX = 64;
				var offsetY = 32;
			} else if(direction == "l") {	//왼쪽
				var offsetX = 0;
				var offsetY = 32;
			}

			//indicator 위치로 보정하여 저장
			posX = parseInt(posX) + offsetX;
			posY = parseInt(posY) + offsetY;

			var param = {domainIdx : domainIdx , brandIdx : brandIdx, floorIdx : floorIdx, francIdx : francIdx, deviceIdx : deviceIdx, posX : posX, posY : posY, direction : direction};
			params.push(param);
		});

		var jsonData = JSON.stringify(params);
		var result = $.ajaxUtil.ajaxArray('<c:url value="/FloorManagement/FloorKioskUpdate"/>', {"jsonData" : jsonData});

		if (result.resultCode == 0) {
			$.modalCommon.alertView('단말정보를 저장하였습니다.');

			//단말 정보 리셋
			$(".draggable").each(function(index, item){
				$(item).remove();
			});

			makeFloorKioskListTable(true);
		}
	}

	//kiosk 선택 single 처리
	function makeOneSelectedRow() {
		var table = $('#kioskListTable').DataTable();
		$('#kioskListTable tbody').off('click');
		$('#kioskListTable tbody').on('click', 'tr', function (e) {
			var data = table.row(this).data();

			if ($(this).hasClass('selected')) {
				//textinput , button 선택시에는 선택 유지
				if(e.target.type != "text" && e.target.type != "button") {
					$(this).removeClass('selected');

					clearImgFilter();
				}
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');

				clearImgFilter();

				var selectedRowImg = "#" + data.francIdx + "_" + data.deviceIdx;
				var filterVal = "invert(75%)";
				$(selectedRowImg).css("filter", filterVal).css("webkitFilter", filterVal).css("mozFilter", filterVal).css("oFilter", filterVal).css("msFilter", filterVal);
			}
		});
	}

	//좌표적용 버튼 클릭 시 textinput 좌표 적용
	function applyKioskPosition(e) {
		var uniqueIdx = $(e).attr("id").replace("_btn", "");

		var inputPosX = "#" + uniqueIdx + "_x";
		var inputPosY = "#" + uniqueIdx + "_y";
		var inputDirection = "#" + uniqueIdx + "_d";

		//단말 이미지 포지션 및 방향 변경
		var kioskImg = "#" + uniqueIdx;
		
		//TODO : 포지션도 변경되어야 겠네....
		//20200413 단말방향에 따라 위치 보정
		var offsetX = 32;
		var offsetY = 64;
		var direction = $(inputDirection).val();
		if(direction == "t") {	//상단
			var offsetY = 0;
		} else if(direction == "r") {	//오른쪽
			var offsetX = 64;
			var offsetY = 32;
		} else if(direction == "l") {	//왼쪽
			var offsetX = 0;
			var offsetY = 32;
		}

		$(kioskImg).css("left", parseInt($(inputPosX).val()) - offsetX + "px");
		$(kioskImg).css("top", parseInt($(inputPosY).val()) - offsetY + "px");
		$(kioskImg).attr("src", changeKioskImg($(inputDirection).val()));

		clearImgFilter();

		var filterVal = "invert(75%)";
		$(kioskImg).css("filter", filterVal).css("webkitFilter", filterVal).css("mozFilter", filterVal).css("oFilter", filterVal).css("msFilter", filterVal);
	}

	//선택된 단말에 따라 그리드 row 선택 변경
	function changeSeletedRow(imgId) {
		var table = $('#kioskListTable').DataTable();
		table.$('tr.selected').removeClass('selected');

		$('#kioskListTable tbody tr').each(function(index, item) {
			var data = table.row(item).data();
			var rowId = data.francIdx + "_" + data.deviceIdx;

			//선택된 단말에 맞게 그리드 row를 선택
			if(rowId == imgId) {
				$(item).addClass('selected');
				return false;
			}
		});
	}

	//단말 이미지 필터 제거
	function clearImgFilter() {
		$("#kioskDiv img").each(function(index, item) {
			$(item).css({"filter" : "" , "webkitFilter" : "" , "mozFilter" : "" , "oFilter" : "" , "msFilter" : ""});
		});
	}

	//단말 방향에 따른 이미지 변경
	function changeKioskImg(direction) {
		var imageSrc = "/Content/images/indicator_current_location_b.png";

		switch(direction) {
			case 't' :
				imageSrc = "/Content/images/indicator_current_location_t.png";
				break;
			case 'r' :
				imageSrc = "/Content/images/indicator_current_location_r.png";
				break;
			case 'l' :
				imageSrc = "/Content/images/indicator_current_location_l.png";
				break;
			default :
				imageSrc = "/Content/images/indicator_current_location_b.png";
		}

		return imageSrc;
	}
</script>
