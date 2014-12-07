import java.io.IOException;
import java.io.OutputStream;

public class FileBitWriteUtility {
	private OutputStream out;
	private int buffer;
	private int bufferPos;// bit remaining in the byte buffer

	/**
	 * @param os
	 */
	public FileBitWriteUtility(OutputStream os) {
		bufferPos = 0;
		buffer = 0;
		out = os;
	}

	/**
	 * @param val
	 *            write bit to byte
	 * @throws IOException
	 */
	public void writeBits(int[] val) throws IOException {
		int len = val.length;
		for (int i = 0; i < len; i++) {
			writeBit(val[i]);
		}
	}

	/**
	 * write byte to bits.
	 * 
	 * @param val
	 *            current bit
	 * @throws IOException
	 */
	public void writeBit(int val) throws IOException {
		buffer = setBit(buffer, bufferPos++, val);// convert bit to byte
		// flush when finished one byte
		if (bufferPos == Constant.BITS_PER_BYTES)
			flush();
	}

	/**
	 * flush method
	 * 
	 * @throws IOException
	 */
	public void flush() throws IOException {
		if (bufferPos == 0)// if no data
			return;
		// write to out
		out.write(buffer);
		// reset buffer
		bufferPos = 0;
		buffer = 0;
	}

	/**
	 * close
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		flush();
		out.close();
	}

	/**
	 * change the byte by bit
	 * 
	 * @param pack
	 * @param pos
	 * @param val
	 *            current bit
	 * @return
	 */
	private int setBit(int pack, int pos, int val) {
		if (val == 1)
			pack |= (val << pos);
		return pack;
	}
}
