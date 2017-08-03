//jQuery

var URI = getUrl();

$(function() {
	var screenHeight = screen.availHeight;
	var screenWidth = screen.availWidth;
	// 动态获取并设定首页高度
	$('#home').css('height', screenHeight + "px");
	// 动态获取并设定'who'高度
	//$('#who').css('height', screen.availHeight + "px");
	$('#who').css('padding-top', screenHeight / 2 - 80 + "px");

	// message
	$('#message').on('click', function() {
		var name = $('#name').val();
		var content = $('#content').val();
		var contact = $('#contact').val();
		var address = $('#address').val();

		if (name !== null && name !== undefined && name !== '') {
			if (content !== null && content !== undefined && content !== '') {

				$.ajax({
			        url: URI + '/message/add',
			        type: 'POST', //GET
			        async: true,    //或false,是否异步
			        data: {
			        	name: name,
			        	content: content,
			        	contact: contact,
			        	address: address
			        },
			        timeout: 3000,    //超时时间 3s
			        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			        beforeSend: function(xhr){
			            
			        },
			        success: function(data, textStatus, jqXHR){
			            alert('Success');
			        },
			        error: function(xhr, textStatus){
			        	
			        },
			        complete: function(){
			            location.href="./index.html"
			        }
			    });

			} else {
				alert('还是应该说点什么！');
			}

		} else {
			alert('你应该给自己取个名字');
		}
	});

	// 获取 diary
	getData(0);

	$(document).on('click', '#prePaging', function() {
		var pageValue = document.getElementById('page').value;
		
		getData(pageValue - 1);
	});

	$(document).on('click', '#nextPaging', function() {
		var pageValue = document.getElementById('page').value;

		getData(pageValue + 1);
	});

    // like
	$(document).on('click', '#like-no', function() {
		alert('为了保持一颗平常心，暂不支持点赞，如果你有什么想说的，可以通过留言让我知晓');
	});

	//link
	$.ajax({
        url: URI + '/link/all',
        type: 'GET', //GET
        async: true,    //或false,是否异步
        data: {},
        timeout: 3000,    //超时时间 3s
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend: function(xhr){
            
        },
        success: function(data, textStatus, jqXHR){
            // console.log(data);
            // 解析json数据
            // console.log(data.code + " " + data.msg + " " + data.data);
            var code = data.code;
            if (code == 0) {
            	$.each(data.data, function(i, item) {
            		var linkHtml = '<a href="' + item.http + '" target="_blank" class="link-a">' + item.name + '</a>';
            		$('#link').prepend(linkHtml);
            	});
            } else {
            	alert(data.msg);
            }

        },
        error: function(xhr, textStatus){
        	// sory ....
            alert('数据请求出错，请刷新试试');
        },
        complete: function(){
            
        }
    });
});

/* 时间格式化工具 */
function formatDate(now) {
　　var year = now.getFullYear(),
　　month = now.getMonth() + 1,
　　date = now.getDate(),
　　hour = now.getHours(),
　　minute = now.getMinutes();
　　return month + "-" + date + " " + hour + ":" + minute;
}

// 获取日记数据
function getData(page) {
	$.ajax({
        url: URI + '/admin/diary/paging',
        type: 'GET', //GET
        async: true,    //或false,是否异步
        data: {
        	page: page,
        	size: 7
        },
        timeout: 3000,    //超时时间 3s
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend: function(xhr){
            
        },
        success: function(data, textStatus, jqXHR){
            // console.log(data);
            // 解析json数据
            // console.log(data.code + " " + data.msg + " " + data.data);
            var code = data.code;
            if (code == 0) {
            	$('#diary-content').html('');
            	$.each(data.data.content, function(i, item) {
            		//console.log(item.id + " / " + item.time + " / " + item.place + " / " + item.content);
            		var diaryHtml = '<li>'
										+ '<div class="d-content">' + item.content + '</div>'
										+ '<div class="d-info">'
											 + '<div class="d-left">'
												+ '<span class="d-time">' + formatDate(new Date(item.time)) + '</span>'
												+ '<span class="d-fen">&nbsp;|&nbsp;</span>'
												+ '<span class="d-city">' + item.place + '</span></div>'
											+ '<div class="d-right">'
												+ '<a href="javascript:;" title="Like"><i class="fa fa-heart-o like-no" id="like-no"></i></a><i class="fa fa-heart like" id="like"></i><span>10</span></div>'
											+ '<div class="clear"></div>'
										+ '</div>'
									+ '</li>';
					$('#diary-content').append(diaryHtml);
            	});

            	var htmlPage = data.data.page;
            	var htmlTotalPages = data.data.totalPages;



            	var diaryPaging = '<div class="d-more" id="diary-paging">'
            							+ '<input type="hidden" id="page" value="' + htmlPage + '" />'
            							+ '<input type="hidden" id="totalPages" value="' + htmlTotalPages + '" />'
										+ '<a href="javascript:;" id="prePaging">上一页</a>'
										+ '<a href="javascript:;" class="d-next" id="nextPaging">下一页</a>'
										+ '<div class="clear"></div>'
									+ '</div>';

				$('#diary-content').append(diaryPaging);

				if (htmlPage == 0) {
            		document.getElementById('prePaging').style.display = 'none';
            	}

            	if (htmlPage == htmlTotalPages - 1) {
            		document.getElementById('nextPaging').style.display = 'none';
            	}

       

            } else {
            	alert(data.msg);
            }

        },
        error: function(xhr, textStatus){
        	// sory ....
            alert('数据请求出错，请刷新试试');
        },
        complete: function(){
            
        }
    });
}