$(function(){
	var span = '<span></span>';
	$('.menu li a').wrapInner(span);
	$('.menu_content ul:gt(0)').hide();
	
	var menu_li = $('.menu li');
	
	$('.menu li').mousedown(function(){
		$(this).addClass('selected')
			   .siblings().removeClass('selected');
		
		var index = menu_li.index(this);
		$('.menu_content ul').eq(index).show()
							 .siblings().hide();
		
		return false;
	});
	
	var span = '<span></span>';
	$('.menu_content li a').wrapInner(span);
	$('.menu_content ul:gt(0)').hide();
	
	var menu_content_li = $('.menu_content li');
	
	$('.menu_content li').mousedown(function(){
		$(this).addClass('selected')
			   .siblings().removeClass('selected');
		
		var index = menu_li.index(this);
		$('.menu_content ul').eq(index).show()
							 .siblings().hide();
		
		return false;
	});
});