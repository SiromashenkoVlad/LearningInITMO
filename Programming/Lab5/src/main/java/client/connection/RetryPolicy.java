package client.connection;

public class RetryPolicy {
    private final int maxAttempts;
    private final int baseDelaySeconds;

    public RetryPolicy(int maxAttempts, int baseDelaySeconds) {
        this.maxAttempts = maxAttempts;
        this.baseDelaySeconds = baseDelaySeconds;
    }

    public int getDelay(int attempt) {
        if (attempt >= maxAttempts) return -1;
        return baseDelaySeconds * (attempt + 1);
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}