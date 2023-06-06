public class Vertex {
    int pathLength;
    int nodeCount;
    int[] nodes;
    int nodeName;
    Vertex(Vertex ver,int weight,int node)
    {
        pathLength = ver.pathLength + weight;
        nodeCount = ver.nodeCount + 1;
        nodes = new int[nodeCount];
        for (int i = 0; i < ver.nodes.length; i++)
            nodes[i] = ver.nodes[i];
        nodes[nodeCount-1] = node;
        nodeName = node;
    }
    Vertex(int node)
    {
        pathLength = 0;
        nodeCount = 1;
        nodes = new int[1];
        nodes[0] = node;
        nodeName = node;
    }
    String getPath()
    {
        String ans = "";
        for (int i = 0; i < nodes.length; i++) {
            ans += nodes[i] + " - ";
        }

        return ans;
    }
}
