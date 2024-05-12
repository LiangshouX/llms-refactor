public class NextPermutation {
    public static ArrayList<Integer> nextPermutation(ArrayList<Integer> perm) {
        for (int i = perm.size() - 2; i >= 0; i--) {
            if (perm.get(i) < perm.get(i + 1)) {
                for (int j = perm.size() - 1; j != i; j--) {
                    if (perm.get(j) < perm.get(i)) {
                        ArrayList<Integer> nextPerm = new ArrayList<>(perm);
                        int tempJ = perm.get(j);
                        int tempI = perm.get(i);
                        nextPerm.set(i, tempJ);
                        nextPerm.set(j, tempI);

                        ArrayList<Integer> reversed = new ArrayList<>();
                        for (int k = nextPerm.size() - 1; k != i; k--) {
                            reversed.add(nextPerm.get(k));
                        }

                        int q = i + 1;
                        for (Integer replace : reversed) {
                            nextPerm.set(q, replace);
                            q++;
                        }

                        return nextPerm;
                    }
                }
            }
        }

        return new ArrayList<>();
    }
}