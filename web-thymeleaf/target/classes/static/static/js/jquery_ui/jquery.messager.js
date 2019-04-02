(function() {
  var ua = navigator.userAgent.toLowerCase();
  var is = (ua.match(/\b(chrome|opera|safari|msie|firefox)\b/) || [ '',
  'mozilla' ])[1];
  var r = '(?:' + is + '|version)[\\/: ]([\\d.]+)';
  var v = (ua.match(new RegExp(r)) || [])[1];
  //jQuery.browser.is = is;
  //jQuery.browser.ver = v;
  //jQuery.browser[is] = true;
})();


//messages
var messagerjs={};


(function(jQuery) {
  this.version = '@1.5';
  this.layer = {
  'width' :200,
  'height' :100
  };

  this.title = '消息提醒';
  this.time = 4000;
  this.anims = {
  'type' :'slide',
  'speed' :400
  };

  //no use this,use animss trance anims

  var animss= {
     'type' :'slide',
     'speed' :400
  };

  this.timer1 = null;
  this.inits = function(title, text) {
  if ($("#message").is("div")) {
       return;
  }
  $(document.body)
  .prepend(
  '<div id="message" style="-webkit-box-shadow: 5px 5px 15px #9C9C9C;-moz-box-shadow: 5px 5px 15px #9C9C9C;box-shadow: 5px 5px 15px #9C9C9C;*box-shadow: 5px 5px 15px #9C9C9C; border-radius:5px; margin:20px;z-index:100;width:'
  + this.layer.width
  + 'px;height:'
  + this.layer.height
  + 'px;position:absolute; display:none;background:#7FC2D5; bottom:0; right:0; overflow:hidden;"><div style="border-bottom:none;width:100%;height:25px;font-size:12px;overflow:hidden;color:#000000;font-weight:bold;"><span id="message_close" style="float:right;padding:0px 0 5px 0;width:16px;line-height:auto;color:#000;font-size:14px;font-weight:bold;text-align:center;cursor:pointer;overflow:hidden;">&times;</span><div style="padding:5px 0 5px 5px;width:100px;line-height:18px;text-align:left;overflow:hidden;">'
  + title
  + '</div><div style="clear:both;"></div></div> <div style="padding-bottom:5px;border-top:none;width:100%;height:auto;font-size:12px;"><div id="message_content" style="margin:0 5px 0 5px; background:#FFFFFF;padding:10px 0 10px 5px;font-size:12px;width:'
  + (this.layer.width - 17)
  + 'px;height:'
  + (this.layer.height - 50)
  + 'px;color:#333333;text-align:left;overflow:hidden;">'
  + text + '</div></div></div>');

  $("#message_close").click( function() {
     //setTimeout('this.close()',1);
     setTimeout('messagerjs.close()', 1);
  });

  $("#message").hover( function() {
      clearTimeout(timer1);
      timer1 = null;

  }, function() {
     if (time > 0)
     //timer1 = setTimeout('this.close()', time);
     timer1 = setTimeout('messagerjs.close()', time);
  });

  $(window).scroll(
  function() {
     var bottomHeight =  "-"+document.documentElement.scrollTop;
     $("#message").css("bottom", bottomHeight + "px");
  });
  };

  this.show = function(title, text, time) {
      if ($("#message").is("div")) {
      return;
  }
  if (title == 0 || !title)
  title = this.title;
  this.inits(title, text);
  if (time >= 0)
  this.time = time;
  
  //use this
  animss=this.anims;
  switch (this.anims.type) {
  case 'slide':
  $("#message").slideDown(this.anims.speed);
  break;
  case 'fade':
  $("#message").fadeIn(this.anims.speed);
  break;
  case 'show':
  $("#message").show(this.anims.speed);
  break;
  default:
  $("#message").slideDown(this.anims.speed);
  break;
  }

  var bottomHeight =  "-"+document.documentElement.scrollTop;
  $("#message").css("bottom", bottomHeight + "px");

  /**

  if ($.browser.is == 'chrome') {

  setTimeout( function() {

  $("#message").remove();

  this.inits(title, text);

  $("#message").css("display", "block");

  }, this.anims.speed - (this.anims.speed / 5));

  }*/

  this.rmmessage(this.time);
  };
  this.lays = function(width, height) {
  if ($("#message").is("div")) {
  return;
  }
  if (width != 0 && width)
  this.layer.width = width;
  if (height != 0 && height)
  this.layer.height = height;
  }
  this.anim = function(type, speed) {
  if ($("#message").is("div")) {
  return;
  }
  if (type != 0 && type)
  this.anims.type = type;
  if (speed != 0 && speed) {
  switch (speed) {
  case 'slow':
  ;
  break;
  case 'fast':
  this.anims.speed = 200;
  break;
  case 'normal':
  this.anims.speed = 400;
  break;
  default:
  this.anims.speed = speed;
  }
  }
  }


  this.rmmessage = function(time) {
  if (time > 0) {
     //timer1 = setTimeout('this.close()', time);
     timer1 = setTimeout('messagerjs.close()', time);
  }
  };

  //this.close=function(){
  messagerjs.close = function() {
  switch (animss.type) {
  case 'slide':
  $("#message").slideUp(animss.speed);
  break;
  case 'fade':
  $("#message").fadeOut(animss.speed);
  break;
  case 'show':
  $("#message").hide(animss.speed);
  break;
  default:
  $("#message").slideUp(animss.speed);
  break;
  }
  ;
  setTimeout('$("#message").remove();', animss.speed);
  }
  /*this.original = function() {
  this.layer = {
  'width' :200,
  'height' :100
  };
  this.title = '信息提示';
  this.time = 4000;
  this.anims = {
  'type' :'slide',
  'speed' :600
  };
  };*/
  jQuery.messager = this;
  return jQuery;
})(jQuery);
