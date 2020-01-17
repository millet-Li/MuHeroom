/*2019/11/5*/

//发表动态
function pubNewLife() {
    var author = $("#m_author").val();
    var l_text = $("#m_l_text").val();
    var l_att = $("#m_l_att").val();
    var fileA = document.getElementById("m_file").files[0];

    if (fileA == null && l_text === "") {
        alert("不能发表没内容的动态！");
        return;
    }
    //实例form对象
    var formData = new FormData();
    formData.append("author", author);
    formData.append("l_text", l_text);
    formData.append("l_att", l_att);
    formData.append("file", fileA);
    $.ajax({
        type: "post",
        url: "/pubLife",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        success: function (response) {
            //调用
            getMyNewLife();
            //清空
            $("#m_l_text").val('');
            $("#m_file").val('');
            $("#m_file_name").val('');
            $('#imgLi').html('');
        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}

//发表动态图片预览功能实现
function imgPre(e) {
    var ele = document.getElementById('m_file').files[0];

    var createObjectURL = function (blob) {
        return window[window.webkitURL ? 'webkitURL' : 'URL']['createObjectURL'](blob);
    };
    var newimgdata = createObjectURL(ele);

    var pvImg = new Image();
    pvImg.src = newimgdata;
    pvImg.style = "max-width: 200px";
    pvImg.setAttribute('id', 'previewImg');
    $('#imgLi').html('').append(pvImg);
    //$('#imgY').attr('src',pvImg.src);
}

//请求自己刚发布的动态
function getMyNewLife() {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/getMyNewLife",
        data: {
            "author": username
        },
        success: function (response) {
            var response = JSON.parse(response);
            //将数据加入到data中
            for (var i = 0; i < response.length; i++) {
                that.life.unshift(response[i]);
            }
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });
}

//动态评论功能实现
function subComment(e) {
    var to_life_id = e.getAttribute("data-id");
    var to_user_id = $("#life_author-" + to_life_id).val();
    var c_text = $("#input_text-" + to_life_id).val();

    subComment2target(to_user_id, to_life_id, c_text);
}

//动态评论子函数
function subComment2target(to_user_id, to_life_id, c_text) {
    if (!c_text) {
        alert("不能评论空内容");
        return;
    }
    $.ajax({
        type: "post",
        url: "/subComment",
        data: {
            "user_id": username,
            "to_user_id": to_user_id,
            "c_type": 1,
            "to_life_id": to_life_id,
            "c_text": c_text
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                //window.location.reload();
                //调用函数
                getNewCom(to_user_id, to_life_id);
                //清空输入框
                $("#input_text-" + to_life_id).val('');
                //改变浏览数、评论数
                var com_ = $("#com_c_" + to_life_id);
                var look_ = $("#look_l_" + to_life_id);
                var com_c = com_.html();
                var look_c = look_.html();
                com_c++;
                look_c++;
                com_.html(com_c);
                look_.html(look_c);
            } else
                alert(response);
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });

}

//一二级评论回复功能实现
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var to_com_id = e.getAttribute("data-to_cid");
    var to_user_id = $("#to_user_id-" + commentId).val();
    var to_life_id = $("#to_life_id-" + commentId).val();
    var c_text = $("#input-" + commentId).val();

    comment2target(to_user_id, to_life_id, commentId, to_com_id, c_text);
}

//一二级评论回复子函数
function comment2target(to_user_id, to_life_id, commentId, to_com_id, c_text) {
    if (!c_text) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "post",
        url: "/replyComment",
        data: {
            "user_id": username,
            "to_user_id": to_user_id,
            "to_life_id": to_life_id,
            "c_type": 2,
            "to_col_id": to_com_id,
            "c_text": c_text
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                //调用函数
                getNewCom(to_user_id, to_life_id);
                //清空评论框
                $("#input-" + commentId).val('');
                //改变浏览数、评论数
                var com_ = $("#com_c_" + to_life_id);
                var look_ = $("#look_l_" + to_life_id);
                var com_c = com_.html();
                var look_c = look_.html();
                com_c++;
                look_c++;
                com_.html(com_c);
                look_.html(look_c);
            } else
                alert(response);

        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });
}

