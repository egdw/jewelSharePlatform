var index = 0;//每张图片的下标，

$(document).ready(function () {
    var test = window.location.href.split('#')[0];
    var domain = document.domain;
    console.log("获取到的地址:" + test)
    console.log("获取到的domain:" + domain)
    $.ajax({
        url: '/jewel/sign',
        type: 'GET', //GET
        async: true,    //或false,是否异步
        timeout: 5000,    //超时时间
        data: {
            'requestUrl': test
        },
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        success: function (data, textStatus, jqXHR) {
            console.log(data)
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appId, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名，见附录1
                jsApiList: [
                    'checkJsApi',
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage',
                    'onMenuShareQQ',
                    'onMenuShareWeibo',
                    'chooseWXPay'
                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            var logoUrl = "http://" + domain + "/jewel/upload/logo.jpg";
            // 分享给朋友、QQ、微博
            var shareData = {
                // "imgUrl": 'http://test2.hongdeyan.cn/jewel/upload/logo.jpg',
                "imgUrl": logoUrl,
                "title": '我刚刚上传的图文',
                "desc": '快过来看看吧~',
                'link': test
            };
            // 分享到朋友圈
            var shareToTimeline = {
                "imgUrl": logoUrl,
                "title": '测试分析',
                'link': '测试描述',
                "desc": '这个东西不错啊'
            }

            wx.ready(function () {
                wx.onMenuShareTimeline(shareToTimeline);
                wx.onMenuShareAppMessage(shareData);
                wx.onMenuShareQQ(shareData);
                wx.onMenuShareQZone(shareData);

                wx.error(function (res) {
                    alert(res.errMsg);
                });
            });
        }
    })
});

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

//自定义弹框
function Toast(msg, duration) {
    duration = isNaN(duration) ? 3000 : duration;
    var m = document.createElement('div');
    m.innerHTML = msg;
    m.style.cssText = "width:60%; min-width:150px; background:#000; opacity:0.5; height:4em; color:#fff; line-height:4em; text-align:center; border-radius:5px; position:fixed; top:50%; left:20%; z-index:999999; font-weight:bold; font-size:2.5em;";
    document.body.appendChild(m);
    setTimeout(function () {
        var d = 0.5;
        m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
        m.style.opacity = '0';
        setTimeout(function () {
            document.body.removeChild(m)
        }, d * 1000);
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

function liked(e) {
    // body...
    var e1 = e;
    // var islike=false;
    console.log(e1.src);
    console.log(e1.alt);
    $.ajax({
        url: '/jewel/like',
        type: 'POST',
        async: true,    //或false,是否异步
        timeout: 5000,    //超时时间
        dataType: 'json',
        data: {pageId: e1.alt + ''},
        success: function (data, textStatus, jqXHR) {
            console.log(data)
            var icon = $('.c-foot img')[2];
            if (data.liked == true) {
                e1.src = '/jewel/user/detail/img/s-like.png';
            } else {
                e1.src = '/jewel/user/detail/img/like.png';
            }
        }
    })
}

function add(data) {
    //是不是男性
    var isBoy = data[0].user.boy;
    //是否是隐藏
    var isHidden = false;
    console.log("是否是男生:" + isBoy)
    console.log("是否是匿名:" + isHidden)
    $.each(data, function (index, content) {
        isHidden = content.hidden
        var len = 0
        if (content.imgUrl != undefined) {
            len = content.imgUrl.length;
        }
        var likeLen = 0;
        if (content.likes != undefined) {
            likeLen = content.likes.length
        }
        if (isHidden == true) {
            console.log("是隐藏的样式")
            if (len > 0) {
                var images = "";

                for (var i=0;i<len;i++)
                {
                    images = images + "<div class='swiper-slide' ><img class='swiper-slide'  src='" + "/jewel/upload/" + content.imgUrl[i] + "'/>" + "</div>";
                }
                var imgs = "<div class='swiper-container' style='width: 100%;height: 100%'>" +
                    "<div class='swiper-wrapper'>" + images + "</div></div>";

                var body = " <ul class=\"contents\" ><li class='anonymous'>" +
                    "<div class='c-head'>" + "<img src='/jewel/user/yours/img/anonymous-head.png'>" + "<div class='name'>匿名</div>" +
                    "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + imgs + "</div>" +
                    "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                    "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" +
                    "<a href=\"javascript:void(0)\" onclick=\"Toast('请点击右上角按钮转发！',2000)\"><img src='user/yours/img/forward.png'></a>" +
                    "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                if (content.userLiked == false) {
                    body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                        + "</div>" +
                        "</li></ul>"
                } else {
                    body = body + "<a href='javascript:void(0)'><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                        + "</div>" +
                        "</li></ul>"
                }
                $("#mainBody").append(body);
            } else {
                var body = "<ul class=\"contents\" > <li class='anonymous'>" +
                    "<div class='c-head'>" + "<img src='/jewel/user/yours/img/anonymous-head.png'>" + "<div class='name'>匿名</div>" +
                    "<div class='dot'></div>"
                    + "</div>" + "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                    "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" +
                    "<a href=\"javascript:void(0)\" onclick=\"Toast('请点击右上角按钮转发！',2000)\"><img src='user/yours/img/forward.png'></a>" +
                    "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                if (content.userLiked == false) {
                    body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                        + "</div>" +
                        "</li></ul>"
                } else {
                    body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                        + "</div>" +
                        "</li></ul>"
                }
                $("#mainBody").append(body);
            }
        } else {
            if (isBoy == true) {
                console.log("是男孩")
                if (len > 0) {
                    console.log("获取到的长度"+len)
                    var images = "";

                    for (var i=0;i<len;i++)
                    {
                        images = images + "<div class='swiper-slide' ><img class='swiper-slide' src='" + "/jewel/upload/" + content.imgUrl[i] + "'/>" + "</div>";
                    }
                    var imgs = "<div class='swiper-container' style='width: 100%;height: 100%'>" +
                        "<div class='swiper-wrapper'>" + images + "</div></div>";


                    var body = " <ul class=\"contents\" ><li class='men'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + imgs + "</div>" +
                        "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                        "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" + "<a href=''><img src='user/yours/img/forward.png'></a>" +
                        "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                    if (content.userLiked == false) {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    } else {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    }
                    $("#mainBody").append(body);
                    // <a style="text-decoration: none;color: #0f0f0f" th:href="'/jewel/detail?pageId='+${page._id}" th:text="${page.text}">文字文字文字纯文字排版背景灰色字体变细匿名用户</a>
                    var swiper = new Swiper('.swiper-container', {
                        spaceBetween: 30,
                        centeredSlides: true,
                        autoplay: {
                            delay: 2500,
                            disableOnInteraction: false,
                        },
                        pagination: {
                            el: '.swiper-pagination',
                            clickable: true,
                        },
                        navigation: {
                            nextEl: '.swiper-button-next',
                            prevEl: '.swiper-button-prev',
                        },
                    });
                } else {
                    var body = "<ul class=\"contents\" > <li class='men'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>"
                        + "</div>" + "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                        "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" + "<a href=''><img src='user/yours/img/forward.png'></a>" +
                        "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                    if (content.userLiked == false) {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    } else {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    }
                    $("#mainBody").append(body);
                }
            }

            if (isBoy == false) {
                console.log(("是女孩"))
                if (len > 0) {
                    var images = "";

                    for (var i=0;i<len;i++)
                    {
                        images = images + "<div class='swiper-slide' ><img class='swiper-slide' src='" + "/jewel/upload/" + content.imgUrl[i] + "'/>" + "</div>";
                    }
                    var imgs = "<div class='swiper-container' style='width: 100%;height: 100%'>" +
                        "<div class='swiper-wrapper'>" + images + "</div></div>";
                    var body = " <ul class=\"contents\" ><li class='women'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + imgs+ "</div>" +
                        "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                        "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" + "<a href=''><img src='user/yours/img/forward.png'></a>" +
                        "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                    if (content.userLiked == false) {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    } else {
                        body = body + "<a href='javascript:void(0)' ><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    }
                    $("#mainBody").append(body);
                } else {
                    var body = "<ul class=\"contents\" > <li class='women'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>"
                        + "</div>" + "<div class='c-text'>" + "<a style=\"text-decoration: none;color: #0f0f0f\" href='" + "/jewel/detail?pageId=" + content.id + "'> " + content.text + "</a>" + "</div>" +
                        "<div class='c-foot'>" + " <span>" + likeLen + "赞&nbsp;·&nbsp;182 评论</span>" + "<a href=''><img src='user/yours/img/forward.png'></a>" +
                        "<a href='" + "/jewel/detail?pageId=" + content.id + "'><img src='user/yours/img/comment.png'></a>";
                    if (content.userLiked == false) {
                        body = body + "<a href='javascript:void(0)'><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    } else {
                        body = body + "<a href='javascript:void(0)'><img onclick='liked(this)' alt='" + content.id + "' src='user/yours/img/s-like.png'></a>"
                            + "</div>" +
                            "</li></ul>"
                    }
                    $("#mainBody").append(body);
                }
            }
        }
    })


}
