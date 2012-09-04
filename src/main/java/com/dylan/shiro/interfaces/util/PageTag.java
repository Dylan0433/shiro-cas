package com.dylan.shiro.interfaces.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author laigood
 * @date 2012-3-7
 * 
 **/
public class PageTag extends TagSupport {
  private static final long serialVersionUID = 183373550159351609L;
  private int curpage;// 当前页
  private int pages;// 总页数
  private String urlstart;
  private String urlend;
  
  public PageTag() {}
  
  public int doStartTag() throws JspException {
    try {
      String bar = getNavigatorBar();
      pageContext.getOut().write(bar);
      return SKIP_BODY;
    } catch (IOException e) {
      e.printStackTrace();
      throw new JspException(e);
    }
  }
  
  private String getNavigatorBar() {
    
    StringBuffer bar = new StringBuffer();
    if (pages == -1) {
      bar.append("");
      return bar.toString();     
    }
    
    /**
     * 第一页
     */
    // 如果是第一页
    if (curpage == 0) {
      bar.append("<span class='current'>1</span>");
    } else {
      bar.append("<a href='" + urlstart + (curpage - 1) + urlend + "'>上一页</a>")
          .append("<a href='" + urlstart + 0 + urlend + "'>1</a>");
    }
    /**
     * 结束(第一页)
     */
    
    // 如果总页数小于11
    if (pages <= 11) {
      for (int i = 1; i < pages; i++) {
        if (curpage == i) {
          bar.append("<p class='current'>" + (i + 1) + "</p>");
        } else {
          bar.append("<a href='" + urlstart + i + urlend + "'>" + (i + 1)
              + "</a>");
        }
      }
    } else {// 总页数大于11
      if (curpage <= 6) { // 当前页小页等于6时 (页码要与前面的街上)
        for (int i = 1; i <= 7; i++) {
          if (curpage == i) {
            bar.append("<p class='current'>" + (i + 1) + "</p>");
          } else {
            bar.append("<a href='" + urlstart + i + urlend + "'>" + (i + 1)
                + "</a>");
          }
        }
        bar.append("<p>....</p>");
        bar.append("<a href='" + urlstart + (pages - 1) + urlend + "'>" + pages
            + "</a>");
      } else if (curpage >= pages - 5) { // 当前页大于最大页-5时 要与后面的接上
        bar.append("<a href='" + urlstart + 2 + urlend + "'>" + 2 + "</a>");
        bar.append("<p>....</p>");
        for (int i = pages - 5 - 3; i <= pages - 1; i++) {
          if (curpage == i) {
            bar.append("<p class='current'>" + (i + 1) + "</p>");
          } else {
            bar.append("<a href='" + urlstart + i + urlend + "'>" + (i + 1)
                + "</a>");
          }
        }
      } else {
        bar.append("<a href='" + urlstart + 2 + urlend + "'>" + 2 + "</a>");
        bar.append("<p>....</p>");
        for (int i = curpage - 3; i <= curpage + 3; i++) {
          if (curpage == i) {
            bar.append("<p class='current'>" + (i + 1) + "</p>");
          } else {
            bar.append("<a href='" + urlstart + i + urlend + "'>" + (i + 1)
                + "</a>");
          }
        }
        bar.append("<p>....</p>");
        bar.append("<a href='" + urlstart + (pages - 1) + urlend + "'>" + pages
            + "</a>");
      }
    }
    
    /**
     * 最后一页
     */
    // 如果是最后一页
    if (curpage == pages) {
      if (pages != 0) {
        bar.append("<p class='current'>" + (pages + 1) + "</p>");
      }
    } else {
      bar.append(
          "<a href='" + urlstart + pages + urlend + "'>" + (pages + 1) + "</a>")
          .append("<a href='" + urlstart + (curpage + 1) + urlend + "'>下一页</a>");
    }
    bar.append("<select style='height:26px;text-align: center;padding: 0.3em 0.5em;' onchange=\"location.href='"
        + urlstart + "'+this.value+'" + urlend + "'\">");
    for (int i = 0; i <= pages; i++) {
      if (curpage == i) {
        bar.append("<option value='" + i + "' selected='selected'>")
            .append("第" + (i + 1) + "页").append("</option>");
      } else {
        bar.append("<option value='" + i + "'>").append("第" + (i + 1) + "页")
            .append("</option>");
      }
    }
    bar.append("</select>");
    return bar.toString();
  }
  
  public int doEndTag() throws JspTagException {
    return EVAL_PAGE;
  }
  
  public String getUrlend() {
    return urlend;
  }
  
  public void setUrlend(String urlend) {
    this.urlend = urlend;
  }
  
  public String getUrlstart() {
    return urlstart;
  }
  
  public void setUrlstart(String urlstart) {
    this.urlstart = urlstart;
  }
  
  public int getCurpage() {
    return curpage;
  }
  
  public void setCurpage(int curpage) {
    this.curpage = curpage;
  }
  
  public int getPages() {
    return pages;
  }
  
  public void setPages(int pages) {
    this.pages = pages;
  }
  
}
