var index = 0;//每张图片的下标，

window.onload = function () {
    console.log("已经加载..")
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
            // var talkLen = content.
            if (len > 0) {
                var body = " <ul class=\"contents\" ><li class='anonymous'>" +
                    "<div class='c-head'>" + "<img src='/jewel/user/yours/img/anonymous-head.png'>" + "<div class='name'>匿名</div>" +
                    "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + " <img src='" + "/jewel/upload/" + content.imgUrl[0] + "'>" + "</div>" +
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
                    var body = " <ul class=\"contents\" ><li class='men'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + " <img src='" + "/jewel/upload/" + content.imgUrl[0] + "'>" + "</div>" +
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
                    var body = " <ul class=\"contents\" ><li class='women'>" +
                        "<div class='c-head'>" + "<img src='" + content.user.imgUrl + "'>" + "<div class='name'>" + content.user.name + "</div>" +
                        "<div class='dot'></div>" + "</div>" + " <div class='photo'>" + " <img src='" + "/jewel/upload/" + content.imgUrl[0] + "'>" + "</div>" +
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
