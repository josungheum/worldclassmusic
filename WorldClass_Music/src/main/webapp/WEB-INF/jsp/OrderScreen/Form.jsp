<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="orderScreenVo" name="orderScreenForm" id="orderScreenForm" method="post" onsubmit="return false;">
<form:hidden path="orderScreenIdx"/>
<form:hidden path="brandIdx"/>
<form:hidden path="francIdx"/>
<style>
.resource-list > li > div {
    width: 300px;
    height: 200px;
    padding: 15px 25px;
    margin-top: -15px;
    cursor: pointer;
}

.resource-list > li {
	float: left;
	width: 170px;
	height: 200px;
	margin-top: 15px;
	border-right: 1px solid #f9fcff;
}

.resource-list > li:last-child {
	width: 170px;
}

.resource-list > li:last-child > div {
	width: 300px;
}

.resource-list {
	width: 92%;
	height: 200px;
	border: 0px solid #bcc1c7;
	border-radius: 0px;
}

.modal-dialog {
}

.list-group-item2 {
  position: relative;
  display: block;
  padding: 0px 0px;
  margin-bottom: -1px;
  background-color: @list-group-bg;
}


.lEditorLayerlistItemDel {
	position: relative;
	border: none;
	color: #ffffff;
	left: 160px;
	top: 0px;
	margin-left: -120px;
	text-align: center;
	background: #ffffff;
	border-radius: 50%;
	padding: -3px 0px 0px 0px;
	font-size: 10px;
	font-weight: normal;
	background-image:url(/Content/images/details_close.png);
}

.resource-list > li {
    float: left;
    width: 300px;
    height: 400px;
    margin-top: 15px;
    border-right: 1px solid #f9fcff;
}

/* .col-md-2, .col-sm-2, .col-xs-2, .col-lg-2, .col-md-10, .col-sm-10, .col-xs-10, .col-lg-10 { */
/* 	padding-right: 0px; */
/* 	padding-left: 0px; */
/* } */

</style>
<div class="modal-body" style="height:580px;">
	<div class="col-md-10">
		<div id="modalId">
			<label>주문화면명</label> &nbsp;&nbsp;
			<form:input path="orderScreenName" class="form-control" style="ime-mode:active; width: 50%; display: inline;" maxlength="50" placeholder="주문화면명"/>
		</div><br/>
		<div>
			<div style="border: 1px solid #EAEAEA; width: 100%; height: 520px; overflow:auto">
				<div class="col-md-3 col-sm-3 col-xs-3 col-lg-3" style="border: 1px solid #EAEAEA;">
					<label>주문 메뉴 구성</label>
					<div class="btn-group pull-right" style="margin: 4%;">
					  <button type="button" class="btn btn-default editbtn" onclick="NameEdit()">수정</button>
		              <button type="button" class="btn btn-default editbtn" onclick="menuAdd()"><span class="glyphicon glyphicon-plus"></span></button>
		              <button type="button" class="btn btn-default editbtn" onclick="menuDelete()"><span class="glyphicon glyphicon-minus"></span></button>
		              <button type="button" class="btn btn-default editbtn" onclick="MoveNode(-1)"><span class="glyphicon glyphicon-arrow-up"></span></button>
		              <button type="button" class="btn btn-default editbtn" onclick="MoveNode(1)"><span class="glyphicon glyphicon-arrow-down"></span></button>
		            </div>
		            <div style="margin-top:4px; overflow-y: auto; height: 380px; width: 100%;" id="menuTree">
		            </div>
				</div>

				<div class="col-md-9 col-sm-9 col-xs-9 col-lg-9">
					<label>주문 메뉴 별 상품 구성</label>
					<div class="nav-tabs" id="tabLab" style="">
						<ul class="nav nav-pills" id="tabList">

						</ul>
					</div>
					<div id="prodTable">

	            	</div>

				</div>
			</div>
		</div><br/>
	</div>
	<div class="col-md-2">
		<div>
			<label>적용 단말 목록</label>
			<div id="targetTreeList" style="border: 1px solid #EAEAEA; width: 100%; height: 500px; overflow-y:scroll;" class="proton-demo"></div>
		</div>
	</div>
