package olawp;


public class BinaryTree {
    private TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public String formatStrings(String data) {
        data = data.toUpperCase();
        // Fjerner symboler som komma og punktum.
        data = data.replaceFirst("[^A-Z]*$", "");
        return data;

    }

    public void insert(String data) {
        String formatedData = formatStrings(data);

        if (formatedData.equals("")) {
            return;
        }

        if (root == null) {
            root = new TreeNode(formatedData);
            return;
        }

        TreeNode current = root;
        TreeNode newNode = new TreeNode(formatedData);

        while (current != null) {
            if (formatedData.equals(current.getData())) {
                current.increment();
                return;
            } else if (newNode.compareTo(current) < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(newNode);
                    return;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(newNode);
                    return;
                } else {
                    current = current.getRight();
                }
            }
        }

    }

    public void print() {
        System.out.println("\nForekomster  Ord");
        System.out.println("----------- -------------------");
        inOrder(root);
    }

    private void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }


        // Formatering av printen så det ikke blir "ujevnheter" i printen
        String formatCount = String.format("%5d", node.getCount());
        inOrder(node.getLeft());
        System.out.println(formatCount + "        " + node.getData());
        inOrder(node.getRight());
    }

}
