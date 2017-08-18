package com.fengwenyi.index.controller;

import com.fengwenyi.bean.ParserHtmlUrl;
import com.fengwenyi.bean.URLBean;
import com.fengwenyi.entity.URL;
import com.fengwenyi.entity.User;
import com.fengwenyi.repository.URLRepository;
import com.fengwenyi.repository.UserRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Name   : URLController
 * Desc   : 主要负责URL
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:38
 */
@RestController
@RequestMapping(value = "/url")
public class URLController {

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    // 添加
    @PostMapping(value = "/add")
    public String add(@RequestParam(value = "name") String name,
                      @RequestParam(value = "url") String uri,
                      @RequestParam(value = "introduce") String introduce,
                      @RequestParam(value = "nickname") String nickname,
                      @RequestParam(value = "phone") String phone) {
        long time = System.currentTimeMillis();
        URL url = new URL(name, uri, introduce);
        url.setTime(time);
        User user = new User(nickname, phone);
        Result result;
        // 首先 看url是否已经存在，如果存在则返回是吧，否则继续
        List<URL> urls = urlRepository.findAllByUrl(uri);
        if (urls.size() > 0) {
            result = new Result(ExceptionConstant.ERROR_URL_URI);
        } else {
            result = new Result(ExceptionConstant.SUCCESS);
            //查看user 是否存在
            List<User> users = userRepository.findAllByPhone(phone);
            if (users.size() > 0) //表示已存在
            {
                //保存URL即可
                url.setUser(users.get(0));
                urlRepository.save(url);
            } else //手机号还不存在
            {
                //先保存用户 再保存url
                user.setTime(time);
                url.setUser(userRepository.save(user));
                urlRepository.save(url);
            }
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * 所有的网址
     * @return
     */
    @GetMapping(value = "/all")
    public String all() {
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        List<URL> urls = urlRepository.findAll(sort);

        List<URLBean> urlBeans = new ArrayList<>();
        for (URL url: urls) {
            URLBean urlBean = new URLBean(url.getName(), url.getUrl(), url.getIntroduce(), url.getTime(), url.getUser().getNickname());
            urlBeans.add(urlBean);
        }

        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        result.setData(urlBeans);
        return gson.toJson(result);
    }

    //个人收藏
    @GetMapping(value = "/my/{phone}")
    public String myBookmarks(@PathVariable(value = "phone") String phone) {
        //通过手机号查找用户
        List<User> users = userRepository.findAllByPhone(phone);
        if (users.size() > 0) //查找到用户
        {
            User user = users.get(0);
            //通过用户查找url
            List<URL> urls = urlRepository.findAllByUser(user);
            List<URLBean> urlBeans = new ArrayList<>();
            for (URL url: urls) {
                URLBean urlBean = new URLBean(url.getName(), url.getUrl(), url.getIntroduce(), url.getTime(),
                        user.getNickname(), user.getPhone(), user.getTime());
                urlBeans.add(urlBean);
            }

            Result result = new Result(ExceptionConstant.SUCCESS);
            Gson gson = new Gson();
            result.setData(urlBeans);
            return gson.toJson(result);
        } else // 未查找到用户
        {
            Result result = new Result(ExceptionConstant.ERROR_USER_NO_PHONE);
            Gson gson = new Gson();
            return gson.toJson(result);
        }

    }

    @PostMapping(value = "/import")
    public String importBookMarks(@RequestParam(value = "bookmarks") MultipartFile file,
                                  @RequestParam(value = "nickname") String nickname,
                                  @RequestParam(value = "phone") String phone) {

        Result result;

        long time = System.currentTimeMillis();

        // 上传
        File in = uploadFile(file);

        // 解析
        List<ParserHtmlUrl> urls = parserHtml(in);

        // 入库
        if (urls.size() > 0) // 存在可导入的数据
        {
            //首先判断URL是否已经存在，如果存在就不入库，否则就入库
            for (ParserHtmlUrl urlObject: urls) {

                String uri = urlObject.getUrl();

                List<URL> urlsSql = urlRepository.findAllByUrl(uri);
                if (urlsSql.size() > 0) // 已经存在
                {

                } else //不存在
                    {
                        String name = urlObject.getName();
                        URL url = new URL(name, uri, name);
                        url.setTime(time);
                        //查看 user 是否存在
                        List<User> users = userRepository.findAllByPhone(phone);
                        if (users.size() > 0) //表示已存在
                        {
                            //保存URL即可
                            url.setUser(users.get(0));
                            urlRepository.save(url);

                        } else //手机号还不存在
                        {
                            //先保存用户 再保存url
                            User user = new User(nickname, phone);
                            user.setTime(time);
                            User userUrl = userRepository.save(user);

                            url.setUser(userUrl);
                            urlRepository.save(url);

                        }
                    }

            }

            result = new Result(ExceptionConstant.SUCCESS);

        } else // 没有可导入的数据
        {
            result = new Result(ExceptionConstant.ERROR_IMPORT_URL);
        }

        // 删除
        deleteFile(in);

        Gson gson = new Gson();

        return gson.toJson(result);

    }


    //文件上传
    public File uploadFile( MultipartFile file) {

        File in = null;

        try {

            String parentFile = "/fengwenyi/file/temp/";

            in = new File(parentFile + file.getOriginalFilename());

            File dest = in.getParentFile();

            if (!dest.exists()) //如果这个文件不存在
            {
                dest.mkdirs(); //创建
            }

            file.transferTo(in); // copy

        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    //解析
    public List<ParserHtmlUrl> parserHtml(File in) {

        List<ParserHtmlUrl> urls = new ArrayList<>();

        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            Elements elements = doc.getElementsByTag("A");

            for (Element element: elements) {
                String name = element.text();
                String uri = element.attr("href");
//                System.out.println(name + " { " + uri);
                ParserHtmlUrl url = new ParserHtmlUrl(name, uri);
                urls.add(url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    //文件删除
    public void deleteFile(File in) {
        boolean isFileDelete = in.delete(); // delete file

        if (!isFileDelete) //删除失败
        {
            System.out.println("删除失败");
        }
    }


    // 搜索，模糊匹配，  （？支持多关键字搜索[自定义sql语句] 怎么对页面关键字变红呢。。。。。。）
    @GetMapping(value = "/n_i_like/{keyword}")
    public String nameAndIntroduceLike(@PathVariable(value = "keyword") String keyword) {

        /*String [] splits = keyword.split(" ");

        String sql = "1 and ";
        int i = 0;
        for (String split : splits) {
            sql += "url.name like %:" + split + "% Or url.introduce like %:" + split + "%";

            if (i < (splits.length - 1)) {
                sql += " and ";
            }

            i++;
        }*/


        String key = "%" + keyword + "%";

        List<URL> urls = urlRepository.findAllByNameLikeOrIntroduceLike(key);

        Result result;

        if (urls.size() > 0) // 有数据
        {
            result = new Result(ExceptionConstant.SUCCESS);
            List<URLBean> urlBeans = new ArrayList<>();
            for (URL url : urls) {
                URLBean urlBean = new URLBean(url.getName(), url.getUrl(), url.getIntroduce(), url.getTime(),
                        url.getUser().getNickname());
                urlBeans.add(urlBean);
            }
            result.setData(urlBeans);
        } else // 没有数据
        {
            result = new Result(ExceptionConstant.ERROR_NO_DATA);
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    // 书签导出
    @GetMapping(value = "/export/{phone}")
    public ResponseEntity<InputStreamResource> exportBookmarks(@PathVariable(value = "phone") String phone) throws IOException {

        // 此处定义一些返回时可能涉及到的变量
        HttpHeaders headers = null;
        FileSystemResource file = null;

        // 查询
        List<User> users = userRepository.findAllByPhone(phone);
        if (users.size() > 0) {
            User user = users.get(0);
            List<URL> urls = urlRepository.findAllByUser(user);

            StringBuffer stringBuffer = new StringBuffer();

            long startTime = System.currentTimeMillis() / 1000;

            for (URL url : urls) {
                stringBuffer.append("<DT><A HREF=\"" + url.getUrl() + "\" ADD_DATE=\"" + (url.getTime() / 1000) + "\"> "+ url.getName() + "</A>\n");
            }

            long endTime = System.currentTimeMillis() / 1000;

            String text = "<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                    "\n" +
                    "<!-- WenyiFeng(xfsy2014@gmail.com) -->\n" +
                    "\n" +
                    "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                    "<TITLE>Bookmarks</TITLE>\n" +
                    "<H1>Bookmarks</H1>\n" +
                    "<DL><p>\n" +
                    "    <DT><H3 ADD_DATE=\"" + startTime + "\" LAST_MODIFIED=\"" + endTime + "\" PERSONAL_TOOLBAR_FOLDER=\"true\">书签栏</H3>\n" +
                    "    <DL><p>\n" +
                    stringBuffer +
                    "    </DL><p>\n" +
                    "</DL><p>";
            // 文件写入(HTML)
            String filePath = "/fengwenyi/file/temp/";
            // String fileName = "Bookmarks_" + System.currentTimeMillis() + ".html"; // 由于文件不能删除，所以直接覆盖掉，如果以后，可以解决这个问题的话，最好用标志命名
            String fileName = "Bookmarks" + ".html";

            try {
                fileWrite(text, filePath + fileName);

                // 文件下载

                file = new FileSystemResource(filePath + fileName);
                headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                // 文件删除
                //deleteFile(new File(filePath + fileName));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    // 文件写入
    public void fileWrite(String text, String fileInfo) throws IOException {
        File file = new File(fileInfo);
        if (!file.exists()) // 如果不存在
        {
            file.createNewFile(); // 创建一个新文件
        }
        FileOutputStream out = new FileOutputStream(file, false); // 文件； 是否采用追加的方式写入
        out.write(text.getBytes("UTF-8")); // 采用UTF-8编写写入文件
        out.close(); // 关闭输出流
    }
}
