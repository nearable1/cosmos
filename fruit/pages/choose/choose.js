var global = getApp().data

Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: [],
    date: '',
    arrayClass: ['1班', '2班', '3班', '4班', '5班', '6班', '7班', '8班', '9班', '10班',
      '11班', '12班', '13班', '14班', '15班', '16班', '17班', '18班', '19班', '20班',
      '21班', '22班', '23班', '24班', '25班', '26班', '27班', '28班', '29班', '30班',
      '31班', '32班', '33班', '34班', '35班', '36班', '37班', '38班', '39班', '40班',
      '41班', '42班', '43班', '44班', '45班', '46班', '47班', '48班', '49班', '50班',
      '51班', '52班', '53班', '54班', '55班', '56班', '57班', '58班', '59班', '60班',
      '61班', '62班', '63班', '64班', '65班', '66班', '67班', '68班', '69班', '70班',
      '71班', '72班', '73班', '74班', '75班', '76班', '77班', '78班', '79班', '80班',
      '81班', '82班', '83班', '84班', '85班', '86班', '87班', '88班', '89班', '90班',
      '91班', '92班', '93班', '94班', '95班', '96班', '97班', '98班', '99班'],
    arraySchool: ['','学校1', '学校2', '学校3', '学校4'],
    arrayGrade: ['初一第一次座位', '初三最后一次座位', '高一第一次座位', '高三最后一次座位'],
    indexClass: 0,
    indexSchool: 0,
    hiddenmodalput: true,
    hiddenClass: true,
    //数组中的数字依次表示 picker-view 内的 picker-view-colume 选择的第几项（下标从 0 开始）
    value: [0],
    grade: '',
    //界面显示的学校
    school: '',
    //是否需要新建学校
    newSchcool: false,
    //界面显示班级
    className: '',
    //中间值
    inputSchool:''
  },
  //地区事件
  bindRegionChange: function (e) {
    
    this.setData({
      region: e.detail.value
    })
    //提取学校
    var data={'province':this.data.region[0],
      'city':this.data.region[1],
      'area':this.data.region[2]}
    wx.request({
      url: 'https://www.4java.cn/myseat/selectSchoolByRegion.html',
      method: 'POST',
      timeout: 5000,
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      data: Util.json2Form(data),
      success: function(e) {
        console.log(e)
        this.setData({
          arraySchool: e.detail.value
        })
      },
      fail: function(e){
        console.log(e)
      }
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
    console.log(e)
    this.setData({
      grade: this.data.arrayGrade[e.detail.value]
    })
  }, 
  //班级事件
  bindClassChange: function(e) {
    this.setData({
      className: this.data.arrayClass[e.detail.value]
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
  //弹窗右下角确定
  schoolConfirm: function(e) {
    this.setData({
      hiddenmodalput: true,
    })
    if (this.data.newSchool) {
      //插入学校
      var data = {province:this.data.region[0],
        city:this.data.region[1],
        area:this.data.region[2],
        chineseName: this.data.inputSchool}
      wx.request({
        url: 'https://www.4java.cn/myseat/insertSchool.html',
        data: data,
        method: 'GET',
        success: function(e) {
          if(e.detail.value==1) {
            console.log('success')
          }else if(e.detail.value==0) {
            console.log(e)
          }
        },
        fail: function(e) {
          console.log(e)
        }
      })
      this.setData({
        school: this.data.inputSchool
      })
    }else {
      this.setData({
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
  bindSchoolInputChange: function(e) {
    if(e.detail.value.length>=4) {
      this.setData({
        //school: e.detail.value
        newSchool: true,
        inputSchool: e.detail.value
      })
    } else {
      this.setData({
        newSchool: false
      })
    }
  },
  //点击跳转到seat.wxml
  clickToGoSeat: function() {
    var grade = this.data.grade
    var inc = this.data.className
    var school = this.data.school
    var date = this.data.date
    var region = this.data.region
    var data = {
      province: this.data.region[0],
      city: this.data.region[1],
      area: this.data.region[2],
      ChineseName: school,
      className: inc,
      year: date,
      times: grade}
    var data1 = {
      province: this.data.region[0],
      city: this.data.region[1],
      area: this.data.region[2],
      ChineseName: school
    }
    //给schoolId赋值
    wx.request({
      url: 'https://www.4java.cn/myseat/getSchoolId.html',
      data: data1,
      method: 'POST',
      success: function (e) {
        //给每个班的座位赋值-全局变量
        global.schoolId = e.detail.value
      },
      fail: function (e) {
        console.log(e)
      }
    })
    //获得classId的参数
    var data2 = {
      className: inc,
      year: date,
      times: grade,
      schoolId: global.schoolId
    }
    if (grade==''||inc==''||school==''||date==''||region.length==0) {
      wx.showToast({
        title:'请输入空白部分',
        icon: 'loading',
        mask:true,
        duration:1000
      })
    }else {
      wx.request({
        url: 'https://www.4java.cn/myseat/insertClass.html',
        data: data,
        method: 'POST',
        success: function(e) {
          //给每个班的座位赋值-全局变量
          global.seatList = e.detail.value
        },
        fail: function(e) {
          console.log(e)
        }
      })
      //给classId赋值
      wx.request({
        url: 'https://www.4java.cn/myseat/getClassId.html',
        data: data2,
        method: 'POST',
        success: function (e) {
          //给每个班的座位赋值-全局变量
          global.classId = e.detail.value
        },
        fail: function (e) {
          console.log(e)
        }
      })
      wx.navigateTo({
        url: 'seat/seat'
      })
    }
  }
})
var Util = require('../../utils/util.js')