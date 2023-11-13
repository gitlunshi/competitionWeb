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
                
               errorResDeal(result);
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
                
               errorResDeal(result);
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

function modifyPassword(passwordInfo){
    let data=false;
    $.ajax({
        type: 'POST', // 规定请求的类型（GET 或 POST）
        url: '/user/modifyPassword', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        data: passwordInfo,//规定要发送到服务器的数据
        contentType:"application/x-www-form-urlencoded",
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function getTeamInfo(){
    let data={};
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/squadron/getTeamInfo', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function creatTeamPost(squadronReq){
    let data=false;
    $.ajax({
        type: 'POST', // 规定请求的类型（GET 或 POST）
        url: '/squadron/creatTeam', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        data:JSON.stringify(squadronReq) ,//规定要发送到服务器的数据
        async:false,
        contentType:"application/json",
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function getProjectInfoList(){
    let data={};
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/projectInfo/getProjectInfoList', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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
function deleteSquadronById(id){
    let data=false;
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/squadron/deleteTeam/'+id, // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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
function updateSquadron(squadronReq){
    let data=false;
    $.ajax({
        type: 'POST', // 规定请求的类型（GET 或 POST）
        url: '/squadron/updateSquadron', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        data:JSON.stringify(squadronReq) ,//规定要发送到服务器的数据
        async:false,
        contentType:"application/json",
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function joinTeamByid(id){
    let data=false;
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/squadron/joinTeam/'+id, // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function getHomePage(id){
    let data= {};
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/user/getHomePage', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                errorResDeal(result);
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
function outTeamByid(id){
    let data=false;
    $.ajax({
        type: 'GET', // 规定请求的类型（GET 或 POST）
        url: '/squadron/outTeam/'+id, // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        async:false,
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function modifyCapInfo(modifyCapReq){
    let data=false;
    $.ajax({
        type: 'POST', // 规定请求的类型（GET 或 POST）
        url: '/squadron/modifyCap', // 请求的url地址
        dataType: 'json', //预期的服务器响应的数据类型
        data:JSON.stringify(modifyCapReq) ,//规定要发送到服务器的数据
        async:false,
        contentType:"application/json",
        beforeSend: function () { //发送请求前运行的函数（发送之前就会进入这个函数）

        },
        success: function (result) { // 当请求成功时运行的函数
            if (result.code!=200){
                
               errorResDeal(result);
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

function errorResDeal(rs){

    if (rs.code==1000){
        alert("未登录，或者登录已过期，请重新登录！！");
        location.href = "/apply";
        return;
    }

    if (rs.code==1001){
        window.sessionStorage.setItem("currentpage","userInfo");
        app.defaultActive="userInfo";
        app.ComponentValue.content=tabTyp["userInfo"]
        app.ComponentValue.name="userInfo";
    }
    app.$message.error(rs.msg);
}
