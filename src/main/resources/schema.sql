CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT,
    gender VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- H2はON UPDATE構文をサポートしないため、アプリケーション側で更新するか、トリガーを使用
);

INSERT INTO users (name, email, age, gender, created_at, updated_at) VALUES
  ('山田太郎', 'taro.yamada@example.com', 28, 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('佐藤花子', 'hanako.sato@example.com', 25, 'F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('鈴木一郎', 'ichiro.suzuki@example.com', 32, 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
