let passwords = [];

async function displayPasswords() {
    try {
        const response = await fetch('http://localhost:8080/credential/list');
        const data = await response.json();
        console.log("data");
        console.log(data);
        passwords = data;
        console.log("passwords");
        console.log(passwords);
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
  

function togglePassword(index) {
    const passwordInput = document.querySelector(`#passwordsBody tr:nth-child(${index + 1}) input[type='password']`);
    
    passwordInput.type = passwordInput.type === "password" ? "text" : "password";
    
}

// function togglePassword(index) {
//     const passwordInput = document.querySelector(`#passwordsBody tr:nth-child(${index + 1}) input[type='password']`);
  
//     passwordInput.toggleAttribute('type', passwordInput.getAttribute('type') === 'password' ? 'text' : 'password');
// }

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
                body: JSON.stringify({ password: password }),
            });

            const data = await response.json();
            console.log('Success:', data);
            success = data.success;
            message = data.message;
        } catch (error) {
            console.error('Error:', error);
        }

        if(success){
            console.log("Successful");
        }else{
            console.log("Failed");
        }
}

async function evaluatePassword(){
    let password = document.getElementById("passwordInput").value
    let success = false;
    let message = "";

    try {
        console.log("evaluate password");
        console.log(password);
        const response = await fetch('http://localhost:8080/credential/evaluate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ password: password }),
        });

        const data = await response.json();
        console.log('Success:', data);
        success = data.success;
        message = data.message;
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
      event.preventDefault();
       await savePassword(id);
       await displayPasswords();
      formContainer.classList.add("hidden");
      passwordTable.classList.remove("hidden");
    };
  
    const cancelButton = document.getElementById("cancelButton");
    cancelButton.onclick = function() {
      formContainer.classList.add("hidden");
      passwordTable.classList.remove("hidden");
    };
  }

  document.getElementById("homeButton").addEventListener("click", () => {
      const formContainer = document.getElementById("formContainer");
      const passwordTable = document.getElementById("passwordTable");
      passwordTable.classList.remove("hidden");
      formContainer.classList.add("hidden");

    //Need to do the remaining
    });
  
  document.getElementById("addButton").addEventListener("click", () => {
    const formContainer = document.getElementById("formContainer");
    const passwordTable = document.getElementById("passwordTable");
    formContainer.classList.remove("hidden");
    passwordTable.classList.add("hidden");
  
    const submitButton = document.getElementById("submitButton");
    submitButton.onclick = async function(event) {
      event.preventDefault();
      await checkOldPassword();
      await evaluatePassword();
      await savePassword();
      await displayPasswords();
      formContainer.classList.add("hidden");
      passwordTable.classList.remove("hidden");
    };
  
    const cancelButton = document.getElementById("cancelButton");
    cancelButton.onclick = function() {
      formContainer.classList.add("hidden");
      passwordTable.classList.remove("hidden");
    };
  });
  
  displayPasswords();
  