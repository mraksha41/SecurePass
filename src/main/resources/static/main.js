let passwords = [];

async function displayPasswords() {
    try {
        const response = await fetch('http://localhost:8080/credential/list');
        const data = await response.json();
        passwords = data;
    } catch (error) {
        console.error('Error:', error);
    }

    const passwordsBody = document.getElementById("passwordsBody");
    passwordsBody.innerHTML = "";

    passwords.forEach((password, index) => {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${password.name}</td>
        <td>${password.url}</td>
        <td>${password.username}</td>
        <td>
        <input type="password" value="${password.password}" readonly>
        <button onclick="togglePassword(${index})">Show/Hide</button>
        </td>
        <td>
        <button onclick="editPassword(${index},${password.id})">Edit</button>
        <button onclick="deletePassword(${index},${password.id})">Delete</button>
        </td>
    `;
    passwordsBody.appendChild(row);
    });
}
    
async function deletePassword(index, id) {
//    passwords.splice(index, 1);
    try {
        const response = await fetch('http://localhost:8080/credential/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: id,
        });

        const data = await response.json();
        console.log('Success:', data);
    } catch (error) {
        console.error('Error:', error);
    }
    await displayPasswords();
}

async function generateRandomPassword() {
    let password = "";
    try {
        const response = await fetch('http://localhost:8080/credential/generatePassword');
        const data = await response.text();
        console.log("data");
        console.log(data);
        password = data;
        console.log(password);
    } catch (error) {
        console.error('Error:', error);
    }
    return password;
}

function togglePassword(index) {
    const passwordInput = document.querySelector(`#passwordsBody tr:nth-child(${index + 1}) input[type='password'] `);
    if(passwordInput){
        passwordInput.type = passwordInput.type === "password" ? "text" : "password";
    }else{
        const passwordInput = document.querySelector(`#passwordsBody tr:nth-child(${index + 1}) input[type='text'] `);
        passwordInput.type = passwordInput.type === "password" ? "text" : "password";
    }
    // Set a timeout to change it back to type "password" after 3 seconds (for visibility)
    setTimeout(() => {
      passwordInput.type = "password";
    }, 5000); // Change this value to adjust the visibility duration
}

async function checkOldPassword(){
    let password = document.getElementById("passwordInput").value
    let success = false;
    let message = "";

    try {
        console.log("check old password");
        console.log(password);
        const response = await fetch('http://localhost:8080/credential/checkOldPassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: password,
        });

        const data = await response.json();
        console.log('Success:', data);
        return data.message;
    } catch (error) {
        console.error('Error:', error);
    }

    if(success){
        console.log("Successful");
    }else{
        console.log("Failed");
    }
}

async function validatePassword(){
    let password = document.getElementById("passwordInput").value

    try {
        console.log("validate password");
        console.log(password);
        const response = await fetch('http://localhost:8080/credential/validate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: password,
        });

        const data = await response.json();
        console.log('Success:', data);
//        success = data.success;
        return data.message;
    } catch (error) {
        console.error('Error:', error);
    }

    if(success){
        console.log("Successful");
    }else{
        console.log("Failed");
    }
}

async function savePassword(id){
    const passwordData = {
        name: document.getElementById("nameInput").value,
        url: document.getElementById("urlInput").value,
        username: document.getElementById("usernameInput").value,
        password: document.getElementById("passwordInput").value
    };

    if(id != undefined){
        passwordData.id = id;
    }

    try {
        const response = await fetch('http://localhost:8080/credential/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(passwordData),
        });

        const data = await response.json();
        console.log('Success:', data);
    } catch (error) {
        console.error('Error:', error);
    }

    document.getElementById("usernameInput").value = "";
    document.getElementById("passwordInput").value = "";
    document.getElementById("nameInput").value = "";
    document.getElementById("urlInput").value = "";

}

async function editPassword(index, id) {
    const passwordToEdit = passwords[index];
    document.getElementById("nameInput").value = passwordToEdit.name;
    document.getElementById("urlInput").value = passwordToEdit.url;
    document.getElementById("usernameInput").value = passwordToEdit.username;
    document.getElementById("passwordInput").value = passwordToEdit.password;

    const formContainer = document.getElementById("formContainer");
    const passwordTable = document.getElementById("passwordTable");
    formContainer.classList.remove("hidden");
    passwordTable.classList.add("hidden");

    const submitButton = document.getElementById("submitButton");
    submitButton.onclick = async function(event) {
        await submitPassword(id);
    };

    const cancelButton = document.getElementById("cancelButton");
    cancelButton.onclick = function() {
        formContainer.classList.add("hidden");
        passwordTable.classList.remove("hidden");
    };
}

async function submitPassword(id){
    const formContainer = document.getElementById("formContainer");
    const passwordTable = document.getElementById("passwordTable");

    event.preventDefault();
    const enteredPassword = document.getElementById("passwordInput").value;/////////////////
    if(enteredPassword){
        response = await validatePassword(enteredPassword);
        if(response == ""){
            checkOldPasswordResponse = await checkOldPassword();
            if(checkOldPasswordResponse != ""){
                alert(checkOldPasswordResponse);
            }
            await savePassword(id);
            await displayPasswords();
            formContainer.classList.add("hidden");
            passwordTable.classList.remove("hidden");

        }else{
            alert(response
            +"\n"
            +"\nPassword should be at least 8 characters long but not exceed 30 characters,"
             +"\n should not any contain whitespace characters, including spaces, tabs, or newlines,"
              +"\n must contain at least one number,"
              +"\n must include at least one special character");
        }
    }else{
        alert("Please enter a password");
    }
}

document.getElementById("homeButton").addEventListener("click", () => {
    const formContainer = document.getElementById("formContainer");
    const passwordTable = document.getElementById("passwordTable");
    passwordTable.classList.remove("hidden");
    formContainer.classList.add("hidden");

});
  
document.getElementById("addButton").addEventListener("click", () => {
    const formContainer = document.getElementById("formContainer");
    const passwordTable = document.getElementById("passwordTable");
    formContainer.classList.remove("hidden");
    passwordTable.classList.add("hidden");

    const submitButton = document.getElementById("submitButton");
    submitButton.onclick = async function(event) {
        await submitPassword();
    };

    const cancelButton = document.getElementById("cancelButton");
    cancelButton.onclick = function() {
        formContainer.classList.add("hidden");
        passwordTable.classList.remove("hidden");
    };
});

document.getElementById("generateButton").addEventListener("click", async () => {
    const generatedPassword = await generateRandomPassword();
    const passwordInput = document.getElementById("passwordInput");
    passwordInput.value = generatedPassword;
//    passwordInput.type = "text"; // Change the input type temporarily

    // Set a timeout to change it back to type "password" after 3 seconds (for visibility)
    setTimeout(() => {
      passwordInput.type = "password";
    }, 500000); // Change this value to adjust the visibility duration
});
  
displayPasswords();
  