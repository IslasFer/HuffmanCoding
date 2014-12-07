import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class HuffmanTreeOutputStream {
	private ByteArrayOutputStream tempInputStream = new ByteArrayOutputStream();
	private DataOutputStream dout; // Data output to this stream
	private HuffmanTree codeTree;
	private OutputStream out;

	/**
	 * Constructor
	 * 
	 * @param in
	 *            The input stream
	 * @param out
	 *            The output stream
	 * @throws IOException
	 */
	public HuffmanTreeOutputStream(InputStream in, OutputStream out)
			throws IOException {
		codeTree = null;
		dout = null;
		this.out = out;
		int ch;
		while ((ch = in.read()) != -1) {
			tempInputStream.write(ch);
		}
		in.close();
	}

	/**
	 * Build the Huffman tree
	 * 
	 * @throws IOException
	 */
	public void buildTree() throws IOException {
		byte[] theInput = tempInputStream.toByteArray();
		ByteArrayInputStream byteIn = new ByteArrayInputStream(theInput);
		CharFrequncy countObj = new CharFrequncy(byteIn);

		byteIn.close();

		// Create a Huffman tree based on the frequency table
		this.codeTree = new HuffmanTree(countObj);
	}

	/**
	 * Write the compress file
	 * 
	 * @param force
	 *            Is force compress or not
	 * @return The number of bits have been written into the compressed file
	 * @throws IOException
	 */
	public int writeCompressedFile(boolean force) throws IOException {
		dout = new DataOutputStream(out);
		FileBitWriteUtility bout = new FileBitWriteUtility(dout);
		codeTree.writeHeader(dout);
		int bitsWritten = 0;
		int tableSizeEstimated = (codeTree.getValidChar() + 1) * 40;

		bitsWritten += tableSizeEstimated;

		byte[] theInput = tempInputStream.toByteArray();

		// write bits to bytes output stream
		int len = theInput.length;

		for (int i = 0; i < len; i++) {
			int value = theInput[i] & 0xff;
			int[] code = codeTree.getCode(value);
			bout.writeBits(code);
			bitsWritten += code.length;
		}

		int[] code = codeTree.getCode(Constant.EOF);
		bitsWritten += code.length;
		bout.writeBits(code);

		int sourceSize = theInput.length * 8;

		if (sourceSize > bitsWritten || force) {
			;
		} else {
			// pop up an alert
			JOptionPane
					.showMessageDialog(
							null,
							String.format(
									"Compression uses %d more bits\nuse force compression to compress",
									bitsWritten - sourceSize));

			// Close streams
			tempInputStream.close();
			bout.close();

			// The compressed file is larger than the original file. So there is
			// not need to compress. Throw an exception
			throw new IOException("No need to compress");
		}

		tempInputStream.close();
		bout.close();

		return bitsWritten;
	}
}
