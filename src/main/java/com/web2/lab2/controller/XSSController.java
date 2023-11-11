package com.web2.lab2.controller;

import com.web2.lab2.model.Comment;
import com.web2.lab2.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@RestController
public class XSSController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/api/comments")
    public Iterable<Comment> getAllComments() {
        //return commentRepository.findAll();
        Comment comment1 = new Comment();
        comment1.setUsername("Slavko Haker");
        comment1.setContent("<img src=x onerror=\"javascript:alert('XSS-Slavko_Haker_Comment')\">");

        Comment comment2 = new Comment();
        comment2.setUsername("Ime Prezime");
        comment2.setContent("Dobar dan!");

        Iterable<Comment> iter = Arrays.asList(comment1, comment2);

        return iter;
    }

    @PostMapping("/api/addComment")
    public RedirectView addComment(@RequestParam("username") String username, @RequestParam("content") String content) {
        Comment newComment = new Comment();
        newComment.setUsername(username);
        newComment.setContent(content);
        System.out.println(content);
        commentRepository.save(newComment);
        return new RedirectView("/");
    }
}
