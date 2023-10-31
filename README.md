Create / add orders - POST /store/api/orders request example:
{
"customerId": "2",
"products": [
"Alan Wake 2",
"Civilization IV"
]
}

List orders - GET /store/api/orders/{customerId} customerId - optional path variable for admins only

Remove orders - DELETE /store/api/orders/{orderId} orderId - path variable(available for admin only)

Startup rules:
I don't know why, but db init script doesn't work on startup, so you need to run it manually. Proper file located here
src/main/resources/database/mongo-init.js After that you can run application and use it. Please note that you need
docker installed and running on your machine. Also note that at the initial database is empty, so you need to create
some orders first.
