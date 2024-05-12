public class CoinChange {
    public static int coinChangePossibilities(final int[] coins, final int total) {
        if (total == 0) {
            return 1;
        }
        if (total < 0) {
            return 0;
        }

        int firstCoin = coins[0];
        int[] remainingCoins = Arrays.copyOfRange(coins, 1, coins.length);
        return coinChangePossibilities(coins, total - firstCoin) + coinChangePossibilities(remainingCoins, total);
    }
}