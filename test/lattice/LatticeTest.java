package lattice;

import dgraph.DAGraph;
import org.junit.Test;
import dgraph.Node;
import dgraph.DGraph;
import java.util.TreeSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jean-François
 */
public class LatticeTest {
    /**
     * Test empty constructor.
     */
    @Test
    public void testLatticeEmpty() {
        Lattice l = new Lattice();
        assertFalse(l.getNodes() == null);
        assertFalse(l.getEdges() == null);
    }
    /**
     * Test constructor from a TreeSet.
     */
    @Test
    public void testLatticeTS() {
        TreeSet ts = new TreeSet();
        ts.add(new Node("a"));
        Lattice l = new Lattice(ts);
        assertEquals(l.getNodes(), ts);
    }
    /**
     * Test constructor from a DAG.
     */
    @Test
    public void testLatticeDAG() {
        DAGraph dag = new DAGraph();
        dag.addNode(new Node("a"));
        Lattice l = new Lattice(dag);
        assertEquals(l.getNodes(), dag.getNodes());
    }
    /**
     * Test the ArrowRelation method.
     */
    @Test
    public void testArrowRelation() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        Node e = new Node("e"); l.addNode(e);
        Node f = new Node("f"); l.addNode(f);
        Node g = new Node("g"); l.addNode(g);
        Node h = new Node("h"); l.addNode(h);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(b, d);
        l.addEdge(c, e);
        l.addEdge(d, f);
        l.addEdge(e, g);
        l.addEdge(f, g);
        l.addEdge(g, h);
        DGraph ar = l.arrowRelation();
        assertEquals((String) ar.getEdge(g, b).getContent(), "Cross");
        assertEquals((String) ar.getEdge(f, c).getContent(), "UpDown");
        assertEquals((String) ar.getEdge(f, e).getContent(), "Up");
        assertEquals((String) ar.getEdge(d, c).getContent(), "Down");
        assertEquals((String) ar.getEdge(a, h).getContent(), "Circ");
    }
    /**
     * Test bottom method.
     */
    @Test
    public void testbottom() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        l.addEdge(a, b);
        assertEquals(l.bottom(), a);
    }
    /**
     * test getCanonicalDirectBasis.
     */
    @Test
    public void testgetCanonicalDirectBasis() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        l.addEdge(a, b);
        l.addEdge(b, c);
        ImplicationalSystem is = l.getCanonicalDirectBasis();
        Rule r = new Rule();
        r.addToPremise("c");
        r.addToConclusion("b");
        assertTrue(is.containsRule(r));
    }
    /**
     * Test getDependencyGraph method.
     */
    @Test
    public void testgetDependencyGraph() {
        DGraph dg = new DGraph();
        Node a = new Node("a"); dg.addNode(a);
        Node b = new Node("b"); dg.addNode(b);
        dg.addEdge(a, b);
        Lattice l = new Lattice();
        l.setDependencyGraph(dg);
        assertEquals(l.getDependencyGraph(), dg);
    }
    /**
     * Test getIS method.
     */
    @Test
    public void testgetIS() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        l.addEdge(a, b);
        l.addEdge(b, c);
        ImplicationalSystem is = l.getIS();
        is.makeDirect();
        Rule r = new Rule();
        r.addToPremise("c");
        r.addToConclusion("b");
        assertTrue(is.containsRule(r));
    }
    /**
     * Test getMinimalGenerators method.
     */
    @Test
    public void testgetMinimalGenerators() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        l.addEdge(a, b);
        l.addEdge(b, c);
        TreeSet ts = l.getMinimalGenerators();
        ComparableSet prem = new ComparableSet();
        prem.add("c");
        assertTrue(ts.contains(prem));
    }
    /**
     * test getTable method.
     */
    @Test
    public void testgetTable() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        Context ctx = l.getTable();
        assertTrue(ctx.containsAttribute(b));
        assertTrue(ctx.containsAttribute(d));
        assertTrue(ctx.containsObservation(b));
        assertTrue(ctx.containsObservation(d));
    }
    /**
     * Test hasDependencyGraph method.
     */
    @Test
    public void testhasDependencyGraph() {
        DGraph dg = new DGraph();
        Node a = new Node("a"); dg.addNode(a);
        Node b = new Node("b"); dg.addNode(b);
        dg.addEdge(a, b);
        Lattice l = new Lattice();
        l.setDependencyGraph(dg);
        assertTrue(l.hasDependencyGraph());
    }
    /**
     * test irreductibleClosure.
     */
    @Test
    public void testirreducibleClosure() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        ConceptLattice cl = l.irreducibleClosure();
        assertEquals(cl.getNodes().size(), 4);
        assertEquals(cl.getEdges().size(), 4);
    }
    /**
     * Test irreductiblesSubgraph.
     */
    @Test
    public void testirreduciblesSubgraph() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        DAGraph dag = l.irreduciblesSubgraph();
        assertTrue(dag.containsNode(b));
        assertTrue(dag.containsNode(d));
    }
    /**
     * Test isLattice.
     */
    @Test
    public void testisLattice() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        assertTrue(l.isLattice());
        Node e = new Node("e"); l.addNode(e);
        l.addEdge(e, b);
        l.addEdge(e, d);
        assertFalse(l.isLattice());
    }
    /**
     * Test join method.
     */
    @Test
    public void testjoin() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        assertEquals(l.join(b, d), c);
    }
    /**
     * Test joinClosure method.
     */
    @Test
    public void testjoinClosure() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        ConceptLattice cl = l.joinClosure();
        assertEquals(cl.getNodes().size(), 4);
        assertEquals(cl.getEdges().size(), 4);
    }
    /**
     * test joinIrreductibles method.
     */
    @Test
    public void testjoinIrreduciblesNode() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        TreeSet<Comparable> j = l.joinIrreducibles(c);
        assertTrue(j.contains(b));
        assertTrue(j.contains(d));
    }
    /**
     * Test joinIrreductibles.
     */
    @Test
    public void testjoinIrreducibles() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        TreeSet<Node> j = l.joinIrreducibles();
        assertTrue(j.contains(b));
        assertTrue(j.contains(d));
    }
    /**
     * test joinIrreductiblesSubgraph.
     */
    @Test
    public void testjoinIrreduciblesSubgraph() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        DAGraph dag = l.joinIrreduciblesSubgraph();
        assertTrue(dag.containsNode(b));
        assertTrue(dag.containsNode(d));
    }
    /**
     * Test meet method.
     */
    @Test
    public void testmeet() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        assertEquals(l.meet(b, d), a);
    }
    /**
     * test meetClosure method.
     */
    @Test
    public void testmeetClosure() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        ConceptLattice cl = l.meetClosure();
        assertEquals(cl.getNodes().size(), 4);
        assertEquals(cl.getEdges().size(), 4);
    }
    /**
     * Test meetIrreductibles method.
     */
    @Test
    public void testmeetIrreduciblesNode() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        TreeSet<Comparable> m = l.meetIrreducibles(a);
        assertTrue(m.contains(b));
        assertTrue(m.contains(d));
    }
    /**
     * Test meetIrreductibles.
     */
    @Test
    public void testmeetIrreducibles() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        TreeSet<Node> m = l.meetIrreducibles();
        assertTrue(m.contains(b));
        assertTrue(m.contains(d));
    }
    /**
     * test meetIrreductiblesSubgraph.
     */
    @Test
    public void testmeetIrreduciblesSubgraph() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(a, d);
        l.addEdge(d, c);
        DAGraph dag = l.meetIrreduciblesSubgraph();
        assertTrue(dag.containsNode(b));
        assertTrue(dag.containsNode(d));
    }
    /**
     * Test setDependencyGraph method.
     */
    @Test
    public void testsetDependencyGraph() {
        DGraph dg = new DGraph();
        Node a = new Node("a"); dg.addNode(a);
        Node b = new Node("b"); dg.addNode(b);
        dg.addEdge(a, b);
        Lattice l = new Lattice();
        l.setDependencyGraph(dg);
        assertTrue(l.hasDependencyGraph());
    }
    /**
     * Test top method.
     */
    @Test
    public void testtop() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        l.addEdge(a, b);
        assertEquals(l.top(), b);
    }
}
