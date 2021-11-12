/*
0-----文档格式到高清html的转换
1-----文档格式到html的转换
14----pdf文档格式到html的转换
20----PDF文件到html的转换
19----压缩文件到html的转换
*/
 convertParamer = {
	   ms2html : 0,
	   pdf2html:14,
	   zip2html :19,
	   apiUrl : "http://dcs.yozosoft.com/onlinefile"
	}
var mb = myBrowser();
//如果是IE 低版本浏览器，修改为标准版预览
if("IE" == mb){
	var b_version=navigator.appVersion 
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(navigator.appName == "Microsoft Internet Explorer" &&(trim_Version=="MSIE8.0"||trim_Version=="MSIE7.0"||trim_Version=="MSIE6.0")){
		convertParamer.ms2html=1;
	}
}
var myAlert =function (event) {
	event = event ? event : window.event; 
	var obj = event.srcElement ? event.srcElement : event.target; 
	console.info(obj);
	obj.innerHTML="加载中...";
	obj.setAttribute("class", "noDcs");
	removeEvent(obj, "click", myAlert);
	var oldUrl =obj.previousSibling.href;
	var oldName =obj.previousSibling.previousSibling.innerHTML;
	var docUrl = encodeURIComponent(oldUrl);
	var docName = encodeURIComponent(oldName);
	var ext = docUrl.substring(docUrl.lastIndexOf(".")+1);
	var convertType;
	switch(ext){
	case "pdf": 	
		convertType = convertParamer.pdf2html;
		break;
	case "zip": 
		convertType = convertParamer.zip2html;
		break;
	default:
		convertType = convertParamer.ms2html;
	}
	var url=convertParamer.apiUrl+"?isDelSrc=1&convertType="+convertType+"&downloadUrl="+docUrl+"&htmlName="+docName+"&callbackName=callbackFunction";
		// 提供jsonp服务的url地址（不管是什么类型的地址，最终生成的返回值都是一段javascript代码）
	/* var url = "http://dcs.yozosoft.com/onlinefile?convertType=1&downloadUrl=http%3a%2f%2fimg.iyocloud.com%3a8000%2fdoctest.docx&callbackName=callbackFunction&htmlName=ddd";*/
	// 创建script标签，设置其属性
	var script = document.createElement('script');
	script.setAttribute('src', url);
	// 把script标签加入head，此时调用开始
	document.getElementsByTagName('head')[0].appendChild(script);  
}
	function addEvent(obj, type, handle) {
		for (var i = 0; i < obj.length; i++) {
			try { // Chrome、FireFox、Opera、Safari、IE9.0及其以上版本
				obj[i].addEventListener(type, handle, false);
			} catch (e) {
				try { // IE8.0及其以下版本
					obj[i].attachEvent('on' + type, handle);
				} catch (e) { // 早期浏览器
					obj[i]['on' + type] = handle;
				}
			}
		}
	}
 	function removeEvent(obj, type, handle) {
 		 //删除事件  
 		 	try { // Chrome、FireFox、Opera、Safari、IE9.0及其以上版本
 		 		obj.removeEventListener(type, handle, false);
			} catch (e) {
				try { // IE8.0及其以下版本
					obj.detachEvent('on' + type, handle);
				} catch (e) { // 早期浏览器
					obj['on' + type] = "";
				}
			}
	} 
 	function myBrowser(){
 	     var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
 	    var isOpera = userAgent.indexOf("Opera") > -1;
 	     if (isOpera) {
 	         return "Opera"
 	     }; //判断是否Opera浏览器
 	    if (userAgent.indexOf("Firefox") > -1) {
 	         return "FF";
 	     } //判断是否Firefox浏览器
 	    if (userAgent.indexOf("Chrome") > -1){
 	   return "Chrome";
 	  }
 	     if (userAgent.indexOf("Safari") > -1) {
 	         return "Safari";
 	     } //判断是否Safari浏览器
 	    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
 	         return "IE";
 	     }; //判断是否IE浏览器
 	}
	var callbackFunction = function(data) {
		var isIE=navigator.userAgent.toUpperCase().indexOf("MSIE")?true:false; //判断是否是IE浏览器
		var isFirefox=navigator.userAgent.toUpperCase().indexOf("FIREFOX")?true:false;//是否是火狐浏览器
		if (data.result == 0) {
			//var mb = myBrowser();
			  if ("FF" == mb) {
				 window.open(data.data[0],"_blank");
			 }
			 else if ("Chrome" == mb) {
				 var newTarget = document.createElement("a");
					newTarget.setAttribute("href", data.data[0]);
					newTarget.setAttribute("target", "_blank");
					newTarget.click();
			 }
			 else if ("Opera" == mb) {
				 var newTarget = document.createElement("a");
					newTarget.setAttribute("href", data.data[0]);
					newTarget.setAttribute("target", "_blank");
					newTarget.click();
			 }
			 else{
				 	 window.open(data.data[0]);
				// location.href=data.data[0];
			 }
		/*	 if ("Safari" == mb) {
				 location.href=data.data[0];
			 }*/
			/*if(isIE){
				location.href=data.data[0];
			}else if(isFirefox){
				window.open(data.data[0],"_blank");
			}else{
			var newTarget = document.createElement("a");
			newTarget.setAttribute("href", data.data[0]);
			newTarget.setAttribute("target", "_blank");
			newTarget.click();
			}*/
		}else{
			alert(data.message);
		}
		var noDcsObjs = getClassNames("noDcs",'a');
			addEvent(noDcsObjs, "click", myAlert);
		for (var i = 0; i < noDcsObjs.length; i++) {
			noDcsObjs[i].innerHTML="[预览]";
//			noDcsObjs[i].setAttribute("src", "http://dcs.yozosoft.com/img/fdj.png");  
//			noDcsObjs[i].setAttribute("width", "16px");
//			noDcsObjs[i].setAttribute("height", "16px");
//			noDcsObjs[i].setAttribute("class", "useDcs");
		}
	};


