<%--
  Name : Form.jsp
  Description : 메뉴관리 - 등록/수정 폼
  Modification Information
    수정일                     수정자                 수정내용
  -------      --------    ---------------------------
  2019-06-17   이재환                 최초작성
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="MenuInfoVo" name="frm" id="frm" method="post">
    <form:hidden path="menuIdx"/>
    <form:hidden path="parentMenuId"/>
    <c:choose>
      <c:when test="${MenuInfoVo.depth == '0'}">
        <c:set var="height" value="580" />
      </c:when>
      <c:otherwise>
        <c:set var="height" value="690" />
      </c:otherwise>
    </c:choose>
	<div class="modal-body" style="height: ${height}px;">
	  <c:if test="${MenuInfoVo.depth != '0'}">
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="menuId" class="control-label">부모 메뉴</label>
				<div><c:out value="${MenuInfoVo.parentMenuNm}[${MenuInfoVo.parentMenuId}]" /></div>
			</div>
	    </div>
	  </c:if>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="menuId" class="control-label">메뉴 ID <font class="fontSet">[필수]</font></label>
				<form:input path="menuId" id="menuId" cssClass="form-control autoUpperNumber" cssStyle="ime-mode:active; width: 95%; display: inline;" maxlength="30" placeholder="메뉴 ID" />
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="menuKorName" class="control-label">메뉴한글 명 <font class="fontSet">[필수]</font></label>
				<form:input path="menuKorName" id="menuKorName" cssClass="form-control" cssStyle="ime-mode:active; width: 95%; display: inline;" maxlength="100" placeholder="메뉴한글 명" />
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for=menuSubName class="control-label">메뉴서브 명 </label>
				<form:input path="menuSubName" id="menuSubName" cssClass="form-control" cssStyle="ime-mode:active; width: 95%; display: inline;" maxlength="100" placeholder="메뉴서브 명" />
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for=menuLinkAddr class="control-label">메뉴 URL</label>
				<form:input path="menuLinkAddr" id="menuLinkAddr" cssClass="form-control" cssStyle="ime-mode:active; width: 95%; display: inline;" maxlength="300" placeholder="메뉴 URL" />
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="sysShowYn" class="control-label">시스템관리자 노출 여부 </label>
				<div>
				    <input type="checkbox" data-group-cls="btn-group-sm" id="sysShowYn" data-reverse checked><br />
				</div>
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="brdShowYn" class="control-label">브랜드관리자 노출 여부 </label>
				<div>
				    <input type="checkbox" data-group-cls="btn-group-sm" id="brdShowYn" data-reverse checked><br />
				</div>
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="frcShowYn" class="control-label">가맹점관리자 노출 여부 </label>
				<div>
				    <input type="checkbox" data-group-cls="btn-group-sm" id="frcShowYn" data-reverse checked><br />
				</div>
			</div>
	    </div>
	    <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="activeYn" class="control-label">활성화 여부 </label>
				<div>
				    <input type="checkbox" data-group-cls="btn-group-sm" id="activeYn" data-reverse checked><br />
				</div>
			</div>
	    </div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
	</div>
</form:form>

<script type="text/javascript">
<!--
$(document).ready(function () {
	$('#frm #sysShowYn').checkboxpicker(defaults = {	offLabel: '미노출',	    onLabel: '노출'});
	$('#frm #brdShowYn').checkboxpicker(defaults = {	offLabel: '미노출',	    onLabel: '노출'});
	$('#frm #frcShowYn').checkboxpicker(defaults = {	offLabel: '미노출',	    onLabel: '노출'});
	$('#frm #activeYn' ).checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

	$('#frm #sysShowYn').prop('checked', '${MenuInfoVo.sysShowYn}' == 'Y' ? true : false);
	$('#frm #brdShowYn').prop('checked', '${MenuInfoVo.brdShowYn}' == 'Y' ? true : false);
	$('#frm #frcShowYn').prop('checked', '${MenuInfoVo.frcShowYn}' == 'Y' ? true : false);
	$('#frm #activeYn' ).prop('checked', '${MenuInfoVo.activeYn}'  == 'N' ? false : true);

	if($.trim($('#frm #menuIdx').val()) != '') {
		$('#frm #menuId').attr('disabled', 'disabled');
	}
});

<%-- 저장 --%>
function Save() {
	var menuIdx			= $('#frm #menuIdx').val();
	var parentMenuId	= $('#frm #parentMenuId').val();
	var menuId			= $('#frm #menuId').val();
	var menuKorName		= $('#frm #menuKorName').val();
	var menuSubName		= $('#frm #menuSubName').val();
	var menuLinkAddr	= $('#frm #menuLinkAddr').val();
	var sysShowYn		= $('#frm #sysShowYn').prop('checked') ? 'Y' : 'N';
	var brdShowYn		= $('#frm #brdShowYn').prop('checked') ? 'Y' : 'N';
	var frcShowYn		= $('#frm #frcShowYn').prop('checked') ? 'Y' : 'N';
	var activeYn		= $('#frm #activeYn' ).prop('checked') ? 'Y' : 'N';

	if( !$.formCheck('#frm #menuId',      'Y', 30,  'N', '메뉴 ID')	||
		!$.formCheck('#frm #menuKorName', 'Y', 100, 'N', '메뉴한글 명')	||
		!$.formCheck('#frm #menuSubName', 'N', 100, 'N', '메뉴서브 명')){
		return;
	}

	var param = {
		'menuIdx' 		: menuIdx,
		'parentMenuId'	: parentMenuId,
		'menuId'		: menuId,
		'menuKorName'	: menuKorName,
		'menuSubName'	: menuSubName,
		'menuLinkAddr'	: menuLinkAddr,
		'sysShowYn'		: sysShowYn,
		'brdShowYn'		: brdShowYn,
		'frcShowYn'		: frcShowYn,
		'activeYn'		: activeYn
	};

	BootstrapDialog.confirm({
		title : '',
		message : '저장하시겠습니까?',
		type : BootstrapDialog.TYPE_WARNING,
		closable : false,
		draggable : true,
		btnCancelLabel: '취소',
		btnOKLabel: '확인',
		btnOKClass : 'btn-warning',
		callback : function(){
			var result = $.ajaxUtil.ajaxDefault(
					'<c:url value="/MenuInfo/Save"/>', param);

			if (result.resultCode == 0) {
				$.modalCommon.close();
				menuInfoList();
			}
		}
	});

}
//-->
</script>

