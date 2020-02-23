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
