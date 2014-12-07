public class HuffNode implements Comparable<HuffNode> {
	public int value;// Node value
	public int weight;// weight
	HuffNode left, right;// left/right child node
	HuffNode parent;// parent node

	HuffNode(int v, int w, HuffNode lchild, HuffNode rchild, HuffNode pt) {
		value = v;
		weight = w;
		left = lchild;
		right = rchild;
		parent = pt;
	}

	/**
	 * compare the weight
	 */
	public int compareTo(HuffNode rhs) {
		return weight - rhs.weight;
	}
}
