

## Userテーブル設計書

### 1. テーブル概要

この設計書は、アプリケーション内でユーザー情報を管理するための`users`テーブルの構造と仕様を定義します。

| 項目名 | 論理名 | 物理名 | データ型 | NULL許容 | 備考 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| ユーザーID | ID | `id` | `BIGINT` | NOT NULL | **主キー**、**自動採番** |
| 名前 | Name | `name` | `VARCHAR(255)` | NOT NULL | ユーザーの名前 |
| メールアドレス | Email Address | `email` | `VARCHAR(255)` | NOT NULL | **一意制約**（重複不可） |
| 年齢 | Age | `age` | `INT` | NULL許容 | ユーザーの年齢 |
| 性別 | Gender | `gender` | `VARCHAR(50)` | NULL許容 | 性別（例: 'male', 'female', 'other' など） |
| 登録日時 | Created At | `created_at` | `TIMESTAMP` | NOT NULL | レコード作成日時、**デフォルトで現在日時** |
| 更新日時 | Updated At | `updated_at` | `TIMESTAMP` | NOT NULL | レコード最終更新日時、**自動更新** |



### 2. テーブル定義SQL例

以下のSQLは、一般的なRDBMSで利用可能なテーブル定義の例です。データベースの種類によって、`id`の自動採番構文や`updated_at`の自動更新方法が異なる点に注意してください。

#### 2.1. PostgreSQL / MySQL 向けSQL例

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- PostgreSQLの場合は BIGSERIAL PRIMARY KEY
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT,
    gender VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- MySQL固有、PostgreSQLはトリガーが必要
);
