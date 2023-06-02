<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
<!--
tree-root {
    background-color: '#F7F5F5';
}
-->
</style>
<div class="box box-widget">
  <form id="mainForm">
	<div class="box-header">
		<div class="user-block under-line">메뉴 목록</div>
	</div>
	<div class="box-body">
		<table id="menuInfoTable" class="table">
			<thead>
				<tr>
					<th style="text-align: center;">메뉴ID</th>
					<th style="text-align: left;"  >메뉴한글명</th>
					<th style="text-align: left;"  >메뉴 URL</th>
					<th style="text-align: center;">시스템노출</th>
					<th style="text-align: center;">브랜드노출</th>
					<th style="text-align: center;">가맹점노출</th>
					<th style="text-align: center;">수정</th>
					<th style="text-align: center;">정렬</th>
					<th style="text-align: center;">수정일</th>
					<th style="text-align: center;">활성화</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
<!-- 			<button type="button" class="btn btn-primary" data-toggle="modal" id="addBtn" onclick="order();">정렬</button> -->
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="fnCreate();"><i class="glyphicon glyphicon-plus mr-5"></i>대메뉴 등록</button>
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="fnCreateChild();"><i class="glyphicon glyphicon-plus mr-5"></i>자식메뉴 등록</button>
			<button type="button" class="btn btn-delete" onclick="fnDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
  </form>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		menuInfoList();
	});

	<%-- 메뉴 조회 --%>
	function menuInfoList() {
		var optObject = {};

		optObject.id 	= '#menuInfoTable';
		optObject.url 	= '<c:url value="/MenuInfo/MenuInfoList"/>';

		<%-- 컬럼 정의 --%>
	   	optObject.arrColumns = [
	   	     {data: 'menuId'}
            ,{data: 'menuKorName'}
            ,{data: 'menuLinkAddr'}
            ,{data: 'sysShowYn'}
            ,{data: 'brdShowYn'}
            ,{data: 'frcShowYn'}
            ,{data: 'menuIdx'}
            ,{data: 'menuIdx'}
            ,{data: 'modDate'}
            ,{data: 'activeYn'}
        ];

	   	<%-- 컬럼 모양 정의 --%>
	   	optObject.arrColumnDefs = [
	   		{
	         'width'	: '13%'
	        ,'targets'	: 0
	        ,'className': 'text-left'
	        ,'render'	: function (data, type, full, meta)
	                      {
	        	              var returnText = '';
	        	              var sClass = '';

	        	              returnText += '<span style="margin-left: '+(1*full.depth*25)+'px;">';
	        	              returnText += '</span>';
	        	              if(full.leaf > 0) {
	        	            	  returnText += '<button type="button" id="menuId_'+ meta.row +'"  class="btn btn-default btn-xs glyphicon glyphicon-minus" onclick="fnTreeLeaf(\'' + meta.row + '\');"  ></button>\n';
	        	              }
	        	              returnText += data;
	        	              return returnText;
	                      }
	   	    }
	   	   ,{
	         'width'	: '10%'
	        ,'targets'	: 1
	        ,'className': 'text-left'
	   	    }
	   	   ,{
	         'width'	: '7%'
	        ,'targets'	: 3
	        ,'data'		: 'sysShowYn'
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	           			  {
	        	              var spanText = '<span class="glyphicon">&nbsp;</span>';
	        	              if(data == 'Y') {
	        	            	  spanText = '<span class="glyphicon glyphicon-ok"></span>';
	        	              }
	        	              var returnText = '';
	        	              returnText += spanText;
	            		      return returnText;
	            	      }
	   	    }
	   	   ,{
	         'width'	: '7%'
	        ,'targets'	: 4
	        ,'data'		: 'brdShowYn'
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	           			  {
	        	              var spanText = '<span class="glyphicon">&nbsp;</span>';
	        	              if(data == 'Y') {
	        	            	  spanText = '<span class="glyphicon glyphicon-ok"></span>';
	        	              }
	        	              var returnText = '';
	        	              returnText += spanText;
	            		      return returnText;
	            	      }
	   	    }
	   	   ,{
	         'width'	: '7%'
	        ,'targets'	: 5
	        ,'data'		: 'frcShowYn'
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	           			  {
	        	              var spanText = '<span class="glyphicon">&nbsp;</span>';
	        	              if(data == 'Y') {
	        	            	  spanText = '<span class="glyphicon glyphicon-ok"></span>';
	        	              }
	        	              var returnText = '';
	        	              returnText += spanText;
	            		      return returnText;
	            	      }
	   	    }
	   	   ,{
	         'targets'	: 6
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	                      {
	        			      return '<button type="button"  class="btn btn-sm btn-warning" onclick="fnEdit(\'' + meta.row + '\');">수정</button>';
	                      }
	   	    }
	   	   ,{
	         'targets'	: 7
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	                      {
	        	              var returnText = '';
	        	              returnText += '<button type="button" class="btn btn-primary btn-xs glyphicon glyphicon-triangle-top"    onclick="fnTreeUp(\'' + meta.row + '\');"  ></button>\n';
	        	              returnText += '<button type="button" class="btn btn-primary btn-xs glyphicon glyphicon-triangle-bottom" onclick="fnTreeDown(\'' + meta.row + '\');"></button>';
	        	              return returnText;
	                      }
	   	    }
	   	   ,{
	         'targets'	: 8
	        ,'className': 'text-center'
	   	    }
	   	   ,{
	         'width'	: '14%'
	        ,'targets'	: 9
	        ,'data'		: 'activeYn'
	        ,'className': 'text-center'
	        ,'render'	: function (data, type, full, meta)
	           			  {
	        	              var checkText = '';
	        	              if(data == 'Y') {
	        	            	  checkText = 'checked="checked"';
	        	              }
	        	              return '<input type="checkbox" name="activeYn" id="active'+full.menuIdx+'"  value="'+full.menuIdx+'" '+checkText+' data-group-cls="btn-group-sm" class="activeYn" data-reverse />';
	            	      }
	   	    }
	   	];

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
			$('.activeYn').on('change', function(){
				commonActive($(this).val(), { menuIdx: $(this).val()}, '<c:url value="/MenuInfo/MenuInfoActive"/>');
			});

			$('#menuInfoTable tbody').off('click').on('click', 'tr', function(){
				if(window.menuTable){
					var data = window.menuTable.row(this).data();
					if ($(this).hasClass('selected')) {
						//$(this).removeClass('selected');
					} else {
						if(window.menuTable){
							window.menuTable.$('tr.selected').removeClass('selected');
							$('.tree-root').css('background-color','#F7F5F5');
						}
						$(this).addClass('selected');
						<%-- 이미 background-color가 있는 경우 변하지 않으므로 따로 코딩 --%>
						if(data.depth == '0'){
							$(this).css('background-color','#B0BED9');
						}
					}
				}

			});
			returnDataTable = window.menuTable;
	   	}

	   	optObject.fnRowCallback = function(nRow, data) {
	   		var $nRow = $(nRow);
			if(data.depth == 0) {
				$nRow.addClass('tree-root');
				$nRow.css('background-color','#F7F5F5');
			}
	   	}

	   	window.menuTable = $(optObject.id).DataTable(
			{
			 'paging'			: false
			,'ordering'			: false
			,'info'				: false
			,'processing'		: true
			,'scrollY' 			: '50vh'
			,'searching'		: false
			,'scrollCollapse' 	: true
			,'fnServerParams' 	: optObject.serverParams
			,'sAjaxSource' 		: optObject.url
			,'sServerMethod' 	: "POST"
			,'columns' 			: optObject.arrColumns
			,'columnDefs' 		: optObject.arrColumnDefs
			,'fnDrawCallback'	: optObject.fnDrawCallback
			,'fnRowCallback'	: optObject.fnRowCallback
			,'destroy'          : true
			,'language'			: {
				'processing' : '<div class="wrap-loading"><div>DATA LOADING...</div></div>'
			}
			});


	}

	<%-- 트리 펼치기/접기 --%>
	function fnTreeLeaf(nRow) {
		if(window.menuTable){
			var data  	= window.menuTable.row(nRow).data();
			var depth 	= data.depth;
			var bExpand = false;	// 현재 펼침/접힘 여부(true 면 펼침)
			if($('#menuId_'+nRow).hasClass('glyphicon-minus')) {
				$('#menuId_'+nRow).removeClass('glyphicon-minus').addClass('glyphicon-plus');
				bExpand = true;
			} else {
				$('#menuId_'+nRow).removeClass('glyphicon-plus').addClass('glyphicon-minus');
			}
			$('#menuInfoTable tbody tr').each(function(index, data){
				<%--  nRow 부터 다음 leaf가 있는 row까지를 펼치거나 접어야 함 --%>
				if(index > nRow) {
					data  	= window.menuTable.row(index).data();
					if(data.depth == depth){
						return false;
					}

					if(bExpand) {
						$(this).hide();
					} else {
						$(this).show()
					}
				}
			});
		}
	}

	<%-- 수정 호출 --%>
	function fnEdit(nRow) {
		if(window.menuTable){
			var data  		= window.menuTable.row(nRow).data();
			var paramValue	= {
				'menuIdx' 		: data.menuIdx,
				'parentMenuId'	: data.parentMenuId
			};

			$.modalCommon.loadFullDataView('메뉴 수정', '<c:url value="/MenuInfo/FormNullTemp"/>', paramValue);
		}
	}

	<%-- 트리 단계 위로 변경 --%>
	function fnTreeUp(nRow) {
		if(window.menuTable){
			var data  	= window.menuTable.row(nRow).data();
			var paramValue	= {
				'menuIdx' 		: data.menuIdx,
				'treeUpDown'	: 'U'
			};
			var bUpPossible = false;
			<%-- 만약 제일 위이면 얼럿 표시만 함 --%>
			$('#menuInfoTable tbody tr').each(function(index){
				var data2  	= window.menuTable.row(index).data();
				if((data2.parentMenuId == data.parentMenuId) && (data2.depth == data.depth) && (index < nRow)) {
					bUpPossible = true;
					return false;
				}
			});
			if(!bUpPossible) {
				$.modalCommon.alertView('맨 위 입니다.');
			} else {
				fnTreeUpDown(paramValue);
			}
		}
	}

	<%-- 트리 단계 아래로 변경 --%>
	function fnTreeDown(nRow) {
		if(window.menuTable){
			var data  	= window.menuTable.row(nRow).data();
			var paramValue	= {
				'menuIdx' 		: data.menuIdx,
				'treeUpDown'	: 'D'
			};
			var bDownPossible = false;
			<%-- 만약 제일 아래이면 얼럿 표시만 함 --%>
			$('#menuInfoTable tbody tr').each(function(index){
				var data2  	= window.menuTable.row(index).data();
				if((data2.parentMenuId == data.parentMenuId) && (data2.depth == data.depth) && (index > nRow)) {
					bDownPossible = true;
					return false;
				}
			});
			if(!bDownPossible) {
				$.modalCommon.alertView('맨 아래 입니다.');
			} else {
				fnTreeUpDown(paramValue);
			}
		}
	}

	<%-- 대메뉴 등록 호출 --%>
	function fnCreate() {
		var paramValue = {};
		$.modalCommon.loadFullDataView('메뉴 등록', '<c:url value="/MenuInfo/FormNullTemp"/>', paramValue);
	}

	<%-- 자식 메뉴 등록 호출 --%>
	function fnCreateChild() {
		if($('#menuInfoTable tbody tr.selected').length == 0) {
			$.modalCommon.alertView('자식 메뉴를 등록할 메뉴를 선택해주세요.');
		} else {
			var nRow 		= $('#menuInfoTable tbody tr.selected').index();
			var data  		= window.menuTable.row(nRow).data();
			var paramValue	= {
				'parentMenuId' : data.menuId
			};

			$.modalCommon.loadFullDataView('메뉴 등록', '<c:url value="/MenuInfo/FormNullTemp"/>', paramValue);
		}
	}

	<%-- 삭제 호출 --%>
	function fnDelete() {
		if($('#menuInfoTable tbody tr.selected').length == 0) {
			$.modalCommon.alertView('삭제할 메뉴를 선택해주세요.');
		} else {
			var nRow 	= $('#menuInfoTable tbody tr.selected').index();
			var data  	= window.menuTable.row(nRow).data();
			var message = '삭제하시겠습니까?';
			if(data.leaf > 0) {
				message = '자식 메뉴도 같이 삭제됩니다. 삭제하시겠습니까?';
			}
			var data  		= window.menuTable.row(nRow).data();
			var menuIdx 	= data.menuIdx;
			var menuIdxs 	= data.menuIdx;
			var menuId		= data.menuId;

			$('#menuInfoTable tbody tr').each(function(index, data){
				<%--  nRow 부터 다음 leaf가 있는 row까지를 펼치거나 접어야 함 --%>
				data  	= window.menuTable.row(index).data();
				if(data.path) {
				    if((data.path.indexOf(menuId) > -1) && (data.menuIdx != menuIdx)) {
				    	menuIdxs += ',' + data.menuIdx;
				    }
				}
			});

			var callBack = function (result) {
	        	if (result) {
	                var param = {idx:menuIdxs};
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/MenuInfo/MenuInfoDelete"/>', param);
	                if (dResult.resultCode == 0){
	                	menuInfoList();
	                }
	            }
	        }

			BootstrapDialog.confirm({
				title : '',
				message : message,
				type : BootstrapDialog.TYPE_WARNING,
				closable : false,
				draggable : true,
				btnCancelLabel: '취소',
				btnOKLabel: '확인',
				btnOKClass : 'btn-warning',
				callback : callBack
			});
		}
	}

	function fnTreeUpDown(param) {
		var message = '정렬순서를 ';
		if(param.treeUpDown == 'U'){
			message += '위로 ';
		} else {
			message += '아래로 ';
		}
		message += '변경하시겠습니까?';

		BootstrapDialog.confirm({
			title : '',
			message : message,
			type : BootstrapDialog.TYPE_WARNING,
			closable : false,
			draggable : true,
			btnCancelLabel: '취소',
			btnOKLabel: '확인',
			btnOKClass : 'btn-warning',
			callback : function(ok){
				if(ok){
					var result = $.ajaxUtil.ajaxDefault(
							'<c:url value="/MenuInfo/TreeUpDown"/>', param);

					if (result.resultCode == 0) {
						$.modalCommon.close();
						menuInfoList();
					}
				}
			}
		});
	}

</script>