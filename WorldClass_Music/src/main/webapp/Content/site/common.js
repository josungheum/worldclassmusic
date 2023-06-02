//localhost:8080/scoservice/Resource/Main
var url = top.location.href.split('//');
//localhost:8080
var host = url[1].substr(0, url[1].indexOf('/'));
//array = [localhost:8080,scoservice,Resource,Main]
var hostUrl = url[1].split('/');
//scoservice
//var pName = hostUrl[1];
//Resource
var controller = hostUrl[1];
//Main
var action = hostUrl[2];
//n
var queryString = url[1].substr(url[1].lastIndexOf('?'));

(function ($) {
    /*
	 * url 검사
	 */
    $.sendPage = function (url, idx) {
        $('#sendUrl').val(url);
        $('#SM_INDEX').val(idx);
        $('#pagesend').attr('action', '/Common/UrlCheck');
        $('#pagesend').submit();
    };
    $.sendPage2 = function (url, idx) {
        $('#sendUrl').val(url);
        $('#SM_INDEX').val(idx);
        $('#frm').attr('action', '/Common/UrlCheck');
        $('#frm').submit();
    };
    /*
	 * 공통 세션체크
	 */
    $.sessionCheck = function () {
    	var url = '/Common/SessionCheck';

        $.ajax({
            url: url,
            data: {login : true},
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (data) {
            	if (data.resultCode != null && data.resultCode != 0) {
                    window.parent.parent.location.href = '/Logout';
                }
            }, error:function(request, status, error){
            	 window.parent.parent.location.href = '/Logout';
            }
        });


    };
    /*
     * 썸네일 이미지 미리보기
     */
    $.openPreview = function () {
    		 var xOffset = 10;
             var yOffset = 30;

             $(document).on("click",".thumbnailPreview",function(e){

                 $("body").append("<p id='preview'><img src='"+ $(this).attr("value") +"' width='200px' /></p>");
                 $("#preview")
                     .css("top",(e.pageY - xOffset) + "px")
                     .css("left",(e.pageX + yOffset) + "px")
                     .fadeIn("fast"); //미리보기 화면 설정 셋팅
             });

             $(document).on("mousemove",".thumbnailPreview",function(e){
                 $("#preview")
                     .css("top",(e.pageY - xOffset) + "px")
                     .css("left",(e.pageX + yOffset) + "px");
             });

             $(document).on("mouseout",".thumbnailPreview",function(){
                 $("#preview").remove();
             });
    };
    /*
	 * 공통 ajax 처리
	 */
    $.ajaxSetup({ cache: false });
    $.ajaxUtil = {
        //Ajax default
        //return true, false, data
        //$.ajaxUtil.ajaxDefault(url, param)
        ajaxDefault: function (url, param, focus, reset) {
            var isResult;

            if (param.login != true) {
            	$.sessionCheck();
			};

            $.ajax({
                url: url,
                data: param,
                type: 'post',
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0){
                    	isResult = data;
                    } else {
                    	$.modalCommon.alertView(data.messages.title, focus, data.messages.detail, reset);
                    	isResult = data;
                    }
                }, error:function(request, status, error){
                	$.sessionCheck();
                	if(request.status == 0){
                		alertAjaxOnOff = false;
                		//공통으로 변경해야할듯(버튼 관련)
                		$('#closeBtn').attr('disabled', false);
                		fnErrorMessage(request);
                	}else{
                		$.modalCommon.alertView(request.status + " : " + error, focus, null, reset);
                    	isResult = {"resultCode" : request.status, "messages" : {"title" : error}};
                	}

                }
            });

            return isResult;
        },
        //Ajax Array
        //return true, false, data;
        //$.ajaxUtil.ajaxArray(url, param)
        ajaxArray: function (url, param, focus, reset) {
            var isResult;
            $.sessionCheck();

            $.ajaxSettings.traditional = true;

            $.ajax({
                url: url,
                data: param,
                type: 'post',
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0){
                    	isResult = data;
                    } else {
                    	$.modalCommon.alertView(data.messages.title, focus, data.messages.detail, reset);
                    	isResult = data;
                    }
                }, error:function(request, status, error){
                	$.sessionCheck();
                	$.modalCommon.alertView(request.status + " : " + error, focus, null, reset);
                	isResult = {"resultCode" : request.status, "messages" : {"title" : error}};
                }
            });

            return isResult;
        },
        ajaxJson: function (url, param, cbFunction) {
            var isResult = true;

            $.sessionCheck();

            $.ajax({
                url: url,
                data: param,
                type: 'post',
                dataType: 'json',
                processData: false,
                success: function (data) {
                    if (data.resultCode == 0){
                    	isResult = data;
                    } else {
                    	$.modalCommon.alertView(data.messages.title, focus, data.messages.detail, reset);
                    	isResult = data;
                    }
                }, error:function(request, status, error){
                	$.sessionCheck();
                	$.modalCommon.alertView(request.status + " : " + error, focus, null, reset);
                	isResult = {"resultCode" : request.status, "messages" : {"title" : error}};
                }
            });

            return isResult;
        },
        //Ajax Text
        //return data
        //$.ajaxUtil.ajaxText(url, param)
        ajaxText: function (url, param) {
            var isResult = true;

            $.sessionCheck();

            $.ajax({
                url: url,
                data: param,
                type: 'post',
                dataType: 'text',
                async: false,
                success: function (data) {
                    if (data.resultCode == 0){
                    	isResult = data;
                    } else {
                    	$.modalCommon.alertView(data.messages.title, focus, data.messages.detail, reset);
                    	isResult = data;
                    }
                }, error:function(request, status, error){
                	$.sessionCheck();
                	$.modalCommon.alertView(request.status + " : " + error, focus, null, reset);
                	isResult = {"resultCode" : request.status, "messages" : {"title" : error}};
                }
            });

            return isResult;
        }
    };
    /*
    * 공통 modal 처리
    */
    $.modalCommon = {
        //일반 alert
        //$.modalCommon.alertView(msg);
        alertView: function (msgTitle, focus, msgDetail, reset) {

        	if (msgDetail != null && msgDetail != '') {
        		var slideMsg = document.createElement('div');
				var slideTitle = document.createElement('div');
				slideTitle.appendChild(document.createTextNode(msgTitle));

				var slideDetail = document.createElement('div');
				slideDetail.setAttribute('style', 'text-align: center;');

				var more = document.createElement('span');
				more.setAttribute('style', 'vertical-align: middle;')
				more.setAttribute('aria-hidden', 'true');
				more.setAttribute('onclick', '$(" .errorDetail").slideToggle( "slow" ); $(this).hide();');

				slideDetail.appendChild(more);

				var moreImg = document.createElement('img');
				moreImg.setAttribute('src', '/Content/images/more.png');

				more.appendChild(moreImg);

				var detailMsg = document.createElement('textarea');
				detailMsg.setAttribute('Style', 'display:none; width: 270px; height: 237px; resize:none;');
				detailMsg.setAttribute('class', 'errorDetail');
				detailMsg.setAttribute('readonly', 'none');
				detailMsg.appendChild(document.createTextNode(msgDetail));

				slideDetail.appendChild(detailMsg);

				slideMsg.appendChild(slideTitle);
				slideMsg.appendChild(slideDetail);

				msgTitle = slideMsg;
			}

            BootstrapDialog.show({
                title: '',
                size: BootstrapDialog.SIZE_SMALL,
                draggable: true,
                closable: false,
                message: msgTitle,
                cssClass: 'login-dialog',
                onhide: function () {
                	if (reset != null) {
                		for (var i = 0; i < reset.length; i++) {
                			$(reset[i]).val('');
                		}
					}

                    setTimeout(function () {
                    	$(focus).focus();
                    }, 500);
                },
                buttons: [{
                    label: '확인',
                    cssClass: 'btn-primary',
                    action: function (dialog) {
                    	alertAjaxOnOff = true;
                    	dialog.close();
                    }
                }]
            });
        },
        //remote page
        //$.modalCommon.loadView(title, url);
        loadView: function (title, url) {
            BootstrapDialog.show({
                title: title,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);

                    return $message;
                },
                data: {
                    'pageToLoad': url
                },
                onshown: function () {
                    $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
                }
            });
        },
        loadDataView: function (title, url, data) {
            BootstrapDialog.show({
                title: title,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad,data);

                    return $message;
                },
                data: {
                    'pageToLoad': url
                },
                onshown: function () {
                    $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
                }
            });
        },
        //$.modalCommon.loadFullView(title, url);
        loadFullView: function (title, url) {
            BootstrapDialog.show({
                title: title,
                draggable: true,
                closable: false,
                size: BootstrapDialog.SIZE_WIDE,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);

                    return $message;
                },
                data: {
                    'pageToLoad': url
                },
                onshown: function (dialog) {
                    $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
                }
            });
        },
        loadFullDataView: function (title, url, data) {
            BootstrapDialog.show({
                title: title,
                draggable: true,
                closable: false,
                size: BootstrapDialog.SIZE_WIDE,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad,data);

                    return $message;
                },
                data: {
                    'pageToLoad': url
                },
                onshown: function (dialog) {
                    $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
                }
            });
        },
        //$.modalCommon.close();
        close: function () {
            $.each(BootstrapDialog.dialogs, function (id, dialog) {
                dialog.close();
            });
        }
    }
    /*
     * 공통 modal RESET
     * $.fn.modalReset('myModal');
     */
    $.fn.modalReset = function (obj) {
        $(obj).on('hidden.bs.modal', function (e) {
            $(this).find("input").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked", "").end();
        });
    }
    /*
    * 공통 tree 처리
    */
    $.treeCommon = {
        //검색기능
        //obj1: search input id
        //obj2: tree list id
        //$.treeCommon.search(obj1, obj2);
        search:  function(obj1, obj2) {
            $(obj1).keyup(function () {
                var to = false;

                if (to) { clearTimeout(to); }
                to = setTimeout(function () {
                    var v = $(obj1).val();

                    $(obj2).jstree(true).search(v);
                }, 250);
            });
        }
    }
    /*
	 * 폼체크
	 * $.formCheck(obj, isEssential, maxLength, isKor, msg)
	 * $.formCheck('#id', 'Y', 50, 'Y', 'name');
	 * isEssential: 필수여부(Y,N)
	 * maxLength: 문자열길이 체크
	 * isKor: 문자열 체크시 한글인지
	 * msg: alert 메세지
	 */
    $.formCheck = function (obj, isEssential, maxLength, isKor, msg) {
    	$(obj).val($.trim($(obj).val()));
    	var formValue = $(obj).val();
        var formType = $(obj).attr('type');

        if (isEssential == 'Y') {
            var isCheck = true;

            switch (formType) {
                case 'text':
                    if ($.trim(formValue) == '' || $.trim(formValue) == null) {
                        $(obj).val('');
                        isCheck = false;
                    }
                    break;
                case 'checkbox':
                    if ($('input:checkbox[id="' + obj.replace('#', '') + '"]:checked').length == 0) {
                        isCheck = false;
                    }
                    break;
                case 'radio':
                    if ($('input:radio[id="' + obj.replace('#', '') + '"]:checked').length == 0) {
                        isCheck = false;
                    }
                    break;
                default:
                    if (formValue == '' || formValue == null) {
                        isCheck = false;
                    }
                    break;
            }
            if (!isCheck) {
                $.modalCommon.alertView(msg + '은(는) 필수 입력값 입니다.', obj);
                return false;
            }
        }
        if (maxLength > 0) {
            if (maxLength < parseInt(formValue.length)) {
            	$.modalCommon.alertView( msg + '은(는) ' + parseInt(maxLength) + '자 초과\n입력할 수 없습니다.');
                return false;
            }
        }

        return true;
    };

    $.formCommaCheck = function (obj, isEssential, maxLength, msg) {
    	$(obj).val($.trim($(obj).val()));
    	var formValue = $(obj).val();
        var isCheck = true;

        if ($.trim(formValue) == '' || $.trim(formValue) == null) {
            $(obj).val('');
            isCheck = false;
        }
        if (isEssential == 'Y') {
        	if (!isCheck) {
                $.modalCommon.alertView(msg + '은(는) 필수 입력값 입니다.', obj);
                return false;
            }
        }


        if (maxLength > 0) {
            if (maxLength < parseInt(formValue.replace(/,/gi, "").length)) {
            	$.modalCommon.alertView( msg + '은(는) ' + parseInt(maxLength)  + '자 초과\n입력할 수 없습니다.');
                return false;
            }
        }

        return true;
    };
    /*
	 * 기타 폼체크
	 * $.formOtherUtil. + function(param)
	 */
    $.formOtherUtil = {
        //아이디 유효성 체크 영문자(소) + 숫자 + 6~15자리
        //$.formOtherUtil.isIdCheck(obj);
        isIdCheck: function (obj) {
            var chk1 = /[\S]{6,15}$/;
            var chk2 = /^[a-z0-9]+$/g;

            return chk1.test(obj) && chk2.test(obj) ? true : false;
        },
        //비밀번호체크 8~20자리 + 영문자(대)+영문자(소)+숫자+특수문자
        //$.formOtherUtil.isPwdCheck(obj);
        isPwdCheck: function (obj) {
            var chk1 = /[\S]{9,20}$/;
            var chk2 = /[a-z]/;
            var chk3 = /[A-Z]/;
            var chk4 = /\d/;
            var chk5 = /[^a-zA-Z0-9]/;

            return chk1.test(obj) && chk2.test(obj) && chk4.test(obj) && chk5.test(obj) ? true : false;
        },
        //비밀번호 반복문자 4개 이상 처리
        //$.formOtherUtil.isPwdOverCheck(obj);
        isPwdOverCheck: function (obj) {
            var chk1 = /(\w)\1\1\1/;

            return chk1.test(obj) ? true : false;
        },
        //list 체크박스 array id
        //$.formOtherUtil.isArrCheck(obj);
        isArrCheck: function (obj) {
            var arr = new Array();
            var i = 0;

            $('input[id="' + obj + '"]:checked').each(function () {
                arr[i] = $(this).val();
                i++;
            });

            return arr;
        },
        isArrNameCheck: function (obj) {
            var arr = new Array();
            var i = 0;

            $('input[name="' + obj + '"]:checked').each(function () {
                arr[i] = $(this).val();
                i++;
            });

            return arr;
        },
        //list 체크박스 array name
        //$.formOtherUtil.isArrNameCheck(obj);
        isArrNameCheck: function (obj) {
            var arr = new Array();
            var i = 0;

            $('input[name="' + obj + '"]:checked').each(function () {
                arr[i] = $(this).val();
                i++;
            });

            return arr;
        },
        //list 체크박스 array id, attr
        //$.formOtherUtil.isArrAttrCheck(obj, attrName);
        isArrAttrCheck: function (obj, attrName) {
            var arr = new Array();
            var i = 0;

            $('input[id="' + obj + '"]:checked').each(function () {
                arr[i] = $(this).attr(attrName);
                i++;
            });

            return arr;
        },
        //텍스트박스 array id
        //$.formOtherUtil.isArrIdText(obj);
        isArrIdText: function (obj) {
            var arr = new Array();
            var i = 0;

            $('input[id="' + obj + '"]').each(function () {
                if ($.trim($(this).val()) != '') {
                    arr[i] = $.trim($(this).val());
                    i++;
                }
            });

            return arr;
        },
        //텍스트박스 array name
        //$.formOtherUtil.isArrNameText(obj);
        isArrNameText: function (obj) {
            var arr = new Array();
            var i = 0;

            $('input[name="' + obj + '"]').each(function () {
                if ($.trim($(this).val()) != '') {
                    arr[i] = $.trim($(this).val());
                    i++;
                }
            });

            return arr;
        },
        //텍스트박스 숫자 Check
        //$.formOtherUtil.isNumCheck(obj);
        isNumCheck: function (obj) {
            var reg = /^(\s|\d)+$/;

            if (reg.test($(obj).val())) {
                return true;
            }
            else {
                $.modalCommon.alertView( '숫자만 입력하세요.');
                $(obj).val('');
                return false;
            }
        },
        //radio id
        //$.formOtherUtil.isIdRadio(obj);
        isIdRadio: function (obj) {
            return val = $(':radio[id="' + obj + '"]:checked').val();
        },
        //radio name
        //$.formOtherUtil.isNameRadio(obj);
        isNameRadio: function (obj) {
            return $(':radio[name="' + obj + '"]:checked').val();
        }
    };
    /*
	 * text box 이벤트 처리
	 * style="ime-mode:disabled" onKeyPress, onKeyUp, onKeyDown = "$.textBox(this, 'case값', 'Y', 'Y');"
	 * case=> num: 숫자만, lower: 영문 소문자로, upper: 영문 대문자로, ko 한글만
	 * isEmpty: Y(공백입력불가)
	 * isSpecialStr: Y(특수문자입력불가)
	 */
    $.textBox = function (obj, val, isEmpty, isSpecialStr) {
        if (isEmpty == 'Y') {
            if (event.keyCode == 32) {
                event.preventDefault();
            }
        }
        if (isSpecialStr == 'Y') {
            if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97) || (event.keyCode > 122 && event.keyCode < 125)) {
                event.preventDefault();
            }
        }
        switch (val) {
            case 'num':
                if (event.keyCode < 48 || event.keyCode > 57) {
                	if (event.keyCode != 45) {
                        event.preventDefault();
                    }
                }
                break;
            case 'lower':
                $(obj).keyup(function () {
                    var value = $(obj).val();
                    if (value != '') {
                        $(obj).val(value.toLowerCase());
                    }
                });
                break;
            case 'upper':
                $(obj).keyup(function () {
                    var value = $(obj).val();
                    if (value != '') {
                        $(obj).val(value.toUpperCase());
                    }
                });
                break;
            case 'ko':
                $(obj).keyup(function () {
                    var koCheck = /([^가-힣ㄱ-ㅎㅏ-ㅣ\x20])/i;
                    var value = $(obj).val();
                    if (koCheck.test(value)) {
                        $(obj).val('');
                    }
                });
                break;
            case 'en':
                $(obj).keyup(function (event) {
                    if (!(event.keyCode >= 37 && event.keyCode <= 40)) {
                        var inputVal = $(this).val();

                        $(this).val(inputVal.replace(/[^a-z-z0-9]/gi, ''));
                    }
                });
            default:
                break;
        }
    };
    /*
	 * checkbox 이벤트 처리 모음
	 */
    $.checkboxUtil = {
        //onclick='$.checkboxUtil.checkAll(this, obj);'
        checkAll: function (id, obj) {
            if ($(id).is(':checked')) {
                $('input[type=checkbox][id="' + obj + '"]').each(function () {
            		if ($(this).attr('disabled') != 'disabled') {
            			$(this).prop('checked', true);
            		}
                });
            }
            else {
                $('input[type=checkbox][id="' + obj + '"]').each(function () {
                    $('input[type=checkbox][id="' + obj + '"]').prop('checked', false);
                });
            }
        },
        checkNameAll: function (id, obj) {
            if ($(id).is(':checked')) {
                $('input[type=checkbox][name="' + obj + '"]').each(function () {
            		if ($(this).attr('disabled') != 'disabled') {
            			$(this).prop('checked', true);
            		}
                });
            }
            else {
                $('input[type=checkbox][name="' + obj + '"]').each(function () {
                    $('input[type=checkbox][name="' + obj + '"]').prop('checked', false);
                });
            }
        },
		//onclick='$.checkboxUtil.checkAllChk(this, obj);'
        checkAllChk: function (id, obj) {
        	var i = 0;
        	var allid = obj + '_ALL';

        	$('input[type=checkbox][id="' + obj + '"]').each(function () {
        		if ($('input:checkbox[id="' + obj + '"]:checked')) {
        			i++;
        		}
        	});


        	if ((i-$('input:checkbox[id="' + obj + '"]:disabled').length) == $('input:checkbox[id="' + obj + '"]:checked').length) {
        		$("input[name=ARRIDX_ALL]").prop('checked', true);
//        		$('input:checkbox[id="' + allid + '"]').prop('checked', true);
        	}
        	else {
        		$("input[name=ARRIDX_ALL]").prop('checked', false);
//        		$('input:checkbox[id="' + allid + '"]').prop('checked', false);
        	}
        },
      //onclick='$.checkboxUtil.checkAllChk(this, obj);'
        checkNameAllChk: function (id, obj) {
        	var i = 0;
        	var allid = obj + '_ALL';

        	$('input[type=checkbox][name="' + obj + '"]').each(function () {
        		if ($('input:checkbox[name="' + obj + '"]:checked')) {
        			i++;
        		}
        	});
        	if (i == $('input:checkbox[name="' + obj + '"]:checked').length) {
        		$('input:checkbox[name="' + allid + '"]').prop('checked', true);
        	}
        	else {
        		$('input:checkbox[name="' + allid + '"]').prop('checked', false);
        	}
        },
        //onclick='$.checkboxUtil.checkDayCaseChk(this, obj);'
        checkDayCaseChk: function (id, obj) {
            var allid = obj + '_ALL';
            var weekdaysid = obj + '_WEEKDAYS';
            var weekendid = obj + '_WEEKEND';

            if ($(id).is(':checked')) {
                $('input[type=checkbox][id="' + obj + '"]').each(function () {
                    $('input[type=checkbox][id="' + obj + '"]').prop('checked', false);
                });
                switch ($(id).attr('id')) {
                    case allid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            $('input:checkbox[id="' + obj + '"]').prop('checked', true);
                        });
                        $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', false);
                        $('input:checkbox[id="' + weekendid + '"]').prop('checked', false);
                        break;
                    case weekdaysid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            if ($(this).val() == '2' || $(this).val() == '3' || $(this).val() == '4' || $(this).val() == '5' || $(this).val() == '6') {
                                $(this).prop('checked', true);
                            }
                        });
                        $('input:checkbox[id="' + allid + '"]').prop('checked', false);
                        $('input:checkbox[id="' + weekendid + '"]').prop('checked', false);
                        break;
                    case weekendid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            if ($(this).val() == '1' || $(this).val() == '7') {
                                $(this).prop('checked', true);
                            }
                        });
                        $('input:checkbox[id="' + allid + '"]').prop('checked', false);
                        $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', false);
                        break;
                }
            }
            else {
                switch ($(id).attr('id')) {
                    case allid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            $('input[type=checkbox][id="' + obj + '"]').prop('checked', false);
                        });
                        break;
                    case weekdaysid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            if ($(this).val() == '2' || $(this).val() == '3' || $(this).val() == '4' || $(this).val() == '5' || $(this).val() == '6') {
                                $(this).prop('checked', false);
                            }
                        });
                        break;
                    case weekendid:
                        $('input[type=checkbox][id="' + obj + '"]').each(function () {
                            if ($(this).val() == '1' || $(this).val() == '7') {
                                $(this).prop('checked', false);
                            }
                        });
                        break;
                }
            }
        },
        //onclick='$.checkboxUtil.checkDayChk(this, obj);'
        checkDayChk: function (id, obj) {
            var i = 0;
            var allid = obj + '_ALL';
            var weekdaysid = obj + '_WEEKDAYS';
            var weekendid = obj + '_WEEKEND';
            var cnt = 0;

            $('input[type=checkbox][id="' + obj + '"]').each(function () {
                if ($('input:checkbox[id="' + obj + '"]:checked')) {
                    i++;
                }
            });

            if (i == $('input:checkbox[id="' + obj + '"]:checked').length) {
                $('input:checkbox[id="' + allid + '"]').prop('checked', true);
                $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', false);
                $('input:checkbox[id="' + weekendid + '"]').prop('checked', false);
            }
            else if ($('input:checkbox[id="' + obj + '"]:checked').length == 5) {
                $('input[id="' + obj + '"]:checked').each(function () {
                    if ($(this).val() == '2' || $(this).val() == '3' || $(this).val() == '4' || $(this).val() == '5' || $(this).val() == '6') {
                        cnt++;
                    }
                });

                if (cnt == 5) {
                    $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', true);
                    $('input:checkbox[id="' + allid + '"]').prop('checked', false);
                    $('input:checkbox[id="' + weekendid + '"]').prop('checked', false);
                }
            }
            else if ($('input:checkbox[id="' + obj + '"]:checked').length == 2) {
                $('input[id="' + obj + '"]:checked').each(function () {
                    if ($(this).val() == '1' || $(this).val() == '7') {
                        cnt++;
                    }
                });

                if (cnt == 2) {
                    $('input:checkbox[id="' + weekendid + '"]').prop('checked', true);
                    $('input:checkbox[id="' + allid + '"]').prop('checked', false);
                    $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', false);
                }
            }
            else {
                $('input:checkbox[id="' + allid + '"]').prop('checked', false);
                $('input:checkbox[id="' + weekdaysid + '"]').prop('checked', false);
                $('input:checkbox[id="' + weekendid + '"]').prop('checked', false);
            }
        },
        //checkbox 폼 체크 여부 확인 $.checkboxUtil.check(id);
        check: function (obj) {
            if ($('input:checkbox[id="' + obj + '"]:checked').length == 0) {
                return false;
            }
            else {
                return true;
            }
        },
      //tree checkbox  체크 추가 $.checkboxUtil.treeCheckBoxAdd(obj, pObj);
        treeCheckBoxAdd: function (obj, pObj) {
    		if (pObj != 'undefined') {
    			if ($('input[id="' + obj + '"][parentId="' + pObj + '"]').length == $('input[id="' + obj + '"][parentId="' + pObj + '"]:checked').length) {
    				for (var i = 0; i < $('input[id="' + obj + '"]').length; i++) {
    					if ($('input[id="' + obj + '"]').eq(i).val() == pObj) {
    						$('input[id="' + obj + '"]').eq(i).prop('checked', 'checked');
    						$.checkboxUtil.treeCheckBoxAdd(obj, $('input[id="' + obj + '"]').eq(i).attr('parentId'));
    					}
    				}
    			}
    		}
    	},
    	//tree checkbox  체크 해제 $.checkboxUtil.treeCheckBoxRemove(obj, pObj);
    	treeCheckBoxRemove: function (obj, pObj) {
    		if (pObj != 'undefined') {
    			for (var i = 0; i < $('input[id="' + obj + '"]').length; i++) {
    				if ($('input[id="' + obj + '"]').eq(i).val() == pObj) {
    					$('input[id="' + obj + '"]').eq(i).removeAttr('checked');
    					$.checkboxUtil.treeCheckBoxRemove(obj, $('input[id="' + obj + '"]').eq(i).attr('parentId'));
    				}
    			}
    		}
    	},
    	//checkbox 체크 전체 해제 $.checkboxUtil.checkAllFalse();
    	checkAllFalse: function () {
    		$('input').filter(':checkbox').prop('checked',false);
    	}
    };
    /*
	 * 문자 byte체크
	 * $.getStrLength(value)
	 */
    $.getStrLength = function (str) {
        var len = 0;

        if (str == '') {
            return 0;
        }
        else {
            for (var i = 0; i < str.length; i++) {
                var chr = str.charCodeAt(i);

                if (chr > 0 && chr < 255) {
                    len = len + 1;
                }
                else {
                    len = len + 2;
                }
            }

            return len;
        }
    };
    /*
	 * 쿠키1
	 */
    $.Cookie = {
        cookie_arr: null,
        set: function (name, value, options) {
            options = options || {};
            this.cookie_arr = [escape(name) + '=' + escape(value)];

            //-- expires
            if (options.expires) {
                if (typeof options.expires === 'object' && options.expires instanceof Date) {
                    var date = options.expires;
                    var expires = 'expires=' + date.toUTCString();
                    this.cookie_arr.push(expires);
                }
            }
            else if (options.expires_day) {
                this.set_expires_date(options.expires_day, 24 * 60 * 60);
            }
            else if (options.expires_hour) {
                this.set_expires_date(options.expires_hour, 60 * 60);
            }
            //-- domain
            if (options.domain) {
                var domain = 'domain=' + options.domain;
                this.cookie_arr.push(domain);
            }
            //-- path
            if (options.path) {
                var path = 'path=' + options.path;
                this.cookie_arr.push(path);
            }
            //-- secure
            if (options.secure === true) {
                var secure = 'secure';
                this.cookie_arr.push(secure);
            }
            document.cookie = this.cookie_arr.join('; ');
        },
        get: function (name) {
            var nameEQ = escape(name) + '=';
            var ca = document.cookie.split(';');

            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1, c.length);
                if (c.indexOf(nameEQ) == 0) return unescape(c.substring(nameEQ.length, c.length));
            }
            return null;
        },
        del: function (name, options) {
            options = options || {};
            options.expires_day = -1;
            this.set(name, '', options);
        },
        set_expires_date: function (expires, time) {
            var date = new Date();
            date.setTime(date.getTime() + (expires * time * 1000));
            var expires = 'expires=' + date.toUTCString();
            this.cookie_arr.push(expires);
        }
    };
    /*
     * 쿠키2
     */
    $.cookie = function (name, value, options) {
        if (typeof value != 'undefined') { // name and value given, set cookie
            options = options || {};
            if (value === null) {
                value = '';
                options.expires = -1;
            }
            var expires = '';
            if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                var date;
                if (typeof options.expires == 'number') {
                    date = new Date();
                    date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                } else {
                    date = options.expires;
                }
                expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
            }
            var path = options.path ? '; path=' + options.path : '';
            var domain = options.domain ? '; domain=' + options.domain : '';
            var secure = options.secure ? '; secure' : '';

            document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
        }
        else { // only name given, get cookie
            var cookieValue = null;

            if (document.cookie && document.cookie != '') {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var cookie = jQuery.trim(cookies[i]);
                    // Does this cookie string begin with the name we want?
                    if (cookie.substring(0, name.length + 1) == (name + '=')) {
                        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                        break;
                    }
                }
            }
            return cookieValue;
        }
    };
    /*
	 * Timer
	 * $.startTimer(10);
	 */
    $.startTimer = function (secs, viewChk, refreshChk) {
        var startTime = '';
        var countAmt = '';
        var interval = '';
        var timeText = '';
        var nowTime = function () {
            return ((new Date()).getTime());
        };
        var tick = function () {
            var elapsed = nowTime() - startTime;
            var cnt = countAmt - elapsed;
            var d = new Date(); // for now
            var h = d.getHours();
            var m = d.getMinutes();
            var s = d.getSeconds();

            if (h.toString().length == 1) {
                h = '0' + h;
            }
            if (m.toString().length == 1) {
                m = '0' + m;
            }
            if (s.toString().length == 1) {
                s = '0' + s;
            }

            timeText = h + ':' + m + ':' + s;

            if (cnt > 0) {
                if (viewChk) {
                    viewFn(timeText, Math.round(cnt / 1000)); //local function 사용시
                }
            }
            else {
                if (refreshChk) {
                    refreshFn(timeText); //local function 사용시
                }

                $.sessionCheck();
                clearInterval(interval);
                $.startTimer(secs, viewChk, refreshChk);
            }
        };

        clearInterval(interval);
        countAmt = secs * 1000;
        startTime = nowTime();
        interval = setInterval(tick, 1000);
    };
    /*
	 * 엔터 처리
	 */
    $.enterUtil = {
        //onKeyPress="$.enterUtil.index(form, fnCall);"
        index: function (form, fnCall) {
            if (event.keyCode == 13) {
            	event.preventDefault ? event.preventDefault() : event.returnValue = false;

                if (fnCall == 1) {
                    login();
                }
                else {
                    pwdChange();
                }
            }
        },
        //onKeyPress="$.enterUtil.search(this.form);"
        searchForm: function (form) {
            if (event.keyCode == 13) {
                event.preventDefault();
                event.returnValue = false;
                searchView();
            }
        },
        //onKeyPress="$.enterUtil.modal(this.form);"
        modal: function (form) {
            if (event.keyCode == 13) {
                event.preventDefault();
                event.returnValue = false;
            }
        },
    };
    /*
     * 기타모음
     */
    //onkeyup="$.fn.nextField('#obj1', '#obj2', 1, 'Y', '');" maxlength="1"
    $.fn.nextField = function (obj1, obj2, len, isNext, nextObj) {
        if ($(obj1).val().length == len) {
            if (obj1 == obj2 && isNext == 'Y') {
                $(nextObj).trigger('click');
                $(nextObj).setCursorToTextEnd();
            }
            else {
                $(obj2).focus();
            }
        }
    };

    $.fn.setCursorToTextEnd = function () {
        var $initialVal = this.val();
        this.val($initialVal);
    };
    //$.fn.setDateTimeFormat(data);
    $.fn.setDateTimeFormat = function (date) {
        var leadingZeros = function (n, digits) {
            var zero = '';
            n = n.toString();

            if (n.length < digits) {
                for (i = 0; i < digits - n.length; i++)
                    zero += '0';
            }
            return zero + n;
        };
        var d = new Date(date);
        var s = leadingZeros(d.getFullYear(), 4) + '-' +
                    leadingZeros(d.getMonth() + 1, 2) + '-' +
                    leadingZeros(d.getDate(), 2) + ' ' +

                    leadingZeros(d.getHours(), 2) + ':' +
                    leadingZeros(d.getMinutes(), 2) + ':' +
                    leadingZeros(d.getSeconds(), 2);

        return s;
    };
    //$.fn.setTimeFormat(second);
    $.fn.setTimeFormat = function (sec) {
        return (sec < 3600 * 10 ? "0" : "") + Math.floor(sec / (3600)) + new Date(sec * 1000).toISOString().slice(13, 19);
    };
    $.fn.toKorChars = function (val) {
        var cCho = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'],
            cJung = ['ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'],
            cJong = ['', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'],
            cho, jung, jong;

        var str = val,
            cnt = str.length,
            chars = [],
            cCode;

        for (var i = 0; i < cnt; i++) {
            cCode = str.charCodeAt(i);

            if (cCode == 32) { continue; }

            // 한글이 아닌 경우
            if (cCode < 0xAC00 || cCode > 0xD7A3) {
                chars.push(str.charAt(i));
                continue;
            }

            cCode = str.charCodeAt(i) - 0xAC00;

            jong = cCode % 28; // 종성
            jung = ((cCode - jong) / 28) % 21 // 중성
            cho = (((cCode - jong) / 28) - jung) / 21 // 초성
            chojung = cCho[cho] + cJung[jung];

            chars.push(cCho[cho], chojung);
            if (cJong[jong] !== '') { chars.push(cJong[jong]); }
        }

        return chars;
    };


})($);

