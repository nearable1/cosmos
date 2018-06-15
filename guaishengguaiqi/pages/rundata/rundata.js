Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.login({
      success: function (res) {
        if (res.code) {
          wx.request({
            url: 'http://localhost:88/myaudio/getRunData.html?js_code=' + res.code,
            header: {
              'content-type': 'json'
            },
            success: function (res) {
              var sessionkey = res.data.session_key;
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
                          wx.navigateTo({
                            url: '../index/index',
                          })
                        }
                      }
                    })
                  } else {
                    wx.getWeRunData({
                      success: function (res) {
                        var encryptedData1 = res.encryptedData;
                        var iv1 = res.iv;
                        wx.request({
                          url: 'http://localhost:88/myaudio/decodeRunData.html',
                          data: {
                            encryptedData: encryptedData1,
                            iv: iv1,
                            session_key: sessionkey
                          },
                          method: 'GET',
                          header: {
                            'content-type': 'json'
                          },
                          success: function(e) {
                            console.log(e.data.stepInfoList[30])
                          },
                          fail: function(e) {
                            console.log("fail")
                          }
                        })
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
            }
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})