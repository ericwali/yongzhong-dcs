 $(function() {
    	var appendStr ='<div id="zoom" style="position: fixed; right: 48px; bottom: 48px; width: 50px; height: 90px;">'+
					   	'<div style="border-radius:18px;width: 36px;height: 36px;background-color: #f2f2f2;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(1)"><img src="http://www.dcsapi.com/cdn/021.png" style="position: relative;top: 2px;opacity: 0.5;"/></div>'+
					   	'<div style="border-radius:18px;width: 36px;height: 36px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(0)"><img src="http://www.dcsapi.com/cdn/022.png" style="position: relative;top: 2px;opacity: 0.5;"/></div>'+
					   '</div>';
		$('body').append(appendStr);		
		$(".container-fluid-content").css('zoom');
    });
    function changeTab(type){
    	var size = $(".container-fluid-content").css('zoom');
    	if(type==1){
    		size *= 1.2;
    	}else if(type==0){
    		size *= 0.8;
    	}
    	$(".container-fluid-content").animate({ 
    		zoom: size
    	  }, 500 );
    }