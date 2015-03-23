/****************************************************
*以下是基于jqueryui的组件，包括通用的弹出层
*创建人：吴克
*创建日期：2011-7-26
****************************************************/

//初始化
//create by wk on 2011-7-26
//modefy by wk on 2011-8-3
$(document).ready(function() {
   
    $(".common_list tr").hover(function () {
        $(this).css("background", " #fffed5");
    }, function () {
        $(this).removeAttr("style");
    });
    //添加列表隔行色
    var isalam = /alam.htm/.test(location.href);
    if (!isalam) {
        $(".trcls:odd").css("background", "#f2f5fa");
        $(".common_list tr:even").addClass("trcls");
    }

    //alert(parent.$("#opFrame").contents().find("html").innerHeight());

    //alert($("html").innerHeight());

    if (parent.$("#opFrame").length > 0 && $("html").innerHeight() > 0) {

        parent.$("#opFrame").height($("html").innerHeight());

    }

    //复选框全选
    $("#ckbAll").live("click", function () {

        //alert($(this).attr("checked"));
        if ($(this).attr("checked") == "checked") {
            $(".ckbcls").attr("checked", "checked");
        } else {
            $(".ckbcls").removeAttr("checked");
        }

    }); //end $("#ckbAll").click()

    //复选框单选或多选
    $(".ckbcls").live("click", function () {

        var ckb = $(".ckbcls");
        var ckbchecked = $(".ckbcls:checked");

        //如果选中的复选框个数与复选框的总个数相同，则全选
        if (ckbchecked.length == ckb.length) {
            $("#ckbAll").attr("checked", "checked");
        }

        //如果选中的复选框个数小于复选框的总个数，则取消全选
        if (ckbchecked.length < ckb.length) {
            $("#ckbAll").removeAttr("checked");
        }
    }); //end $(".ckbcls").click()

    //删除选择
    $("#lbtnAllDel").live("click", function (e) {

        if ($(".ckbcls:checked").length == 0) {
            openMsgStateDialog(300, 180, "提示信息", "请选择您要删除的数据");
            return false;
        }

        $("#hidfCkbVal").val(getCkbVals());

        e.preventDefault();

        openMsgDialog("dialogDel", 300, 180, "用户删除选择", "您确定要删除所选择的数据吗？", $(this).attr("href"));

    }); //end $("#lbtnAllDel").click()

    //删除单行
    $(".del").live("click", function (e) {
        e.preventDefault();
        openMsgDialog("dialogDel", 300, 180, "", $(this).attr("tip") != undefined ? $(this).attr("tip") : "您确定要删除当前数据吗？", $(this).attr("href"));
    }); //$(".del").click()


    //去除两边空格
    $("input:text[class*='required']").blur(function () {
        //$(this).val($.trim($(this).val()));
        $(this).val($(this).val().replace(/^\s+|\s+$/g, ""));
    });

});             //end $(document).ready();



/**********************************  以下用于jquery与net ajax控件(UpdatePanel)结合使用 *************************************/
//这种方式目的UpdatePanel局部刷新时不会导致jquery效果失效
function EndRequestHandler() {
    $(".trcls:odd").css("background", "#f7f9fc");
}

function reloadPrm() {

    Sys.WebForms.PageRequestManager.getInstance().add_endRequest(EndRequestHandler);

}

//这种方式在如果没有使用ScriptManager控件的列表页面会报错：Sys未定义
//解决方案是只有使用了ScriptManager控件的列表页面才使用这函数
//$(document).ready(function() {
//    reloadPrm();
//});
//setTimeout('$("#opFrame").contents().find(":text").first().focus();', 500);
/***************************************************************************************************************************/

function clearIEProgressBar(p_url) {
    setTimeout('$("#opFrame").prop("src", "' + p_url + '");', 1);
    setTimeout('setInputFocus();', 300);
}

//带有确定与关闭按钮的弹出层 
//create by wk on 2011-7-26
function openDialog(p_url, p_width, p_height, p_btnok, p_title) {
    $("<iframe id='opFrame' src='" + p_url + "' border='0' frameborder='0' style='width:90% !important;'/>").dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "确定": function() {
                if (p_btnok.length > 0) {
                    $(this).contents().find("#" + p_btnok).click();
                }
            },
            "关闭": function() {
                $(this).dialog("close");
            }
        },
        close: function() {
            $(this).attr("src", "about:blank");
            $(this).dialog('destroy');
            $(this).remove();
        }
    }).width(p_width - 30);

    clearIEProgressBar(p_url);

}


//没有按钮的弹出层
//create by wk on 2011-7-26
function openNoBtnDialog(p_url, p_width, p_height, p_title) {
    $("<iframe id='opFrame' src='" + p_url + "' border='0' frameborder='0' style='width:90% !important;'/>").dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "关闭": function() {
                $(this).dialog("close");
            }
        }
    }).width(p_width - 30);

    clearIEProgressBar(p_url);
}

