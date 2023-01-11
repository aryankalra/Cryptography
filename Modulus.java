public class Modulus {

	public static void main(String[] args) {

		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (char first : letters.toCharArray()) {
			for (char second : letters.toCharArray()) {
				for (char third : letters.toCharArray()) {
					String word = String.valueOf(first) + String.valueOf(second) + String.valueOf(third);

					if (word.hashCode() == "pre".hashCode())
						System.out.println(word);
				}
			}
		}

	}

}
