//index.js
//获取应用实例
const app = getApp()

Page({
    data: {
        selfPhone: '',
        targetPhone: '',
        longitude: '',
        latitude: '',
        markers: [],
        hidden: true
    },
    onLoad: function() {
        var that = this
        //setInterval(function() {
            wx.getSetting({
                success: (res) => {
                    if (!res.authSetting['scope.userLocation']) {
                        that.setData({
                            hidden: false
                        })
                    }
                }
            })
            wx.getLocation( {
                type: 'gcj02',
                success: function( res ) {
                    console.log( res )
                    that.setData({
                        longitude: Number( res.longitude ),
                        latitude: Number( res.latitude )
                    })
                    that.find(app.data.targetPhone)
                    that.saveOrUpdate(app.data.selfPhone,res.longitude,res.latitude)
                }
            })
        //},30000)

    },
    find: function(phone) {
        var that = this
        wx.request({
            url: 'https://www.appwx.club/locate/find?phone=' + phone,
            method: 'GET',
            header: {
                //设置参数内容类型为x-www-form-urlencoded
                'content-type': 'application/json'
            },
            success: function (res) {
                //赋值 phone location
                if(res.data==null) {
                    wx.showModal({
                        title: '提示！！',
                        content: '对方尚未填入正确电话号码',
                        showCancel: false
                    })
                }else {
                    console.log(res.data)
                    var markers =[{
                        iconPath: "../../img/marker.png",
                        id: 0,
                        latitude: res.data.latitude,
                        longitude: res.data.longitude,
                        callout:{
                            content:'那个人',
                            color: '#FF0000',
                            fontSize: 15,
                            borderRadius: 10,
                            display: 'ALWAYS',
                        },
                        width: 21,
                        height: 27
                    }]
                    that.setData({
                        markers:markers
                    })
                }
            }
        })
    },
    saveOrUpdate: function(phone, longitude, latitude) {
        wx.request({
            url: 'https://www.appwx.club/locate/save?phone=' + phone+'&longitude='+longitude+'&latitude='+latitude,
            method: 'GET',
            header: {
                //设置参数内容类型为x-www-form-urlencoded
                'content-type': 'application/json'
            },
            success: res => {
                console.log(res)
            },
            fail: function (res) {
                console.log(res)
            }
        })
    }
})
