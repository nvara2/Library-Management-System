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

public class PublisherServices {

	private static int publisherId = 0;
	private static final String publisherFileName = "C:\\Temp\\Publisher.txt";
	private static final String publisherTempFile = "C:\\Temp\\PublisherTemp.txt";

	public boolean addPublisher() throws IOException {

		Scanner scan = new Scanner(System.in);

		String name, address;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;

		System.out.print("Enter the Publisher's Full Name \n");
		name = scan.nextLine();
		System.out.print("Enter the Publisher's Address \n");
		address = scan.nextLine();
		String record;
		try {
			Path file = Paths.get(publisherFileName);
			if (Files.exists(file)) {
				bread = new BufferedReader(new FileReader(publisherFileName));
				while ((record = bread.readLine()) != null) {
					String[] names = record.split("\\|");
					if (names != null && names.length > 0 && names[1].equalsIgnoreCase(name)){
						System.out.println("Sorry !!! Publisher name exists already !");
						return false;
					}
					String str = names[0];
					if (str !=null && !"".equals(str)) {
						publisherId = Integer.valueOf(str);
					}
				}
				bread.close();

			}
			bwrite = new BufferedWriter(new FileWriter(publisherFileName, true));
			publisherId++;
			bwrite.write(publisherId + "|" + name + "|" + address);
			bwrite.flush();
			bwrite.newLine();
			bwrite.close();

			System.out.println("Publisher has been successfully added! \n");
			return true;

		} catch (IOException e) {
			System.out.println("Error occurred while adding publishername: " + e.getMessage());
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

	public void deletePublisher() throws IOException {

		Scanner scan = new Scanner(System.in);
		BookServices bservice = new BookServices();
		readAllPublishers();

		String publisherID = scan.nextLine();
		deletePublisher(publisherID);
		bservice.deleteBookWithPublisherID(publisherID);
	}

	public void updatePublisher() throws IOException {
		Scanner scan = new Scanner(System.in);

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		BufferedReader bread2 = null;
		File tempFile = new File(publisherTempFile);
		File file = new File(publisherFileName);

		// System.out.print("Enter the Publisher's Full Name \n");
		// name = scan.nextLine();
		try {
			readAllPublishers();

			boolean found = false;
			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));
			String publisherID = scan.nextLine();
			while ((record = bread.readLine()) != null) {
				if (record.contains(publisherID)) {
					System.out.println(record);
					found = true;
					break;
				}
			}
			bread.close();
			if (found) {
				System.out.println("Enter the new Name: ");
				String newName = scan.nextLine();
				System.out.println("Enter address to be added:");
				String newAdress = scan.nextLine();
				bread2 = new BufferedReader(new FileReader(file));
				String newRecord;
				while ((newRecord = bread2.readLine()) != null) {
					if (newRecord.contains(publisherID)) {
						bwrite.write(publisherID + "|" + newName + "|" + newAdress);
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
				File newFile = new File(publisherFileName);

				tempFile.renameTo(newFile);
			} else {
				System.out.println("error publisher name not found !!");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting publishername: " + e.getMessage());
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

	public void readAllPublishers() throws IOException {

		System.out.println(" ------------------------------------------------------------- ");
		System.out.println("| ID	Name 	Address						 		  |");
		System.out.println(" ------------------------------------------------------------- ");
		InputStream is = new FileInputStream(publisherFileName);
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

	public void deletePublisher(String publisherID) throws IOException {

		String record;

		BufferedWriter bwrite = null;
		BufferedReader bread = null;
		File tempFile = new File(publisherTempFile);
		File file = new File(publisherFileName);

		try {

			boolean found = false;
			bread = new BufferedReader(new FileReader(file));
			bwrite = new BufferedWriter(new FileWriter(tempFile));

			while ((record = bread.readLine()) != null) {
				if (!record.contains(publisherID)) {
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
			File newFile = new File(publisherFileName);
			if (found) {
				System.out.println("Publisher name has been successfully deleted! \n");
				tempFile.renameTo(newFile);
			} else {
				System.out.println("Error Publisher name is not existing please try again! \n");
			}

		} catch (IOException e) {
			System.out.println("Error occurred while deleting publishername: " + e.getMessage());
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
