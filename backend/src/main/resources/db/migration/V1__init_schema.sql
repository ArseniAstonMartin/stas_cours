CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    avatar_url VARCHAR(500),
    provider VARCHAR(20) NOT NULL DEFAULT 'LOCAL',
    provider_id VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    icon_url VARCHAR(500)
);

CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    order_index INTEGER,
    subject_id BIGINT NOT NULL REFERENCES subjects(id) ON DELETE CASCADE,
    parent_topic_id BIGINT REFERENCES topics(id) ON DELETE SET NULL
);

CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    explanation VARCHAR(1000),
    question_type VARCHAR(30) NOT NULL,
    difficulty VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    points INTEGER NOT NULL DEFAULT 1,
    image_url VARCHAR(500),
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE
);

CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    order_index INTEGER,
    question_id BIGINT NOT NULL REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE tests (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    time_limit_minutes INTEGER,
    test_type VARCHAR(30) NOT NULL DEFAULT 'PRACTICE',
    is_adaptive BOOLEAN NOT NULL DEFAULT FALSE,
    question_count INTEGER DEFAULT 20,
    subject_id BIGINT NOT NULL REFERENCES subjects(id) ON DELETE CASCADE,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE test_questions (
    test_id BIGINT NOT NULL REFERENCES tests(id) ON DELETE CASCADE,
    question_id BIGINT NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    PRIMARY KEY (test_id, question_id)
);

CREATE TABLE test_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    test_id BIGINT NOT NULL REFERENCES tests(id) ON DELETE CASCADE,
    score DOUBLE PRECISION,
    max_score DOUBLE PRECISION,
    percentage DOUBLE PRECISION,
    time_spent_seconds INTEGER,
    status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS',
    started_at TIMESTAMP NOT NULL DEFAULT NOW(),
    completed_at TIMESTAMP
);

CREATE TABLE user_answers (
    id BIGSERIAL PRIMARY KEY,
    test_attempt_id BIGINT NOT NULL REFERENCES test_attempts(id) ON DELETE CASCADE,
    question_id BIGINT NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    selected_answer_id BIGINT REFERENCES answers(id) ON DELETE SET NULL,
    text_answer TEXT,
    is_correct BOOLEAN,
    time_spent_seconds INTEGER
);

CREATE TABLE weakness_records (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    total_attempts INTEGER NOT NULL DEFAULT 0,
    correct_attempts INTEGER NOT NULL DEFAULT 0,
    accuracy_rate DOUBLE PRECISION DEFAULT 0,
    weakness_level VARCHAR(20) NOT NULL DEFAULT 'UNKNOWN',
    last_practiced_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, topic_id)
);

CREATE TABLE study_materials (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    material_type VARCHAR(30) NOT NULL,
    external_url VARCHAR(500),
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    order_index INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Indexes for performance
CREATE INDEX idx_topics_subject_id ON topics(subject_id);
CREATE INDEX idx_questions_topic_id ON questions(topic_id);
CREATE INDEX idx_questions_difficulty ON questions(difficulty);
CREATE INDEX idx_answers_question_id ON answers(question_id);
CREATE INDEX idx_test_attempts_user_id ON test_attempts(user_id);
CREATE INDEX idx_test_attempts_test_id ON test_attempts(test_id);
CREATE INDEX idx_test_attempts_status ON test_attempts(status);
CREATE INDEX idx_user_answers_attempt_id ON user_answers(test_attempt_id);
CREATE INDEX idx_weakness_records_user_id ON weakness_records(user_id);
CREATE INDEX idx_study_materials_topic_id ON study_materials(topic_id);
