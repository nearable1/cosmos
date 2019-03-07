Page({
  naviToWeather: function (e) {
    wx.navigateTo({
      url: '../weather/weather'
    })
  },
  naviToPoi: function (e) {
    wx.navigateTo({
      url: '../poi/poi'
    })
  },
  naviToNavi: function(){
    wx.navigateTo({
      url: '../navigation_car/navigation'
    })
  }
})