<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#facilities, #facilities_path {
	float : left;
	height : 100%;
}

#facilities {
	width : 28%;
	margin: 10px;
}

#facilities_path {
	width : 67%;
	margin: 10px;
}

#facilitiesListTable tr {
	cursor : pointer;
}

#mapBackgroundImg {
	position : relative;
	text-align : center;
}

#facilitiesDiv {
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
<div class="box box-widget" id="facilities">
	<div class="box-header">
		<div class="user-block under-line">포함된 편의시설</div>
	</div>
	<div class="box-body">
		<div class="pull-right">
			<button type="button" class="btn btn-primary" onclick="addFloorFacilities();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>추가</button>
		</div>
		<div style="margin : 40px 0 0 0;">
			<table id="facilitiesListTable" class="table table-striped">
				<thead>
					<tr>
						<th>편의시설명</th>
						<th>좌표(x)</th>
						<th>좌표(y)</th>
						<th>좌표적용</th>
						<th>삭제</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- div>현재 마우스 좌표 : <span style="color:red;" id="mouseX"></span> , <span style="color:blue;" id="mouseY"></span> </div>
		<div>이미지 포지션 좌표 : <span style="color:red;" id="divMouseX"></span> , <span style="color:blue;" id="divMouseY"></span> </div-->
	</div>
</div>
<div class="box box-widget" id="facilities_path">
	<div class="box-header">
		<div class="user-block under-line">위치관리</div>
	</div>
	<div class="box-body" id="mapImg" style="overflow:auto; width:100%; height:100%;">
		<div>
			<img id="mapBackgroundImg"></img>
		</div>
		<div id="facilitiesDiv"></div>
	</div>
</div>
<div class="modal-footer">
	<!-- button type="button" class="btn btn-warning" style="float : left;" onclick="toggleFacilitiesDivBorder()" id="toggleBtn">Toggle</button-->
	<button type="button" class="btn btn-primary" onclick="save()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>

