(function ($) {
	/*********************************          Layer MouseDown 관련 Start            **************************************/

	$.fn.AdjustPostionByScroll = function(position) {
		//console.log('AdjustPostionByScroll');
		var doc = document.documentElement;
		var left = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
		var top = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0);
		position.y = (position.y + top);
		position.x = position.x + left;
	}

	//Layer Drag Start
	$.fn.CanvasDocumentOnMouseMove= function(e) {
		//console.log('CanvasDocumentOnMouseMove');
		if ($(e.target).attr('drag') == 'true')
		{
			var canvas = $('#CanvasDiv');

			var guide = canvas.find('div[ot="lEditorGuideLine"]');
			var nposition = {
				x: Math.round(e.clientX - canvas.offset().left),
				y: Math.round(e.clientY - canvas.offset().top)
			};
			$.fn.AdjustPostionByScroll(nposition);
			var dx = nposition.x - sposition.x;
			var dy = nposition.y - sposition.y;

			if (dx >= 0 && dy >= 0)
				guide.css('width', dx).css('height', dy);
			else if (dx > 0 && dy < 0)
				guide.css('width', dx).css('top', nposition.y).css('height', Math.abs(dy));
			else if (dx < 0 && dy > 0)
				guide.css('height', dy).css('left', nposition.x).css('width', Math.abs(dx));
			else
				guide.css('left', nposition.x).css('top', nposition.y).css('width', Math.abs(dx)).css('height', Math.abs(dy));
		}
	}

	//Layer 그릴 때 임시로 보여주는 Guide Line
	$.fn.MakeGuideLineObject = function(canvas){
		//console.log('MakeGuideLineObject');
		$('<div>').attr('ot', 'lEditorGuideLine')
				  .attr('drag', 'true')
				  .addClass('layoutEditorGuideLine')
			      .css('position', 'absolute')
				  .css('top', sposition.y).css('left', sposition.x)
				  .appendTo($(canvas));
	}

	//Layer 좌표값 확정
	$.fn.CanvasDocumentOnMouseUp = function(e) {
		//console.log('CanvasDocumentOnMouseUp');
		var canvas = $('#CanvasDiv');
		canvas.find('div[ot="lEditorGuideLine"]').remove();
		$(document).off('mousemove', $.fn.CanvasDocumentOnMouseMove);
		$(document).off('mouseup', $.fn.CanvasDocumentOnMouseUp);

		var nposition = {
			x: Math.round(e.clientX - canvas.offset().left),
			y: Math.round(e.clientY - canvas.offset().top)
		};
		$.fn.AdjustPostionByScroll(nposition);
		if (Math.abs(sposition.x - nposition.x) > 10 && Math.abs(sposition.y - nposition.y))
			$.fn.MakeContentLayer(e);
		else
			$.fn.LayerSeletionClear();
	}

	/*********************************          Layer MouseDown 관련 End            **************************************/

	/*********************************          CanvasDocumentOnMouseUp 관련 Start            **************************************/
	//New Content Layer
	$.fn.MakeContentLayer = function(e)	{
		//console.log('MakeContentLayer');
		var canvas = $('#CanvasDiv');
		// 주석처리 레이어2개만 사용가능하도록 하는 소스
		/*if(canvas.find('.lEditorLayer').length >= 2){
			return false;
		}*/

		var rc = $.fn.GetColumnAndRowInfo(canvas, e); // Row And Column 위치정보
        var item = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('sel', 'false');

		if (rc.isVal)
		{
			var layer = $.fn.MakeNewLayer(rc, canvas); //layer 생성
			$.fn.BindLayerListByPage(null); //layer list
			$.fn.MakeResizeHandleLayer(layer); //layer 사이즈 조절
		}
	}

	//select Layer -> deselect Layer
	$.fn.LayerSeletionClear = function () {
		//console.log('LayerSeletionClear');
		var editor = $('#contentEditor');
		var canvas = $('#CanvasDiv');

		var oldLayer = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('sel', 'false');
		canvas.find('div[ot="lEditorResizeHandle"]').remove();

		$('.contentItemList').css('display', 'none').attr('dis', 'false');
		$('#contentDiv').css('height', '0');

		var list = editor.find('div[ot="lList"]');
		var container = list.children('div .layoutContainer');
		var item = container.find('div[sel="true"]').css('border', '0px dotted #17b293').attr('sel', 'false');
		item.css('background', item.attr('colorbackup'));
	}

	/*********************************          CanvasDocumentOnMouseUp 관련 End            **************************************/

	/*********************************          MakeContentLayer 관련 Start            **************************************/
	// Row And Column 위치정보
	$.fn.GetColumnAndRowInfo = function(canvas, e) {
		//console.log('GetColumnAndRowInfo');
		var bthick = 2;
		var nposition = {
			x: Math.round(e.clientX - canvas.offset().left),
			y: Math.round(e.clientY - canvas.offset().top)
		};
		$.fn.AdjustPostionByScroll(nposition);
		var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas);
		var arrY = rcloc.arrY;
		var arrX = rcloc.arrX;

		var minY, maxY, minX, maxX;
		minY = Math.min(sposition.y, nposition.y);
		maxY = Math.max(sposition.y, nposition.y);
		minX = Math.min(sposition.x, nposition.x);
		maxX = Math.max(sposition.x, nposition.x);

		var tablePosi = {};
		for (var i = 0; i < arrY.length - 1; ++i)
		{
			if (arrY[i] <= minY && minY < arrY[i + 1])
				tablePosi.row1 = i;
			if (arrY[i] <= maxY && maxY < arrY[i + 1])
				tablePosi.row2 = i;
		}

		for (var i = 0; i < arrX.length - 1; ++i)
		{
			if (arrX[i] <= minX && minX < arrX[i + 1])
				tablePosi.col1 = i;
			if (arrX[i] <= maxX && maxX < arrX[i + 1])
				tablePosi.col2 = i;
		}
		return $.fn.GetLayerLocationInfoFromColumnAndRows(tablePosi, rcloc);
	}

	//New Layer Create
	$.fn.MakeNewLayer = function(rc, canvas) {
		//console.log('MakeNewLayer');
		var r = Math.floor(Math.random() * 100);
		var g = Math.floor(Math.random() * 100);
		var b = Math.floor(Math.random() * 100);
		var color = Please.make_color({ scheme_type: 'mono' });
        var cnt = canvas.find('div[class="lEditorLayer"]').length + 1;
        var code = [];

        canvas.find('div[class="lEditorLayer"]').each(function (idx) {
            code.push($(this).attr('charcode'));
        });
        //Layer 문자 순서대로 출력
        if (code[0] != null) {
            cnt = Number(code[code.length - 1]) +1;
        }
        else {
            cnt = 65
        }

		var layer = $('<div>').css('position', 'absolute')
								  .addClass('lEditorLayer')
								  .attr('drag', 'true').attr('sel', 'true')
								  .attr('rc', rc.r.toString() + '_' + rc.c.toString() + '_' + rc.rspan.toString() + '_' + rc.cspan.toString()
													+ '_' + (rc.r + rc.rspan - 1).toString() + '_' + (rc.c + rc.cspan - 1).toString())
                                  .attr('charcode', cnt)
								  .css('top', rc.t).css('left', rc.l)
								  .css('width', rc.w)
								  .attr('alpha',String.fromCharCode(cnt))
								  .text(String.fromCharCode(cnt))
								  .css('background', color)
								  .css('height', rc.h).appendTo(canvas)
								  .attr('guid', $.fn.GetGuid)
								  .on('mousedown', $.fn.LayerSelectedChangeEH); //Layer 선택 값 변경
		layer.fitText(.11);

		$('<div>').attr('ot', 'contentItemList')
				  .addClass('layerContentItemList')
				  .appendTo(layer);

		return layer;
	}


	//Left Side Layer List
	$.fn.BindLayerListByPage = function	(newPage) {
		//console.log('BindLayerListByPage');
		var editor = $('#contentEditor');
		var canvas = $('#CanvasDiv');
		var area = $('#lEditorArea');

		var list = $('#lList');
		list.children().remove();
		var width = list.height();

		var container = $('<div>').addClass('layoutContainer')
								  .css('width', '100%')
								  .css('margin-top', '2px')
								  .appendTo(list);
		canvas.find('.lEditorLayer').each(function (idx) {
			var l = $('<div>').css('width', 160)
					  .css('height', width / 8)
					  .attr('sel', 'false')
					  .attr('ot', 'lEditorLayerlistItem')
					  .attr('tlayer', $(this).attr('guid'))
				      .text($(this).attr('alpha'))
				      .addClass('lEditorLayerlistItem')
					  .attr('colorbackup', $(this).css('background-color'))
					  .css('background-color', $(this).css('background-color')).css('opacity', .8)
					  .on('mousedown', $.fn.LayerListSelectedChangeEH)
					  .appendTo(container);

			$('<div>').text('00:00:00').attr('id', 'totalPlayTime').appendTo(l);
			$.fn.ChangeTotalPlayTime(l.attr('tlayer'));

			var removeBtn = $('<button>').css('width', 17).css('height', 17)
									     .attr('ot', 'lEditorLayerlistItemDel')
									     .addClass('lEditorLayerlistItemDel')
									     .on('click', $.fn.LayerDeleteConfirm)
									     .appendTo(l);

//			$('<i>').addClass('fa').addClass('fa-remove').css('margin-left', '-3px').appendTo(removeBtn);

			if ($(this).attr('sel') == 'true')
				l.attr('sel', 'true').css('border', '2px solid #17b293');
		});

		container.sortable({
			revert: 10,
			update: function (e, ui) { $.fn.LayerOrderChangeEH(e, ui); }
		});
	}

	//Layer ReSize
	$.fn.MakeResizeHandleLayer = function (layer) {
		//console.log('MakeResizeHandleLayer');
		var size = 8;
		layer.parent().find('div[class="lEditorLayer"]').attr('sel', 'false');
		layer.parent().find('div[ot="lEditorResizeHandle"]').remove();

		layer.attr('sel', 'true');

		var t = Number(layer.offset().top);
		var l = Number(layer.offset().left);
		var w = Number(layer.width());
		var h = Number(layer.height());

		var hd = $('<div>').attr('ot', 'lEditorResizeHandle')
				  .attr('tlayer', layer.attr('guid'))
				  .css('position', 'absolute')
				  .css('top', layer.css('top'))
				  .css('left', layer.css('left'))
				  .css('width', layer.css('width'))
				  .css('height', layer.css('height'))
				  .css('opacity', .8).css('background', '#488fe7')
				  .on('mousedown', $.fn.ResizeHandlerMouseDownEH)
				  .css('cursor', 'move')
				  .css('border', '2px dashed #0000ff').appendTo(layer.parent());

		var loc = [
			{ x: 0, y: 0, mt: -size / 2, ml: -size / 2, c: 'nw-resize' },
			{ x: '50%', y: 0, mt: -size / 2, ml: -size / 2, c: 'n-resize' },
			{ x: '100%', y: 0, mt: -size / 2, ml: -size / 2, c: 'ne-resize' },
			{ x: 0, y: '50%', mt: -size / 2, ml: -size / 2, c: 'w-resize' },
			{ x: '100%', y: '50%', mt: -size / 2, ml: -size / 2, c: 'e-resize' },
			{ x: 0, y: '100%', mt: -size / 2, ml: -size / 2, c: 'sw-resize' },
			{ x: '50%', y: '100%', mt: -size / 2, ml: -size / 2, c: 's-resize' },
			{ x: '100%', y: '100%', mt: -size / 2, ml: -size / 2, c: 'se-resize' }
		];

		for (var i = 0; i < loc.length; ++i)
		{
			$('<div>').attr('ot', 'lEditorResizePoint').css('position', 'absolute').css('cursor', loc[i].c)
					  .css('width', size).css('height', size)
					  .css('border', '1px solid #000000').css('background', '#ffffff')
					  .css('left', loc[i].x).css('top', loc[i].y)
					  .css('margin-left', loc[i].ml).css('margin-top', loc[i].mt)
					  .on('mousedown', $.fn.LayerEditorResizeHandleMouseDownEH)
					  .appendTo(hd);
		}
	}

 	/*********************************          MakeContentLayer 관련 End            **************************************/

	/*********************************          GetColumnAndRowInfo 관련 Start            **************************************/

	//rows and columns 배열 받아오기
	$.fn.GetRowsAndColumnsLocationArray = function (canvas) {
		//console.log('GetRowsAndColumnsLocationArray');
		var arrY = [], arrX = [];
		arrY.push(0);
		arrX.push(0);

		var rcnt = canvas.find('div[ot="lEditorGridLine"][gridtype="r"]').length + 1;
		var ccnt = canvas.find('div[ot="lEditorGridLine"][gridtype="c"]').length + 1;

		canvas.find('div[ot="lEditorGridLine"]').each(function (idx)
		{
			if ($(this).attr('gridtype') == 'r')
				arrY.push(Math.floor(Number($(this).attr('loc'))));
			else
				arrX.push(Math.floor(Number($(this).attr('loc'))));
		});
		arrY.push(canvas.height());
		arrX.push(canvas.width());

		return { arrX: arrX, arrY: arrY };
	}

	//Layer 별 좌표 출력
	$.fn.GetLayerLocationInfoFromColumnAndRows = function (tablePosi, rcloc) {
		//console.log('GetLayerLocationInfoFromColumnAndRows');
		var arrX = rcloc.arrX;
		var arrY = rcloc.arrY;
		var ret = { r: 0, c: 0, rspan: 0, cspan: 0, w: 0, h: 0, l: 0, t: 0, isVal: false };

		if (Object.keys(tablePosi).length == 4)
		{
			ret.isVal = true;
			ret.r = tablePosi.row1;
			ret.c = tablePosi.col1;
			ret.rspan = tablePosi.row2 - tablePosi.row1 + 1;
			ret.cspan = tablePosi.col2 - tablePosi.col1 + 1;
			ret.w = arrX[tablePosi.col2 + 1] - arrX[tablePosi.col1] - (ret.c == 0 ? 1 : 2) - (ret.c + ret.cspan - 1 == arrX.length - 2 ? 1 : 0);
			ret.h = arrY[tablePosi.row2 + 1] - arrY[tablePosi.row1] - (ret.r == 0 ? 1 : 2) - (ret.r + ret.rspan - 1 == arrY.length - 2 ? 1 : 0);
			ret.l = arrX[tablePosi.col1] + (ret.c == 0 ? 1 : 2);
			ret.t = arrY[tablePosi.row1] + (ret.r == 0 ? 1 : 2);
		}
		return ret;
	}
	/*********************************          GetColumnAndRowInfo 관련 End            **************************************/

	/*********************************          MakeNewLayer 관련 Start            **************************************/

	$.fn.GetGuid = function () {
		//console.log('GetGuid');
		function s4() {
			return Math.floor((1 + Math.random()) * 0x10000)
			  .toString(16)
			  .substring(1);
		}
		return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
		  s4() + '-' + s4() + s4() + s4();
	}

	//Layer 선택
	$.fn.LayerSelectedChangeEH = function (e) {
		//console.log('LayerSelectedChangeEH');
		$.fn.MakeResizeHandleLayer($(e.currentTarget));
		$.fn.LayerListSelectedChangeByLayer($(e.currentTarget));
		$.fn.ChangeContentList($(e.currentTarget).attr('guid'));

	}
	/*********************************          MakeNewLayer 관련 End            **************************************/

	/*********************************          BindLayerListByPage 관련 Start            **************************************/
	//Side Layer list 선택시 메인 Layer 선택
	$.fn.LayerListSelectedChangeEH = function (e) {
		//console.log('LayerListSelectedChangeEH');
		var editor = $('#contentEditor');
		var canvas = $('#CanvasDiv');
		var area = $('#lEditorArea');

		var layer = $(e.currentTarget);
		var container = layer.parent();
		var item = container.find('div[sel="true"]').attr('sel', 'false').css('border', '0px dotted #17b293');
		item.css('background', item.attr('colorbackup'));
		layer.attr('sel', 'true').css('border', '2px solid #17b293');
		var oldTarget = canvas.find('div[guid="' + item.attr('tlayer') + '"]');
		var target = canvas.find('div[guid="' + layer.attr('tlayer') + '"]');
		$.fn.MakeResizeHandleLayer(target);
		$.fn.ChangeContentList($(e.currentTarget).attr('tlayer'));
	}

	//Layer Delete
	$.fn.LayerDeleteConfirm = function (e) {
		//console.log('LayerDeleteConfirm');
		var canvas = $('#CanvasDiv');
		var area = $('#lEditorArea');

		var guid = $(e.currentTarget).parent().attr('tlayer');
		canvas.find('div[guid="' + guid + '"]').remove();
		$(e.currentTarget).parent().remove();
		$.fn.LayerSeletionClear();
	}

	//하단 Layer list 순서 변경
	$.fn.LayerOrderChangeEH = function (e, ui) {
		//console.log('LayerOrderChangeEH');
		var index = ui.item.index();
		var list = $('#lList');
		var container = list.children('div .layoutContainer');

		var canvas = $('#CanvasDiv');
		var area = $('#lEditorArea');
		var moveLayer = $($(e.target).find('div[class="lEditorLayerlistItem"]')[index]);

		var layer = canvas.find('div[guid="' + moveLayer.attr('tlayer') + '"]');
		layer.remove();

		if (index == 0) {
			layer.on('mousedown', $.fn.LayerSelectedChangeEH)
			 .prependTo(canvas);
		}
		else {
			var target = canvas.find('.lEditorLayer')[Number(index)-1];
			layer.on('mousedown', $.fn.LayerSelectedChangeEH)
			 .insertAfter($(target));
		}
	}

	/*********************************          BindLayerListByPage 관련 End            **************************************/

	/*********************************          MakeResizeHandleLayer 관련 Start            **************************************/
	//layer size line 눌러진 상태
	$.fn.ResizeHandlerMouseDownEH = function (e)
	{
		//console.log('ResizeHandlerMouseDownEH');
		if (e.target == e.currentTarget)
		{
			var canvas = $('#CanvasDiv');
			var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas); // rows and columns 배열로 받아오기
			var nowloc = $.fn.GetRowAndColumnInfoByOnePosition(canvas, e, rcloc); // 좌표 반환

			sposition = {
				x: Math.round(e.clientX - canvas.offset().left),
				y: Math.round(e.clientY - canvas.offset().top),
				sr: nowloc.row1,
				sc: nowloc.col1,
				st: $(e.currentTarget).offset().top - canvas.offset().top,
				sl: $(e.currentTarget).offset().left - canvas.offset().left
			};
			$.fn.AdjustPostionByScroll(sposition);
			$(document).on('mousemove', $.fn.ResizeHandleMouseMoveEH);
			$(document).on('mouseup', $.fn.ResizeHandleMouseUpEH);
		}
	}

	$.fn.LayerEditorResizeHandleMouseDownEH = function(e) {
		//console.log('LayerEditorResizeHandleMouseDownEH');
		if (e.target == e.currentTarget) {
			var canvas = $('#CanvasDiv');
			var rhandler = canvas.find('div[ot="lEditorResizeHandle"]');
			sposition = {
				x: Math.round(e.clientX - canvas.offset().left),
				y: Math.round(e.clientY - canvas.offset().top),
				w: rhandler.width(),
				h: rhandler.height(),
				tp: $(e.currentTarget).css('cursor')
			};

			$.fn.AdjustPostionByScroll(sposition);

			$(document).on('mousemove', $.fn.LayerEditorResizeHandleMouseMoveEH);
			$(document).on('mouseup', $.fn.LayerEditorResizeHandleMouseUpEH);
		}
	}

	/*********************************          MakeResizeHandleLayer 관련 End            **************************************/

	/*********************************          LayerSelectedChangeEH 관련 Start            **************************************/
	//메인 Layer 선택시 Side Layer list에서 자동 선택
	$.fn.LayerListSelectedChangeByLayer = function (layer) {
		//console.log('LayerListSelectedChangeByLayer');
		var canvas = $('#CanvasDiv');
		var list = $('#lList');
		var container = list.children('div .layoutContainer');

		var item = container.find('div[sel="true"]').attr('sel', 'false').css('border', '0px dotted #17b293');
		item.css('background', item.attr('colorbackup'));

		var oldTarget = canvas.find('div[guid="' + item.attr('tlayer') + '"]');
		container.find('div[tlayer="' + layer.attr('guid') + '"]')
				 .attr('sel', 'true').css('border', '2px solid #17b293');
	}

	/*********************************          LayerSelectedChangeEH 관련 End            **************************************/

	/*********************************          ResizeHandlerMouseDownEH 관련 Start            **************************************/
	 //좌표 반환
	$.fn.GetRowAndColumnInfoByOnePosition = function (canvas, e, rcloc) {
		//console.log('GetRowAndColumnInfoByOnePosition');
		var arrY = rcloc.arrY;
		var arrX = rcloc.arrX;
		var nposition = {
			x: Math.round(e.clientX - canvas.offset().left),
			y: Math.round(e.clientY - canvas.offset().top)
		};
		$.fn.AdjustPostionByScroll(nposition);
		var tablePosi = {};
		for (var i = 0; i < arrY.length - 1; ++i)
		{
			if (arrY[i] <= nposition.y && nposition.y < arrY[i + 1])
				tablePosi.row1 = i;
		}

		for (var i = 0; i < arrX.length - 1; ++i)
		{
			if (arrX[i] <= nposition.x && nposition.x < arrX[i + 1])
				tablePosi.col1 = i;
		}

		return tablePosi;
	}

	 // layer size line 드래그
	$.fn.ResizeHandleMouseMoveEH = function (e) {
		//console.log('ResizeHandleMouseMoveEH');
		var canvas = $('#CanvasDiv');
        var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas);// rows and columns 배열로 받아오기
        var nowloc = $.fn.GetRowAndColumnInfoByOnePosition(canvas, e, rcloc);// 좌표 반환

		var layer = canvas.find('div[class="lEditorLayer"][sel="true"]');
		var pos = $(layer).attr('rc').split('_');
		var tablePosi = {
			row1: Number(pos[0]), col1: Number(pos[1]), row2: Number(pos[4]), col2: Number(pos[5]), rspan: Number(pos[2]), cspan: Number(pos[3])
		};

		var movePosi = {
			row1: tablePosi.row1 + nowloc.row1 - sposition.sr,
			row2: tablePosi.row1 + nowloc.row1 - sposition.sr + tablePosi.rspan - 1,
			col1: tablePosi.col1 + nowloc.col1 - sposition.sc,
			col2: tablePosi.col1 + nowloc.col1 - sposition.sc + tablePosi.cspan - 1,
		};

		if ((movePosi.row1 >= 0 && movePosi.row2 < rcloc.arrY.length - 1) && (movePosi.col1 >= 0 && movePosi.col2 < rcloc.arrX.length - 1))
		{
			var t = rcloc.arrY[movePosi.row1] + (movePosi.row1 == 0 ? 1 : 2);
			var l = rcloc.arrX[movePosi.col1] + (movePosi.col1 == 0 ? 1 : 2);
			var w = rcloc.arrX[movePosi.col2 + 1] - rcloc.arrX[movePosi.col1] - (movePosi.col1 == 0 ? 1 : 2) - (movePosi.col1 + pos[3] - 1 == rcloc.arrX.length - 2 ? 1 : 0);
			var h = rcloc.arrY[movePosi.row2 + 1] - rcloc.arrY[movePosi.row1] - (movePosi.row1 == 0 ? 1 : 2) - (movePosi.row1 + pos[2] - 1 == rcloc.arrY.length - 2 ? 1 : 0);
			var handle = canvas.find('div[ot="lEditorResizeHandle"]').css('top', t).css('left', l).css('width', w).css('height', h);
			handle.attr('rc', movePosi.row1.toString() + '_' + movePosi.col1.toString() + '_' + pos[2] + '_' + pos[3] + '_'
										+ movePosi.row2.toString() + '_' + movePosi.col2.toString());
		}
	}

	//layer resize 완료
	$.fn.ResizeHandleMouseUpEH = function (e)
	{
		//console.log('ResizeHandleMouseUpEH');
		var canvas = $('#CanvasDiv');
		var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas);
		var nowloc = $.fn.GetRowAndColumnInfoByOnePosition(canvas, e, rcloc);
		var rhandle = canvas.find('div[ot="lEditorResizeHandle"]');
		var layer = canvas.find('div[class="lEditorLayer"][sel="true"]');
		layer.attr('rc', $(rhandle).attr('rc'));
		layer.css('left', rhandle.css('left')).css('top', rhandle.css('top')).css('width', rhandle.css('width')).css('height', rhandle.css('height'));
		layer.fitText(.11);
		$(document).off('mousemove', $.fn.ResizeHandleMouseMoveEH);
		$(document).off('mouseup', $.fn.ResizeHandleMouseUpEH);
	}

	/*********************************          ResizeHandlerMouseDownEH 관련 End            **************************************/

	/*********************************          LayerEditorResizeHandleMouseDownEH 관련 Start            **************************************/

	//Content Layer 드래그
	$.fn.LayerEditorResizeHandleMouseMoveEH = function (e) {
		//console.log('LayerEditorResizeHandleMouseMoveEH');
		var canvas = $('#CanvasDiv');
		var rhandler = canvas.find('div[ot="lEditorResizeHandle"]');
		var nposition = {
			x: Math.round(e.clientX - canvas.offset().left),
			y: Math.round(e.clientY - canvas.offset().top)
		};

		$.fn.AdjustPostionByScroll(nposition);
		if (sposition.tp.indexOf('e-') >= 0)
			rhandler.css('width', sposition.w + nposition.x - sposition.x);
		if (sposition.tp.indexOf('s') == 0)
			rhandler.css('height', sposition.h + nposition.y - sposition.y);
		if (sposition.tp.indexOf('w-') >= 0)
			rhandler.css('left', nposition.x).css('width', sposition.w + sposition.x - nposition.x);
		if (sposition.tp.indexOf('n') == 0)
			rhandler.css('top', nposition.y).css('height', sposition.h + sposition.y - nposition.y);
	}

		//Content Layer 드래그 마무리
	$.fn.LayerEditorResizeHandleMouseUpEH = function (e) {
		//console.log('LayerEditorResizeHandleMouseUpEH');
		var canvas = $('#CanvasDiv');
		var layer = canvas.find('div[class="lEditorLayer"][sel="true"]');

		var rcloc = $.fn.GetRowsAndColumnsLocationArray(canvas);
		var nowloc = $.fn.GetRowAndColumnInfoByOnePosition(canvas, e, rcloc);

		var pos = $(layer).attr('rc').split('_');
		var tablePosi = { row1: Number(pos[0]), col1: Number(pos[1]), row2: Number(pos[4]), col2: Number(pos[5]) };
		var newPosi = { row1: tablePosi.row1, row2: tablePosi.row2, col1: tablePosi.col1, col2: tablePosi.col2 };

		if (sposition.tp.indexOf('e-') >= 0)
			newPosi.col2 = nowloc.col1;
		if (sposition.tp.indexOf('s') == 0)
			newPosi.row2 = nowloc.row1;
		if (sposition.tp.indexOf('w-') >= 0)
			newPosi.col1 = nowloc.col1;
		if (sposition.tp.indexOf('n') == 0)
			newPosi.row1 = nowloc.row1;

        //Content Layer가 영역 밖으로 나갈시 기존 영역으로 복귀
        if (nowloc.row1 == undefined || nowloc.row1 == null || nowloc.col1 == undefined || nowloc.col1 == null) {
        	newPosi = { row1: tablePosi.row1, row2: tablePosi.row2, col1: tablePosi.col1, col2: tablePosi.col2 };
        }

		var layerLoc = $.fn.GetLayerLocationInfoFromColumnAndRows(newPosi, rcloc);
		$(layer).attr('rc', layerLoc.r.toString() + '_' + layerLoc.c.toString() + '_' + layerLoc.rspan.toString() + '_' + layerLoc.cspan.toString()
									+ '_' + (layerLoc.r + layerLoc.rspan - 1).toString() + '_' + (layerLoc.c + layerLoc.cspan - 1).toString())
         .css('top', layerLoc.t).css('left', layerLoc.l).css('width', layerLoc.w).css('height', layerLoc.h);
		$.fn.MakeResizeHandleLayer(layer);
		$(layer).fitText(.11);
		$(document).off('mousemove', $.fn.LayerEditorResizeHandleMouseMoveEH);
		$(document).off('mouseup', $.fn.LayerEditorResizeHandleMouseUpEH);
	}

	/*********************************          LayerEditorResizeHandleMouseDownEH 관련 End            **************************************/




	/*********************************          Content List 관련 Start            **************************************/
	$.fn.AddNewContent = function(e) {
		//console.log('AddNewContent');
		var canvas = $('#CanvasDiv');
		var cdiv = $('#contentDiv');
		var layerGuide = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('guid');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+layerGuide+'"]');
		var layer = canvas.find("div[class='lEditorLayer'][sel='true']");

		var totPtime = 0;
		guidelist.children().each(function (idx) {
			totPtime = totPtime+Number($(this).attr('ptime'));
        });

		totPtime = Math.floor(totPtime/1000) * 1000;
		if(totPtime >= 86399000){
			$.modalCommon.alertView('최대 시간(23:59:59) 이상일 경우 추가 불가능합니다.');
			return;
		}

		var ret = {};

		ret.isPossable = layer.length > 0;

		if (!ret.isPossable) {
			$.modalCommon.alertView('Layer를 선택해주세요.');
			return;
		}

		var ext = ['PNG', 'JPEG', 'JPG', 'MP4'];

		$.showUploadBox({
			url: '/Upload/View/Screen/',
			width: 600,
			height: 400,
			title: '컨텐츠 등록',
			ext: ext,
			Finished: FileUploadFinishedEH
		});

	   function FileUploadFinishedEH(data){
		   if(data == undefined){
		   }else{
			   var canvas = $('#CanvasDiv');
			   var cdiv = $('#contentDiv');
			   var layerGuide = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('guid');
			   var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+layerGuide+'"]');
			   var totPtime = 0;
			   var jsonData = $.FileUpload.Data;
			   if (jsonData.length > 0) {
				   guidelist.children().each(function (idx) {
						totPtime += Number($(this).attr('ptime'));
			        });
				   for(var i = 0; i< jsonData.length; i++) {
					    totPtime += Number(jsonData[i].playTime);
						totPtime = Math.floor(totPtime/1000) * 1000;
						if(totPtime > 86399000){
							$.modalCommon.alertView('최대 시간(23:59:59) 초과일 경우 추가 불가능합니다.');
							return;
						}

					   var arr = [
					                {
					                	"fileContentIdx" : jsonData[i].fileContentIdx,
					        			"fileName" : jsonData[i].fileName,
					        			"fileSaveName" : jsonData[i].fileSaveName,
					        			"savePath" : jsonData[i].savePath,
					        			"thumbnailPath" : jsonData[i].thumbnailPath,
					        			"fileSize" : jsonData[i].fileSize,
					        			"checkSum" : jsonData[i].checkSum,
					        			"playTime" : jsonData[i].playTime
					                }
					            ];
						$.fn.AddContentList(arr);
				   }
			   }
		   }
	   }
	}

	//Content 등록, 정보 받아오기
	$.fn.AddContentList = function(conList) {
		//console.log('AddContentList');
		var canvas = $('#CanvasDiv');
		var cdiv = $('#contentDiv');

		var layerGuide = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('guid');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+layerGuide+'"]');

		if (guidelist.length > 0) {
			var list = guidelist;
			if (guidelist.attr('dis') != 'true') {
				var list = guidelist.appendTo(cdiv);
				list.css('display', 'inline-block').attr('dis', 'true').css('height', $('#contentItemList').attr('listH'));
				cdiv.css(list.height());
			}
		}
		else {
			var list =  $('<div>').appendTo(cdiv)
								  .attr('id', 'contentItemList')
								  .attr('ot', 'contentItemList')
								  .attr('tlayer', layerGuide)
								  .attr('dis', 'true')
								  .addClass('contentItemList')
								  .css('width', '100%');
			list.sortable({});
		}

		var layer = canvas.find("div[class='lEditorLayer'][sel='true'][tlayer='"+layerGuide+"']");

		var items = list.find('div[ot="contentItem"]');
		var maxlen = $('#contentList').width() -98;
		var maxPlayTime = 0;

		items.each(function (idx) {
			maxPlayTime = Math.max(maxPlayTime, Number($(this).attr('ptime')));
		});

		for (var i = 0; i < conList.length; i++) {
			maxPlayTime = Math.max(maxPlayTime, Number(conList[i].playTime));
		}

		items.each(function (idx) {
			$(this).children('div[ot="contentItemLength"]').css('width', maxlen * Number($(this).attr('ptime')) / maxPlayTime);
		});

		for (var i = 0; i < conList.length; ++i) {
			var playtime = Number(conList[i].playTime);

			var time = new Date(0, 0, 0, 0, 0, 0, playtime);

			var min = time.getDay() * 24 * 60 + time.getHours() * 60 + time.getMinutes();
			min = min.toString().length > 1 ? min.toString() : "0" + min.toString();
			var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();

			cdiv.css('height', cdiv.height()+($('#contentList').height()/5)+5);
			list.css('height', list.height()+($('#contentList').height()/5)+5);
			list.attr('listH', list.height());

			var item = $("<div>").attr("ot", "contentItem")
					  .addClass("contentItem")
					  .attr("ptime", playtime.toString())
					  .attr("contid", conList[i].fileContentIdx)
					  .attr("curl", conList[i].savePath)
					  .css('height', $('#contentList').height() / 5)
					  .appendTo(list);

			var divImg = $("<div>").addClass("contentItemImage")
						  .attr("ot", "contentItemImage")
						  .appendTo(item);

			var contextPath = window.location.pathname.substring(0,window.location.pathname.indexOf("/",2));
			$("<img>").attr("src", conList[i].thumbnailPath)
					  .attr("ot", "contentImage")
					  .on("load", function (e) {
						  var w = $(e.currentTarget).width();
					  	  var h = $(e.currentTarget).height();
					  	  var re = h / w;
						  if(w > h+5) {
							  var max = 110;
							  $(e.currentTarget).css("width", max).css("height", max * re)
								     			.css("margin-top", (divImg.height() - max * re) / 2)
						  			    		.css("margin-left", (divImg.width()-max)/2 );

						  }
						  else {
							  var max = 85;
							  $(e.currentTarget).css("width", max / re).css("height", max)
							  					.css("margin-top", (divImg.height() - max) / 2)
									    		.css("margin-left", (divImg.width()-(max / re))/2);
						  }
					  })
					  .appendTo(divImg);

			var sideDiv = $('<div>').appendTo(item)
									.addClass('pull-right')
									.addClass('sideDiv');

			var removeBtn = $('<button>').css('width', 17).css('height', 17)
										 .attr('ot', 'contentItemDelete')
										 .css('text-align', 'center')
										 .css('z-index', '999')
										 .addClass('contentItemDelete')
										 .css('background-image', 'url(/Content/images/details_close.png)')
										 .on('click', $.fn.DeleteContentList)
										 .appendTo(sideDiv);

			var color = Please.make_color({ scheme_type: 'mono' });

			var stDiv = $('<div>').appendTo(sideDiv)
					  .attr("ot", "contentItemName")
                      .text(conList[i].fileName)
					  .attr("title", conList[i].fileName)
					  .css('background', color)
					  .css('color', '#FFFFFF')
					  .css('line-height', '35px')
					  .css('width', '100%')
					  .css('text-align', 'center')
					  .css('height', '40%')
					  .css("text-overflow", "ellipsis")
					  .css("overflow", "hidden")
					  .css("white-space", "nowrap")
					  .css('margin-top', '-20px');


			var sbDiv = $('<div>').addClass('sbDiv')
								  .appendTo(sideDiv)
					  			  .css('width', '100%')
					  			  .css('height', '60%');

			$('<div>').appendTo(sbDiv)
					   .text('PlayTime')
					   .addClass('playTime')

			$('<input>').attr('type', 'text')
						.attr('ot', 'contentTimeInput')
						.attr('time', 'min')
						.addClass('contentTimeInput')
						.val(min)
						.on('change',$.fn.ChangePtime)
						.attr('maxlength','4')
						.appendTo(sbDiv);

			$('<span>').attr('type', 'text')
						.css('display', 'inline-block')
						.css('text-align', 'center')
						.text(':')
						.appendTo(sbDiv);

			$('<input>').attr('type', 'text')
						.attr('ot', 'contentTimeInput')
						.attr('time', 'sec')
						.addClass('contentTimeInput')
						.val(sec)
						.attr('maxlength','2')
						.on('change',$.fn.ChangePtime)
						.appendTo(sbDiv);
		};

		$.fn.ChangeTotalPlayTime(layerGuide);
	}

	//Layer별 content list
	$.fn.ChangeContentList = function(val) {
		//console.log('ChangeContentList');
		var guid = val;

		var canvas = $('#CanvasDiv');
		var cdiv = $('#contentDiv');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+guid+'"]');

		if (guidelist.length > 0) {
			$('.contentItemList').css('display', 'none').attr('dis', 'false');
			cdiv.css('height', guidelist.attr('listH'));
			guidelist.css('display', 'inline-block').css('height', guidelist.attr('listH')).attr('dis', 'true');

			var item = guidelist.children('.contentItem');
			for (var i = 0; i < item.length; i++) {
				var image = item.eq(i).children('div[class="contentItemImage"][ot="contentItemImage"]');
				var realImage = image.children('img');

				var w = realImage.width();
		  	  	var h = realImage.height();
	  	  		/*realImage.css("margin-top", (image.height() - h) / 2)
					     .css("margin-left", (image.width()-w)/2 );*/
	  	  		var re = h / w;
	  	  		if(w > h+5) {
	  	  			var max = 110;
	  	  			realImage.css("width", max).css("height", max * re)
	  	  							  .css("margin-top", (image.height() - max * re) / 2)
	  	  							  .css("margin-left", (image.width()-max)/2 );

	  	  		}
	  	  		else {
	  	  			var max = 85;
	  	  			realImage.css("width", max / re).css("height", max)
	  	  							  .css("margin-top", (image.height() - max) / 2)
	  	  							  .css("margin-left", (image.width()-(max / re))/2);
	  	  		}
			}
		}
		else {
			$('.contentItemList').css('display', 'none').attr('dis', 'false');
			cdiv.css('height', 0);
		}

	}

	//content list delete
	$.fn.DeleteContentList = function (e) {
		//console.log('DeleteContentList');
		$(e.currentTarget).parent().parent().remove();
		var cdiv = $('#contentDiv');
		var guidelist = cdiv.find('div[ot="contentItemList"][dis="true"]');

		var height = cdiv.height()-104.6;

		cdiv.css('height', height);
		guidelist.css('height', height);
		guidelist.attr('listH', height);
		$.fn.ChangeTotalPlayTime(guidelist.attr('tlayer'));

	}

	//play time change
	$.fn.ChangeTotalPlayTime = function(guid) {
		//console.log('ChangeTotalPlayTime');
		var layer = $('.layoutContainer').find('div[tlayer="'+guid+'"]');

		var cdiv = $('#contentDiv');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+guid+'"]');
		var ptime = 0;

		guidelist.children().each(function (idx) {
            ptime = ptime+Number($(this).attr('ptime'));
        });

		var time = new Date(0, 0, 0, 0, 0, 0, ptime);
		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();

		var ttpt = layer.find('div[id="totalPlayTime"]').text(hour + ":" + min + ":" + sec);
	}

	//ptime change
	$.fn.ChangePtime = function(e) {
		//console.log('ChangePtime');
		var sec, min;

		if ($(e.currentTarget).attr('time') =='sec') {
			sec = $(e.currentTarget);
			min = sec.parent().children('input[time="min"]');
		}
		else {
			min = $(e.currentTarget);
			sec = min.parent().children('input[time="sec"]');
		}

		if (isNaN(sec.val()) || isNaN(min.val())) {
			$.modalCommon.alertView('숫자만 입력하세요.');
			if ($(e.currentTarget).attr('time') =='min') {
				$(e.currentTarget).val('00');
			}
			else {
				$(e.currentTarget).val('01');
			}
		}
		else if(Number(sec.val()) <= 0 && Number(min.val()) <= 0) {
			$.modalCommon.alertView('시간이 1초 이상으로 등록하세요.');
			if ($(e.currentTarget).attr('time') =='min') {
				$(e.currentTarget).val('00');
			}
			else {
				$(e.currentTarget).val('01');
			}
		}
		else {
			var secNum = Number(sec.val());
			var minNum = Number(min.val());

			if (secNum >= 60) {
				var num = parseInt(secNum/60);
				secNum = secNum - (60*num);
				minNum = Number(minNum) + (1*num);

				if (minNum < 10) {
					minNum = '0'+minNum;
				}
				if (secNum == 0) {
					secNum = '00';
				}
				if (secNum < 10) {
					secNum = '0' + secNum;
				}

				sec.val(secNum);
				min.val(minNum);
			}
			else {
				if (secNum == 0) {
					secNum = '00';
				}else if (secNum < 10) {
					secNum = '0' + secNum;
				}
				if (minNum < 10) {
					minNum = '0'+minNum;
				}
				if (minNum < 1) {
					minNum = '00';
				}
				if (minNum == 0) {
					minNum = '00';
				}


				sec.val(secNum);
				min.val(minNum);
			}

		}


		var ptime = $(e.currentTarget).parent().parent().parent();
		//console.log('ptime:'+(Number((min.val()*60))+Number((sec.val())))*1000);
		ptime.attr('ptime', (Number((min.val()*60))+Number((sec.val())))*1000 );


		var guid = ptime.parent().attr('tlayer');

		var cdiv = $('#contentDiv');
		var guidelist = cdiv.find('div[ot="contentItemList"][tlayer="'+guid+'"]');
		var totPtime = 0;
		var otherPtime = 0;
		//console.log(guidelist);
		guidelist.children().each(function (idx) {
			if($(e.currentTarget).parent().parent().parent().attr("contid") != $(this).attr("contid")	)
				otherPtime = otherPtime+Number($(this).attr('ptime'));
			if($(this).attr('ptime') != null){
				//console.log($(this).attr('ptime'));
				totPtime = totPtime+Number($(this).attr('ptime'));
			}

        });

		//console.log('totPtime:'+totPtime);


		totPtime = Math.floor(totPtime/1000) * 1000;
		if(totPtime > 86399000){
			$.modalCommon.alertView('최대시간(23:59:59)를 초과하였습니다.');
			var longTotTime = 86399000-otherPtime;

			var time = new Date(0, 0, 0, 0, 0, 0, longTotTime);
			var min = (time.getHours() * 60) +time.getMinutes();
			min.toString().length > 1 ? min.toString() : "0" + min.toString();
			var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
			//console.log($(e.currentTarget).val());
			//console.log($(e.currentTarget).attr('time'));

			if ($(e.currentTarget).attr('time') =='min') {
				$(e.currentTarget).val(min);
				$(e.currentTarget).parent().find('[time="sec"]').val(sec);
			}
			if ($(e.currentTarget).attr('time') =='sec') {
				$(e.currentTarget).val(sec);
				$(e.currentTarget).parent().find('[time="min"]').val(min);
			}



			ptime.attr('ptime', (Number((min*60))+Number((sec)))*1000 );

		}


		$.fn.ChangeTotalPlayTime(ptime.parent().attr('tlayer'));

	}

	/*********************************          Content List 관련 End            **************************************/

	/*********************************          불러오기 Start            **************************************/

	$.fn.Load = function(data) {
		//console.log('Load');
		$('#contentEditor').attr('screenIdx', data.screenIdx);
		$('#screenName').val(data.screenName);
		$('#resolutionX').val(data.resolutionX);
		$('#resolutionY').val(data.resolutionY);
		$('#rowCnt').val(data.rowCnt);
		$('#colCnt').val(data.colCnt);

		var canvas = $('#CanvasDiv');

		for (var i = 0; i < data.layer.length; i++) {
			var layer = data.layer[i];
			var rc = {
					r : layer.startRow,
					c : layer.startCol,
					rspan : layer.rowSpan,
					cspan : layer.colSpan
			};

			var newLayer = $.fn.MakeNewLayer(rc, canvas); //layer 생성
			$.fn.BindLayerListByPage(null); //layer list
			$.fn.ChangeResolution();
//			$.fn.ChangeRowAndCol();
			$.fn.MakeResizeHandleLayer(newLayer);

			if (data.layer.length > 1) {
				$('#lList').children().find('div[class="lEditorLayerlistItem"]').attr('sel', 'false').css('border', '0px dotted #17b293');
				$('#lList').children().find('div[class="lEditorLayerlistItem"]').eq(data.layer.length-1).attr('sel', 'true').css('border', '2px solid #17b293');
			}

			for (var j = 0; j < layer.content.length; j++) {
				var content = layer.content[j];
				$.fn.AddContentList([content]);
			}
		}
		//$.fn.ChangeRowAndCol();
		// 화면 재구성으로 변경
		$.fn.ChangeResolution();
		var guid = canvas.find('div[class="lEditorLayer"][sel="true"]').attr('guid');
		$.fn.ChangeContentList(guid);
	}
	/*********************************          불러오기 End            **************************************/
})($);