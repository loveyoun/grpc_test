package proto.grpctest;

import greet.GreeterGrpc;
import greet.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class GreeterImpl extends GreeterGrpc.GreeterImplBase{ //.proto file에 package 써주어야 GreeterGrpc 인식함.

    //클라이언트가 stub을 통해 hello를 불렀을 때, 실제로 수행될 작업들
    @Override
    public void hello(GreeterOuterClass.Hello.Request request, StreamObserver<GreeterOuterClass.Hello.Response> responseObserver) {
        //클라이언트가 보낸 값에 대해, 서버가 생성한 질의 request.
        final String str = "Hello " + request.getName() + "(" + request.getAge() + ")";
        System.out.println(str);

        //클라이언트에 보낼 응답 response.
        final GreeterOuterClass.Hello.Response response = GreeterOuterClass.Hello.Response.newBuilder()
                .setStr(str).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        //super.hello(request, responseObserver);
    }

}
