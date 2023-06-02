<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="brandVo" name="discountFrm" id="discountFrm" method="post">
<form:hidden path="discountIdx"/>
	<div class="modal-body box-body">
		<div>
			<label class="lb ">할인명 <font class="fontSet">[필수]</font></label>
			<form:input path="discountName" class="form-control" id="discountName" maxlength="50" placeholder="할인명"/>
		</div><br/>
		<div>
			<label>할인등급</label>
			<select id="discountLevel" name="discountLevel" class="form-control">
				<c:forEach items="${discountLevel}" var="list">
					<option value="<c:out value="${list.codeValue}" />" <c:if test="${list.codeValue eq brandVo.discountLevel}">selected</c:if>><c:out value="${list.codeName}" /></option>
				</c:forEach>
		    </select>
		</div><br/>
		<div>
			<label>할인율</label>
			<form:input path="discountPercent" class="form-control" placeholder="할인율 (Ex: 10.75)" onkeypress="return isNumberKey(event);" onkeyup="koreanChange(this);"/>
		</div><br/>
		<div>
			<label class="lb ">시작일 <font class="fontSet">[필수]</font></label>
			<div class="input-group date">
				<form:input path="startDate" id="startDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
		</div><br/>
		<div>
			<label class="lb ">종료일 <font class="fontSet">[필수]</font></label>
			<div class="input-group date">
				<form:input path="endDate" id="endDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
		</div><br/>
		<div>
			<label>활성화 여부</label>
			<input type="checkbox" data-group-cls="btn-group-sm" id="activeYn" data-reverse ><br/>
        </div><br />
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="discountSave()" id="addBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('#activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('#activeYn').prop('checked', "${brandVo.activeYn}" == "N" ? false : true);
		$.datePickerCommon.createDatePicker('startDate');
		$.datePickerCommon.createDatePicker('endDate');

	});

	//할인율 저장/수정
	function discountSave(){
		if (!$.formCheck('#discountName', 'Y', 50, 'N', '할인명')) return;
		if (!$.formCheck('#startDate', 'Y', 20, 'N', '시작일')) return;
		if (!$.formCheck('#endDate', 'Y', 20, 'N', '종료일')) return;

		var startDate = new Date($('#startDate').val());
		var endDate = new Date($('#endDate').val());

		if(startDate > endDate){
			$.modalCommon.alertView("종료일이 시작일보다 빠를 수 없습니다.", null, null, null);
			return;
		}

		var param = {
				brandIdx: $('#brandIdx').val(),
	    		discountName: $('#discountName').val(),
	    		discountLevel: $('#discountLevel').val(),
	    		discountPercent: $('#discountPercent').val(),
	    		startDate: $('#startDate').val(),
	    		endDate: $('#endDate').val(),
	    		activeYn: $('#activeYn').prop('checked') ? "Y" : "N",
	    		discountIdx: $('#discountIdx').val()
	    };

		var msgCode = $('#discountIdx').val() == "" ? 'C':'U';

		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/BrandManagement/DiscountCreate"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'할인율');
					discountList(1);
			    }
			}
		}
		commonSave(msgCode, callBack);

	}

</script>