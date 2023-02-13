package proto.grpctest;

import com.google.protobuf.ByteString;
import file.DataChunk;
import file.DownloadFileRequest;
import file.FileServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@NoArgsConstructor
public class FileServiceImpl extends FileServiceGrpc.FileServiceImplBase {
    @Override
    public void downloadFile(DownloadFileRequest request, StreamObserver<DataChunk> responseObserver) {
        try {
            //클라이언트에서 보낸 요청: request
            String fileName = request.getFileName();

            //file 읽고,
            Path path = Paths.get(fileName);
            byte[] contents = Files.readAllBytes(path); //얘 때문에 try~catch 해주어야 함.

            // byte array로 바꾸기
            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(contents));
            int bufferSize = Integer.MAX_VALUE/2;
            byte[] buffer = new byte[bufferSize];
            int length;
            while((length=bis.read(buffer, 0, bufferSize)) != -1){
                //클라이언트로 데이터 보내기
                responseObserver.onNext(DataChunk.newBuilder()
                        .setData(ByteString.copyFrom(buffer, 0, length))
                        .setSize(bufferSize)
                        .build());
                log.info("importing files...");
            }

            bis.close();

            responseObserver.onCompleted();
        } catch (Throwable e) {
            responseObserver.onError(Status.ABORTED
                    .withDescription("***Unable to acquire the image" + request.getFileName())
                    .withCause(e)
                    .asException());
            //throw new RuntimeException(e);
        }

        //super.downloadFile(request, responseObserver);
    }

}
