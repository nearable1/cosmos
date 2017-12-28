// Y-CHIPS的方法路径
window.ychips = window.ychips ? window.ychips : {};

// 页面命名空间一览初始化
window.ychips.ns = window.ychips.ns ? window.ychips.ns : [];

//页面命名空间一览初始化
window.ychips.locale = window.ychips.locale ? window.ychips.locale : 'zh_CN';


// 对象生成器
window.definer = window.definer ? window.definer : function(key, value) {
    var path = key.split(".");
    var tmp = window.ychips;

    for (var i = 0; i < path.length; i++) {
        if (i == path.length - 1) {
            tmp[path[i]] = value;
        } else {
            tmp[path[i]] = tmp[path[i]] ? tmp[path[i]] : {};
            tmp = tmp[path[i]];
        }
    }
};