/**
 * 그리드 조회
 * @param id: 아이디
 * @param url: 주소
 * @param pageLen: 한번에 노출될 페이지
 * @param arrColumns: 컬럼
 * @param arrColumnDefs: 컬럼 조건
 * @param arrButtons: 버튼
 * @returns
 */

var returnDataTable;
var alertAjaxOnOff = true;

function commonPagingDateTable(optObject)
{
	$.sessionCheck();
	if(!optObject.pageLen)
		optObject.pageLen = 10;

	if(!optObject.iDisplayLength)
		optObject.iDisplayLength = 10;

	if(optObject.searching == null)
		optObject.searching = true;

	if(optObject.bLengthChange == null)
		optObject.bLengthChange = true;

	if(!optObject.arrColumns)
		optObject.arrColumns = [];

	if(!optObject.strDom)
		optObject.strDom = "";

	if(!optObject.arrButtons)
		optObject.arrButtons = null;

	if(!optObject.serverParams){
		optObject.serverParams = function ( aoData ) {
            aoData.push( { "name": "test", "value": "123" } );
        }
	}

	if(!optObject.bStateSave){
		optObject.bStateSave = false;
	}

	if(!optObject.fnServerData){
		optObject.fnServerData = function (sSource, aoData, fnCallback) {
			// 체크박스 풀기
			$("input[name=ARRIDX_ALL]").prop('checked', false);

			// 20190531 sort요청한 컬럼명 넘기기 추가
			var orderName = "", sortIndex = "";
			for(var idx=0; idx<aoData.length; idx++){
				if(aoData[idx].name == "iSortCol_0"){
					sortIndex = aoData[idx].value;
					break;
				}
			}

			for(var idx=0; idx<aoData.length; idx++){

				if(aoData[idx].name == "mDataProp_" + sortIndex){
					orderName = aoData[idx].value;
					aoData.push( { "name": "sSortName", "value": orderName } );
					break;
				}
			}

			 $.post(sSource, aoData, function (json) {
				 if(json.resultCode == 0){
					 fnCallback (json);
	 					activeDisabled();
	 					if(json.data == null || json.data.length == 0)
	 						$(".dataTables_paginate.paging_simple_numbers").attr("style","display:none");
	 					else
	 						$(".dataTables_paginate.paging_simple_numbers").attr("style","text-align: center;");
				 }else{
					 alertAjaxOnOff = false;
					 fnErrorMessage(json);

				 }
             }).fail(function handleError(jqXHR, statusText, errorThrown) {
            	 $.sessionCheck();
             });
		 }
	}

	if(optObject.language != null){
		optObject.language.zeroRecords = "데이터가 없습니다.";
		optObject.language.lengthMenu = "_MENU_ 개씩 보기";
	}
	else {
		optObject.language = {zeroRecords : "데이터가 없습니다.", lengthMenu:"_MENU_ 개씩 보기" };
	}
	optObject.language.processing = '<div class="wrap-loading"><div>DATA LOADING...</div></div>';


	if(optObject.type == 'undefined' || optObject.type == null || optObject.type == '1'){
		returnDataTable = $(optObject.id).DataTable(
		    	{
		    	 "paging": true,
				 "processing": true,
				 "autoWidth": false,
		         "ordering": true,
		         "order": [], //[ 0, "desc" ]
		         "serverSide": true,
		         "bLengthChange": optObject.bLengthChange,
		         "bDestroy" : true,
		         "pageLength": optObject.pageLen,
		         'iDisplayLength': optObject.iDisplayLength,
		         "pagingType" : "simple_numbers", /*numbers, simple, simple_numbers, full, full_numbers, first_last_numbers*/
		         "lengthMenu" : [ [ 10, 30, 50, 100 ], [ 10, 30, 50, 100 ] ],
		         "searching": optObject.searching ,
		         "language": optObject.language,
		         "info":     false,
		         "fnServerParams" : optObject.serverParams,
		         "sAjaxSource" : optObject.url,
		         "sServerMethod" : "POST",
		         "columns" : optObject.arrColumns,
		         "buttons" : optObject.arrButtons,
		         "columnDefs" : optObject.arrColumnDefs,
		         "fnDrawCallback" : optObject.fnDrawCallback,
		         "fnRowCallback" : optObject.fnRowCallback,
		         "fnServerData": optObject.fnServerData,
		         "bStateSave":optObject.bStateSave
		      });
	}else if(optObject.type == '2'){

		// 조회조건 없음
		returnDataTable = $(optObject.id).DataTable(
		    	{
		    	 "paging": false,
				 "processing": true,
		         "ordering": true,
		         "autoWidth": false,
		         "order": [], //[ 0, "desc" ]
		         "serverSide": false,
		         "bLengthChange": true,
		         "bDestroy" : true,
		         "pageLength": optObject.pageLen,
		         'iDisplayLength': optObject.iDisplayLength,
		         "pagingType" : "simple_numbers", /*numbers, simple, simple_numbers, full, full_numbers, first_last_numbers*/
		         "lengthMenu" : [ [ 10, 30, 50, 100 ], [ 10, 30, 50, 100 ] ],
		         "searching": false,
		         "language": optObject.language,
		         "info":     false,
		         "fnServerParams" : optObject.serverParams,
		         "sAjaxSource" : optObject.url,
		         "sServerMethod" : "POST",
		         "columns" : optObject.arrColumns,
		         "buttons" : optObject.arrButtons,
		         "columnDefs" : optObject.arrColumnDefs,
		         "fnDrawCallback" : optObject.fnDrawCallback,
		         "fnRowCallback" : optObject.fnRowCallback,
		         "fnServerData": optObject.fnServerData,
		         "dom" : optObject.strDom
		      });
	}else if(optObject.type == '3'){
		// 조회조건 없음
		returnDataTable = $(optObject.id).DataTable(
    	{
    		"paging": true,
			 "processing": true,
	         "ordering": true,
	         "autoWidth": false,
	         "order": [], //[ 0, "desc" ]
	         "serverSide": true,
	         "bLengthChange": true,
	         "bDestroy" : true,
	         "pageLength": optObject.pageLen,
	         'iDisplayLength': optObject.iDisplayLength,
	         "pagingType" : "simple_numbers", /*numbers, simple, simple_numbers, full, full_numbers, first_last_numbers*/
	         "lengthMenu" : [ [ 10, 30, 50, 100 ], [ 10, 30, 50, 100 ] ],
	         "searching": true,
	         "language": optObject.language,
	         "info":     false,
	         "fnServerParams" : optObject.serverParams,
	         "sAjaxSource" : optObject.url,
	         "sServerMethod" : "POST",
	         "columns" : optObject.arrColumns,
	         "buttons" : optObject.arrButtons,
	         "columnDefs" : optObject.arrColumnDefs,
	         "fnDrawCallback" : optObject.fnDrawCallback,
	         "fnRowCallback" : optObject.fnRowCallback,
	         "fnServerData": optObject.fnServerData,
	         "dom" : optObject.strDom
	      });
	}else if(optObject.type == '4'){
		returnDataTable = $(optObject.id).DataTable(
		    	{
		    	 "paging": true,
				 "processing": true,
				 "autoWidth": false,
		         "ordering": true,
		         "scrollX": "100%",
		         "order": [], //[ 0, "desc" ]
		         "serverSide": true,
		         "bLengthChange": optObject.bLengthChange,
		         "bDestroy" : true,
		         "pageLength": optObject.pageLen,
		         'iDisplayLength': optObject.iDisplayLength,
		         "pagingType" : "simple_numbers", /*numbers, simple, simple_numbers, full, full_numbers, first_last_numbers*/
		         "lengthMenu" : [ [ 10, 30, 50, 100 ], [ 10, 30, 50, 100 ] ],
		         "searching": optObject.searching ,
		         "language": optObject.language,
		         "info":     false,
		         "fnServerParams" : optObject.serverParams,
		         "sAjaxSource" : optObject.url,
		         "sServerMethod" : "POST",
		         "columns" : optObject.arrColumns,
		         "buttons" : optObject.arrButtons,
		         "columnDefs" : optObject.arrColumnDefs,
		         "fnDrawCallback" : optObject.fnDrawCallback,
		         "fnRowCallback" : optObject.fnRowCallback,
		         "fnServerData": optObject.fnServerData,
		         "bStateSave":optObject.bStateSave
		      });
	}

	// 데이터 테이블 하단 페이징 중앙 정렬
    $(".dataTables_paginate").parent().removeClass("col-sm-7").addClass("col-sm-12");
    $(".dataTables_paginate").css({"text-align" : "center"});

	return returnDataTable;


}

