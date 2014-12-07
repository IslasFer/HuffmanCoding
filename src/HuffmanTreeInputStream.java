import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HuffmanTreeInputStream {
	private FileBitReadUtility myFileBitReadUtility;// bit input stream
	private HuffmanTree codeTree;// HuffmanTree Tree

	/**
	 * @param in
	 *            The input stream
	 * @throws IOException
	 */
	public HuffmanTreeInputStream(InputStream in) throws IOException {
		DataInputStream myDataInputStream = new DataInputStream(in);
		myFileBitReadUtility = new FileBitReadUtility(in);

		// Create and construct the Huffman tree
		codeTree = new HuffmanTree();
		codeTree.readHeader(myDataInputStream);
	}

	/**
	 * Read one huffman code from the compressed file stream. Convert it to a
	 * byte, and write it to the uncompressed file
	 * 
	 * @return
	 * @throws IOException
	 */
	public int uncompressOneBype() throws IOException {
		String code = "";
		int bit;
		int decode = -1;

		while (true) {
			bit = myFileBitReadUtility.readBit();
			if (bit == -1) {
				throw new IOException("Unexpectied EOF!");
			}

			code = code + bit;
			decode = codeTree.getChar(code); // Get decode from the Huffman tree

			// Wrong code
			if (decode == HuffmanTree.INCOMPLETE_CODE) {
				continue;
			}
			// Error
			else if (decode == HuffmanTree.ERROR) {
				throw new IOException("Error!");
			}
			// End of file
			else if (decode == HuffmanTree.END) {
				return -1;
			}

			return decode;
		}
	}

	/**
	 * Close the input stream in the FileBitReadUtility
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		myFileBitReadUtility.close();
	}
}
