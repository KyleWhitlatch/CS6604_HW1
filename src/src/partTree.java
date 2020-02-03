import javafx.scene.Group;

public class partTree {

    int numLayers;
    int globalX;
    int globalY;
    Group treeGroup;
    partNode root;

    public partTree(int numLayers, Group p1group, int globalX, int globalY){

        this.numLayers = numLayers;
        this.treeGroup = p1group;
        this.globalX = globalX;
        this.globalY = globalY;

    }
    public void addNode(int key, String name){

        int curLayer = 0;

        if(root == null){

            partNode root = new partNode(key, name, treeGroup, globalX/2,50);
            this.root = root;

        }else{

            partNode newNode = new partNode(key, name, treeGroup);
            partNode focusNode = root;
            partNode parent;

            while(true){

                parent = focusNode;

                if(key < focusNode.Key){

                    focusNode = focusNode.leftChild;

                    if(focusNode == null){

                        parent.leftChild = newNode;
                        parent.leftChild.setNodeXY(parent.nodeX - 100, parent.nodeY + 40);
                        return;


                    }

                } else{

                    focusNode = focusNode.rightChild;

                    if(focusNode == null){

                        parent.rightChild = newNode;
                        parent.rightChild.setNodeXY(parent.nodeX + 100, parent.nodeY + 40);
                        return;


                    }


                }


            }


        }


    }

    public void inOrderGetShapes(partNode focusNode, Group globalGroup){

        if(focusNode != null){
                globalGroup.getChildren().add(focusNode.nodeCircle);
                inOrderGetShapes(focusNode.leftChild, globalGroup);
                inOrderGetShapes(focusNode.rightChild, globalGroup);

        }

    }




}
