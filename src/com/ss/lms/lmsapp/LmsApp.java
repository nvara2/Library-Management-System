package com.ss.lms.lmsapp;


import java.io.IOException;
import java.util.Scanner;
import com.ss.lms.services.AuthorServices;
import com.ss.lms.services.BookServices;
import com.ss.lms.services.PublisherServices;




public class LmsApp {
 private Scanner scan = new Scanner(System.in);
  AuthorServices asvc = new AuthorServices();
  PublisherServices psvc = new PublisherServices();
  BookServices bsvc = new BookServices();

  public void authorServices() throws IOException {

    boolean validChoice = false;

    while (!validChoice) {
      System.out.println("Please select an option to move forward!");
      System.out.println(
          " 1. Add Author\n 2. Delete Author\n 3. Update Author\n 4. Read All Authors\n 5. Return to Previous Menu");

      System.out.println("Enter option number");

      int asc = scan.nextInt();

      switch (asc) {
        case 1:
          System.out.println("You would like to add a author, so let's get started!");
          asvc.addAuthor();
          break;
        case 2:
          System.out.println("You would like to delete a author, so let's get started!");
          asvc.deleteAuthor();
          break;
        case 3:
          System.out.println("You would like to update a author, so let's get started!");
          asvc.updateAuthor();
          break;
        case 4:
          System.out.println("One moment please!");
          asvc.readAllAuthors();
          break;
        case 5:
          System.out.println("One moment please!");
          runApp();
          break;
        default:
          System.out.println("You picked a option that does not exist! Please try again!");
      }

    }
  }


  public void publisherServices() throws IOException {


    boolean validChoice = false;

    while (!validChoice) {
      System.out.println("Please select an option to move forward!");
      System.out.println(
          " 1. Add Publisher\n 2. Delete Publisher\n 3. Update Publisher\n 4. Read All Publishers\n 5. Return to Previous Menu");

      System.out.println("Enter option number");

      int psc = scan.nextInt();

      switch (psc) {
        case 1:
          System.out.println("You would like to add a publisher, so let's get started!");
          psvc.addPublisher();
          break;
        case 2:
          System.out.println("You would like to delete a publisher, so let's get started!");
          psvc.deletePublisher();
          break;
        case 3:
          System.out.println("You would like to update a publisher, so let's get started!");
          psvc.updatePublisher();
          break;
        case 4:
          System.out.println("One moment please!");
          psvc.readAllPublishers();
          break;
        case 5:
          System.out.println("One moment please!");
          runApp();
          break;
        default:
          System.out.println("You picked a option that does not exist! Please try again!");
      }

    }
  }

  public void bookServices() throws IOException {

    boolean validChoice = false;

    while (!validChoice) {
      System.out.println("Please select an option to move forward!");
      System.out.println(
          " 1. Add Book\n 2. Delete Book\n 3. Update Book\n 4. Read All Books\n 5. Return to Previous Menu");

      System.out.println("Enter option number");

      int bsc = scan.nextInt();

      switch (bsc) {
        case 1:
          System.out.println("You would like to add a book, so let's get started!");
          bsvc.addBook();
          break;
        case 2:
          System.out.println("You would like to delete a book, so let's get started!");
          bsvc.deleteBook();
          break;
        case 3:
          System.out.println("You would like to update a book, so let's get started!");
          bsvc.updateBook();
          break;
        case 4:
          System.out.println("One moment please!");
          bsvc.readAllBooks();
          break;
        case 5:
          System.out.println("One moment please!");
          runApp();
          break;
        default:
          System.out.println("You picked a option that does not exist! Please try again!");
      }

    }
  }



  public void runApp() throws IOException {

    boolean validChoice = false;

    while (!validChoice) {
      System.out.println("Please select an option below to get started!");
      System.out
          .println(" 1. Author Services\n 2. Publisher Services\n 3. Book Services\n 0. Exit\n");

      System.out.println("Enter option number");

      int option = scan.nextInt();

      switch (option) {
        case 1:
          System.out.println("Welcome to Author Services, let's get started!");
          authorServices();
          // validChoice = true;
          break;
        case 2:
          System.out.println("Welcome to Publisher Services, let's get started!");
           publisherServices();
          // validChoice = true;
          break;
        case 3:
          System.out.println("Welcome to Book Services, let's get started!");
          bookServices();
          // validChoice = true;
          break;
        case 0:
          System.out.println("Thank you for using the SmoothStack Library Management System!");
          validChoice = true;
          break;
        default:
          System.out.println("You picked a option that does not exist! Please try again!");
      }
    }

  }

  public static void main(String[] args) {

    System.out.println("Welcome to the SmoothStack Library Management System!");
    LmsApp app = new LmsApp();
    try {
      app.runApp();
    } catch (IOException e) {

      e.printStackTrace();
    }
    //finally {
    //	ConsoleUtils.Close();
    }
  }
 

