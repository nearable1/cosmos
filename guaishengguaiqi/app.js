//app.js
App({
  onLaunch: function () {
    this.getUserInfo()
  },
  getUserInfo: function () {
      //调用登录接口
      wx.login({
        success: function () {
          wx.getUserInfo({
            success: function (res) {
              console.log(res);
              that.globalData.userInfo = res.userInfo
              if (!wx.getStorageSync('sessionKey')) {
                wx.request({
                  url: 'http://localhost:88/myaudio/getRunData.html?js_code=' + res.code,
                  header: {
                    'content-type': 'json'
                  },
                  success: function (res) {
                    console.log(res)
                    var session_key = res.data.session_key
                    wx.setStorageSync('sessionKey', session_key)
                  }
                })
              }
            }
          })
        }
      })
  },
  globalData: {
    userInfo: null
  }
})