
var _menus = { "menus": [] };

/*-------------充电站------sta---------*/
var menus_myStation = [];
var menus_record = [];
var menus_user = [];
var menus_account = [];
var menus_add_pile = [];
/*-------------充电站------end---------*/
/*-------------车主------sta---------*/
var menus_searchStation = [];
var menus_carOwnerRecord = [];
var menus_userInfo = [];
var menus_carOwneraccount = [];
/*-------------车主------end---------*/
/*-------------管理员------sta---------*/
var menus_admin_station = [];
var menus_admin_user = [];
/*-------------管理员------end---------*/

/*-------------充电站菜单初始化------sta---------*/
function station_menus_init(){
	_menus = { "menus": [] };
	/*-------------我的充电站------sta---------*/
	menus_myStation = [];
	menus_myStation.push({ "menuid": "chargingPileList", "menuname": "我的充电桩", "icon": "icon-nav", "divclass": "", "url": 'station/myStation.jsp' });
	menus_myStation.push({ "menuid": "stationManage", "menuname": "充电站管理", "icon": "icon-nav", "divclass": "", "url": 'station/myStation.jsp' });
	/*-------------我的充电站------end---------*/
	/*-------------充电记录------sta---------*/
	menus_record = [];
	menus_record.push({ "menuid": "chargingOrdersList", "menuname": "充电记录", "icon": "icon-nav", "divclass": "", "url": 'station/record.jsp' });
	/*-------------充电记录------end---------*/
	/*-------------充电用户------sta---------*/
	menus_user = [];
	menus_user.push({ "menuid": "userList", "menuname": "用户列表", "icon": "icon-nav", "divclass": "", "url": 'station/user.jsp' });
	/*-------------充电用户------end---------*/
	/*-------------账户------sta---------*/
	menus_account = [];
	menus_account.push({ "menuid": "accountPage", "menuname": "账户信息", "icon": "icon-nav", "divclass": "", "url": 'station/account.jsp' });
	/*-------------账户------end---------*/
	/*----------创建充电桩---str--------*/
	menus_add_pile = [];
	menus_add_pile.push({ "menuid": "addPilePage", "menuname": "创建充电桩", "icon": "icon-nav", "divclass": "", "url": 'station/addPile.jsp' });
	/*----------创建充电桩---end--------*/
}
/*-------------充电站菜单初始化------end---------*/

/*-------------车主菜单初始化------sta---------*/
function carOwner_menus_init(){
	_menus = { "menus": [] };
	/*-------------我的充电站------sta---------*/
	menus_searchStation = [];
	menus_searchStation.push({ "menuid": "mapSearch", "menuname": "地图搜索", "icon": "icon-nav", "divclass": "", "url": 'carOwner/searchStation.jsp' });
	menus_searchStation.push({ "menuid": "stationList", "menuname": "列表", "icon": "icon-nav", "divclass": "", "url": 'carOwner/searchStation.jsp' });
	/*-------------我的充电站------end---------*/
	/*-------------充电记录------sta---------*/
	menus_carOwnerRecord = [];
	menus_carOwnerRecord.push({ "menuid": "chargingOrdersList", "menuname": "充电记录", "icon": "icon-nav", "divclass": "", "url": 'carOwner/record.jsp' });
	menus_carOwnerRecord.push({ "menuid": "checkNumberList", "menuname": "我的预约校验码", "icon": "icon-nav", "divclass": "", "url": 'carOwner/record.jsp' });
	/*-------------充电记录------end---------*/
	/*-------------个人信息------sta---------*/
	menus_userInfo = [];
	menus_userInfo.push({ "menuid": "userInfoPage", "menuname": "个人信息", "icon": "icon-nav", "divclass": "", "url": 'carOwner/userInfo.jsp' });
	/*-------------个人信息------end---------*/
	/*-------------账户------sta---------*/
	menus_carOwneraccount = [];
	menus_carOwneraccount.push({ "menuid": "accountPage", "menuname": "账户信息", "icon": "icon-nav", "divclass": "", "url": 'carOwner/account.jsp' });
	/*-------------账户------end---------*/
}
/*-------------车主菜单初始化------end---------*/
/*-------------管理员菜单初始化------sta---------*/
function admin_menus_init(){
	_menus = { "menus": [] };
	/*-------------充电站------sta---------*/
	menus_admin_station = [];
	menus_admin_station.push({ "menuid": "stationList", "menuname": "充电站列表", "icon": "icon-nav", "divclass": "", "url": 'admin/station.jsp' });
	/*-------------充电站------end---------*/

	/*-------------用户------sta---------*/
	menus_admin_user = [];
	menus_admin_user.push({ "menuid": "userList", "menuname": "车主用户列表", "icon": "icon-nav", "divclass": "", "url": 'admin/user.jsp' });
	/*-------------用户------end---------*/
	
}
/*-------------管理员菜单初始化------end---------*/

