package modulo.logic.writer;

/**
 * Class that helps to convert repeated events into .ics format.
 */
public class IcsRRule {
    private String freq;
    private int count;
    private String byDay;

    /**
     * Constructs a helper class with the given parameters.
     *
     * @param freq  Frequency of repeat.
     * @param count Times to repeat.
     * @param byDay When to stop repeat.
     */
    public IcsRRule(String freq, int count, String byDay) {
        this.freq = freq;
        this.count = count;
        this.byDay = byDay;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FREQ=" + freq + ";"
                + "COUNT=" + count + ";"
                + "BYDAY=" + byDay;
    }
}
