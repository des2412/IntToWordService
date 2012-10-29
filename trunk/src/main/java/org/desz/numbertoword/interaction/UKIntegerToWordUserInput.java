package org.desz.numbertoword.interaction;

/**
 * User Input class for UKIntegerToWord
 */
import java.util.Scanner;

import org.desz.numbertoword.factory.NumberToWordFactory;

public class UKIntegerToWordUserInput {
	public static void main(String args[]) throws Exception {
		Scanner scn = new Scanner(System.in);

		boolean quit = false;
		do {
			System.out
					.println("This program will convert an Integer [0-999999999] to UK English word representation");
			System.out.println();
			System.out
					.println("Type y to enter an Integer or x to quit the application): ");
			String userinput = scn.next();
			char choice = userinput.toLowerCase().charAt(0);
			switch (choice) {
			case 'y':
				System.out
						.println("Enter Integer to convert to UK English Word and press Return key:");
				String input = scn.next();
				Integer num = null;
				try {
					num = Integer.valueOf(input);
					System.out.println("Word conversion of "
							+ num
							+ " = "
							+ NumberToWordFactory.UK_MAPPER
									.getNumberToWordMapper().getWord(num));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println();
				break;
			case 'x':
				quit = true;
				scn.close();
				System.exit(0);
				break;

			default:
				System.out.println("Error! Entry must be 'y or 'x''");
				break;
			}
		} while (!quit);

	}

}
