package im.hdy.utils;

import java.util.regex.Pattern;

public class HtmlUtils {
    /***
     *
     * @param content 内容String
     * @param p >0 .位数
     * @return @tale:
     * @purpose：得到相应位数已过滤html、script、style 标签的内容 内容结尾 为...
     * @author：Simon - 赵振明
     * @CreationTime：Aug 25, 201011:07:06 AM
     */
    public static String getNoHTMLString(String content, int p) {

        if (null == content) return "";
        if (0 == p) return "";

        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(content);
            content = m_script.replaceAll(""); //过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(content);
            content = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(content);

            content = m_html.replaceAll(""); //过滤html标签
        } catch (Exception e) {
            return "";
        }

        if (content.length() > p) {
            content = content.substring(0, p);
        } else {
            content = content;
        }


        return content;
    }
}
