print('Start #################################################################');

db = db.getSiblingDB('offers_db_dev');
db.createUser(
    {
        user: 'api_user_dev',
        pwd: 'apipwd',
        roles: [{ role: 'readWrite', db: 'offers_db_dev' }],
    },
);
db.createCollection('selection_offers');

print('###########');

db = db.getSiblingDB('offers_db_prod');
db.createUser(
    {
        user: 'api_user_prod',
        pwd: 'apipwd',
        roles: [{ role: 'readWrite', db: 'offers_db_prod' }],
    },
);
db.createCollection('selection_offers');

print('END #################################################################');