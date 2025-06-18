# Kotlin01

起動コマンド

./gradlew bootRun

ビルド失敗した時の調査
./gradlew bootRun --stacktrace

自動修正
./gradlew ktlintFormat

閉じる時

control + C

./gradlew clean build

テストパス

http://localhost:8080/hello


curl http://localhost:8080/public/hello

curl -H "Authorization: Bearer <YOUR_FIREBASE_ID_TOKEN>" http://localhost:8080/secure/hello
