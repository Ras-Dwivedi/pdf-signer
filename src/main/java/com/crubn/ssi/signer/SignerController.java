package com.crubn.ssi.signer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

@RestController
public class SignerController {
    /*
    This class signs the pdf using the .pfx file
     */


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private byte[] responseData = null;

//    @Autowired

    @RequestMapping(method = RequestMethod.GET,
            value = "/home",
            produces = MediaType.TEXT_PLAIN_VALUE)

    public String homePage() {
        LOGGER.error("DONE");
        return "Welcome to pdfSigner home page";
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/sign",
            produces = MediaType.TEXT_PLAIN_VALUE)
    /**
     * This function sets up the connection with the card.
     * It should be called everytime the card is connected to the PC.
     * This function performs the task of applet selection.
     * In case single card is used, no function would work until applet selection is done.
     * This function should be called before any other function, except detect card function which
     * performs the similar task but for multiple cards
     */

//    public static void signPdf() throws IOException, KeyStoreException, CertificateException,
//            NoSuchAlgorithmException, UnrecoverableEntryException, InvalidKeyException, SignatureException{
    public String signPage() {
            LOGGER.info("START: Init Page");

            try {
                return getSign();
            } catch (Exception e) {
                return "Error in creating signature";
            }
        }

    @RequestMapping(method = RequestMethod.POST,
            value = "/sign1",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String signPostPage(@RequestBody Message message){
        try {
            return getSign(message.getMsg());
        } catch (Exception e) {
            return "Error in creating signature";
        }
    }

    public String getSign() throws Exception{
        return getSign("Test message");
    }

    //TODO: convert this to another class to PFXHandler
    public String getSign(String msg) throws Exception {
        System.out.println("signing on "+msg);
        Certificate certificate = null;
        PrivateKey pvtkey = null;

        // load key from pfx keystore
        Enumeration<String> aliasList;
        String alias;
        // password need to be secured
        String pfxPath = "cert/somecert.pfx", certPassword = "pass";

        // create a keystore
        File securityFileKeyPair = new File(pfxPath);
        InputStream cerFileStream = new FileInputStream(securityFileKeyPair);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(cerFileStream, certPassword.toCharArray());
        aliasList = keyStore.aliases();
        while (aliasList.hasMoreElements()) {
            alias = aliasList.nextElement();
            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(certPassword.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, entryPassword);
            pvtkey = privateKeyEntry.getPrivateKey();
            System.out.println("Private key is:" + pvtkey);

            //TODO remove printing certificate
            String cert = getCertificate((X509Certificate) keyStore.getCertificate(alias));


            // sign
//            PrivateKey privKey = pvtkey;
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(pvtkey);
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            sign.update(bytes);
            byte[] signature = sign.sign();
            String base64encodedString = Base64.getEncoder().encodeToString(signature);
            System.out.println("Digital signature of the file: " + base64encodedString);

            //TODO remove verification
            // verify
            PublicKey pubKey = certificate.getPublicKey();
            Signature verify = Signature.getInstance("SHA256withRSA");
            // verification with the public key inside certificate
            verify.initVerify(pubKey);
            // verification with the certificate itself
            //verify.initVerify(certificate);
            verify.update(bytes);
            boolean signatureVerified = verify.verify(signature);
            System.out.println("signatureVerified: " + signatureVerified);
            return base64encodedString;
        }
        return "No private key found";
    }

    private String getCertificate(Certificate certificate) throws CertificateEncodingException {
        StringWriter sw = new StringWriter();
        sw.write("-----BEGIN CERTIFICATE-----\n");
        sw.write(DatatypeConverter.printBase64Binary(certificate.getEncoded()).replaceAll("(.{64})", "$1\n"));
        sw.write("\n-----END CERTIFICATE-----\n");
        return sw.toString();
    }
}
