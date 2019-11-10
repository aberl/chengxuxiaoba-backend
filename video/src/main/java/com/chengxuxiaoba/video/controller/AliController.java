package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.service.imp.ali.MediaService;
import com.chengxuxiaoba.video.util.StringUtil;
import com.sun.net.httpserver.HttpExchange;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

@RestController
public class AliController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/hls/decrypt")
    public void hslDescrypt(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ciphertext = request.getParameter("Ciphertext");

            if (StringUtil.isNullOrEmpty(ciphertext)) {
                return;
            }

            //从KMS中解密出来，并Base64 decode
            byte[] key = mediaService.decrypt(ciphertext);

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(HttpStatus.OK.value());
            response.setContentLengthLong(key.length);

            response.getOutputStream().write(key);
            response.getOutputStream().close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
