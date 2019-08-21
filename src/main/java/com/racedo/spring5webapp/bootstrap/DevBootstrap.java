package com.racedo.spring5webapp.bootstrap;

import com.racedo.spring5webapp.model.Author;
import com.racedo.spring5webapp.model.Book;
import com.racedo.spring5webapp.model.Publisher;
import com.racedo.spring5webapp.repositories.AuthorRepository;
import com.racedo.spring5webapp.repositories.BookRepository;
import com.racedo.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private AuthorRepository authorRepository;
  private BookRepository bookRepository;
  private PublisherRepository publisherRepository;

  public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
    this.publisherRepository = publisherRepository;
  }

  private void initData() {
    Author eric = new Author("Eric", "Evans");
    Publisher harperCollins = new Publisher("Harper Collins", "address");
    Book ddd = new Book("Domain Driven Design", "1234", harperCollins);
    eric.getBooks().add(ddd);
    ddd.getAuthors().add(eric);

    Author rod = new Author("Rod", "Johnson");
    Publisher worx = new Publisher("Worx", "address");
    Book noEJB = new Book("J2EE Development without EJB", "23444", worx);
    rod.getBooks().add(noEJB);

    publisherRepository.saveAll(Arrays.asList(harperCollins, worx));
    authorRepository.saveAll(Arrays.asList(eric, rod));
    bookRepository.saveAll(Arrays.asList(ddd, noEJB));
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    initData();
  }
}
