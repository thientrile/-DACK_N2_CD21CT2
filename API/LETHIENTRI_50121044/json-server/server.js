// - nhập mô-đun hệ thống tệp .js nút tích hợp để đọc và ghi tệp.
const fs = require("fs");
// - Nhập mô-đun phần mềm trung gian body-parser để phân tích cú pháp các cơ quan yêu cầu đến trong phần mềm trung gian trước khi xử lý các tuyến.
const bodyParser = require("body-parser");
// - nhập mô-đun json-server là một khung nhẹ giúp thiết lập API REST với phụ trợ dựa trên JSON.
const jsonServer = require("json-server");
// - nhập mô-đun jsonwebtoken được sử dụng để tạo và xác minh JSON Web Tokens (JWT) để xác thực và ủy quyền.
const jwt = require("jsonwebtoken");
// tạo một phiên bản JSON Server mới.
const server = jsonServer.create();
// tạo một bộ định tuyến JSON Server mới và trỏ nó đến tệp JSON làm cơ sở dữ liệu.
const router = jsonServer.router("./db.json");
// - đọc tệp JSON và phân tích nội dung của nó thành một đối tượng JavaScript.
const db = JSON.parse(fs.readFileSync("./db.json", "UTF-8"));
// - tạo một tập hợp các hàm middleware mặc định cho JSON Server instance.
const middlewares = jsonServer.defaults();
// - đặt số cổng để lắng nghe biến môi trường PORT hoặc 3000 nếu nó không được xác định.
const PORT = process.env.PORT || 3000;
// - thêm các hàm middleware mặc định vào phiên bản JSON Server.
server.use(middlewares);
//  thêm các hàm phần mềm trung gian JSON Server mặc định vào phiên bản JSON Server.
server.use(jsonServer.defaults());
// sử dụng middleware body-parser để phân tích dữ liệu được gửi từ client và đưa vào đối tượng req.body. Middleware này cho phép đọc dữ liệu được gửi đến server dưới dạng URL-encoded
server.use(bodyParser.urlencoded({ extended: true }));
//  sử dụng middleware body-parser để đọc dữ liệu được gửi đến server dưới dạng JSON và đưa vào đối tượng req.body.
server.use(bodyParser.json());
// Đây là khóa bí mật được sử dụng trong quá trình mã hóa và giải mã token JWT.
const SECRET_KEY = "123456789";
// Đây là thời gian tồn tại của token JWT. Trong trường hợp này, token chỉ tồn tại trong vòng 1 giờ.
const expiresIn = "1h";
// Hàm này tạo ra một token JWT dựa trên thông tin được cung cấp trong đối số payload
function createToken(payload) {
    return jwt.sign(payload, SECRET_KEY, { expiresIn });
}
// Hàm này kiểm tra tính hợp lệ của token JWT được cung cấp trong đối số token. Nếu token hợp lệ, hàm trả về đối tượng được giải mã. Nếu không, hàm trả về một lỗi.
function verifyToken(token) {
    return jwt.verify(token, SECRET_KEY, (err, decode) => (decode !== undefined ? decode : err));
}
// Hàm này kiểm tra xem một người dùng có thể xác thực bằng số điện thoại và mật khẩu được cung cấp. Nếu tìm thấy người dùng tương ứng trong cơ sở dữ liệu db.users, hàm trả về giá trị true. Nếu không, hàm trả về giá trị false.
function isAuthenticated({ phonenumber, password }) {
    return db.users.findIndex((user) => user.phonenumber === phonenumber && user.password === password) !== -1;
}
// Hàm này tạo ra một số ngẫu nhiên trong khoảng từ 0 đến 9999 và trả về giá trị đó
function generateRandomNumber() {
    return Math.floor(Math.random() * 10000);
}
server.use('/users', (req, res, next) => {
    res.status(403).json({});
});
server.use('/orders', (req, res, next) => {
    res.status(403).json({});
});

