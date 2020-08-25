package com.github.geekuniversity_java_215.cmsbackend.utils.testcontainers;

import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.Socket;

//@Testcontainers
public class PostgresContainerStarter {

    private Socket testSocket;

    private final static int CONTAINER_PORT = 5432;
    private final static int EXPOSED_PORT = 5442;

    // DockerImageName ("redis:5.0.3-alpine")
    //@Container
    private static final FixedHostPortGenericContainer<?> container = new FixedHostPortGenericContainer<>("dreamworkerln/cms-postgres")
        .withEnv("POSTGRES_PASSWORD","gjhUY876787ytuh87gdf")
        .withEnv("PGDATA","/var/lib/postgresql/data/pgdata")
        .withFixedExposedPort(EXPOSED_PORT, CONTAINER_PORT);

//    static {
//        container.start();
//    }


    public static void start() {
        container.start();
    }

    public static void stop() {
        container.stop();
    }

//    //@BeforeAll
//    public static void setUp() {
//        String address = postgres.getHost();
//        Integer port = postgres.getFirstMappedPort();
//
//        System.out.println(postgres);
//
//        // Now we have an address and port for Redis, no matter where it is running
//
//        try {
//            testSocket = new Socket(address, port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    @Test
//    public void testSimplePutAndGet() {
//
////        underTest.put("test", "example");
////
////        String retrieved = underTest.get("test");
////        assertEquals("example", retrieved);
//    }
}

