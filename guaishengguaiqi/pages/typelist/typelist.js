
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    name: '',
    imgUrls: ''
  },
  onLoad: function(e) {
    var that = this
    this.setData({
      name: e.type
    })
    var name = { 'type': e.type}
    wx.request({
      url: 'https://www.4java.cn/myaudio/getAudio.html',
      method: 'POST',
      data: name,
      timeout: 5000,
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        that.setData({
          list:res.data,
          imgUrls: res.data[0].post
        })
      },
      fail: function () {
        console.log(e)
      }
    })
  },
})