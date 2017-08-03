package com.fengwenyi.admin.controller;

import com.fengwenyi.bean.Paging;
import com.fengwenyi.entity.Diary;
import com.fengwenyi.repository.DiaryRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fengwenyi on 2017/7/4.
 */
@RestController
@RequestMapping(value = "/admin/diary")
public class AdminDiaryController {

    @Autowired
    public DiaryRepository diaryRepository;

    /**
     * 查询所有日记
     * @return
     */
    @GetMapping(value = "/all")
    public String all() {
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        List<Diary> diaries = diaryRepository.findAll();
        result.setData(diaries);
        return gson.toJson(result);
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Test";
    }

    /**
     * 查询某一篇日记
     * @param id
     * @return
     */
    @GetMapping(value = "/one/{id}")
    public String findOne(@PathVariable(value = "id") Integer id) {
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        Diary diary = diaryRepository.findOne(id);
        result.setData(diary);
        return gson.toJson(result);
    }

    /**
     * 添加日记
     * @param diary
     * @return
     */
    @PostMapping(value = "/add")
    public String add(Diary diary) {
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        long time = System.currentTimeMillis();
        diary.setTime(time);
        result.setData(diaryRepository.save(diary));
        return gson.toJson(result);
    }

    /**
     * 更改日记
     * @param id
     * @param place
     * @param content
     * @return
     */
    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable(value = "id") Integer id,
                        @RequestParam(value = "place", required = false) String place,
                        @RequestParam(value = "content", required = false) String content) {
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        Diary diary = new Diary();
        long time = System.currentTimeMillis();
        diary.setId(id);
        diary.setTime(time);
        diary.setPlace(place);
        diary.setContent(content);
        result.setData(diary);
        return gson.toJson(result);
    }

    /**
     * 删除一篇日记
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        diaryRepository.delete(id);
    }

    /**
     * 分页查询日记
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/paging", method=RequestMethod.GET)
    public String findAllByPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "7") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Diary> diaries = diaryRepository.findAll(pageable);
        List<Diary> content = diaries.getContent();
        Integer totalPages = diaries.getTotalPages();
        Paging paging = new Paging(page, content, totalPages);
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        result.setData(paging);
        return gson.toJson(result);
    }
}