//弹出层用于删除等功能的信息提示
//create by wk on 2011-7-26
function openMsgDialog(p_obid, p_width, p_height, p_title, p_msg, p_execjs) {
    var html = "<div id='" + p_obid + "' class='msgcls'><span>" + p_msg + "</span></div>";

    $(html).dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "确定": function() {
                eval(p_execjs);
                $(this).dialog("close");
            },
            "关闭": function() {
                $(this).dialog("close");
            }
        }
    }).width(p_width - 30);
}

//弹出层用于新增、编辑等操作成功与否的信息提示（包含刷新列表功能）
//create by wk on 2011-7-26
/*
* p_obid:<div>元素的ID属性
* p_width:弹出层的宽度
* p_height:弹出层的高度
* p_title:弹出层的标题
* p_msg:弹出层的内容信息
*/
function openMsgStateDialog(p_width, p_height, p_title, p_msg) {
    var html = "<div id='dialogMsg' class='msgcls'><span>" + p_msg + "</span></div>";

    $(html).dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "确定": function() {
                $(this).dialog("close");
                refresh();
            }
        }
    }).width(p_width - 30);
}

//弹出层用于新增、编辑等报异常的信息提示（不包含刷新列表功能）
//create by wk on 2011-8-20
/*
* p_obid:<div>元素的ID属性
* p_width:弹出层的宽度
* p_height:弹出层的高度
* p_title:弹出层的标题
* p_msg:弹出层的内容信息
*/
function openMsgStateDialog_1(p_width, p_height, p_title, p_msg) {
    var html = "<div id='dialogMsg' class='msgcls'><span>" + p_msg + "</span></div>";

    $(html).dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "确定": function() {
                $(this).dialog("close");
            }
        }
    }).width(p_width - 30);
}

//弹出层用于删除等操作后，在当期窗口打开新的页面
//create by lybohe on 2011-08-09
/*
* p_obid:<div>元素的ID属性
* p_width:弹出层的宽度
* p_height:弹出层的高度
* p_title:弹出层的标题
* p_msg:弹出层的内容信息
* p_refreshUrl:刷新当前页面，或者当前页面窗口打开新的页面
*/
function openMsgStateDialogRefresh(p_width, p_height, p_title, p_msg, p_refreshUrl) {
    var html = "<div id='dialogMsg' class='msgcls'><span>" + p_msg + "</span></div>";

    $(html).dialog({
        autoOpen: true,
        modal: true,
        width: p_width,
        height: p_height,
        resizable: false,
        title: p_title,
        buttons: {
            "确定": function() {
                $(this).dialog("close");
                location.href = p_refreshUrl;
            }
        }
    }).width(p_width - 30);
}

//刷新父页面的页面，不用reload()是防止出现页面弹出“重试”的对话框
//create by wk on 2011-7-26
function refresh() {
    url = location.href.split("#")[0];
    url = url.toLowerCase();
    //如果当前列表只有一行
    if ($(".trcls").length == 1) {

        var pageIndexVal = getUrlQueryStringRegExp(url, "pageindex");

        if (pageIndexVal.length > 0 && parseInt(pageIndexVal) > 1) {
            var newPageIndexVal = parseInt(pageIndexVal) - 1;
            url = url.replace("pageindex=" + pageIndexVal, "PageIndex=" + newPageIndexVal);
        }

        if (url.indexOf("key") != -1) {

            var paKeyVal = getUrlQueryStringRegExp(url, "key");
            url = url.replace("key=" + paKeyVal + "&", "");
        }

    }

    location.href = url;

}

//获取选中复选框的值
//create by wk on 2011-7-28
function getCkbVals() {

    var ckbvals = new Array();

    var num = 0;

    $(".ckbcls").each(function(i, n) {

        if ($(n).attr("checked") == "checked") {

            ckbvals[num++] = $(n).val();
        }

    });

    return ckbvals.join(",");
}

//设置input类型的控件焦点，包括文本框或密码框
//create by wk on 2011-11-7
function setInputFocus() {

    var inputs = $("#opFrame").contents().find("input");
    inputs.each(function(index) {
        if (($(this).prop("type") == "text" || $(this).prop("type") == "password") && !$(this).prop("disabled")) {

            $(this).focus(function() {
                $(this).val($(this).val());
            });

            $(this).focus();

            return false;
        }
    });   //end inputs.each()
}


function getUrlQueryStringRegExp(url, name) {
    var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
    var parameterVal = "";

    if (reg.test(url)) {
        parameterVal = RegExp.$2;
    }

    return parameterVal;
}