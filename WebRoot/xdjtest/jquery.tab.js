/*

 * Filename(文件名): jquery.tab.js
 * Description(描述): jquery tab
 * Version(版本): 1.0.0(2009-09-21)
 * Website(站点): http://www.ue360.net/
 * Author(作者): Kelvin Chow

*/

;(function($){
	$.ui = $.ui || {};
	$.fn.tab = function(options) {
        return this.each(function() {
			var opts = $.extend({},options);
			if(opts !== false) new $.ui.tab(this, opts);
		});
    };

	$.ui.tab = function(obj, options) {
		this.settings = $.extend({}, $.ui.tab.settings, options);
		this.element = $(obj);

		this.refresh();
		this.attachEvents();
	}
	$.ui.tab.prototype = {
		refresh: function() {
			this.tabs = this.element.find(this.settings.tabs);
			this.box = this.element.find(this.settings.box);

			this.index = typeof this.settings.index == 'function' ? this.settings.index.call(this) : this.settings.index;
			if (this.index < 0 || this.index > this.element.find(this.settings.tabs).length - 1) {
				this.index = 0;
			}
			if (this.settings.cookies) {
				this.index = $.cookie(this.settings.cookies) == null ? this.index : $.cookie(this.settings.cookies);
			}
			
			this.tabs.removeClass(this.settings.cur).eq(this.index).addClass(this.settings.cur);
			this.box.hide().eq(this.index).show();
			if (this.settings.onSet) {
				this.settings.onSet(this);
			}
		},
		attachEvents: function() {
			var _this = this;
			this.tabs.each(function(index) {
				var $this = $(this);
				$this.bind(_this.settings.evtype, function(e) {
					if (!$this.hasClass(_this.settings.cur)) {
						_this.index = index;
						if (_this.settings.cookies) {
							$.cookie(_this.settings.cookies, _this.index);
						}
						setTimeout(function() {
							_this.tabs.removeClass(_this.settings.cur).eq(index).addClass(_this.settings.cur);
							_this.box.hide().eq(index).show();
						}, _this.settings.delay)
						if (_this.settings.onSelect) {
							_this.settings.onSelect(_this);
						}
						e.stopPropagation();
						return false;
					}
				});
			})
		}
	}
	$.extend($.ui.tab, {
		settings: {
			delay: 0,
			tabs: ">ol li",
			index: 0,
			cur: 'cur',
			evtype: "click",
			box: ">.main",
			cookies: false,
			onSet: undefined,
			onSelect: undefined
		}
	});
})(jQuery);