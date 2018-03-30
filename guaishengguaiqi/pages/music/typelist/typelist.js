
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    name: '',
    imgUrls: '',
    flag: false,
    currentId: 0
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
  click: function(e) {
    console.log(e.currentTarget.id)
    var id = e.currentTarget.id
    var music = this.data.list[id]
    this.audioCtx = wx.createInnerAudioContext() 
    //audio对象属性
    this.audioCtx.src = music
    this.audioCtx.loop = true
    //判断是否点击另一个方块
    if (this.data.currentId != id) {
      this.setData({
        flag: false,
        currentId: id
      })
    }
    var flag = this.data.flag
    if (!flag) {
      this.audioCtx.play()
      this.setData({
        flag: true
      })
    } else if (flag) {
      this.audioCtx.stop()
      this.setData({
        flag: false
      })
    }
  }
})