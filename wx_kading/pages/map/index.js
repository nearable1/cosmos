var WXBizDataCrypt = require('../WXBizDataCrypt.js')
Page({
  data: {

  },
  //"0134laeL17H5V41eWagL1xkbeL14laem"
  onLoad: function (options) {
    var that = this;
    wx.login({
      success: function (res) {
        console.log(res)
        var appid = "wx33800aa282ed651b";
        var secret = "30ffc49f6f2765faec431f2ed88b2a6a";
        //data = {'js_code': res.code}
        
        if (res.code) {
          wx.request({
            url: 'https://www.4java.cn/kading/getRunData.html', 
            method: "POST",
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            data: '',
            success: function (e) {
              console.log(e)
              var session_key = e.data.session_key;
              console.log(session_key);
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
              console.log("appid:" + appid + "session_key:" + session_key + "encryptedData:" + res.encryptedData + "iv:" + res.iv);
              var encryptedData = res.encryptedData;
              var iv = res.iv;
              //使用解密工具，链接地址：
              //https://codeload.github.com/gwjjeff/cryptojs/zip/master
              var pc = new WXBizDataCrypt(appid, session_key);
              console.log(pc);
              var data = pc.decryptData(encryptedData, iv)
              console.log(data)
            },
            fail: function (res) {
              wx.showModal({
                title: '提示',
                content: '开发者未开通微信运动，请关注“微信运动”公众号后重试',
                showCancel: false,
                confirmText: '知道了'
              })
            }
          })
        }
      }
    })
  },
});
