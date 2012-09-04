<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/commons/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/wm.css" rel="stylesheet" type="text/css" />
<title>亲，你没有权限哦</title>
</head>
<body>
<div class="div960">
<div class="page_404"><a href="${ctx}/admin/right"><img src="${ctx}/resources/images/400.gif"></img></a></div>
<div class="tit">亲！您没有访问权限喔～</div>
</div>
</body>
</html>
