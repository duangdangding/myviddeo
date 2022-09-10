// 关闭加载层
function closeLoad() {
  // 关闭所有的加载层
  layer.closeAll('loading');
  layer.close(load_index);
}
// 显示加载层
var load_index;
function showLoad() {
  load_index = layer.msg('请稍等...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: 'auto', time:100000});
  return load_index;
}
/*layer 消息窗*/
// 0叹号，i=1对号,i=2 错误，3问号，4锁，5不开心，6开心
function show_l_m(m,i) {
  layer.msg(m,{icon: i,time:1000})
}
function show_err_msg(m) {
  show_l_m(m,5)
}
function show_suc_msg(m) {
  show_l_m(m,6)
}
function serverErr() {
  layer.msg("server_err");
}
// 上传文件 上传图片
// 1,弹出消息2，运行函数3，既弹消息又运行函数
function upload_file(url,data,fun,o,p) {
  showLoad()
  $.ajax({
    type: 'post',
    url: url, //txy上传文件的请求路径必须是绝对路劲
    data: data,
    cache: false,
    // async:false,
    processData: false,  //  告诉jQuery不要去处理发送的数据
    contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
    // mimeType: "multipart/form-data",
    success:function (data) {
      let showMsg = data.result ? data.result : data.msg;
      let icon = data.status === 1 ? 6 : 5;
      let r_s = data.status === 1 ? p : o;
      jcAjax_result(showMsg,fun,r_s,icon);
    },
    complete:closeLoad,
    error:serverErr
  });
}

/**
 * 关闭所有page页
 * */
function closelayer(fun){
  layer.closeAll('page'); //关闭所有页面层
  if (fun) {
    fun()
  }
}
function closelayer2(t,fun){
  if (!t) {
    t = 'page';
  }
  layer.closeAll(t); //关闭所有页面层
  if (fun) {
    fun()
  }
}

/**
 * 关闭当前layer弹窗iframe
 */
function closeIframe() {
  //当你在iframe页面关闭自身时
  let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
  parent.layer.close(index); //再执行关闭
}

/**
 * ajax基础提交
 * @param u 链接
 * @param data 参数
 * @param ty 类型 get post
 * @param fun 执行成功之后要运行的函数
 * @param f 失败 1,弹出消息2，运行函数3，既弹消息又运行函数
 * @param s 成功 1,弹出消息2，运行函数3，既弹消息又运行函数
 */
function jcAjax(u,data,ty,fun,f,s) {
  f = f ? f : 3;
  s = s ? s : 1;
  showLoad()
  $.ajax({
    url: u,
    data: data,
    type: ty,
    dataType:'json',
    success:function (data) {
      let showMsg = data.result ? data.result : data.msg;
      let state = data.status;
      let icon = state === 1 ? 6 : 5;
      let r_s = state === 1 ? s : f;
      jcAjax_result(showMsg,fun,r_s,icon);
    },
    complete:closeLoad
  })
}
// 去掉complete事件 防止重复提交
function noComlleteAjax(u,data,ty,fun,f,s) {
  f = f ? f : 3;
  s = s ? s : 1;
  showLoad()
  $.ajax({
    url: u,
    data: data,
    type: ty,
    dataType:'json',
    success:function (data) {
      let showMsg = data.result ? data.result : data.msg;
      let state = data.status;
      let icon = state === 1 ? 6 : 5;
      let r_s = state === 1 ? s : f;
      jcAjax_result(showMsg,fun,r_s,icon);
    }
  })
}
// 同步加载
function unAsyncAjax(u,data,ty,fun,o,p) {
  o = o ? o : 3;
  p = p ? p : 1;
  showLoad()
  $.ajax({
    url: u,
    data: data,
    async:false,
    type: ty,
    dataType:'json',
    success:function (data) {
      let showMsg = data.result ? data.result : data.msg;
      let state = data.status;
      let icon = state === 1 ? 6 : 5;
      let r_s = state === 1 ? p : o;
      jcAjax_result(showMsg,fun,r_s,icon);
    },
    complete:closeLoad
  })
}
// 失败运行和成功运行函数
function ajaxSunFailFun(u,data,ty,fun1,fun2) {
  showLoad()
  $.ajax({
    url: u,
    data: data,
    async:false,
    type: ty,
    dataType:'json',
    success:function (data) {
      let showMsg = data.result ? data.result : data.msg;
      let state = data.status;
      let icon = state === 1 ? 6 : 5;
      let fun = state === 1 ? fun1 : fun2;
      jcAjax_result(showMsg,fun,2,icon);
    },
    complete:closeLoad
  })
}
// 不加载lodding层
function jcAjax_n(u,d,ty,fun,o,p) {
  o = o ? o : 3;
  p = p ? p : 1;
  $.ajax({
    url: u,
    data: d,
    type: ty,
    async:false,
    dataType:'JSON',
    success:function (data) {
      if (data.status === -1) {
        jcAjax_result(data.msg,fun,o,5);
      } else {
        jcAjax_result(data.result,fun,p,6);
      }
    },
    complete:function () {
    }
  })
}
// 1,弹出消息2，运行函数3，既弹消息又运行函数
function jcAjax_result(data,fun,o,i) {
  if (o === 1) {
    show_l_m(data,i)
  } else if (o === 2) {
    if (fun) {
      fun(data)
    }
  } else if (o === 3) {
    show_l_m(data,i)
    if (fun) {
      fun(data)
    }
  }
}
// 无参请求成功，执行函数
function noParam_sucfun_get(u,fun) {
  noParam_get(u, fun,1,2);
}
function noParam_sucAndfun_get(u,fun) {
  noParam_get(u, fun,1,3);
}
// 有参get请求成功，执行函数
function param_sucfun_get(u,data,fun) {
  param_get(u,data,fun,1,2);
}
function param_sucAndfun_get(u,data,fun) {
  param_get(u,data,fun,1,3);
}
// 有参get请求成功，弹出消息
function param_suc_get(u,data,fun) {
  param_get(u,data,fun,1,1);
}

/**
 * 有参get请求方法
 * @param u
 * @param data
 * @param fun
 * @param o
 * @param p
 */
function param_get(u,data,fun,o,p) {
  jcAjax(u,data,'get',fun,o,p)
}
/**
 * 不带请求参数的get方法
 * @param u
 * @param fun
 * @param o
 * @param p
 */
function noParam_get(u,fun,o,p) {
  jcAjax(u,{},'get',fun,o,p)
}
// 有参post请求
function param_post(u,data,fun,o,p) {
  jcAjax(u,data,'post',fun,o,p)
}
// 有参执行成功，执行函数
function param_suc_post(u,data,fun) {
  jcAjax(u,data,'post',fun,1,2)
}
function param_post_un_complete(u,data,fun) {
    noComlleteAjax(u,data,'post',fun,1,2)
}

// 判断对象是不是null
function isNull_O(o) {
  // console.log("isNull_O(" + o)
  // console.log("isNull_O(" + typeof o)
  return o == null || o === "null" || typeof o === "undefined" || false || o === "undefined" || o === "";
}

// 设置select 不可用
function disabled_select(o) {
  let e = getElementByO(o);
  e.attr("disabled","disabled");
}

function remove_disabled(o) {
  let e = getElementByO(o);
  e.removeAttr("disabled");
}

function getElementByO(o) {
  let e;
  if (typeof o === "string") {
    if (o.indexOf("#") >= 0) {
      o = o.subString(1);
    }
    e = $("#" + o);
  } else if (typeof o === "object") {
    e = $(o);
  }
  return e;
}

/**
 * 判断该页面是否被iframe
 */
function isframe() {
  return self.frameElement && self.frameElement.tagName === "IFRAME" ;
}
function initSelects(selectId, url,k,v) {
  let selectObj = $("#" + selectId);
  jcAjax_n(url,{},'get',function (options) {
    // selectObj.empty();
    for (var i in options) {
      let option = options[i];
      // var op = "<option value='" + key + "'>" + text + "</option>"
      let op = "<option value='" + option[k] + "'>" + option[v] + "</option>"
      selectObj.append(op);
    }
  },1,2);// ajax
}

/**
 * 上传图片，并设置预览
 * @param fileId 文件元素id
 * @param nameId 表单[url]的id
 * @param formData 文件data
 * @param previewImgId 预览元素的id
 */
function uploadImgAndYL(fileId,nameId, formData, previewImgId) {
  upload_file("/imageAdmin/uploadImgSingle",formData,function (imgUrl) {
    let iu = imgUrl.replaceAll("\"","");
    $('#' + nameId).val(iu);
    $('#' + previewImgId).attr('src', photoPath + iu);
  },1,2);
  // 解决只触发一次上传的问题 ，重新赋值
  $('#' + fileId).val('');
}
