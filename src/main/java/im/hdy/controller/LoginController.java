package im.hdy.controller;

import im.hdy.constant.Constants;
import im.hdy.impl.AdminInterface;
import im.hdy.model.Admin;
import im.hdy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "adminlogin")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminInterface adminInterface;

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, String repeatPassword, HttpSession session) {
        logger.info("获取到的旧密码:" + oldPassword);
        logger.info("获取到的新密码:" + newPassword);
        logger.info("获取到的重复密码:" + repeatPassword);
        if (newPassword == null || newPassword.length() == 0) {
            return "redirect:/";
        }
//        User u = (User) session.getAttribute(Constants.CURRENTUSER);
        List<Admin> all = adminInterface.findAll();
        if (all != null && all.size() > 0) {
            Admin admin = all.get(0);
            String password = admin.getPassword();
            if (password.equals(oldPassword) && newPassword.equals(repeatPassword)) {
                admin.setPassword(newPassword);
                adminInterface.save(admin);
            }
        } else {
            Admin admin = new Admin();
            if (oldPassword.equals("hzkj") && newPassword.equals(repeatPassword)) {
                admin.setUsername("admin");
                admin.setAdminUser(null);
                admin.setPassword(newPassword);
                adminInterface.save(admin);
            }
        }
        return "redirect:/";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session) {
        logger.info("获取到的用户名:" + username);
        logger.info("获取到的用户名:" + password);
        List<Admin> all = adminInterface.findAll();
        User user = (User) session.getAttribute(Constants.CURRENTUSER);
        if (all == null || all.size() == 0) {
            //说明当前还没有进行过登录操作
            Admin admin = new Admin();
            admin.setAdminUser(user);
            admin.setUsername("admin");
            admin.setPassword("hzkj");
            Admin save = adminInterface.save(admin);
            session.setAttribute(Constants.ISADMIN, "true");
        } else {
            //说明已经进行过登录操作,替换管理员身份
            Admin admin = all.get(0);
            if (admin.getPassword().equals(password) && admin.getUsername().equals(username)) {
                //判断密码账号是否相同
                if (user != null) {
                    admin.setAdminUser(user);
                    adminInterface.save(admin);
                    session.setAttribute(Constants.ISADMIN, "true");
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        logger.info("接收到请求:");
        return "admin/login";
    }
}
