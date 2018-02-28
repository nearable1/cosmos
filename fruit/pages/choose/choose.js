
Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: ['广东省', '广州市', '海珠区'],
    date: '2009',
    arrayClass: ['1班', '2班', '3班', '4班', '5班', '6班', '7班', '8班', '9班', '10班', '11班'
      , '12班', '13班', '14班', '15班', '16班', '17班', '18班', '19班', '20班', '21班', '22班', '23班', '24班', '25班', '26班', '27班', '28班', '29班', '30班', '31班', '32班', '33班', '34班', '35班'],
    arraySchool: ['学校1', '学校2', '学校3', '学校4'],
    arrayGrade: ['初一第一次座位', '初三最后一次座位', '高一第一次座位', '高三最后一次座位'],
    indexClass: 0,
    indexSchool: 0,
    indexGrade: 0,

    months: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    month: 1,
    value: [1],//数组中的数字依次表示 picker-view 内的 picker-view-colume 选择的第几项（下标从 0 开始）
  },
  bindChange: function (e) {
    const val = e.detail.value
    this.setData({
      month: this.data.months[val[0]],
    })
  },

  bindRegionChange: function (e) {
    console.log(e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  bindDateChange: function (e) {
    console.log(e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  bindClassChange: function(e) {
    console.log(e.detail.value)
    this.setData({
      indexClass: e.detail.value
    })
  },
  bindSchoolChange: function(e) {
    this.setData({
      indexSchool: e.detail.value
    })
  },
  bindGradeChange: function (e) {
    this.setData({
      indexGrade: e.detail.value
    })
  }
})