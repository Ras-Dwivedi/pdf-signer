package com.crubn.ssi.signer;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

public class DSCPublicKeyExtractor {
    public static void main(String[] args) {



        try {
            Security.addProvider(new BouncyCastleProvider());
            String configPath = "config.cfg";
            Provider pkcs11Provider = Security.getProvider("SunPKCS11");
            pkcs11Provider = pkcs11Provider.configure(configPath);
            KeyStore pkcs11KeyStore = KeyStore.getInstance("PKCS11", pkcs11Provider);
            char[] password = "12345678".toCharArray(); // Replace with your PIN or password
            pkcs11KeyStore.load(null, password);
            java.util.Enumeration<String> aliases = pkcs11KeyStore.aliases();
            int noAliases = 0;
            List<String> aliasList = new ArrayList<>();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Alias: " + alias);
                noAliases+=1;
                aliasList.add(alias);
            }
            System.out.println("Total number of alias are: "+noAliases);
            String firstAlias = aliasList.isEmpty() ? null : aliasList.get(0);

            if (firstAlias != null) {
                // Extract the certificate for the first alias
                Certificate certificate = pkcs11KeyStore.getCertificate(firstAlias);

                // Now, you can work with the certificate
                System.out.println("Certificate for the first alias (" + firstAlias + "):");
                System.out.println(certificate);
            } else {
                System.out.println("No aliases found in the PKCS11 KeyStore.");
            }
//            pkcs11KeyStore.getCertificate()
//            X509Certificate certificate = (X509Certificate) pkcs11KeyStore.getCertificate(aliasList[0]);
////
//        // Get the public key from the certificate.
//        PublicKey publicKey = certificate.getPublicKey();
//
//        return publicKey;

////            Provider pkcs11Provider = Security.getProvider("PKCS11");
//            Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configPath);
//            KeyStore keyStore = KeyStore.getInstance("PKCS11", pkcs11Provider);
////            keyStore.load(null, null);




//            Pkcs11Token pkcs11Token = new Pkcs11Token("my-token", "pkcs11.conf");



//            KeyStore.getInstance("PKCS11");

//            PublicKey publicKey = getPublicKey("/usr/local/lib/libcastle_v2.1.0.0.dylib","12345678", "Certificate");

//            // Load the DSC keystore
//            String dscKeystorePath = "/usr/local/lib/libcastle_v2.1.0.0.dylib"; // Replace with the actual path to your DSC keystore
//            String dscKeystorePassword = "12345678"; // Replace with your keystore password
//
//            KeyStore dscKeyStore = KeyStore.getInstance("PKCS11");
//            dscKeyStore.load(null, dscKeystorePassword.toCharArray());
//
//            // Get the DSC certificate
//            String dscCertificateAlias = "your-certificate-alias"; // Replace with the alias of your certificate
//            Certificate certificate = dscKeyStore.getCertificate(dscCertificateAlias);
//
//            // Extract the public key from the certificate
//            PublicKey publicKey = certificate.getPublicKey();
//
//            // Output the public key
//            System.out.println("Public Key:");
//            System.out.println(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static PublicKey getPublicKey(String tokenPath, String tokenPIN, String fileName)
//            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException {
//        System.out.println("trying to read the public key");
//        // Create a new KeyStore object and initialize it with the PKCS#11 provider.
//        KeyStore keyStore = KeyStore.getInstance("PKCS11");
//        keyStore.load(null, null);
//
//        // Load the DSC token into the KeyStore object.
//        keyStore.load(new FileInputStream(tokenPath), tokenPIN.toCharArray());
//
//        // Get the certificate from the KeyStore object that contains the public key of the file you want to access.
//        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(fileName);
//
//        // Get the public key from the certificate.
//        PublicKey publicKey = certificate.getPublicKey();
//
//        return publicKey;
//    }

}
