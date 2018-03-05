
Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: [],
    date: '',
    arrayClass: ['','1班', '2班', '3班', '4班', '5班', '6班', '7班', '8班', '9班', '10班'],
    arraySchool: ['','学校1', '学校2', '学校3', '学校4'],
    arrayGrade: ['','初一第一次座位', '初三最后一次座位', '高一第一次座位', '高三最后一次座位'],
    indexClass: 0,
    indexSchool: 0,
    indexGrade: 0,
    hiddenmodalput: true,
    hiddenClass: true,
    //数组中的数字依次表示 picker-view 内的 picker-view-colume 选择的第几项（下标从 0 开始）
    value: [0],
    //界面显示的学校
    school: '',
    //是否需要新建学校
    newSchcool: false,
    //界面显示班级
    className: '',
    //是否需要新建班级
    newClass: false,
    //中间值
    inputSchool:'',
    inputClass:''
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
  //picker-view班级选择事件
  bindClassChange: function(e) {
    const val = e.detail.value
    this.setData({
      indexClass: val[0],
    })
  },
  //年级事件
  bindGradeChange: function (e) {
    this.setData({
      indexGrade: e.detail.value
    })
  },
  //picker-view学校选择事件
  bindSchoolChange: function (e) {
    const val = e.detail.value
    this.setData({
      indexSchool: val[0],
    })
  },
  //弹窗左下角取消
  schoolCancel: function() {
    this.setData({
      hiddenmodalput:true
    })
  },
  classCancel: function() {
    this.setData({
      hiddenClass:true
    })
  },
  //弹窗右下角确定
  schoolConfirm: function(e) {
    this.setData({
      hiddenmodalput: true,
    })
    if (!this.data.newSchool) {
      this.setData({
        school: this.data.arraySchool[this.data.indexSchool]
      })
    }
  },
  classConfirm: function() {
    this.setData({
      hiddenClass: true,
    })
    if (this.data.newClass) {
      this.setData({
        className: this.data.inputClass
      })
    } else {
      this.setData({
        className: this.data.arrayClass[this.data.indexClass]
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
  clickToChooseClass: function () {
    //select出班级
    this.setData({
      hiddenClass: !this.data.hiddenClass
    })
  },
  //弹窗中输入框值改变事件
  bindSchoolInputChange: function(e) {
    if(e.detail.value.length>=4) {
      this.setData({
        //school: e.detail.value
        newSchool: true,
        inputSchool: e.detail.value
      })
    }
  },
  bindClassInputChange: function (e) {
    if (e.detail.value.length >= 1) {
      this.setData({
        //school: e.detail.value
        newClass: true,
        inputClass: e.detail.value
      })
    }
  },
  //点击跳转到seat.wxml
  clickToGoSeat: function() {
    var grade = this.data.indexGrade
    var inc = this.data.indexClass
    var school = this.data.school
    var date = this.data.date
    var region = this.data.region
    if (grade==0||inc==0||school==''||date==''||region.length==0) {
      wx.showToast({
        title:'请输入空白部分',
        icon: 'loading',
        mask:true,
        duration:1000
      })
    }else {
      wx.navigateTo({
        url: 'seat/seat'
      })
    }
  }
})