package com.radix2.algorithms.week1;

public class UFWithFindLargest extends WeightedQuickUnionUF {
    private int[] max;

    public UFWithFindLargest(int size) {
        super(size);

        max = new int[size];

        for (int i = 0; i < size; i++) {
            max[i] = i;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);

        if (pRoot == qRoot)
            return;

        int[] size = getSize();
        int[] ids = getIds();

        if (size[pRoot] < size[qRoot]) {
            ids[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
            if (max[qRoot] < max[pRoot]) {
                max[qRoot] = max[pRoot];
            }
        } else {
            ids[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
            if (max[pRoot] < max[qRoot]) {
                max[pRoot] = max[qRoot];
            }
        }
    }

    public int find(int p) {
        return max[root(p)];
    }
}