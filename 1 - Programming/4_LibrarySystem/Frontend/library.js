const url = 'http://localhost:8080/api';
const classListString = [
    "dark:bg-slate-900", 
    "my-2",
];

var booksList = document.getElementById("booksList");

async function getAllUsers(){
    // Gets all users and adds them to UsersList
    const temp = await fetch(`${url}/users/getusers`);
    temp.json().then(data => {
        for (let i = 0; i < data.length; i++) {
            addUserToCollection(data[i]);            
        }
    });
}

function addUserToCollection(user) {
    // Creates the div element and adds the classes
    let li = document.createElement("div");
    classListString.forEach((cls) => {
        li.classList.add(cls);
    });
    li.innerText = user.name;

    // Adds the onClick function, so the user can be selected
    li.onclick = () => {
        selectedUser = user;
        for (let l = 0; l < usersList.children.length; l++) {
            const child = usersList.children[l];
            child.classList = classListString.join(" ");
        }
        li.classList.add("outline", "outline-blue-500");
    }
    // Add to UsersList
    usersList.appendChild(li);
}

async function getAllBooks() {
    // Gets all books and adds them to BooksList
    const temp = await fetch(`${url}/books/getavailable`);
    document.getElementById("booksList").innerHTML = '';
    temp.json().then(data => { 
        for (let i = 0; i < data.length; i++) {
            addBookToCollection(data[i]);
        }
    });
}

function addBookToCollection(book) {
    // Creates the div element and adds the classes
    var booksList = document.getElementById("booksList");
    let li = document.createElement("div");
    classListString.forEach((cls) => {
        li.classList.add(cls);
    });
    li.innerText = book.title;

    // Adds the onClick function, so the book can be selected
    li.onclick = () => {
        selectedBook = book;
        for (let l = 0; l < booksList.children.length; l++) {
            const child = booksList.children[l];
            child.classList = classListString.join(" ");
        }
        li.classList.add("outline", "outline-blue-500")
    }
    // Add to BooksList
    booksList.appendChild(li);
}

async function addBook(form) {
    // Posts the values from the add book form
    const temp = await fetch(`${url}/books/add?` + new URLSearchParams({
        title: form.title.value,
        author: form.author.value,
        isbn: form.isbn.value,
        stock: form.stock.value
    }), {method: 'POST'});

    // Affirmation text and adds the newly created book to the BooksList
    temp.json().then(data => {
        document.getElementById("bookOutput").innerText = `${data.title} blev tilføjet!`;
        addBookToCollection(data);
    });
}

async function addUser(form) {
    // Posts the values from the add user form
    const temp = await fetch(`${url}/users/add?` + new URLSearchParams({
        name: form.name.value
    }), {method: 'POST'});

    // Affirmation text and adds the newly created user to the UsersList
    temp.json().then(data => {
        document.getElementById("userOutput").innerText = `${data.name} blev tilføjet!`;
        addUserToCollection(data);
    });
}

async function borrowBook() {
    // Takes the selected user and book and adds it to the User_Book table
    const temp = await fetch(`${url}/books/borrow?` + new URLSearchParams({
        userId: selectedUser.id,
        isbn: selectedBook.isbn
    }), {method: 'POST'});
}

async function returnBook() {
    // Takes the selected user and book and removes it from the User_Book table
    const temp = await fetch(`${url}/books/return?` + new URLSearchParams({
        userId: selectedUser.id,
        isbn: selectedBook.isbn
    }), { method: 'POST' })
}

async function getUsersBooks() {
    // Gets the selected users rented books
    const temp = await fetch(`${url}/users/getbooks?` + new URLSearchParams({
        id: selectedUser.id
    }));

    temp.json().then(data => {
        // booksList = document.getElementById("booksList");
        
        // Empties the BooksList and replaces it with the users rented books
        booksList.innerHTML = '';
        if(data.length > 0){
            for (let i = 0; i < data.length; i++) {
                addBookToCollection(data[i]);
            }
        } else {
            var outputText = document.createElement("p");
            outputText.innerText = `Ingen bøger udlejet til ${selectedUser.name}`
            booksList.appendChild(outputText);
        }
    });
}