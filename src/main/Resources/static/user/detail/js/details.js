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

var ID;

//显示评论框
function comment() {
    var ta = $('.pinlun').children('textarea');
    $('.pinlun').css('display', 'block');
    ta.val("");
    $('.pinlun').attr('alt', '1');
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


var onetwoflg = true;//为了防止点了二级评论跳到一级评论的函数里（因为二级评论是一级评论的子元素所以也会触发click事件）
$('.one').click(function () {
    console.log(onetwoflg);
    if (onetwoflg) {
        var name = $(this).children('strong').text();
        ID = $(this).attr("alt")
        // pinlun(name);
        $('.pinlun').css('display', 'block');
        ta.val('');
        $('.pinlun').attr('alt', '1');//在评论的表格的alt上添加是一级评论还是二级评论
    }
    onetwoflg = true;//默认为true
})
$('.two').click(function () {
    var name = $(this).children('strong:eq(0)');
    ID = $(this).attr("alt")
    // pinlun(name.text());
    $('.pinlun').css('display', 'block');
    ta.val('');
    $('.pinlun').attr('alt', '1');
    $('.pinlun textarea').attr('alt', ID);//在输入框的alt里放当前小的评论id（可能要从评论的li的alt里取出来）
    onetwoflg = false;//点击就把flg置为false
})

function pinlun(name) {//修改评论输入框里的预设内容
    var ta = $('.pinlun').children('textarea');
    console.log(ta);
    $('.pinlun').css('display', 'block');
    ta.val('回复 ' + name + ':');
}

$('.pinlun').focusin(function (event) {
    /* Act on the event */
    $('.pinlun').css('display', 'block');
});
/*这个加上就会不能提交
$('.pinlun').focusout(function(event) {

	$('.pinlun').css('display', 'none');
});
*/
$('form').submit(function (event) {
    /* Act on the event */
    submitpinlun($('.pinlun').attr('alt'));
});

function submitpinlun(jibie) {//级别一级二级
    // console.log(pageId)
    console.log(jibie);
    // jibie = '1';
    var talk_message = $('.pinlun textarea').val();//评论框所有内容
    var messages = talk_message.substr(talk_message.indexOf(':') + 1);//实际评论内容
    console.log(messages);
    switch (jibie) {
        case '1':
            var pageId1 = $("#pageTitle").attr("alt");//大写的id我也不知道写啥所以就在最前面定义了个变量下面注释的ajax我没试过
            $.ajax({
                url: '/jewel/talk/addBigPage',
                type: 'post',
                dataType: 'json',
                data: {
                    pageId: pageId1,
                    talk_message: messages
                },
                success: function (data, textStatus, jqXHR) {
                    if (data.code == 501) {
                        Toast("评论操作太过频繁!!", 2000);
                    } else {
                        console.log(data)
                        var body = "<ul class='comments'>" +
                            "        <li class='one' th:alt='" + data.id + "' th:if=\"${talk.talks == null || talk.talks.size() == 0}\"><strong" +
                            "        >" + data.user.name + "</strong>：<p>" + data.message + "</p></li>\n" +
                            "    </ul>";
                        console.log(body)
                        $("#MainDiv").append(body)
                        Toast("评论成功!", 2000);
                    }
                }
            })
            break;
        case '2':
            var talk_id1 = ID;
            var destSmallTalkId1 = $('.pinlun textarea').attr('alt');//当前小的评论id我放在输入框的alt里
            $.ajax({
                url: '/jewel/talk/addSmallPage',
                type: 'post',
                dataType: 'json',
                data: {
                    talk_id: talk_id1,
                    talk_message: messages,
                },
                success: function (data, textStatus, jqXHR) {
                    console.log(data)
                }
            })
            break;
        case '3':
            var talk_id1 = ID;
            var destSmallTalkId1 = $('.pinlun textarea').attr('alt');//当前小的评论id我放在输入框的alt里
            $.ajax({
                url: '/jewel/talk/addSmallPage',
                type: 'post',
                dataType: 'json',
                data: {
                    talk_id: talk_id1,
                    talk_message: messages,
                    destSmallTalkId: destSmallTalkId1
                },
            })
                .done(function () {
                    console.log("success");
                })
                .fail(function () {
                    console.log("error");
                })
                .always(function () {
                    console.log("complete");
                });
            break;
    }
    // location.reload(true)
    // var test = window.location.href.split('#')[0];
    // window.location.replace(test)

}

var time = 0;//初始化起始时间
$(".comments li").on('touchstart', function (e) {  //长按触发事件
    e.stopPropagation(); //阻止事件冒泡到父元素，阻止任何父事件处理程序被执行
    var tkid = $(this).attr('alt');//talkId存在li的alt里就可以读取了
    console.log("tkid:" + tkid)
    time = setTimeout(function () {
        if (confirm("确定要删除吗？")) {//如果确定删除
            console.log(tkid);//
            $.ajax({
                url: '/jewel/talk',
                type: 'post',
                dataType: 'json',
                data: {talkId: tkid},
                success: function (data) {
                    console.log(data)
                    if (data.code == 200) {
                        Toast('删除评论成功!', 2000)
                        $(this).parent().remove()
                    } else {
                        Toast('删除评论失败!', 2000)
                    }
                },
                error: function () {
                    Toast('删除失败!', 2000)
                }
            })
        }
    }, 500);//这里设置长按响应时间

});

$(".comments li").on('touchend', function (e) {  //长按事件结束
    e.stopPropagation();
    clearTimeout(time);
});