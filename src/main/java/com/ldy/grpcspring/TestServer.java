package com.ldy.grpcspring;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.ldy.grpcSpring.GreeterGrpc;
import io.ldy.grpcSpring.TestRequest;
import io.ldy.grpcSpring.TestResponse;

import java.io.IOException;

/**
 * @description:
 * @author: TianSu_Ldy
 * @createDate: 2021/4/27
 */
public class TestServer {
    //定义端口
    private final int port = 50051;
    //服务
    private Server server;

    //启动服务,并且接受请求
    private void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();
        System.out.println("服务开始启动-------");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // 在这里使用stderr，因为记录器可能已被其JVM关闭钩子重置了
                System.err.println("------shutting down gRPC server since JVM is shutting down-------");
                TestServer.this.stop();
                System.err.println("------server shut down------");
            }
        });
    }

    //stop服务
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
    //server阻塞到程序退出
    private void  blockUntilShutdown() throws InterruptedException {
        if (server!=null){
            server.awaitTermination();
        }
    }

    //实现服务接口的类
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        public void testSomeThing(TestRequest request, StreamObserver<TestResponse> responseObserver) {
            TestResponse build = TestResponse.newBuilder().setMessage("Hello " + request.getName()).build();
            //onNext()方法向客户端返回结果
            responseObserver.onNext(build);
            //告诉客户端这次调用已经完成
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final  TestServer server=new TestServer();
        server.start();
        server.blockUntilShutdown();    //先启动服务,然后等待终止(期间客户端开启访问)
    }

}
