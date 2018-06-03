Page({
  data: {
    longitude: '',
    latitude: '',
  },
  onLoad: function() {
    wx.getLocation({
      success: function(res) {
        wx.openLocation({
          latitude: res.latitude,
          longitude: res.longitude,
        })
      },
    })
  }
})