import java.math.BigInteger;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.Math;

public class Puzzle {
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static int getTarget(String id) {

		int r = -1;
		try {
			BigInteger h2 = BigDecimal.valueOf(Math.pow(2, 256) / 40).toBigInteger();
			String s = id;
			Boolean found = false;
			while (!found) {
				r = r + 1;
				String str = s + Integer.toString(r);
				String h1 = bytesToHex(getSHA(str));
				BigInteger h3 = new BigInteger(h1, 16);
				if (h3.compareTo(h2) == -1) {
					found = true;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception thrown for incorrect algorithm: " + e);
		}
		return r;
	}

	public static void main(String args[]) {
		String id = "1111";
		int target = getTarget(id);
		System.out.println("Attempts: " + Integer.toString(target));
		int runs = 1000;
		double average = 0;
		int studentint = Integer.parseInt(id);
		for (int i = 0; i < runs; i++) {
			String student = Integer.toString(studentint + i);
			target = getTarget(student);
			average = average + target;
		}
		average = average / runs;
		System.out.println("Average: " + average);
	}
}