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

public class AuthorServices {

	private static int authorId = 0;
	private static final String authorFileName = "C:\\Temp\\Author.txt";
	private static final String authorTempFile = "C:\\Temp\\AuthorTemp.txt";

	public boolean addAuthor() throws IOException {

		Scanner scan = new Scanner(System.in);

		String name;
		BufferedWriter bwrite = null;
		BufferedReader bread = null;

		System.out.print("Enter the Author's Full Name \n");
		name = scan.nextLine();
		String record;
		try {
			Path file = Paths.get(authorFileName);
			if (Files.exists(file)) {
				bread = new BufferedReader(new FileReader(authorFileName));
				while ((record = bread.readLine()) != null) {
					String[] names = record.split("\\|");
					if (names != null && names.length > 0 && names[1].equalsIgnoreCase(name)) {
						System.out.println("Sorry !!! Author name exists already !");
						return false;
					}
					String str = names[0];
					if (str !=null && !"".equals(str)) {
						authorId = Integer.valueOf(str);
					}
					
				}
				bread.close();

			}
			bwrite = new BufferedWriter(new FileWriter(authorFileName, true));
			authorId++;
			bwrite.write(authorId + "|" + name);
			bwrite.flush();
			bwrite.newLine();
			bwrite.close();

			System.out.println("Author has been successfully added! \n");
			return true;

		} catch (IOException e) {
			System.out.println("Error occurred while adding authorname: " + e.getMessage());
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

	public void deleteAuthor() throws IOException {

		Scanner scan = new Scanner(System.in);
		BookServices bservice = new BookServices();
		readAllAuthors();

		String authorID = scan.nextLine();

		deleteAuthor(authorID);
		bservice.deleteBookWithAuthorID(authorID);

	}

	public void updateAuthor() throws IOException {
		Scanner scan = new Scanner(System.in);

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		BufferedReader bread2 = null;
		File tempFile = new File(authorTempFile);
		File file = new File(authorFileName);

		// System.out.print("Enter the Author's Full Name \n");
		// name = scan.nextLine();
		try {
			readAllAuthors();

			boolean found = false;
			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));
			String authorID = scan.nextLine();
			while ((record = bread.readLine()) != null) {
				if (record.contains(authorID)) {
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
					if (newRecord.contains(authorID)) {
						bwrite.write(authorID + "," + newName);
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
				File newFile = new File(authorFileName);

				tempFile.renameTo(newFile);
			} else {
				System.out.println("error author name not found !!");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting authorname: " + e.getMessage());
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

	public void readAllAuthors() throws IOException {

		System.out.println(" ------------------------------------------------------------- ");
		System.out.println("| ID	Name 							 		  |");
		System.out.println(" ------------------------------------------------------------- ");
		InputStream is = new FileInputStream(authorFileName);
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

	public void deleteAuthor(String authorID) {

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		File tempFile = new File(authorTempFile);
		File file = new File(authorFileName);
		String record;
		boolean found = false;
		try {
			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));

			while ((record = bread.readLine()) != null) {
				if (!record.contains(authorID)) {
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
			File newFile = new File(authorFileName);
			if (found) {
				System.out.println("Author name has been successfully deleted! \n");
				tempFile.renameTo(newFile);
			} else {
				System.out.println("Author name does not exist! \n");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting authorname: " + e.getMessage());
		} finally {
			if (bwrite != null) {
				try {
					bwrite.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (bread != null) {
				try {
					bread.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

}
