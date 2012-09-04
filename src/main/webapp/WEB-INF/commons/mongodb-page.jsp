<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="pages" uri="/WEB-INF/pages.tld" %> 
<input type="hidden" name="page" id="page" value="${page.pageNo - 1}"/>
<input type="hidden" name="size" id="size" value="${page.pageSize}"/>
<div class="s_page">
	<pages:page 
		curpage="${page.pageNo - 1}" 
		pages="${page.totalPages - 1}" 
		urlstart="javascript:youboy.jumpPage("
		urlend=")" 
		/>   
</div>