package im.hdy.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping(value = "pic")
@Controller
public class ImgController {

    @RequestMapping(method = RequestMethod.POST)
    public void upload(HttpServletRequest request,
                       HttpServletResponse response, MultipartFile file) {
        //获取到文件上传的地址
        StringBuilder sb = new StringBuilder()
                .append(request.getSession().getServletContext()
                        .getRealPath("/")).append("upload");

        //通过uuid生成图片名称
        String uuid_fileName = UUID.randomUUID().toString();

        File saveFile = new File(sb.toString(), uuid_fileName + ".jpg");

        try {
            //保存压缩图
            Thumbnails.of(file.getInputStream()).scale(1f)
                    .outputQuality(0.5f)
                    .toFile(saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
