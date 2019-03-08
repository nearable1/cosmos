function AMapWX(a) {
  this.key = a.key,
    this.requestConfig = {
      key: a.key,
      s: "rsx",
      platform: "WXJS",
      appname: a.key,
      sdkversion: "1.2.0",
      logversion: "2.0"
    }
}
AMapWX.prototype.getWxLocation = function (a, b) {
  wx.getLocation({
    type: "gcj02",
    success: function (a) {
      var c = a.longitude + "," + a.latitude;
      wx.setStorage({
        key: "userLocation",
        data: c
      }),
        b(c)
    },
    fail: function (c) {
      wx.getStorage({
        key: "userLocation",
        success: function (a) {
          a.data && b(a.data)
        }
      }),
        a.fail({
          errCode: "0",
          errMsg: c.errMsg || ""
        })
    }
  })
},
  AMapWX.prototype.getWeather = function (a) {
    function d(d) {
      var e = "base";
      a.type && "forecast" == a.type && (e = "all"),
        wx.request({
          url: "https://restapi.amap.com/v3/weather/weatherInfo",
          data: {
            key: b.key,
            city: d,
            extensions: e,
            s: c.s,
            platform: c.platform,
            appname: b.key,
            sdkversion: c.sdkversion,
            logversion: c.logversion
          },
          method: "GET",
          header: {
            "content-type": "application/json"
          },
          success: function (b) {
            function c(a) {
              var b = {
                city: {
                  text: "城市",
                  data: a.city
                },
                weather: {
                  text: "天气",
                  data: a.weather
                },
                temperature: {
                  text: "温度",
                  data: a.temperature
                },
                winddirection: {
                  text: "风向",
                  data: a.winddirection + "风"
                },
                windpower: {
                  text: "风力",
                  data: a.windpower + "级"
                },
                humidity: {
                  text: "湿度",
                  data: a.humidity + "%"
                }
              };
              return b
            }
            var d, e;
            b.data.status && "1" == b.data.status ? b.data.lives ? (d = b.data.lives, d && d.length > 0 && (d = d[0], e = c(d), e["liveData"] = d, a.success(e))) : b.data.forecasts && b.data.forecasts[0] && a.success({
              forecast: b.data.forecasts[0]
            }) : a.fail({
              errCode: b.data.infocode,
              errMsg: b.data.info
            })
          },
          fail: function (b) {
            a.fail({
              errCode: "0",
              errMsg: b.errMsg || ""
            })
          }
        })
    }
    function e(e) {
      wx.request({
        url: "https://restapi.amap.com/v3/geocode/regeo",
        data: {
          key: b.key,
          location: e,
          extensions: "all",
          s: c.s,
          platform: c.platform,
          appname: b.key,
          sdkversion: c.sdkversion,
          logversion: c.logversion
        },
        method: "GET",
        header: {
          "content-type": "application/json"
        },
        success: function (b) {
          var c, e;
          b.data.status && "1" == b.data.status ? (e = b.data.regeocode, e.addressComponent ? c = e.addressComponent.adcode : e.aois && e.aois.length > 0 && (c = e.aois[0].adcode), d(c)) : a.fail({
            errCode: b.data.infocode,
            errMsg: b.data.info
          })
        },
        fail: function (b) {
          a.fail({
            errCode: "0",
            errMsg: b.errMsg || ""
          })
        }
      })
    }
    var b = this,
      c = b.requestConfig;
    a.city ? d(a.city) : b.getWxLocation(a,
      function (a) {
        e(a)
      })
  },
  AMapWX.prototype.getPoiAround = function (a) {
    function d(d) {
      var e = {
        key: b.key,
        location: d,
        s: c.s,
        platform: c.platform,
        appname: b.key,
        sdkversion: c.sdkversion,
        logversion: c.logversion
      };
      a.querytypes && (e["types"] = a.querytypes),
        a.querykeywords && (e["keywords"] = a.querykeywords),
        wx.request({
          url: "https://restapi.amap.com/v3/place/around",
          data: e,
          method: "GET",
          header: {
            "content-type": "application/json"
          },
          success: function (b) {
            var c, d, e, f;
            if (b.data.status && "1" == b.data.status) {
              if (b = b.data, b && b.pois) {
                for (c = [], d = 0; d < b.pois.length; d++) e = 0 == d ? a.iconPathSelected : a.iconPath,
                  c.push({
                    latitude: parseFloat(b.pois[d].location.split(",")[1]),
                    longitude: parseFloat(b.pois[d].location.split(",")[0]),
                    iconPath: e,
                    width: 22,
                    height: 32,
                    id: d,
                    name: b.pois[d].name,
                    address: b.pois[d].address
                  });
                f = {
                  markers: c,
                  poisData: b.pois
                },
                  a.success(f)
              }
            } else a.fail({
              errCode: b.data.infocode,
              errMsg: b.data.info
            })
          },
          fail: function (b) {
            a.fail({
              errCode: "0",
              errMsg: b.errMsg || ""
            })
          }
        })
    }
    var b = this,
      c = b.requestConfig;
    a.location ? d(a.location) : b.getWxLocation(a,
      function (a) {
        d(a)
      })
  },
  AMapWX.prototype.getInputtips = function (a) {
    var b = this,
      c = b.requestConfig,
      d = {
        key: b.key,
        s: c.s,
        platform: c.platform,
        appname: b.key,
        sdkversion: c.sdkversion,
        logversion: c.logversion
      };
    a.location && (d["location"] = a.location),
      a.keywords && (d["keywords"] = a.keywords),
      a.type && (d["type"] = a.type),
      a.city && (d["city"] = a.city),
      a.citylimit && (d["citylimit"] = a.citylimit),
      wx.request({
        url: "https://restapi.amap.com/v3/assistant/inputtips",
        data: d,
        method: "GET",
        header: {
          "content-type": "application/json"
        },
        success: function (b) {
          b && b.data && b.data.tips && a.success({
            tips: b.data.tips
          })
        },
        fail: function (b) {
          a.fail({
            errCode: "0",
            errMsg: b.errMsg || ""
          })
        }
      })
  },
  AMapWX.prototype.getDrivingRoute = function (a) {
    var b = this,
      c = b.requestConfig,
      d = {
        key: b.key,
        s: c.s,
        platform: c.platform,
        appname: b.key,
        sdkversion: c.sdkversion,
        logversion: c.logversion
      };
    a.origin && (d["origin"] = a.origin),
      a.destination && (d["destination"] = a.destination),
      a.strategy && (d["strategy"] = a.strategy),
      a.waypoints && (d["waypoints"] = a.waypoints),
      a.avoidpolygons && (d["avoidpolygons"] = a.avoidpolygons),
      a.avoidroad && (d["avoidroad"] = a.avoidroad),
      wx.request({
        url: "https://restapi.amap.com/v3/direction/driving",
        data: d,
        method: "GET",
        header: {
          "content-type": "application/json"
        },
        success: function (b) {
          b && b.data && b.data.route && a.success({
            paths: b.data.route.paths,
            taxi_cost: b.data.route.taxi_cost || ""
          })
        },
        fail: function (b) {
          a.fail({
            errCode: "0",
            errMsg: b.errMsg || ""
          })
        }
      })
  },
  AMapWX.prototype.getWalkingRoute = function (a) {
    var b = this,
      c = b.requestConfig,
      d = {
        key: b.key,
        s: c.s,
        platform: c.platform,
        appname: b.key,
        sdkversion: c.sdkversion,
        logversion: c.logversion
      };
    a.origin && (d["origin"] = a.origin),
      a.destination && (d["destination"] = a.destination),
      wx.request({
        url: "https://restapi.amap.com/v3/direction/walking",
        data: d,
        method: "GET",
        header: {
          "content-type": "application/json"
        },
        success: function (b) {
          b && b.data && b.data.route && a.success({
            paths: b.data.route.paths
          })
        },
        fail: function (b) {
          a.fail({
            errCode: "0",
            errMsg: b.errMsg || ""
          })
        }
      })
  },
  AMapWX.prototype.getTransitRoute = function (a) {
    var b = this,
      c = b.requestConfig,
      d = {
        key: b.key,
        s: c.s,
        platform: c.platform,
        appname: b.key,
        sdkversion: c.sdkversion,
        logversion: c.logversion
      };
    a.origin && (d["origin"] = a.origin),
      a.destination && (d["destination"] = a.destination),
      a.strategy && (d["strategy"] = a.strategy),
      a.city && (d["city"] = a.city),
      a.cityd && (d["cityd"] = a.cityd),
      wx.request({
        url: "https://restapi.amap.com/v3/direction/transit/integrated",
        data: d,
        method: "GET",
        header: {
          "content-type": "application/json"
        },
        success: function (b) {
          if (b && b.data && b.data.route) {
            var c = b.data.route;
            a.success({
              distance: c.distance || "",
              taxi_cost: c.taxi_cost || "",
              transits: c.transits
            })
          }
        },
        fail: function (b) {
          a.fail({
            errCode: "0",
            errMsg: b.errMsg || ""
          })
        }
      })
  },
  module.exports.AMapWX = AMapWX;