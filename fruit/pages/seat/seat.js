const date = new Date()
const years = []
const months = []
const days = []

for (let i = 1990; i <= date.getFullYear(); i++) {
  years.push(i)
}

for (let i = 1; i <= 12; i++) {
  months.push(i)
}

for (let i = 1; i <= 31; i++) {
  days.push(i)
}

Page({
  data:{
    region: ['广东省', '广州市', '海珠区'],
    customItem: '全部',
    seats: [],
    count: 1,
    it: "",
    
  },
  clicktoaddseat:function(){
    this.data.it = "座位"+this.data.count
    this.data.seats = [this.data.it].concat(this.data.seats)
    this.data.count += 1
    this.setData({
      seats: this.data.seats,
      count: this.data.count
    })
  },
  bindRegionChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({  
      region: e.detail.value
    })
  }
})