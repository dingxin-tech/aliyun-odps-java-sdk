# 更新日志
## [0.48.2-public] - 2024-05-08

### 重要修复
- 修复了Tunnel upsert时，对DATE、DATETIME类型的主键进行分桶时，依赖用户本地时区的问题。这可能导致分桶有误，导致数据查询异常。强烈建议依赖该特性的用户升级到0.48.2版本。

### 新增
- `Table`增加获取分层存储的lifecycle配置的方法`getTableLifecycleConfig()`。
- `TableReadSession` 现支持谓词下推了


## [0.48.1-public] - 2024-05-07

### 新增

Arrow和ANTLR库：在 Maven Shade 插件配置中添加了新的包含项，以更好地处理和打包特定库。这些包含项确保某些关键库被正确地打包进最终的遮蔽(Shaded)构件中。新加入的库包括：
- org.apache.arrow:arrow-format:jar
- org.apache.arrow:arrow-memory-core:jar
- org.apache.arrow:arrow-memory-netty:jar
- org.antlr:ST4:jar
- org.antlr:antlr-runtime:jar
- org.antlr:antlr4:jar
- org.antlr:antlr4-runtime:jar

### 位置调整
ANTLR和StringTemplate的遮蔽重定位：配置现在包括针对 org.antlr 和 org.stringtemplate.v4 包的更新重定位规则，以防止可能在类路径中存在的这些库的其他版本的潜在冲突。新的遮蔽模式是：
org.stringtemplate.v4 重定位至 com.aliyun.odps.thirdparty.org.stringtemplate.v4
org.antlr 重定位至 com.aliyun.odps.thirdparty.antlr

## [0.48.0-public] - 2024-04-22

### 新增
- 引入了`odps-sdk-udf`模块，支持在UDF中按批读取MaxCompute数据，能在大数据量场景下显著提高性能。
- `Table`现支持获取`ColumnMaskInfo`，用于数据脱敏场景，方便相关信息的获取。
- 新增通过`odps.getRestClient().setProxy(Proxy)`方法设置代理的支持。
- 实现了可迭代的`RecordReader`以及`RecordReader.stream()`方法，允许将其转换为`Record`对象的流。
- 在`TableAPI RestOptions`中新增`upsertConcurrentNum`和`upsertNetworkNum`参数，为使用TableAPI进行upsert操作的用户提供更细致的控制。
- 支持使用`Builder`模式来构建`TableSchema`。
- `ArrayRecord`支持`toString`方法。

### 变更
- 现在，当用户使用`StsAccount`但不传递`StsToken`时，将被视作使用`AliyunAccount`。

### 改进
- `UploadSession`现支持配置`GET_BLOCK_ID`参数，当客户端不需要`blockId`时，可以加速创建Session的速度。
- 使用`builder`模式(`TableCreator`)加强了表的创建方法，现在可以更简单地创建表了。

### 修复
- 修复了`Upsert Session`获取连接时，超时时间配置错误的问题。
- 修复了`TimestampWritable`在纳秒为负数时计算出错一秒的问题。

## [0.47.0-public] - 2024-04-08

### 新增
- 对 Stream 新类型的支持，可用于进行增量查询。
- 在 `TableTunnel` 中增加了 `preview` 方法，用于数据预览。
- 引入 `OdpsRecordConverter`，用于对 Record 进行解析和格式化。
- `Projects` 类增加了 `create`（创建）和 `delete`（删除）方法，`update` 方法现已公开。`group-api` 包下的相关操作已被标记为弃用。
- `Schemas` 类增强，支持通过设置 `SchemaFilter` 来过滤 schema，支持 `listSchema` 以及获取 schema 的详细元信息。
- `DownloadSession` 新增参数 `disableModifiedCheck`，用于跳过修改检查。新增参数 `fetchBlockId`，用于跳过获取 block ID 列表。
- `TableWriteSession` 支持写入 `TIMESTAMP_NTZ` / `JSON` 类型，新增参数 `MaxFieldSize`。
- `TABLE_API` 新增 `predicate` 相关类，用于后续支持谓词下推。

### 变更
- `Table` 类的 `read` 方法实现现已更换为 `TableTunnel.preview` 方法，会支持 MaxCompute 新类型，时间类型切换为 Java 8 无时区类型。
- 默认的 `MapWritable` 实现从 `HashMap` 改为 `LinkedHashMap`，以确保有序。
- `Column` 类现支持使用建造者模式（Builder pattern）进行创建。

### 改进
- `TableReadSession` 新增参数 `maxBatchRawSize` 和 `splitMaxFileNum`。
- `UpsertSession` 现支持：
  - 写入部分列。
  - 设置 Netty 线程池的数量（默认更改为 1）。
  - 设置最大并发量（默认值更改为 16）。
- `TableTunnel` 支持设置 `quotaName` 选项。

