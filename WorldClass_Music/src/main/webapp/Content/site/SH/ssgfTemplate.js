(function ($) {
	var gobj;
	var contentUrl;
	$.fn.ContentEditor = function(options) {
		//console.log('ContentEditor');
		var settings = $.extend({
			resolutionW : 1080,
			resolutionH : 1920,
			rows : 4,
			headerH : 50,
			url : 'N'
		}, options);

		return this.each(function() {
			gobj = $(this);
			$.fn.ContentEditor.Init(settings, $(this));
		});
	}

	$.fn.ContentEditor.Init = function(settings, obj) {
		//console.log('ContentEditor');
		contentUrl = settings.url;
		$(obj).on('mousedown', $.fn.OnlyDocumentMouseDownEH);
		$(obj).on('keypress', $.fn.OnlyDocumentKeypress);

		/*********************************          Header Start            **************************************/

		//Create Header
		var headDiv = $('<div>').appendTo($(obj))
								.addClass('headDiv')
								.attr('ot', 'hd');

		var tb = $('<table>').appendTo(headDiv)
							 .css('width', '100%')
							 .css('height', '100%');

		var tr = $('<tr>').appendTo(tb);

		var td;
		var input;

		//Screen Name
		$('<th>').appendTo(tr)
				 .text('스크린 명 : ')
				 .css('text-align', 'center')
				 .css('width', '165px')
				 .css('font-size', '20px');
		td = $('<td>').appendTo(tr);
		$('<input>').attr('type', 'text')
					.attr('ot', 'screenName')
					.attr('id', 'screenName')
					.attr('maxlength','50')
					.addClass('scpropScreenName')
					.css('text-align', 'center')
					.css('height', settings.headerH)
					.appendTo(td);

		// 해상도 RESOLUTION_X
		$('<th>').appendTo(tr).text('해상도 :').css('font-size', '20px');
		td = $('<td>').appendTo(tr).attr('ot', 'resolutionX');
		input = $('<input>').attr('id', 'resolutionX')
					.attr('type', 'text')
//					.attr('rowCnt', settings.rows)
					.attr('maxlength', 4)
					.css('width', '80px')
					.css('text-align', 'center')
					.css('height', settings.headerH)
					.css('font-size', '20px')
					.on('change', $.fn.ChangeResolution)
					.prependTo(td).val(settings.resolutionW);

		// RESOLUTION_Y
		$('<th>').appendTo(tr).text('X ').css('font-size', '20px');
		td = $('<td>').appendTo(tr).attr('ot', 'resolutionY');
			$('<input>').attr('id', 'resolutionY')
			.attr('type', 'text')
//			.attr('rowCnt', settings.rows)
			.attr('maxlength', 4)
			.css('width', '80px')
			.css('text-align', 'center')
			.css('height', settings.headerH)
			.css('font-size', '20px')
			.on('change', $.fn.ChangeResolution)
			.prependTo(td).val(settings.resolutionH);

		//Row
		$('<th>').appendTo(tr)
				 .text('GRID :')
				 .css('font-size', '20px');
		td = $('<td>').appendTo(tr)
					  .attr('ot', 'RowTD');
		$('<input>').attr('id', 'rowCnt')
					.attr('type', 'text')
					.attr('rowCnt', settings.rows)
					.attr('maxlength', 2)
					.css('width', '80px')
					.css('text-align', 'center')
					.css('height', settings.headerH)
					.css('font-size', '20px')
					.on('change', $.fn.ChangeColAndRowValue)
					.prependTo(td).val(settings.rows);

		//Col
        $('<th>').appendTo(tr).text('X ').css('font-size', '20px');
        td = $('<td>').attr('ot', 'ColumnTD').appendTo(tr);
        $('<input>').attr('id', 'colCnt')
        			.attr('type', 'text')
        			.attr('colCnt', settings.cols)
        			.attr('maxlength', 2)
        			.css('width', '80px')
        			.css('text-align', 'center')
        			.css('height', settings.headerH)
        			.css('font-size', '20px')
        			.on('change', $.fn.ChangeColAndRowValue)
        			.prependTo(td).val(settings.cols);

         //APPLY버튼
         /*td = $('<td>').attr('ot', 'applyTD').css('width', '100px').appendTo(tr);
         $('<button>').css('width', '80px')
         		      .css('height', settings.headerH)
         		      .attr('type', 'button')
         		      .addClass('btn')
         		      .addClass('btn-primary')
         		      .addClass('btn-info')
         		      .text('APPLY')
         		      .appendTo(td)
         		      .on('click', $.fn.ChangeRowAndCol);*/

         /*********************************          Header End & Left Side(Layer List) Start            **************************************/

         //Left Side Layout List
         var lSideDiv = $('<div>').appendTo($(obj))
         						  .attr('id', 'lList')
         						  .addClass('lSideDiv');


         /*********************************          Left Side(Layer List) End & Center Start            **************************************/
         var resolutionX = $('#resolutionX').val();
 		 var resolutionY = $('#resolutionY').val();

         //Center
         var cenDiv = $('<div>').appendTo($(obj)).attr('id', 'lEditorArea').addClass('lEditorArea')
         /*.css('width', "799")*/;
//         width: 799px; height: 345px;"
         var resolution = $.fn.GetScreenResolutionValue(resolutionX, resolutionY); //해상도 값 추출
 		 var thumSize = resolution.getSize2({ w: cenDiv.width(), h: cenDiv.height() });

 		 var CanvasDiv = $('<div>').appendTo(cenDiv)
        		 				   .addClass('layoutEditorCanvas')
        		 				   .attr('drag', 'true')
        		 				   .attr('id', 'CanvasDiv')
        		 				   .css('width', thumSize.w)
        		 				   .css('height', thumSize.h)
//        		 				   .css('margin-left', (thumSize.w/2)+30)
        		 				   .css('margin-left', (cenDiv.width()-thumSize.w)/2)
        		 				   .css('margin-top', (cenDiv.height()-thumSize.h)/2)
        		 				   .css('-moz-user-select', 'none')
        		 				   .css('-webkit-user-select', 'none')
        		 				   .on('mousedown', $.fn.CanvasOnMouseDown);

         $.fn.MakeLayoutCanvasRowsAndColumns(CanvasDiv, settings.rows, settings.cols);


         /*********************************          Center End & Right Side(Content List) Start           **************************************/


         var ClDiv = $('<div>').appendTo($(obj))
         					   .addClass('contentList')
         					   .addClass('pull-right')
         					   .attr('id', 'contentList')
         					   /*.css('width', "280");*/
         					   ;

         var itemDiv = $('<div>').appendTo(ClDiv)
         						 .attr('id', 'contentDiv')
         						 .attr('ot', 'contentDiv')
         						 .addClass('contentDiv')
         						 .css('width', '100%');

         var comm = '';
         if (settings.url == 'N') {
        	 comm = $('<div>').appendTo(ClDiv)
							  .attr('id', 'contentCommandBar')
							  .addClass('comm')
							  .css('height', ClDiv.height()/5)
							  .on('click', $.fn.AddNewContent); 
         }
         else {
        	 comm = $('<div>').appendTo(ClDiv)
							  .attr('id', 'contentCommandBar')
							  .addClass('comm')
							  .css('height', ClDiv.height()/5)
							  .on('click', $.fn.AddNewContentUrl); 
         }

         //content text
         var ancDiv = $('<div>').appendTo(comm)
         						.attr('id', 'addContent')
         						.text('새로운 컨텐츠 등록')
         						.addClass('ancDiv');

         //plus Icon
         var pIcon = $('<i>').appendTo(comm)
         					 .attr('id', 'plusIconDiv')
         					 .addClass('fa')
         					 .addClass('fa-plus')
         					 .addClass('pIcon');

/*         var tottalPTime = $('<div>').appendTo(ClDiv)
							 .attr('id', 'tottalPTime')
							 .text('총 재생시간')
							 .addClass('ancDiv');

		//재생시간
		var pIcon = $('<i>').appendTo(tottalPTime)
							 .attr('id', 'tottalPTimeValue')
							 .addClass('contentDiv')
							 .text('11:11');*/

         /*********************************          Right Side(Content List) End            **************************************/

	};

	// 엔터키 방지
	$.fn.OnlyDocumentKeypress = function (e) {
		//console.log('OnlyDocumentKeypress');
		 if ( e.which == 13 ) {
	         return false;
	     }
	}

	//가이드 라인 안에서 Layer가 아닌 빈 화면 클릭
	$.fn.OnlyDocumentMouseDownEH = function (e) {
		$(':focus').blur();  
		//console.log('OnlyDocumentMouseDownEH');
		var possableList = [
			'lEditorResizeHandle',
			'lEditorLayer',
			'lEditorCanvas',
			'lEditorResizePoint',
			'lEditorLayerlistItem',
			'lList',
			'layoutList',
			'lEditorLayerlistItemDel',
			'contentItemListButton',
			'contentItem', 'contentCommandBar', 'contentCommandBarLabel', 'sideDiv', 'sbDiv', 'contentItemDelete',
			'contentItemList', 'contentItemNumber', 'contentItemName', 'contentItemDelete', 'contentItemTime', 'contentItemLength',
			'contentTimeInput', 'contentImage', 'plusIconDiv', 'addContent', 'totalPlayTime', 'contentDiv', 'contentItemImage'
		];
		var isPossable = true;
		for (var i = 0; i < possableList.length; ++i)
		{
			isPossable = isPossable && $(e.target).attr('ot') != possableList[i];
			isPossable = isPossable && $(e.target).attr('class') != possableList[i];
			isPossable = isPossable && $(e.target).attr('id') != possableList[i];
		}
		if (isPossable)
		{
//			$.fn.LayerSeletionClear();
		}
	}

	//col & row change
	$.fn.ChangeColAndRowValue = function(e) {
		//console.log('ChangeColAndRowValue');
		var row = $('#rowCnt').val();
		var col = $('#colCnt').val();
		if (isNaN(row)) {
			$('#rowCnt').val($('#rowCnt').attr('rowCnt'));
			$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('숫자만 입력하세요.');
			return;
		}
		if (isNaN(col)) {
			$('#colCnt').val($('#colCnt').attr('colCnt'));
			$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('숫자만 입력하세요.');
			return;
		}
		if (row <= 0) {
			$('#rowCnt').val($('#rowCnt').attr('rowCnt'));
			$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('Row는 1이상 등록가능합니다.');
			return;
		}
		if (col <= 0) {
			$('#colCnt').val($('#colCnt').attr('colCnt'));
			$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('Column는 1이상 등록가능합니다.');
			return;
		}

//		$.fn.ReMakeLayoutCanvasRowsAndColumns( $('#CanvasDiv'), row, col);
		$.fn.ChangeRowAndCol();
	}



	// 해상도 변경
	$.fn.ChangeResolution = function(e) {
		//console.log('ChangeResolution');
		// 선택된 레이어 제거
		$.fn.LayerSeletionClear();

		var resolutionX = $('#resolutionX').val();
		var resolutionY = $('#resolutionY').val();
		if (isNaN(resolutionX)) {
			$('#resolutionX').val("");
			//$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('숫자만 입력하세요.');
			return;
		}
		if (isNaN(resolutionY)) {
			$('#resolutionY').val("");
			//$.fn.ChangeRowAndCol();
			$.modalCommon.alertView('숫자만 입력하세요.');
			return;
		}
		if (resolutionX <= 0) {
			$('#resolutionX').val("");
			//$.fn.ChangeRowAndCol();
//			$.modalCommon.alertView('해상도는 1이상 등록가능합니다.');
			return;
		}
		if (resolutionY <= 0) {
			$('#resolutionY').val("");
//			$.fn.ChangeRowAndCol();
//			$.modalCommon.alertView('해상도는 1이상 등록가능합니다.');
			return;
		}

		var resolutionX = $('#resolutionX').val();
		var resolutionY = $('#resolutionY').val();

		// CanvasDiv 사이즈 변경을 위한 처리
		var resolution = $.fn.GetScreenResolutionValue(resolutionX, resolutionY); //해상도 값 추출
		var thumSize = resolution.getSize2({ w: $('#lEditorArea').width(), h: $('#lEditorArea').height() });
		var CanvasDiv = $('#CanvasDiv').css('width', thumSize.w)
		.css('height', thumSize.h)
		.css('margin-left', ($('#lEditorArea').width()-thumSize.w)/2)
		.css('margin-top', ($('#lEditorArea').height()-thumSize.h)/2)

	    var row = $('#rowCnt').val();
		var col = $('#colCnt').val();
		$.fn.ReMakeLayoutCanvasRowsAndColumns(CanvasDiv, row, col);

	}

	//Apply
	$.fn.ChangeRowAndCol = function(e) {
		//console.log('ChangeRowAndCol');
		var resolutionX = $('#resolutionX').val();
		var resolutionY = $('#resolutionY').val();
		var row = $('#rowCnt').val();
		var col = $('#colCnt').val();
		var canvas = $('#CanvasDiv');

		$('#rowCnt').attr('rowCnt', row);
		$('#colCnt').attr('colCnt', col);

		canvas.find('div[ot="lEditorGridLine"]').remove();
		$.fn.MakeLayoutCanvasRowsAndColumns(canvas, row, col);
		var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas);

		canvas.find('.lEditorLayer').each(function (idx) {
			var pos = $(this).attr('rc').split('_');
			var tablePosi = { row1: Number(pos[0]), col1: Number(pos[1]), row2: Number(pos[4]), col2: Number(pos[5]) };

			var rspan = Number(pos[2]);
			var cspan = Number(pos[3]);
			if (tablePosi.row1 > row - 1) tablePosi.row1 = row - 1;
			if (tablePosi.col1 > col - 1) tablePosi.col1 = col - 1;

			var col2 = tablePosi.col1 + cspan - 1;
			if (col2 >= col - 1)
				tablePosi.col2 = col - 1;
			var row2 = tablePosi.row1 + rspan - 1;
			if (row2 >= row - 1)
				tablePosi.row2 = row - 1;

			rspan = tablePosi.row2 - tablePosi.row1 + 1;
			cspan = tablePosi.col2 - tablePosi.col1 + 1;

			var layerLoc = $.fn.GetLayerLocationInfoFromColumnAndRows(tablePosi, rcloc);
			$(this).attr('rc', tablePosi.row1.toString() + '_' + tablePosi.col1.toString() + '_' + rspan.toString() + '_' + cspan.toString()
									+ '_' + tablePosi.row2.toString() + '_' + tablePosi.col2.toString())
			$(this).css('width', Number(layerLoc.w)).css('height', Number(layerLoc.h))
				   .css('top', layerLoc.t).css('left', layerLoc.l);
		});

		var layers = canvas.find("div[class='lEditorLayer']");
        //Apply 클릭시 글자 크기 맞추기
        layers.each(function (idx) {
            $(this).fitText(.11);
        });
	}

	//Resolution
	//해상도 값 추출
	$.fn.GetScreenResolutionValue = function(scpropResolutionW, scpropResolutionH) {
		//console.log('GetScreenResolutionValue');
        var w = scpropResolutionW;
        var h = scpropResolutionH;

		return {
			w: w,
			h: h,
			isVal: (!isNaN(w) && !isNaN(h) && w != '' && h != ''),
			getSize: function (cwidth) {
				var ret = {};
				var nw = Number(w);
				var nh = Number(h);
				var cw = cwidth * .9;
				if (nw > nh) {
					ret.w = cw;
					ret.h = cw * nh / nw;
				}
				else {
					ret.h = cw;
					ret.w = cw * nw / nh;
				}
				return ret;
			},
			getSize2: function (size) {
				var ret = {};
				var nw = Number(w);
				var nh = Number(h);
				var rate = nw / nh;

				if (size.w > size.h) {
					size.h = size.h - 10;
					ret.w = size.h * rate;
					ret.h = size.h;
					if (ret.w > size.w - 10) {
						ret.w = size.w - 10;
						ret.h = ret.w / rate;
					}
				}
				else {
					size.w = size.w - 10;
					ret.w = size.w;
					ret.h = size.h / rate;
					if (ret.h > size.h - 10) {
						ret.h = size.h - 10;
						ret.w = ret.h * rate;
					}
				}
				return ret;
			}
		};
    }

	//GridLine 그리기
	$.fn.MakeLayoutCanvasRowsAndColumns = function (canvas, rows, cols) {
		//console.log('MakeLayoutCanvasRowsAndColumns');
		for (var r = 1; r < rows; ++r) {
			$('<div>').attr('ot', 'lEditorGridLine')
					  .attr('drag', 'true').attr('gridtype', 'r')
					  .css('position', 'absolute')
					  .css('top', $(canvas).height() / rows * r)
					  .css('height', 1).css('width', '100%')
					  .css('border-top', '2px dotted #676a6c')
					  .attr('loc', $(canvas).height() / rows * r)
					  .attr('order', r)
					  .appendTo($(canvas));
		}

		for (var c = 1; c < cols; ++c) {
			$('<div>').attr('ot', 'lEditorGridLine')
					  .attr('drag', 'true').attr('gridtype', 'c')
					  .css('position', 'absolute')
					  .css('left', $(canvas).width() / cols * c)
					  .css('height', '100%').css('width', '1')
					  .attr('order', c)
					  .attr('loc', $(canvas).width() / cols * c)
					  .css('border-left', '2px dotted #676a6c')
					  .appendTo($(canvas));
		}
	}

	//GridLine 그리기
	$.fn.ReMakeLayoutCanvasRowsAndColumns = function (canvas, rows, cols) {
		//console.log('ReMakeLayoutCanvasRowsAndColumns');
		for (var r = 1; r < rows; ++r) {
			$('<div>').attr('ot', 'lEditorGridLine')
					  .attr('drag', 'true').attr('gridtype', 'r')
					  .css('position', 'absolute')
					  .css('top', $(canvas).height() / rows * r)
					  .css('height', 1).css('width', '100%')
					  .css('border-top', '2px dotted #676a6c')
					  .attr('loc', $(canvas).height() / rows * r)
					  .attr('order', r)
					  .html("");
		}

		for (var c = 1; c < cols; ++c) {
			$('<div>').attr('ot', 'lEditorGridLine')
					  .attr('drag', 'true').attr('gridtype', 'c')
					  .css('position', 'absolute')
					  .css('left', $(canvas).width() / cols * c)
					  .css('height', '100%').css('width', '1')
					  .attr('order', c)
					  .attr('loc', $(canvas).width() / cols * c)
					  .css('border-left', '2px dotted #676a6c')
					  .html("");
		}

		// 새로생성된 화면 기준으로 줄 새로 긋기
		$.fn.ChangeRowAndCol();
	}

	//Layer mousedown
	$.fn.CanvasOnMouseDown = function(e) {
		//console.log('CanvasOnMouseDown');
		if (e.target === e.currentTarget) {
			var canvas = $(e.currentTarget);
			sposition = {
				x: Math.round(e.clientX - canvas.offset().left),
				y: Math.round(e.clientY - canvas.offset().top)
			};

			$.fn.AdjustPostionByScroll(sposition);
			$(document).on('mousemove', $.fn.CanvasDocumentOnMouseMove);
			$(document).on('mouseup', $.fn.CanvasDocumentOnMouseUp);
			$.fn.MakeGuideLineObject(canvas);
		}
	}
	
	
	
	
	/**
	 * tshop 신규 추가 
	 */
	$.fn.AddNewContentUrl = function(e) {
		var canvas = $('#CanvasDiv');
		var cdiv = $('#contentDiv');
		var layerGuide = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('guid');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+layerGuide+'"]');
		var layer = canvas.find("div[class='lEditorLayer'][sel='true']");

		var ret = {};

		ret.isPossable = layer.length > 0;

		if (!ret.isPossable) {
			$.modalCommon.alertView('Layer를 선택해주세요.');
			return;
		}
		var param = { 
				brandIdx:$('#frm #brandIdx').val(), francIdx: $('#frm #francIdx').val()
		}
		$.modalCommon.loadDataView('Add Content', contentUrl, param);
	}
})($);