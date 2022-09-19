package com.edson.util;

import java.util.Iterator;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class NamedNodeMapIterableUtil implements Iterable<Node> {

    private final NamedNodeMap namedNodeMap;

    private NamedNodeMapIterableUtil(NamedNodeMap namedNodeMap) {
        this.namedNodeMap = namedNodeMap;
    }

    public static NamedNodeMapIterableUtil of(NamedNodeMap namedNodeMap) {
        return new NamedNodeMapIterableUtil(namedNodeMap);
    }

    private class NamedNodeMapIterator implements Iterator<Node> {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return (namedNodeMap.getLength() > nextIndex);
        }
        @Override
        public Node next() {
            Node item = namedNodeMap.item(nextIndex);
            nextIndex = nextIndex + 1;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<Node> iterator() {
        return new NamedNodeMapIterator();
    }
}