// đăng ký tài khoản
server.post("/register", (req, res) => {
    // Dòng này giải nén (destructuring) đối tượng req.body và lấy ra các thuộc tính phonenumber, email, fullname và password. Đối tượng req.body là dữ liệu được gửi lên từ client trong yêu cầu POST
    const { phonenumber, email, fullname, password } = req.body;
    // Kiểm tra xem số điện thoại đã được đăng ký trước đó hay chưa bằng cách tìm kiếm trong mảng db.users.
    exist_phonenumber = db.users.findIndex((item) => item.phonenumber === phonenumber)
    // Nếu số điện thoại đã được đăng ký trước đó thì trả về một đối tượng JSON với thông báo lỗi và mã trạng thái 400
    if (exist_phonenumber !== -1) {
        return res.status(400).json({ message: "Phone number already exist" });
    }
    // Kiểm tra xem email đã được đăng ký trước đó hay chưa bằng cách tìm kiếm trong mảng db.users.
    exist_email = db.users.findIndex((item) => item.email === email)
    // Nếu email đã được đăng ký trước đó thì trả về một đối tượng JSON với thông báo lỗi và mã trạng thái 400.
    if (exist_email !== -1) {
        return res.status(400).json({ message: "Email already exist" });
    }
    //  Tạo một đối tượng new_user chứa thông tin của người dùng mới, bao gồm các giá trị id, phonenumber, email, fullname, password, City, District, Wards, và Address
    const new_user = {
        id: generateRandomNumber(),
        phonenumber,
        email,
        fullname,
        password,
        City: "",
        District: "",
        Wards: "",
        Address: ""
    }
    // thêm đối tưởng vào new_user vào db.users
    db.users.push(new_user);
    /* Writing to the file db.json. */
    fs.writeFileSync("./db.json", JSON.stringify(db), () => {
        if (err) return console.log(err);
        console.log("writing to " + fileName);
    });
    /*  */
    res.status(201).json({
        status: 201,
        message: "Success",
        data: new_user,
    });
})
// đăng nhập tài khoản
server.post("/login", (req, res) => {
    // lấy số điện thoại và mật khẩu từ đối tưởng rep.body bằng phương thức post
    const { phonenumber, password } = req.body;
    //  kiểm tra xem số điện thoại và mật khẩu có đúng không  nếu đúng trả về true sai trả vè false và báo lỗi 
    if (isAuthenticated({ phonenumber, password }) === false) {
        const status = 401;
        const message = "Incorrect phonenumber or password";
        res.status(status).json({ status, message });
        return;
    }
    // tạo một token với số điện thoại mà mật khẩu
    const access_token = createToken({ phonenumber, password });
    // giá trị trả về với trạng thái 200 và data là tokens đã tạo ở trên
    res.status(200).json({
        status: 200,
        message: "Success",
        data: {
            access_token,
        },
    });

})
// xác định uỷ quyền

