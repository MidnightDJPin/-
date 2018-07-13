package Æ±Îñ¹ÜÀí;

import java.util.Scanner;
import java.util.regex.*;

public class TestMain {

	public static void main(String[] args) {
		String numPatten = "\\d{0,1}";
		Scanner input = new Scanner(System.in);
		String testString = input.nextLine();
		System.out.println(Pattern.matches(numPatten, testString));
		input.close();
	}

}
