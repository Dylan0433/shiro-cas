<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/commons/common-header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>单点登录</title>
登录成功!!!!!!

<shiro:hasAnyRoles name="admin,webmaster">
admin或webmaster角色的用户都可以看到我
</shiro:hasAnyRoles>
<shiro:hasRole name="admin">
只有admin角色的用户才可以看到我<br>
</shiro:hasRole>

<shiro:hasRole name="webmaster">
只有webmaster角色的用户才可以看到我
</shiro:hasRole>


</html>