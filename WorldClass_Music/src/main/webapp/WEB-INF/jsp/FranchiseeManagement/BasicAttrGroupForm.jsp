<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
	vertical-align: middle;
}
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
	vertical-align: middle;
}
</style>
<form:form commandName="franchiseeVo" name="francGroupform" id="francGroupform" method="post">
	<form:hidden path="groupIdx"/>
	<div class="modal-body">
		<div class="form-group  row">
			<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				<label for="name" class="control-label">서비스명 </label>
		    	<input type="text" id="brandName" name="brandName" class="form-control" maxlength="50" placeholder="서비스명" readonly>
			</div>

		</div>
		<div class="form-group  row">
			<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				<label for="parentName" class="control-label" style="display: block;">상위그룹명 </label>
				<form:hidden path="parentGroupIdx"/>
				<form:input path="parentName" class="form-control" maxlength="50" placeholder="상위그룹명" readonly="true"  style="display: inline; width: 86%"/>
				<button type="button" class="btn btn-default notDisabled pull-right" onclick="FindParentGroup()" style="display: inline;">변경</button>
			</div>
		</div>
		<div class="form-group  row">
			<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				<label for="groupName" class="control-label">그룹명 <font class="fontSet">[필수]</font></label>
			    <form:input path="groupName" class="form-control" maxlength="50" placeholder="그룹명"/>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="FormSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function() {
		$('#brandName').val($('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_anchor').text());
		if($('#groupIdx').val() > 0 && $('#francGroupform #groupIdx').val() == '0'){
			$('#parentName').val($('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()+'_anchor').text());
		}
	});

	//기본 정보 저장
	function FormSave(){
		if (!$.formCheck('#francGroupform #groupName', 'Y', 50, 'N', '그룹명'))
			return;

		var param = {
				domainIdx: $('#domainIdx').val(),
				brandIdx: $('#brandIdx').val(),
				parentGroupIdx: $('#francGroupform #parentGroupIdx').val(),
				groupIdx: $('#groupIdx').val(),
				realGroupIdx: $('#francGroupform #groupIdx').val(),
				groupName: $('#francGroupform #groupName').val()
		};

		var msgCode = $('#francGroupform #groupIdx').val() != '0' ? 'U' : 'C';
		var url;
		if($('#francGroupform #groupIdx').val() == '0'){
			url = '<c:url value="/FranchiseeManagement/InsertGroup"/>';
		}
		else {
			url = '<c:url value="/FranchiseeManagement/UpdateGroup"/>';
		}
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault(url, param);

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'그룹');
					treeSearchList(1, false, null, 'STO02');
					if($('#groupIdx').val() == '0'){
						$('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_anchor').click();
					}
					else {
						$('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()+'_anchor').click();
					}
			   }
			}
		}
		commonSave(msgCode, callBack);
	}
	
	function FindParentGroup(){
		BootstrapDialog.show({
	        title: '사이트 그룹',
	        draggable: true,
	        closable: true,
            closeByBackdrop:false,
	        message: function (dialog) {
	            var $message = $('<div></div>');
	            var pageToLoad = dialog.getData('pageToLoad');
	
	            $.sessionCheck();	            
	            $message.load('<c:url value="/FranchiseeManagement/GroupFind"/>');
	
	            return $message;
	        },
	       /*  data: {
	            'pageToLoad': url
	        }, */
	        buttons: [{
	            label: '확인',
	            cssClass:'btn-primary',
	            action: function (dialog) {
            		var selectedTarget = $('#findTreeList').jstree('get_selected', true);
            		console.log(selectedTarget);
            		if (selectedTarget[0].li_attr.brandidx == 0) {
	        	    	$.modalCommon.alertView('서비스나 사이트 그룹을 선택해주세요.');
	        	    	return;
	        		}
            		if (selectedTarget[0].li_attr.groupidx == $('#francGroupform #groupIdx').val()) {
	        	    	$.modalCommon.alertView('현재 수정중인 그룹입니다.');
	        	    	return;
	        		}
            		var gidx = 0;
            		var gText = '';
            		if (selectedTarget[0].li_attr.groupidx != 0) {
            			gidx = selectedTarget[0].li_attr.groupidx;
            			gText = selectedTarget[0].text;
	        		}
            		$('#francGroupform #parentGroupIdx').val(gidx);
            		$('#francGroupform #parentName').val(gText);
	        		
	            	dialog.close();
	            }
	        },
	        {
	            label: '취소',
	            cssClass:'btn-gray',
	            action: function (dialog) {
	                dialog.close();
	            }
	        }]
	    });
	}

</script>