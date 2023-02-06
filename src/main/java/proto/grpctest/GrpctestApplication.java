package proto.grpctest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpctestApplication {

	public static void main(String[] args) throws IOException, InterruptedException{
		SpringApplication.run(GrpctestApplication.class, args);

		final GreeterServer server = new GreeterServer();
		server.start();
		server.blockUtilShutdown();
	}

}
