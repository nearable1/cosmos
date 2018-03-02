
Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: ['北京市', '北京市', '东城区'],
    date: '2009',
    arrayClass: ['1班', '2班', '3班', '4班', '5班', '6班', '7班', '8班', '9班', '10班', '11班'
      , '12班', '13班', '14班', '15班', '16班', '17班', '18班', '19班', '20班', '21班', '22班', '23班', '24班', '25班', '26班', '27班', '28班', '29班', '30班', '31班', '32班', '33班', '34班', '35班'],
    arraySchool: ['','学校1', '学校2', '学校3', '学校4'],
    arrayGrade: ['初一第一次座位', '初三最后一次座位', '高一第一次座位', '高三最后一次座位'],
    indexClass: 0,
    indexSchool: 0,
    indexGrade: 0,
    hiddenmodalput: true,
    //数组中的数字依次表示 picker-view 内的 picker-view-colume 选择的第几项（下标从 0 开始）
    value: [0],
    //界面显示的学校
    school: '',
    //是否需要新建学校
    newSchcool: false
  },
  //picker-view学校选择事件
  bindSchoolChange: function (e) {
    const val = e.detail.value
    this.setData({
      indexSchool: val[0],
    })
  },
  //地区事件
  bindRegionChange: function (e) {
    console.log(e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  //入学年份事件
  bindDateChange: function (e) {
    console.log(e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  //班级事件
  bindClassChange: function(e) {
    //console.log(e.detail.value)
    this.setData({
      indexClass: e.detail.value
    })
  },
  //年级事件
  bindGradeChange: function (e) {
    this.setData({
      indexGrade: e.detail.value
    })
  },
  //弹窗左下角取消
  schoolCancel: function() {
    this.setData({
      hiddenmodalput:true
    })
  },
  //弹窗右下角确定
  schoolConfirm: function(e) {
    this.setData({
      hiddenmodalput: true,
    })
    if (!this.data.newSchool) {
      this.setData({
        hiddenmodalput: true,
        school: this.data.arraySchool[this.data.indexSchool]
      })
    }
  },
  //点击显示选择学校的弹窗
  clickToChooseSchool: function() {
    //需要根据地区select出学校
    this.setData({
      hiddenmodalput: !this.data.hiddenmodalput
    })
  },
  //弹窗中输入框值改变事件
  bindInputChange: function(e) {
    if(e.detail.value.length>=4) {
      this.setData({
        //school: e.detail.value
        newSchool: true,
        school: e.detail.value
      })
    }
  },
  //点击跳转到seat.wxml
  clickToGoSeat: function() {
    wx.navigateTo({
      url: 'seat/seat'
    })
  }
})