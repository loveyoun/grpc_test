package proto.grpctest;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.quarkus.grpc.GrpcService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class GreeterServer {

    private Server server;

    void start() throws IOException{
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        System.out.println("Server Started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.err.println("***Shutting down gRPC server, since JVM is shutting down");
                try {
                    GreeterServer.this.stop();
                } catch(InterruptedException e){
                    e.printStackTrace(System.err);
                }
                //super.run();
            }
        });
    }

    private void stop() throws InterruptedException{
        if(server != null) server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    void blockUtilShutdown() throws InterruptedException{
        if(server != null) server.awaitTermination();
    }

//    public static void main(String[] args) throws IOException, InterruptedException{
//
//    }

}
