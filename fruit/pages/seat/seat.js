Page({
  data:{
    seats: [],
    count: 1,
    it: "" 
  },
  clicktoaddseat:function(){
    this.data.it = "座位"+this.data.count
    this.data.seats = [this.data.it].concat(this.data.seats)
    this.data.count += 1
    this.setData({
      seats: this.data.seats,
      count: this.data.count
    })
  }
})