// JScript 文件

var ResultTable = {
	hfSortExpression:"",
	hfSortDirection:"",
	hfPageCount:0,
	hfPageSize:10,
	hfPageIndex:1,
	
	//============
	//   查询
	//============
	
	searchurl:"",
	searchdata:{},
	searchcallback:null,
	searchcomplete:null,
	pageTableId : null,
	search:function(params,callback,complete){
		this.hfPageIndex=1;
		this.searchdata=params.searchParams;
		this.searchcallback=callback;
		this.searchcomplete=complete;
		this.research();
	},
	research:function(){
		this.searchdata = $.extend({},this.searchdata,{
					            sortExp:this.hfSortExpression,
					            sortDir:this.hfSortDirection,
					            pageSize:this.hfPageSize,
					            pageNo:this.hfPageIndex
							});
		var bRet = false;
	    $("#searching").show();
	    $("#btnSearch").val("查询中...").attr("disabled", "disabled");
	    CallWebMethod(this.searchurl,this.searchdata,
				function(json, textStatus){
	    			var page = json;
	                if (json && json.error) {
	                	ResultTable.msg(json.error);
	                    return;
	                }
	                if (!page || !page.result || page.result.length == 0) {
	                	if(this.hfPageIndex>1){
	                		this.gotoPage(1);
	                		return;
	                	}
	                	ResultTable.msg("查询结果为空！");
	                    return;
	                }

	                if (page && page.totalPage) ResultTable.setCount(page.totalPage);
	                if (page && page.currentPage) ResultTable.setIndex(page.currentPage);
	                if(typeof ResultTable.searchcallback == 'function')ResultTable.searchcallback();
	                bRet = true;
	                ResultTable.fill(page.result);
	                ResultTable.showPager();
	        	},
	        	function(){
	        		$("#btnSearch").val("查询").removeAttr("disabled");
	                $("#searching").hide();
	                if (!bRet) {
	                	ResultTable.clearPager();
	                }
	                if(typeof ResultTable.searchcomplete == 'function')ResultTable.searchcomplete();
	        	}
	    );
	},
	
	//============
	//  查询结果
	//============
	hasResult: false,
    column:[],
    keyid:"",
    init: function(params) {
    	this.pageTableId = params.pageTableId;
    	this.searchurl = params.searchURL;
        $("#"+this.pageTableId).hide();
        this.keyid =  $("#" +this.pageTableId).attr("keyid");
        this.column=[];
        $("#"+this.pageTableId+" thead tr th").each(function(){
        	switch($(this).attr("type")){
        	case "xh":ResultTable.column.push({type:"xh"});break;
        	case "btn":
        		var btn=[];
        		$.each($(this).attr("btn").split('&'),function(i,b){
        			var tmp = b.split('|');
        			btn.push({id:tmp[0],text:tmp[1]});
        		});
        		ResultTable.column.push({type:"btn",btn:btn});
        		break;
        	case "callback":ResultTable.column.push({type:"callback",fun:$(this).attr("fun")});break;
        	case "checkbox":
        		ResultTable.column.push({type:"checkbox"});
        		//$(this).html("<input type='checkbox' id='selall' class='checkall' />");
        		break;
        	case "child":
        		ResultTable.column.child=true;
        		ResultTable.column.childlist=$(this).attr("childlist");
        		ResultTable.column.push({type:"child",fun:$(this).attr("fun")});
        		break;
        	default:
        		if($(this).attr("column"))
        			ResultTable.column.push({type:"col",col:$(this).attr("column"),tdClass:$(this).attr("tdClass")});
        		else
        			ResultTable.column.push({type:"none"});
        		break;
        	}
		});
        this.hasResult = false;
    },
    fill: function(arr) {
        if (arr.length <= 0) return;
        $("#selall").removeAttr('checked');
        $("#"+this.pageTableId+" tbody tr").remove();
        $.each(arr, function(i, n) {
        	//alert(n.logContent);  
    		var rowspan=ResultTable.column.child?" rowspan='"+n[ResultTable.column.childlist].length+"'":"";
    		var background = (i%2==1?" style='background:#f2f2f2'":"");
    		var rows = ResultTable.column.child&&n[ResultTable.column.childlist]&&n[ResultTable.column.childlist].length>0?n[ResultTable.column.childlist].length:1;
    		for(var r=0;r<rows;r++){
            	$("#"+ResultTable.pageTableId+" tbody").append("<tr"+(ResultTable.keyid?" key='"+n[ResultTable.keyid]+"'":"")+background+"></tr>");
            	$.each(ResultTable.column,function(j, l){
        			switch(l.type){
            		case "xh":
            			if(r>0) return true;
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td"+rowspan+">" + (i+1) + "</td>");
            			break;
            		case "btn":
            			if(r>0) return true;
            			var tmp ="<td"+rowspan+">";
            			$.each(l.btn,function(t,c){
            				tmp+=" <a mode='"+c.id+"' nid='" + n[ResultTable.keyid] + "'>"+c.text+"</a>";
            			});
            			tmp+="</td>";
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append(tmp);
            			break;
            		case "callback":
            			if(r>0) return true;
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td"+rowspan+">" + eval(l.fun+"(n,i)")+ "</td>");
            			break;
            		case "checkbox":
            			if(r>0) return true;
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td"+rowspan+"><input type='checkbox' nid='" + n[ResultTable.keyid] + "' value='"+n[ResultTable.keyid]+"' /></td>");
            			break;
            		case "col":
            			if(r>0) return true;
            			//alert(l.col);
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td"+rowspan+" class='"+l.tdClass+"'>" + (n[l.col]||"") + "</td>");
            			break;
            		case "child":
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td>"+eval(l.fun+"(n,i,r)")+"</td>");
            			break;
            		default:
            			if(r>0) return true;
            			$("#"+ResultTable.pageTableId+" tbody tr:last-child").append("<td"+rowspan+"></td>");
            			break;
            		}
    			});
    		}
        });
        //alert($("#pageTable tbody").html());
        //$("#pageTable").find("tr").show().end().show();
        this.hasResult = true;
    },
    clear: function() {
        $("#"+this.pageTableId+" tbody tr").remove();
        $("#"+this.pageTableId).hide();
        this.clearPager();
        this.hasResult = false;
    },
    msg: function(s) {
    	this.clearPager();
        $("#"+this.pageTableId+" tbody").find("tr").remove().end()
                    .find("tr").hide().end()
                    .append("<tr><td colspan='100'>" + s + "</td></tr>").show();
        this.hasResult = false;
    },
    
	//============
	//  分页
	//============
	  gotoPage: function(index) {
	      this.hfPageIndex=parseInt(index);
	      this.research();
	  },
	  setCount: function(count) {
	      this.hfPageCount=parseInt(count);
	  },
	  setIndex: function(index) {
	      this.hfPageIndex=parseInt(index);
	  },
	  showPager: function() {
	      var count = this.hfPageCount;
	      var index = this.hfPageIndex;
	      $("#pager").empty().hide();
	      if (count <= 1) return;
	      $("#pager").append("<span id='lIndex'>第" + index + "页</span>")
	                 .append("<span id='lCount'>/共" + count + "页</span>")
	                 .append("<div></div>");
	      if (index > 1) {
	          $("#pager div").append("<a page='1' class='disabled'>首页</a>")
	                     .append("<a page='" + (index - 1) + "' class='disabled'>上一页</a>");
	      }
	      for (var i = Math.floor((index - 1) / 10) * 10 + 1; i <= count && i <= Math.floor((index - 1) / 10 + 1) * 10; i++) {
	          if (i == index)
	              $("#pager div").append("<a class='selected'>" + i + "</a>");
	          else
	              $("#pager div").append("<a page='" + i + "' class='bg'>" + i + "</a>");
	      }
	      if (index < count) {
	          $("#pager div").append("<a page='" + (index + 1) + "' class='disabled'>下一页</a>")
	                     .append("<a page='" + count + "' class='disabled'>末页</a>");
	      }
	      $("#pager").show();
	  },
	  clearPager: function() {
	      $("#pager").empty().hide();
	  },
	  
	//============
	//	添加
	//============
	insertTitle:"",
	insert:function(url,data,success,complete) {
		$("#searching").show();  
		CallWebMethod(url,data,
			function(json, textStatus){
	            if (json) {
	                if (json.error)
	                    jAlert(json.error);
	                else {
	                    jAlert('添加成功', function() {
	                    	if(typeof success == 'function')success();
	                        if (ResultTable.hasResult) ResultTable.research();
	                    });
	                }
	            }
	    	},
	    	function(){
	    		if(typeof complete == 'function')complete();
	    		$("#searching").hide();
	        }
		);
	},
	//============
	//	修改
	//============
	editTitle:"",
	edit:function(url,data,success,complete) {
		$("#searching").show();
		CallWebMethod(url,data,
				function(json, textStatus){
	                if (json) {
	                    if (json.error)
	                        jAlert(json.error);
	                    else {
	                        jAlert('修改成功', function() {
	                        	if(typeof success == 'function')success();
	                            if (ResultTable.hasResult) ResultTable.research();
	                        });
	                    }
	                }
	        	},
	        	function(){
	        		if(typeof complete == 'function')complete();
	        		$("#searching").hide();
	        	}
			);
	},
	//============
	//	删除
	//============
	del:function(url,data,success,complete) {
		$("#searching").show();
		CallWebMethod(url,data,
			function(json, textStatus){
				if (json) {
	                if (json.error)
	                    jAlert(json.error);
	                else {
	                    jAlert('删除成功', function() {
	                    	if(typeof success == 'function')success();
	                        if (ResultTable.hasResult) ResultTable.research();
	                    });
	                }
	            }
	    	},
	    	function(){
	    		if(typeof complete == 'function')complete();
	    		$("#searching").hide();
	    	}
		);
	},

	//============
	//  选择
	//============
	getSelected:function(){
		var sels=[];
		$("input[type='checkbox'][nid]").each(function(){
			if($(this).attr("checked"))
				sels.push($(this).attr("nid"));
		});
		return sels;
	},
	
	
	//============
	//  绑定
	//============
	bindDetail:function bindDetail(url,data,success,complete){
		$("#searching").show();
		CallWebMethod(url,data,
				function(json, textStatus){
					if (json) {
		                if (json.error)
		                    jAlert(json.error);
		                else {
		                	success(json,textStatus);
		                }
		            }
		    	},
		    	function(){
		    		if(typeof complete == 'function')complete();
		    		$("#searching").hide();
		    	}
			);
	}
};
//============
//  选择init
//============
$(function() {
	$("#selall").click(function(){
		$("input[type='checkbox'][nid]").attr("checked",!!$(this).attr("checked"));
	});
});

