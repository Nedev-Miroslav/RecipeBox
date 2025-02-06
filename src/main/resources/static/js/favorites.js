document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('favoriteForm');

    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault();

            const formAction = this.action;
            const formData = new FormData(this);

            fetch(formAction, {
                method: 'POST',
                body: formData
            })
                .then(response => {

                    window.location.href = '/favorites';
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }
});
