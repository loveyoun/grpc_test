package proto.grpctest;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase{ //.proto file에 package 써주어야 GreeterGrpc 인식함.

    @Override
    public void hello(GreeterOuterClass.Hello.Request request, StreamObserver<GreeterOuterClass.Hello.Response> responseObserver) {
        final String str = "Hello " + request.getName() + "(" + request.getAge() + ")";
        System.out.println(str);

        final GreeterOuterClass.Hello.Response response = GreeterOuterClass.Hello.Response.newBuilder()
                .setStr(str).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        //super.hello(request, responseObserver);
    }

}
