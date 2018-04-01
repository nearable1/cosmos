
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    baks: ['http://www.4java.cn:8080/file/music/play.png'],
    imgUrls: '',
    flag: false,
    currentId: 0,
    bakImg: 'http://www.4java.cn:8080/file/music/play.png'
  },
  onLoad: function(e) {
    var that = this

    //设置tarbar的title
    wx.setNavigationBarTitle({
      title: e.type//页面标题为路由参数
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
        for (var i = 0; i < res.data.length; i++) {
          that.data.baks.push('http://www.4java.cn:8080/file/music/play.png')
        }
        that.setData({
          list:res.data,
          imgUrls: res.data[0].post,
          baks: that.data.baks
        })
        that.audioCtx = wx.createInnerAudioContext()
      },
      fail: function () {
        console.log(e)
      }
    })
  },
  //左上角返回
  onUnload: function() {
    this.audioCtx.destroy()
  },
  click: function(e) {
    var id = e.currentTarget.id
    var music = this.data.list[id].path
    //audio对象属性
    this.audioCtx.src = music
    this.audioCtx.loop = true
    //判断是否点击另一个方块
    if (this.data.currentId != id) {
      this.data.baks[this.data.currentId] = 'http://www.4java.cn:8080/file/music/play.png'
      this.setData({
        flag: false,
        currentId: id,
        baks: this.data.baks
      })
    }
    var flag = this.data.flag
    if (!flag) {
      //wx.playBackgroundAudio({
      //  dataUrl: music,
      //})
      this.audioCtx.play()
      this.data.baks[id] = 'http://www.4java.cn:8080/file/music/pause.png'
      this.setData({
        flag: true,
        baks: this.data.baks
      })
    } else if (flag) {
      //wx.pauseBackgroundAudio()
      this.audioCtx.stop()
      this.data.baks[id] = 'http://www.4java.cn:8080/file/music/play.png'
      this.setData({
        flag: false,
        baks: this.data.baks
      })  
    }
    wx.getBackgroundAudioPlayerState({
      success: function (res) {
        //调用需要更新的
        //self._onUpdate(res);
        console.log(res)
      }
    })
  }
})