var app = getApp().data
Page({
  data:{
    hiddenmodalput: true,
    list: [{ id: '310', name: 'gaoe'}, { id: '410', name: '刘熠庚'}, { id: '614', name: '刘熠庚'}, { id: 'aa', name: '刘熠庚'}],
    index: 0,
  },
  onLoad: function() {
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
        console.log("get,"+data)
      },
      fail: function (e) {
        console.log(e)
      }
    })
  },
  //更改赋值
  bindchange: function(e) {
    this.data.list[this.data.index].name = e.detail.value
    
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
    this.setData({
      hiddenmodalput: true,
      list: this.data.list
    })
    //访问后台，update数据库，加锁访问
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