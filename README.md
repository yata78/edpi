# プロジェクト名 (Project Name)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 概要 (Overview)
このプロジェクトは、FPSゲームプレイヤー向けのEDPI管理アプリケーションです。ユーザーが自身のEDPI設定や試合成績を管理し、統計情報を活用することで、パフォーマンス向上をサポートします。

## 主な機能 (Features)
- **ユーザー管理機能**
  - ユーザー登録、ログイン、ログアウト (Spring Securityでの予定)
- **EDPI管理**
  - EDPIの登録、編集、削除
  - 勝率とHS率の登録
  - 登録データのソート機能  (予定)
- **非同期通信**
  - EDPI削除やバリデーションの非同期化  (予定)
- **デプロイ**
  - AWSを使用したクラウドデプロイ (予定)

## 使用技術 (Technologies Used)
- **バックエンド:** Spring Boot, Java 17
- **フロントエンド:** HTML, CSS, JavaScript
- **データベース:** PostgreSQL
- **ビルドツール:** Maven
- **デプロイ:** AWS (予定)

## 環境構築 (Setup)
1. **リポジトリのクローン**
   ```bash
   git clone https://github.com/yourusername/your-repository.git
   cd your-repository