///根据url设置菜单项
var category = ''; //栏目名称，当前位置用
//create by hqs on 2012-11-9
function setStationMenusItem() {
	station_menus_init();
    var url = location.href;
    var regMyStation = /myStation.jsp/;
    var regRecord = /record.jsp/;
    var regUser = /user.jsp/;
    var regAccount = /account.jsp/;
    var regAdd_pile= /addPile.jsp/;
    var cases = { 1: regMyStation.test(url), 2: regRecord.test(url), 3: regUser.test(url), 4: regAccount.test(url), 5: regAdd_pile.test(url) };
    var num = 1;
    
    for (var item in cases) {
        if (cases[item]) {
            num = parseInt(item);
        }
    }
    switch (num) {
        case 1: //chargingPileList.jsp
            $(".system_menu li a").eq(0).addClass("home_submenu_sl");
            _menus.menus.push({ "menuid": "myStation", "icon": "icon-sys report_today", "menuname": "我的充电站", "menus": menus_myStation });
            category = '我的充电站';
            InitLeftMenu();
            break;
        case 2: //record.jsp
            $(".system_menu li a").eq(1).addClass("form_submenu_sl");
            _menus.menus.push({ "menuid": "record", "icon": "icon-sys statistics_performance", "menuname": "充电记录", "menus": menus_record });
            category = '充电记录';
            InitLeftMenu();
            break;
        case 3: //user.jsp
            $(".system_menu li a").eq(2).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "user", "icon": "icon-sys alarmMessage", "menuname": "用户记录", "menus": menus_user });
            category = '用户记录';
            InitLeftMenu();
            break;
        case 4: //account.jsp
            $(".system_menu li a").eq(3).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "account", "icon": "icon-sys accountIcon", "menuname": "账户", "menus": menus_account });
            category = '账户';
            InitLeftMenu();
            break;
        case 5: //addPile.jsp
            _menus.menus.push({ "menuid": "addPile", "icon": "icon-sys create_site_monitoring", "menuname": "创建充电桩", "menus": menus_add_pile });
            category = '创建充电桩';
            InitLeftMenu();
            break;
    }
}

function setCarOwnerMenusItem() {
	carOwner_menus_init();
    var url = location.href;
    var regSearchStation = /searchStation.jsp/;
    var regRecord = /record.jsp/;
    var regUserInfo = /userInfo.jsp/;
    var regAccount = /account.jsp/;
    var cases = { 1: regSearchStation.test(url), 2: regRecord.test(url), 3: regUserInfo.test(url), 4: regAccount.test(url) };
    var num = 1;
    
    for (var item in cases) {
        if (cases[item]) {
            num = parseInt(item);
        }
    }
    switch (num) {
        case 1: //
            $(".system_menu li a").eq(0).addClass("home_submenu_sl");
            _menus.menus.push({ "menuid": "searchStation", "icon": "icon-sys report_today", "menuname": "查找充电站", "menus": menus_searchStation });
            category = '查找充电站';
            InitLeftMenu();
            break;
        case 2: //record.jsp
            $(".system_menu li a").eq(1).addClass("form_submenu_sl");
            _menus.menus.push({ "menuid": "record", "icon": "icon-sys statistics_performance", "menuname": "充电记录", "menus": menus_carOwnerRecord });
            category = '充电记录';
            InitLeftMenu();
            break;
        case 3: //user.jsp
            $(".system_menu li a").eq(2).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "userInfo", "icon": "icon-sys alarmMessage", "menuname": "个人信息", "menus": menus_userInfo});
            category = '个人信息';
            InitLeftMenu();
            break;
        case 4: //account.jsp
            $(".system_menu li a").eq(3).addClass("submenu_sl");
            _menus.menus.push({ "menuid": "account", "icon": "icon-sys accountIcon", "menuname": "账户", "menus": menus_carOwneraccount });
            category = '账户';
            InitLeftMenu();
            break;
    }
}

function setAdminMenusItem() {
	admin_menus_init();
    var url = location.href;
    var regStation = /station.jsp/;
    var regUser = /user.jsp/;
    var cases = { 1: regStation.test(url), 2: regUser.test(url) };
    var num = 1;
    
    for (var item in cases) {
        if (cases[item]) {
            num = parseInt(item);
        }
    }
    switch (num) {
        case 1: //stationList.jsp
            $(".system_menu li a").eq(0).addClass("home_submenu_sl");
            _menus.menus.push({ "menuid": "station", "icon": "icon-sys report_today", "menuname": "充电站", "menus": menus_admin_station });
            category = '充电站';
            InitLeftMenu();
            break;
        case 2: //userList.jsp
            $(".system_menu li a").eq(1).addClass("form_submenu_sl");
            _menus.menus.push({ "menuid": "user", "icon": "icon-sys statistics_performance", "menuname": "车主用户", "menus": menus_admin_user });
            category = '车主用户';
            InitLeftMenu();
            break;
    }
}

$(function () {
    var url = location.href; 
    var regStation = /[\s\S]\/station\/[\s\S]/;
    var regCarOwner = /[\s\S]\/carOwner\/[\s\S]/;
    var regAdmin = /[\s\S]\/admin\/[\s\S]/;

    if(regStation.test(url))
    {
    	setStationMenusItem();
    }
    else if(regCarOwner.test(url)){
    	setCarOwnerMenusItem();
    }
    else if(regAdmin.test(url)){
    	setAdminMenusItem();
    }
    //    InitLeftMenu();

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

function loadPageContent(pag, menuid){
	$("#pageContent").load(pag+"/"+menuid+".jsp");
}