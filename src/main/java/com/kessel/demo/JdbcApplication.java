package com.kessel.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import com.kessel.demo.jdbc.Author;
import com.kessel.demo.jdbc.AuthorRepository;
import com.kessel.demo.jdbc.Comment;
import com.kessel.demo.jdbc.Post;
import com.kessel.demo.jdbc.PostRepository;

@SpringBootApplication
public class JdbcApplication {

  public static void main(String[] args) {
    SpringApplication.run(JdbcApplication.class, args);
  }

  @Bean
  CommandLineRunner run(PostRepository postRepository, AuthorRepository authorRepository) {
    return args -> {
      
      Author unsavedAuthor = new Author(
          null, 
          "Sebastian", 
          "Kessel", 
          "sebastian@gmailc.com", 
          "skessel");
      Author savedAuthor = authorRepository.save(unsavedAuthor);
      
      AggregateReference<Author, Long> author = AggregateReference.to(savedAuthor.id());

      Post post = new Post("My First Post", "This is Sebastian'S first Post", author);
      post.addComment(new Comment("One", "This is a comment"));
      post.addComment(new Comment("Two", "This is another comment"));
      postRepository.save(post);
    };
  }

}
