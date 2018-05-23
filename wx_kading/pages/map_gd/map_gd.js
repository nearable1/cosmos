var amapFile = require('../../utils/amap-wx.js');

Page({
  data: {
    latitude_s: '',
    longitude_s: '',
    latidude_e: '',
    longitude_e: '',
    markers: [{
      iconPath: "../images/start.png",
      id: 0,
      latitude: '',
      longitude: '',
      width: 43,
      height: 43
    }, {
      iconPath: "../images/end.png",
      id: 0,
      latitude: '',
      longitude: '',
      width: 44,
      height: 44
    }],
    distance: '',
    cost: '',
    polyline: []
  },
  onLoad: function () {
    var that = this;
    var myAmapFun = new amapFile.AMapWX({ key: '11627b48952cfcc6e18b900c50d7731f' });
    wx.getLocation({
      success: function(res) {
        
        wx.chooseLocation({
          success: function (e) {
            that.setData({
              markers: [{
                iconPath: "../images/start.png",
                id: 0,
                latitude: res.latitude,
                longitude: res.longitude,
                width: 43,
                height: 43
              }, {
                iconPath: "../images/end.png",
                id: 0,
                latitude: e.latitude,
                longitude: e.longitude,
                width: 44,
                height: 44
              }]
            })
            myAmapFun.getWalkingRoute({

              origin: res.latitude+','+res.longitude,
              destination: e.latitude+'+'+e.longitude,
              success: function (data) {
                var points = [];
                if (data.paths && data.paths[0] && data.paths[0].steps) {
                  var steps = data.paths[0].steps;
                  for (var i = 0; i < steps.length; i++) {
                    var poLen = steps[i].polyline.split(';');
                    for (var j = 0; j < poLen.length; j++) {
                      points.push({
                        longitude: parseFloat(poLen[j].split(',')[0]),
                        latitude: parseFloat(poLen[j].split(',')[1])
                      })
                    }
                  }
                }
                that.setData({
                  polyline: [{
                    points: points,
                    color: "#0091ff",
                    width: 6
                  }]
                });
                if (data.paths[0] && data.paths[0].distance) {
                  that.setData({
                    distance: data.paths[0].distance + '米'
                  });
                }
                if (data.taxi_cost) {
                  that.setData({
                    cost: '打车约' + parseInt(data.taxi_cost) + '元'
                  });
                }

              },
              fail: function (info) {

              }
            })
          },
        })
      },
    })
    
    
  },
  
  goDetail: function () {
    wx.navigateTo({
      url: '../navigation_car_detail/navigation'
    })
  },
  goToCar: function (e) {
    wx.redirectTo({
      url: '../navigation_car/navigation'
    })
  },
  goToBus: function (e) {
    wx.redirectTo({
      url: '../navigation_bus/navigation'
    })
  },
  goToRide: function (e) {
    wx.redirectTo({
      url: '../navigation_ride/navigation'
    })
  },
  goToWalk: function (e) {
    wx.redirectTo({
      url: '../navigation_walk/navigation'
    })
  }
})