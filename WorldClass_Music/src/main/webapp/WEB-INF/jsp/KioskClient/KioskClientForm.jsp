<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
  .progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
  .bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
  .percent { position:absolute; display:inline-block; top:3px; left:48%; }
</style>

<div class="modal-body">
	<form id="clientUpdateForm" method="post" enctype="multipart/form-data">

		<input type="hidden" id="oldFileName" name="oldFileName" value='<c:out value="${kioskClientVo.fileName}" />'>
		<input type="hidden" id="kioskClientIdx" name="kioskClientIdx" value="${kioskClientVo.kioskClientIdx}">
		
		<div id="modalId">
			<label>버전<font class="fontSet">[필수]</font></label>
			<c:if test="${kioskClientVo.kioskClientIdx != null}">
			<input type="text" class="form-control" id="version" name="version"  value="<c:out value="${kioskClientVo.version}"/>" <c:if test="${kioskClientVo.version != null}">readonly=true</c:if> style="ime-mode:active" maxlength="7" placeholder="1.0.0.0"/>
			</c:if>
			<c:if test="${kioskClientVo.kioskClientIdx == null}">
			<input type="text" class="form-control autoHypenVersion" id="version" name="version"  value="<c:out value="${kioskClientVo.version}"/>" <c:if test="${kioskClientVo.version != null}">readonly=true</c:if> style="ime-mode:active" maxlength="7" placeholder="1.0.0.0"/>
			</c:if>
		</div><br/>
		<div>
			<label>파일<font class="fontSet">[필수]</font></label>
			<div>
				<input type="file" id="UPLOADFILE" name="UPLOADFILE" style="display: none;"/>
				<input type="text" id="fileName" name="fileName" value='<c:out value="${kioskClientVo.fileName}" />' class="form-control" style="float: left; width: 84%;" readonly/>
				<button type="button" class="btn btn-info" id="btnFileSelect">파일선택</button>
			</div>
		</div>
		<br/>
		
		<div class="progress" style="display: none;">
			<div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:0%;">
				<span class="sr-only"></span>
			</div>
		</div>
		
		
		
		<br/>
		<div>
			<label class="lb">내용</label>
			<textarea id="detail" name="detail"  style="border: 1px solid #EAEAEA; width: 95%; height: 50px; overflow-y: scroll;"><c:out value="${kioskClientVo.detail}"/></textarea>
		</div>

		<div style="margin-top:4px;  overflow-x: hidden; overflow-y: auto;" class="proton-demo" id="targetTreeList" >
		</div>
		
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="kioskClientSave()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeBtn">닫기</button>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		$("#btnFileSelect").off("click").on("click", function() {
			$("#UPLOADFILE").click();
		});

		$("#UPLOADFILE").off("change").on("change", function() {
			var orgFileName = $(this).val().replace(/\\/g, '/').replace(/.*\//, '');
			<%-- 확장자만 분리하여 체크. zip --%>
			if(orgFileName.length > 5) {
				var ext = orgFileName.substr(orgFileName.length - 4, 4);
			    if ( ext != '.zip') {
					$.modalCommon.alertView('파일의 확장자는 zip 만 가능합니다.');
					return;
				}
			}

			$("#fileName").val(orgFileName);
		});

		$("#version").focus(function(){

        }).focusin(function(){

        }).focusout(function(){
        	 $(this).val(autoHypenVersionRet($(this).val()));
        }).blur(function() {

        }).change(function() {

        }).select(function() {

        });

	});

	function kioskClientSave() {
		$("#version").val(autoHypenVersionRet($("#version").val()));

		if(!$.formCheck('#version', 'Y', 20, 'N', '버전'))
			return;
		if(!$.formCheck('#detail', 'N', 300, 'N', '내용'))
			return;

		if(!$("#UPLOADFILE").val() && !$("#fileName").val()) {
			$.modalCommon.alertView('파일을 선택해 주세요.');
			return;
		}

		var bar = $('.progress-bar');
// 		var percent = $('.percent');
// 		    var status = $('#status');
		    
		$(".progress-bar").attr("style","width:0%");
		$(".progress").attr("style",""); 
		$("#clientUpdateForm").attr("action", '<c:url value="/KioskClient/ClientCreate"/>');
		$("#clientUpdateForm").ajaxForm({
			type: "POST",
            dataType: 'json',
			success: function(data) {
				$(".progress").attr("style","display:none");
				$("#procBtn, #closeBtn").attr("disabled",false);
				if (data.resultCode == 0){
					$.modalCommon.close();
					commMessage('S','단말 버전');
					clientList(1);
				}else{
					$.modalCommon.alertView(data.messages.title);
				}

			},
			 beforeSend: function() {
// 		            status.empty();
		            $(".progress-bar").attr("style","width:0%");
		            $(".progress-bar").text("0% 진행중");
		            $("#procBtn, #closeBtn").attr("disabled",true);
		            
		            
		        },
		        uploadProgress: function(event, position, total, percentComplete) {
		        	$(".progress-bar").attr("style","width:"+percentComplete+"%");
		        	$(".progress-bar").text(""+percentComplete+"% 진행중");
// 		            var percentVal = percentComplete + '%';
// 		            bar.width(percentVal);
// 		            percent.html(percentVal);
		        },
			error: function(e) {
				$("#procBtn, #closeBtn").attr("disabled",false);
				$(".progress").attr("style","display:none"); 
				$.modalCommon.alertView("업로드 도중에 오류가 발생했습니다.");
				$.modalCommon.close();
				
				
			}
		}).submit();
	}
</script>