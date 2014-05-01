package dgraph;

/*
 * DGraphWriterDot.java
 *
 * Copyright: 2013 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2014
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * This class defines the way for writing a graph as a dot file.
 *
 * ![DGraphWriterDot](DGraphWriterDot.png)
 *
 * @uml DGraphWriterDot.png
 * !include src/dgraph/DGraphWriterDot.iuml
 * !include src/dgraph/DGraphWriter.iuml
 *
 * hide members
 * show DGraphWriterDot members
 * class DGraphWriterDot #LightCyan
 * title DGraphWriterDot UML graph
 */
public final class DGraphWriterDot implements DGraphWriter {
    /**
     * The singleton instance.
     */
    private static DGraphWriterDot instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static DGraphWriterDot getInstance() {
        if (instance == null) {
            instance = new DGraphWriterDot();
        }
        return instance;
    }

    /**
     * Register this class for writing .dot files.
     */
    public static void register() {
        DGraphWriterFactory.register(DGraphWriterDot.getInstance(), "dot");
    }

    /**
     * Write a graph to a output stream.
     *
     * @param   graph  a graph to write
     * @param   out    an output stream
     *
     * @throws  IOException  When an IOException occurs
     */
    public void write(DGraph graph, DataOutputStream out) throws IOException {
        out.writeBytes("digraph G {\n");
        out.writeBytes("Graph [rankdir=BT]\n");
        StringBuffer nodes  = new StringBuffer();
        StringBuffer edges = new StringBuffer();
        for (Node node : graph.getNodes()) {
            String dot = node.getIdentifier() + " [label=\"";
            StringTokenizer tokenizer = new StringTokenizer(node.toString(), "\"");
            while (tokenizer.hasMoreTokens()) {
                dot += tokenizer.nextToken();
            }
            dot += "\"]";
            nodes.append(dot).append("\n");
        }
        for (Edge edge : graph.getEdges()) {
            String dot = edge.getFrom().getIdentifier() + "->" + edge.getTo().getIdentifier();
            if (edge.hasContent()) {
                dot = dot + " [" + "label=\"";
                StringTokenizer tokenizer = new StringTokenizer(edge.getContent().toString(), "\"");
                while (tokenizer.hasMoreTokens()) {
                    dot += tokenizer.nextToken();
                }
                dot = dot + "\"]";
            }
            edges.append(dot).append("\n");
        }
        out.writeBytes(nodes.toString());
        out.writeBytes(edges.toString());
        out.writeBytes("}");
    }
}

