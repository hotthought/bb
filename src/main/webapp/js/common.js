// JScript 文件
//v 1.0.0.1
function CallWebMethod(url, data, callback, complete,error) {
    if (typeof PreCallWebMethod == 'function') PreCallWebMethod();
    if (typeof console != "undefined") console.log("CallWebMethod：" + url + ":" + data);
    return $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        data: data,
        dataType: "json",
        timeout: 30000,
        success: callback,
        complete: function() {
            if (typeof complete == 'function') complete();
            if (typeof PostCallWebMethod == 'function') PostCallWebMethod();
        },
        error: function(xhr, status, errMsg) {
            if (typeof error == 'function'){error();return}
            var error = "系统异常！";
            try {
                error = eval(xhr.responseText).Message;
            } catch (ex) { }
            if (typeof console != "undefined") console.log("error：" + error);
            jAlert(error);
        }
    });
}


//提示框
function jAlert(msg, postcallback, type) {  //dialog插件
    type = type || "alert";
    $("#alertdialog").remove();
    $("body").append("<div id='alertdialog' title='提示' style='display:none;'><p><span class='ui-icon ui-icon-" + type + "' style='float:left;width:28px;height:31px;'></span><label id='alertmsg' style='height:31px;line-height:21px;'></label></p></div>");
    $("#alertdialog").dialog({
        bgiframe: true,
        autoOpen: false,
        modal: true,
        width:200,
        height:180,
        //hide: 'scale',
        //show: 'scale',
        buttons: { "确定": function() { $(this).dialog('close'); } },
        close: function() { if (postcallback) postcallback(); }
    });
    $("#alertmsg").html(msg);
    $('#alertdialog').dialog('open');
}
function jConfirm(msg, callback, postcallback) {  //dialog插件
    if ($('#confirmdialog').length == 0) {
        $("body").append("<div id='confirmdialog' title='询问' style='display:none;'><p><span class='ui-icon ui-icon-confirm' style='float:left;width:28px;height:31px;'></span><label id='confirmmsg' style='height:31px;line-height:31px;'></label></p></div>");
        $("#confirmdialog").dialog({
            bgiframe: true,
            autoOpen: false,
            modal: true,
            //hide: 'scale',
            //show: 'scale',
            buttons: {
                "确定": function() { callback(); $(this).dialog('close'); },
                "取消": function() { $(this).dialog('close'); }
            },
            close: function() {
                if (postcallback) postcallback();
                $("#confirmdialog").remove();
            }, open: function() {
                $(".ui-button:contains('确定')").focus();
                $(".ui-button:contains('取消')").addClass("ui-cancelbutton");
            }
        });
    }
    $("#confirmmsg").html(msg);
    $('#confirmdialog').dialog('open');
    return false;
}

//获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
    if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
    return "";
}

// 出错处理
onerror = function() {
    var msg = "Error:" + arguments[0] + " URL:" + arguments[1] + " Line:" + arguments[2];
    if (typeof console != "undefined") console.log(msg);
    window.status = msg;
    setTimeout("try{top.SetStatus();}catch(e){ window.status='';}", 3000);
}


function StringToDate(str) {
	var date=new Date(0);
    if(typeof str=="string" && str.length>=6){
        var dt=str.match(/^(\d{1,4})([^\d]?)(\d{1,2})([^\d]?)(\d{1,2})([\s]*)(\d{0,2})([^\d]?)(\d{0,2})([^\d]?)(\d{0,2})$/);
        if(dt!=null){
            var y,M,d,h,m,s;
            y=parseInt('0'+dt[1],10);
            M=parseInt('0'+dt[3],10);
            d=parseInt('0'+dt[5],10);
            h=parseInt('0'+dt[7],10);
            m=parseInt('0'+dt[9],10);
            s=parseInt('0'+dt[11],10);
            date=new Date(y,M-1,d,h,m,s);
        }
    }
    return date;
}

