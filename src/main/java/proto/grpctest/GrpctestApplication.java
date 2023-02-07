package proto.grpctest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpctestApplication {

	@Autowired
	private static GreeterServer greeterServer;
	public GrpctestApplication(GreeterServer greeterServer) {
		this.greeterServer = greeterServer;
	}

	public static void main(String[] args) throws IOException, InterruptedException{
		SpringApplication.run(GrpctestApplication.class, args);

		greeterServer.start();
		greeterServer.blockUtilShutdown();
	}

}
