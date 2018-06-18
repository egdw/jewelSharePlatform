var index=0;//每张图片的下标，

window.onload=function(){       
    var start=setInterval(autoPlay,1000);//开始轮播，每秒换一张图
    $('.imgchangediv').onmouseover=function(){//当鼠标光标停在图片上，则停止轮播
        clearInterval(start);
    }
    $('.imgchangediv').onmouseout=function(){//当鼠标光标停在图片上，则开始轮播
        start=setInterval(autoPlay,1000);
    }
    autoPlay();
    var lis=$('.imgyuan font');//得到所有圆圈
    //当移动到圆圈，则停滞对应的图片
    var funny = function(i){
        lis[i].onmouseover = function(){
            changeImg(i);
        }
    }
    for(var i=0;i<lis.length;i++){
        funny(i);
    }
    clickhandle(2);
}
//一轮过后，还是第二轮
function autoPlay(){
    if(index>2){
        index=0;
    }
    changeImg(index++);
}
//对应圆圈和图片同步
function changeImg(index){
    var list=$('.imgchangediv img');
//    console.log(list);
   var list1=$('.imgyuan font');
    for(i=0;i<list.length;i++){
        list[i].style.display='none';
        list1[i].style.backgroundColor='#6f6f6f';
    }
    list[index].style.display='block';
    list1[index].style.backgroundColor='black';
}
$('.bottom').onclick = function(){
    alert("跳转");
//    window.open ("../../发文/writing.html"); 
}
//网上抄的固定到顶部程序
$(function () { 
        var ie6 = document.all; 
        var dv = $('#nav'), st; 
        dv.attr('otop', dv.offset().top); //存储原来的距离顶部的距离 
        $(window).scroll(function () { 
            st = Math.max(document.body.scrollTop || document.documentElement.scrollTop); 
            if (st > parseInt(dv.attr('otop'))) { 
                if (ie6) {//IE6不支持fixed属性，所以只能靠设置position为absolute和top实现此效果 
                    dv.css({ position: 'absolute', top: st }); 
                } 
                else if (dv.css('position') != 'fixed') dv.css({ 'position': 'fixed', top: 0 }); 
            }
            else if (dv.css('position') != 'static') dv.css({ 'position': 'static',top:0 }); 
        }
        ); 
    }
); 
function clickhandle(i){
    console.log(i);
    $('.navigation span').css('display', 'none');
    $('.memoir').children('img').attr('src', 'user/yours/img/memoir.png');
    $('.talk').children('img').attr('src', 'user/yours/img/talk.png');
    $('.mondaybest').children('img').attr('src', 'user/yours/img/mondaybest.png');
    switch (i) {
        case 1:
            // statements_1
            $('.memoir').children('img').attr('src', 'user/yours/img/s-memoir.png');
            $('.memoir').children('span').css('display', 'inline');
            break;
        case 2:
            $('.talk').children('img').attr('src', 'user/yours/img/s-talk.png');
            $('.talk').children('span').css('display', 'inline');
            break;
        case 3:
            $('.mondaybest').children('img').attr('src', 'user/yours/img/s-mondaybest.png');
            $('.mondaybest').children('span').css('display', 'inline');
            break;
        default:
            // statements_def
            break;
    }        

}
