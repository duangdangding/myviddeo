/**
 * 打开窗口
 * */
function openWind(t,e,wi,hi,fun,succFun) {
    let w = 'auto',h = 'auto';
    if (wi) {
        w = wi;
    }
    if (hi) {
        h = hi;
    }
    let index = layer.open({
        skin: 'layui-layer-rim', //加上边框
        area: [w, h], //宽高
        title: t,
        type:1,
        time:0,
        content:$("#" + e),
        cancel: function(){
            if (fun) {
                fun()
            }
        },
        success:function () {
            if (succFun) {
                succFun()
            }
        }
    })
    return index;
}

function closeLayer(index) {
    layer.close(index)
}

// t 1 新增 2 修改
function openAddOrEditWind(t,e,wi,hi,fun,succFun) {
    let title = t === 1 ? '新增' : '编辑';
    return openWind(title,e,wi,hi,fun,succFun);
}
function openSimpleWin(title,id,w,h) {
    return layer.open({
        title : title,
        type : 1,
        id : "layer_win_" + (Math.floor(Math.random() * (100 - 1 + 1)) + 1),
        content:$("#" + id),
        area : [ w + 'px', h + 'px' ]
    });
}
/**
 * 自定义窗口位置
 * @param t
 * @param e
 * @param wi
 * @param hi
 * @param offset ['1px','1px'] or '1px'
 * @param fun
 */
function openOffsetWind(t,e,wi,hi,offset,fun) {
    let w = 'auto',h = 'auto';
    if (wi) {
        w = wi;
    }
    if (hi) {
        h = hi;
    }
    layer.open({
        skin: 'layui-layer-rim', //加上边框
        area: [w, h], //宽高
        title: t,
        offset: offset,
        type:1,
        content:$("#" + e),
        cancel: function(){
            if (fun) {
                fun()
            }
        }
    })
}
function autoWidthWind(t,e,hi,fun) {
    openWind(t,e,'auto',hi,fun);
}
function autoHeightWind(t,e,wi,fun) {
    openWind(t,e,wi,'auto',fun);
}
function autoWHWind(t,e,fun) {
    openWind(t,e,'auto','auto',fun);
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
function initSelectOptions(selectId, url,key,value) {
    let selectObj = $("#" + selectId);
    $.ajax({
        url : url,
        async : false,
        type : "GET",
        success : function(result) {
            let configs = result.data;
            selectObj.find("option:not(:first)").remove();
            for (let i in configs) {
                let addressConfig = configs[i];
                let optionValue = addressConfig[value];
                let optionText = addressConfig[key];
                selectObj.append(new Option(optionValue, optionText));
            }
        },
        error : function(result) {
            // toastr.error('获取信息失败，原因：' + result.errorMessage);
        }
    });// ajax
}

/**
 * 
 * @param url 请求地址
 * @param selectId select元素ID
 * @param k value 的属性值
 * @param v text 的属性值
 * @param data 请求参数
 * @param tv 第一项默认value
 * @param tt 第一项默认text
 * @param selected 选定的值
 */
function initMySelect(url,selectId,k,v,data,tv,tt,selected) {
    let ttv = tv ? tv : '';
    let ttt = tt ? tt : '';
    data = data ? data : {};
    let selectObj = $("#" + selectId);
    selectObj.empty();
    if (ttt) {
        selectObj.append(new Option(ttt, ttv));
    }
    unAsyncAjax(url,data,'get',function (configs) {
        // let configs = r.result;
        for (let i in configs) {
            let addressConfig = configs[i];
            let optionValue = addressConfig[k];
            let optionText = addressConfig[v];
            if (selected && optionValue === selected) {
                selectObj.append(new Option(optionText, optionValue,true,true));
            } else {
                selectObj.append(new Option(optionText, optionValue));
            }
        }
    },1,2);
}
function initDataMySelect(configs,selectId,k,v,tv,tt,selected) {
    let ttv = tv ? tv : '';
    let ttt = tt ? tt : '';
    let selectObj = $("#" + selectId);
    selectObj.empty();
    if (ttt) {
        selectObj.append(new Option(ttt, ttv));
    }
    for (let i in configs) {
        let addressConfig = configs[i];
        let optionValue = addressConfig[k];
        let optionText = addressConfig[v];
        if (selected && optionValue === selected) {
            selectObj.append(new Option(optionText, optionValue,true,true));
        } else {
            selectObj.append(new Option(optionText, optionValue));
        }
    }
}