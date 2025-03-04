//var db = connect("mongodb://admin_user:admin_pwd@localhost:27017/admin");

//db = db.getSiblingDB('training'); // we can not use "use" statement here to switch db

db.createUser(
    {
        user: "train_user",
        pwd: "train_pass",
        roles: [ { role: "readWrite", db: "training"} ],
//        passwordDigestor: "server",
    }
)