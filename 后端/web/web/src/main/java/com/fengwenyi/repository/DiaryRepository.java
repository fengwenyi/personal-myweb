package com.fengwenyi.repository;

import com.fengwenyi.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fengwenyi on 2017/7/4.
 */
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

}
