// pages/movie/movie.js
Page({
  data: {
    imgUrls: 'http://www.4java.cn:8080/file/image/loading.png',
    image1: 'http://www.4java.cn:8080/file/image/blackboard1.png',
    image2: 'http://www.4java.cn:8080/file/image/blackboard2.png',
    image3: 'http://www.4java.cn:8080/file/image/nighf.png',
    image4: 'http://www.4java.cn:8080/file/image/pen.png',
    image5: 'http://www.4java.cn:8080/file/image/drill.png',
    image6: 'http://www.4java.cn:8080/file/image/pipe.png',
    image7: 'http://www.4java.cn:8080/file/image/bottle1.png',
    image8: 'http://www.4java.cn:8080/file/image/mosquito.png',
    image9: 'http://www.4java.cn:8080/file/image/fly.png',
    movielist: ['http://www.4java.cn:8080/file/blackboard1.mp3', 'http://www.4java.cn:8080/file/blackboard2.mp3', 'http://www.4java.cn:8080/file/nighf.mp3', 'http://www.4java.cn:8080/file/writeblackboard.mp3', 'http://www.4java.cn:8080/file/docter.mp3', 'http://www.4java.cn:8080/file/pipe.mp3', 'http://www.4java.cn:8080/file/glass.mp3',
'http://www.4java.cn:8080/file/mosquito.mp3',
'http://www.4java.cn:8080/file/fly.mp3'],
    flag: false,
    currentId: 0
  },
  onReady: function (e) {
    // 使用 wx.createAudioContext 获取 audio 上下文 context
    this.audioCtx = wx.createInnerAudioContext()
  },
  audio: function (e) {
    var id = e.currentTarget.id
    this.setData({
      src: this.data.movielist[id]
    })
    //audio对象属性
    this.audioCtx.src = this.data.src
    this.audioCtx.loop = true
    //判断是否点击另一个方块
    if (this.data.currentId != id) {
      console.log(this.data.currentId != id)
      this.setData({
        flag: false,
        currentId: id
      })
    }
    var flag = this.data.flag
    if (!flag) {
      this.audioCtx.play()
      this.setData({
        flag: true,
        imgUrls: 'http://www.4java.cn:8080/file/image/play3.gif'
      })
    } else if(flag){
      this.audioCtx.stop()
      this.setData({
        flag: false,
        imgUrls: 'http://www.4java.cn:8080/file/image/loading.png'
      })
    }
  },
  onShareAppMessage: function (res) {
    if (res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    return {
      title: '那一天，人类想起被声音支配的恐惧',
      path: '/pages/video/video',
      imageUrl: 'http://www.4java.cn:8080/file/image/share.png',
      success: function (res) {
        // 转发成功
        console.log(res)
      },
      fail: function (res) {
        // 转发失败
        console.log(res)
      }
    }
  }
})