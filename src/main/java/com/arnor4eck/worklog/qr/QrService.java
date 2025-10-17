package com.arnor4eck.worklog.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** Сервис для работы с QR
 * */
@Service
public class QrService {

    /** Генерация QR
     * @param content Содержимое QR
     * @param height Высота в пикселях
     * @param width Ширина в пикселях
     * @return Массив байт - QR
     * */
    public byte[] generateQr(String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);

        return out.toByteArray();
    }

    /** Преобразование массива байтов в QR
     * @param qr Массив байт
     * @return ByteArrayResource - QR
     * */
    public ByteArrayResource qrResource(byte[] qr){
        return new ByteArrayResource(qr);
    }
}