function DateToString(dt) {
    return dt.getFullYear() + 
           "-" +
           (dt.getMonth() > 8 ? "" : "0") + (dt.getMonth() + 1) + 
           "-" +
           (dt.getDate() > 9 ? "" : "0") + dt.getDate()
}

//添加jQuery插件： 将控件失效置灰
var empty = function() { return false; }
jQuery.fn.extend({
    gray: function() { return this.attr('disabled', 'disabled').css({ filter: "Alpha(opacity=50)", "-moz-opacity": "0.5", opacity: "0.5" }).bind("click", empty); },
    ungray: function() { return this.removeAttr('disabled').css({ filter: "", "-moz-opacity": "", opacity: "" }).unbind("click", empty); }
});

/////////////
$(function(){
	
	//两个字的按钮样式切换
	$("#tools ul li a").hover(function(){
		$(this).removeClass("bttools").addClass("bttoolshover");
	},function(){
		$(this).removeClass("bttoolshover").addClass("bttools");
	}).mousedown(function(){
		$(this).removeClass("bttools").addClass("bttoolsclick");
	}).mouseup(function(){
		$(this).removeClass("bttoolsclick").addClass("bttools");
	});
	//四个字的按钮样式切换
	$("a.bttoolsL").hover(function(){
		$(this).removeClass("bttoolsL").addClass("bttoolsLhover");
	},function(){
		$(this).removeClass("bttoolsLhover").addClass("bttoolsL");
	}).mousedown(function(){
		$(this).removeClass("bttoolsL").addClass("bttoolsLclick");
	}).mouseup(function(){
		$(this).removeClass("bttoolsLclick").addClass("bttoolsL");
	});
	//确定按钮样式切换
    $(".btn").hover(function(){
  		$(this).removeClass("btn").addClass("btnhover");
  	},function(){
  		$(this).removeClass("btnhover").addClass("btn");
  	}).mousedown(function(){
      	$(this).removeClass("btn").addClass("btnclick");
    }).mouseup(function(){
      	$(this).removeClass("btnclick").addClass("btn");
    });
    //取消按钮样式切换
    $(".cancelbtn").hover(function(){
      	$(this).removeClass("cancelbtn").addClass("cancelbtnhover");
    },function(){
      	$(this).removeClass("cancelbtnhover").addClass("cancelbtn");
    }).mousedown(function(){
      	$(this).removeClass("cancelbtn").addClass("cancelbtnclick");
    }).mouseup(function(){
      	$(this).removeClass("cancelbtnclick").addClass("cancelbtn");
    });
    
});
//======================
//ie6窗口大小变化
$(function(){
	if($.browser.msie && $.browser.version=="6.0"){
	    $(window).resize(function(){reSize();});
	    reSize();
	}
});
function reSize(){
	if($.browser.msie && $.browser.version=="6.0"){
		var bh = $("body").height();
	    $("#mainright").height(bh-99-28);
	    $("#mainleft").height(bh-99-28);
	}
}

//绑定Select
function BindSelect(id,url,data,value,name,withAll,callback){
	CallWebMethod(url,data,function(data){
		$("#"+id).empty();
		if(withAll&&withAll==true){
			$("#"+id).append("<option value=''>全部</option>");  
		}
		if(data){
			for(var key in data){
				$("#"+id).append("<option value='"+data[key][value]+"'>"+data[key][name]+"</option>");   
			}
		}
		if(callback){
			callback(data);
		}
	});
};

//jquery弹出框
var showDialog=function(id,width,height,openFun,closeFun,modal){
	if($("#" + id).length>0){
		$("#"+id).dialog({
			width:width||400,
			height:height||300,
			modal:modal==null,
			open:function(event,ui){
				if(typeof openFun=="function"){
					openFun();
				}
			},
			close:function(event,ui){
				if(typeof closeFun=="function"){
					closeFun();
				}
			}
		});
		if($("#"+id).dialog("isOpen")){
			if(typeof openFun=="function"){
				openFun();
			}
		}else{
			$("#"+id).dialog("open");
		}
	}
};

