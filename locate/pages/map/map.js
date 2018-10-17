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
        hidden: true,
        interval: ''
    },
    onUnload: function() {
        clearInterval(this.data.interval)
    },
    onLoad: function() {
        var that = this
        that.find(app.data.targetPhone)
        this.setData({
            interval: setInterval(function() {
                wx.getLocation( {
                    type: 'gcj02',
                    success: function( res ) {
                        console.log( res )
                        that.setData({
                            longitude: Number( res.longitude ),
                            latitude: Number( res.latitude )
                        })
                        that.saveOrUpdate(app.data.selfPhone,res.longitude,res.latitude)
                    }
                })
            },3000)
        })
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
                            content:'target',
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
    },
    //设置分享
    onShareAppMessage: function () {
        return {
            title: '定位同伴位置',
            path: '/pages/start/start',
            imageUrl: 'https://www.appwx.club/manager/trace1.png',
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
