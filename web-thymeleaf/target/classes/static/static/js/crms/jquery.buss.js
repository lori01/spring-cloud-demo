/**
 *  date 部分 start
 */
// 获取指定期限后的日期
function getAfterDate(type,numDay,dtDate){
	var date = parseISO8601(dtDate);
 	type = parseInt(type); //类型
 	numDay = parseInt(numDay); //间隔
  	switch(type){
   		case 2 ://年
  			date.setFullYear(date.getFullYear() + numDay);
  			break;
 		case 1 ://月
			date.setMonth(date.getMonth() + numDay);
			break;
 		case 0 ://天
  			date.setDate(date.getDate() + numDay);
  			break;
 		default:   
 			break;
  	} 
  	return date.getFullYear() +'-' + (date.getMonth()+1) + '-' +date.getDate();
} 

// 支持ie7、ie8的newdate转换
function parseISO8601(dateStringInRange) {
	var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/;
	var date = new Date(NaN);
	var month;
	var  parts = isoExp.exec(dateStringInRange);
	
	if(parts) {
	  month = +parts[2];
	  date.setFullYear(parts[1], month - 1, parts[3]);
	  if(month != date.getMonth() + 1) {
	    date.setTime(NaN);
	  }
	}
	return date;
}
/**
 * date 部分 end
 */ 

