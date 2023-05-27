# code-refactoring-22-23
Wiryawan Khairi <br />
Вирьяван Хайри <br />
972006 <br />

## Lab 1 Laboratory (Code Analysis)
References : Delivery Website Final > js > login.js
#### 1. Hardcoded URL
```
const url = new URL('https://food-delivery.kreosoft.ru/api/account/login');
const header = new Headers();
header.append('Content-Type', 'application/json');
```
#### 2. Redundant Check
```
localStorage.getItem('token') && (window.location.href = 'menu.html');
```
#### 3. Inconsistent Variable Naming
```
const header = new Headers();
header.append('Content-Type', 'application/json');

const headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Authorization', `Bearer ${data.token}`);
```
#### 4. Duplicate Code
```
activateButton(button, spinner);

deactivateButton(button, spinner);

function activateButton(button, spinner) {
	button.disabled = false;
	spinner.classList.add('d-none');
}

function deactivateButton(button, spinner) {
	button.disabled = true;
	spinner.classList.remove('d-none');
}
```
#### 5. Lack of Error Handling
The code doesn't handle server errors very well proved by little to no error handling method to provide better understanding for clients.

possible error handling enhancement :
```
catch (error) {
			console.error(error);
			triggerToast(error.message);
		}
```

#### 6. Long Functions
```
document.addEventListener('DOMContentLoaded', function () {
	const bootstrap = window.bootstrap;

	const form = document.getElementById('loginForm');
	form.addEventListener('submit', async (event) => {
		event.preventDefault();

		const button = form.querySelector('button');
		const spinner = button.querySelector('span');
		deactivateButton(button, spinner);

		form.classList.remove('was-validated');
		form.querySelectorAll('.is-invalid').forEach((element) => {
			element.classList.remove('is-invalid');
		});

		if (!form.checkValidity()) {
			event.stopPropagation();
			setTimeout(() => {
				form.classList.add('was-validated');
				activateButton(button, spinner);
			}, 1500);
			return;
		}

		const loginData = {
			email: form.elements.email.value,
			password: form.elements.password.value,
		};

		try {
			const url = new URL('https://food-delivery.kreosoft.ru/api/account/login');
			const header = new Headers();
			header.append('Content-Type', 'application/json');

			const response = await fetch(url, {
				method: 'POST',
				headers: header,
				body: JSON.stringify(loginData),
			});

			const data = await response.json();

			if (response.ok) {
				const url = new URL('https://food-delivery.kreosoft.ru/api/basket');
				const headers = new Headers();
				headers.append('Content-Type', 'application/json');
				headers.append('Authorization', `Bearer ${data.token}`);

				const response = await fetch(url, {
					method: 'GET',
					headers,
				});

				const carts = await response.json();
				localStorage.setItem('token', data.token);
				localStorage.setItem('carts', JSON.stringify(carts));
				window.location.href = 'menu.html';
			} else {
				const email = form.elements.email;
				const password = form.elements.password;

				email.classList.add('is-invalid');
				password.classList.add('is-invalid');

				email.nextElementSibling.innerHTML = data.errors.email;
				password.nextElementSibling.innerHTML = data.errors.password;

				form.classList.add('was-validated');
				activateButton(button, spinner);
			}
		} catch (error) {
			console.error(error);
			triggerToast(error.message);
		}

		activateButton(button, spinner);
	});

	function triggerToast(message) {
		const toast = document.getElementById('liveToast');
		const trigger = bootstrap.Toast.getOrCreateInstance(toast);

		toast.querySelector('.toast-body').innerHTML = message;
		trigger.show();

		setTimeout(() => {
			trigger.hide();
		}, 1500);
	}
});
```

#### 7. Modularisation
```
class Unauthorized extends Error {
	constructor(message) {
		super(message);
		this.name = 'Unauthorized';
	}
}
```
```
function triggerToast(message) {
		const toast = document.getElementById('liveToast');
		const trigger = bootstrap.Toast.getOrCreateInstance(toast);
		toast.querySelector('.toast-body').innerHTML = message;
		trigger.show();
		setTimeout(() => {
			trigger.hide();
		}, 1500);
	}
```