</div><br/>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="addBtn()" id="addbtn">주문 메뉴 별 상품 추가</button>
	<button type="button" class="btn btn-primary" onclick="orderScreenSave()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
</div>
<input type="hidden" id="parentIdx">
</form:form>
<script type="text/javaScript">
	var globalNextVal = 0;
	$(document).ready(function () {
		$('.modal-dialog').css('width', '1700px').css('height', '1200px');


		var param = {
				  brandIdx : $('#orderScreenForm #brandIdx').val()
				, francIdx : $('#orderScreenForm #francIdx').val()
				, orderScreenIdx : $('#orderScreenIdx').val()}

		treeSearchList(0,'<c:url value="/OrderScreen/SearchKioskTree"/>',param);

		productMenuTreeList($('#orderScreenForm #brandIdx').val(), $('#orderScreenForm #francIdx').val(), $("#orderScreenIdx").val());

		tabTreeList();



	});

	function tabTreeList(id){
		var menuTreeArr = $('#menuTree').jstree().get_json($('#menuTree'))[0].children;

		var tabTreeHtml = "";
		for(var i = 0; i < menuTreeArr.length; i++)
		{
			var menuName = menuTreeArr[i].text;
			if(id == menuTreeArr[i].id){
				tabTreeHtml += '<li class="active" id="'+menuTreeArr[i].id+'">';
			}else{
				tabTreeHtml += '<li id="'+menuTreeArr[i].id+'">';
			}

			var title = menuTreeArr[i].text;
			if(title.length > 7){
				title = title.substring(0,7) + "...";
			}

			tabTreeHtml += '	<a onclick="tabView('+menuTreeArr[i].id+')" data-toggle="tab" style="cursor:pointer;">'+fnDataTableRenderText(title)+'</a>';
			tabTreeHtml += '</li>';


		}
		$("#tabList").html(tabTreeHtml);
	}

	function tabView(id){
		$('#'+id+'_anchor').click();
	}

	//주문화면 저장
	function orderScreenSave()
	{
		if (!$.formCheck('#orderScreenForm #orderScreenName', 'Y', 50, 'N', '주문화면명')) return;

		var targetTreeArr = [];

		$.each($('#targetTreeList').jstree('get_selected', true), function ()
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				targetTreeArr.push(this.li_attr.deviceidx);
	    });

		if(targetTreeArr.length == 0){
			$.modalCommon.alertView('단말을 하나라도 선택해주세요.');
			return false;
		}

		var menuJson = [];

		var menuTreeArr = $('#menuTree').jstree().get_json($('#menuTree'))[0].children;


		//메뉴 목록
		var prodList = [];
		var menuLengthCheck = true;
		for(var i = 0; i < menuTreeArr.length; i++)
		{
			var menuName = menuTreeArr[i].text.replace("&amp;","&").replace("&lt;","<").replace("&gt;",">");
			
			 if (50 < parseInt(menuName.length)) {
            	$.modalCommon.alertView('메뉴 명('+menuName+')은(는) 50자 초과\n입력할 수 없습니다.');
            	menuLengthCheck = false;
             }
			
			
			var id = menuTreeArr[i].id;
			var orderScreenMenuIdx = (i+1);
			menuJson.push(
				{
					orderSeq: id,
					menuName: menuName,
					orderScreenMenuIdx: orderScreenMenuIdx
				}
			);
			
			if(!menuLengthCheck){
				return false;
			}

			$("#sortable"+id).find("li.ui-sortable-handle").each(function(index) {
				var prodItem = {};
				prodItem.domainIdx = $("#domainIdx").val();
				prodItem.brandIdx = $("#brandIdx").val();
				prodItem.francIdx = $("#francIdx").val();
				prodItem.orderScreenIdx = $("#orderScreenIdx").val() == "" ? "0":$("#orderScreenIdx").val();
				prodItem.orderProdIdx = $(this).attr("name");
				prodItem.stateCode = $(this).find("select").val();
				prodItem.orderScreenMenuIdx = orderScreenMenuIdx;
				prodItem.orderSeq = orderScreenMenuIdx;
				//빈값이 아닐 경우만
				if($(this).attr("name") != null){
					prodList.push(prodItem);
				}
			});

		}


		var param = {
				 checkboxArr: targetTreeArr
				,menuJson: JSON.stringify(menuJson)
				,prodList: JSON.stringify(prodList)
				,brandIdx:$("#brandIdx").val()
				,francIdx:$("#francIdx").val()
				,orderScreenIdx : $("#orderScreenForm #orderScreenIdx").val()
				,orderScreenName : $("#orderScreenForm #orderScreenName").val()
	    }

		var dResult = $.ajaxUtil.ajaxArray('<c:url value="/OrderScreen/Create"/>', param);

		if (dResult.resultCode == 0){
			$.modalCommon.close();
			commMessage($("#orderScreenForm #orderScreenIdx").val() == '' ? 'C':'U','주문화면');
			orderScreenList();
		 }
	}

	//메뉴 트리 조회
	function productMenuTreeList(brandIdx, francIdx, orderScreenIdx) {
		var param = {brandIdx:brandIdx, francIdx:francIdx, orderScreenIdx: orderScreenIdx}
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/OrderScreen/ProductMenuTree"/>', param).data;
	    var ul = $('#menuTree');
	    var htmlStr = '';

         var ulp = $('<ul>').appendTo(ul);
         var li = $('<li>').attr('id', 'menu0')
                              .attr('idx', 0)
                              .attr('menulevel', 0)
                              .attr('orderSeq', 0)
                              .attr('menuName', '')
							  .attr('menuEngName', '')
                              .attr('data-jstree', '{\"opened\":true}')
                              .attr('aria-selected', 'true')
                              .text('메뉴')
                              .appendTo(ulp);
         $('<ul>').attr('id', 'menu0').attr('idx', 0).appendTo(li);

         if (jsonData.length > 0) {
 	        for (var i = 0; i < jsonData.length; i++) {
		         var parent = ul.find('ul[idx="'+jsonData[i].parentIdx+'"]');

		         var li2 = $('<li>').attr('data-jstree', '{ \"opened\" : true }')
							         .attr('id', jsonData[i].orderScreenMenuIdx)
							         .attr('idx', jsonData[i].orderScreenMenuIdx)
							         .attr('menulevel', jsonData[i].menuLevel)
							         .attr('orderSeq', jsonData[i].orderSeq)
							         .attr('menuName', jsonData[i].menuName)
							         .attr('menuEngName', jsonData[i].menuEngName)
							         .text(jsonData[i].menuName)
							         .appendTo(parent);
				 var ul2 = $('<ul>').attr('id', jsonData[i].orderScreenMenuIdx).attr('idx', jsonData[i].orderScreenMenuIdx).appendTo(li2);

				 if(jsonData[i].menuLevel == 1){
					 globalNextVal += 1;
					 ProductList(jsonData[i].orderScreenMenuIdx);

				 }
 	        }
         }
         menuTreeClickEvent();
	}

	//메뉴 트리 이벤트
	function menuTreeClickEvent() {
		$('#menuTree').jstree(
			{
	        'core': {
	            'themes': {
	            	"name": "default",
	                "dots": true,
	                "icons": true
	            },
	            'force_text' : true,
	            'check_callback' : true
	        },
	        'search': {
	            'case_insensitive': true,
	            'show_only_matches': true
	        },
	        'plugins': [
	            'search', 'wholerow'
	        ],
	        callback: {
	        	oncreate: function (NODE, REF_NODE, TYPE, TREE_OBJ, RB) {
	            	alert('oncreate');
	            },
	            onrename: function (NODE, LANG, TREE_OBJ, RB) {
	            	alert('onrename');
	            },
	            ondelete: function (NODE, TREE_OBJ, RB) {
	            	alert('ondelete');
	            }
	       }
	    }).on('select_node.jstree', function (e, data) {
	    	$("#parentIdx").val(data.node.li_attr.id);
	    	ProductListShow();
	    }).on('rename_node.jstree', function (e, data) {
	    	var snodes = $('#menuTree').jstree('get_selected');
	    	tabTreeList(snodes[0]);
	    });
		$('#menuTree').jstree("select_node", "#menu0");
	}

	//메뉴 이름 변경
	function NameEdit() {
		if($('#parentIdx').val() == 'menu0'){
			$.modalCommon.alertView('메뉴는 변경 불가능합니다.');
			return false;
		}
		var ref = $('#menuTree').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }

		sel = sel[0];
		ref.edit(sel);
	}

	//메뉴 추가
	function menuAdd(){
		if($('#parentIdx').val() != 'menu0'){
			$.modalCommon.alertView('메뉴에서만 추가 가능합니다.');
			return false;
		}

		var ref = $('#menuTree').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		globalNextVal += 1;

		sel = ref.create_node(sel, {
		      "id": globalNextVal,
		      "text": "메뉴" + globalNextVal
		    });
		if(sel) {
			ref.edit(sel);
		}
		addPdTable(globalNextVal);
	}

	//메뉴 삭제
	function menuDelete(){
		if($('#parentIdx').val() == 'menu0'){
			$.modalCommon.alertView('메뉴는 삭제 불가능합니다.');
			return false;
		}

		var ref = $('#menuTree').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		ref.delete_node(sel);
		tabTreeList();
		$('#menu0_anchor').click();

	}

	//메뉴 이동
	function MoveNode(moveValue) {

		var snodes = $('#menuTree').jstree('get_selected');

		if (snodes.length == 1){
			var tree = $("#menuTree").jstree(true);
			var selNode = tree.get_node(snodes[0]);
			var pnode = tree.get_node(selNode.parent);
			var pos = $.inArray(selNode.id, pnode.children);

			pos = pos + moveValue;

			if (moveValue > 0) {pos++;}

			if (pos > -1 && pos < pnode.children.length + 1){
			 	tree.move_node(selNode, pnode, pos);
			 }
			tabTreeList(snodes[0]);
		}
	}

	//상품 보여주기
	function ProductListShow(){
		$('.resource-list').css('display', 'none');
		$("#tabList > li").removeClass("active");
		if($('#parentIdx').val() != 'menu0'){
			$('#sortable'+ $("#parentIdx").val()).css('display', 'inline');
			$("#tabList > li#"+$("#parentIdx").val()).addClass("active");
		}

	}

	//상품 추가
	function addBtn(id){
		var snodes = $('#menuTree').jstree('get_selected');
		if(snodes == null || snodes[0] == "menu0"){
			$.modalCommon.alertView('메뉴를 선택해주세요.');
		}else{
			$.sessionCheck();
	    	$.modalCommon.loadView('상품 추가', '<c:url value="/OrderScreen/ProductForm"/>'	);
		}

	}

	//상품 조회
	function ProductList(orderScreenMenuIdx) {
		var parent = $('#prodTable');

		var param =
			{   brandIdx : $('#orderScreenForm #brandIdx').val()
			  , francIdx : $('#orderScreenForm #francIdx').val()
			  , orderScreenIdx : $('#orderScreenIdx').val()
			  , orderScreenMenuIdx: orderScreenMenuIdx
			}
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/OrderScreen/ProductList"/>', param).data;

		imageDrawing(orderScreenMenuIdx, jsonData);
	}

	function imageDrawing(orderScreenMenuIdx, jsonData){
		var innerHtml = "";
		innerHtml += '<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">';
		innerHtml += '<ul class="resource-list" id="sortable'+ orderScreenMenuIdx +'">';
		for(var i = 0; i < jsonData.length; i++){
			innerHtml += '	<li name=\"'+jsonData[i].orderProdIdx+'\">';
			innerHtml += '		<div class="card" style="">';
			innerHtml += '			<div class="card-header">';
			innerHtml += '				<select class="form-control" style="ime-mode:active; width: 180px; display: inline; background-color: white;margin-bottom: 10px;" id="prodStateCode\"'+jsonData[i].orderProdIdx+'\"">';
			if('${prodStateCode}' != "")
			{
				var prodStateCode = JSON.parse('${prodStateCode}');
				for(var j = 0; j < prodStateCode.length; j++)
					innerHtml += '<option value="'+fnDataTableRenderText(prodStateCode[j].codeValue)+'" '+(fnDataTableRenderText(prodStateCode[j].codeValue) == fnDataTableRenderText(jsonData[i].stateCode) ? 'selected':'')+'>'+fnDataTableRenderText(prodStateCode[j].codeName)+'</option>';
			}
			innerHtml += '				</select>';
			innerHtml += '				<button onclick="prodDelete('+jsonData[i].orderScreenMenuIdx+','+jsonData[i].orderProdIdx+')" style="width: 17px; height: 17px;" class="lEditorLayerlistItemDel" ot="lEditorLayerlistItemDel"></button>';
			innerHtml += '			</div>';
			innerHtml += '			<div class="card-body" style="background-color: #dcdbdb;border-radius: 20px;">';
			innerHtml += '				<img width="240px" height="240px" src="'+jsonData[i].thumbnailPath+'" class="card-img-top" alt="..."  style="border-radius: 20px 20px 0px 0px;">';
			innerHtml += '				<ul class="list-group list-group-flush" style="padding: 10px;">';
			innerHtml += '					<li class="list-group-item2" style="font-size: 5px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><strong style="font-size: 20px;">'+fnDataTableRenderText(jsonData[i].prodDisplayName);
			innerHtml += '						</strong><br>';
			innerHtml += '						<font style="color:grey;font-size: 12px;word-break:break-all;">'+fnDataTableRenderText(jsonData[i].prodEnName);
			innerHtml += '						</font><br>';
// 			if(jsonData[i].prodAmount != jsonData[i].prodDiscountAmount){

				innerHtml += '						<div style="font-size: 20px;width: 100%;font-weight: bold;text-align: right;">￦ '+autoComma(jsonData[i].prodAmount - jsonData[i].prodDiscountAmount)+'</div>';
// 			}else{
// 				innerHtml += '						<div style="font-size: 20px;width: 100%;font-weight: bold;text-align: right;">￦ '+autoComma(jsonData[i].prodAmount)+'</div>';
// 			}

			innerHtml += '					</li>';
			innerHtml += '				</ul>';
			innerHtml += '			</div>';
			innerHtml += '		</div>';
			innerHtml += '	</li>';
		}

		if(jsonData.length == 0){
// 			innerHtml += '	<li >상품을 선택해주세요.</li>';
		}

		innerHtml += '</ul>';
		innerHtml += '</div>';
		$(innerHtml).appendTo('#prodTable');
		$('#sortable'+ orderScreenMenuIdx).css('display', 'none').sortable().disableSelection();
	}

	//상품 아이템 조회
	function addPdTable(orderScreenMenuIdx){

		var parent = $('#menuTree').jstree('get_selected');
		var orderScreenMenuIdx = $('#'+parent)[0].children[3].lastChild.id;
		var jsonData = [];
		imageDrawing(orderScreenMenuIdx, jsonData);
		tabTreeList();
		$('#menuCloseBtn').click();
	}

	function prodDelete(orderScreenMenuIdx, orderProdIdx){
		$('#sortable'+orderScreenMenuIdx+' > li[name="'+orderProdIdx+'"]').remove();
	}

</script>