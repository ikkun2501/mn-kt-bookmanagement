# Bookmanagement
Kotlin Micronautを使用した書籍管理システムのAPI実装

# アーキテクチャ
DDD+オニオンアーキテクチャ+CQRSの構成で組んでいます。
レイヤーを分け責務を明確に分け、依存関係を一方方向にするようにしています。

## プロジェクト構成
プロジェクトを分割することで依存関係を強制するようにしています。
次の構成にプロジェクトを分割しています。

| プロジェクト | 内容 |
| --- | --- |
| root | すべてのプロジェクトを内包するプロジェクト |
| api | インターフェイス層(API)|
| domain| アプリケーション層、ドメイン層 |
| infrastructure| インフラストラクチャ層 |
| jooq-custom-strategy | jooqの自動生成カスタマイズするための実装 |

## アプリケーション層
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
Flywayというデータベースマイグレーションツールを導入しています。
Flywayを使うことで、データベースの状態をバージョン管理できるようになります。
FlywayのGradle Pluginを導入しており、次の表に記載したコマンドを実行すると各DBの状態を最新化（マイグレーション）します。

| DB | コマンド |
| --- | --- |
| 開発 | flywayMigrate | 
| ユニットテスト | unitFlywayMigrate | 

# OPEN API
コンパイルすると次のパスにymlファイルが出力されます。
```
api/build/tmp/kapt3/classes/main/META-INF/swagger/mn-kotlin-openapi-app-1.0.yml
```

# TODO
* [x] OpenAPI導入
* [ ] github actions
    * [x] ktlint
    * [ ] test
* [x] テストのダミーデータをfakerを利用して生成
* [ ] 権限チェック実装
* [ ] ユースケースでバリデーション（Arrow or YAVI)
* [ ] Client実装（react）
* [ ] EXPOSEによる実装
    * [ ] 環境変数による切り替え    
* [ ] coroutineによる実装 
* [ ] AWSにデプロイ