//============
//    分页init
//============
$(function() { $("a[page]").live("click", function() { ResultTable.gotoPage($(this).attr("page")); }); });
//============
//   排序
//============
$(function() {
    $("th[sort]").live("click", function() {
        $("th[sort]").each(function() { $(this).html($(this).html().replace(/[↑↓]/g, '')); });
        if ($(this).attr("sort") == ResultTable.hfSortExpression) {
        	ResultTable.hfSortDirection = (ResultTable.hfSortDirection == "desc" ? "" : "desc");
        }
        ResultTable.hfSortExpression=$(this).attr("sort");
        $(this).html($(this).html() + (ResultTable.hfSortDirection == "desc" ? "↑" : "↓"));
        ResultTable.research();
    }).live("mouseover",function(){
    	$(this).css("cursor","hand");
    });
});

//============
//  添删改
//============
$(function(){
	$("#btnAdd").click(function() {
		$('#DetailsDiv').find('input[type=text]').val('').end()
						.find('input[type=password]').val('');
	    if(ResultTable.insertTitle)$('#DetailsDiv').dialog('option', 'title', ResultTable.insertTitle);
	    $('#DetailsDiv').find('#aInsert').show().end()
	                    .find('#aEdit').hide().end()
	                    .dialog('open');
	});

	$("#btnDel").click(function() {
		var ids=ResultTable.getSelected();
		if(ids.length==0){
			jAlert("请先选择要删除的数据！");
			return;
		}
		jConfirm("确定删除?",function(){ del(ids); });
	});

	/*$("#btnEdit").click(function() {
	    $('#DetailsDiv').find('input[type=text]').val('').end()
						.find('input[type=password]').val('');
	    if(ResultTable.editTitle)$('#DetailsDiv').dialog('option', 'title', ResultTable.editTitle);
	    var id=ResultTable.getSelected();
		if(id.length!=1){
			jAlert("请先选择一条修改的数据！");
			return;
		}
		bindDetail(id[0]);
	    $('#DetailsDiv').find('#aInsert').hide().end()
	                    .find('#aEdit').show().end()
	                    .dialog('open');
	});	*/
});
