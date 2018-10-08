//logs.js
const util = require('../../utils/util.js')

Page({
  data: {
      btnText:'',
    count:0,
      sum:0,
      currentCount:0,
      item:[],
      head:'',
      header:['1．你更喜欢吃哪种水果','2．你平时休闲经常去的是什么地方',
      '3．你认为容易吸引你的人是','4．如果你可以成为一种动物，你希望自己是哪种',
      '5．天气很热，你更愿意选择什么方式解暑','6．如果必须与一个你讨厌的动物或昆虫在一起生活，你能容忍哪一个',
      '7．你喜欢看哪类电影、电视剧','8．以下哪个是你身边必带的物品',
      '9．你出行时喜欢坐什么交通工具','10．以下颜色你更喜欢哪种',
      '11．下列运动中挑选一个你最喜欢的（不一定擅长）','12．如果你拥有一座别墅，你认为它应当建立在哪里',
      '13．你更喜欢以下哪种天气现象','14．你希望自己的窗口在一座30层大楼的第几层',
      '15．你认为自己更喜欢在以下哪一个城市中生活'],
      items:[[
              {value: 2, name: '草莓'},
              {value: 3, name: '苹果'},
              {value: 5, name: '西瓜'},
              {value: 10, name: '菠萝'},
              {value: 15, name: '橘子'}
          ],[
              {value: 2, name: '郊外'},
              {value: 3, name: '电影院'},
              {value: 5, name: '公园'},
              {value: 10, name: '商场'},
              {value: 15, name: '酒吧'},
              {value: 20, name: '练歌房'}
          ],[
          {value: 2, name: '有才气的人'},
          {value: 3, name: '依赖你的人'},
          {value: 5, name: '优雅的人'},
          {value: 10, name: '善良的人'},
          {value: 15, name: '性情豪放的人'}
      ],[
          {value: 2, name: '猫'},
          {value: 3, name: '马'},
          {value: 5, name: '大象'},
          {value: 10, name: '猴子'},
          {value: 15, name: '狗'},
          {value: 20, name: '狮子'}
      ],[
          {value: 5, name: '游泳'},
          {value: 10, name: '喝冷饮'},
          {value: 15, name: '开空调'}
      ],[
          {value: 2, name: '蛇'},
          {value: 5, name: '猪'},
          {value: 10, name: '老鼠'},
          {value: 15, name: '苍蝇'}
      ],[
          {value: 2, name: '悬疑推理类'},
          {value: 3, name: '童话神话类'},
          {value: 5, name: '自然科学类'},
          {value: 10, name: '伦理道德类'},
          {value: 15, name: '战争枪战类'}
      ],[
          {value: 2, name: '打火机'},
          {value: 2, name: '口红'},
          {value: 3, name: '记事本'},
          {value: 5, name: '纸巾'},
          {value: 10, name: '手机'}
      ],[
          {value: 2, name: '火车'},
          {value: 3, name: '自行车'},
          {value: 5, name: '汽车'},
          {value: 10, name: '飞机'},
          {value: 15, name: '步行'}
      ],[
          {value: 2, name: '紫'},
          {value: 3, name: '黑'},
          {value: 5, name: '蓝'},
          {value: 8, name: '白'},
          {value: 12, name: '黄'},
          {value: 15, name: '红'}
      ],[
          {value: 2, name: '瑜伽'},
          {value: 3, name: '自行车'},
          {value: 5, name: '乒乓球'},
          {value: 8, name: '拳击'},
          {value: 10, name: '足球'},
          {value: 15, name: '蹦极'}
      ],[
          {value: 2, name: '湖边'},
          {value: 3, name: '草原'},
          {value: 5, name: '海边'},
          {value: 10, name: '森林'},
          {value: 15, name: '城中区'}
      ],[
          {value: 2, name: '雪'},
          {value: 3, name: '风'},
          {value: 5, name: '雨'},
          {value: 10, name: '雾'},
          {value: 15, name: '雷电'}
      ],[
          {value: 2, name: '七层'},
          {value: 3, name: '一层'},
          {value: 5, name: '二十三层'},
          {value: 10, name: '十八层'},
          {value: 15, name: '三十层'}
      ],[
          {value: 1, name: '丽江'},
          {value: 3, name: '拉萨'},
          {value: 5, name: '昆明'},
          {value: 8, name: '西安'},
          {value: 10, name: '杭州'},
          {value: 15, name: '北京'}
      ],
      ]
  },
  onLoad: function () {
    this.setData({
        head:this.data.header[0],
        item:this.data.items[0],
        count:0,
        sum:0,
        currentCount:0,
        btnText:'下一题'
    })
  },

  radioChange : function(e) {
    console.log("current:"+e.detail.value)
      this.setData({
          currentCount:e.detail.value
      })

  },

  clicktonext:function() {

      var that = this
      var cout = that.data.count+1
      var sum = parseInt(that.data.sum)+parseInt(that.data.currentCount)
      var len = parseInt(that.data.items.length-1)
      console.log(sum)
      if(cout==len) {
          that.setData({
              btnText: '查询结果'
          })
      }
      if(cout>len && that.data.currentCount!=0) {
          wx.redirectTo({
              url: '../index/index?data='+sum
          })
          wx.showLoading({
              title: '正在生成'
          })
      }else if(that.data.currentCount!=0){
          that.setData({
              sum: parseInt(that.data.sum)+parseInt(that.data.currentCount),
              currentCount: 0,
              count: cout,
              item: that.data.items[cout],
              head: that.data.header[cout]
          })
      }
  }
})
