
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls: [
      //'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      //'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      //'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
    ],
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    types: [],
  },
  click: function(e) {
    console.log(e.target.id)
  },
  //加载种类
  onLoad: function() {
    var that = this
    wx.request({
      url: 'https://www.4java.cn//myaudio/getTypes.html',
      method: 'POST',
      timeout: 5000,
      header: {
        "content-type": "application/json"
      },
      success: function(e) {
        that.setData({
          types: e.data
        })
      },
      fail: function() {
        console.log(e)
      }
    })
  },
  getMyaudio: function(e) {
    var res = e.currentTarget.id
    
    wx.navigateTo({
      url: './typelist/typelist?type=' + res,
    })
  }
})