server.use("/account", (req, res, next) => {
    // kiểm tra xem trong header có mã tokes không nếu không có thì sẽ báo lỗi
    if (req.headers.authorization == undefined || req.headers.authorization.split(" ")[0] !== "Bearer") {
        const status = 401;
        const message = "Bad authorization header";
        res.status(status).json({ status, message });
        return;
    }
    try {
        let verifyTokenResult;
        // kiểm tra xme tokens trong header có hợp lễ không
        verifyTokenResult = verifyToken(req.headers.authorization.split(" ")[1]);
        // nêu không hợp lệ trả về status 401 và hiện thị thông báo lỗi
        if (verifyTokenResult instanceof Error) {
            const status = 401;
            const message = "Error: access_token is not valid";
            res.status(status).json({ status, message });
            return;
        }
        next();
    } catch (err) {
        const status = 401;
        const message = "Token has expired";
        res.status(status).json({ status, message });
    }
})
// xem tất cả thông tin người dùng
server.get("/account/profile", (req, res) => {
    // trả về kết quả dưới dạng json và status 200
    res.status(200).json({
        status: 200,
        data: {
            users: db.users
        },
    });
})
// xem thông tin của người dùng đã đăng nhập;
server.post("/account/profile", (req, res) => {
    // thực hiện lấy số điện thoại từ đối tượng req.body được gửi lên bằng phương thức post
    const phonenumber = req.body.phonenumber;
    //   kiểm tra xem số điện thoại có nằm xong mảng db.users không nếu tìm thấy sẽ trả về vị trí của số điện thoại đó không tìm thấy sẽ trả về giá trị -1
    const exist_phonenumber = db.users.findIndex((item) => item.phonenumber === phonenumber)
    if (exist_phonenumber !== -1) {
        // nếu tìm thấy sẽ lấy thông tin của người dùng gán vào biến result
        const result = db.users[exist_phonenumber]
        // tạo biến status gán giá trị bằng 200
        const status = 200;
        // trả về thông tin người dùng sau khi được tìm thấy
        return res.status(status).json({ status, result })

    }
    // trả về status:401 và hiện thị thông báo lỗi
    return res.status(401).json({
        status: 401,
        message: 'Phone number is not found!!'
    })



})
// cập nhật địa chỉ của người dùng
server.patch("/account/profile", (req, res) => {
    // lấy số  điện thoại từ đối tưởng req.body.phonenumber
    const phonenumber = req.body.phonenumber;
    // lấy tên thành phố từ đối tưởng req.body.city
    const city = req.body.city;
    // lấy tên quận từ đối tưởng req.body.district
    const district = req.body.district;
    // lấy tên xã từ đối tưởng req.body.wards
    const wards = req.body.wards;
    // lấy địa chỉ đường từ đối dưởng address
    const address = req.body.address;
    // kiểm tra xem số điện thoại có nằm xong mảng db.users không nếu tìm thấy sẽ trả về vị trí của số điện thoại đó không tìm thấy sẽ
    const exist_phonenumber = db.users.findIndex((item) => item.phonenumber === phonenumber)
    if (exist_phonenumber !== -1) {
        // nếu tìm thấy sẽ lấy thông tin của người dùng gán 
        db.users[exist_phonenumber].City = city;
        db.users[exist_phonenumber].District = district;
        db.users[exist_phonenumber].Wards = wards;
        db.users[exist_phonenumber].Address = address;
        // tiến hành đồng bộ file db.json với đối tưởng db 
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
            console.log("writing to " + fileName);
        });
        // hiện thị thông báo sau khi cập nhật thành công
        res.status(304).json({
            success: "Success",
            user: db.users[exist_phonenumber]
        })

    }
    else {
        // hiện thị thông báo lỗi nếu như không tìm thấy số điện thoại
        res.status(401).json({
            status: 401,
            message: "Phone number not found!!",
        });
    }
})
// thay đổi mật khẩu cho người dùng
server.patch('/account/change-password', (req, res) => {
    // láy số điện thoại từ đối tưởng req.body.phonenumber
    const phonenumber = req.body.phonenumber;
    // lấy mật khẩu mới từ đối tưởng req.body.newPassword
    const newPassword = req.body.newPassword;
    // kiểm tra xem số điền thoại này có tồn tại không
    const exist_phonenumber = db.users.findIndex((item) => item.phonenumber === phonenumber)
    if (exist_phonenumber !== -1) {
        // nếu tồn tại tiến hành quá trình cập nhật lại mật khẩu
        db.users[exist_phonenumber].password = newPassword;
        // tiến hành đồng bộ dữ liệu file db.json với đối ưởng db sau khi được cập nhật
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
        })
        // hiện thị status 304 thông báo cập nhật  thành công và hiện thị thông tin sau khi được cập nhật
        res.status(304).json({
            success: "Success",
            user: db.users[exist_phonenumber]
        })
    } else {
        // hiện thị status 401 thông báo cập nhật thất bại và hiện thị thông báo lỗi
        res.status(401).json({
            status: 401,
            message: "Phone number not found!!",
        });
    }

})
// cập nhật email cho người dùng
server.patch("/account/verify-email", (req, res) => {
    // lấy số điện thoại từ đối tưởng req.body.phonenumber
    const phonenumber = req.body.phonenumber;
    // lấy địa chỉ email mới từ đối tưởng req.body.newEmail
    const newEmail = req.body.newEmail;

    // kiểm tra xem số điện thoại có toàn tại không
    const exist_phonenumber = db.users.findIndex((item) => item.phonenumber === phonenumber)
    if (exist_phonenumber !== -1) {
        // nếu tồn tại tiến hành quá trình cập nhật lại email
        db.users[exist_phonenumber].email = newEmail;
        // tiến hành đồng bộ dữ liệu file db.json với đối ưởng db sau khi được cập nhật
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
        })
        // hiện thị status 304 thông báo cập nhật  thành công và hiện thị thông tin sau khi được cập nhật 
        res.status(304).json({
            success: "Success",
            user: db.users[exist_phonenumber]
        })
    }
    else {
        //hiệnt thông status 401 để thông báo cập nhật thất bại và hiện thị lỗi
        res.status(401).json({
            status: 401,
            message: "Phone number not found!!",
        });
    }
})
// Xoá người dùng theo số điện thoại
server.delete("/account/delete", (req, res) => {
    // lấy số điện thoại từ đối tưởng reb.body.phonenubmer
    const phonenumber = req.body.phonenumber;
    // kiểm tra xem số điện thoại có nằm xong mảng db.users không nếu tìm thấy sẽ trả về vị trí của số điện thoại đó không
    const exist_phonenumber = db.users.findIndex((x) => x.phonenumber == phonenumber)
    if (exist_phonenumber !== -1) {
        // nếu tìm tháy tiến hành xoá đối tưởng trong mảng với splice
        db.users.splice(exist_phonenumber, 1)
        // trả về status 305 thông báo xoá hoàn thất và hiện thị thông báo lỗi
        res.status(305).json({
            status: 305,
            message: "Success",
            data: db.users
        })
    }
    else {
        // hiện thị status 401 để thông báo xoá thất bại và hiện thị lỗi

        res.status(402).json({
            status: 402,
            message: "Phone number not found!!",
        })
    }
})
// Sản phẩm
// lấy toàn bộ sản phẩm
server.get("/products", (req, res) => {
    // trả về thông tin của tất cả sản phảm
    return res.status(203).json({
        status: 203,
        message: "success",
        data: db.products
    })
})
server.get("/products/:id", (req, res) => {
    // lấy id từ req.params.id
    const id = req.params.id;
    // kiêmr tra xem id có tồn tại trong đối tưởng db.products không nếu tìm thấy sẽ trả về vị của nó trong mảng còn không tìm thấy sẽ trả về -1
    const exist_product = db.products.findIndex((item) => item.id == id)
    if (exist_product !== -1) {
        // nếu tìm thấy thị thông tin của sản phẩm có id  hợp lệ
        return res.status(200).json({
            status: 200,
            message: "success",
            data: db.products[exist_product]
        })
    }
    // hiện thông báo lỗi
    return res.status(404).json({
        status: 404,
        message: "Product not found!!",
    })

})
// giỏ hàng
// thêm một sản phẩm vào giỏ hàng
server.post("/cart", (req, res) => {
    // lấy id sản phẩm từ đối tưởng req.body.productId
    const productId = req.body.productId;
    // lấy số lượng sản phẩm từ đối tưởng req.body.quantity
    const quantity = Number(req.body.quantity);
    // kiểm tra xem id có tồn tại trong đối tưởng db.products không
    const exist_product = db.products.findIndex((item) => item.id == productId)
    if (exist_product !== -1) {
        // kiểm tra xem sản phẩm đã tồn tại trong giỏi hàng chưa
        const exist_cart = db.cart.findIndex((item) => item.productId == productId)
        // lấy số lượng  hiện tại của sản phẩm trong giỏ hàng nếu có tồn tại thì trả về số lượng đố
        const quantityOnCart = exist_cart !== -1 ? Number(db.cart[exist_cart].quantity) : 0;
        // nếu sản phẩm đã hết hàng thì không thể thêm vô giỏ hàng được và hiện thị thông báo lỗi
        if (db.products[exist_product].inventory == 0) {
            return res.status(400).json({
                status: 400,
                message: "Product is out of stock"
            })

        }
        // nếu số lượng hàng tồn ít hơn số lượng muốn thêm vào giỏ hàng thì không thể thêm
        else if (db.products[exist_product].inventory < (quantity + quantityOnCart)) {
            return res.status(400).json({
                status: 400,
                message: "Quantity must not be greater than quantity in stock"
            })
        }
        else {
            // nếu hàng hoá đã tồn tại thì cộng dồn thêm số lượng trong giỏ hàng
            if (exist_cart !== -1) {
                db.cart[exist_cart].quantity = quantity + quantityOnCart;
            }
            else {
                // nếu chưa tồn tại trong giỏ hàng
                // tạo một đối tưởng chứa thông tin của thành phần mới trong giỏ hàng
                const newCart = {
                    id: generateRandomNumber(),
                    productId: db.products[exist_product].id,
                    code: db.products[exist_product].code,
                    title: db.products[exist_product].title,
                    price: db.products[exist_product].price,
                    quantity: quantity
                }
                // thêm phần tự mới vào trong mảng db.cart
                db.cart.push(newCart)

            }
            // đồng bộ dữ liệu giữa file db.json và đối tưởng db
            fs.writeFileSync("./db.json", JSON.stringify(db), () => {
                if (err) return console.log(err);
            })
            // thông báo trang thái thêm thành công và hiện thị thông tin của sản phẩm sau khi được vào giỏ hàng
            res.status(200).json({
                success: "Success",
                data: db.cart[db.cart.length - 1]
            })

        }
    }
    else {
        // thông báo trạng thái và thông báo lỗi
        res.status(401).json({
            status: 401,
            message: "No products found!!",
        });
    }
})
// cập nhật số lượng sản phẩm trong giỏ hàng
server.patch("/cart/:id", (req, res) => {
    // lấy id giỏ hàng từ đối tưởng req.params.id
    const id = req.params.id;
    // lấy số lướng mới từ đối tưởng req.body.quantity
    const newQuantity = req.body.quantity;
    // kiểm tra xem id có tồn tại trong đối tưởng db.cart không
    const exist_cart = db.cart.findIndex((item) => item.id == id);

    if (exist_cart !== -1) {
        // nếu tồn tại
        // lấy vị trị sản phẩm trong mảng db.products
        const exist_product = db.products.findIndex((item) => item.id == db.cart[exist_cart].productId);
        // nếu số lượng tồn ít hơn số lượng cập nhật trong giỏ hàng thì sẽ thông  báo lỗi
        if (db.products[exist_product].inventory < (newQuantity)) {
            return res.status(400).json({
                status: 400,
                message: "Quantity must not be greater than quantity in stock"
            })
        } else {
            // nếu số lượng tồn còn nhiều hơn số lượng cập nhật trong giỏ hàng thì tiến hành cập nhật
            db.cart[exist_cart].quantity = newQuantity;
            fs.writeFileSync("./db.json", JSON.stringify(db), () => {
                if (err) return console.log(err);
            })
            // hiện thị thông tin của thành phần mới của giỏ hàng
            res.status(304).json({
                success: "Success",
                data: db.cart[exist_cart]
            })
        }
    }
    else {
        // thông báo trạng thái và thông báo lỗi
        res.status(401).json({
            status: 401,
            message: "Cart not found!!",
        });
    }
})
// xoá toàn bộ giỏ hàng
/* Deleting the cart array in the db.json file. */
server.delete("/cart", (req, res) => {
    
    db.cart = [];
    fs.writeFileSync("./db.json", JSON.stringify(db), () => {
        if (err) return console.log(err);
        console.log("writing to " + fileName);
    });
    return res.status(202).json({
        status: 202,
        message: "Success",
        data: db.cart
    });
})
// xoá giỏ hàng theo id

