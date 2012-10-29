<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>登录先声网</title>
        <link rel="shortcut icon" href="herald.ico" />
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" />
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="container" style="margin: 100px auto;">
            <div class="row">
            <div class="span5" style="background-color: black;">
                <img src="images/login.png" />
            </div>
            <div class="span4">
                <form method="POST" action="http://localhost/sso/authentication" class="form-horizontal">
                <div class="control-group">
                  <label class="control-label" for="inputEmail">一卡通</label>
                  <div class="controls">
                      <input type="text" placeholder="一卡通" name="username" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="inputPassword">密码</label>
                  <div class="controls">
                    <input type="password" id="inputPassword" placeholder="密码" name="password" />
                  </div>
                </div>
                <div class="control-group">
                  <div class="controls">
                    <label class="checkbox">
                        <input type="checkbox" name="rememberme" /> 记住我
                    </label>
                    <button type="submit" class="btn">登录</button>
                  </div>
                </div>
                <input type="hidden" name="type" value="form" />
                <input type="hidden" name="success" value="http://herald.seu.edu.cn/" />
                <input type="hidden" name="failure" value="" />
              </form>
            </div>
            </div>
         </div>
    </body>
</html>
