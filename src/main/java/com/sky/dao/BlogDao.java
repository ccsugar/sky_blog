package com.sky.dao;

import com.sky.entity.Blog;
import com.sky.queryvo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 博客持久层接口
 */
@Mapper
@Repository
public interface BlogDao {

    ShowBlog getBlogById(Long id);

    List<Blog> getAllBlog();

    List<BlogQuery> getAllBlogQuery();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    List<BlogQuery> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    List<FirstPageBlog> getFirstPageBlog();

    List<RecommendBlog> getAllRecommendBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    int updateViews(Long id);

//    根据博客id查询出评论数量
    int getCommentCountById(Long id);

    List<FirstPageBlog> getByTypeId(Long typeId);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();
}