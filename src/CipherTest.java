/*
 * @(#)CipherTest.java
 */

/**
 * A test program with a suite of white box test cases.
 * 
 * @author Junyu Chen
 */
public class CipherTest {

  public static void main(String[] args) {
  
	//Encoding test
    String encode = Cipher.encodeLine("Pop Quiz Today!");
    System.out.println("\nExpected: PpQi oa!o uzTdy \n  Actual: " + encode);
    
    //Add 5 more test cases here for encodeLine method
    encode = Cipher.encodeLine("Hello, my teacher!");
    System.out.println("\nExpected: Hlo ytahrel,m ece! \n  Actual: " + encode);
    
    encode = Cipher.encodeLine("~!@#$%^&*()");
    System.out.println("\nExpected: ~@$^*)!#%&( \n  Actual: " + encode);
    
    encode = Cipher.encodeLine("	,./");
    System.out.println("\nExpected: 	.,/ \n  Actual: " + encode);
    
    encode = Cipher.encodeLine("123456789");
    System.out.println("\nExpected: 135792468 \n  Actual: " + encode);
    
    encode = Cipher.encodeLine("I love football.");
    System.out.println("\nExpected: Ilv otal oefobl. \n  Actual: " + encode);
    
    
    //Decoding test
    String decode = Cipher.decodeLine("PpQi oa!o uzTdy");
    System.out.println("\nExpected: Pop Quiz Today! \n  Actual: " + decode);
    
    //Add 5 more test cases here for decodeLine method
    decode = Cipher.decodeLine("^#QwT UG&,)57][im &");
    System.out.println("\nExpected: ^)#5Q7w]T[ iUmG &&, \n  Actual: " + decode);
    
    decode = Cipher.decodeLine("UX%?	you are= ");
    System.out.println("\nExpected: UuX %a?r	ey=o  \n  Actual: " + decode);
    
    decode = Cipher.decodeLine("WERT 09 ()%% $KU\\");
    System.out.println("\nExpected: W)E%R%T  $0K9U \\( \n  Actual: " + decode);
    
    decode = Cipher.decodeLine("\nt %% &OP");
    System.out.println("\nExpected: \n t& 0%p% \n  Actual: " + decode);
    
    decode = Cipher.decodeLine("1+1  = 2 ???");
    System.out.println("\nExpected: 1 +21  ? ?=? \n  Actual: " + decode);

  }
}
