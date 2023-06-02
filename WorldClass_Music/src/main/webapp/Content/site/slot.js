(function ($) {    
    var Plugin = function ($self, options) {
        this.$self = $self;
        this.options = $.extend({}, $.fn.SlotView.defaults, options);
    };
    var slotArrHtml = [];
    var agencyArrData = [];
    var agencyArrColor = [];

    $.fn.SlotView = function (option) {
        var options = typeof option == 'object' && option;
        var $this = $(this);
        var $plugin = new Plugin($this, options);              
        $.isFunction(options.setup) && options.setup.call(this);        

        return this.each(function () {
            switch ($plugin.options.dataOption) {
                case 'left':
                    break;
                default:
                    $this.children().remove();
                    break;
            }

            if ($plugin.options.slotCnt > 0) {
                $plugin.ui($this, $plugin);
            }
        });
    };

    Plugin.prototype.ui = function ($this, $plugin) {
        var main = $this;
        var layout = '';
        var ul = '';

        for (var i = 0; i < $plugin.options.layoutCnt; i++) {
            var idName = $plugin.options.viewid + '_' + i;

            switch ($plugin.options.dataOption) {
                case 'left':
                    layout = $('<div>').attr('agency', $plugin.options.viewid).addClass('list2').appendTo(main);
                    ul = $('<ul>').attr('id', idName).appendTo(layout);
                    break;
                default:
                    layout = $('<div>').attr('id', idName).addClass('list3').appendTo(main);
                    break;
            }
        }
        $plugin.division($this, $plugin);
    };

    Plugin.prototype.division = function ($this, $plugin) {
        var main = $this;
        var startNum = 1;
        var uiChk = function ($plugin) {
            if ($plugin.options.slotCnt < $plugin.options.layoutCnt * 10) {
                return '1';
            }
            else if ($plugin.options.slotCnt == ($plugin.options.layoutCnt * 10)) {
                return '2';
            }
            else {
                if (pointChk($plugin.options.slotCnt / $plugin.options.layoutCnt)) {
                    return '3';
                }
                else {
                    return '4';
                }
            }
        };
        var pointChk = function (val) {
            var pattern = /^\d+$/;

            if (pattern.test(val)) {
                return true;
            }
            else {
                return false;
            }
        };
        var zeroNum = function ($plugin) {
            return $plugin.options.slotCnt / $plugin.options.layoutCnt;
        };
        var ceilNum = function ($plugin) {
            return Math.ceil($plugin.options.slotCnt / $plugin.options.layoutCnt);
        };
        var htmlAppend = function ($id) {
            if ($plugin.options.dragOption == true && slotArrHtml.length > 0) {
                var $viewHtml = $.parseHTML(slotArrHtml[startNum - 1]);

                $id.append(slotArrHtml[startNum - 1]);
            }
            else {
                switch ($plugin.options.dataOption) {
                    case 'left':
                        var li = $('<li>').attr('id', idName + '_' + startNum).attr('agencyval', $plugin.options.viewid + '_' + startNum)
                                    .attr('seq', startNum).text(startNum).css('cursor', 'move').appendTo($id);
                        break;
                    case 'right':
                        var div = $('<div>').attr('id', idName + '_' + startNum).addClass('box1').attr('seq', startNum).appendTo($id);
                        var span = $('<span>').text(startNum).appendTo(div);
                        var p = $('<p>').appendTo(div);
                        var em = $('<em>').appendTo(div);
                        var a = $('<a>').addClass('off').css('cursor', 'pointer').appendTo(div);
                        break;
                    default:
                        var div = $('<div>').attr('id', idName + '_' + startNum).addClass('box1').attr('seq', startNum).appendTo($id);
                        var span = $('<span>').text(startNum).appendTo(div);
                        var p = $('<p>').appendTo(div);
                        var em = $('<em>').appendTo(div);
                        break;
                }
            }
        };

        switch (uiChk($plugin)) {
            case '3':
                for (var i = 0; i < $plugin.options.layoutCnt; i++) {
                    for (var k = 0; k < zeroNum($plugin) ; k++) {
                        if (startNum > $plugin.options.slotCnt) break;
                        var idName = $plugin.options.viewid + '_' + i;
                        var $id = $('#' + idName);

                        htmlAppend($id);
                        startNum++
                    }
                }
                break;
            case '4':
                var ceilCnt = ceilNum($plugin);

                for (var i = 0; i < $plugin.options.layoutCnt; i++) {
                    var idName = $plugin.options.viewid + '_' + i;
                    var $id = $('#' + idName);

                    for (var k = 0; k < ceilCnt; k++) {
                        if (startNum > $plugin.options.slotCnt) break;

                        htmlAppend($id);
                        startNum++
                    }
                }
                break;
            default:
                for (var i = 0; i < $plugin.options.layoutCnt; i++) {
                    for (var k = 0; k < 10; k++) {
                        if (startNum > $plugin.options.slotCnt) break;
                        var idName = $plugin.options.viewid + '_' + i;
                        var $id = $('#' + idName);

                        htmlAppend($id);
                        startNum++
                    }
                }
                break;
        };
        
        if ($plugin.options.dragOption) {
            switch ($plugin.options.dataOption) {
                case 'left':
                    $plugin.leftDragFn($this, $plugin);                     
                    break;
                case 'right':
                    $plugin.rightDragFn($this, $plugin);
                    break;
            }
        }
        else {
            $plugin.loadData($this, $plugin);
        }
    };

    Plugin.prototype.leftDragFn = function ($this, $plugin) {
        $this.find('li').draggable({
            addClasses: false,
            appendTo: '#slotFrm',
            cursor: 'move',
            helper: 'clone'
        });
        if ($plugin.options.loadDataOption) $plugin.loadData($this, $plugin);
    };

    Plugin.prototype.rightDragFn = function ($this, $plugin) {
        $this.find('.list3').sortable({
            connectWith: '.list3',
            cursor: 'move',
            stop: function (event, ui) {
                var i = 0;
                slotArrHtml = [];

                $this.find('.list3').find('div').each(function () {
                    i++;
                    $(this).each(function () {
                        $(this).children('span').text(i);
                        $(this).attr('seq', i);
                        slotArrHtml.push($(this)[0].outerHTML);
                    });                                                           
                });
                $('#slotList2').html('');
                $plugin.options.loadDataOption = false;
                $plugin.ui($this, $plugin);
                slotArrHtml = [];
            }
        }).disableSelection();

        $this.find('.box1').droppable({
            addClasses: false,
            activeClass: 'listActive',
            accept: ':not(.ui-sortable-helper)',
            drop: function (event, ui) {
                var $leftObj = $('#' + ui.draggable.attr('id'));
                var $rightObj = $('#' + event.target.id);
                var agencyVal = ui.draggable.attr('agencyval');
                var bgName = '';
                
                if ($rightObj.children('a').hasClass('on')) {
                    bgName = $rightObj.children('a').attr('bgname');
                    $('#slotList').find('li[agencyval="' + $rightObj.children('a').attr('agencyval') + '"]').attr('bgname', bgName).css('cursor', 'move')
                                       .draggable('enable').addClass(bgName);
                    $rightObj.children('span').removeClass();
                }

                bgName = $leftObj.attr('bgname');
                $rightObj.attr('agency', agencyVal.split('_')[0]).attr('bgname', $leftObj.attr('bgname'));
                $rightObj.children('span').addClass(bgName);
                $rightObj.children('a').removeClass('off').attr('agencyval', agencyVal).attr('bgname', bgName).addClass('on');
                $rightObj.children('p').text($('#agencyList').jstree().get_selected('id')[0].text);
                $rightObj.children('em').text(ui.draggable.text());
                $leftObj.css('cursor', 'auto').removeClass().draggable('disable');
            }
        });

        $this.find('.box1').on('click', '.on', function () {
            var $rightObj = $(this);
            var $leftObj = $('#slotList').find('li[agencyval="' + $rightObj.attr('agencyval') + '"]');
            var $parent = $rightObj.parent();

            if ($leftObj.length > 0) {
                $leftObj.draggable('enable').css('cursor', 'move').attr('bgname', $rightObj.attr('bgname')).addClass($rightObj.attr('bgname'));
            }
            
            $rightObj.removeClass('on').removeAttr('agencyval').removeAttr('bgname').addClass('off');
            $parent.children('span').removeClass();
            $parent.children('p').text('');
            $parent.children('em').text('');
        });

        if ($plugin.options.loadDataOption) $plugin.loadData($this, $plugin);
    };

    Plugin.prototype.loadData = function ($this, $plugin) {
        var arr = [];
        
        switch ($plugin.options.dataOption) {
            case 'left':                
                $('#slotList2 a').each(function () {
                    var agencyVal = $(this).attr('agencyval');
                    var $right = $(this);

                    $('#slotList li').each(function () {
                        if ($(this).attr('agencyval') == agencyVal) {
                            $(this).css('cursor', 'auto').removeClass().draggable('disable');
                        }
                    });
                });
                bgView($this, $plugin, '');
                break;
            case 'right':
                if ($plugin.options.data.length > 0) {
                    for (var i = 0; i < $plugin.options.data.length; i++) {
                        $this.find('.box1').each(function () {
                            if ($(this).attr('seq') == $plugin.options.data[i].MS_MTSEQ) {
                                var $rightObj = $(this).parent();
                                var agencyVal = $plugin.options.data[i].MS_ACINDEX + '_' + $plugin.options.data[i].MS_ACSEQ;

                                $(this).attr('agency', $plugin.options.data[i].MS_ACINDEX);
                                $(this).children('p').text($plugin.options.data[i].AC_NAME);
                                $(this).children('em').text($plugin.options.data[i].MS_ACSEQ);
                                $(this).children('a').removeClass('off').attr('agencyval', agencyVal).addClass('on');
                                arr.push($plugin.options.data[i].MS_ACINDEX);
                            }
                        });
                    }
                }
                bgView($this, $plugin, arr);
                break;
            default:
                for (var i = 0; i < $plugin.options.data.length; i++) {
                    $this.find('.box1').each(function () {
                        if ($(this).attr('seq') == $plugin.options.data[i].MS_MTSEQ) {
                            $(this).attr('agency', $plugin.options.data[i].MS_ACINDEX);
                            $(this).children('p').text($plugin.options.data[i].AC_NAME);
                            $(this).children('em').text($plugin.options.data[i].MS_ACSEQ);
                            arr.push($plugin.options.data[i].MS_ACINDEX);
                        }
                    });
                }
                bgView($this, $plugin, arr);
                break;
        }
    };

    function bgView($this, $plugin, arr) {
        var bgName = '';
        
        switch ($plugin.options.dataOption) {
            case 'left':
                var agencyid = $('#agencyList').jstree().get_selected('id')[0].li_attr.agid;
                var $left = $this.find('div[agency="' + agencyid + '"]');
                var $right = $('#slotList2').find('div[agency="' + agencyid + '"]');
                var seq = [];
                
                if ($right.length > 0) {
                    bgName = $right.attr('bgname');

                    $right.children('em').each(function () {
                        seq.push($(this).text());
                    });
                    if (seq.length > 0) {
                        $left.find('li').each(function () {
                            if ($.inArray($(this).text(), seq) == -1) {
                                $(this).attr('bgname', bgName).addClass(bgName);
                            }
                        });
                    }
                }
                else {
                    var index = agencyArrData.indexOf(parseInt(agencyid));

                    if (index == -1) {
                        bgName = 'clr' + agencyArrData.length;
                        agencyArrColor.push(bgName);
                        agencyArrData.push(parseInt(agencyid));
                    }
                    else {
                        bgName = agencyArrColor[index];
                    }
                    $left.find('li').each(function () {
                        $(this).attr('bgname', bgName).addClass(bgName);
                    });
                }
                break;
            default:
                var arrAgency = uniqueArr(arr);

                agencyArrData = [];
                agencyArrColor = [];

                for (var i = 0; i < arrAgency.length; i++) {
                    agencyArrData.push(arrAgency[i]);
                    agencyArrColor.push('clr' + i);
                    bgName = agencyArrColor[i];

                    $this.find('div[agency="' + arrAgency[i] + '"]').each(function () {
                        $(this).attr('bgname', bgName);
                        $(this).children('span').addClass(bgName);
                        $(this).children('a').attr('bgname', bgName);                        
                    });
                }
                break;
        }
    }

    function uniqueArr(array) {
        var result = [];

        $.each(array, function (index, element) {
            if ($.inArray(element, result) == -1) {
                result.push(element);
            }
        });

        return result;
    }
    
    $.fn.SlotView.defaults = {
        layoutCnt: 4,
        viewid: 'view',
        slotCnt: 0,
        dragOption: false,
        data: [],
        dataOption: '',
        loadDataOption: true
    };
})($);