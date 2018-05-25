var QQMapWX = require('../../libs/qqmap-wx-jssdk.js');
//var config = require('../../libs/config.js');
var qqmapsdk;

Page({
  onLoad: function() {
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: '77BBZ-IVJCV-D4PPF-UHWEU-RG552-V2BBO'
    });
  },
  onShow: function () {
    // 调用接口
    qqmapsdk.search({
      keyword: '酒店',
      success: function (res) {
        console.log(res);
      },
      fail: function (res) {
        console.log(res);
      },
      complete: function (res) {
        console.log(res);
      }
    });
  }
})