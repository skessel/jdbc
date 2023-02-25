package com.kessel.demo.jdbc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Post {

  @Id
  @JsonIgnore
  private Long id;
  private String title;
  private String content;
  private LocalDateTime publishedOn;
  private LocalDateTime updatedOn;
  private final Set<Comment> comments = new HashSet<>();
  private AggregateReference<Author, Long> author;

  public Post(String title, String content, AggregateReference<Author, Long> author) {
    this.title = title;
    this.content = content;
    this.author = author;
    this.publishedOn = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getPublishedOn() {
    return publishedOn;
  }

  public void setPublishedOn(LocalDateTime publishedOn) {
    this.publishedOn = publishedOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }

  public void addComments(List<Comment> comments) {
    comments.forEach(this::addComment);
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.post = this;
  }

  public Set<Comment> getComments() {
    return comments;
  }

  public AggregateReference<Author, Long> getAuthor() {
    return author;
  }

  public void setAuthor(AggregateReference<Author, Long> author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }

}
