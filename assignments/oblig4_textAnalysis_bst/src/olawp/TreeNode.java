package olawp;


public class TreeNode implements Comparable<TreeNode> {
    // Setter data til å være final så jeg slipper intelliJ warnings, denne verdien skal endres etter innsettings uansett.
    private final String data;
    private int count;
    TreeNode left;
    TreeNode right;

    public TreeNode(String value) {
        data = value;
        count = 1;
        left = null;
        right = null;
    }

    @Override
    public int compareTo(TreeNode node) {
        int compareString = data.compareTo(node.getData());
        if (compareString != 0) {
            return compareString;
        } else {
            return Integer.compare(this.getCount(), node.getCount());
        }
    }

    public String getData() {
        return data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setLeft(TreeNode value) {
        left = value;
    }

    public void setRight(TreeNode value) {
        right = value;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }


}
