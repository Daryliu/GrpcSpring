# GrpcSpring
 创建一个空的spring项目，用于实现基本grpc-java

# 步骤
1. 配置pom.xml文件
2. 在main文件夹内创建proto文件夹
3. 在proto文件夹中创建并编写.proto文件
4. 双击maven中install，在target文件夹下generated-sources中生成GreeterGrpc等一系列文件（如显示有问题，可以试试右键target->generated-sources->protobuf->grpc-java，选择Mark Directory as
   中的Generated Sources Root）
5. 编写服务端代码
6. 编写客户端代码
7. 先运行服务端，再运行客户端进行测试