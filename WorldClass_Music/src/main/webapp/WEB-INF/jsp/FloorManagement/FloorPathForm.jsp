<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	#mapBackgroundImg {
		position : relative;
		text-align : center;
	}

	#pixel_canvas {
		border : 1px solid rgba(255, 0, 0, .5);
		position : absolute;
		width : 920px;
		height : 736px;
		top : 79px;
		left : 40px;
		cursor : pointer;
		z-index : 10000;
	}

	#pixel_canvas tr td{
		border : 1px dashed rgba(0, 0, 0, .2);
	}
	
	#kioskDiv {
		position : absolute;
		width : 920px;
		height : 736px;
		top : 79px;
		left : 40px;
	}
</style>
<div class="box box-widget" id="path">
	<div class="box-header">
		<div class="user-block under-line">지도 내 경로 관리</div>
	</div>
	<div class="box-body">
		<div class="pull-right">
			<b style="font-size:18px;vertical-horizontal:bottom;">그리드 설정 : </b>
			<input type="text" id="rows" class="autoNumber" maxlength="3" placeholder="" style="ime-mode:active;width:34px;height:34px;" value="80" /> X
			<input type="text" id="cols" class="autoNumber" maxlength="3" placeholder="" style="ime-mode:active;width:34px;height:34px;" value="80" />
			<button type="button" id="refresh" class="btn btn-primary"><i class="glyphicon glyphicon-plus mr-5"></i>적용</button>
		</div>
		<div>
			<img id="mapBackgroundImg" style=""></img>
		</div>
		<div id="kioskDiv"></div>
		<table class="table-grid-class" id="pixel_canvas"></table>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="save()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
</div>
<script type="text/javascript">
	var color = "#c9d9f2";
	var isClicked = false;

	$(function() {
		drawFloorMap();	//맵 이미지 그리기
		getMapPathData();	//맵 경로 설정 데이터
	});

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

	//맵 경로 설정 데이터
	function getMapPathData() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorPath"/>', param);

		if (result.resultCode == 0) {
			cleanGrid();	//그리드 clear

			//단말 이미지 그리기
			if(result.messages && result.messages != null) {
				drawKioskImg(result.messages.floorKioskList);
			}

			if(result.data != null && (result.data.rowCnt != null && result.data.rowCnt != 0) && (result.data.colCnt != null && result.data.colCnt != 0)) {
				$('#rows').val(result.data.rowCnt);
				$('#cols').val(result.data.colCnt);

				makeGrid(result.data.pathList);		//그리드 설정

			} else {
				makeGrid(false);
			}

			setGridEventHandler();	//그리드 이벤트
		}
	}

	//그리드 clear
	function cleanGrid(){
		$("#pixel_canvas tr").remove();
	}

	//그리드 만들기
	function makeGrid(pathList) {
		var rows = $('#rows').val();
		var cols = $('#cols').val();

		for(var x = 0; x < rows; x++){
			var htmlRowString = '<tr data-tr="' + x + '">';

			for(var y = 0; y < cols; y++){
				htmlRowString += '<td id="' + x + ',' + y + '" data-x="' + x + '" data-y="' + y + '"></td>';
			}

			htmlRowString += '</tr>';
			$('#pixel_canvas').append(htmlRowString);
		}

		//데이터가 있는 경우
		var pathListSplit = [];
		if(pathList) {
			pathListSplit = pathList.split("_");

			/*$("#pixel_canvas tr").each(function(row, tr) {
				var position = 0;

				//TODO....
				$(this).each(function(column, td) {
					if(row==0){console.log(pathListSplit[position] + " : " + (JSON.stringify($(this))));}
					if(pathListSplit[position] == $(td).attr("id")) {
						$(item).css('background-color', color);
						$(item).addClass("selected");

						position++;
					}
				});
			});*/

			for (var i = 0; i < pathListSplit.length; i++) {
				$("#pixel_canvas td").each(function(index, item) {
					if(pathListSplit[i] == $(item).attr("id")) {
						$(item).css('background-color', color);
						$(item).addClass("selected");
						return false;
					}
				});
			}
		}
	}

	//단말 이미지 그리기
	function drawKioskImg(kioskList) {
		var imageSrc = "/Content/images/indicator_current_location_b.png";

		$.each($.parseJSON(kioskList), function(index, item) {
			//20200413 단말방향에 따라 위치 보정
			var offsetX = 32;
			var offsetY = 64;
			if(item.direction == "t") {	//상단
				var offsetY = 0;
				imageSrc = "/Content/images/indicator_current_location_t.png";
			} else if(item.direction == "r") {	//오른쪽
				var offsetX = 64;
				var offsetY = 32;
				imageSrc = "/Content/images/indicator_current_location_r.png";
			} else if(item.direction == "l") {	//왼쪽
				var offsetX = 0;
				var offsetY = 32;
				imageSrc = "/Content/images/indicator_current_location_l.png";
			}

			var createdImg = $("<img />", {
				id : item.francIdx + "_" + item.deviceIdx,
				src : imageSrc,
				css : {
					position : "absolute",
					top : item.posY - offsetY,
					left : item.posX - offsetX,
					width : "64px",
					height : "64px",
					zIndex : "5000",
					border : "1px solid #000000"
				},
				appendTo : '#kioskDiv'
			});

			$(createdImg).data("francIdx", item.francIdx);
			$(createdImg).data("deviceIdx", item.deviceIdx);
		});
	}
	//그리드 픽셀 색상
	function makePixel(el) {
		var element = $(el.target);

		if(element.hasClass("selected")) {
			element.css('background-color', "");
			element.removeClass("selected");
		} else {
			element.css('background-color', color);
			element.addClass("selected");
		}
	}

	//그리드 이벤트
	function setGridEventHandler() {
		$("#pixel_canvas").on("click", "td", function(e) {
			makePixel(e);
			isClicked = false;
		}).on("dblclick", "td", function(e) {
			$(e.target).css('background-color', '');
			$(e.target).removeClass("selected");
		}).on("mousedown", function(e) {
			e.preventDefault();
			isClicked = true;
		}).on("mouseover", "td", function(e) {
			if(isClicked){
				makePixel(e);
			}
		});

		$(document).on('mouseup', function() {
			isClicked = false;
		});

		$("#refresh").on("click", function() {
			cleanGrid();
			makeGrid(false);
		});
	}

	//저장
	function save() {
		//그리드에서 선택된 픽셀 추출 , ex)0,1_0,2_1,3 형태로 저장
		var pixelData = "";
		$('#pixel_canvas tr').each(function(row, tr) {
			$(this).find('td').each(function(column, td) {
				if($(td).hasClass("selected")) {
					if(pixelData != "") {
						pixelData = pixelData + "_";
					}
					pixelData = pixelData + $(td).data('x') + "," + $(td).data('y');
				}
			});
		});

		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var floorIdx = $("#floorIdx").val();
		var rowCnt = $('#rows').val();
		var colCnt = $('#cols').val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx, rowCnt : rowCnt, colCnt : colCnt, pathList : pixelData};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorMappathUpdate"/>', param);

		if (result.resultCode == 0) {
			commMessage($("#floorForm #floorIdx").val() == ''? 'C' : 'U' ,'층 경로');
			//makeFloorListTable();
		}
	}
</script>
