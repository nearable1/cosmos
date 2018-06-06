Page({
  onLoad: function (options) {
    var that = this;
    wx.login({
      success: function (res) {
        console.log(res.code)
        if (res.code) {
          wx.request({
            url: 'http://localhost:88/kading/getRunData.html?js_code=' + res.code,
            header: {
              'content-type': 'json'
            },
            success: function (res) {
              console.log(res)
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
        console.log(res);
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
              console.log(res);
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
})