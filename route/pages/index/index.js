//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
      sum : 0,
      data: '',
      nature: '',
      poem: []
  },
  onLoad: function (options) {
      this.getDetail(options)

  },

  getDetail: function(options) {
      wx.showLoading({
          title: '正在生成'
      })
      setTimeout(function(){
          wx.hideLoading()
      },1000)

      var that = this;
      var type = 0;
      var nature = '';
      var data = parseInt(options.data);
      if(data>=180) {
          type = 1;
      }else if(data>=140&&data<=179) {
          type = 2;
      }else if(data>=100&&data<=139) {
          type = 3;
      }else if(data>=70&&data<=99) {
          type = 4;
      }else if(data<=69) {
          type = 5
      }
      wx.request({
          url: 'http://localhost:8080/index?type='+type,
          method: 'GET',
          header: {
              //设置参数内容类型为x-www-form-urlencoded
              'content-type':'application/json'
          },
          success: function (res) {
              console.log(res.data)
              var id = parseInt(res.data.type);
              wx.request({
                  url:'http://localhost:8080/type?id='+id,
                  method:'GET',
                  header: {
                      //设置参数内容类型为x-www-form-urlencoded
                      'content-type':'application/json'
                  },
                  success:res=>{
                      console.log(res)
                      that.setData({
                          nature:res.data
                      })
                  },
                  fail: function (res) {
                      console.log(res)
                  }
              })

              that.setData({
                  data: res.data,
                  nature: nature
              })
              var str = res.data.poem
              that.setData({
                  poem:str.split(",")
              })
          },
          fail: function (res) {
              console.log(res)
          }
      })
  }

})
