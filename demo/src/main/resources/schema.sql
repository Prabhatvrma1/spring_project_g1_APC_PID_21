-- Drop existing tables if they exist
DROP TABLE IF EXISTS talent;
DROP TABLE IF EXISTS `user`;

-- User table with all resume fields including skills
CREATE TABLE `user` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    linkedin VARCHAR(255),
    website VARCHAR(255),
    summary TEXT,
    experience TEXT,
    education TEXT,
    certifications TEXT,
    projects TEXT,
    skills TEXT
);

-- Talent table
CREATE TABLE talent (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    skills TEXT NOT NULL
);

-- Insert sample data for testing
INSERT INTO `user` (username, email, password, full_name, phone, address, summary, skills) VALUES 
('preet', 'preet@example.com', 'password', 'Preet', '+1-234-567-8900', '123 Main St, City, State', 'Software developer with Java and web development experience', 'Java,Spring Boot,HTML,CSS,JavaScript');