/**
 * 활성화 여부
 */
function commonActive(val, param, url){
	param.activeYn = $('#active'+val).prop('checked') ? "Y":"N";

	BootstrapDialog.confirm({
       title: '',
       message: param.activeYn == "Y" ? "활성화 하시겠습니까?":"비활성화 하시겠습니까?",
       type: BootstrapDialog.TYPE_WARNING,
       closable: false,
       draggable: false,
       btnCancelLabel: '취소',
       btnOKLabel: '저장',
       btnOKClass: 'btn-warning',
       callback: function (result) {
           if (result) {
               var dResult = $.ajaxUtil.ajaxArray(url, param);

               if (dResult.resultCode == 0){
                	$.modalCommon.alertView( (param.activeYn != "Y" ? "비":"")+'활성화 되었습니다.');
               }
           }

           returnDataTable.ajax.reload(null, false);
       }
	});
}

/**
 * 공통 삭제
 * @param val
 * @param param
 * @param url
 * @returns
 */
function commonDelete(arr, callBack){
	if (arr.length == 0)
        $.modalCommon.alertView('삭제할 체크박스를 선택해주세요.');
    else {
        BootstrapDialog.confirm({
            title: '',
            message: '삭제하시겠습니까?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: false,
            draggable: true,
            btnCancelLabel: '취소',
            btnOKLabel: '삭제',
            btnOKClass: 'btn-warning',
            callback: callBack
        });
    }
}

