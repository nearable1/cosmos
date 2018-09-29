//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
      sum : 0,
      data: '',
      nature: '',
      poem: [],
      userInfo: {},
      hasUserInfo: false,
      canIUse: wx.canIUse('button.open-type.getUserInfo'),
      shareTempFilePath:''
  },
  onLoad: function (options) {

      if (app.globalData.userInfo) {
          this.setData({
              userInfo: app.globalData.userInfo,
              hasUserInfo: true
          })
      } else if (this.data.canIUse){
          // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
          // 所以此处加入 callback 以防止这种情况
          app.userInfoReadyCallback = res => {
              this.setData({
                  userInfo: res.userInfo,
                  hasUserInfo: true
              })
          }
      } else {
          // 在没有 open-type=getUserInfo 版本的兼容处理
          wx.getUserInfo({
              success: res => {
                  app.globalData.userInfo = res.userInfo
                  this.setData({
                      userInfo: res.userInfo,
                      hasUserInfo: true
                  })
              }
          })
      }

      this.getDetail(options)

  },
    //获取用户权限
    getUserInfo: function(e) {
        console.log(e)
        app.globalData.userInfo = e.detail.userInfo
        this.setData({
            userInfo: e.detail.userInfo,
            hasUserInfo: true
        })
    },

  getDetail: function(options) {

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
      console.log(type)
      wx.request({
          url: 'https://www.appwx.club/route/index?type='+type,
          method: 'GET',
          header: {
              //设置参数内容类型为x-www-form-urlencoded
              'content-type':'application/json'
          },
          success: function (res) {
              console.log(res.data)
              var id = parseInt(res.data.type);
              wx.request({
                  url:'https://www.appwx.club/route/type?id='+id,
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
              wx.hideLoading()
          },
          fail: function (res) {
              console.log(res)
          }
      })
  },
   //设置分享
    onShareAppMessage: function () {
        return {
            title: '看看古人如何描述你',
            path: '/pages/start/start',
            imageUrl: 'https://www.appwx.club/manager/back2.jpg',
            success: function () {
                wx.showToast({
                    title: '转发成功',
                    icon: 'success',
                    duration: 2000
                })
            }
        }
    },


})
