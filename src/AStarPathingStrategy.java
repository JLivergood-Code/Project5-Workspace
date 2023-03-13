import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

//g is distance gone
//h is distance left
//f is g+h


class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
        HashMap<Point, Node> openSet = new HashMap<>();
        HashSet<Point> closed = new HashSet<>();


        Node root = new Node(start, null, 0, Point.distanceSquared(start, end));
        open.add(root);
        openSet.put(start, root);


        while(open.size() > 0)
        {
            //grabs the first node on the list
            Node curNode = open.remove();
            openSet.remove(curNode.pos);


            List<Point> potential = potentialNeighbors.apply(curNode.pos).filter(canPassThrough).toList();



            for(Point neighbor : potential) {
                if(neighbor.equals(end))
                {
                    path = pathBuilder(curNode, path);
                    return path;
                }
                if(!closed.contains(neighbor) && withinReach.test(curNode.pos, neighbor))
                {
                    Node neighborNode = new Node(neighbor, curNode, curNode.g+1, Point.manhattanDistance(neighbor, end));

                    //if current node is in the openSet, compare the g value inside of
                    if(openSet.containsKey(neighbor))
                    {
                        if( openSet.get(neighbor).g >= neighborNode.g && openSet.get(neighbor).f >= neighborNode.f)
                        {
                            open.remove(neighborNode);
                            open.add(neighborNode);
                            openSet.remove(neighbor);
                            openSet.put(neighbor, neighborNode);
                        }
//                        System.out.println("Better g value for the " + neighborNode.pos + ": old is: " + openSet.get(neighborNode) + " new is: " + neighborNode.g);
                    }
                    else {
                        open.add(neighborNode);
                        openSet.put(neighbor, neighborNode);
                    }

                }
            }

            closed.add(curNode.pos); //adds to the closed list.
        }

        return path;
    }

    private List<Point> pathBuilder(Node curNode, List<Point> path)
    {
        if(curNode.prior == null)
        {
            return path;
        }
        path.add(0, curNode.pos);
        return pathBuilder(curNode.prior, path);
    }

    private class Node
    {
        public Point pos;
        public Node prior;
        public double f;
        public double g;
        public double h;

        Node( Point pos, Node prior, double g, double h)
        {
            this.pos = pos;
            this.prior = prior;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        double getF()
        {
            return f;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj != null && obj instanceof Node)
            {
                Node n = (Node) obj;
                return this.pos.equals(n.pos);
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            int result = 17;
            result = result * 31 + pos.hashCode();
            return result;
        }
    }
}
