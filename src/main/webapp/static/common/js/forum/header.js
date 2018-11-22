;$(function() {
	inTime = 0,
	outTime = 0,
	inTimer = null,
	outTimer = null,
	info_inTime = 0,
	info_outTime = 0,
	info_inTimer = null,
	info_outTimer = null;
	$('.idst_l').mouseenter(function () {
		inTime = new Date().getTime();
		clearTimeout(inTimer);
		if (inTime - outTime < 300) {
			clearTimeout(outTimer);
			outTimer = null;
		}
		inTimer = setTimeout(function () {
			$('.head_user_avator').css('opacity', 0);
			$('.idst_l').css('border-color', 'transparent');
			$('.zoom_user_avator').css('opacity', 1).addClass('zoomIn_user_avator');
			$('#head_container').slideDown("fast");
			clearTimeout(inTimer)
			inTimer = null
		}, 300)
	});
	$('.index-me01').mouseenter(function () {
		info_inTime = new Date().getTime();
		clearTimeout(info_inTimer);
		if (info_inTime - info_outTime < 300) {
			clearTimeout(info_outTimer);
			info_outTimer = null;
		}
		info_inTimer = setTimeout(function () {
			$('.head_info_container').slideDown("fast");
			clearTimeout(info_inTimer);
			info_inTimer = null;
		}, 300)
	});

	$('.idst_l').mouseleave(function () {
		outTime = new Date().getTime();
		if (outTime - inTime < 300) {
			clearTimeout(inTimer)
			inTimer = null;
		}
		clearTimeout(outTimer)
		outTimer = setTimeout(function () {
			var zoomTime = null;
			clearTimeout(zoomTime);
			$('.zoom_user_avator').removeClass('zoomIn_user_avator');
			zoomTime = setTimeout(function () {
				$('.zoom_user_avator').css('opacity', 0);
				$('.head_user_avator').css('opacity', 1);
				$('.idst_l').css('border-color', '#c2c2c2');
				clearTimeout(zoomTime);
			}, 300)
			$('#head_container').slideUp("fast");
			clearTimeout(outTimer)
			outTimer = null
		}, 300)
	});
	$('.index-me01').mouseleave(function () {
		info_outTime = new Date().getTime();
		if (info_outTime - info_inTime < 300) {
			clearTimeout(info_inTimer)
			info_inTimer = null;
		}
		clearTimeout(info_outTimer)
		info_outTimer = setTimeout(function () {
			$('.head_info_container').slideUp("fast");
			clearTimeout(info_outTimer);
			info_outTimer = null;
		}, 300)
	});
});