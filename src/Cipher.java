/*
 * @(#) Cipher.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This is a Cipher program that uses "rail fence" Cipher to encode and decode files.
 * It provides a way of encoding a message to disguise and store it,and it also contains
 * a method to decode it. When the user provides an input file, this application will 
 * provide the encoded/decoded file in an output file.
 * 
 * @author "your name"
 */
public class Cipher {

	/**
	 * Returns a scanner of file, the file is provided by user's input,
	 * and it contains text which will be processed by Cipher.
	 * 
	 * @param console The scanner of console, get the input of user.
	 * @return A scanner for an input file.
	 */
	public static Scanner getInputScanner(Scanner console){
		Scanner input = null;
		//prompts user to input a filename which need to be processed.
		//if there is no such file or directory, prompt user to input a correct filename.
		while (input == null) {	
			System.out.print("Enter input file: ");
			String filename = console.nextLine().trim();	
			inputFilename = filename;
			try {
				input = new Scanner(new File(filename));
			}
			catch (FileNotFoundException e) {
				System.out.println(filename + " (No such file or directory, please enter it again)");
			}
		}
		return input;
	}


	/**
	 * Gets a PrintStream of file, this file is used to store the output.
	 * 
	 * @param console The scanner of console, get the input of user.
	 * @return A PrintStream for an output file.
	 */
	public static PrintStream getOutputPrintStream(Scanner console){
		PrintStream output = null;		
		String filename = null;	
		//prompts user to input a path that store the decoding file.
		while (output == null) {
			System.out.print("Enter output file: ");
			filename = console.nextLine().trim();	
			outputFilename = filename;
			
			File inputFile = new File(inputFilename);
			File outputFile = new File(outputFilename);
			try {
				if (inputFile.getCanonicalPath().equals(outputFile.getCanonicalPath())) {
					System.out.println("Same file! Please enter another output file!");
					continue;
				}
			} catch (IOException exception) {
				System.out.println("Require filesystem queries");
				continue;
			}
			
			try {
				File file = new File(filename);		
				//if file is existed, then prompt user whether it is OK
				//to overwrite the file.
				if (file.exists()) {
					System.out.println("File exists!");
					System.out.print("Is it OK to overwrite the file? (Y/N): ");
					String confirm = console.nextLine().trim();
					if (confirm.equalsIgnoreCase("Y")) {
						System.out.println("The file: '" + filename + "' has been overwrited!");
					}
					//if answer is no, put another file.
					else if (confirm.equalsIgnoreCase("N")) {	
						System.out.println("Please enter another output file!");
						continue;
					}
					//invalid action
					else {		
						System.out.println("Invalid Action\n");
						continue;
					}
				}
				//if file is not existed, create it.
				else {
					file.createNewFile();	
				}
			}
			catch (IOException e) {	
				System.out.println("The system could not find the specified path!");
				continue;
			}
			
			try {
				output = new PrintStream(filename);
			}
			catch (FileNotFoundException e) {
				System.out.println(filename + " (No such file or directory)");
			}
		}
		return output;
	}

	/**
	 * Process the file through input and write the result to output according to the 
	 * value of "encode".
	 * @param encode 
	 * 			A boolean; if true, encodes lines in input and outputs encoded file;
	 * 			if false, decodes lines in input and outputs decoded file.
	 * @param input
	 * 			A scanner for an input file.
	 * @param output
	 * 			A PrintStream for an output file.
	 */
	public static void processFile (boolean encode, Scanner input, PrintStream output){
		if (input != null && output != null) {		
			while (input.hasNextLine()) {
				String line = input.nextLine();
				String resultLine = null;
				if (encode) {
					//if encode is true, encodes the line.
					resultLine = encodeLine(line);	
				}
				else {
					//if encode is false, decodes the line.
					resultLine = decodeLine(line);	
				}
				//output result.
				output.println(resultLine);	
			}
			File outputFile = new File(outputFilename);
			System.out.println("Success!\nThe output file is: '"
					+ outputFile.getAbsolutePath() + "'");
			
			input.close();
			output.close();	
		}
		else {
			System.out.println("input and output can't be null!");
		}
	}

	/**
	 * Encoding a line of plainText, it is staggered between rows.
	 * <p>For example,the message "we are discovered save yourself" will become
	 * "waeicvrdaeoreferdsoeesvyusl" after encoding.
	 * 
	 * @param line String need to encode.
	 * @return String containing encoded line.
	 */
	public static String encodeLine(String line){
		String encodedLine = "";
		if(line != null) {
			//get the half index of line
			int halfOfLine = line.length() / 2 + line.length() % 2;
			for(int index = 0; index < halfOfLine ; index ++) {
				encodedLine += line.charAt(2 * index);
			}
			for(int index = 0; index < line.length() - halfOfLine ; index ++) {
				encodedLine += line.charAt(2 * index + 1);
			}
		}
		return encodedLine;
	}

	/**
	 * Decoding a line of encoded text according to "rail fence."
	 * 
	 * @param line String need to decode.
	 * @return String containing decoded line.
	 */
	public static String decodeLine(String line){
		String decodedLine = null;
		if (line != null){
			decodedLine = "";
			//get the half index of line
			int halfOfLine = line.length() / 2 + line.length() % 2;
			for(int index = 0; index < halfOfLine ; index ++){
				//characters that located in odd number
				decodedLine += line.charAt(index);
				if (index + halfOfLine < line.length()) {
					//characters that located in even number
					decodedLine += line.charAt(index + halfOfLine);
				}
			}
		}
		return decodedLine;
	}
	
	/**
	 * Provides the user interface.
	 */
	public static void userInterface(){
		System.out.println("\nCipher program provides a way of encoding/decoding a message.\n"
				+ "\nPlease enter the action.");
		boolean flag = true;
		while (flag) {
			System.out.print("Enter E-ncode, D-ecode, or Q-uit: ");
			Scanner console = new Scanner(System.in);
			String action = console.nextLine().trim();
			//if user input "e", do encode.
			if (action.equalsIgnoreCase("e")) {
				Scanner input = getInputScanner(console);
				PrintStream output = getOutputPrintStream(console);
				processFile(true, input, output);
				System.out.println("");
			}
			//if user input "d", do decode.
			else if (action.equalsIgnoreCase("d")) {
				Scanner input = getInputScanner(console);
				PrintStream output = getOutputPrintStream(console);
				processFile(false, input, output);
				System.out.println("");
			}
			//if user input "q", exit the program.
			else if (action.equalsIgnoreCase("q")) {
				System.out.println("PROGRAM EXITS!");
				flag = false;
			}
			//prompts user invalid action.
			else {
				System.out.println("Invalid Action!\n");
			}
		}
	}
	
	/**
	 * Function main, call userInterface().
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args){
		userInterface();
	}
	
	//this two variables is used to determine whether the input file and output file are equal.
	private static String inputFilename;
	private static String outputFilename;
}
