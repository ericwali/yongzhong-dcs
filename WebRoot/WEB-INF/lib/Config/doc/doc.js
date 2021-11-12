window.addEventListener('message',function(e){
    if(e.data == "printDoc"){
		printDoc(); ;
	}
},false);

function printDoc()
{
	if(getExplorer() == "IE"){
            pagesetup_null();
        }
        $(".navbar").hide();
	  $("body").css("padding-top","0px");
	  $(".word-content").each(function(index, element) {
		$(this).find("embed").hide().next("img").show();
      });
        window.print(); 
	  $(".word-content").each(function(index, element) {
		$(this).find("embed").show().next("img").hide();
      });
	  $(".navbar").show();
	  $("body").css("padding-top","60px");
}


function pagesetup_null(){                
    var hkey_root,hkey_path,hkey_key;
    hkey_root="HKEY_CURRENT_USER";
    hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
    try{
        var RegWsh = new ActiveXObject("WScript.Shell");
        hkey_key="header";
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
        hkey_key="footer";
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
    }catch(e){}
}

function getExplorer() {
    var explorer = window.navigator.userAgent ;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return "IE";
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return "Firefox";
    }
    //Chrome
    else if(explorer.indexOf("Chrome") >= 0){
        return "Chrome";
    }
    //Opera
    else if(explorer.indexOf("Opera") >= 0){
        return "Opera";
    }
    //Safari
    else if(explorer.indexOf("Safari") >= 0){
        return "Safari";
	}
}

function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
          sParameterName = sURLVariables[i].split('=');

          if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
          }
        }
      }
      var isMeeting=getUrlParameter('meeting')!=null;
      var isSpeaker=isMeeting && getUrlParameter('speaker');
      var serverhost=getUrlParameter('serverhost');
      var intervalMessage;
      var topValue;
      if(isMeeting &&!isSpeaker) {
        window.addEventListener('message',function(e){
          var msg=JSON.parse(e.data);
          switch(msg.type){
            case 'page':{
              var pos = document.body.clientHeight * msg.param;
              $('html,body').animate({scrollTop:pos},0);
              break;
            }
          }
        },false);
      }
      if(isSpeaker) {
        document.onscroll = function() {
          if(intervalMessage == null) {
            intervalMessage = self.setInterval("bindScroll()",20)
          }
        }
      }

      function bindScroll() {
        if(document.body.scrollTop == topValue) {
          clearInterval(intervalMessage)
          intervalMessage = null;
        }else{
          topValue = document.body.scrollTop;
          var msg={};
          var totalHeight = document.body.clientHeight;
          var scrollDistance = $(window).scrollTop();
          msg.type='page';
          msg.param = scrollDistance/totalHeight;
          parent.postMessage(JSON.stringify(msg),serverhost);
        }
      }