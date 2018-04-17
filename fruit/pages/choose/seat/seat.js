var app = getApp().data
Page({
  data:{
    hiddenmodalput: true,
    list: [{ id: '310', name: 'gaoe'}, { id: '410', name: '刘熠庚'}, { id: '614', name: '刘熠庚'}, { id: 'aa', name: '刘熠庚'}],
    index: 0,
    name:''
  },
  onLoad: function() {
    this.setData({
      list: app.seatList
    })
    var data = {
      'schoolId': app.schoolId,
      'classId': app.classId
    }
    wx.request({
      url: 'https://www.4java.cn/myseat/selectSeat.do',
      data: data,
      method: 'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      success: function (e) {
        //给每个班的座位赋值-全局变量
        console.log(e)
      },
      fail: function (e) {
        console.log(e)
      }
    })
  },
  //更改赋值
  bindchange: function(e) {
    this.data.name = e.detail.value
    
  },
  //显示弹窗，指定选中的index
  modalinput: function (e) {
    console.log(e)
    this.setData({
      hiddenmodalput: !this.data.hiddenmodalput,
      index: e.currentTarget.id
    })
  },
  //取消按钮  
  cancel: function () {
    this.setData({
      hiddenmodalput: true
    });
  },
  //确认并最终赋值  
  confirm: function (e) {
    var that = this
    this.setData({
      hiddenmodalput: true,
      list: this.data.list
    })
    //访问后台，update数据库，加锁访问
    var data = {
      'schoolId': app.schoolId,
      'classId': app.classId,
      'name': that.data.name,
      'seatId': that.data.index
    }
    var data1 = {
      'schoolId': app.schoolId,
      'classId': app.classId
    }
    wx.request({
      url: 'https://www.4java.cn/myseat/insertSeat.do',
      data: data,
      method: 'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      success: function (e) {
        //给每个班的座位赋值-全局变量
        console.log(e)
        wx.request({
          url: 'https://www.4java.cn/myseat/selectSeat.do',
          data: data1,
          method: 'POST',
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          success: function (e) {
            //给每个班的座位赋值-全局变量
            that.setData({
              list: e.data
            })

          },
          fail: function (e) {
            console.log(e)
          }
        })
      },
      fail: function (e) {
        console.log(e)
      }
    })
  },
  //设置分享
  onShareAppMessage: function () {
    return {
      title: '学校+班级+年级',
      path: '/page/choose/seat?schoolId=***&seatId=***',
      success: function(){
        wx.showToast({
          title:'转发成功',
          icon:'success',
          duration:2000
        })
      }
    }
  },
})