import java.io.IOException;
import java.io.InputStream;

public class CharFrequncy {
	// 256 characters in ASCII
	private int theCounts[] = new int[Constant.NUM_Chars];
	private int validChar = 0;

	public CharFrequncy() {

	}

	/**
	 * Get the frequency count from input stream
	 * 
	 * @param input
	 *            InputStream
	 * @throws IOException
	 */
	public CharFrequncy(InputStream input) throws IOException {
		int ch;
		while ((ch = input.read()) != -1) {
			// System.out.println(ch&0xff);
			if (theCounts[ch & 0xff] == 0) {
				++validChar;
			}
			theCounts[ch & 0xff]++;

		}
	}

	/**
	 * get the frequency of the character
	 * 
	 * @param ch
	 *            index of character
	 * @return the count of the character
	 */
	public int getCount(int ch) {
		return theCounts[ch & 0xff];
	}

	/**
	 * set the frequency of character
	 * 
	 * @param ch
	 *            index of array
	 * @param count
	 *            character appears
	 */
	public void setCount(int ch, int count) {
		theCounts[ch & 0xff] = count;
	}

	public int getValidChar() {
		return validChar;
	}
}
