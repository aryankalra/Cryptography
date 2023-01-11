import java.math.BigInteger;

public class Euclidean {

	public static void main(String[] args) {
		BigInteger a = new BigInteger("1111");
		BigInteger b = new BigInteger("2222");
		BigInteger g = gcd(a, b, false);
		System.out.println("GCD of " + a + " and " + b + " is " + g);

	}

	public static BigInteger gcd(BigInteger a, BigInteger b, boolean print) {
		BigInteger q, r;
		do {
			q = a.divide(b);
			r = a.remainder(b);
			if (print)
				System.out.println(a + "= " + b + ',' + q + " + " + r);
			a = b;
			b = r;
		} while (r.compareTo(BigInteger.ONE) >= 1);
		return a;

	}

}
