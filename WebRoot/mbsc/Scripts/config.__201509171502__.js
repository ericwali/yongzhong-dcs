G.config({"baseUrl":"http://sta.yongshei.com/amd/","alias":{"jquery":"lib/jquery/jquery-1.8.2.js","jquery-ui-js":"lib/jquery-ui/jquery-ui-1.10.3.custom.js","jquery-ui-css":"lib/jquery-ui/jquery-ui-1.10.3.custom.css","widget":"lib/widget/widget.js","log":"util/log.cmb.js","event":"lib/event/event.js"},"cacheExpire":604800000,"rootpaths":["http://sta.yongshei.com"],"combine":{},"version":{}});G.config({versionTemplate:function anonymous(obj) {
var p=[],print=function(){p.push.apply(p,arguments);};with(obj){p.push('', url.href.replace(url.ext, '.__' + version + '__' + url.ext) ,'');}return p.join('');
}});