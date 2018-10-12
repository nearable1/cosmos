//index.js
//获取应用实例
const app = getApp()

var amapFile = require('lib/amap-wx.js');

Page({
    data: {
        locationKey:'d983b6fb19539e541f98d0a6dbefe421'
    },
    onload: function() {
        var that = this;
        var myAmapFun = new amapFile.AMapWX({key:that.data.locationKey});
        myAmapFun.getPoiAround({
            success: function(data){
                //成功回调
            },
            fail: function(info){
                //失败回调
                console.log(info)
            }
        })
    }
})
