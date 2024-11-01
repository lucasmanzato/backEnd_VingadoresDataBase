-- Cria um usuário 'admin' com uma senha
CREATE USER admin WITH PASSWORD 'adm123';

-- Cria um usuário 'user1' com uma senha
CREATE USER user1 WITH PASSWORD '123';

-- Conceder permissões ao usuário 'admin'
GRANT ALL PRIVILEGES ON DATABASE VingadoresDB TO admin;

-- Conceder permissões específicas ao usuário 'user1'
GRANT CONNECT ON DATABASE VingadoresDB TO user1;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO user1;

GRANT ALL PRIVILEGES ON SCHEMA public TO postgres;
ALTER ROLE postgres WITH CREATEDB;
