var coordtransform = require('../../utils/mapLocation.js');

Page({
  data: {
    latitude:'',
    longitude: '',
    markers: [],
    polyline: [{
      points: [{
        longitude: 121.41075,
        latitude: 31.16697
      }, {
        longitude: 121.46379,
        latitude: 31.21831
      }],
      color: "#FF0000DD",
      width: 2,
      dottedLine: true
    }],
  },
  onReady: function (e) {
    // 使用 wx.createMapContext 获取 map 上下文
    this.mapCtx = wx.createMapContext('myMap')
  },
  onLoad: function() {
    
    var that = this
    wx.chooseLocation({
      success: function (res) {
        // success
        console.log(res)
        var wgs84togcj02 = coordtransform.wgs84togcj02(res.longitude, res.latitude);
        that.setData({
          longitude: res.longitude,//wgs84togcj02[0],
          latitude: res.latitude,//wgs84togcj02[1],
          markers: [{
            iconPath: "/images/start.png",
            latitude: res.latitude,
            longitude: res.longitude,
            name: '',
            width: 33,
            height: 43
          }]
        })
        
      },
      fail: function () {
        // fail
      },
      complete: function () {
        // complete
      }
    })
    console.log(this.data.markers)
    // wx.getLocation({
    //   type: 'gcj02',
    //   altitude: true,
    //   success: function (res) {
        
    //     //latitude = res.latitude,
    //     //longitude = res.longitude,
    //     //accuracy = res.accuracy
    //     wx.openLocation({
    //       latitude: res.latitude,
    //       longitude: res.longitude,
    //       scale: 28
    //     })
    //   },
    //   fail: function(e) {
        
    //   }
    // })
   
  },
  //"0134laeL17H5V41eWagL1xkbeL14laem"
  click: function (options) {
    var that = this;
    wx.login({
      success: function (res) {
        var appid = "wx24637ac470fd8876";
        var data={"js_code":res.code}
        
        if (res.code) {
          wx.request({
            url: 'https://www.4java.cn/kading/getRunData.html', 
            method: "POST",
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            data: data,
            success: function (e) {
              var session_key = e.data.session_key;
              that.getData(appid, session_key);
            },
            fail: function(e) {
              console.log(res.code)
            }
          })
        }
      }
    })
  },

  //获取encryptedData（没有解密的步数）和iv（加密算法的初始向量）
  getData: function (appid, session_key) {
    wx.getSetting({
      success: function (res) {
        if (!res.authSetting['scope.werun']) {
          wx.showModal({
            title: '提示',
            content: '获取微信运动步数，需要开启计步权限',
            success: function (res) {
              if (res.confirm) {
                //跳转去设置
                wx.openSetting({
                  success: function (res) {

                  }
                })
              } else {
                //不设置
              }
            }
          })
        } else {
          wx.getWeRunData({
            success: function (res) {
              var data1= {'appid':appid,
                'session_key': session_key, 'encryptedData': res.encryptedData, 'iv': res.iv}
              wx.request({
                url: 'https://www.4java.cn/kading/decodeRunData.html',
                method: "POST",
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                data: data1,
                success: function (e) {
                  console.log(e.data.stepInfoList[30])
                },
                fail: function (e) {
                  console.log(res.code)
                }
              })
            
            },
            fail: function (res) {
              wx.showModal({
                title: '提示',
                content: '开发者未开通微信运动，请关注“微信运动”公众号后重试',
                confirmText: '知道了'
              })
            }
          })
        }
      }
    })
  },
});
