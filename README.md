# code-refactoring-22-23
Wiryawan Khairi <br />
Вирьяван Хайри <br />
972006 <br />






## Lab 1 Laboratory (Code Analysis)
References : Delivery Website Final > js > login.js

If I asked "Why could this flaw appear?: I probably would say because I generally more task-goal-oriented. so I did what I know without any consideration on the beauty of the code



#### 1. Hardcoded URL
To put it simply, "hard coded test values" are specific values that are used in setting up a test, as well as during the test itself and in verifying the results. These values are not assigned to a named constant or variable and are typically scalar values or value objects.
<br />
When test data is hard coded, a common issue is that the data can be interrelated or dependent on one another, leading to potential problems or errors.

```
const url = new URL('https://food-delivery.kreosoft.ru/api/account/login');
const header = new Headers();
header.append('Content-Type', 'application/json');
```
#### 2. Redundant Check
This line of code redirects the user to the menu page if they already have a token saved in localstorage. However, the check for the token is redundant because if the token is not present in localStorage, localStorage.getItem('token') will return null which somehow false.
```
localStorage.getItem('token') && (window.location.href = 'menu.html');
```
#### 3. Inconsistent Variable Naming
This flaw/smell appear due to variable that may similar to another variable which resulted confusion and hard to maintain in the future.
<br />
Names should speak language of business(Mattas Sunny, 2020)
```
const header = new Headers();
header.append('Content-Type', 'application/json');

const headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Authorization', `Bearer ${data.token}`);
```
#### 4. Duplicate Code
Code duplication refers to the repetition of an algorithm or data in two or more locations.
<br />
in this code, we may find the very exact logic of 2 function that seem very redundant. we may simplify it for a better understanding and more simple and easy-to-grasp kind of code.
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
The code doesn't handle server errors very well proved by little to no error handling method to provide better understanding for clients. It will better if it has a higher level of error handling to give a better understand to the developer or the future clients about trouble that they are facing.
<br />
possible error handling enhancement :
```
catch (error) {
			console.error(error);
			triggerToast(error.message);
		}
```

#### 6. Long Method
This flaw is appeared to a very crowded function that increase the complexity of its method/function. A good general guideline is to limit a single method to no more than one-half to one full screen height, depending on the resolution and font size being used (Eames Joe, 2020)
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

#### 7. Modularisation smells
This smell arises when data and/or methods that ideally should have been localized into a single abstraction are separated and spread across multiple abstractions. It is like we rewrite a book as a one long story without any breaks (Suryanarayana & Samarthyam, 2015).
<br />
For this specific part that I quote, it is not necessary, but the code will be better if it is created under the principle of modularisation.
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