//请求我刚生成的最新的评论或回复
function getNewCom(to_user_id, to_life_id) {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/getNewCom",
        data: {
            "user_id": username,
            "to_user_id": to_user_id,
            "to_life_id": to_life_id
        },
        success: function (response) {
            var response = JSON.parse(response);
            //将数据加入到data中
            for (var i = 0; i < response.length; i++) {
                that.comment.push(response[i]);
            }
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        },
    });
}

//一级评论回复框图标颜色控制
function glyComment(e) {
    var id = e.getAttribute("id");
    var gly_id = $("#" + id);

    var collapse = e.getAttribute("aria-expanded");
    if (collapse === "true") {
        // gly_id.addClass("gly-com");
        gly_id.removeClass("gly-com");
        gly_id.addClass("gly-coma");
    } else {
        gly_id.removeClass("gly-coma");
        gly_id.addClass("gly-com");
        // gly_id.removeClass("gly-com");
    }
}

//动态点赞调用的函数
function glyLike(e) {
    var id = e.getAttribute("id");
    var l_id = e.getAttribute("data-id");
    var to_user_id = e.getAttribute("data-author");
    var like_id = $("#" + id);

    var look_ = $("#look_" + id);
    var look_c = look_.html();

    var like_ = $("#like_" + id);
    var like_c = like_.html();

    var status = e.getAttribute("data-a");
    if (status === null) {
        like_id.removeClass("gly-ica");
        like_id.addClass("gly-ic");
        e.setAttribute("data-a", "a");
        like2targeta(l_id, to_user_id);
        like_c++;
    } else {
        like_id.removeClass("gly-ic");
        like_id.addClass("gly-ica");
        e.removeAttribute("data-a");
        like2targetb(l_id, to_user_id);
        like_c--;
    }

    look_c++;
    look_.html(look_c);
    like_.html(like_c);

}

//加赞   改11/11 12/10
function like2targeta(l_id, to_user_id) {
    $.ajax({
        type: "post",
        url: "/addLike",
        data: {
            "l_id": l_id,
            "user_id": username,
            "to_user_id": to_user_id
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response == "true") {
                // alert("点赞成功~");
                // window.location.reload();
            } else
                alert(response);

        },
        error: function (msg) {
            // alert(msg);
            alert("请求失败");
        },
    });
}

//减赞
function like2targetb(l_id, to_user_id) {
    $.ajax({
        type: "post",
        url: "/redLike",
        data: {
            "l_id": l_id,
            "user_id": username,
            "to_user_id": to_user_id
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response == "true") {
                // alert("取消赞成功~");
                //    window.location.reload();
            } else
                alert(response);

        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        },
    });
}

//删除动态
function delLife(e) {
    var that = this.vm;
    var id = e.getAttribute("id");
    var l_id = e.getAttribute("data-id");

    var r = confirm("确认删除此动态吗？");
    if (r === false)
        return;

    $.ajax({
        type: "post",
        url: "/delLife",
        data: {"l_id": l_id},
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                /*alert("删除动态成功~");
                   window.location.reload();*/

                that.life.some((item, i) => {
                    if(item.l_id == l_id
            )
                {
                    that.life.splice(i, 1)
                }
            })
            } else
                alert(response);

        },
        error: function (msg) {
            alert("请求失败");
        },
    });
}

//删除评论
function delComment(e) {
    var that = this.vm;

    var c_id = e.getAttribute("data-id");
    var to_life_id = e.getAttribute("data-lid");

    var r = confirm("确认删除此评论吗？");
    if (r === false) {
        return;
    }

    $.ajax({
        type: "post",
        url: "/delComment",
        data: {
            "c_id": c_id,
            "to_life_id": to_life_id
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                /*window.location.reload();*/

                that.comment.some((item, i) => {
                    if(item.c_id == c_id)
                {
                    that.comment.splice(i, 1)
                }
            });

                //改变浏览数、评论数
                var com_ = $("#com_c_" + to_life_id);
                var look_ = $("#look_l_" + to_life_id);
                var com_c = com_.html();
                var look_c = look_.html();
                com_c--;
                look_c++;
                com_.html(com_c);
                look_.html(look_c);

            } else
                alert(response);

        },
        error: function (msg) {
            alert("请求失败");
        },
    });
}

