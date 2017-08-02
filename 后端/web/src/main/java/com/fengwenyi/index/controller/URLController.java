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
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    //
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
}
