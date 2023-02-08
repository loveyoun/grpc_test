package proto.grpctest;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class FileServiceServer {

    @Autowired
    private final FileServiceImpl fileserviceImpl;
    public FileServiceServer(FileServiceImpl fileserviceImpl) {
        this.fileserviceImpl = fileserviceImpl;
    }

    private Server server;

    void start() throws IOException {
        int port = 50053;
        server = ServerBuilder.forPort(port)
                .addService(fileserviceImpl)
                .build()
                .start();
        System.out.println("Server Started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.err.println("***Shutting down gRPC server, since JVM is shutting down");
                try {
                    FileServiceServer.this.stop();
                } catch(InterruptedException e){
                    e.printStackTrace(System.err);
                }
            }
        });
    }

    private void stop() throws InterruptedException{
        if(server != null) server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    void blockUtilShutdown() throws InterruptedException{
        if(server != null) server.awaitTermination();
    }

}
