
## SDK源码和示例
- SDK源码请参见[GitHub](https://github.com/mtyj-hz/mtyw-java-sdk)
- Maven示例工程：mtyw-sdk-demo-mvn.zip

## 安装
#### 1.在Maven项目中加入依赖项（推荐方式）：

在Maven工程中使用OSS Java SDK，只需在pom.xml中加入相应依赖即可。以3.10.2版本为例，在<dependencies>中加入如下内容：

```xml
<dependency>
    <groupId>mtyw-sdk</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.10.2</version>
</dependency>
```

#### 2.在Intellij IDEA项目中导入JAR包

- 下载并解压Java SDK 开发包。
- 将解压后文件夹中的文件mtyw-sdk.jar以及lib文件夹下的所有JAR文件拷贝到您的项目中。
- 在Intellij IDEA中选择您的工程，右键选择File > Project Structure > Modules > Dependencies > + > JARs or directories 。
- 选中拷贝的所有JAR文件，导入到External Libraries中。

## 获取Access Key
[点击这里](点击这里)获取和查看Access Key


## 标准存储

#### 获取节点列表
```java
String accessKeyId = "<yourAccessKeyId>";
String accessKeySecret = "<yourAccessKeySecret>";


```

#### 上传文件

```java
String accessKeyId = "<yourAccessKeyId>";
String accessKeySecret = "<yourAccessKeySecret>";


```

#### 下载文件

#### 获取文件列表

#### 新建文件夹

## 冷备存储

#### 上传文件

#### 检索文件

#### 下载文件