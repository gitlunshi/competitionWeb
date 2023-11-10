function modifyUserInfo(userInfo){
    let data=false;
    $.ajax({
        type: 'POST', // 规定请求的类型（GET 或 POST）
        url: '/user/modifyUserInfo', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        data:JSON.stringify(userInfo) ,//规定要发送到服务器的数据
        async:false,
        contentType:"application/json",
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                app.$message.error(result.msg);
            }else{
                data=true;
            }
        },
        error: function (result) { //失败的函数

        },
        complete: function () { //请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后，不管成功还是失败 都会进这个函数）
        }
    });
    return data;
}

function getUserInfo(){
    let data=false;
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/user/getUserInfo', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                app.$message.error(result.msg);
            }else{
                data=result.data;
            }
        },
        error: function (result) { //失败的函数

        },
        complete: function () { //请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后，不管成功还是失败 都会进这个函数）
        }
    });
    return data;
}