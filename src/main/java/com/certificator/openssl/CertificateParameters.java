package com.certificator.openssl;

import java.util.Map;

import static com.certificator.openssl.SubjectAttr.*;

public class CertificateParameters {
  private String commonName;
  private String country;
  private String stateOrProvince;
  private String locality;
  private String organizationName;
  private String organizationalUnit;
  private String issuerCN;

  public CertificateParameters() {
  }

  public CertificateParameters(String commonName,
                               String country,
                               String stateOrProvince,
                               String locality,
                               String organizationName,
                               String organizationalUnit,
                               String issuerCN) {
    this.commonName = commonName;
    this.country = country;
    this.stateOrProvince = stateOrProvince;
    this.locality = locality;
    this.organizationName = organizationName;
    this.organizationalUnit = organizationalUnit;
    this.issuerCN = issuerCN;
  }

  public String getCommonName() {
    return commonName;
  }

  public String getCountry() {
    return country;
  }

  public String getStateOrProvince() {
    return stateOrProvince;
  }

  public String getLocality() {
    return locality;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public String getOrganizationalUnit() {
    return organizationalUnit;
  }

  public String getIssuerCN() {
    return issuerCN;
  }

  public String toCN() {
    assert commonName != null && !commonName.isBlank();

    String result = createPart(CN, commonName);
    result += createPart(OU, organizationalUnit);
    result += createPart(O, organizationName);
    result += createPart(L, locality);
    result += createPart(ST, stateOrProvince);
    result += createPart(C, country);

    return result;
  }

  private String createPart(SubjectAttr s, String value) {
    if(value == null || value.isBlank()) {
      return "";
    }

    return "/" + s.name() + "=" + value;
  }

  public static CertificateParameters fromMap(Map<SubjectAttr, String> params, String issuerCN) {
    return new CertificateParameters(params.get(CN), params.get(C), params.get(ST),
        params.get(L), params.get(O), params.get(OU), issuerCN);
  }
}