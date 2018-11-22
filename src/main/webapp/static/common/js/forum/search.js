$(function() {
	var searchTerm = $('#keyword').val();
	$(".result").removeHighlight();
	if (searchTerm) {
		$(".result .t, .result .c-abstract").highlight(searchTerm);
	}
});

javaex.page({
	id : "page",
	pageCount : pageCount,	// 总页数
	currentPage : currentPage,// 默认选中第几页
	position : "left",
	// 返回当前选中的页数
	callback:function(rtn) {
		jump(rtn.pageNum);
	}
});
function jump(pageNum) {
	if (!pageNum) {
		pageNum = 1;
	}
	window.location.href = "search.action"
			+ "?keyword="+keyword
			+ "&pageNum="+pageNum
			;
}