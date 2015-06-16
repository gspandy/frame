<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>登陆</title> 
<jsp:include page="shared/importCss.jsp"/>
<jsp:include page="shared/importJs.jsp"/>
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="icon-leaf green"></i> <span class="red">Metes/bonwe</span><br/>
									<span class="white">账务中心</span>
							</h1>
							<h4 class="blue">&copy; Company Name</h4>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i> Please Enter Your Information
										</h4>

										<!--  div class="space-6">${errorMessage}</div>-->
										<div style="color: red">${errorMessage}</div>
										
										<form action="${pageContext.request.contextPath}/login/userLogin" method="POST">
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="用户名" name="userName"/>
														<i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="密码" name="password"/> <i class="icon-lock"></i>
												</span>
												</label>

												<div class="space"></div>

												<div class="clearfix">
													<label class="inline"> <input type="checkbox"
														class="ace" /> <span class="lbl"> 记住密码</span>
													</label>

													<button type="submit"
														class="width-35 pull-right btn btn-sm btn-primary">
														<i class="icon-key"></i> 登陆
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>

										<div class="social-or-login center">
											<span class="bigger-110">Or Login Using</span>
										</div>

										<div class="social-login center">
											<a class="btn btn-primary"> <i class="icon-facebook"></i>
											</a> <a class="btn btn-info"> <i class="icon-twitter"></i>
											</a> <a class="btn btn-danger"> <i class="icon-google-plus"></i>
											</a>
										</div>
									</div>
									<!-- /widget-main -->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i class="icon-arrow-left"></i>
												忘记密码
											</a>
										</div>

										<div>
											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> 注册 <i
												class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							
							
							<!-- 忘记密码 -->
							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> 找回密码
										</h4>

										<div class="space-6"></div>
										<h4>填写邮箱找回密码</h4>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label>

												<div class="clearfix">
													<button type="button"
														class="width-35 pull-right btn btn-sm btn-danger">
														<i class="icon-lightbulb"></i> 发送给我
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> 返回登陆界面 <i
											class="icon-arrow-right"></i>
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							
							<!-- ===========新用户注册界面==================== -->
							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i>新用户注册
										</h4>
										<div class="space-6"></div>
										<p>请填写个人信息:</p>
										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="用户名" />
														<i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="密码" /> <i class="icon-lock"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="确认密码" /> <i class="icon-retweet"></i>
												</span>
												</label> <label class="block"> <input type="checkbox"
													class="ace" /> <span class="lbl"> 我同意 <a href="#">用户协议</a>
												</span>
												</label>

												<div class="space-24"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="icon-refresh"></i> 重置
													</button>

													<button type="button"
														class="width-65 pull-right btn btn-sm btn-success">
														注册 <i class="icon-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											返回登陆页面
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /signup-box -->
						</div>
						<!-- /position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->

	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='../assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='../assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		function show_box(id) {
			jQuery('.widget-box.visible').removeClass('visible');
			jQuery('#' + id).addClass('visible');
		}
	</script>
	<div style="display: none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>