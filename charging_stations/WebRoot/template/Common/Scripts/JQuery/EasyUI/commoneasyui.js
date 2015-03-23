/***********************************
/* 修改人：hqs
/* 修改日期：2012-11-9
***********************************/
var _menus = { "menus": [] };
/*-------------报告------sta---------*/
var menus_1 = [];
var menus_2 = [];
var menus_3 = [];
var menus_4 = [];
menus_1.push({ "menuid": "11", "menuname": "有效任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_1.push({ "menuid": "12", "menuname": "过期任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_1.push({ "menuid": "13", "menuname": "站点告警", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_2.push({ "menuid": "21", "menuname": "有效任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_2.push({ "menuid": "22", "menuname": "过期任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_2.push({ "menuid": "23", "menuname": "站点告警", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_3.push({ "menuid": "31", "menuname": "有效任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_3.push({ "menuid": "32", "menuname": "过期任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_3.push({ "menuid": "33", "menuname": "站点告警", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_4.push({ "menuid": "41", "menuname": "有效任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_4.push({ "menuid": "42", "menuname": "过期任务", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
menus_4.push({ "menuid": "43", "menuname": "站点告警", "icon": "icon-nav", "divclass": "", "url": 'report.htm' });
//_menus.menus.push({ "menuid": "1", "icon": "icon-sys report_today", "menuname": "今日报告", "menus": menus_1 });
//_menus.menus.push({ "menuid": "2", "icon": "icon-sys report_week", "menuname": "本周报告", "menus": menus_2 });
//_menus.menus.push({ "menuid": "3", "icon": "icon-sys report_month", "menuname": "本月报告", "menus": menus_3 });
//_menus.menus.push({ "menuid": "4", "icon": "icon-sys report_define", "menuname": "自定义报告", "menus": menus_4 });
/*-------------报告------end---------*/
/*-------------报表统计------sta---------*/
var menus_11 = [];
menus_11.push({ "menuid": "111", "menuname": "中国地图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "112", "menuname": "性能历史曲线图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "113", "menuname": "城市性能图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "114", "menuname": "城市性能曲线图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "115", "menuname": "运营商性能图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "116", "menuname": "运营商曲线图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "117", "menuname": "城市运营商性能曲线图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "118", "menuname": "散点图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
menus_11.push({ "menuid": "119", "menuname": "性能分布直方图", "icon": "icon-nav", "divclass": "", "url": 'statistics.htm' });
/*-------------报表统计------end---------*/
/*-------------告警------sta---------*/
var menus_5 = [];
var menus_6 = [];
var menus_7 = [];
var menus_8 = [];
var menus_9 = [];
menus_5.push({ "menuid": "51", "menuname": "告警消息", "icon": "icon-nav", "divclass": "", "url": 'alam.htm' });
menus_6.push({ "menuid": "61", "menuname": "告警消息", "icon": "icon-nav", "divclass": "", "url": 'alam.htm' });
menus_7.push({ "menuid": "71", "menuname": "告警消息", "icon": "icon-nav", "divclass": "", "url": 'alam.htm' });
menus_8.push({ "menuid": "81", "menuname": "告警消息", "icon": "icon-nav", "divclass": "", "url": 'alam.htm' });
menus_9.push({ "menuid": "91", "menuname": "告警消息", "icon": "icon-nav", "divclass": "", "url": 'alam.htm' });
/*-------------告警------end---------*/
/*-------------账户------sta---------*/
var menus_10 = [];
menus_10.push({ "menuid": "101", "menuname": "账户信息", "icon": "icon-nav", "divclass": "", "url": 'user.htm' });
menus_10.push({ "menuid": "102", "menuname": "监测点设置", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "103", "menuname": "维护窗口", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "104", "menuname": "套餐升级", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "105", "menuname": "购买站点监控项目", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "106", "menuname": "购买监测点", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "107", "menuname": "购买包月短信", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "108", "menuname": "购买短信包", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "109", "menuname": "购物车", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "1010", "menuname": "购买历史", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "1011", "menuname": "发票申请", "icon": "icon-nav", "divclass": "", "url": '' });
menus_10.push({ "menuid": "1012", "menuname": "申请记录", "icon": "icon-nav", "divclass": "", "url": '' });
/*-------------账户------end---------*/
/*----------创建站点监控---str--------*/
var menus_12 = [];
menus_12.push({ "menuid": "121", "menuname": "创建站点监控", "icon": "icon-nav", "divclass": "", "url": 'add_site.htm' });
/*----------创建站点监控---end--------*/

///根据url设置菜单项
var category = ''; //栏目名称，当前位置用
//create by hqs on 2012-11-9
function setmenusItem() {
    var url = location.href;
    var regReport = /report.htm/;
    var regStatistics = /statistics.htm/;
    var regAlam = /alam.htm/;
    var regUser = /user.htm/;
    var regAddSite = /add_site.htm/;
    var cases = { 1: regReport.test(url), 2: regStatistics.test(url), 3: regAlam.test(url), 4: regUser.test(url), 5: regAddSite.test(url) };
    var num = 1;
    
    for (var item in cases) {
        if (cases[item]) {
            num = parseInt(item);
        }
    }
    switch (num) {
        case 1: //report.htm
            $(".system_menu li a").eq(0).addClass("home_submenu_sl");
            _menus.menus.push({ "menuid": "1", "icon": "icon-sys report_today", "menuname": "今日报告", "menus": menus_1 });
            _menus.menus.push({ "menuid": "2", "icon": "icon-sys report_week", "menuname": "本周报告", "menus": menus_2 });
            _menus.menus.push({ "menuid": "3", "icon": "icon-sys report_month", "menuname": "本月报告", "menus": menus_3 });
            _menus.menus.push({ "menuid": "4", "icon": "icon-sys report_define", "menuname": "自定义报告", "menus": menus_4 });
            category = '报告';
            InitLeftMenu();
            break;
        case 2: //statistics.htm
            $(".system_menu li a").eq(1).addClass("form_submenu_sl");
            _menus.menus.push({ "menuid": "11", "icon": "icon-sys statistics_performance", "menuname": "性能概况", "menus": menus_11 });
            category = '报表统计';
            InitLeftMenu();
            $("#nav .panel").before($("#st_list_menu").html());
            $("#nav .panel").after($("#st_quick_menu").html());

            $("#nav .st_sl_pic,#nav .st_sl_date").click(function () {
                $(this).next().slideToggle();
            });

            $("#nav .st_sl_option a,#nav .st_sl_date_option a").click(function () {
                $(this).parent().hide().prev().text($(this).text());
            });

            $(".list_content,.sl_date").mouseleave(function () {
                $(this).children().last().hide();
            });

            $(':radio').d_radio();
            break;
        case 3: //alam.htm
            $(".system_menu li a").eq(2).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "5", "icon": "icon-sys alarmMessage", "menuname": "告警消息", "menus": menus_5 });
            _menus.menus.push({ "menuid": "6", "icon": "icon-sys alarmNotice", "menuname": "告警通知", "menus": menus_6 });
            _menus.menus.push({ "menuid": "7", "icon": "icon-sys faultManagement", "menuname": "故障管理", "menus": menus_7 });
            _menus.menus.push({ "menuid": "8", "icon": "icon-sys alarmNotificationSetting", "menuname": "告警通知设置", "menus": menus_8 });
            _menus.menus.push({ "menuid": "9", "icon": "icon-sys customAlarm", "menuname": "自定义告警", "menus": menus_9 });
            category = '警告';
            InitLeftMenu();
            break;
        case 4: //user.htm
            $(".system_menu li a").eq(3).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "10", "icon": "icon-sys accountIcon", "menuname": "账户", "menus": menus_10 });
            category = '账户';
            InitLeftMenu();
            break;
        case 5: //add_site.htm
            _menus.menus.push({ "menuid": "12", "icon": "icon-sys create_site_monitoring", "menuname": "创建站点监控", "menus": menus_12 });
            category = '创建站点监控';
            InitLeftMenu();
            break;

    }
   
}

$(function () {
    setmenusItem();
    //    InitLeftMenu();
    //在线时间计算依赖jquery.cookie.js
    var startTime = $.cookie("starttime");
    if (startTime == null) {
        startTime = new Date().getTime();
        $.cookie("starttime", startTime, { path: '/' });
    }
    setInterval(function () {
        var start = $.cookie("starttime");
        var now = new Date().getTime();
        $("#Div_TimeInfo").html(GetDateDiffString(start, now));
    }, 1000);


});                //end $(document).ready();

//获取url传递参数
//create by hqs on 2012-11-9
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象 
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数 
    if (r != null) return unescape(r[2]);
    return null; //返回参数值 
}

//初始化左侧
function InitLeftMenu() {
    var menuid = getUrlParam('menuid');
    var menuname = getUrlParam('menuname');
    $("#nav").accordion({ animate: true });
    $.each(_menus.menus, function (i, n) {
        var menulist = '';

        if (n.menus.length > 0) {
            menulist += '<ul>';
            $.each(n.menus, function (j, o) {
                var url = o.url + '?menuid=' + o.menuid + '&menuname=' + n.menuname;
                var currentClass = '';
                
                if (menuid == o.menuid || (menuid == null && i == 0 && j == 0)) {
                    currentClass = ' currentItem';
                }

                menulist += '<li><div class="' + o.divclass + currentClass + '"><a ref="' + o.menuid + '" href="' + url + '" >';
                menulist += '<span class="icon ' + o.icon + ' pngFix" >&nbsp;</span><span class="nav">';
                menulist += '<span id="|' + o.menuid + '|">' + o.menuname + '</span></span></span></a></div></li>';
            });
            menulist += '</ul>';
        }
        var collapse = n.menuname == menuname; //当前栏目判断用
        if (menuname == null && i == 0) {
            collapse = true;
        }
        $('#nav').accordion('add', {
            title: n.menuname,
            collapsed: collapse,
            content: menulist,
            iconCls: 'icon ' + n.icon + ' pngFix'
        });
    });           //end $.each()

    $('.easyui-accordion .panel li a').hover(function () {
        var div = $(this).parent();

        if (div.prop("class").length == 0) {
            $(this).parent().addClass("hover");
        }

    }, function () {
        $(this).parent().removeClass("hover");
    });      //end $('.easyui-accordion li a').click().hover()
    //设置当前选项

    
//    if (menuname != null) {
//        $('#nav').accordion('select', menuname);
//    }
    setCurrentLocation(menuid);

} //end InitLeftMenu()


//设置当前位置
function setCurrentLocation(id) {
    var title = '';
    var pageLocation = '<div style="float:right;"><ul><li><span>当前位置：</span></li>' + '<li>' + category + '</li>';
    if (id == null || id == undefined) {
        title = _menus.menus[0].menus[0].menuname;
        pageLocation += '<li>></li>' + '<li>' + _menus.menus[0].menuname + '</li>'+ '<li>></li><li>' + _menus.menus[0].menus[0].menuname + '</li>';
    } else {
        for (var n = 0; n < _menus.menus.length; n++) {
            for (var i = 0; i < _menus.menus[n].menus.length; i++) {
                if (_menus.menus[n].menus[i].menuid == id) {
                    title = _menus.menus[n].menus[i].menuname;
                    pageLocation += '<li>></li>' + '<li>' + _menus.menus[n].menuname + '</li>' + '<li>></li><li>' + _menus.menus[n].menus[i].menuname + '</li>';
                }
            }
           
        }
    }
    $('#pageTitle span').text(title);
    $('#pagePath').html(pageLocation + '</ul></div>');
}

function displayMenu() {
    $("#nav .menu_home").hide();
}

//获取左侧导航的图标
function getIcon(menuid) {
    var icon = 'icon ';
    $.each(_menus.menus, function (i, n) {
        $.each(n.menus, function (j, o) {
            if (o.menuid == menuid) {
                icon += o.icon;
            }
        });
    });

    return icon;

} //end getIcon()

//选中样式
function setSelectedClass(menu) {
    var div = $(menu).parent();

    if (div.prop("class").indexOf("icon_hide") == -1) {
        $('.easyui-accordion li div').removeClass("selected");
        $(menu).parent().addClass("selected");

        if ($(menu).parent().parent().parent().parent().is(":hidden"))
            $(menu).parent().parent().parent().parent().prev().click();
    }
    else {
        $('.easyui-accordion li div').removeClass("big_selected");
        $(this).parent().addClass("big_selected");
    }

} //end setSelectedClass()


//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
} //end msgShow()

//--------------------------------------------------------------------------------------------------------//
//确定信息
//create by wk on 2011-7-19
function confirm(title, message, fun) {

    $.messager.confirm(title, message, function (isOK) {
        if (isOK) {
            //alert('confirmed:' + isOK + " " + fun);
            eval(fun);
        }
    });
} //end confirm()

//防止点击其他TAB时出现光标闪烁或日期控件弹出之后没有关闭
//create by wk on 2011-8-31
function setIframeFocus() {
    $(".tabs li").live("click", function () {
        var getTab = $('#tabs').tabs('getSelected');
        var iframes = getTab.find('iframe');
        if (iframes.length > 0) {
            iframes.contents().find("body").focus();
        }
    }); //end $(".tabs li").live()

} //end setIframeFocus()

function checkIframe() {
    var iframe = $(".tabs-panels .panel:not([style*=display])").find("iframe")[0];
    if (iframe.attachEvent) {
        iframe.attachEvent("onload", function () {
            $(".datagrid-mask,.datagrid-mask-msg").remove();
        });
    }
    else {
        iframe.onload = function () {
            $(".datagrid-mask,.datagrid-mask-msg").remove();
        };
    }
}
//根据开始结束时间毫秒数计算时间差
//create by hqs on 2012-11-9
function GetDateDiffString(startTime, endTime) {
    //时，分，秒单位数
    var hourUnit = 1000 * 3600;
    var minuteUnit = 1000 * 60;
    var secondUnit = 1000;
    //计算时差
    var diff = parseInt(endTime) - parseInt(startTime);
    var hours = parseInt(diff / hourUnit);
    var minute = parseInt((diff % hourUnit) / minuteUnit);
    var second = parseInt((diff % minuteUnit) / secondUnit);

    return hours + "小时 " + minute + "分" + second + "秒";
}