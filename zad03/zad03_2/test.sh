echo "TEST 1 powodzenie"
curl -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{
	"username": "u_1",
	"password": "p_1"
}'

echo
echo

echo "TEST 2 powodzenie"
curl -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{
	"username": "u_3",
	"password": "p_3"
}'

echo
echo

echo "TEST 3 niepowodzenie"
curl -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{
	"username": "blabla",
	"password": "blablabla"
}'