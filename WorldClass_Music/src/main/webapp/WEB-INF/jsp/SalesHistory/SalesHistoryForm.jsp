<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	.salesItemTable tbody tr td{
		padding : 3px;
		border : 1px solid;
		border-color : #d2d6de;
	}

	.salesItemTable thead tr td{
		padding : 3px;
		border : 1px solid;
		border-color : #d2d6de;
	}

	.salesHistoryDetail tbody tr td{
		font-size : 15px;
		padding : 7px;
	}
</style>
<div class="modal-body">
	<form:form commandName="SalesHistoryVo" name="frm" id="frm" method="post">
		<div style="float: left;width:49%; display:inline;">
			<div>
				<table class="salesHistoryDetail">
					<tbody>
						<tr>
							<td style="font-weight: bold; width: 40%;">단말 명</td>
							<td style="width: 60%"><c:out value="${SalesHistoryVo.deviceName}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">주문 일시</td>
							<td><c:out value="${SalesHistoryVo.orderDate}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">결제 수단</td>
							<td><c:out value="${SalesHistoryVo.payType}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">상품금액</td>
							<td id="orderAmt"><c:out value="${SalesHistoryVo.orderAmt}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">할인금액</td>
							<td id="discAmt"><c:out value="${SalesHistoryVo.discAmt}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">결제금액</td>
							<td id="payAmt"><c:out value="${SalesHistoryVo.payAmt}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">결제 완료 여부</td>
							<td><c:out value="${SalesHistoryVo.payProcCode}" /></td>
						<tr>
						<tr>
							<td style="font-weight: bold;">포인트 적립 / 사용</td>
							<td>
							<c:out value="${SalesHistoryVo.pointSaveAmt}P / "/>
							<c:out value="${SalesHistoryVo.pointUseAmt}P"/>
							</td>
						<tr>
					</tbody>
				</table>
			</div>
			<br/>
		</div>
		<div style="float: left;width:49%;display:inline;">
			<label>주문 상품 목록</label>
			<div style="height:405px; border-color:#d2d6de; border-style:solid; overflow: auto;" id="jstree-selectscreen" >
				<table style="width: 100%" class="salesItemTable">
					<thead>
						<tr>
							<td style="text-align:center; width: 51%; padding: 3px;">상품명</td>
							<td style="text-align:center; width: 12%; padding: 3px;">수량</td>
							<td style="text-align:center; width: 15%; padding: 3px;">상품금액</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${SalesHistoryVo.orderItemVo ne null and fn:length(SalesHistoryVo.orderItemVo) > 0}">
						<c:forEach items="${SalesHistoryVo.orderItemVo}"  var="list" >
							<tr>
								<td><c:out value="${list.prodDisplayName}"/></td>
								<td align="center"><c:out value="${list.quantity}"/></td>
								<td name="unitPrice" align="right"><c:out value="${list.unitPrice}"/></td>
							</tr>
						</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<br/>
		<div class="modal-footer" style="border-top-color: #ffffff; padding-top:420px;">
			<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
		</div>
	</form:form>
</div>

<script>
	$(document).ready(function () {
		$('#orderAmt').text(Number($('#orderAmt').text()).toLocaleString('ko') + '원');
		$('#discAmt').text(Number($('#discAmt').text()).toLocaleString('ko') + '원');
		$('#empDiscAmt').text(Number($('#empDiscAmt').text()).toLocaleString('ko') + '원');
		$('#pntDiscAmt').text(Number($('#pntDiscAmt').text()).toLocaleString('ko') + '원');
		$('#payAmt').text(Number($('#payAmt').text()).toLocaleString('ko') + '원');

		var unitPriceList = $('[name="unitPrice"]');

		for (var i = 0; i < unitPriceList.length; i++) {
			$('[name="unitPrice"]')[i].innerText = Number($('[name="unitPrice"]')[i].innerText).toLocaleString('ko');
		}
	});
</script>