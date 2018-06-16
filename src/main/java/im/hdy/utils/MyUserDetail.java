package im.hdy.utils;

import im.hdy.model.Manager;
import im.hdy.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class MyUserDetail implements UserDetailsService {

    @Autowired
    private ManagerService ManagerService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<Manager> list = ManagerService.findByUserName(s);
        if (list == null || list.size() == 0) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return list.get(0);
    }
}
