# Changelog
## [0.48.2-public] - 2024-05-08

### Important fixes
- Fixed the issue of relying on the user's local time zone when bucketing primary keys of DATE and DATETIME types during Tunnel upsert. This may lead to incorrect bucketing and abnormal data query. Users who rely on this feature are strongly recommended to upgrade to version 0.48.2.

### Added
- `Table` adds a method `getTableLifecycleConfig()` to obtain the lifecycle configuration of hierarchical storage.
- `TableReadSession` now supports predicate pushdown


## [0.48.1-public] - 2024-05-07

### Added
Arrow and ANTLR Libraries: Added new includes to the Maven Shade Plugin configuration for better handling and packaging of specific libraries. These includes ensure that certain essential libraries are correctly packaged into the final shaded artifact. The newly included libraries are:
- org.apache.arrow:arrow-format:jar
- org.apache.arrow:arrow-memory-core:jar
- org.apache.arrow:arrow-memory-netty:jar
- org.antlr:ST4:jar
- org.antlr:antlr-runtime:jar
- org.antlr:antlr4:jar
- org.antlr:antlr4-runtime:jar

### Relocation Adjustments
Shaded Relocation for ANTLR and StringTemplate: The configuration now includes updated relocation rules for org.antlr and org.stringtemplate.v4 packages to prevent potential conflicts with other versions of these libraries that may exist in the classpath. The new shaded patterns are:
org.stringtemplate.v4 relocated to com.aliyun.odps.thirdparty.org.stringtemplate.v4
org.antlr relocated to com.aliyun.odps.thirdparty.antlr

## [0.48.0-public] - 2024-04-22

### Added
- Introduced `odps-sdk-udf` module to allow batch data reading in UDFs for MaxCompute, significantly improving performance in high-volume data scenarios.
- `Table` now supports retrieving `ColumnMaskInfo`, aiding in data desensitization scenarios and relevant information acquisition.
- Support for setting proxies through the use of `odps.getRestClient().setProxy(Proxy)` method.
- Implementation of iterable `RecordReader` and `RecordReader.stream()` method, enabling conversion to a Stream of `Record` objects.
- Added new parameters `upsertConcurrentNum` and `upsertNetworkNum` in `TableAPI RestOptions` for more detailed control for users performing upsert operations via the TableAPI.
- Support for `Builder` pattern in constructing `TableSchema`.
- Support for `toString` method in `ArrayRecord`.

### Improved
- `UploadSession` now supports configuration of the `GET_BLOCK_ID` parameter to speed up session creation when the client does not need `blockId`.
- Enhanced table creation method using the `builder` pattern (`TableCreator`), making table creation simpler.

### Fixed
- Fixed a bug in `Upsert Session` where the timeout setting was configured incorrectly.
- Fixed the issue where `TimestampWritable` computed one second less when nanoseconds were negative.

## [0.47.0-public] - 2024-04-08

### Added
- Support for new Stream type that enables incremental queries.
- `preview` method to the `TableTunnel` for data preview purposes.
- `OdpsRecordConverter` for parsing and formatting records.
- Enhancements to the `Projects` class with `create` and `delete` methods now available, and `update` method made public. Operations related to the `group-api` package are now marked as deprecated.
- Improved `Schemas` class to support filtering schemas with `SchemaFilter`, listing schemas, and retrieving detailed schema metadata.
- `DownloadSession` introduces new parameter `disableModifiedCheck` to bypass modification checks and `fetchBlockId` to skip block ID list retrieval.
- `TableWriteSession` supports writing `TIMESTAMP_NTZ` / `JSON` types and adds a new parameter `MaxFieldSize`.
- `TABLE_API` adds `predicate` related classes to support predicate pushdown in the future.

### Changed
- The implementation of the `read` method in the `Table` class is now replaced with `TableTunnel.preview`, supporting new types in MaxCompute and time types switched to Java 8 time types without timezone.
- The default `MapWritable` implementation switched from `HashMap` to `LinkedHashMap` to ensure order.
- `Column` class now supports creation using the Builder pattern.

### Improved
- `TableReadSession` now introduces new parameters `maxBatchRawSize` and `splitMaxFileNum`.
- `UpsertSession` enhancements:
  - Supports writing partial columns.
  - Allows setting the number of Netty thread pools with the default changed to 1.
  - Enables setting maximum concurrency with the default value changed to 16.
- `TableTunnel` now supports setting `quotaName` option.

