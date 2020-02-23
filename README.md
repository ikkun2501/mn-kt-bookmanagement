# Bookmanagement
Kotlin Micronautを使用した書籍管理システムのAPI実装

# アーキテクチャ
DDD+オニオンアーキテクチャ+CQRSの構成で組んでいます。
レイヤーを分け責務を明確に分け、依存関係を一方方向にするようにしています。

## CQRS
DDDをベースに参照系の処理を実装していくと、
実装の複雑化、パフォーマンスの低下といった問題が発生しました。
CQRSの考えを取り入れ、更新系と参照系の処理とモデルを分けています。
更新系の処理はCommand,参照系の処理はQueryに実装しています。

# 技術要素
* Micronaut
* Jooq
* JUnit5
* DbSetup
* ktlint
* Flyway


# 開発
## Databaseのセットアップ
dockerを使って開発用のデータベースをセットアップできるようにしています。
次のコマンドを実行するとテーブルに記載したDBにアクセスできるようになります。
```
docker-compose up
```

| DB | URL| user | password |
| --- | --- | --- | --- |
| 開発 | jdbc:mysql://localhost:33060 | root | "" |
| ユニットテスト | jdbc:mysql://localhost:33061 | root | "" |


## DBマイグレーション
Flyway は、オープンソースのデータベースマイグレーションツールです。
Flyway を使うことで、データベースの状態をバージョン管理できるようになります。
Gradle Pluginを導入しています。次の表に記載したコマンドを実行すると各DBにDBの状態を最新化（マイグレーション）されます。

| DB | コマンド |
| --- | --- |
| 開発 | flywayMigrate | 
| ユニットテスト | unitFlywayMigrate | 


