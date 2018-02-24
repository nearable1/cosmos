var order = ['red', 'yellow', 'blue', 'green', 'red']
Page({
  data:{
    hiddenmodalput: true,
    list: [{ id: 0, name: '位子1' }, { id: 1, name: '位子2' }, { id: 2, name: '位子3' }, 
      { id: 3, name: '位子3' }, { id: 4, name: '位子3' }, { id: 5, name: '位子3' },
      { id: 6, name: '位子3' }, { id: 7, name: '位子3' }, { id: 8, name: '位子3' },
      { id: 9, name: '位子3' }, { id: 10, name: '位子3' }, { id: 11, name: '位子3' }],
    index: 0,
    toView: 'green',
    scrollTop: 100,
    scrollLeft: 0
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