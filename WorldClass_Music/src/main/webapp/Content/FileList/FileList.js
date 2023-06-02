$.FileList = {};
$.fn.FileList = function (opt) {
	$.FileList.options = {
		viewCycle: false
	};

	if (opt != null)
		$.extend($.FileList.options, opt);

	return this.each(function () {
		$(this).empty();
		$.FileList.MakeList($(this));
	});
};

$.fn.SaveFileList = function () {
	var table = $(this).find('table.FileListTable');
	var ret = [];

	table.children('tr').each(function (idx) {
		var file = {};
		var input = $(this).children('td').eq(1).children('input');

		file.FI_INDEX = $(this).attr('idx');
		file.FI_PLAYTIME = $.FileList.GetSecondFromTimeString(input.val());

		if ($.FileList.options.viewCycle) {
			input = $(this).children('td').eq(2).children('input').eq(0);
			file.FI_COUNT = input.val();

			input = $(this).children('td').eq(2).children('input').eq(1);
			file.FI_CYCLE = input.val();

			input = $(this).children('td').eq(3).children('input').eq(0);
			file.FI_START = input.val();

		}
		ret.push(file);
	});

	return ret;
}

$.fn.LoadFileList = function (data) {
	return this.each(function () {
		var table = $(this).find('table.FileListTable');

		for (var i = 0; i < data.length; ++i) {
			var tr = $('<tr>').attr('idx', data[i].FI_INDEX).attr('issel', 'false').appendTo(table);
			
			tr.on('click', function () {
				var table = $(this).parent();

				table.find('tr').attr('issel', 'false');
				$(this).attr('issel', 'true');
			});

			var td = $('<td>').text(data[i].FI_ORINAME).attr('type', 'name').appendTo(tr);

			td = $('<td>').attr('type', 'playtime').appendTo(tr);
			$('<input type="text">').mask('00:A0:A0', {
				translation: {
					'A': {
						pattern: /[0-5]/, optional: true
					}
				}
			}).appendTo(td).val($.FileList.GetTimeString(data[i].FI_PLAYTIME));

			if ($.FileList.options.viewCycle) {
				td = $('<td>').attr('type', 'cycle').appendTo(tr);
				$('<input type="text">').mask('00').appendTo(td).val(data[i].FI_COUNT);
				$('<span>').text('/').appendTo(td);
				$('<input type="text">').mask('00').appendTo(td).val(data[i].FI_CYCLE);

				td = $('<td>').attr('type', 'start').appendTo(tr);
				$('<input type="text">').mask('00').appendTo(td).val(data[i].FI_START);
			}

			td = $('<td>').attr('type', 'more').appendTo(tr);
			var a = $('<button>').on('click', $.FileList.ViewFileDetailFInfo)
								.addClass('btn btn-default')
								.css({ height: 22, padding: '0px 6px 0px 6px' })
								.text('More')
								.appendTo(td);
		}

		if (table.children('tr').length > 1 && $.FileList.options.viewCycle) {
			//alert('1개이상의 파일이 업로드 되었습니다.');
		}
	});
};

$.FileList.SelectedFileIndex = 0;

$.FileList.ViewFileDetailFInfo = function (e) {
	var tr = $(e.currentTarget).parent().parent();
	$.FileList.SelectedFileIndex = tr.attr('idx');
	$.modalCommon.loadView('파일상세정보', '/Common/FileDetail/');
};

$.FileList.GetSecondFromTimeString = function (str) {
	var time = str.split(':');
	var d = new Date(0, 0, 0, Number(time[0]), Number(time[1]), Number(time[2]), 0);

	return d.getHours() * 3600 + d.getMinutes() * 60 + d.getSeconds();
};

$.FileList.GetTimeString = function (sec) {
	var d = new Date(0, 0, 0, 0, 0, sec, 0);
	var func = $.FileList.GetPadZeroString;

	return func(d.getHours(), 2) + ':' + func(d.getMinutes(), 2) + ':' + func(d.getSeconds(), 2);
};

$.FileList.GetPadZeroString = function (num, len) {
	var ret = num.toString();

	for (; (ret.length < len) ;)
		ret = '0' + ret;

	return ret;
};

$.FileList.ItemMove = function (e) {
	if (e.currentTarget == e.target) {
		var move = Number($(this).attr('move'));
		var list = $(this).parent().parent().parent();
		var table = list.find('table.FileListTable');
		var selitem = table.find('tr[issel="true"]');

		if (selitem.length > 0) {
			var index = table.children('tr').index(selitem);

			if (move > 0) {
				var len = table.children('tr').length;

				if (index + move <= len - 1) {
					selitem.remove();
					selitem.insertAfter(table.children('tr').eq(index)).on('click', function () {
						var table = $(this).parent();
						table.find('tr').attr('issel', 'false');
						$(this).attr('issel', 'true');
					});
				}
			}
			else {
				if (index + move >= 0) {
					selitem.remove();
					selitem.insertBefore(table.children('tr').eq(index + move)).on('click', function () {
						var table = $(this).parent();
						table.find('tr').attr('issel', 'false');
						$(this).attr('issel', 'true');
					});
				}
			}
		}
	}
}

$.FileList.DeleteButtonCallback = function (e) {
	var list = $(e.currentTarget).parent().parent().parent();
	var table = list.find('.FileListTable');
	var tr = table.find('tr[issel="true"]');
	
	if (tr.length > 0) {
		//$.FileList.options.minusClick(tr.attr('idx'));
		tr.remove();
	}
};

$.FileList.MakeList = function (list) {
	list.addClass('FileList').css({ height: $.FileList.options.height });

	var header = $('<div>').addClass('FileListHeader').css({ height: 35 }).appendTo(list);
	var body = $('<div>').addClass('FileListBody').css({ height: list.height() - header.height() }).appendTo(list);
	var btnGroup = $('<div>').addClass('btn-group').appendTo(header);
	var callback = function (e) { 
		ShowUpload();
		return false;
	};

	$('<button>').addClass('btn btn-sm btn-default').appendTo(btnGroup).text('+').on('click', callback);
	$('<button>').addClass('btn btn-sm btn-default').appendTo(btnGroup).text('-').on('click', function (e) { $.FileList.DeleteButtonCallback(e); });
	$('<button>').addClass('btn btn-sm btn-default').attr('move', '-1').appendTo(btnGroup).text('▲').on('click', $.FileList.ItemMove);
	$('<button>').addClass('btn btn-sm btn-default').attr('move', '1').appendTo(btnGroup).text('▼').on('click', $.FileList.ItemMove);

	var table = $('<table>').addClass('FileListHeaderTable').css({ width: '100%' }).appendTo(body);
	var tr = $('<tr>').appendTo(table);

	$('<th>').text('File Name').attr('type', 'name').appendTo(tr);
	$('<th>').text('Play Time').attr('type', 'playtime').appendTo(tr);

	if ($.FileList.options.viewCycle) {
		$('<th>').text('Cnt/Cycle').attr('type', 'cycle').appendTo(tr);
		$('<th>').text('Start').attr('type', 'start').appendTo(tr);
	}

	$('<th>').text('More').attr('type', 'more').appendTo(tr);
	$('<th>').attr('type', 'scroll').appendTo(tr);

	var bodyDiv = $('<div>').addClass('FileListDiv').css({ height: body.height() - 29 }).appendTo(body);
	var table = $('<table>').addClass('FileListTable').css({ width: '100%' }).appendTo(bodyDiv)
}