var order = ['red', 'yellow', 'blue', 'green', 'red']
Page({
  data:{
    hiddenmodalput: true,
    list: [{ id: 0, name: '' }, { id: 1, name: '' }, { id: 2, name: '' }, 
      { id: 3, name: '' }, { id: 4, name: '' }, { id: 5, name: '' },
      { id: 6, name: '' }, { id: 7, name: '' }, { id: 8, name: '' },
      { id: 9, name: '' }, { id: 10, name: '' }, { id: 11, name: '' }],
    index: 0,
    name:'test'
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
  }  
})