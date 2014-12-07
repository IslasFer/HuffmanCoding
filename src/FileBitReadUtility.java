import java.io.IOException;
import java.io.InputStream;

public class FileBitReadUtility {
	private InputStream in;
	private int buffer;// byte buffer
	private int bufferPos;// bit pos in one byte.

	/**
	 * 
	 * @param is
	 *            InputStream
	 */
	public FileBitReadUtility(InputStream is) {
		in = is;
		bufferPos = Constant.BITS_PER_BYTES;// initial remaining space
	}

	/**
	 * read one bit
	 * 
	 * @return 1 or 0
	 * @throws IOException
	 */
	public int readBit() throws IOException {
		// if one byte is exhusted, get another byte.
		if (bufferPos == Constant.BITS_PER_BYTES) {

			buffer = in.read();
			// end of file
			if (buffer == -1)
				return -1;
			// reset pos
			bufferPos = 0;
		}

		return getBit(buffer, bufferPos++);
	}

	/**
	 * close input stream
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		in.close();
	}

	/**
	 * get the bit per byte
	 * 
	 * @param pack
	 * @param pos
	 * @return
	 */
	private static int getBit(int pack, int pos) {
		return (pack & (1 << pos)) != 0 ? 1 : 0;
	}
}
