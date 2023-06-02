<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.col-title-area{
		width:19%;
		display:inline;
		padding-top:6px;
		padding-bottom:6px;
		float:left;
	}
	
	.col-input-area{
		width:79%;
		display:inline;
		padding-top:3px;
		padding-bottom:3px;
		float:left;
	}
	
	.bgColorSelect {
		position:relative; 
		line-height:35px;
	}
	
	.bgColorSelect > a {
		display:block; 
		border:1px solid #ccc; 
		padding:0 8px; 
		overflow:hidden;
		border-radius: 5px;
	}
	.bgColorSelect > a:after,.bgColorSelect > ul > li:first-child:after {
		display:block; 
		float:right;
	}
	.bgColorSelect > a:after {
		content:'▼';
	}
	.bgColorSelect > ul {
		position:absolute; 
		width:100%; 
		top:1px; 
		background:#fff; 
		display:none;
		z-index:999;
	}
	.bgColorSelect > ul > li {
		cursor:pointer; 
		padding:0 8px; 
		border:1px solid #ccc;
		border-top:0;
	}
	.bgColorSelect > ul > li:first-child:after {
		content:'▲';
	}

</style>

<div class="modal-body" id="barledForm">
	<form:form commandName="BarLedVo" name="barledForm" id="barledForm" method="post">
		<form:hidden path="domainIdx"/>
		<form:hidden path="brandIdx"/>
		<form:hidden path="francIdx"/>
		<form:hidden path="mqIdx"/>
		<div style="float: left;width:70%; display:inline;">
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>마퀴 텍스트<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<form:input path="mqText" class="form-control" style="ime-mode: active" maxlength="1000" placeholder="마퀴 텍스트" onkeyup="Preview()"/>
				</div>
			</div>
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>글꼴</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<select id="font" class="form-control selectBox" onchange="Preview()">
						<option value="NanumGothic" <c:if test="${MarqueeVo.mqFont eq 'NanumGothic'}">selected</c:if>>고딕체</option>
						<option value="RidiBatang" <c:if test="${MarqueeVo.mqFont eq 'RidiBatang'}">selected</c:if>>바탕체</option>
						<option value="Moebius" <c:if test="${MarqueeVo.mqFont eq 'Moebius'}">selected</c:if>>제목체</option>
						<option value="Gungsuh" <c:if test="${MarqueeVo.mqFont eq 'Gungsuh'}">selected</c:if>>궁서체</option>
						<option value="TheJung160" <c:if test="${MarqueeVo.mqFont eq 'TheJung160'}">selected</c:if>>굵은고딕체</option>
					</select>
				</div>
			</div>
			
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>크기</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<select id="size" class="form-control selectBox" onchange="Preview()">
						<option value="S" <c:if test="${BarLedVo.mqSize eq 'S'}">selected</c:if>>S</option>
						<option value="M" <c:if test="${BarLedVo.mqSize eq 'M'}">selected</c:if>>M</option>
						<option value="L" <c:if test="${BarLedVo.mqSize eq 'L'}">selected</c:if>>L</option>
					</select>
				</div>
			</div>
			
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>글꼴 색상</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<%-- <form:input type="color" path="mqTextColor" class="form-control" style="ime-mode: active" maxlength="50" onchange="Preview()"/> --%>
					<select id="mqTextColor" style="background-color:#000000; color:#FFFFFF;" class="form-control selectBox" onchange="Preview()">
						<option value="#000000" style="background-color:#000000; color:#FFFFFF;" <c:if test="${BarLedVo.mqTextColor eq '#000000'}">selected</c:if>>#000000</option>
						<option value="#FFFFFF" style="background-color:#FFFFFF; color:#000000;" <c:if test="${BarLedVo.mqTextColor eq '#FFFFFF'}">selected</c:if>>#FFFFFF</option>
						<option value="#3A3A3A" style="background-color:#3A3A3A; color:#FFFFFF;" <c:if test="${BarLedVo.mqTextColor eq '#3A3A3A'}">selected</c:if>>#3A3A3A</option>
						<option value="#CCCCCC" style="background-color:#CCCCCC; color:#000000;" <c:if test="${BarLedVo.mqTextColor eq '#CCCCCC'}">selected</c:if>>#CCCCCC</option>
					</select>
				</div>
			</div>
			
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>배경 색상</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<div class="bgColorSelect">
					    <a ot="#FFFFFF" style="background-color:#FFFFFF; color:#000000;">#FFFFFF</a>
					    <ul id="bgColorSelect">
					    	<li ot="#000000" style="background-color:#000000; color:#FFFFFF;">#000000</li>
					        <li ot="#FFFFFF" style="background-color:#FFFFFF; color:#000000;">#FFFFFF</li>
					        <li ot="#3D3D3D" style="background-color:#3D3D3D; color:#FFFFFF;">#3D3D3D</li>
					        <li ot="#2D2D2D" style="background-color:#2D2D2D; color:#FFFFFF;">#2D2D2D</li>
					        <li ot="#C1C1C1" style="background-color:#C1C1C1; color:#000000;">#C1C1C1</li>
					        <li ot="IMG_SKY" style="background-image:url('<c:url value="/Content/images/YGImage/sky.jpg"/>'); color:#000000;">맑은하늘</li>
					        <li ot="IMG_SMOKE" style="background-image:url('<c:url value="/Content/images/YGImage/smoke.jpg"/>'); color:#FFFFFF;">블랙스모크</li>
					        <li ot="IMG_NIGHT" style="background-image:url('<c:url value="/Content/images/YGImage/night.jpg"/>'); color:#FFFFFF;">밤하늘</li>
					        <li ot="IMG_GRADATION" style="background-image:url('<c:url value="/Content/images/YGImage/gradation.jpg"/>'); color:#FFFFFF;">그라데이션</li>
					    </ul>
					</div>
				</div>
			</div>
			
			<div style="display:none; clear: both; padding-bottom: 15px;"></div>
			<div style="display:none;">
				<div class="col-title-area" style="width:25%;">
					<label>모션</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<select id="motion" class="form-control selectBox" onchange="Preview()">
						<option value="left" <c:if test="${BarLedVo.mqMotion eq 'left'}">selected</c:if>>우에서 좌</option>
						<option value="right" <c:if test="${BarLedVo.mqMotion eq 'right'}">selected</c:if>>좌에서 우</option>
						<option value="fix" <c:if test="${BarLedVo.mqMotion eq 'fix'}">selected</c:if>>고정</option>
					</select>
				</div>
			</div>
			
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>정렬순서</label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<form:input path="orderSeq" id="orderSeq" cssClass="form-control" cssStyle="ime-mode:active;" placeholder="정렬순서" />
				</div>
			</div>
			
			<div style="clear: both; padding-bottom: 15px;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>활성화</label>
				</div>
				<div class="col-input-area" style="width:75%;">
					<input type="checkbox" data-group-cls="" id="activeYn" value="Y" data-reverse checked><br/>
				</div>
			</div>
		</div>
		
		<div style="float: left;width:30%;display:inline;">
			<label>적용 단말 목록<font class="fontSet">[필수]</font></label>
			<select id="deviceType" class="form-control selectBox" onchange="searchDeviceTree()">
				<option value="">단말 종류 전체</option>
				<c:forEach items="${brandDeviceTypeList}" var="list">
					<option value="${list.deviceType}"><c:out value="${list.deviceTypeName}" /></option>
				</c:forEach>
			</select>
			<select id="deviceResType" class="form-control selectBox" onchange="searchDeviceTree()">
				<option value="">해상도 전체</option>
				<c:forEach items="${deviceResTypeList}" var="list">
					<option value="${list.codeValue}"><c:out value="${list.codeName}" /></option>
				</c:forEach>
			</select>
			<div style="height:355px; border-color:#d2d6de; border-style:solid; overflow: auto;" id="targetTreeList" ></div>
		</div>
		
		<div style="clear: both;"></div>
		<div>
			<div class="col-title-area" style="width:25%;">
				<label>미리보기</label>
			</div>
			<div id="previewDiv" style="float: left;width:100%;display:inline;padding-bottom: 5px; background-color: #FFFFFF; height: 75px; border:1px dotted #000000;">
			</div>
		</div>
			
		<div style="clear: both;"></div>
		<br/>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
			<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
		</div>
	</form:form>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog').css('width', '1200px');

		
		$('#activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		if ("${BarLedVo.activeYn}" == "N"){
			$('#activeYn').prop("checked", false);
		}
		else{
			$('#activeYn').prop("checked", true);
		}
		
		if($('#barledForm #mqIdx').val() != 0) {
			if ("${BarLedVo.mqBgColor}".indexOf('IMG') == -1) {
				$('div.bgColorSelect').children('a').css('background-color', $('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').css('background-color'));
				$('div.bgColorSelect').children('a').css('color', $('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').css('color'));
				$('div.bgColorSelect').children('a').attr('ot', "${BarLedVo.mqBgColor}");
				$('div.bgColorSelect').children('a').text($('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').text());
			}
			else {
				$('div.bgColorSelect').children('a').css('background-image', $('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').css('background-image'));
				$('div.bgColorSelect').children('a').css('color', $('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').css('color'));
				$('div.bgColorSelect').children('a').attr('ot', "${BarLedVo.mqBgColor}");
				$('div.bgColorSelect').children('a').text($('#bgColorSelect').find('li[ot="${BarLedVo.mqBgColor}"]').text());
			}
			Preview();
		}
		
		MarqueeTree();
		
		BgColorSelect();
		
		$(document).on('click', function(e) {
			if($(e.target).prop('tagName').toLowerCase() != 'li') {
				$('#bgColorSelect').css('display', 'none');
			}
		});
		
		$("#orderSeq").on("keyup", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		});
	});
	
	function BgColorSelect() {
		$("div.bgColorSelect > a").click(function() {
		    $(this).next("ul").toggle();
		    return false;
		});
		 
		$("div.bgColorSelect > ul > li").click(function() {
		    $(this).parent().hide().parent("div.bgColorSelect").children("a").text($(this).text());
		    //$(this).prependTo($(this).parent());
		    $(this).parent().hide().parent("div.bgColorSelect").children("a").attr('ot', $(this).attr('ot'));
		    if ($(this).attr('ot').indexOf('IMG') == -1) {
		    	var bgColor = $(this).css('background-color');
		    	var fontColor = $(this).css('color');
		    	$(this).parent().hide().parent("div.bgColorSelect").children("a").css('background-color', bgColor).css('color', fontColor);
		    	$(this).parent().hide().parent("div.bgColorSelect").children("a").css('background-image', '');
			}
		    else {
		    	var bgImg = $(this).css('background-image');
		    	var fontColor = $(this).css('color');
		    	$(this).parent().hide().parent("div.bgColorSelect").children("a").css('background-image', bgImg).css('color', fontColor);
		    }
		    
		    Preview();
		});
	}
	
	function Preview() {
		var mqText = $('#mqText').val();
		var font = $('#font').val();
		var size = $('#size').val();
		var motion = $('#motion').val();
		var mqTextColor = $('#mqTextColor').val();
		var mqBgColor = $('#mqBgColor').val();
		
		var textColor = $('#mqTextColor').find('option[value="'+mqTextColor+'"]');
		textColor = textColor.css('color');
		$('#mqTextColor').css('background-color', mqTextColor).css('color', textColor);
		
		if($('div.bgColorSelect').children('a').attr('ot').indexOf('IMG') == -1) {
			$('#previewDiv').css('background-color', $('div.bgColorSelect').children('a').css('background-color'));
			$('#previewDiv').css('background-image', '');
		}
		else {
			$('#previewDiv').css('background-image', $('div.bgColorSelect').children('a').css('background-image'));
		}
		
		var fontSize = '20';

		switch (size) {
		case 'S':
			fontSize = '20';
			break;
		case 'L':
			fontSize = '28';
			break;
		default:
			fontSize = '24';
			break;
		}
		
		if(font == 0) font = 'NanumGothic';
		if(motion == 0) motion = 'left';
		
		var marquee;
		if (motion == 'fix') {
			marquee = '<span style="line-height:'+$('#previewDiv').css('height')+'; width:100%; color:'+mqTextColor+'; font-size:'+fontSize+'px; font-family:'+font+'">'+mqText+'</span>';
		}
		else {
			marquee = '<marquee direction="'+motion+'" style="line-height:'+$('#previewDiv').css('height')+'; width:100%; color:'+mqTextColor+'; font-size:'+fontSize+'px; font-family:'+font+'">'+mqText+'</marquee>';
		}
		
		$('#previewDiv').html(marquee);
	}
	
	function Save() {
		var mqText = $('#mqText').val();
		var font = $('#font').val();
		var size = $('#size').val();
		var motion = $('#motion').val();
		var mqTextColor = $('#mqTextColor').val();
		var mqBgColor = $('div.bgColorSelect').children('a').attr('ot');
		var orderSeq = $('#orderSeq').val();
		var active;
		
		var deviceList = [];
		$.each($('#targetTreeList').jstree('get_selected', true), function ()
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				deviceList.push(this.li_attr.deviceidx);
	    });

		if(!$.formCheck('#barledForm #mqText', 'Y', 1000, 'N', '마퀴 텍스트'))
			return;
		
		if(deviceList.length == 0){
			$.modalCommon.alertView("1개 이상의 단말이 선택되어야 합니다.", null, null, null);
			return;
		}
		
		if ($('#activeYn').prop('checked')) {
			active = 'Y';
		} else {
			active = 'N';
		}
		
		if (font == 0) font = 'NanumGothic';
		if (size == 0) size = 'M';
		if (motion == 0) motion = 'left';
		if (mqTextColor == 0) mqTextColor = '#000000';
		
		
		var params = {
				domainIdx : $('#user #domainIdx').val(),
				brandIdx : $('#user #brandIdx').val(),
				francIdx : $('#user #francIdx').val(),
				mqIdx : parseInt($('#barledForm #mqIdx').val()),
				mqText : mqText,
				mqFont : font,
				mqSize : size,
				mqMotion : motion,
				mqTextColor : mqTextColor,
				mqBgColor : mqBgColor,
				activeYn : active,
				deviceList : deviceList,
				orderSeq : orderSeq
			};
		
		var result;
		var msg = '저장하시겠습니까?';
		var endMsg = '저장되었습니다.';
		var url;

		if ($('#barledForm #mqIdx').val() == 0) {
			url = '<c:url value="/BarLed/Create"/>';
		} else {
			url = '<c:url value="/BarLed/Update"/>';
		}

		BootstrapDialog.confirm({
			title : '',
			message : msg,
			type : BootstrapDialog.TYPE_WARNING,
			closable : false,
			draggable : true,
			btnCancelLabel: '취소',
		    btnOKLabel: '저장',
			btnOKClass : 'btn-warning',
			callback : function(confirmResult) {
				if (confirmResult) {
					result = $.ajaxUtil.ajaxArray(url, params);

					if(result.resultCode == 0){
						$.modalCommon.close();
						$.modalCommon.alertView(endMsg, null, null, null);

						MarqueeList();
					}
				}
			}
		});
	}
	
	function searchDeviceTree(){
		var param = {
				domainIdx : $("#barledForm #domainIdx").val(),
		   		brandIdx : $("#barledForm #brandIdx").val(),
		   		francIdx : $("#barledForm #francIdx").val(),
		   		mqIdx : $("#barledForm #mqIdx").val(),
		   		searchDvType: $('#barledForm #deviceType').val(),
		   		searchDvRes: $('#barledForm #deviceResType').val(),
		   		searchDvSite: ""
		}

		$('#barledForm #targetTreeList').jstree('destroy');
		treeSearchList(0,'<c:url value="/BarLed/SearchKioskTree"/>',param);
	}
	
	function MarqueeTree(){
		var param = {
				domainIdx : $("#barledForm #domainIdx").val(),
		   		brandIdx : $("#barledForm #brandIdx").val(),
		   		francIdx : $("#barledForm #francIdx").val(),
		   		mqIdx : $("#barledForm #mqIdx").val(),
		   		searchDvType: $('#barledForm #deviceType').val(),
		   		searchDvRes: $('#barledForm #deviceResType').val(),
		   		searchDvSite: ""
		}

		treeSearchList(0,'<c:url value="/BarLed/SearchKioskTree"/>',param);
	}
</script>