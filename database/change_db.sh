psql -h localhost -U postgres -d martadb -f db_change.sql
psql -h localhost -U postgres -d martadb -f insert_train_data.sql
psql -h localhost -U postgres -d martadb -f create_travel_time_tables.sql