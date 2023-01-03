 
// 导航栏

// 移动端目录按钮
 $('.menu.toggle').click(function () {
     $('.m-item').toggleClass('m-mobile-hide');
     $('.grid-header').toggleClass('stacked-non-transparent');
 });
// 导航栏变色

$('nav .dropdown').dropdown({
    on: 'hover',
    action: 'select'
});
$('.form .dropdown').dropdown({
    on: 'hover',
});


// 页脚
$('#blog-message').load(/*[[@{/footer/blogmessage]]*/"/footer/blogmessage");
    // 粘性功能
function elmBtmDifferWithBodybtm(el) {
    if (typeof jQuery === "function" && el instanceof jQuery) {
        el = el[0];
    }
    var elBottom = el.getBoundingClientRect().bottom;
    var bodyBottom=$("body")[0].getBoundingClientRect().bottom;
    return (bodyBottom-elBottom);
}
function footerAdjuster(){
    var footer = $("footer")
    footer.css({"top":""})
    var BtmDiffer=elmBtmDifferWithBodybtm(footer);
    if(BtmDiffer>=0){
        footer.css({"top":BtmDiffer+"px"})
    }else{
        footer.css({"top":""})
    }
}
footerAdjuster()
$(window).resize(footerAdjuster);

$('.close.icon').click(function(){
    $(this).closest('.message').transition('fade');
})
