import sqlite3

connection = sqlite3.connect("GP_database.db")
c = connection.cursor()

c.execute("CREATE TABLE IF NOT EXISTS humidity_temperature (time TIMESTAMP, temperature NUMERIC, humidity NUMERIC )")
print("Done")

c.execute("INSERT INTO humidity_temperature VALUES (CURRENT_TIMESTAMP , abs(random() % 11), abs(random() % 12))")


c.execute("SELECT*FROM humidity_temperature")
rows = c.fetchall()
for row in rows:
    for col in row:
        print "%s," % col
    print "\n"