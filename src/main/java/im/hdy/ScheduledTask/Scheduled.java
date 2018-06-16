package im.hdy.ScheduledTask;

import im.hdy.model.Page;
import im.hdy.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定期执行事件
 */
@Component
public class Scheduled {

    @Autowired
    private PageService pageService;

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 1 ? * MON")
    //把数据提取放入到记忆录当中
    //每周一进行统计一次
    //每周一的凌晨一点自动执行下面的操作
    public void goto_memoirs() {
        List<Page> all = pageService.findAll();
        //得到所有的文章,进行遍历
        //效率比较低- -.是我现在能想到的方法先了.
        //毕竟一个星期只执行一次
        String topPageId = null;
        int bigSize = 0;
        for (int i = 0; i < all.size(); i++) {
            Page page = all.get(i);
            if (page.isInRecall()) {
                //判断是否已经放入回忆录当中
                //如果已经放进去了就跳过
                continue;
            }
            int size = page.getLikes().getUsers().size();
            //判断当前的size大小
            if (size > bigSize) {
                bigSize = size;
                topPageId = page.get_id();
            }
            //释放资源,防止内存泄露
            page = null;
        }
        if (topPageId != null && bigSize != 0) {
            //然后就标注文章为纪念,不再在其中间显示
            Page one = pageService.findOne(topPageId);
            one.setInRecall(true);
            pageService.update(one);
        }
    }


}
