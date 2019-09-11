
public interface BinaryTreeInterface<T> extends TreeInterFace<T> {
	public void setTree(T rootData);
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
	                                BinaryTreeInterface<T> rightTree);
}
