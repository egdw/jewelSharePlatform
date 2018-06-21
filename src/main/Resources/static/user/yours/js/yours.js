var index = 0;//每张图片的下标，

window.onload = function () {
    // var start = setInterval(autoPlay, 1000);//开始轮播，每秒换一张图
    // $('.imgchangediv').onmouseover = function () {//当鼠标光标停在图片上，则停止轮播
    //     clearInterval(start);
    // }
    // $('.imgchangediv').onmouseout = function () {//当鼠标光标停在图片上，则开始轮播
    //     start = setInterval(autoPlay, 1000);
    // }
    // $.ajax({
    //     url: '/jewel/banner/findAll',
    //     type: 'GET', //GET
    //     async: true,    //或false,是否异步
    //     timeout: 5000,    //超时时间
    //     dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
    //     success: function (data, textStatus, jqXHR) {
    //         // add(data)
    //         console.log(data)
    //         var imgLength = data.length
    //         console.log("获取到的图片长度为:" + imgLength)
    //         if (imgLength > 0) {
    //             autoPlay(imgLength);
    //             var lis = $('.imgyuan font');//得到所有圆圈
    //             //当移动到圆圈，则停滞对应的图片
    //             var funny = function (i) {
    //                 lis[i].onmouseover = function () {
    //                     changeImg(i);
    //                 }
    //             }
    //             for (var i = 0; i < lis.length; i++) {
    //                 funny(i);
    //             }
    //             clickhandle(2);
    //         }
    //     }
    // })

}

//一轮过后，还是第二轮
function autoPlay(imgLength) {
    if (index > imgLength) {
        index = 0;
    }
    changeImg(index++);
}

//对应圆圈和图片同步
function changeImg(index) {
    var list = $('.imgchangediv img');
//    console.log(list);
    var list1 = $('.imgyuan font');
    for (i = 0; i < list.length; i++) {
        list[i].style.display = 'none';
        list1[i].style.backgroundColor = '#6f6f6f';
    }
    list[index].style.display = 'block';
    list1[index].style.backgroundColor = 'black';
}

$('.bottom').onclick = function () {
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
                        dv.css({position: 'absolute', top: st});
                    }
                    else if (dv.css('position') != 'fixed') dv.css({'position': 'fixed', top: 0});
                }
                else if (dv.css('position') != 'static') dv.css({'position': 'static', top: 0});
            }
        );
    }
);

// $(window).scroll(function () {
//     //已经滚动到上面的页面高度
//     var scrollTop = parseFloat($(this).scrollTop()),
//         //页面高度
//         scrollHeight = $(document).height(),
//         //浏览器窗口高度
//         windowHeight = parseFloat($(this).height()),
//         totalHeight = scrollTop + windowHeight;
//     console.log("totalHeight:"+totalHeight)
//     //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
//     if (scrollTop + windowHeight >= scrollHeight - 0.7) {
//         console.log("滚动到了底部!!!")
//         // //一级分类
//         // var first = $("#h_first").val(),
//         //     //二级分类
//         //     second = $("#h_second").val(),
//         //     //分页数
//         //     num = parseInt($("#pageNum").val()),
//         //     num = num + 1;
//         // $("#pageNum").attr('value', num);
//         // $.ajax({
//         //     type: 'post',
//         //     url: '/MicroPortal/ContentListForNotice?f=' + first + '&s=' + second + '&t=&PageIndex=' + num,
//         //     beforeSend: function (XMLHttpRequest) {
//         //         $("#loadMore").removeClass('hidden').text('正在加载数据...');
//         //     },
//         //     success: function (data) {
//         //         if (data.length == 0) {
//         //             $("#loadMore").removeClass('hidden').text('已加载全部数据！');
//         //         }
//         //         var data = getTime(data);
//         //         for (var i = 0, length = data.length; i < length; i++) {
//         //             $("#infoList").append("<li><a href='public_content.html?id=" + data[i].Id + "&f=" + first + "&s=" + second + "'><span>" + data[i].Title + "</span><span class='time'>" + data[i].PublishTime + "</span></a></li>");
//         //         }
//         //     },
//         //     error: function (XMLHttpRequest, textStatus, errorThrown) {
//         //         $("#loadMore").removeClass('hidden').text('数据加载失败，请重试！');
//         //     }
//         // });
//     }
// });

//自定义弹框
function Toast(msg,duration){
    duration=isNaN(duration)?3000:duration;
    var m = document.createElement('div');
    m.innerHTML = msg;
    m.style.cssText="width:60%; min-width:150px; background:#000; opacity:0.5; height:4em; color:#fff; line-height:4em; text-align:center; border-radius:5px; position:fixed; top:50%; left:20%; z-index:999999; font-weight:bold; font-size:2.5em;";
    document.body.appendChild(m);
    setTimeout(function() {
        var d = 0.5;
        m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
        m.style.opacity = '0';
        setTimeout(function() { document.body.removeChild(m) }, d * 1000);
    }, duration);
}

function clickhandle(i) {
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
