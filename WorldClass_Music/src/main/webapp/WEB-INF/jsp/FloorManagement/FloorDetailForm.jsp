<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
</style>
<div class="box-body">
   	<div class="row nav-tabs" id="tabLab" style="margin: 5px 0 30px 4px;">
	<ul class="nav nav-pills" id="tabList"></ul>
</div>
<div id="contentView"></div>
</div>
<input type="hidden" id="domainIdx" name="domainIdx" value="${FloorVo.domainIdx}" />
<input type="hidden" id="brandIdx" name="brandIdx" value="${FloorVo.brandIdx}" />
<input type="hidden" id="floorIdx" name="floorIdx" value="${FloorVo.floorIdx}" />
<input type="hidden" id="dispShortNm" name="dispShortNm" value="${FloorVo.dispShortNm}" />

<script type="text/javaScript">
	$(function() {
		tabView(0);
		
		$('.modal-dialog').css('width', '1580px').css('height', '750px');
	});
	
	function tabView(val) {
		$('#tabLab').show();
		
		var domainIdx = $('#domainIdx').val();
		var brandIdx = $('#brandIdx').val();
		var floorIdx = $('#floorIdx').val();
		var param = {domainIdx : domainIdx, brandIdx : brandIdx, floorIdx : floorIdx};

		var tabDataArr = [
				{tabName: "브랜드관리", tabUrl: '<c:url value="/FloorManagement/ShopForm"/>', tabParam : param},
				{tabName: "편의시설관리", tabUrl: '<c:url value="/FloorManagement/FacilitiesForm"/>', tabParam : param},
				{tabName: "단말관리", tabUrl: '<c:url value="/FloorManagement/KioskForm"/>', tabParam : param},
				{tabName: "경로관리", tabUrl: '<c:url value="/FloorManagement/PathForm"/>', tabParam : param}
		];

		$.newTabControl(tabDataArr, val);
	}
</script>