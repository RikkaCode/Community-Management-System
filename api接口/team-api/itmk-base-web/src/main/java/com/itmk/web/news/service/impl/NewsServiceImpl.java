package com.itmk.web.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.news.entity.News;
import com.itmk.web.news.mapper.NewsMapper;
import com.itmk.web.news.service.NewsService;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
