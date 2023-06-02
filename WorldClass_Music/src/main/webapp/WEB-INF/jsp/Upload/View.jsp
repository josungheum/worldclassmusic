<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/Dropzone/basic.min.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/Dropzone/dropzone.min.css'/>" />
<script type="text/javascript" src="<c:url value='/Content/Dropzone/dropzone.js'/>"></script>
<form:form commandName="UploadVo" name="fileUpload" id="fileUpload" method="post" class="dropzone">
<form:hidden path="SaveFolder"/>
<form:hidden path="Extens"/>
	<div class="dz-message" >
		<h5>여기에 파일을 올리거나 업로드하려면 클릭하십시오.</h5>
	</div>
</form:form>
<script type="text/javaScript">
	$.FileUpload.StartUpload = function () {
		$.FileUpload.Data = [];
		$.FileUpload.dz.processQueue();
	}
	var curMax = true;
	var msg = '';

	$(document).ready(function () {
		var acceptedFiles = '.jpeg,.jpg,.png,.PNG,.JPEG,.JPG,.mp4,.MP4,';
		$('.dropzone').css('overflow-y', 'auto');
		var max = 0;
		var name = $('#SaveFolder').val();
		var contType="";
		var groupIdx="";
		if (name == 'Screen') {
			max = 5;
		}
		else if(name == 'OptProdItem' || name == 'Logo' || name == 'Event') {
			max = 1;
			acceptedFiles = '.jpeg,.jpg,.png,.PNG,.JPEG,.JPG';
		}
		else if(name == 'orderProd') {
			max = 1;
			acceptedFiles = '.jpeg,.jpg,.png,.PNG,.JPEG,.JPG';
		}
		else if (name == 'Brand'){
			max = 1;
			acceptedFiles = '.jpeg,.jpg,.png,.PNG,.JPEG,.JPG';
		}
		else if (name == 'Addition' || name == 'Detail'){
			max = 10;
			acceptedFiles = '.jpg,.jpeg,.png,.avi,.wmv,.mpeg,.mp4,.JPG,.JPEG,.PNG,.AVI,.WMV,.MPEG,.MP4';
		}
		else if (name == 'Shop'){
			var imageSize = $('.contentAdditionImage').length;
			max = 6 - imageSize;
			acceptedFiles = '.jpg,.jpeg,.png,.JPG,.JPEG,.PNG';
		}
		else if (name == 'Content'){
			max = 1;
			acceptedFiles = '.jpg,.jpeg,.png,.avi,.wmv,.mpeg,.mp4,.JPG,.JPEG,.PNG,.AVI,.WMV,.MPEG,.MP4';
		}
		else if (name == 'CommonContent'){
			max = 10;
			acceptedFiles = '.jpg,.jpeg,.png,.avi,.wmv,.mpeg,.mp4,.JPG,.JPEG,.PNG,.AVI,.WMV,.MPEG,.MP4';
			contType= 'C'
			groupIdx= $('#contGroupIdx').val();

			$('.modal-dialog .btn').removeAttr('disabled');
		}
		else {
			max = 1;
		}

		// 기존 데이타 초기화
		$.FileUpload.Data = null;

		$.FileUpload.dz = new Dropzone('#fileUpload', {
			url: '<c:url value="/Upload/Save"/>',
			addRemoveLinks: true,
			parallelUploads: 1,
			//uploadMultiple:true,
			//clickable: true,
			autoProcessQueue: false,
			dictDefaultMessage: '파일을 드래그 하거나 이곳을 클릭하세요',
			paramName: 'uploadFile',
			params: {contType: contType, groupIdx: groupIdx},
			maxFilesize: 5000, // MB
			maxFiles: max,
			acceptedFiles: acceptedFiles,
			init: function () {
				this.on('success', function (file, resq) {
// 					console.log("success");
					if(resq == null || resq == "" || resq == undefined){
						$.modalCommon.alertView('지원하지 않는 색상이 포함된 <br>이미지 입니다.');
						msg = '';
						this.removeAllFiles(true);
						$.FileUpload.Data = null;
					}else{
						$.FileUpload.Data.push(resq);
						$.FileUpload.dz.processQueue();
					}
				});
				this.on('addedfiles', function (file, resq) {
// 					console.log("addedfiles");
					/* if(file.length+this.files.length > 5 && curMax == false){
						$.modalCommon.alertView('파일은 최대 ' + max + '개 까지 업로드 가능합니다.');
						curMax = true;
						this.removeFile(file);
						return false;
					} */

					if(msg != ''){
						$.modalCommon.alertView(msg);
						msg = '';
					}
				});
				this.on('addedfile', function (file) {
// 					console.log("addedfile");
					if(file.length+this.files.length > 5){
						this.removeFile(file);
						return false;
					}

					if (file.size >= this.options.maxFilesize * 1024 * 1024) {
// 						$.modalCommon.alertView('파일 용량을 확인하세요.(MAX : 5000MB)');
						msg = '파일 용량을 확인하세요.(MAX : 5000MB)';
						this.removeFile(file);
// 						this.removeAllFiles(true);
// 						$.FileUpload.Data = null;
						return false;
					}


					if(file.type != ""){
						if (!$('#Extens').val().toLowerCase().match(file.type.split('/')[1].toLowerCase())) {
							msg = '파일 확장자를 확인 해주세요.<br>[' + $('#Extens').val().toLowerCase() + '] 파일만 가능합니다.';
// 							$.modalCommon.alertView('파일 확장자를 확인 해주세요.<br>[' + $('#Extens').val().toLowerCase() + '] 파일만 가능합니다.');
							this.removeFile(file);
// 							this.removeAllFiles(true);
// 							$.FileUpload.Data = null;
							return false;
						}
					}else{
						msg = '파일 확장자를 확인 해주세요.<br>[' + $('#Extens').val().toLowerCase() + '] 파일만 가능합니다.';
// 						$.modalCommon.alertView('파일 확장자를 확인 해주세요.<br>[' + $('#Extens').val().toLowerCase() + '] 파일만 가능합니다.');
						this.removeFile(file);
// 						this.removeAllFiles(true);
// 						$.FileUpload.Data = null;
						return false;
					}
				});
				this.on('maxfilesexceeded', function (file) {
					msg = '파일은 최대 ' + max + '개 까지 업로드 가능합니다.';
// 					console.log("maxfilesexceeded");
// 					curMax = false;
// 					$.modalCommon.alertView('파일은 최대 ' + max + '개 까지 업로드 가능합니다.');
					this.removeFile(file);

				});
				this.on('queuecomplete', function (file) {
// 					console.log("queuecomplete");
// 					console.log(file);
					if(file != "undefined" && file != null && this.files != null){
						if(file.length+this.files.length > 5){
							this.removeAllFiles(true);
							$.FileUpload.Data = null;
							return false;
						}
					}

 					var errorCnt = true;

 					if (this.files.length <= 0) {
 						errorCnt = false;
					}else{
						for (var cnt = 0; cnt < this.files.length; cnt++) {
							if(this.files[cnt].status != "success"){
	 							errorCnt = false;
	 							break;
							}
						}
					}
					if(errorCnt == true){
						$.FileUpload.Finished($.FileUpload.Data);
						$.FileUpload.Dialog.close();
					}else{
						if (this.files.length > 0) {
							this.removeAllFiles(true);
						}
					}
				});
				this.on('canceled', function (file) {
// 					console.log("canceled");
					msg = '';
				});
				this.on('removedfile', function (file) {
// 					console.log("removedfile");
					if (file.Index != null) {
// 						this.removeFile(file);
					}
				});
			},
			accept: function (file, done) {
				file.acceptDimensions = done('');
			},
			error: function(file, response) {
				if(file != null  && file.length > 0){
					this.removeAllFiles(true);
				}

		    }
		});
	});
</script>