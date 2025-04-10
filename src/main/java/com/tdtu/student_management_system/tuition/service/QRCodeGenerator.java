package com.tdtu.student_management_system.tuition.service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    public static byte[] getQRCodeByAsByteArray(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
        return stream.toByteArray();
    }
}
