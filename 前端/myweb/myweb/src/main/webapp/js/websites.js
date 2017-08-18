/*
jQuery(document).ready(function($) {
	//我要推荐
  $('#iRecommend').click(function(){
    $('#recommend-bg').fadeIn(100);
    $('#recommend').slideDown(200);
  })

  	//我的书签
  $('#iBookmarks').click(function(){
    $('#recommend-bg').fadeIn(100);
    $('#recommend').slideDown(200);
    //$('#form-iBookmarks').slideDown(200);

    // 需要隐藏一些东西
    $('#import-a').hide();
    $('#form').hide();
    $('#form-import').hide();

    // 显示
    $('#form-iBookmarks').show();
    $('#title').html('我的书签');
  })

  //close
  $('#close').click(function(){
    $('#recommend-bg').fadeOut(100);
    $('#recommend').slideUp(200);
  })
})
*/

$(function() {

	var URI = getUrl();

	$('#form-import').hide();
	$('#form').show();

/*
	// 我在这里隐藏一些东西
	//隐藏弹出界面背景
	$('#recommend-bg').hide();
	//隐藏数据加载提示
	$('#data-load-tip').hide();
	// 隐藏弹出框界面
	$('#recommend').hide();
	//隐藏弹出框标题
	$('#recommend-title').hide();
	// 隐藏推荐输入框
	$('#form').hide();
	// 隐藏推荐导入浏览器书签
	$('#form-import').hide();
	// 隐藏个人书签
	$('#form-iBookmarks').hide();
	// 隐藏我的书签导出
	$('#iBookmarks-export').hide();
	// 隐藏浏览器书签导入提示
	$('#import-a').hide();
	// 书签导出选项
	$('#iBookmarks-export-option').hide();
	// 书签示例代码
	$('#iBookmarks-export-ajax-demo').hide();
*/

	//界面点击事件
	// 推荐(背景，弹窗背景，推荐)
	$('#iRecommend').on('click', function() {
		$('#recommend-bg').fadeIn(100); //1

    	$('#recommend').slideDown(200); //2

    	//3
    	$('#recommend-title').show();
    	$('#form').show();
    	$('#import-a').show();
	});

	// 关闭
	$('#close').on('click', function() {
		$('#recommend-bg').fadeOut(100); // 1
    	$('#recommend').slideUp(200); // 2

    	// 3
    	$('#recommend-title').hide();
		$('#form').hide();
		$('#form-import').hide();
		$('#form-iBookmarks').hide();
		$('#import-a').hide();
	});

	// 我的书签
	$('#iBookmarks').on('click',function(){
	    $('#recommend-bg').fadeIn(100);
	    $('#recommend').slideDown(200);
	    //$('#form-iBookmarks').slideDown(200);

	    // 需要隐藏一些东西
	    $('#import-a').hide();
	    $('#form').hide();
	    $('#form-import').hide();

	    // 显示
	    $('#form-iBookmarks').show();
	    $('#title').html('我的书签');
  	});

  	//书签导出选项
	$('#iBookmarks-export').on('click', function() {
		$('#recommend-bg').fadeIn(100);
	    $('#recommend').slideDown(200);

	    $('#title').html('书签导出');

	    // show
	    $('#recommend-title').show();
	    $('#iBookmarks-export-option').show();
	});

	$('#submit-iBookmarks-export-ajax').on('click', function() {
		$('#title').html('书签导出Ajax代码示例');

	    // show
	    $('#recommend-title').show();
	    $('#iBookmarks-export-ajax-demo').show();
	    $('#iBookmarks-export-option').hide();
	});

	//网址大全
	$.ajax({
        url: URI + '/url/all',
        type: 'GET', //GET
        async: true,    //或false,是否异步
        data: {
        	
        },
        timeout: 10000,    //超时时间 10s
        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend: function(xhr){
            //loading
            $('#recommend-bg').fadeIn(100);
    		$('#data-load-tip').slideDown(200);
        },
        success: function(data, textStatus, jqXHR){
            var code = data.code;
            if (code == 0) {
            	$.each(data.data, function(i, item) {
            		var urlHtml = '<li>'
            							+ '<span class="http-count">' + (i + 1) + '</span>'
										+ '<a class="name" target="_blank" href="' + item.url + '">' + item.name + '</a>'
										+ '<div class="info">' + formatDate(new Date(item.time)) + '<span class="division">|</span>' + item.nickname + '</div>'
										+ '<div class="desc">' + item.introduce + '</div>'
									+ '</li>';
					$('#url').append(urlHtml);
            	});

            	var clear = '<div class="clear"></div>';
            	$('#url').append(clear);

            } else {
            	alert(data.msg);
            }

        },
        error: function(xhr, textStatus){
        	
        },
        complete: function(){
            //loaded
            $('#recommend-bg').fadeOut(100);
    		$('#data-load-tip').slideUp(200);
        }
    });

    //所有的点击事件
    $('#import-a').on('click', function() {
		//
		$('#form').hide();
		$('#form-import').show();
	});

	// 网址推荐
	$('#submit').on('click', function() {
		var name = $('#name').val();
		var url = $('#web-url').val();
		var introduce = $('#introduce').val();
		var nickname = $('#nickname').val();
		var phone = $('#phone').val();

		if (name !== null && name !== undefined && name !== ''
			&& url !== null && url !== undefined && url !== ''
			&& introduce !== null && introduce !== undefined && introduce !== ''
			&& nickname !== null && nickname !== undefined && nickname !== ''
			&& phone !== null && phone !== undefined && phone !== '') {
			
			$.ajax({
		        url: URI + '/url/add',
		        type: 'POST', //GET
		        async: true,    //或false,是否异步
		        data: {
		        	name: name,
		        	url: url,
		        	introduce: introduce,
		        	nickname: nickname,
		        	phone: phone
		        },
		        timeout: 3000,    //超时时间 3s
		        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		        beforeSend: function(xhr){
		            //loading
    				$('#data-load-tip').slideDown(200);
		        },
		        success: function(data, textStatus, jqXHR){
		            alert('Success');
		            location.href="./websites.html";
		        },
		        error: function(xhr, textStatus){
		        	
		        },
		        complete: function(){
		            //loaded
		            $('#recommend-bg').fadeOut(100);
    				$('#data-load-tip').slideUp(200);
		        }
		    });

		} else {
			alert('不能为空。感谢');
		}
	});


	// 浏览器书签导入
	$('#import').on('click', function() {

		var file = document.getElementById("bookmarks").files[0];
		var nicknameImport = $('#nickname-import').val();
		var phoneImport = $('#phone-import').val();

		if (file !== null && file !== undefined && file !== ''
			&& nicknameImport !== null && nicknameImport !== undefined && nicknameImport !== ''
			&& phoneImport !== null && phoneImport !== undefined && phoneImport !== '') {

			var formData = new FormData();

			formData.append('bookmarks', file);
			formData.append('nickname', nicknameImport);
			formData.append('phone', phoneImport);
			
			$.ajax({
		        url: URI + '/url/import',
		        type: 'POST', //GET
		        async: true,    //或false,是否异步
		        data: formData,
		        contentType: false, //必须false才会自动加上正确的Content-Type

		        /**
                    * 必须false才会避开jQuery对 formdata 的默认处理
                    * XMLHttpRequest会对 formdata 进行正确的处理
                    */
		        processData: false, 
		        timeout: 1000 * 60,    //超时时间 60s(1分钟)
		        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		        beforeSend: function(xhr){
		            //loading
    				$('#data-load-tip').slideDown(200);
		        },
		        success: function(data, textStatus, jqXHR){

		        	console.log(data);

		        	if (data.code == 0) {
		        		alert('Success');
		        		location.href="./websites.html";
		        	} else {
		        		alert(data.msg);
		        	}
		            
		        },
		        error: function(xhr, textStatus){
		        	
		        },
		        complete: function(){
		            //loaded
		            $('#recommend-bg').fadeOut(100);
    				$('#data-load-tip').slideUp(200);
		        }
		    });

		} else {
			alert('不能为空。感谢');
		}
	});

	// 我的书签-查询
	$('#submit-iBookmarks').on('click', function() {
		var phone = $('#phone-iBookmarks').val();

		if (phone !== null && phone !== undefined && phone !== '') {
			
			$.ajax({
		        url: URI + '/url/my/' + phone,
		        type: 'GET', //GET
		        async: true,    //或false,是否异步
		        data: {
		        },
		        timeout: 3000,    //超时时间 3s
		        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		        beforeSend: function(xhr){
		            //loading
    				$('#data-load-tip').slideDown(200);
		        },
		        success: function(data, textStatus, jqXHR){

		            $('#url').html(''); //清空

		            // 1. 正文显示
		            var code = data.code;
		            if (code == 0) {
		            	$.each(data.data, function(i, item) {

		            		if (i == 0) {
		            			$('#iBookmarks-export').html('[ ' + item.nickname + ' ]书签导出');
		            		}

		            		

		            		//console.log(item.id + " / " + item.time + " / " + item.place + " / " + item.content);
		            		var urlHtml = '<li>'
		            							+ '<span class="http-count">' + (i + 1) + '</span>'
												+ '<a class="name" target="_blank" href="' + item.url + '">' + item.name + '</a>'
												+ '<div class="info">' + formatDate(new Date(item.time)) + '<span class="division">|</span>' + item.nickname + '</div>'
												+ '<div class="desc">' + item.introduce + '</div>'
											+ '</li>';
									$('#url').append(urlHtml);
		            	});

		            	var clear = '<div class="clear"></div>';
		            	$('#url').append(clear);

		            	// 2. 用户信息
			            //...
			            $('#iBookmarks-export').show();

			            $('#iBookmarks').hide();

			            $('#form-iBookmarks').hide();

		            	$('#recommend-bg').fadeOut(100);
    					$('#recommend').slideUp(200);

		            } else {
		            	alert(data.msg);
		            }

		        },
		        error: function(xhr, textStatus){
		        	
		        },
		        complete: function(){
		            //loaded
    				$('#data-load-tip').slideUp(200);
		        }
		    });

		} else {
			alert('不能为空。感谢');
		}
	});

	// 搜索
	$('#submit-iSearch').on('click', function() {
		var search = $('#search').val();

		if (search !== null && search !== undefined && search !== '') {
			
			$.ajax({
		        url: URI + '/url/n_i_like/' + search,
		        type: 'GET', //GET
		        async: true,    //或false,是否异步
		        data: {
		        },
		        timeout: 3000,    //超时时间 3s
		        dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		        beforeSend: function(xhr){
		            //loading
    				$('#data-load-tip').slideDown(200);
		        },
		        success: function(data, textStatus, jqXHR){

		            $('#url').html(''); //清空

		            // 1. 正文显示
		            var code = data.code;
		            if (code == 0) {
		            	$.each(data.data, function(i, item) {

		            		//console.log(item.id + " / " + item.time + " / " + item.place + " / " + item.content);
		            		var urlHtml = '<li>'
		            							+ '<span class="http-count">' + (i + 1) + '</span>'
												+ '<a class="name" target="_blank" href="' + item.url + '">' + item.name + '</a>'
												+ '<div class="info">' + formatDate(new Date(item.time)) + '<span class="division">|</span>' + item.nickname + '</div>'
												+ '<div class="desc">' + item.introduce + '</div>'
											+ '</li>';
									$('#url').append(urlHtml);
		            	});

		            	var clear = '<div class="clear"></div>';
		            	$('#url').append(clear);

		            } else {
		            	alert(data.msg);
		            }

		        },
		        error: function(xhr, textStatus){
		        	
		        },
		        complete: function(){
		            //loaded
    				$('#data-load-tip').slideUp(200);
		        }
		    });

		} else {
			alert('不能为空。感谢');
		}
	});

	//我的书签导出
	$('#submit-iBookmarks-export-html').on('click', function() {

		/* 方法一 */
		var phone = $('#phone-iBookmarks').val();

		var url = URI + '/url/export/' + phone;

		//window.open(url);

		/* 方法二 */
		var $eleForm = $("<form method='get'></form>");

        $eleForm.attr("action", url);

        $(document.body).append($eleForm);

        //提交表单，实现下载
        $eleForm.submit();
	});

});

// 时间格式化
function formatDate(now) {
　　var year = now.getFullYear(),
　　month = now.getMonth() + 1,
　　date = now.getDate();
　　return year + '-' + month + "-" + date;
}