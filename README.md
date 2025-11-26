# WishlistApp
1. Przed uruchomieniem
   1.1. Uzupełnij plik `.env` na podstawie pliku `.example.env`.
   1.2. Upewnij się, że w katalogu z `docker-compose.yml` znajdują się pliki:

    * `docker-compose.yml`
    * `.env` (z wartościami m.in. `AUTH_DB`, `AUTH_DB_USER`, `AUTH_DB_PASSWORD`).

2. Start bazy danych
   2.1. Uruchom bazę z użyciem docker-compose:

   ```bash
   docker compose up -d
   ```

   2.2. Jeśli kontener `auth-db` został wcześniej utworzony, ale jest zatrzymany, możesz go uruchomić:

   ```bash
   docker start auth-db
   ```

3. Sprawdzenie, czy baza działa
   3.1. Lista działających kontenerów:

   ```bash
   docker ps
   ```

   3.2. Sprawdzenie stanu healthcheck kontenera `auth-db`:

   ```bash
   docker inspect auth-db --format='{{json .State.Health}}'
   ```

   3.3. Podgląd logów kontenera:

   ```bash
   docker logs auth-db
   ```

4. Wejście do kontenera i połączenie z bazą
   4.1. Wejście do shella w kontenerze:

   ```bash
   docker exec -it auth-db bash
   ```

   4.2. Połączenie z bazą z poziomu kontenera (dopasuj dane do `.env`):

   ```bash
   psql -U auth_user -d auth_db
   ```

5. Zatrzymanie bazy
   5.1. Zatrzymanie działającego kontenera `auth-db`:

   ```bash
   docker stop auth-db
   ```

   5.2. Zatrzymanie i usunięcie kontenera zdefiniowanego w `docker-compose.yml` (dane w wolumenie pozostają):

   ```bash
   docker compose down
   ```

6. Ponowne uruchomienie po zatrzymaniu
   6.1. Jeśli kontener został tylko zatrzymany:

   ```bash
   docker start auth-db
   ```

   6.2. Jeśli kontener został usunięty komendą `docker compose down`:

   ```bash
   docker compose up -d
   ```

7. Całkowite usunięcie bazy wraz z danymi
   7.1. Zatrzymanie i usunięcie kontenera oraz wolumenów (łącznie z `auth-db-data`):

   ```bash
   docker compose down -v
   ```

   Po tej operacji baza oraz wszystkie dane zostaną usunięte.
