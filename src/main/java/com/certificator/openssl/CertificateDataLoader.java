package com.certificator.openssl;

import com.certificator.openssl.structure.CAFileStructure;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class CertificateDataLoader {
  private final String certificatesRoot;

  public CertificateDataLoader(@Value("${certificates.root}") String certificatesRoot) {
    this.certificatesRoot = certificatesRoot;
  }

  public CertificateParameters getCAParameters(String caCN) {
    if(caCN == null || caCN.isBlank()) {
      return new CertificateParameters();
    }

    X509Certificate x509Certificate = loadCACertificate(caCN);
    LdapName ldapName = parseDN(x509Certificate.getSubjectX500Principal());
    Map<SubjectAttr, String> subjectAttributes = ldapName.getRdns().stream().collect(Collectors.<Rdn, SubjectAttr, String>toMap(rdn -> SubjectAttr.valueOf(rdn.getType()), rdn -> (String)rdn.getValue()));
    return CertificateParameters.fromMap(subjectAttributes, null);
  }

  private LdapName parseDN(X500Principal subjectX500Principal) {
    try {
      return new LdapName(subjectX500Principal.getName());
    } catch (InvalidNameException e) {
      throw new RuntimeException(e);
    }
  }

  public X509Certificate loadCACertificate(String cn) {
    CAFileStructure fs = new CAFileStructure(certificatesRoot, cn);

    try {
      CertificateFactory cf = CertificateFactory.getInstance("X509");
      return (X509Certificate) cf.generateCertificate(new FileInputStream(fs.getCaPEM().toString()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}