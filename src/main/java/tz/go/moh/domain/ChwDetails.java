package tz.go.moh.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChwDetails {
    /**
     * The national identification number of the CHW.
     */
    @JsonProperty("NationalIdentificationNumber")
    private String nationalIdentificationNumber;

    /**
     * The OpenMRS provider ID of the CHW.
     */
    @JsonProperty("OpenmrsProviderId")
    private String openmrsProviderId;

    /**
     * Default constructor for ChwDetails.
     */
    public ChwDetails() {
    }

    /**
     * Constructor for ChwDetails.
     *
     * @param nationalIdentificationNumber The national identification number of the CHW.
     * @param openmrsProviderId            The OpenMRS provider ID of the CHW.
     */
    public ChwDetails(String nationalIdentificationNumber, String openmrsProviderId) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.openmrsProviderId = openmrsProviderId;
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

    public String getOpenmrsProviderId() {
        return openmrsProviderId;
    }
    /**
     * Sets the OpenMRS provider ID of the CHW.
     *
     * @param openmrsProviderId The OpenMRS provider ID to set.
     */
    public void setOpenmrsProviderId(String openmrsProviderId) {
        this.openmrsProviderId = openmrsProviderId;
    }

    /**
     * Returns a string representation of the ChwDetails object.
     *
     * @return A string representation of the object, including the national identification number and OpenMRS provider ID.
     *
     *
     */
    @Override
    public String toString() {
        return "Chw{" +
                "nationalIdentificationNumber='" + nationalIdentificationNumber + '\'' +
                ", openmrsProviderId='" + openmrsProviderId + '\'' +
                '}';
    }
}
