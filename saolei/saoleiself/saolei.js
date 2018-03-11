/**
 * Created by dell on 2018/3/10.
 */
(function(g,callback){
    callback(g)
})(window, function(g){
    var mineCrafe = function(num1,num2,mineNumber,obj) {
        this.num1 = num1;
        this.num2 = num2;
        this.mineNumber = mineNumber;
        this.obj = obj;
        this.tiles = [];
        this.sideBox = [];
        this.flag = true;
        this.buildTiles();
    };

    mineCrafe.prototype = {
        buildTiles: function() {
            this.obj.style.width = this.num1*51+'px';
            this.obj.style.height = this.num2*51+'px';
            var indexOfDiv = 0;
            for(var i=0; i<this.num2; i++) {
                for(var j=0; j<this.num1; j++) {
                    var tile = document.createElement('div');
                    tile.className = 'tile';
                    tile.index = indexOfDiv;
                    this.tiles[indexOfDiv] = tile;
                    indexOfDiv ++;
                    this.obj.appendChild(tile);
                }
            }
            this.obj.oncontextmenu = function() {
                return false;
            }
            this.event();
        },

        event: function() {
            var that = this;
            this.obj.onmouseover = function(e) {
                if(e.target.className=='tile') {
                    e.target.className = 'tile current'
                }
            }
            this.obj.onmouseout = function(e) {
                if(e.target.className == 'tile current') {
                    e.target.className = 'tile'
                }
            }
            this.obj.onmousedown = function(e) {
                that.changeStyle(e.button, e.target, e.target.index)
            }
        },

        changeStyle: function(mouseNum, obj, index) {
            if(mouseNum==0) {
                if(this.flag) {

                    this.store(index)

                    this.setMineCraft(this.mineNumber,this.sideBox, index)

                    this.flag = false
                }
                if(obj.className!='tile current') {
                    return false;
                }
                if(obj.getAttribute('val') ==0) {
                    obj.className = 'showed'
                    obj.innerHTML = obj.getAttribute('value')==0?'':obj.getAttribute('value')
                    this.showAll(obj.index)
                }
                if(this.over(obj)){
                    this.last();
                }
            }
            if(num1 == 2){//右键标记事件
                if(obj.className == 'biaoji'){
                    obj.className = 'tile';
                }else if(obj.className !='biaoji'&&obj.className != 'showed'){
                    obj.className = 'biaoji';
                }
            }
        },

        over: function(obj) {
            var flag = false
            var showed = this.document.getElementsByClassName('showed');
            var num = this.tiles.length-this.mineNumber;
            if(showed.length == showed-num) {
                alert('success')
                flag = true
            }else {
                alertA('failed')
                flag = true
            }
            return flag
        },
        last: function() {
            var len = this.tiles.length;
            for(var i=0; i<len; i++) {
                this.tiles[i].className = this.tiles[i].getAttribute("value")==1?'boom':'showed';
                if(this.tiles[i].className != 'boom'){
                    this.tiles[i].innerHTML = this.tiles[i].getAttribute('value') == 0?'':this.tiles[i].getAttribute('value');
                }
            }
            this.obj.onclick = null
            this.obj.oncontextmenu = null;
        },

        showAll: function(index) {
            if(this.tiles[index].className=='showed' && this.tiles[index].getAttribute('value') == 0) {
                this.store(index)
                var arr2 = this.sideBox
                for(var i=0; i<arr2.length; i++) {
                    if(arr2[i].className != "showed"&&arr2[i].className !='biaoji') {
                        if(arr2[i].getAttribute('value')==0) {
                            arr2[i].className = "showed";
                            this.showAll(arr2[i].index)
                        }else{
                            arr2[i].className = "showed";
                            arr2[i].innerHTML = arr2[i].getAttribute("value");
                        }
                    }
                }
            }
        },

        setMineCraft: function(mineNumber,sideBox, index) {

            var arr_index = [];
            for(var i = 0;i<sideBox.length;i++){
                arr_index.push(sideBox[i].index);
            }
            var length = this.tiles.length

            for(var i=0; i<length; i++) {
                this.tiles[i].setAttribute('val',0);

            }
            for(var i=0; i<mineNumber; i++) {
                var index_mine = Math.floor(Math.random()*this.tiles.length);
                if(index_mine==index || arr_index.lastIndexOf(index_mine)>-1){
                    mineNumber ++;
                    continue;
                }
                if(this.tiles[index_mine].getAttribute('val')==0) {
                    this.tiles[index_Mine].setAttribute("val", 1);
                }else{
                    mineNumber++;
                }
            }
            this.setValue();
        },

        setValue: function() {
          var count = 0;
          for (var i=0; i<this.tiles.length; i++) {
              this.store(this.tiles[i].index)
              for(var j=0; j<this.sideBox.length; j++) {
                  if(this.sideBox[j].getAttribute('val') ==1 ) {
                      count++;
                  }
              }
              this.tiles[i].setAttribute('value',count);
              count=0;
            }
        },

        store: function(num) {
            var tiles_2d = [];
            var indexs = 0;
            for(var i = 0;i<this.num2;i++){
                tiles_2d.push([]);
                for(var j = 0;j<this.num1;j++){
                    tiles_2d[i].push(this.tiles[indexs]);
                    indexs++;
                }
            }
            var j = num % this.num1;//列
            var i = (num - j) / this.num1;//行
            this.sideBox = [];
            //左上
            if (i - 1 >= 0 && j - 1 >= 0) {
                this.sideBox.push(tiles_2d[i - 1][j - 1]);
            }
            //正上
            if (i - 1 >= 0) {
                this.sideBox.push(tiles_2d[i - 1][j]);
            }
            //右上
            if (i - 1 >= 0 && j + 1 <= this.num1-1) {
                this.sideBox.push(tiles_2d[i - 1][j + 1]);
            }
            //左边
            if (j - 1 >= 0) {
                this.sideBox.push(tiles_2d[i][j - 1]);
            }
            //右边
            if (j + 1 <= this.num1-1) {
                this.sideBox.push(tiles_2d[i][j + 1]);
            }
            //左下
            if (i + 1 <= this.num2-1 && j - 1 >= 0) {
                this.sideBox.push(tiles_2d[i + 1][j - 1]);
            }
            //正下
            if (i + 1 <= this.num2-1) {
                this.sideBox.push(tiles_2d[i + 1][j]);
            }
            //右下
            if (i + 1 <= this.num2-1 && j + 1 <= this.num1-1) {
                this.sideBox.push(tiles_2d[i + 1][j + 1]);
            }
        }
    };
    g.mineCraft = mineCrafe;
});