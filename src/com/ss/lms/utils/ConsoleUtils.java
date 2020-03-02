package com.ss.lms.utils;

import java.util.Scanner;

public final class ConsoleUtils {

	private static final Scanner scan = new Scanner(System.in);

	public static String getString() {
		return scan.nextLine();
	}
	
	public static int getInt() {
		return scan.nextInt();
	}

	public static void Close() {
		scan.close();
	}
	
}