//消息通知控制
function setNotic(e) {
    var c_id = e.getAttribute("data-id");
    var status = e.getAttribute("data-s");
    var c_ida = $("#" + c_id);
    var notice = $("#notice");
    var not_val = notice.html();
    var ic_notice = $("#ic_notice");

    if (status === null) {
        setRead(c_id, c_ida, notice, not_val, ic_notice);
        e.setAttribute("data-s", "a");
    } else if (status === "a") {
        e.removeAttribute("data-s");
        setNotRead(c_id, c_ida, notice, not_val, ic_notice);
    }

}

//设置消息已读
function setRead(c_id, c_ida, notice, not_val, ic_notice) {

    $.ajax({
        type: "post",
        url: "/setNoticeRead",
        data: {"c_id": c_id},
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                // alert("该消息已读");
                c_ida.removeClass("label-danger");
                c_ida.addClass("label-default");
                c_ida.html("已读");
                not_val--;
                notice.html(not_val);
                if (not_val == 0) {
                    notice.removeClass("notice");
                    ic_notice.removeClass("ic-notice");
                }
                // window.location.reload();
            } else
                alert(response);
        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}

//设置消息未读
function setNotRead(c_id, c_ida, notice, not_val, ic_notice) {

    $.ajax({
        type: "post",
        url: "/setNoticeNotRead",
        data: {"c_id": c_id},
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                // alert("该消息已读");
                c_ida.removeClass("label-default");
                c_ida.addClass("label-danger");
                c_ida.html("未读");
                if (not_val == 0) {
                    notice.addClass("notice");
                    ic_notice.addClass("ic-notice");
                }
                not_val++;
                notice.html(not_val);
                // window.location.reload();
            } else
                alert(response);

        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}

//查询未读消息数量
function inqNoticeNotRead() {

    // var c_id = e.getAttribute("id");
    var notice = $("#notice");
    var ic_notice = $("#ic_notice");

    $.ajax({
        type: "post",
        url: "/inqNotice",
        data: {"to_user_id": username},
        success: function (response) {
            var notNum = response;
            if (notNum !== 0) {
                notice.addClass("notice");
                ic_notice.addClass("ic-notice");
                notice.html(notNum);
            }
        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}

//关注控制
function friend(e) {
    var f_id = e.getAttribute("data-id");
    var f_friend_id = e.getAttribute("data-friend_id");
    var status = e.getAttribute("data-s");
    var f_ida = $('#f_' + f_id);

    if (status === "a") {
        /*取消关注*/
        removeFriend(f_friend_id);
        /*f_ida.addClass("btn-info");
        f_ida.html("关注");
        e.removeAttribute("data-s")*/;
    } else {
        /*加关注*/
        addFriend(f_friend_id);
        /*f_ida.removeClass("btn-info");
        f_ida.html("已关注");
        e.setAttribute("data-s", "a");*/
    }
}

//取消关注
function removeFriend(f_friend_id) {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/removeFriend",
        data: {
            "f_user_id": username,
            "f_friend_id": f_friend_id
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                /*alert("取消关注成功");*/
                // window.location.reload();
                that.friends.some((item, i) => {
                    if(item.f_friend_id == f_friend_id
            )
                {
                    that.friends.splice(i, 1)
                }
            });

            } else
                alert(response);
        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}
//获取刚关注的朋友
function getMyNewFri() {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/getMyNewFriend",
        data: {
            "username": username
        },
        success: function (response) {
            var response = JSON.parse(response);
            //将数据加入到data中
            for (var i = 0; i < response.length; i++) {
                that.friends.unshift(response[i]);
            }
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        },
    });
}

//添加关注
function addFriend(f_friend_id) {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/addFriend",
        data: {
            "f_user_id": username,
            "f_friend_id": f_friend_id
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                /*alert("关注成功");*/
                // window.location.reload();
                getMyNewFri();
            } else
                alert(response);
        },
        error: function (msg) {
            alert("请求失败");
        }
    });
}

