package im.hdy.filter;

import im.hdy.constant.Constants;
import im.hdy.model.User;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 过滤未登录用户,跳转到登陆接口获取用户的信息和权限
 */
public class SessionFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    private final static String redirectUrL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WX_APPID + "&redirect_uri=" + Constants.URL + "/jewel/url&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
    //    private final static String redirectUrL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WX_APPID + "&redirect_uri=" + Constants.URL + "/jewel/url&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
    //    private final static String redirectUrL = "http://t.cn/RBBIBdd";
    //排除的一些请求地址...
    private final static String[] outofUrl = new String[]{Constants.URL + "/jewel/url"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //获取到当前正在请求的链接地址
        String pre_Url = httpRequest.getRequestURL().toString();
        String requestURI = httpRequest.getRequestURI();
        if (pre_Url != null && pre_Url.contains("detail")) {
            String queryString = ((HttpServletRequest) servletRequest).getQueryString();
            pre_Url = pre_Url + "?" + queryString;
        }
        logger.info("当前请求的地址为:" + pre_Url);
        logger.info("当前请求的requestURI地址为:" + requestURI);
//        logger.info("当前正在请求的链接地址:" + pre_Url);
        //获取到session对象,通过session中判断用户是否已经登录,同时保存用户现在浏览的网页
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute(Constants.CURRENTUSER);
        String url = (String) session.getAttribute(Constants.PRE_URL);
        if (user == null) {

            for (int i = 0; i < outofUrl.length; i++) {
                if (outofUrl[i].equals(pre_Url)) {
                    //说明是要排除的网站
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            //如果user是空的就说明当前还没有进行登陆的操作
            //正在浏览的网页进行保存
            if (url == null) {
                logger.info("用户还未登录,保存当前链接地址,跳转到登陆页面:" + pre_Url);
                session.setAttribute(Constants.PRE_URL, pre_Url);
            }
            ((HttpServletResponse) servletResponse).sendRedirect(redirectUrL);
        } else {
            //说明user不等于null
            if (url != null) {
                //说明有之前访问网页的记录.那么进行跳转
                session.setAttribute(Constants.PRE_URL, null);
                logger.info("用户登录成功,跳转到之前保存的链接界面:" + url);
                ((HttpServletResponse) servletResponse).sendRedirect(url);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
