package com.ss.lms.services;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;

public class BookServices {

	private static int bookId = 0;
	private static final String bookFileName = "C:\\Temp\\Book.txt";
	private static final String bookTempFile = "C:\\Temp\\BookTemp.txt";

	public boolean addBook() throws IOException {

		Scanner scan = new Scanner(System.in);

		String name, record;
		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		AuthorServices aService = new AuthorServices();
		PublisherServices pService = new PublisherServices();

		System.out.print("Enter the Book's Full Name \n");
		name = scan.nextLine();

		aService.readAllAuthors();
		System.out.println("Enter Author ID: \n");
		String authorId = scan.nextLine();
		pService.readAllPublishers();
		System.out.println("Enter publisher id: \n");
		String publisherId = scan.nextLine();

		try {
			Path file = Paths.get(bookFileName);
			if (Files.exists(file)) {
				bread = new BufferedReader(new FileReader(bookFileName));
				while ((record = bread.readLine()) != null) {
					String[] names = record.split("\\|");
					if (names != null && names.length > 0 && names[1].equalsIgnoreCase(name)) {
						System.out.println("Sorry !!! Book name exists already !");
						return false;
					}
					String str = names[0];
					if (str !=null && !"".equals(str)) {
						bookId = Integer.valueOf(str);
					}
				}

			}

			bwrite = new BufferedWriter(new FileWriter(bookFileName, true));
			bookId++;
			bwrite.write(bookId + "|" + name + "|" + authorId + "|" + publisherId);
			bwrite.flush();
			bwrite.newLine();
			bwrite.close();

			System.out.println("Book has been successfully added! \n");
			return true;

		} catch (IOException e) {
			System.out.println("Error occurred while adding bookname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				bwrite.close();
			}
			if (bread != null) {
				bread.close();
			}

		}
		return true;

	}

	public void deleteBook() throws IOException {

		Scanner scan = new Scanner(System.in);

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		File tempFile = new File(bookTempFile);
		File file = new File(bookFileName);

		try {
			readAllBooks();

			boolean found = false;

			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));
			String bookID = scan.nextLine();
			while ((record = bread.readLine()) != null) {
				if (!record.contains(bookID)) {

					bwrite.write(record);
					bwrite.flush();
					bwrite.newLine();
				} else {

					found = true;
				}
			}
			bread.close();
			bwrite.close();

			file.delete();
			File newFile = new File(bookFileName);
			if (found) {

				System.out.println("Book name has been successfully deleted! \n");
				tempFile.renameTo(newFile);
			} else {
				System.out.println("Book name does not exist! \n");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting bookname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				bwrite.close();
			}
			if (bread != null) {
				bread.close();
			}
		}

	}

	public void updateBook() throws IOException {
		Scanner scan = new Scanner(System.in);

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		BufferedReader bread2 = null;
		File tempFile = new File(bookTempFile);
		File file = new File(bookFileName);

		// System.out.print("Enter the Book's Full Name \n");
		// name = scan.nextLine();
		try {

			readAllBooks();
			boolean found = false;
			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));
			String bookID = scan.nextLine();
			while ((record = bread.readLine()) != null) {
				if (record.contains(bookID)) {
					System.out.println(record);
					found = true;
					break;
				}
			}
			bread.close();
			if (found) {
				System.out.println("Enter the new Name: ");
				String newName = scan.nextLine();
				bread2 = new BufferedReader(new FileReader(file));
				String newRecord;
				while ((newRecord = bread2.readLine()) != null) {
					if (newRecord.contains(bookID)) {
						bwrite.write(bookID + "," + newName);
						System.out.println("Changes have been made!");

					} else {
						bwrite.write(newRecord);
					}
					bwrite.flush();
					bwrite.newLine();
				}
				bread2.close();
				bwrite.close();
				file.delete();
				File newFile = new File(bookFileName);

				tempFile.renameTo(newFile);
			} else {
				System.out.println("error book name not found !!");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting bookname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				bwrite.close();
			}
			if (bread != null) {
				bread.close();
			}
			if (bread2 != null) {
				bread2.close();
			}
		}

	}

	public void readAllBooks() throws IOException {

		System.out.println(" ------------------------------------------------------------- ");
		System.out.println("| ID	Name 	AuthorID	PublisherID				 		  |");
		System.out.println(" ------------------------------------------------------------- ");
		InputStream is = new FileInputStream(bookFileName);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

			// Get Stream with lines from BufferedReader
			reader.lines().forEach(this::print);

		} catch (IOException ioException) {
			ioException.printStackTrace();

		}

	}

	private void print(String aLine) {

		System.out.println(aLine);
	}

	public void deleteBookWithAuthorID(String authorID) throws IOException {

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		File tempFile = new File(bookTempFile);
		File file = new File(bookFileName);

		try {
			readAllBooks();

			boolean found = false;

			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));

			while ((record = bread.readLine()) != null) {
				String[] names = record.split("\\|");
				if (names != null && names.length > 0 && !names[2].equalsIgnoreCase(authorID)) {

					bwrite.write(record);
					bwrite.flush();
					bwrite.newLine();
				} else {

					found = true;
				}
			}
			bread.close();
			bwrite.close();

			file.delete();
			File newFile = new File(bookFileName);
			if (found) {

				System.out.println("Book has been successfully deleted! \n");
				tempFile.renameTo(newFile);
			} else {
				System.out.println("Book name does not exist! \n");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting bookname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				bwrite.close();
			}
			if (bread != null) {
				bread.close();
			}
		}

	}

	public void deleteBookWithPublisherID(String publisherID) throws IOException {

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		File tempFile = new File(bookTempFile);
		File file = new File(bookFileName);

		try {
			readAllBooks();

			boolean found = false;

			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));

			while ((record = bread.readLine()) != null) {
				String[] names = record.split("\\|");
				if (names != null && names.length > 0 && !names[3].equalsIgnoreCase(publisherID)) {

					bwrite.write(record);
					bwrite.flush();
					bwrite.newLine();
				} else {

					found = true;
				}
			}
			bread.close();
			bwrite.close();

			file.delete();
			File newFile = new File(bookFileName);
			if (found) {

				System.out.println("Book has been successfully deleted! \n");
				tempFile.renameTo(newFile);
			} else {
				System.out.println("Book name does not exist! \n");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting bookname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				bwrite.close();
			}
			if (bread != null) {
				bread.close();
			}
		}

	}

}
