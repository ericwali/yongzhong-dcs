jQuery(document).ready(function($) {
	var system ={};
	var p = navigator.platform;
	system.win = p.indexOf("Win") == 0;
	system.mac = p.indexOf("Mac") == 0;
	system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
	if(system.win||system.mac||system.xll){//如果是电脑
//		(function(m, ei, q, i, a, j, s) {
//			m[a] = m[a] || function() {
//					(m[a].a = m[a].a || []).push(arguments)
//				};
//			j = ei.createElement(q),
//				s = ei.getElementsByTagName(q)[0];
//			j.async = true;
//			j.charset = 'UTF-8';
//			j.src = i + '?v=' + new Date().getUTCDate();
//			s.parentNode.insertBefore(j, s);
//		})(window, document, 'script', '//eco-api.meiqia.com/dist/meiqia.js', '_MEIQIA');
//		_MEIQIA('entId', 8012);

		if ($("meta[name=toTop]").attr("content") == "true") {
			$("<div id='toTop' title='回到顶部'><img src='img/top.png'></div>").appendTo('body');
			$("#toTop").css({
				width: '50px',
				height: '50px',
				bottom: '10px',
				right: '130px',
				position: 'fixed',
				cursor: 'pointer',
				zIndex: '999999',
			});
			if ($(this).scrollTop() == 0) {
				$("#toTop").hide();
			}
			$(window).scroll(function (event) {
				/* Act on the event */
				if ($(this).scrollTop() == 0) {
					$("#toTop").hide();
				}
				if ($(this).scrollTop() != 0) {
					$("#toTop").show();
				}
			});
			$("#toTop").click(function (event) {
				/* Act on the event */
				$("html,body").animate({
						scrollTop: "0px"
					},
					666
				)
			});
		}
	}

	$(".lev1:first").css("font-weight","bold");

	$(".lev2").bind("click",function(){
		$("a").css("font-weight","normal");
		$(this).css("font-weight","bold");
		var temp=$($(this).attr("href")).parent().attr("class");
		if(temp=="content1"){
			$("."+temp).css("display","block");
			$(".content2").css("display","none");
			$(".content0").css("display","none");
			$(".content3").css("display","none");
		}else if(temp=="content2"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content0").css("display","none");
			$(".content3").css("display","none");
		}
		else if(temp=="content0"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content2").css("display","none");
			$(".content3").css("display","none");
		}
		else if(temp=="content3"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content2").css("display","none");
			$(".content0").css("display","none");
		}
		//var atemp=$(this).attr("href");
		//$("html,body").animate({
		//	scrollTop:$(atemp).offset().top},
		//	666);
	})

	$(".lev1").bind("click",function(){
		$("a").css("font-weight","normal");
		$(this).css("font-weight","bold");
		var temp=$($(this).data("url")).parent().attr("class");
		if(temp=="content1"){
			$("."+temp).css("display","block");
			$(".content2").css("display","none");
			$(".content0").css("display","none");
			$(".content3").css("display","none");
		}else if(temp=="content2"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content0").css("display","none");
			$(".content3").css("display","none");
		}
		else if(temp=="content0"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content2").css("display","none");
			$(".content3").css("display","none");
		}
		else if(temp=="content3"){
			$("."+temp).css("display","block");
			$(".content1").css("display","none");
			$(".content2").css("display","none");
			$(".content0").css("display","none");
		}
	})

});