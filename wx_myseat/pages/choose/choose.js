var globalData = getApp().data
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
    arraySchool: ['new'],
    arrayGrade: ['初一第一次座位', '初三最后一次座位', '高一第一次座位', '高三最后一次座位'],
    hiddenmodalput: true,
    //数组中的数字依次表示 picker-view 内的 picker-view-colume 选择的第几项（下标从 0 开始）
    value: [0],
    grade: '',
    //界面显示的学校
    school: '',
    //是否需要新建学校
    newSchool: false,
    //界面显示班级
    className: '',
    //中间值
    inputSchool:'',
    schoolHide: true,
    yearHide: true,
    seatHide: true,
    classHide: true,
  },
  onHide: function () {
    if(this.data.region==null) {
      this.setData({
        schoolHide: true,
      })
    }
    this.setData({
      
      yearHide: true,
      seatHide: true,
      classHide: true,
      grade: '',
      //界面显示的学校
      school: '',
      className: '',
      date: '',
    })
  },
  //地区事件
  bindRegionChange: function (e) {
    var that = this
    this.setData({
      region: e.detail.value,
      schoolHide: false,
      arraySchool: ['new'],
      inputSchool: '',
      school: '',
    })
    //提取学校
    var data={'province':this.data.region[0],
      'city':this.data.region[1],
      'area':this.data.region[2]}
    wx.request({
      url: 'https://wxapp.4java.cn/myseat/selectSchoolByRegion.do',
      method: 'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      data: data,
      success: function(e) {
        var array = that.data.arraySchool.concat(e.data)
        
        that.setData({
          arraySchool: array
        })
      },
      fail: function(e){
        console.log(e)
      }
    })
  },
  //入学年份事件
  bindDateChange: function (e) {
    this.setData({
      date: e.detail.value,
      classHide: false
    })
  },
  //年级事件
  bindGradeChange: function (e) {
    this.setData({
      grade: this.data.arrayGrade[e.detail.value],
      yearHide: false
    })
  }, 
  //班级事件
  bindClassChange: function(e) {
    this.setData({
      className: this.data.arrayClass[e.detail.value]
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
    var that = this
    this.setData({
      hiddenmodalput: true,
      seatHide: false
    })
    if (this.data.newSchool) {
      //插入学校
      var data = {province:this.data.region[0],
        city:this.data.region[1],
        area:this.data.region[2],
        chineseName: this.data.inputSchool}
      wx.request({
        url: 'https://wxapp.4java.cn/myseat/insertSchool.do',
        method: 'POST',
        data: data,
        header: {
          "content-type": "application/x-www-form-urlencoded"
        },
        success: function() {
          console.log("success")
          //提取学校
          var data = {
            'province': that.data.region[0],
            'city': that.data.region[1],
            'area': that.data.region[2]
          }
          //学校arraySchool重新赋值
          wx.request({
            url: 'https://wxapp.4java.cn/myseat/selectSchoolByRegion.do',
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            data: data,
            success: function (e) {
              that.setData({
                arraySchool: ['new']
              })
              var array = that.data.arraySchool.concat(e.data)

              that.setData({
                arraySchool: array
              })
            },
            fail: function (e) {
              console.log(e)
            }
          }) 
        },
        fail: function() {
          console.log("fail")
        }
      })
      this.setData({
        school: this.data.inputSchool
      })
    }
  },
  //点击显示选择学校的弹窗
  clickToChooseSchool: function(e) {
    //需要根据地区select出学校
    var id = e.detail.value
    if(id==0) {
      this.setData({
        hiddenmodalput: !this.data.hiddenmodalput
      })
    }else {
      this.setData({
        school: this.data.arraySchool[id],
        seatHide: false
      })
    }
    
  },
  //弹窗中输入框值改变事件
  bindSchoolInputChange: function(e) {
    if(e.detail.value.length>=4) {
      this.setData({
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
    var that = this
    var grade = this.data.grade
    var inc = this.data.className
    var school = this.data.school
    var date = this.data.date
    var region = this.data.region
    var data = {
      province: this.data.region[0],
      city: this.data.region[1],
      area: this.data.region[2],
      chineseName: school,
      className: inc,
      year: date,
      times: grade}
    var data1 = {
      province: this.data.region[0],
      city: this.data.region[1],
      area: this.data.region[2],
      chineseName: school
    }
    //给schoolId赋值
    wx.request({
      url: 'https://wxapp.4java.cn/myseat/getSchoolId.do',
      method: 'POST',
      data: data1,
      header: {
        "content-type": "application/x-www-form-urlencoded"
      },
      success: function (e) {
        //给每个班的座位赋值-全局变量
        globalData.schoolId = e.data

        //获得classId的参数
        var data2 = {
          className: inc,
          year: date,
          times: grade,
          schoolId: globalData.schoolId
        }
        if (grade == '' || inc == '' || school == '' || date == '' || region.length == 0) {
          wx.showToast({
            title: '请输入空白部分',
            icon: 'loading',
            mask: true,
            duration: 1000
          })
        } else {
          wx.request({
            url: 'https://wxapp.4java.cn/myseat/insertClass.do',
            data: data,
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            success: function (e) {
              //给每个班的座位赋值-全局变量
              globalData.seatList = e.data

            },
            fail: function (e) {
              console.log(e)
            }
          })
          //给classId赋值
          wx.request({
            url: 'https://wxapp.4java.cn/myseat/getClassId.do',
            data: data2,
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            success: function (e) {
              //给每个班的座位赋值-全局变量
              globalData.classId = e.data

            },
            fail: function (e) {
              console.log(e)
            }
          })
          wx.navigateTo({
            url: 'seat/seat'
          })
        }

      },
      fail: function (e) {
        console.log(e)
      }
    })
  },
  //设置分享
  onShareAppMessage: function () {
    return {
      title: '来寻找曾经的同桌吧',
      path: '/pages/choose/choose',
      imageUrl: 'https://wxapp.4java.cn/file/music/share.png',
      success: function () {
        wx.showToast({
          title: '转发成功',
          icon: 'success',
          duration: 2000
        })
      }
    }
  }
})