CREATE SCHEMA `ssafytrip`;

USE `ssafytrip`;

CREATE TABLE Members (
    email VARCHAR(40) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    title VARCHAR(30),
    exp BIGINT DEFAULT 0,
    created_at DATETIME DEFAULT NOW(),
    is_admin TINYINT(1) DEFAULT 0 COMMENT '0:MEMBER, 1:ADMIN',
    mode TINYINT(1) DEFAULT 0 COMMENT '0:OFF, 1:ON',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '0:FALSE, 1:TRUE',
    profile_image_url TEXT
);

CREATE TABLE Posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(40) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME,
    category ENUM('NORMAL', 'NOTICE') DEFAULT 'NORMAL'
);

CREATE TABLE Likes (
    like_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL
);

CREATE TABLE Comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME
);

CREATE TABLE Plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    member_email VARCHAR(40) NOT NULL,
    title VARCHAR(200),
    description VARCHAR(200),
    start_date DATETIME,
    end_date DATETIME,
    created_at DATETIME DEFAULT NOW(),
    is_start TINYINT(1) DEFAULT 0,
    is_shared TINYINT(1) DEFAULT 0
);

CREATE TABLE Saves (
    member_email VARCHAR(40),
    plan_id INT,
    saved_at DATETIME DEFAULT NOW()
);

CREATE TABLE Plans_Routes (
    plan_id INT NOT NULL,
    attraction_id INT NOT NULL,
    day INT NOT NULL,
    sequence INT NOT NULL
);

CREATE TABLE Routes (
    attraction_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    latitude decimal(20,17) DEFAULT NULL,
    longitude decimal(20,17) DEFAULT NULL,
    type VARCHAR(30),
    category_name VARCHAR(200),
    phone VARCHAR(15)
);

CREATE TABLE Friends (
    email1 VARCHAR(40),
    email2 VARCHAR(40),
    created_at DATETIME DEFAULT NOW(),
    PRIMARY KEY (email1, email2)
);

CREATE TABLE Follow_Requests (
    requester_email VARCHAR(40),
    target_email VARCHAR(40),
    status ENUM('APPLIED', 'ACCEPTED', 'DENIED') NOT NULL DEFAULT 'APPLIED',
    created_at DATETIME DEFAULT NOW(),
    PRIMARY KEY (requester_email, target_email)
);

CREATE TABLE Titles (
    title_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Members_Titles (
    member_email VARCHAR(40) NOT NULL,
    title_id INT NOT NULL,
    created_at DATETIME DEFAULT NOW()
);

CREATE TABLE Quests (
    location_quest_id INT AUTO_INCREMENT PRIMARY KEY,
    attraction_id INT NOT NULL,
    member_email VARCHAR(40) NOT NULL,
    title VARCHAR(100),
    quest_description VARCHAR(10000) NULL,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'MEDIUM',
    created_at    DATETIME NOT NULL DEFAULT NOW(),
    is_private TINYINT(1) DEFAULT 0,
    content_type_id int NOT NULL,
    stamp_description TEXT NULL,
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '0:FALSE, 1:TRUE'
);

CREATE TABLE Members_Quests (
    member_email VARCHAR(40) NOT NULL,
    location_quest_id INT NOT NULL,
    is_completed ENUM('IN_PROGRESS', 'COMPLETED') DEFAULT 'IN_PROGRESS',
    created_at DATETIME DEFAULT NOW()
);