//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Player {
    private String name;
    private int score;
    private int consecutiveMatches;
    private int attempts;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.consecutiveMatches = 0;
        this.attempts = 0;
    }

    void sumScore() {
        ++this.consecutiveMatches;
        this.score += 10 * this.consecutiveMatches;
    }

    void formatConsecutiveMatches() {
        this.consecutiveMatches = 0;
    }

    void onePlusAttempts() {
        ++this.attempts;
    }

    String getName() {
        return this.name;
    }

    int getScore() {
        return this.score;
    }

    int getAttempts() {
        return this.attempts;
    }
}
