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
//点赞
function like() {
    var usernameClass = document.getElementsByClassName("username")[0]
    var id = usernameClass.getAttribute("alt")
    $.ajax({
        url: '/jewel/like',
        type: 'POST',
        async: true,    //或false,是否异步
        timeout: 5000,    //超时时间
        dataType: 'json',
        data: {pageId: id},
        success: function (data, textStatus, jqXHR) {
            console.log(data)
            var icon = $('.c-foot img')[2];
            if (data.liked == true) {
                icon.src = '/jewel/user/detail/img/s-like.png';
            } else {
                icon.src = '/jewel/user/detail/img/like.png';
            }
        }
    })
}

//显示评论框
function comment() {
    $('.pinlun').slideToggle('fast', function () {

        $('.c-foot img')[1].src = 'img/s-comment.png';
    });
}

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
