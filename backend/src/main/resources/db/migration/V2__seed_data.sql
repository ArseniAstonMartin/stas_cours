-- Seed subjects
INSERT INTO subjects (name, description) VALUES
('Математика', 'Подготовка к ЦТ/ЦЭ по математике'),
('Физика', 'Подготовка к ЦТ/ЦЭ по физике'),
('Русский язык', 'Подготовка к ЦТ/ЦЭ по русскому языку'),
('Белорусский язык', 'Подготовка к ЦТ/ЦЭ по белорусскому языку'),
('Английский язык', 'Подготовка к ЦТ/ЦЭ по английскому языку'),
('Биология', 'Подготовка к ЦТ/ЦЭ по биологии');

-- Seed topics for Math
INSERT INTO topics (name, description, order_index, subject_id) VALUES
('Алгебра', 'Алгебраические выражения и уравнения', 1, 1),
('Геометрия', 'Планиметрия и стереометрия', 2, 1),
('Тригонометрия', 'Тригонометрические функции и уравнения', 3, 1),
('Функции и графики', 'Исследование функций', 4, 1),
('Производная', 'Дифференцирование и его применение', 5, 1);

-- Seed topics for Physics
INSERT INTO topics (name, description, order_index, subject_id) VALUES
('Механика', 'Кинематика и динамика', 1, 2),
('Электричество', 'Электростатика и постоянный ток', 2, 2),
('Оптика', 'Геометрическая и волновая оптика', 3, 2);

-- Seed topics for Russian
INSERT INTO topics (name, description, order_index, subject_id) VALUES
('Орфография', 'Правописание', 1, 3),
('Пунктуация', 'Знаки препинания', 2, 3),
('Морфология', 'Части речи', 3, 3);

-- Seed sample questions for Math - Algebra
INSERT INTO questions (text, explanation, question_type, difficulty, points, topic_id) VALUES
('Решите уравнение: 2x + 5 = 15', 'Перенесём 5 в правую часть: 2x = 10, x = 5', 'SINGLE_CHOICE', 'EASY', 1, 1),
('Найдите значение выражения: (a + b)² при a = 3, b = 4', 'По формуле квадрата суммы: (3+4)² = 49', 'SINGLE_CHOICE', 'EASY', 1, 1),
('Решите систему уравнений: x + y = 10, x - y = 4', 'Складывая уравнения: 2x = 14, x = 7, y = 3', 'SINGLE_CHOICE', 'MEDIUM', 2, 1),
('Разложите на множители: x² - 9', 'Формула разности квадратов: (x-3)(x+3)', 'SINGLE_CHOICE', 'EASY', 1, 1),
('Найдите корни уравнения: x² - 5x + 6 = 0', 'Дискриминант D = 25-24 = 1, x₁ = 3, x₂ = 2', 'SINGLE_CHOICE', 'MEDIUM', 2, 1),
('Упростите выражение: (2x³)² / x⁴', 'Ответ: 4x²', 'TEXT_INPUT', 'MEDIUM', 2, 1),
('Решите неравенство: 3x - 7 > 2', 'x > 3', 'SINGLE_CHOICE', 'EASY', 1, 1),
('Найдите область определения функции f(x) = √(x - 3)', 'x ≥ 3 или [3; +∞)', 'SINGLE_CHOICE', 'MEDIUM', 2, 1);

-- Answers for question 1
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('x = 3', FALSE, 1, 1),
('x = 5', TRUE, 2, 1),
('x = 10', FALSE, 3, 1),
('x = 7', FALSE, 4, 1);

-- Answers for question 2
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('25', FALSE, 1, 2),
('49', TRUE, 2, 2),
('12', FALSE, 3, 2),
('24', FALSE, 4, 2);

-- Answers for question 3
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('x = 7, y = 3', TRUE, 1, 3),
('x = 5, y = 5', FALSE, 2, 3),
('x = 8, y = 2', FALSE, 3, 3),
('x = 6, y = 4', FALSE, 4, 3);

-- Answers for question 4
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('(x-3)(x+3)', TRUE, 1, 4),
('(x-9)(x+1)', FALSE, 2, 4),
('(x-3)²', FALSE, 3, 4),
('x(x-9)', FALSE, 4, 4);

-- Answers for question 5
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('x = 2, x = 3', TRUE, 1, 5),
('x = 1, x = 6', FALSE, 2, 5),
('x = -2, x = -3', FALSE, 3, 5),
('x = 5, x = 1', FALSE, 4, 5);

-- Answers for question 7
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('x > 3', TRUE, 1, 7),
('x > 1', FALSE, 2, 7),
('x < 3', FALSE, 3, 7),
('x > 5', FALSE, 4, 7);

-- Answers for question 8
INSERT INTO answers (text, is_correct, order_index, question_id) VALUES
('[3; +∞)', TRUE, 1, 8),
('(-∞; 3]', FALSE, 2, 8),
('(3; +∞)', FALSE, 3, 8),
('(-∞; +∞)', FALSE, 4, 8);

-- Seed a sample practice test
INSERT INTO tests (title, description, time_limit_minutes, test_type, is_adaptive, question_count, subject_id)
VALUES ('Алгебра: Базовый тест', 'Проверка базовых знаний по алгебре', 30, 'PRACTICE', FALSE, 5, 1);

INSERT INTO test_questions (test_id, question_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- Seed study materials
INSERT INTO study_materials (title, content, material_type, topic_id, order_index) VALUES
('Линейные уравнения', 'Линейное уравнение — уравнение вида ax + b = 0, где a ≠ 0. Решение: x = -b/a.', 'THEORY', 1, 1),
('Квадратные уравнения', 'Квадратное уравнение ax² + bx + c = 0. Дискриминант D = b² - 4ac. Если D > 0, два корня; D = 0, один корень; D < 0, нет действительных корней.', 'THEORY', 1, 2),
('Формулы сокращённого умножения', '(a+b)² = a² + 2ab + b²\n(a-b)² = a² - 2ab + b²\n(a+b)(a-b) = a² - b²', 'FORMULA', 1, 3);
