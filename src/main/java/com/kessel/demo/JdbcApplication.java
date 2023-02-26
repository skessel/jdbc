package com.kessel.demo;

import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

  @Component
  class CommandLineRunnerImpl implements CommandLineRunner {
    
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    
    CommandLineRunnerImpl(PostRepository postRepository, AuthorRepository authorRepository) {
      this.postRepository = postRepository;
      this.authorRepository = authorRepository;
    }

    @Override
    @Transactional()
    public void run(String... args) throws Exception {
      Author unsavedAuthor = new Author(
          null, 
          "Sebastian", 
          "Kessel", 
          "sebastian@gmailc.com", 
          "skessel");
      Author savedAuthor = authorRepository.save(unsavedAuthor);
      
      AggregateReference<Author, Long> author = AggregateReference.to(savedAuthor.id());

      Post post = new Post("My First Post", "This is Sebastian'S first Post", author, new HashSet<>());
      post.addComment(new Comment("One", "This is a comment"));
      post.addComment(new Comment("Two", "This is another comment"));
      postRepository.save(post);
      
      Post reloadPost = postRepository.findById(post.getId()).orElseThrow();
      reloadPost.getAuthor();
      reloadPost.getComments();
      
      System.out.println(reloadPost);
    }
    
  }
  
  

}
