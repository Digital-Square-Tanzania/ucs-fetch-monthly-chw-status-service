package tz.go.moh.domain;

/**
 * Represents a period defined by a month and a year.
 */
public class Period {

    /**
     * The month of the period.
     */
    private String month;
    /**
     * The year of the period.
     */
    private String year;

    /**
     * Constructs a new Period object.
     */
    public Period() {
    }
    /**
     * Constructs a new Period object with the specified month and year.
     *
     * @param month The month of the period.
     * @param year  The year of the period.
     */
    public Period(String month, String year) {
        this.month = month;
        this.year = year;
    }

    /**
     * Gets the month of the period.
     *
     * @return The month of the period.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month of the period.
     *
     * @param month The month of the period.
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the year of the period.
     *
     * @return The year of the period.
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year of the period.
     * @param year The year of the period.
     */
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Period{" +
                "month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
