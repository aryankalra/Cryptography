public class Powers {

	public static void main(String[] args) {
		int base = 10;
		for (int x = 0; x < 6; x++) {
			System.out.println(base + "^" + x + " = " + (int) Math.pow(base, x));
		}
	}

}