/* The above code is deleting the cart from the database. */
server.delete("/cart/:id", (req, res) => {
  /* The above code is creating a variable called id and setting it equal to the id parameter in the
  url. */
    const id = req.params.id;
  /* Checking if the item is already in the cart. */
    const exist_cart = db.cart.findIndex((item) => item.id == id);
    /* Deleting the cart from the database. */
    if (exist_cart !== -1) {
        db.cart.splice(exist_cart, 1)
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
            console.log("writing to " + fileName);
        });

        return res.status(202).json({
            status: 202,
            message: "Success",
        });

    }
    else {
        return res.status(401).json({
            status: 401,
            message: "Cart not found!!",
        });
    }
})
/* The above code is creating a checkout route that is used to create a new order. */
server.post("/checkout", (req, res) => {
   /* The above code is declaring variables and assigning them values from the request body. */
    const email = req.body.email
    const phonenumber = req.body.phonenumber
    const fullnames = req.body.fullnames
    const address = req.body.address
    const shipping_fee = req.body.shipping_fee
    const payment = req.body.payment

    if (db.cart.length > 0) {
      
        db.cart.forEach((e) => {
          
            const index = db.products.findIndex((x) => x.id == e.productId)
            if (index !== -1 && db.products[index].inventory - e.quantity > 0) {

                db.products[index].inventory -= e.quantity
            }
        })
        /* Creating a new order object. */
        const newOrder = {
            id: generateRandomNumber(),
            stasus: "success",
            date_created: new Date(),
            Order_info: {
                products: db.cart,
                shipping_fee: shipping_fee

            },
            pay: {
                payments: payment,
                status: "paid",
            },
            Customer_information: {
                phonenumber: phonenumber,
                email: email,
                fullnames: fullnames,
                address: address,

            }


        }
       /* Pushing the newOrder object into the orders array. */
        db.orders.push(newOrder)
       
        
      /* Writing the data to the db.json file. */
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
        })
    //  
        res.status(200).json({
            success: "Success",
            data: db.orders[db.orders.length - 1]
        })
    }
    else {
        res.status(401).json({
            status: 401,
            message: "Cart is empty!!",
        });
    }


})
server.get("/account/orders", (req, res) => {
    res.status(200).json({
        success: "Success",
        data: db.orders
    })
})
server.get("/account/orders/:id", (req, res) => {
    const id = req.params.id;
    const exist_order = db.orders.findIndex((item) => item.id == id);
    res.status(200).json({
        success: "Success",
        data: db.orders[exist_order]
    })
})
// cập nhật thông tin nhận hàng
server.patch("/account/orders/:id", (req, res) => {
    const id = req.params.id;
    const exist_order = db.orders.findIndex((x) => x.id == id)
    if (exist_order !== -1) {
        if (req.body.fullname) {
            console.log("true");
            db.orders[exist_order].Customer_information.fullnames = req.body.fullnames;
        }
        if (req.body.email) {
            db.orders[exist_order].Customer_information.email = req.body.email;
        }
        if (req.body.phonenumber) {
            db.orders[exist_order].Customer_information.phonenumber = req.body.phonenumber;
        }
        if (req.body.address) {
            db.orders[exist_order].Customer_information.address = req.body.address;
        }
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
        })
        res.status(200).json({
            success: "Success",
            data: db.orders[exist_order]
        })


    } else {
        res.status(401).json({
            status: 401,
            message: "Order not found!!",
        });
    }

})
// xoá hoá đơn
server.delete("/account/orders/:id", (req, res) => {
    const id = req.params.id;
    const exist_order = db.orders.findIndex((x) => x.id == id)
    if (exist_order !== -1) {
        db.orders.splice(exist_order, 1)
        fs.writeFileSync("./db.json", JSON.stringify(db), () => {
            if (err) return console.log(err);
        })
        res.status(204).json({
            status: 204,
            message: "Success",
        })
    }
    else {
        res.status(401).json({
            status: 401,
            message: "Order not found!!",
        });
    }
})
server.use(router);
server.listen(PORT, () => {
    console.log("http://localhost:"+PORT);
});
