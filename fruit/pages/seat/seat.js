Page({
  data:{
    seats:[]
  },
  clicktoaddseat:function(){
    this.data.seats = ['<button class="seat">座位</button>'].concat(this.data.seats)
    this.setData({
      seats: this.data.seats
    })
  }
})