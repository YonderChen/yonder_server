/********************************************************************************************************
 * D-Checkbox
 *----------------------------------------------------------------------------------------------------
 * @Desc 美化单选框,复选框
 *----------------------------------------------------------------------------------------------------
 * @Author D.夏亦知非
 * @Email DeclanZhang@gmail.com
 * @QQ 29540200
 * @Blog http://onblur.javaeye.com
 * @Date 2009-10-13
 * @Version 1.0 
 * @JQueryVersion 1.3.2
 **/

(function($){
	
//Checkbox
jQuery.fn.extend({
	d_checkbox:function(method){
		
		var config = {
		    defaultCls: 'zogo-form-checkbox pngFix',
		    hoverCls: 'zogo-form-checkbox-hover pngFix',
		    checkedCls: 'zogo-form-checkbox-checked pngFix'
		};
		
		if(method=='check'){return this.each(function(){
			$(this).attr('checked','checked');
			$(this).next().addClass(config.checkedCls);
		});};
		
		if(method=='uncheck'){return this.each(function(){
			$(this).removeAttr('checked');
			$(this).next().removeClass(config.checkedCls);			
		});};
		
		if(method=='disable'){return this.each(function(){
			$(this).attr('disabled','disabled');
			$(this).next().css('opacity',.3)
						  .unbind('click')
						  .unbind('mouseover')
						  .unbind('mouseout');
		});};
		
		if(method=='enable'){return this.each(function(){
			var target = $(this);
			var item = target.next();
			target.removeAttr('disabled');
			item.css('opacity',1)
				.bind('click',{target:target,item:item},_click)
				.bind('mouseover',{item:item},_mouseover)
				.bind('mouseout',{item:item},_mouseout);
		});};
		
		if(method=='click'){return this.each(function(){
			_click({data:{target:$(this),item:$(this).next()}});
		});};

		return this.each(function(){
			var target = $(this);
			var text = $(this).next();
	        var item = 
			$('<span />').insertAfter(this)
						 .addClass(config.defaultCls)
						 .html(text.html());
						 
	        target.hide();
			text.remove();
			
			item.bind('click',{target:target,item:item},_click)
				.bind('mouseover',{item:item},_mouseover)
				.bind('mouseout',{item:item},_mouseout);
			
			if(target.attr('disabled')){$(this).d_checkbox('disable');}
			if(target.attr('checked')){$(this).d_checkbox('check');}
		});
		
		function _click(event){
			var _target = event.data.target;
			var _item = event.data.item;
			_target.attr('checked', !_target.attr('checked'));
			_item.toggleClass(config.checkedCls);
		}
		
		function _mouseover(event){
			event.data.item.addClass(config.hoverCls);
		}
		
		function _mouseout(event){
			event.data.item.removeClass(config.hoverCls);
		}
		
	}
});


//Radio
jQuery.fn.extend({
	d_radio: function(method){
		
		var config = {
		    defaultCls: 'zogo-form-radio pngFix',
		    hoverCls: 'zogo-form-radio-hover pngFix',
		    checkedCls: 'zogo-form-radio-checked pngFix'
		};
		
		if(method=='disable'){return this.each(function(){
			$(this).attr('disabled','disabled');
			$(this).next().css('opacity',.3)
						  .unbind('click')
						  .unbind('mouseover')
						  .unbind('mouseout');
		});};
		
		if(method=='enable'){return this.each(function(){
			var target = $(this);
			var item = target.next();
			target.removeAttr('disabled');
			item.css('opacity',1)
				.bind('click',{target:target,item:item},_click)
				.bind('mouseover',{item:item},_mouseover)
				.bind('mouseout',{item:item},_mouseout);
		});};
		
		if(method=='click'){return this.each(function(){
			_click({data:{target:$(this),item:$(this).next()}});
		});};
		
		return this.each(function(i){
			var target = $(this);
			var text = $(this).next();
			var item = $('<span />').insertAfter(this)
									.addClass(config.defaultCls)
									.addClass(target.attr('checked') ? config.checkedCls : '')
									.html(text.html());
			
			target.hide();
			text.remove();
	
			item.bind('click',{target:target,item:item},_click)
				.bind('mouseover',{item:item},_mouseover)
				.bind('mouseout',{item:item},_mouseout);
			
			if(target.attr('disabled')){$(this).d_checkbox('disable');}
		});
		
		function _click(event){
			var _target = event.data.target;
			var _item = event.data.item;
			_target.attr('checked', !_target.attr('checked'));
			$(":radio[name='"+_target.attr('name')+"']").next().removeClass(config.checkedCls);
			_item.toggleClass(config.checkedCls);
		}
		
		function _mouseover(event){
			event.data.item.addClass(config.hoverCls);
		}
		
		function _mouseout(event){
			event.data.item.removeClass(config.hoverCls);
		}		
		
	}
});



})(jQuery);