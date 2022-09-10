Date.prototype.format = function(fmt) {
  var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt)) {
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  }
  for(var k in o) {
    if(new RegExp("("+ k +")").test(fmt)){
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length===1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    }
  }
  return fmt;
}

function formatDate(day) {
  if(day) {
    var year = day.getYear();
    var month = day.getMonth() + 1;
    var date = day.getDate();
    if(month.toString().length == 1) {
      month = "0" + month;
    }
    if(date.toString().length == 1) {
      date = "0" + date;
    }
    return year + "-" + month + "-" + date;
  } else {
    return "";
  }
}

function mssFormatYMDHMS(mss) {
    if (mss) {
        let date = new Date(mss);
        return date.format("yyyy-MM-dd hh:mm:ss");
    } else {
        return '';
    }
}

// 格式化日期 0000-00-00
function dateFormatYMD(date) {
  // console.log(typeof date);object
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  // let hour = date.getHours();
  month = month < 10 ? '0'+ month : month;
  day = day < 10 ? '0'+ day : day;
  // hour = hour < 10 ? '8'+ hour : hour;
  return year + '-' + month + '-' + day;
}
// 格式化日期 00000000
function dateFormatYMD2(date) {
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  // let hour = date.getHours();
  month = month < 10 ? '0'+ month : month;
  day = day < 10 ? '0'+ day : day;
  // hour = hour < 10 ? '8'+ hour : hour;
  return year + '' + month + '' + day;
}
// 获取当月的第一天
function getNowMonthDay() {
  let date = new Date();
  date.setDate(1);
  let month = date.getMonth() + 1;
  if (month < 10) {
    month = '0' + month;
  }
  return date.getFullYear() + "-" + month + "-01";
}
// 本周开始时间
function getStartDayOfWeek() {
  let date = new Date();
  let nowDay = date.getDate(); // 当前日
  let nowDayOfWeek = date.getDay(); // 今天本周的第几天
  let day = nowDayOfWeek || 7;
  let nowMonth = date.getMonth(); // 当前月
  let newDate = new Date(date.getFullYear(), nowMonth, nowDay + 1 - day);
  return dateFormatYMD(newDate);
}
// 本周开始时间
/*function getEndDayOfWeek() {
  let now = new Date();
  let nowDay = now.getDate(); // 当前日
  let nowDayOfWeek = date.getDay(); // 今天本周的第几天
  let day = nowDayOfWeek || 7;
  let nowMonth = now.getMonth(); // 当前月
  let newDate = new Date(now.getFullYear(), nowMonth, nowDay + 7 - day);
  return dateFormatYMD(newDate);
}*/
// 上周开始时间
function getLastWeekStartDay() {
  let now = new Date(); //当前日期
  let nowDayOfWeek = now.getDay(); //今天本周的第几天
  let nowDay = now.getDate(); //当前日
  let nowMonth = now.getMonth(); //当前月
  let nowYear = now.getFullYear(); //当前年
  // nowYear += (nowYear < 2000) ? 1900 : 0;
  let weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 6);
  return dateFormatYMD(weekStartDate);
}
// 上周结束时间
function getLastWeekEndDay() {
  let now = new Date(); //当前日期
  let nowDayOfWeek = now.getDay(); //今天本周的第几天
  let nowDay = now.getDate(); //当前日
  let nowMonth = now.getMonth(); //当前月
  let nowYear = now.getFullYear(); //当前年
  // nowYear += (nowYear < 2000) ? 1900 : 0;
  let weekEndDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
  return dateFormatYMD(weekEndDate);
}
// 获取上月开始时间
function getLastMonthStartDay() {
  let now = new Date(); //当前日期
  let nowYear = now.getFullYear(); //当前年
  let lastMonthDate = new Date(); //上月日期
  lastMonthDate.setDate(1);
  lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
  let lastMonth = lastMonthDate.getMonth();
  let lastMonthStartDate = new Date(nowYear, lastMonth, 1);
  return dateFormatYMD(lastMonthStartDate);
}
// 获取上月结束时间
function getLastMonthEndDay() {
  let now = new Date(); //当前日期
  let nowYear = now.getFullYear(); //当前年
  let lastMonthDate = new Date(); //上月日期
  lastMonthDate.setDate(1);
  lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
  let lastMonth = lastMonthDate.getMonth();
  let lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
  return dateFormatYMD(lastMonthEndDate);
}
//获得某月的天数
function getMonthDays(myMonth) {
  let now = new Date(); //当前日期
  let nowYear = now.getFullYear(); //当前年
  let monthStartDate = new Date(nowYear, myMonth, 1);
  let monthEndDate = new Date(nowYear, myMonth + 1, 1);
  return (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
}
// 获取当月
function getNowMonth() {
  let date = new Date;
  let month = date.getMonth()+1;
  return month<10 ? "0"+month:month;
}
// 获取当天
function getNowDay() {
  var day2 = new Date();
  day2.setTime(day2.getTime());
  return day2.getDate();
}
// 计算两个日期相差多少天 格式(yyyy-mm-dd)
function caclBetweenDay(s,e) {
  let start = new Date(s);
  let end = new Date(e);
  return Math.abs(end - start) / (1000 * 60 * 60 * 24);
}
function caclBetweenDay62(s,e) {
  if (caclBetweenDay(s,e) > 62) {
    layer.msg("查询时间间隔不能超过2个月哦~", {icon:0});
    return false;
  }
  return true;
}

/**
 * 将秒转换成时分秒格式 00:00:00
 * @param value
 * @returns {string}
 */
function formatSeconds(value) {
  let result = parseInt(value)
  let h = Math.floor(result / 3600) < 10 ? '0' + Math.floor(result / 3600) : Math.floor(result / 3600)
  let m = Math.floor((result / 60 % 60)) < 10 ? '0' + Math.floor((result / 60 % 60)) : Math.floor((result / 60 % 60))
  let s = Math.floor((result % 60)) < 10 ? '0' + Math.floor((result % 60)) : Math.floor((result % 60))
  result = `${h}:${m}:${s}`
  return result
}

/**
 * 根据日期格式字符串获取该日期的时间戳
 * @param str
 * @returns {string}
 */
function getTimeStampByStr(str) {
    let time = new Date(str.replace(/-/,"/"))
    let time_str = time.getTime().toString();
    // return time_str.substr(0, 10);
    return time_str;
}