/*
	document.getElementsByClassName = function (className, element) {
        var children = (element || document).getElementsByTagName('*');
        var elements = new Array();
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            var classNames = child.className.split(' ');
            for (var j = 0; j < classNames.length; j++) {
                if (classNames[j] == className) {
                    elements.push(child);
                    break;
                }
            }
        }
        return elements;
    };*/
	
function getClassNames(classStr,tagName){ 
	if (document.getElementsByClassName) { 
		return document.getElementsByClassName(classStr) 
	}else { 
		var nodes = document.getElementsByTagName(tagName),ret = []; 
			for(i = 0; i < nodes.length; i++) { 
				if(hasClass(nodes[i],classStr)){ 
				ret.push(nodes[i]) 
				} 
			} 
		return ret; 
	} 
} 
function hasClass(tagStr,classStr){ 
	var arr=tagStr.className.split(/\s+/ ); //这个正则表达式是因为class可以有多个,判断是否包含 
		for (var i=0;i<arr.length;i++){ 
			if (arr[i]==classStr){ 
			return true ; 
			} 
		} 
	return false ; 
} 
//兼容浏览器
// 获取下一个紧邻的兄弟元素
function getNextElement(element) {
  // 能力检测
 if(element.nextElementSibling) {
   return element.nextElementSibling;
  } else {
     var node = element.nextSibling;
     while(node && node.nodeType !== 1) {
         node = node.nextibling;
     }
     return node;
  }
 }

/**
* 返回上一个元素
* @param element
* @returns {*}
*/
function getPreviousElement(element) {
  if(element.previousElementSibling) {
    return element.previousElementSibling;
  }else {
    var el = element.previousSibling;
    while(el && el.nodeType !== 1) {
      el = el.previousSibling;
      }
    return el;
  }
}

/**
* 返回第一个元素firstElementChild的浏览器兼容
* @param parent
* @returns {*}
*/
function getFirstElement(parent) {
  if(parent.firstElementChild) {
    return parent.firstElementChild;
  }else {
    var el = parent.firstChild;
    while(el && el.nodeType !== 1) {
      el = el.nextSibling;
      }
    return el;
  }
}

/**
* 返回最后一个元素
* @param parent
* @returns {*}
*/
function getLastElement(parent) {
  if(parent.lastElementChild) {
    return parent.lastElementChild;
  }else {
    var el = parent.lastChild;
    while(el && el.nodeType !== 1) {
      el = el.previousSibling;
      }
    return el;
  }
}

/**
*获取当前元素的所有兄弟元素
* @param element
* @returns {Array}
*/
function sibling(element) {
  if(!element) return ;
   
  var elements = [ ];
  var el = element.previousSibling;
  while(el) {
    if(el.nodeType === 1) {
      elements.push(el);
    }
    el = el.previousSibling;
  }
   el = element.previousSibling;
   while(el ) {
    if(el.nodeType === 1) {
      elements.push(el);
    }
    el = el.nextSibling;
  }
    return elements;
}

//页面加载时执行方法
	window.onload =function () {
		var num = document.getElementById("Zoom").getElementsByTagName("a")
		for(var i=0;i<num.length;i++){
			num.item(i).style.textDecoration="none"
		}
		/* <img class="useDcs" height="16px" width="16px" src="img/fdj.png" style="vertical-align:middle;"/> */
		var eles = document.getElementsByTagName("a");
		eles = Array.prototype.slice.call(eles);
		for (var i = 0; i < eles.length; i++) {
			var _href = eles[i].href;
			//var _href = eles[i].innerHTML;
				//if(href.endsWith("doc")||href.endsWith("docx")||href.endsWith("pdf")||href.endsWith("xls")||href.endsWith("xlsx")||href.endsWith("ppt")||href.endsWith("pptx")){
			if(_href.indexOf(".zip")>0||_href.indexOf(".doc")>0||_href.indexOf(".docx")>0||_href.indexOf(".pdf")>0||_href.indexOf(".xls")>0||_href.indexOf(".xlsx")>0||_href.indexOf(".ppt")>0||_href.indexOf(".pptx")>0){
				var download = document.createElement("a");
//				download.setAttribute("class", "nofordcs");
				download.setAttribute("href", ""+eles[i].href+"");
				download.innerHTML = "[下载]";
				download.setAttribute("target","_blank")
				download.style.marginLeft="10px";
				download.style.textDecoration="none";
				var preview = document.createElement("a");
				preview.className = 'useDcs'; 
			//	preview.setAttribute("class", "useDcs");
				preview.setAttribute("href", "javascript:void(0)");
				
				preview.innerHTML = "[预览]";
				preview.style.marginLeft="10px";
				preview.style.textDecoration="none";
				  var parent = eles[i].parentNode;	
//				   if ( parent.lastChild == eles[i] )
//				   {
//				        // 如果最后的节点是目标元素，则直接添加。因为默认是最后
//				        parent.insertBefore(img);
//				   }
//				   else
//				   {
//				        //如果不是，则插入在目标元素的下一个兄弟节点的前面。也就是目标元素的后面
//				        parent.insertBefore( img, eles[i].nextSibling );
//				   }
				  
				  parent.insertBefore( preview, eles[i].nextSibling );
				  parent.insertBefore( download, eles[i].nextSibling );
			}
		}
	addEvent(getClassNames("useDcs",'a'), "click", myAlert);
	};
