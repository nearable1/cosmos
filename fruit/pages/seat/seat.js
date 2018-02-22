Page({
  data:{
    name1:'位子1',
    hiddenmodalput: true,
  },
  bindchange: function(e) {
    this.setData({
      name1: e.detail.value
    })
  },
  modalinput: function () {
    this.setData({
      hiddenmodalput: !this.data.hiddenmodalput,
    })
  },
  //取消按钮  
  cancel: function () {
    this.setData({
      hiddenmodalput: true
    });
  },
  //确认  
  confirm: function () {
    this.setData({
      hiddenmodalput: true
    })
  }  
})