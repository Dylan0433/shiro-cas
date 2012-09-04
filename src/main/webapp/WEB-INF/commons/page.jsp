<%@ page language="java"  pageEncoding="utf-8"%>
<%@include file="/WEB-INF/commons/taglibs.jsp" %>
<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}"/>
<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
<input type="hidden" name="order" id="order" value="${page.order}" />
共有${page.totalCount }条记录，第${page.pageNo }页, 共有 ${page.totalPages}页
<a href="javascript:mimo.jumpPage(1)">首页</a>
<c:if test="${page.isHasPre}"><a href="javascript:mimo.jumpPage(${page.prePage })"><span class="pagePrev">&lt;&lt;上一页</span></a> </c:if>
<c:if test="${page.isHasNext}"><a href="javascript:mimo.jumpPage(${page.nextPage })" class="pageNext">下一页&gt;&gt;</a></c:if>
<a href="javascript:mimo.jumpPage(${page.totalPages })">末页</a>