/**
 * 공통 저장
 * @param val
 * @param param
 * @param url
 * @returns
 */
function commonSave(msg, callBack){
	BootstrapDialog.confirm({
		title : '',
		message : msg == "C" ? "저장하시겠습니까?":'저장하시겠습니까?',
		type : BootstrapDialog.TYPE_WARNING,
		closable : false,
		draggable : true,
		btnCancelLabel: '취소',
		btnOKLabel: '저장',
		btnOKClass : 'btn-warning',
		callback : callBack
	});
}

/**
 * 트리 높이
 * @param val
 * @returns
 */
function treeHeight(val){
	var docuHeight = $(window).height() - $('#treeList').offset().top;
    if(val == 'resize'){
    	$("#scrollbody").css('height',docuHeight);
	    $("#treeList").css('height',docuHeight);
    } else {
    	$("#scrollbody").css('height',docuHeight);
	    $("#treeList").css('height',docuHeight);
    }
}

/**
 * 트리 높이 (브랜드, 가맹점 버튼이 있는 용도)
 * @param val
 * @returns
 */
function treeBtnHeight(val){
	var docuHeight = $(window).height() - $('#treeList').offset().top;
    if(val == 'resize'){
    	$("#scrollbody").css('height',docuHeight);
	    $("#treeList").css('height',docuHeight);
    } else {
    	$("#scrollbody").css('height',val);
	    $("#treeList").css('height',val);
    }
}

