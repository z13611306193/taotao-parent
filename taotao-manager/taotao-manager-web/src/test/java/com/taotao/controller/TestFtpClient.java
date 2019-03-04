package com.taotao.controller;

import com.taotao.common.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.*;

/**
*  测试ftp客户端
* <p>Title:TestFtpClient</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/4 15:28
* @version 1.0
*/
public class TestFtpClient {

    /**
     * 测试FTP服务
     */
    @Test
    public void test() throws IOException {
        //创建FTPClient对象
        FTPClient ftpClient = new FTPClient();
        //创建FTP链接
        ftpClient.connect("172.16.184.132",21);
        //使用用户名和密码登录
        ftpClient.login("ftpuser","ftpuser");
        //上传文件
        //指定上传位置
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
        //创建上传文件流
        InputStream inputStream = new FileInputStream(new File("F:/123.jpg"));
        //修改上传文件的格式,默认为文本,不修改图片就会失真
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //文件名/文件流
        ftpClient.storeFile("123.jpg",inputStream);
        //关闭连接
        ftpClient.logout();
    }

    @Test
    public void testFtpUtil() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("F:/123.jpg"));
        FtpUtil.uploadFile("172.16.184.132",21,"ftpuser","ftpuser",
                            "/home/ftpuser/www/images","/2019/03/04","hello.jpg",
                            inputStream);
    }

}
