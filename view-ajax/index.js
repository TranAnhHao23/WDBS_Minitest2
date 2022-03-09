let pageNumber = 0;

function getAllProduct() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products`,
        success: function (products) {
            let content = '<tr>\n' +
                '<th> Product Name</th>\n' +
                '<th>Price</th>\n' +
                '<th>Category</th>' +
                '<th>Image</th>' +
                '<th>Description</th>' +
                '<th colspan="2">Action</th></tr>'
            for (let i = 0; i < products.content.length; i++) {
                content += detailProduct(products.content[i]);
            }
            document.getElementById('tableProduct').innerHTML = content;
            document.getElementById("formCreate").hidden = true;
        }
    })
}

function detailProduct(product) {
    return `<tr><td>${product.name}</td>
            <td>${product.price}</td>
            <td >${product.category.nameCategory}</td>
            <td>${product.description}</td>
            <td><img src="${product.imageUrl}" alt="error" style="height: 100px; width: 100px"></td>
            <td><button class="btn btn-danger" onclick="deleteProduct(${product.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="edit(${product.id})">Edit</button></td></tr>`;
}

function deleteProduct(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/products/${id}`,
        success: function () {
            getAllProduct();
        }
    })
}

function edit(id) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products/${id}`,
        success: function (product) {
            $('#name').val(product.name)
            $('#price').val(product.price)
            $('#category').val(product.category.idCategory)
            // $('#imageFile').
            $('#description').val(product.description)
            updateProduct(id);
            document.getElementById("formCreate").hidden = false;
            document.getElementById("form-button").onclick = function () {
                updateProduct(id);
            }
        }
    })
}

function updateProduct(id) {
    let name = $('#name').val()
    let price = $('#price').val()
    let categoryId = $('#category').val()
    let description = $('#description').val()
    let newProduct = {
        name: name,
        price: price,
        description: description,
        category: {
            idCategory: categoryId,
        },
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(newProduct),
        url: `http://localhost:8080/products/${id}`,
        success: function () {
            getAllProduct();
        }
    })
    event.preventDefault();
}

function getAllCategory() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/categories`,

        success: function (categories) {
            let content = '<optgroup label="Select Category">\n' +
                '<option value="0">All Category</option>'
            for (let i = 0; i < categories.length; i++) {
                content += detailCategory(categories[i]);
            }
            content += '</optgroup>'
            document.getElementById("categoryList").innerHTML = content; // tại sao ở đây chỉ nhận dấu ngoặc đơn nhỉ ???
            document.getElementById("formCreateCategory").hidden = true;
        }
    })
}

function detailCategory(category) {
    return `<option value="${category.idCategory}">${category.nameCategory}</option>`;
}

function paging() {

    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products`,
        success: function (products) {
            document.getElementById("paging").innerHTML = `${pageNumber + 1} / ${products.totalPages}`;
        }
    })
}

function previous() {
    let search = document.getElementById("search").value;
    let firstPrice = document.getElementById("firstPrice").value;
    let secondPrice = document.getElementById("secondPrice").value;
    let idCategory = document.getElementById("categoryList").value;
    pageNumber--;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products/searchFull?search=${search}&firstPrice=${firstPrice}&secondPrice=${secondPrice}&idCategory=${idCategory}&page=${pageNumber}`,
        success: function (products) {
            let content = '<tr>\n' +
                '<th> Product Name</th>\n' +
                '<th>Price</th>\n' +
                '<th>Category</th>' +
                '<th>Image</th>' +
                '<th>Description</th>' +
                '<th colspan="2">Action</th></tr>'
            for (let i = 0; i < products.content.length; i++) {
                content += detailProduct(products.content[i]);
            }
            if (products.pageable.pageNumber === 0) {
                document.getElementById("previous").hidden = true;
            }
            // paging();
            pagingContent(products);
            document.getElementById("next").hidden = false;
            document.getElementById('tableProduct').innerHTML = content;
            document.getElementById("formCreate").hidden = true;
        }
    })
}

function next() {
    let search = document.getElementById("search").value;
    let firstPrice = document.getElementById("firstPrice").value;
    let secondPrice = document.getElementById("secondPrice").value;
    let idCategory = document.getElementById("categoryList").value;
    pageNumber++;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products/searchFull?search=${search}&firstPrice=${firstPrice}&secondPrice=${secondPrice}&idCategory=${idCategory}&page=${pageNumber}`,
        success: function (products) {
            let content = '<tr>\n' +
                '<th> Product Name</th>\n' +
                '<th>Price</th>\n' +
                '<th>Category</th>' +
                '<th>Image</th>' +
                '<th>Description</th>' +
                '<th colspan="2">Action</th></tr>'
            for (let i = 0; i < products.content.length; i++) {
                content += detailProduct(products.content[i]);
            }

            if (pageNumber === products.totalPages - 1) {
                document.getElementById("next").hidden = true;
            }
            // paging();
            pagingContent(products);
            document.getElementById("previous").hidden = false;
            document.getElementById('tableProduct').innerHTML = content;
            document.getElementById("formCreate").hidden = true;
        }
    })
}


function searchProduct() {
    let search = document.getElementById("search").value;
    let firstPrice = document.getElementById("firstPrice").value;
    let secondPrice = document.getElementById("secondPrice").value;
    let idCategory = document.getElementById("categoryList").value;

    $.ajax({
        type: "GET",
        url: `http://localhost:8080/products/searchFull?search=${search}&firstPrice=${firstPrice}&secondPrice=${secondPrice}&idCategory=${idCategory}`,
        success: function (products) {
            let content = '<tr>\n' +
                '<th> Product Name</th>\n' +
                '<th>Price</th>\n' +
                '<th>Category</th>' +
                '<th>Image</th>' +
                '<th>Description</th>' +
                '<th colspan="2">Action</th></tr>'
            for (let i = 0; i < products.content.length; i++) {
                content += detailProduct(products.content[i]);
            }
            pagingContent(products);

            document.getElementById('tableProduct').innerHTML = content; // tại sao ở đây chỉ nhận dấu ngoặc đơn nhỉ ???
            document.getElementById("formCreate").hidden = true;
        }
    })
    event.preventDefault();
}

function pagingContent(products) {
    document.getElementById("paging").hidden = true;
    document.getElementById("pagingSearch").hidden = false;
    return document.getElementById("pagingSearch").innerHTML = `${pageNumber + 1} / ${products.totalPages}`;
}

getAllProduct()
getAllCategory()
paging()