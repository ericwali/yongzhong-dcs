(function(){
	var qqTarget = document.createElement("a");
	qqTarget.setAttribute("target", "_blank");
	qqTarget.setAttribute("href", "http://wpa.qq.com/msgrd?v=3&uin=3467763504&site=qq&menu=yes");
	qqTarget.style.position="fixed";
	qqTarget.style.top="312px";
	qqTarget.style.right="0";
	var qqImg = document.createElement("img");
	qqImg.setAttribute("border", "0");
	qqImg.setAttribute("src", "img/qq.gif");
	qqImg.setAttribute("alt", "点击这里给我发消息");
	qqImg.setAttribute("title", "点击这里给我发消息");
	qqTarget.appendChild(qqImg);
	document.getElementsByTagName("body")[0].appendChild(qqTarget);
})()