/**
 * 트리 조회
 * @param num
 * @param paramUrl
 * @param param
 * @param type
 * @returns
 */
function treeSearchList(num, paramUrl, param, type) {
	$.sessionCheck();
	if (num > 0)
		$('#treeList').jstree('destroy');

	var treeId = "";

	var url = "";

	//고정  트리가 아니면 선택된 값을 위해서 쿼리를 따로 태운다.
	if(paramUrl)
	{
		url = paramUrl;
		treeId = '#targetTreeList';
	}
	//고정 트리일 경우 기존값 그대로 조회
	else
	{
		param = {
			serchKey : $('#treeSearch').val()
		}
		url = '/Common/SearchTree';
		treeId = '#treeList';
	}


	var result = $.ajaxUtil.ajaxDefault(url, param).data;
	var jsonData = result.data;

	$(treeId)[0].innerHTML = '';
	var ul = $(treeId);
    var htmlStr = '';
    var domainValue = "";

    if (jsonData.length > 0) {
    	for (var i = 0; i < jsonData.length; i++) {
    		var brandValue = "";
    		var groupValue = "";
    		var francValue = "";
    		var deviceValue = "";

    		if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 1)
    			domainValue = jsonData[i].IDX.split('_')[0];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 2)
				brandValue = jsonData[i].IDX.split('_')[1];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 3)
				groupValue = jsonData[i].IDX.split('_')[2];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 4)
				francValue = jsonData[i].IDX.split('_')[3];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 5)
				deviceValue = jsonData[i].IDX.split('_')[4];
			
        	if (i == 0) {
                var ulp = $('<ul>').appendTo(ul);
                var li = $('<li>').attr('id', jsonData[i].IDX)
                                     .attr('depth', jsonData[i].DEPTH)
                                     .attr('data-jstree', '{\"opened\":true}')
                                     .attr('domainidx',domainValue)
                                     .attr('brandidx',0)
                                     .attr('groupidx', 0)
	                                 .attr('francidx', 0)
	                                 .attr('deviceidx', 0)
	                                 .attr('ischecked', jsonData[i].CHECKED)
                                     .text(jsonData[i].NAME)
                                     .appendTo(ulp);
                $('<ul>').attr('id', jsonData[i].IDX).appendTo(li);
            }
            else {
                var parent = ul.find('ul[id="' + jsonData[i].PARENT_IDX + '"]');

                if (parent != null) {
                	if (jsonData[i].DEPTH_NAME =='BRAND') {

                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
				                           .attr('id', jsonData[i].IDX)
				                           .attr('idx', jsonData[i].IDX)
				                           .attr('depth', jsonData[i].DEPTH)
				                           .attr('domainidx', domainValue)
				                           .attr('brandidx', brandValue)
				                           .attr('groupidx', 0)
				                           .attr('francidx', 0)
				                           .attr('deviceidx', 0)
				                           .attr('ischecked', jsonData[i].CHECKED)
				                           .text(jsonData[i].NAME)
				                           .appendTo(parent);
 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
					}
                	else if (jsonData[i].DEPTH_NAME =='GROUP') {
                		if(type != 'STO01')
            			{
	                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
					                           .attr('id', jsonData[i].IDX)
					                           .attr('idx', jsonData[i].IDX)
					                           .attr('depth', jsonData[i].DEPTH)
					                           .attr('domainidx', domainValue)
					                           .attr('brandidx', brandValue)
					                           .attr('groupidx', groupValue)
					                           .attr('francidx', 0)
					                           .attr('deviceidx', 0)
					                           .attr('ischecked', jsonData[i].CHECKED)
					                           .text(jsonData[i].NAME)
					                           .appendTo(parent);
	 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                	else if (jsonData[i].DEPTH_NAME =='FRANCHISEE') {
                		if(type != 'STO01')
            			{
                			var classNm = 'franc';
                			if(treeId == '#treeList'){
                				classNm += ' iconChange';
                			}
	                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
					                           .attr('id', jsonData[i].IDX)
					                           .attr('idx', jsonData[i].IDX)
					                           .attr('depth', jsonData[i].DEPTH)
					                           .attr('domainidx', domainValue)
					                           .attr('brandidx', brandValue)
					                           .attr('groupidx', groupValue)
					                           .attr('francidx', francValue)
					                           .attr('deviceidx', 0)
					                           .attr('ischecked', jsonData[i].CHECKED) 
				 	                           .addClass(classNm)
					                           .text(jsonData[i].NAME)
					                           .appendTo(parent);
	 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                	else
                	{
                		if(type != 'STO01' && type != 'STO02')
            			{
                			var classNm = 'device';
                			if(treeId == '#targetTreeList'){
                				classNm += ' iconChange';
                			}
                			if(type == 'STO03') classNm += ' iconChange';
                			var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
	                           .attr('id', jsonData[i].IDX)
	                           .attr('idx', jsonData[i].IDX)
	                           .attr('depth', jsonData[i].DEPTH)
	                           .attr('domainidx', domainValue)
	                           .attr('brandidx', brandValue)
	                           .attr('groupidx', groupValue)
	                           .attr('francidx', francValue)
	                           .attr('deviceidx', deviceValue)
	                           .attr('ischecked', jsonData[i].CHECKED)
 	                           .addClass(classNm)
	                           .text(jsonData[i].NAME)
	                           .appendTo(parent);
							var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                }
            }
        }

    	if (num == 0) {
    		ul.find('li[id="' + jsonData[0].IDX + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    		if(!paramUrl)
    		{
	    		$('#domainIdx').val(jsonData[0].IDX);
	    		$('#brandIdx').val(0);
	    		$('#groupIdx').val(0);
	    		$('#francIdx').val(0);
    		}
    		eventHandle();
    		callbackFirstSelect(jsonData[0]);
		}
    	else {
    		ul.find('li[id="' + $("#auth").val() + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    	}

    	if(paramUrl)
    		kioskClickEvent();
    	else
    		treeClickEvent(type);
    }

    $('#' + result.target + '_anchor').click();

    var height = "580";
    //var height = $("#scrollbody").css('height');

    if(treeId == '#treeList') {
    	$(treeId).attr("style","overflow: auto; height: "+height+"px;	");
    	$('li.franc > a .jstree-themeicon').addClass('glyphicon glyphicon-leaf');
    	$('li.device > a .jstree-themeicon').addClass('glyphicon glyphicon-expand'); 
    }
    else if(treeId == '#targetTreeList'){
    	$('li.device > a .jstree-themeicon').addClass('glyphicon glyphicon-expand'); 
    	$('.glyphicon-expand').css('font-size','medium');
    }



}

/*
 * 기본 트리 이벤트
 */
function treeClickEvent(type) {
    $('#treeList').jstree({
        'core': {
            'themes': {
            	"name": "default",
                "dots": true,
                "icons": true
            }
        },
        'search': {
            'case_insensitive': true,
            'show_only_matches': true
        },
        'plugins': [
            'search', 'wholerow', /*"contextmenu",*/  "dnd"
        ]
    }).on('select_node.jstree', function (e, data) {
    	$.sessionCheck();
    	$('#domainIdx').val(data.node.li_attr.domainidx);
    	$('#brandIdx').val(data.node.li_attr.brandidx);
    	$('#groupIdx').val(data.node.li_attr.groupidx);
        $('#francIdx').val(data.node.li_attr.francidx);
        eventHandle();
        callbackSelectNodeJstree(data);
    }).bind("open_node.jstree", function (event, data) {
    	$('li.franc > a .jstree-themeicon').addClass('glyphicon glyphicon-leaf');
    
	}); 
    $('#treeSearch').keyup(function (e) {
    	callbackTreeSearchKeyEvt(e);
    });
}

/**
 * 트리 추가
 * @returns
 */
function treeAdd(menuId) {
	$.sessionCheck();
	if(menuId == "STO01")
		$.modalCommon.loadDataView('서비스 생성', '/BrandManagement/BasicAttrFormNullTemp');
	else if (menuId == "GROUP"){
		if($('#brandIdx').val() == 0){
			$.modalCommon.alertView('서비스를 선택해주세요.');
			return false;
		}
		$.modalCommon.loadDataView('그룹 생성', '/FranchiseeManagement/BasicAttrGroupFormNullTemp' , {brandIdx:$('#brandIdx').val()});
	}
	else {
		if($('#groupIdx').val() == 0){
			$.modalCommon.alertView('그룹을 선택해주세요.');
			return false;
		}
		$.modalCommon.loadDataView('사이트 생성', '/FranchiseeManagement/BasicAttrFormNullTemp' , {brandIdx:$('#brandIdx').val()});
	}
}

/**
 * 트리 수정
 * @returns
 */
function treeEdit(menuId) {
	$.sessionCheck();
	if($('#groupIdx').val() == 0){
		$.modalCommon.alertView('그룹을 선택해주세요.');
		return false;
	}
	$.modalCommon.loadDataView('그룹 수정', '/FranchiseeManagement/BasicAttrGroupFormNullTemp' , {domainIdx:$('#domainIdx').val(), brandIdx:$('#brandIdx').val(), groupIdx:$('#groupIdx').val()});

}

/**
 * 트리 삭제
 * @returns
 */
function treeDel(menuId) {
	// 서비스 관리자는 서비스 삭제 못하도록 처리
	if($('#pagesend #ladminType').val() == "ADM0002" || menuId == "GROUP"){
		if($('#brandIdx').val() == "0" && $('#francIdx').val() == "0"){
			$.modalCommon.alertView('도메인은 삭제할 수 없습니다.');
			return false;
		}else if($('#brandIdx').val() != "0" && $('#groupIdx').val() == "0" && $('#francIdx').val() == "0"){
			$.modalCommon.alertView('서비스는 삭제할 수 없습니다.');
			return false;
		}
	}
	if($('#brandIdx').val() != "0" && $('#groupIdx').val() != "0" && $('#francIdx').val() != "0" && menuId == "GROUP"){
		$.modalCommon.alertView('그룹을 선택해주세요.');
		return false;
	}else if($('#brandIdx').val() != "0" && $('#groupIdx').val() != "0" && $('#francIdx').val() == "0" && menuId != "GROUP"){
		$.modalCommon.alertView('사이트를 선택해주세요.');
		return false;
	}
	var delURL = '';
	var param = {};
	if($('#francIdx').val() != "0" && menuId != "GROUP"){
		param = { brandIdx: $('#brandIdx').val(), groupIdx: $('#groupIdx').val(), francIdx: $('#francIdx').val()};
		delURL = '/FranchiseeManagement/Delete/';
	}
	else if($('#groupIdx').val() != "0" && menuId == "GROUP")
	{
		var paramList = [];
		paramList.push($('#groupIdx').val());
		if($("#treeList").jstree().get_node('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()).children != null){
			var tagObj = $("#treeList").jstree().get_node('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()).children;
			var leng = tagObj.length;
			for(var i=0 ; i<leng ; i++){
				var groupIdx = tagObj[i].split('_')[2];
				paramList.push(groupIdx);
				getTreeChildren(tagObj[i], paramList);
			}
		}
		param = { brandIdx: $('#brandIdx').val(), strArr: paramList};
		delURL = '/FranchiseeManagement/DeleteGroup/';
	}
	else
	{
		param = { brandIdx: $('#brandIdx').val()};
		delURL = '/BrandManagement/Delete/';
	}

	if($('#francIdx').val() != "0" || $('#groupIdx').val() != "0" || $('#brandIdx').val() != "0")
	{
		BootstrapDialog.confirm({
            title: '',
            message: '삭제하시겠습니까?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            btnCancelLabel: '취소',
            btnOKLabel: '삭제',
            btnOKClass: 'btn-warning',
            callback: function (result) {
                if (result) {
                    var dResult = $.ajaxUtil.ajaxArray(delURL, param);

                    if (dResult.resultCode == 0){
                    	if($('#francIdx').val() != "0"){
                    		commMessage('D','사이트');
                    		treeSearchList(1,false,null,menuId);
                    		$('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()+'_anchor').click();
                    	}
                    	else if($('#groupIdx').val() != "0"){
                    		commMessage('D','그룹');
                    		treeSearchList(1,false,null,menuId);
                    		$('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_anchor').click();
                    	}
                    	else{
                    		commMessage('D','서비스');
                    		treeSearchList(1,false,null,menuId);
                    		$('#'+$('#domainIdx').val()+'_anchor').click();
                    	}



                    }
                }
            }
        });
	}
	else
		$.modalCommon.alertView('서비스, 그룹, 사이트중 하나를 선택해 주세요.');
}
function getTreeChildren(obj, paramList){
	var tagObj2 = $("#treeList").jstree().get_node('#'+obj).children;
	var leng2 = tagObj2.length;
	for(var y=0 ; y<leng2 ; y++){
		var groupIdx = tagObj2[y].split('_')[2];
		paramList.push(groupIdx);
		getTreeChildren(tagObj2[y], paramList);
	}
}

/**
 * 단말기 전용 트리 이벤트
 * @returns
 */
function kioskClickEvent() {
	$('#targetTreeList').jstree({
		"checkbox": {
			'keep_selected_style': false,
            'three_state': true
        },
		'core': {
        	'check_callback': true,
            'themes': {
            	"name": "default",
                "dots": true,
                "icons": true

            }
        },
       'search': {
            'case_insensitive': true,
            'show_only_matches': true
        },
        'plugins': [
            'search', 'wholerow', 'checkbox'
        ]
    }).bind("open_node.jstree", function (event, data) {
    	$('li.device > a .jstree-themeicon').addClass('glyphicon glyphicon-expand');
    	$('.glyphicon-expand').css('font-size','medium');
    
	}); ;
	//$('#targetTreeList').jstree('hide_icons'); 
	$("#targetTreeList").jstree(true).uncheck_all();
	$('li[ischecked="1"]').each(function () {
		$('#targetTreeList').jstree(true).check_node('li[id="' + $(this).attr('id') + '"]');
    });
}

/**
 * 콤마 제거
 * @param str
 * @returns
 */
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');

}

/**
 * 버전 관리 실행
 * @param str
 * @returns
 */
function autoHypenVersion(str){
	str = str.replace('.', '');
	if(str.length > 4)
		str = str.substr(0, 4);
	return str;
}

/**
 * 버전 관리 실행 결과
 * @param str
 * @returns
 */
function autoHypenVersionRet(str){
	str = str.replace(/[^0-9]/g, '');
	if( str.length < 2){
		return str;
	}else if(str.length < 3){
		return str.substr(0, 1) + '.' + str.substr(1);
	}else if(str.length < 4){
		return str.substr(0, 1) + '.' + str.substr(1, 1) + '.' + str.substr(2);
	}else{
		return str.substr(0, 1) + '.' + str.substr(1, 1) + '.' + str.substr(2, 1) + '.' + str.substr(3);
	}

	return str;
}

/**
 * 휴대폰 번호
 * @param str
 * @returns
 */
function autoHypenPhone(str){
	str = str.replace(/[^0-9]/g, '');
	if( str.length < 4){
		return str;
	}else if(str.length < 7){
		return str.substr(0, 3) + '-' + str.substr(3);
	}else if(str.length < 11){
		return str.substr(0, 3) + '-' + str.substr(3, 3) + '-' + str.substr(6);
	}else{
		return str.substr(0, 3) + '-' + str.substr(3, 4) + '-' + str.substr(7);
	}
	return str;
}

/**
 * 숫자만
 * @param str
 * @returns
 */
function autoNumber(str){
	str = str.replace(/[^0-9]/g, '');
	return str;
}

/**
 * 콤마와 숫자
 * @param str
 * @returns
 */
function autoComma(str) {
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n = String(str);    //숫자 -> 문자변환
	while(reg.test(n)){
		n = n.replace(reg, "$1" + "," + "$2");
	}
	return n;
}

/**
 * 클래스 이벤트 실행
 * @returns
 */
function eventHandle(){
	$(".clsNotKor").keyup(function(e) {
		if (!(e.keyCode >=37 && e.keyCode<=40)) {
			var v = $(this).val();
			$(this).val(v.replace(/[^a-z0-9]/gi,''));
		}
	});

	//버전 관리
	$(".autoHypenVersion").off("keyup").on("keyup", function(event){
		event = event || window.event;
		var _val = this.value.trim();
		this.value = autoHypenVersion(_val) ;
	});

	//휴대폰번호
	$(".autoHypenPhone").off("keyup").on("keyup", function(event){
		event = event || window.event;
		var _val = this.value.trim();
		this.value = autoHypenPhone(_val) ;
	});

	//숫자 콤마만
	/*$(".autoComma").off("keyup").on("keyup", function(event){
		event = event || window.event;
		var _val = this.value.trim();
		_val = _val.replace(/,/g, "");
		_val = _val.replace(/[^0-9]/gi, "");
		this.value = autoComma(_val) ;
	});*/

	$(".autoNumber").off("keyup").on("keyup", function(event){
		this.value = this.value.replace(/[^0-9]/g, '');
	});

	$('.autoUpperNumber').off('keyup').on('keyup', function(event){
		event = event || window.event;
		var _val = this.value.trim();
		if(_val) {
			this.value = _val.toUpperCase();
			this.value = this.value.replace(/[^A-Z0-9]/g, '');
		}
	});

	//숫자 콤마만
	$(".autoComma").off("keyup").on("keyup", function(event){
		if(this.value != "")
			this.value = $.number(this.value);
	});

	//if($("#parentMenuId").val() != "SET00" && $("#parentMenuId").val() != "STO00" && $("#parentMenuId").val() != "BMT00" && $("#parentMenuId").val() != "CON00" && $('#brandIdx').val() == 0 && $("#mobileYn").val() != "Y"){
	if($("#parentMenuId").val() != "SET00" && $("#parentMenuId").val() != "STO00" && ($('#brandIdx').val() == 0) && $("#mobileYn").val() != "Y" && ($('#contDomainIdx').val() == 0 || $('#contDomainIdx').val() == null)){
	//if($("#parentMenuId").val() != "SET00" && $("#parentMenuId").val() != "STO00" && ($('#brandIdx').val() == 0 || $('#francIdx').val() == 0) && $("#mobileYn").val() != "Y"){
		$('button').not(".notDisabled").attr('disabled', true);
		$("input[type!='search']").not(".notDisabled").attr('disabled', true);
	}else{
		$('button').not(".notDisabled").attr('disabled', false);
		$("input[type!='search']").not(".notDisabled").attr('disabled', false);
	}

	// 매출 보고서는 모든 버튼 활성화
	if(($("#menuId").val() == "SAL03" && $('#brandIdx').val() != 0) && $("#mobileYn").val() != "Y"){
		$('button').attr('disabled', false);
		$("input[type!='search']").attr('disabled', false);
	}
}

function activeDisabled(){
//	console.log("현재 메뉴명, 브랜드, 가맹점:"+$("#menuId").val()+", "+$('#brandIdx').val()+", "+$('#francIdx').val());
	//브랜드 관리
//	if($("#menuId").val() == "STO01" && $('#brandIdx').val() == 0){
//		$('a.btn').attr('disabled', true).attr('onclick', '').unbind('click');
//	}

//	$("#menuId").val() == "STO02" ||
	if(($("#menuId").val() == "ORD01" || $("#menuId").val() == "SHO02") && ($('#brandIdx').val() == 0 || $('#francIdx').val() == 0) && $("#mobileYn").val() != "Y"){
		$('a.btn').attr('disabled', true).attr('onclick', '').unbind('click');
	}
}

/**
 * 공동 문구
 * @param type
 * @param message
 * @returns
 */
function commMessage(type,message){
	if(type == "C")
		$.modalCommon.alertView(message + '을(를) 저장하였습니다.');
	else if(type == "U")
		$.modalCommon.alertView(message + '을(를) 저장하였습니다.');
	else if(type == "D")
		$.modalCommon.alertView(message + '을(를) 삭제하였습니다.');
	else if(type == "S")
		$.modalCommon.alertView(message + '을(를) 저장하였습니다.');
}

/**
 * 한국어 제외
 * @param obj
 * @returns
 */
function koreanChange(obj){
	obj.value = obj.value.replace(/[\ㄱ-하-ㅣ가-힝]/g,'');
}

/**
 * 숫자와 소수점
 * @param evt
 * @returns
 */
function isNumberKey(evt) {
	// Textbox value
	var value = event.srcElement.value;
	var valueIn = value + evt.key;
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if ((charCode != 46 ) && (48 > charCode || charCode > 57)){
		return false;
	}

	// 소수점(.)이 두번 이상 나오지 못하게
	var pattern0 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
	if (pattern0.test(value)) {
		if (charCode == 46) {
			return false;
		}
	}

	// 정수부분 세자리 입력
	var pattern1 = /^\d{3}$/; // 현재 value값이 3자리 숫자이면 . 만 입력가능
	if (pattern1.test(value)) {
		if (charCode != 46) {
			//alert("정수 부분 두자리 숫자만 입력가능합니다");
			return false;
		}
	}else {
		if(valueIn > 100){
			$('#discountPercent').val(100);
			return false;
		}
	}

	// 소수점 둘째자리까지만 입력가능
	var pattern2 = /^\d*[.]\d{2}$/; // 현재 value값이 소수점 둘째짜리 숫자이면 더이상 입력 불가
	if (pattern2.test(value)) {
		if(valueIn <= 100){
			return false;
		}
	}

	//소수점 부터 입력하였을시 0입력
	if(valueIn.indexOf('.') == 0 ){
		$('#discountPercent').val(0)
	}
	return true;
}

/**
 * datatable 특수문자 변경
 * @param meta
 * @returns
 */
function fnDataTableRenderText(data)
{
	return typeof data === 'string' ?
			data.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;') :
			data;
}

/**
 * datatable row idx
 * @param meta
 * @returns
 */
function dataTableRowIdx(meta)
{
	var viewLength = meta.settings._iDisplayLength;
	var totalLength = meta.settings._iRecordsDisplay;
	var currLength = meta.settings._iDisplayStart;
	var rowIdx = totalLength - ((currLength/viewLength)*viewLength+meta.row);
    return rowIdx;
}

function fnErrorMessage(json)
{
	var errorMassage = '네트워크가 끊겼습니다. 확인해주시기 바랍니다.';
    if(json) {
    	if(json.messages) {
    		// 에러 메시지 자르기
    		var messageDetail = json.messages.detail;
    		if(messageDetail && messageDetail.length > 40){
    			messageDetail = messageDetail.substring(0,40)+'...';
    		}
    		errorMassage = json.messages.title + '['+messageDetail+']';
		}
    }
    if($('.wrap-loading').is(':visible')){
    	$('.wrap-loading').hide();
    }

    $.modalCommon.alertView(errorMassage);
}

/**
 * 최대시간 제약(name을 넘겨서 사용한다)
 * @param id, name등을 넘긴다.
 * @returns
 */
function timeMaxData(timeAttr){
	var timeSplit = $(timeAttr).val().split(":");
	if(timeSplit.length >= 3){
		if(Number(timeSplit[0]) > 23)
			timeSplit[0] = "23";
		if(Number(timeSplit[1]) > 59)
			timeSplit[1] = "59";
		if(Number(timeSplit[2]) > 59)
			timeSplit[2] = "59";
		$(timeAttr).val(timeSplit[0]+":"+timeSplit[1]+":"+timeSplit[2]);
	}
}


/**
 * 
 * TShop 추가
 */

/**
 * 컨텐츠 그룹 트리 조회
 * @param num
 * @param paramUrl
 * @param param
 * @param type
 * @returns
 */
function contentTreeSearchList(num, paramUrl, param, type) {
	$.sessionCheck();
	if (num > 0)
		$('#treeList').jstree('destroy');

	var treeId = "";

	var url = "";

	param = {
		serchKey : $('#treeSearch').val()
	}
	url = '/Contents/SearchTree';
	treeId = '#treeList';
	if(type=="FORM"){
		treeId = '#formtreeList';
	}


	var result = $.ajaxUtil.ajaxDefault(url, param).data;
	var jsonData = result.data;

	$(treeId)[0].innerHTML = '';
	var ul = $(treeId);
    var htmlStr = '';
    var domainValue = "";

    if (jsonData.length > 0) {
    	for (var i = 0; i < jsonData.length; i++) {
    		var groupValue = "";

    		if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 1)
    			domainValue = jsonData[i].IDX.split('_')[0];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 2)
				groupValue = jsonData[i].IDX.split('_')[1];

        	if (i == 0) {
                var ulp = $('<ul>').appendTo(ul);
                var li = $('<li>').attr('id', jsonData[i].IDX)
                                     .attr('depth', jsonData[i].DEPTH)
                                     .attr('data-jstree', '{\"opened\":true}')
                                     .attr('domainidx',domainValue)
	                                 .attr('groupidx', 0)
                                     .text(jsonData[i].NAME)
                                     .appendTo(ulp);
                $('<ul>').attr('id', jsonData[i].IDX).appendTo(li);
            }
            else {
                var parent = ul.find('ul[id="' + jsonData[i].PARENT_IDX + '"]');

                if (parent != null) {
                	if (jsonData[i].DEPTH_NAME =='GROUP') {

                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
				                           .attr('id', jsonData[i].IDX)
				                           .attr('idx', jsonData[i].IDX)
				                           .attr('depth', jsonData[i].DEPTH)
				                           .attr('domainidx', domainValue)
				                           .attr('groupidx', groupValue)
				                           .text(jsonData[i].NAME)
				                           .appendTo(parent);
 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
					}
                }
            }
        }

    	if (num == 0) {
    		ul.find('li[id="' + jsonData[0].IDX + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    		if(!paramUrl)
    		{
	    		$('#contDomainIdx').val(jsonData[0].IDX);
	    		$('#contGroupIdx').val(0);
    		}
    		eventHandle();
    		callbackcontentFirstSelect(jsonData[0]); 
		}
    	else {
    		ul.find('li[id="' + $("#auth").val() + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    	}
    	contentTreeClickEvent(type);
    }

    var height = "580";
    //var height = $("#scrollbody").css('height');

    if(treeId == '#treeList')
    	$(treeId).attr("style","overflow: auto; height: "+height+"px;	");
}


/*
 * 기본 트리 이벤트
 */
function contentTreeClickEvent(type) {
	var id='#treeList';
	if(type=="FORM"){
		id='#formtreeList';
	}
    $(id).jstree({
        'core': {
            'themes': {
            	"name": "default",
                "dots": true,
                "icons": true
            }
        },
        'search': {
            'case_insensitive': true,
            'show_only_matches': true
        },
        'plugins': [
            'search', 'wholerow', /*"contextmenu",*/  "dnd"
        ]
    }).on('select_node.jstree', function (e, data) {
    	$.sessionCheck();
    	$('#contDomainIdx').val(data.node.li_attr.domainidx);
    	$('#contGroupIdx').val(data.node.li_attr.groupidx);
        eventHandle();
        callbackContentSelectNodeJstree(data);
    }); 
}

/**
 * 트리 조회
 * @param num
 * @param paramUrl
 * @param param
 * @param type
 * @returns
 */
function findTreeSearchList(num, paramUrl, param, type) {  
	$.sessionCheck();
	if (num > 0)
		$('#findTreeList').jstree('destroy');

	var treeId = "";

	var url = "";
	param = {
		}
		url = '/Common/SearchTree';
		treeId = '#findTreeList';
		
	var result = $.ajaxUtil.ajaxDefault(url, param).data;
	var jsonData = result.data;

	$(treeId)[0].innerHTML = '';
	var ul = $(treeId);
    var htmlStr = '';
    var domainValue = "";

    if (jsonData.length > 0) {
    	for (var i = 0; i < jsonData.length; i++) {
    		var brandValue = "";
    		var groupValue = "";
    		var francValue = "";
    		var deviceValue = "";

    		if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 1)
    			domainValue = jsonData[i].IDX.split('_')[0];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 2)
				brandValue = jsonData[i].IDX.split('_')[1];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 3)
				groupValue = jsonData[i].IDX.split('_')[2];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 4)
				francValue = jsonData[i].IDX.split('_')[3];

			if(jsonData[i].IDX != null && jsonData[i].IDX.split('_').length >= 5)
				deviceValue = jsonData[i].IDX.split('_')[4];

        	if (i == 0) {
                var ulp = $('<ul>').appendTo(ul);
                var li = $('<li>').attr('id', jsonData[i].IDX)
                                     .attr('depth', jsonData[i].DEPTH)
                                     .attr('data-jstree', '{\"opened\":true}')
                                     .attr('domainidx',domainValue)
                                     .attr('brandidx',0)
	                                 .attr('groupidx', 0)
	                                 .attr('francidx', 0)
	                                 .attr('deviceidx', 0)
	  	                           .attr('ischecked', jsonData[i].CHECKED)
                                     .text(jsonData[i].NAME)
                                     .appendTo(ulp); 
                $('<ul>').attr('id', jsonData[i].IDX).appendTo(li);
            }
            else {
                var parent = ul.find('ul[id="' + jsonData[i].PARENT_IDX + '"]');

                if (parent != null) {
                	if (jsonData[i].DEPTH_NAME =='BRAND') {

                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
				                           .attr('id', jsonData[i].IDX)
				                           .attr('idx', jsonData[i].IDX)
				                           .attr('depth', jsonData[i].DEPTH)
				                           .attr('domainidx', domainValue)
				                           .attr('brandidx', brandValue)
				                           .attr('groupidx', 0)
				                           .attr('francidx', 0)
				                           .attr('deviceidx', 0)
				                           .attr('ischecked', jsonData[i].CHECKED)
				                           .text(jsonData[i].NAME)
				                           .appendTo(parent);
 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
					}
                	else if (jsonData[i].DEPTH_NAME =='GROUP') {
                		if(type != 'STO01')
            			{
	                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
					                           .attr('id', jsonData[i].IDX)
					                           .attr('idx', jsonData[i].IDX)
					                           .attr('depth', jsonData[i].DEPTH)
					                           .attr('domainidx', domainValue)
					                           .attr('brandidx', brandValue)
					                           .attr('groupidx', groupValue)
					                           .attr('francidx', 0)
					                           .attr('deviceidx', 0)
					                           .attr('ischecked', jsonData[i].CHECKED)
					                           .text(jsonData[i].NAME)
					                           .appendTo(parent);
	 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                	else if (jsonData[i].DEPTH_NAME =='FRANCHISEE') {
                		if(type != 'STO01')
            			{
                			var classNm = 'franc';
                			if(treeId == '#findTreeList'){
                				classNm += ' iconChange';
                			}
	                		var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
					                           .attr('id', jsonData[i].IDX)
					                           .attr('idx', jsonData[i].IDX)
					                           .attr('depth', jsonData[i].DEPTH)
					                           .attr('domainidx', domainValue)
					                           .attr('brandidx', brandValue)
					                           .attr('groupidx', groupValue)
					                           .attr('francidx', francValue)
					                           .attr('deviceidx', 0)
					                           .attr('ischecked', jsonData[i].CHECKED) 
				 	                           .addClass(classNm)
					                           .text(jsonData[i].NAME)
					                           .appendTo(parent);
	 						var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                	else
                	{
                		if(type != 'STO01')
            			{
                			var classNm = 'device';
                			if(treeId == '#targetTreeList'){
                				classNm += ' iconChange';
                			} 
                			var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
	                           .attr('id', jsonData[i].IDX)
	                           .attr('idx', jsonData[i].IDX)
	                           .attr('depth', jsonData[i].DEPTH)
	                           .attr('domainidx', domainValue)
	                           .attr('brandidx', brandValue)
	                           .attr('groupidx', groupValue)
	                           .attr('francidx', francValue)
	                           .attr('deviceidx', deviceValue)
	                           .attr('ischecked', jsonData[i].CHECKED)
 	                           .addClass(classNm)
	                           .text(jsonData[i].NAME)
	                           .appendTo(parent);
							var ul2 = $('<ul>').attr('id', jsonData[i].IDX).appendTo(li2);
            			}
					}
                }
            }
        }

    	if (num == 0) {
    		ul.find('li[id="' + jsonData[0].IDX + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    		eventHandle();
		}
    	else {
    		ul.find('li[id="' + $("#auth").val() + '"]').attr('data-jstree', '{\"selected\":true,\"opened\":true}');
    	}

		findTreeClickEvent(type);
    }

    $('#' + result.target + '_anchor').click();
	$('li.franc > a .jstree-themeicon').addClass('glyphicon glyphicon-leaf');
    

}

/*
 * 기본 트리 이벤트
 */
function findTreeClickEvent(type) {
    $('#findTreeList').jstree({
        'core': {
            'themes': {
            	"name": "default",
                "dots": true,
                "icons": true
            }
        },
        'search': {
            'case_insensitive': true,
            'show_only_matches': true
        },
        'plugins': [
            'search', 'wholerow', /*"contextmenu",*/  "dnd"
        ]
    }).on('select_node.jstree', function (e, data) {
    	$.sessionCheck();
        eventHandle();
    }).bind("open_node.jstree", function (event, data) {
    	$('li.franc > a .jstree-themeicon').addClass('glyphicon glyphicon-leaf');
    
	}); 
}