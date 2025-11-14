SELECT 'CREATE DATABASE auth_DB'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth_db')\gexec