set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER docker;
    CREATE DATABASE dockdatabase;
    GRANT ALL PRIVILEGES ON DATABASE dockdatabase TO docker;
EOSQL