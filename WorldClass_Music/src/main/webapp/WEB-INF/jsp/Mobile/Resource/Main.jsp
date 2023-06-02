<%--
  Name : Main.jsp
  Description : 모바일 메인
  Modification Information
    수정일                    수정자                  수정내용
  -------      --------    ---------------------------
  2019.06.18   이재환                  최초작성
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form name="mainForm" id="mainForm">
<input type="hidden" name="domainIdx" id="domainIdx"/>
<input type="hidden" name="brandIdx" id="brandIdx"/>
<div class="container">
  <div class="row form-group">
      <select id="dom_brndIdx" name="dom_brndIdx" class="form-control">
          <option value="">브랜드 선택</option>
          <c:forEach var="brandList" items="${brandList}" varStatus="status">
            <option <c:if test="${brandList.brandIdx eq  commonVo.brandIdx}">selected</c:if> value="<c:out value="${brandList.domainIdx}_${brandList.brandIdx}" />"><c:out value="${brandList.name}" /></option>
          </c:forEach>
      </select>
  </div>
  <div class="row form-group">
      <select id="francIdx" name="francIdx" class="form-control">
          <option value="">가맹점 선택</option>
          <c:if test="${!empty francList }">
            <c:forEach var="francList" items="${francList}" varStatus="status">
              <option <c:if test="${francList.francIdx eq  commonVo.francIdx}">selected</c:if> value="<c:out value="${francList.francIdx}" />"><c:out value="${francList.francName}" /></option>
            </c:forEach>
          </c:if>
      </select>
  </div>
  <div class="row form-group">
      <button id="btn_product"    type="button" class="btn btn-primary btn-ms btn-block">상품 관리</button>
  </div>
  <div class="row form-group">
      <button id="btn_order"      type="button" class="btn btn-primary btn-ms btn-block">주문 화면 관리</button>
  </div>
  <div class="row form-group">
      <button id="btn_statistics" type="button" class="btn btn-primary btn-ms btn-block">매출 통계</button>
  </div>
</div>
</form>
<script type="text/javascript">
<!--
$(document).ready(function () {
    $('#mainForm #btn_product'   ).prop('disabled', '');
    $('#mainForm #btn_order'     ).prop('disabled', '');
    $('#mainForm #btn_statistics').prop('disabled', '');

    <%-- 브랜드 관리자이면 브랜드를 미리 선택 --%>
    <c:if test="${sessionScope.adminType == 'ADM0002' || sessionScope.adminType == 'ADM0003'}">
    $('#mainForm #dom_brndIdx').val('<c:out value="${sessionScope.domainIdx}_${sessionScope.brandIdx}" />');
    $('#mainForm #dom_brndIdx option:not(:selected)').remove();
    </c:if>

    <%-- 매장 관리자이면 가맹점을 선택 --%>
    <c:if test="${sessionScope.adminType == 'ADM0003'}">
    $('#mainForm #francIdx').val('<c:out value="${sessionScope.francIdx}" />');
    $('#mainForm #francIdx option:not(:selected)').remove();
    </c:if>

    <%-- 브랜드 변경 이벤트-브랜드에 따라 가맹점 목록 변경 --%>
	$('#mainForm #dom_brndIdx').off('change').on('change', function(event){
		$('#mainForm #francIdx').empty();
		var dom_brndIdx = $('#mainForm #dom_brndIdx').val();
		var pArr		= dom_brndIdx.split('_');
		$('#mainForm #domainIdx').val(pArr[0]);
		$('#mainForm #brandIdx' ).val(pArr[1]);
		var param  = {'domainIdx' : pArr[0], 'brandIdx' : pArr[1]};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FranchiseeManagement/FranchiseCombo"/>', param);
		var option = $('<option>',{
			value : '',
			text  : '가맹점 선택'
		});
		$('#mainForm #francIdx').append(option);
		if(result.resultCode == '0'){
			if(result.data) {
				$.each(result.data, function(index, data){
					option = $('<option>',{
						value : data.francIdx,
						text  : data.francName
					});
					$('#mainForm #francIdx').append(option);
				});
			}
		}
	});

	<%-- 상품 관리 버튼 이벤트 --%>
	$('#mainForm #btn_product').off('click').on('click', function(event){
		if(fnValidate()){
			
			$('#mainForm').attr('brandIdx',$('#mainForm #brandIdx').val());
			$('#mainForm').attr('francIdx',$('#mainForm #francIdx').val());
			$('#mainForm').attr('action', '/Mobile/OrderProd/Main');
            $('#mainForm').submit();
		}
	});

	<%-- 주문 화면 관리 버튼 이벤트 --%>
	$('#mainForm #btn_order').off('click').on('click', function(event){
		if(fnValidate()){
			$('#mainForm').attr('action', '/Mobile/OrderScreen/Main');
            $('#mainForm').submit();
		}

	});

	<%-- 매출 통계 버튼 이벤트 --%>
	$('#mainForm #btn_statistics').off('click').on('click', function(event){
		if(fnValidate()){
			$('#mainForm').attr('action', '/Mobile/Report/Main');
            $('#mainForm').submit();
		}

	});
});

<%-- 필수값 체크 Validate --%>
function fnValidate(){
	var brandIdx = $('#mainForm #dom_brndIdx').val();
	var francIdx = $('#mainForm #francIdx').val();
	if($.trim(brandIdx) == ''){
		$.modalCommon.alertView('브랜드를 먼저 선택해 주세요.');
		return false;
	} else if($.trim(francIdx) == ''){
		$.modalCommon.alertView('가맹점을 선택해 주세요.');
		return false;
	}
	var dom_brndIdx = $('#mainForm #dom_brndIdx').val();
	var pArr		= dom_brndIdx.split('_');
	$('#mainForm #domainIdx').val(pArr[0]);
	$('#mainForm #brandIdx' ).val(pArr[1]);

	return true;
}
//-->
</script>