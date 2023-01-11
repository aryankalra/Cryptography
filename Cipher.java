import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Cipher {
    
	private static String studentNumberAsString = ""; 
    private static final String programName = "Cipher";	            // Name of the program
    private static SecretKeySpec secretKey;                         // Secret key generated to use with the cipher
    private static BufferedInputStream inputFileStream = null;      // A buffered input stream to read from
    private static BufferedOutputStream outputFileStream = null;    // A buffered output stream to write to
    private static byte[] bytesOutputFromMethod = null;             // The resulting bytes from the method that was called

   
    public static void main(String args[]){

        // First, check the user has provided all the required arguments, 
        // and if they haven't, tell them something is wrong and then exit
        if(args.length != 4) {
            printUsageMessage(); 
            System.exit(1);
        }

        // Open the input file
        try {
            inputFileStream = new BufferedInputStream(new FileInputStream(args[2]));
        } catch (FileNotFoundException e) {
            printErrorMessage("Unable to open input file: " + args[2], null);
            System.exit(1);
        }

        try {
            outputFileStream = new BufferedOutputStream(new FileOutputStream(args[3]));
        } catch (FileNotFoundException e) {
            printErrorMessage("Unable to open output file: " + args[3], null);
            System.exit(1);
        }

        studentNumberAsString = args[0];

        // feature needs to be run - encrypt, decrypt, hash
        if(args[1].equals("encrypt")){
            doBlockCipherEncryptOnInputFile();
        }else if(args[1].equals("decrypt")){
            doBlockCipherDecryptOnInputFile();
        }else if(args[1].equals("hash")){
            doFileHashOnInputFile();
        }else{
            System.out.println("Please use one of the following words (written in lower-case) for the feature: encrypt, decrypt, hash");
            System.exit(1);
        }

        try {
            outputFileStream.write(bytesOutputFromMethod);
        } catch (IOException e) {
            printErrorMessage("output error with the file IO", e);
            System.exit(1);
        }

        try {
            outputFileStream.close();
            inputFileStream.close();
        } catch (IOException e) {
            printErrorMessage("close error with the file IO", e);
            System.exit(1);
        }

    } 

    private static void doBlockCipherEncryptOnInputFile(){
        Cipher blockCipher = null;

        // Creating a AES cipher in the desired mode, with padding
        try {
            // Retrieving block cipher instance with properties: AES ECB with padding

            blockCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        } catch (NoSuchAlgorithmException e) {
            printErrorMessage("No Such Algorithm Exception when creating AES cipher", e);
            System.exit(2);
        } catch (NoSuchPaddingException e) {
            printErrorMessage("No Such Algorithm Exception when creating AES cipher", e);
            System.exit(2);
        }

        // Getting the block key ready using the number entered

        try {
            // Initialise the block cipher to prepare it to be used for encryption using the secret key

            secretKey = getSecretKey(studentNumberAsString);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e1) {
            printErrorMessage("Algortihm or encoding for key generation not found", e1);
            System.exit(2);
        }

        // Setting up the 16 byte key for the block cipher 
        try {
            // Initialise the block cipher using the secret key to be used in encryption mode 

            blockCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
        } catch (InvalidKeyException e) {
            printErrorMessage("Invalid Secret Key", e);
            System.exit(2);
        }

        // Processing the text from the inFile
        try {
            // Processing all the bytes from the inputFileStream through the blockCipher
            // byte [] AllBytes = new byte[blockCipher.getBlockSize()]; 
            // AllBytes = inputFileStream.readAllBytes();

            byte [] AllBytes = inputFileStream.readAllBytes();

            // Storing the result into the variable bytesOutputFromMethod so that it can be used later.

            bytesOutputFromMethod = blockCipher.doFinal(AllBytes);

        } catch (IllegalBlockSizeException e) {
            printErrorMessage("Encryption, error with the block sizing", e);
            System.exit(2);
        } catch (BadPaddingException e) {
            printErrorMessage("Encryption, error with the padding applied", e);
            System.exit(2);
        } catch (IOException e) {
            printErrorMessage("Encryption, error with the file IO", e);
            System.exit(2);
        }

        System.out.println("Result from encryption is:");
        System.out.println(byteArrayInHex(bytesOutputFromMethod));

    }

    // Writing the decryption algorithm    

    private static void doBlockCipherDecryptOnInputFile(){
    
    }

    private static void doFileHashOnInputFile(){
        // Hashing function and running the hash functionality over the two specified files and submitting the digest  
    }

	/**
	 * Print an error message on stderr, optionally picking up additional detail from
	 * a passed exception
	 * @param errMsg
	 * @param e
	 */
	private static void printErrorMessage(String errMsg, Exception e) {
		System.err.println(errMsg);
		if (e != null) 
			System.err.println(e.getMessage());
	}

    /**
     * 
     * @param secretString
     * @return AES secret key generated from the secretString passed in.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(String secretString) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
        return new SecretKeySpec(shaDigest.digest(secretString.getBytes("UTF-8")),"AES");
    }

	/**
	 * Print a usage message if something hasn't been entered corectly.
	 */
	private static void printUsageMessage() {
		System.out.println("==========================");
        System.out.println(programName + "Revision: 1.0 - Usage in terminal:\njava " + programName + ".java <shared-secret> <feature> <infile> <outfile>");
        System.out.println("==========================");
        System.out.println("<feature> should be one of 3 words depending on what you want to execute. [encrypt decrypt hash]");
        System.out.println("     encrypt - this feature will take an input file and run the encryption method using the shared secret key");
        System.out.println("     decrypt - this feature will take an input file and run the decryption method using the shared secret key");
        System.out.println("     hash - this feature will take an input file and run a hash");
        System.out.println("<infile> should be the full name of the file you want to read in ... e.g. a1_file_to_encrypt.txt");
        System.out.println("<outfile> should be the name of the file you want to output");
        System.out.println("==========================");

	}

	final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    /**
     * A helper function to write byte values as a hex text string
     * @param a the raw bytes from any processing
     * @return a string that represents what the bytes are (but as hex-printed values)
     */
    private static String byteArrayInHex(byte[] a) {
		StringBuffer sb = new StringBuffer();
		sb.append("0x");
		for(int i = 0; i < a.length; i++) {
			sb.append(String.format("%02x", a[i]));
		}
		return sb.toString();
	}

} 
