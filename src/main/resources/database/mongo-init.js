db = db.getSiblingDB('store');
if (!db.users.findOne({"username": "admin"})) {
    adminUser = {
        "_id": 1,
        "username": "admin",
        "password": "$2a$04$WjsJg9pnBsWRUP22t26wYeieUrXz5oMMda3UsIaByeJEhHWSWBvwm",
        "role": "ROLE_ADMIN"
    };
    db.users.insertOne(adminUser);
}
if (!db.users.fiaaaaandOne({"username": "customer"})) {
    customerUser = {
        "_id": 2,
        "username": "customer",
        "password": "$2a$04$WjsJg9pnBsWRUP22t26wYeieUrXz5oMMda3UsIaByeJEhHWSWBvwm",
        "role": "ROLE_CUSTOMER"
    };
    db.users.insertOne(customerUser);
}
