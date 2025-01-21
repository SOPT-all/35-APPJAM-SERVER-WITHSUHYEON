package sopt.appjam.withsuhyeon.util;

import org.springframework.web.multipart.MultipartFile;
import sopt.appjam.withsuhyeon.exception.FileErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.global.exception.GlobalErrorCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileConvertUtil {
    /**
     * MultipartFile -> File 변환 메서드
     *
     * @param multipartFile 변환할 MultipartFile
     * @return 변환된 File
     * @throws IOException 파일 변환 중 발생할 수 있는 예외
     */
    public static File convertMultipartFileToFile(
            MultipartFile multipartFile
    ) {
        // 원본 파일명이 존재하지 않으면 임의의 파일명을 생성
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            originalFilename = "temp_" + System.currentTimeMillis();
        }

        try {
            // OS에서 제공하는 임시 디렉토리에 파일 생성
            File file = File.createTempFile("temp_", "_" + originalFilename);
            // 임시 파일을 삭제 가능한 상태로 표시 (프로그램이 종료될 때 삭제 시도)
            file.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            } catch (Exception e) {
                throw BaseException.type(GlobalErrorCode.INTERNAL_SERVER_ERROR);
            }
            return file;
        } catch (IOException e) {
            throw BaseException.type(FileErrorCode.FILE_CONVERT_ERROR);
        }
    }

}
