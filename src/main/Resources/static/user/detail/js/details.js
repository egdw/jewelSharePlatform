//获取文章
$('body').onload = function(){
	
}
//点赞
function like(id){
	$.ajax({
		url: '/jewel/like',
		type: 'GET',
        async: true,    //或false,是否异步
        timeout: 5000,    //超时时间
		dataType: 'json',
		data: {pageId: id},
		success:function(result){
			console.log(result);
		},
	})
	.done(function() {
		console.log("success");
	})
	.fail(function() {
		console.log("error");
	})
	
	var icon = $('.c-foot img')[2];
	console.log(icon.src=='img/like.png');
	if(icon.src=='img/like.png')
		icon.src = 'img/s-like.png';
	else
		icon.src='img/like.png';
	$.ajax({
		url: '/jewel/like',
		type: 'POST',
        async: true,    //或false,是否异步
        timeout: 5000,    //超时时间
		dataType: 'json',
		data: {pageId: id},
	})
	.done(function() {
		console.log("success");
	})
	.fail(function() {
		console.log("error");
	})
	.always(function() {
		console.log("complete");
	});
}
//显示评论框
function comment(){
	$('.pinlun').slideToggle('fast', function() {

		$('.c-foot img')[1].src = 'img/s-comment.png';
	});
}
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
