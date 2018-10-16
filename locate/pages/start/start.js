//logs.js
const util = require('../../utils/util.js')
var app = getApp()

Page({
  data: {

  },
  onLoad: function () {

    },
    formSubmit:function(e){
        var that = this
        //表单点击提交的时候获取数据
        //正则匹配
        // var mobile = new RegExp('[0-9]','g'); //不严格
        var mobile = /^[1][3,4,5,7,8][0-9]{9}$/;
        // var myreg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;  //判断是否是座机电话
        //检查
        var isMobile1 = mobile.exec(e.detail.value.self)
        var isMobile2 = mobile.exec(e.detail.value.target)
        //输入有误的话，弹出模态框提示
        if(isMobile1==null||isMobile2==null){
            wx.showModal({
                title: '提示！！',
                content: '你输入的电话不符，请重新检查填写',
                showCancel: false
            })
        }else {
            app.data.selfPhone = e.detail.value.self
            app.data.targetPhone = e.detail.value.target
            wx.navigateTo({
                url:'../map/map'
            })
        }
    },
})
