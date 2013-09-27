<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/crud/jsrender.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/js/crud/jquery.observable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/crud/jquery.views.js"></script>

<!-- Crud -->
<script type="text/javascript">

function Crud(config){
	this.config=$.extend(
			{
				//查询
				searchUrl : null,
				PreSearch : null,
				PostSearch : null,
				//结果
				resultTmpl : null,
				resultDiv : null,
				//分页
				pagerDiv : null,
				pagerTmpl :"#pagerTemplate",
				currentPage : 1,
				pageSize : 10,
				//排序
				sortExp:'',
				sortDir:'',
				sortHead:'',
				PostSort : null,
				//详细
				detialUrl : null,
				detialDiv : null,
				detailTmpl : null,
				PostShowDetial: null,
				//保存
				saveUrl : null,
				PostSave: null
			},
			config);

	var cn = this;			//当前对象
	this.resultJson=null;	//查询结果json
	//查询
	this.SearchParams={};
	this.setSearchParams=function(params){
		this.SearchParams=this.params;
	};
	this.search=function(params){
		this.setSearchParams(params);
		this.gotoPage(1);
	};
	this.refresh=function(){
		if(this.config.PreSearch)this.config.PreSearch.call(this);
		if(cn.config.resultDiv) $(cn.config.resultDiv).html("");
		if(cn.config.pagerDiv) $(cn.config.pagerDiv).html ("");
		var params=$.extend( {},
							 this.SearchParams,
							 this.getPagerParams(),
							 this.getSortParams());
		this.hasResult=false;
		this.resultJson=null;
		$.getJSON(this.config.searchUrl,params)
		 .success(function(json){
			if(json){
				cn.resultJson=json;
				if(cn.config.resultDiv) $(cn.config.resultDiv).html($(cn.config.resultTmpl).render(json));
				if(cn.config.pagerDiv) $(cn.config.pagerDiv).html (cn.toHtml(json));
				
				if(json.currentPage>0)cn.hasResult=true;
			}
		 })
		 .complete(function(){
			 if(cn.config.PostSearch) cn.config.PostSearch.call(cn,cn.hasResult);
		 });
	};

	//分页
	this.getPagerParams=function(){
		return {
			pageSize:   this.config.pageSize,
	        currentPage:this.config.currentPage
	       };
	};
	this.toHtml=function(json){
		var pagenav=[],
			currentPage=json.currentPage,
			totalPage=json.totalPage,
			b=Math.floor((currentPage-1)/10)*10+1,
			e=b+10<totalPage?b+9:totalPage;
		for(var i=b;i<=e;i++) pagenav.push({i:i});
		json.pagenav=pagenav;
		this.config.currentPage=currentPage;
		
		return $(this.config.pagerTmpl).render(json);
	};
	this.gotoPage=function(page){
		this.config.currentPage=page;
		this.refresh();
	};
	if(cn.config.pagerDiv && cn.config.pagerTmpl=="#pagerTemplate"){
		$(cn.config.pagerDiv).find("a[topage]").live("click",function(){ cn.gotoPage($(this).attr('topage'));});
	}
	
	//删除
	this.del = function(id){
		if(! this.config.deleteUrl && this.config.deleteListUrl) return this.delList([id]);
		
		$.getJSON(this.config.deleteUrl,{id:id})
		 .success(function(json){
			 cn.refresh();
		 });
	};
	this.delList=function(idList){
		if(!this.config.deleteListUrl && this.config.deleteUrl){
			$.each(idList,function(i,v){cn.del(v);});
			return;
		}
		$.getJSON(this.config.deleteListUrl,{ids:idList.join()})
		 .success(function(json){
			 cn.refresh();
		 });
	};
	
	//排序
	this.getSortParams = function(){
		return {
			sortExp:this.config.sortExp,
        	sortDir:this.config.sortDir
	       };
	};
	this.setSortParams=function(sortExp,sortDir){
		this.config.sortDir = sortDir?sortDir:(this.config.sortExp==sortExp?(this.config.sortDir=="desc"?"":"desc"):"");
		this.config.sortExp=sortExp;
	};
	this.sort=function(sortExp,sortDir){
		this.setSortParams(sortExp,sortDir);
		this.refresh();
		if(this.config.PostSort) this.config.PostSort.call(this,this.sortExp,this.sortDir);
		else this.PostSortCallback();
	};
	this.PostSortCallback = function(){
		if(this.config.sortHead && !this.config.PostSort){
			$(this.config.sortHead).find("th[sortby]").each(function() { $(this).html($(this).html().replace(/[↑↓]/g, '')); });
			if(this.config.sortExp) $(this.config.sortHead).find("th[sortby="+this.config.sortExp+"]").html($("th[sortby="+this.config.sortExp+"]").html() + (this.config.sortDir == "desc" ? "↑" : "↓"));
		}
	};
	if(this.config.sortHead && !this.config.PostSort){
		$(this.config.sortHead).find("th[sortby]").live("click", function() { cn.sort($(this).attr('sortby')); }).css("cursor","hand");
	}
	
	//详细信息，修改、添加
	this.showEmpty=function(){
		$(this.config.detialDiv).html($(this.config.detialTmpl).render({}));
		if(this.config.PostShowDetial)this.config.PostShowDetial.call(this);
	};
	this.showDetial=function(id){
		$.getJSON(this.detialUrl,{id:id})
		 .success(function(item){
			 $(cn.config.detialDiv).html($(cn.config.detialTmpl).render(item));
		 });
		if(this.config.PostShowDetial)this.config.PostShowDetial.call(this);
	};
	this.save=function(data){
		$.getJSON(this.saveUrl,data)
		 .success(function(json){
			 if(cn.config.PostSave)cn.config.PostSave.call(cn);
			 cn.refresh();
		 });
	};
}
</script>

<script id="pagerTemplate" type="text/x-jsrender">
{{if currentPage>0}}
	<span id='lIndex'>第{{>currentPage}}页</span>
	<span id='lCount'>/共{{>totalPage}}页</span>

	{{if currentPage>1}}
		<a class='bg' topage='1'>首页</a>
		<a class='bg' topage='{{>currentPage-1}}'>上一页</a>
	{{/if}}

	{{for pagenav}}
		{{if i== #parent.parent.data.currentPage}}
			<a class='selected' topage='{{>i}}'>{{>i}}</a>
		{{else}}
			<a class='bg' topage='{{>i}}'>{{>i}}</a>
		{{/if}}
	{{/for}}

	{{if currentPage<totalPage}}
		<a class='bg' topage='{{>currentPage+1}}'>下一页</a> 
		<a class='bg' topage='{{>totalPage}}'>末页</a>
	{{/if}}
{{/if}}
</script>
