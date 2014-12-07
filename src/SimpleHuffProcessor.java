import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleHuffProcessor implements IHuffProcessor {
	private HuffViewer myViewer;

	public int compress(InputStream in, OutputStream out, boolean force)
			throws IOException {
		int bitsWritten = 0;

		HuffmanTreeOutputStream hzout = new HuffmanTreeOutputStream(in, out);
		hzout.buildTree();
		bitsWritten = hzout.writeCompressedFile(force);

		return bitsWritten;
	}

	public int preprocessCompress(InputStream in) throws IOException {
		FileBitReadUtility myBitInputStream = new FileBitReadUtility(in);

		int size = 0;
		while (true) {
			int bit = myBitInputStream.readBit();
			if (bit == -1) {
				break;
			}
			size++;
		}

		return size;
	}

	public void setViewer(HuffViewer viewer) {
		myViewer = viewer;
	}

	public int uncompress(InputStream in, OutputStream out) throws IOException {
		int bitsWritten = 0;
		HuffmanTreeInputStream hIn = new HuffmanTreeInputStream(in);
		int ch;

		// Uncompress and write the file
		while ((ch = hIn.uncompressOneBype()) != -1) {
			out.write(ch);
			bitsWritten += 8;
		}

		// Close input Stream
		hIn.close();

		// Close output stream
		out.close();

		return bitsWritten;
	}

	private void showString(String s) {
		myViewer.update(s);
	}

}
