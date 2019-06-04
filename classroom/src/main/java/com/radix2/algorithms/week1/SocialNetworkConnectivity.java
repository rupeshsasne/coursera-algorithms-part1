package com.radix2.algorithms.week1;

/**
 * Social network connectivity.
 *
 * Given a social network containing n members and a log file containing m timestamps at which times pairs of members
 * formed friendships, design an algorithm to determine the earliest time at which all members are connected
 * (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by
 * timestamp and that friendship is an equivalence relation. The running time of your algorithm should be mlog(n) or
 * better and use extra space proportional to n.
 */
public class SocialNetworkConnectivity {
    private WeightedQuickUnionWithPathCompressionUF uf;

    private int connectedComponents;

    private long timestampWhereAllPersonsAreConnected = -1;

    public SocialNetworkConnectivity(int size) {
        uf = new WeightedQuickUnionWithPathCompressionUF(size);
        connectedComponents = size;
    }

    public void makeFriend(int p, int q, long timestamp) {
        if (!uf.connected(p, q)) {
            connectedComponents--;

            if (connectedComponents == 1) {
                timestampWhereAllPersonsAreConnected = timestamp;
            }

            uf.union(p, q);
        }
    }

    public boolean allConnected() {
        return connectedComponents == 1;
    }

    public long getTimestampWhereAllPersonsAreConnected() {
        return timestampWhereAllPersonsAreConnected;
    }
}
