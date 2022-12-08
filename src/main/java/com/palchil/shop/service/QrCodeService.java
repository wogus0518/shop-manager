package com.palchil.shop.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    public Object createQr(Long itemId) throws IOException {

        BitMatrix bitMatrix=null;
        MatrixToImageConfig matrixToImageConfig=null;
        // QRCode에 담고 싶은 정보를 문자열로 표시한다. url이든 뭐든 가능하다.
        String codeInformation = "http://localhost:8080/item/list/"+itemId;

        // 큐알코드 바코드 및 배경 색상값
        int qrcodeColor =   0xFF2e4e96; // 바코드 색
        int backgroundColor = 0xFFFFFFFF; // 배경 색

        // 이름 그대로 QRCode 만들때 쓰는 클래스다
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 큐알 전경과 배경의 색을 정한다. 값을 넣지 않으면 검정코드에 흰 배경이 기본값이다.
        matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);

        // QRCode 전체 크기
        // 단위는 fixel
        int width=200;
        int height=200;

        try {
            // bitMatrix 형식으로 QRCode를 만든다.
            bitMatrix = qrCodeWriter.encode(codeInformation, BarcodeFormat.QR_CODE, width, height);
            // QRCode 중간에 빈공간을 만들고 색을 offColor로 바꿔주는 메소드
            // bitMatrix= emptyQR(bitMatrix,regionHeight,regionWidth); // QR내부에 빈 공간 만드는 메소드(사용할 경우 hint의 error_correction 을 반드시 높여줘야 합니다)
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, matrixToImageConfig);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