//留言
function leave() {
    var to_user_id = $("#leaTo").val();
    var c_text = $("#leaText").val();

    if (!c_text) {
        alert("不能留言空内容~~~");
        return;
    }
    $.ajax({
        type: "post",
        url: "/subLeave",
        data: {
            "user_id": username,
            "to_user_id": to_user_id,
            "c_type": 7,
            "c_text": c_text
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                /*alert("");*/
                // window.location.reload();
                getNewLew(to_user_id);
                $("#leaText").val('');
            } else
                alert(response);

        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });
}

//留言回复
function repLeave(e) {
    // var that = this.vm;
    var c_id = e.getAttribute("data-id");
    var to_user_id = $("#to_user_id-" + c_id).val();
    var c_text = $("#input-" + c_id).val();
    // var time = (new Date()).format("MM-dd hh:mm");

    if (!c_text) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "post",
        url: "/repLeave",
        data: {
            "user_id": username,
            "to_user_id": to_user_id,
            "c_type": 8,
            "to_col_id": c_id,
            "c_text": c_text
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                // window.location.reload();
                getNewToLew(to_user_id);
                $("#input-" + c_id).val('');
            } else
                alert(response);

        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        },
    });
}

//请求我刚生成的最新的留言
function getNewLew(to_user_id) {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/getNewLew",
        data: {
            "user_id": username,
            "to_user_id": to_user_id
        },
        success: function (response) {
            var response = JSON.parse(response);
            //将数据加入到data中
            for (var i = 0; i < response.length; i++) {
                that.myLeave.unshift(response[i]);
            }
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });
}

//请求我刚生成的最新的留言回复
function getNewToLew(to_user_id) {
    var that = this.vm;
    $.ajax({
        type: "post",
        url: "/getNewLew",
        data: {
            "user_id": username,
            "to_user_id": to_user_id
        },
        success: function (response) {
            var response = JSON.parse(response);
            //将数据加入到data中
            for (var i = 0; i < response.length; i++) {
                that.myToLeave.push(response[i]);
            }
        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        }
    });
}

//删除留言及回复
function delLea(e) {
    var that = this.vm;
    var c_id = e.getAttribute("data-id");
    var c_type = e.getAttribute("data-c_type");
    // var time1 = (new Date()).format("MM-dd hh:mm");

    //alert(c_type);
    var r = confirm("确认删除此留言吗？");
    if (r === false) {
        return;
    }

    $.ajax({
        type: "post",
        url: "/delLea",
        data: {"c_id": c_id},
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                // alert("删除留言成功~");
                if (c_type == 7) {
                    that.myLeave.some((item, i) => {
                        if(item.c_id == c_id
                )
                    {
                        that.myLeave.splice(i, 1)
                    }
                })
                } else {
                    that.myToLeave.some((item, i) => {
                        if(item.c_id == c_id
                )
                    {
                        that.myToLeave.splice(i, 1)
                    }
                })
                }
                /*window.location.reload();*/
            } else
                alert(response);

        },
        error: function (msg) {
            alert("请求失败");
        },
    });
}

//更改动态公开范围
function alterAtt(e) {
    var l_id = e.getAttribute("data-id");
    var l_att = e.getAttribute("data-att");
    var Lid = $('#Lid_' + l_id);
    var Lat = $('#Lat_' + l_id);

    altAtt(l_id, l_att, Lid, Lat);
    if (l_att == 1)
        e.setAttribute("data-att", "2");
    else e.setAttribute("data-att", "1");
}
//向后端传送数据
function altAtt(l_id, l_att, Lid, Lat) {
    $.ajax({
        type: "post",
        url: "/altAtt",
        data: {
            "l_id": l_id,
            "l_att": l_att
        },
        success: function (response) {
            // var obj = JSON.parse(response);
            if (response === "true") {
                // window.location.reload();
                if (l_att == 1) {
                    Lid.removeClass("panel-success");
                    Lid.addClass("panel-info");
                    Lat.html("公开");
                } else {
                    Lid.removeClass("panel-info");
                    Lid.addClass("panel-success");
                    Lat.html("关注可见");
                }

            } else
                alert(response);

        },
        error: function (msg) {
            alert(msg);
            alert("请求失败");
        },
    });
}

/*
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}*/
