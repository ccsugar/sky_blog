package com.sky.service.Impl;

import com.sky.dao.BlogDao;
import com.sky.dao.CommentDao;
import com.sky.entity.Comment;
import com.sky.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 博客评论业务层接口实现类

 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查询出父节点
        List<Comment> comments = commentDao.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));
        for(Comment comment : comments){
            Long commentId = comment.getId();
            String firstLvNickname = comment.getNickname();
            List<Comment> childComments = commentDao.findByBlogIdParentIdNotNull(blogId,commentId);
//            查询出子评论
            combineChildren(blogId, childComments, firstLvNickname);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(Long blogId, List<Comment> childComments, String firstLvNickname) {
//        判断是否有一级子评论
        if(childComments.size() > 0){
//                循环找出子评论的id
            for(Comment secondLvComment : childComments){
                String secondLvNickname = secondLvComment.getNickname();
                secondLvComment.setParentNickname(firstLvNickname);
                tempReplys.add(secondLvComment);
                Long secondLvCommentId = secondLvComment.getId();
//                    查询出子二级评论
                recursively(blogId, secondLvCommentId, secondLvNickname);
            }

        }
    }

    private void recursively(Long blogId, Long commentId, String parentNickname) {
//        根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentDao.findByBlogIdAndReplayId(blogId,commentId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String replyNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname);
                Long replyId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replyId,replyNickname);
            }
        }
    }

//    新增评论
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int comments = commentDao.saveComment(comment);
//        文章评论计数
        blogDao.getCommentCountById(comment.getBlogId());
        return comments;
    }

//    删除评论
    @Override
    public void deleteComment(Comment comment,Long id) {
        commentDao.deleteComment(id);
        blogDao.getCommentCountById(comment.getBlogId());
    }
}