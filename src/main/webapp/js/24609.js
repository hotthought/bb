function open_dialog_guide(i){
	var l=document.getElementById("mainpage");
var m=document.getElementById("dialog_guide");m.style.display="block";l.style.display="none";
var j=document.getElementById("urls");if(j.innerHTML==""){var h="";var k="";for(key in dialog_url){if(dialog_url[key].url){h+='<li><a href="'+add_pos(dialog_url[key].url,i)+'" style="background-position:3px -'+(key*38)+'px;">'+dialog_url[key].name+"</a></li>"}}j.innerHTML=h}}
each(document.querySelectorAll(".open_guide"),function(){this.onclick=function(){open_dialog_guide(108)}});each(document.querySelectorAll(".aclose"),function(){var c=this;c.onclick=function(){each(document.querySelectorAll(".dialog"),function(){this.style.display="none"});document.querySelector("#mainpage").style.display="block"}});function add_pos(d,e){if(d.indexOf("?")>=0){d=d+"&amp;"}else{d=d+"?"}return d+"pos="+e}var f_interface="";function friend_read_init(){if(!sid||!gsid||!uid){var c=f_interface+"?call_back=getFriendReadData&no_html=1&source=wapjscn"}else{var c=f_interface+"?user_id="+uid+"&auth_type=2&skey="+sid+"&auth_key="+gsid+"&call_back=getFriendReadData&no_html=1&source=wapjscn"}createScript(c)}function getFriendReadData(f){if(!f){return false}else{var g="";g+='<div class="bar_nav b_top">';g+='<ul><li class="onlychild on"><a href=""></a></li></ul><p class="top"><a href="#top" title="返回顶部"></a></p></div>';g+='<div class="nav_content_list"><div class="nav_content_item"><ul class="news_list">';for(key in f){g+='<li><a href="'+f[key]["url"]+'">'+cutstr(f[key]["title"],24)+"</a></li>"}g+='</ul><div class="list_more"><a href="" title="">&gt;&gt;</a></div></div></div>';var e=document.querySelector("#friend_read");e.innerHTML=g}}var t_interface="";function user_ttt_init_wapjs_cn(){if(!sid||!gsid){return false}var c=t_interface+"?type=js&c=wapjs_cn&s="+sid+"&unixtimestamp="+timestamp+"&gsid="+gsid+"&callback=get_data_from_json";createScript(c)}function get_data_from_json(b){if(typeof b.errno!="undefined"){return false}else{var e=Number(b.comment)+Number(b.message)+Number(b.notice)+Number(b.atmsg)+Number(b.atcmt)+Number(b.newfans);create_wb_html(e)}}function create_wb_html(d){var e=document.querySelector("#wb_cnt");if(d>0){message_html=(d>99)?"99+":d;message_html="<em>"+message_html+"</em>";e.innerHTML="微博"+message_html}}function addLoadEvent(d){var e=window.onload;if(typeof window.onload!="function"){window.onload=d}else{window.onload=function(){e();d()}}}function createScript(e){var g=document.createElement("script");g.src=e;g.charset="utf-8";var f=document.getElementsByTagName("head")[0];f.appendChild(g)}function addListener(e,f,g){if(e.addEventListener){e.addEventListener(f,g,false)}else{e.attachEvent("on"+f,g)}}addListener(window,"DOMContentLoaded",function(){user_ttt_init_wapjs_cn();});function cutstr(i,m){var k=0;var j=0;var h=0;str_cut=new String();i=htmldecode(i);j=i.length;for(var l=0;l<j;l++){a=i.charAt(l);h++;if(escape(a).length>4){h++}}for(var l=0;l<j;l++){a=i.charAt(l);k++;if(escape(a).length>4){k++}str_cut=str_cut.concat(a);if(k>m&&h>27){str_cut=str_cut.concat("...");return str_cut}}if(k<=27){return i}}function htmlencode(c){c=c.replace(/&/g,"&amp;");c=c.replace(/</g,"&lt;");c=c.replace(/>/g,"&gt;");c=c.replace(/(?:t| |v|r)*n/g,"<br/>");c=c.replace(/  /g,"&nbsp; ");c=c.replace(/t/g,"&nbsp; &nbsp; ");c=c.replace(/x22/g,"&quot;");c=c.replace(/x27/g,"&#39;");return c}
function htmldecode(c){c=c.replace(/&amp;/gi,"&");c=c.replace(/&nbsp;/gi," ");c=c.replace(/&quot;/gi,'"');c=c.replace(/&#39;/g,"'");c=c.replace(/&lt;/gi,"<");c=c.replace(/&gt;/gi,">");c=c.replace(/<br[^>]*>(?:(rn)|r|n)?/gi,"n");return c}
function search_box(){
	var f=document.getElementById("parent");
	var e=f.getElementsByTagName("li");
	for(var g=0;g<e.length;g++){e[g].onclick=function(){this.innerHTML=(this.innerHTML=="")?"全部":this.innerHTML;document.getElementById("searchcont").innerHTML=this.innerHTML;
	if(this.title=="ttt_user"){document.getElementById("searchBoxBor").className="searchBoxBors"}
	else{
		document.getElementById("searchBoxBor").className="searchBoxBor"
		}
	//document.getElementById("search_type_input").value=this.innerHTML;
	document.getElementById("search_type_input").value=this.value;
	document.getElementById("searchPanel").style.display="none"}}}
function display_searchPanel(){var c=document.getElementById("searchPanel");c.style.display="block";search_box()}var SSEtimer;var ua=navigator.userAgent;var index_storage=window.localStorage;window.onload=function(){};function closeUserGuide(d){var b=$(d).parents(".userGuide")[0];document.body.removeChild(b);index_storage.userGuide=1;var c=getGuideTop()}function loadModule(){if(!supportCookie()){return}var c=cookieToArray();var e={};var j=/^slide_(\d+)_index$/;for(var h=0;h<c.length;h++){var d=c[h],b=d.split("=");tName=b[0],tValue=b[1];var g=j.exec(tName);if(g){var f=tValue;e[tName]=tValue}}$.each($(".nav_content_list"),function(q,p){var l="slide_"+q+"_index";var t=$(p);var k=t.prev();k.find("li").removeClass("on");var s=t.attr("data-role");var o=s?(s=="unslide"?false:true):true;if(!o){return}if(e[l]){var m=k.find("li").eq(e[l]);if(m.length){m.addClass("on")}}else{var r=(k.find(".on").size()>0)?r=k.find(".on").index():0;k.find("li").eq(r).addClass("on")}})}function supportCookie(){return document.cookie?true:false}function setCookie(b,c){if(!supportCookie){return}cookieOperate(b,c,"set")}function cookieOperate(b,n,m){var k=cookieToArray(),h=[],d=true;for(var j=0;j<k.length;j++){var o=k[j];var e=o.split("=");var g=o[0],c=o[1];if(g&&b){if(g==b){d=false;var l="";switch(m){case"set":l=b+"="+n;var f=new Date();f.setTime(f.getTime()+365*24*60*60*1000);document.cookie=b+"="+n+";expires="+f.toGMTString();return}}}}if(d&&m=="set"&&b){var f=new Date();f.setTime(f.getTime()+365*24*60*60*1000);document.cookie=b+"="+n+";expires="+f.toGMTString()}}function cookieToArray(){var c="; "+document.cookie;var b=c.split(/;\s+/);return b}function userGuide(){if(!index_storage.userGuide){var d=document.createElement("div");d.setAttribute("class","userGuide hid");var e="<div class='pop_cont'><div class='userGuideImg'></div><a href='javascript:void(0)' onclick='closeUserGuide(this)' class='closeUserGuide'>知道了,开始体验~</a></div>";
d.innerHTML=e;
var h=document.documentElement;
var f=Math.max(h.clientHeight,h.scrollHeight,h.offsetHeight);
d.style.height=f+"px";
document.body.appendChild(d);
var g=getGuideTop();
var c=$(d).find(".pop_cont");
var b=g+100;c.css({"margin-top":b+"px"});
d.style.display="block"}}
function getGuideTop(){var d=0;var f=$(".mainlistsbox");
	if(f.size()>0){var c=f.eq(0);var e=c.find(".nav_content_list");var b=e.eq(0);d=b.offset().top-100}return d}loadModule();addOnPageShow(function(){addOnscroll(function(){var b=$(".nav_content_item[data-role=lazy]");$.each(b,function(l,j){var c=$(j).index();var d=$(j).parents(".nav_content_list").eq(0).prev();var f=d.find(".on");var o=(f.size()>0)?f.index():0;if(c!=o&&d.find("li").length>1){return}var h=document.documentElement.scrollTop||document.body.scrollTop;var g=document.documentElement.clientHeight;var e=j.getBoundingClientRect().top;var k=e-h;var p=f.text();if(e<0||(e>0&&k<=1.5*g)){var m=$(j).find("img");if(!j.getAttribute("data-unload")){$.each(m,function(i,n){var q=n.getAttribute("data-src");n.src=q});j.setAttribute("data-unload",0)}}})})});$.each($(".slide_img_list"),function(e,h){var g=$(h);var b=g.attr("id");if(!b){return}if(!window.carousel||ua.indexOf("msie")>-1){return}var d=g.find(".img_box");if(g.length&&d.length){d.show()}var c=new carousel(b,{vertical:false,horizontal:true,pagingDiv:g.next()[0],pagingCssName:"img_dot",pagingCssNameSelected:"img_dot active",pagingFunction:function(i){var j;if(this.childrenCount>1&&this.carouselIndex>0){var k=g.find(".img_box").eq(this.carouselIndex).find("img");if(k.size()>0){$.each(k,function(n,m){var l=$(m);var o=l.attr("data-src");if(o){l.attr("src",o)}})}}}});var f=setInterval(function(){autoSlide(c)},20000)});function autoSlide(b){var d=b.carouselIndex;var c=d;if(b.childrenCount>1&&d==b.childrenCount-1){c=0}else{c=d+1}b.onMoveIndex(c)}$.each($(".nav_content_list"),function(g,e){var o=$(e);var b=o.prev();var c=b.find(".on");var l=(c.size()>0)?c.index():0;var f=o.find(".nav_content_item").eq(l);var j=f.find("img");if(j.size()>0&&!f.attr("data-role")){$.each(j,function(q,i){var p=$(i);var n=p.attr("data-src");if(n){p.attr("src",n)}})}var m=o.attr("data-role");var d=m?(m=="unslide"?false:true):true;if(!d){return}if(!window.carousel||ua.indexOf("msie")>-1){return}var k=o.find(".nav_content_item");if(o.length&&k.length){k.show()}var h=o.carousel({vertical:false,horizontal:true,carouselIndex:l,pagingFunction:function(i){if(b.find("li").size()>1){b.find("li").removeClass("on");var p=b.find("li").eq(i);p.addClass("on")}setCookie("slide_"+g+"_index",this.carouselIndex);var n=o.find(".nav_content_item").eq(this.carouselIndex).find("img");if(n.size()>0){$.each(n,function(r,t){var q=$(t);var s=q.attr("data-src");if(s){q.attr("src",s)}})}}});b.find("li").click(function(i){i.stopPropagation();var p=this;var n=$(p).index();h.onMoveIndex(n)});SSEchange()});
function SSEchange(){
	if(SSEtimer){clearInterval(SSEtimer)}
	var d=document.querySelector(".stockBox");
	var b=d.parentNode;
	var c=document.querySelectorAll(".stock_text");
	if(!c.length||getStyle(b,"display")==="none"){return}
	SSEtimer=setInterval(function(){
		if(index(d.querySelector(".hidden"))===2){
			removeClass(c[1],"hidden");
			addClass(c[0],"hidden")
		}
		else{
			removeClass(c[0],"hidden");
			addClass(c[1],"hidden")}},4000)
		}
	function preventDef(c){
		var b=c||window.event;
		return b.preventDefault?b.preventDefault():b.returnValue=false}
		function each(b,e){if(!b||!b.length||typeof e!=="function"){return}for(var d=0,c=b.length;d<c;d++){e.call(b[d],d)}}
		function addClass(c,b){
			if(!this.hasClass(c,b)){
				c.className+=" "+b}}
		function removeClass(d,b){
			if(hasClass(d,b)){var c=new RegExp("(\\s|^)"+b+"(\\s|$)");
			d.className=d.className.replace(c," ")}}
		function hasClass(c,b){return c.className.match(new RegExp("(\\s|^)"+b+"(\\s|$)"))}
		function index(c){
			var b=0,d=c;
			while(d){
				if("previousSibling"in d){
					d=d.previousSibling;
					if(d&&d.nodeType!==1){
						continue
						}
						b=b+1}
					else{
						b=-1;
						break
						}
					}
					return b
				}
		function getStyle(d,c){
			var b;
			if(typeof b==="string"){
				b=document.getElementById(d)
				}
				if("tagName"in d){
					b=d
					}
					if(b.currentStyle){
						var e=b.currentStyle[c]}
					else{
						if(window.getComputedStyle){
							var e=document.defaultView.getComputedStyle(b,null).getPropertyValue(c)
							}
						}
					return e
					}
		function triggerClick(c){
			if(c.click){
				c.click()
				}
			else{
				try{
					var b=document.createEvent("Event");
					b.initEvent("click",true,true);
					c.dispatchEvent(b)
					}
				catch(d){
					alert(d)
					}
					}
				}
		function getEleLeft(b){
			var d=b.offsetLeft;
			var c=b.offsetParent;
			while(c!==null){
				d+=c.offsetLeft;
				c=c.offsetParent
				}
				return d
				}
		function getEleTop(b){
			var d=b.offsetTop;
			var c=b.offsetParent;
			while(c!==null){
				d+=c.offsetTop;
				c=c.offsetParent
				}
				return d
				}
		function addOnPageShow(c){
			if(typeof window.onpageshow=="function"){
				var b=window.onpageshow;
				window.onpageshow=function(){
					b();
					c()
					}
				}
				else{window.onpageshow=function(){c()}}
				}
		function addOnscroll(b){
			if(typeof window.onscroll=="function"){
				var c=window.onscroll;
				window.onscroll=function(){c();b()}}
				else{window.onscroll=function(){b()}}
			};