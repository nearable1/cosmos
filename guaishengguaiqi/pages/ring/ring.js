Page({
  onReady: function (e) {
    // 使用 wx.createAudioContext 获取 audio 上下文 context
    this.audioCtx = wx.createAudioContext('myAudio')
  },
  data: {
    flag: false,
    src: '',
    currentId: 0,
    isFirst: true,
    srcs: ['http://www.4java.cn:8080/file/blackboard1.mp3', 'http://www.4java.cn:8080/file/blackboard2.mp3', 'http://www.4java.cn:8080/file/nighf.mp3', 'http://www.4java.cn:8080/file/writeblackboard.mp3', 'http://www.4java.cn:8080/file/docter.mp3', 'http://www.4java.cn:8080/file/pipe.mp3', 'http://www.4java.cn:8080/file/glass.mp3']
  },
  audio: function(e) {
    var id = e.target.id
    this.setData({
      src:this.data.srcs[id]
    })
    //判断是否点击另一个方块
    if(this.data.currentId != id) {
      this.setData({
        flag: false
      })
    }
    if(!this.data.flag) {
      this.audioCtx.play()
      this.setData({
        flag: true
      })
    }else {
      this.audioCtx.pause()
      this.setData({
        flag: false,
      })
    }
    this.setData({
      currentId: id
    })
   
  }
})