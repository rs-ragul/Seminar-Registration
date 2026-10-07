const form = document.getElementById('registrationForm');
const message = document.getElementById('message');
const registerButton = document.getElementById('registerButton');
const clearButton = document.getElementById('clearButton');

form.addEventListener('submit', async function (event) {
    event.preventDefault();
    if (registerButton.disabled) return;

    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const phone = document.getElementById('phone').value.trim();
    const college = document.getElementById('college').value.trim();
    const seminar = document.getElementById('seminar').value;

    message.className = 'error';

    if (name === '' || email === '' || phone === '' || college === '' || seminar === '') {
        message.textContent = 'Please fill in all fields.';
        return;
    }
    if (name.length < 2 || name.length > 80) {
        message.textContent = 'Name must contain 2 to 80 characters.';
        return;
    }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email) || email.length > 120) {
        message.textContent = 'Please enter a valid email address.';
        return;
    }
    if (!/^[6-9][0-9]{9}$/.test(phone)) {
        message.textContent = 'Please enter a valid 10-digit Indian mobile number.';
        return;
    }
    if (college.length < 2 || college.length > 120) {
        message.textContent = 'College name must contain 2 to 120 characters.';
        return;
    }

    const student = { name: name, email: email, phone: phone, college: college, seminar: seminar };
    registerButton.disabled = true;
    clearButton.disabled = true;
    registerButton.textContent = 'Saving...';
    message.textContent = '';

    try {
        // Send the form to Spring Boot, not directly to MySQL.
        const response = await fetch('/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(student)
        });
        const result = await response.text();

        if (response.ok) {
            form.reset();
            message.className = 'success';
            message.textContent = result;
        } else if (response.status === 400 || response.status === 409) {
            message.textContent = result;
        } else {
            message.textContent = 'Registration could not be completed. Please try again.';
        }
    } catch (error) {
        message.textContent = 'Could not confirm the registration. Check your connection and try again. Duplicate registrations are blocked.';
    } finally {
        registerButton.disabled = false;
        clearButton.disabled = false;
        registerButton.textContent = 'Register';
    }
});

form.addEventListener('reset', function () {
    message.textContent = '';
});
