package ua.ilyadreamix.m3amino.http.utility;

import java.util.Random;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.Base64;

public final class AminoRequestUtility {
    private static final String DEVICE_KEY = "02B258C63559D8804321C5D5065AF320358D366F";
    private static final String SIG_KEY = "F8E7A61AC3F725941E3AC7CAE2D688BE97F30B93";

    private static byte[] hexToBytes(String str) throws DecoderException {
        return Hex.decodeHex(str);
    }

    private static String hmacSha1Hex(
        byte[] value,
        byte[] key
    ) {
        HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key);
        return hmac.hmacHex(value);
    }

    private static byte[] hmacSha1Digest(
        byte[] value,
        byte[] key
    ) {
        HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key);
        return hmac.hmac(value);
    }

    public static String generateDeviceId() throws DecoderException {
        byte[] data = new byte[20];
        byte[] key = hexToBytes(DEVICE_KEY);

        new Random().nextBytes(data);

        String mac = hmacSha1Hex(
            ArrayUtils.addAll(
                hexToBytes("42"),
                data
            ),
            key
        );

        return ("42" +
            Hex.encodeHexString(data) +
            mac).toUpperCase();
    }

    public static String generateSig(String data) throws DecoderException {
        byte[] byteData = data.getBytes();
        byte[] key = hexToBytes(SIG_KEY);
        byte[] mac = hmacSha1Digest(
            byteData, key
        );

        byte[] b64Bytes = Base64.encodeBase64(
            ArrayUtils.addAll(
                hexToBytes("42"),
                mac
            )
        );
        return new String(b64Bytes);
    }
}
