import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

// Folositi clasa aceasta pentru o citire mai rapida
// sursa: http://codeforces.com/blog/entry/7018
/**
 * Given class for faster reading from file.
 */
public class MyScanner {
	BufferedReader br;
	StringTokenizer st;

	public MyScanner(String file) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(file));
	}

	String next() {
		while (st == null || !st.hasMoreElements()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(next());
	}

	long nextLong() {
		return Long.parseLong(next());
	}

	double nextDouble() {
		return Double.parseDouble(next());
	}

	String nextLine() {
		String str = "";
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	void close() throws IOException {
		br.close();
	}
}