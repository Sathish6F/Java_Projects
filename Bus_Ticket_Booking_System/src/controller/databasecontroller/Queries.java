package controller.databasecontroller;

public enum Queries {
    CREATE_BUS_INFO("CREATE TABLE IF NOT EXISTS bus_info (id BIGSERIAL NOT NULL,bus_no VARCHAR,driver_id BIGINT,helper_id BIGINT,travels_id BIGINT,total_seats INT,route_id BIGINT,PRIMARY KEY(id),FOREIGN KEY(driver_id) REFERENCES employees(id),FOREIGN KEY(helper_id) REFERENCES employees(id),FOREIGN KEY(travels_id) REFERENCES travels_info(id),FOREIGN KEY(route_id) REFERENCES routes_info(id));"),
    CREATE_TRAVELS_INFO("CREATE TABLE IF NOT EXISTS travels_info (id BIGSERIAL NOT NULL, name VARCHAR(255) NOT NULL,owner_name VARCHAR(50) NOT NULL,address VARCHAR(255),mobile_number VARCHAR(10) NOT NULL,total_buses INT NOT NULL,PRIMARY KEY(id));"),
    CREATE_EMPLOYEE_INFO("CREATE TABLE IF NOT EXISTS employees (id BIGSERIAL NOT NULL,travels_id BIGINT,emp_name VARCHAR(50) NOT NULL,emp_mobile VARCHAR(10) NOT NULL,PRIMARY KEY(id),FOREIGN KEY(travels_id) REFERENCES travels_info(id));"),
    CREATE_ROLES("CREATE TABLE IF NOT EXISTS roles(id Bigserial not null,role varchar(30),primary key(id));"),
    CREATE_PRICE_INFO("create table if not exists price_info(id bigserial,bus_id bigint,seat_type int,price int,primary key(id),foreign key(bus_id) references bus_info(id));"),
    CREATE_USER_CRED("CREATE TABLE IF NOT EXISTS user_credentials(id bigserial not null,user_id bigint not null,user_role bigint not null,mail varchar(100) not null,password varchar(10) not null,primary key(id),foreign key(user_role) references roles(id));"),
    CREATE_PASSENGER_INFO("CREATE TABLE IF NOT EXISTS passenger_info(passenger_id BIGSERIAL NOT NULL,name VARCHAR(100) NOT NULL,age INT NOT NULL,gender VARCHAR NOT NULL,mobile_number VARCHAR(10),address VARCHAR(255),PRIMARY KEY(passenger_id));"),
    CREATE_AVAILABILITY("CREATE TABLE IF NOT EXISTS availability(id BIGSERIAL NOT NULL,bus_id BIGINT,avl_sleeper INT NOT NULL,avl_semiSleeper INT NOT NULL,date DATE,PRIMARY KEY(id),FOREIGN KEY(bus_id) REFERENCES bus_info(id));"),
    CREATE_SEATS("CREATE TABLE IF NOT EXISTS seats(id BIGSERIAL,seat_no VARCHAR(5),bus_id BIGINT,status BOOLEAN DEFAULT FALSE,price DOUBLE PRECISION,gender VARCHAR NOT NULL,date DATE,PRIMARY KEY(id),FOREIGN KEY(bus_id) REFERENCES bus_info(id));"),
    CREATE_CITIES("CREATE TABLE IF NOT EXISTS cities(id BIGSERIAL,city_name VARCHAR(50) NOT NULL,PRIMARY KEY(id));"),
    CREATE_ROUTES("CREATE TABLE IF NOT EXISTS routes_info(id BIGSERIAL,from_ BIGINT,to_ BIGINT,distance INT NOT NULL,PRIMARY KEY(id),FOREIGN KEY(from_) REFERENCES cities(id),FOREIGN KEY(to_) REFERENCES cities(id));"),
    CREATE_STOPPINGS("CREATE TABLE IF NOT EXISTS stoppings(id BIGSERIAL,name VARCHAR(100),city_id BIGINT,PRIMARY KEY(id),FOREIGN KEY(city_id) REFERENCES cities(id));"),
    CREATE_TRIP("CREATE TABLE IF NOT EXISTS trip(id BIGSERIAL,bus_id BIGINT,depature_time TIME,arrival_time TIME,route_id BIGINT, PRIMARY KEY(id),FOREIGN KEY(route_id) REFERENCES routes_info(id),FOREIGN KEY(bus_id) REFERENCES bus_info(id));"),
    CREATE_TICKET("CREATE TABLE IF NOT EXISTS tickets(id BIGSERIAL,passenger_id BIGINT,bus_id BIGINT,price DOUBLE PRECISION,status BOOLEAN,booked_for DATE,PRIMARY KEY(id),FOREIGN KEY(passenger_id) REFERENCES passenger_info(passenger_id),FOREIGN KEY(bus_id) REFERENCES bus_info(id));"),
    CREATE_BOOKED_LIST("CREATE TABLE IF NOT EXISTS booked_tickets(id BIGSERIAL,ticket_id BIGINT,seat_no VARCHAR(5),passenger_name VARCHAR(50),gender VARCHAR(10),age INT, PRIMARY KEY(id),FOREIGN KEY(ticket_id) REFERENCES tickets(id));"),
    CREATE_PAYMENTS("CREATE TABLE IF NOT EXISTS payments(id BIGSERIAL,passenger_id BIGINT,ticket_id BIGINT,price DOUBLE PRECISION NOT NULL,date TIMESTAMP,PRIMARY KEY(id),UNIQUE(ticket_id),FOREIGN KEY(passenger_id) REFERENCES passenger_info(passenger_id),FOREIGN KEY(ticket_id) REFERENCES tickets(id));"),
    CREATE_BOOKINGS("CREATE TABLE IF NOT EXISTS bookings(id BIGSERIAL,passenger_id BIGINT,ticket_id BIGINT,travels_id BIGINT,payment_id BIGINT,date DATE DEFAULT(CURRENT_DATE),PRIMARY KEY(id),FOREIGN KEY(passenger_id) REFERENCES passenger_info(passenger_id),FOREIGN KEY(ticket_id) REFERENCES tickets(id),FOREIGN KEY(travels_id) REFERENCES travels_info(id),FOREIGN KEY(payment_id) REFERENCES payments(id));"),
    CREATE_CANCELS("CREATE TABLE IF NOT EXISTS cancel(id BIGSERIAL,passenger_id BIGINT,ticket_id BIGINT,date TIMESTAMP,PRIMARY KEY(id),FOREIGN KEY(passenger_id) REFERENCES passenger_info(passenger_id),FOREIGN KEY(ticket_id) REFERENCES tickets(id));"),
    CREATE_REVIEWS("CREATE TABLE IF NOT EXISTS reviews(id BIGSERIAL,travels_id BIGINT,bus_id BIGINT,comments VARCHAR(255),PRIMARY KEY(id),FOREIGN KEY(travels_id) REFERENCES travels_info(id),FOREIGN KEY(bus_id) REFERENCES bus_info(id));"),
    CHECK_USER("SELECT user_role,user_id FROM user_credentials WHERE mail = ? AND password = ?"),
    GET_PASSENGER("SELECT passenger_info.passenger_id,passenger_info.name,passenger_info.address,passenger_info.mobile_number,user_credentials.mail,passenger_info.age,passenger_info.gender FROM passenger_info INNER JOIN user_credentials ON passenger_info.passenger_id=?;"),
    GET_TRAVELS("SELECT travels_info.id,travels_info.name,travels_info.address,travels_info.mobile_Number,user_credentials.mail,travels_info.owner_name,travels_info.total_buses FROM travels_info INNER JOIN user_credentials ON travels_info.id=?;"),
    GET_CITY_ID("SELECT id FROM cities WHERE city_name=?"),
    GET_ROUTE_ID("SELECT id FROM routes_info WHERE from_=? AND to_=?"),
    GET_BUSES("SELECT \n" +
            "    bus_info.id,\n" +
            "    bus_info.bus_no,\n" +
            "    travels_info.name,\n" +
            "    availability.avl_sleeper,\n" +
            "    availability.avl_semisleeper,\n" +
            "    bus_info.route_id,\n" +
            "    price_info_sleeper.price AS sleeper_price,\n" +
            "    price_info_semisleeper.price AS semisleeper_price\n" +
            "FROM \n" +
            "    bus_info\n" +
            "INNER JOIN \n" +
            "    travels_info ON bus_info.travels_id = travels_info.id\n" +
            "INNER JOIN \n" +
            "    availability ON bus_info.id = availability.bus_id \n" +
            "LEFT JOIN \n" +
            "    price_info AS price_info_sleeper ON bus_info.id = price_info_sleeper.bus_id AND price_info_sleeper.seat_type = 1\n" +
            "LEFT JOIN \n" +
            "    price_info AS price_info_semisleeper ON bus_info.id = price_info_semisleeper.bus_id AND price_info_semisleeper.seat_type = 2\n" +
            "WHERE \n" +
            "    bus_info.route_id = ? \n" +
            "    AND availability.date = ?::DATE;\n"),
    GET_BUS_EMPLOYEES("SELECT employees.id,employees.emp_name,employees.emp_mobile,bus_info.id FROM bus_info INNER JOIN employees ON bus_info.travels_id=employees.travels_id WHERE bus_info.id=?;"),
    GET_BUS("SELECT id,bus_no,total_seats FROM bus_info WHERE id=?;"),
    GET_BUS_INFO("SELECT bus_info.id,\n" +
            "       bus_info.bus_no,\n" +
            "       trip.depature_time,\n" +
            "       trip.arrival_time,\n" +
            "       from_city.city_name AS departure_city,\n" +
            "       to_city.city_name AS arrival_city\n" +
            "FROM bus_info\n" +
            "INNER JOIN trip ON bus_info.id = trip.bus_id\n" +
            "INNER JOIN routes_info ON bus_info.route_id = routes_info.id\n" +
            "JOIN cities AS from_city ON from_city.id = routes_info.from_\n" +
            "JOIN cities AS to_city ON to_city.id = routes_info.to_ WHERE bus_info.id=?;\n"),
    GET_PASSENGES("SELECT passenger_name,age,gender,seat_no FROM booked_tickets WHERE ticket_id=?;"),
    GET_TICKET_PRICE("SELECT price FROM tickets WHERE id=?;"),
    GET_BOOKING_DATE("SELECT date FROM payments WHERE ticket_id=?;"),
    GET_BUS_ID("SELECT bus_id FROM tickets WHERE id=?;"),
    GET_SEATS_TO_CANCEL("SELECT seat_no,gender FROM booked_tickets WHERE ticket_id=?;"),
    GET_ALL_SEATS("SELECT id,seat_no,gender FROM seats WHERE bus_id=? AND date=?::DATE;"),
    GET_TRIP("SELECT depature_time,arrival_time,route_id FROM trip WHERE bus_id=?"),
    GET_STOPPINGS("SELECT id,name FROM stoppings WHERE city_id=?;"),
    GET_STOP("SELECT name FROM stoppings WHERE id=?;"),
    GET_SEAT_PRICE("SELECT price FROM price_info WHERE bus_id=? AND seat_type=?;"),
    GET_LAST_TICKET("SELECT * FROM tickets ORDER BY id DESC LIMIT 1;"),
    GET_TRAVELS_NAME("SELECT travels_info.name,bus_info.id FROM bus_info INNER JOIN travels_info ON bus_info.travels_id=travels_info.id and bus_info.id=?;"),
    GET_TRAVELS_ID("SELECT travels_id FROM bus_info WHERE id=?;"),
    GET_BOOKED_FOR("SELECT booked_for FROM tickets WHERE id=?"),
    UPDATE_AVAILABILITY("UPDATE availability SET avl_sleeper=avl_sleeper-?,avl_semisleeper=avl_semisleeper-? WHERE bus_id=? AND date=?::DATE"),
    UPDATE_SEATS("INSERT INTO seats(seat_no,bus_id,status,price,gender,date) VALUES(?,?,?,?,?,?::DATE);"),
    UPDATE_PAYMENT("INSERT INTO payments(passenger_id,ticket_id,price,date) VALUES(?,?,?,?::TIMESTAMP) RETURNING id;"),
    UPDATE_BOOKINGS("INSERT INTO bookings(passenger_id,ticket_id,travels_id,payment_id) VALUES(?,?,?,?);"),
    UPDATE_BOOKED_TICKETS("INSERT INTO booked_tickets(ticket_id,seat_no,passenger_name,gender,age) VALUES(?,?,?,?,?)"),
    UPDATE_TICKET("INSERT INTO tickets(passenger_id,bus_id,price,status,booked_for) VALUES(?,?,?,true,?::DATE)"),
    UPDATE_SEAT_COUNT("UPDATE availability SET avl_sleeper=avl_sleeper+?,avl_semisleeper=avl_semisleeper+? WHERE bus_id=? AND date=?::DATE"),
    UPDATE_CANCEL("INSERT INTO cancel(passenger_id,ticket_id,date) VALUES(?,?,?::TIMESTAMP)"),
    UPDATE_TICKET_STATUS("UPDATE tickets SET status=false WHERE id=?"),
    REMOVE_FROM_BOOKED_TICKETS("DELETE FROM booked_tickets WHERE ticket_id=?"),
    REMOVE_SEATS("DELETE FROM seats WHERE seat_no=? AND bus_id=? AND date=?::DATE"),
    CHECK_TICKET("SELECT * FROM tickets WHERE id=?"),
    CHECK_TICKET_STATUS("SELECT * FROM tickets WHERE id=? AND status=true"),
    EXISTING_USER_CHECK("SELECT mail,password FROM user_credentials WHERE mail=? OR password=?;"),
    UPDATE_PASSENGER_INFO("INSERT INTO passenger_info(name,age,gender,mobile_number,address) VALUES(?,?,?,?,?) RETURNING passenger_id;"),
    UPDATE_PASSENGER_CRED("INSERT INTO user_credentials(user_id,user_role,mail,password) VALUES(?,2,?,?);"),
    UPDATE_EMPLOYEE("INSERT INTO employees(travels_id,emp_name,emp_mobile) VALUES(?,?,?) RETURNING id;"),
    UPDATE_TRIPS("INSERT INTO trip(bus_id,depature_time,arrival_time,route_id) VALUES(?,?,?,?)"),
    UPDATE_CITIES("WITH inserted AS (\n" +
            "    INSERT INTO public.cities (city_name)\n" +
            "    SELECT ? \n" +
            "    WHERE NOT EXISTS (\n" +
            "        SELECT 1 \n" +
            "        FROM public.cities \n" +
            "        WHERE city_name = ?\n" +
            "    )\n" +
            "    RETURNING id\n" +
            ")\n" +
            "SELECT id FROM inserted\n" +
            "UNION ALL\n" +
            "SELECT id FROM public.cities WHERE city_name = ? LIMIT 1;\n"),
    UPDATE_ROUTE_INFO("INSERT INTO routes_info(from_,to_,distance) VALUES(?,?,?) RETURNING id;"),
    UPDATE_PRICE_INFO("INSERT INTO price_info(bus_id,seat_type,price) VALUES(?,?,?)"),
    UPDATE_BUS_INFO("INSERT INTO bus_info(bus_no,driver_id,helper_id,travels_id,total_seats,route_id) VALUES(?,?,?,?,?,?) RETURNING id"),
    EDIT_USER_NAME("UPDATE passenger_info SET name=? WHERE passenger_id=?"),
    EDIT_USER_PASSWORD("UPDATE user_credentials SET password=? WHERE user_id=? AND user_role=?"),
    EDIT_PASSENGER_MOBILE("UPDATE passenger_info SET mobile_number=? WHERE passenger_id=?"),
    EDIT_PASSENGER_ADDRESS("UPDATE passenger_info SET address=? WHERE passenger_id=?"),
    EDIT_TRAVELS_NAME("UPDATE travels_info SET name=? WHERE id=?"),
    EDIT_TRAVELS_ADDRESS("UPDATE travels_info SET address=? WHERE id=?"),
    EDIT_TRAVELS_MOBILE("UPDATE travels_info SET mobile_number=? WHERE id=?"),
    GET_UPCOMMING_TRIPS("SELECT t.id, t.passenger_id, t.bus_id, t.price, t.status, t.booked_for, \n" +
            "       b.bus_no, ti.name AS travels_name, \n" +
            "       fc.city_name AS from_city, \n" +
            "       tc.city_name AS to_city\n" +
            "FROM tickets t\n" +
            "JOIN bus_info b ON t.bus_id = b.id\n" +
            "JOIN travels_info ti ON b.travels_id = ti.id\n" +
            "JOIN routes_info ri ON b.route_id = ri.id\n" +
            "JOIN cities fc ON ri.from_ = fc.id\n" +
            "JOIN cities tc ON ri.to_ = tc.id\n" +
            "WHERE t.passenger_id = ?\n" +
            "  AND t.booked_for >= CURRENT_DATE\n" +
            "  AND t.status = 't'\n" +
            "ORDER BY t.booked_for ASC;\n"),
    AVAILABILITY_FUNCTION("CREATE OR REPLACE FUNCTION insert_availability_for_new_bus()\n" +
            "RETURNS TRIGGER AS $$\n" +
            "BEGIN\n" +
            "    -- Insert for the current date\n" +
            "    INSERT INTO availability (bus_id, avl_sleeper, avl_semisleeper, date)\n" +
            "    VALUES (NEW.id, 18, 30, CURRENT_DATE);\n" +
            "\n" +
            "    -- Insert for the next day\n" +
            "    INSERT INTO availability (bus_id, avl_sleeper, avl_semisleeper, date)\n" +
            "    VALUES (NEW.id, 18, 30, CURRENT_DATE + INTERVAL '1 day');\n" +
            "\n" +
            "    RETURN NEW;\n" +
            "END;\n" +
            "$$ LANGUAGE plpgsql;\n"),
    TRIGGER("CREATE TRIGGER after_bus_insert\n" +
            "AFTER INSERT ON bus_info\n" +
            "FOR EACH ROW\n" +
            "EXECUTE FUNCTION insert_availability_for_new_bus();\n"),
    VIEW_ALL_BUSES("SELECT\n" +
            "    bus_info.id,\n" +
            "    bus_info.bus_no,\n" +
            "    availability.date,\n" +
            "    (availability.avl_sleeper + availability.avl_semisleeper) AS available_seats,\n" +
            "    bus_info.total_seats,\n" +
            "    cities.city_name AS from_city,\n" +
            "    to_cities.city_name AS to_city\n" +
            "FROM\n" +
            "    bus_info\n" +
            "INNER JOIN\n" +
            "    availability ON availability.bus_id = bus_info.id\n" +
            "INNER JOIN\n" +
            "    routes_info ON bus_info.route_id = routes_info.id\n" +
            "JOIN\n" +
            "    cities ON routes_info.from_ = cities.id\n" +
            "JOIN\n" +
            "    cities AS to_cities ON routes_info.to_ = to_cities.id\n" +
            "WHERE\n" +
            "    bus_info.travels_id = ? AND availability.date >= CURRENT_DATE\n" +
            "ORDER BY\n" +
            "    availability.date ASC;\n"),
    GET_ALL_BUSES("SELECT\n" +
            "    bus_info.bus_no,\n" +
            "    bus_info.id AS bus_id,\n" +
            "    driver.emp_name AS driver_name,\n" +
            "    helper.emp_name AS helper_name,\n" +
            "    from_city.city_name AS from_city,\n" +
            "    to_city.city_name AS to_city\n" +
            "FROM\n" +
            "    bus_info\n" +
            "JOIN\n" +
            "    employees AS driver ON bus_info.driver_id = driver.id\n" +
            "JOIN\n" +
            "    employees AS helper ON bus_info.helper_id = helper.id\n" +
            "JOIN\n" +
            "    routes_info ON bus_info.route_id = routes_info.id\n" +
            "JOIN\n" +
            "    cities AS from_city ON routes_info.from_ = from_city.id\n" +
            "JOIN\n" +
            "    cities AS to_city ON routes_info.to_ = to_city.id\n" +
            "WHERE\n" +
            "    bus_info.travels_id = ?;\n"),
    EDIT_PRICE_INFO("UPDATE price_info SET price=? WHERE bus_id=? AND seat_type=?");

    private final String query;
    Queries(String query){
        this.query = query;
    }
    public String getQuery(){
        return this.query;
    }
}
//