(function(g,callback){
    callback(g);
})(window,function(g){

    var mineCraft = function(num1,num2,mine_num,obj){
        this.num1 = num1;                        //行数
        this.num2 = num2;                        //列
        this.mine_num = mine_num;                //雷的个数
        this.tiles = [];                         //数组里面存放的是每个小格子
        this.obj = obj;							//扫雷放置的对象
        this.flag = true;//判断是否为第一次点击
        this.arr = [];//存放点击格子周围的8个格子
        this.buildTiles();//创建游戏函数
    };

    mineCraft.prototype = {
		//在页面上创建扫雷的界面 函数buildTiles
        buildTiles:function(){
            this.obj.style.width = 51*this.num1+'px'; //在传进来的对象上画整体格子，每个小格子51px大小，总大小就为个数*单个大小
            this.obj.style.height = 51*this.num2+'px';
            var indexOfdiv = 0;
            for(var i = 0;i<this.num2;i++){
                for(var j = 0;j<this.num1;j++){
                    var tile = document.createElement('div');
                    tile.className = 'tile';//定义小格子class
                    tile.index = indexOfdiv;//为每个小格子添加索引
                    this.tiles[indexOfdiv] = tile; //将小格子存入数组中
                    indexOfdiv++;
                    this.obj.appendChild(tile); //将小格子插入到整个扫雷界面中  
                }
            }
            this.obj.oncontextmenu = function(){//取消浏览器的默认右键菜单事件
                return false;
            }
            this.event();//点击事件
        },
		//布雷：在页面加载在buildTiles()的时候布雷，
		//会导致有可能点击的第一个格子就是雷（游戏性不强），
		//后来修改到第一次点击完成之后布雷（确保第一下点的不是雷），
		//避开直接炸死的现象.所以把调用放在后面的event后触发的changeStyle函数中
        setMineCraft:function(num,arr_first,num_first){//雷的个数、最开始被点击的格子周围的八个、被点击的那个格子
            var arr_index = [];
            for(var i = 0;i<arr_first.length;i++){
                arr_index.push(arr_first[i].index);
            }
            var length = this.tiles.length;
            for (var i = 0; i < length; i++) {
                this.tiles[i].setAttribute("val", 0);//将每个tile的val设定为0
            }
            for (var i = 0; i < num; i++) {
                var index_Mine = Math.floor(Math.random() * this.tiles.length);
                if(index_Mine == num_first||arr_index.lastIndexOf(index_Mine)>-1){//如果是属于第一次点击的周围的直接跳过在该位置布雷
                    num++;//保证布置num个雷
                    continue;
                }
                if (this.tiles[index_Mine].getAttribute("val") == 0) {//防止重复布雷，雷的val=1
                    this.tiles[index_Mine].setAttribute("val", 1);
                }else {
                    num++;
                }
            }
            this.setValue()
        },
		//绑事件函数
        event : function(){
            var _this = this;
            this.obj.onmouseover = function(e){
                if(e.target.className == 'tile'){
                    e.target.className = 'tile current';
                }
            }
            this.obj.onmouseout = function(e){
                if(e.target.className == 'tile current'){
                    e.target.className = 'tile';
                }
            }
            this.obj.onmousedown = function(e){

                //e.button属性 左键0/中键1/右键2
                _this.changeStyle(e.button,e.target,e.target.index);
            }
        },
		//结束判断：
        over:function(obj){
            var flag = false;
            var showed = document.getElementsByClassName('showed');
            var num = this.tiles.length - this.mine_num;
            if(showed.length == this.tiles.length - this.mine_num){//如果被排出来的格子数等于总格子数-雷数，这游戏成功结束
                alert('恭喜你获得成功');
                flag = true;
            }else if(obj&&obj.getAttribute('val') == 1){//如果被点击的是雷，则炸死
                alert('被炸死！');
                flag = true;
            }
            return flag;
        },
		//结束后的显示函数：
        last:function(){
            var len = this.tiles.length;
            for(var i = 0;i<len;i++){
                this.tiles[i].className = this.tiles[i].getAttribute('val') == 1?'boom':'showed';
                if(this.tiles[i].className != 'boom'){
                    this.tiles[i].innerHTML = this.tiles[i].getAttribute('value') == 0?'':this.tiles[i].getAttribute('value');
                }
            }
            this.obj.onclick = null;
            this.obj.onmousedown = null
        },
		//点击调用的函数
        changeStyle:function(num1,obj,num_index){
            if(num1 == 0){//是左键的话
                if(this.flag){//this.flag 是之前定义的用于判断是否为第一次点击
                    this.store(num_index);//store函数，存放被点击的格子周围的8个格子
                    this.setMineCraft(this.mine_num,this.arr,num_index);//如果是第一次点击 即调用布雷函数 更改flag状态
                    this.flag = false;
                }                
                if(obj.className != 'tile'&&obj.className !='tile current'){//如果不是第一次点击，被点击的格子不是未点击状态，无效
                    return false;
                }
                if(obj.getAttribute('val') == 0){//如果不是雷。改为翻开状态
                    obj.className = "showed";
                    obj.innerHTML = obj.getAttribute('value') == 0?'':obj.getAttribute('value');//显示周围雷数
                    this.showAll(obj.index);//递归函数判断周围格子的情况，就是扫雷游戏上一点开会出现一片的那种
                }
                if(this.over(obj)){//判断游戏是否结束
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
        setValue:function(){
            var count = 0;
            for(var i = 0;i<this.tiles.length;i++){
                this.store(this.tiles[i].index);
                for(var j = 0;j<this.arr.length;j++){
                    if(this.arr[j].getAttribute('val') == 1){
                        count++;//统计周围8个格子的雷数
                    }
                }
                this.tiles[i].setAttribute('value',count);
                count = 0;
            }
        },
        //储存周围的位置；；
        store : function(num) {//传入格子的index.
            var tiles_2d = [];
            var indexs = 0;
            for(var i = 0;i<this.num2;i++){
                tiles_2d.push([]);
                for(var j = 0;j<this.num1;j++){
                    tiles_2d[i].push(this.tiles[indexs]);
                    indexs++;
                } 
            }
            var j = num % this.num1;//行
            var i = (num - j) / this.num1;//列
            this.arr = [];
                //左上
            if (i - 1 >= 0 && j - 1 >= 0) {
                this.arr.push(tiles_2d[i - 1][j - 1]);
            }
                //正上
            if (i - 1 >= 0) {
                this.arr.push(tiles_2d[i - 1][j]);
            }
                //右上
            if (i - 1 >= 0 && j + 1 <= this.num1-1) {
                this.arr.push(tiles_2d[i - 1][j + 1]);
            }
                //左边
            if (j - 1 >= 0) {
                this.arr.push(tiles_2d[i][j - 1]);
            }
                //右边
            if (j + 1 <= this.num1-1) {
                this.arr.push(tiles_2d[i][j + 1]);
            }
                //左下
            if (i + 1 <= this.num2-1 && j - 1 >= 0) {
                this.arr.push(tiles_2d[i + 1][j - 1]);
            }
                //正下
            if (i + 1 <= this.num2-1) {
                this.arr.push(tiles_2d[i + 1][j]);
            }
                //右下
            if (i + 1 <= this.num2-1 && j + 1 <= this.num1-1) {
                this.arr.push(tiles_2d[i + 1][j + 1]);
            }
        },
		//作用是如果该格子周围没有雷，自动翻开周围8个格子，然后再判断周围八个格子的周围8隔格子是否有雷，利用了递归的方法
        showAll:function(num){
            if (this.tiles[num].className == "showed" && this.tiles[num].getAttribute("value") == 0){
                this.store(this.tiles[num].index);
                var arr2 = new Array();
                arr2 = this.arr;
                for (var i = 0; i < arr2.length; i++) {
                    if (arr2[i].className != "showed"&&arr2[i].className !='biaoji') {
                        if (arr2[i].getAttribute("value") == 0) {
                            arr2[i].className = "showed";
                            this.showAll(arr2[i].index);
                        } else {
                            arr2[i].className = "showed";
                            arr2[i].innerHTML = arr2[i].getAttribute("value");
                        }
                    }
                }
            }
        }
    }
    g.mineCraft = mineCraft;
})

