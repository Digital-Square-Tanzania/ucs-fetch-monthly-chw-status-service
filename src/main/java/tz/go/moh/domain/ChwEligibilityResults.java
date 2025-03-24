package tz.go.moh.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChwEligibilityResults {
    /**
     * The national identification number of the CHW.
     */
    @JsonProperty("NationalIdentificationNumber")
    private String nationalIdentificationNumber;

    /**
     * Indicates whether the CHW is eligible.
     */
    @JsonProperty("Eligible")
    private boolean eligible;

    /**
     * Default constructor for ChwEligibilityResults.
     */
    public ChwEligibilityResults() {
    }

    /**
     * Constructor for ChwEligibilityResults.
     *
     * @param nationalIdentificationNumber The national identification number of the CHW.
     * @param eligible                     Indicates whether the CHW is eligible.
     */
    public ChwEligibilityResults(String nationalIdentificationNumber, boolean eligible) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.eligible = eligible;
    }

    /**
     * Gets the national identification number of the CHW.
     *
     * @return The national identification number.
     */
    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    /**
     * Sets the national identification number of the CHW.
     *
     * @param nationalIdentificationNumber The national identification number to set.
     */
    public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    /**
     * Checks if the CHW is eligible.
     *
     * @return True if the CHW is eligible, false otherwise.
     */
    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    /**
     * Returns a string representation of the ChwEligibilityResults object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Chw{" +
                "nationalIdentificationNumber='" + nationalIdentificationNumber + '\'' +
                ", elibible='" + eligible + '\'' +
                '}';
    }
}
