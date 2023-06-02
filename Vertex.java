public class Vertex {
    int pathLenght;
    int nodeCount;
    int[] nodes;
    int nodeName;
    Vertex(Vertex ver,int weight,int node)
    {
        pathLenght = ver.pathLenght + weight;
        nodeCount = ver.nodeCount + 1;
        nodes = new int[nodeCount];
        for (int i = 0; i < ver.nodes.length; i++)
            nodes[i] = ver.nodes[i];
        nodes[nodeCount-1] = node;
        nodeName = node;
    }
    Vertex(int node)
    {
        pathLenght = 0;
        nodeCount = 1;
        nodes = new int[1];
        nodes[0] = node;
        nodeName = node;
    }
}
