<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="iHelin">
    <link rel="stylesheet" href="${request.contextPath}/css/styles.css">
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type='text/css'
          media="all"/>
</head>
<body class="focusedform">
<div class="verticalcenter">
    <div class="panel panel-primary">
        <form action="${request.contextPath}/admin/login" method="post" class="form-horizontal"
              style="margin-bottom: 0px !important;">
            <input type="hidden" name="from" value="${from!}">
            <div class="panel-body">
                <h4 class="text-center" style="margin-bottom: 25px;">管理后台</h4>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <input type="text" class="form-control" name="username" placeholder="用户名" required>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                            <input type="password" class="form-control" name="password" placeholder="密码" required>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-6">
                        <img src="${request.contextPath}/kaptcha"
                             onclick="this.src='${request.contextPath}/kaptcha'">
                    </div>
                    <div class="col-sm-6">
                        <div class="input-group" style="margin-left: 20px;margin-top: 10px;">
                            <span class="input-group-addon"><i class="fa fa-key"></i></span>
                            <input type="text" class="form-control" name="captcha" placeholder="验证码" required>
                        </div>
                    </div>
                </div>
            <#if error??><span class="text-danger">${error!}</span></#if>
            </div>
            <div class="panel-footer">
                <div class="pull-right">
                    <button type="reset" class="btn btn-default">重置</button>
                    <button type="submit" class="btn btn-primary">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>