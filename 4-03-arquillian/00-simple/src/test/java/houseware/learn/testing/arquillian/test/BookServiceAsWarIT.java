package houseware.learn.testing.arquillian.test;

import houseware.learn.testing.arquillian.books.Book;
import houseware.learn.testing.arquillian.books.BookEJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class BookServiceAsWarIT {
    @Inject
    private BookEJB bookEJB;


    @Deployment
    public static WebArchive createArchiveAndDeploy() {
        WebArchive s = ShrinkWrap.create(WebArchive.class)
                .addClasses(BookEJB.class, Book.class)
                .addAsWebInfResource("persistence-test-war.xml", "persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        File[] files =  Maven.resolver().loadPomFromFile("pom.xml").resolve("org.hibernate:hibernate-entitymanager").withTransitivity().asFile();

        s.addAsLibraries(files);
        System.out.println(s.toString(true));
        return s;
    }


    @Test
    public void testFetchBooks() {
        assertTrue(bookEJB.findAllBooks().isEmpty());
        Book b1 = new Book();
        b1.setAuthor("tim");
        b1.setTitle("A fascinating book");
        Book b2 = new Book();
        b2.setAuthor("tom");
        b2.setTitle("I R Coder");
        Book b3 = new Book();
        b3.setAuthor("maria");
        b3.setTitle("Some book");
        Book b4 = new Book();
        b4.setAuthor("tim");
        b4.setTitle("Another fascinating book");
        bookEJB.saveBook(b1);
        bookEJB.saveBook(b2);
        bookEJB.saveBook(b3);
        bookEJB.saveBook(b4);
        assertEquals(4, bookEJB.findAllBooks().size());
        bookEJB.deleteBook(b4);
        assertEquals(3, bookEJB.findAllBooks().size());
    }
}
