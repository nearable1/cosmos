// pages/movie/movie.js
Page({
  data: {
    //imgUrls:'http://news.youth.cn/yl/201705/W020170503421346317624.jpg',
    imgUrls: '../image/blackboard.png',
    movielist: ['http://www.4java.cn:8080/file/blackboard1.mp3', 'http://www.4java.cn:8080/file/blackboard2.mp3', 'http://www.4java.cn:8080/file/nighf.mp3', 'http://www.4java.cn:8080/file/writeblackboard.mp3', 'http://www.4java.cn:8080/file/docter.mp3', 'http://www.4java.cn:8080/file/pipe.mp3', 'http://www.4java.cn:8080/file/glass.mp3'],
    flag: false,
    src: '',
    currentId: 0,
    isFirst: true
  },
  onReady: function (e) {
    // 使用 wx.createAudioContext 获取 audio 上下文 context
    this.audioCtx = wx.createAudioContext('myAudio')
  },
  audio: function (e) {
    var id = e.currentTarget.id
    this.setData({
      src: this.data.movielist[id]
    })
    //判断是否点击另一个方块
    if (this.data.currentId != id) {
      this.setData({
        flag: false
      })
    }
    if (!this.data.flag) {
      this.audioCtx.play()
      this.setData({
        flag: true
      })
    } else {
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