var order = ['red', 'yellow', 'blue', 'green', 'red']
Page({
  data:{
    hiddenmodalput: true,
    list: [{ id: '310', name: '刘熠庚', state: '' }, { id: '410', name: '刘熠庚', state: '' }, { id: '614', name: '刘熠庚', state: '' }],
    index: 0,
    currentItem: ''
  },
  onLoad: function() {
    //数据的遍历
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
  //点击变换样式
  clickToChangeStyle:function(e) {
    //console.log(e)
    this.setData({
      currentItem: e.currentTarget.id
    })
  }  
})