Page({
  onReady: function (e) {
    // 使用 wx.createAudioContext 获取 audio 上下文 context
    this.audioCtx = wx.createAudioContext('myAudio')
  },
  data: {
    src: '../file/glass.mp3',
    flag: false
  },
  audio1: function(e) {
    if(!this.data.flag) {
      this.audioCtx.play()
      this.setData({
        flag: true
      })
    }else {
      this.audioCtx.pause()
      this.setData({
        flag: false
      })
    }
    
  }
})