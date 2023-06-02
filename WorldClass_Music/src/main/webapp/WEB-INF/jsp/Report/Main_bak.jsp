<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
		padding: 7px;
		vertical-align: middle;
	}
	.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
		vertical-align: middle;
	}

	.dropDown {position:relative; display:inline-block; *display:inline; *zoom:1;}
	.btn_drop {background:#767676; color:#fff; padding:5px 10px; border-radius:5px;}
	.dropBox {position:absolute; z-index:1; height:0; overflow:hidden; width:200px; }
	.dropBox.on {width:200px; height:200px; z-index:999; padding:10px 0; border:1px solid #ddd; overflow:auto; background:#fff;}
	.dropBox ul li:hover,
	.dropBox ul li.on {background:#eee;}
	.dropBox ul li label,.dropBox ul li a {display:block; padding:0 10px;}
	.dropBox ul li label input {margin-right:5px;}
	.btn_save {display:block; margin:10px auto 0; border:1px solid #ccc; padding:5px;}
	.btn_close {display:block; height:0; overflow:hidden;}
	.on .btn_close,
	.on .btn_close_check {
	    position:fixed;
	    z-index:-1;
	    left:0;
	    top:0;
	    width:100%;
	    height:100%;
	    cursor:default;
	    text-indent:-99999px;
	}
</style>

<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">매출 보고서</div>
	</div>
	<form:form commandName="ReportVo" name="user" id="user" method="post">
		<form:hidden path="domainIdx" />
		<form:hidden path="brandIdx" />
		<form:hidden path="francIdx" />
		<form:hidden path="reportType" />
		<form:hidden path="searchType" />
		<form:hidden path="searchStartDate" />
		<form:hidden path="searchEndDate" />
		<form:hidden path="checkboxFrancArr" />
		<form:hidden path="brdName" />
	</form:form>
	<div class="box-body">
	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 form-inline ">
		<table>
			<tr>
				<td>
					<label style="margin:0px;">가맹점<font class="fontSet">[필수]</font></label>
				</td>
				<td>
					<!-- Checkbox Drop Down -->
					<div class="dropDown">
					    <a href="#" class="btn_drop">가맹점 선택</a>
					    <div class="dropBox">
					        <ul id="francList"></ul>
					        <a href="#" class="btn_close_check">닫기</a>
					    </div>
					</div>
					<!-- //Checkbox Drop Down -->
				</td>
			</tr>
			<tr>
				<td>
					<label class="lb" style="margin:0px;">보고서 종류 <font class="fontSet">[필수]</font> </label>
				</td>
				<td>
					<select id="localSearchType" name="localSearchType" class="form-control">
						<option value="MON">월별</option>
						<option value="DAY">일별</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lb" style="margin:0px;">조회 시작 <font class="fontSet">[필수]</font> </label>
				</td>
				<td>
					<div class="input-group date" id="startDayDiv" style="display: none;">
						<input id="startDate" class="form-control" style="background-color: #fff" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>

					<div class="input-group date" id="startMonthDiv">
						<input id="startMonth" class="form-control" style="background-color: #fff" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>


				</td>
			</tr>
			<tr>
				<td>
					<label class="lb" style="margin:0px;">조회 종료 <font class="fontSet">[필수]</font> </label>
				</td>
				<td>
					<div class="input-group date" id="endDayDiv" style="display: none;">
						<input id="endDate" class="form-control" style="background-color: #fff" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>

					<div class="input-group date" id="endMonthDiv">
						<input id="endMonth" class="form-control" style="background-color: #fff" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>

				</td>
			</tr>

<!-- 			<tr> -->
<!-- 				<td> -->
<!-- 					<label style="margin:0px;">결재수단</label> -->
<!-- 				</td> -->
<!-- 				<td> -->
<%-- 					<c:forEach items="${SystemCodeList}" var="list"> --%>
<!-- 						<label style="font-weight:normal;  margin-right: 15px;"> -->
<%-- 							<input class="optiongroup" name="payType" type="checkbox" value="${list.codeValue}"  style="margin-right: 3px;"/>${list.codeName} --%>
<!-- 						</label> -->
<%-- 					</c:forEach> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
		</table>
	</div>

</div>
<div class="box-footer" id="inputBtn">
	<div class="pull-right">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="saveBtn" onclick="excelDown();"><i class="glyphicon glyphicon-saved mr-5"></i>매출 보고서 조회</button>
	</div>
</div>
</div>
<script type="text/javaScript">
	var date;
	var year;
	var month;
	var today;
	var endday;

	$.datePickerCommon.createDatePicker('startDate');
	$.datePickerCommon.createDatePicker('endDate');
	$.datePickerCommon.createDatePicker2('startMonth');
	$.datePickerCommon.createDatePicker2('endMonth');

	$('#startDate, #startMonth').attr('readOnly', true);
	$('#endDate, #endMonth').attr('readOnly', true);

	$(document).ready(function () {

		treeSearchList(0, false, null, 'STO01');
		treeHeight();

		$(window).resize(function(){
			treeHeight('resize');
	    });


		$( ".btn_drop" ).click(function() {
		     $(this).next().toggleClass("on");
		     return false;
		});

		$("#localSearchType").change(function() {
			if($(this).val() == "MON")
			{
				$("#startMonthDiv, #endMonthDiv").show();
				$("#startDayDiv, #endDayDiv").hide();
			}
			else
			{
				$("#startDayDiv, #endDayDiv").show();
				$("#startMonthDiv, #endMonthDiv").hide();

			}
		});



		$( ".btn_save,.btn_close,.btn_close_check" ).click(function() {
		     $(this).parent().removeClass("on");
		     return false;
		});

		$( ".dropBox ul li a" ).click(function() {
		     $(this).parent().parent().parent().removeClass("on").prev(".btn_drop").text($(this).text());
		     return false;
		});

		$(".dropBox ul li label").click(function() {
		    if($(this).children("input").is(':checked')) {
		        $(this).parent().addClass("on");
		    } else {
		        $(this).parent().removeClass("on");
		    }
		    if(!$(this).children("input").is(":checked")) {
		        $(this).parent().siblings("li.check_all").removeClass("on").find("input").prop("checked", false);
		    }
		    else {
		        var li_nav = $(this).parent().parent().children("li");
		        if(li_nav.not(".check_all").find("input:checked").length == li_nav.length-1) {
		            $(this).parent().siblings("li.check_all").addClass("on").find("input").prop("checked", true);
		        }
		    }
		});

		$(".dropBox ul li.check_all label").click(function() {
		    if($(this).children("input").is(":checked")) {
		        $(this).parent().siblings("li").removeClass("on").find("input[type=checkbox]").prop("checked", true);
		    }
		    else {
		        $(this).parent().siblings("li").removeClass("on").find("input[type=checkbox]").prop("checked", false);
		    }
		});

		$( ".btn_save,.btn_close_check" ).click(function() {
		    if($(this).siblings("ul").find("li.check_all").children().children("input").is(":checked")) {
		        $(this).parent().prev(".btn_drop").text("전체");
		    }

		    else {
		        var input_checked = $(this).siblings("ul").find("input:checked");
		        if((input_checked.length) > 1){
		            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text()+" 외 "+(input_checked.length-1) + "개");
		        }

		        else if(($(this).siblings("ul").find("input:checked").length) == 1){
		            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text());
		        }
		        else {
		            $(this).parent().prev(".btn_drop").text("가맹점 선택");
		         }
		    }
		    return false;

		});
	});



	function excelDown(){
   		var francArr = [];
		$("[name='checkboxArr']:checked").each(function() {
			francArr.push($(this).val());
		});

		if (francArr.length == 0){
			$.modalCommon.alertView("가맹점" + '은(는) 필수 입력값 입니다.', '가맹점');
			return;
		}


		if (!$.formCheck('#localSearchType', 'Y', 50, 'N', '보고서 종류'))
			return;

		var startDate = $("#localSearchType").val() == "MON" ? $("#startMonth").val() : $("#startDate").val();
		var endDate = $("#localSearchType").val() == "MON" ? $("#endMonth").val() : $("#endDate").val();
		if (startDate == ""){
			$.modalCommon.alertView("조회 시작" + '은(는) 필수 입력값 입니다.', '조회 시작');
			return false;
		}

		if (endDate == ""){
			$.modalCommon.alertView("조회 종료" + '은(는) 필수 입력값 입니다.', '조회 종료');
			return false;
		}

		if(startDate > endDate){
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}
		var param = {
				domainIdx: $('#domainIdx').val(),
				brandIdx: $('#brandIdx').val(),
				francArr: francArr,
				searchType: $("#localSearchType").val(),
				startDate: startDate,
				endDate: endDate,
		};
// 		$("#user #reportType").val($("#reportType").val());
		$("#user #searchType").val($("#localSearchType").val());
		$("#user #searchStartDate").val(startDate);
		$("#user #searchEndDate").val(endDate);
		$("#user #checkboxFrancArr").val(francArr);

		$('#user').attr('action', '<c:url value="/Report/ExportExcel"/>');
   		$('#user').submit();

	}

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
        if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){
		$("#user #domainIdx").val(data.node.li_attr.domainidx);
        $("#user #brandIdx").val(data.node.li_attr.brandidx);
        $("#user #francIdx").val(data.node.li_attr.francidx);

        var brdName = data.node.li_attr.domainidx +"_"+ data.node.li_attr.brandidx + "_anchor";
        $("#user #brdName").val($("#"+brdName).text());
        reportList();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		$("#user #domainIdx").val(jsonData.IDX);
		reportList();
	}

	function reportList(){
		$(".btn_drop").text("가맹점 선택");
		var param = {
						domainIdx : $("#domainIdx").val(),
						brandIdx : $("#brandIdx").val(),
						francIdx : $("#francIdx").val()/* ,
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						payType : $("#payType").val(),
						reportType : $("#reportType").val() */
					};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/Report/FrancList/"/>', param);

		if (result.resultCode == 0){
			var html = "";
			$("#francList").empty();
			if(result.iTotalDisplayRecords > 0){
				html ="<li class=\"check_all\"><label><input type=\"checkbox\" name=\"ARRIDX_ALL\" onclick=\"$.checkboxUtil.checkAll(this, 'checkboxArr');\">전체</label></li>";
			}else{
				html ="존재하는 매장이 없습니다.";
			}

			for(var idx=0; idx<result.iTotalDisplayRecords; idx++){
				var dataVo = result.data[idx];
				var dataFrancIdx = dataVo.francIdx;
				var dafaFancName = dataVo.francName;
				html += "<li>";
				html += "<label style=\"font-weight:normal; margin-right: 15px;\">";
				html += "<input type=\"checkbox\"id=\"checkboxArr\" name=\"checkboxArr\" onclick=\"$.checkboxUtil.checkAllChk(this, 'checkboxArr');\" value=\""+dataFrancIdx+"\">"+dafaFancName+"</label></li>";
			}
			$('#francList').html(html);
        }

	}

</script>