<script type="text/javaScript">
	$(function() {
		$("#facilitiesDiv").mousemove(function(event) {
			var clickX = Math.round(event.pageX - $(this).offset().left);
			var clickY = Math.round(event.pageY - $(this).offset().top);

			//$("#mouseX").text(clickX);
			//$("#mouseY").text(clickY);
		});

		makeFloorFacilitiesListTable(true);
		drawFloorMap();

		//facilities 선택 single 처리
		makeOneSelectedRow();

		//맵 이미지 클릭 시 이미지 생성
		$('#facilitiesDiv').on('click', function(e) {
			var clickX = Math.round(e.pageX - $(this).offset().left);
			var clickY = Math.round(e.pageY - $(this).offset().top);

			var rowData = $('#facilitiesListTable').DataTable().row('.selected').data();

			if(rowData == null || rowData == '') {
				$.modalCommon.alertView('편의시설을 선택해 주세요');
				return false;
			} else {
				if((rowData.posX != null && rowData.posX != "0") && (rowData.posY != null && rowData.posY != "0")) {
					$.modalCommon.alertView('이미 적용된 편의시설 입니다.');
					return false;
				} else {
					var isApplied = false;

					//적용되지 않은 편의시설 확인
					$(".draggable").each(function(index, item) {
						var rowDataId = rowData.facilityType + "_" + rowData.orderSeq;

						if($(item).attr("id") == rowDataId) {
							isApplied = true;

							$.modalCommon.alertView('이미 적용된 편의시설 입니다.');
							return false;
						}
					});

					if(!isApplied) {
						createFacilitiesImg(rowData.facilityType, rowData.orderSeq, rowData.codeName, clickX, clickY, "ACTIVE");
					}
				}
			}
		});
	});

	//층 별 편의시설 리스트
	function makeFloorFacilitiesListTable(refreshDivContent) {
		var optObject = {};

		optObject.id = "#facilitiesListTable";
		optObject.url = '<c:url value="/FloorManagement/FloorFacilitiesList"/>';

		//데이터 필드 정보
		optObject.arrColumns = [
			{data: "codeName"},
			{data: "posX"},
			{data: "posY"},
			{data: "facilityType"},
			{data: "facilityType"}
		];

		//데이터 필드 속성 정보
		optObject.arrColumnDefs = [
			{
				'targets' : [0],
				'width' : '40%',
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<div class="ellipsis130">'+fnDataTableRenderText(data)+'</div>';
				}
			}, {
				'targets' : [1],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.facilityType + '_' + full.orderSeq + '_x" class="" value="' + full.posX + '" style="width:34px;height:34px;" maxlength="4"'
						+ 'onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [2],
				'className' : "text-center",
				'orderable' : false,
				'render' : function (data, type, full, meta) {
					return '<input type="text" id="' + full.facilityType + '_' + full.orderSeq + '_y" class="" value="' + full.posY + '" style="width:34px;height:34px;" maxlength="4"'
						+ 'onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');" />';
				}
			}, {
				'targets' : [3],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-success" style="width:34px;" data-posx="' + full.posX  + '" data-posy="' + full.posY +
						'" data-facilitytype="' + full.facilityType + '"onclick="applyFacilitiesPosition(this);" id="' + full.facilityType + '_' + full.orderSeq + '_btn">O</button>';
				}
			}, {
				'targets' : [4],
				'orderable' : false,
				'data' : 'idx',
				'className' : 'text-center',
				'render' : function (data, type, full, meta) {
					return '<button type="button" class="btn btn-danger" style="width:34px;" data-posx="' + full.posX  + '" data-posy="' + full.posY +
						'" data-facilitytype="' + full.facilityType  + '" onclick="deleteFloorFacilities(\'' + full.facilityType + '\',\'' + full.orderSeq + '\');" id="">X</button>';
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

		//facilities 선택 single 처리
		deactiveImg();	//편의시설 이미지 DEACTIVE
		makeOneSelectedRow();	//그리드 단일 선택만 가능하도록

		//위치관리 data create
		if(refreshDivContent) {
			drawFacilitiesInfo();
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
	function drawFacilitiesInfo() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorFacilitiesList"/>', param);

		if (result.resultCode == 0) {
			if(result.data != null) {
				$.each(result.data, function(key, item){
					var facilityType = item.facilityType;
					var orderSeq = item.orderSeq;
					var codeName = item.codeName;
					var left = item.posX;
					var top = item.posY;

					//포지션이 0, 0 인것은 제외하고 생성
					if((left != null && left != "0") && (top != null && top != "0")) {
						createFacilitiesImg(facilityType, orderSeq, codeName, left, top, "DEACTIVE");
					}
				});
			}
		}
	}

	//편의시설 이미지 생성
	function createFacilitiesImg(facilityType, orderSeq, codeName, posX, posY, imageType) {
		var imageSrc = "";

		if(imageType == "ACTIVE") {
			imageSrc = "/Content/images/" + facilityType + ".png";
		} else if (imageType == "DEACTIVE") {
			imageSrc = "/Content/images/" + facilityType +"_DEACTIVE" + ".png";
		} else {
			imageSrc = "/Content/images/indicator_destination.png";
		}

		var createdImg = $("<img />", {
			id : facilityType + "_" + orderSeq,
			src : imageSrc,
			"class" : "draggable",
			css : {
				position : "absolute",
				top : posY - 18,	//위치보정하여 표기
				left : posX - 18,
				width : "36px",
				height : "36px",
				cursor : "pointer",
				zIndex : "10000",
				border : "1px solid #eaeaea"
			},
			appendTo : '#facilitiesDiv'
		});

		//데이터 매핑
		$(createdImg).data("facilityType", facilityType);
		$(createdImg).data("orderSeq", orderSeq);
		$(createdImg).data("posX", posX);
		$(createdImg).data("posY", posY);

		//최초 적용되는 편의시설 Active 처리
		if(imageType == "ACTIVE") {
			//이미지 위치에 따라 textinput 값 적용
			var inputPosX = "#" + facilityType + "_" + orderSeq + "_x";
			var inputPosY = "#" + facilityType + "_" + orderSeq + "_y";

			$(inputPosX).val(posX);
			$(inputPosY).val(posY);
		}

		$(".draggable").draggable( {
			start : function(e) {
				deactiveImg();

				var rowData = $('#facilitiesListTable').DataTable().row('.selected').data();
				var imgId = $(e.target).attr("id");

				//선택된 단말이미지 필터 적용
				if(rowData != null && rowData != undefined) {
					var currentSelectedRowId = rowData.facilitiyType + "_" + rowData.orderSeq;

					if(imgId != currentSelectedRowId) {
						changeSeletedRow(imgId);
					}
				} else {
					changeSeletedRow(imgId);
				}

				$("#" + imgId).attr("src", "/Content/images/" + $(e.target).data('facilityType') + ".png");
			},
			drag: function(e) {
				var mapOffset = $('#facilitiesDiv').offset();

				var offset = $(this).offset();
				var xPos = offset.left - mapOffset.left;
				var yPos = offset.top - mapOffset.top;

				//$('#divMouseX').text(xPos);
				//$('#divMouseY').text(yPos);

				//편의시설위치 input text에 반영
				var imgId = $(e.target).attr("id");
				var selectedInputPosX = "#" + imgId + "_x";
				var selectedInputPosY = "#" + imgId + "_y";

				var currentPosX = parseInt($(e.target).css("left").replace(/[^-\d\.]/g, '')) + 18;
				var currentPosY = parseInt($(e.target).css("top").replace(/[^-\d\.]/g, '')) + 18;

				$(selectedInputPosX).val(currentPosX);
				$(selectedInputPosY).val(currentPosY);
			}
		});
	}

	//층 편의시설 삭제
	function deleteFloorFacilities(facilityType, orderSeq) {
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

				var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, facilityType : facilityType, orderSeq : orderSeq};
				var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorFacilitiesDelete"/>', param);

				if (result.resultCode == 0) {
					$.modalCommon.alertView('편의시설 정보를 삭제하였습니다.');

					//편의시설 정보 리셋
					$(".draggable").each(function(index, item) {
						$(item).remove();
					});

					makeFloorFacilitiesListTable(true);
				}
			}
		});
	}

	//층 편의시설정보 추가 팝업
	function addFloorFacilities() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		$.modalCommon.loadFullDataView('편의시설 추가', '<c:url value="/FloorManagement/FacilitiesAddForm"/>', param);
	}

	//층 편의시설 정보 저장
	function save() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var params = [];

		$(".draggable").each(function(index, item){
			var facilityType = $(item).data("facilityType");
			var orderSeq = $(item).data("orderSeq");
			var posX = $(item).css("left").replace("px", "").replace(/[^-\d\.]/g, '');;
			var posY = $(item).css("top").replace("px", "").replace(/[^-\d\.]/g, '');;

			//이미지 센터로 위치 보정
			posX = parseInt(posX) + 18;
			posY = parseInt(posY) + 18;

			var param = {domainIdx : domainIdx , brandIdx : brandIdx, floorIdx : floorIdx, facilityType : facilityType, orderSeq : orderSeq, posX : posX, posY : posY};
			params.push(param);
		});

		var jsonData = JSON.stringify(params);
		var result = $.ajaxUtil.ajaxArray('<c:url value="/FloorManagement/FloorFacilitiesUpdate"/>', {"jsonData" : jsonData});

		if (result.resultCode == 0) {
			$.modalCommon.alertView('편의시설 정보를 저장하였습니다.');

			//편의시설 정보 리셋
			$(".draggable").each(function(index, item){
				$(item).remove();
			});

			makeFloorFacilitiesListTable(true);
		}
	}

	//facilities 선택 single 처리
	function makeOneSelectedRow() {
		var table = $('#facilitiesListTable').DataTable();
		$('#facilitiesListTable tbody').off('click');
		$('#facilitiesListTable tbody').on('click', 'tr', function (e) {
			var data = table.row(this).data();

			if ($(this).hasClass('selected')) {
				//textinput , button 선택시에는 선택 유지
				if(e.target.type != "text" && e.target.type != "button") {
					$(this).removeClass('selected');

					deactiveImg();
				}
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');

				deactiveImg();

				var selectedRowImg = "#" + data.facilityType + "_" + data.orderSeq;
				var imageSrc = "/Content/images/" + data.facilityType + ".png";

				$(selectedRowImg).attr("src", imageSrc);
			}
		});
	}

	//좌표적용 버튼 클릭 시 textinput 좌표 적용
	function applyFacilitiesPosition(e) {
		var uniqueIdx = $(e).attr("id").replace("_btn", "");

		var inputPosX = "#" + uniqueIdx + "_x";
		var inputPosY = "#" + uniqueIdx + "_y";

		//단말 이미지 포지션 변경
		var facilitiesImg = "#" + uniqueIdx;
		$(facilitiesImg).css("left", parseInt($(inputPosX).val()) - 18 + "px");
		$(facilitiesImg).css("top", parseInt($(inputPosY).val()) - 18 + "px");

		deactiveImg();

		var selectedRowImg = "#" + uniqueIdx;
		var imageSrc = "/Content/images/" + $(e).data('facilitytype') + ".png";

		$(selectedRowImg).attr("src", imageSrc);
	}

	//선택된 편의시설에 따라 그리드 row 선택 변경
	function changeSeletedRow(imgId) {
		var table = $('#facilitiesListTable').DataTable();
		table.$('tr.selected').removeClass('selected');

		$('#facilitiesListTable tbody tr').each(function(index, item) {
			var data = table.row(item).data();
			var rowId = data.facilityType + "_" + data.orderSeq;

			//선택된 편의시설에 맞게 그리드 row를 선택
			if(rowId == imgId) {
				$(item).addClass('selected');
				return false;
			}
		});
	}

	//이미지 비활성화
	function deactiveImg() {
		$("#facilitiesDiv img").each(function(index, item) {
			var deactiveImgSrc = "/Content/images/" + $(item).data('facilityType') + "_DEACTIVE.png";
			$(item).attr("src", deactiveImgSrc);
		});
	}
</script>
